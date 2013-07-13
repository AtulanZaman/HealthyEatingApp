package com.google.gwt.sample.healthyeatingapp.client;
import com.google.gwt.user.client.rpc.IsSerializable;

public class User implements IsSerializable {
	@SuppressWarnings("unused")
	private String username = null;
	private boolean loggedIn = false;
	private String loginUrl;
	private String logoutUrl;
	private String password = null;
	
	
	@SuppressWarnings("unused")
	 private User() {
	 //just here because GWT wants it.
	 }
	
	 public User(String username, String password) {

		 this.username = username;
		 this.password = password; 
		 
	 }
	 
	 public String getPassword() {
		    return password;
		  }
	 public String getUserName() {
		    return username;
		  }
	 public boolean isLoggedIn() {
		    return loggedIn;
		  }
	 
	 public void setLoggedIn(boolean loggedIn) {
		    this.loggedIn = loggedIn;
		  }

	 public String getLoginUrl() {
		    return loginUrl;
		  }
	 
	 public void setLoginUrl(String loginUrl) {
		    this.loginUrl = loginUrl;
		  }
	 
	 public String getLogoutUrl() {
		    return logoutUrl;
		  }

	 public void setLogoutUrl(String logoutUrl) {
		    this.logoutUrl = logoutUrl;
		  }
	 
}
