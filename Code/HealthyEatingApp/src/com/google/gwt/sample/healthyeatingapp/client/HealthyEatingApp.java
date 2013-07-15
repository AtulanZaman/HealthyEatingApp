package com.google.gwt.sample.healthyeatingapp.client;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.api.gwt.oauth2.client.Auth;
import com.google.api.gwt.oauth2.client.AuthRequest;
import com.google.api.gwt.oauth2.client.Callback;
import com.google.gwt.sample.healthyeatingapp.client.SocialMedia.SocialMedia;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.URL;
import com.google.gwt.http.*;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HealthyEatingApp implements EntryPoint 
{
	//login code  *************************
	String sessionID;
	private final VerticalPanel loginOrganizerPanel;
	private final StackLayoutPanel homePageOrganizerPanel;
	private final DBConnectionServiceAsync rpcLogin;
	private final Button loginButton;
	private final Button logoutButton;
	private TextBox usernameBox;
	private PasswordTextBox passwordBox;
	private Label loginLabel;
	private FacebookFriends FBFriends;
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
		homePageOrganizerPanel = new StackLayoutPanel(Unit.EM);
	    	rpcLogin = (DBConnectionServiceAsync) GWT.create(DBConnectionService.class);
	   
	 	ServiceDefTarget target = (ServiceDefTarget) rpcLogin;
		String moduleRelativeURL = GWT.getModuleBaseURL() + "DBConnectionServiceImpl";
		target.setServiceEntryPoint(moduleRelativeURL); 
		FBFriends = new FacebookFriends();
		//****************************************************
	}
	private void addFacebookAuth(VerticalPanel lp) {
	 Button button = new Button("Authenticate with Facebook");
	    button.addClickHandler(new ClickHandler() {
	      @Override
	      public void onClick(ClickEvent event) {
	        
	        	  FacebookUtil.getInstance().doGraph("me", new Callback<JSONObject, Throwable>(){

					@Override
					public void onFailure(Throwable reason) {
						// TODO Auto-generated method stub
						System.out.println(reason.getMessage());
					}

					@Override
					public void onSuccess(JSONObject result) {		
						System.out.println("FB Logged In");
						String Name = result.get("name").toString().replaceAll("\"", "");
						String [] NameSegments = Name.split(" ");
						String firstName = NameSegments[0];
						String lastName = NameSegments[NameSegments.length - 1];
						System.out.println(firstName + " " + lastName);
						rpcLogin.authenticateFacebookUser(firstName, lastName, new FBLoginCallback());
						//System.out.println(result.toString());
						//System.out.println(Name);
						//loadHomepage();
					}
	        		  
	        	  });	        	  
	        	  //loadHomepage();	            
	          }	    
	    	});
	    lp.add(button);
	}
	private class FBLoginCallback implements AsyncCallback{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Object result) {
			User user = (User) result;
			System.out.println(user.getUserName() + " " + user.getPassword());
			rpcLogin.authenticateUser(user.getUserName(), user.getPassword(), new LoginButtonCallback());			
		}
		
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
	loginOrganizerPanel.clear();
	RootLayoutPanel.get().clear();
	loginOrganizerPanel.add(loginLabel);
	loginOrganizerPanel.add(usernameBox);
	loginOrganizerPanel.add(passwordBox);
	loginOrganizerPanel.add(loginButton);
	addFacebookAuth(loginOrganizerPanel);
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
		 //System.out.println(FBFriends.Friends.get(0).FirstName());
		 Homepage menubar = new Homepage();
		 
		 //homePageOrganizerPanel.add(logoutButton);
		 homePageOrganizerPanel.add(menubar, new HTML("Menu"), 2);
		 homePageOrganizerPanel.add(logoutButton, new HTML("Logout"), 4);
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
			System.out.print("clicked logout\n");
			String token = FacebookUtil.getInstance().getToken();
			FacebookUtil.getInstance().resetToken();
			//Auth.get().clearAllTokens();	
			//Window.Location.replace("https://www.facebook.com/logout.php?access_token=" + FacebookUtil.getInstance().getToken() + "&confirm=1&next=http://www.google.ca");
			//Window.Location.replace("https://www.facebook.com/logout.php?access_token=" + token + "&confirm=1&next=http://127.0.0.1:8888/healthyeatingapp/oauthWindow.html");
			//Window.Location.replace("http://127.0.0.1:8888/HealthyEatingApp.html?gwt.codesvr=127.0.0.1:9997");	
			//FacebookUtil.getInstance().resetToken();
			Auth.get().clearAllTokens();	
			loadLogin();
			
		}
		
	}
	
	public void loadLoginAgain() {
	    // Assemble login panel.
		loginLabel.setText("Username or password was incorrect. Please try again");
		 
	}
}
