/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI.Popup;

import Controler.MainControler;
import DTO.ProductDTO;
import DTO.SectionDTO;
import Tools.Converter;
import Tools.DirLib;
import UI.Fragment.ProdField;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class ProductInfo extends javax.swing.JFrame {
    final JFileChooser imgChoser = new JFileChooser();
    private MainControler mc;
    private ProductDTO prod, oldP;
    private ArrayList<ProdField> fields;
    private int mode;
    private final int imgsz = 160;
    /**
     * Creates new form NewProduct
     * @param mc
     */
    
    
    public ProductInfo(MainControler mc){
        initComponents();
        this.mc = mc;
        this.prod = new ProductDTO();
        this.prod.setType("Nall");
        this.oldP = new ProductDTO();
        this.oldP.setType("Nall");
        this.fields = new ArrayList<>();
        prePare();
    }

    public ProductInfo(MainControler mc, ProductDTO prod, int mode) {
        this(mc);
        this.prod = prod;
        this.oldP = prod;
        System.out.println(this.prod);
        setMode(mode);
        setData();
        setMode(mode);
    }
    
    private void prePare(){
        typeCB.removeAllItems();
        ArrayList<String> typ = Converter.typeList();
        for (String ty : typ) {
            typeCB.addItem(ty);
        }
        
        sectionCB.removeAllItems();
        for(SectionDTO sec : mc.getSectList()){
            sectionCB.addItem(sec.getCode());
        }
        
    }
    
    private void setMode(int mode){
        this.mode = mode;
        if(mode > 2 || mode < 0){
            this.mode = 1;
        }
        dateTxt.setEnabled(false);
        imgBut.setVisible( (this.mode == 0) || (this.mode ==2) );
        saveBut.setVisible((this.mode == 0) || (this.mode ==2) );
        backBut.setVisible(this.mode == 2);
        editBut.setVisible( (this.mode == 1) );
        
        codeTxt.setEditable(this.mode != 1);
        typeCB.setEnabled(this.mode != 1);
        nameTxt.setEditable( this.mode != 1);
        brandTxt.setEditable( this.mode != 1);
        sizeTxt.setEditable( this.mode != 1);
        priceTxt.setEditable( this.mode != 1);
        quantities.setEnabled(this.mode != 1);
        sectionCB.setEnabled(this.mode != 1);
        
        for (ProdField field : fields) {
            field.setEditable(this.mode != 1);
        }
    }
    
    private void setData(){
        codeTxt.setText(prod.getCode());
        typeCB.setSelectedItem(prod.getType().toString());
        nameTxt.setText(prod.getName());
        brandTxt.setText(prod.getBrand());
        if(prod.getSize()!= null){
            sizeTxt.setText(prod.getSize().toString());
        }
        if(prod.getPrice() != null){
            priceTxt.setText(prod.getPrice().toString());
        }
        if(prod.getQuantity() != null){
            quantities.setValue(prod.getQuantity());
        }else{
            quantities.setValue(0);
        }
        if(prod.getLocation() != null ){
            sectionCB.setSelectedItem(prod.getLocation());
        }else{
            sectionCB.setSelectedIndex(0);
        }
        
        dateTxt.setText(prod.getDateIN());
        if(prod.getImageDIR() != null){
            ImageIcon mg = new ImageIcon(prod.getImageDIR());
            Image a = mg.getImage().getScaledInstance(imgsz, imgsz, Image.SCALE_SMOOTH);
            mg = new ImageIcon(a);
            img.setIcon(mg);
        }

        setExtraDisplay();
    }
    
    private void setExtraDisplay(){
        this.fields = new ArrayList<>();
        ArrayList<Object> data = Converter.productToData(this.prod);
        ArrayList<Object> fieldName = Converter.productFieldName(this.prod);
        for(int i = 10; i < data.size(); i++){
            ProdField pf = new ProdField();
            pf.setField(fieldName.get(i).toString());
            if(data.get(i) != null){
                pf.setDescription(data.get(i).toString());
            }else{
                pf.setDescription(null);
            }
            this.fields.add(pf);
        }
        
        extraDisplayer.removeAll();
        for (ProdField field : this.fields) {
            extraDisplayer.add(field);
        }
        extraDisplayer.setLayout(new GridLayout(this.fields.size(), 1));
        extraDisplayer.setVisible(false);
        extraDisplayer.setVisible(true);
    }    
    
    private ProductDTO getData(){
        ArrayList<Object> data = new ArrayList<>();
        
        data.add(codeTxt.getText());
        data.add(typeCB.getSelectedItem().toString());
        data.add(nameTxt.getText());
        data.add(brandTxt.getText());
        data.add(sizeTxt.getText());
        data.add(priceTxt.getText());
        
        data.add(quantities.getValue().toString());
        data.add(sectionCB.getSelectedItem().toString());
        //date in
        LocalDate dt = LocalDate.now();
        String datee = String.format("%02d/%02d/%04d", dt.getDayOfMonth(), dt.getMonthValue(), dt.getYear());
        data.add(datee);
        //img
        if( (this.prod.getImageDIR() != null && this.prod.getImageDIR().length() > 0)
            && ( (this.oldP.getImageDIR() == null || this.oldP.getImageDIR().length() == 0 )
            || (this.oldP.getImageDIR() != null 
                && this.oldP.getImageDIR().length() > 0
                && !this.prod.getImageDIR().toLowerCase().equalsIgnoreCase(oldP.getImageDIR().toLowerCase()))
                )
        ){
            try {
                File pre = new File(this.prod.getImageDIR());
                File neo = new File(DirLib.imgPath() + "/P" + this.prod.getCode() + pre.getName());
 
                Files.copy( Paths.get(pre.getAbsolutePath()), 
                            Paths.get(neo.getAbsolutePath()), 
                            StandardCopyOption.REPLACE_EXISTING);
                if(this.oldP.getImageDIR() != null && this.oldP.getImageDIR().length() > 0){
                    File old = new File(this.oldP.getImageDIR());
                    old.delete();
                }
                data.add(neo.getAbsolutePath());
            } catch (IOException ex) {
                data.add(this.prod.getImageDIR());
                Logger.getLogger(ProductInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            data.add("");
        }
        for(ProdField pf : fields){
            data.add(pf.getDescription());
        }
        return Converter.dataToProduct(data);
    }

    private boolean isFull(){
        return (codeTxt.getText().length() >  0) && (typeCB.getSelectedIndex() >= 0) && (nameTxt.getText().length() >  0) && (brandTxt.getText().length() >  0) && (sectionCB.getSelectedIndex() >= 0);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    //Actionperformed Method
    private void choseIMG(){
        int returnVal = imgChoser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            File imgFile = imgChoser.getSelectedFile();
            String now = imgFile.getAbsolutePath();
            prod.setImageDIR(now);
            
            ImageIcon mg = new ImageIcon(prod.getImageDIR());
            Image a = mg.getImage().getScaledInstance(imgsz, imgsz, Image.SCALE_SMOOTH);
            mg = new ImageIcon(a);
            img.setIcon(mg);
        }
    }
    
    private void save(){
        if(isFull()){
            this.prod = getData();
            if(this.mode == 0){
                if(mc.getpDAO().addProduct(getData(), true)){
                    setMode(1);
                }
            }else if(this.mode == 2){
                if(mc.getpDAO().editProduct(this.oldP, this.prod, true)){
                    setMode(1);
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "All field must be filled to continue.");
        }
    }
    
    
    private void typeCbSelected(){
        if(typeCB.getSelectedItem() != null && this.prod != null && this.mode != 1){
            ArrayList<Object> nprod = new ArrayList<>(Converter.productToData(prod).subList(0, 10));
            nprod.set(1, typeCB.getSelectedItem());
            this.prod = Converter.dataToProduct(nprod);
            
            setExtraDisplay();
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        baseInfoP = new javax.swing.JPanel();
        imgPanel = new javax.swing.JPanel();
        img = new javax.swing.JLabel();
        imgBut = new javax.swing.JButton();
        baseInfo = new javax.swing.JPanel();
        codeLb = new javax.swing.JLabel();
        typeLb = new javax.swing.JLabel();
        nameLb = new javax.swing.JLabel();
        brandLb = new javax.swing.JLabel();
        sizeLb = new javax.swing.JLabel();
        priceLb = new javax.swing.JLabel();
        quantityLb = new javax.swing.JLabel();
        sectionLabel = new javax.swing.JLabel();
        dateLb = new javax.swing.JLabel();
        codeTxt = new javax.swing.JTextField();
        typeCB = new javax.swing.JComboBox<>();
        nameTxt = new javax.swing.JTextField();
        brandTxt = new javax.swing.JTextField();
        quantities = new javax.swing.JSpinner();
        sectionCB = new javax.swing.JComboBox<>();
        dateTxt = new javax.swing.JTextField();
        sizeTxt = new javax.swing.JTextField();
        priceTxt = new javax.swing.JTextField();
        controlP = new javax.swing.JPanel();
        editBut = new javax.swing.JButton();
        saveBut = new javax.swing.JButton();
        backBut = new javax.swing.JButton();
        extraScroll = new javax.swing.JScrollPane();
        extraDisplayer = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        img.setBackground(new java.awt.Color(153, 153, 153));
        img.setOpaque(true);

        imgBut.setText("Browse image");
        imgBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imgButActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout imgPanelLayout = new javax.swing.GroupLayout(imgPanel);
        imgPanel.setLayout(imgPanelLayout);
        imgPanelLayout.setHorizontalGroup(
            imgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, imgPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(imgBut, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(img, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        imgPanelLayout.setVerticalGroup(
            imgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(imgPanelLayout.createSequentialGroup()
                .addComponent(img, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(imgBut, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        codeLb.setText("Code");

        typeLb.setText("Type");

        nameLb.setText("Name");

        brandLb.setText("Brand");

        sizeLb.setText("Size");

        priceLb.setText("Price");

        quantityLb.setText("Quantity");

        sectionLabel.setText("Section");

        dateLb.setText("Date In");

        typeCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NULL", "New" }));
        typeCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeCBActionPerformed(evt);
            }
        });

        sectionCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NULL" }));

        dateTxt.setText("dd/mm/yyyy");

        sizeTxt.setText("0");
        sizeTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                sizeTxtFocusLost(evt);
            }
        });

        priceTxt.setText("0");
        priceTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                priceTxtFocusLost(evt);
            }
        });

        javax.swing.GroupLayout baseInfoLayout = new javax.swing.GroupLayout(baseInfo);
        baseInfo.setLayout(baseInfoLayout);
        baseInfoLayout.setHorizontalGroup(
            baseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(baseInfoLayout.createSequentialGroup()
                .addGroup(baseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(sectionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(quantityLb, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                    .addComponent(sizeLb, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(brandLb, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nameLb, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(typeLb, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(codeLb, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(baseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameTxt)
                    .addGroup(baseInfoLayout.createSequentialGroup()
                        .addGroup(baseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(codeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(typeCB, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(quantities, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(baseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(brandTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(baseInfoLayout.createSequentialGroup()
                                    .addGroup(baseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(baseInfoLayout.createSequentialGroup()
                                            .addComponent(sectionCB, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(baseInfoLayout.createSequentialGroup()
                                            .addComponent(sizeTxt)
                                            .addGap(84, 84, 84)))
                                    .addGroup(baseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(baseInfoLayout.createSequentialGroup()
                                            .addComponent(dateLb)
                                            .addGap(18, 18, 18)
                                            .addComponent(dateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, baseInfoLayout.createSequentialGroup()
                                            .addComponent(priceLb, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(priceTxt))))))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        baseInfoLayout.setVerticalGroup(
            baseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(baseInfoLayout.createSequentialGroup()
                .addGroup(baseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codeLb)
                    .addComponent(codeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(baseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeLb)
                    .addComponent(typeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(baseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLb, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(baseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(brandLb)
                    .addComponent(brandTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(baseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sizeLb)
                    .addComponent(priceLb, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sizeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(priceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(baseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quantityLb, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quantities))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(baseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sectionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sectionCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateLb)
                    .addComponent(dateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        editBut.setText("Edit");
        editBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButActionPerformed(evt);
            }
        });

        saveBut.setText("Save");
        saveBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButActionPerformed(evt);
            }
        });

        backBut.setText("Back");
        backBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout controlPLayout = new javax.swing.GroupLayout(controlP);
        controlP.setLayout(controlPLayout);
        controlPLayout.setHorizontalGroup(
            controlPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backBut, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(saveBut, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(editBut, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        controlPLayout.setVerticalGroup(
            controlPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPLayout.createSequentialGroup()
                .addComponent(editBut)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(backBut)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(saveBut)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout baseInfoPLayout = new javax.swing.GroupLayout(baseInfoP);
        baseInfoP.setLayout(baseInfoPLayout);
        baseInfoPLayout.setHorizontalGroup(
            baseInfoPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(baseInfoPLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(baseInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(controlP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        baseInfoPLayout.setVerticalGroup(
            baseInfoPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(baseInfoPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(baseInfoPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(baseInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(imgPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(controlP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout extraDisplayerLayout = new javax.swing.GroupLayout(extraDisplayer);
        extraDisplayer.setLayout(extraDisplayerLayout);
        extraDisplayerLayout.setHorizontalGroup(
            extraDisplayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 777, Short.MAX_VALUE)
        );
        extraDisplayerLayout.setVerticalGroup(
            extraDisplayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 199, Short.MAX_VALUE)
        );

        extraScroll.setViewportView(extraDisplayer);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(extraScroll)
                    .addComponent(baseInfoP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(baseInfoP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(extraScroll)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void typeCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeCBActionPerformed
        typeCbSelected();
    }//GEN-LAST:event_typeCBActionPerformed

    private void imgButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imgButActionPerformed
        // TODO add your handling code here:
        choseIMG();
    }//GEN-LAST:event_imgButActionPerformed

    private void editButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButActionPerformed
        // TODO add your handling code here:
        setMode(2);
    }//GEN-LAST:event_editButActionPerformed

    private void saveButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButActionPerformed
        // TODO add your handling code here:
        save();
    }//GEN-LAST:event_saveButActionPerformed

    private void backButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButActionPerformed
        // TODO add your handling code here:
        this.prod = this.oldP;
        setData();
        setMode(1);
    }//GEN-LAST:event_backButActionPerformed

    private void sizeTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sizeTxtFocusLost
        sizeTxt.setText(sizeTxt.getText().replaceAll("[a-zA-z]", ""));
        sizeTxt.setText(sizeTxt.getText().replaceAll("^[0+]", ""));
        if(sizeTxt.getText().length() == 0){
            sizeTxt.setText("0");
        }
    }//GEN-LAST:event_sizeTxtFocusLost

    private void priceTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_priceTxtFocusLost
        priceTxt.setText(priceTxt.getText().replaceAll("[a-zA-z]", ""));
        priceTxt.setText(priceTxt.getText().replaceAll("^[0+]", ""));
        if(priceTxt.getText().length() == 0){
            priceTxt.setText("0");
        }
    }//GEN-LAST:event_priceTxtFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBut;
    private javax.swing.JPanel baseInfo;
    private javax.swing.JPanel baseInfoP;
    private javax.swing.JLabel brandLb;
    private javax.swing.JTextField brandTxt;
    private javax.swing.JLabel codeLb;
    private javax.swing.JTextField codeTxt;
    private javax.swing.JPanel controlP;
    private javax.swing.JLabel dateLb;
    private javax.swing.JTextField dateTxt;
    private javax.swing.JButton editBut;
    private javax.swing.JPanel extraDisplayer;
    private javax.swing.JScrollPane extraScroll;
    private javax.swing.JLabel img;
    private javax.swing.JButton imgBut;
    private javax.swing.JPanel imgPanel;
    private javax.swing.JLabel nameLb;
    private javax.swing.JTextField nameTxt;
    private javax.swing.JLabel priceLb;
    private javax.swing.JTextField priceTxt;
    private javax.swing.JSpinner quantities;
    private javax.swing.JLabel quantityLb;
    private javax.swing.JButton saveBut;
    private javax.swing.JComboBox<String> sectionCB;
    private javax.swing.JLabel sectionLabel;
    private javax.swing.JLabel sizeLb;
    private javax.swing.JTextField sizeTxt;
    private javax.swing.JComboBox<String> typeCB;
    private javax.swing.JLabel typeLb;
    // End of variables declaration//GEN-END:variables
}
