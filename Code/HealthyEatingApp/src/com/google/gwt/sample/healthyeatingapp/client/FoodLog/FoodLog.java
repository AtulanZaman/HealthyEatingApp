package com.google.gwt.sample.healthyeatingapp.client.FoodLog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.sample.healthyeatingapp.client.DBConnectionService;
import com.google.gwt.sample.healthyeatingapp.client.DBConnectionServiceAsync;
import com.google.gwt.sample.healthyeatingapp.client.FoodLogData;
import com.google.gwt.sample.healthyeatingapp.client.FoodLogItems;
import com.google.gwt.sample.healthyeatingapp.client.Graph;
import com.google.gwt.sample.healthyeatingapp.client.HealthyEatingApp;
import com.google.gwt.sample.healthyeatingapp.client.Points;
import com.google.gwt.sample.healthyeatingapp.server.DBConnectionServiceImpl;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;

public class FoodLog extends Composite implements HasWidgets {

	private final DBConnectionServiceAsync rpc;
	private TabPanel tp;
	ListBox lbFG;
	ListBox lbFN;
	Label SuccessLabel;
	FlowPanel fp;
	Button Btn;
	FlexTable ft;
	String foodLogItems;
	String[] array;
	ArrayList<FoodLogItems> ret = null;
	final InlineLabel caloriesText = new InlineLabel();
	String selectedDate;
	String userNameTyped = "";

	Button submit, cancel;

	InlineLabel dateLabel = new InlineLabel();
	Date d = new Date();
	DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
	String dateString = dfm.format(d);
	ArrayList<FoodLogData> foodEntries = new ArrayList<FoodLogData>();
	String prevFoodData;
	
	public FoodLog(TabPanel tp) {
		// initWidget(this.fp);
		this.tp = tp;
		rpc = (DBConnectionServiceAsync) GWT.create(DBConnectionService.class);
		ServiceDefTarget target = (ServiceDefTarget) rpc;
		String moduleRelativeURL = GWT.getModuleBaseURL()
				+ "DBConnectionServiceImpl";
		target.setServiceEntryPoint(moduleRelativeURL);
		SuccessLabel = new Label("New items successfully entered");
		
		
		// fp.add(new LeaderboardWidget(this));
		// SocialMediaWebPageLoad(HTML);
	}

	private void rpcCall() {

		rpc.GetFoodNames(new DefaultCallback());
	}

	private class DefaultCallback implements AsyncCallback<String> {
		public void onFailure(Throwable caught) {
			caught.printStackTrace();
			Window.alert("Failure: " + caught.getMessage());
		}

		// @Override
		public void onSuccess(String result) {
			// TODO Auto-generated method stub
			foodLogItems = result;
			// Window.alert("SUCCESSFULLY CONNECTED TO DB!");
			// Window.alert(foodLogItems);

		}
	}

	private void rpcUserNameCall()
	{
		rpc.getUserName(new DefaultUserNameCallback());
	
	}
	
	private class DefaultUserNameCallback implements AsyncCallback<String>
	{

		@Override
		public void onFailure(Throwable caught) {
			caught.printStackTrace();
			Window.alert("Failure: " + caught.getMessage());
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(String result) {
			// TODO Auto-generated method stub
			
			
			userNameTyped = result;
			//Window.alert(result);
			
		}}
	
	private void insertRpcCall(String userName, String foodName, String date, int calories) {
		rpc.InsertFoodLog(userName, foodName, date, calories, new DefaultInsertCallback());
	}

	private class DefaultInsertCallback implements AsyncCallback {
		public void onFailure(Throwable caught) {
			caught.printStackTrace();
			Window.alert("Failure: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Object result) {
			//Window.alert("Inserted");
			//System.out.println("Inserted");
			
			Runnable onLoadCallBack = new Runnable(){
				@Override
				public void run(){
					String username = Cookies.getCookie("healthy_app_user");
					System.out.println("Graph update called");
					rpc.getUserCalories(username, new AsyncCallback<String>(){

						@Override
						public void onFailure(Throwable caught) {
							// Show the RPC error message to the user
							Window.alert("No log records for user:"+Cookies.getCookie("healthy_app_user"));
						}

						@Override
						public void onSuccess(String result) {
							// TODO Auto-generated method stub
							if(tp.getWidget(tp.getWidgetCount()-1).getClass().toString().contains("LineChart")){
								tp.remove(tp.getWidgetCount()-1);
							}
							DataTable data = toDataTable(result);
							LineChart newGraph = new Graph().returnGraph(data);
							tp.add(newGraph, "Graph");
						}
					});
				}
			};
			VisualizationUtils.loadVisualizationApi(onLoadCallBack, CoreChart.PACKAGE);
			// TODO Auto-generated method stub

		}
	}

	private void queryRpcCall(String userName, String date) {
		rpc.QueryFoodLog(userName, date, new DefaultQueryCallback());
	}

	private class DefaultQueryCallback implements AsyncCallback<String> {
		public void onFailure(Throwable caught) {
			caught.printStackTrace();
			Window.alert("Failure: " + caught.getMessage());
		}

