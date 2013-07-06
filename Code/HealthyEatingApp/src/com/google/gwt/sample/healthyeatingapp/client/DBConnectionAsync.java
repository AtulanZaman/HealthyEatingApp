package com.google.gwt.sample.healthyeatingapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

//Asynchronous interface specifies the same methods you have written into the synchronous interface, with two
//differences: The return type is always void and every method has an additional AsyncCallback parameter
public interface DBConnectionAsync {
	public void authenticateUser(String user, String pass, AsyncCallback<User>
	callback);
}
