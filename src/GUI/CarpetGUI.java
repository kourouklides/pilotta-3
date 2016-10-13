/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.UserPreferencesGUI;
import GUI.MainSignedIn;
import Game.Suit;
import Game.Player;
import Game.Card;
import Multiplayer.ChatClient;
import Multiplayer.Client;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.xml.datatype.Duration;

/**
 *
 * @author gmyrianthous
 */
public class CarpetGUI extends javax.swing.JFrame {

    /**
     * Creates new form CarpetGUI
     */
    private Player[] carpetPlayers = new Player[4];
    private Client client = MainSignedIn.getClient();
    private String incomingMessage = "";
    private Card[] currentlyPresentedCards = new Card[8];
    private JLabel[] cardsJLabels = new JLabel[8];
    
    private String playedCard = null;
    private boolean isCardSelected = false;
    
    private int clientID = MainSignedIn.getClient().getClientID();
    
    private Card[] cards = new Card[8];
    
    private ArrayList<Card> heartsCards = new ArrayList<Card>();
    private ArrayList<Card> diamondsCards = new ArrayList<Card>();
    private ArrayList<Card> spadesCards = new ArrayList<Card>();
    private ArrayList<Card> clubsCards = new ArrayList<Card>();
    
    private ChatClient chatClient;
    private boolean chatEnabled = true;
    
