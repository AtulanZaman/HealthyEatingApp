package com.google.gwt.sample.healthyeatingapp.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabLayoutPanel;


public class Homepage extends Composite  {
	
	//create our own custom widget for Navigation container
	private TabLayoutPanel tp = new TabLayoutPanel(2.5, Unit.EM); 
    
	public Homepage(){
		initWidget(this.tp);
		// Add a home tab
		tp.add(new HTML("Graph"), "Graph");
	    tp.add(new HTML("Food Log"), "Food Log");
	    tp.add(new HTML("Social Media"), "Social Media");
	}
	

		
}