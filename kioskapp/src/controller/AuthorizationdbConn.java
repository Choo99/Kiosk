package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class AuthorizationdbConn {

	private int portNo = 3307;
	private String databaseName = "creditcardauthorization";
	private String username = "root";
	private String password = "";
	
    public Connection getConnection() throws ClassNotFoundException, SQLException 
    {
		
		// Load database driver
    	Class.forName("com.mysql.jdbc.Driver");
		
		// Get connection object from the database
		Connection connection =  DriverManager.getConnection("jdbc:mysql://localhost:" + portNo +"/" + databaseName + "?useTimezone=true&serverTimezone=UTC", 
				username, password);
		
		return connection;
  
    }



}
