/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author gmyrianthous
 */
public class Card {
    
    private int cardPoints;
    private int cardPointsKozi;
    private Suit suit;
    private String cardName;
    private boolean isKozi;
    private int cardRank;
    private int playerID;
    ImageIcon image;
   
    
    /* Constructors */
    public Card(String cardName, Suit suit) {
        this.cardName = cardName;
        this.suit = suit;
        this.isKozi = false;
        this.image = new ImageIcon(getClass().getResource("/images/" + this.cardName + "_" 
                                   + this.suit.getSuitToString() + ".png"));
    } // Card
    
    public Card(int cardRank, Suit suit){
        this.cardRank = cardRank;
        this.suit = suit;
        this.isKozi = false;
    } // Card
   
    
    /* Accessor methods */
    public String getCardToString(){
        return this.cardName;
    } // getCardToString()
    
    public int getCardRank() {
        switch(this.cardName){
            case "Seven":
                return 1;
            case "Eight":
                return 2;
            case "Nine":
                return 3;
            case "Jack":
                return 4;
            case "Queen":
                return 5;
            case "King":
                return 6;
            case "Ten":
                return 7;
            case "Ace":
                return 8;
            default:
                return 0;
        } // switch
    } // getCardRank
    
    public int getCardRankKozi() {
        switch(this.cardName){
            case "Seven":
                return 1;
            case "Eight":
                return 2;
            case "Queen":
                return 3;
            case "King":
                return 4;
            case "Ten":
                return 5;
            case "Ace":
                return 6;
            case "Nine":
                return 7;
            case "Jack":
                return 8;
            default:
                return 0;
        } // switch
    } // getCardRankKozi
    
    public int getCardPoints(){
        switch (this.cardName){
            case "Seven":
                return 0;
            case "Eight":
                return 0;
            case "Nine":
                return 0;
            case "Jack":
                return 2;
            case "Queen":
                return 3;
            case "King":
                return 4;
            case "Ten":
                return 10;
            case "Ace":
                return 11;
            default:
                return 0;
        } // switch
    } // getCardPoints()
    
    public int getCardPointsKozi(){
        switch(this.cardName){
            case "Seven":
                return 0;
            case "Eight":
                return 0;
            case "Queen":
                return 3;
            case "King":
                return 4;
            case "Ten":
                return 10;
            case "Ace":
                return 11;
            case "Nine":
                return 14;
            case "Jack":
                return 20;
            default:
                return 0;
        } // switch
    } // getCardPointsKozi()
    
    public void setKozi(boolean bool){
        this.isKozi = bool;
    } // setKozi()
    
    public static String getCardNameGivenRank(int rank){
        switch(rank){
            case 1:
                return "Seven";
            case 2:
                return "Eight";
            case 3:
                return "Nine";
            case 4:
                return "Ten";
            case 5:
                return "Jack";
            case 6: 
                return "Queen";
            case 7:
                return "King";
            case 8:
                return "Ace";
            default:
                return null;
        } // switch
    } // getCardNameGivenRank
    
    
    public Suit getSuit(){
        return this.suit;
    } // getSuit
    
    public String getFullCardToString(){
        return (this.cardName + "_" + this.suit.getSuitToString()); 
    } // getFullCardToString()
    
    public ImageIcon getImage(){
        return this.image;
    }
    
    public boolean isKozi() {
        return this.isKozi;
    } // isKozi
    
    public void setPlayerID(int pl){
        this.playerID = pl;
    } // setPlayer
    
    public int getPlayerID() {
        return this.playerID;
    } // getPlayer
} // class Card
