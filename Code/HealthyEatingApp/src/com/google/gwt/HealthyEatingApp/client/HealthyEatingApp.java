package com.google.gwt.HealthyEatingApp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HealthyEatingApp implements EntryPoint, ValueChangeHandler {

 VerticalPanel verticalPanel = new VerticalPanel();

 Label labelMealType=new Label();
 Label labelFoodName=new Label();
 Label labelLink=new Label();
 TextBox meal = new TextBox();
 TextBox foodName = new TextBox();
 

    
    public void onModuleLoad() {
        Hyperlink link1 = new Hyperlink("See Past Food Logs", "log");
        Hyperlink link2 = new Hyperlink("Enter New Food Log", "newEntry");
        Hyperlink link3 = new Hyperlink("Graphical Information", "graph");
        Hyperlink link4 = new Hyperlink("Leaderboard", "leaderboard");
        verticalPanel.add(link1);
        verticalPanel.add(link2);
        verticalPanel.add(link3);
        verticalPanel.add(link4);
        verticalPanel.add(labelLink);

        History.addValueChangeHandler(this);
        //when there is no token, the "home" token is set else changePage() is called.
        //this is useful if a user has bookmarked a site other than the homepage.
        if(History.getToken().isEmpty()){
            History.newItem("home");
        } else {
            changePage(History.getToken());
        }
        
        RootPanel.get().add(verticalPanel);

    }


public void onValueChange(ValueChangeEvent event) {
    changePage(History.getToken());
}
public void changePage(String token) {
    if(History.getToken().equals("log")) {
    	labelLink.setText("Here are this months food logs");
    	
    	//hide New Entry display
  	  	labelLink.setText("Checkout the leaderboard based on other Facebook friends using this application");
  	  	verticalPanel.remove(meal);
  	  	verticalPanel.remove(foodName);
  	  	verticalPanel.remove(labelFoodName);
  	  	verticalPanel.remove(labelMealType);

    } else if (History.getToken().equals("graph")) {
    	labelLink.setText("Here is a graph showing your stats");
    	
    	//hide New Entry display
  	  	labelLink.setText("Checkout the leaderboard based on other Facebook friends using this application");
  	  	verticalPanel.remove(meal);
  	  	verticalPanel.remove(foodName);
  	  	verticalPanel.remove(labelFoodName);
  	  	verticalPanel.remove(labelMealType);

    }
      else if (History.getToken().equals("newEntry")) {
    	  //New Entry display
    	  labelLink.setText("Add new entry to your food log");	
          verticalPanel.add(labelMealType);
    	  labelMealType.setText("Meal:");
    	  verticalPanel.add(meal);
          verticalPanel.add(labelFoodName);
    	  labelFoodName.setText("Food item:");
          verticalPanel.add(foodName);

      }
      else if (History.getToken().equals("leaderboard")) {
    	  
    	  //hide New Entry display
    	  labelLink.setText("Checkout the leaderboard based on other Facebook friends using this application");
    	  verticalPanel.remove(meal);
    	  verticalPanel.remove(foodName);
    	  verticalPanel.remove(labelFoodName);
    	  verticalPanel.remove(labelMealType);

    }
      else {
    	  labelLink.setText("This is the profile page that contains snippets of all relevant information available via this application");
    }
}
}
    
