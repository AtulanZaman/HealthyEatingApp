package com.google.gwt.HealthyEatingApp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HealthyEatingApp implements EntryPoint, ValueChangeHandler {

 VerticalPanel panel = new VerticalPanel();
    Label label=new Label();
    public void onModuleLoad() {
        Hyperlink link1 = new Hyperlink("Food Logs", "log");
        Hyperlink link2 = new Hyperlink("Graphical Information", "graph");
        panel.add(link1);
        panel.add(link2);
        panel.add(label);
        RootPanel.get().add(panel);
        History.addValueChangeHandler(this);
        //when there is no token, the "home" token is set else changePage() is called.
        //this is useful if a user has bookmarked a site other than the homepage.
        if(History.getToken().isEmpty()){
            History.newItem("home");
        } else {
            changePage(History.getToken());
        }
    }


public void onValueChange(ValueChangeEvent event) {
    changePage(History.getToken());
}
public void changePage(String token) {
    if(History.getToken().equals("log")) {
        label.setText("Here are this months food logs");
    } else if (History.getToken().equals("graph")) {
        label.setText("Here is a graph showing your stats");
    } else {
        label.setText("This is the profile page that contains snippets of all relevant information available via this application");
    }
}
}
    
