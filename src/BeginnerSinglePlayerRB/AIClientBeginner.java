/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeginnerSinglePlayerRB;

import Game.Card;
import Game.Suit;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author gmyrianthous
 */
public class AIClientBeginner {
    
    private int clientID;
    private String name;
    private Card[] cards = new Card[8];
    
    private AITeamBeginner team;
    private AITeamBeginner2 aiTeam;
    
    private ArrayList<Card> heartsCards;
    private ArrayList<Card> diamondsCards;
    private ArrayList<Card> clubsCards;
    private ArrayList<Card> spadesCards;
    
    /* Constructor */
    public AIClientBeginner(int clientID) {
        this.clientID = clientID;
        this.name = "Bot " + clientID;
        
        this.heartsCards = new ArrayList<Card>();
        this.diamondsCards = new ArrayList<Card>();
        this.clubsCards = new ArrayList<Card>();
        this.spadesCards = new ArrayList<Card>();
    } // AIClient
    
    public String getName(){
        return this.name;
    } // getName()
    
    public int getID(){
        return this.clientID;
    } // getID
    
    public void setCards(Card[] c) {
        this.cards = c;
    } // setCards
    
    public Card[] getCards() {
        return this.cards;
    } // getCards()
    
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
    
    public void printCards() {
        System.out.print(this.name + " cards: ");
        for (Card c : this.cards)
            System.out.print(c.getFullCardToString() + ", ");
        System.out.println();
    } // printCards()
    
    
    private String getRandomCard() {
        String selectedCard = "";
            // Play a random suit.
            String randomSuit = this.getRandomSuit();
            System.out.println("Playing a random suit..");
            if (randomSuit.equals("Hearts")) {
                System.out.println("Playing hearts");
                if (this.heartsCards.size() != 0) {
                    selectedCard = this.getID() + "_" + this.heartsCards.get(0).getFullCardToString();
                    this.heartsCards.remove(0);
                } else {
                    System.out.println("You don't have hearts.. Playing diamonds");
                    if (this.diamondsCards.size() != 0) {
                        selectedCard = this.getID() + "_" + this.diamondsCards.get(0).getFullCardToString();
                        this.diamondsCards.remove(0);
                    } else {
                        System.out.println("You don't have diamonds.. Playing clubs");
                        if (this.clubsCards.size() != 0) {
                            selectedCard = this.getID() + "_" + this.clubsCards.get(0).getFullCardToString();
                            this.clubsCards.remove(0); 
                        } else {
                            System.out.println("You don't have Clubs..Playing spades");
                            if (this.spadesCards.size() != 0) {
                                selectedCard = this.getID() + "_" + this.spadesCards.get(0).getFullCardToString();
                                this.spadesCards.remove(0);
                            } else
                                System.out.println("Something went wrong: class AIClientBeginner, method play 1");
                        } // else
                    } // else
                } // else
            } else if (randomSuit.equals("Diamonds")) {
                System.out.println("Playing diamonds");
                if (this.diamondsCards.size() != 0) {
                    selectedCard = this.getID() + "_" + this.diamondsCards.get(0).getFullCardToString();
                    this.diamondsCards.remove(0);
                } else {
                    System.out.println("You don't have Diamonds.. Playing hearts");
                    if (this.heartsCards.size() != 0) {
                        selectedCard = this.getID() + "_" + this.heartsCards.get(0).getFullCardToString();
                        this.heartsCards.remove(0);
                    } else {
                        System.out.println("You don't have hearts.. Playing clubs");
                        if (this.clubsCards.size() != 0) {
                            selectedCard = this.getID() + "_" + this.clubsCards.get(0).getFullCardToString();
                            this.clubsCards.remove(0); 
                        } else {
                            System.out.println("You don't have Clubs..Playing spades");
                            if (this.spadesCards.size() != 0) {
                                selectedCard = this.getID() + "_" + this.spadesCards.get(0).getFullCardToString();
                                this.spadesCards.remove(0);
                            } else
                                System.out.println("Something went wrong: class AIClientBeginner, method play 2");
                        } // else
                    } // else
                } // else
            } else if (randomSuit.equals("Clubs")) {
                System.out.println("Playing Clubs");
                if (this.clubsCards.size() != 0) {
                    selectedCard = this.getID() + "_" + this.clubsCards.get(0).getFullCardToString();
                    this.clubsCards.remove(0);
                } else {
                    System.out.println("You don't have clubs.. Playing diamonds");
                    if (this.diamondsCards.size() != 0) {
                        selectedCard = this.getID() + "_" + this.diamondsCards.get(0).getFullCardToString();
                        this.diamondsCards.remove(0);
                    } else {
                        System.out.println("You don't have diamonds.. Playing Hearts");
                        if (this.heartsCards.size() != 0) {
                            selectedCard = this.getID() + "_" + this.heartsCards.get(0).getFullCardToString();
                            this.heartsCards.remove(0); 
                        } else {
                            System.out.println("You don't have Hearts..Playing spades");
                            if (this.spadesCards.size() != 0) {
                                selectedCard = this.getID() + "_" + this.spadesCards.get(0).getFullCardToString();
                                this.spadesCards.remove(0);
                            } else
                                System.out.println("Something went wrong: class AIClientBeginner, method play 1");
                        } // else
                    } // else
                } // else
            } else if (randomSuit.equals("Spades")) {
                System.out.println("Playing Spades");
                if (this.spadesCards.size() != 0) {
                    selectedCard = this.getID() + "_" + this.spadesCards.get(0).getFullCardToString();
                    this.spadesCards.remove(0);
                } else {
                    System.out.println("You don't have Spades.. Playing diamonds");
                    if (this.diamondsCards.size() != 0) {
                        selectedCard = this.getID() + "_" + this.diamondsCards.get(0).getFullCardToString();
                        this.diamondsCards.remove(0);
                    } else {
                        System.out.println("You don't have diamonds.. Playing clubs");
                        if (this.clubsCards.size() != 0) {
                            selectedCard = this.getID() + "_" + this.clubsCards.get(0).getFullCardToString();
                            this.clubsCards.remove(0); 
                        } else {
                            System.out.println("You don't have Clubs..Playing hearts");
                            if (this.heartsCards.size() != 0) {
                                selectedCard = this.getID() + "_" + this.heartsCards.get(0).getFullCardToString();
                                this.heartsCards.remove(0);
                            } else
                                System.out.println("Something went wrong: class AIClientBeginner, method play 1");
                        } // else
                    } // else
                } // else                
            } else {
                System.out.println("class AIClientBeginner, method play!!!");  
            } // else                   
            return selectedCard;     
    } // getRandomCard
    
