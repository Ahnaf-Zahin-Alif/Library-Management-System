package database;

import java.sql.*;


public class DatabaseConnection {
    private static final String URL = 
    "jdbc:mysql://localhost:3306/library_db";

    private static final String USER = "root";

    private static final String PASSWORD = "1234";

    public static Connection getConnection(){
        Connection conn = null;

        try{
            conn = DriverManager.getConnection(URL , USER , PASSWORD);
            

        }catch(SQLException e){
            e.printStackTrace();
        }

        return conn;
    
    }
}
