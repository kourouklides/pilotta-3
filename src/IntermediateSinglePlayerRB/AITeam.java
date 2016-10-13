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
public class AITeam {
    
    private String teamName;
    private AIClient firstPlayer, secondPlayer;
    
    public AITeam(String teamName, AIClient firstPlayer, AIClient secondPlayer){
        this.teamName = teamName;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    } // AITeam
    
    public String getName() {
        return this.teamName;
    } // getName()
    
    public AIClient getFirstPlayer() {
        return this.firstPlayer;
    } // getFirstPlayer
    
    public AIClient getSecondPlayer() {
        return this.secondPlayer;
    } // getSecondPlayer
    
} // class AITeam
