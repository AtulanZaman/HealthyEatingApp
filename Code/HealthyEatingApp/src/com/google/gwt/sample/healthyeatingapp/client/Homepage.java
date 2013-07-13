package com.google.gwt.sample.healthyeatingapp.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.google.gwt.sample.healthyeatingapp.client.Graph;
import com.google.gwt.sample.healthyeatingapp.client.SocialMedia.SocialMedia;;

public class Homepage extends Composite  {
	
	//create our own custom widget for Navigation container
	private final TabPanel tp = new TabPanel();
	 	
	public Homepage()
	{
		initWidget(tp);
		// Add a home tab
	    tp.add(new HTML("Food Log"), "Food Log");
	    tp.add(new SocialMedia().SocialMediaWebPageLoad(), "Social Media");	
	    
		Runnable onLoadCallBack = new Runnable(){
			@Override
			public void run(){
				tp.add(new Graph().returnGraph(), "Graph");
			}
		};
		
		VisualizationUtils.loadVisualizationApi(onLoadCallBack, CoreChart.PACKAGE);
	}
}

 
