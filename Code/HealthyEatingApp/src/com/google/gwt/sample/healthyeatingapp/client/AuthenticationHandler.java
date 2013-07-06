package com.google.gwt.sample.healthyeatingapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class AuthenticationHandler<T> implements AsyncCallback<User> 
{
	public void onFailure(Throwable ex) 
	{
		RootPanel.get().add(new HTML("RPC call failed. :-("));
	}
	public void onSuccess(User result) 
	{
		RootPanel.get().add(new HTML("RPC call success. :-)"));

		//do stuff on success with GUI, like load the next GUI element
	}
}