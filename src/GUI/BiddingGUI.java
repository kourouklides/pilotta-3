/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Game.Player;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author gmyrianthous
 */
public class BiddingGUI extends javax.swing.JFrame {
    private int currentBid;
    
    private int selectedBid;
    private String selectedSuit;
    private String biddings;
    private Player[] players = new Player[4];
    
    private Timer timer;
    private int timeLeft = 15;
    
    /**
     * Creates new form BiddingGUI
     */
    public BiddingGUI(int currentBid, String biddings, Player[] players) {
        initComponents();
        this.setResizable(false);
        
        this.currentBid = currentBid;
        this.players = players;
        this.biddings = biddings;
        
        for (int i = currentBid + 1; i < 39; i++)
            this.biddingJComboBox.addItem(new Integer(i));
        
        if (!this.biddings.equals("")){
            this.biddings = this.biddings.substring(0, this.biddings.length() -1);
            String bidders = this.biddings;
            String[] splitBidders = bidders.split("-");
            
            for (int j = 0; j < splitBidders.length; j++){
                String bidding = splitBidders[j];
                String[] split = bidding.split("_");
                int playerID = Integer.parseInt(split[0]);
                
                if (split.length == 2){ // pass -> form: 'playerID_Pass'
                    String bid = split[1]; // 'Pass'
                    this.biddingsJTextArea.append(this.players[playerID].getPlayerName()
                                                  + " declared " + bid + "\n");
                } // if
                else if (split.length == 3) { // normal declaration -> form: 'playerID_bid_suit'
                    String bid = split[1];
                    String suit = split[2];                    
                    this.biddingsJTextArea.append(this.players[playerID].getPlayerName() 
                                                  + " declared: " + bid + " of "
                                                  + suit + "\n");                
                } // else
            
            }// for  
        } // if
        
        this.remainingSecondsJLabel.setText(timeLeft+"");
        TimeClass tc = new TimeClass(timeLeft);
        timer = new Timer(1000, tc);
        timer.start();
 
    } // BiddingGUI

    
    public class TimeClass implements ActionListener {
        int counter;
    
        public TimeClass(int counter){
            this.counter = counter;
        } // TimeClass

        @Override
        public void actionPerformed(ActionEvent tc){
            counter--;
            if (counter >= 1)
                remainingSecondsJLabel.setText("" + counter);
            else{
                MainSignedIn.getClient().setClientBid(MainSignedIn.getClient().getClientID() + "_Pass");
                MainSignedIn.getClient().setBitSubmitted(true);

                Toolkit.getDefaultToolkit().beep();
                timer.stop();
                dispose();
            } // else
        } // actionPerformed
    } // class TimeClass
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        biddingJPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        heartsJRadioButton = new javax.swing.JRadioButton();
        diamondsJRadioButton = new javax.swing.JRadioButton();
        clubsJRadioButton = new javax.swing.JRadioButton();
        spadesJRadioButton = new javax.swing.JRadioButton();
        bidJButton = new javax.swing.JButton();
        bidding1JLabel = new javax.swing.JLabel();
        passJButton = new javax.swing.JButton();
        biddingJComboBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        biddingsJTextArea = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        remainingSecondsJLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        biddingJPanel.setBackground(new java.awt.Color(71, 113, 165));

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Bidding Round");
        jLabel1.setEnabled(false);

        jLabel5.setText("Your bid: ");

        buttonGroup.add(heartsJRadioButton);
        heartsJRadioButton.setText("Hearts");

        buttonGroup.add(diamondsJRadioButton);
        diamondsJRadioButton.setText("Diamonds");

        buttonGroup.add(clubsJRadioButton);
        clubsJRadioButton.setText("Clubs");

        buttonGroup.add(spadesJRadioButton);
        spadesJRadioButton.setText("Spades");

