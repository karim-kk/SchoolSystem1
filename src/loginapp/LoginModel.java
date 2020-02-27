package loginapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dbUtil.dbConnection;

public class LoginModel {

    Connection connection;  //connection object


    //*Checking if it is connected to the DB
    public LoginModel(){
        try{                // try and catch to try if this connection object can be set to getconnection method
            this.connection = dbConnection.getConnection();   //class.method in class
        }
        catch(SQLException ex){
            System.out.println("ERROR LoginModel LoginModel " + ex );
            ex.printStackTrace();
        }
        if(this.connection == null){
            System.exit(1);
        }
    }

    public boolean isDBConnected(){

        return this.connection != null;
    }

    public boolean isLoginConnected(String paramID, String paramPassword, String paramType) throws Exception{

        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM login where ID = ? and Password = ? and Type = ?"; //sql query

        try{
            ps = this.connection.prepareStatement(sql);  // passing in the query
            ps.setString(1, paramID); //getting the username from th DB
            ps.setString(2, paramPassword); //getting the passsword from th DB
            ps.setString(3,paramType);      //getting the logintype from th DB
            rs = ps.executeQuery();

            boolean bool;

            if (rs.next()){   // checking if their is more items in the database
                return true;
            }
                return false;


        } catch (SQLException ex){
            System.out.println("ERROR LoginModel isLoginConnected " + ex );
            ex.printStackTrace();
            return false;
        }
        finally{         //closing the connection
            {
                rs.close();
                ps.close();
            }
        }
    }
/*
    public void whoIsConnencted(String paramUsername) throws Exception{
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM login where Username = ?";


        try{
            ps = this.connection.prepareStatement(sql);  // passing in the query
            ps.setString(1, paramUsername); //getting the username from th DB

            rs = ps.executeQuery();
        } catch (SQLException ex){
            ex.printStackTrace();

        }



    }

 */



}
