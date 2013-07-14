package com.google.gwt.sample.healthyeatingapp.client;
import java.util.Date;

import com.google.gwt.sample.healthyeatingapp.client.SocialMedia.SocialMedia;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

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
