package DAO;

import Database.ConnectionFactory;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;
import DTO.ProductDTO;
import javax.swing.JOptionPane;

public class ProductDAO {
    Connection con;
    PreparedStatement pst;
    ResultSet resultSet;
    
    public ProductDAO(){
        con = new ConnectionFactory().getCon();
    }

    public DefaultTableModel listOfProduct() throws SQLException {
        String query = "SELECT * FROM product";
        return buildTableModel(query);
    }

    //String option = (String) jComboBox.getSelectedItem()
    public DefaultTableModel sortProductBy(String option) throws SQLException {
        String query = "SELECT * FROM product ORDER BY " + option;
        return buildTableModel(query);
    }
    
    //add new product;
    public void addProductDAO(ProductDTO productDTO) {
        try {
            String query = "SELECT * FROM product WHERE code='"
                    + productDTO.getCode()
                    + "'";
            resultSet = pst.executeQuery(query);
            //if the product code already exist;
            if (resultSet.next())
                JOptionPane.showMessageDialog(null, "The code of ProductDAO has already been added.");
            else
                addFunction(productDTO);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   
    public void addFunction(ProductDTO productDTO) {
        try {
            String query = "INSERT INTO product VALUES(?,?,?,?,?,?,?)";
            pst = con.prepareStatement(query);
            pst.setString(1, productDTO.getCode());
            pst.setString(2, productDTO.getName());
            pst.setString(3, productDTO.getBrand());
            pst.setInt(4, productDTO.getSpace());
            pst.setInt(5, productDTO.getQuantity());
            pst.setInt(6, productDTO.getPrice());
            pst.setString(7, productDTO.getAddress());

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "ProductDAO added and ready for sale.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    public void editProductDao(ProductDTO productDTO, String code){
        try {
            String query = "SELECT * FROM product WHERE code='"
                    + productDTO.getCode() + "'";
            resultSet = pst.executeQuery(query);
            //if the product code already exist;
            if (!resultSet.next())
                JOptionPane.showMessageDialog(null, "The code of ProductDAO is not found.");
            else
                editFunction(productDTO);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void editFunction(ProductDTO productDTO) {
        try {
            String query = "UPDATE product "
                + "SET name = ? "
                + "SET brand = ? "
                + "SET space = ? "
                + "SET quantity = ? "
                + "SET price = ? "
                + "SET location = ? "
                + "WHERE code = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, productDTO.getName());
            pst.setString(2, productDTO.getBrand());
            pst.setInt(3, productDTO.getSpace());
            pst.setInt(4, productDTO.getQuantity());
            pst.setInt(6, productDTO.getPrice());
            pst.setString(6, productDTO.getAddress());
            pst.setString(7, productDTO.getCode());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "ProductDAO was edited and ready for sale.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
            
    public Vector<Object> searchByCode(String code) throws SQLException{
        String query = "SELECT * FROM product WHERE code = '" + code +"'";
        Vector<Object> data = new Vector<Object>();
        
        pst = con.prepareStatement(query);
        resultSet = pst.executeQuery();
        
        ResultSetMetaData RSMD = resultSet.getMetaData();
        int colCount = RSMD.getColumnCount();
        
        Vector<Object> vector = new Vector<Object>();
        while(resultSet.next()){
            for (int col=1; col<=colCount; col++) {
                vector.add(resultSet.getObject(col));
            }
        }
        return vector;
    }
    
    public DefaultTableModel buildTableModel(String query) throws SQLException{
        DefaultTableModel DTM = null;
        try{
            //prepare statement (sql query);
            pst = con.prepareStatement(query);
            ResultSet resultSet = pst.executeQuery();

            ResultSetMetaData RSMD = resultSet.getMetaData();
            Vector<String> columnNames = new Vector<String>();
            int colCount = RSMD.getColumnCount();

            for (int col=1; col <= colCount; col++){
                columnNames.add(RSMD.getColumnName(col));
            }

            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (resultSet.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int col=1; col<=colCount; col++) {
                    vector.add(resultSet.getObject(col));
                }
                data.add(vector);
            }
            
            DTM = new DefaultTableModel(data, columnNames);
        }catch(Exception e){}
        return DTM;
    }
}
