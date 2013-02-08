package com.springinaction.spitter.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Spitter {
	
	@Id
	private long id;
	
	@Size(min=3, max=20, message="Username must be between 3 and 20 characters long.")
	@Pattern(regexp="^[a-zA-Z0-9]+$", message="Username must be alphanumeric with no spaces")
	private String username;
	
	@Size(min=6, max=20, message="Password must be at 6 characters long.")
	private String password;

	@Size(min=3, max=50, message="Your full name must be between 3 and 50 characters long.")
	private String fullName;
	
	@Pattern(regexp="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2-4}", message="Invalid email address.")
	private String email;
	private boolean updateByEmail;
	
	public String getEmail() {
		return email;
	}
	public String getFullName() {
		return fullName;
	}
	public long getId() {
		return id;
	}
	public String getPassword() {
		return password;
	}
	public String getUsername() {
		return username;
	}
	public boolean isUpdateByEmail() {
		return updateByEmail;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUpdateByEmail(boolean updateByEmail) {
		this.updateByEmail = updateByEmail;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
