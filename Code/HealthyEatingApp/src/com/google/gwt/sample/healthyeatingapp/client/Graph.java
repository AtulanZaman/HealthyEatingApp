package com.google.gwt.sample.healthyeatingapp.client;
 
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;


public class Graph extends Composite{
	
	private LineChart chart;
	private final DBConnectionServiceAsync rpc;
	
	public Graph(){
		this.rpc = (DBConnectionServiceAsync) GWT.create(DBConnectionService.class);
	 	ServiceDefTarget target = (ServiceDefTarget) rpc;
		String moduleRelativeURL = GWT.getModuleBaseURL() + "DBConnectionServiceImpl";
		target.setServiceEntryPoint(moduleRelativeURL);
	}
	
	public LineChart returnGraph (DataTable data){
		this.chart = new LineChart(data, createOptions());
		return chart;
		//get stuff from database
	}	
	
	private Options createOptions() {
	    Options options = Options.create();
	    options.setWidth(400);
	    options.setHeight(240);
	    options.setTitle("My Daily Activities");
	    return options;
	}
}
