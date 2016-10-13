/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISimulations;

/**
 *
 * @author gmyrianthous
 */

public class BeginnerIntermediateSimulationMain {
    
    private static int winsForBeginnerTeam;
    private static int winsForIntermediateTeam;
    
    public static void main(String[] args) {
        winsForBeginnerTeam = 0;
        winsForIntermediateTeam = 0;
        
        int teamApointsFinal;
        int teamBpointsFinal;
        
        
        for (int i = 0; i < 100; i++){
            teamApointsFinal = 0;
            teamBpointsFinal = 0;
            
            // play a game

            while (teamApointsFinal < 301 && teamBpointsFinal < 301) {
                BeginnerIntermediateSimulation sim = new BeginnerIntermediateSimulation();
                sim.dealDeck();
                sim.play();

                teamApointsFinal += sim.getRoundPointsTeamA();
                teamBpointsFinal += sim.getRoundPointsTeamB();
                System.out.println("Current scores: ");
                System.out.println("Team A: " + teamApointsFinal + " - Team B: " + teamBpointsFinal);
            } // while
           
            if (teamApointsFinal > teamBpointsFinal)
                    winsForIntermediateTeam++;
                else
                    winsForBeginnerTeam++;
        } // for
        System.out.println("\n*** Simulation results ***");
        System.out.println("Total number of simulations: 100");
        System.out.println("Games won by Team A (Intermediate Level): " + winsForIntermediateTeam);
        System.out.println("Games won by Team B (Beginner Level): " + winsForBeginnerTeam); 
        System.out.println("Percentage of wins for Team A: " + (winsForIntermediateTeam * 1.0));
        System.out.println("Percentage of wins for Team B: " + (winsForBeginnerTeam * 1.0));
        
        

        
    } // main
    
} // class BeginnerIntermediateSimulationMain
