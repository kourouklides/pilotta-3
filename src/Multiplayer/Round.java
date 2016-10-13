/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import Game.Deck;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gmyrianthous
 */
public class Round implements Runnable{
    
    private int roundID;
    private Team firstTeam, secondTeam;
    private ArrayList<ConnectionNode> nodes;
    private int turn;
    private Table table;
    private Deck deck;
    
    private int currentHighestBid;
    private String currentAtou;
    private boolean isRoundCompleted = false;
    private int currentHighestBidder;
    private Team currentHighestBidderTeam;
    
    /* Score calculation */
    private int firstTeamCurrentPoints = 0;
    private int secondTeamCurrentPoints = 0;
    
    private int playerTurn;
    
    private MiniRound miniRound;
    
    private static ArrayList<String> biddingList;
    /* Constructor */
    public Round(int roundID, Team firstTeam, Team secondTeam, ArrayList<ConnectionNode> nodes, 
                 int initialBidder, Table table, Deck deck) {
        this.roundID = roundID;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.nodes = nodes;
        this.turn = initialBidder;
        this.table = table;
        this.currentHighestBid = 7;
        this.biddingList = new ArrayList<String>();
        this.deck = deck;
    } // Round
    
    public void run() {
        System.out.println("Round is running..");
        
        // Send cards to the players of the table.
        try {
            String playerHand; 
            int counter = 0;
            for (int i = 0; i < 4; i++){
                nodes.get(i).connectionOutput.writeUTF("round-sending_cards");
                nodes.get(i).connectionOutput.flush();
                playerHand = "";
                for (int j = 0; j < 8; j++){
                    playerHand += deck.getDeckCards()[counter].getFullCardToString() + "-";
                    counter++;
                } // for
                playerHand = playerHand.substring(0, playerHand.length()-1); // Remove the last '-' from the playerHand string
                nodes.get(i).connectionOutput.writeUTF(playerHand);
                nodes.get(i).connectionOutput.flush();
                System.out.println("Player " + i + " hand: " + playerHand);
            } // for 
            System.out.println("Cards have been sent to all players");
        } catch (IOException e){
            System.err.println("Could not communicate with clients");
            e.printStackTrace();
        } // catch
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Round.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Bidding part
        String message = null;
        String split;
        String[] bidding;
        int biddingNo;
        String biddingSuit;
        try {    

            String previousBiddings = "";

            while ((this.biddingList.size() < 4) || !(this.biddingList.get(this.biddingList.size()-1).contains("Pass") && this.biddingList.get(this.biddingList.size()-2).contains("Pass") && this.biddingList.get(this.biddingList.size()-3).contains("Pass"))){
                System.out.println("Turn ->" + getTurn());
               nodes.get(getTurn()).connectionOutput.writeUTF("round-bid_now");
               nodes.get(getTurn()).connectionOutput.flush();
               System.out.println("Sending round-bid_now");

               nodes.get(getTurn()).connectionOutput.writeInt(this.currentHighestBid);
               nodes.get(getTurn()).connectionOutput.flush();
               System.out.println("Sending currentMaxBid");
               
               nodes.get(getTurn()).connectionOutput.writeUTF(previousBiddings);
               nodes.get(getTurn()).connectionOutput.flush();
               System.out.println("Sending previous biddings.. -> " + previousBiddings);
               
               /*
               nodes.get(getTurn()).connectionOutput.writeObject(this.biddingList);
               nodes.get(getTurn()).connectionOutput.flush();
               System.out.println("Sending biddingList of size " + this.biddingList.size());            
               */
               
               // Read player's bidding
               Thread.sleep(15000);
               System.out.println("Slept fro 15secs");
               message = nodes.get(getTurn()).getClientBid();
               
               // if Player ran out of pass his message is equal to ""
               if (message.equals("")){
                   System.out.println("Player ran out of time. ");
                   message = getTurn() + "_Pass";
               } // if
               
               
               this.biddingList.add(message);
               previousBiddings += message + "-";
               System.out.println("Bidding message " + message);

               if (!message.equals("0_Pass") && !message.equals("1_Pass") 
                   && !message.equals("2_Pass") && !message.equals("3_Pass")){
                   split = message;
                   bidding = split.split("_");

                   biddingNo = Integer.parseInt(bidding[1]);
                   biddingSuit = bidding[2];

                   System.out.println("Client's bid: " + biddingNo + " of " + biddingSuit);

                   if (biddingNo > this.currentHighestBid){
                       this.currentHighestBid = biddingNo;
                       this.currentAtou = biddingSuit;
                       this.currentHighestBidder = Integer.parseInt(bidding[0]);
                   } // if
               } // if
               
               incrementTurn();
               System.out.println("Printing current biddings..");
               for (int i = 0; i < this.biddingList.size(); i++)
                   System.out.println("BiddingList( " + i + ") -> " + this.biddingList.get(i));
         } // while
            
            if (this.currentHighestBidder == 0 || this.currentHighestBidder == 2)
                this.currentHighestBidderTeam = this.firstTeam;
            else
                this.currentHighestBidderTeam = this.secondTeam;
            
            // Bidding completed
            System.out.println("Round is about to start");
            System.out.println("Suit: " + currentHighestBid + " - Value: " + currentAtou);
            System.out.println("Highest Bidder ID: " + currentHighestBidder);
            System.out.println("Highest Bidder Team: " + currentHighestBidderTeam.getTeamName());
            
            
            System.out.println();
            System.out.println("Bidding history:");
            for (int i = 0; i < this.biddingList.size(); i++)
                System.out.println(this.biddingList.get(i));
            
            
            // Send the atou for this round
            for (int i = 0; i < 4; i++){
                nodes.get(i).connectionOutput.writeUTF("round-sending_atou");
                nodes.get(i).connectionOutput.flush();
                
                nodes.get(i).connectionOutput.writeUTF(currentAtou);
                nodes.get(i).connectionOutput.flush();
            } // for
            System.out.println("Atou Suit has been sent to all the players");
            
            // Create 8 MiniRound
            int playerID = 0;
            for (int i = 0; i < 8; i++){
                System.out.println("(Round) Starting a new MiniRound.");
                
                this.miniRound = new MiniRound(i, playerID, nodes, table, this, deck, firstTeam, secondTeam, currentAtou, currentHighestBidderTeam);
                miniRound.run();
 
                while (!this.miniRound.isMiniroundEnded()) {
                  Thread.sleep(3000);
                  System.out.println("Waiting for miniRound to end");
                } // while
                System.out.println("(ROUND) Miniround has ended");
                
                if (i > 0 ){
                    playerID = miniRound.getMiniRoundWinner();
                } // if
                
                System.out.println("Statistics: ");
                System.out.println(this.firstTeam.getTeamName() + ": " + this.firstTeam.getTeamScore());
                System.out.println(this.secondTeam.getTeamName() + ": " + this.secondTeam.getTeamScore());
                  
           } // for 
                
           // End of round, calculate and display the winner.
           if (this.currentHighestBidderTeam.getTeamName().equals(this.firstTeam.getTeamName())){
               if (currentHighestBid * 10 <= this.firstTeam.getTeamScore()){
                   for (int i = 0; i < 4; i++){
                       nodes.get(i).connectionOutput.writeUTF("round-sending_winner_team");
                       nodes.get(i).connectionOutput.flush();
                       
                       nodes.get(i).connectionOutput.writeUTF(this.firstTeam.getTeamName());
                       nodes.get(i).connectionOutput.flush();
                   } // for
               } else {
                   for (int i = 0; i < 4; i++){
                       nodes.get(i).connectionOutput.writeUTF("round-sending_winner_team");
                       nodes.get(i).connectionOutput.flush();
                       
                       nodes.get(i).connectionOutput.writeUTF(this.secondTeam.getTeamName());
                       nodes.get(i).connectionOutput.flush();
                   } // for
               } // else
           } else {
                if (currentHighestBid * 10 <= this.secondTeam.getTeamScore()){
                   for (int i = 0; i < 4; i++){
                       nodes.get(i).connectionOutput.writeUTF("round-sending_winner_team");
                       nodes.get(i).connectionOutput.flush();
                       
                       nodes.get(i).connectionOutput.writeUTF(this.secondTeam.getTeamName());
                       nodes.get(i).connectionOutput.flush();
                   } // for
               } else {
                   for (int i = 0; i < 4; i++){
                       nodes.get(i).connectionOutput.writeUTF("round-sending_winner_team");
                       nodes.get(i).connectionOutput.flush();
                       
                       nodes.get(i).connectionOutput.writeUTF(this.firstTeam.getTeamName());
                       nodes.get(i).connectionOutput.flush();
                   } // for
               } // else
           } // else
            
           
           this.isRoundCompleted = true;
        } catch (ArrayIndexOutOfBoundsException ex){
           System.err.println("AIOOBE in round !!!!");

        } catch(IOException e){
            System.err.println("Class Round -> IO Exceptin");
            e.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(Round.class.getName()).log(Level.SEVERE, null, ex);
        } 
    } // run()
    
