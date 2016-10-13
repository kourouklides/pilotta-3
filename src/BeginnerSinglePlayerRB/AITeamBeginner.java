/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeginnerSinglePlayerRB;

import IntermediateSinglePlayerRB.RealClient;

/**
 *
 * @author gmyrianthous
 */
public class AITeamBeginner {
    
    private String teamName;
    private RealClient firstPlayer;
    private AIClientBeginner secondPlayer; 
    
    public AITeamBeginner(String teamName, RealClient firstPlayer, AIClientBeginner secondPlayer){
        this.teamName = teamName;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    } // Team
    
    public String getName() {
        return this.teamName;
    } // getName
    
    public RealClient getFirstPlayer() {
        return this.firstPlayer;
    } // getFirstPlayer()
    
    public AIClientBeginner getSecondPlayer() {
        return this.secondPlayer;
    } // getSecondPlayer()
    
    
    
} // class Team
