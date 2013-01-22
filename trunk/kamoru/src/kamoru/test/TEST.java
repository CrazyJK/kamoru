package kamoru.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class TEST {

    public static byte cElectronicDocFlag; 

	public String getFormID(String strdate) throws ParseException, SQLException {
		StringBuffer loadDataBuffer = new StringBuffer();
		String dataValueBuffer = "";
		java.util.Date date = new java.text.SimpleDateFormat("yyyyMMdd")
				.parse(strdate);

		System.out.println("getFormID date:" + date);

		java.sql.PreparedStatement ps = null;
		ps.setDate(1, new java.sql.Date(date.getTime()));

		return null;
	}

	public static String getSizeConvert(long size) {
		DecimalFormat df = new DecimalFormat(".#");
		if(size < 1000l){
			return size + "B";
		}else if(size < 1000000l){
			return Math.round(size/1000l) + "KB";
		}else if(size < 1000000000l){
			return (size/1000000l) + "MB";
		}else if(size < 1000000000000l){
			return df.format(size/1000000000f) + "GB";
		}else if(size < 1000000000000000l){
			return df.format(size/1000000000000f) + "TB";
		}else{
			return String.valueOf(size);
		}
	}
	
	public static void main(String[] args) throws UnknownHostException {
		try {
			System.out.println(getSizeConvert(12345679));
			System.out.println(getSizeConvert(123456790));
			System.out.println(getSizeConvert(1234567901));
			System.out.println(getSizeConvert(12345679012l));
			System.out.println(1213/123);
			System.out.println(1213/123l);
			System.out.println(1213/123f);
			
			
			
			// "2007-07-22" 이란 문자열로 2007년 7월 22일의 정보를 갖는 Date객체를 만들어보자
			String textDate = "2007-07-22";

			// 입력할 날짜의 문자열이 yyyy-MM-dd 형식이므로 해당 형식으로 포매터를 생성한다.
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");

			// SimpleDateFormat.parse()메소드를 통해 Date객체를 생성한다.
			// SimpleDateFormat.parse()메소드는 입력한 문자열 형식의 날짜가
			// 포맷과 다를경우 java.text.ParseException을 발생한다.
			java.util.Date date = format.parse(textDate);

			// 위에서 만든 date객체가 정말 7월22일인지 확인 해보자.
			java.text.SimpleDateFormat format1 = new java.text.SimpleDateFormat(
					"yyyy년MM월dd일 HH시mm분ss초");
			String dateString = format1.format(date);
			// Date객체의 날자를 확인한다.. 결과 : 2007년07월22일 00시00분00초
			System.out.println(dateString);
		} catch (java.text.ParseException ex) {
			ex.printStackTrace();
		}
		java.io.File f = new java.io.File("/home/gw67/~$GW 6.7.5.9 Structure.pptx");
		
		System.out.println("Exist " + f.exists());
		System.out.println("isFile " + f.isFile());
		System.out.println("canWrite " + f.canWrite());
		System.out.println("renameTo " + f.renameTo(new java.io.File("/home/kamoru/delete.pptx")));
		System.out.println("Delete " + f.delete());

		System.out.println((byte)50);
		System.out.println(50);
		System.out.println((byte)50 + "");
		
        cElectronicDocFlag = 49;

        if(cElectronicDocFlag == 49)
        	System.out.print(cElectronicDocFlag);
        else
        	System.out.print((byte)50);

        
		byte bt = 1;
		System.out.println(cElectronicDocFlag);

		char ch = 50;
		System.out.println(ch);

		
		 Date date = new Date();
	    SimpleDateFormat sdf;
	    sdf = new SimpleDateFormat("hh:mm:ss");
	    System.out.println(sdf.format(date));
	    sdf = new SimpleDateFormat("dd MMM yyyy hh:mm:ss zzz");
	    System.out.println(sdf.format(date));
	    sdf = new SimpleDateFormat("E MMM dd yyyy");
	    System.out.println(sdf.format(date));
		
	    String str = "aaaa";
	    System.out.println(str.split(",").length);
	    
	    System.out.println("System property");
	    Properties prop = System.getProperties();
	    Enumeration enm =  prop.keys();
	    while(enm.hasMoreElements()) {
	    	String key = (String)enm.nextElement();
	    	System.out.println(key + "\t " + System.getProperty(key));
	    	
	    }
	    
	    System.out.println();
	    System.out.println(System.currentTimeMillis());
	    System.out.println(Long.parseLong("30")*24*60*60*1000);
	    System.out.println();
	    
	    System.out.println("".split(";").length);
	 
	    System.out.println(System.getProperty("os.name"));
	    
	    List<String> list = new ArrayList<String>();
	    list.add("111");
	    list.add("222");
	    System.out.println(list.toString());
	    
	    System.out.println(
	    		InetAddress.getLocalHost().getHostName()
	    		);
	}

}
