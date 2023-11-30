/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controler;

import DAO.ProductDAO;
import DAO.SectionDAO;
import DTO.ProductDTO;
import DTO.SectionDTO;
import Tools.DirLib;
import User.User;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class MainControler {
    //Model
    private User user;
    private ProductDAO pDAO;
    private SectionDAO sDAO;
    
    //Data
    private ArrayList<SectionDTO> sectList;
    private ArrayList<ProductDTO> prodList;


    public MainControler(User user) {
        this.user = new User(user.getUsername(), user.getPassword()){
            @Override
            public void logging(String log){
                toLog(log);
            }
        }; 
        this.user.getUser();
        this.pDAO = new ProductDAO(){
            @Override
            public void logging(String log){
                toLog(log);
            }
        };
        this.sDAO = new SectionDAO(){
            @Override
            public void logging(String log){
                toLog(log);
            }
        };
        this.prodList = this.pDAO.allProductList();
        this.sectList = this.sDAO.allSectionList();
    }

    public User getUser() {
        return user;
    }

    public ProductDAO getpDAO() {
        return pDAO;
    }

    public SectionDAO getsDAO() {
        return sDAO;
    }
    
    public ArrayList<SectionDTO> getSectList() {
        return sectList;
    }
    
    public ArrayList<ProductDTO> getProdList() {
        return prodList;
    }

    public ArrayList<ProductDTO> searchFor(String code, ArrayList<String> type, String name, String brand, 
                                Double price1, Double price2,
                                Integer quantity1, Integer quantity2,
                                ArrayList<String> location, String yyyy, String mm, String dd){
        ArrayList<ProductDTO> res = new ArrayList<>();
        ArrayList<ProductDTO> tes = this.prodList;
        
        //check if product's code contain code
        for (ProductDTO prod : tes) {
            if(prod.getCode().toLowerCase().contains(code)){
                res.add(prod);
            }
        }
        
        // check for product's type
        //reset res and tes
        tes = res;
        res = new ArrayList<>();
        for (ProductDTO prod : tes) {
            for(String typ :type){
                if(prod.getType().contains(typ)){
                    res.add(prod);
                    break;
                }
            }
        }
        
        //check if product's name contain name
        //reset res and tes
        tes = res;
        res = new ArrayList<>();
        for (ProductDTO prod : tes) {
            if(prod.getName().toLowerCase().contains(name)){
                res.add(prod);
            }
        }
        
        //check if product's brand contain brand
        //reset res and tes
        tes = res;
        res = new ArrayList<>();
        for (ProductDTO prod : tes) {
            if(prod.getBrand().toLowerCase().contains(brand)){
                res.add(prod);
            }
        }
        
        //check if product's price if it between price1 & price2
        //reset res and tes
        tes = res;
        res = new ArrayList<>();
        for (ProductDTO prod : tes) {
            if(prod.getPrice() >= price1 && prod.getPrice() <= price2){
                res.add(prod);
            }
        }
        
        //check if product's quantity if it between quantity1 & quantity2
        //reset res and tes
        tes = res;
        res = new ArrayList<>();
        for (ProductDTO prod : tes) {
            if(prod.getQuantity() >= quantity1 && prod.getQuantity()<= quantity2){
                res.add(prod);
            }
        }
        
        // check for product's location
        //reset res and tes
        tes = res;
        res = new ArrayList<>();
        for (ProductDTO prod : tes) {
            for(String loca : location){
                if(prod.getLocation().contains(loca)){
                    res.add(prod);
                    break;
                }
            }
        }
        
        // check for product's dateIN
        //reset res and tes
        tes = res;
        res = new ArrayList<>();
        for (ProductDTO prod : tes) {
            String d = dd;
            String m = mm;
            String y = yyyy;
            String dat = prod.getDateIN();
            String ddd = dat.substring(0,2);
            String mmm = dat.substring(3,5);
            String yyy = dat.substring(6);
//            System.out.println(d + "/" + m + "/" + y);
//            System.out.println(ddd + "/" + mmm + "/" + yyy);
            if(ddd.contains(d) && mmm.contains(m) && yyy.contains(y)){
                res.add(prod);
            }
        }
        return res;
    }
    
    public ArrayList<ProductDTO> prodListInSection(String code){
        ArrayList<ProductDTO> res = new ArrayList<>();
        ArrayList<ProductDTO> tes = this.prodList;
        
        for(ProductDTO prod : tes){
            if(prod.getLocation().equals(code)){
                res.add(prod);
            }
        }
        
        return res;
    }
    
    
    public boolean moveProduct(ProductDTO prod, String sectionCode, int quantity){
        boolean res = false;
        ProductDTO prod0 = pDAO.getFullProduct(prod);
        
        ProductDTO prod1 = prod0;
        ProductDTO prod2 = prod0;
        
        if(prod0.getQuantity() == quantity && sDAO.isSectionExsit(sectionCode)){
            prod1.setLocation(sectionCode);
            res = pDAO.editProduct(prod0, prod1, false);
        }else if(prod0.getQuantity() > quantity && sDAO.isSectionExsit(sectionCode)){
            
            prod1.setQuantity(prod1.getQuantity() - quantity);
            
            if(pDAO.editProduct(prod0, prod1, false)){
                prod2.setLocation(sectionCode);
                prod2.setQuantity(quantity);
                if(pDAO.addProduct(prod2, false)){
                    res = true;
                }
            }    
            
        }else if(prod0.getQuantity() < quantity  && sDAO.isSectionExsit(sectionCode)){
            JOptionPane.showMessageDialog(null, "Invalid number of product.\nCannot move more than the product's quantity.");
        }else{
            JOptionPane.showMessageDialog(null, "Invalid section code.\n Section " + sectionCode + " does not exists.");
        }
        
        return res;
    }
    
    public void refreshAll(){
        this.user.getUser();
        this.prodList = this.pDAO.allProductList();
        this.sectList = this.sDAO.allSectionList();
    }
    public void refreshSect(){
        this.sectList = this.sDAO.allSectionList();
    }
    
    private void toLog(String log){
        //Write log of action
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter dtm = DateTimeFormatter.ofPattern("dd/MM/yyyy -- HH:mm:ss");
        try {
            String logPath = DirLib.srcPath() + "/Database/log.txt";
            
            File logg = new File(logPath);
            if(!logg.exists()){
                logg.createNewFile();
            }
            try ( //write to file
                    FileWriter fw = new FileWriter(logg,true)) {
                fw.write(ldt.format(dtm) + " | " + this.user.getUsername() + " | " + log + "\n");
                fw.flush();
            }

        } catch (IOException ex) {
            Logger.getLogger(MainControler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String getLog(){
        String res = "";
        try {
            String logPath = DirLib.srcPath() + "/Database/log.txt";
            Scanner fsc = new Scanner(new File(logPath));
            while(fsc.hasNext()){
                res += fsc.nextLine() + "\n";
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Unable to access to logfile");
            Logger.getLogger(MainControler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
}
