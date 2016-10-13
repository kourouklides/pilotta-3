/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IntermediateSinglePlayerRB;

import Game.Card;
import Game.Suit;
import java.util.ArrayList;

/**
 *
 * @author gmyrianthous
 */
public class Calculator {
    
    private ArrayList<String> playedCards;
    private String kozi;
    
    private String winnerTeam;
    private int winnerPlayerID;
    private Card maxCard;
    private int firstTeamPoints, secondTeamPoints;
    private int winnerTeamPoints;
    
    /* Constructor for mini-rounds*/
    public Calculator(ArrayList<String> playedCards, String kozi) {
        this.playedCards = playedCards;
        this.kozi = kozi;
        this.firstTeamPoints = 0;
        this.secondTeamPoints = 0;
        this.winnerTeamPoints = 0;
    } // Calculator
    
    
    
    
    
    private int firstPoints, secondPoints, bidding;
    private String team;
    private int firstRoundPoints, secondRoundPoints;
    
    /* Constructor for rounds */
    public Calculator(int firstPoints, int secondPoints, int bidding, String team) {
        this.firstPoints = firstPoints;
        this.secondPoints = secondPoints;
        this.team = team;
        this.bidding = bidding;
    } // Calculator
    
    public String getRoundWinner() {        
        if (team.equals("Team A")) {
            if (firstPoints >= bidding * 10) {
                // Calculate points
                this.firstRoundPoints = firstPoints / 10 + bidding;
                if (firstPoints % 10 > 6)
                    this.firstRoundPoints++;
                
                this.secondRoundPoints = secondPoints / 10;
                if (secondPoints % 10 > 6)
                    this.secondRoundPoints++;
                return "Team A";
            } else {
                this.firstRoundPoints = 0;
                this.secondRoundPoints = bidding + 16;
                return "Team B";
            } // else
        } else {
            if (secondPoints >= bidding * 10) {
                this.secondRoundPoints = secondPoints / 10 + bidding;
                if (this.secondPoints % 10 > 6)
                    this.secondRoundPoints++;
                
                this.firstRoundPoints = firstPoints / 10;
                if (this.firstRoundPoints % 10 > 6)
                    this.firstRoundPoints++;
                
                return "Team B";
            } else {
                this.firstRoundPoints =  bidding + 16;
                this.secondRoundPoints = 0;
                return "Team A";
            } // else
        } // else      
    } // getRoundWinner
    
    public int getFirstTeamRoundPoints() {
        return this.firstRoundPoints;
    } // getFirstTeamRoundPoints
    
    public int getSecondTeamRoundPoints() {
        return this.secondRoundPoints;
    } // getSecondTeamRoundPoints
    
    
    
    /* Mini round */
    
