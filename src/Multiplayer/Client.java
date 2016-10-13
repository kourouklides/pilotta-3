/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import GUI.CarpetGUI;
import GUI.MainSignedIn;
import GUI.BiddingGUI;
import Game.Suit;
import Game.Player;
import Game.Card;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author gmyrianthous
 */
public class Client implements Runnable{

    /* Client confuguration */
    private static final String host = "localhost";
    private static final int serverPortNumber = 4444;
    public ObjectInputStream clientInput;
    public ObjectOutputStream clientOutput;
    private static Socket socket;
    private String serverResponse;
    private int clientID;
    private String clientName;
    private static boolean gameStarted;
    private static int messageCounter = 0;
    private String clientCardsToString = "";
    
    public Player tablePlayers[]  = new Player[4];
    
    private String clientBid = "";
    private int currentMaxBid;
    private ArrayList<String> previousBiddings = new ArrayList<String>();

    private boolean bitSubmitted = false;
    private String biddingsString;
    
    
    private static Card[] clientCards = new Card[8];
    private String previouslyPlayedCards = "";
    private String playedCard = "";
    private CarpetGUI carpet;
    
    private String recentlyPlayedCard;
    private int recentlyPlayedCardPlayerID;
    private String selectedSuit = "";
    
    private int firstTeamScore;
    private int secondTeamScore;
    
    private ChatClient chatClient;
    
    public Client(){
        
    } // Client()
    
