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
public class MCGame implements Runnable {
    
    private RealClient client;
    private MCClient firstClient, secondClient, thirdClient;
    private int firstTeamPoints, secondTeamPoints;
    
    private int playerTurnID;
    
    /* Constructor */
    public MCGame(RealClient client){
        this.client = client;
        this.firstTeamPoints = 0;
        this.secondTeamPoints = 0;
    } // MCGame
    
    @Override
    public void run() {
        System.out.println("New Monte Carlo game started.");
        
        firstClient = new MCClient(1);
        secondClient = new MCClient(2);
        thirdClient = new MCClient(3);
        
        MCTeam firstTeam = new MCTeam("Team A", client, this.secondClient);
        MCAITeam secondTeam = new MCAITeam("Team B", firstClient, thirdClient);
        
        secondClient.setTeam((firstTeam));
        firstClient.setTeam(secondTeam);
        thirdClient.setTeam(secondTeam);
        
        // Rounds
        this.playerTurnID = 0;
        MCRound round;
        
        // while
        while (this.firstTeamPoints < 302 || this.secondTeamPoints < 302) {
            System.out.println("A new round has been started");
            round = new MCRound(firstTeam, secondTeam, playerTurnID, this.firstTeamPoints, this.secondTeamPoints);
            round.run();            
            
            this.firstTeamPoints += round.getFirstTeamPoints();
            this.secondTeamPoints += round.getSecondTeamPoints();
            this.incrementPlayerTurnID();
        } // while

        /* Game has ended */
        String winnerTeam;
        if (firstTeamPoints > secondTeamPoints)
            winnerTeam = "Team A";
        else
            winnerTeam = "Team B";
        
        System.out.println("Game has ended");
        this.client.showMessage("Game has ended. " + winnerTeam + " won the game");
        System.exit(0);        
    } // run
    
    private void incrementPlayerTurnID() {
        this.playerTurnID++;
        if (this.playerTurnID > 3)
            this.playerTurnID = 0;
    }
    
} // class MCGame