    /* This method returns a card in the form 'AIClientID_Figure_Suit' */
    public String play(ArrayList<String> playedCards, String kozi, String teamOfRound) {
        String selectedCard = "";
        System.out.println("Bot " + this.clientID + " is playing");
        
        if (playedCards.size() == 0){ // first to play
            System.out.println("First to play");
            System.out.println("Kozi of round: " + kozi);
            System.out.println("Team of round: " + teamOfRound);
            
            // get a random card
            selectedCard = getRandomCard();
            System.out.println("Selected card: " + selectedCard);
        } else { // cards already on table
            System.out.println("You have to follow the rules");
            if (playedCards.size() == 1) {
                System.out.println("1 card has already been played");
                // Analyze previous card that was played 
                String playedCard = playedCards.get(0);
                String[] splitCard = playedCard.split("_");
                String figure = splitCard[1];
                String suit = splitCard[2];
                
                if (suit.equals(kozi)) { // play kozi
                    System.out.println("You have to play kozi");
                    selectedCard = playKozi(suit, figure);
                } else { // play other suit
                    System.out.println("You have to play " + suit);
                    selectedCard = playSuit(suit, kozi, playedCards);     
                } // else
                
            } else if (playedCards.size() == 2) {
                System.out.println("2 cards have already been played");
                // Analyze previous cards
                String card1 = playedCards.get(0);
                String[] splitCard1 = card1.split("_");
                String figure1 = splitCard1[1];
                String suit1 = splitCard1[2];
                
                String card2 = playedCards.get(1);
                String[] splitCard2 = card2.split("_");
                String figure2 = splitCard2[1];
                String suit2 = splitCard2[2];
                
                // if suit1 is kozi, then you have to play kozi
                if (suit1.equals(kozi)) {
                    if (suit2.equals(kozi)) {
                        // Find maximum card on table.
                        Card maxCard = new Card(figure1, new Suit(suit1));
                        Card secondCard = new Card(figure2, new Suit(suit2));
                        
                        if (secondCard.getCardRankKozi() > maxCard.getCardRankKozi())
                            maxCard = secondCard;
                        
                        System.out.println("Currently highest card on table is: " + maxCard.getFullCardToString());
                        selectedCard = playKozi(maxCard.getSuit().getSuitToString(), maxCard.getCardToString());
                        return selectedCard;
                    } else {
                        // Play kozi higher than figure1
                        selectedCard = playKozi(suit1, figure1);
                        return selectedCard;
                    } // else
                } else {
                    selectedCard = playSuit(suit1, kozi, playedCards);
                    return selectedCard;
                } // else
            } else {
                System.out.println("3 cards have already been played");
                // Analyze previous cards
                String card1 = playedCards.get(0);
                String[] splitCard1 = card1.split("_");
                String figure1 = splitCard1[1];
                String suit1 = splitCard1[2];
                
                String card2 = playedCards.get(1);
                String[] splitCard2 = card2.split("_");
                String figure2 = splitCard2[1];
                String suit2 = splitCard2[2];                
                
                String card3 = playedCards.get(2);
                String[] splitCard3 = card3.split("_");
                String figure3 = splitCard3[1];
                String suit3 = splitCard3[2];
                
                if (suit1.equals(kozi)) {
                    if (suit2.equals(kozi)) {
                        if (suit3.equals(kozi)) {
                            // Find max
                            Card maxCard = new Card(figure1, new Suit(suit1));
                            Card secondCard = new Card(figure2, new Suit(suit2));
                            Card thirdCard = new Card(figure3, new Suit(suit3));
                            
                            if (secondCard.getCardRankKozi() > maxCard.getCardRankKozi())
                                maxCard = secondCard;
                            
                            if (thirdCard.getCardRankKozi() > maxCard.getCardRankKozi())
                                maxCard = thirdCard;
                            
                            selectedCard = playKozi(maxCard.getSuit().getSuitToString(), maxCard.getCardToString());
                            return selectedCard; 
                        } else {
                            Card maxCard = new Card(figure1, new Suit(suit1));
                            Card secondCard = new Card(figure2, new Suit(suit2));                            

                            if (secondCard.getCardRankKozi() > maxCard.getCardRankKozi())
                                maxCard = secondCard;
                            
                            selectedCard = playKozi( maxCard.getSuit().getSuitToString(), maxCard.getCardToString());
                            return selectedCard;                                                         
                        } // else
                    } else {
                        // Only the first player and the third player have played a kozi.
                        if (suit3.equals(kozi)) {
                            Card maxCard = new Card(figure1, new Suit(suit1));
                            Card thirdCard = new Card(figure3, new Suit(suit3));                            

                            if (thirdCard.getCardRankKozi() > maxCard.getCardRankKozi())
                                maxCard = thirdCard;
                            
                            selectedCard = playKozi(maxCard.getSuit().getSuitToString(), maxCard.getCardToString());
                            return selectedCard;                            
                        } else {
                            // Only the first player has played a kozi
                            selectedCard = playKozi(suit1, figure1);
                            return selectedCard;                            
                        }
                    }
                } else {
                    selectedCard = playSuit(suit1, kozi, playedCards);
                    return selectedCard;
                } // else
            } // else
        } // else
        
        if (selectedCard == null) 
            System.out.println("Selected card is null: class AIClient, method play!");
        
        return selectedCard;
    } // play
    
