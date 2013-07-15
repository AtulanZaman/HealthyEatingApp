package com.google.gwt.sample.healthyeatingapp.client;

public class FacebookFriend{
	private String FirstName;
	private String LastName;
	
	public FacebookFriend(){
		
	}
	public String FirstName(){
		
		return FirstName;
	}
	
	public String LastName(){
		return LastName;
	}
	
	public void SetFirstName(String FirstName){
		this.FirstName = FirstName;
	}
	
	public void SetLastName(String LastName){
		this.LastName = LastName;
	}
}
