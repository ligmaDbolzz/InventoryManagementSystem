/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI.Popup;

import Controler.MainControler;
import DTO.SectionDTO;
import Tools.DirLib;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

/**
 *
 * @author Admin
 */
public class SectionInfo extends javax.swing.JFrame {
    final JFileChooser imgChoser = new JFileChooser();
    private SectionDTO secT, oldS;
    private MainControler mc;
    private int mode;
    private final int imgsz = 200;
    /**
     * Creates new form SectionInfo
     * @param mc
     * @param sec
     * @param mode
     */
    public SectionInfo(MainControler mc,SectionDTO sec, int mode) {
        initComponents();
        this.mc = mc;
        this.secT = this.mc.getsDAO().getSection(sec.getCode());
        this.oldS = this.mc.getsDAO().getSection(sec.getCode());
        this.mode = mode;
        if(this.mode == 0){
            getData();
        }
        setData();
        setMode();
    }
    
    private void setMode(){
        freeSpaceBar.setVisible(this.mode == 1);
        sizeFreeOutOf.setVisible(this.mode == 1);
        valueLb.setVisible(this.mode == 1);
        value.setVisible(this.mode == 1);
        
        value.setEditable(false);
        backBt.setVisible(this.mode == 1);
    }
    
    private void setData(){
        code.setText(this.secT.getCode());
        size.setText(this.secT.getSize().toString());
        sizeFreeOutOf.setText(sizeFreeOutOf.getText()
                                .replaceAll("-1-", this.secT.getFree().toString()));
        freeSpaceBar.setMinimum(0);
        int mx = this.secT.getSize().intValue();
        int fr = this.secT.getFree().intValue();
        freeSpaceBar.setMaximum(mx);
        freeSpaceBar.setValue(mx - fr);
        value.setText(this.secT.getValue().toString());
        ImageIcon mg = new ImageIcon(this.secT.getImageDIR());
        Image a = mg.getImage().getScaledInstance(imgsz, imgsz, Image.SCALE_SMOOTH);
        mg = new ImageIcon(a);
        img.setIcon(mg);
    }
    
    private void getData(){
        System.out.println(oldS.getImageDIR());
        this.secT.setCode(code.getText());
        this.secT.setSize(Double.valueOf(size.getText()));
        if(this.secT.getFree() == null){
            this.secT.setFree(this.secT.getSize());
            this.secT.setValue(0.0);
        }
        if(this.mode == 0){
            this.secT.setFree(this.secT.getSize());
        }
        if( (this.secT.getImageDIR() != null && this.secT.getImageDIR().length() > 0)
            && ((this.oldS.getImageDIR() == null || this.oldS.getImageDIR().length() == 0 )
            || (this.oldS.getImageDIR() != null 
                && this.oldS.getImageDIR().length() > 0 
                && !this.secT.getImageDIR().toLowerCase().equalsIgnoreCase(this.oldS.getImageDIR().toLowerCase())
                )
            )
        ){
            try {
                File pre = new File(this.secT.getImageDIR());
                File neo = new File(DirLib.imgPath() + "/P" + this.secT.getCode() + pre.getName());
                if(this.oldS.getImageDIR() != null && this.oldS.getImageDIR().length()>0){
                    File old = new File(this.oldS.getImageDIR());
                    old.delete();
                }
                Files.copy(Paths.get(pre.getAbsolutePath()), 
                            Paths.get(neo.getAbsolutePath()), 
                            StandardCopyOption.REPLACE_EXISTING);
                this.secT.setImageDIR(neo.getAbsolutePath().replaceAll("\\\\", "\\\\\\\\"));
                System.out.println(this.secT.getImageDIR());
            } catch (IOException ex) {
                this.secT.setImageDIR("");
                Logger.getLogger(SectionInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            this.secT.setImageDIR("");
        }
    }
    
    private void choseIMG(){
        int returnVal = imgChoser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            File imgFile = imgChoser.getSelectedFile();
            String now = imgFile.getAbsolutePath();
            this.secT.setImageDIR(now); 
            ImageIcon mg = new ImageIcon(secT.getImageDIR());
            Image a = mg.getImage().getScaledInstance(imgsz, imgsz, Image.SCALE_SMOOTH);
            mg = new ImageIcon(a);
            img.setIcon(mg);
        }
    }
    
    private void back(){
        this.secT = this.oldS;
        setData();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    //Avtion Method
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPasswordField1 = new javax.swing.JPasswordField();
        sectionLb = new javax.swing.JLabel();
        img = new javax.swing.JLabel();
        sizeLb = new javax.swing.JLabel();
        valueLb = new javax.swing.JLabel();
        code = new javax.swing.JTextField();
        size = new javax.swing.JTextField();
        freeSpaceBar = new javax.swing.JProgressBar();
        sizeFreeOutOf = new javax.swing.JLabel();
        value = new javax.swing.JTextField();
        ctrlP = new javax.swing.JPanel();
        imgChoseBt = new javax.swing.JButton();
        saveBut = new javax.swing.JButton();
        backBt = new javax.swing.JButton();

        jPasswordField1.setText("jPasswordField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        sectionLb.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sectionLb.setText("Section code :");

        img.setBackground(new java.awt.Color(204, 204, 204));
        img.setOpaque(true);

        sizeLb.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sizeLb.setText("Size :");

        valueLb.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        valueLb.setText("Total Value :");

        code.setText("---");

        size.setText("0");
        size.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                sizeFocusLost(evt);
            }
        });

