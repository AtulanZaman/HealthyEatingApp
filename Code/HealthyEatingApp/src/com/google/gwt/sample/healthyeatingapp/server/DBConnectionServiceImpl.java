package com.google.gwt.sample.healthyeatingapp.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.visualization.datasource.base.TypeMismatchException;
import com.google.visualization.datasource.datatable.ColumnDescription;
//import com.google.gwt.visualization.client.DataTable;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.value.ValueType;
import com.google.gwt.sample.healthyeatingapp.client.DBConnectionService;
import com.google.gwt.sample.healthyeatingapp.client.Points;
//import com.google.gwt.sample.healthyeatingapp.client.Points;
import com.google.gwt.sample.healthyeatingapp.client.User;
//import com.google.gwt.sample.healthyeatingapp.client.Points;
import com.google.visualization.datasource.render.JsonRenderer;

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
	private String currentYear = "";
	private String currentMonth = "";
	private String currentDate = "";
	private String previousYear = "";
	private String previousMonth = "";
	private String previousDate = "";
	
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
	
	private User getUserInSession(String username){
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession(true);
        return (User)session.getAttribute("user");
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

		
	public Points GetFriendsPoints(String userName){
		Points points = new Points("test", "test", "test", 0);
		int point = 0;
		int userID = 0;
		String firstName = "";
		String lastName = "";		
		Date cal = new Date();
		currentYear = Integer.toString(cal.getYear() + 1900);
		if(cal.getMonth() > 8){
			currentMonth = Integer.toString(cal.getMonth() + 1);			
		}
		else{
			currentMonth = "0" + Integer.toString(cal.getMonth() + 1);
		}
		currentDate = Integer.toString(cal.getDate());
		String currentDateString = currentYear + "-" + currentMonth + "-" + currentDate;
		Date dateBefore = new Date(cal.getTime() - 7 * 24 * 3600 * 1000 );
		previousYear = Integer.toString(dateBefore.getYear() + 1900);
		if(dateBefore.getMonth() > 8){
			previousMonth = Integer.toString(dateBefore.getMonth() + 1);			
		}
		else{
			previousMonth = "0" + Integer.toString(dateBefore.getMonth() + 1);
		}
		previousDate = Integer.toString(dateBefore.getDate());
		String previousDateString = previousYear + "-" + previousMonth + "-" + previousDate;
		try{
			PreparedStatement ps1 = conn.prepareStatement("select * from user where userName = \"" + userName + "\"");
			ResultSet result_ps1 = ps1.executeQuery();
			while(result_ps1.next()){
				userID = result_ps1.getInt(1);
			}
			
			
			PreparedStatement ps2 = conn.prepareStatement("select * from pointsearned where userID = \"" + userID + "\" AND DATE(date) Between \"" + previousDateString + "\" AND \"" + currentDateString + "\"");
			ResultSet result_ps2 = ps2.executeQuery();
			while(result_ps2.next()){
				point += result_ps2.getInt(1);
			}
			
			
			PreparedStatement ps3 = conn.prepareStatement("select * from user where userName = \"" + userName + "\"");
			ResultSet result_ps3 = ps3.executeQuery();
			while(result_ps3.next()){
			firstName = result_ps3.getString(2);
			lastName = result_ps3.getString(3);
			}
			result_ps1.close();
			ps1.close();
			result_ps2.close();
			ps2.close();
			result_ps3.close();
			ps3.close();
			
			points = new Points(userName, firstName, lastName, point);			
			
			//conn.close();
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
		}
		return points;
	}
	@Override
	public String getUserCalories(String username){
		DataTable data = new DataTable();	
		try{
			PreparedStatement ps1 = conn.prepareStatement("SELECT FoodLog.date, FoodItems.Calories  FROM HealthyEatingApp.FoodLog, HealthyEatingApp.FoodItems, HealthyEatingApp.user, HealthyEatingApp.FoodItemFoodLog Where user.userName = \"" + username +"\" and user.userID = FoodLog.userID and FoodLog.logID = FoodItemFoodLog.logID and FoodItemFoodLog.foodName = FoodItems.foodName;");
			ResultSet result_ps2 = ps1.executeQuery();
			
			data.addColumn(new ColumnDescription("date", ValueType.TEXT, "date"));
			data.addColumn(new ColumnDescription("calorie", ValueType.NUMBER, "calorie"));
			
			result_ps2.last();
			int size = result_ps2.getRow();
			result_ps2.first();
			int i = result_ps2.getRow();
			
			for(; i <= size; i++){
				data.addRowFromValues(result_ps2.getDate(1).toString(), result_ps2.getInt(2));
				result_ps2.next();
			}
			
		}catch(SQLException | TypeMismatchException sqle){
			sqle.printStackTrace();
		}
	    return JsonRenderer.renderDataTable(data, true, false, false).toString();
	}

}