package com.google.gwt.sample.healthyeatingapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

//Asynchronous interface specifies the same methods you have written into the synchronous interface, with two
//differences: The return type is always void and every method has an additional AsyncCallback parameter
public interface DBConnectionServiceAsync {
	public void authenticateUser(String username, String password, AsyncCallback<User> callback);
	public void authenticateFacebookUser(String firstName, String lastName, AsyncCallback<User> callback);
	public void logout(AsyncCallback callback);

	public void register(String newusername, String newpassword, String newfirstname, String newlastname, AsyncCallback callback);
	public void GetFriendsPoints(String username, AsyncCallback<Points> callback);
	public void getUserCalories(String username, AsyncCallback<String> callback);

	
	public void IsFriendUser(String firstName, String lastName, AsyncCallback<FriendUser> callback);
	public void GetFoodNames(AsyncCallback<String> callback);
	public void QueryFoodLog(String userName, String date, AsyncCallback<String> callback);
	public void getUserName(AsyncCallback<String> callback);
	public void InsertFoodLog(String userID, String foodName, String date, int calories, AsyncCallback callback);
}