    private String getRandomSuit() {
        int min = 0;
        int max = 3;
        Random rand = new Random();
        int randomNumber = rand.nextInt((max-min) + 1) + min;
        
        if (randomNumber == 0) {
            return "Hearts";
        } else if (randomNumber == 1) {
            return "Diamonds";
        } else if (randomNumber == 2) {
            return "Clubs";
        } else if (randomNumber == 3) {
            return "Spades";
        } else {
            return "Hearts";
        } // else
    } // getRandomSuit
    
    
    /* This method returns a card of the given suit */
    private String playSuit(String suit, String kozi, ArrayList<String> playedCards) {
        String card = null;
        
        if (suit.equals("Hearts")) {
            if (this.heartsCards.size() == 0) {
                System.out.println("You don't have Hearts");
                // You have to play kozi.
                card = playKoziNoSuit(suit, kozi, playedCards);
                return card;
            } else {
                System.out.println("You will play Hearts");
                card = this.getID() + "_" + this.heartsCards.get(0).getFullCardToString();
                this.heartsCards.remove(0);
                System.out.println("Selected card: " + card);
                return card;
            } // else
        } else if (suit.equals("Diamonds")) {
            if (this.diamondsCards.size() == 0) {
                System.out.println("You don't have Diamonds");
                // You have to play kozi
                card = playKoziNoSuit(suit, kozi, playedCards);
                return card;
            } else {
                System.out.println("You will play Diamonds");
                card = this.getID() + "_" + this.diamondsCards.get(0).getFullCardToString();
                this.diamondsCards.remove(0);
                System.out.println("Selected card: " + card);
                return card;                
            } // else
        } else if (suit.equals("Clubs")) {
            if (this.clubsCards.size() == 0) {
                System.out.println("You don't have Clubs");
                // You have to play kozi
                card = playKoziNoSuit(suit, kozi, playedCards);
                return card;
            } else {
                System.out.println("You will play Clubs");
                card = this.getID() + "_" + this.clubsCards.get(0).getFullCardToString();
                this.clubsCards.remove(0);
                System.out.println("Selected card: " + card);
                return card;                
            } // else
        } else if (suit.equals("Spades")) {
            if (this.spadesCards.size() == 0) {
                System.out.println("You don't have spades");
                // You have to play kozi
                card = playKoziNoSuit(suit, kozi, playedCards);
                return card;
            } else {
                System.out.println("You will play Spades");
                card = this.getID() + "_" + this.spadesCards.get(0).getFullCardToString();
                this.spadesCards.remove(0);
                System.out.println("Selected card: " + card);
                return card;                
            } // else
        } else {
            System.err.println("Something went wrong: class AIClient, method playSuit");
        } // else
        
        if (card == null)
            System.out.println("Card is null, class AIClient, method playSuit");
        return card;
    } // playSuit
    