    public String getWinnerTeam() {        
        String[] splitFirstCard = this.playedCards.get(0).split("_");
        String[] splitSecondCard = this.playedCards.get(1).split("_");
        String[] splitThirdCard = this.playedCards.get(2).split("_");
        String[] splitFourthCard = this.playedCards.get(3).split("_");
        
        int firstPlayerID = Integer.parseInt(splitFirstCard[0]);
        
        Card player1Card, player2Card, player3Card, player4Card;
        
        if (firstPlayerID == 0) {
            player1Card = new Card(splitFirstCard[1], new Suit(splitFirstCard[2]));
            player2Card = new Card(splitSecondCard[1], new Suit(splitSecondCard[2]));
            player3Card = new Card(splitThirdCard[1], new Suit(splitThirdCard[2]));
            player4Card = new Card(splitFourthCard[1], new Suit(splitFourthCard[2]));
        } else if (firstPlayerID == 1) {
            player1Card = new Card(splitFourthCard[1], new Suit(splitFourthCard[2]));
            player2Card = new Card(splitFirstCard[1], new Suit(splitFirstCard[2]));
            player3Card = new Card(splitSecondCard[1], new Suit(splitSecondCard[2]));
            player4Card = new Card(splitThirdCard[1], new Suit(splitThirdCard[2]));            
        } else if (firstPlayerID == 2) {
            player1Card = new Card(splitThirdCard[1], new Suit(splitThirdCard[2]));
            player2Card = new Card(splitFourthCard[1], new Suit(splitFourthCard[2]));
            player3Card = new Card(splitFirstCard[1], new Suit(splitFirstCard[2]));
            player4Card = new Card(splitSecondCard[1], new Suit(splitSecondCard[2]));            
        } else {
            player1Card = new Card(splitSecondCard[1], new Suit(splitSecondCard[2]));
            player2Card = new Card(splitThirdCard[1], new Suit(splitThirdCard[2]));
            player3Card = new Card(splitFourthCard[1], new Suit(splitFourthCard[2]));
            player4Card = new Card(splitFirstCard[1], new Suit(splitFirstCard[2]));            
        } // else
        
        System.out.println("Player 1 played: " + player1Card.getFullCardToString());
        System.out.println("Player 2 played: " + player2Card.getFullCardToString());
        System.out.println("Player 3 played: " + player3Card.getFullCardToString());
        System.out.println("Player 4 played: " + player4Card.getFullCardToString());
        
        // Set kozia
        if (player1Card.getSuit().getSuitToString().equals(kozi))
            player1Card.setKozi(true);
        if (player2Card.getSuit().getSuitToString().equals(kozi))
            player2Card.setKozi(true);        
        if (player3Card.getSuit().getSuitToString().equals(kozi))
            player3Card.setKozi(true);        
        if (player4Card.getSuit().getSuitToString().equals(kozi))
            player4Card.setKozi(true);        
        
        // Find the highest card
        this.maxCard = player1Card;
        this.winnerPlayerID = 0;
        this.winnerTeam = "Team A";

        
        if (player2Card.isKozi() && !maxCard.isKozi()) {
            maxCard = player2Card;
            
            this.winnerPlayerID = 1;
            this.winnerTeam = "Team B";
        }
        else if (player2Card.isKozi() && maxCard.isKozi()){
             if (player2Card.getCardRankKozi() > maxCard.getCardRankKozi()) {
                this.maxCard = player2Card;
                this.winnerPlayerID = 1;
                this.winnerTeam = "Team B";
             } // if
        } else if (!player2Card.isKozi() && !maxCard.isKozi()){
            if (player2Card.getCardRank() > maxCard.getCardRank()) {
                this.maxCard = player2Card;
                this.winnerPlayerID = 1;
                this.winnerTeam = "Team B";
            } // if
        } // else if
        
        if (player3Card.isKozi() && !maxCard.isKozi()) {
            maxCard = player3Card;
            this.winnerPlayerID = 2;
            this.winnerTeam = "Team A";
        }
        else if (player3Card.isKozi() && maxCard.isKozi()){
             if (player3Card.getCardRankKozi() > maxCard.getCardRankKozi()) {
                this.maxCard = player3Card;
                this.winnerPlayerID = 2;
                this.winnerTeam = "Team A";
             } // if
        } else if (!player3Card.isKozi() && !maxCard.isKozi()){
            if (player3Card.getCardRank() > maxCard.getCardRank()) {
                this.maxCard = player3Card;
                this.winnerPlayerID = 2;
                this.winnerTeam = "Team A";
            } // if
        } // else if        

        
        if (player4Card.isKozi() && !maxCard.isKozi()) {
            this.maxCard = player4Card;
            this.winnerPlayerID = 3;
            this.winnerTeam = "Team B";
        }
        else if (player4Card.isKozi() && maxCard.isKozi()){
             if (player4Card.getCardRankKozi() > maxCard.getCardRankKozi()) {
                this.maxCard = player4Card;
                this.winnerPlayerID = 3;
                this.winnerTeam = "Team B";
             } // if
        } else if (!player4Card.isKozi() && !maxCard.isKozi()){
            if (player4Card.getCardRank() > maxCard.getCardRank()) {
                this.maxCard = player4Card;
                this.winnerPlayerID = 3;
                this.winnerTeam = "Team B";
            } // if
        } // else if   
        
        
        // Calculate points
        
        if (player1Card.isKozi()) 
            this.firstTeamPoints += player1Card.getCardPointsKozi();
        else
            this.firstTeamPoints += player1Card.getCardPoints();

        if (player2Card.isKozi()) 
            this.secondTeamPoints += player2Card.getCardPointsKozi();
        else
            this.secondTeamPoints += player2Card.getCardPoints();        
        
        if (player3Card.isKozi()) 
            this.firstTeamPoints += player3Card.getCardPointsKozi();
        else
            this.firstTeamPoints += player3Card.getCardPoints();        
        
        if (player4Card.isKozi()) 
            this.secondTeamPoints += player4Card.getCardPointsKozi();
        else
            this.secondTeamPoints += player4Card.getCardPoints();        
        
        this.winnerTeamPoints = firstTeamPoints + secondTeamPoints;
        
        return this.winnerTeam;
    } // getWinnerTeam
    
    public int getWinnerID() {
        return this.winnerPlayerID;
    } // getWinnerID
    
    public int getFirstTeamPoints() {
        return this.firstTeamPoints;
    } 
    
    public int getSecondTeamPoints() {
        return this.secondTeamPoints;
    }
    
    public int getWinnerTeamPoints() {
        return this.winnerTeamPoints;
    }
    
} // class Calculator
