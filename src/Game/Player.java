/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Multiplayer.Team;
import java.io.Serializable;

/**
 *
 * @author gmyrianthous
 */
public class Player implements Serializable {
    
    private static final int NORTH = 0;
    private static final int WEST = 1;
    private static final int SOUTH = 2;
    private static final int EAST = 3;
    
    private int playerID;
    private String playerName;
    private String positionOnTable;
    private Team playerTeam;
    
    private int currentBid;
    private Suit currentBidSuit;
    
    public Player(int playerID, String playerName) {
        this.playerID = playerID; 
        this.playerName = playerName;
        switch (this.playerID){
            case 0: this.positionOnTable = "North";
                    break;
            case 1: this.positionOnTable = "West";
                    break;
            case 2: this.positionOnTable = "South";
                    break;
            case 3: this.positionOnTable = "East";
                    break;
        } // switch
    } // Player
    
    public int getPlayerID(){
        return this.playerID;
    } // getPlayerID
    
    public Team getPlayerTeam(){
        return this.playerTeam;
    } // getPlayerTeam
    
    public String getPositionOnTable(){
        return this.positionOnTable;
    } // getPositionOnTable
    
    public void setPlayerTeam(Team team){
        this.playerTeam = team;
    } // setPlayerTeam
    
    public String getPlayerName(){
        return playerName;
    } // getPlayerName
    
    public void setPlayerBid(int bid) {
        this.currentBid = bid;
    } // setPlayerBid
    
    public void setPlayerBidSuit(Suit s){
        this.currentBidSuit = s;
    } // setPlayerBidSuit
    
    public int getPlayerCurrentBid() {
        return this.currentBid;
    } // getPlayerCurrentBid
    
    public Suit getPlayerCurrentBidSuit() {
        return this.currentBidSuit;
    } // getPlayerCurrentBidSuit
} // class Player
