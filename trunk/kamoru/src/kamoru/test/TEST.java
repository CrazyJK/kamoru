package kamoru.test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TEST {

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

	public static void main(String[] args) {
		try {
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

	}

}
