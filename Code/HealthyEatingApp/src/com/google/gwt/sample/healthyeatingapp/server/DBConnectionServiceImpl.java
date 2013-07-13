package com.google.gwt.sample.healthyeatingapp.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.sample.healthyeatingapp.client.DBConnectionService;
import com.google.gwt.sample.healthyeatingapp.client.User;
import java.sql.SQLException;
import java.util.Date;


@SuppressWarnings("serial")
public class DBConnectionServiceImpl extends RemoteServiceServlet implements DBConnectionService {
	
	private Connection conn = null;
	//private String status;
	private String url = "jdbc:mysql://localhost/HealthyEatingApp";
	private String dbuser = "rrazdan";
	private String pass = "rrazdan";
	
	public DBConnectionServiceImpl() 
	{
		try 
		{
		 Class.forName("com.mysql.jdbc.Driver").newInstance();
		 conn = DriverManager.getConnection(url, dbuser, pass);
		} 
		catch (Exception e) 
		{
			System.out.println("DID NOT CONNECT TO DB");
		}
		
	}
	
	@Override
	public User authenticateUser(String userId, String pass)  
	{
		
		 User user = new User("test", "test");
		 	
		 try 
		 {		
			 
			 
			 PreparedStatement ps1 = conn.prepareStatement( "select userName, password from Login where userName = ? AND password = ?;");
			 ps1.setString(1, userId);
			 ps1.setString(2, pass);
			 ResultSet result_ps1 = ps1.executeQuery();
			 while (result_ps1.next()) 
			 {
				 user = new User(result_ps1.getString(1), result_ps1.getString(2));
			 }
			 result_ps1.close();
			 ps1.close();
			 
			  
			 conn.close();
		 } 
		 catch (SQLException sqle) 
		 {
	         sqle.printStackTrace();
		 }  
		  
		 return user;
	}
	
	@SuppressWarnings("deprecation")
	public int GetFriendsPoints(String userName){
		int points = 0;
		Date currentDate = new Date();
		Date previousDate = new Date();
		previousDate.setDate(currentDate.getDate() - 7);
		String currentDateString = Integer.toString(currentDate.getYear()) + "-" + Integer.toString(currentDate.getMonth()) + "-" + Integer.toString(currentDate.getDate());
		String previousDateString = Integer.toString(previousDate.getYear()) + "-" + Integer.toString(previousDate.getMonth()) + "-" + Integer.toString(previousDate.getDate());
		try{
			PreparedStatement ps1 = conn.prepareStatement("select userID from user where userName = \"" + userName + "\"");
			ResultSet result_ps1 = ps1.executeQuery();
			int userID = result_ps1.getInt(0);
			result_ps1.close();
			ps1.close();
			
			PreparedStatement ps2 = conn.prepareStatement("select * from pointsearned where userID = \"" + userID + "\" AND DATE(date) Between \"" + previousDateString + "\" AND \"" + currentDateString + "\"");
			ResultSet result_ps2 = ps2.executeQuery();
			while(result_ps2.next()){
				points += result_ps2.getInt(0);
			}
			result_ps2.close();
			ps2.close();
			
			conn.close();
				
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
		}
		return points;
	}
}