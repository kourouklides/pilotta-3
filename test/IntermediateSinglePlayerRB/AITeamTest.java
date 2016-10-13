/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IntermediateSinglePlayerRB;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gmyrianthous
 */
public class AITeamTest {
    
    /**
     * Test of getName method, of class AITeam.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        AIClient ai1 = new AIClient(0);
        AIClient ai2 = new AIClient(2);
        AITeam instance = new AITeam("Team A", ai1, ai2);
        String expResult = "Team A";
        String result = instance.getName();
        assertEquals(expResult, result);
    } // testGetName()

    /**
     * Test of getFirstPlayer method, of class AITeam.
     */
    @Test
    public void testGetFirstPlayer() {
        System.out.println("getFirstPlayer");
        AIClient ai1 = new AIClient(0);
        AIClient ai2 = new AIClient(2);
        AITeam instance = new AITeam("Team A", ai1, ai2);        
        AIClient expResult = ai1;
        AIClient result = instance.getFirstPlayer();
        assertEquals(expResult, result);
    } // testGetFirstPlayer()

    /**
     * Test of getSecondPlayer method, of class AITeam.
     */
    @Test
    public void testGetSecondPlayer() {
        System.out.println("getSecondPlayer");
        AIClient ai1 = new AIClient(0);
        AIClient ai2 = new AIClient(2);
        AITeam instance = new AITeam("Team A", ai1, ai2);
        AIClient expResult = ai2;
        AIClient result = instance.getSecondPlayer();
        assertEquals(expResult, result);
    } // testGetSecondPlayer()
    
}
