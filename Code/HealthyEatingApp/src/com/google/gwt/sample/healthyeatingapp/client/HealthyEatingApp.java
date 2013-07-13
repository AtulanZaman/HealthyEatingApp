package com.google.gwt.sample.healthyeatingapp.client;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.sample.healthyeatingapp.client.SocialMedia.SocialMedia;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HealthyEatingApp implements EntryPoint 
{

	//DB Connection tester code  *************************

	private final VerticalPanel vertPanel;
	private final DBConnectionServiceAsync rpc;
	private final Button dbConnection;
	private TextBox usernameBox;
	private TextBox passwordBox;
	private Label loginLabel;
	public SocialMedia SM;

	//****************************************************
	public HealthyEatingApp()
	{

		//DB Connection tester code  *************************
		dbConnection = new Button("Login");
		usernameBox = new TextBox();
		passwordBox = new TextBox();
		loginLabel = new Label("Please sign in to your account to access the Healthy Eating application.");
		vertPanel = new VerticalPanel();
	    rpc = (DBConnectionServiceAsync) GWT.create(DBConnectionService.class);
	 	ServiceDefTarget target = (ServiceDefTarget) rpc;
		String moduleRelativeURL = GWT.getModuleBaseURL() + "DBConnectionServiceImpl";
		target.setServiceEntryPoint(moduleRelativeURL); 

		//****************************************************
	}
	/**
	 * This is the entry point method.
	 */
/**
 * This is the code for the graph. Need to test this with the DB code before running.
 * */
/*	public void onModuleLoad() {
		Runnable onLoadCallBack = new Runnable(){
		public void run(){
				TabLayoutPanel homepage = new TabLayoutPanel(2.5, Unit.EM);
				
				homepage.add(new Graph().returnGraph(), "Graph");
				homepage.add(new HTML(""), "Log");
				homepage.add(new HTML(""), "Social");
				RootLayoutPanel.get().add(homepage);
				
				
			}
		}; 
     //Create a callback to be called when the visualization API
     //has been loaded.
		VisualizationUtils.loadVisualizationApi(onLoadCallBack, LineChart.PACKAGE);
}*/
	public void onModuleLoad() {
				//RootLayoutPanel.get().add(Window.alert("hello"));
			TabLayoutPanel homepage = new TabLayoutPanel(2.5, Unit.EM);
			
			homepage.add(new HTML(""), "Graph");
			homepage.add(new HTML(""), "Log");
			homepage.add(new SocialMedia().SocialMediaWebPageLoad(), "Social Media");
				
			RootLayoutPanel.get().add(homepage);
				
		 
     //Create a callback to be called when the visualization API
     //has been loaded.
		//VisualizationUtils.loadVisualizationApi(onLoadCallBack, LineChart.PACKAGE);
}

/*	@Override
	public void onModuleLoad() 
	{

		//DB Connection tester code  *************************
		vertPanel.add(loginLabel);
		vertPanel.add(usernameBox);
		vertPanel.add(passwordBox);
		vertPanel.add(dbConnection);

		RootPanel.get().add(vertPanel);

		// Listen for mouse events on the button.
		dbConnection.addClickHandler(new ClickHandler() {
	    @Override
		public void onClick(ClickEvent event) {
 	    	  rpc.authenticateUser(usernameBox.getText(),passwordBox.getText(), new DefaultCallback());			
	      }
	    });


		//****************************************************
	}


	private class DefaultCallback implements AsyncCallback {
		public void onFailure(Throwable caught) 
		{
			caught.printStackTrace();
	    	Window.alert("Failure: " + caught.getMessage());        
		}

		@Override
		public void onSuccess(Object result) {
			// TODO Auto-generated method stub
			Window.alert("SUCCESSFULLY CONNECTED TO DB!");  
		}
	}*/
  }