/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdvancedSinglePlayerMC;

/**
 *
 * @author gmyrianthous
 */
public class MCAITeam {
    
    private String name;
    private MCClient firstPlayer, secondPlayer;
    
    /* Constructor */
    public MCAITeam(String name, MCClient firstPlayer, MCClient secondPlayer) {
        this.name = name;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    } // MCAITeam
    
    /* Accessor methods */
    public String getName() {
        return this.name;
    } // getName
    
    public MCClient getFirstPlayer() {
        return this.firstPlayer;
    } // getFirstPlayer
    
    public MCClient getSecondPlayer() {
        return this.secondPlayer;
    } // getSecondPlayer
    
} // class MCAITeam
