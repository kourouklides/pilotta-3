/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdvancedSinglePlayerMC;

import Game.Card;
import Game.Suit;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author gmyrianthous
 */
public class MCClient {
    
    private int id;
    private String name;
    
    private MCTeam team;
    private MCAITeam aiTeam;
    
    private Card[] cards = new Card[8];
    private Card[] initialCards = new Card[8];
    private Card[] allPlayedCards; 
    int currentlyPlayedCards;
    private double[] cardProbabilities = new double[8];
    
    private ArrayList<Card> heartsCards;
    private ArrayList<Card> diamondsCards;
    private ArrayList<Card> clubsCards;
    private ArrayList<Card> spadesCards;
    
    /* Constructor */
    public MCClient(int id){
        this.id = id;
        this.name = "Bot " + this.id;
        
        this.heartsCards = new ArrayList<Card>();
        this.diamondsCards = new ArrayList<Card>();
        this.clubsCards = new ArrayList<Card>();
        this.spadesCards = new ArrayList<Card>();   
        this.currentlyPlayedCards = 0;
        allPlayedCards = new Card[32];
    } // MMClient

    public String play(ArrayList<String> cardsOnTable, String kozi, String team) {
        String selectedCard = "";
        
        Arrays.fill(cardProbabilities, 0.0);
        
        // Add all the cards that have already been played
        for (int i = 0; i < cardsOnTable.size(); i++) {
            System.out.println("Splitting card: " + cardsOnTable.get(i));
            String splitCard[] = cardsOnTable.get(i).split("_");
            System.out.println("Currently played cards:" + currentlyPlayedCards);
            this.allPlayedCards[currentlyPlayedCards] = new Card(splitCard[1], new Suit(splitCard[2]));
            currentlyPlayedCards++;
        } // for
        
        System.out.println("CurrentlyPlayedCards: " + this.currentlyPlayedCards);
        System.out.println("allPlayedCards length: " + this.allPlayedCards.length);
 
        int noOfSimulations = 450;
        
        if (cardsOnTable.size() == 0) {
            System.out.println("You are the first who plays a card:No restrictions");
   
            // Simulate games
            MonteCarlo mc;
            for (int cardIndex = 0; cardIndex < this.cards.length; cardIndex++) {
                for (int i = 0; i < noOfSimulations; i++) {
                    if (this.cards[cardIndex] != null) {
                        mc = new MonteCarlo(cardsOnTable, kozi, this.cards[cardIndex],
                                                        this.initialCards, this.allPlayedCards); 
                        boolean result = mc.calculateProbabilities();
                        if (result)
                           this.cardProbabilities[cardIndex] += 1;
                    } // if
                } // for
            } //for
            
            // Normalization of probabilities
            for (int i = 0; i < 8; i++)
                this.cardProbabilities[i] = this.cardProbabilities[i] / noOfSimulations;
            
            
            printCardProbabilities();
            
            // Get the card with the highest probability.
            Card maxProbCard = null;
            double maxProbability = 0.0;
            int maxIndex = -1;
            for (int i = 0; i < this.cards.length; i++) {
                if (this.cards[i] != null) {
                    maxProbCard = this.cards[i];
                    maxProbability = this.cardProbabilities[i];
                    maxIndex = i;
                    break;
                } // if
            } // for
            
            for (int i = 0; i < this.cards.length; i++)
                if (this.cards[i] != null && this.cardProbabilities[i] > maxProbability){
                    maxProbability = this.cardProbabilities[i];
                    maxProbCard = this.cards[i];
                    maxIndex = i;
                } // if
            
            System.out.println("Card with the highest probability is: " 
                                + maxProbCard.getFullCardToString() 
                                + " with probability " + maxProbability);
                 
            selectedCard = maxProbCard.getFullCardToString();
            this.cards[maxIndex] = null;
        } else if (cardsOnTable.size() == 1) {
            System.out.println("One card has already been played");
            String splitCard1[] = cardsOnTable.get(0).split("_");
            Card card1 = new Card(splitCard1[1], new Suit(splitCard1[2]));
            
            if (card1.getSuit().getSuitToString().equals(kozi)) {
                System.out.println("Only kozia should be simulated");
                
                // Find valid cards.
                Card[] validCards = new Card[8];
                
                boolean higherKoziExists = false;
                for (int i = 0; i < this.cards.length; i++) {
                    if (this.cards[i] != null && this.cards[i].getSuit().getSuitToString().equals(kozi) 
                            && this.cards[i].getCardRankKozi() > card1.getCardRankKozi()) {
                        higherKoziExists = true;
                        validCards[i] = this.cards[i];
                    } // if
                } // for
                
                boolean koziExists = false;
                if (!higherKoziExists) {
                    System.out.println("Higher kozia were not found.");
                    System.out.println("Probability of winning is 0");
                    for (int i = 0; i < this.cards.length; i++) {
                        if (this.cards[i] != null && this.cards[i].getSuit().getSuitToString().equals(kozi)) {
                            koziExists = true;
                            validCards[i] = this.cards[i];
                        } // if
                    } // for    
                } else
                    System.out.println("Higher kozia will be simulated");
                
                if (!koziExists && !higherKoziExists) {
                    System.out.println("There are no kozia. Probability of winning is 0");
                    System.out.println("All cards will be simulated");
                    for (int i = 0; i < this.cards.length; i++) {
                        if (this.cards[i] != null)
                            validCards[i] = this.cards[i];
                    } // for
                } // if
                
                // Simulate games
                MonteCarlo mc;
                for (int cardIndex = 0; cardIndex < validCards.length; cardIndex++) {
                    for (int i = 0; i < noOfSimulations; i++) {
                        if (validCards[cardIndex] != null) {
                            mc = new MonteCarlo(cardsOnTable, kozi, validCards[cardIndex],
                                                            this.initialCards, this.allPlayedCards); 
                            boolean result = mc.calculateProbabilities();
                            if (result)
                               this.cardProbabilities[cardIndex] += 1;
                        } // if
                    } // for
                } // for
                
                // Normalize probabilities
                for (int i = 0; i < this.cardProbabilities.length; i++)
                    this.cardProbabilities[i] /= noOfSimulations;

                // Print valid cards.
                for (int i = 0; i < validCards.length; i++)
                    if (validCards[i] != null)
                        System.out.println("Card: " + validCards[i].getFullCardToString());
                    else
                        System.out.println("Card: null");                
                
                printCardProbabilities();
                
                // if higher kozi exists get max else get min probability
                if (higherKoziExists) {
                    System.out.println("Get max card");
                    
                    // Get the card with the highest probability.
                    Card maxProbCard = null;
                    double maxProbability = 0.0;
                    int maxIndex = -1;
                    for (int i = 0; i < validCards.length; i++) {
                        if (validCards[i] != null) {
                            maxProbCard = validCards[i];
                            maxProbability = this.cardProbabilities[i];
                            maxIndex = i;
                            break;
                        } // if
                    } // for

                    for (int i = 0; i < validCards.length; i++)
                        if (validCards[i] != null && this.cardProbabilities[i] > maxProbability){
                            maxProbability = this.cardProbabilities[i];
                            maxProbCard = validCards[i];
                            maxIndex = i;
                        } // if

                    System.out.println("Card with the highest probability is: " 
                                        + maxProbCard.getFullCardToString() 
                                        + " with probability " + maxProbability);

                    selectedCard = maxProbCard.getFullCardToString();
                    this.cards[maxIndex] = null;                  
                } else {
                    System.out.println("Probability of winning is 0. Get minimum card");
                    
                    Card minProbCard = null;
                    double minProbability = 1.1;
                    int minIndex = -1;
                    
                    for (int i = 0; i < validCards.length; i++) {
                        if (validCards[i] != null) {
                            minProbCard = validCards[i];
                            minProbability = this.cardProbabilities[i];
                            minIndex = i;
                            break;
                        } // if
                    } // for
                    
                    for (int i = 1; i < validCards.length; i++) {
                        if (validCards[i] != null && this.cardProbabilities[i] < minProbability) {
                            minProbability = this.cardProbabilities[i];
                            minProbCard = validCards[i];
                            minIndex = i;
                        }  // if 
                    } // for
                    System.out.println("Card with the lowest probability is: " 
                                        + minProbCard.getFullCardToString()
                                        + " with probability " + minProbability);
                    
                    selectedCard = minProbCard.getFullCardToString();
                    this.cards[minIndex] = null;
                } // else
                
            } else {
                System.out.println(card1.getSuit().getSuitToString() + " should be played");
                
                Card[] validCards = new Card[8];
                // Simulate cards of this particular suit.
                boolean suitExists = false;
                for (int i = 0; i < this.cards.length; i++) {
                    if (this.cards[i] != null 
                            && this.cards[i].getSuit().getSuitToString().equals(card1.getSuit().getSuitToString())) {
                        suitExists = true;
                        validCards[i] = this.cards[i];
                    } // if
                } // for
                
                boolean koziExists = false;
                if (!suitExists) {
                    System.out.println("You don't have " + card1.getSuit().getSuitToString());
                    System.out.println("Kozia will be enabled");
                    
                    for (int i = 0; i < this.cards.length; i++) {
                        if (this.cards[i] != null && this.cards[i].getSuit().getSuitToString().equals(kozi)) {
                            koziExists = true;
                            validCards[i] = this.cards[i];
                        } // if
                    } // for
                } // if
                
  
                if (!koziExists && !suitExists) {
                    System.out.println("No kozi exists, all cards will be simulated");
                    for (int i = 0; i < this.cards.length; i ++) {
                        if (this.cards[i] != null) {
                            validCards[i] = this.cards[i];
                        } // if
                    } // for
                } // 

                
                // Simulate games
                MonteCarlo mc;
                for (int cardIndex = 0; cardIndex < validCards.length; cardIndex++) {
                    for (int i = 0; i < noOfSimulations; i++) {
                        if (validCards[cardIndex] != null) {
                            mc = new MonteCarlo(cardsOnTable, kozi, validCards[cardIndex],
                                                            this.initialCards, this.allPlayedCards); 
                            boolean result = mc.calculateProbabilities();
                            if (result)
                               this.cardProbabilities[cardIndex] += 1;
                        } //if
                    } // for
                } //for
                 
                // Normalize probabilities
                for (int i = 0; i < this.cardProbabilities.length; i++)
                    this.cardProbabilities[i] /= noOfSimulations;

                // Print valid cards.
                for (int i = 0; i < validCards.length; i++)
                    if (validCards[i] != null)
                        System.out.println("Card: " + validCards[i].getFullCardToString());
                    else
                        System.out.println("Card: null");                
                
                printCardProbabilities();   
                
                
                
                if (!suitExists && !koziExists) {
                    System.out.println("Probability of winning is 0. Min card will be selected");
                    Card minProbCard = null;
                    double minProbability = 1.1;
                    int minIndex = -1;
                    
                    for (int i = 0; i < validCards.length; i++) {
                        if (validCards[i] != null) {
                            minProbCard = validCards[i];
                            minProbability = this.cardProbabilities[i];
                            minIndex = i;
                            break;
                        } // if
                    } // for
                    
                    for (int i = 1; i < this.cards.length; i++) {
                        if (this.cards[i] != null && this.cardProbabilities[i] < minProbability) {
                            minProbability = this.cardProbabilities[i];
                            minProbCard = this.cards[i];
                            minIndex = i;
                        }  // if 
                    } // for
                    System.out.println("Card with the lowest probability is: " 
                                        + minProbCard.getFullCardToString()
                                        + " with probability " + minProbability);
                    
                    selectedCard = minProbCard.getFullCardToString();
                    this.cards[minIndex] = null;                    
                } else {
                    System.out.println("Max card will be selected");
                    
                    // Get the card with the highest probability.
                    Card maxProbCard = null;
                    double maxProbability = 0.0;
                    int maxIndex = -1;
                    for (int i = 0; i < this.cards.length; i++) {
                        if (validCards[i] != null) {
                            maxProbCard = validCards[i];
                            maxProbability = this.cardProbabilities[i];
                            maxIndex = i;
                            break;
                        } // if
                    } // for

                    for (int i = 1; i < validCards.length; i++)
                        if (validCards[i] != null && this.cardProbabilities[i] > maxProbability){
                            maxProbability = this.cardProbabilities[i];
                            maxProbCard = validCards[i];
                            maxIndex = i;
                        } // if

                    System.out.println("Card with the highest probability is: " 
                                        + maxProbCard.getFullCardToString() 
                                        + " with probability " + maxProbability);

                    selectedCard = maxProbCard.getFullCardToString();
                    this.cards[maxIndex] = null;                     
                } // else
                
            } // else
            
        } else if (cardsOnTable.size() == 2) {
            System.out.println("2 cards have already been played");
            // Analyze played cards.
            String[] splitCard1 = cardsOnTable.get(0).split("_");
            String[] splitCard2 = cardsOnTable.get(1).split("_");
            Card card1 = new Card(splitCard1[1], new Suit(splitCard1[2]));
            Card card2 = new Card(splitCard2[1], new Suit(splitCard2[2]));
            
            if (card1.getSuit().getSuitToString().equals(kozi)) {
                System.out.println("You must play kozi");
                Card[] validCards = new Card[8];
                
                // Find max card
                Card maxCard = card1;
                if (card2.getSuit().getSuitToString().equals(kozi) && card2.getCardRankKozi() > maxCard.getCardRankKozi())
                    maxCard = card2;
                
                boolean higherKoziExists = false;
                for (int i = 0; i < this.cards.length; i++) {
                    if (this.cards[i] != null && this.cards[i].getSuit().getSuitToString().equals(kozi)
                            && this.cards[i].getCardRankKozi() > maxCard.getCardRankKozi()) {
                        higherKoziExists = true;
                        validCards[i] = this.cards[i];
                    } // if
                } // for
                
                boolean koziExists = false;
                if (!higherKoziExists) {
                    System.out.println("You don't have higher kozia. All kozia will be simulated");
                    for (int i = 0; i < this.cards.length; i++) {
                        if (this.cards[i] != null && this.cards[i].getSuit().getSuitToString().equals(kozi)) {
                            koziExists = true;
                            validCards[i] = this.cards[i];
                        } // if
                    } // for
                } // if
                
                if (!koziExists && !higherKoziExists) {
                    System.out.println("You don't have kozia. All cards will be simulated");
                    for (int i = 0; i < this.cards.length; i++) {
                        if (this.cards[i] != null)
                            validCards[i] = this.cards[i];
                    } // for
                } // if
                
                // Simulate games using only valid cards.
                MonteCarlo mc;
                for (int cardIndex = 0; cardIndex < validCards.length; cardIndex++) {
                    for (int i = 0; i < noOfSimulations; i++) {
                        if (validCards[cardIndex] != null) {
                            mc = new MonteCarlo(cardsOnTable, kozi, validCards[cardIndex],
                                                            this.initialCards, this.allPlayedCards); 
                            boolean result = mc.calculateProbabilities();
                            if (result)
                               this.cardProbabilities[cardIndex] += 1;
                        } //if
                    } // for
                } //for
                 
                // Normalize probabilities
                for (int i = 0; i < this.cardProbabilities.length; i++)
                    this.cardProbabilities[i] /= noOfSimulations;

                // Print valid cards.
                for (int i = 0; i < validCards.length; i++)
                    if (validCards[i] != null)
                        System.out.println("Card: " + validCards[i].getFullCardToString());
                    else
                        System.out.println("Card: null");
                
                printCardProbabilities();                 
                
                if (!koziExists && !higherKoziExists) {
                    System.out.println("Probability of winning is 0. Get the least possible card");
                    Card minProbCard = null;
                    double minProbability = 1.1;
                    int minIndex = -1;
                    
                    for (int i = 0; i < validCards.length; i++) {
                        if (validCards[i] != null) {
                            minProbCard = validCards[i];
                            minProbability = this.cardProbabilities[i];
                            minIndex = i;
                            break;
                        } // if
                    } // for
                    
                    for (int i = 1; i < this.cards.length; i++) {
                        if (this.cards[i] != null && this.cardProbabilities[i] < minProbability) {
                            minProbability = this.cardProbabilities[i];
                            minProbCard = this.cards[i];
                            minIndex = i;
                        }  // if 
                    } // for
                    System.out.println("Card with the lowest probability is: " 
                                        + minProbCard.getFullCardToString()
                                        + " with probability " + minProbability);
                    
                    selectedCard = minProbCard.getFullCardToString();
                    this.cards[minIndex] = null;                    
                } else {
                    System.out.println("Max card will be selected");
                    
                    // Get the card with the highest probability.
                    Card maxProbCard = null;
                    double maxProbability = 0.0;
                    int maxIndex = -1;
                    for (int i = 0; i < this.cards.length; i++) {
                        if (validCards[i] != null) {
                            maxProbCard = validCards[i];
                            maxProbability = this.cardProbabilities[i];
                            maxIndex = i;
                            break;
                        } // if
                    } // for

                    for (int i = 1; i < validCards.length; i++)
                        if (validCards[i] != null && this.cardProbabilities[i] > maxProbability){
                            maxProbability = this.cardProbabilities[i];
                            maxProbCard = validCards[i];
                            maxIndex = i;
                        } // if

                    System.out.println("Card with the highest probability is: " 
                                        + maxProbCard.getFullCardToString() 
                                        + " with probability " + maxProbability);

                    selectedCard = maxProbCard.getFullCardToString();
                    this.cards[maxIndex] = null;                     
                } // else
                
            } else {
                System.out.println("You must play " + card1.getSuit().getSuitToString());
                Card[] validCards = new Card[8];
                
                // Simulate cards of this particular suit.
                boolean suitExists = false;
                for (int i = 0; i < this.cards.length; i++) {
                    if (this.cards[i] != null 
                            && this.cards[i].getSuit().getSuitToString().equals(card1.getSuit().getSuitToString())) {
                        suitExists = true;
                        validCards[i] = this.cards[i];
                    } // if
                } // for
                
                boolean koziExists = false;
                if (!suitExists) {
                    System.out.println("You don't have " + card1.getSuit().getSuitToString());
                    System.out.println("Kozia will be enabled");
                    
                    for (int i = 0; i < this.cards.length; i++) {
                        if (this.cards[i] != null && this.cards[i].getSuit().getSuitToString().equals(kozi)) {
                            koziExists = true;
                            validCards[i] = this.cards[i];
                        } // if
                    } // for
                } // if
                
  
                if (!koziExists && !suitExists) {
                    System.out.println("No kozi exists, all cards will be simulated");
                    for (int i = 0; i < this.cards.length; i ++) {
                        if (this.cards[i] != null) {
                            validCards[i] = this.cards[i];
                        } // if
                    } // for
                } // 
                
                // Simulate games
                MonteCarlo mc;
                for (int cardIndex = 0; cardIndex < validCards.length; cardIndex++) {
                    for (int i = 0; i < noOfSimulations; i++) {
                        if (validCards[cardIndex] != null) {
                            mc = new MonteCarlo(cardsOnTable, kozi, validCards[cardIndex],
                                                            this.initialCards, this.allPlayedCards); 
                            boolean result = mc.calculateProbabilities();
                            if (result)
                               this.cardProbabilities[cardIndex] += 1;
                        } //if
                    } // for
                } //for
                 
                // Normalize probabilities
                for (int i = 0; i < this.cardProbabilities.length; i++)
                    this.cardProbabilities[i] /= noOfSimulations;

                // Print valid cards.
                for (int i = 0; i < validCards.length; i++)
                    if (validCards[i] != null)
                        System.out.println("Card: " + validCards[i].getFullCardToString());
                    else
                        System.out.println("Card: null");                
                
                printCardProbabilities();   
                
                if (!suitExists && !koziExists) {
                    System.out.println("Probability of winning is 0. Min card will be selected");
                    Card minProbCard = null;
                    double minProbability = 1.1;
                    int minIndex = -1;
                    
                    for (int i = 0; i < validCards.length; i++) {
                        if (validCards[i] != null) {
                            minProbCard = validCards[i];
                            minProbability = this.cardProbabilities[i];
                            minIndex = i;
                            break;
                        } // if
                    } // for
                    
                    for (int i = 1; i < this.cards.length; i++) {
                        if (this.cards[i] != null && this.cardProbabilities[i] < minProbability) {
                            minProbability = this.cardProbabilities[i];
                            minProbCard = this.cards[i];
                            minIndex = i;
                        }  // if 
                    } // for
                    System.out.println("Card with the lowest probability is: " 
                                        + minProbCard.getFullCardToString()
                                        + " with probability " + minProbability);
                    
                    selectedCard = minProbCard.getFullCardToString();
                    this.cards[minIndex] = null;                    
                } else {
                    System.out.println("Max card will be selected");
                    
                    // Get the card with the highest probability.
                    Card maxProbCard = null;
                    double maxProbability = 0.0;
                    int maxIndex = -1;
                    for (int i = 0; i < this.cards.length; i++) {
                        if (validCards[i] != null) {
                            maxProbCard = validCards[i];
                            maxProbability = this.cardProbabilities[i];
                            maxIndex = i;
                            break;
                        } // if
                    } // for

                    for (int i = 1; i < validCards.length; i++)
                        if (validCards[i] != null && this.cardProbabilities[i] > maxProbability){
                            maxProbability = this.cardProbabilities[i];
                            maxProbCard = validCards[i];
                            maxIndex = i;
                        } // if

                    System.out.println("Card with the highest probability is: " 
                                        + maxProbCard.getFullCardToString() 
                                        + " with probability " + maxProbability);

                    selectedCard = maxProbCard.getFullCardToString();
                    this.cards[maxIndex] = null;                                     
                } // else
            } // else
            
            
        } else if (cardsOnTable.size() == 3) {
           System.out.println("3 cards have already been played");
           Card[] validCards = new Card[8];
           
           // Analyze playedCards
           String[] splitCard1 = cardsOnTable.get(0).split("_");
           String[] splitCard2 = cardsOnTable.get(1).split("_");
           String[] splitCard3 = cardsOnTable.get(2).split("_");
           
           Card card1 = new Card(splitCard1[1], new Suit(splitCard1[2]));
           Card card2 = new Card(splitCard2[1], new Suit(splitCard2[2]));
           Card card3 = new Card(splitCard3[1], new Suit(splitCard3[2]));
           
           if (card1.getSuit().getSuitToString().equals(kozi)) {
               System.out.println("Kozi should be played.");
               
               // Analyze previous kozia
               Card maxCard = card1; 
               
               if (card2.getSuit().getSuitToString().equals(kozi) && card2.getCardRankKozi() > maxCard.getCardRankKozi())
                   maxCard = card2;
               
               if (card3.getSuit().getSuitToString().equals(kozi) && card3.getCardRankKozi() > maxCard.getCardRankKozi())
                   maxCard = card3;
               
               boolean higherKoziExists = false;
               for (int i = 0; i < this.cards.length; i++) {
                   if (this.cards[i] != null && this.cards[i].getSuit().getSuitToString().equals(kozi)
                           && this.cards[i].getCardRankKozi() > maxCard.getCardRankKozi()) {
                       higherKoziExists = true;
                       validCards[i] = this.cards[i];
                   } // if
               } // for
               
               boolean koziExists = false;
               if (!higherKoziExists) {
                   System.out.println("Higher kozia were not found");
                   for (int i = 0; i < this.cards.length; i++) {
                       if (this.cards[i] != null && this.cards[i].getSuit().getSuitToString().equals(kozi)) {
                           koziExists = true;
                           validCards[i] = this.cards[i];
                       } // if
                   } // for
               }  // if
               
               if (!higherKoziExists && !koziExists) {
                   System.out.println("You don't have kozi. All cards will be simulated");
                   for (int i = 0; i < this.cards.length; i ++) {
                       if (this.cards[i] != null)
                           validCards[i] = this.cards[i];
                   } // for
               } // if
               
               // Simulate a game using valid cards.
               MonteCarlo mc;
                for (int cardIndex = 0; cardIndex < validCards.length; cardIndex++) {
                    for (int i = 0; i < noOfSimulations; i++) {
                        if (validCards[cardIndex] != null) {
                            mc = new MonteCarlo(cardsOnTable, kozi, validCards[cardIndex],
                                                            this.initialCards, this.allPlayedCards); 
                            boolean result = mc.calculateProbabilities();
                            if (result)
                               this.cardProbabilities[cardIndex] += 1;
                        } //if
                    } // for
                } //for
                 
                // Normalize probabilities
                for (int i = 0; i < this.cardProbabilities.length; i++)
                    this.cardProbabilities[i] /= noOfSimulations;
                 
                // Print valid cards.
                for (int i = 0; i < validCards.length; i++)
                    if (validCards[i] != null)
                        System.out.println("Card: " + validCards[i].getFullCardToString());
                    else
                        System.out.println("Card: null");
                
                printCardProbabilities();   
                
                if (!higherKoziExists) {
                    System.out.println("Probability of winning is 0. Min card will be selected");
                    Card minProbCard = null;
                    double minProbability = 1.1;
                    int minIndex = -1;
                    
                    for (int i = 0; i < validCards.length; i++) {
                        if (validCards[i] != null) {
                            minProbCard = validCards[i];
                            minProbability = this.cardProbabilities[i];
                            minIndex = i;
                            break;
                        } // if
                    } // for
                    
                    for (int i = 1; i < this.cards.length; i++) {
                        if (this.cards[i] != null && this.cardProbabilities[i] < minProbability) {
                            minProbability = this.cardProbabilities[i];
                            minProbCard = this.cards[i];
                            minIndex = i;
                        }  // if 
                    } // for
                    System.out.println("Card with the lowest probability is: " 
                                        + minProbCard.getFullCardToString()
                                        + " with probability " + minProbability);
                    
                    selectedCard = minProbCard.getFullCardToString();
                    this.cards[minIndex] = null;                    
                } else {
                    System.out.println("Max card will be selected");
                    
                    // Get the card with the highest probability.
                    Card maxProbCard = null;
                    double maxProbability = 0.0;
                    int maxIndex = -1;
                    for (int i = 0; i < this.cards.length; i++) {
                        if (validCards[i] != null) {
                            maxProbCard = validCards[i];
                            maxProbability = this.cardProbabilities[i];
                            maxIndex = i;
                            break;
                        } // if
                    } // for

                    for (int i = 1; i < validCards.length; i++)
                        if (validCards[i] != null && this.cardProbabilities[i] > maxProbability){
                            maxProbability = this.cardProbabilities[i];
                            maxProbCard = validCards[i];
                            maxIndex = i;
                        } // if

                    System.out.println("Card with the highest probability is: " 
                                        + maxProbCard.getFullCardToString() 
                                        + " with probability " + maxProbability);

                    selectedCard = maxProbCard.getFullCardToString();
                    this.cards[maxIndex] = null;                                     
                } // else               
           } else {
               System.out.println(card1.getSuit().getSuitToString() + " should be played.");
               
               boolean suitExists = false;
               for (int i = 0; i < this.cards.length; i++) {
                   if (this.cards[i] != null && this.cards[i].getSuit().getSuitToString().equals(card1.getSuit().getSuitToString())) {
                       suitExists = true;
                       validCards[i] = this.cards[i];
                   } // if
               } // for
               
               boolean koziExists = false;
               if (!suitExists) {
                   System.out.println("You don't have " + card1.getSuit().getSuitToString());
                   System.out.println("Kozia will be simulated");
                   
                   // Analyze previous cards (check if kozi was played)
                   Card maxCard = null;
                   if (card2.getSuit().getSuitToString().equals(kozi))
                       maxCard = card2;
                   
                   if (card3.getSuit().getSuitToString().equals(kozi) && maxCard != null && card3.getCardRankKozi() > maxCard.getCardRankKozi()) {
                       maxCard = card3;
                   } else if (card3.getSuit().getSuitToString().equals(kozi) && maxCard == null) {
                       maxCard = card3;
                   } // else if
                   
                   if (maxCard != null) {
                       System.out.println("Kozia were found on table");
                       boolean higherKoziExists = false;
                       for (int i = 0; i < this.cards.length; i++) {
                           if (this.cards[i] != null && this.cards[i].getSuit().getSuitToString().equals(kozi) && this.cards[i].getCardRankKozi() > maxCard.getCardRankKozi()) {
                                higherKoziExists = true;
                                koziExists = true;
                                validCards[i] = this.cards[i];
                            } // if
                       } // for
                       
                       if (!higherKoziExists) {
                           System.out.println("higher kozi was not found");
                           for (int i = 0; i < this.cards.length; i++) {
                               koziExists = true;
                               validCards[i] = this.cards[i];
                           } // for
                       } // if
                       
                       if (!higherKoziExists && !koziExists) {
                           System.out.println("You don't have kozi, all cards will be simulated");
                           for (int i = 0; i < this.cards.length; i++) {
                               if (this.cards[i] != null) 
                                   validCards[i] = this.cards[i];
                           } // for
                       } // if
                   } else {
                       System.out.println("Kozia were not played");
                       for (int i = 0; i < this.cards.length; i++) {
                           if (this.cards[i] != null && this.cards[i].getSuit().getSuitToString().equals(kozi)) {
                               koziExists = true;
                               validCards[i] = this.cards[i];
                           } // if
                       } // for
                       
                       if (!koziExists) {
                           System.out.println("Kozi was not found. All cards will be simulated");
                           for (int i = 0; i < this.cards.length; i++) {
                               if (this.cards[i] != null)
                                   validCards[i] = this.cards[i];
                           } // for
                       } // if
                   } // else
               } // if
               
               if (!koziExists && !suitExists) {
                   System.out.println("kozi was not found. all cards will be simulated");
                   for (int i = 0; i < this.cards.length; i++) {
                       if (this.cards[i] != null)
                           validCards[i] = this.cards[i];
                   } // for
               } // if
               
               // Simulate games using valid cards.
               MonteCarlo mc;
                for (int cardIndex = 0; cardIndex < validCards.length; cardIndex++) {
                    for (int i = 0; i < noOfSimulations; i++) {
                        if (validCards[cardIndex] != null) {
                            mc = new MonteCarlo(cardsOnTable, kozi, validCards[cardIndex],
                                                            this.initialCards, this.allPlayedCards); 
                            boolean result = mc.calculateProbabilities();
                            if (result)
                               this.cardProbabilities[cardIndex] += 1;
                        } //if
                    } // for
                } //for
                 
                // Normalize probabilities
                for (int i = 0; i < this.cardProbabilities.length; i++)
                    this.cardProbabilities[i] /= noOfSimulations;
                    
                // Print valid cards.
                for (int i = 0; i < validCards.length; i++)
                    if (validCards[i] != null)
                        System.out.println("Card: " + validCards[i].getFullCardToString());
                    else
                        System.out.println("Card: null");
                
                
                printCardProbabilities();  
                
                System.out.println("Max card will be selected");
                    
                // Get the card with the highest probability.
                Card maxProbCard = null;
                double maxProbability = 0.0;
                int maxIndex = -1;
                for (int i = 0; i < this.cards.length; i++) {
                    if (validCards[i] != null) {
                        maxProbCard = validCards[i];
                        maxProbability = this.cardProbabilities[i];
                        maxIndex = i;
                        break;
                    } // if
                } // for

                for (int i = 1; i < validCards.length; i++)
                    if (validCards[i] != null && this.cardProbabilities[i] > maxProbability){
                        maxProbability = this.cardProbabilities[i];
                        maxProbCard = validCards[i];
                        maxIndex = i;
                    } // if

                System.out.println("Card with the highest probability is: " 
                                    + maxProbCard.getFullCardToString() 
                                    + " with probability " + maxProbability);

                selectedCard = maxProbCard.getFullCardToString();
                this.cards[maxIndex] = null;                 
           } // else
           
        } // else if
        
        if (selectedCard.equals(""))
            System.err.println("Card is empty!! MCCClient");
        
        
        System.out.println("Client played: " + this.getID() + "_selextedCard");
        return (this.getID() + "_" + selectedCard);
    } // play
    
    
    private void printCardProbabilities() {
        for (int i = 0; i < 8; i++) {
            if (this.cards[i] != null) 
                System.out.println("Card: " + this.cards[i].getFullCardToString() 
                                    + "; Probability: " + this.cardProbabilities[i]);
            else 
                System.out.println("Card: null");
        } // for
    } // printCardProbabilities
    
