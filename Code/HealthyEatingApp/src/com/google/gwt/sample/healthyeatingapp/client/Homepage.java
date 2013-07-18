package com.google.gwt.sample.healthyeatingapp.client;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.healthyeatingapp.client.Graph;
import com.google.gwt.sample.healthyeatingapp.client.FoodLog.FoodLog;
import com.google.gwt.sample.healthyeatingapp.client.SocialMedia.SocialMedia;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class Homepage extends Composite  {
	
	//create our own custom widget for Navigation container
	private final TabPanel tp = new TabPanel();
	private final DBConnectionServiceAsync rpc;
	 	
	public Homepage()
	{
		initWidget(tp);
		// Add a home tab
		tp.add(new FoodLog(tp).onModuleLoad(), "Food Log");
	    tp.add(new SocialMedia().SocialMediaWebPageLoad(), "Social Media");	    
		this.rpc = (DBConnectionServiceAsync) GWT.create(DBConnectionService.class);
	 	ServiceDefTarget target = (ServiceDefTarget) rpc;
		String moduleRelativeURL = GWT.getModuleBaseURL() + "DBConnectionServiceImpl";
		target.setServiceEntryPoint(moduleRelativeURL);
	    
		Runnable onLoadCallBack = new Runnable(){
			@Override
			public void run(){
				String username = Cookies.getCookie("healthy_app_user");
				rpc.getUserCalories(username, new AsyncCallback<String>(){

					@Override
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						Window.alert("No log records for user:"+Cookies.getCookie("healthy_app_user"));
					}

					@Override
					public void onSuccess(String result) {
						// TODO Auto-generated method stub
						DataTable data = toDataTable(result);
						tp.add(new Graph().returnGraph(data), "Graph");
					}
				});
			}
		};
		
		VisualizationUtils.loadVisualizationApi(onLoadCallBack, CoreChart.PACKAGE);
	}
	
	public static native DataTable toDataTable(String json) /*-{
	  return new $wnd.google.visualization.DataTable(eval("(" + json + ")"));
	}-*/; 
}

 
