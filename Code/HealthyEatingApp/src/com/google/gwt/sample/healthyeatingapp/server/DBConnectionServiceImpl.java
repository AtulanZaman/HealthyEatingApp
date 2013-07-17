package com.google.gwt.sample.healthyeatingapp.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.sample.healthyeatingapp.client.BCrypt;
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
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;

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
	
	// Login, Logout, Register server side handling **********************************************************************
	@Override
	public User authenticateUser(String userId, String pass)  
	{	
		
		 User user  = null;
		 if (userId == null || pass == null)
		 {
			 return user;
		 }

		 try 
		 {			 
			 PreparedStatement psAuthorize = conn.prepareStatement("select userName, password from Login where userName = ?;");
			 psAuthorize.setString(1, userId);
			// psAuthorize.setString(2, pass);
			 ResultSet result_psAuthorize = psAuthorize.executeQuery();
			 if (result_psAuthorize.next()) 
			 {
				 String username = result_psAuthorize.getString("userName");
				 //this is a hashed version from the DB
				 String password = result_psAuthorize.getString("password");
				 boolean valid = BCrypt.checkpw(pass, password);
				 if (valid){
					 user = new User(username, password);  
					 user.setLoggedIn(true);
	            	 user.setSessionId(this.getThreadLocalRequest().getSession().getId());
	        		 storeUserInSession(user);
				 }
				 else{
					 
					 //return null
					 return user;
				 }
			 }
			  
			 result_psAuthorize.close();
             psAuthorize.close();
		 } 
		 catch (SQLException sqle) 
		 {
			  
	         //sqle.printStackTrace();
		 } 
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
		getThreadLocalRequest().getSession().invalidate();
	}
	
    private void deleteUserFromSession()
    {
    	 HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
         HttpSession session = httpServletRequest.getSession();
         session.removeAttribute("user");

    }
    
    public String getUserName()  
    {  
        HttpSession session = getThreadLocalRequest().getSession(true);  
        if (session.getAttribute("user") != null)  
        {  
           User user = (User) session.getAttribute("user"); 
           System.out.println(user.getUserName());
           return user.getUserName();
        }  
        else   
        {  
            return "";  
        }  
    }  

    @Override
	public User register(String newusername, String newpassword) {
		System.out.println("in register server side");
		User IfExists = null;
		try{
			String hash = BCrypt.hashpw(newpassword, BCrypt.gensalt());
			//check if duplicate
			IfExists = authenticateUser(newusername, newpassword); 
			PreparedStatement psRegister;
            //only insert new user if not a duplicate
            if(IfExists == null)
            {
				psRegister = conn.prepareStatement("insert into Login(userName, password) values (?, ?);");
				
				psRegister.setString(1, newusername);
				psRegister.setString(2, hash);
				psRegister.execute();
				psRegister.close();
            }
            
		}
		catch (SQLException sqle) {
			
 		} 
		return IfExists;	
	}	   
    
    
  
    // end of Login, Logout, Register server side handling *******************************************************************
    
    
    
    
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
			PreparedStatement ps1 = conn.prepareStatement("SELECT FoodLog.date, FoodItems.calories FROM HealthyEatingApp.FoodLog, HealthyEatingApp.user, HealthyEatingApp.FoodItems where user.userName = \""+username+"\" and user.userID = FoodLog.userID and FoodLog.foodName = FoodItems.foodName;");
			ResultSet result_ps2 = ps1.executeQuery();
			
			data.addColumn(new ColumnDescription("date", ValueType.TEXT, "date"));
			data.addColumn(new ColumnDescription("calorie", ValueType.NUMBER, "calorie"));
			
			result_ps2.last();
			int size = result_ps2.getRow();
			result_ps2.first();
//			data.addRowFromValues(result_ps2.getDate(1).toString(), result_ps2.getInt(2));
//			int i = result_ps2.getRow();
		
			Vector<Date> date_vector = new Vector<Date>();
			Vector<Integer> calorie_vector = new Vector<Integer>();
			
			for(int j = 0; j < size; j++){
				if(date_vector.size() == 0){
					date_vector.add(result_ps2.getDate(1));
					calorie_vector.add(result_ps2.getInt(2));
					result_ps2.next();
				}else{
					boolean matched = false;
					for(Date d: date_vector){
						if(d.equals(result_ps2.getDate(1))){
							Integer temp = calorie_vector.get(date_vector.indexOf(d));
							calorie_vector.add(date_vector.indexOf(d), temp+result_ps2.getInt(2));
							result_ps2.next();
							matched = true;
							break;
						}
					}
					if(!matched){
						date_vector.add(result_ps2.getDate(1));
						calorie_vector.add(result_ps2.getInt(2));
						result_ps2.next();
					}
				}	
			}
			
			
			for(int i = 0; i < date_vector.size(); i++){
				data.addRowFromValues(date_vector.elementAt(i).toString(), calorie_vector.elementAt(i));
			}
			
//			for(; i <= size; i++){
//				if(data.getNumberOfRows() == 0){
//					data.addRowFromValues(result_ps2.getDate(1).toString(), result_ps2.getInt(2));
////					dateSet.add(result_ps2.getDate(1));
//					result_ps2.next();
//				}else{
//					for(int j = 0; j < data.getNumberOfRows(); j++){
//						if((Date)data.getValue(i, 0).getObjectToFormat() == result_ps2.getDate(1)){
//							data.s
//						}
//					}
//				
//					data.addRowFromValues(result_ps2.getDate(1).toString(), result_ps2.getInt(2));
//					result_ps2.next();
//				}
//			}
			
		}catch(SQLException | TypeMismatchException sqle){
			sqle.printStackTrace();
		}
	    return JsonRenderer.renderDataTable(data, true, false, false).toString();
	}

}