    public String bid(int biddingRoundID, int currentBid, String currentSuit, ArrayList<String> biddingHistory){
        System.out.println(this.getName() + " is bidding..");
        
        int partnerID = -1;
        if (this.getID() == 1)
            partnerID = 3;
        else if (this.getID() == 2)
            partnerID = 0;
        else if (this.getID() == 3)
            partnerID = 1;
        
        System.out.println("Partner ID: " + partnerID);
        
        // TODO -> vertines
        if (biddingRoundID > 0 && partnerID != 0)
            return (this.getID() + "_Pass");
        
        if (biddingHistory.size() != 0) {
            // Analyze the bidding history and check the most recent bid of aiClient's partner
            int partnersHighestBidding = -1;
            String partnersHighestSuit = "";
            
            /* Go through the bidding history and find the latest partner's bid */
            for (int i = 0; i < biddingHistory.size(); i++){
                // Split bidding(i)
                String bidding = biddingHistory.get(i);
                String[] splitBidding = bidding.split("_");
                int bidderID = Integer.parseInt(splitBidding[0]);
                
                if (bidderID == partnerID){
                    if (splitBidding.length == 3){
                        int bid = Integer.parseInt(splitBidding[1]);
                        String suit = splitBidding[2];
                        
                        if (bid > partnersHighestBidding){
                            partnersHighestBidding = bid;
                            partnersHighestSuit = suit;
                        } // if
                    } // else partner has declared pass
                } // if
            } // for
            
            /* Print results of bidding history analysis */
            if (biddingHistory.size() == 0)
                System.out.println("No previous bids have been found");
            else if (partnersHighestBidding == -1)
                System.out.println("Partner has either not yet declared, or declared pass");
            
            /* Analyze client's cards and select the proper bid */
            if (partnersHighestBidding == - 1){
                // Analyze suits
                int bidOnHearts = analyzeHearts();  
                int bidOnDiamonds = analyzeDiamonds();
                int bidOnClubs = analyzeClubs();
                int bidOnSpades = analyzeSpades();       
                System.out.println("Results of Cards Analysis: ");
                System.out.println("Hearts: " + bidOnHearts);
                System.out.println("Diamonds: " + bidOnDiamonds);
                System.out.println("Clubs: " + bidOnClubs);
                System.out.println("Spades: " + bidOnSpades);
                
                /* Analyze Aces (we don't actually care for aces of Kozi cards, 
                   so we will only analyze aces of other suits rather than the 
                   given one. 
                 */
                int acesExceptHearts = analyzeAces("Hearts");
                int acesExceptDiamonds = analyzeAces("Diamonds");
                int acesExceptClubs = analyzeAces("Clubs");
                int acesExceptSpades = analyzeAces("Spades");
                
                System.out.println("Results of Ace Analysis: ");
                System.out.println("Except Hearts: " + acesExceptHearts);
                System.out.println("Except Diamonds: " + acesExceptDiamonds);
                System.out.println("Except Clubs: " + acesExceptClubs);
                System.out.println("Except Spades: " + acesExceptSpades);
                
                /* Decisions for bidding */
                String bid = selectBid(currentBid, bidOnHearts, bidOnDiamonds, bidOnClubs, bidOnSpades, 
                                       acesExceptHearts, acesExceptDiamonds, acesExceptClubs, acesExceptSpades);
                System.out.println("Bot " + this.getID() + " bid: " + bid);
                return bid;
            } else {
                System.out.println("Partners highest bidding: " 
                                   + partnersHighestBidding + " of " 
                                   + partnersHighestSuit);
                
                // We want to check if we can add points to partner's bid.
                
                // Look for Jack or Nine of the suit that partner has declared
                int addedPoints = analyzePartnersBid(partnersHighestSuit);
                
                
                // Then look for aces.
                int acePoints = analyzeAces(partnersHighestSuit);
                
                int totalAddedPoints = addedPoints + acePoints;
                System.out.println("Total added points: " + totalAddedPoints 
                                   + "(Jack/Nine: " + addedPoints + ", Aces: " 
                                   + acePoints + ").");
                
                if (totalAddedPoints == 0)
                    return (this.getID() + "_Pass");
                else
                    return (this.getID() + "_" + (currentBid + totalAddedPoints) + "_" + partnersHighestSuit);
            } // else
            
        } // if
        else { // AIClient is the first bidder. There is no need to analyze bidding history
            // Analyze only the cards.
            int hearts = analyzeHearts();
            int diamonds = analyzeDiamonds();
            int clubs = analyzeClubs();
            int spades = analyzeSpades();
            int aceHearts = analyzeAces("Hearts");
            int aceDiamonds = analyzeAces("Diamonds");
            int aceClubs = analyzeAces("Clubs");
            int aceSpades = analyzeAces("Spades");
            
            String bid = selectBid(currentBid, hearts, diamonds, clubs, spades, 
                                   aceHearts, aceDiamonds, aceClubs, aceSpades);
            return bid;
        } // else
    } // bid
  
    
     private String selectBid(int currentMaxBid, int bidHearts, int bidDiamonds, int bidClubs, int bidSpades,
                             int acesExHearts, int acesExDiamonds, int acesExClubs, int acesExSpades){
       
        int[] bidSuits = new int[4];
        bidSuits[0] = bidHearts;
        bidSuits[1] = bidDiamonds;
        bidSuits[2] = bidClubs;
        bidSuits[3] = bidSpades;
        
        int[] aceSuits = new int[4];
        aceSuits[0] = acesExHearts;
        aceSuits[1] = acesExDiamonds;
        aceSuits[2] = acesExClubs;
        aceSuits[3] = acesExSpades;
        
        int maxIndex = 0; // hearts
        for (int i = 1; i < bidSuits.length; i++){
            if (bidSuits[i] > bidSuits[maxIndex])
                maxIndex = i;
            else if (bidSuits[i] == bidSuits[maxIndex]){ // if the same->compare aces
                if (aceSuits[i] > aceSuits[maxIndex])
                    maxIndex = i;
                else if (aceSuits[i] == aceSuits[maxIndex]){ // if the same -> compare number
                    /* TODO */
                }
            } // else if
        } // for
    
        if (bidSuits[maxIndex] == 0 || aceSuits[maxIndex] == 0) // if bidSuits maxIndex = 0 -> 
                                                                // no jack no 9, if aceSuits=0 -> no ace
            return (this.getID() + "_Pass");
        else{
            String suit;
            if (maxIndex == 0)
                suit = "Hearts";
            else if (maxIndex == 1)
                suit = "Diamonds";
            else if (maxIndex == 2)
                suit = "Clubs";
            else
                suit = "Spades";
            return (this.getID() + "_" + (currentMaxBid + bidSuits[maxIndex]) + "_" + suit);
        } // else
    } // selectBid   
    
    
    private int analyzeHearts() {
        int suggestedBid = 0;
        boolean hasJack = false;
        boolean hasNine = false;
        
        for (int i = 0; i < this.heartsCards.size(); i++){
            if (this.heartsCards.get(i).getCardToString().equals("Jack"))
                hasJack = true;
            else if (this.heartsCards.get(i).getCardToString().equals("Nine"))
                hasNine = true;
        } // for
        
        if (hasJack && hasNine && this.heartsCards.size() > 2)
            suggestedBid = 2;
        else if (hasJack && !hasNine && this.heartsCards.size() > 2)
            suggestedBid = 1;
        else if (!hasJack && hasNine && this.heartsCards.size() > 2)
            suggestedBid = 1;
        else if (!hasJack && !hasNine && this.heartsCards.size() > 4)
            suggestedBid = 1;
        
        return suggestedBid;
    } // analyzeHearts
    