		@Override
		public void onSuccess(String result) {
			// Window.alert("Queried");
			prevFoodData = result;
			// TODO Auto-generated method stub

		}
	}

	public static native DataTable toDataTable(String json) /*-{
	  return new $wnd.google.visualization.DataTable(eval("(" + json + ")"));
	}-*/; 

	public FlowPanel onModuleLoad() {

		rpcUserNameCall();
		rpcCall();

		// Create a Flex Table
		final FlexTable flexTable = new FlexTable();
		FlexCellFormatter cellFormatter = flexTable.getFlexCellFormatter();
		flexTable.addStyleName("flexTable");
		flexTable.setCellSpacing(2);
		flexTable.setCellPadding(2);

		lbFG = new ListBox();
		lbFN = new ListBox();
		submit = new Button("Submit");
		cancel = new Button("Pick a New Date");

		fp = new FlowPanel();
		// Add some text
		cellFormatter.setHorizontalAlignment(0, 1,
				HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.setHTML(0, 0,
				"This table allows you to log your food entries:");
		cellFormatter.setColSpan(0, 0, 2);

		lbFG.setVisibleItemCount(1);

		lbFN.setVisible(false);
		lbFG.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				fp.remove(SuccessLabel);
				int selectedIndex = lbFG.getSelectedIndex();
				int numRows = 0;

				if (selectedIndex > 0) {

					numRows = flexTable.getRowCount();
					flexTable.setWidget(numRows - 1, 0, lbFG);
					lbFN.clear();
					caloriesText.setText("", Direction.DEFAULT);

					String test = lbFG.getValue(lbFG.getSelectedIndex());
					String[] str = getFoodNames(ret, test);
					lbFN.addItem("");
					for (String s : str) {
						lbFN.addItem(s);
					}
					lbFN.setSelectedIndex(0);
					lbFN.setVisible(true);

					flexTable.setWidget(numRows - 1, 1, lbFN);
				}

			}
		});

		lbFN.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				int selectedIndex = lbFN.getSelectedIndex();
				int numRows = 0;
				if (selectedIndex > 0)

					numRows = flexTable.getRowCount();
				flexTable.setWidget(numRows - 1, 1, lbFN);

