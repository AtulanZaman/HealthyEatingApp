package com.google.gwt.sample.healthyeatingapp.client;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.sample.healthyeatingapp.client.AuthenticationHandler;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HealthyEatingApp implements EntryPoint 
{

	//DB Connection tester code  *************************

	private final HorizontalPanel addPanel;
	private final DBConnectionAsync rpc;
	private final Button dbConnection;
	
	
	
	//****************************************************
	public HealthyEatingApp()
	{
	
		
		//DB Connection tester code  *************************

		addPanel = new HorizontalPanel();
		dbConnection = new Button("Test Connection");
	    rpc = (DBConnectionAsync) GWT.create(DBConnection.class);
	 	ServiceDefTarget target = (ServiceDefTarget) rpc;
		String moduleRelativeURL = GWT.getModuleBaseURL() + "MySQLConnection";
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
		
	@Override
	public void onModuleLoad() 
	{
		//Homepage homeContainer = new Homepage();
		//RootLayoutPanel.get().add(homeContainer);
		//DB Connection tester code  *************************

		 
	    addPanel.add(dbConnection);
		RootPanel.get("dbConnection").add(addPanel);
		 
		// Listen for mouse events on the button.
		dbConnection.addClickHandler(new ClickHandler() {
	    @Override
		public void onClick(ClickEvent event) {
	    	  //test();
	    	  System.out.print("Hi, reached button");
	    	  AsyncCallback<User> callback = new AuthenticationHandler<User>();
	    	  rpc.authenticateUser("rrazdan","rrazdan", callback);
				
	      }
	    });
		

		//****************************************************
	}
	
 
	public void test(){
		Window.alert("clicked");
		System.out.println("TRIAL");
	}
	


}
