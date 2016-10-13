/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import Game.Player;

/**
 *
 * @author gmyrianthous
 */
public class Team {
    
    private Player firstPlayer, secondPlayer;
    private String teamName;
    private int score;
    
    /* Constructor */
    public Team(String teamName, Player firstPlayer, Player secondPlayer){
        this.teamName = teamName;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.score = 0;
    } // Team
    
    public Player getTeamFirstPlayer(){
        return this.firstPlayer;
    } // getTeamFirstPlayer
    
    public Player getTeamSecondPlayer(){
        return this.secondPlayer;
    } // getTeamSecondPlayer
    
    public String getTeamName() {
        return this.teamName;
    } // getTeamName
    
    public int getTeamScore(){
        return this.score;
    } // getTeamScore
    
    public void setTeamScore(int points){
        this.score += points;
    } // setTeamScore
    
    public boolean playerBelongsToTeam(Player p){
       return (p.equals(this.firstPlayer) || (p.equals(this.secondPlayer)));
    } // playerBelongsToTeam
    
} // class Team
