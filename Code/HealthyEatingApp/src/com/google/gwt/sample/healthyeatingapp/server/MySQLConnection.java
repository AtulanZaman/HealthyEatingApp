package com.google.gwt.sample.healthyeatingapp.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.sample.healthyeatingapp.client.DBConnection;
import com.google.gwt.sample.healthyeatingapp.client.User;


public class MySQLConnection extends RemoteServiceServlet implements DBConnection {
	
	private Connection conn = null;
	private String status;
	private String url = "jdbc:mysql://localhost:3306/HealthyEatingApp";
	private String user = "rrazdan";
	private String pass = "rrazdan";
	
	public MySQLConnection() 
	{
		try 
		{
		 Class.forName("com.mysql.jdbc.Driver").newInstance();
		 conn = DriverManager.getConnection(url, user, pass);
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
		
	}
	public User authenticateUser(String user, String pass) 
	{
		 User user1 = null;
		 try 
		 {
			 PreparedStatement ps = conn.prepareStatement( "select readonly * from users where username = \"" + user1 + "\" AND " + "password = \"" + pass + "\"");
			 ResultSet result = ps.executeQuery();
			 while (result.next()) 
			 {
				 user1 = new User(result.getString(1),
				 result.getString(2));
			 }
			 result.close();
			 ps.close();
		 } 
		 catch (SQLException sqle) 
		 {
			 //do stuff on fail
		 }
		 return user1;
	}	
}