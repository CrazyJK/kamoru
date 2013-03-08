package kamoru.app.spring.video.source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import kamoru.app.spring.video.domain.Actress;

public class ActressFactory {

	public static List<Actress> getActress(String actressNames) {
		List<Actress> actressList = new ArrayList<Actress>();
		
		String[] namesArr = StringUtils.split(actressNames, ",");
		for(String name : namesArr) {
			name = StringUtils.join(StringUtils.split(name), " ");
			boolean bFindSameName = false;
			for(Actress actress : actressList) {
				if(equalsName(actress, name)) {
					bFindSameName = true;
					break;
				}
			}
			if(!bFindSameName) {
				Actress actress = new Actress();
				actress.setName(name);
				actressList.add(actress);
			}
		}
		
		return actressList;
	}

	private static boolean equalsName(Actress actress, String name2) {
		String name1 = actress.getName();
		if(name1 == null || name2 == null) return false;
		return forwardNameSort(name1).equalsIgnoreCase(forwardNameSort(name2)) || name1.toLowerCase().indexOf(name2.toLowerCase()) > -1;
	}
	private static String forwardNameSort(String name) {
		String[] nameArr = StringUtils.split(name);
		Arrays.sort(nameArr);
		String retName = "";
		for(String part : nameArr) {
			retName += part + " ";
		}
		return retName.trim();
	}

}
