package com.google.gwt.sample.healthyeatingapp.client;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
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
	private final DBConnectionAsync rpc;
	private final Button dbConnection;
	private final VerticalPanel vp;
	
	
	//****************************************************
	public HealthyEatingApp()
	{
		this.dbConnection= new Button("Test Connection");
		this.vp = new VerticalPanel();
		this.rpc = (DBConnectionAsync) GWT.create(DBConnection.class);
		
		//DB Connection tester code  *************************
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
 