    private int analyzeDiamonds() {
        int suggestedBid = 0;
        boolean hasJack = false;
        boolean hasNine = false;

        for (int i = 0; i < this.diamondsCards.size(); i++){
            if (this.diamondsCards.get(i).getCardToString().equals("Jack"))
                hasJack = true;
            else if (this.diamondsCards.get(i).getCardToString().equals("Nine"))
                hasNine = true;
        } // for

        if (hasJack && hasNine && this.diamondsCards.size() > 2)
            suggestedBid = 2;
        else if (hasJack && !hasNine && this.diamondsCards.size() > 2)
            suggestedBid = 1;
        else if (!hasJack && hasNine && this.diamondsCards.size() > 2)
            suggestedBid = 1;
        else if (!hasJack && !hasNine && this.diamondsCards.size() > 4)
            suggestedBid = 1;

        return suggestedBid;
    } // analyzeDiamonds

    private int analyzeClubs() {
        int suggestedBid = 0;
        boolean hasJack = false;
        boolean hasNine = false;

        for (int i = 0; i < this.clubsCards.size(); i++){
            if (this.clubsCards.get(i).getCardToString().equals("Jack"))
                hasJack = true;
            else if (this.clubsCards.get(i).getCardToString().equals("Nine"))
                hasNine = true;
        } // for

        if (hasJack && hasNine && this.clubsCards.size() > 2)
            suggestedBid = 2;
        else if (hasJack && !hasNine && this.clubsCards.size() > 2)
            suggestedBid = 1;
        else if (!hasJack && hasNine && this.clubsCards.size() > 2)
            suggestedBid = 1;
        else if (!hasJack && !hasNine && this.clubsCards.size() > 4)
            suggestedBid = 1;

        return suggestedBid;
    } // analyzeClubs   
    
