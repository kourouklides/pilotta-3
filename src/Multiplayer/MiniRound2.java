/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import Game.Suit;
import Game.Deck;
import Game.Card;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gmyrianthous
 */
public class MiniRound2 implements Runnable {
    private ArrayList<ConnectionNode> nodes;
    private Table table;
    private Round round;
    private Deck deck;
    private int firstBidder;
    private int miniRoundID;
    private Team firstTeam, secondTeam;
    private String selectedSuit;
    
    private int bidderID;
    private Team biddingTeam;
    
    private Card[] cardsOfRound = new Card[4];
    
    private boolean miniRoundEnded = false;
    
    /* Constructor */
    public MiniRound2(int miniRoundID, int firstBidder, ArrayList<ConnectionNode> nodes, 
                     Table table, Round round, Deck deck, Team firstTeam, Team secondTeam, String selectedSuit, Team biddingTeam) {
        this.miniRoundID = miniRoundID;
        this.firstBidder = firstBidder;
        this.nodes = nodes;
        this.table = table;
        this.round = round;
        this.deck = deck;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.selectedSuit = selectedSuit;
        this.biddingTeam = biddingTeam;
    } // MiniRound
    
    @Override
    public void run() {
        System.out.println("Mini-round " + this.miniRoundID + " has started.");
        bidderID = firstBidder;
        String previousCardsOnTable = "";
        String recentlySelectedCard = "";
        try {
            
          for (int j = 0; j < 4; j++){
            // The first player who is going to play a card.
            nodes.get(bidderID).connectionOutput.writeUTF("miniround-play");
            nodes.get(bidderID).connectionOutput.flush();
            System.out.println("Sending 'miniround-play' to player " + bidderID);
            
            // Send cards already played by other players (if any)
            nodes.get(bidderID).connectionOutput.writeUTF(previousCardsOnTable);
            nodes.get(bidderID).connectionOutput.flush();
            System.out.println("Sending previously played cards -> " + previousCardsOnTable);
            
            // Read playedCards.
            while (!nodes.get(bidderID).isCardSelected()){
                Thread.sleep(2000);
                System.out.println("Card not yet selected");
            } // while
            System.out.println("Card has been selected.");
            // Reset boolean value for next mini Rounds
            nodes.get(bidderID).setCardSelected(false);
            
            System.out.println("A card has been selected");
            recentlySelectedCard = nodes.get(bidderID).connectionInput.readUTF();
            System.out.println("Selected card: " + recentlySelectedCard);
            
            // Send the cards to the rest of the players.
            for (int i = 0; i < 4; i++){
                if (i != bidderID){
                    nodes.get(i).connectionOutput.writeUTF("miniround-player_selected_card");
                    nodes.get(i).connectionOutput.flush();
                    
                    nodes.get(i).connectionOutput.writeUTF(recentlySelectedCard);
                    nodes.get(i).connectionOutput.flush();
                    
                    nodes.get(i).connectionOutput.writeInt(bidderID);
                    nodes.get(i).connectionOutput.flush();
                    
                } // if
            } // for
            System.out.println("Card has been sent to other players.");
            
            // Add recentlyPlayedCard to the string
            previousCardsOnTable += recentlySelectedCard + "-";
            
            String splitCard = recentlySelectedCard;
            String splitted[] = splitCard.split("_");
            this.cardsOfRound[j] = new Card(splitted[0], new Suit(splitted[1])); 
            if (this.cardsOfRound[j].getSuit().getSuitToString().equals(selectedSuit))
                this.cardsOfRound[j].setKozi(true);
            this.cardsOfRound[j].setPlayerID(bidderID);
            System.out.println("Card added to cardsOfRound[]");
            
            // Player(bidderID) is now done.
            System.out.println("Incrementing bidder ID..");
            bidderID++;
            
          } // for
          
          // Print the cards of the round.
          for (int i = 0; i < cardsOfRound.length; i++){
              System.out.println("Card " + cardsOfRound[i].getFullCardToString()
                                 + " played by " + cardsOfRound[i].getPlayerID() 
                                 + ". (isKozi? " + cardsOfRound[i].isKozi());
          }
          
          // Get the kozi from bidding (from table) and then find out who is the winner + calculate points
          Team winnerTeam;
          int pazaPoints;
          Calculator cal = new Calculator(cardsOfRound, firstTeam, secondTeam);
          winnerTeam = cal.getMiniroundWinnerTeam();
          pazaPoints = cal.getMiniroundPoints();
          
          System.out.println("Mini-round winner team -> " + winnerTeam.getTeamName());
          System.out.println("Mini-round winner player -> " + cal.getWinnerPlayerID());
          System.out.println("Paza points -> " + pazaPoints);
          
          // Pass the results to the Round
          this.round.setMiniRoundScores(winnerTeam, pazaPoints);
          this.round.setPlayerTurn(cal.getWinnerPlayerID());
          
          Thread.sleep(3000);
          
          // Hide previously played cards
          for (int i = 0; i < 4; i++){
              nodes.get(i).connectionOutput.writeUTF("miniround-ended_hide_cards");
              nodes.get(i).connectionOutput.flush();
          } // for
          System.out.println("Players notified that miniround has ended (hide their cards)");
          
          System.out.println("End of miniround " + this.miniRoundID);
          //miniRoundEnded = true;
          
          
          
          
          bidderID = cal.getWinnerPlayerID();

          previousCardsOnTable = "";
          recentlySelectedCard = "";
         
          nodes.get(bidderID).connectionOutput.writeUTF("miniround-play_2");
          nodes.get(bidderID).connectionOutput.flush();
          System.out.println("Sending 'miniround-play_2' to player " + bidderID);
            
          // Send cards already played by other players (if any)
          nodes.get(bidderID).connectionOutput.writeUTF(previousCardsOnTable);
          nodes.get(bidderID).connectionOutput.flush();
          System.out.println("Sending previously played cards -> " + previousCardsOnTable);
          
          /*
          // Read playedCards.
          while (!nodes.get(bidderID).isCardSelected()){
              Thread.sleep(2000);
              System.out.println("Card not yet selected");
          } // while
          System.out.println("Card has been selected.");
          // Reset boolean value for next mini Rounds
          nodes.get(bidderID).setCardSelected(false);
          */
          
          
          
        } catch (IOException ex) {
            System.err.println("IOException in class MiniRound");
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(MiniRound.class.getName()).log(Level.SEVERE, null, ex);
        } // catch

    } // run()
    
    public boolean isMiniroundEnded(){
        return this.miniRoundEnded;
    }
    
} // class MiniRound