    /* This method returns a kozi if a player don't have a particular suit 
       If user does not have kozi, then another card is returned.
     */
    private String playKoziNoSuit(String suit, String kozi, ArrayList<String> playedCards) {
        String card;
        if (kozi.equals("Hearts")) {
            if (this.heartsCards.size() == 0) {
                System.out.println("You don't even have a kozi");
                // Play any suit
                if (this.diamondsCards.size() != 0){
                    card = this.getID() + "_" + this.diamondsCards.get(0).getFullCardToString();
                    this.diamondsCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;
                } else if (this.clubsCards.size() != 0) {
                    card = this.getID() + "_" + this.clubsCards.get(0).getFullCardToString();
                    this.clubsCards.remove(0);
                    System.out.println("SelectedCard: " + card);
                    return card;                          
                } else if (this.spadesCards.size() != 0) {
                    card = this.getID() + "_" + this.spadesCards.get(0).getFullCardToString();
                    this.spadesCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;
                } // else if
            } else {
                System.out.println("You have a kozi");
                // Check if somebody else already played a kozi.
                // This happens only if at least two cards are already on table
                if (playedCards.size() > 1) {
                    
                   // We don't care for the first card, so start from the second.
                   String currentMax = null; 
                    
                   String card1 = playedCards.get(1);
                   if (card1.contains(kozi))
                       currentMax = card1;
                   
                   if (playedCards.size() > 2) {
                       String card2 = playedCards.get(2);
                       if (card2.contains(kozi))
                           currentMax = card2;
                       
                       if (playedCards.size() > 3){
                           String card3 = playedCards.get(3);
                           if (card3.contains(kozi))
                               currentMax = card3;
                       } // if
                   } // if
                   
                   if (currentMax == null) {
                       System.out.println("There are no kozia on table");
                       card = this.getID() + "_" + this.heartsCards.get(0).getFullCardToString();
                       this.heartsCards.remove(0);
                       System.out.println("Selected card: " + card);
                       return card;
                   } else {
                       System.out.println("There are kozia on table");
                       System.out.println("Current max: " + currentMax);
                       String currentMaxSplit[] = currentMax.split("_");
                       Card highestCardOnTable = new Card(currentMaxSplit[1], new Suit(kozi));
                       Card yourCard = null;
                       int yourCardIndex = -1;
                       
                       for (int i = 0; i < this.heartsCards.size(); i++) {
                           if (this.heartsCards.get(i).getCardRankKozi() > highestCardOnTable.getCardRankKozi()) {
                               yourCard = this.heartsCards.get(i);
                               yourCardIndex = i;
                           } // if
                       } // for
                       
                       if (yourCardIndex == -1) { 
                           System.out.println("You don't have any higher kozia");
                           card = this.getID() + "_" + this.heartsCards.get(0).getFullCardToString();
                           this.heartsCards.remove(0);
                           System.out.println("Selected card: " + card);
                           return card;
                       } else {
                           card = this.getID() + "_" + yourCard.getFullCardToString();
                           this.heartsCards.remove(yourCardIndex);
                           System.out.println("Selected card: " + card);
                           return card;
                       } // else            
                   } // else
                   
                } else {
                    card = this.getID() + "_" + this.heartsCards.get(0).getFullCardToString();
                    this.heartsCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;
                } // else
            } // else
        } else if (kozi.equals("Diamonds")) {
            if (this.diamondsCards.size() == 0) {
                System.out.println("You don't even have a kozi");
                // Play any suit
                if (this.heartsCards.size() != 0){
                    card = this.getID() + "_" + this.heartsCards.get(0).getFullCardToString();
                    this.heartsCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;
                } else if (this.clubsCards.size() != 0) {
                    card = this.getID() + "_" + this.clubsCards.get(0).getFullCardToString();
                    this.clubsCards.remove(0);
                    System.out.println("SelectedCard: " + card);
                    return card;                          
                } else if (this.spadesCards.size() != 0) {
                    card = this.getID() + "_" + this.spadesCards.get(0).getFullCardToString();
                    this.spadesCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;
                } // else if
            } else {
                System.out.println("You have a kozi");
                // Check if somebody else already played a kozi.
                // This happens only if at least two cards are already on table
                if (playedCards.size() > 1) {
                    
                   // We don't care for the first card, so start from the second.
                   String currentMax = null; 
                    
                   String card1 = playedCards.get(1);
                   if (card1.contains(kozi))
                       currentMax = card1;
                   
                   if (playedCards.size() > 2) {
                       String card2 = playedCards.get(2);
                       if (card2.contains(kozi))
                           currentMax = card2;
                       
                       if (playedCards.size() > 3){
                           String card3 = playedCards.get(3);
                           if (card3.contains(kozi))
                               currentMax = card3;
                       } // if
                   } // if
                   
                   if (currentMax == null) {
                       System.out.println("There are no kozia on table");
                       card = this.getID() + "_" + this.diamondsCards.get(0).getFullCardToString();
                       this.diamondsCards.remove(0);
                       System.out.println("Selected card: " + card);
                       return card;
                   } else {
                       System.out.println("There are kozia on table");
                       System.out.println("CurrentMax: " + currentMax);
                       String currentMaxSplit[] = currentMax.split("_");
                       Card highestCardOnTable = new Card(currentMaxSplit[1], new Suit(kozi));
                       Card yourCard = null;
                       int yourCardIndex = -1;
                       
                       for (int i = 0; i < this.diamondsCards.size(); i++) {
                           if (this.diamondsCards.get(i).getCardRankKozi() > highestCardOnTable.getCardRankKozi()) {
                               yourCard = this.diamondsCards.get(i);
                               yourCardIndex = i;
                           } // if
                       } // for
                       
                       if (yourCardIndex == -1) { 
                           System.out.println("You don't have any higher kozia");
                           card = this.getID() + "_" + this.diamondsCards.get(0).getFullCardToString();
                           this.diamondsCards.remove(0);
                           System.out.println("Selected card: " + card);
                           return card;
                       } else {
                           card = this.getID() + "_" + yourCard.getFullCardToString();
                           this.diamondsCards.remove(yourCardIndex);
                           System.out.println("Selected card: " + card);
                           return card;
                       } // else            
                   } // else
                } else {
                    card = this.getID() + "_" + this.diamondsCards.get(0).getFullCardToString();
                    this.diamondsCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;
                } // else
            } // else
        } else if (kozi.equals("Clubs")) {
            if (this.clubsCards.size() == 0) {
                System.out.println("You don't even have a kozi");
                // Play any suit
                if (this.diamondsCards.size() != 0){
                    card = this.getID() + "_" + this.diamondsCards.get(0).getFullCardToString();
                    this.diamondsCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;
                } else if (this.heartsCards.size() != 0) {
                    card = this.getID() + "_" + this.heartsCards.get(0).getFullCardToString();
                    this.heartsCards.remove(0);
                    System.out.println("SelectedCard: " + card);
                    return card;                          
                } else if (this.spadesCards.size() != 0) {
                    card = this.getID() + "_" + this.spadesCards.get(0).getFullCardToString();
                    this.spadesCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;
                } // else if
            } else {
                System.out.println("You have a kozi");
                // Check if somebody else already played a kozi.
                // This happens only if at least two cards are already on table
                if (playedCards.size() > 1) {
                    
                   // We don't care for the first card, so start from the second.
                   String currentMax = null; 
                    
                   String card1 = playedCards.get(1);
                   if (card1.contains(kozi))
                       currentMax = card1;
                   
                   if (playedCards.size() > 2) {
                       String card2 = playedCards.get(2);
                       if (card2.contains(kozi))
                           currentMax = card2;
                       
                       if (playedCards.size() > 3){
                           String card3 = playedCards.get(3);
                           if (card3.contains(kozi))
                               currentMax = card3;
                       } // if
                   } // if
                   
                   if (currentMax == null) {
                       System.out.println("There are no kozia on table");
                       card = this.getID() + "_" + this.clubsCards.get(0).getFullCardToString();
                       this.clubsCards.remove(0);
                       System.out.println("Selected card: " + card);
                       return card;
                   } else {
                       System.out.println("There are kozia on table");
                       System.out.println("CurrentMax: " + currentMax);
                       String currentMaxSplit[] = currentMax.split("_");
                       Card highestCardOnTable = new Card(currentMaxSplit[1], new Suit(kozi));
                       Card yourCard = null;
                       int yourCardIndex = -1;
                       
                       for (int i = 0; i < this.clubsCards.size(); i++) {
                           if (this.clubsCards.get(i).getCardRankKozi() > highestCardOnTable.getCardRankKozi()) {
                               yourCard = this.clubsCards.get(i);
                               yourCardIndex = i;
                           } // if
                       } // for
                       
                       if (yourCardIndex == -1) { 
                           System.out.println("You don't have any higher kozia");
                           card = this.getID() + "_" + this.clubsCards.get(0).getFullCardToString();
                           this.clubsCards.remove(0);
                           System.out.println("Selected card: " + card);
                           return card;
                       } else {
                           card = this.getID() + "_" + yourCard.getFullCardToString();
                           this.clubsCards.remove(yourCardIndex);
                           System.out.println("Selected card: " + card);
                           return card;
                       } // else            
                   } // else
                   
                } else {
                    card = this.getID() + "_" + this.clubsCards.get(0).getFullCardToString();
                    this.clubsCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;
                } // else
            } // else
        } else if (kozi.equals("Spades")) {
            if (this.spadesCards.size() == 0) {
                System.out.println("You don't even have a kozi");
                // Play any suit
                if (this.diamondsCards.size() != 0){
                    card = this.getID() + "_" + this.diamondsCards.get(0).getFullCardToString();
                    this.diamondsCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;
                } else if (this.clubsCards.size() != 0) {
                    card = this.getID() + "_" + this.clubsCards.get(0).getFullCardToString();
                    this.clubsCards.remove(0);
                    System.out.println("SelectedCard: " + card);
                    return card;                          
                } else if (this.heartsCards.size() != 0) {
                    card = this.getID() + "_" + this.heartsCards.get(0).getFullCardToString();
                    this.heartsCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;
                } // else if
            } else {
                System.out.println("You have a kozi");
                // Check if somebody else already played a kozi.
                // This happens only if at least two cards are already on table
                if (playedCards.size() > 1) {
                    
                   // We don't care for the first card, so start from the second.
                   String currentMax = null; 
                    
                   String card1 = playedCards.get(1);
                   if (card1.contains(kozi))
                       currentMax = card1;
                   
                   if (playedCards.size() > 2) {
                       String card2 = playedCards.get(2);
                       if (card2.contains(kozi))
                           currentMax = card2;
                       
                       if (playedCards.size() > 3){
                           String card3 = playedCards.get(3);
                           if (card3.contains(kozi))
                               currentMax = card3;
                       } // if
                   } // if
                   
                   if (currentMax == null) {
                       System.out.println("There are no kozia on table");
                       card = this.getID() + "_" + this.spadesCards.get(0).getFullCardToString();
                       this.spadesCards.remove(0);
                       System.out.println("Selected card: " + card);
                       return card;
                   } else {
                       System.out.println("There are kozia on table");
                       System.out.println("CurrentMax: " + currentMax);
                       String currentMaxSplit[] = currentMax.split("_");
                       Card highestCardOnTable = new Card(currentMaxSplit[1], new Suit(kozi));
                       Card yourCard = null;
                       int yourCardIndex = -1;
                       
                       for (int i = 0; i < this.spadesCards.size(); i++) {
                           if (this.spadesCards.get(i).getCardRankKozi() > highestCardOnTable.getCardRankKozi()) {
                               yourCard = this.spadesCards.get(i);
                               yourCardIndex = i;
                           } // if
                       } // for
                       
                       if (yourCardIndex == -1) { 
                           System.out.println("You don't have any higher kozia");
                           card = this.getID() + "_" + this.spadesCards.get(0).getFullCardToString();
                           this.spadesCards.remove(0);
                           System.out.println("Selected card: " + card);
                           return card;
                       } else {
                           card = this.getID() + "_" + yourCard.getFullCardToString();
                           this.spadesCards.remove(yourCardIndex);
                           System.out.println("Selected card: " + card);
                           return card;
                       } // else            
                   } // else
                   
                } else {
                    card = this.getID() + "_" + this.spadesCards.get(0).getFullCardToString();
                    this.spadesCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;
                } // else
            } // else
        } else {
            System.err.println("Something went wrong: class AIClient, method playKoziNoSuit");
        } // else
        
        return null;
    } // playKoziNoSuit
    
