/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdvancedSinglePlayerMC;

import IntermediateSinglePlayerRB.RealClient;

/**
 *
 * @author gmyrianthous
 */
public class MCTeam {
    
    private String name;
    private RealClient firstPlayer;
    private MCClient secondPlayer;
    
    /* Constructor */
    public MCTeam(String name, RealClient firstPlayer, MCClient secondPlayer) {
       this.name = name; 
       this.firstPlayer = firstPlayer;
       this.secondPlayer = secondPlayer;
    } // MCTeam
    
    
    /* Accessor methos */
    public String getName() {
        return this.name;
    } // getName
    
    public RealClient getFirstPlayer() {
        return this.firstPlayer; 
    } // getFirstPlayer
    
    public MCClient getSecondPlayer() {
        return this.secondPlayer;
    } // getSecondPlayer
    
} // class MCTeam
