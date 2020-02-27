package dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class dbConnection {        //class connecting to the DB

    private static final String SQLCONNECTION= "jdbc:sqlite:school.sqlite";  // linking the DB

    public static Connection getConnection() throws SQLException {

        try{
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(SQLCONNECTION);
        }
        catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        return null;
    }



}

