package com.google.gwt.sample.healthyeatingapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HealthyEatingApp implements EntryPoint {
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
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
		
	}
}
