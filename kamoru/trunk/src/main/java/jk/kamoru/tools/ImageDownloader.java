package jk.kamoru.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import jk.kamoru.util.StringUtils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 입력된 URL에 있는 이미지를 모두 다운 받는다.
 * @author kamoru
 *
 */
public class ImageDownloader {

	private static final Logger logger = LoggerFactory.getLogger(ImageDownloader.class);

	List<String> imagePrefixList = Arrays.asList("png", "jpg", "jpeg", "gif", "webp", "bmp");

	/**
	 * 이미지가 있는 URL 
	 */
	private String urlString;
	/**
	 * 이미지 저장할 폴더
	 */
	private String downloadDir;
	/**
	 * 이미지 제목으로 사용할 태그의 CSS Query
	 */
	private String titleCssQuery;
	/**
	 * URL의 페이지 번호. 게시판 같은 경우에 해당
	 */
	private int pageNo;

	
	
	/**
	 * @param urlString 이미지가 있는 URL
	 * @param pageNo URL의 페이지 번호. 게시판 같은 경우에 해당
	 * @param downloadDir 이미지 저장할 폴더
	 * @param titleCssQuery 이미지 제목으로 사용할 태그의 CSS Query
	 */
	public ImageDownloader(String urlString, int pageNo, String downloadDir, String titleCssQuery) {
		super();
		this.urlString = urlString;
		this.pageNo = pageNo;
		this.downloadDir = downloadDir;
		this.titleCssQuery = titleCssQuery;
	}

	public DownloadResult download() {
		// jsoup HTML parser를 이용해 접속해서 이미지 찾기
		Document document;
		try {
			document = Jsoup.connect(urlString).get();
		} catch (IOException e) {
			logger.debug("접속이 안됨 {} - {}", e.getMessage(), urlString);
			return new DownloadResult(pageNo, false, "접속이 안됨 " + e.getMessage() + " " + urlString);
		}
		// 페이지 타이틀 구하기
		String title = null;
		if (titleCssQuery == null) {
			title = document.title();
		}
		else {
			title = document.select(titleCssQuery).first().text();
		}
		// img 태그 찾기
		Elements imgTags = document.getElementsByTag("img");
		int foundImageCount = imgTags.size();
		
		if (foundImageCount == 0) {
			logger.debug("이미지가 없음 {}", urlString);
			return new DownloadResult(pageNo, false, "이미지가 없음 " + urlString);
		}
		else {
			logger.debug("no={} [{}] 이미지 {}", pageNo, title, foundImageCount);
		
			// 찾은 이미지 다운로드
			int count = 0;
			for (Element imgTag : imgTags) {
				String imgSrc = imgTag.attr("src");

				// download thread start
				ImageDownload imageDownload = new ImageDownload(imgSrc, title, ++count); 
				imageDownload.start();
				
			}
			return new DownloadResult(pageNo, true);
		}
	}
	
	public Future<DownloadResult> download(final ExecutorService executorService) {
		return executorService.submit(new Callable<DownloadResult>() {

			@Override
			public DownloadResult call() throws Exception {
				logger.debug("task start - {}", urlString);
				return download();
			}
		});
	}
	
	private class ImageDownload extends Thread {
		
		private String imgSrc;
		private String title;
		private int count;
		
		public ImageDownload(String imgSrc, String title, int count) {
			super();
			this.imgSrc = imgSrc;
			this.title = title;
			this.count = count;
		}

		public void run() {

			// Executes a request image
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = null;
			HttpResponse httpResponse = null;
			try {
				httpGet = new HttpGet(imgSrc);
				httpResponse = httpClient.execute(httpGet);
			} catch (Exception e) {
				logger.debug("이미지 연결 실패 {} - {}", imgSrc, e.getMessage());
				return;
			}
			HttpEntity entity = httpResponse.getEntity();
			
			// 확장자 결정
			Header contentTypeHeader = httpResponse.getLastHeader("Content-Type");
			String[] contentType = StringUtils.split(contentTypeHeader.getValue(), '/');
			String imagePrefix = "jpg";
			if (contentType != null && contentType.length > 1) {
				if (contentType[0].equals("image"))
					imagePrefix = contentType[1];
				else {
					String srcBasedPrefix = StringUtils.substringAfterLast(imgSrc, ".");
					if (imagePrefixList.contains(srcBasedPrefix.toLowerCase()))
						imagePrefix = srcBasedPrefix;
				}
			}
			
			/* Header에서 content-type을 구하기 위한 테스트 코드
			Header[] headers = httpResponse.getAllHeaders();
			for (Header header : headers) {
				logger.info("Header info {}={}", header.getName(), header.getValue());
			}*/
			
			// 이미지 파일로 저장
			if (entity != null) {
				String imageName = null;
				if (pageNo < 1)
					imageName = String.format("%s-%s.%s", title, ++count, imagePrefix);
				else 
					imageName = String.format("%s_%s-%s.%s", pageNo, title, ++count, imagePrefix);
				File imageFile = new File(downloadDir, imageName);
				
				InputStream inputStream = null;
				OutputStream outputStream = null;
				try {
					inputStream = entity.getContent();
					outputStream = new FileOutputStream(imageFile);
					byte[] buffer = new byte[1024];
					int length = 0;
					while ((length = inputStream.read(buffer)) >0) {
						outputStream.write(buffer, 0, length);
					}
					logger.debug("{} - save as {} from {}", urlString, imageFile.getAbsolutePath(), imgSrc);
				} catch (IOException e) {
					logger.debug("다운로드 실패 {} - {}", imgSrc, e.getMessage());
				} finally {
					if (outputStream != null)
						try {
							outputStream.close();
						} catch (IOException e) {
							logger.error("{}", e.getMessage());
						}
					if (inputStream != null)
						try {
							inputStream.close();
						} catch (IOException e) {
							logger.error("{}", e.getMessage());
						}
				}
			}
			else {
				logger.warn("entity is null. {}", imgSrc);
			}		
		}
	}
}
