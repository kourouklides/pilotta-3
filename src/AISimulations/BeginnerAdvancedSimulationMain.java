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
public class BeginnerAdvancedSimulationMain {
    
    private static int noOfGameWinsBeginner; // beginner level
    private static int noOfGameWinsAdvanced; // advanced level  
    
    public static void main(String[] args) {
        
        noOfGameWinsAdvanced = 0;
        noOfGameWinsBeginner = 0;
        
        int teamApointsFinal;
        int teamBpointsFinal;
        
        for (int i = 0; i < 4; i++){
            teamApointsFinal = 0;
            teamBpointsFinal = 0;
            
            // play a game

            while (teamApointsFinal < 301 && teamBpointsFinal < 301) {
                IntermediateAdvancedSimulation sim = new IntermediateAdvancedSimulation();

                sim.distributeHands();
                sim.play();

                teamApointsFinal += sim.getRoundPointsTeamA();
                teamBpointsFinal += sim.getRoundPointsTeamB();
                System.out.println("Current scores: ");
                System.out.println("Team A: " + teamApointsFinal + " - Team B: " + teamBpointsFinal);
            } // while
           
            if (teamApointsFinal > teamBpointsFinal)
                    noOfGameWinsAdvanced++;
                else
                    noOfGameWinsBeginner++;
        } // for
        System.out.println("*** Simulation results ***");
        System.out.println("Games won by Team A (Advanced Level): " + noOfGameWinsAdvanced);
        System.out.println("Games won by Team B (Beginner Level): " + noOfGameWinsBeginner);
    } // main 
} // class BeginnerAdvancedSimulationMain
