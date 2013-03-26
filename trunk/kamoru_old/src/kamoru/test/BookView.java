package kamoru.test;

import java.io.*;
import java.util.ArrayList;

public class BookView {

	private final int lineNo = 10;
	
	public String filepath = null;
	public ArrayList<String> book = null;
	public int totalLine = 0;
	
	public BookView(String filepath) {
		this.filepath = filepath;
		book = new ArrayList<String>();
		readBook();
		viewBook();
	}
	
	public void readBook() {
		try {
			debug("Start reading from '" + filepath + "'");
			long starttime = System.nanoTime();
			String line = ""; 
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filepath)), "EUC-KR"));
			while((line = br.readLine()) != null) {
				book.add(line);
			}
			br.close();
			totalLine = book.size();
			debug("Finish reading. \n\tTotal line is " + totalLine + "\n\tElapse time : " + (System.nanoTime() - starttime) / 1000000 + " ms");
		} catch (FileNotFoundException e) {
			debug("파일을 찾을수 없음");
			e.printStackTrace();
		} catch (IOException e) {
			debug("파일을 읽을 수 없음");
			e.printStackTrace();
		}

	}
	
	public void viewBook() {
		try {
			int currLine = 0;
			System.out.print("Input start line (bookmark : " + readCurrentLine() + ") ");
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			try {
				currLine = Integer.parseInt(in.readLine());
			}catch(NumberFormatException nfe) {
				currLine = 0;
			}
			/*
			String s = null;
			while((s=in.readLine()) != null) {
				if("quit".equalsIgnoreCase(s)) { // End
					break;
				} else if("qwe789".indexOf(s) > -1) { // back view
					currLine = showBook(currLine - lineNo * 2);
				} else { // next view
					currLine = showBook(currLine);
				}
			}
			*/

			while(checkLine(currLine)) {
				int i = 10;
				
				if(i > 10) { // back view
					currLine = showBook(currLine - lineNo * 2);
				} else { // next view
					currLine = showBook(currLine);
				}
				System.err.print("[" + currLine + " / " + totalLine + "]");
				i = System.in.read();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	boolean checkLine(int line) {
		if(totalLine > line) {
			return true;
		} else {
			System.out.println("The End Of Book");
			return false;
		}
	}
	
	public int showBook(int line) {
		if(line < 0) {
			return 0;
		}
		for(int i=0; i<lineNo; i++) {
			if(line >= totalLine) {
				break;
			}
			System.out.println((line + 1) + "\t: " + book.get(line));
			line++;
		}
		saveCurrentLine(line);
		return line;
	}
	
	void saveCurrentLine(int i) {
		try {
			FileWriter fw = new FileWriter(filepath + ".bookmark");
			fw.write(String.valueOf(i));
			fw.flush();
			fw.close();
		} catch (IOException e) {
			debug("현재 라인 저장 실패");
			e.printStackTrace();
		}
	}
	
	String readCurrentLine(){
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filepath + ".bookmark")));
			return br.readLine();
		} catch (Exception e) {
			debug("현재 라인 읽기 실패 " + e.getMessage());
			//e.printStackTrace();
			return "0";
		}
	}
	
	private void debug(Object o) {
		System.out.println(o);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BookView bv = new BookView("/media/Data/Daum 클라우드/MyDocs/books/데프콘/데프콘/데프콘 3부 한미전쟁.txt");
	}

}
