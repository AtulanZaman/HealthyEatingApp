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
import com.google.gwt.sample.healthyeatingapp.client.FacebookGraph;
import com.google.gwt.sample.healthyeatingapp.client.FriendUser;
import com.google.gwt.sample.healthyeatingapp.client.Points;
//import com.google.gwt.sample.healthyeatingapp.client.Points;
import com.google.gwt.sample.healthyeatingapp.client.User;
//import com.google.gwt.sample.healthyeatingapp.client.Points;
import com.google.visualization.datasource.render.JsonRenderer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



@SuppressWarnings("serial")
public class DBConnectionServiceImpl extends RemoteServiceServlet implements DBConnectionService {
	
	private Connection conn = null;
	// private String status;
	private String url = "jdbc:mysql://localhost/healthyeatingapp";
	//private String url = "jdbc:mysql://eceweb.uwaterloo.ca:3306/hospital_n9ahmad";
	private String dbuser = "rrazdan";
	//private String dbuser = "user_n2ahmad";
	private String pass = "rrazdan";
	//private String pass = "ece356prj";
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
    
    @Override
    public User authenticateFacebookUser(String firstName, String lastName){
		User user = null;
		String userName = "";
		String passWord = "";
    	try
    	{
    		PreparedStatement ps = conn.prepareStatement("select * from user where firstName = \"" + firstName + "\" and lastName = \"" + lastName + "\"");
    		ResultSet result_ps = ps.executeQuery();	
			while(result_ps.next()){
				userName = result_ps.getString(5);
			}
			ps.close();
			if(userName.length() != 0)
			{
				PreparedStatement ps1 = conn.prepareStatement("select * from login where userName = \"" + userName + "\"");
				ResultSet result_ps1 = ps1.executeQuery();
				while(result_ps1.next())
				{
					passWord = result_ps1.getString(2);
				}
				ps1.close();
				user = new User(userName, passWord);
			}
    	}
    	catch(SQLException sqle)
    	{
    		sqle.printStackTrace();
    	}
    	return user;    	
    }
    
    public String getUserName()  
    {  
        HttpSession session = getThreadLocalRequest().getSession(true);  
        if (session.getAttribute("user") != null)  
        {  
           User user = (User) session.getAttribute("user"); 
           //System.out.println(user.getUserName());
           return user.getUserName();
        }  
        else   
        {  
            return "";  
        }  
    }  

    @Override
	public User register(String newusername, String newpassword, String newfirstname, String newlastname) {
		System.out.println("in register server side");
		User IfExists = null;
		try{
			String hash = BCrypt.hashpw(newpassword, BCrypt.gensalt());
			int userID = -1;
			//check if duplicate
			IfExists = authenticateUser(newusername, newpassword); 
			PreparedStatement psRegister;
			PreparedStatement psRegister2;
			PreparedStatement psRegister1;
            //only insert new user if not a duplicate
            if(IfExists == null)
            {
				psRegister = conn.prepareStatement("insert into Login(userName, password) values (?, ?);");
				psRegister.setString(1, newusername);
				psRegister.setString(2, hash);				
				psRegister.execute();
				psRegister.close();
				
				psRegister2 = conn.prepareStatement("select * from Login where userName = \"" + newusername + "\"");
				
				ResultSet result_ps2 = psRegister2.executeQuery();
				while(result_ps2.next()){
					userID = result_ps2.getInt(3);
				}
				result_ps2.close();
				psRegister2.close();
				
				psRegister1 = conn.prepareStatement("insert into User(userID, firstName, lastName, type, userName) values(?,?,?,?,?)");
				psRegister1.setInt(1, userID);
				psRegister1.setString(2, newfirstname);
				psRegister1.setString(3, newlastname);
				psRegister1.setString(4, "user");
				psRegister1.setString(5, newusername);
				psRegister1.execute();				
				psRegister1.close();
            }
            
		}
		catch (SQLException sqle) {
			
 		} 
		return IfExists;	
	}	   
    
    
  
