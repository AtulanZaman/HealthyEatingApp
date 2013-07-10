package com.google.gwt.sample.healthyeatingapp.client;

import com.google.gwt.user.client.rpc.RemoteService;


//In this interface specify all the methods you'd like to use to connect to the database
//I recommend to make one method for every specific update, insert, delete, whatever
public interface DBConnection extends RemoteService {

	public User authenticateUser(String userId, String pass);
	
}
