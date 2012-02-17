package strutsguide.beans;

import java.util.HashMap;

public class LoginProcess {

	private HashMap userInfos = null;
	private HashMap userPasswords = null;
	private static LoginProcess instance = new LoginProcess();
	
	private LoginProcess() {
		userPasswords = new HashMap();
		userInfos = new HashMap();
		
		userPasswords.put("kamoru", "1234");
		userInfos.put("kamoru", new UserInfoBean("kamoru", "010-2762-6880", "kamoru@kamoru.com"));
	}
	
	public static LoginProcess getInstance() {
		return instance;
	}
	
	public UserInfoBean login(String userName, String password) {
		String userPassword = (String)userPasswords.get(userName);
		
		if(userPassword == null) {
			return null;
		}
		if(!userPassword.equals(password)) {
			return null;
		}
		UserInfoBean userInfo = (UserInfoBean)userInfos.get(userName);
		return userInfo;
	}
}
