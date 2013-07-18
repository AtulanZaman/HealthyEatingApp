package com.google.gwt.sample.healthyeatingapp.client.SocialMedia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.text.DateFormat;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import com.google.api.gwt.oauth2.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
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
import com.google.gwt.sample.healthyeatingapp.client.DBConnectionService;
import com.google.gwt.sample.healthyeatingapp.client.DBConnectionServiceAsync;
import com.google.gwt.sample.healthyeatingapp.client.FacebookFriends;
import com.google.gwt.sample.healthyeatingapp.client.FacebookGraph;
import com.google.gwt.sample.healthyeatingapp.client.FriendUser;
import com.google.gwt.sample.healthyeatingapp.client.Points;
/*import com.gwittit.client.facebook.ConnectState;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookConnect;
import com.gwittit.client.facebook.ui.ProfilePicsPanel;
import com.gwittit.client.facebook.xfbml.Xfbml;*/



public class SocialMedia extends Composite implements HasWidgets{
	
	FlowPanel fp;
	Button Btn;
	FlexTable ft;
	private final DBConnectionServiceAsync rpc;
	private FacebookFriends FBFriends; 
	public List<Points> PointsList;
	
	public SocialMedia(){
		FBFriends = new FacebookFriends();
		PointsList = new ArrayList<Points>();
		rpc = (DBConnectionServiceAsync) GWT.create(DBConnectionService.class);
	 	ServiceDefTarget target = (ServiceDefTarget) rpc;
		String moduleRelativeURL = GWT.getModuleBaseURL() + "DBConnectionServiceImpl";
		target.setServiceEntryPoint(moduleRelativeURL); 
	}
	
	public void VerifyFriendAsUser()
	{		
		for(int i=0; i<FBFriends.Friends.size(); i++)
		{
			rpc.IsFriendUser(FBFriends.Friends.get(i).FirstName(), FBFriends.Friends.get(i).LastName(), new FriendsUserNameCallback());
		}		
	}
	
	private class FriendsUserNameCallback implements AsyncCallback{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			System.out.println("failed at friendsUserName callback " + caught.getMessage());
		}

		@Override
		public void onSuccess(Object result) {
			FriendUser NewFriend = (FriendUser) result;
			if(NewFriend.IsUser()){
				System.out.println("Friend " + NewFriend.userName() + " is Healthy App User");
				rpc.GetFriendsPoints(NewFriend.userName(), new DefaultCallback());
			}
			else{
				
			}
		}
		
	}
	
	public FlowPanel SocialMediaWebPageLoad(){		
		
		fp = new FlowPanel();
		ft = new FlexTable();
		flexTableInitialize(ft);
		
		Btn = new Button();		
		Btn.setSize("100px", "30px");
		Btn.setText("Update");	
		Btn.addClickHandler(new ClickHandler() 
		{
		    @Override
			public void onClick(ClickEvent event) 
		    {
		    	PointsList.clear();
		    	ft.removeAllRows();
		    	ft.setText(0, 0, "Updating");
				ft.getFlexCellFormatter().setColSpan(0, 0, 3);		
				ft.getFlexCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		    	if(FBFriends.Friends.size()==0)
		    	{		    		
		    		System.out.println("Querying Facebook again");
		    		Window.alert("Please authenticate with Facebook to use this feature");
		    		FacebookGraph.getStaticObject().RequestAuthorizationAndQueryGraph("me/friends", new Callback<JSONObject, Throwable>(){

					@Override
					public void onFailure(Throwable reason) {
						// TODO Auto-generated method stub
						System.out.println(reason.getMessage());
					}

					@Override
					public void onSuccess(JSONObject result) {
						// TODO Auto-generated method stub
						JSONArray datas = (JSONArray) result.get("data");
						for(int i=0; i< datas.size(); i++){
							JSONObject friend = (JSONObject) datas.get(i);
							FBFriends.FriendsNames.add(friend.get("name").toString());
						}
						FBFriends.FriendsNameSort(FBFriends.FriendsNames);
						VerifyFriendAsUser();
					}
	        		  
	        	  });	        	  
		    	}		    	
		    	VerifyFriendAsUser();	    	
		      }
		    });
		fp.add(ft);
		fp.add(Btn);
		if(FBFriends.Friends.size()!=0)
		{
			VerifyFriendAsUser();
		}
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
		
	private class DefaultCallback implements AsyncCallback {
		public void onFailure(Throwable caught) 
		{
			caught.printStackTrace();
	    	Window.alert("Failure: " + caught.getMessage());        
		}
		
		public void onSuccess(Object result) {
			// TODO Auto-generated method stub
			if(!PointsList.contains((Points) result))
			{
				PointsList.add((Points) result);
			}
			SortPointsList();
			ft.removeAllRows();
	    	flexTableInitialize(ft);
			flexTablePaint();			
		}
	}	
	
	private void SortPointsList()
	{
		if(PointsList.size() != 0){
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
		if(PointsList.size() != 0){
		for(int i = 0; i < PointsList.size(); i++)
		{
			ft.setText(2+i, 0, PointsList.get(i).FirstName() + " " + PointsList.get(i).LastName());
			ft.setText(2+i, 1, Integer.toString(PointsList.get(i).Points()));
		}
		System.out.println("table painted");
		
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
