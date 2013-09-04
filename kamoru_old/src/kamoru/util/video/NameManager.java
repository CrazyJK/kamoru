package kamoru.util.video;

import java.io.File;
import java.io.IOException;

public class NameManager {

	static String[] name = {
			"[Prestige][abs177] 2012-12-11 ABS-177 아오키 카렌 나를 유혹하는 옆집 예쁜 언니",
			"[Prestige][abs178] 2012-12-11 ABS-178 Yui Hoshino 나를 유혹하는 옆집 예쁜 언니",
			"[Prestige][abs179] 2012-12-11 ABS-179 히라세 미쿠루 1 박 2 일, 미소녀 완전 예약제",
			"[Prestige][abs180] 2012-12-21 ABS-180 Kokone Mizutani 프레스티지 졸업 기념! 프레스티지으로 팬 감사 제! 버스 투어",
			"[Prestige][abs181] 2013-01-01 ABS-181 Asuka Kirara 천연 성분 유래 키라라 즙 100",
			"[Prestige][abs182] 2013-01-01 ABS-182 Mao Hamasaki 만족도 만점 신인 소프",
			"[Prestige][abs183] 2013-01-01 ABS-183 Lisa 나를 유혹하는 옆집 예쁜 언니",
			"[Prestige][abs184] 2013-01-01 ABS-184 Ayami Shunka 彩美 순 효과가 봉사 해 버리는 최신 중독성 에스테",
			"[Prestige][abs185] 2013-01-11 ABS-185 Kokoro Harumiya 나를 유혹하는 옆집 예쁜 언니",
			"[Prestige][abs186] 2013-01-11 ABS-186 1 박 2 일, 미소녀 완전 예약제",
			"[Prestige][chs031] 2012-11-09 CHS-031 초보 헌터 33",
			"[Prestige][chs032] 2012-12-11 CHS-032 초보 헌터 34",
			"[Prestige][chs033] 2013-01-11 CHS-033 초보 헌터 35",
			"[Prestige][del014] 2012-11-01 DEL-014 riko chitose 노을 아마추어 택배 소프 14",
			"[Prestige][deu003] 2012-11-09 DEU-003 히라세 미쿠루 첫 촬영 아마추어 3",
			"[Prestige][deu004] 2012-12-11 DEU-004 아리 무라 첫 촬영 아마추어 4",
			"[Prestige][dom043] 2012-12-01 DOM-043 나미 제멋대로 29",
			"[Prestige][dom044] 2013-01-11 DOM-044 유이 제멋대로 30",
			"[Prestige][edd214] 2012-12-01 EDD-214 모나 에스컬레이션 드 시로와 딸 214",
			"[Prestige][edd215] 2012-12-01 EDD-215 사야카 에스컬레이션 드 시로와 딸 215",
			"[Prestige][edd216] 2013-01-01 EDD-216 미즈호 에스컬레이션 드 시로와 딸 216",
			"[Prestige][edd217] 2013-01-01 EDD-217 팥고물 에스컬레이션 드 시로와 딸 217",
			"[Prestige][inu049] 2012-10-20 INU-049 Mao Hamasaki 순종 애완 동물 후보생 30",
			"[Prestige][inu050] 2012-11-01 INU-050 Ayami Shunka 순종 애완 동물 후보생 31",
			"[Prestige][inu051] 2012-11-20 INU-051 우치다 순종 애완 동물 후보생 32",
			"[Prestige][inu052] 2012-12-21 INU-052 Kokoro Harumiya 순종 애완 동물 후보생 33",
			"[Prestige][inu053] 2013-01-01 INU-053 Mei Mizuhara 순종 애완 동물 후보생 34",
			"[Prestige][job030] 2012-12-21 JOB-030 Asano Woman working2 Vol.34",
			"[Prestige][loo004] 2012-12-01 LOO-004 미쿠 연하의 그녀 4",
			"[Prestige][loo005] 2012-12-21 LOO-005 아리 무라 성인 여자에하십시오",
			"[Prestige][loo006] 2013-01-11 LOO-006 아오이 코하루 성인 여자에하십시오 2",
			"[Prestige][mas092] 2012-11-20 MAS-092 속 아마추어 여자, 빌려드립니다. VOL.59",
			"[Prestige][mas093] 2012-12-11 MAS-093 Ayami Shunka 절대적 미소녀, 빌려드립니다. ACT.25",
			"[Prestige][mas094] 2012-12-21 MAS-094 속 아마추어 여자, 빌려드립니다. VOL.60",
			"[Prestige][mas095] 2012-12-21 MAS-095 속 아마추어 여자, 빌려드립니다. VOL.61",
			"[Prestige][mas096] 2013-01-01 MAS-096 Ichika Kanhata 절대적 미소녀, 빌려드립니다. ACT.26",
			"[Prestige][mas097] 2013-01-11 MAS-097 히라세 미쿠루 절대적 미소녀, 빌려드립니다. ACT.27",
			"[Prestige][net009] 2012-12-01 NET-009 오토 나시 섹스에 빠진다 9",
			"[Prestige][ppb012] 2012-11-09 PPB-012 후지사와 미우 PRESTIGE PREMIUM BEST",
			"[Prestige][ppb013] 2012-12-11 PPB-013 Asuka Kirara PRESTIGE PREMIUM BEST Vol.04",
			"[Prestige][ppb014] 2013-01-11 PPB-014 Rola Takizawa PRESTIGE PREMIUM BEST",
			"[Prestige][sad058] 2012-11-01 SAD-058 우치다 1 박 2 일, 미소녀 완전 예약제",
			"[Prestige][tap002] 2012-12-01 TAP-002 PRESTIGE 전속 여배우가 당신의 꿈 실현됩니다",
			"[Prestige][yrz055] 2012-11-20 YRZ-055 에스컬레이트 너무 숙녀 5 명, 당신의 집에 돌격 방문. 03",
			"[Prestige][yrz056] 2012-11-20 YRZ-056 전체 가치 교섭! 거리에서 소문의, 순진한 인기 여성을 노려라! 15",
			"[Prestige][yrz057] 2012-11-20 YRZ-057 데려 콘 투숙 대작전! 5",
			"[Prestige][yrz058] 2012-12-01 YRZ-058 인간 관찰 문서 QnA 8",
			"[Prestige][yrz059] 2012-12-01 YRZ-059 You win woman work vol.20",
			"[Prestige][yrz060] 2012-12-11 YRZ-060 데려 콘 투숙 대작전! 6",
			"[Prestige][yrz061] 2012-12-21 YRZ-061 전체 가치 교섭! 거리에서 소문의, 순진한 인기 여성을 노려라! 16",
			"[Prestige][yrz062] 2012-12-21 YRZ-062 집단 최면",
			"[Prestige][yrz063] 2013-01-01 YRZ-063 인간 관찰 문서 QnA 8",
			"[Prestige][yrz064] 2013-01-01 YRZ-064 Woman eat work 3",
			"[Prestige][yrz065] 2013-01-11 YRZ-065 데려 콘 투숙 대작전! 7",
			"[Prestige][yrz066] 2013-01-11 YRZ-066 질내 사정 서클 일지 Vol.4"
			}; 
	
		
	
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		/* 파일명 소문자로 바꾸기
		String path = "G:\\Unwatched_video";
		File dir = new File(path);
		File[] files = dir.listFiles();
		for(File f : files) {
			String name = f.getName().toLowerCase();
			File dest = new File(path + "\\" + name);
			System.out.print(dest.getAbsolutePath());
			System.out.println(" " + f.renameTo(dest));
		}
		*/
//		for(String str: name) {
//			new File("E:\\AV_JAP\\Prestige\\" + str + ".txt").createNewFile();
//		}
		File[] dirs = new File[2];
		dirs[0] = new File("E:\\AV_JAP");
		dirs[1] = new File("E:\\AVT");
		for(File dir: dirs) {
			File[] files = dir.listFiles();
			System.out.println("Directory : " + dir.getAbsolutePath());
			for(File file: files) {
				if(file.isFile())
					System.out.println(file.getName());
			}
		}
	}

}
