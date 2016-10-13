/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.AboutGUI;
import Game.Card;
import GUI.HowToPlayGUI;
import Game.Suit;
import IntermediateSinglePlayerRB.RealClient;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author gmyrianthous
 */
public class AICarpetGUI extends javax.swing.JFrame {

    private RealClient client;
    private JLabel[] cardsJLabel = new JLabel[8];
    private Card[] cards;
    private boolean isCardSelected = false;
    private String playedCard = null;
    
    /**
     * Creates new form AICarpetUGUI
     */
    public AICarpetGUI(RealClient client) {
        this.client = client;
        this.cards = client.getCards();

        initComponents();
        
        // Draw player's Name.
        drawMyName();

        // Draw bots' names.
        drawBotsNames();

        // Add cards JLabels in an array
        addJLabelsToArray();

        // Draw player's cards.
        drawMyCards();
        
        // Draw bots' cards.
        drawBotsCards();

        // Set initial team points
        setInitialScores();
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
        
    } // AICarpetGUI
    
    public void drawBidding(int bid, String suit, String team) {
        this.biddingJLabel.setText(bid + " of " + suit + " for " + team);
    } // drawBidding
    
    public void drawPlayedCard(String card) {
        String[] splittedCard = card.split("_");
        System.out.println("drawPlayedCard: " + card);
        int playerID = Integer.parseInt(splittedCard[0]);
        String cardFigure = splittedCard[1];
        String cardSuit = splittedCard[2];
        
        Card plCard = new Card(cardFigure, new Suit(cardSuit));
        if (playerID == 1)
            this.bot1CardJLabel.setIcon(plCard.getImage());
        else if (playerID == 2)
            this.bot2CardJLabel.setIcon(plCard.getImage());
        else
            this.bot3CardJLabel.setIcon(plCard.getImage());
    } // drawPlayedCard.

    private void drawMyName() {
        this.playerNameJLabel.setText(this.client.getName());
    } // drawMyName()
    
    public void updateScore(int firstScore, int secondScore) {
        this.firstTeamPointsJLabel.setText(firstScore+"");
        this.secondTeamPointsJLabel.setText(secondScore+"");
    } // updateScore
    
    private void setInitialScores(){
        this.firstTeamPointsJLabel.setText("0");
        this.secondTeamPointsJLabel.setText("0");
    } // setInitialScores
    
    private void drawBotsNames(){
        this.bot1NameJLabel.setText("Bot 1");
        this.bot2NameJLabel.setText("Bot 2");
        this.bot3NameJLabel.setText("Bot 3");
    } // drawBotsNames()
    
    private void drawMyCards() {
       for (int i = 0; i < 8; i++)
           this.cardsJLabel[i].setIcon(this.cards[i].getImage());
    } // drawMyCards()
    
    private void drawBotsCards() {
        this.bot1cardJLabel.setIcon(new ImageIcon(getClass().getResource("/images/card_back.png")));
        this.bot2cardJLabel.setIcon(new ImageIcon(getClass().getResource("/images/card_back.png")));
        this.bot3cardJLabel.setIcon(new ImageIcon(getClass().getResource("/images/card_back.png")));
    } // drawBotscards()
    
    public void updateStatus(String status){
        this.statusJLabel.setText(status);
    } // updateStatus
    
    private void addJLabelsToArray() {
        this.cardsJLabel[0] = this.card1JLabel;
        this.cardsJLabel[1] = this.card2JLabel;
        this.cardsJLabel[2] = this.card3JLabel;
        this.cardsJLabel[3] = this.card4JLabel;
        this.cardsJLabel[4] = this.card5JLabel;
        this.cardsJLabel[5] = this.card6JLabel;
        this.cardsJLabel[6] = this.card7JLabel;
        this.cardsJLabel[7] = this.card8JLabel;
        
        // Name labels (used for mouseListener)
        for (int i = 0; i < cardsJLabel.length; i++)
            this.cardsJLabel[i].setName(this.cards[i].getFullCardToString());
        
    } // addJLabelsToArray
    
