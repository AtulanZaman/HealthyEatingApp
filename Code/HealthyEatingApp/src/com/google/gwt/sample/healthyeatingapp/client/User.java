package com.google.gwt.sample.healthyeatingapp.client;
import com.google.gwt.user.client.rpc.IsSerializable;

public class User implements IsSerializable {
	@SuppressWarnings("unused")
	private String username = null;	 
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
	 
}
