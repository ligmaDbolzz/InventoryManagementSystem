package Database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {
    static final String driver = "com.mysql.cj.jdbc.Driver";
    static final String url = "jdbc:mysql://localhost:3306/inventory";
    static String username = "root";
    static String password = "@Minhtuan03";
    Connection con;
    
    public ConnectionFactory(){
        try {
            //Config user and password of the mysql connection
            Class.forName(driver);
            con = DriverManager.getConnection(url,username, password);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Connection getCon() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Connected successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
