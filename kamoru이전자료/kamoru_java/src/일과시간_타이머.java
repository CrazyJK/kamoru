import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;
import java.awt.Toolkit;

public class 일과시간_타이머 {

	Toolkit 툴킷;
	final String[][] 알람 = {
			{"알람체크", "09:50"},
			{"담배탐  ", "10:50"},
			{"테스트  ", "11:15"},
			{"점심시간", "11:30"},
			{"담배탐  ", "13:00"},
			{"담배탐  ", "14:00"},
			{"담배탐  ", "15:00"},
			{"담배탐  ", "16:00"},
			{"담배탐  ", "17:00"},
			{"퇴근시간", "18:00"}
	};
	
	public void 시작(){
		notice("타이머 시작");
		notice("설정 내용");
		툴킷 = Toolkit.getDefaultToolkit();
		
		for(int i=0 ; i<알람.length ; i++){
			String[] 알람시간 = 알람[i][1].split(":");
			Calendar 달력 = Calendar.getInstance();
			달력.set(달력.get(Calendar.YEAR), 
					 달력.get(Calendar.MONTH), 
					 달력.get(Calendar.DAY_OF_MONTH), 
					 Integer.parseInt(알람시간[0]), 
					 Integer.parseInt(알람시간[1]));
			
			Timer 타이머 = new Timer();
			타이머.scheduleAtFixedRate(new 타이머타스크(알람[i]), 0, 60*1000);
			notice("\t" + 알람[i][0] + " : " + 알람[i][1]);
		}
		notice("설정 완료");
	}
	
	class 타이머타스크 extends TimerTask {
		String 알람내용;
		String 알람시간;
		String 현재시간;
		Calendar 현재일자;
		boolean 수행했음 = false;
		
		public 타이머타스크(String[] 알람){
			this.알람내용 = 알람[0];
			this.알람시간 = 알람[1];
		}
		
		public void run() {
			현재일자 = Calendar.getInstance();
			현재시간 = 현재일자.get(Calendar.HOUR_OF_DAY) + ":" + 두자리숫자(현재일자.get(Calendar.MINUTE)); 
//			System.out.println("TimerTask : " + 알람내용 + ":" + 현재시간);
			if(알람시간.equals(현재시간)) {
				if(수행했음){
					수행했음 = false;
				}else{
					System.out.println("알람시간입니다.!!! " + 알람시간 + " = " + 알람내용);
					툴킷.beep();
					try{
						Runtime.getRuntime().exec("eog /home/kamoru/사진/사진/matchtt_318.jpg");
					}catch(Exception e){
						e.printStackTrace();
					}
					수행했음 = true;
				}
			}else{
//				System.out.println("호출 " + 알람시간 + " = " + 알람내용);
			}
		}
		
		private String 두자리숫자(int i) {
			return (i<10) ? "0" + i : String.valueOf(i);
		}
	}

	public static void main(String[] args) {
		new 일과시간_타이머().시작();
		
	}

	public void notice(Object obj){
		System.out.println(obj);
	}
}
