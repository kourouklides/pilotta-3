/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISimulations;

import AdvancedSinglePlayerMC.MCAITeam;
import AdvancedSinglePlayerMC.MCClient;
import AdvancedSinglePlayerMC.MCTeam;
import Game.Card;
import Game.Deck;
import IntermediateSinglePlayerRB.AIClient;
import IntermediateSinglePlayerRB.AITeam;
import IntermediateSinglePlayerRB.Calculator;
import java.util.ArrayList;

/**
 *
 * @author gmyrianthous
 */
public class IntermediateAdvancedSimulation {
    
    // Monte Carlo
    // MC Clients' IDs: 0, 2
    private MCClient mc1 = new MCClient(0);
    private MCClient mc2 = new MCClient(2);
    private MCAITeam firstTeam = new MCAITeam("Team A", mc1, mc2);
    
    // Rule-based lvl 2 (Intermediate)
    private AIClient ai1 = new AIClient(1);
    private AIClient ai2 = new AIClient(3);
    
    private AITeam secondTeam = new AITeam("Team B", ai1, ai2);
    
    
    private int bidderID;
    private int playerTurnID;
    private ArrayList<String> biddings = new ArrayList<String>();
    
    // Deck
    private Deck deck;
    
    private static int roundPointsTeamA;
    private static int roundPointsTeamB;

    // Constructor
    public IntermediateAdvancedSimulation() {
        this.bidderID = 0;
        this.playerTurnID = 0;
        this.ai1.setTeam(this.secondTeam);
        this.ai2.setTeam(this.secondTeam);
        this.mc1.setTeam(firstTeam);
        this.mc2.setTeam(firstTeam);
        this.roundPointsTeamA = 0;
        this.roundPointsTeamB = 0;
        
    } // Simulation()
    
    public void distributeHands() {
        this.deck = new Deck();
        this.deck.shuffle();
        
        Card[] deckCards = new Card[32];
        deckCards = this.deck.getDeckCards();
        
        Card[] tmpCards = new Card[8];
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
        
        System.out.println("Hands distributed");
        mc1.printCards();
        mc2.printCards();
        ai1.printCards();
        ai2.printCards();
        System.out.println("");
    } // distributeHands()
    
    public String play() {
        System.out.println("Bidding round");
        int biddingRoundID = 0;
        int currentBid = 7;
        String currentSuit = "";
        int winnerBiddingID = 0;
        String winnerTeam = "";
        
        // Sort cards (separation base on their suit)
        mc1.sortCards();
        mc2.sortCards();
        ai1.sortCards();
        ai2.sortCards();
                

        while (biddings.size() < 4                
               || !(this.biddings.get(this.biddings.size()-1).contains("Pass") 
                  && this.biddings.get(this.biddings.size()-2).contains("Pass")
                  && this.biddings.get(this.biddings.size()-3).contains("Pass"))) {
                        
            String botBidding = "";
            
            if (this.bidderID == 0) {
                    botBidding = mc1.bid(biddingRoundID, currentBid, currentSuit, biddings);

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
                botBidding = mc2.bid(biddingRoundID, currentBid, currentSuit, biddings);
                
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
                botBidding = ai1.bid(biddingRoundID, currentBid, currentSuit, biddings);
                
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
                botBidding = ai2.bid(biddingRoundID, currentBid, currentSuit, biddings);
                
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
            return "Skipped";
        } else {
            System.out.println("Winner team: " + winnerTeam);
            System.out.println("Winner bidder: " + winnerBiddingID);
            System.out.println("Declared suit: " + currentSuit);
            System.out.println("Declared bidding: " + currentBid); 

            // Continue with the actual game
            
            
            int teamAwonPoints = 0;
            int teamBwonPoints = 0;
            
            for (int j = 0; j < 8; j++) {
                ArrayList<String> playedCards = new ArrayList<String>();            
                for (int i = 0; i < 4; i++) {
                    //playedCards = new ArrayList<String>();
                    if (this.playerTurnID == 0) {
                        String selectedCard = mc1.play(playedCards, currentSuit, winnerTeam);
                        System.out.println("Player 0 played -> " + selectedCard);
                        playedCards.add(selectedCard);
                    } else if (this.playerTurnID == 1) {
                        String selectedCard = ai1.play(playedCards, currentSuit, winnerTeam);
                        System.out.println("Player 1 played -> " + selectedCard);
                        playedCards.add(selectedCard);
                    } else if (this.playerTurnID == 2) {
                        String selectedCard = mc2.play(playedCards, currentSuit, winnerTeam);
                        System.out.println("Player 2 played -> " + selectedCard);
                        playedCards.add(selectedCard);
                    } else if (this.playerTurnID == 3) {
                        String selectedCard = ai2.play(playedCards, currentSuit, winnerTeam);
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
            
            return roundWinnerTeam;
        } // else
  
    } // play
    
    
    public int getRoundPointsTeamA(){
        return roundPointsTeamA;
    } // getRoundPointsTeamA
    
    public int getRoundPointsTeamB() {
        return roundPointsTeamB;
    } // getRoundPointsTeamB
    
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
    
    
} // class Simulation
