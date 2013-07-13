package com.google.gwt.sample.healthyeatingapp.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.sample.healthyeatingapp.client.DBConnectionService;
//import com.google.gwt.sample.healthyeatingapp.client.Points;
import com.google.gwt.sample.healthyeatingapp.client.User;
//import com.google.gwt.sample.healthyeatingapp.client.Points;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



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
			System.out.println("Did not connect to DB");
		}
		
	}
	
	@Override
	public User authenticateUser(String userId, String pass)  
	{	
		 if (userId == null || pass == null)
		 {
			 return null;
		 }

		User user  = null;
		
		 try 

		 {			 
			 PreparedStatement ps1 = conn.prepareStatement("select userName, password from Login where userName = ? AND password = ?;");
			 ps1.setString(1, userId);
			 ps1.setString(2, pass);
			 ResultSet result_ps1 = ps1.executeQuery();
			 while (result_ps1.next()) 
			 {
				 String username = result_ps1.getString("userName");
				 String password = result_ps1.getString("password");
				 user = new User(username, password);  
			 }
			 result_ps1.close();
			 ps1.close();
			 
             if (user.getPassword() != null || user.getUserName() != null)
             {
                 user.setSessionId(this.getThreadLocalRequest().getSession().getId());
             }

			 //conn.close();
		 } 
		 catch (SQLException sqle) 
		 {
	         sqle.printStackTrace();
		 } 
		 storeUserInSession(user);
		 return user;
	}

	private void storeUserInSession(User user)
    {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("user", user);
    }
	
	
	public void logout()
	{
		deleteUserFromSession();
	}
	
    private void deleteUserFromSession()
    {
    	 HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
         HttpSession session = httpServletRequest.getSession();
         session.removeAttribute("user");

    }

	
	
	
//	@SuppressWarnings("deprecation")
//	public Points GetFriendsPoints(String userName){
//		Points points = new Points("test", "test", "test", 0);
//		int point = 0;
//		Date currentDate = new Date();
//		Date previousDate = new Date();
//		previousDate.setDate(currentDate.getDate() - 7);
//		String currentDateString = Integer.toString(currentDate.getYear()) + "-" + Integer.toString(currentDate.getMonth()) + "-" + Integer.toString(currentDate.getDate());
//		String previousDateString = Integer.toString(previousDate.getYear()) + "-" + Integer.toString(previousDate.getMonth()) + "-" + Integer.toString(previousDate.getDate());
//		try{
//			PreparedStatement ps1 = conn.prepareStatement("select userID from user where userName = \"" + userName + "\"");
//			ResultSet result_ps1 = ps1.executeQuery();			
//			int userID = result_ps1.getInt(0);
//			result_ps1.close();
//			ps1.close();
//			
//			PreparedStatement ps2 = conn.prepareStatement("select * from pointsearned where userID = \"" + userID + "\" AND DATE(date) Between \"" + previousDateString + "\" AND \"" + currentDateString + "\"");
//			ResultSet result_ps2 = ps2.executeQuery();
//			while(result_ps2.next()){
//				point += result_ps2.getInt(0);
//			}
//			result_ps2.close();
//			ps2.close();
//			
//			PreparedStatement ps3 = conn.prepareStatement("select * from user where userName = \"" + userName + "\"");
//			ResultSet result_ps3 = ps3.executeQuery();
//			String firstName = result_ps3.getString(1);
//			String lastName = result_ps3.getString(2);
//			result_ps3.close();
//			ps3.close();
//			
//			points = new Points(userName, firstName, lastName, point);			
//			
//			conn.close();
//				
//		}
//		catch(SQLException sqle){
//			sqle.printStackTrace();
//		}
//		return points;
//	}	
}