    public void play(ArrayList<String> cardsOnTable, String kozi){
        // Set kozi
        for (int i = 0; i < cards.length; i++){
            if (this.cards[i] != null && this.cards[i].getSuit().getSuitToString().equals(kozi))
                this.cards[i].setKozi(true);
        } // for
        
        MyMouseListener myMouseListener = new MyMouseListener();
        this.isCardSelected = false;
        this.playedCard = null;
        
        boolean koziExists = false;
        boolean suitExists = false;
        if (cardsOnTable.size() == 0){ // first to play   
            // Enable all cards.
            for (int i = 0; i < 8; i++)
                if (cards[i] != null)
                    cardsJLabel[i].addMouseListener(myMouseListener);
                System.out.println("Added mouse listeners to all the cards");
        } else if (cardsOnTable.size() == 1){
            String[] splitCard = cardsOnTable.get(0).split("_");
            String cardFigure = splitCard[1];
            String cardSuit = splitCard[2];
            
            if (cardSuit.equals(kozi)) {
                System.out.println("You have to play kozi");
                
                // Enable higher kozia.
                Card playedCard = new Card(cardFigure, new Suit(cardSuit));
                boolean higherKoziFound = false;
                for (int i = 0; i < cards.length; i++) {
                    if (this.cards[i] != null && this.cards[i].isKozi() && this.cards[i].getCardRankKozi() > playedCard.getCardRankKozi() ) {
                        this.cardsJLabel[i].addMouseListener(myMouseListener);
                        higherKoziFound = true;
                        koziExists = true;
                    } // if
                } // for
                
                if (!higherKoziFound) {
                    System.out.println("You don't have any jigher kozia");
                    for (int i = 0; i < cards.length; i++) {
                        if (this.cards[i] != null && this.cards[i].isKozi()) {
                            cardsJLabel[i].addMouseListener(myMouseListener);
                            koziExists = true;
                        } // if
                    } // for                    
                } else
                    System.out.println("Higher kozia have been enabled");
                 
                if (!koziExists) {
                    System.out.println("You don't even have kozi");
                    // Enable all the cards.
                    for (int i = 0; i < this.cards.length; i++){
                        if (this.cards[i] != null)
                            cardsJLabel[i].addMouseListener(myMouseListener);
                    } // for
                    System.out.println("All cards have been enabled");
                } // if
            } else {
                System.out.println("You have to play (not kozi) " + cardSuit);
                for (int i = 0; i < this.cards.length; i++) {
                    if (this.cards[i] != null && this.cards[i].getSuit().getSuitToString().equals(cardSuit)) {
                        cardsJLabel[i].addMouseListener(myMouseListener);
                        suitExists = true;
                    } // if
                } // for
                
                if (!suitExists) {
                    System.out.println("You dont have " + cardSuit);
                    // Enable kozia
                    for (int i = 0; i < this.cards.length; i++) {
                        if (this.cards[i] != null && this.cards[i].isKozi()) {
                            cardsJLabel[i].addMouseListener(myMouseListener);
                            koziExists = true;
                        } // if
                    } // for
                    
                    if (!koziExists) {
                        System.out.println("You don't even have a kozi.");
                        // Enable all cards
                        for (int i = 0; i < this.cards.length; i++) {
                            if (this.cards[i] != null)
                                cardsJLabel[i].addMouseListener(myMouseListener);
                        } // for
                        System.out.println("All cards have been enabled");
                    } else 
                        System.out.println("Kozia have been enabled");
                } else
                    System.out.println("All cards of " + cardSuit + " have been enabled");
            } // else       
        } else if (cardsOnTable.size() == 2) {
            String[] splittedCard1 = cardsOnTable.get(0).split("_");
            String[] splittedCard2 = cardsOnTable.get(1).split("_");
            String figure1 = splittedCard1[1];
            String figure2 = splittedCard2[1];
            String suit1 = splittedCard1[2];
            String suit2 = splittedCard2[2];
            
            if (suit1.equals(kozi)) {
                System.out.println("You have to play kozi");
                
                // Find the maximum kozi on table
                Card maxCard = new Card(figure1, new Suit(suit1));
                if (suit2.equals(kozi)) {
                    Card secondCard = new Card(figure2, new Suit(suit2));
                    
                    if (secondCard.getCardRankKozi() > maxCard.getCardRankKozi())
                        maxCard = secondCard;
                } // if
                
                // Enable only higher kozia
                boolean higherKoziFound = false;
                for (int i = 0; i < this.cards.length; i++) {
                    if (this.cards[i] != null && this.cards[i].isKozi() 
                        && this.cards[i].getCardRankKozi() > maxCard.getCardRankKozi()) {
                            higherKoziFound = true;
                            this.cardsJLabel[i].addMouseListener(myMouseListener);
                    } // if
                } // for
                
                if (!higherKoziFound) {
                    System.out.println("You don't have higher kozia. All kozia will be enabled");
                    for (int i = 0; i < this.cards.length; i++) {
                        if (this.cards[i] != null && this.cards[i].isKozi()) {
                            this.cardsJLabel[i].addMouseListener(myMouseListener);
                            koziExists = true;
                        } // if
                    } // for
                } else 
                    System.out.println("Higher kozia enabled");
                
                if (!koziExists) {
                    System.out.println("You don't have kozia");
                    for (int i = 0; i < this.cards.length; i++) {
                        if (this.cards[i] != null)
                            this.cardsJLabel[i].addMouseListener(myMouseListener);
                    } // for
                } else
                    System.out.println("All kozia have been enabled");
            } else {
                System.out.println("You have to play " + suit1);
                
                for (int i = 0; i < this.cards.length; i++) {
                    if (this.cards[i] != null && this.cards[i].getSuit().getSuitToString().equals(suit1)){
                        this.cardsJLabel[i].addMouseListener(myMouseListener);
                        suitExists = true;
                    } // if
                } // for
                
                if (!suitExists) {
                    System.out.println("You don't have " + suit1);
                    
                    // Enable kozia + check what has been played
                    if (suit2.equals(kozi)) {
                       
                        Card secondCard = new Card(figure2, new Suit(suit2));
                        boolean higherKoziFound = false;
                        for (int i = 0; i < this.cards.length; i++) {
                            if (this.cards[i] != null && this.cards[i].isKozi() && this.cards[i].getCardRankKozi() > secondCard.getCardRankKozi()){
                                this.cardsJLabel[i].addMouseListener(myMouseListener);
                                higherKoziFound = true;
                            } // if
                        } // for
                        
                        if (!higherKoziFound) {
                            System.out.println("Higher kozi was not found");
                            for (int i = 0; i < this.cards.length; i++) {
                                if (this.cards[i] != null && this.cards[i].isKozi()){
                                    this.cardsJLabel[i].addMouseListener(myMouseListener);
                                    koziExists = true;
                                } // if 
                            } // for
                        } else
                            System.out.println("Higher Kozia have been enabled");
                        
                        if (!koziExists) {
                            System.out.println("You don't even have a kozi");
                            for (int i = 0; i < this.cards.length; i++) {
                                if (this.cards[i] != null)
                                    this.cardsJLabel[i].addMouseListener(myMouseListener);
                            } // for
                            System.out.println("All cards are enabled");  
                        } else
                            System.out.println("All kozia have been enabled");  
                    } else {
                        // Enable all kozia
                        for (int i = 0; i < this.cards.length; i++) {
                            if (this.cards[i] != null && this.cards[i].isKozi()) {
                                this.cardsJLabel[i].addMouseListener(myMouseListener);
                                koziExists = true;
                            } // if
                        } // for
                        
                        if (!koziExists) {
                            System.out.println("You don't have kozi");
                            for (int i = 0; i < this.cards.length; i++) {
                                if (this.cards[i] != null)
                                    this.cardsJLabel[i].addMouseListener(myMouseListener);
                            } // for
                            System.out.println("All cards have been enabled");
                        } else
                            System.out.println("Kozia have been enabled");
                    }
                    
                } else {
                    System.out.println("All cards of " + suit1 + " have been enabled");
                } // else
            } // else
            
        } else if (cardsOnTable.size() == 3) {
            String[] splitCard1 = cardsOnTable.get(0).split("_");
            String[] splitCard2 = cardsOnTable.get(1).split("_");
            String[] splitCard3 = cardsOnTable.get(2).split("_");
            String figure1 = splitCard1[1];
            String figure2 = splitCard2[1];
            String figure3 = splitCard3[1];
            String suit1 = splitCard1[2];
            String suit2 = splitCard2[2];
            String suit3 = splitCard3[2];
            
            if (suit1.equals(kozi)) {
                System.out.println("You have to play kozi");
                Card maxCard = new Card(figure1, new Suit(suit1));
                Card secondCard = new Card(figure2, new Suit(suit2));
                Card thirdCard = new Card(figure3, new Suit(suit3));
                if (suit2.equals(kozi)) {
                    if (suit3.equals(kozi)) {
                        System.out.println("ALl players played kozia");
                        if (secondCard.getCardRankKozi() > maxCard.getCardRankKozi())
                            maxCard = secondCard;
                        
                        if (thirdCard.getCardRankKozi() > maxCard.getCardRankKozi())
                            maxCard = thirdCard;
                    } else {
                        System.out.println("2 players played kozia");
                        if (secondCard.getCardRankKozi() > maxCard.getCardRankKozi())
                            maxCard = secondCard;
                    } // else
                    
                    // Enable proper cards
                    boolean higherKoziFound = false;
                    for (int i = 0; i < this.cards.length; i++) {
                        if (this.cards[i] != null && this.cards[i].isKozi() 
                                && this.cards[i].getCardRankKozi() > maxCard.getCardRankKozi()) {
                            this.cardsJLabel[i].addMouseListener(myMouseListener);
                            higherKoziFound = true;
                        } // if
                    } // for
                    
                    if (!higherKoziFound) {
                        System.out.println("You don't have any higher kozia");
                        for (int i = 0; i < this.cards.length; i++) {
                            if (this.cards[i] != null && this.cards[i].isKozi()) {
                                this.cardsJLabel[i].addMouseListener(myMouseListener);
                                koziExists = true;
                            } // if
                        } // for
                    } else
                        System.out.println("Higher kozia have been enabled");
                    
                    if (!koziExists) {
                        System.out.println("You don't even have a kozi");
                        for (int i = 0; i < this.cards.length; i++) {
                            if (this.cards[i] != null)
                                this.cardsJLabel[i].addMouseListener(myMouseListener);
                        } // for
                        System.out.println("All cards are now enabled");
                    } else
                        System.out.println("All kozia have been enabled");                     
                } else {
                    if (suit3.equals(kozi)) {
                        System.out.println("Two players played kozi");
                        
                        if (thirdCard.getCardRankKozi()> maxCard.getCardRankKozi())
                            maxCard = thirdCard;
                    } else {
                       System.out.println("Only one player played kozi"); 
                    } // else
                    
                    // Enable proper cards
                    boolean higherKoziFound = false;
                    for (int i = 0; i < this.cards.length; i++) {
                        if (this.cards[i] != null && this.cards[i].isKozi() 
                                && this.cards[i].getCardRankKozi() > maxCard.getCardRankKozi()) {
                            this.cardsJLabel[i].addMouseListener(myMouseListener);
                            higherKoziFound = true;
                        } // if
                    } // for
                    
                    if (!higherKoziFound) {
                        System.out.println("You don't have any higher kozia");
                        for (int i = 0; i < this.cards.length; i++) {
                            if (this.cards[i] != null && this.cards[i].isKozi()) {
                                this.cardsJLabel[i].addMouseListener(myMouseListener);
                                koziExists = true;
                            } // if
                        } // for
                    } else
                        System.out.println("Higher kozia have been enabled");
                    
                    if (!koziExists) {
                        System.out.println("You don't even have a kozi");
                        for (int i = 0; i < this.cards.length; i++) {
                            if (this.cards[i] != null)
                                this.cardsJLabel[i].addMouseListener(myMouseListener);
                        } // for
                        System.out.println("All cards are now enabled");
                    } else
                        System.out.println("All kozia have been enabled"); 
                } // else
            } else {
                System.out.println("You have to play " + suit1);
                for (int i = 0; i < this.cards.length; i++) {
                    if (this.cards[i] != null && this.cards[i].getSuit().getSuitToString().equals(suit1)){
                        this.cardsJLabel[i].addMouseListener(myMouseListener);
                        suitExists = true;
                    } // if      
                } // for
                
                if (!suitExists) {
                    // Check if anyone has played kozi
                    if (suit2.equals(kozi)) {
                        Card maxCard = new Card(figure2, new Suit(suit2));
                        if (suit3.equals(kozi)) {
                            System.out.println("2 players played a kozi (tsakka)");
                            Card thirdCard = new Card(figure3, new Suit(suit3));
                            
                            if (thirdCard.getCardRankKozi() > maxCard.getCardRankKozi())
                                maxCard = thirdCard;
                        } else {
                            System.out.println("One player played kozi (tsakka)");
                        } // else
                        
                        boolean higherKoziFound = false;
                        for (int i = 0; i < this.cards.length; i++) {
                            if (this.cards[i] != null && this.cards[i].isKozi() 
                                    && this.cards[i].getCardRankKozi() > maxCard.getCardRankKozi()) {
                                      this.cardsJLabel[i].addMouseListener(myMouseListener);
                                      higherKoziFound = true;
                            } // if
                        } // for
                        
                        if (!higherKoziFound) {
                            System.out.println("You don't have any higher kozia");
                            for (int i = 0; i < this.cards.length; i++) {
                                if (this.cards[i] != null && this.cards[i].isKozi()) {
                                    this.cardsJLabel[i].addMouseListener(myMouseListener);
                                    koziExists = true;
                                } // if
                            } // for
                        } else
                            System.out.println("Higher kozia have been enabled");
                        
                        if (!koziExists) {
                            System.out.println("You don't even have a kozi");
                            for (int i = 0; i < this.cards.length; i++){
                                if (this.cards[i] != null)
                                    this.cardsJLabel[i].addMouseListener(myMouseListener);
                            } // for
                            System.out.println("All cards are noe enabled");
                        } else
                            System.out.println("All kozia are now enabled");
             
                    } else {
                        if (suit3.equals(kozi)) {
                            System.out.println("One player played a kozi (tsakka)");
                            Card maxCard = new Card(figure3, new Suit(suit3));
                            
                            boolean higherKoziFound = false;
                            for (int i = 0; i < this.cards.length; i++) {
                                if (this.cards[i] != null && this.cards[i].isKozi() 
                                        && this.cards[i].getCardRankKozi() > maxCard.getCardRankKozi()) {
                                    this.cardsJLabel[i].addMouseListener(myMouseListener);
                                    higherKoziFound = true;
                                } // if
                            } // for
                            
                            if (!higherKoziFound) {
                                System.out.println("You don't have any higher kozia");
                                for (int i = 0; i < this.cards.length; i++) {
                                    if (this.cards[i] != null && this.cards[i].isKozi()) {
                                        this.cardsJLabel[i].addMouseListener(myMouseListener);
                                        koziExists = true;
                                    } // if
                                } // for 
                            } else
                                System.out.println("Higher kozia are now enabled");
                            
                            if (!koziExists) {
                                System.out.println("You don't even have a kozi");
                                for (int i = 0; i < this.cards.length; i++) {
                                    if (this.cards[i] != null)
                                        this.cardsJLabel[i].addMouseListener(myMouseListener);
                                } // for
                                System.out.println("All cards are now enabeld");
                            } else 
                                System.out.println("All kozia are now enabled");
                        } else {
                            System.out.println("Nobody played kozi (tsakka)");
                            for (int i = 0; i < this.cards.length; i++) {
                                if (this.cards[i] != null && this.cards[i].isKozi()) {
                                    this.cardsJLabel[i].addMouseListener(myMouseListener);
                                    koziExists = true;
                                } // if
                            } // for
                            
                            if (!koziExists) {
                                System.out.println("You don't have kozia");
                                for (int i = 0; i < this.cards.length; i++) {
                                    if (this.cards[i] != null)
                                        this.cardsJLabel[i].addMouseListener(myMouseListener);
                                } // for
                                System.out.println("All cards are now enabeld");
                            } else
                                System.out.println("All kozia are now enabled");
                        } // else
                    } // else
                } else
                    System.out.println("All cards of " + suit1 + " are enabaled");
            } // else
            
        } else {
            System.out.println("Something went wrong: class AICarpetGUI, method play");
        } // else
        
    } // play
    
