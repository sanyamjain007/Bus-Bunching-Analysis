package org.iiitb.bunching.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataAccessClass {

	private Connection conn;

	public DataAccessClass() {
		try {
			String userName = "root";
			String password = "";
			String url = "jdbc:mysql://localhost:3306/bmtc_bunching";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, userName, password);
			System.out.println("connection !");
		} catch (Exception e) {
			System.out.println("Exception found" + e);
			closeConnection();
		}
	}

	private static class DAOHelper {
		private static final DataAccessClass dataobject_INSTANCE = new DataAccessClass();
	}

	public static DataAccessClass getInstance() {
		return DAOHelper.dataobject_INSTANCE;
	}

	public Connection Connect() {

		return this.conn;
	}

	public void closeConnection() {
		try {
			conn.close();
		} catch (Exception e) {
			System.out.println("Connection close error");
		}
	}
}




/*
 * 
 * package org.iiitb.bunching.database;

import java.sql.Connection;
import java.sql.DriverManager;



//	private Connection conn;

	private DataAccessClass() {
		try {
			String userName = "root";
			String password = "abcd1234@";
			String url = "jdbc:mysql://localhost/bmtc_bunching";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, userName, password);
			System.out.println("connection !");
		} catch (Exception e) {
			System.out.println("Exception found" + e);
			closeConnection();
		}*/
	/*	Connection conn=null;
	
		try{
			String username = "root";
			String password = "abcd1234@";
			String url = "jdbc:mysql://localhost:3306/mysql";
			//String driver = ("com.mysql.jdbc.Driver");
			
			
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("not Connected");
			conn = DriverManager.getConnection(url,username,password);
			System.out.println("Connected");
			
		}
		catch(Exception e)
		{
			System.out.println("connection failed");
		}

	
		
	
	
	}


	private static class DAOHelper {
		private static final DataAccessClass dataobject_INSTANCE = new DataAccessClass();
	}

	public static DataAccessClass getInstance() {
		return DAOHelper.dataobject_INSTANCE;
	}

	public Connection Connect() {

		return this.conn;
	}

	public void closeConnection() {
		try {
			conn.close();
		} catch (Exception e) {
			System.out.println("Connection close error");
		}
	}
}



public class DataAccessClass {
	public Connection getConnection(){
		Connection con=null;
	try{
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/mysql";
		String username = "root";
		String password = "abcd1234@";
		Class.forName(driver);
		System.out.println("not Connected");
		con = DriverManager.getConnection(url,username,password);
		System.out.println("Connected");
		//return conn;
	}
	catch(Exception e)
	{
		System.out.println("connection failed");
	}
		
	return con;
	
	}

}
*/