    /* This method strictly follows the rules of kozi. 
       Arguments: Kozi suit, figure that was previously played 
     */
    private String playKozi(String suit, String figure) {
        String card;
        System.out.println("You have to play kozi (" + suit + ").");
        if (suit.equals("Hearts")) {
            if (this.heartsCards.size() == 0){
                System.out.println("You don't have kozi.");
                // Play other card
                if (this.diamondsCards.size() != 0){
                    card = this.getID() + "_" + this.diamondsCards.get(0).getFullCardToString();
                    this.diamondsCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;
                } else if (this.clubsCards.size() != 0) {
                    card = this.getID() + "_" + this.clubsCards.get(0).getFullCardToString();
                    this.clubsCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;
                } else if (this.spadesCards.size() != 0) {
                    card = this.getID() + "_" + this.spadesCards.get(0).getFullCardToString();
                    this.spadesCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;                    
                } else {
                    System.err.println("Something went wrong: class AIClient, playKozi!!");
                } // else
            } else {
                // Create an instance of the played suit.
                Card playedCard = new Card(figure, new Suit(suit));
                
                // Look for a higher kozi
                Card maxCard = null;
                int maxCardIndex = -1;
                for (int i = 0; i < heartsCards.size(); i++) {
                    if (this.heartsCards.get(i).getCardRankKozi() > playedCard.getCardRankKozi()) {
                        maxCard = this.heartsCards.get(i);
                        maxCardIndex = i;
                        break; 
                    } 
                } // for
                
                // If maxCard remains null, then no higher kozi exists, hence
                // play a lower kozi.
                if (maxCard == null) {
                    System.out.println("No higher kozi found");
                    card = this.getID() + "_" + this.heartsCards.get(0).getFullCardToString();
                    this.heartsCards.remove(0);
                    System.out.println("Selected card: " + card);    
                    return card;
                } // if
                
                System.out.println("Higher kozi found");
                card = this.getID() + "_" + maxCard.getFullCardToString();
                this.heartsCards.remove(maxCardIndex);
                System.out.println("Selected card: " + card);
                
                return card;
            } // else
        } else if (suit.equals("Diamonds")) {
            if (this.diamondsCards.size() == 0){
                System.out.println("You don't have kozi.");
                // Play other card
                if (this.heartsCards.size() != 0){
                    card = this.getID() + "_" + this.heartsCards.get(0).getFullCardToString();
                    this.heartsCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;
                } else if (this.clubsCards.size() != 0) {
                    card = this.getID() + "_" + this.clubsCards.get(0).getFullCardToString();
                    this.clubsCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;
                } else if (this.spadesCards.size() != 0) {
                    card = this.getID() + "_" + this.spadesCards.get(0).getFullCardToString();
                    this.spadesCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;                    
                } else {
                    System.err.println("Something went wrong: class AIClient, playKozi!!");
                } // else                
            } else {
                // Create an instance of the played suit.
                Card playedCard = new Card(figure, new Suit(suit));
                
                // Look for a higher kozi
                Card maxCard = null;
                int maxCardIndex = -1;
                for (int i = 0; i < diamondsCards.size(); i++) {
                    if (this.diamondsCards.get(i).getCardRankKozi() > playedCard.getCardRankKozi()) {
                        maxCard = this.diamondsCards.get(i);
                        maxCardIndex = i;
                        break; 
                    } 
                } // for
                
                // If maxCard remains null, then no higher kozi exists, hence
                // play a lower kozi.
                if (maxCard == null) {
                    System.out.println("No higher kozi found");
                    card = this.getID() + "_" + this.diamondsCards.get(0).getFullCardToString();
                    this.diamondsCards.remove(0);
                    System.out.println("Selected card: " + card);    
                    return card;
                } // if
                
                System.out.println("Higher kozi found");
                card = this.getID() + "_" + maxCard.getFullCardToString();
                this.diamondsCards.remove(maxCardIndex);
                System.out.println("Selected card: " + card);
                
                return card;                
            } // else           
        } else if (suit.equals("Clubs")) {
            if (this.clubsCards.size() == 0){
                System.out.println("You don't have kozi.");
                // Play other card.
                if (this.diamondsCards.size() != 0){
                    card = this.getID() + "_" + this.diamondsCards.get(0).getFullCardToString();
                    this.diamondsCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;
                } else if (this.heartsCards.size() != 0) {
                    card = this.getID() + "_" + this.heartsCards.get(0).getFullCardToString();
                    this.heartsCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;
                } else if (this.spadesCards.size() != 0) {
                    card = this.getID() + "_" + this.spadesCards.get(0).getFullCardToString();
                    this.spadesCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;                    
                } else {
                    System.err.println("Something went wrong: class AIClient, playKozi!!");
                } // else
            } else {
                // Create an instance of the played suit.
                Card playedCard = new Card(figure, new Suit(suit));
                
                // Look for a higher kozi
                Card maxCard = null;
                int maxCardIndex = -1;
                for (int i = 0; i < clubsCards.size(); i++) {
                    if (this.clubsCards.get(i).getCardRankKozi() > playedCard.getCardRankKozi()) {
                        maxCard = this.clubsCards.get(i);
                        maxCardIndex = i;
                        break; 
                    } 
                } // for
                
                // If maxCard remains null, then no higher kozi exists, hence
                // play a lower kozi.
                if (maxCard == null) {
                    System.out.println("No higher kozi found");
                    card = this.getID() + "_" + this.clubsCards.get(0).getFullCardToString();
                    this.clubsCards.remove(0);
                    System.out.println("Selected card: " + card);    
                    return card;
                } // if
                
                System.out.println("Higher kozi found");
                card = this.getID() + "_" + maxCard.getFullCardToString();
                this.clubsCards.remove(maxCardIndex);
                System.out.println("Selected card: " + card);
                
                return card;                
            } // else
        } else if (suit.equals("Spades")) {
            if (this.spadesCards.size() == 0) {
                System.out.println("You don't have kozi.");
                // Play other card
                if (this.diamondsCards.size() != 0){
                    card = this.getID() + "_" + this.diamondsCards.get(0).getFullCardToString();
                    this.diamondsCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;
                } else if (this.clubsCards.size() != 0) {
                    card = this.getID() + "_" + this.clubsCards.get(0).getFullCardToString();
                    this.clubsCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;
                } else if (this.heartsCards.size() != 0) {
                    card = this.getID() + "_" + this.heartsCards.get(0).getFullCardToString();
                    this.heartsCards.remove(0);
                    System.out.println("Selected card: " + card);
                    return card;                    
                } else {
                    System.err.println("Something went wrong: class AIClient, playKozi!!");
                } // else
            } else {
                // Create an instance of the played suit.
                Card playedCard = new Card(figure, new Suit(suit));
                
                // Look for a higher kozi
                Card maxCard = null;
                int maxCardIndex = -1;
                for (int i = 0; i < spadesCards.size(); i++) {
                    if (this.spadesCards.get(i).getCardRankKozi() > playedCard.getCardRankKozi()) {
                        maxCard = this.spadesCards.get(i);
                        maxCardIndex = i;
                        break; 
                    } 
                } // for
                
                // If maxCard remains null, then no higher kozi exists, hence
                // play a lower kozi.
                if (maxCard == null) {
                    System.out.println("No higher kozi found");
                    card = this.getID() + "_" + this.spadesCards.get(0).getFullCardToString();
                    this.spadesCards.remove(0);
                    System.out.println("Selected card: " + card);    
                    return card;
                } // if
                
                System.out.println("Higher kozi found");
                card = this.getID() + "_" + maxCard.getFullCardToString();
                this.spadesCards.remove(maxCardIndex);
                System.out.println("Selected card: " + card);
                
                return card;
            } // else
        } else {
            System.err.println("Something went wrong: Class AIClient, method playKozi");
        } // else
        
        
        return null;
    } // playKozi
    
