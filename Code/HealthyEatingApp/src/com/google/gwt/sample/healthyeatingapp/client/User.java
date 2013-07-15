package com.google.gwt.sample.healthyeatingapp.client;
import com.google.gwt.user.client.rpc.IsSerializable;

public class User implements IsSerializable {

	private String username = null;	 
	private String password = null;
	private String httpSession;
	private boolean loggedIn = false;
	
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
	 public void setSessionId(String sessionID){
            this.httpSession = sessionID;
	 }
	 public boolean getLoggedIn(){
		 return loggedIn;
	 }
	 public void setLoggedIn(boolean loggedIn){
		 this.loggedIn = loggedIn;
	 }
}
