/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.SectionDTO;
import Database.ConnectionFactory;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Admin
 */
public class SectionDAO {
    Connection con;
    public SectionDAO(){
        con = new ConnectionFactory().getCon();
    }
    public boolean isSectionExsit(String code){
        String query = "SELECT * FROM section WHERE code = '" + code + "'";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            //if section code already exist;
            if (rs.next()){
               return true; 
            }
        } catch (SQLException ex) {
            Logger.getLogger(SectionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public ArrayList<SectionDTO> allSectionList(){
        ArrayList<SectionDTO> res = new ArrayList<>();
            
        String query = "SELECT * FROM section";
        
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            //Load Sectino info to SectionDTO list
            while(rs.next()){
                SectionDTO se = new SectionDTO(
                                    rs.getString("code"),
                                    rs.getDouble("size"),
                                    rs.getDouble("free"),
                                    rs.getDouble("value"),
                                    rs.getString("imageDIR")
                                    );
                res.add(se);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SectionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    public boolean addSection(SectionDTO section){
        //check if section exist
        if(!isSectionExsit(section.getCode())){
            String query = "INSERT INTO section VALUES(?,?,?,?,?)";
            try {
                PreparedStatement pst = con.prepareStatement(query);
                //Load section ìno to Statement for exceute
                pst.setObject(1, section.getCode());
                pst.setObject(2, section.getSize());
                pst.setObject(3, section.getFree());
                pst.setObject(4, section.getValue());
                pst.setObject(5, section.getImageDIR());
                
                
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "SectionDAO added and ready for store.");
                logging("Added section " + section.getCode() + ".");
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(SectionDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }else{
            JOptionPane.showMessageDialog(null, "Section code already exist.");
            return false;
        }
    }
    
    public boolean deleteSection(SectionDTO section){
        String query = "DELETE FROM section WHERE code = '" + section.getCode() + "'";
        //check if section exists
        if(isSectionExsit(section.getCode())){
            //check if section is empty
            if(Objects.equals(section.getSize(), section.getFree())){
                try {
                    File oo = new File(section.getImageDIR());
                    oo.delete();
                    PreparedStatement pst = con.prepareStatement(query);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Section: " + section.getCode() + " deleted.");
                    logging("Deleted section " + section.getCode() + ".");
                    return true;
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Failed to delete section " + section.getCode() + " .");
                    Logger.getLogger(SectionDAO.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }else{
                JOptionPane.showMessageDialog(null, "ERROR: Section canot be deleted when thereis still product in it.");
                return false;
            }
        }else{
            JOptionPane.showMessageDialog(null, "Section "+ section.getCode() + " does not exists.");
            return false;
        }
    }
    
    public boolean editSection(SectionDTO oldSection, SectionDTO newSection){
        //check if oldSection exists
        if(isSectionExsit(oldSection.getCode())){
            //Check if new Section have already exists
            if(!isSectionExsit(newSection.getCode()) || newSection.getCode().equalsIgnoreCase(oldSection.getCode())){
                //Load new section data to query but keep the already consumed space
                String query =  "UPDATE section SET" + 
                                " code = '" +  newSection.getCode() + "'" + 
                                ", size = " +  newSection.getSize()+
                                ", free = " +  (oldSection.getFree() - oldSection.getSize() + newSection.getSize() ) + 
                               ", value = " +  oldSection.getValue() + 
                               ", imageDIR = '" + newSection.getImageDIR() + "'" +
                            " WHERE code = '" + oldSection.getCode() + "'";
                // check size if new free size > 0
                if(oldSection.getFree() >= (oldSection.getSize() - newSection.getSize())){
                    try {
                        PreparedStatement pst = con.prepareStatement(query);
                        pst.executeUpdate();

                        JOptionPane.showMessageDialog(null, 
                        "Section: " + oldSection.getCode() + " has been edited.");
                        logging("Edited section " + oldSection.getCode() + " to " + newSection.getCode() + ".");
                        return true;
                    } catch (SQLException ex) {
                        Logger.getLogger(SectionDAO.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                    }

                }else{
                    JOptionPane.showMessageDialog(null, 
                    "New Section Size is not sufficient.");
                    return false;
                }
            }else{
                JOptionPane.showMessageDialog(null, 
                "Section: " + newSection.getCode() + " already exists.");
                return false;
            }
        }else{
            JOptionPane.showMessageDialog(null, 
            "Section: " + oldSection.getCode() + " does not exists.");
            return false;
        }
    }
    
    public SectionDTO getSection(String code){
        //get section info
        SectionDTO section = new SectionDTO();
        String query = "SELECT * FROM section WHERE code = '" + code + "'";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            // Load section info to object section
            // it will return an empty section if section does not exists
            if(rs.next()){
                section = new SectionDTO(
                            rs.getString("code"),
                            rs.getDouble("size"),
                            rs.getDouble("free"),
                            rs.getDouble("value"),
                            rs.getString("imageDIR")
                            );
            }else{
                JOptionPane.showMessageDialog(null, "SectionDAO: " + section.getCode() + " does not exist.");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SectionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return section;
    }
    public void logging(String log){/*Overriding*/}
}
//    public static void main(String[] args) {
//        SectionDAO sDAO = new SectionDAO();
//        sDAO.deleteSection(sDAO.getSection("001"));
//        sDAO.addSection(new SectionDTO("001",1000.0));
//        sDAO.editSection(sDAO.getSection("001"),new SectionDTO("003",300.0));
//        for(SectionDTO se : sDAO.allSectionList()){
//            System.out.println(se.getCode());
//        }
//    }