    class MyMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e){
            final JLabel label = (JLabel) e.getSource();
            System.out.println("Player hit card with label -> " + label.getName());
   
            // Disable all other cards (make them non-clickable)
            int selectedCardIndex = 0;
            
            for (int i = 0; i < 8; i++){
                if (cards[i] != null && label.getName().equals(cards[i].getFullCardToString()))
                   selectedCardIndex = i;
                cardsJLabel[i].removeMouseListener(this);
            } // for
            System.out.println("Selected card index = " + selectedCardIndex);
            
            // Draw image
            playerCardJLabel.setIcon(cards[selectedCardIndex].getImage());
            cardsJLabel[selectedCardIndex].setIcon(null);
            
            // Set to null
            cards[selectedCardIndex] = null;
            
            playedCard = label.getName();
            isCardSelected = true;
        } // mouseClicked
    } // class MyMouseListener
    
    public boolean isCardSelected(){
        return this.isCardSelected;
    } // isCardSelected
    
    public String getPlayedCard(){
        return this.playedCard;
    } // getPlayedCard
    
    public void hideCards() {
        this.bot1CardJLabel.setIcon(null);
        this.bot2CardJLabel.setIcon(null);
        this.bot3CardJLabel.setIcon(null);
        this.playerCardJLabel.setIcon(null);
        System.out.println("Cards are now hidden");
    } // hideCards
    
    public void setBlueDeck() {
        this.bot1cardJLabel.setIcon(new ImageIcon(getClass().getResource("/images/card_back.png")));
        this.bot2cardJLabel.setIcon(new ImageIcon(getClass().getResource("/images/card_back.png")));
        this.bot3cardJLabel.setIcon(new ImageIcon(getClass().getResource("/images/card_back.png")));
    } // setBlueDeck
    
    public void setRedDeck() {
        this.bot1cardJLabel.setIcon(new ImageIcon(getClass().getResource("/images/card_back_red.png")));
        this.bot2cardJLabel.setIcon(new ImageIcon(getClass().getResource("/images/card_back_red.png")));
        this.bot3cardJLabel.setIcon(new ImageIcon(getClass().getResource("/images/card_back_red.png")));        
    } // setRedDeck
    
    public void setGreenCarpet() {
        this.carpetJPanel.setBackground(new Color(106,215,117));
    } // setGreenCarpet
    
    public void setBlueCarpet() {
        this.carpetJPanel.setBackground(new Color(65, 105, 225));
    }
    
    public void setGreyCarpet() {
        this.carpetJPanel.setBackground(new Color(Color.GRAY.getRGB()));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem3 = new javax.swing.JMenuItem();
        carpetJPanel = new javax.swing.JPanel();
        playerNameJLabel = new javax.swing.JLabel();
        bot1NameJLabel = new javax.swing.JLabel();
        bot2NameJLabel = new javax.swing.JLabel();
        bot3NameJLabel = new javax.swing.JLabel();
        card1JLabel = new javax.swing.JLabel();
        card2JLabel = new javax.swing.JLabel();
        card3JLabel = new javax.swing.JLabel();
        card4JLabel = new javax.swing.JLabel();
        card5JLabel = new javax.swing.JLabel();
        card6JLabel = new javax.swing.JLabel();
        card7JLabel = new javax.swing.JLabel();
        card8JLabel = new javax.swing.JLabel();
        bot1cardJLabel = new javax.swing.JLabel();
        bot3cardJLabel = new javax.swing.JLabel();
        bot2cardJLabel = new javax.swing.JLabel();
        bot1CardJLabel = new javax.swing.JLabel();
        bot2CardJLabel = new javax.swing.JLabel();
        playerCardJLabel = new javax.swing.JLabel();
        bot3CardJLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        firstTeamPointsJLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        secondTeamPointsJLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        statusJLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        biddingJLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();

        jMenuItem3.setText("jMenuItem3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        carpetJPanel.setBackground(new java.awt.Color(106, 215, 117));

        playerNameJLabel.setText("jLabel1");

        bot1NameJLabel.setText("jLabel2");

        bot2NameJLabel.setText("jLabel3");

        bot3NameJLabel.setText("jLabel4");

        jPanel1.setBackground(new java.awt.Color(233, 233, 181));

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setText("Score board");

        jLabel2.setText("Team A (You)");

        firstTeamPointsJLabel.setText("jLabel3");

        jLabel4.setText("Team B");

        secondTeamPointsJLabel.setText("jLabel5");

        jLabel3.setForeground(new java.awt.Color(255, 51, 51));
        jLabel3.setText("Game status:");

        jLabel5.setForeground(new java.awt.Color(255, 51, 51));
        jLabel5.setText("Bidding results:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(statusJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(biddingJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(firstTeamPointsJLabel)
                            .addComponent(secondTeamPointsJLabel)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(firstTeamPointsJLabel))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(secondTeamPointsJLabel)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 3, Short.MAX_VALUE))
                    .addComponent(biddingJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout carpetJPanelLayout = new javax.swing.GroupLayout(carpetJPanel);
        carpetJPanel.setLayout(carpetJPanelLayout);
        carpetJPanelLayout.setHorizontalGroup(
            carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, carpetJPanelLayout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(card1JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(carpetJPanelLayout.createSequentialGroup()
                        .addComponent(bot3CardJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(playerCardJLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bot2CardJLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(89, 89, 89)
                        .addComponent(bot1CardJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103)
                        .addGroup(carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bot1cardJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(carpetJPanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(bot1NameJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(157, 157, 157))
                    .addGroup(carpetJPanelLayout.createSequentialGroup()
                        .addComponent(card2JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(card3JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(carpetJPanelLayout.createSequentialGroup()
                                .addComponent(card4JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(card5JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(card6JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(card7JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(card8JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(167, 167, 167))
                            .addGroup(carpetJPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bot2cardJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bot2NameJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(97, 97, 97))))))
            .addGroup(carpetJPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(carpetJPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(bot3NameJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(bot3cardJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(carpetJPanelLayout.createSequentialGroup()
                .addGap(406, 406, 406)
                .addComponent(playerNameJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        carpetJPanelLayout.setVerticalGroup(
            carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(carpetJPanelLayout.createSequentialGroup()
                .addGroup(carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(carpetJPanelLayout.createSequentialGroup()
                        .addGap(0, 77, Short.MAX_VALUE)
                        .addComponent(bot2NameJLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bot2cardJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(bot2CardJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(playerCardJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, carpetJPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addGroup(carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(bot3cardJLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bot1cardJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(bot1CardJLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bot3CardJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bot1NameJLabel)
                            .addComponent(bot3NameJLabel))
                        .addGap(107, 107, 107)))
                .addComponent(playerNameJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(carpetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(card1JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card2JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card3JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card4JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card5JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card6JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card7JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card8JLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        playerCardJLabel.getAccessibleContext().setAccessibleDescription("");

        jMenu1.setText("Preferences");

        jMenuItem2.setText("System preferences");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("About");

        jMenuItem1.setText("About Pilotta");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem5.setText("How to play");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Exit");

        jMenuItem4.setText("Exit Game");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(carpetJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 902, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(carpetJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        new SinglePlayerUserPreferencesGUI(this).setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        new AboutGUI().setVisible(true);
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        new HowToPlayGUI().setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel biddingJLabel;
    private javax.swing.JLabel bot1CardJLabel;
    private javax.swing.JLabel bot1NameJLabel;
    private javax.swing.JLabel bot1cardJLabel;
    private javax.swing.JLabel bot2CardJLabel;
    private javax.swing.JLabel bot2NameJLabel;
    private javax.swing.JLabel bot2cardJLabel;
    private javax.swing.JLabel bot3CardJLabel;
    private javax.swing.JLabel bot3NameJLabel;
    private javax.swing.JLabel bot3cardJLabel;
    private javax.swing.JLabel card1JLabel;
    private javax.swing.JLabel card2JLabel;
    private javax.swing.JLabel card3JLabel;
    private javax.swing.JLabel card4JLabel;
    private javax.swing.JLabel card5JLabel;
    private javax.swing.JLabel card6JLabel;
    private javax.swing.JLabel card7JLabel;
    private javax.swing.JLabel card8JLabel;
    private javax.swing.JPanel carpetJPanel;
    private javax.swing.JLabel firstTeamPointsJLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel playerCardJLabel;
    private javax.swing.JLabel playerNameJLabel;
    private javax.swing.JLabel secondTeamPointsJLabel;
    private javax.swing.JLabel statusJLabel;
    // End of variables declaration//GEN-END:variables
}
