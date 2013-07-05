package com.google.gwt.sample.healthyeatingapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HealthyEatingApp implements EntryPoint {

	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		 
		TabLayoutPanel homepage = new TabLayoutPanel(2.5, Unit.EM);
		homepage.add(new HTML(""), "Graph");
		homepage.add(new HTML(""), "Log");
		homepage.add(new HTML(""), "Social");
		RootLayoutPanel.get().add(homepage);
	}
}
