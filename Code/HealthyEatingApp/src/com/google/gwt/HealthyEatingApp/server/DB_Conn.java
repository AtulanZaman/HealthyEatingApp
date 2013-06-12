package com.google.gwt.HealthyEatingApp.server;


import java.sql.Connection;
import java.sql.DriverManager;
 

public class DB_Conn {

	protected Connection getConn() {

        Connection conn = null;

        // figure out what server this application is being hosted on
        String url = "jdbc:mysql://127.0.0.1/HealthyEatingApp";
        String db               = "HealthyEatingApp";
        String driver = "com.mysql.jdbc.Driver";
        String user     = "rrazdan";
        String pass     = "rrazdan";

        
        url = url + db;
        
        System.out.println("connection url: " + url);
        
        try {

                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url, user, pass);

        } catch (Exception e) {

                // error
                System.err.println("Mysql Connection Error: ");

                // for debugging error
                e.printStackTrace();
        }

        if (conn == null)  {
                System.out.println("~~~~~~~~~~ can't get a Mysql connection");
        }
        
        return conn;
	}

	

}
