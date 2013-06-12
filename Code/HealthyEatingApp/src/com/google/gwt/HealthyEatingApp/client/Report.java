package com.google.gwt.HealthyEatingApp.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;





public class Report implements EntryPoint {


private VerticalPanel mainPanel = new VerticalPanel();
private FlexTable foodFlexTable = new FlexTable();
private HorizontalPanel addPanel = new HorizontalPanel();
private TextBox newSymbolTextBox = new TextBox();
private Button addfoodButton = new Button("Add");
private Label lastUpdatedLabel = new Label();
private ArrayList<String> stocks = new ArrayList<String>();

  
  
  /**
   * Entry point method.
   */
  public void onModuleLoad() {
	 
	  // Create table for stock data.
	  foodFlexTable.setText(0, 0, "Type of Meal");
	  foodFlexTable.setText(0, 1, "Food Item         ");
	 
	    // Assemble Add Stock panel.
	    addPanel.add(newSymbolTextBox);
	    addPanel.add(addfoodButton);

	    // Assemble Main panel.
	    mainPanel.add(foodFlexTable);
	    mainPanel.add(addPanel);
	    mainPanel.add(lastUpdatedLabel);

	    // Associate the Main panel with the HTML host page.
	    RootPanel.get("userEntry").add(mainPanel);
	    // Move cursor focus to the input box.
	    newSymbolTextBox.setFocus(true);
	    
	    // Listen for mouse events on the Add button.
	    addfoodButton.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	        addFood();
	      }
	    });
	    
	    // Listen for keyboard events in the input box.
	    newSymbolTextBox.addKeyPressHandler(new KeyPressHandler() {
	      public void onKeyPress(KeyPressEvent event) {
	        if (event.getCharCode() == KeyCodes.KEY_ENTER) {
	        	addFood();
	        }
	      }
	    });
	    


	  }

	 
	
	/**
	   * Add stock to FlexTable. Executed when the user clicks the addStockButton or
	   * presses enter in the newSymbolTextBox.
	   */
	  private void addFood() {
		  final String symbol = newSymbolTextBox.getText().toUpperCase().trim();
		    newSymbolTextBox.setFocus(true);

		    // Stock code must be between 1 and 10 chars that are numbers, letters, or dots.
		    if (!symbol.matches("^[0-9A-Z\\.]{1,10}$")) {
		      Window.alert("'" + symbol + "' is not a valid symbol.");
		      newSymbolTextBox.selectAll();
		      return;
		    }

		    newSymbolTextBox.setText("");

		    // Don't add the stock if it's already in the table.
		    if (stocks.contains(symbol))
		      return;
		    
		    // Add the stock to the table.
		    int row = foodFlexTable.getRowCount();
		    stocks.add(symbol);
		    foodFlexTable.setText(row, 0, symbol);

		    // Add a button to remove this stock from the table.
		    Button removeStockButton = new Button("x");
		    removeStockButton.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		        int removedIndex = stocks.indexOf(symbol);
		        stocks.remove(removedIndex);        
		        foodFlexTable.removeRow(removedIndex + 1);
		      }
		    });
		    foodFlexTable.setWidget(row, 3, removeStockButton);
		    
		    
	  }

  }

