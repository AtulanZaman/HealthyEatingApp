package com.google.gwt.sample.healthyeatingapp.client;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("DBConnectionService")
public interface DBConnectionService extends RemoteService {

	public User authenticateUser(String username, String password);
	public User authenticateFacebookUser(String firstName, String lastName);
	public void logout();		
	public Points GetFriendsPoints(String username);	
	public FriendUser IsFriendUser(String firstName, String lastName);
	
	
}
