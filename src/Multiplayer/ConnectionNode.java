/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import Game.Player;
import Networking.Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gmyrianthous
 */
public class ConnectionNode implements Runnable {
    
    /* Connection configuration */
    public ObjectInputStream connectionInput;
    public ObjectOutputStream connectionOutput;
    private int connectionID;
    private String clientResponse;
    private static boolean connectionIsAlive = true;
    private ArrayList<ConnectionNode> connectedNodes = new ArrayList<ConnectionNode>();
    
    /* Logic */
    public String clientName;
    public int clientID;
    private String clientBid = "";
    private boolean bitSubmitted = false;
    private String selectedCard = "";
    private boolean cardPlayed = false;
    
    public ConnectionNode(ObjectInputStream connectionInput, 
                          ObjectOutputStream connectionOutput, int connectionID, ArrayList<ConnectionNode> connectedNodes){
        this.connectionInput = connectionInput;
        this.connectionOutput = connectionOutput;
        this.connectionID = connectionID;
        this.connectedNodes = connectedNodes;
    } // ConnectionNode
    
    @Override
    public void run(){
        
        clientResponse = "";
        while (true){
            if (connectionIsAlive){
                try{
                    clientResponse = connectionInput.readUTF();
                    System.out.println("Message from client: " + clientResponse);

                    if (clientResponse.equals("client-connected")){
                        if (Server.getNoOfConnectedNodes() < 4){
                            System.out.println("server-client_is_accepted");
                            connectionOutput.writeUTF("server-client_is_accepted");
                            connectionOutput.flush();

                            // Read client's ID.
                            clientName = connectionInput.readUTF();
                            System.out.println("Client's name: " + clientName);

                            // send client ID
                            clientID = Server.getNoOfConnectedNodes();
                            connectionOutput.writeInt(clientID);
                            connectionOutput.flush();

                            // Increment number of connected nodes in server-side
                            Server.incrementClients();
                        } // if
                        else { // Table is full
                            connectionOutput.writeUTF("server-client_is_rejected");
                            connectionOutput.flush();
                            // Remove this connection node from the connected nodes arraylist
                            // that is kept in Server-side
                            Server.removeConnection(this);
                        } // else
                    } // if
                    else if (clientResponse.equals("client-enter_game_request")){
                        if (Server.getNoOfConnectedNodes() >= 4){
                            connectionOutput.writeUTF("server-starting_new_game");
                            connectionOutput.flush();
                            System.out.println("Starting new game");
                        } // if
                        else{
                            connectionOutput.writeUTF("server-table_not_yet_full");
                            connectionOutput.flush();
                            System.out.println("Table is not yet full. Waiting for new players..");
                        } // else
                    } // else if
                    else if (clientResponse.equals("client-request_players")){
                        connectionOutput.writeUTF("server-sending_table_players");
                        connectionOutput.flush();
                        
                        for (int i =0; i < 4; i++){
                            System.out.println("Sending player " + Server.players.get(i).getPlayerName());
                            Player tmpPl = Server.players.get(i);
                            //connectionOutput.writeUTF(Server.players.get(i).getPlayerName());
                            connectionOutput.writeObject(tmpPl);
                            connectionOutput.flush();    
                            
                            //connectionOutput.writeInt(Server.players.get(i).getPlayerID());
                            //connectionOutput.flush();
                        } // for
                    } // else if
                    else if (clientResponse.equals("client-bid_submitted")){
                        System.out.println("Bid Received");
                        clientBid = connectionInput.readUTF();
                        System.out.println("Bid: " + clientBid);
                        bitSubmitted = true;
                    } // else if
                    else if (clientResponse.equals("client-card_played")){
                        System.out.println("Card has been played");
                        selectedCard = connectionInput.readUTF();
                        System.out.println("Selected card: " + selectedCard);
                        cardPlayed = true;
                    } // else if

                } catch (ArrayIndexOutOfBoundsException ex){
                        System.out.println("Error!!!!! ConnectionNode");
                        ex.printStackTrace();
                        ex.getCause();
                        ex.getMessage();
                                
                } catch (IOException exception){
                    try{
                        connectionInput.close();
                        connectionOutput.close();  
                        System.out.println("Closing connection node's streams");
                        Thread.currentThread().interrupt();
                        connectionIsAlive = false;
                    } catch (IOException ex){
                        System.err.println("Failed to close streams");
                        System.err.println(ex.getMessage());
                    } 
                    System.err.println("Something went wrong");                
                } 
            } // if
            else { // Connection is dead.
                System.out.println("Exit.");
                break;
            } // else
        } // while     
    } // run
  
    public String getClientBid(){
        return this.clientBid;
    } // getClientBid
    
    public boolean isBidSubmitted() {
        return this.bitSubmitted;
    } // isBidSubmitted
    
    public String getClientSelectedCard() {
        System.out.println("getClientSelectedCard()  called -> " + selectedCard);
        return this.selectedCard;
    } // getClientSelectedCard
    
    public boolean isCardSelected() {
        return this.cardPlayed;
    }
    
    public void setCardSelected(boolean b){
        this.cardPlayed = b;
    } // setCardSelected
}
