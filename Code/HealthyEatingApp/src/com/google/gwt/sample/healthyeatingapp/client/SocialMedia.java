package com.google.gwt.sample.healthyeatingapp.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.text.DateFormat;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.sample.healthyeatingapp.*;
//import com.google.gwt.sample.healthyeatingapp.client.Points;



public class SocialMedia extends Composite implements HasWidgets{
	
	FlowPanel fp;
	Button Btn;
	FlexTable ft;
	private final DBConnectionServiceAsync rpc;
	
	public List<Points> PointsList = new ArrayList<Points>();
	public List<String> firstNames = new ArrayList<String>();
	public List<String> lastNames = new ArrayList<String>();
	public List<Integer> userPoints= new ArrayList<Integer>();
	private List<String> friendsUsernames = new ArrayList<String>();
	
	public SocialMedia(){
		//initWidget(this.fp);
		rpc = (DBConnectionServiceAsync) GWT.create(DBConnectionService.class);
	 	ServiceDefTarget target = (ServiceDefTarget) rpc;
		String moduleRelativeURL = GWT.getModuleBaseURL() + "DBConnectionServiceImpl";
		target.setServiceEntryPoint(moduleRelativeURL); 
		
		//fp.add(new LeaderboardWidget(this));
		//SocialMediaWebPageLoad(HTML);
	}
	
	
	
	public FlowPanel SocialMediaWebPageLoad(){
		
		friendsUsernames.add("rrazdan");
		friendsUsernames.add("a3zaman");
		friendsUsernames.add("jh33lee");

		
		fp = new FlowPanel();
		ft = new FlexTable();
		flexTableInitialize(ft);
		
		Btn = new Button();		
		Btn.setSize("100px", "30px");
		Btn.setText("Update");	
		Btn.addClickHandler(new ClickHandler() {
		    @Override
			public void onClick(ClickEvent event) {
		    	rpcCall();
		    	SortPointsList();
		    	flexTablePaint();
		      }
		    });
		fp.add(ft);
		fp.add(Btn);
		rpcCall();		
		SortPointsList();
		flexTablePaint();
		//flexTablePaint();*/
		return fp;
	}
	
	private void flexTableInitialize(FlexTable ft){
		ft.setTitle("Leaderboard");
		ft.setText(0, 0, "Leaderboard");
		ft.getFlexCellFormatter().setColSpan(0, 0, 2);		
		ft.getFlexCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		ft.setText(1, 0, "Friend");
		ft.setText(1, 1, "Points");
	}
	
	private void rpcCall(){		
		for(int i = 0; i<friendsUsernames.size(); i++){	
			rpc.GetFriendsPoints(friendsUsernames.get(i), new DefaultCallback());	    	
	    	}		
	}
	
	private class DefaultCallback implements AsyncCallback {
		public void onFailure(Throwable caught) 
		{
			caught.printStackTrace();
	    	Window.alert("Failure: " + caught.getMessage());        
		}
		
		public void onSuccess(Object result) {
			// TODO Auto-generated method stub
			
			PointsList.add((Points) result);
			//Window.alert("SUCCESSFULLY CONNECTED TO DB!"); 
			
		}
	}	
	
	private void SortPointsList()
	{
		if(!PointsList.isEmpty()){
		quickSort(PointsList, 0, PointsList.size()-1);
		}
	}
	
	public static void quickSort(List<Points> a, int low, int high)
    {
		int i=low;
		int j=high;
		Points temp;
		int middle=a.get((low+high)/2).Points();

		while (i<j)
		{
			while (a.get(i).Points()>middle)
			{
				i++;
			}
			while (a.get(j).Points()<middle)
			{
				j--;
			}
			if (j>=i)
			{
				temp=a.get(i);
				a.set(i, a.get(j));
				a.set(j, temp);
				i++;
				j--;
			}
		}


		if (low<j)
		{
			quickSort(a, low, j);
		}
		if (i<high)
		{
			quickSort(a, i, high);
		}
    }

    
	
	private void flexTablePaint(){
		FlexTable ft = (FlexTable) fp.getWidget(0);
		if(!PointsList.isEmpty()){
		for(int i = 0; i < PointsList.size(); i++){
			ft.setText(2+i, 0, PointsList.get(i).FirstName() + " " + PointsList.get(i).LastName());
			//Window.alert("First Name is " + PointsList.get(i).FirstName());
			ft.setText(2+i, 1, Integer.toString(PointsList.get(i).Points()));
		}
		PointsList.clear();
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
