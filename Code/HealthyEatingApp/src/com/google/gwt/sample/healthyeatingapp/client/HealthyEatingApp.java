package com.google.gwt.sample.healthyeatingapp.client;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.sample.healthyeatingapp.client.AuthenticationHandler;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HealthyEatingApp implements EntryPoint 
{
	//DB Connection tester code  *************************
	private DBConnectionAsync rpc;
	private Button dbConnection = new Button("Test Connection");
	private VerticalPanel vp = new VerticalPanel();
	
	
	//****************************************************
	public HealthyEatingApp()
	{
		//DB Connection tester code  *************************
	    rpc = (DBConnectionAsync) GWT.create(DBConnection.class);
	 	ServiceDefTarget target = (ServiceDefTarget) rpc;
		String moduleRelativeURL = GWT.getModuleBaseURL() + "MySQLConnection";
		target.setServiceEntryPoint(moduleRelativeURL); 
		//****************************************************
	}
	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() 
	{
	    
		Homepage homeContainer = new Homepage();
		RootLayoutPanel.get().add(homeContainer);
		
		//DB Connection tester code  *************************
		 vp.add(dbConnection);
		 RootPanel.get("dbConnection").add(vp);
		//****************************************************
		 
	}
	
	//button ClickListener
	public void onClick(Widget sender) 
	{
		 if (sender.equals(dbConnection)) 
		 {
				vp.add(dbConnection);
			AsyncCallback<User> callback = new AuthenticationHandler<User>();
			rpc.authenticateUser("rrazdan", callback);
		 }
	}

}
 