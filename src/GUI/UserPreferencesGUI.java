/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Toolkit;

/**
 *
 * @author gmyrianthous
 */
public class UserPreferencesGUI extends javax.swing.JFrame {

    // Default preferences
    private boolean soundsEnabled = true;
    private boolean chatEnabled = true;
    private boolean deckBlue = true;
    private String carpetColour = "Green";
    private CarpetGUI carpet;
    
    /**
     * Creates new form UserPreferencesGUI
     */
    public UserPreferencesGUI(CarpetGUI carpet) {
        this.setResizable(false);
        this.carpet = carpet;
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    } // UserPreferencesGUI

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        deckColourButtonGroup = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        mainJPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        exitJButton = new javax.swing.JButton();
        saveJButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        resetJButton = new javax.swing.JButton();
        blueDeckJRadioButton = new javax.swing.JRadioButton();
        redDeckJRadioButton = new javax.swing.JRadioButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        chatJToggleButton = new javax.swing.JToggleButton();
        jLabel4 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        greenCarpetJRadioButton = new javax.swing.JRadioButton();
        blueCarpetJRadioButton = new javax.swing.JRadioButton();
        greyCarpetJRadioButton = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainJPanel.setBackground(new java.awt.Color(216, 231, 227));

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 3, 18)); // NOI18N
        jLabel2.setText("User Preferences");

        exitJButton.setText("Exit");
        exitJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitJButtonActionPerformed(evt);
            }
        });

        saveJButton.setText("Save changes");
        saveJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveJButtonActionPerformed(evt);
            }
        });

        resetJButton.setText("Reset to Default");
        resetJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetJButtonActionPerformed(evt);
            }
        });

        deckColourButtonGroup.add(blueDeckJRadioButton);
        blueDeckJRadioButton.setText("Blue (Default)");
        blueDeckJRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blueDeckJRadioButtonActionPerformed(evt);
            }
        });

        deckColourButtonGroup.add(redDeckJRadioButton);
        redDeckJRadioButton.setText("Red");

        jLabel1.setText("Deck colour");

        jLabel3.setText("Sounds & Chat");

        chatJToggleButton.setText("Disable chat");
        chatJToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatJToggleButtonActionPerformed(evt);
            }
        });

        jLabel4.setText("Carpet colour");

        buttonGroup1.add(greenCarpetJRadioButton);
        greenCarpetJRadioButton.setText("Green (Default)");
        greenCarpetJRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                greenCarpetJRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup1.add(blueCarpetJRadioButton);
        blueCarpetJRadioButton.setText("Blue");

        buttonGroup1.add(greyCarpetJRadioButton);
        greyCarpetJRadioButton.setText("Grey");

        javax.swing.GroupLayout mainJPanelLayout = new javax.swing.GroupLayout(mainJPanel);
        mainJPanel.setLayout(mainJPanelLayout);
        mainJPanelLayout.setHorizontalGroup(
            mainJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainJPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(mainJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainJPanelLayout.createSequentialGroup()
                        .addGroup(mainJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(mainJPanelLayout.createSequentialGroup()
                                .addComponent(saveJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(resetJButton))
                            .addComponent(jLabel2))
                        .addGap(33, 33, 33)
                        .addComponent(exitJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainJPanelLayout.createSequentialGroup()
                        .addGroup(mainJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(blueDeckJRadioButton)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(154, 154, 154))))
            .addGroup(mainJPanelLayout.createSequentialGroup()
                .addGroup(mainJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainJPanelLayout.createSequentialGroup()
                            .addGap(227, 227, 227)
                            .addComponent(redDeckJRadioButton))
                        .addGroup(mainJPanelLayout.createSequentialGroup()
                            .addGap(38, 38, 38)
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainJPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(mainJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainJPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainJPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addGap(18, 18, 18)
                                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(mainJPanelLayout.createSequentialGroup()
                        .addGap(187, 187, 187)
                        .addComponent(chatJToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainJPanelLayout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(greenCarpetJRadioButton)
                        .addGap(27, 27, 27)
                        .addComponent(blueCarpetJRadioButton)
                        .addGap(50, 50, 50)
                        .addComponent(greyCarpetJRadioButton)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        mainJPanelLayout.setVerticalGroup(
            mainJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGroup(mainJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainJPanelLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(mainJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(blueDeckJRadioButton)
                            .addComponent(redDeckJRadioButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addGroup(mainJPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(mainJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(greenCarpetJRadioButton)
                    .addComponent(blueCarpetJRadioButton)
                    .addComponent(greyCarpetJRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(mainJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainJPanelLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainJPanelLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chatJToggleButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(exitJButton)
                        .addComponent(saveJButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainJPanelLayout.createSequentialGroup()
                        .addComponent(resetJButton)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void blueDeckJRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blueDeckJRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_blueDeckJRadioButtonActionPerformed

    private void exitJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitJButtonActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_exitJButtonActionPerformed

    private void chatJToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatJToggleButtonActionPerformed
        // TODO add your handling code here:
        if (this.chatJToggleButton.isSelected()){
            this.chatJToggleButton.setText("Enable chat");
            this.chatEnabled = false;
        } else {
            this.chatJToggleButton.setText("Disable chat");
            this.chatEnabled = true;
        } // else
            
    }//GEN-LAST:event_chatJToggleButtonActionPerformed

    private void saveJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveJButtonActionPerformed
        // TODO add your handling code here:
        if (!this.blueDeckJRadioButton.isSelected() && !this.redDeckJRadioButton.isSelected())
            this.deckBlue = true;
        else if (this.blueDeckJRadioButton.isSelected())
            this.deckBlue = true;
        else if (this.redDeckJRadioButton.isSelected())
            this.deckBlue = false;
          
        if (this.chatJToggleButton.isSelected())
            this.chatEnabled = false;
        else
            this.chatEnabled = true;
        
        if (this.greenCarpetJRadioButton.isSelected())
            this.carpetColour = "Green";
        else if (this.greyCarpetJRadioButton.isSelected())
            this.carpetColour = "Grey";
        else if (this.blueCarpetJRadioButton.isSelected())
            this.carpetColour = "Blue";
        
        this.carpet.changeUserPreferences(this.deckBlue, this.carpetColour, this.chatEnabled);
        
    }//GEN-LAST:event_saveJButtonActionPerformed

    private void greenCarpetJRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_greenCarpetJRadioButtonActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_greenCarpetJRadioButtonActionPerformed

    private void resetJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetJButtonActionPerformed

        this.carpet.changeUserPreferences(true, "Green", true);
    }//GEN-LAST:event_resetJButtonActionPerformed
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton blueCarpetJRadioButton;
    private javax.swing.JRadioButton blueDeckJRadioButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JToggleButton chatJToggleButton;
    private javax.swing.ButtonGroup deckColourButtonGroup;
    private javax.swing.JButton exitJButton;
    private javax.swing.JRadioButton greenCarpetJRadioButton;
    private javax.swing.JRadioButton greyCarpetJRadioButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JPanel mainJPanel;
    private javax.swing.JRadioButton redDeckJRadioButton;
    private javax.swing.JButton resetJButton;
    private javax.swing.JButton saveJButton;
    // End of variables declaration//GEN-END:variables
}
