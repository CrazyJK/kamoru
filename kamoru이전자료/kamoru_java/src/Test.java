import java.net.*;
public class Test {

	public static String getFilesizeTxt(long filesize){
		if(filesize < 1024){
			return filesize + "b";
		}else if(filesize < 1024*1024l){
			return filesize/1024 + "Kb";
		}else if(filesize < 1024*1024*1024l){
			return filesize/1024/1024 + "Mb";
		}else if(filesize < 1024*1024*1024*1024l){
			return Math.round(filesize*10l/1024l/1024l/1024l) / 10f + "Gb";
		}else if(filesize < 1024*1024*1024*1024*1024l){
			return Math.round(filesize*100l/1024/1024/1024/1024l) / 100f + "Tb";
		}else{
			return "unknown size [" + filesize + "]";
		}
	}
	
    public static String replaceAll(String s, String s1, String s2)
    {
        int i = s.indexOf(s1);
        if(i != -1)
        {
            int j = 0;
            int k = s1.length();
            StringBuffer stringbuffer = new StringBuffer();
            for(; i != -1; i = s.indexOf(s1, j))
            {
                stringbuffer.append(s.substring(j, i));
                stringbuffer.append(s2);
                j = i + k;
            }

            stringbuffer.append(s.substring(j));
            return stringbuffer.toString();
        } else
        {
            return s;
        }
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(Math.round(76.00455*100)/100f);
		System.out.println(Test.getFilesizeTxt(1689628130));
		System.out.println(1024*1024*1024*1024l);
		System.out.println(1024*1024*1024*2-1);
		System.out.println(Test.replaceAll("select  /*+ first_rows */ count() %% 1 from rlvntdata c", "%", ""));
		/*
		
		String str = "%B9%AB%C1%A6-2_%BA%B9%BB%E7_hematospring.jpg";
		System.out.println(URLEncoder.encode(str));
		System.out.println(URLDecoder.decode(str));
		
		System.out.println(
				new java.text.SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss").format(new java.util.Date())
		);

		//long milli = ;
		System.out.println(
				new java.text.SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss").format(new java.util.Date(new java.util.Date().getTime() - 1000l*60l*60l*24l*30l))
		);

		String a_reg_date_start = "";
		String a_reg_date_end = "";
		
		String  default_date_pattern = "yyyy-MM-dd' 'HH:mm:ss";
		java.util.Date currDate = new java.util.Date();
		long currMilli = currDate.getTime();
		long prevMilli = currMilli - 1000l*60l*60l*24l*30l;
		java.util.Date prevDate = new java.util.Date(prevMilli);
		if(a_reg_date_start.trim().length() == 0){
			a_reg_date_start = new java.text.SimpleDateFormat(default_date_pattern).format(prevDate); 
		}
		if(a_reg_date_end.trim().length() == 0){
			a_reg_date_end = new java.text.SimpleDateFormat(default_date_pattern).format(currDate);
		}
		System.out.println(a_reg_date_start);
		System.out.println(a_reg_date_end);
		*/
	}

}
