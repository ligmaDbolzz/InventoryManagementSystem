/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.Popup;

import java.awt.event.MouseEvent;

/**
 *
 * @author Admin
 */
public class PopupMenuP extends javax.swing.JPanel {
    public int sizE = 169;
    public Boolean hasCusor;
    private String nowP;
    /**
     * Creates new form PopupMenuP
     */
    public PopupMenuP() {
        initComponents();
        hasCusor = false;
    }
    
    //Method
    public String getUserButName(){
        return userBut.getText();
    }
    public String getProdButName(){
        return productButon.getText();
    }
    public String getSectButName(){
        return sectionButton.getText();
    }
    public String getImpoButName(){
        return importButton.getText();
    }
    public String getLogButName(){
        return historyBut.getText();
    }
    
    
    public String getNowP(){
        return this.nowP;
    }
    
    //Override
    public void doIt(){}
    public void mouseExited(MouseEvent evt){}
    //
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    //Actionperformed Method
    public void mouseEnter(){
        hasCusor = true;
    }
    public void mouseExit(){
        hasCusor = false;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        logOutBut = new javax.swing.JButton();
        productButon = new javax.swing.JButton();
        sectionButton = new javax.swing.JButton();
        historyBut = new javax.swing.JButton();
        userBut = new javax.swing.JButton();
        importButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 204, 204));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
        });

        logOutBut.setBackground(new java.awt.Color(255, 102, 51));
        logOutBut.setForeground(new java.awt.Color(0, 0, 0));
        logOutBut.setText("Log out");
        logOutBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutButActionPerformed(evt);
            }
        });

        productButon.setBackground(new java.awt.Color(102, 102, 102));
        productButon.setForeground(new java.awt.Color(204, 204, 204));
        productButon.setText("Product");
        productButon.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        productButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productButonActionPerformed(evt);
            }
        });

        sectionButton.setBackground(new java.awt.Color(102, 102, 102));
        sectionButton.setForeground(new java.awt.Color(204, 204, 204));
        sectionButton.setText("Section");
        sectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sectionButtonActionPerformed(evt);
            }
        });

        historyBut.setBackground(new java.awt.Color(102, 102, 102));
        historyBut.setForeground(new java.awt.Color(204, 204, 204));
        historyBut.setText("Action Log");
        historyBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historyButActionPerformed(evt);
            }
        });

        userBut.setBackground(new java.awt.Color(102, 102, 102));
        userBut.setForeground(new java.awt.Color(204, 204, 204));
        userBut.setText("User Info");
        userBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userButActionPerformed(evt);
            }
        });

        importButton.setBackground(new java.awt.Color(102, 102, 102));
        importButton.setForeground(new java.awt.Color(204, 204, 204));
        importButton.setText("Import");
        importButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(logOutBut, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userBut, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(productButon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                        .addComponent(sectionButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(historyBut, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(importButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(userBut, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(productButon, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(sectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(importButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(historyBut, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 182, Short.MAX_VALUE)
                .addComponent(logOutBut, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void productButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productButonActionPerformed
        nowP = getProdButName();
        doIt();
    }//GEN-LAST:event_productButonActionPerformed

    private void sectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sectionButtonActionPerformed
        nowP = getSectButName();
        doIt();
    }//GEN-LAST:event_sectionButtonActionPerformed

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        mouseExited(evt);
        mouseExit();
    }//GEN-LAST:event_formMouseExited

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        mouseEnter();
    }//GEN-LAST:event_formMouseEntered

    private void logOutButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutButActionPerformed
        System.exit(0);
    }//GEN-LAST:event_logOutButActionPerformed

    private void importButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importButtonActionPerformed
        nowP = getImpoButName();
        doIt();
    }//GEN-LAST:event_importButtonActionPerformed

    private void historyButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyButActionPerformed
        nowP = getLogButName();
        doIt();
    }//GEN-LAST:event_historyButActionPerformed

    private void userButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userButActionPerformed
        nowP = getUserButName();
        doIt();
    }//GEN-LAST:event_userButActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton historyBut;
    private javax.swing.JButton importButton;
    private javax.swing.JButton logOutBut;
    private javax.swing.JButton productButon;
    private javax.swing.JButton sectionButton;
    private javax.swing.JButton userBut;
    // End of variables declaration//GEN-END:variables
}