    public CarpetGUI() throws InterruptedException  {
        this.setResizable(false);
        initComponents(); 
        
        // Draw client's cards
        drawMyCards();
        drawOtherCards();
        drawMyName();       
        
        this.carpetPlayers = MainSignedIn.getClient().getTablePlayers();
        
        Thread.sleep(1000);
        for (int i = 0; i < 4; i++) {
            //this.carpetPlayers[i] = GUI.getClient().tablePlayers[i];
            System.out.println("Received player " + this.carpetPlayers[i].getPlayerName() 
                               + " with ID " + this.carpetPlayers[i].getPlayerID());
        } // for

        drawTeamPlayerName();
        drawOpponentsNames();
        this.updateScore(0, 0);
        
        cards = MainSignedIn.getClient().getClientCards();
        
        this.chatClient = this.client.getChatClient();
        
    } // CarpetGUI()
    
    
    public void play(String previousCards, String suit) {
      try{
        this.gameStatusJLabel.setText("It's your turn to play!!!");
        System.out.println("Your turn to play.");
        System.out.println("Kozi for this round is " + suit);
        
        // Set Kozi
        for (int i = 0; i < currentlyPresentedCards.length; i++){
            if (currentlyPresentedCards[i] != null && currentlyPresentedCards[i].getSuit().getSuitToString().equals(suit))
                currentlyPresentedCards[i].setKozi(true);
        } // for
        
        isCardSelected = false;
        playedCard = null;
        MyMouseListener myMouseListener = new MyMouseListener();
        if (previousCards.equals("")){ // i.e. you are the first player 
            // TODO draw the previously played cards
            for (int i = 0; i < 8; i++)
                if (currentlyPresentedCards[i] != null)
                    cardsJLabels[i].addMouseListener(myMouseListener);
                System.out.println("Added mouse listeners to all the cards"); 
        } else {
            System.out.println("You have to follow the rules.");
            previousCards = previousCards.substring(0, previousCards.length()-1); // remove the last '-' of the string
            System.out.println("Previously played cards: " + previousCards);
            String cardsOnTable = previousCards;
            String splitCardsOnTable[] = cardsOnTable.split("-");
            
            String card = "";
            String cardSuit = "";
            String[] splitCard;
            
            
            boolean cardSuitExists = false;
            boolean koziExists = false;
            
            splitCard = splitCardsOnTable[0].split("_");
            card = splitCard[0];
            cardSuit = splitCard[1];

            // Enable all the cards with the same suit as the previously played card.
            System.out.println("You have to play " + cardSuit);
            
            if (!cardSuit.equals(suit)){ // non-kozi was played
                int koziaCounter = 0;
                for (int i = 0; i < currentlyPresentedCards.length; i++){
                    if (currentlyPresentedCards[i] != null && currentlyPresentedCards[i].getSuit().getSuitToString().equals(cardSuit)){
                        cardsJLabels[i].addMouseListener(myMouseListener);
                        cardSuitExists = true;
                    } // if
                } // for

                // If you don't have cards of the same suit as with previously played card
                // you have to selecte Cozi
                if (cardSuitExists){
                    System.out.println("Cards of " + cardSuit + " have been enabled");
                } else {
                    System.out.println("You don't hold " + cardSuit + ", cozia will be enabled");
                    for (int i = 0; i < currentlyPresentedCards.length; i++){
                       if (currentlyPresentedCards[i] != null && currentlyPresentedCards[i].isKozi()){
                           cardsJLabels[i].addMouseListener(myMouseListener);
                           koziaCounter++;
                           koziExists = true;
                       } // if
                    } // for  

                    // Now check if kozi has been previously played, so enable only higher-value kozia
                    if (koziExists) {
                        System.out.println("Kozia have been enabled");
                        if (splitCardsOnTable.length > 1){
                            String card2;
                            String cardSuit2;
                            String[] splittedCard2;

                            splittedCard2 = splitCardsOnTable[1].split("_");
                            card2 = splittedCard2[0];
                            cardSuit2 = splittedCard2[1];

                            if (cardSuit2.equals(suit)){ // i.e the previous player plaed Kozi
                                // Enable only higher cards - actually disable lower kozia
                                for (int i = 0; i < currentlyPresentedCards.length; i++){
                                    if (currentlyPresentedCards[i] != null && currentlyPresentedCards[i].isKozi()
                                        && currentlyPresentedCards[i].getCardRankKozi() < (new Card(card2, new Suit(cardSuit2))).getCardRankKozi()){
                                            cardsJLabels[i].removeMouseListener(myMouseListener);
                                            koziaCounter--;
                                    } // if
                                } // for
                            } // if

                            if (splitCardsOnTable.length == 3){
                                String card3;
                                String cardSuit3;
                                String[] splittedCard3;                                
                                splittedCard3 = splitCardsOnTable[2].split("_");
                                card3 = splittedCard3[0];
                                cardSuit3 = splittedCard3[1];


                                if (cardSuit3.equals(suit)){
                                    for (int i = 0; i < currentlyPresentedCards.length; i++){
                                        if (currentlyPresentedCards[i] != null && currentlyPresentedCards[i].isKozi()
                                            && currentlyPresentedCards[i].getCardRankKozi() < (new Card(card2, new Suit(cardSuit2))).getCardRankKozi()) {
                                                cardsJLabels[i].removeMouseListener(myMouseListener);
                                                koziaCounter--;
                                        } // if
                                    } // for
                                } // if           
                            } // if
                        } // if
                    } // if
                    
                    // If you don't even have a cozi, then pick whatever you want
                    if (koziExists){
                        System.out.println("Kozia enabled"); 
                        System.out.println("Kozia counter -> " + koziaCounter);
                        if (koziaCounter <= 0){
                            for (int i = 0; i < currentlyPresentedCards.length; i++){
                                if (currentlyPresentedCards[i] != null && currentlyPresentedCards[i].isKozi())
                                    cardsJLabels[i].addMouseListener(myMouseListener);
                            }
                        }
                    } else {
                        System.out.println("You don't even hold kozia. All the cards will now be enabled");
                        for (int i = 0; i < currentlyPresentedCards.length; i++){
                            if (currentlyPresentedCards[i] != null)
                                cardsJLabels[i].addMouseListener(myMouseListener);
                        } // for                    
                    } // else
                } // if 
            } else { // kozi was played
                int koziaCounter = 0;
                System.out.println("Kozi has been played.");
                
                // Check whether the player has at least one kozi
                for (int i = 0; i < currentlyPresentedCards.length; i++){
                    if (currentlyPresentedCards[i] != null && currentlyPresentedCards[i].isKozi())
                        koziExists = true;
                } // for
                
                // if player does not have kozi, then he can play any card.
                if (!koziExists){ 
                    System.out.println("You don't have kozia. Play whatever you want");
                    for (int i = 0; i < currentlyPresentedCards.length; i++)
                        if (currentlyPresentedCards[i] != null)
                            cardsJLabels[i].addMouseListener(myMouseListener);
                } else {
                    System.out.println("You have kozia!");
                    // Enable only the cards which are higher than the played kozi
                    for (int i = 0; i < currentlyPresentedCards.length; i++){
                        if (currentlyPresentedCards[i] != null && currentlyPresentedCards[i].isKozi()
                            && currentlyPresentedCards[i].getCardPointsKozi() > (new Card(card, new Suit(cardSuit))).getCardPointsKozi()){
                                cardsJLabels[i].addMouseListener(myMouseListener);
                                koziaCounter++;
                                System.out.println("Enabling " + currentlyPresentedCards[i].getCardToString());
                        } // if
                    } // for
                    
                    if (splitCardsOnTable.length > 1) {
                        String card2;
                        String card2Suit;
                        String[] splittedCard2;
                        
                        splittedCard2 = splitCardsOnTable[1].split("_");
                        card2 = splittedCard2[0];
                        card2Suit = splittedCard2[1];
                        
                        // Remove mouse listener from cards with lower rank
                        for (int i = 0; i < currentlyPresentedCards.length; i++){
                           if (currentlyPresentedCards[i] != null && currentlyPresentedCards[i].isKozi() 
                               && currentlyPresentedCards[i].getCardRankKozi() < new Card(card2, new Suit(card2Suit)).getCardRankKozi()){
                                   cardsJLabels[i].removeMouseListener(myMouseListener);
                                   koziaCounter--;
                                   System.out.println("Disabling " + currentlyPresentedCards[i].getCardToString());

                           } // if
                        } // for
                        
                        if (splitCardsOnTable.length > 2){
                            String card3;
                            String card3Suit;
                            String[] splittedCard3;
                            
                            splittedCard3 = splitCardsOnTable[2].split("_");
                            card3 = splittedCard3[0];
                            card3Suit = splittedCard3[1];
                            
                            // Remove mouse listener from cards with lower rank
                            for (int i = 0; i < currentlyPresentedCards.length; i++){
                               if (currentlyPresentedCards[i] != null && currentlyPresentedCards[i].isKozi() 
                                   && currentlyPresentedCards[i].getCardRankKozi() < new Card(card3, new Suit(card3Suit)).getCardRankKozi()){
                                       cardsJLabels[i].removeMouseListener(myMouseListener);
                                       koziaCounter--;
                                       System.out.println("Disabling " + currentlyPresentedCards[i].getCardToString());
                               } // if
                            } // for     
                            
                        } // if
                    } // if
                    System.out.println("Kozia counter -> " + koziaCounter);
                    // If you don't have higher kozia, then it's ok to play any of the kozia you have
                    if (koziaCounter <= 0) {
                        System.out.println("You don't have any higher kozia, so select any kozi you like");
                        for (int i = 0; i < currentlyPresentedCards.length; i++){
                            if (currentlyPresentedCards[i] != null && currentlyPresentedCards[i].isKozi())
                                cardsJLabels[i].addMouseListener(myMouseListener);
                        } // for
                    } // if
                    
                } // else
                
            } // else
            

        } // else
        
      } catch (ArrayIndexOutOfBoundsException ex){
          System.err.println("Index out of bounds in CarpetGUI");
          ex.printStackTrace();
      }
  
    } // play
    
