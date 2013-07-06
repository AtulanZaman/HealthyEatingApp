package com.google.gwt.sample.healthyeatingapp.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.sample.healthyeatingapp.client.DBConnection;
import com.google.gwt.sample.healthyeatingapp.client.User;


@SuppressWarnings("serial")
public class MySQLConnection extends RemoteServiceServlet implements DBConnection {
	
	private Connection conn = null;
	//private String status;
	private String url = "jdbc:mysql://localhost:3306/HealthyEatingApp";
	private String dbuser = "rrazdan";
	private String pass = "rrazdan";
	
	public MySQLConnection() 
	{
		try 
		{
		 Class.forName("com.mysql.jdbc.Driver").newInstance();
		 conn = DriverManager.getConnection(url, dbuser, pass);
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
		
	}
	@Override
	public User authenticateUser(String userId) 
	{
		 User user = null;
		 try 
		 {
			 PreparedStatement ps = conn.prepareStatement( "select firstName from User where firstName = \"" + dbuser);
			 ResultSet result = ps.executeQuery();
			 while (result.next()) 
			 {
				 user = new User(result.getString(1));
			 }
			 result.close();
			 ps.close();
		 } 
		 catch (SQLException sqle) 
		 {
			 //do stuff on fail
		 }
		 return user;
	}	
}