/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdvancedSinglePlayerMC;

import Game.Card;
import Game.Deck;
import java.util.ArrayList;

/**
 *
 * @author gmyrianthous
 */
public class MonteCarlo {
    
    private ArrayList<String> playedCards;
    private String kozi;
    private Card card;
    private Card[] initialCards;
    private Card[] allPlayedCards;
    
    private double[] cardsProbabilities;
    
    /* Constructor */
    public MonteCarlo(ArrayList<String> playedCards, 
                      String kozi, Card card, Card[] initialCards, Card[] allPlayedCards) {
        this.playedCards = playedCards;
        this.kozi = kozi;
        this.card = card;
        this.initialCards = initialCards;
        this.allPlayedCards = allPlayedCards;
    } // MonteCarlo
    
    public boolean calculateProbabilities() {           
           return roundSimulation(this.card);

    } // calculateProbabilities       
           
    /* This method is used to simulate a round using a given Card */
    public boolean roundSimulation(Card c) {
        
        // Initialize a deck
        Deck deck  = new Deck();
        deck.shuffle();
        Card[] deckCards = deck.getDeckCards();
        
        // Remove deck cards of players.
        for (int i = 0; i < this.initialCards.length; i++) {
            for (int j = 0; j < deckCards.length; j++)
                if (deckCards[j] != null && this.initialCards[i] != null &&
                        deckCards[j].getFullCardToString().equals(this.initialCards[i].getFullCardToString()))
                            deckCards[j] = null;
        } // for

        // Remove all the other cards that have already been played
        
        for (int i = 0; i < this.allPlayedCards.length; i++) {
            for (int j = 0; j < deckCards.length; j++)
                if (deckCards[j] != null && allPlayedCards[i] != null && 
                        deckCards[j].getFullCardToString().equals(this.allPlayedCards[i].getFullCardToString()))
                            deckCards[j] = null;
        } // for
                
        
        // Simulate a round using 3 random cards.
        Card card1 = null;
        Card card2 = null;
        Card card3 = null;
        for (int i = 0; i < deckCards.length; i++) 
            if (deckCards[i] != null){
                card1 = deckCards[i];
                deckCards[i] = null;
                break;
            } // if
        
        for (int i = 0; i < deckCards.length; i++) 
            if (deckCards[i] != null){
                card2 = deckCards[i];
                deckCards[i] = null;
                break;
            } // if        

        for (int i = 0; i < deckCards.length; i++) 
            if (deckCards[i] != null){
                card3 = deckCards[i];
                deckCards[i] = null;
                break;
            } // if        
        
        if (card1 != null && card2 != null && card3 != null) {
            
        } else
            System.out.println("A card is null. Error in monte carlo");
        
        // Set kozi
        if (c != null && c.getSuit().getSuitToString().equals(kozi))
            c.setKozi(true);
        
        if (card1 != null && card1.getSuit().getSuitToString().equals(kozi))
            card1.setKozi(true);
        
        if (card2 != null && card2.getSuit().getSuitToString().equals(kozi))
            card2.setKozi(true);        
        
        if (card3 != null && card3.getSuit().getSuitToString().equals(kozi))
            card3.setKozi(true);
        
        // Check the result.
        boolean result = getSimulatedRoundWinner(c, card1, card2, card3);
       
        return result;
        
    } // gameSimulation

    private boolean getSimulatedRoundWinner(Card card0, Card card1, Card card2, Card card3) {         
         Card maxCard = card0;
         
         if (card0 == null)
             System.err.println("card0 is null");
         
         if (card1 == null)
             System.err.println("card1 is null");
         
         // Compare card0 and card1
         if (!maxCard.isKozi() && card1.isKozi()) {
             maxCard = card1;
             return false;
         } else if (maxCard.isKozi() && card1.isKozi()) {
             if (card1.getCardRankKozi() > maxCard.getCardRankKozi()){
                 maxCard = card1;
                 return false;
             } // if       
         } else if (!maxCard.isKozi() && !card1.isKozi()) {
             if (card1.getCardRank() > maxCard.getCardRank()) {
                 maxCard = card1;
                 return false;
             } // if
         } // else if
         
         // Compare card0 and card2
         if (!maxCard.isKozi() && card2.isKozi()) {
             maxCard = card2;
             return false;
         } else if (maxCard.isKozi() && card2.isKozi()) {
             if (card2.getCardRankKozi() > maxCard.getCardRankKozi()){
                 maxCard = card2;
                 return false;
             } // if       
         } else if (!maxCard.isKozi() && !card2.isKozi()) {
             if (card2.getCardRank() > maxCard.getCardRank()) {
                 maxCard = card2;
                 return false;
             } // if
         } // else if         
         
         // Compare card0 and card3
         if (!maxCard.isKozi() && card3.isKozi()) {
             maxCard = card3;
             return false;
         } else if (maxCard.isKozi() && card3.isKozi()) {
             if (card3.getCardRankKozi() > maxCard.getCardRankKozi()){
                 maxCard = card3;
                 return false;
             } // if       
         } else if (!maxCard.isKozi() && !card3.isKozi()) {
             if (card3.getCardRank() > maxCard.getCardRank()) {
                 maxCard = card3;
                 return false;
             } // if
         } // else if 
         
         return true;
    } // getSimulatedRoundWinner
        
} // class MonteCarlo
