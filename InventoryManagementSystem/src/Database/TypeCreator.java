/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import DTO.ProductDTO;
import Tools.DirLib;
import Tools.Pair;
import Tools.Converter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
//New Product Creator v2.0 
public class TypeCreator {
    private String clsName;
    private String header;
    private String data;
    private String tData;
    
    //Create file header and name and package;
    public TypeCreator(String clsName) {
        this.clsName = clsName.toUpperCase().charAt(0) + clsName.toLowerCase().substring(1) + "DTO";
        this.header =   "package DTO;\n\n" + 
                        "public class " + this.clsName +  " extends " + ProductDTO.class.getName().replaceAll("DTO.", "") + " {\n";
        this.data = "}";
    }
    
    public void setData(ArrayList<Pair> fieldList){
        String fields = "";
        String getset = "";
        String tField = "";
        HashSet<String> fieldd = new HashSet<>();
        ArrayList<Object> baseField = Converter.productFieldName(new ProductDTO());
        for(Pair pair : fieldList){
            String typ = pair.getKey().toString().trim();
            String nem = pair.getValue().toString().trim();
            if (typ.equals("String") || typ.equals("Integer") || typ.equals("int")  || typ.equals("Double")){
                //if field's name does not match with ProductDTO field's name and it's other field's name, start with an alpabelt charater, does not contain any special character.
                if ( !fieldd.contains(nem.toLowerCase()) && !baseField.contains(nem) && nem.matches("^[a-zA-Z].*") && nem.matches("^[a-zA-Z0-9]+$")){
                    fieldd.add(nem.toLowerCase());
                    fields += "\tprivate " + typ + " " + nem + ";\n";

                    getset += "\tpublic " + typ + " get" + nem.toUpperCase().charAt(0) + nem.substring(1) + "(){\n";
                    getset += "\t\treturn this." + nem + ";\n\t}\n\n";

                    getset += "\tpublic void set" + nem.toUpperCase().charAt(0) + nem.substring(1) + "(" + typ + " " + nem + "){\n";
                    getset += "\t\tthis." + nem + " = " + nem + ";\n\t}\n\n";
                    
                    tField += nem + " ";
                    
                    switch (typ) {
                        case "int":
                            tField += "int";
                            break;
                        case "Integer":
                            tField += "int";
                            break;
                        case "Double":
                            tField += "float";
                            break;
                        default:
                            tField += "varchar(50)";
                            break;
                    }
                    
                    tField += ",\n";
                    
                }else{
                    fields = "";
                    getset = "";
                    JOptionPane.showMessageDialog(null, "ERROR: Invalid name for new class field\n Field's name must be distinct, start with a character and not contain any special character.");
                    break;
                }
            }else{
                fields = "";
                getset = "";
                JOptionPane.showMessageDialog(null, "ERROR: Invalid type for new class field");
                break;
            }
        }
        this.data = fields + 
                    "\n\tpublic " + this.clsName + " (){\n\t}\n\n"
                    + getset + "}";
        this.tData = tField;
    }
    public void deleteType() throws IOException, SQLException{
        String query1 = "SELECT * FROM " + this.clsName.replaceAll("DTO", "").toLowerCase();
        PreparedStatement pst1 = new ConnectionFactory().getCon().prepareStatement(query1);
        ResultSet rs = pst1.executeQuery();
        if(!rs.next()){
            String path = DirLib.srcPath() + "/DTO/" + this.clsName + ".java";
            File fileToDelete = new File(path);
            boolean donee = fileToDelete.delete();

            String query = "DROP TABLE IF EXISTS " + 
            this.clsName.replaceAll("DTO", "").toLowerCase();
            try {
                PreparedStatement pst = new ConnectionFactory().getCon().prepareStatement(query);
                pst.executeUpdate();
            } catch (SQLException ex) {
                donee = false;
                Logger.getLogger(TypeCreator.class.getName()).log(Level.SEVERE, null, ex);
            }

            if(donee){
                JOptionPane.showMessageDialog(null, "Delete success : " + path);
            }else{
                JOptionPane.showMessageDialog(null, "Failed to delete the type.");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Can not delete the type if there is still product of this type.");
        }
    }
    public void createType() throws IOException{
        String alll = header + data;
        String path = DirLib.srcPath() + "/DTO/" + this.clsName + ".java";
        
        if(!Converter.typeList().contains(this.clsName)){
            FileWriter fw = new FileWriter(path, true);
            fw.write(alll);
            fw.flush();
            fw.close();

            String query  = "CREATE TABLE IF NOT EXISTS " + this.clsName.replaceAll("DTO", "").toLowerCase() + " (\n";
            query +=        "code varchar(50),\n";
            query +=        this.tData;
            query +=        "PRIMARY KEY (code)\n";
            query +=        ");\n";

            PreparedStatement pst;
            try {
                pst = new ConnectionFactory().getCon().prepareStatement(query);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, 
                "New type : " +  this.clsName + " created successfuly");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, 
                "Failed to create : " +  this.clsName + ".");
                Logger.getLogger(TypeCreator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, 
                "Failed to create , " +  this.clsName + " already exists");
        }
    }
    
//    public static void main(String[] args) throws IOException {
//        TypeCreator pcZ = new TypeCreator("Laptop");
//        ArrayList<Pair> arr = new ArrayList<>();
//        arr.add(new Pair("String", "core"));
//        arr.add(new Pair("Integer", "ram"));
//        arr.add(new Pair("Double", "hd"));
//        pcZ.setData(arr);
//        pcZ.createType();
//        pcZ.deleteType();
//    }
}