        sizeFreeOutOf.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        sizeFreeOutOf.setText("Free space : |-1-|");

        value.setText("0");

        imgChoseBt.setText("Browse Image");
        imgChoseBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imgChoseBtActionPerformed(evt);
            }
        });

        saveBut.setText("Save");
        saveBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButActionPerformed(evt);
            }
        });

        backBt.setText("Back");
        backBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ctrlPLayout = new javax.swing.GroupLayout(ctrlP);
        ctrlP.setLayout(ctrlPLayout);
        ctrlPLayout.setHorizontalGroup(
            ctrlPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ctrlPLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgChoseBt, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saveBut)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(backBt)
                .addGap(7, 7, 7))
        );
        ctrlPLayout.setVerticalGroup(
            ctrlPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ctrlPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ctrlPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imgChoseBt)
                    .addComponent(saveBut)
                    .addComponent(backBt))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ctrlP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(img, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(sectionLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(sizeLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(size)
                                    .addComponent(code)))
                            .addComponent(freeSpaceBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sizeFreeOutOf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(valueLb, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(value, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(img, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sectionLb)
                            .addComponent(code, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(size, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sizeLb))
                        .addGap(18, 18, 18)
                        .addComponent(freeSpaceBar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sizeFreeOutOf)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(valueLb)
                            .addComponent(value, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ctrlP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void sizeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sizeFocusLost
        size.setText(size.getText().replaceAll("[a-zA-Z]", ""));
        if(size.getText().length() == 0){
            size.setText("0");
        }
    }//GEN-LAST:event_sizeFocusLost

    private void saveButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButActionPerformed
        getData();
        switch (this.mode) {
            case 0:
                this.mc.getsDAO().addSection(secT);
                break;
            case 1:
                this.mc.getsDAO().editSection(oldS, secT);
                break;
            default:
                break;
        }
        this.secT = mc.getsDAO().getSection(this.secT.getCode());
        setData();
    }//GEN-LAST:event_saveButActionPerformed

    private void imgChoseBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imgChoseBtActionPerformed
        choseIMG();
    }//GEN-LAST:event_imgChoseBtActionPerformed

    private void backBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtActionPerformed
        back();
    }//GEN-LAST:event_backBtActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBt;
    private javax.swing.JTextField code;
    private javax.swing.JPanel ctrlP;
    private javax.swing.JProgressBar freeSpaceBar;
    private javax.swing.JLabel img;
    private javax.swing.JButton imgChoseBt;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JButton saveBut;
    private javax.swing.JLabel sectionLb;
    private javax.swing.JTextField size;
    private javax.swing.JLabel sizeFreeOutOf;
    private javax.swing.JLabel sizeLb;
    private javax.swing.JTextField value;
    private javax.swing.JLabel valueLb;
    // End of variables declaration//GEN-END:variables
}
