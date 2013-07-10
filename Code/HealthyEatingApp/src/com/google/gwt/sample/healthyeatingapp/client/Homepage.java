package com.google.gwt.sample.healthyeatingapp.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.sample.healthyeatingapp.client.Graph;;

public class Homepage extends Composite  {
	
	//create our own custom widget for Navigation container
	private final TabLayoutPanel tp = new TabLayoutPanel(2.5, Unit.EM);
	private final Button dbConnection;

	 	
	public Homepage()
	{
		initWidget(this.tp);
		// Add a home tab
	    tp.add(new HTML("Food Log"), "Food Log");
	    tp.add(new HTML("Social Media"), "Social Media");
	    
	    this.dbConnection = new Button("Test Connection");
		dbConnection.addClickHandler(new ClickHandler() {
		      @Override
			public void onClick(ClickEvent event) {
		    	  test();
		      }
		});
		
		Runnable onLoadCallBack = new Runnable(){
			public void run(){
				tp.add(new Graph().returnGraph(), "Graph");
			}
		};
		
		VisualizationUtils.loadVisualizationApi(onLoadCallBack, LineChart.PACKAGE);
	}
	
	public void test(){
		Window.alert("clicked");
		System.out.println("TRIAL");
	}
}

 
