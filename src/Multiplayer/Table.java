/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import Game.Player;
import Game.Deck;
import Networking.Server;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gmyrianthous
 */
public class Table implements Runnable {

    private static Team firstTeam, secondTeam;
    private static ArrayList<ConnectionNode> nodes;
    private static ArrayList<Player> connectedPlayers;
    public ArrayList<Player> players = new ArrayList<Player>();
        
    private int currentMaxBid = 7;
    private String currentMaxSuit = "";
    private int roundID = 0;
    private int playerDeclarationTurn = 0;
    
    public Table(ArrayList<ConnectionNode> nodes){
        this.nodes = nodes;
    } // Table()
    
    public void run(){
        System.out.println("New game has been started.");
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        } // catch
        
     try {   
        // Create new players.
        Player playerA = new Player(nodes.get(0).clientID, nodes.get(0).clientName);        
        Player playerB = new Player(nodes.get(1).clientID, nodes.get(1).clientName);        
        Player playerC = new Player(nodes.get(2).clientID, nodes.get(2).clientName);
        Player playerD = new Player(nodes.get(3).clientID, nodes.get(3).clientName);
        
        // Add recenlty created players to the arrayList.
        players.add(0, playerA);
        players.add(1, playerB);
        players.add(2, playerC);
        players.add(3, playerD);
        
        Server.players.add(0, playerA);
        Server.players.add(1, playerB);
        Server.players.add(2, playerC);
        Server.players.add(3, playerD);
        
        // Print players
        System.out.println("Printing players..");
        printPlayers();
        
        // Create the two teams.
        Team teamA = new Team("Team A", playerA, playerC);
        Team teamB = new Team("Team B", playerB, playerD);
        
        // Print the teams.
        System.out.println("Team name: " + teamA.getTeamName() + " has players " 
                           + teamA.getTeamFirstPlayer().getPlayerName() 
                           + " and " + teamA.getTeamSecondPlayer().getPlayerName());
        
        System.out.println("Team name: " + teamB.getTeamName() + " has players " 
                   + teamB.getTeamFirstPlayer().getPlayerName() 
                   + " and " + teamB.getTeamSecondPlayer().getPlayerName());
        System.out.println();
        
        // Create a new Deck, and distribute the cards.
        Deck deck = new Deck();
        deck.printDeck();
        
        // Shuffle the deck.
        System.out.println();
        System.out.println("Shuffling the deck..");
        System.out.println();
        deck.shuffle();
        deck.printDeck();
        
        /* Distribute the cards of the deck to the 4 players */

        // Sleep for 5 seconds (barrier)
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        } // catch
        Round newRound;
        
                    newRound = new Round(roundID, teamA, teamB, this.nodes, getPlayerDeclarationTurn(), this, deck);
            newRound.run();
        
            System.out.println("Round " + roundID + " is now completed");
            this.roundID++;
            this.incrementPlayerDeclarationTurn();
        
        while (teamA.getTeamScore() < 352 && teamB.getTeamScore() < 352) { 
          if (newRound.isCompleted()){
                    newRound = new Round(roundID, teamA, teamB, this.nodes, getPlayerDeclarationTurn(), this, deck);
            newRound.run();
        
            System.out.println("Round " + roundID + " is now completed");
            this.roundID++;
            this.incrementPlayerDeclarationTurn();
          }
        } // while
            
     } catch (ArrayIndexOutOfBoundsException ex){
         System.err.println("Table = Index out of bounds");
       ex.printStackTrace();
     } 
    } // run
    
    
    public void printPlayers(){
        for (Player pl : this.players){
            System.out.println("Player Name: " + pl.getPlayerName() + " with ID "
                               + pl.getPlayerID() + " sitting in position " 
                               + pl.getPositionOnTable());
        } // for
    } // printPlayers()
    
    private void incrementPlayerDeclarationTurn() {
        this.playerDeclarationTurn++;
        if (playerDeclarationTurn > 3){
            playerDeclarationTurn = 0;
        } // if
    }
    
    public int getPlayerDeclarationTurn(){
        return this.playerDeclarationTurn;
    } // getPlayerTurn
    
    public int getCurrentMaxBid() {
        return this.currentMaxBid;
    } // getCurrentMaxBid
    
} // class Table
