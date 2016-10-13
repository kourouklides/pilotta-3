/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking;

import Chat.ChatServer;
import Multiplayer.ConnectionNode;
import Game.Player;
import Multiplayer.Table;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gmyrianthous
 */
public class Server implements Serializable {
    
    /* Server - Connections configuration */
    private static final int portNumber = 4444;
    private static Socket socket;
    private static ObjectInputStream serverInput;
    private static ObjectOutputStream serverOutput;
    private static ServerSocket serverSocket;
    public static ArrayList<ConnectionNode> connectedNodes;
    private static boolean gameHasStarted;
    private static ArrayList<ChatServer> chatNodes;
    
    /* Game logic */
    private static int noOfConnectedNodes; // 4 players are connected
    public static ArrayList<Player> players;
    
    public static void main(String args[]){
        try {
            serverSocket = new ServerSocket(portNumber);
            System.out.println("Server is up. Port: " + portNumber);
            System.out.println();
        } catch (IOException exception){
            System.err.println("Could not set up Server (port: " + portNumber +")");
            System.err.println(exception.getMessage());
            System.err.println(exception.getCause());
            System.exit(0);
        } // catch
        
        // Set number of connected nodes, initially to zero.
        noOfConnectedNodes = 0;
        connectedNodes = new ArrayList<ConnectionNode>();
        chatNodes = new ArrayList<ChatServer>();
        gameHasStarted = false;
        players = new ArrayList<Player>();
        System.out.println("Server configuration is completed!");
        
        while (true){
            // Accept incoming connections.
            try{ 
                socket = serverSocket.accept();
                System.out.println("New connection established.");
                System.out.println("Connection ID: " + noOfConnectedNodes);
                System.out.println("Connection IP: " + socket.getInetAddress());
                System.out.println();
               
                // Open new input/output streams.
                try {
                     serverInput = new ObjectInputStream(socket.getInputStream());
                    serverOutput = new ObjectOutputStream(socket.getOutputStream()); 
                    
                    // Create an instance of ConnectionNode to establish
                    // communication between Server and Client.
                    
                    ConnectionNode cn = new ConnectionNode(serverInput, serverOutput, getNoOfConnectedNodes(), connectedNodes);
                                    
                    // Add this new connection to the arraylist.
                    connectedNodes.add(getNoOfConnectedNodes(), cn);

                    // Start a connection node in a new thread.
                    Thread t = new Thread(connectedNodes.get(getNoOfConnectedNodes()));
                    t.start();
                    

                } catch(IOException exception){
                    System.err.println("Could not open streams.");
                    System.err.println(exception.getMessage());
                    System.err.println(exception.getCause());
                } // catch
                                
                // If the table is completed, start a new game.
                if (connectedNodes.size() == 4 && !gameHasStarted){
                    // Create two different teams.

                    Thread.sleep(4000);
                    
                    // Chat
                    
                    
                    /*
                    Team teamA = new Team("TEAM A", players.get(0), players.get(1));
                    Team teamB = new Team("TEAM B", players.get(2), players.get(3));
                    */
                    Table newTable = new Table(connectedNodes);
                    gameHasStarted = true;
                    
                    Thread tableThread = new Thread(newTable);
                    tableThread.start();
                    
                    System.out.println("A new table has been created!");
                    /*
                    System.out.println("Printing the Players of the table..");
                    printPlayers();
                    */

                } // if
            } catch (ArrayIndexOutOfBoundsException ex){
                        System.out.println("Server - Array index out of bounds");
            }catch (IOException exception){
                System.err.println("Node could not be accepted.");
                System.err.println(exception.getMessage());
                System.err.println(exception.getCause());
            } catch (InterruptedException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } // catch
            

            
        } // while
    } // main
    
    public static int getServerPortNumber(){
        return portNumber;
    } // getServerPortNuumber()
    
    public static int getNoOfConnectedNodes(){
        return noOfConnectedNodes;
    } // getNoOfConnectedNodes
    
    public static void incrementClients(){
        noOfConnectedNodes++;
    } // incrementClients
    
    public static void removeConnection(ConnectionNode cn){
        connectedNodes.remove(cn);
        System.out.println("A connection node has been removed.");
    } // removeConnection
    
    public static void addPlayer(Player pl){
        players.add(pl);
    }
    
    /*
    public static void printPlayers(){
        for (Player pl : players){
            System.out.println("Player Name: " + pl.getPlayerName() + " with ID "
                               + pl.getPlayerID() + " sitting in position " 
                               + pl.getPositionOnTable());
        } // for
    } // printPlayers()
    */
   
} // class Server
