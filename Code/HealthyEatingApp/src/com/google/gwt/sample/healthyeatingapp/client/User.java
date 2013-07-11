package com.google.gwt.sample.healthyeatingapp.client;
import com.google.gwt.user.client.rpc.IsSerializable;

public class User implements IsSerializable {
	@SuppressWarnings("unused")
	private String username;
	private String password;

	@SuppressWarnings("unused")
	 private User() {
	 //just here because GWT wants it.
	 }
	 public User(String username, String password) {
		 this.password = password;
		 this.username = username; 
	 }
}