        bidJButton.setText("Bid");
        bidJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bidJButtonActionPerformed(evt);
            }
        });

        passJButton.setText("Pass");
        passJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passJButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("(Default: Spades)");

        biddingsJTextArea.setEditable(false);
        biddingsJTextArea.setColumns(20);
        biddingsJTextArea.setRows(5);
        jScrollPane1.setViewportView(biddingsJTextArea);

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(225, 17, 0));
        jLabel3.setText("Remaining Time: ");

        javax.swing.GroupLayout biddingJPanelLayout = new javax.swing.GroupLayout(biddingJPanel);
        biddingJPanel.setLayout(biddingJPanelLayout);
        biddingJPanelLayout.setHorizontalGroup(
            biddingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(biddingJPanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(biddingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(biddingJPanelLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(biddingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(biddingJPanelLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(biddingJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(biddingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spadesJRadioButton)
                            .addComponent(heartsJRadioButton)
                            .addComponent(diamondsJRadioButton)
                            .addComponent(clubsJRadioButton)))
                    .addGroup(biddingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(biddingJPanelLayout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(remainingSecondsJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(biddingJPanelLayout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(bidding1JLabel)
                            .addGap(87, 87, 87)
                            .addGroup(biddingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(passJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bidJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(37, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, biddingJPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(215, 215, 215))
        );
        biddingJPanelLayout.setVerticalGroup(
            biddingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(biddingJPanelLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGroup(biddingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(biddingJPanelLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(biddingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(biddingJPanelLayout.createSequentialGroup()
                                .addGroup(biddingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(biddingJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(3, 3, 3))
                            .addGroup(biddingJPanelLayout.createSequentialGroup()
                                .addComponent(heartsJRadioButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(diamondsJRadioButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clubsJRadioButton)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(biddingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spadesJRadioButton)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addComponent(bidJButton)
                        .addGap(18, 18, 18)
                        .addComponent(passJButton)
                        .addGap(61, 61, 61)
                        .addGroup(biddingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(remainingSecondsJLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(biddingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(biddingJPanelLayout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(bidding1JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(biddingJPanelLayout.createSequentialGroup()
                            .addGap(38, 38, 38)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(biddingJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(biddingJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void passJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passJButtonActionPerformed
        /* In the form 'clientID_Pass' */
        MainSignedIn.getClient().setClientBid(MainSignedIn.getClient().getClientID() + "_Pass");
        MainSignedIn.getClient().setBitSubmitted(true);
        dispose();
    }//GEN-LAST:event_passJButtonActionPerformed

    private void bidJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bidJButtonActionPerformed
        /* In the form 'clientID_bid_suit' */
        if (this.heartsJRadioButton == null && this.diamondsJRadioButton == null
            && this.clubsJRadioButton == null && this.spadesJRadioButton == null){
            JOptionPane.showMessageDialog(null, "Please select suit", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (this.biddingJComboBox == null){
            JOptionPane.showMessageDialog(null, "Please select a bid", "Error", JOptionPane.ERROR_MESSAGE);
        } else { // everything is OK.
            selectedBid = (int) this.biddingJComboBox.getSelectedItem();
            if (this.heartsJRadioButton.isSelected())
            selectedSuit = "Hearts";
            else if (this.diamondsJRadioButton.isSelected())
            selectedSuit = "Diamonds";
            else if (this.clubsJRadioButton.isSelected())
            selectedSuit = "Clubs";
            else
            selectedSuit = "Spades";
        } // else

        System.out.println("Selected bid: " + this.selectedBid + ". Selected suit:" + this.selectedSuit);

        MainSignedIn.getClient().setClientBid(MainSignedIn.getClient().getClientID() + "_" + this.selectedBid + "_" + this.selectedSuit);
        MainSignedIn.getClient().setBitSubmitted(true);

        // send bid
        dispose();
    }//GEN-LAST:event_bidJButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bidJButton;
    private javax.swing.JLabel bidding1JLabel;
    private javax.swing.JComboBox biddingJComboBox;
    private javax.swing.JPanel biddingJPanel;
    private javax.swing.JTextArea biddingsJTextArea;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JRadioButton clubsJRadioButton;
    private javax.swing.JRadioButton diamondsJRadioButton;
    private javax.swing.JRadioButton heartsJRadioButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton passJButton;
    private javax.swing.JLabel remainingSecondsJLabel;
    private javax.swing.JRadioButton spadesJRadioButton;
    // End of variables declaration//GEN-END:variables
}
