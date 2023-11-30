/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.ProductDTO;
import Database.ConnectionFactory;
import Tools.Converter;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Admin
 */
public class ProductDAO {
    Connection con;

    public ProductDAO() {
        con = new ConnectionFactory().getCon();
    }
    
    public boolean isProductExists(String code){
        String query = "SELECT * FROM product WHERE code = '" +code + "'";
        //check if product exist
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ArrayList<ProductDTO> allProductList(){
        ArrayList<ProductDTO> res = new ArrayList<>();
        String query = "SELECT * FROM product";
        
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            // load all product info to res
            while(rs.next()){
                ProductDTO prod = new ProductDTO(
                    rs.getString("code"),
                    rs.getString("type"), 
                    rs.getString("name"), 
                    rs.getString("brand"), 
                    rs.getDouble("size"), 
                    rs.getDouble("price"), 
                    rs.getInt("quantity"), 
                    rs.getString("location"), 
                    rs.getString("dateIN"),
                    rs.getString("imageDIR"));
                res.add(prod);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    public ProductDTO getFullProduct(ProductDTO prod){
        ProductDTO res = Converter.dataToProduct(Converter.productToData(prod));
        ArrayList<Object> data = new ArrayList<>();
        if(isProductExists(res.getCode())){
            String query1 = "SELECT * FROM product WHERE code = '" + res.getCode() + "'";
            String query2 = "SELECT * FROM " + res.getType() + " WHERE code = '" + res.getCode() + "'";
            try {
                PreparedStatement pst = con.prepareStatement(query1);
                ResultSet rs = pst.executeQuery();
                // get product info from product table
                int prodN = Converter.NumOfBasicField();
                if(rs.next()){
                    for(int i = 1; i <= prodN ; i++){
                        data.add(rs.getObject(i));
                    }
                }
                pst = con.prepareStatement(query2);
                rs = pst.executeQuery();
                // get product info from child table
                int childN = Converter.NumOfChildField(res);
                if(rs.next()){
                    for(int i = 2; i <= childN+1 ; i++){
                        data.add(rs.getObject(i));
                    }
                }
                res = Converter.dataToProduct(data);
            } catch (SQLException ex) {
                Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, " ERROR: Product " + res.getCode() + " does not exists.");
        }
        return res;
    }
    
    public boolean addProduct(ProductDTO product, boolean msg){
        //check if product exist
        if(!isProductExists(product.getCode())){
            String query1 = "INSERT INTO product VALUES(?,?,?,?,?,?,?,?,?,?)";
            String query2 = "INSERT INTO " + product.getType() + " VALUES(";
            int superN = Converter.NumOfBasicField();
            int childN = Converter.NumOfChildField(product);
            //Prepare query for child table
            for(int i = 0; i < childN; i++){
                query2 += "?,";
            }   query2 += "?)";
            
            ArrayList<Object> data = Converter.productToData(product);
            try {
                PreparedStatement pst = con.prepareStatement(query1);
                //load data for product table
                for(int i = 1; i <= superN; i++){
                    pst.setObject(i, data.get(i-1));
                }
                pst.executeUpdate();
                
                pst = con.prepareStatement(query2);
                //load data for child table
                pst.setObject(1, data.get(0));
                for(int i = 2; i <= childN + 1; i++){
                    pst.setObject(i, data.get(superN + i - 2));
                }
                pst.executeUpdate();
                
                if(msg){
                    JOptionPane.showMessageDialog(null, "Product " + product.getCode() +" added.");
                    logging("Addeded product " + product.getCode() + ".");
                }
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            
        }else{
            if(msg)JOptionPane.showMessageDialog(null, "Product " + product.getCode() +" already exist.");
            return false;
        }
    }
    
    public boolean deleteProduct(ProductDTO product, boolean msg){
        //check if product exist
        if(isProductExists(product.getCode())){
            String query1 = "DELETE FROM product WHERE code = '" + product.getCode() + "'";
            String query2 = "DELETE FROM " + product.getType() + " WHERE code = '" + product.getCode() + "'";
            
            try {
                File noo = new File(product.getImageDIR());
                noo.delete();
                PreparedStatement pst = con.prepareStatement(query1);
                pst.executeUpdate();
                pst = con.prepareStatement(query2);
                pst.executeUpdate();
                
                if(msg){
                    JOptionPane.showMessageDialog(null, "Product " + product.getCode() +" deleted.");
                    logging("Deleted product " + product.getCode() + ".");
                }
                return true;
            } catch (SQLException ex) {
                if(msg)JOptionPane.showMessageDialog(null, "Failed to delete product " + product.getCode() + ".");
                Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }else{
            if(msg)JOptionPane.showMessageDialog(null, "Product " + product.getCode() +" does not exist.");
            return false;
        }
    }
    
    public boolean editProduct(ProductDTO oldProd, ProductDTO newProd, boolean msg){
        //check if product exist
        // delete old product
        // insert new product
        if(isProductExists(oldProd.getCode()) && deleteProduct(oldProd,false) && addProduct(newProd,false)){
            if(msg){
                JOptionPane.showMessageDialog(null, "Product " + oldProd.getCode() +" edited.");
                logging("Edited product " + oldProd.getCode() + " to " + newProd.getCode() + ".");
            }
            return true;
        }else{
            if(msg) JOptionPane.showMessageDialog(null, "Product " + oldProd.getCode() +" does not exist.");
            return false;
        }
        
    }
    
    public void logging(String log){/*Overriding*/}
}
//    public static void main(String[] args) {
//        ProductDAO pDAO = new ProductDAO();
//        
//        ProductDTO p =  new ProductDTO(
//                        "007", 
//                        "laptop", 
//                        "razor", 
//                        "ASUS", 
//                        15.4, 
//                        16.2, 
//                        8, 
//                        "001", 
//                        "", 
//                        "");
////        System.out.println(Converter.productToData(p));
//        ProductDTO z = Converter.dataToProduct(Converter.productToData(p));
////        System.out.println(Converter.productToData(z));
//        
//        pDAO.addProduct(z,true);
////        System.out.println(Converter.productToData(pDAO.getFullProduct(z)));
////        for(ProductDTO prod : pDAO.allProductList()){
////            System.out.println(prod.getCode());
////        }
//        
//        ProductDTO p2 =  new ProductDTO(
//                        "008", 
//                        "laptop", 
//                        "razo", 
//                        "ASU", 
//                        15.4, 
//                        16.2, 
//                        8, 
//                        "001", 
//                        "", 
//                        "");
//        ProductDTO z2 = Converter.dataToProduct(Converter.productToData(p2));
//        pDAO.editProduct(z, z2,true);
//        pDAO.deleteProduct(p2,true);
////        
//        for(ProductDTO prod : pDAO.allProductList()){
//            System.out.println(prod.getCode());
//        }
//    }