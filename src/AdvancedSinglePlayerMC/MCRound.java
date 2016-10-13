/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdvancedSinglePlayerMC;

import Game.Card;
import Game.Deck;
import IntermediateSinglePlayerRB.Calculator;
import IntermediateSinglePlayerRB.RealClient;
import java.util.ArrayList;

/**
 *
 * @author gmyrianthous
 */
public class MCRound implements Runnable {
    
    private MCTeam firstTeam;
    private MCAITeam secondTeam;
    private int bidderID;
    
    private RealClient client;
    private MCClient[] bots;
    
    private ArrayList<String> biddings = new ArrayList<String>();
    private int currentBid = 7;
    private String currentSuit;
    private String currentTeamWinner = "";    
    private boolean hasReceived = false;

    private int firstFinalPoints, secondFinalPoints;
    
    private int initialPointsA, initialPointsB;

    private int firstToPlay;
    
    /* Constructor */
    public MCRound(MCTeam firstTeam, MCAITeam secondTeam, int bidderID, int pointsA, int pointsB) {
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.bidderID = bidderID;
        this.firstToPlay = bidderID;
        
        this.client = this.firstTeam.getFirstPlayer();
        bots = new MCClient[3];
        bots[0] = this.secondTeam.getFirstPlayer();
        bots[1] = this.firstTeam.getSecondPlayer();
        bots[2] = this.secondTeam.getSecondPlayer();
        
        this.initialPointsA = pointsA;
        this.initialPointsB = pointsB;
    } // MCRound
    
    @Override
    public void run() {
        System.out.println("New round is about to start");
        
        this.biddings.clear();
        
        // Instanciate a deck, shuffle it and distribute the cards.
        Deck deck = new Deck();
        deck.shuffle();
        
        Card[] deckCards = new Card[32];
        deckCards = deck.getDeckCards();
        
        Card[] tmpCards;
        
        tmpCards = new Card[8];
        for (int i = 0; i < 8; i++) 
            tmpCards[i] = deckCards[i];
        this.firstTeam.getFirstPlayer().setCards(tmpCards);
        
        tmpCards = new Card[8];
        for (int i = 8; i < 16; i++)
            tmpCards[i-8] = deckCards[i];
        this.firstTeam.getSecondPlayer().setCards(tmpCards);
        
        tmpCards = new Card[8];
        for (int i = 16; i < 24; i++)
            tmpCards[i-16] = deckCards[i];
        this.secondTeam.getFirstPlayer().setCards(tmpCards);
        
        tmpCards = new Card[8];
        for (int i = 24; i < 32; i++)
            tmpCards[i-24] = deckCards[i];
        this.secondTeam.getSecondPlayer().setCards(tmpCards);
        
        System.out.println("Cards have been distributed");
        this.firstTeam.getFirstPlayer().printCards();
        this.firstTeam.getSecondPlayer().printCards();
        this.secondTeam.getFirstPlayer().printCards();
        this.secondTeam.getSecondPlayer().printCards();
        
        this.client.drawInitialGUI();
        this.client.sendRoundWinner(initialPointsA, initialPointsB);

        System.out.println("Initial GUI has been sucessfully drawn");
        
        
        
        // Sort AI clients' cards.
        for (int i = 0; i < bots.length; i++)
            bots[i].sortCards();   
        
        // Wait for 3 seconds - give the chance to the player to look at his cards
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } // catch
        
        
        /* Bidding round */ 
        System.out.println("Bidding time");
        int biddingRoundID = 0;
        int counter = 0;
        while ((this.biddings.size() < 4)
               || !(this.biddings.get(this.biddings.size()-1).contains("Pass") 
                    && this.biddings.get(this.biddings.size()-2).contains("Pass")
                    && this.biddings.get(this.biddings.size()-3).contains("Pass"))){
            
            if (this.bidderID == 0){ // RealClient
                this.client.updateStatus("Your turn to bid!");
                this.client.drawBiddingGUI(currentBid, currentSuit, biddings);
                System.out.println("BiddingGUI has been sucessfuly drew");

                this.client.bid(currentBid, currentSuit, biddings);

                System.out.println("Bid submitted");        
                String playerBid = this.client.getBid();
                this.biddings.add(playerBid);
                System.out.println("Player's bid " + playerBid 
                                   + " has been added to biddings arrayList");

                // Update current max bid
                String[] splittedBid = playerBid.split("_");
                if (splittedBid.length == 3){ // else the bid is pass
                    if (Integer.parseInt(splittedBid[1]) > this.currentBid){
                        this.currentBid = Integer.parseInt(splittedBid[1]);
                        this.currentSuit = splittedBid[2];
                        if (Integer.parseInt(splittedBid[0]) == 0 
                            || Integer.parseInt(splittedBid[0]) == 2)
                            this.currentTeamWinner = "Team A";
                        else
                            this.currentTeamWinner = "Team B";
                    } // if
                    System.out.println("Current max bid updated: " + this.currentBid 
                                       + " of " + this.currentSuit + " -> " 
                                       + this.currentTeamWinner);
                } // if                   
            } else { // Bot
                this.client.updateStatus("Bot " + this.bidderID + " is bidding now");
                String botBid = this.bots[this.bidderID-1].bid(biddingRoundID, this.currentBid, this.currentSuit, this.biddings);
                System.out.println("Bot submitted a bid");
                this.biddings.add(botBid);
                System.out.println(this.bots[bidderID-1].getName() + " bid: " + botBid 
                                   + " has been added to biddings arrayList");

                // Update current max bid
                String[] splittedBid = botBid.split("_");
                if (splittedBid.length == 3){ // else the bid is pass
                    if (Integer.parseInt(splittedBid[1]) > this.currentBid){
                        this.currentBid = Integer.parseInt(splittedBid[1]);
                        this.currentSuit = splittedBid[2];
                        if (Integer.parseInt(splittedBid[0]) == 0 
                            || Integer.parseInt(splittedBid[0]) == 2)
                            this.currentTeamWinner = "Team A";
                        else
                            this.currentTeamWinner = "Team B";
                    } // if
                    System.out.println("Current max bid updaed: " + this.currentBid 
                                       + " of " + this.currentSuit + " -> " 
                                       + this.currentTeamWinner);
                } // if    
            } // else
            
            this.incrementBidderID();
            if (counter % 3 == 0 && counter != 0){
                biddingRoundID++;
                System.out.println("Bidding round ID: " + biddingRoundID);
            } // if
            counter++;
        } // while        

