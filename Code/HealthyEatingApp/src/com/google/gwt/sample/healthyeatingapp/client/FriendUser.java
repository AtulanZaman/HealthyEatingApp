package com.google.gwt.sample.healthyeatingapp.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class FriendUser implements IsSerializable {

	private String userName;
	private boolean IsUser;
	
		
	public FriendUser(){
		
	}
	
	public String userName(){
		return userName;
	}
	
	public boolean IsUser(){
		return IsUser;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	public void setIsUser(boolean IsUser){
		this.IsUser = IsUser;
	}
}
