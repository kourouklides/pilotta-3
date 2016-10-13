/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Game.Card;

/**
 *
 * @author gmyrianthous
 */
public class Deck {
    
    private Card[] deckCards;
    private int numberOfCards;
    
    public Deck(){
        this.numberOfCards = 32;
        deckCards = new Card[this.numberOfCards];
        
        // Instanciate card suits
        Suit hearts = new Suit("Hearts");
        Suit diamonds = new Suit("Diamonds");
        Suit clubs = new Suit("Clubs");
        Suit spades = new Suit("Spades");
        
        // Instanciate cards
        int counter = 0;
        for (int suit = 1; suit < 5; suit++){ // From Hearts(1) to Spades(4)
            for (int rank = 1; rank < 9; rank++){ // From Seven(1) to Ace(8)
                deckCards[counter] = new Card(Card.getCardNameGivenRank(rank), 
                                              new Suit(Suit.getSuitToString(suit)));
                counter++;
            } // for
        } // for
    } // Deck()  
    
    public void printDeck() {
        for (Card c : this.deckCards){
            System.out.println("Card " + c.getCardToString() + " of " 
                               + c.getSuit().getSuitToString());
        } // for    
    } // printDeck()
    
    public void shuffle() {
        for (int i = 0; i < this.numberOfCards; i++){
            int rand = i + (int) (Math.random() * (this.numberOfCards - i));
            Card c = deckCards[rand];
            deckCards[rand] = deckCards[i];
            deckCards[i] = c;
        }
    } // shuffle
    
    public Card[] getDeckCards(){
        return this.deckCards;
    } // getDeckCards
} // class Deck
