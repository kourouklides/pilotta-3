/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeginnerSinglePlayerRB;

/**
 *
 * @author gmyrianthous
 */
public class AITeamBeginner2 {
    
    private String teamName;
    private AIClientBeginner firstPlayer, secondPlayer;
    
    public AITeamBeginner2(String teamName, AIClientBeginner firstPlayer, AIClientBeginner secondPlayer){
        this.teamName = teamName;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    } // AITeam
    
    public String getName() {
        return this.teamName;
    } // getName()
    
    public AIClientBeginner getFirstPlayer() {
        return this.firstPlayer;
    } // getFirstPlayer
    
    public AIClientBeginner getSecondPlayer() {
        return this.secondPlayer;
    } // getSecondPlayer
    
} // class AITeam
