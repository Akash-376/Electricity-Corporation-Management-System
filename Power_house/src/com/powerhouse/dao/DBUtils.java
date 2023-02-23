package com.powerhouse.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBUtils {
	final static String URL;
	final static String userName;
	final static String password;
	
	Connection connection = null;
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			System.err.println("Fatal error! Unable to start application");
			System.exit(1);
		}
		
		// you are here means Driver class is loaded successfully
		
		ResourceBundle bundle = ResourceBundle.getBundle("dbdetails");
		
		URL = bundle.getString("url");
		userName = bundle.getString("username");
		password = bundle.getString("password");
	}
	
	static Connection connectToDatabase() throws SQLException {
		return DriverManager.getConnection(URL, userName, password);
	}
	
	static void closeConnection(Connection connection) throws SQLException {
		if(connection!=null) connection.close();
	}
	
	
}