				String test = lbFN.getValue(lbFN.getSelectedIndex());
				String str = "" + getCalories(ret, test);
				caloriesText.setText(str, Direction.DEFAULT);
				flexTable.setWidget(numRows - 1, 2, caloriesText);

			}
		});

		// Add a button that will add more rows to the table
		final Button addRowButton = new Button("Add a Row");
		Button btn2 = new Button("Update");
		addRowButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				fp.remove(SuccessLabel);
				addRow(flexTable);
			}
		});

		addRowButton.addStyleName("fixedWidthButton");

		VerticalPanel buttonPanel = new VerticalPanel();
		buttonPanel.setStyleName("flexTable-buttonPanel");
		buttonPanel.add(addRowButton);
		flexTable.setWidget(0, 1, buttonPanel);
		cellFormatter
				.setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);

		// Add two rows to start
		// addRow(flexTable);

		final DatePicker datePicker = new DatePicker();

		// Set the value in the text box when the user selects a date
		datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {

			public void onValueChange(ValueChangeEvent<Date> event) {
				
				Date date = event.getValue();
				DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
				dateString = dfm.format(date);
				dateLabel.setText(dateString, Direction.DEFAULT);
				selectedDate = dateLabel.getText();
				queryRpcCall(userNameTyped, selectedDate);

			}
		});
		final Button Btn2 = new Button();
		ret = new ArrayList<FoodLogItems>();
		Btn2.setSize("100px", "30px");
		Btn2.setText("View/Update");
		Btn2.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Btn2.setVisible(false);
				datePicker.setVisible(false);

				selectedDate = dateLabel.getText();
				String[] dbRows;
				String[] dbElements;

				// Window.alert(prevFoodData);
				if (!(prevFoodData.equals(""))) {

					dbRows = prevFoodData.split(";");
					for (String s : dbRows)

					{
						dbElements = s.split(":");
						FoodLogData e = new FoodLogData(userNameTyped,
								dbElements[2], Integer.parseInt(dbElements[1]),
								dbElements[0], selectedDate);
						foodEntries.add(e);
					}

				}

				String[] temp;
				if (foodLogItems != null) {
					array = foodLogItems.split(";");
					// String s="";
					for (String s : array)

					{
						temp = s.split(":");
						FoodLogItems e = new FoodLogItems(temp[0], Integer
								.parseInt(temp[1]), temp[2]);
						ret.add(e);
					}
					lbFG.addItem("");

					String[] input = new String[ret.size()];
					int i = 0;
					for (FoodLogItems e : ret) {

						input[i] = e.FoodGroup();
						i++;

					}

					Set<String> tmp = new LinkedHashSet<String>();
					for (String each : input) {
						tmp.add(each);
					}
					String[] output = new String[tmp.size()];
					int j = 0;
					for (String each : tmp) {
						output[j++] = each;
					}

					for (String s : output) {
						lbFG.addItem(s);
					}

					lbFG.setSelectedIndex(0);

				}

				int numRows = flexTable.getRowCount();
				// Window.alert(""+numRows);
				flexTable.removeAllRows();
				flexTable.insertRow(0);

				int i = 0;
				for (FoodLogData j : foodEntries) {

					flexTable.insertRow(i + 1);
					numRows = flexTable.getRowCount();

					flexTable.setText(numRows - 1, 0, j.getFoodGroup());
					flexTable.setText(numRows - 1, 1, j.getFoodName());
					flexTable.setText(numRows - 1, 2, j.getCalories()+"");

					flexTable.getFlexCellFormatter().setRowSpan(0, 1,
							numRows + 1);

					i++;

				}
				numRows = flexTable.getRowCount();
				lbFG.setSelectedIndex(0);
				flexTable.setWidget(numRows, 0, lbFG);
				flexTable.getFlexCellFormatter().setRowSpan(0, 1, numRows + 1);

				fp.add(flexTable);
				fp.add(addRowButton);
				fp.add(submit);
				fp.add(cancel);
				

			}
		});

		InlineLabel dateMessageLabel = new InlineLabel();
		dateMessageLabel.setText("Pick a date: ");

		submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				System.out.println("Submitting these items");
				for (FoodLogData e : foodEntries) {					
					System.out.println("Food name is " + e.getFoodName());
					insertRpcCall(e.getUserName(), e.getFoodName(),e.getDate(), e.getCalories());
				}
				fp.add(SuccessLabel);
				foodEntries.clear();
			}

		});
		cancel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				// TODO Auto-generated method stub
				fp.remove(SuccessLabel);
				Window.Location.reload();
			}

		});


		fp.add(dateMessageLabel);
		fp.add(dateLabel);
		fp.add(datePicker);
		fp.add(Btn2);

		return fp;

	}

	/**
	 * Add a row to the flex table.
	 */

	private int getCalories(ArrayList<FoodLogItems> ret, String foodName) {
		int test = 0;
		for (FoodLogItems e : ret) {

			if (e.FoodName().equals(foodName)) {
				test = e.Calories();
			}

		}
		return test;

	}

	private String[] getFoodNames(ArrayList<FoodLogItems> ret, String foodGroup) {
		int count = 0;

		for (FoodLogItems e : ret) {

			if (e.FoodGroup().equals(foodGroup)) {
				count++;
			}

		}
		String[] retArray = new String[count];
		int i = 0;

		for (FoodLogItems e : ret) {

			if (e.FoodGroup().equals(foodGroup)) {
				retArray[i] = e.FoodName();
				i++;
			}

		}
		return retArray;

	}

	private void addRow(FlexTable flexTable) {
		int numRows = flexTable.getRowCount();

		if ((lbFG.getSelectedIndex() != 0)) {

			if ((lbFN.getSelectedIndex() != 0)) {
				
				String str2 = lbFG.getValue(lbFG.getSelectedIndex());
				String str3 = lbFN.getValue(lbFN.getSelectedIndex());
				String str4 = flexTable.getText(numRows - 1, 2);
				
				
				
				FoodLogData e = new FoodLogData(userNameTyped, str3,
						Integer.parseInt(str4), str2, selectedDate);
				foodEntries.add(e);
				//Kevin's code
				flexTable.insertRow(flexTable.getRowCount());
				numRows = flexTable.getRowCount();
				flexTable.setText(numRows-2, 0, e.getFoodGroup());
				flexTable.setText(numRows-2, 1, e.getFoodName());
				flexTable.setText(numRows-2, 2, e.getCalories() + "");
				flexTable.getFlexCellFormatter().setRowSpan(0, 1,
						numRows + 1);
				//Kevin's code ends
				//Window.alert(""+numRows);
				
				/*Nadeem's original code
				flexTable.removeAllRows();
				flexTable.insertRow(0);
				numRows = flexTable.getRowCount();
				
				
				int i = 0;
				for (FoodLogData j : foodEntries) {					
					
					flexTable.insertRow(i + 1);
					numRows = flexTable.getRowCount();

					flexTable.setText(numRows - 1, 0, j.getFoodGroup());
					flexTable.setText(numRows - 1, 1, j.getFoodName());
					flexTable.setText(numRows - 1, 2, j.getCalories()+"");

					flexTable.getFlexCellFormatter().setRowSpan(0, 1,
							numRows + 1);
					
					i++;

				}*/
				//Nadeem's code ends

				numRows = flexTable.getRowCount();
				lbFG.setSelectedIndex(0);
				flexTable.setWidget(numRows, 0, lbFG);
				flexTable.getFlexCellFormatter().setRowSpan(0, 1, numRows + 1);
			} else {
				Window.alert("Food Item Not Selected.");
			}

		} else if (numRows <= 1) {
			lbFG.setSelectedIndex(0);
			flexTable.setWidget(numRows, 0, lbFG);
			flexTable.getFlexCellFormatter().setRowSpan(0, 1, numRows + 1);
		} else {
			Window.alert("Food Item Not Selected.");

		}

	}


	@Override
	public void add(Widget w) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator<Widget> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Widget w) {
		// TODO Auto-generated method stub
		return false;
	}

}