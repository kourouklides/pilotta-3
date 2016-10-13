/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IntermediateSinglePlayerRB;

/**
 *
 * @author gmyrianthous
 */
public class Team {
    
    private String teamName;
    private RealClient firstPlayer;
    private AIClient secondPlayer; 
    
    public Team(String teamName, RealClient firstPlayer, AIClient secondPlayer){
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
    
    public AIClient getSecondPlayer() {
        return this.secondPlayer;
    } // getSecondPlayer()
    
    
    
} // class Team
