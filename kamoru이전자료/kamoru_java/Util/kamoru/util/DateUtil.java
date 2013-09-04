package kamoru.util;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

	private static final String  default_date_pattern = "yyyy-MM-dd' 'HH:mm:ss";

	/**
	 * get timestamp. format 'yyyy-MM-dd hh:mm:ss'
	 * @return a timestamp 
	 */
	public static String getDate(){
		SimpleDateFormat sdf = new SimpleDateFormat(default_date_pattern); 		
		return sdf.format(new Date());
	}

	public static String getDate(String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern); 
		return sdf.format(new Date());
	}
	
	public static String getDate(Date date, String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern); 
		return sdf.format(date);
	}
	

	public static String getDate(int year, int month, int day){
		SimpleDateFormat sdf = new SimpleDateFormat(default_date_pattern); 
		return sdf.format(new Date(year,month,day));
	}

	public static String getDate(String pattern, int year, int month, int day){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern); 
		return sdf.format(new Date(year-1900,month-1,day));
	}

	public static String getDate(String pattern, String date, String regex){
		String[] arrDate = date.split(regex);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern); 
		return sdf.format(new Date(Integer.parseInt(arrDate[0])-1900,Integer.parseInt(arrDate[1])-1,Integer.parseInt(arrDate[2])));
	}

	public static int getWeekOfYear(int year, int month, int day){
		Calendar cal = Calendar.getInstance(); 
		cal.set(year,month-1,day);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	public static int getWeekOfYear(String date, String regex){
		String[] arrDate = date.split(regex);
		Calendar cal = Calendar.getInstance();             
		cal.set(Integer.parseInt(arrDate[0]),Integer.parseInt(arrDate[1])-1,Integer.parseInt(arrDate[2]));
		return cal.get(Calendar.WEEK_OF_YEAR);
	}
	
	public static int getDayinweek(int year, int month, int day){
		String str = DateUtil.getDate("EEEE", year, month, day);
		int d = 0;
		if(str.equals("일요일")){
			d = 0;
		}else if(str.equals("월요일")){
			d = 1;
		}else if(str.equals("화요일")){
			d = 2;
		}else if(str.equals("수요일")){
			d = 3;
		}else if(str.equals("목요일")){
			d = 4;
		}else if(str.equals("금요일")){
			d = 5;
		}else if(str.equals("토요일")){
			d = 6;
		}
		return d;
	}

	public static int getDayinweek(String year, String month, String day){
		return getDayinweek(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
	}
	
	public static int getDayinweek(String date, String regex){
		String[] arrDate = date.split(regex);
		return getDayinweek(Integer.parseInt(arrDate[0]),Integer.parseInt(arrDate[1]),Integer.parseInt(arrDate[2]));
	}
	
	public static int getLastDayinMonth(int year, int month){
		int d = 31;
		if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
			d = 31;
		}else if(month == 4 || month == 6 || month == 9 || month == 11){
			d = 30;
		}else if(month == 2 && (year%4 == 0)){
			d = 29;
		}else{
			d = 28;
		}
		return d;
	}
    public static String format(String sourceDate, String sourceFormat, String targetFormat, String zoneID)
    {
        try
        {
            SimpleDateFormat sdf1 = new SimpleDateFormat(sourceFormat);
            Date date = sdf1.parse(sourceDate);
            return formatDate(date, targetFormat, zoneID);
        }
        catch(Exception e)
        {
            return null;
        }
    }
    public static String formatDate(Date sourceDate, String targetFormat, String zoneID)
    {
        String newDate = "";
        try
        {
            SimpleDateFormat sdf2 = new SimpleDateFormat(targetFormat);
            if(null != zoneID && !"".equals(zoneID))
                sdf2.setTimeZone(TimeZone.getTimeZone(zoneID));
            newDate = sdf2.format(sourceDate);
        }
        catch(Exception e) { }
        return newDate;
    }

    public static void main(String[] args){
    	System.out.println(
    			DateUtil.format("2007-01-02 11:11:11","yyyy-MM-dd' 'HH:mm:ss","yyyy-MM-dd' 'HH:mm:ss",null)
    	);
    	System.out.println(
    			DateUtil.getDate("yyyy-MM-dd_HH:mm:ss")
    	);
    	System.out.println(
    			DateUtil.getDate("yyyy/MM/dd/")
    	);
    }
}
