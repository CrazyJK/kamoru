package jk.kamoru.tools.gnom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import jk.kamoru.tools.ImageDownloader;
import jk.kamoru.util.FileUtils;
import jk.kamoru.util.StringUtils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.googlecode.mp4parser.util.Path;

public class GnomImageDownloader {
	
	private static final Logger logger = LoggerFactory.getLogger(GnomImageDownloader.class);

	final String urlPattern = "http://cham.us.to/?bbs=%s&no=%s&offset=%s&time=%s";
	final String[] bbsList = {"dcaworld", "picture", "sexy"};
	final String PAGENO_PREFIX = ".pageno";
	final String titleCssQuery = "div.b2";
	
	List<String> imagePrefixList = Arrays.asList("png", "jpg", "jpeg", "gif", "webp", "bmp");

	String downloadPath = "/home/kamoru/image";
	
	public GnomImageDownloader() {
	}
	
	public void process() {
		logger.info("그놈 이미지 가져오기 시작");
		// 게시판별 작업
		for (String bbs : bbsList) { 
			logger.info("{} 게시판 시도", bbs);
			// 마지막 페이지 읽어오기
			int pageNo;
			try {
				pageNo = getLastPageNo(bbs);
			} catch (IOException e1) {
				logger.error("{}의 마지막 페이지를 알수 없음. {}", bbs, e1);
				continue;
			}

			// 저장할 폴더 확인
			File downloadDir = new File(downloadPath, bbs + "/" + DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
			if (!downloadDir.exists())
				try {
					FileUtils.forceMkdir(downloadDir);
				} catch (IOException e) {
					logger.error("폴더 생성 실패", e);
					continue;
				}

			// 게시판의 이미지 받기
			boolean result = true;
//			while (result) {
//				pageNo++;
//
//				// url string 
//				String urlString = String.format(urlPattern, bbs, pageNo, 0, System.currentTimeMillis());
//				
//				ImageDownloader imageDownloader = new ImageDownloader(urlString, pageNo, downloadDir.getAbsolutePath(), titleCssQuery);
//				result = imageDownloader.download();
//			}
//			try {
//				saveLastPageNo(bbs, --pageNo);
//			} catch (IOException e) {
//				logger.error("{}의 마지막 페이지 번호를 저장 할 수 없음. pageNo={}", bbs, pageNo);
//			}
			
			// java.util.concurrent.Future 사용 비동기 방식 처리
			int futureCount = 10;
			while (result) {
				// futureCount 만큼 비동기 수행
				final List<Future<Boolean>> futures = new ArrayList<Future<Boolean>>();
				final ExecutorService eService = Executors.newFixedThreadPool(futureCount);
				for (int i=0; i<futureCount; i++) {
					pageNo++;
					String urlString = String.format(urlPattern, bbs, pageNo, 0, System.currentTimeMillis());
					ImageDownloader imageDownloader = new ImageDownloader(urlString, pageNo, downloadDir.getAbsolutePath(), titleCssQuery);
					futures.add(imageDownloader.download(eService));
				}
				eService.shutdown();

				// 결과 확인
				for (final Future<Boolean> future : futures) {
					try {
						boolean downloadResult = future.get();
						logger.info("{} 다운로드 결과 {}", bbs, downloadResult);
						result = result && downloadResult;
					} catch (InterruptedException e) {
						logger.error("", e);
						result = false;
					} catch (ExecutionException e) {
						logger.error("", e);
						result = false;
					}
				}
			}
			try {
				pageNo = pageNo - futureCount;
				saveLastPageNo(bbs, pageNo);
				logger.info("{} 마지막 페이지 번호 저장 {}", bbs, pageNo);
			} catch (IOException e) {
				logger.error("{}의 마지막 페이지 번호를 저장 할 수 없음. pageNo={}", bbs, pageNo);
			}
		}
		logger.info("그놈 이미지 가져오기 완료");
	}
	
	private void saveLastPageNo(String bbs, int pageNo) throws IOException {
		FileUtils.writeStringToFile(new File(downloadPath, bbs + PAGENO_PREFIX), String.valueOf(pageNo));
	}

	private int getLastPageNo(String bbs) throws IOException {
		String lastNo = FileUtils.readFileToString(new File(downloadPath, bbs + PAGENO_PREFIX));
		int pageNo = NumberUtils.toInt(lastNo.trim());
		if (pageNo == 0) 
			throw new IOException("페이지 번호를 알 수 없음. text=" + lastNo);
		return pageNo;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		GnomImageDownloader downloader = new GnomImageDownloader();
		downloader.process();

	}

}
