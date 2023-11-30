/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User;

import Database.UserConection;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class User {
    private String username;
    private String password;    
    private int level;
    private String name;
    private String dateOfBirth;
    private String gender;
    private String imageDIR;
    Connection con;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        con = new UserConection().getCon();
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getLevel() {
        return level;
    }

    private void setLevel(int level) {
        this.level = level;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageDIR() {
        return imageDIR;
    }

    public void setImageDIR(String imageDIR) {
        this.imageDIR = imageDIR;
    }
    
    public ArrayList<User> allUser(){
        ArrayList<User> res = new ArrayList<>();
        String query = "";
        boolean con1 = false;
        if(this.level > 9){
            query = "SELECT * FROM user";
            con1 = true;
        }else if(this.level > 2){
            query = "SELECT * FROM user WHERE level <= " + this.level;
            con1 = true;
        }else {
            JOptionPane.showMessageDialog(null, "Your level are too low to use this feture.");
        }
        if(con1){
            PreparedStatement pst;
            try {
                pst = new UserConection().getCon().prepareStatement(query);
                ResultSet rs = pst.executeQuery();
                //load all user info to res
                while(rs.next()){
                    User nU = new User(rs.getString("username"), rs.getString("password"));
                    if(nU.getUser() && !nU.getUsername().equals(this.username)){
                        res.add(nU);
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Unable to connect to server.");
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return res;
    }

    public boolean login(boolean rp){
        boolean res = true;
        String query = "SELECT * FROM user WHERE username = '" + this.username + "'";
        PreparedStatement pst;
        try {
            pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                String passwordd = rs.getString("password");
                res = passwordd.equals(this.password);
            }else{
                res = false;
            }
        } catch (SQLException ex) {
            if(rp)JOptionPane.showMessageDialog(null, "Unable to connect to server.");
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!res){
            if(rp)JOptionPane.showMessageDialog(null, "Wrong username or password");
        }
        return res;
    }
    
    public boolean addUser(){
        boolean res = true;
        String query = "INSERT INTO user VALUES(?,?,?,?,?,?,?)";
        PreparedStatement pst;
        if(!isUserExists()){
            try {
                pst = con.prepareStatement(query);
                pst.setString(1, this.username);
                pst.setString(2, this.password);
                pst.setInt(3, 0);
                pst.setString(4, "");
                pst.setString(5, "00/00/0000");
                pst.setString(6, "");
                pst.setString(7, "");
                
                pst.executeUpdate();
                logging("Added user " + this.username + " .");
                res = true;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Unable to connect to server.");
                res = false;
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            res = false;
            JOptionPane.showMessageDialog(null, "Username already exists.");
        }
        return res;
    }
    
    public boolean isUserExists(){
        String query = "SELECT * FROM user WHERE username = '" + this.username + "'";
        PreparedStatement pst;
        try {
            pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean getUser(){
        if(login(false)){
            try {
                String query = "SELECT * FROM user WHERE username = '" + this.username + "'";
                PreparedStatement pst = con.prepareStatement(query);
                ResultSet rs = pst.executeQuery();
                
                if(rs.next()){
                    this.setLevel( rs.getInt("level") );
                    this.setName(rs.getString("name"));
                    this.setDateOfBirth(rs.getString("dateOfBirth"));
                    this.setGender(rs.getString("gender"));
                    this.setImageDIR(rs.getString("imageDIR"));
                    return true;
                }else{
                    JOptionPane.showMessageDialog(null, "Unable to get user info.");
                    return false;
                }
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Unable to conect to server");
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return false;
    }
    
    public boolean updateInfo(User user){
        if(user.isUserExists() && this.level >= user.getLevel()){
            try {
                String query =  "UPDATE user SET " +
                        "password = '" + user.getPassword() + "'" +
                        ", name = '" + user.getName() + "'" +
                        ", dateOfBirth = '" + user.getDateOfBirth() + "'" +
                        ", gender = '" + user.getGender() + "'" +
                        ", imageDIR = '" + user.getImageDIR() + "'" +
                        " WHERE username = '" + user.getUsername() + "'";
                PreparedStatement pst = con.prepareStatement(query);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "User " + user.getUsername() + " has been updated.");
                logging("Updated user " + user.getUsername() + " info.");
                return true;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Unable to conect to server");
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }else{
            JOptionPane.showMessageDialog(null, "Your level are lower than the updating user level.");
            return false;
        }
    }
    
    public boolean promote(User user, int toLevel){
        if(user.isUserExists() && this.level > user.getLevel() && this.level > toLevel){
            try {
                String query =  "UPDATE user SET " +
                        " level = " + toLevel +
                        " WHERE username = '" + user.getUsername() + "'";
                PreparedStatement pst = con.prepareStatement(query);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "User " + user.getUsername() + " has been promoted.");
                logging("Promoted user " + user.getUsername() + " to level (" + toLevel + ").");
                return true;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Unable to conect to server");
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }else{
            JOptionPane.showMessageDialog(null, "Your level must be than the user's level & promoting level.");
            return false;
        }
    }
    
    public boolean removeUser(User user){
        if(user.isUserExists() && this.level > user.getLevel()){
            try {
                String query =  "DELETE FROM user WHERE username = '" + user.getUsername() + "'";
                PreparedStatement pst = con.prepareStatement(query);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "User " + user.getUsername() + " has been removed.");
                logging("Removed user " + user.getUsername() + " .");
                return true;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Unable to conect to server");
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }else{
            JOptionPane.showMessageDialog(null, "Your level must be than the deleting user's level.");
            return false;
        }
    }
    public void logging(String log){/*Overriding*/}
}
//    public static void main(String[] args) throws NoSuchAlgorithmException {
//        User ad = new User("Admin", new HashPassword().hashPassword("deptraibodoiqua"));
//        ad.getUser();
//        System.out.println(ad.getLevel());
//        System.out.println(ad.login(true));
//        System.out.println(ad.getImageDIR()); 
//        User nU = new User("SMempl001", "");
//        nU.setName("Fisher Man");
//        nU.setGender("male");
//        nU.updateInfo(nU);
//        ad.updateInfo(nU);
//        ad.promote(nU,2);
//        ad.removeUser(nU);
//        nU.addUser();
//    }   