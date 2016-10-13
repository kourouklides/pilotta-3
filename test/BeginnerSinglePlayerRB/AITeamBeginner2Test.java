/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeginnerSinglePlayerRB;

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
public class AITeamBeginner2Test {


    /**
     * Test of getName method, of class AITeamBeginner2.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        AIClientBeginner ai1 = new AIClientBeginner(0);
        AIClientBeginner ai2 = new AIClientBeginner(1);
        AITeamBeginner2 instance = new AITeamBeginner2("Team A", ai1, ai2);
        String expResult = "Team A";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFirstPlayer method, of class AITeamBeginner2.
     */
    @Test
    public void testGetFirstPlayer() {
        System.out.println("getFirstPlayer");
        AIClientBeginner ai1 = new AIClientBeginner(0);
        AIClientBeginner ai2 = new AIClientBeginner(1);
        AITeamBeginner2 instance = new AITeamBeginner2("Team A", ai1, ai2);
        AIClientBeginner expResult = ai1;
        AIClientBeginner result = instance.getFirstPlayer();
        assertEquals(expResult, result);
    } // testGetFirstPlayer()

    /**
     * Test of getSecondPlayer method, of class AITeamBeginner2.
     */
    @Test
    public void testGetSecondPlayer() {
        System.out.println("getSecondPlayer");
        AIClientBeginner ai1 = new AIClientBeginner(0);
        AIClientBeginner ai2 = new AIClientBeginner(1);
        AITeamBeginner2 instance = new AITeamBeginner2("Team A", ai1, ai2);
        AIClientBeginner expResult = ai2;
        AIClientBeginner result = instance.getSecondPlayer();
        assertEquals(expResult, result);
    } // testGetSecondPlayer()
    
}
