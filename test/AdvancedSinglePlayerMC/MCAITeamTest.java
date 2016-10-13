/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdvancedSinglePlayerMC;

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
public class MCAITeamTest {
    
    /**
     * Test of getName method, of class MCAITeam.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        MCClient ai1 = new MCClient(0);
        MCClient ai2 = new MCClient(1);
        MCAITeam instance = new MCAITeam("Team A", ai1, ai2);
        String expResult = "Team A";
        String result = instance.getName();
        assertEquals(expResult, result);
    } // testGetName()

    /**
     * Test of getFirstPlayer method, of class MCAITeam.
     */
    @Test
    public void testGetFirstPlayer() {
        System.out.println("getFirstPlayer");
        MCClient ai1 = new MCClient(0);
        MCClient ai2 = new MCClient(1);
        MCAITeam instance = new MCAITeam("Team A", ai1, ai2);
        MCClient result = instance.getFirstPlayer();
        assertEquals(ai1, result);
    } // testGetFirstPlayer()

    /**
     * Test of getSecondPlayer method, of class MCAITeam.
     */
    @Test
    public void testGetSecondPlayer() {
        System.out.println("getSecondPlayer");
        MCClient ai1 = new MCClient(0);
        MCClient ai2 = new MCClient(1);
        MCAITeam instance = new MCAITeam("Team A", ai1, ai2);
        MCClient result = instance.getSecondPlayer();
        assertEquals(ai2, result);
    } // testGetSecondPlayer()
    
}