    @Override
    public void run(){
        try {
            socket = new Socket(host, serverPortNumber);
            System.out.println("Setting client up");
        } catch (IOException exception) {
            System.err.println("Connection error -> Probably server is down.");
            System.err.println(exception.getMessage());
            System.err.println(exception.getCause());
            JOptionPane.showMessageDialog(null, "Server is down. Try again later.", 
                                          "Connection error!", 
                                          JOptionPane.ERROR_MESSAGE);
            //System.exit(0);
        } // catch
        
        // Open input/output streams.
        try {
            clientOutput = new ObjectOutputStream(socket.getOutputStream());
            clientOutput.flush();
            clientInput = new ObjectInputStream(socket.getInputStream());
            System.out.println("Streams are now open!");
        } catch (IOException exception){
            System.err.println("Could not open stream");
            System.err.println(exception.getMessage());
            System.err.println(exception.getCause());
            JOptionPane.showMessageDialog(null, "Connection error", 
                                          "Something went wrong, please reconnect!", 
                                          JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } // catch
        gameStarted = false;
        clientName = JOptionPane.showInputDialog("Welcome! Please enter you name!");
        
        // Notify server that you are now sucessfully connected.
        try{
               clientOutput.writeUTF("client-connected");
               clientOutput.flush();
               System.out.println("Sending to server: 'client-connected'");
        } catch (IOException exception){
            System.err.println("Error while reading/writing from/to server (message: client-connected)");
            System.err.println(exception.getMessage());
            System.err.println(exception.getCause());
            JOptionPane.showMessageDialog(null, "Something went wrong, please reconnect!", 
                                          "Connection error",
                                          JOptionPane.ERROR_MESSAGE);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } // catch
            System.exit(0);
        } // catch
        
        serverResponse = "";
        String tableStatus = "";
        
        while (true){
            try{
                // Read server's response
                serverResponse = clientInput.readUTF();
                System.out.println("Message from Server: " + serverResponse);
                switch(serverResponse){
                    case "server-connection_established":
                        System.out.println("Connection with the server established");
                        /* TODO create an instance of player */    
                        break;
                    case "server-client_is_rejected":
                        System.out.println("Player rejected beacuse table is already full");
                        JOptionPane.showMessageDialog(null, "Table is already full. Please try again later",
                                                      "Error", JOptionPane.ERROR_MESSAGE);
                        System.out.println("Closing client..");
                        clientInput.close();
                        clientOutput.close();
                        socket.close();
                        System.exit(0);    
                        break;
                    case "server-client_is_accepted":
                        System.out.println("Player accepted on table!");

                        // Send client's name to server.
                        System.out.println("Sending name (" + clientName + ") to server");
                        clientOutput.writeUTF(clientName);
                        clientOutput.flush();

                        // Read client's ID.
                        clientID = clientInput.readInt();
                        System.out.println("Received client ID: " + clientID);

                        // Check if table is completed, so the game can start.
                        clientOutput.writeUTF("client-enter_game_request");
                        clientOutput.flush();

                        serverResponse = clientInput.readUTF();
                        System.out.println("Message from Server: " + serverResponse);

                        while(serverResponse.equals("server-table_not_yet_full")){
                            if (messageCounter == 0){
                                JOptionPane.showMessageDialog(null, "Table is not yet full. Please wait for other players.", 
                                                              "Notification", JOptionPane.INFORMATION_MESSAGE);
                                messageCounter++;
                            } // if

                            // Make a new request, in order to read table status
                            System.out.println("Sending a new request to check if table is now full");
                            clientOutput.writeUTF("client-enter_game_request");
                            clientOutput.flush();
                            
                            serverResponse = clientInput.readUTF();
                            System.out.println(serverResponse);

                            try {
                                Thread.sleep(2000);
                            } catch(InterruptedException exception){
                                System.err.println("Interrupted exception is thrwon");
                                System.err.println(exception.getMessage());
                                System.err.println(exception.getCause());
                            } // catch
                        } // while

                        System.out.println("Escaped from while loop: " + serverResponse);
                        //serverResponse = tableStatus;

                        chatClient = new ChatClient(this);
                        Thread chatThread = new Thread(chatClient);
                        chatThread.start();
                        
                        // Now 4 players have sucessfully been connected.
                        // Start a new game.
                        gameStarted = true;
                        System.out.println("Table is completed. Starting a new game");
                        MainSignedIn.gui.setVisible(false);

                        break;
                    case "round-sending_cards":
                        System.out.println("Receiving my cards..");
                        clientCardsToString = clientInput.readUTF();
                        System.out.println("My hand is: " + clientCardsToString);
                        
                        // Split the cards.
                        String split = clientCardsToString;
                        String[] cardsToString = split.split("-");
                        
                        String split2;
                        for (int i = 0; i < cardsToString.length; i++){
                            split2 = cardsToString[i];
                            String[] splitSuit = cardsToString[i].split("_");
                            Card newCard = new Card(splitSuit[0], new Suit(splitSuit[1]));
                            clientCards[i] = newCard;
                        } // for                
                                
                        clientOutput.writeUTF("client-request_players");
                        clientOutput.flush();
                        break;
                    case "server-sending_table_players":
                        System.out.println("Receiving players....");
                        /*
                        for (int i = 0; i < 4; i++){
                            String playerName = clientInput.readUTF();
                            int playerID = clientInput.readInt();
                            
                            tablePlayers[i] = new Player(playerID, playerName);
                        }
                        */
                        
                        try {
                            for (int i = 0; i < 4; i++){
                                tablePlayers[i] = (Player) clientInput.readObject();                                
                            } // for
                         } catch (ClassNotFoundException ex) {
                            System.err.println("Class not found exception.");
                        } // catch
                        
                        Thread.sleep(1000);
                        /*
                         java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                try {
                                    new CarpetGUI().setVisible(true);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                        */
                        carpet = new CarpetGUI();
                        carpet.setVisible(true);
                        System.out.println("CarpetGUI instanciated");
                        break;
                    case "round-bid_now":
                        System.out.println("Bidding time");
                        bitSubmitted = false;
                        
                        currentMaxBid = clientInput.readInt();
                        System.out.println("CurrentMaxBid is " + currentMaxBid);
                        
                        //previousBiddings = ((ArrayList<String>) clientInput.readObject());
                        //System.out.println("Received previous biddings list -> of size " + previousBiddings.size());
                        biddingsString = clientInput.readUTF();
                        System.out.println("Received previous biddings " + biddingsString);
                        
                        /* Create and display the bidding form */
                        java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                new BiddingGUI(currentMaxBid, biddingsString, getTablePlayers()).setVisible(true);
                            } // run() 
                       });

                        while (!bitSubmitted){
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            System.out.println("Waiting to submit bid..");
                        } // while
                        clientOutput.writeUTF("client-bid_submitted");
                        clientOutput.flush();
                        
                        clientOutput.writeUTF(getClientBid());
                        clientOutput.flush();
                        System.out.println("Bid submitted");
                        break;
                    case "round-sending_atou":
                        selectedSuit = clientInput.readUTF();
                        System.out.println("Atou for this round has been received -> " + selectedSuit);
                        
                        // Set Kozi
                        for (int i = 0; i < 8; i++){
                            if (clientCards[i].getSuit().getSuitToString().equals(selectedSuit))
                                clientCards[i].setKozi(true);
                        } // for
                        
                        break;
                    case "miniround-play":
                        previouslyPlayedCards = clientInput.readUTF();
                        System.out.println("Rceived previously played cards: " + previouslyPlayedCards);
                        
                        carpet.setCardSelected(false);
                        carpet.play(previouslyPlayedCards, selectedSuit);
                        while (!carpet.isCardSelected()){
                            Thread.sleep(2000);
                            System.out.println("Waiting to submit card");
                        }
                        
                        playedCard = carpet.getPlayedCard();
                        
                        clientOutput.writeUTF("client-card_played");
                        clientOutput.flush();
                        
                        System.out.println("Card was played (" + playedCard + ")");
                        
                        clientOutput.writeUTF(playedCard);
                        clientOutput.flush();
                        System.out.println("played card has been sent");
                        break;
                    case "miniround-player_selected_card":
                        recentlyPlayedCard = clientInput.readUTF();
                        System.out.println("Received recently played card -> " + recentlyPlayedCard);
                        
                        recentlyPlayedCardPlayerID = clientInput.readInt();
                        System.out.println("Card was played by player with ID " + recentlyPlayedCardPlayerID);
                        
                        carpet.playerSelectedCard(recentlyPlayedCard, recentlyPlayedCardPlayerID);
                        break;
                    case "miniround-ended_hide_cards":
                        System.out.println("Miniround has ended. Hide cards on table");
                        this.carpet.hideCards();
                        
                        break;
                    case "round_sending_score":
                        firstTeamScore = clientInput.readInt();
                        secondTeamScore = clientInput.readInt();
                        
                        System.out.println("Received team-scores: First team -> " 
                                            + firstTeamScore + ". Second team -> " + secondTeamScore);
                        break;         
                   case "round-miniround_ended":
                       System.out.println("Miniround ended..");
                       clientOutput.writeUTF("client-miniround_ended_confirm");
                       clientOutput.flush();
                       break;
                   case "round-sending_winner_team":
                       System.out.println("Receiving winner team");
                       String winnerTeam = clientInput.readUTF();
                       
                       JOptionPane.showMessageDialog(null, "Winner team of the round: " + winnerTeam, "Notification", JOptionPane.INFORMATION_MESSAGE);
                       
                       carpet.setVisible(false);
                       break;
                 } // switch()
            } catch (ArrayIndexOutOfBoundsException ex){
                        System.out.println("Client - ArrayIndexOutOfBounds ");
             }catch (IOException exception){
                System.err.println("Error while reading/writing from/to server!");
                exception.printStackTrace();
                JOptionPane.showMessageDialog(null, "Something went wrong, please reconnect!", 
                                              "Connection error", 
                                              JOptionPane.ERROR_MESSAGE);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        } // while
    } // run()  
    
    public String getClientCardsToString() {
        return this.clientCardsToString;
    } // getClientCards()
    
    public Card[] getClientCards() {
        return clientCards;
    } // getClientCards()
    
    public String getClientName(){
        return clientName;
    } // getClientName
    
    public int getClientID(){
        return this.clientID;
    } // getClientID()
    
    public String getClientBid() {
        return this.clientBid;
    } // getClientBid
    
    public void setClientBid(String b) {
        this.clientBid = b;
    } // setClientBid
    
    public void setBitSubmitted(boolean b){
        this.bitSubmitted = b;
    } // setBitSubmitted
    
    public Player[] getTablePlayers(){
        return this.tablePlayers;
    } // getTablePlayers
    
    public ChatClient getChatClient(){
        return this.chatClient;
    } // getChatClient
    
    public void chatMessageReceived(String message){
        this.carpet.showMessage(message);
    } // chatMessageReceived()
    

    
} // class Client
