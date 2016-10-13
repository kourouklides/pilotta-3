/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IntermediateSinglePlayerRB;

import GUI.AICarpetGUI;
import GUI.AIBiddingGUI;
import Game.Card;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author gmyrianthous
 */
public class RealClient {
    
    private String name;
    private Card[] cards = new Card[8];
    public AICarpetGUI carpet;
    private boolean bitSubmitted = false;
    private String playerBid = "";
    private AIBiddingGUI biddingGUI;
    
    public RealClient(String name) {
        this.name = name;
    } // Client
    
    public RealClient getClient() {
        return this;
    }
    public AIBiddingGUI getBiddingGUI(){
        return this.biddingGUI;
    }
    
    public String getName() {
        return this.name;
    } // getName()
    
    public void setCards(Card[] c) {
        this.cards = c;
    } // setCards
    
    public Card[] getCards() {
        return this.cards;
    } // getCards
    
    public void printCards() {
        System.out.print("Client cards: ");
        for (Card c : this.cards)
            System.out.print(c.getFullCardToString() + ", ");
        System.out.println();
    } // printCards()
    
    public void drawInitialGUI() {
        this.carpet = new AICarpetGUI(this);
        this.carpet.setVisible(true);   
    } // drawInitialGUI
    
    public void drawBiddingGUI(int currentBid, String currentSuit, ArrayList<String> bids){
        this.biddingGUI = new AIBiddingGUI(this, currentBid, currentSuit, bids);
        this.biddingGUI.setVisible(true);
        
        this.bid(currentBid, currentSuit, bids);
    } // drawBiddingGUI
    
    public void bid(int currentBid, String currentSuit, ArrayList<String> bids) {
        String bid = biddingGUI.getBid();

        while (biddingGUI.getBid().equals("")){
            bid = biddingGUI.getBid();
            System.out.println("Bid not yet submitted");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } // catch
        } // while
        
        System.out.println("Player's bid: " + bid);
        this.playerBid = bid;
        this.bitSubmitted = true;
    } // bid
    
    public String getBid(){
        return this.playerBid;
    } // getBid
    
    public boolean hasSubmitted() {
        return this.bitSubmitted;
    } // hasSubmitted
    
    public void setBidSubmitted(boolean b){
        this.bitSubmitted = b;
    } // setBidSubmitted
    
    public void showMessage(String message){
        JOptionPane.showMessageDialog(null, message, "Notification", JOptionPane.INFORMATION_MESSAGE);
    } // showMessage
    
    public void play(ArrayList<String> cardsOnTable, String kozi){
        this.carpet.play(cardsOnTable, kozi);
    } // play
    
    public void drawPlayedCard(String card) {
        this.carpet.drawPlayedCard(card);
    } // drawPlayedCard
    
    public void hideCards() {
        this.carpet.hideCards();
    } // hideCards
    
    public void sendRoundWinner(int firstTeam, int secondTeam){
        this.carpet.updateScore(firstTeam ,secondTeam);
    } // sendRoundWinner
    
    public void updateStatus(String message) {
        this.carpet.updateStatus(message);
    } // updateStatus
    
    public void drawBidding(int bid, String suit, String team) {
        this.carpet.drawBidding(bid, suit, team);
    } // drawBidding
    
    public void disposeGUI() {
        this.carpet.dispose();
    }
} // class Client
