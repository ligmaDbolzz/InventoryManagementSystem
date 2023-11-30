package Tools;

import DTO.ProductDTO;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class Converter {
    
    public static ArrayList<String> typeList(){
        ArrayList<String> res = new ArrayList<>();
        
        try {
            //Access to src/DTO folder
            String path = DirLib.srcPath() + "/DTO";
            File DTOFolder = new File(path);
            //Get all file in DTOFolder
            File[] typeList = DTOFolder.listFiles();
            for (File file : typeList) {
                String s = file.getName().replaceAll("DTO.java", "");
                if(!s.toLowerCase().contains("product") && !s.toLowerCase().contains("section")){
                    res.add(s);
                }
            }
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: Unable to reach:/src/DTO/\n\t Please check your file.");
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    public static ArrayList<Object> productFieldName(ProductDTO product){
        //List of info of product's field name;
        ArrayList<Object> res = new ArrayList<>();
        // if product is a child add super's field
        if(!product.getClass().equals(ProductDTO.class)){
            for (Field field : ProductDTO.class.getDeclaredFields()) {
                res.add(field.getName());
            }
        }
        // add product field
        for (Field field : product.getClass().getDeclaredFields()) {
            res.add(field.getName());
        }
        
        return res;
    }
    
    public static int NumOfBasicField(){
        return ProductDTO.class.getDeclaredFields().length;
    }
    
    public static int NumOfChildField(ProductDTO product){
        return product.getClass().getDeclaredFields().length;
    }
    
    public static ArrayList<Object> productToData(ProductDTO product){
        //List of info of product;
        ArrayList<Object> res = new ArrayList<>();
        //List of product get method
        ArrayList<Method> methList = new ArrayList<>();
        
        //geting method of product in order of field;
        ArrayList<Method> allGetMethod = new ArrayList<>();
        for (Method method : product.getClass().getMethods()){
            if( method.getName().toLowerCase().contains("get") ){
                allGetMethod.add(method);
            }
        }
        
        for (Object field : Converter.productFieldName(product)){
            for (Method method : allGetMethod) {
                
                String fieldName = field.toString().toLowerCase();
                String methoName = method.getName().toLowerCase();
                
                if( methoName.contains(fieldName) && ( methoName.length() - 3 == fieldName.length() ) ){
                    methList.add(method);
                    break;
                }
            }
        }
        
        //invoke get method to get all product info into a list
        for (Method method : methList) {
            try {
                res.add(method.invoke(product));
            } catch (IllegalAccessException | InvocationTargetException ex) {
                JOptionPane.showMessageDialog(null, "Translate Error check code of Translator, Product's child get/set method or Productinfo");
                Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return res;
    }
    
    public static ProductDTO dataToProduct(ArrayList<Object> data){
        ProductDTO res = new ProductDTO();
        //Check data lenght
        if(data.size() < Converter.NumOfBasicField()){
            JOptionPane.showMessageDialog(null, "ERROR:  Wrong info format.\n Please check your input of product info");
        }
        
        
        //Get Class File Name
        String className = data.get(1).toString().toLowerCase();
        className = className.toUpperCase().charAt(0) + className.substring(1) + "DTO";
        String classPath = "DTO." + className;
        
        //Get class lib URL
        String classLib = "";
        try {
            classLib = DirLib.clsPath();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: Can not find classes folder of project");
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
            return res;
        }
        
        //Load product Class
        Class type = null;
        try {
            File clsFolder = new File(classLib);
            URL url = clsFolder.toURI().toURL();
            URLClassLoader ucl = new URLClassLoader(new URL[]{url});
            
            type = ucl.loadClass(classPath);
            
        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: classes folder URL canot be create");
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: Can not find class:" + className + "\nAt: " + classPath);
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Create an dataa intance of the class and load all setMethod to a List
        ArrayList<Method> setMethod = new ArrayList<>();
        try {
            ProductDTO emptyProd = (ProductDTO) type.newInstance();
            
            //get all set Method of the class
            ArrayList<Method> allSetMethod = new ArrayList<>();
            for (Method method : emptyProd.getClass().getMethods()){
                if(method.getName().toLowerCase().contains("set")){
                    allSetMethod.add(method);
                }
            }
            //get field name inorder
            ArrayList<Object> fieldList = Converter.productFieldName(emptyProd);
            //Sort setmethod to field name order
            for (Object field : fieldList){
                for (Method method : allSetMethod){
                    String fieldName = field.toString().toLowerCase();
                    String methoName = method.getName().toLowerCase();
                    if( methoName.contains(fieldName) && (methoName.length()-3 == fieldName.length()) ){
                        setMethod.add(method);
                        break;
                    }
                }
            }
            res = emptyProd;
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Set product info
        int szn = data.size();
        int szm = setMethod.size();
        if(szn <= szm){
            for(int index = 0; index < szn; index++){
                try {
                    Object dataa = data.get(index);
                    String para = setMethod.get(index).getParameterTypes()[0].getName();
                    
                    if(para.toLowerCase().contains("int")){
                        if(dataa != null){
                            dataa = Integer.parseInt(dataa.toString());
                        }else{
                            dataa = 0;
                        }
                    }else if(para.toLowerCase().contains("float") || para.toLowerCase().contains("double")){
                        if(dataa != null){
                            dataa = Double.parseDouble(dataa.toString());
                        }else{
                            dataa = 0.0;
                        }
                    }else{
                        if(dataa != null){
                            dataa = dataa.toString();
                        }else{
                            dataa = "";
                        }
                    }
                    
                    setMethod.get(index).invoke(res, dataa);
                } catch (IllegalAccessException | InvocationTargetException ex) {
                    JOptionPane.showMessageDialog(null,"Wrong parameter type for set product info");
                    Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if( szn < szm){
                for(int index = szn; index < szm; index ++){
                    try {
                        Object empty;
                        String para = setMethod.get(index).getParameterTypes()[0].getName();
                        if(para.toLowerCase().contains("string")){
                            empty = "";
                        }else if(para.toLowerCase().contains("double")){
                            empty = 0.0;
                        }else {
                            empty = 0;
                        }
                        
                        setMethod.get(index).invoke(res, empty);
                    } catch (IllegalAccessException | InvocationTargetException ex) {
                        JOptionPane.showMessageDialog(null,"Wrong parameter type for set product info");
                        Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }else {
            JOptionPane.showMessageDialog(null,"Data size is not correct to class ( Bigger )");
        }
        
        return res;
    }
}