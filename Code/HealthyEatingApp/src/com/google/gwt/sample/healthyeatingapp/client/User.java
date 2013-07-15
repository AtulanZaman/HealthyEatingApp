package com.google.gwt.sample.healthyeatingapp.client;
import com.google.gwt.user.client.rpc.IsSerializable;

public class User implements IsSerializable {

	private String username = null;	 
	private String password = null;
	private String httpSession;
	
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
	 public String getSessionId(){
            return httpSession;
     }
	 public String setSessionId(String sessionID){
            return httpSession = sessionID;
	 }
}