    /* This method returns a kozi, empty string if kozi does not exist.
       This method does not follow rules of kozi. It assumes that you are the
       first player of the mini-round.
     */
    private String selectKozi(String kozi){
        String selectedCard = "";
        
        if (kozi.equals("Hearts")) {
            System.out.println("Heart should be played");

            if (this.heartsCards.size() == 0)
                return "";
            
            // Play the highest kozi
            Card maxCard = heartsCards.get(0);
            int maxIndex = 0;
            for (int i = 1; i < heartsCards.size(); i++){
                if (this.heartsCards.get(i).getCardRankKozi() > maxCard.getCardRankKozi()){
                    maxCard = this.heartsCards.get(i);
                    maxIndex = i;
                } // if
            } // for
            
            // Remove the selected card from arrayList
            this.heartsCards.remove(maxIndex);
            selectedCard = maxCard.getFullCardToString();
            System.out.println("Selected Card: " + selectedCard);
            return selectedCard;
        } else if (kozi.equals("Diamonds")) {
            System.out.println("Diamond should be played");

            if (this.diamondsCards.size() == 0)
                return "";
            
            // Play the highest kozi
            Card maxCard = diamondsCards.get(0);
            int maxIndex = 0;
            for (int i = 1; i < diamondsCards.size(); i++){
                if (this.diamondsCards.get(i).getCardRankKozi() > maxCard.getCardRankKozi()){
                    maxCard = this.diamondsCards.get(i);
                    maxIndex = i;
                } // if
            } // for
            
            // Remove the selected card from arrayList
            this.diamondsCards.remove(maxIndex);
            selectedCard = maxCard.getFullCardToString();
            System.out.println("Selected Card: " + selectedCard);
            return selectedCard;
        } else if (kozi.equals("Clubs")) {
            System.out.println("Clubs should be played");

            if (this.clubsCards.size() == 0)
                return "";
            
            // Play the highest kozi
            Card maxCard = clubsCards.get(0);
            int maxIndex = 0;
            for (int i = 1; i < clubsCards.size(); i++){
                if (this.clubsCards.get(i).getCardRankKozi() > maxCard.getCardRankKozi()){
                    maxCard = this.clubsCards.get(i);
                    maxIndex = i;
                } // if
            } // for
            
            // Remove the selected card from arrayList
            this.clubsCards.remove(maxIndex);
            selectedCard = maxCard.getFullCardToString();
            System.out.println("Selected Card: " + selectedCard);
            return selectedCard;
        } else if (kozi.equals("Spades")){
            System.out.println("Spade should be played");
            if (this.spadesCards.size() == 0)
                return "";
            
            // Play the highest kozi
            Card maxCard = spadesCards.get(0);
            int maxIndex = 0;
            for (int i = 1; i < spadesCards.size(); i++){
                if (this.spadesCards.get(i).getCardRankKozi() > maxCard.getCardRankKozi()){
                    maxCard = this.spadesCards.get(i);
                    maxIndex = i;
                } // if
            } // for
            
            // Remove the selected card from arrayList
            this.spadesCards.remove(maxIndex);
            selectedCard = maxCard.getFullCardToString();
            System.out.println("Selected Card: " + selectedCard);
            return selectedCard;
        } // else if
        
        return selectedCard;
    } // selectKozi
    