    public void playerSelectedCard(String card, int playerID) throws IOException{
        System.out.println("Card name: " + card);
        //System.out.println("picture.exists() ?: " + new java.io.File(getClass().getResource("/images/"+card+".png").toString()).exists());        

        String imageLocation = "/images/"+card+".png";

        int whereToDraw = clientID - playerID;
        System.out.println("Where to draw -> " + whereToDraw);
        
        if (clientID == 0){
            if (playerID == 1)
                playedCard2JLabel.setIcon(new ImageIcon(getClass().getResource(imageLocation)));
            else if (playerID == 2)
                playedCard1JLabel.setIcon(new ImageIcon(getClass().getResource(imageLocation)));
            else
                playedCard3JLabel.setIcon(new ImageIcon(getClass().getResource(imageLocation)));
        } else if (clientID == 1){
            if (playerID == 2)
                playedCard2JLabel.setIcon(new ImageIcon(getClass().getResource(imageLocation)));
            else if (playerID == 3)
                playedCard1JLabel.setIcon(new ImageIcon(getClass().getResource(imageLocation)));
            else
                playedCard3JLabel.setIcon(new ImageIcon(getClass().getResource(imageLocation)));            
        } else if (clientID == 2){
            if (playerID == 3)
                playedCard2JLabel.setIcon(new ImageIcon(getClass().getResource(imageLocation)));
            else if (playerID == 0)
                playedCard1JLabel.setIcon(new ImageIcon(getClass().getResource(imageLocation)));
            else
                playedCard3JLabel.setIcon(new ImageIcon(getClass().getResource(imageLocation)));            
        } else if (clientID == 3){
            if (playerID == 0)
                playedCard2JLabel.setIcon(new ImageIcon(getClass().getResource(imageLocation)));
            else if (playerID == 1)
                playedCard1JLabel.setIcon(new ImageIcon(getClass().getResource(imageLocation)));
            else
                playedCard3JLabel.setIcon(new ImageIcon(getClass().getResource(imageLocation)));            
        } // else if
        
        System.out.println("Sucessfully drew played card");
        
        
    } // playerSelectedCard()
    
    
    class MyMouseListener extends MouseAdapter {
       // @Override
        public void mouseClicked(MouseEvent e){
          try {
            final JLabel label = (JLabel) e.getSource();
            System.out.println("Player hit card with label -> " + label.getName() );
            
            // Disable all the other cards (make them not-clickable)
            int pickCardIndex = 0;
            for (int i = 0; i < 8; i++){
                if (currentlyPresentedCards[i] != null && label.getName().equals(currentlyPresentedCards[i].getFullCardToString()))
                   pickCardIndex = i;
                cardsJLabels[i].removeMouseListener(this);
            } // for
            System.out.println("Selected card index = " + pickCardIndex);

            playedCardJLabel.setIcon(currentlyPresentedCards[pickCardIndex].getImage());
            cardsJLabels[pickCardIndex].setVisible(false);
            
            // Transition of cards TODO: separate transition for each card based on its position
            //label.setLocation(label.getLocation().x, label.getLocation().y - 130);
            //label.getParent().repaint();
            
            // remove the card from player's cards
            currentlyPresentedCards[pickCardIndex] = null;
            
            playedCard = label.getName();
            isCardSelected = true;
          } catch(ArrayIndexOutOfBoundsException ex){
              System.out.println("Carpet-gui (mouseListener) index out of bounds");
              ex.printStackTrace();
          }
        } // mouseClicked
    } // class MyMouseListener
    
