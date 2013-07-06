package com.google.gwt.sample.healthyeatingapp.client;
import com.google.gwt.user.client.rpc.IsSerializable;

public class User implements IsSerializable {
	 private String username;
	 private String password;
	
	 //@SuppressWarnings("unused") Java annotation to make the Java compiler shut up about unused
	 //private method elements in the class
	 @SuppressWarnings("unused")
	 private User() {
	 //just here because GWT wants it.
	 }
	 public User(String username, String password) {
		 this.username = username;
		 this.password = password;
	 }
}
