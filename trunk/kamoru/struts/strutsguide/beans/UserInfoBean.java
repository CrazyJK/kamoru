package strutsguide.beans;

public class UserInfoBean {

	private String userName = null;
	private String phone = null;
	private String email = null;
	
	public UserInfoBean(String userName, String phone, String email) {
		this.userName = userName;
		this.phone = phone;
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
