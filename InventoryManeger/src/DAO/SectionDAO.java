package DAO;


//import asd.Product;
import Database.ConnectionFactory;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class SectionDAO {
    Connection con;
    PreparedStatement pst;
    
    public SectionDAO(){
        con = new ConnectionFactory().getCon();
    }

    public DefaultTableModel listOfSection() throws SQLException {
        String query = "SELECT * FROM section";
        return buildTableModel(query);
    }
    //String option = (String) jComboBox.getSelectedItem()
    public DefaultTableModel sortProductBy(String option) throws SQLException {
        String query = "SELECT * FROM section ORDER BY " + option;
        return buildTableModel(query);
    }
    
    public Vector<Object> searchByCode(String code) throws SQLException{
        String query = "SELECT * FROM section WHERE code = '" + code +"'";
        Vector<Object> data = new Vector<Object>();
        
        pst = con.prepareStatement(query);
        ResultSet resultSet = pst.executeQuery();
        
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