    public void hideCards(){
        this.playedCardJLabel.setIcon(null);
        this.playedCard1JLabel.setIcon(null);
        this.playedCard2JLabel.setIcon(null);
        this.playedCard3JLabel.setIcon(null);
        System.out.println("Cards have been sucessfully hidden.");
    } // hideCards
    
    
    public String getPlayedCard() {
        return this.playedCard;
    } // getPlayedCard
    
    public boolean isCardSelected(){
        return this.isCardSelected;
    } // isCardSelected
    
    public void setCardSelected(boolean b){ 
        this.isCardSelected = b;
    } // setCardSelected
    
    public void updateScore(int scoreA, int scoreB) {
        this.teamAScoreJLabel.setText(scoreA+"");
        this.teamBScoreJLabel.setText(scoreB+"");
    } // updateScore
    
    private void drawMyCards(){
     try {
       final ImageIcon image1 = MainSignedIn.getClient().getClientCards()[0].getImage();
       final ImageIcon image2 = MainSignedIn.getClient().getClientCards()[1].getImage();
       final ImageIcon image3 = MainSignedIn.getClient().getClientCards()[2].getImage();
       final ImageIcon image4 = MainSignedIn.getClient().getClientCards()[3].getImage();
       final ImageIcon image5 = MainSignedIn.getClient().getClientCards()[4].getImage();
       final ImageIcon image6 = MainSignedIn.getClient().getClientCards()[5].getImage();
       final ImageIcon image7 = MainSignedIn.getClient().getClientCards()[6].getImage();
       final ImageIcon image8 = MainSignedIn.getClient().getClientCards()[7].getImage();

       card1JLabel.setIcon(image1);
       card2JLabel.setIcon(image2);
       card3JLabel.setIcon(image3);
       card4JLabel.setIcon(image4);
       card5JLabel.setIcon(image5);
       card6JLabel.setIcon(image6);
       card7JLabel.setIcon(image7);
       card8JLabel.setIcon(image8);
       
       
       // Name the labels (used for mouseListener).
       card1JLabel.setName(MainSignedIn.getClient().getClientCards()[0].getFullCardToString());
       card2JLabel.setName(MainSignedIn.getClient().getClientCards()[1].getFullCardToString());
       card3JLabel.setName(MainSignedIn.getClient().getClientCards()[2].getFullCardToString());
       card4JLabel.setName(MainSignedIn.getClient().getClientCards()[3].getFullCardToString());
       card5JLabel.setName(MainSignedIn.getClient().getClientCards()[4].getFullCardToString());
       card6JLabel.setName(MainSignedIn.getClient().getClientCards()[5].getFullCardToString());
       card7JLabel.setName(MainSignedIn.getClient().getClientCards()[6].getFullCardToString());
       card8JLabel.setName(MainSignedIn.getClient().getClientCards()[7].getFullCardToString());
       
       // Add the JLabels to to array
       cardsJLabels[0] = card1JLabel;
       cardsJLabels[1] = card2JLabel;
       cardsJLabels[2] = card3JLabel;
       cardsJLabels[3] = card4JLabel;
       cardsJLabels[4] = card5JLabel;
       cardsJLabels[5] = card6JLabel;
       cardsJLabels[6] = card7JLabel;
       cardsJLabels[7] = card8JLabel;
       
       currentlyPresentedCards = MainSignedIn.getClient().getClientCards();
     } catch(ArrayIndexOutOfBoundsException ex){
         System.err.println("CarpetGUI (drawNAmes) - index out of bounds");
     }
    } // drawMyCards()
    