        System.out.println("**** Bidding completed ****");
        System.out.println("Printing bidding history: ");
        for (int i = 0; i < this.biddings.size(); i++)
            System.out.println(this.biddings.get(i));
        
        this.client.carpet.drawBidding(this.currentBid, this.currentSuit,this.currentTeamWinner );
        
        // check for 4 pass
        if (this.biddings.size() == 4 && (this.biddings.get(0).contains("Pass"))
            && this.biddings.get(1).contains("Pass") && this.biddings.get(2).contains("Pass")
            && this.biddings.get(3).contains("Pass")){
                System.out.println("4 passes. Round is not played");
                this.client.showMessage("4 passes were declared. Round is skipped.");
        } else {
            this.client.showMessage("Bidding results: " + this.currentBid + " of " 
                                    + this.currentSuit + " for " + this.currentTeamWinner
                                    + ".\n Press OK to continue.");
            
            
            // Mini-round
            int miniRoundID = 0;
            int miniRoundWinner = this.firstToPlay;
            int firstTeamPoints = 0;
            int secondTeamPoints = 0;
            
            // while
            while (miniRoundID < 8) {
                MCMiniRound miniRound = new MCMiniRound(miniRoundID, miniRoundWinner, this.client,
                                                        this.bots, currentSuit, currentTeamWinner);
                miniRound.run();
                System.out.println("Mini-round #" + miniRoundID + " is now completed");
                
                // Hide the cards for the player
                this.client.hideCards();
                
                // Find the winner of the miniRound
                Calculator cal = new Calculator(miniRound.getCardsOnTable(), currentSuit);
                String winnerTeam = cal.getWinnerTeam();
                System.out.println("Winner of Mini-round: " + cal.getWinnerID());
                System.out.println("Points for Team A: " + cal.getFirstTeamPoints());
                System.out.println("Points for Team B: " + cal.getSecondTeamPoints());
                System.out.println("Winner team: " + winnerTeam);
                System.out.println("Winner team points: " + cal.getWinnerTeamPoints());

                if (winnerTeam.equals("Team A"))
                    firstTeamPoints += cal.getWinnerTeamPoints();
                else
                    secondTeamPoints += cal.getWinnerTeamPoints();

                miniRoundWinner = cal.getWinnerID();                
                miniRoundID++;
            } // while
            System.out.println("****Round has ended****");
            System.out.println("Results: ");
            System.out.println("Bidding declared by " + currentTeamWinner);
            System.out.println("Bidding was " + currentBid + " of " + currentSuit);
            System.out.println("Team A collected " + firstTeamPoints);
            System.out.println("Team B collected " + secondTeamPoints);
            
            // Calculate round winner
            Calculator cal = new Calculator(firstTeamPoints, secondTeamPoints, currentBid, currentTeamWinner);
            String roundWinner = cal.getRoundWinner();
            System.out.println("Round Winner is: "  + roundWinner);
            System.out.println("Team A points: " + cal.getFirstTeamRoundPoints());
            System.out.println("Team B points: " + cal.getSecondTeamRoundPoints());
            
            this.firstFinalPoints = cal.getFirstTeamRoundPoints();
            this.secondFinalPoints = cal.getSecondTeamRoundPoints();
            // Send winner to RealClient and update scores
            this.client.sendRoundWinner(firstFinalPoints, secondFinalPoints); 
        } // else
        
        this.client.disposeGUI();
    } // run
    
    private void incrementBidderID() {
        this.bidderID++;
        if (this.bidderID > 3)
            this.bidderID = 0;
    } // incrementBidderID;
    
    public int getFirstTeamPoints() {
        return this.firstFinalPoints;
    } // getFirstTeamPoints
    
    public int getSecondTeamPoints() {
        return this.secondFinalPoints;
    } // getSecondTeamPoints
    
} // class MCRound