    /* this method selects an ace to play (ignore ace of current kozi) */
    private Card playAce(String kozi) {
        Card card = null;
        
        if (kozi.equals("Hearts")) {
            boolean diamonds = suitHasAce("Diamonds");
            if (diamonds){
                int aceIndex = getAceIndex("Diamonds");
                card = this.diamondsCards.get(aceIndex);
                this.diamondsCards.remove(aceIndex);
                System.out.println("Diamonds ace found");
                
            } else {
                boolean clubs = suitHasAce("Clubs");
                if (clubs){
                    int aceIndex = getAceIndex("Clubs");
                    card = this.clubsCards.get(aceIndex);
                    this.clubsCards.remove(aceIndex);
                    System.out.println("Clubs ace found");
                } else {
                    boolean spades = suitHasAce("Spades");
                    if (spades){
                        int aceIndex = getAceIndex("Spades");
                        card = this.spadesCards.get(aceIndex);
                        this.spadesCards.remove(aceIndex);
                        System.out.println("Spades ace found");
                    } else {
                        System.out.println("Something went wrong -> class AIClient, method playAce 1");
                        System.out.println("clubs: " + clubs + ", diamonds: " + diamonds + ", spades: " + spades);
                        System.out.println("Card: " + card.getFullCardToString());

                    } // else
                } // else
            } // else
        } else if (kozi.equals("Diamonds")) {
            boolean hearts = suitHasAce("Hearts");
            if (hearts){
                int aceIndex = getAceIndex("Hearts");
                card = this.heartsCards.get(aceIndex);
                this.heartsCards.remove(aceIndex);
                System.out.println("Hearts ace found");
                
            } else {
                boolean clubs = suitHasAce("Clubs");
                if (clubs){
                    int aceIndex = getAceIndex("Clubs");
                    card = this.clubsCards.get(aceIndex);
                    this.clubsCards.remove(aceIndex);
                    System.out.println("Clubs ace found");
                } else {
                    boolean spades = suitHasAce("Spades");
                    if (spades){
                        int aceIndex = getAceIndex("Spades");
                        card = this.spadesCards.get(aceIndex);
                        this.spadesCards.remove(aceIndex);
                        System.out.println("Spades ace found");
                    } else {
                        System.out.println("Something went wrong -> class AIClient, method playAce 2");
                        System.out.println("hearts: " + hearts + ", clubs: " + clubs + ", spades: " + spades);
                        System.out.println("Card: " + card.getFullCardToString());

                    } // else
                } // else
            } // else            
        } else if (kozi.equals("Spades")) {
            boolean diamonds = suitHasAce("Diamonds");
            if (diamonds){
                int aceIndex = getAceIndex("Diamonds");
                card = this.diamondsCards.get(aceIndex);
                this.diamondsCards.remove(aceIndex);
                System.out.println("Diamonds ace found");
                
            } else {
                boolean clubs = suitHasAce("Clubs");
                if (clubs){
                    int aceIndex = getAceIndex("Clubs");
                    card = this.clubsCards.get(aceIndex);
                    this.clubsCards.remove(aceIndex);
                    System.out.println("Clubs ace found");
                } else {
                    boolean hearts = suitHasAce("Hearts");
                    if (hearts){
                        int aceIndex = getAceIndex("Hearts");
                        card = this.heartsCards.get(aceIndex);
                        this.heartsCards.remove(aceIndex);
                        System.out.println("Hearts ace found");
                    } else {
                        System.out.println("Something went wrong -> class AIClient, method playAce 3");
                        System.out.println("hearts: " + hearts + ", diamonds: " + diamonds + ", clubs: " + clubs);
                        System.out.println("Card: " + card.getFullCardToString());
                    } // else
                } // else
            } // else            
        } else if (kozi.equals("Clubs")){
            boolean diamonds = suitHasAce("Diamonds");
            if (diamonds){
                int aceIndex = getAceIndex("Diamonds");
                card = this.diamondsCards.get(aceIndex);
                this.diamondsCards.remove(aceIndex);
                System.out.println("Diamonds ace found");
                
            } else {
                boolean hearts = suitHasAce("Hearts");
                if (hearts){
                    int aceIndex = getAceIndex("Hearts");
                    card = this.heartsCards.get(aceIndex);
                    this.heartsCards.remove(aceIndex);
                    System.out.println("Hearts ace found");
                } else {
                    boolean spades = suitHasAce("Spades");
                    if (spades){
                        int aceIndex = getAceIndex("Spades");
                        card = this.spadesCards.get(aceIndex);
                        this.spadesCards.remove(aceIndex);
                        System.out.println("Spades ace found");
                    } else {
                        System.out.println("Something went wrong -> class AIClient, method playAce 4");
                        System.out.println("hearts: " + hearts + ", diamonds: " + diamonds + ", spades: " + spades);
                        System.out.println("Card: " + card.getFullCardToString());

                    } // else
                } // else
            } // else            
        } // else if

        if (card == null) {
            System.err.println("Error!! Card is null");
        } else 
            System.out.println("Selected ace: " + card.getFullCardToString());
        return card;
    } // playAce
    
