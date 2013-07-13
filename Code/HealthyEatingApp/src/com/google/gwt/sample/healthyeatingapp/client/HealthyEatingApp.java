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
	//login code  *************************
	String sessionID;
	private final VerticalPanel loginOrganizerPanel;
	private final FlowPanel homePageOrganizerPanel;
	private final DBConnectionServiceAsync rpcLogin;
	private final Button loginButton;
	private final Button logoutButton;
	private TextBox usernameBox;
	private PasswordTextBox passwordBox;
	private Label loginLabel;
	//****************************************************

	//social media code  *************************
	public SocialMedia SM;
	
	public HealthyEatingApp()
	{
		//login code  *************************
		loginButton = new Button("Login");
		logoutButton = new Button("Logout");
		usernameBox = new TextBox();
		passwordBox = new PasswordTextBox();
		loginLabel = new Label("Please sign in to your account to access the Healthy Eating application. Username and password are case sensitive.");
		loginOrganizerPanel = new VerticalPanel();
		homePageOrganizerPanel = new FlowPanel();
	    	rpcLogin = (DBConnectionServiceAsync) GWT.create(DBConnectionService.class);
	 	ServiceDefTarget target = (ServiceDefTarget) rpcLogin;
		String moduleRelativeURL = GWT.getModuleBaseURL() + "DBConnectionServiceImpl";
		target.setServiceEntryPoint(moduleRelativeURL); 
		//****************************************************
	}
	
	@Override
	public void onModuleLoad() 
	{
		String sessionID = Cookies.getCookie("sid");
	    if (sessionID == null){
	    	loadLogin();
	    }
	 
	    else{
	    	
	    	loadHomepage();
	    }
	    
	}

	public void loadLogin() {
	//Login code  *************************
	RootLayoutPanel.get().clear();
	loginOrganizerPanel.add(loginLabel);
	loginOrganizerPanel.add(usernameBox);
	loginOrganizerPanel.add(passwordBox);
	loginOrganizerPanel.add(loginButton);
	RootLayoutPanel.get().add(loginOrganizerPanel);

	// Listen for mouse events on the button.
	loginButton.addClickHandler(new ClickHandler() {
	@Override
	public void onClick(ClickEvent event) {
		  rpcLogin.authenticateUser(usernameBox.getText(),passwordBox.getText(), new LoginButtonCallback());			
	  }
	});
	//****************************************************
	}


	private class LoginButtonCallback implements AsyncCallback {
		
		@Override
		public void onFailure(Throwable caught){
			caught.printStackTrace();

	    	//Window.alert("Failure: " + caught.getMessage());        
		}

		@Override
		public void onSuccess(Object result) {		 
			if(result == null){
				loadLoginAgain();
			}
			else{
				 User userInfo = (User) result;	
				 //cookie stuff  *************************
				 String sessionID = userInfo.getSessionId();
				 //set session cookie for 1 day expiry.
                 final long DURATION = 1000 * 60 * 60 * 24 * 1;
                 Date expires = new Date(System.currentTimeMillis() + DURATION);
                 Cookies.setCookie("sid", sessionID, expires, null, "/", false);
				 //****************************************************
				
				 loadHomepage();
			}
			 
			System.out.print(sessionID);
		 
		}

	}
	
	public void loadHomepage() {
		 RootLayoutPanel.get().clear();
		 homePageOrganizerPanel.clear();
		 Homepage menubar = new Homepage();		 
		 homePageOrganizerPanel.add(logoutButton);
		 homePageOrganizerPanel.add(menubar);
	 	 RootLayoutPanel.get().add(homePageOrganizerPanel);
		 
		 
		 System.out.println("in home");
		// Listen for mouse events on the button.
		logoutButton.addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
				System.out.println("in logout event");
			  rpcLogin.logout(new LogoutButtonCallback());			
		  }
		});
	}
	
	private class LogoutButtonCallback implements AsyncCallback {

		@Override
		public void onFailure(Throwable caught) {
			System.out.print("fail logout");
		}

		@Override
		public void onSuccess(Object result) {
			System.out.print("clicked logout");
			loadLogin();
		}
		
	}
	
	public void loadLoginAgain() {
	    // Assemble login panel.
		loginLabel.setText("Username or password was incorrect. Please try again");
		 
	}
}
