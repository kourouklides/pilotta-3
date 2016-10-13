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
public class AIGame implements Runnable{
    
    private RealClient client;
    private AIClient firstClient, secondClient, thirdClient;
    private int initialBidderID = 0;

    private int firstTeamPoints, secondTeamPoints;
    
    /* Constructor */
    public AIGame(RealClient client) {
        this.client = client;
        this.firstTeamPoints = 0;
        this.secondTeamPoints = 0;
    } // AIGame
    
    @Override
    public void run() {
        firstClient = new AIClient(1);
        secondClient = new AIClient(2);
        thirdClient = new AIClient(3);
        
        // Split the players into Teams.
        Team firstTeam = new Team("Team A", this.client, this.secondClient);
        AITeam secondTeam = new AITeam("Team B", this.firstClient, this.thirdClient);
        
        this.secondClient.setTeam(firstTeam);
        this.firstClient.setTeam(secondTeam);
        this.thirdClient.setTeam(secondTeam);
        
        
        // Start consecutive rounds until 301 is reached
        AIRound round;
        while (this.firstTeamPoints < 302 && this.secondTeamPoints < 302){
            System.out.println("New round has been started");
            round = new AIRound(this, firstTeam, secondTeam, initialBidderID, this.firstTeamPoints, this.secondTeamPoints);
            round.run();

            this.firstTeamPoints += round.getFirstTeamPoints();
            this.secondTeamPoints += round.getFirstTeamPoints();
            
            System.out.println("Round has ended");
            System.out.println("Stats until now: ");
            System.out.println("Team A Points: " + firstTeamPoints);
            System.out.println("Team B Points: " + secondTeamPoints);
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
        this.client.showMessage("Game has ended. " + winnerTeam + " won the game");
        System.exit(0);
    } // start()
    
    private void incrementInitialBidderID() {
        this.initialBidderID++;
        if (this.initialBidderID > 3)
            this.initialBidderID = 0;
    } // incrementInitialBidderID()
    
    public void setFirstTeamPoints(int p) {
        this.firstTeamPoints += p;
    } // setFirstTeamPoints
    
    public void setSecondTeamPoints(int p) {
        this.secondTeamPoints += p;
    } // setSecondTeamPoints    
} // AIGame
