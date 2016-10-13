/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author gmyrianthous
 */
public class ChatClient implements Runnable {
    
    public DataInputStream chatInput;
    public DataOutputStream chatOutput;
    private Socket socket;
    
    private Client client;
    private String message = "";
    private String chatMessage = "";
   
    
    public ChatClient(Client client) {
        this.client = client;
    } // ChatClient
    
    public void run() {
        try {
            socket = new Socket("localhost", 4445);
            chatInput = new DataInputStream(socket.getInputStream());
            chatOutput  = new DataOutputStream(socket.getOutputStream());
            
            chatOutput.writeUTF("chatClient-hello");
            while (true){
                message = chatInput.readUTF();
                if (message.equals("chat-request_name_id")){
                    this.chatOutput.writeUTF(client.getClientName());
                    this.chatOutput.writeInt(client.getClientID());
                } else if (message.equals("chat-sending_message")){
                    chatMessage = chatInput.readUTF();
                    this.client.chatMessageReceived(chatMessage);
                } // else if
            } // while
            
            
        } catch (IOException ex){
            System.err.println("Problem while writing/reading to/from chatServer");
            ex.printStackTrace();
        } // catch
    } // run
} // class ChatClient
