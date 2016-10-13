/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author gmyrianthous
 */
public class ChatNode implements Runnable {
    
    private DataInputStream nodeInput;
    private DataOutputStream nodeOutput;
    private ArrayList<ChatNode> nodes;
    private int chatNodeID;
    
    private String nodeName;
    private int nodeID;
    private String messageToBeSent;
    
    
    private String clientMessage = "";
    /* Constructor */
    public ChatNode(int chatNodeID, DataInputStream nodeInput, DataOutputStream nodeOutput, ArrayList<ChatNode> nodes){
        this.chatNodeID = chatNodeID;
        this.nodeInput = nodeInput;
        this.nodeOutput = nodeOutput;
        this.nodes = nodes;
    } // ChatNode
    
    @Override
    public void run() {
        try{
            while (true) {
                clientMessage = this.nodeInput.readUTF();
               if (clientMessage.equals("chatClient-hello")){
                   this.nodeOutput.writeUTF("chat-request_name_id");
                   
                   this.nodeName = nodeInput.readUTF();
                   this.nodeID = nodeInput.readInt();
                   
                   System.out.println("ChatClient: " + this.nodeName + " with ID: " + this.nodeID);                
               } else if (clientMessage.equals("chatClient-sending_message")){
                   nodes = ChatServer.getNodes();
                   messageToBeSent = nodeInput.readUTF();
                   for (int i = 0; i < 4; i++){
                       this.nodes.get(i).nodeOutput.writeUTF("chat-sending_message");
                       this.nodes.get(i).nodeOutput.writeUTF(this.nodeName + " says: "+ messageToBeSent);
                   }
                   System.out.println("Message has been sent: " + messageToBeSent);
               } 
            } // while
        } catch(IOException ex){
            System.err.println("Error in ChatNode");
            ex.printStackTrace();
        }
        
    } // run()
    
} // class ChatNode
