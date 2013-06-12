package com.google.gwt.HealthyEatingApp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HealthyEatingApp implements EntryPoint {

  TabPanel tabPanel;
  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    tabPanel = new TabPanel();

    tabPanel.add(new HTML("<h1>Page 0 Content: </h1>"), " Page 0 ");
    tabPanel.add(new HTML("<h1>Page 1 Content: </h1>"), " Page 1 ");
    tabPanel.add(new HTML("<h1>Page 2 Content: </h1>"), " Page 2 ");

    tabPanel.addSelectionHandler(new SelectionHandler<Integer>(){
      public void onSelection(SelectionEvent<Integer> event) {
        History.newItem("page" + event.getSelectedItem());
      }});

    History.addValueChangeHandler(new ValueChangeHandler<String>() {
      public void onValueChange(ValueChangeEvent<String> event) {
        String historyToken = event.getValue();

        // Parse the history token
        try {
          if (historyToken.substring(0, 4).equals("page")) {
            String tabIndexToken = historyToken.substring(4, 5);
            int tabIndex = Integer.parseInt(tabIndexToken);
            // Select the specified tab panel
            tabPanel.selectTab(tabIndex);
          } else {
            tabPanel.selectTab(0);
          }

        } catch (IndexOutOfBoundsException e) {
          tabPanel.selectTab(0);
        }
      }
    });

    tabPanel.selectTab(0);
    RootPanel.get().add(tabPanel);
  }
}