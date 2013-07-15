package com.google.gwt.sample.healthyeatingapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Cookies;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HealthyEatingApp implements EntryPoint 
{
	private LoginControl loginControl;

	public HealthyEatingApp()
	{
		loginControl = new LoginControl();

	}
	
	@Override
	public void onModuleLoad() 
	{
		String sessionID = Cookies.getCookie("sid");
	    if (sessionID == null){
	    	loginControl.loadLogin();
	    }
	 
	    else{
	    	
	    	loginControl.loadHomepage();
	    }
	    
	}
}
