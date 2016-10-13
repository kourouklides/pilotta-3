/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IntermediateSinglePlayerRB;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gmyrianthous
 */
public class AIMiniRound implements Runnable {
    
    private int id, initialPlayerID;
    private RealClient client;
    private AIClient[] bots;
    private String biddedSuit, roundTeam;
    private ArrayList<String> cardsOnTable = new ArrayList<String>();
    
    /* Constructor */
    public AIMiniRound(int id, int initialPlayerID, RealClient client, 
                       AIClient[] bots, String biddedSuit, String roundTeam) {
        this.id = id;
        this.initialPlayerID = initialPlayerID;
        this.client = client;
        this.bots = bots;
        this.biddedSuit = biddedSuit;
        this.roundTeam = roundTeam;
    } // AIMiniRound
    
    @Override
    public void run(){
        System.out.println("\nMini-round " + this.id + " is about to start");
        String currentlySelectedCard = "";
        boolean cardSelected = false;
        
        for (int i = 0; i < 4; i++) {
            if (this.initialPlayerID == 0){ // RealClient
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
            } else { // AIClient
                this.client.updateStatus("Bot " + initialPlayerID +  " is playing");
                String card = this.bots[this.initialPlayerID-1].play(this.cardsOnTable, this.biddedSuit, roundTeam);
                this.cardsOnTable.add(card);
                System.out.println("Card added to arrayList: " + card);

                this.client.drawPlayedCard(card);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } // catch
            } // else
            this.incrementInitialPlayerID();
        } // for       
    } // run
    
    private void incrementInitialPlayerID(){
        this.initialPlayerID++;
        if (this.initialPlayerID > 3)
            this.initialPlayerID = 0;
    } // incrementInitialBidderID
    
    public ArrayList<String> getCardsOnTable() {
        return this.cardsOnTable;
    } // getCardsOnTable
} // class AIMiniRound
