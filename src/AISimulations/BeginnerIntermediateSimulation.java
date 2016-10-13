/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISimulations;

import Game.Card;
import Game.Deck;
import IntermediateSinglePlayerRB.AIClient;
import IntermediateSinglePlayerRB.AITeam;
import IntermediateSinglePlayerRB.Calculator;
import BeginnerSinglePlayerRB.AIClientBeginner;
import BeginnerSinglePlayerRB.AITeamBeginner2;
import java.util.ArrayList;

/**
 *
 * @author gmyrianthous
 */
public class BeginnerIntermediateSimulation {
    // Beginner AI Clients
    private AIClientBeginner aiBeginner1 = new AIClientBeginner(1);
    private AIClientBeginner aiBeginner2 = new AIClientBeginner(3);
    
    // Intermediate AICClients
    private AIClient aiIntermediate1 = new AIClient(0);
    private AIClient aiIntermediate2 = new AIClient(2);
    
    // Two teams
    private AITeamBeginner2 beginnerTeam = new AITeamBeginner2("Team B", aiBeginner1, aiBeginner2);
    private AITeam intermediateTeam = new AITeam("Team A", aiIntermediate1, aiIntermediate2);
    
    // Bidding and game
    private int bidderID;
    private int playerTurnID;
    private ArrayList<String> biddings = new ArrayList<String>();
    private int roundPointsTeamA;
    private int roundPointsTeamB;
    
    // Deck for the game
    private Deck deck;
 
    
    // Constructor
    public BeginnerIntermediateSimulation() {
        
        // Initialise variables
        this.bidderID = 0;
        this.playerTurnID = 0;
        this.roundPointsTeamA = 0;
        this.roundPointsTeamB = 0;
        
        // Set teams for each player
        this.aiBeginner1.setTeam(this.beginnerTeam);
        this.aiBeginner2.setTeam(this.beginnerTeam);
        this.aiIntermediate1.setTeam(this.intermediateTeam);
        this.aiIntermediate2.setTeam(this.intermediateTeam);
        
    } // BeginnerIntermediateSimulation
    
    public void dealDeck() {
        // Instanciate and shuffle the deck
        this.deck = new Deck();
        this.deck.shuffle();
        
        Card[] deckCards = new Card[32];
        deckCards = this.deck.getDeckCards();
        
        Card[] tmpCards = new Card[8];
        for (int i = 0; i < 8; i++)
            tmpCards[i] = deckCards[i];
        this.beginnerTeam.getFirstPlayer().setCards(tmpCards);
        
        tmpCards = new Card[8];
        for (int i = 8; i < 16; i++)
            tmpCards[i-8] = deckCards[i];
        this.beginnerTeam.getSecondPlayer().setCards(tmpCards);
        
        tmpCards = new Card[8];
        for (int i = 16; i < 24; i++)
            tmpCards[i-16] = deckCards[i];
        this.intermediateTeam.getFirstPlayer().setCards(tmpCards);
        
        tmpCards = new Card[8];
        for (int i = 24; i < 32; i++)
            tmpCards[i-24] = deckCards[i];
        this.intermediateTeam.getSecondPlayer().setCards(tmpCards);
        
        System.out.println("Deck dealt");
        
        // Print each player's hand
        this.aiBeginner1.printCards();
        this.aiBeginner2.printCards();
        this.aiIntermediate1.printCards();
        this.aiIntermediate2.printCards();
        
        System.out.println("");
    } // dealDeck
    
