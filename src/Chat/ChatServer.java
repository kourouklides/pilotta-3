/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author gmyrianthous
 */
public class ChatServer {
    private static DataInputStream chatInput;
    private static DataOutputStream chatOutput;
    private static int chatID;
    public static ArrayList<ChatNode> chatNodes;
    
    private static int port = 4445;
    private static ServerSocket serverSocket;
    private static Socket socket;
    
    private static int nodesCounter = 0;
    
    private static boolean chatCreated = false;
    
    public static void main(String[] args) {
        
        try {
            serverSocket = new ServerSocket(port);
            chatNodes = new ArrayList<ChatNode>();
            while (true) {
                socket = serverSocket.accept();
                System.out.println("Chat client has been connected");
                
                chatInput = new DataInputStream(socket.getInputStream());
                chatOutput = new DataOutputStream(socket.getOutputStream());
               
                
                ChatNode n = new ChatNode(nodesCounter, chatInput, chatOutput, chatNodes);
                chatNodes.add(nodesCounter, n);
                
                Thread t = new Thread(n);
                t.start();
                nodesCounter++;

            }
        } catch(IOException ex){
            ex.printStackTrace();
        } // catch
        
        
    } // run()
    
    public static ArrayList<ChatNode> getNodes() {
        return chatNodes;
    } // getNodes()

} // class Chat
