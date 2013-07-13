package com.google.gwt.sample.healthyeatingapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

//Asynchronous interface specifies the same methods you have written into the synchronous interface, with two
//differences: The return type is always void and every method has an additional AsyncCallback parameter
public interface DBConnectionServiceAsync {
	public void authenticateUser(String username, String password, AsyncCallback<User> callback);
	public void logout(AsyncCallback callback);
	//public void GetFriendsPoints(String username, AsyncCallback<Points> callback);	
	
}
