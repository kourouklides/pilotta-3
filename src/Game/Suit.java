/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

/**
 *
 * @author gmyrianthous
 */
public class Suit {
    
    private String suitName;
    private int suitInt;
    
    public Suit(String suitName){
        this.suitName = suitName;
        this.suitInt = this.getSuitToInt(this.suitName);
    } // Suit()
    
    public String getSuitToString(){
        return this.suitName;
    } // getSuitToString()
    
    public int getSuitToInt(String suitStr){
        switch(this.getSuitToString()){
            case "Hearts":
                return 1;
            case "Diamonds":
                return 2;
            case "Clubs":
                return 3;
            case "Spades":
                return 4;
            default: 
                return 0;
        } // switch
    } // getSuitToInt
    
    public static String getSuitToString(int suitInt) {
        switch(suitInt){
            case 1: 
                return "Hearts";
            case 2:
                return "Diamonds";
            case 3: 
                return "Clubs";
            case 4:
                return "Spades";
            default:
                return null;
        } // switch
    } // getSuitToString
} // class Suit
