/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.Base;

import Controler.MainControler;
import DTO.NallDTO;
import DTO.ProductDTO;
import DTO.SectionDTO;
import Tools.Converter;
import UI.Popup.ProductInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class ProductListPanel extends javax.swing.JPanel {
    private MainControler mc;
    private ArrayList<String> selectedType;
    private ArrayList<String> selectedSect;
    private ArrayList<ProductDTO> displayingProd;
    private ArrayList<ProductDTO> holdingProd;
    
    /**
     * Creates new form SearchBar
     * @param mc
     */
    public ProductListPanel(MainControler mc) {
        initComponents();
        this.mc = mc;
        refresh();
    }
    
    //All control Method
    
    
    
    //Event Method
    private void refresh(){
        this.mc.refreshAll();
        this.displayingProd = mc.getProdList();
        prodCodeTxt.setText("");
        resetTypeCB();
        prodNameTxt.setText("");
        prodBrandTxt.setText("");
        resetSectCB();
        priceCB.setSelectedIndex(0);
        quantityCB.setSelectedIndex(0);
        yearCB.setSelectedIndex(0);
        setTable();
    }
    private void resetTypeCB(){
        //prepare type list
        this.selectedType = new ArrayList<>();
        typePM.removeAll();
        
        JCheckBoxMenuItem al = new JCheckBoxMenuItem("All");
        al.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                typeChosed.setText("All");
                resetTypeCB();
            }
        });
        typePM.add(al);
        for(String typ : Converter.typeList()){
            JCheckBoxMenuItem cbb = new JCheckBoxMenuItem(typ);
            cbb.addActionListener(new OpenAction(typePM, typeChosed));
            cbb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!selectedType.contains(typ)){
                        selectedType.add(typ);
                        if(selectedType.size() == 1){
                            typeChosed.setText(typ);
                        }else if(selectedType.size() > 1){
                            typeChosed.setText("Multi type");
                        }
                    }else{
                        selectedType.remove(typ);
                        if(selectedType.isEmpty()){
                            typeChosed.setText("All");
                        }else if(selectedType.size() == 1){
                            typeChosed.setText(selectedType.get(0));
                        }
                    }
                }
            });
            typePM.add(cbb);
        }
    }
    
    private void resetSectCB(){
        //prepare section list
        this.selectedSect = new ArrayList<>();
        sectPM.removeAll();
        
        JCheckBoxMenuItem al = new JCheckBoxMenuItem("All");
        al.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sectionChosed.setText("All");
                resetSectCB();
            }
        });
        sectPM.add(al);
        for(SectionDTO sec : this.mc.getSectList()){
            String ces = sec.getCode();
            JCheckBoxMenuItem cbb = new JCheckBoxMenuItem(ces);
            cbb.addActionListener(new OpenAction(sectPM, sectionChosed));
            cbb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!selectedSect.contains(ces)){
                        selectedSect.add(ces);
                        if(selectedSect.size() == 1){
                            sectionChosed.setText(ces);
                        }else if(selectedSect.size() > 1){
                            sectionChosed.setText("Multi section");
                        }
                    }else{
                        selectedSect.remove(ces);
                        if(selectedSect.isEmpty()){
                            sectionChosed.setText("All");
                        }else if(selectedSect.size() == 1){
                            sectionChosed.setText(selectedSect.get(0));
                        }
                    }
                }
            });
            sectPM.add(cbb);
        }
    }
    
    private void search(){
        String scode = prodCodeTxt.getText();
        ArrayList<String> stype = this.selectedType;
        if(stype.size() == 0){
            stype = Converter.typeList();
        }
        String sname = prodNameTxt.getText().toLowerCase();
        String sbran = prodBrandTxt.getText().toLowerCase();
        Double spri1 = 0.0, spri2 = 0.0;
        switch ( priceCB.getSelectedIndex() ) {
            case 0:
                spri2 = Double.MAX_VALUE;
                break;
            case 1:
                spri1 = Double.parseDouble(topPriceTxt.getText());
                spri2 = Double.MAX_VALUE;
                break;
            case 2:
                spri2 = Double.parseDouble(topPriceTxt.getText());
                break;
            case 3:
                spri1 = Double.parseDouble(topPriceTxt.getText());
                spri2 = Double.parseDouble(botPriceTxt.getText());
                break;
            default:
                throw new AssertionError();
        }
        Integer squa1 = 0, squa2 = 0;
        switch ( quantityCB.getSelectedIndex() ) {
            case 0:
                squa2 = Integer.MAX_VALUE;
                break;
            case 1:
                squa1 = Integer.parseInt(topQuantityTxt.getText());
                squa2 = Integer.MAX_VALUE;
                break;
            case 2:
                squa2 = Integer.parseInt(topQuantityTxt.getText());
                break;
            case 3:
                squa1 = Integer.parseInt(topQuantityTxt.getText());
                squa2 = Integer.parseInt(botQuantityTxt.getText());
                break;
            default:
                throw new AssertionError();
        }
        ArrayList<String> sloca = this.selectedSect;
        if(sloca.size() == 0){
            sloca = new ArrayList<>();
            for(SectionDTO sec : mc.getSectList()){
                sloca.add(sec.getCode());
            }
        }
        String syear = yearCB.getSelectedItem().toString();
        if(syear.equals("yyyy")){
            syear = "";
        }
        String smont = monthCB.getSelectedItem().toString();
        if(smont.equals("mm")){
            smont = "";
        }
        String sdayy = dayCB.getSelectedItem().toString();
        if(sdayy.equals("dd")){
            sdayy = "";
        }
        
        this.displayingProd = mc.searchFor(scode, 
                            stype, 
                            sname, 
                            sbran, 
                            spri1, spri2, 
                            squa1, squa2, 
                            sloca,
                            syear, smont, sdayy);
        setTable();
    }
    
    private void setTable(){
        Vector<Vector<Object>> allProd = new Vector<>();
        for(ProductDTO pro : this.displayingProd){
            ArrayList<Object> data = Converter.productToData(pro);
            Vector<Object> v = new Vector<>();
            v.add(false);
            Collections.addAll(v, data.subList(0, 9).toArray());
            allProd.add(v);
        }
        
        DefaultTableModel dtm = (DefaultTableModel) displayTable.getModel();
        dtm.setRowCount(0);
        for(Vector<Object> v : allProd){
            dtm.addRow(v.toArray());
        }
        displayTable.setVisible(false);
        displayTable.setVisible(true);
    }
    
    private static class OpenAction implements ActionListener {

        private JPopupMenu menu;
        private JComponent pos;

        private OpenAction(JPopupMenu menu, JComponent pos) {
            this.menu = menu;
            this.pos = pos;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            menu.show(pos, 0, pos.getHeight());
        }
    }
    
    private void addNewProduct(){
        NallDTO newP = new NallDTO();
        newP.setType("Nall");
        new ProductInfo(mc, newP, 0).setVisible(true);
    }
    
    private void sortBy(){
        int c1 = sortByCB.getSelectedIndex();
        if(c1 != 0){
            this.holdingProd = this.displayingProd;
            Collections.sort(displayingProd, new Comparator<ProductDTO>() {
                @Override
                public int compare(ProductDTO o1, ProductDTO o2) {
                    return o1.compareWith(c1, o2);
                }
            });
            setTable();
        }else{
            this.displayingProd = this.holdingProd;
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        typePM = new javax.swing.JPopupMenu();
        sectPM = new javax.swing.JPopupMenu();
        productScrollPane = new javax.swing.JScrollPane();
        displayTable = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        srcP1 = new javax.swing.JPanel();
        prodBrandLb = new javax.swing.JLabel();
        prodBrandTxt = new javax.swing.JTextField();
        typeMoreBut = new javax.swing.JButton();
        sectionLabel = new javax.swing.JLabel();
        typeLb = new javax.swing.JLabel();
        prodCodeLb = new javax.swing.JLabel();
        prodCodeTxt = new javax.swing.JTextField();
        prodNameLb = new javax.swing.JLabel();
        prodNameTxt = new javax.swing.JTextField();
        sectMoreBut = new javax.swing.JButton();
        typeChosed = new javax.swing.JLabel();
        sectionChosed = new javax.swing.JLabel();
        srcP2 = new javax.swing.JPanel();
        quantityLb = new javax.swing.JLabel();
        quantityCB = new javax.swing.JComboBox<>();
        priceLb = new javax.swing.JLabel();
        priceCB = new javax.swing.JComboBox<>();
        srcP5 = new javax.swing.JPanel();
        dateLb = new javax.swing.JLabel();
        yearCB = new javax.swing.JComboBox<>();
        monthCB = new javax.swing.JComboBox<>();
        dayCB = new javax.swing.JComboBox<>();
        ctrlP = new javax.swing.JPanel();
        refreshButton = new javax.swing.JButton();
        sortByCB = new javax.swing.JComboBox<>();
        sortByLB = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        newBt = new javax.swing.JButton();
        moreInfoBut = new javax.swing.JButton();
        removeBt = new javax.swing.JButton();
        searchBt = new javax.swing.JButton();
        updownBut = new javax.swing.JToggleButton();
        srcP6 = new javax.swing.JPanel();
        srcP3 = new javax.swing.JPanel();
        topPriceTxt = new javax.swing.JTextField();
        topPriceLabel = new javax.swing.JLabel();
        botPriceTxt = new javax.swing.JTextField();
        botPriceLabel = new javax.swing.JLabel();
        srcP4 = new javax.swing.JPanel();
        topQuantityTxt = new javax.swing.JTextField();
        topQuantityLabel = new javax.swing.JLabel();
        botQuantityTxt = new javax.swing.JTextField();
        botQuantityLabel = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(1052, 168));
        setPreferredSize(new java.awt.Dimension(1160, 600));

        productScrollPane.setBackground(new java.awt.Color(255, 153, 255));
        productScrollPane.setForeground(new java.awt.Color(51, 51, 51));
        productScrollPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        productScrollPane.setEnabled(false);

        displayTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "", "Code", "Type", "Name", "Brand", "Size", "Price", "Quantity", "Location", "Date In"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        displayTable.getTableHeader().setReorderingAllowed(false);
        productScrollPane.setViewportView(displayTable);
        if (displayTable.getColumnModel().getColumnCount() > 0) {
            displayTable.getColumnModel().getColumn(0).setPreferredWidth(3);
            displayTable.getColumnModel().getColumn(3).setPreferredWidth(300);
            displayTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        }

        prodBrandLb.setText("Product's Brand");

        typeMoreBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeMoreButActionPerformed(evt);
            }
        });

        sectionLabel.setText("Section");

        typeLb.setText("Type");

        prodCodeLb.setText("Product Code");

        prodNameLb.setText("Product Name");

        sectMoreBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sectMoreButActionPerformed(evt);
            }
        });

        typeChosed.setText("All");

        sectionChosed.setText("All");

        javax.swing.GroupLayout srcP1Layout = new javax.swing.GroupLayout(srcP1);
        srcP1.setLayout(srcP1Layout);
        srcP1Layout.setHorizontalGroup(
            srcP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, srcP1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(srcP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(prodBrandLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(prodCodeLb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(typeLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(prodNameLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sectionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(srcP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(srcP1Layout.createSequentialGroup()
                        .addComponent(sectionChosed, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sectMoreBut, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(srcP1Layout.createSequentialGroup()
                        .addComponent(typeChosed, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(typeMoreBut, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(prodNameTxt)
                    .addComponent(prodCodeTxt)
                    .addComponent(prodBrandTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        srcP1Layout.setVerticalGroup(
            srcP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(srcP1Layout.createSequentialGroup()
                .addGroup(srcP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prodCodeLb)
                    .addComponent(prodCodeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(srcP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(typeLb)
                    .addComponent(typeMoreBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(typeChosed, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(srcP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prodNameLb)
                    .addComponent(prodNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(srcP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prodBrandLb)
                    .addComponent(prodBrandTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(srcP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sectMoreBut, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(srcP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sectionChosed)
                        .addComponent(sectionLabel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        quantityLb.setText("Quantity");

        quantityCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Upper", "Lower", "From To" }));
        quantityCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quantityCBActionPerformed(evt);
            }
        });

        priceLb.setText("Price");

        priceCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Upper", "Lower", "From To" }));
        priceCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priceCBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout srcP2Layout = new javax.swing.GroupLayout(srcP2);
        srcP2.setLayout(srcP2Layout);
        srcP2Layout.setHorizontalGroup(
            srcP2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(srcP2Layout.createSequentialGroup()
                .addComponent(priceLb, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(priceCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(srcP2Layout.createSequentialGroup()
                .addComponent(quantityLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(quantityCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        srcP2Layout.setVerticalGroup(
            srcP2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(srcP2Layout.createSequentialGroup()
                .addGroup(srcP2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(priceLb)
                    .addComponent(priceCB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(srcP2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quantityLb)
                    .addComponent(quantityCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        dateLb.setText("Date In");

        yearCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "yyyy", "2022", "2023", "2024" }));
        yearCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearCBActionPerformed(evt);
            }
        });

        monthCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "mm", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        monthCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthCBActionPerformed(evt);
            }
        });

        dayCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "dd", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        javax.swing.GroupLayout srcP5Layout = new javax.swing.GroupLayout(srcP5);
        srcP5.setLayout(srcP5Layout);
        srcP5Layout.setHorizontalGroup(
            srcP5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(srcP5Layout.createSequentialGroup()
                .addComponent(dateLb, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(yearCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(monthCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dayCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 134, Short.MAX_VALUE))
        );
        srcP5Layout.setVerticalGroup(
            srcP5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(srcP5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(yearCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(monthCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(dayCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(dateLb)
        );

        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        sortByCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Code", "Type", "Name", "Brand", "Size", "Price", "Quantity", "Location", "Date In" }));
        sortByCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortByCBActionPerformed(evt);
            }
        });

        sortByLB.setText("Sort By");

        newBt.setText("Add New  Product");
        newBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBtActionPerformed(evt);
            }
        });

        moreInfoBut.setText("More");
        moreInfoBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moreInfoButActionPerformed(evt);
            }
        });

        removeBt.setText("Remove");
        removeBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBtActionPerformed(evt);
            }
        });

        searchBt.setText("Search");
        searchBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtActionPerformed(evt);
            }
        });

        updownBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updownButActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ctrlPLayout = new javax.swing.GroupLayout(ctrlP);
        ctrlP.setLayout(ctrlPLayout);
        ctrlPLayout.setHorizontalGroup(
            ctrlPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ctrlPLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(ctrlPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ctrlPLayout.createSequentialGroup()
                        .addComponent(searchBt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(refreshButton))
                    .addComponent(newBt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(ctrlPLayout.createSequentialGroup()
                        .addComponent(moreInfoBut)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(removeBt))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ctrlPLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(ctrlPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(ctrlPLayout.createSequentialGroup()
                                .addComponent(sortByLB)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(sortByCB, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(updownBut, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        ctrlPLayout.setVerticalGroup(
            ctrlPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ctrlPLayout.createSequentialGroup()
                .addComponent(newBt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ctrlPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(removeBt)
                    .addComponent(moreInfoBut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ctrlPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchBt)
                    .addComponent(refreshButton))
                .addGap(18, 18, 18)
                .addGroup(ctrlPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sortByLB)
                    .addComponent(sortByCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updownBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );

        topPriceTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                topPriceTxtFocusLost(evt);
            }
        });

        topPriceLabel.setText(" ");

        botPriceTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                botPriceTxtFocusLost(evt);
            }
        });

        botPriceLabel.setText(" ");

        javax.swing.GroupLayout srcP3Layout = new javax.swing.GroupLayout(srcP3);
        srcP3.setLayout(srcP3Layout);
        srcP3Layout.setHorizontalGroup(
            srcP3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, srcP3Layout.createSequentialGroup()
                .addGroup(srcP3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(topPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(srcP3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(topPriceTxt)
                    .addComponent(botPriceTxt)))
        );
        srcP3Layout.setVerticalGroup(
            srcP3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(srcP3Layout.createSequentialGroup()
                .addGroup(srcP3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(topPriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(topPriceLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(srcP3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botPriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botPriceLabel)))
        );

        topQuantityTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                topQuantityTxtFocusLost(evt);
            }
        });

        topQuantityLabel.setText(" ");

        botQuantityTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                botQuantityTxtFocusLost(evt);
            }
        });

        botQuantityLabel.setText(" ");

        javax.swing.GroupLayout srcP4Layout = new javax.swing.GroupLayout(srcP4);
        srcP4.setLayout(srcP4Layout);
        srcP4Layout.setHorizontalGroup(
            srcP4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, srcP4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(srcP4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(topQuantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botQuantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(srcP4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(topQuantityTxt)
                    .addComponent(botQuantityTxt))
                .addGap(0, 0, 0))
        );
        srcP4Layout.setVerticalGroup(
            srcP4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(srcP4Layout.createSequentialGroup()
                .addGroup(srcP4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(topQuantityLabel)
                    .addComponent(topQuantityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(srcP4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botQuantityLabel)
                    .addComponent(botQuantityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout srcP6Layout = new javax.swing.GroupLayout(srcP6);
        srcP6.setLayout(srcP6Layout);
        srcP6Layout.setHorizontalGroup(
            srcP6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, srcP6Layout.createSequentialGroup()
                .addGroup(srcP6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(srcP4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(srcP3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        srcP6Layout.setVerticalGroup(
            srcP6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(srcP6Layout.createSequentialGroup()
                .addComponent(srcP3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(srcP4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(productScrollPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(srcP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(srcP2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(srcP6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(srcP5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ctrlP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(srcP1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(srcP6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(srcP2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(srcP5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(ctrlP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jSeparator1)
                    .addComponent(jSeparator2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(productScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void priceCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priceCBActionPerformed
        String pri = priceCB.getSelectedItem().toString();
        String t = "", b = "";
        Boolean c1, c2;
        switch (pri) {
            case "All":
                t = ""; b = "";
                c1 = false; c2 = false;
                break;
            case "Upper":
                t = "Than"; b = "";
                c1 = true; c2 = false;
                break;
            case "Lower":
                t = "Than"; b = "";
                c1 = true; c2 = false;
                break;
            case "From To":
                t = "From"; b = "To";
                c1 = true; c2 = true;
                break;
            default:
                throw new AssertionError();
        }
        topPriceLabel.setText(t);
        topPriceTxt.setText("0");
        botPriceLabel.setText(b);
        botPriceTxt.setText("0");
        
        topPriceTxt.setVisible(c1);
        botPriceTxt.setVisible(c2);
        
    }//GEN-LAST:event_priceCBActionPerformed

    private void newBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBtActionPerformed
        addNewProduct();
    }//GEN-LAST:event_newBtActionPerformed

    private void moreInfoButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moreInfoButActionPerformed
        if(displayTable.getSelectedRow() >= 0) {
            new ProductInfo(mc, mc.getpDAO().getFullProduct(displayingProd.get(displayTable.getSelectedRow())), 1).setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "Please select an product.");
        }
    }//GEN-LAST:event_moreInfoButActionPerformed

    private void sortByCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortByCBActionPerformed
        sortBy();
    }//GEN-LAST:event_sortByCBActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        refresh();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void searchBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtActionPerformed
        search();
    }//GEN-LAST:event_searchBtActionPerformed

    private void removeBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBtActionPerformed
        for(int i = 0; i < displayTable.getRowCount(); i++){
            if((boolean) displayTable.getValueAt(i, 0)){
                mc.getpDAO().deleteProduct(displayingProd.get(i), true);
            }
        }
        refresh();
    }//GEN-LAST:event_removeBtActionPerformed

    private void quantityCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantityCBActionPerformed
        String pri = quantityCB.getSelectedItem().toString();
        String t = "", b = "";
        Boolean c1, c2;
        switch (pri) {
            case "All":
                t = ""; b = "";
                c1 = false; c2 = false;
                break;
            case "Upper":
                t = "Than"; b = "";
                c1 = true; c2 = false;
                break;
            case "Lower":
                t = "Than"; b = "";
                c1 = true; c2 = false;
                break;
            case "From To":
                t = "From"; b = "To";
                c1 = true; c2 = true;
                break;
            default:
                throw new AssertionError();
        }
        topQuantityLabel.setText(t);
        topQuantityTxt.setText("0");
        botQuantityLabel.setText(b);
        botQuantityTxt.setText("0");
        
        topQuantityTxt.setVisible(c1);
        botQuantityTxt.setVisible(c2);
    }//GEN-LAST:event_quantityCBActionPerformed

    private void typeMoreButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeMoreButActionPerformed
        // TODO add your handling code here:
        if(!typePM.isVisible()){
            Point p = typeChosed.getLocationOnScreen();
            typePM.setInvoker(typeChosed);
            typePM.setLocation((int) p.getX(), (int) p.getY() + typeChosed.getHeight());
            typePM.setVisible(true);
        }else{
            typePM.setVisible(false);
        }
    }//GEN-LAST:event_typeMoreButActionPerformed

    private void sectMoreButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sectMoreButActionPerformed
        // TODO add your handling code here:
        if(!sectPM.isVisible()){
            Point p = sectionChosed.getLocationOnScreen();
            sectPM.setInvoker(sectionChosed);
            sectPM.setLocation((int) p.getX(), (int) p.getY() + sectionChosed.getHeight());
            sectPM.setVisible(true);
        }else{
            sectPM.setVisible(false);
        }
    }//GEN-LAST:event_sectMoreButActionPerformed

    private void updownButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updownButActionPerformed
        Collections.reverse(this.displayingProd);
        setTable();
    }//GEN-LAST:event_updownButActionPerformed

    private void yearCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearCBActionPerformed
        if(yearCB.getSelectedIndex() == 0){
            monthCB.setSelectedIndex(0);
            monthCB.setEnabled(false);
            dayCB.setSelectedIndex(0);
            dayCB.setEnabled(false);
        }else{
            monthCB.setEnabled(true);
            dayCB.setEnabled(true);
        }
    }//GEN-LAST:event_yearCBActionPerformed

    private void monthCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthCBActionPerformed
        if(monthCB.getSelectedIndex() == 0){
            dayCB.setSelectedIndex(0);
            dayCB.setEnabled(false);
        }else{
            dayCB.removeAllItems();
            dayCB.addItem("dd");
            for(int i = 1; i <= 28; i++){
                dayCB.addItem(String.format("%02d", i));
            }
            if(monthCB.getSelectedIndex() == 1 || monthCB.getSelectedIndex() == 3 || 
                    monthCB.getSelectedIndex() == 5 || monthCB.getSelectedIndex() == 7 ||
                    monthCB.getSelectedIndex() == 8 || monthCB.getSelectedIndex() == 10 || monthCB.getSelectedIndex() == 12){
                for(int i = 29; i <= 31; i++){
                    dayCB.addItem(String.format("%02d", i));
                }
            }else if(monthCB.getSelectedIndex() != 2){
                for(int i = 29; i <= 30; i++){
                    dayCB.addItem(String.format("%02d", i));
                }
            }
            dayCB.setEnabled(true);
        }
    }//GEN-LAST:event_monthCBActionPerformed

    private void botQuantityTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botQuantityTxtFocusLost
        if(botQuantityTxt.getText().length() != 0){
            botQuantityTxt.setText(botQuantityTxt.getText().replaceAll("[a-zA-Z]", ""));
        }
        if(botQuantityTxt.getText().length() == 0){
            botQuantityTxt.setText("0");
        }
    }//GEN-LAST:event_botQuantityTxtFocusLost

    private void topQuantityTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_topQuantityTxtFocusLost
        if(topQuantityTxt.getText().length() != 0){
            topQuantityTxt.setText(topQuantityTxt.getText().replaceAll("[a-zA-Z]", ""));
        }
        if(topQuantityTxt.getText().length() == 0){
            topQuantityTxt.setText("0");
        }
    }//GEN-LAST:event_topQuantityTxtFocusLost

    private void botPriceTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botPriceTxtFocusLost
        if(botPriceTxt.getText().length() != 0){
            botPriceTxt.setText(botPriceTxt.getText().replaceAll("[a-zA-Z]", ""));
        }
        if(botPriceTxt.getText().length() == 0){
            botPriceTxt.setText("0");
        }
    }//GEN-LAST:event_botPriceTxtFocusLost

    private void topPriceTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_topPriceTxtFocusLost
        if(topPriceTxt.getText().length() != 0){
            topPriceTxt.setText(topPriceTxt.getText().replaceAll("[a-zA-Z]", ""));
        }
        if(topPriceTxt.getText().length() == 0){
            topPriceTxt.setText("0");
        }
    }//GEN-LAST:event_topPriceTxtFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel botPriceLabel;
    private javax.swing.JTextField botPriceTxt;
    private javax.swing.JLabel botQuantityLabel;
    private javax.swing.JTextField botQuantityTxt;
    private javax.swing.JPanel ctrlP;
    private javax.swing.JLabel dateLb;
    private javax.swing.JComboBox<String> dayCB;
    private javax.swing.JTable displayTable;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JComboBox<String> monthCB;
    private javax.swing.JButton moreInfoBut;
    private javax.swing.JButton newBt;
    private javax.swing.JComboBox<String> priceCB;
    private javax.swing.JLabel priceLb;
    private javax.swing.JLabel prodBrandLb;
    private javax.swing.JTextField prodBrandTxt;
    private javax.swing.JLabel prodCodeLb;
    private javax.swing.JTextField prodCodeTxt;
    private javax.swing.JLabel prodNameLb;
    private javax.swing.JTextField prodNameTxt;
    private javax.swing.JScrollPane productScrollPane;
    private javax.swing.JComboBox<String> quantityCB;
    private javax.swing.JLabel quantityLb;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton removeBt;
    private javax.swing.JButton searchBt;
    private javax.swing.JButton sectMoreBut;
    private javax.swing.JPopupMenu sectPM;
    private javax.swing.JLabel sectionChosed;
    private javax.swing.JLabel sectionLabel;
    private javax.swing.JComboBox<String> sortByCB;
    private javax.swing.JLabel sortByLB;
    private javax.swing.JPanel srcP1;
    private javax.swing.JPanel srcP2;
    private javax.swing.JPanel srcP3;
    private javax.swing.JPanel srcP4;
    private javax.swing.JPanel srcP5;
    private javax.swing.JPanel srcP6;
    private javax.swing.JLabel topPriceLabel;
    private javax.swing.JTextField topPriceTxt;
    private javax.swing.JLabel topQuantityLabel;
    private javax.swing.JTextField topQuantityTxt;
    private javax.swing.JLabel typeChosed;
    private javax.swing.JLabel typeLb;
    private javax.swing.JButton typeMoreBut;
    private javax.swing.JPopupMenu typePM;
    private javax.swing.JToggleButton updownBut;
    private javax.swing.JComboBox<String> yearCB;
    // End of variables declaration//GEN-END:variables
}