    private int analyzeSpades() {
        int suggestedBid = 0;
        boolean hasJack = false;
        boolean hasNine = false;

        for (int i = 0; i < this.spadesCards.size(); i++){
            if (this.spadesCards.get(i).getCardToString().equals("Jack"))
                hasJack = true;
            else if (this.spadesCards.get(i).getCardToString().equals("Nine"))
                hasNine = true;
        } // for

        if (hasJack && hasNine && this.spadesCards.size() > 2)
            suggestedBid = 2;
        else if (hasJack && !hasNine && this.spadesCards.size() > 2)
            suggestedBid = 1;
        else if (!hasJack && hasNine && this.spadesCards.size() > 3)
            suggestedBid = 1;
        else if (!hasJack && !hasNine && this.spadesCards.size() > 4)
            suggestedBid = 1;

        return suggestedBid;
    } // analyzeSpades 
    
    /* This method analyze aces, and returns the number of aces that a client
       holds. Method ignores ace of the given suit.
     */
    private int analyzeAces(String suit){
        int noOfAces = 0;
        System.out.println("Analyzing aces: ");
        
        if (suit.equals("Hearts")) {
            for (int i = 0; i < this.diamondsCards.size(); i++) {
                if (this.diamondsCards.get(i).getCardToString().equals("Ace"))
                    noOfAces++;
            } // for
            
            for (int i = 0; i < this.spadesCards.size(); i++) {
                if (this.spadesCards.get(i).getCardToString().equals("Ace"))
                    noOfAces++;
            } // for

            for (int i = 0; i < this.clubsCards.size(); i++) {
                if (this.clubsCards.get(i).getCardToString().equals("Ace"))
                    noOfAces++;
            } // for     
            
        } else if (suit.equals("Diamonds")) {

            for (int i = 0; i < this.heartsCards.size(); i++) {
                if (this.heartsCards.get(i).getCardToString().equals("Ace"))
                    noOfAces++;
            } // for
            
            for (int i = 0; i < this.spadesCards.size(); i++) {
                if (this.spadesCards.get(i).getCardToString().equals("Ace"))
                    noOfAces++;
            } // for

            for (int i = 0; i < this.clubsCards.size(); i++) {
                if (this.clubsCards.get(i).getCardToString().equals("Ace"))
                    noOfAces++;
            } // for              
            
        } else if (suit.equals("Spades")) {
            for (int i = 0; i < this.diamondsCards.size(); i++) {
                if (this.diamondsCards.get(i).getCardToString().equals("Ace"))
                    noOfAces++;
            } // for
            
            for (int i = 0; i < this.heartsCards.size(); i++) {
                if (this.heartsCards.get(i).getCardToString().equals("Ace"))
                    noOfAces++;
            } // for

            for (int i = 0; i < this.clubsCards.size(); i++) {
                if (this.clubsCards.get(i).getCardToString().equals("Ace"))
                    noOfAces++;
            } // for              
        } else if (suit.equals("Clubs")) {
            for (int i = 0; i < this.diamondsCards.size(); i++) {
                if (this.diamondsCards.get(i).getCardToString().equals("Ace"))
                    noOfAces++;
            } // for
            
            for (int i = 0; i < this.spadesCards.size(); i++) {
                if (this.spadesCards.get(i).getCardToString().equals("Ace"))
                    noOfAces++;
            } // for

            for (int i = 0; i < this.heartsCards.size(); i++) {
                if (this.heartsCards.get(i).getCardToString().equals("Ace"))
                    noOfAces++;
            } // for              
        } // else if
        
        
/*        
        for (int i = 0; i < this.cards.length; i++){
            if (this.cards[i] != null && !this.cards[i].getSuit().getSuitToString().equals(suit)){
              if (this.cards[i].getCardToString().equals("Ace")){
                  noOfAces++;
                  System.out.println("Ace found: " + this.cards[i].getFullCardToString());
              }
            } // if
        } // for     
        */
        if (noOfAces == 0)
            System.out.println("Ace (except " + suit + " - kozi) was not found");
        return noOfAces;
    } // analyzeAces    
    
