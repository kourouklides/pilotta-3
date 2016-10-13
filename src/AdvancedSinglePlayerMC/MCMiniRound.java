/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdvancedSinglePlayerMC;

import IntermediateSinglePlayerRB.RealClient;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gmyrianthous
 */
public class MCMiniRound implements Runnable {
    
    private int id, initialPlayerID;
    private RealClient client;
    private MCClient[] bots;
    private String biddedSuit, roundTeam;
    
    private ArrayList<String> cardsOnTable = new ArrayList<String>();
    
    /* Constructor */
    public MCMiniRound(int id, int initialPlayerID, RealClient client, MCClient[] bots,
                        String biddedSuit, String roundTeam) {
        this.id = id;
        this.initialPlayerID = initialPlayerID;
        this.client = client;
        this.bots = bots;
        this.biddedSuit = biddedSuit;
        this.roundTeam = roundTeam;
    } // MCMiniRound
    
    @Override
    public void run() {
        System.out.println("Mini-round " + this.id + " is about to start");
        String currentlySelectedCard = "";
        boolean cardSelected = false;
        
        for (int i = 0; i < 4; i++) {
            if (this.initialPlayerID == 0) { // realClient
                this.client.updateStatus("Your turn to play");
                this.client.play(this.cardsOnTable, this.biddedSuit);
                
                currentlySelectedCard = this.client.carpet.getPlayedCard();
                cardSelected = this.client.carpet.isCardSelected();

                while (!cardSelected){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    } // catch
                    System.out.println("Waiting for player to submit a card");
                    cardSelected = this.client.carpet.isCardSelected();
                } // while
                currentlySelectedCard = this.client.carpet.getPlayedCard();
                System.out.println("Card has been selected");
                System.out.println("Played card: " + currentlySelectedCard);
                
                String play = "0_" + currentlySelectedCard;
                this.cardsOnTable.add(play);
                
                System.out.println(play + " has been added to played cards arrayList");

            } else { // bot
                this.client.updateStatus("Bot " + this.initialPlayerID + " is playing");
                String card = this.bots[this.initialPlayerID -1].play(this.cardsOnTable, this.biddedSuit, roundTeam);
                this.cardsOnTable.add(card);
                System.out.println("Card added to arrayList: " + card);
                this.client.drawPlayedCard(card);
                
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MCMiniRound.class.getName()).log(Level.SEVERE, null, ex);
                }
            } // else
            this.incrementInitialPlayerID();
            
           
        } // for
    } // run
    
    private void incrementInitialPlayerID () {
        this.initialPlayerID++;
        if (this.initialPlayerID > 3)
            this.initialPlayerID = 0;
    } // incrementInitialPlayerID
    
    public ArrayList<String> getCardsOnTable() {
        return this.cardsOnTable;
    } // getCardsOnTable    
    
} // class MCMiniRound