    /* This method returns ace index of given suit. -1 if an ace doesn't exist */
    private int getAceIndex(String suit) {
        int aceIndex = -1;
        
        if (suitHasAce(suit)){
            if (suit.equals("Hearts")) {
                for (int i = 0; i < this.heartsCards.size(); i++)
                    if (this.heartsCards.get(i).getCardToString().equals("Ace"))
                        aceIndex = i;
            } else if (suit.equals("Diamonds")) {
                for (int i = 0; i < this.diamondsCards.size(); i++)
                    if (this.diamondsCards.get(i).getCardToString().equals("Ace"))
                        aceIndex = i;
            } else if (suit.equals("Clubs")) {
                for (int i = 0; i < this.clubsCards.size(); i++)
                    if (this.clubsCards.get(i).getCardToString().equals("Ace"))
                        aceIndex = i;
            } else if (suit.equals("Spades")) {
                for (int i = 0; i < this.spadesCards.size(); i++)
                    if (this.spadesCards.get(i).getCardToString().equals("Ace"))
                        aceIndex = i;
                
            } // else if
            return aceIndex;
        } else
            return -1;
        
    } // getAceIndex
    
    /* Check wheter you have an ace of the given suit */
    private boolean suitHasAce(String suit) {
       boolean hasAce = false;
       if (suit.equals("Hearts")) {
           for (int i = 0; i < this.heartsCards.size(); i++){
               if (this.heartsCards.get(i).getCardToString().equals("Ace"))
                   hasAce = true;
           } // for
       } else if (suit.equals("Diamonds")) {
           for (int i = 0; i < this.diamondsCards.size(); i++){
               if (this.diamondsCards.get(i).getCardToString().equals("Ace"))
                   hasAce = true;
           } // for           
       } else if (suit.equals("Clubs")) {
           for (int i = 0; i < this.clubsCards.size(); i++){
               if (this.clubsCards.get(i).getCardToString().equals("Ace"))
                   hasAce = true;
           } // for             
       } else if (suit.equals("Spades")) {
           for (int i = 0; i < this.spadesCards.size(); i++){
               if (this.spadesCards.get(i).getCardToString().equals("Ace"))
                   hasAce = true;
           } // for             
       } // else if
       
       return hasAce;
        
    } // suitHasAce
    
    
    /* This method returns a random card. It is used when no kozi and/or ace
        exists. It takes as an agrument the kozi of current round.
    */
    private Card playOtherCard(String suit){
        Card card = null;
        
        // You don't have a kozi
        if (suit.equals("Hearts")){
            if (this.diamondsCards.size() != 0) {
                card = this.diamondsCards.get(0);
                this.diamondsCards.remove(0);
            } else if (this.spadesCards.size() != 0) {
                card = this.spadesCards.get(0);
                this.spadesCards.remove(0);
            } else if (this.clubsCards.size() != 0) {
                card = this.clubsCards.get(0);
                this.clubsCards.remove(0);
            } else { // something went wrong
                card = null;
            } // else
        } else if (suit.equals("Diamonds")){
            if (this.heartsCards.size() != 0) {
                card = this.heartsCards.get(0);
                this.heartsCards.remove(0);
            } else if (this.spadesCards.size() != 0) {
                card = this.spadesCards.get(0);
                this.spadesCards.remove(0);
            } else if (this.clubsCards.size() != 0) {
                card = this.clubsCards.get(0);
                this.clubsCards.remove(0);
            } else { // something went wrong
                card = null;
            } // else            
        } else if (suit.equals("Spades")){
            if (this.diamondsCards.size() != 0) {
                card = this.diamondsCards.get(0);
                this.diamondsCards.remove(0);
            } else if (this.heartsCards.size() != 0) {
                card = this.heartsCards.get(0);
                this.heartsCards.remove(0);
            } else if (this.clubsCards.size() != 0) {
                card = this.clubsCards.get(0);
                this.clubsCards.remove(0);
            } else { // something went wrong
                card = null;
            } // else            
        } else if (suit.equals("Clubs")) {
            if (this.diamondsCards.size() != 0) {
                card = this.diamondsCards.get(0);
                this.diamondsCards.remove(0);
            } else if (this.spadesCards.size() != 0) {
                card = this.spadesCards.get(0);
                this.spadesCards.remove(0);
            } else if (this.heartsCards.size() != 0) {
                card = this.heartsCards.get(0);
                this.heartsCards.remove(0);
            } else { // something went wrong
                card = null;
            } // else            
        } // else if
        
        return card;
    } // playOtherCard
    
    
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
    } // analyzeHearts

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
    } // analyzeHearts    
    
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
    } // analyzeHearts  
    
    
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
    
    public void setTeam(AITeamBeginner team){
        this.team = team;
    } // setTeam
    
    public void setTeam(AITeamBeginner2 aiTeam){
        this.aiTeam = aiTeam;
    } // setTeam

} // class AIClient
