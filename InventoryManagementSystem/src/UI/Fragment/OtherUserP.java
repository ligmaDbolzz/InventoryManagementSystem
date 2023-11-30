/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.Fragment;

import UI.Base.UserPanel;
import User.HashPassword;
import User.User;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class OtherUserP extends javax.swing.JPanel {
    private User user, admin;
    /**
     * Creates new form OtherUserP
     */
    public OtherUserP(User admin, User user) {
        initComponents();
        username.setEditable(false);
        name.setEditable(false);
        this.user = user;
        this.admin = admin;
        setData();
    }
    
    private void setData(){
        username.setText(user.getUsername());
        name.setText(user.getName());
        lvl.setText(user.getLevel() + "");
    }
    
    public void removeMe(){}
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        img = new javax.swing.JLabel();
        nameLb = new javax.swing.JLabel();
        usernameLb = new javax.swing.JLabel();
        lvlLb = new javax.swing.JLabel();
        resetPassBt = new javax.swing.JButton();
        removeBt = new javax.swing.JButton();
        down = new javax.swing.JButton();
        username = new javax.swing.JTextField();
        name = new javax.swing.JTextField();
        lvl = new javax.swing.JLabel();
        up = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 204, 153));

        img.setBackground(new java.awt.Color(255, 204, 255));
        img.setOpaque(true);

        nameLb.setText("Name : ");

        usernameLb.setText("Username : ");

        lvlLb.setText("Level :");

        resetPassBt.setText("Reset Password");
        resetPassBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetPassBtActionPerformed(evt);
            }
        });

        removeBt.setText("Remove");
        removeBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBtActionPerformed(evt);
            }
        });

        down.setBackground(new java.awt.Color(255, 51, 51));
        down.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        down.setText("-");
        down.setOpaque(true);
        down.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downActionPerformed(evt);
            }
        });

        username.setText("nnn");

        name.setText("nnn");

        lvl.setText("0");

        up.setBackground(new java.awt.Color(51, 204, 0));
        up.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        up.setForeground(new java.awt.Color(0, 0, 0));
        up.setText("+");
        up.setOpaque(true);
        up.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(img, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lvlLb, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lvl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(up)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(down))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(nameLb, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(usernameLb)
                            .addGap(18, 18, 18)
                            .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(resetPassBt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removeBt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(usernameLb)
                                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(resetPassBt))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameLb)
                            .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(removeBt))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lvlLb)
                            .addComponent(lvl)
                            .addComponent(down)
                            .addComponent(up)))
                    .addComponent(img, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void resetPassBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetPassBtActionPerformed
        try {
            String np = JOptionPane.showInputDialog("Enter new password.");
            if(np != null){
                User npu = new User(user.getUsername(), new HashPassword().hashPassword(np));
                this.admin.updateInfo(npu);
                setData();
            }
        } catch (NoSuchAlgorithmException ex) {
            JOptionPane.showMessageDialog(null, "Unable to reset password");
            Logger.getLogger(UserPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_resetPassBtActionPerformed

    private void removeBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBtActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Remove user " + user.getUsername() + " ?")==0){
            if(admin.removeUser(user)){
                JOptionPane.showMessageDialog(null, "User " + user.getUsername() + " removed.");
                setVisible(false);
                removeMe();
            }else{
                JOptionPane.showMessageDialog(null, "Unable to removed user " + user.getUsername() + ".");
            }
        }
    }//GEN-LAST:event_removeBtActionPerformed

    private void upActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upActionPerformed
        admin.promote(user, user.getLevel()+1);
        user.getUser();
        setData();
    }//GEN-LAST:event_upActionPerformed

    private void downActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downActionPerformed
        if(user.getLevel() > 0){
            admin.promote(user, user.getLevel()-1);
        }
        user.getUser();
        setData();
    }//GEN-LAST:event_downActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton down;
    private javax.swing.JLabel img;
    private javax.swing.JLabel lvl;
    private javax.swing.JLabel lvlLb;
    private javax.swing.JTextField name;
    private javax.swing.JLabel nameLb;
    private javax.swing.JButton removeBt;
    private javax.swing.JButton resetPassBt;
    private javax.swing.JButton up;
    private javax.swing.JTextField username;
    private javax.swing.JLabel usernameLb;
    // End of variables declaration//GEN-END:variables
}
