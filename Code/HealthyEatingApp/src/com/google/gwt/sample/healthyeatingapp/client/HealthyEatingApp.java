package com.google.gwt.sample.healthyeatingapp.client;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Anchor;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HealthyEatingApp implements EntryPoint 
{
	private LoginInfo loginInfo = null;
    private VerticalPanel loginPanel = new VerticalPanel();
    private Label loginLabel = new Label("Please sign in to your Google Account to access the Healthy Eating application.");
    private Label welcomeLabel;
    private Anchor signInLink = new Anchor("Sign In");
    private Anchor signOutLink = new Anchor("Sign Out");
    private VerticalPanel mainOrganizer = new VerticalPanel();

	@Override
	public void onModuleLoad() 
	{
		// Check login status using login service.
	    LoginServiceAsync loginService = GWT.create(LoginService.class);
	    
	    loginService.login(GWT.getHostPageBaseURL(), new LoginCallback());
		
	}
	
	private class LoginCallback implements AsyncCallback {
		
		public void onFailure(Throwable caught) 
		{
			caught.printStackTrace();
	    	 
		}

		@Override
		public void onSuccess(Object result) {
			loginInfo = (LoginInfo)result;
	        if(loginInfo.isLoggedIn()) {
	          loadWelcomePage();
	        } else {
	          loadLogin();
	        }	
		}
			
	}	
	
	private void loadWelcomePage() {
		    // Set up sign out hyperlink.
		    signOutLink.setHref(loginInfo.getLogoutUrl());
		    welcomeLabel = new Label("Welcome " + this.loginInfo.getNickname());
		    Homepage menubar = new Homepage();
	    	mainOrganizer.add(signOutLink); 
	    	mainOrganizer.add(welcomeLabel);
	    	RootPanel.get().add(mainOrganizer);
	    	RootPanel.get().add(menubar);

	    }
	
	private void loadLogin() {
	    // Assemble login panel.
	    signInLink.setHref(loginInfo.getLoginUrl());
	    loginPanel.add(loginLabel);
	    loginPanel.add(signInLink);
	    RootPanel.get().add(loginPanel);
	  }

	
}
   