    public int getRoundID(){
        return this.roundID;
    } // getRoundID
    
    public Team getFirstTeam(){
        return this.firstTeam;
    } // getFirstTeam
    
    public Team getSecondTeam() {
        return this.secondTeam;
    } // getSecondTeam
    
    public int getHighestBid() {
        return this.currentHighestBid;
    } // getHighestBid
    
    public void setHighestBid(int bid) {
        this.currentHighestBid = bid;
    } // setHighestBid
    
    public int getTurn() {
        return this.turn;
    } // getTurn()
    
    public void incrementTurn() {
        this.turn++;
        if (this.turn > 3)
            this.turn = 0;
    } // incrementTurn()
    
    public boolean isCompleted() {
        return this.isRoundCompleted;
    } // isCompleted()
    
    public void setMiniRoundScores(Team winnerTeam, int points){
        if (winnerTeam.getTeamName().equals(this.firstTeam.getTeamName())){
            this.firstTeamCurrentPoints += points;
            this.firstTeam.setTeamScore(points);
        }
        else{
            this.secondTeamCurrentPoints += points;
            this.secondTeam.setTeamScore(points);
        }
    } // setMiniRoundScores
    
    public void setPlayerTurn(int id){
        this.playerTurn =  id;
    } // setPlayerTurn
    
} // class Round
