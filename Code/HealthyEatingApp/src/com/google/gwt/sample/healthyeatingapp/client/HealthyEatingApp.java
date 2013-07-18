package com.google.gwt.sample.healthyeatingapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;

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
		if(Window.Location.getHref().contains("http://127.0.0.1:8888")){
			Window.Location.replace("http://localhost:8888/HealthyEatingApp.html?gwt.codesvr=127.0.0.1:9997");
		}
	    if (sessionID == null){
	    	loginControl.loadLogin();
	    	 
	    }
	 
	    else{	    	
	    	loginControl.loadHomepage();
	    }
	    
	}
}