    private int analyzePartnersBid(String suit){
        int pointsToAdd = 0;
        
        // Check if you have 9 or J of the partner's declared suit.
        for (int i = 0; i < this.cards.length; i++){
            if (this.cards[i] != null 
                    && ( (this.cards[i].getCardToString().equals("Jack") && this.cards[i].getSuit().getSuitToString().equals(suit)) 
                        || (this.cards[i].getCardToString().equals("Nine") && this.cards[i].getSuit().getSuitToString().equals(suit))))
                            pointsToAdd++;
        } // for
        
        return pointsToAdd;
    } // analyzePartnersBid
    
    public void printCards() {
        System.out.print(this.name + " cards: ");
        for (Card c : this.cards)
            System.out.print(c.getFullCardToString() + ", ");
        System.out.println();
    } // printCards()
    
    public void sortCards() {
        for (int i = 0; i < this.cards.length; i++){
            if (this.cards[i] != null){
                if (this.cards[i].getSuit().getSuitToString().equals("Hearts"))
                    this.heartsCards.add(this.cards[i]);
                else if (this.cards[i].getSuit().getSuitToString().equals("Diamonds"))
                    this.diamondsCards.add(this.cards[i]);
                else if (this.cards[i].getSuit().getSuitToString().equals("Clubs"))
                    this.clubsCards.add(this.cards[i]);
                else if (this.cards[i].getSuit().getSuitToString().equals("Spades"))
                    this.spadesCards.add(this.cards[i]);
            } // if
        } // for
        
        System.out.println("Bot " + this.getID() + ": Cards have been sorted.");
    } // sortCards()    
    
    
    /* Accessor methods */
    public int getID() {
        return this.id;
    } // getID
    
    public String getName() {
        return this.name;
    } // getName
    
    public Card[] getCards() {
        return this.cards;
    } // getCards
    
    public void setTeam(MCAITeam team) {
        this.aiTeam = team;
    } // setTeam
    
    public void setTeam(MCTeam team) {
        this.team = team;
    } // setTeam
    
    public void setCards(Card[] c) { 
        this.cards = c;
        this.initialCards = c;
    } // setCards
    
} // class MCClient
