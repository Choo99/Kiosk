package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {


	private String databaseName = "kioskappdb_dev";
	private String username = "MingJie";
	private String password = "abc123";
	
	

	
    public Connection getConnection() throws ClassNotFoundException, SQLException 
    {
		
		// Load database driver
    	Class.forName("com.mysql.cj.jdbc.Driver");
		
		// Get connection object from the database
		Connection connection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/kioskappdb_dev?useTimezone=true&serverTimezone=UTC", 
				username, password);
		
		return connection;
  
    }



}