    private void drawOtherCards(){
        final ImageIcon cardBack = new ImageIcon(getClass().getResource("/images/card_back.png"));
        otherCard1JLabel.setIcon(cardBack);
        otherCard2JLabel.setIcon(cardBack);
        otherCard3JLabel.setIcon(cardBack);     
    } // drawOtherCards()
    
    private void drawMyName() {
        clientNameJLabel.setText(MainSignedIn.getClient().getClientName() + " (You)");
    } // drawMyName
    
    private void drawTeamPlayerName() {
        printCarpetPlayers();
        if (MainSignedIn.getClient().getClientID() == 0){
            teamPlayerNameJLabel.setText(this.carpetPlayers[2].getPlayerName());
        } else if (MainSignedIn.getClient().getClientID() == 1){
            teamPlayerNameJLabel.setText(this.carpetPlayers[3].getPlayerName());
        } else if (MainSignedIn.getClient().getClientID() == 2){
            teamPlayerNameJLabel.setText(this.carpetPlayers[0].getPlayerName());
        } else if (MainSignedIn.getClient().getClientID() == 3) {
            teamPlayerNameJLabel.setText(this.carpetPlayers[1].getPlayerName());
        }
    } // drawTeamPlaerName()
    
    private void drawOpponentsNames() {
      try {
        int myID = MainSignedIn.getClient().getClientID();
        int rightOpponentID = myID + 1;
        if (rightOpponentID > 3) {
            rightOpponentID = 0;
        }
        rightOpponentJLabel.setText(this.carpetPlayers[rightOpponentID].getPlayerName());
        
        int leftOpponentID = myID - 1;
        if (leftOpponentID < 0){
            leftOpponentID = 3;
        }
        leftOpponentJLabel.setText(this.carpetPlayers[leftOpponentID].getPlayerName());
      } catch (ArrayIndexOutOfBoundsException ex){
          System.err.println("CarpetGYU (drawOpponents) - index out of bounds");
          ex.printStackTrace();
      }
    } // drawOpponentsNames()
    
    
    private void printCarpetPlayers(){
        for (Player p : this.carpetPlayers)
            System.out.println("Player " + p.getPlayerName() + " with ID " + p.getPlayerID());
    }

    public void showMessage(String m){
        if (this.chatEnabled)
            this.chatTextArea.append(m + "\n");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jMenuItem1 = new javax.swing.JMenuItem();
        carpetJPanel = new javax.swing.JPanel();
        otherCard1JLabel = new javax.swing.JLabel();
        otherCard2JLabel = new javax.swing.JLabel();
        otherCard3JLabel = new javax.swing.JLabel();
        clientNameJLabel = new javax.swing.JLabel();
        teamPlayerNameJLabel = new javax.swing.JLabel();
        rightOpponentJLabel = new javax.swing.JLabel();
        leftOpponentJLabel = new javax.swing.JLabel();
        scoreJPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        teamAScoreJLabel = new javax.swing.JLabel();
        teamBScoreJLabel = new javax.swing.JLabel();
        gameStatusJLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        card1JLabel = new javax.swing.JLabel();
        playedCard2JLabel = new javax.swing.JLabel();
        playedCard1JLabel = new javax.swing.JLabel();
        playedCard3JLabel = new javax.swing.JLabel();
        card2JLabel = new javax.swing.JLabel();
        card3JLabel = new javax.swing.JLabel();
        card4JLabel = new javax.swing.JLabel();
        card5JLabel = new javax.swing.JLabel();
        card6JLabel = new javax.swing.JLabel();
        card7JLabel = new javax.swing.JLabel();
        card8JLabel = new javax.swing.JLabel();
        playedCardJLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatTextArea = new javax.swing.JTextArea();
        chatJButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        messageTextArea = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        jLabel2.setText("jLabel2");

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        carpetJPanel.setBackground(new java.awt.Color(106, 215, 117));

        scoreJPanel.setBackground(new java.awt.Color(233, 233, 181));
        scoreJPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel4.setFont(new java.awt.Font("Abyssinica SIL", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 153, 255));
        jLabel4.setText("Scoreboard");

        gameStatusJLabel.setBackground(new java.awt.Color(172, 164, 176));

        jLabel1.setText("Team A");

        jLabel5.setText("Team B");

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(227, 12, 0));
        jLabel6.setText("Game Status:");

