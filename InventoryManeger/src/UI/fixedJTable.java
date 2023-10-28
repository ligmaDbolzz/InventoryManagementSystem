/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class fixedJTable{
    public static DefaultTableModel fixed(DefaultTableModel model){
        DefaultTableModel res;
        int rows = model.getRowCount();
        int cols = model.getColumnCount();
        
        Object o[][] = new Object[rows][cols];
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                o[row][col] = model.getValueAt(row, col);
            }
        }
        
        Object fiels[] = new Object[cols];
        for(int col = 0; col < cols; col++){
            fiels[col] = model.getColumnName(col);
        }
        
        res = new DefaultTableModel(o,fiels){
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        return res;
    };
}
