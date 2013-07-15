package com.google.gwt.sample.healthyeatingapp.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoginControl {

		String sessionID;
		private final VerticalPanel loginOrganizerPanel;
		private final FlowPanel homePageOrganizerPanel;
		private final DBConnectionServiceAsync rpcLogin;
		private final Button loginButton;
		private final Button registerButton;
		private final Button logoutButton;
		private final Button addMeButton;
		private TextBox usernameBox;
		private PasswordTextBox passwordBox;
		private Label loginLabel;
		private User userLoginTrack;
		private TextBox newusernameBox;
		private TextBox newpasswordBox;
		private Label attentionLabel;
		
		public LoginControl(){
			loginButton = new Button("Login");
			logoutButton = new Button("Logout");
			registerButton = new Button("Register");
			addMeButton = new Button("Add me");
			usernameBox = new TextBox();
			passwordBox = new PasswordTextBox();
			loginLabel = new Label("Please sign in to your account to access the Healthy Eating application. " + "\n" +
									"Username and password are case sensitive.");
			attentionLabel = new Label("ATTENTION: you must enter a username and password!");

			newusernameBox = new TextBox();
			newpasswordBox = new TextBox();	
			loginOrganizerPanel = new VerticalPanel();
			homePageOrganizerPanel = new FlowPanel();
		    rpcLogin = (DBConnectionServiceAsync) GWT.create(DBConnectionService.class);
		 	ServiceDefTarget target = (ServiceDefTarget) rpcLogin;
			String moduleRelativeURL = GWT.getModuleBaseURL() + "DBConnectionServiceImpl";
			target.setServiceEntryPoint(moduleRelativeURL); 
		}
		
		
		//Login methods  ***************************************************************************
		
		public void loadLogin() {
			RootLayoutPanel.get().clear();
			loginOrganizerPanel.add(loginLabel);
			loginOrganizerPanel.add(usernameBox);
			loginOrganizerPanel.add(passwordBox);
			loginOrganizerPanel.add(loginButton);
			loginOrganizerPanel.add(registerButton);
			RootLayoutPanel.get().add(loginOrganizerPanel);

			// Listen for mouse events on the login button.
			loginButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				  rpcLogin.authenticateUser(usernameBox.getText(),passwordBox.getText(), new LoginButtonCallback());
			  }
			});
		 
		
			// Listen for mouse events on the register button.
			registerButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
					
				  System.out.println("in register event");
				  loginOrganizerPanel.add(newusernameBox);
				  loginOrganizerPanel.add(newpasswordBox);
				  loginOrganizerPanel.add(addMeButton);
			  }
			});
			
			// Listen for mouse events on the add me button.
			addMeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
					
				  System.out.println("in add me event");  
				  if(newusernameBox.getText().isEmpty() || newpasswordBox.getText().isEmpty()){
					  loginOrganizerPanel.add(attentionLabel); 
				  }
				  else{
					  rpcLogin.register(newusernameBox.getText(), newpasswordBox.getText(), new RegisterButtonCallback());			  
				  }
			  }
			});
						
		}
		public void loadHomepage() {
			 RootLayoutPanel.get().clear();
			 homePageOrganizerPanel.clear();
			 Homepage menubar = new Homepage();		 
			 homePageOrganizerPanel.add(logoutButton);
			 homePageOrganizerPanel.add(menubar);
		 	 RootLayoutPanel.get().add(homePageOrganizerPanel);
			 
			// Listen for mouse events on the button.
			logoutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
					System.out.println("in logout event");
				  rpcLogin.logout(new LogoutButtonCallback());			
			  }
			});
		}
		
		public void loadLoginAgain() {
		    System.out.println("in load login");
			loginLabel.setText("Username or password was incorrect. Please try again");
			 
		}
		


		//Callbacks methods  ***************************************************************************

		private class LogoutButtonCallback implements AsyncCallback {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("fail logout");
			}

			@Override
			public void onSuccess(Object result) {
				//System.out.print("clicked logout");
				loadLogin();
			}
			
		}
		
		private class LoginButtonCallback implements AsyncCallback {
			
			@Override
			public void onFailure(Throwable caught){
				System.out.println("fail login");
			}

			@Override
			public void onSuccess(Object result) {		 
				
				userLoginTrack = (User)result;
				
				if(userLoginTrack == null){
					loadLoginAgain();
				}
				
				else if (userLoginTrack.getLoggedIn()){
					 
					loadHomepage();
					 
					 //cookie stuff  *************************
					 String sessionID = userLoginTrack.getSessionId();
					 //set session cookie for 1 day expiry.
	                 final long DURATION = 1000 * 60 * 60 * 24 * 1;
	                 Date expires = new Date(System.currentTimeMillis() + DURATION);
	                 Cookies.setCookie("sid", sessionID, expires, null, "/", false);
					 //****************************************************
				}
				else{	
					loadLoginAgain();
				}
				 
				System.out.println("Session ID: " + sessionID);
			 
			}
		}		
		
		
		private class RegisterButtonCallback implements AsyncCallback {
			
			@Override
			public void onFailure(Throwable caught){
				System.out.println("fail register");
				loginOrganizerPanel.clear();
				loadLogin();	

			}

			@Override
			public void onSuccess(Object result) {
				User testIfNull = (User)result;
				if(testIfNull == null){
				   loginLabel.setText("Welcome new user!!");
				}
				else{
				   loginLabel.setText("Already a user!");				   
				}
				newusernameBox.setText("");
				newpasswordBox.setText("");
                loginOrganizerPanel.clear();
				loadLogin();	
			}
		}		
}