        javax.swing.GroupLayout scoreJPanelLayout = new javax.swing.GroupLayout(scoreJPanel);
        scoreJPanel.setLayout(scoreJPanelLayout);
        scoreJPanelLayout.setHorizontalGroup(
            scoreJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scoreJPanelLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(scoreJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(65, 65, 65)
                .addGroup(scoreJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(teamAScoreJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(teamBScoreJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, scoreJPanelLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(scoreJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, scoreJPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(gameStatusJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, scoreJPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86))))
        );
        scoreJPanelLayout.setVerticalGroup(
            scoreJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scoreJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(scoreJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(gameStatusJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(scoreJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(teamAScoreJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(scoreJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(teamBScoreJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(12, 12, 12))
        );

        card1JLabel.setLabelFor(card1JLabel);

        jPanel1.setBackground(new java.awt.Color(233, 233, 181));

        chatTextArea.setEditable(false);
        chatTextArea.setBackground(new java.awt.Color(204, 204, 204));
        chatTextArea.setColumns(20);
        chatTextArea.setRows(5);
        jScrollPane1.setViewportView(chatTextArea);

        chatJButton.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        chatJButton.setText("Send message");
        chatJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatJButtonActionPerformed(evt);
            }
        });

        messageTextArea.setBackground(new java.awt.Color(204, 204, 204));
        messageTextArea.setColumns(15);
        messageTextArea.setLineWrap(true);
        messageTextArea.setRows(3);
        jScrollPane2.setViewportView(messageTextArea);

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel7.setText("Type your message:");

        jLabel8.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel8.setText("Chat");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(chatJButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(jLabel8)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chatJButton))
        );

        javax.swing.GroupLayout carpetJPanelLayout = new javax.swing.GroupLayout(carpetJPanel);
        carpetJPanel.setLayout(carpetJPanelLayout);
        carpetJPanelLayout.setHorizontalGroup(
            carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(carpetJPanelLayout.createSequentialGroup()
                .addGroup(carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(carpetJPanelLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(card1JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(card2JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(card3JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(card4JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(card5JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(card6JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(card7JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(card8JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(carpetJPanelLayout.createSequentialGroup()
                        .addGroup(carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(carpetJPanelLayout.createSequentialGroup()
                                .addGap(356, 356, 356)
                                .addGroup(carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(teamPlayerNameJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(otherCard1JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(carpetJPanelLayout.createSequentialGroup()
                                        .addComponent(playedCardJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(179, 179, 179)
                                        .addComponent(rightOpponentJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(playedCard1JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(carpetJPanelLayout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(leftOpponentJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(carpetJPanelLayout.createSequentialGroup()
                                        .addComponent(otherCard3JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(56, 56, 56)
                                        .addComponent(playedCard3JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(229, 229, 229)
                                        .addComponent(playedCard2JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(31, 31, 31)
                                        .addComponent(otherCard2JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(carpetJPanelLayout.createSequentialGroup()
                                .addGap(324, 324, 324)
                                .addComponent(clientNameJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                        .addGroup(carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scoreJPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        carpetJPanelLayout.setVerticalGroup(
            carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, carpetJPanelLayout.createSequentialGroup()
                .addGroup(carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(carpetJPanelLayout.createSequentialGroup()
                        .addGroup(carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(carpetJPanelLayout.createSequentialGroup()
                                .addGap(237, 237, 237)
                                .addComponent(rightOpponentJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(carpetJPanelLayout.createSequentialGroup()
                                .addGap(223, 223, 223)
                                .addComponent(leftOpponentJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(otherCard2JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(otherCard3JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(playedCard3JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(playedCard2JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, carpetJPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(carpetJPanelLayout.createSequentialGroup()
                                .addComponent(teamPlayerNameJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(otherCard1JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(playedCard1JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(96, 96, 96)
                                .addComponent(playedCardJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clientNameJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(carpetJPanelLayout.createSequentialGroup()
                                .addComponent(scoreJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(card8JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card1JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card6JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card2JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card3JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card4JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card5JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card7JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jMenu1.setText("Preferences");

        jMenuItem5.setText("Change preferences");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("About");

        jMenuItem3.setText("About Pilotta");
        jMenu2.add(jMenuItem3);

        jMenuItem4.setText("How to play");
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Exit");

        jMenuItem2.setText("Exit game");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(carpetJPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(carpetJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chatJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatJButtonActionPerformed
        // TODO add your handling code here:
        System.out.println("Chat button pressed");
        if (!this.messageTextArea.getText().equals("")){
            try {
                this.chatClient.chatOutput.writeUTF("chatClient-sending_message");
                String message = this.messageTextArea.getText();
                System.out.println("Sending message: " + message);
                this.chatClient.chatOutput.writeUTF(message);
                this.messageTextArea.setText("");
            } catch(IOException ex){
                System.err.println("Unable to send message");
                ex.printStackTrace();
            } // cathc
        } else {
            System.out.println("Empty message");
        }
  
    }//GEN-LAST:event_chatJButtonActionPerformed

    
    public void changeUserPreferences(boolean deckColour, String carpetColour, boolean chatStatus){
        /* Deck colour */
        ImageIcon cardBack;
        if (deckColour){
            cardBack = new ImageIcon(getClass().getResource("/images/card_back.png"));    
        } else {
            cardBack = new ImageIcon(getClass().getResource("/images/card_back_red.png"));
        } // else
        otherCard1JLabel.setIcon(cardBack);
        otherCard2JLabel.setIcon(cardBack);
        otherCard3JLabel.setIcon(cardBack); 
        
        /* Enable/disable chat */        
        // Show only once
        if (!chatStatus)
            if (chatStatus != this.chatEnabled)
                this.chatTextArea.append("*** You have disabled chat. ***");
        
        this.chatEnabled = chatStatus;
        
        // Change carpet's colour.
        if (carpetColour.equals("Grey")) {
            this.carpetJPanel.setBackground(new Color(Color.GRAY.getRGB()));
        } else if (carpetColour.equals("Blue")) {
            this.carpetJPanel.setBackground(new Color(65, 105, 225));
        } else {
            this.carpetJPanel.setBackground(new Color (106,215,117));
        } // else
        
    } // changeUserPreferences
    
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        new UserPreferencesGUI(this).setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel card1JLabel;
    private javax.swing.JLabel card2JLabel;
    private javax.swing.JLabel card3JLabel;
    private javax.swing.JLabel card4JLabel;
    private javax.swing.JLabel card5JLabel;
    private javax.swing.JLabel card6JLabel;
    private javax.swing.JLabel card7JLabel;
    private javax.swing.JLabel card8JLabel;
    private javax.swing.JPanel carpetJPanel;
    private javax.swing.JButton chatJButton;
    private javax.swing.JTextArea chatTextArea;
    private javax.swing.JLabel clientNameJLabel;
    private javax.swing.JLabel gameStatusJLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel leftOpponentJLabel;
    private javax.swing.JTextArea messageTextArea;
    private javax.swing.JLabel otherCard1JLabel;
    private javax.swing.JLabel otherCard2JLabel;
    private javax.swing.JLabel otherCard3JLabel;
    private javax.swing.JLabel playedCard1JLabel;
    private javax.swing.JLabel playedCard2JLabel;
    private javax.swing.JLabel playedCard3JLabel;
    private javax.swing.JLabel playedCardJLabel;
    private javax.swing.JLabel rightOpponentJLabel;
    private javax.swing.JPanel scoreJPanel;
    private javax.swing.JLabel teamAScoreJLabel;
    private javax.swing.JLabel teamBScoreJLabel;
    private javax.swing.JLabel teamPlayerNameJLabel;
    // End of variables declaration//GEN-END:variables
}