    // end of Login, Logout, Register server side handling *******************************************************************
    
    
    
    
    public FriendUser IsFriendUser(String firstName, String lastName){
    	FriendUser friendUser = new FriendUser();
    	//System.out.println("In IsFriendUser");
    	String userName = "";
    	try{
    		PreparedStatement ps = conn.prepareStatement("select * from user where firstName = \"" + firstName + "\" and lastName = \"" + lastName + "\"");
    		ResultSet result_ps = ps.executeQuery();
			while(result_ps.next()){
				userName = result_ps.getString(5);
			}
			ps.close();
			if(userName.length() == 0){
				friendUser.setUserName("empty");
				friendUser.setIsUser(false);
			}else{
			friendUser.setUserName(userName);
			friendUser.setIsUser(true);
			}

			return friendUser;
    	}
    	catch(SQLException sqle){
    		sqle.printStackTrace();
    	}
    	return friendUser;
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
			System.out.println("Inside getUserCalories " + username);
			
			PreparedStatement ps1 = conn.prepareStatement("SELECT FoodLog.date, FoodItems.calories FROM HealthyEatingApp.FoodLog, HealthyEatingApp.user, HealthyEatingApp.FoodItems where user.userName = \""+username+"\" and user.userID = FoodLog.userID and FoodLog.foodName = FoodItems.foodName order by Foodlog.date asc;");
			ResultSet result_ps2 = ps1.executeQuery();
			
			data.addColumn(new ColumnDescription("date", ValueType.TEXT, "date"));
			data.addColumn(new ColumnDescription("calorie", ValueType.NUMBER, "calorie"));
			
			Hashtable<Date, Integer> Temp = new Hashtable<Date, Integer>();
			while(result_ps2.next()){
			if(Temp.containsKey(result_ps2.getDate(1))){
			int newVal = Temp.get(result_ps2.getDate(1)) + result_ps2.getInt(2);
			Temp.remove(result_ps2.getDate(1));
			Temp.put(result_ps2.getDate(1), newVal);
			}
			else{
			Temp.put(result_ps2.getDate(1), result_ps2.getInt(2));
			}
			}
			ArrayList<Date> DK = new ArrayList<Date>(Temp.keySet());
			Collections.sort(DK);
			for(int i=0; i<Temp.size(); i++){
			try {
			data.addRowFromValues(DK.get(i).toString(), Temp.get(DK.get(i)));
			} catch (TypeMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			}
			
			/*result_ps2.last();
			int size = result_ps2.getRow();
			System.out.println("size was " + size);
			result_ps2.first();
			
			
			Vector<Date> date_vector = new Vector<Date>();
			Vector<Integer> calorie_vector = new Vector<Integer>();
			
			for(int j = 0; j < size; j++){
				if(date_vector.size() == 0){
					date_vector.add(result_ps2.getDate(1));
					calorie_vector.add(result_ps2.getInt(2));
					System.out.println("Calories were " + result_ps2.getInt(2));
					result_ps2.next();
				}else{
					boolean matched = false;
					for(Date d: date_vector){
						if(d.equals(result_ps2.getDate(1))){
							Integer temp = calorie_vector.get(date_vector.indexOf(d));
							calorie_vector.add(date_vector.indexOf(d), temp+result_ps2.getInt(2));
							System.out.println("Calories were " + temp+result_ps2.getInt(2));
							result_ps2.next();
							matched = true;
							break;
						}
					}
					if(!matched){
						date_vector.add(result_ps2.getDate(1));
						calorie_vector.add(result_ps2.getInt(2));
						System.out.println("Calories were " + result_ps2.getInt(2));
						result_ps2.next();
					}
				}	
			}*/
			ps1.close();
			result_ps2.close();
			/*try{	
				for(int i = 0; i < date_vector.size(); i++){
					data.addRowFromValues(date_vector.elementAt(i).toString(), calorie_vector.elementAt(i));
					System.out.println("Finally: " + calorie_vector + ", " + date_vector);
				}
			}
			catch(TypeMismatchException sqle){
				//System.out.println(sqle.getMessage());
			}	*/	
		
		}
		catch(SQLException sqle){
			//System.out.println(sqle.getMessage());
		}
	    return JsonRenderer.renderDataTable(data, true, false, false).toString();
	}

	
	
	public String GetFoodNames() {
		String ret = "";

		PreparedStatement stmt = null;
		try {

			String query;
			query = "Select * from FoodItems";

			stmt = conn.prepareStatement(query);

			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				ret += resultSet.getString("foodName") + ":";
				ret += resultSet.getInt("calories") + ":";
				ret += resultSet.getString("foodGroup") + ";";

			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return ret;
	}

	public void InsertFoodLog(String userName, String foodName, String date,
			int calories) {
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;

		int userID = 0;
		try {

			String query;

			query = "Select * from Login where userName=?";

			stmt3 = conn.prepareStatement(query);

			stmt3.setString(1, userName);

			ResultSet resultSet = stmt3.executeQuery();

			while (resultSet.next()) {
				userID = resultSet.getInt("userID");

			}

			String insert;
			insert = "INSERT INTO FoodLog(userID, foodName, date) VALUES "
					+ "(?,?,?)";

			stmt = conn.prepareStatement(insert);
			stmt.setInt(1, userID);
			stmt.setString(2, foodName);
			stmt.setString(3, date);

			stmt.executeUpdate();

			String insert2;
			insert2 = "INSERT INTO pointsearned(value, date, userID) VALUES "
					+ "(?,?,?)";

			stmt2 = conn.prepareStatement(insert2);
			stmt2.setInt(1, calories);
			stmt2.setString(2, date);
			stmt2.setInt(3, userID);

			stmt2.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

	}

	public String QueryFoodLog(String userName, String date) {
		String ret = "";

		PreparedStatement stmt = null;
		PreparedStatement stmt3 = null;
		int userID=0;
		try {

			String query2;

			query2 = "Select * from Login where userName=?";

			stmt3 = conn.prepareStatement(query2);

			stmt3.setString(1, userName);

			ResultSet resultSet = stmt3.executeQuery();

			while (resultSet.next()) {
				userID = resultSet.getInt("userID");

			}

			String query;
			query = "Select * from (FoodLog, Login, FoodItems) where "
					+ "FoodLog.foodName=FoodItems.foodName AND "
					+ "FoodLog.userID=Login.userID AND Login.userID=? AND date=?";

			stmt = conn.prepareStatement(query);

			stmt.setInt(1, userID);
			stmt.setString(2, date);

			ResultSet resultSet2 = stmt.executeQuery();

			while (resultSet2.next()) {
				ret += resultSet2.getString("foodName") + ":";
				ret += resultSet2.getInt("calories") + ":";
				ret += resultSet2.getString("foodGroup") + ";";

			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return ret;

	}
	
}