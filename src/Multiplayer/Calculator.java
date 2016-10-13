/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import Game.Card;

/**
 *
 * @author gmyrianthous
 */
public class Calculator {
    
    private Card[] cards;
    private Team teamA, teamB;
    private int winnerPlayerID;
    
    public Calculator(Card[] cards, Team teamA, Team teamB){
        this.cards = cards;
        this.teamA = teamA;
        this.teamB = teamB;
    } // Calculator
    
    public Team getMiniroundWinnerTeam(){
        Card maxCard = cards[0];
        Team maxCardTeam;
        
        for (int i = 1; i < 4; i++){
            System.out.println("comparing " + maxCard.getFullCardToString() + " with " + cards[i].getFullCardToString());
            maxCard = compareCards(maxCard, cards[i]);
        } // for
        System.out.println("maxCard holder ID -> " + maxCard.getPlayerID());
        setWinnerPlayerID(maxCard.getPlayerID());
        if (maxCard.getPlayerID() == 0 || maxCard.getPlayerID() == 2)
            return teamA; 
        return teamB;
    } // getMiniRoundWinner
    
    private void setWinnerPlayerID(int id){
        this.winnerPlayerID = id;
    } // setWinnerPlayerID
    
    public int getWinnerPlayerID(){
        return this.winnerPlayerID;
    } // getWinnerPlayerID
    
    public int getMiniroundPoints(){
        int overallPoints = 0;
        
        for (int i = 0; i < 4; i++)
            if (cards[i].isKozi())
                overallPoints += cards[i].getCardPointsKozi();
            else 
                overallPoints += cards[i].getCardPoints();
        
        return overallPoints;
    } // getMiniRoundPoints
    
    public Card compareCards (Card card1, Card card2){
        if (card1.isKozi() && !card2.isKozi())
            return card1;
        else if (!card1.isKozi() && card2.isKozi())
            return card2;
        else if (card1.isKozi() && card2.isKozi()) {
          if (card1.getCardRankKozi() > card2.getCardRankKozi())
              return card1;
          else
              return card2;
        } else if (!card1.isKozi() && !card2.isKozi()){
            if (card1.getCardRank() > card2.getCardRank())
                return card1;
            else
                return card2;
        } // else if
            
        return null;
    } // compareCards
    
}
