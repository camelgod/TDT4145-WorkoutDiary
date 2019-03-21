package workoutDiary;

import java.sql.*;

public class DBConn {

    private final static String sqlUrl = "jdbc:mysql://mysql.stud.ntnu.no/edvardod_group141?autoReconnect=true&useSSL=false"; 
  
	private final static String userName = "edvardod_group141";
	private final static String password = "root";
    protected Connection conn;
    public DBConn () {
    }
    public void connect() {
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
    	} catch (ClassNotFoundException e) {
    		System.out.println("Where is your MySQL JDBC Driver?");
    		e.printStackTrace();
    		return;
    	}


		try {
			conn = DriverManager.getConnection(sqlUrl, userName, password);

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		System.out.println("You are connected!!!:-)");
    }
}
