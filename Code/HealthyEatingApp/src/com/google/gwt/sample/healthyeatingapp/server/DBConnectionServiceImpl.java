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
}