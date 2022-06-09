package com.test.models;

public class Login_RequestPOJO {

	private String username;
	private String password;
	
	public Login_RequestPOJO(String username, String password){
		
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
}
