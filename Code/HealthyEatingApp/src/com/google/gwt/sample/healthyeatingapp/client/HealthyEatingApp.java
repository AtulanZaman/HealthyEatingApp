package com.google.gwt.sample.healthyeatingapp.client;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
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
	private final Homepage homeContainer;
	
	//****************************************************
	public HealthyEatingApp()
	{
		//DB Connection tester code  ************************
		dbConnection = new Button("Test Connection");
		dbConnection.addClickHandler(new ClickHandler() {
		      @Override
			public void onClick(ClickEvent event) {
		    	  test();
		      }
		});
		
		addPanel = new HorizontalPanel();
	    addPanel.add(dbConnection);
		
	    rpc = (DBConnectionAsync) GWT.create(DBConnection.class);
	 	ServiceDefTarget target = (ServiceDefTarget) rpc;
		String moduleRelativeURL = GWT.getModuleBaseURL() + "MySQLConnection";
		target.setServiceEntryPoint(moduleRelativeURL); 
		homeContainer = new Homepage();
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
				RootLayoutPanel.get().add(homeContainer);
				RootPanel.get().add(addPanel);	
	}
	
 
	public void test(){
		Window.alert("clicked");
		System.out.println("TRIAL");
	}
}