    public void play() {
        System.out.println("Bidding round");
        int biddingRoundID = 0;
        int currentBid = 7;
        String currentSuit = "";
        int winnerBiddingID = 0;
        String winnerTeam = "";
        
        // Sort cards of each player
        this.aiBeginner1.sortCards();
        this.aiBeginner2.sortCards();
        this.aiIntermediate1.sortCards();
        this.aiIntermediate2.sortCards();
        
        while (biddings.size() < 4                
               || !(this.biddings.get(this.biddings.size()-1).contains("Pass") 
                  && this.biddings.get(this.biddings.size()-2).contains("Pass")
                  && this.biddings.get(this.biddings.size()-3).contains("Pass"))) {
                        
            String botBidding = "";
            
            if (this.bidderID == 0) {
                    botBidding = aiIntermediate1.bid(biddingRoundID, currentBid, currentSuit, biddings);

                    // Split bidding
                    String[] splittedBid = botBidding.split("_");
                    if (splittedBid.length == 3) {
                       if (Integer.parseInt(splittedBid[1]) > currentBid) {
                           currentBid = Integer.parseInt(splittedBid[1]);
                           currentSuit = splittedBid[2];
                           winnerBiddingID = Integer.parseInt(splittedBid[0]);
                           winnerTeam = "Team A";
                       } // if
                    } // else pass
            } else if (this.bidderID == 2) {
                botBidding = aiIntermediate2.bid(biddingRoundID, currentBid, currentSuit, biddings);
                
                // Split bidding
                String[] splittedBid = botBidding.split("_");
                if (splittedBid.length == 3) {
                   if (Integer.parseInt(splittedBid[1]) > currentBid) {
                       currentBid = Integer.parseInt(splittedBid[1]);
                       currentSuit = splittedBid[2];
                       winnerBiddingID = Integer.parseInt(splittedBid[0]);
                       winnerTeam = "Team A";
                   } // if
                } // else pass                
            } else if (this.bidderID == 1) {
                botBidding = aiBeginner1.bid(biddingRoundID, currentBid, currentSuit, biddings);
                
                // Split bidding
                String[] splittedBid = botBidding.split("_");
                if (splittedBid.length == 3) {
                   if (Integer.parseInt(splittedBid[1]) > currentBid) {
                       currentBid = Integer.parseInt(splittedBid[1]);
                       currentSuit = splittedBid[2];
                       winnerBiddingID = Integer.parseInt(splittedBid[0]);
                       winnerTeam = "Team B";
                   } // if
                } // else pass                
            } else {
                botBidding = aiBeginner2.bid(biddingRoundID, currentBid, currentSuit, biddings);
                
                // Split bidding
                String[] splittedBid = botBidding.split("_");
                if (splittedBid.length == 3) {
                   if (Integer.parseInt(splittedBid[1]) > currentBid) {
                       currentBid = Integer.parseInt(splittedBid[1]);
                       currentSuit = splittedBid[2];
                       winnerBiddingID = Integer.parseInt(splittedBid[0]);
                       winnerTeam = "Team B";
                   } // if
                } // else pass                
            } // else
            
            System.out.println(this.bidderID + " bidding: " + botBidding);
            this.biddings.add(botBidding);
            this.incrementBidderID();
            biddingRoundID++;
        } // while
        
        System.out.println("End of Bidding Round");
        System.out.println("*** Bidding History ***");
        for (int i = 0; i < this.biddings.size(); i++)
            System.out.println(this.biddings.get(i));

        if (this.biddings.get(0).contains("Pass") && this.biddings.get(1).contains("Pass")
                && this.biddings.get(2).contains("Pass") && this.biddings.get(3).contains("Pass")) {
            System.out.println("4 passes -> Skip round");
            this.incrementBidderID();
        } else {
            System.out.println("Winner team: " + winnerTeam);
            System.out.println("Winner bidder: " + winnerBiddingID);
            System.out.println("Declared suit: " + currentSuit);
            System.out.println("Declared bidding: " + currentBid);    
            
            // Continue with the game
            int teamAwonPoints = 0;
            int teamBwonPoints = 0;
            
            for (int j = 0; j < 8; j++){
                ArrayList<String> playedCards = new ArrayList<String>();
                for (int i = 0; i < 4; i++) {
                    if (this.playerTurnID == 0) {
                        String selectedCard = this.aiIntermediate1.play(playedCards, currentSuit, winnerTeam);
                        System.out.println("Player 0 played -> " + selectedCard);
                        playedCards.add(selectedCard);
                    } else if (this.playerTurnID == 1) {
                        String selectedCard = this.aiBeginner1.play(playedCards, currentSuit, winnerTeam);
                        System.out.println("Player 1 played -> " + selectedCard);
                        playedCards.add(selectedCard);
                    } else if (this.playerTurnID == 2) {
                        String selectedCard = this.aiIntermediate2.play(playedCards, currentSuit, winnerTeam);
                        System.out.println("Player 2 played -> " + selectedCard);
                        playedCards.add(selectedCard);
                    } else if (this.playerTurnID == 3) {
                        String selectedCard = this.aiBeginner2.play(playedCards, currentSuit, winnerTeam);
                        System.out.println("Player 3 played -> " + selectedCard);
                        playedCards.add(selectedCard); 
                    } // else if
                    // Increment bidder ID
                    this.incrementPlayerTurnID();                    
                } // for
                
                System.out.println(playedCards.toString());
                // Calculate mini-round winner
                Calculator cal = new Calculator(playedCards, currentSuit);
                String miniRoundWinnerTeam = cal.getWinnerTeam();
                System.out.println("Mini round winner: " + miniRoundWinnerTeam);

                if (miniRoundWinnerTeam.equals("Team A"))
                    teamAwonPoints += cal.getWinnerTeamPoints();
                else
                    teamBwonPoints += cal.getWinnerTeamPoints();
                // End of mini-round            
            } // for
            System.out.println("End of round");
            System.out.println("Team A points: " + teamAwonPoints);
            System.out.println("Team B points: " + teamBwonPoints);
            
            Calculator roundCal = new Calculator(teamAwonPoints, teamBwonPoints, currentBid, winnerTeam);
            String roundWinnerTeam = roundCal.getRoundWinner();
            System.out.println("Round winner: " + roundWinnerTeam);
            
            this.roundPointsTeamA += roundCal.getFirstTeamRoundPoints();
            this.roundPointsTeamB += roundCal.getSecondTeamRoundPoints();
            
            System.out.println("Team A final points (Round): " + this.roundPointsTeamA);
            System.out.println("Team B final points (Round): " + this.roundPointsTeamB);
        } // else
    } // play
    
    private void incrementBidderID() {
        this.bidderID++;
        if (this.bidderID > 3)
            this.bidderID = 0;
    } // incrementBidderID
    
    private void incrementPlayerTurnID() {
        this.playerTurnID++;
        if (this.playerTurnID > 3)
            this.playerTurnID = 0;
    } // incrementPlayerTurnID
    
    public int getRoundPointsTeamA() {
        return this.roundPointsTeamA;
    } // getRoundPointsTeamA
    
    public int getRoundPointsTeamB() {
        return this.roundPointsTeamB;
    } // getRoundPointsTeamA
    
} // class BeginnerIntermediateSimulation
