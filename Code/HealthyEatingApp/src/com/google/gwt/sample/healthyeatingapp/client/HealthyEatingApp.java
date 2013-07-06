package com.google.gwt.sample.healthyeatingapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HealthyEatingApp implements EntryPoint {

	
	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		 
		Homepage homeContainer = new Homepage();
		RootLayoutPanel.get().add(homeContainer);
	}
}
