package com.google.gwt.sample.healthyeatingapp.client;

import java.util.ArrayList;

import com.google.gwt.sample.healthyeatingapp.client.FacebookFriend;



public class FacebookFriends{

	public ArrayList<String> FriendsNames;
	public ArrayList<FacebookFriend> Friends;
	public FacebookFriends() {
		 	FriendsNames = new ArrayList<String>();
		 	Friends = new ArrayList<FacebookFriend>();
		 }	
	
	public void FriendsNameSort(ArrayList<String> Names){
		String FirstName = "";
		String LastName = "";
		for(int i=0; i<Names.size(); i++){
			
			String Name = Names.get(i).replaceAll("\"", "");
			//System.out.println(Name);
			try
			{
				if(Name.contains(" "))
				{
						FirstName = "";
						LastName = "";
						String[] NameSegments = Name.split(" ");
						
						FirstName = NameSegments[0];
						//System.out.println(FirstName);
						LastName = NameSegments[NameSegments.length-1];
						FacebookFriend NewFriend = new FacebookFriend();
						NewFriend.SetFirstName(FirstName);
						NewFriend.SetLastName(LastName);
						//System.out.println(FirstName + " " + LastName);
						//System.out.println(NewFriend.FirstName() + " " + NewFriend.LastName());
						this.Friends.add(NewFriend);					
				}
				else
				{
					
				}
			}
			catch(Exception e)
			{
				
			}
		}		
	}	
}
