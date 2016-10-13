/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeginnerSinglePlayerRB;

import IntermediateSinglePlayerRB.RealClient;
import GUI.MainSignedIn;

/**
 *
 * @author gmyrianthous
 */
public class AIGameBeginner implements Runnable{
    
    private RealClient client;
    private AIClientBeginner firstClient, secondClient, thirdClient;
    private int initialBidderID = 0;

    private int firstTeamPoints, secondTeamPoints;
    
    /* Constructor */
    public AIGameBeginner(RealClient client) {
        this.client = client;
        this.firstTeamPoints = 0;
        this.secondTeamPoints = 0;
    } // AIGame
    
    @Override
    public void run() {
        firstClient = new AIClientBeginner(1);
        secondClient = new AIClientBeginner(2);
        thirdClient = new AIClientBeginner(3);
        
        // Split the players into Teams.
        AITeamBeginner firstTeam = new AITeamBeginner("Team A", this.client, this.secondClient);
        AITeamBeginner2 secondTeam = new AITeamBeginner2("Team B", this.firstClient, this.thirdClient);
        
        this.secondClient.setTeam(firstTeam);
        this.firstClient.setTeam(secondTeam);
        this.thirdClient.setTeam(secondTeam);
        AIRoundBeginner round;
        while (this.firstTeamPoints < 50 && this.secondTeamPoints < 50){
            System.out.println("New round has been started");
            round = new AIRoundBeginner(this, firstTeam, secondTeam, initialBidderID, this.firstTeamPoints, this.secondTeamPoints);
            round.run();

            //this.firstTeamPoints += round.getFirstTeamPoints();
            //this.secondTeamPoints += round.getFirstTeamPoints();
            
            System.out.println("Round has ended");
            System.out.println("Stats until now: ");
            System.out.println("Team A Points: " + this.firstTeamPoints);
            System.out.println("Team B Points: " + this.secondTeamPoints);
            this.client.sendRoundWinner(this.firstTeamPoints, this.secondTeamPoints);

            this.incrementInitialBidderID();
        } // while
        
        /* Game has ended */
        String winnerTeam;
        if (firstTeamPoints > secondTeamPoints)
            winnerTeam = "Team A";
        else
            winnerTeam = "Team B";
        
        System.out.println("Game has ended");
        this.client.showMessage("Game has ended. " + winnerTeam + " won the game.");
        //System.exit(0);
        this.client.disposeGUI();
        //new GUI().setVisible(true);
    } // start()
    
    private void incrementInitialBidderID() {
        this.initialBidderID++;
        if (this.initialBidderID > 3)
            this.initialBidderID = 0;
    } // incrementInitialBidderID()
    
    public int getFirstTeamPoints() {
        return this.firstTeamPoints;
    } // getFirstTeamPoints
    
    public int getSecondTeamPoints() {
        return this.secondTeamPoints;
    } // getSecondTeamPoints
    
    public void setFirstTeamPoints(int p) {
        this.firstTeamPoints += p;
    } // setFirstTeamPoints
    
    public void setSecondTeamPoints(int p) {
        this.secondTeamPoints += p;
    } // setSecondTeamPoints
} // AIGame
