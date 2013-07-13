package com.google.gwt.sample.healthyeatingapp.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Points implements IsSerializable {
	@SuppressWarnings("unused")
	private String username;
	private String firstName;
	private String lastName;
	private int userPoints;
	
	
	@SuppressWarnings("unused")
	 private Points() {
	 //just here because GWT wants it.
	 }
	
	 public Points(String username, String firstName, String lastName, int userPoints) {
		  this.username = username; 
		  this.firstName = firstName;
		  this.lastName = lastName;
		  this.userPoints = userPoints;
	 }
	 
	 
	 public String UserName() {
		    return username;
		  }
	 
	 public String FirstName() {
		    return firstName;
		  }
	 
	 public String LastName() {
		    return lastName;
		  }	 
	 	 
	 public int Points() {
		    return userPoints;
		  }
	 
	 public void setFirstName(String fn){
			this.firstName = fn;
		}
		
		public void setLastName(String ln){
			this.lastName = ln;
		}
		
		public void setPoints(Integer pts){
			this.userPoints = pts;
		}
}
