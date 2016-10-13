/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IntermediateSinglePlayerRB;

import Game.Card;
import Game.Suit;
import java.util.ArrayList;
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
public class AIClientTest {
    

    /**
     * Test of getName method, of class AIClient.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        AIClient instance = new AIClient(0);
        String expResult = "Bot 0";
        String result = instance.getName();
        assertEquals(expResult, result);
    } // testGetName()

    /**
     * Test of getID method, of class AIClient.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        AIClient instance = new AIClient(0);
        int expResult = 0;
        int result = instance.getID();
        assertEquals(expResult, result);
    } // testGetID()

    /**
     * Test of getCards method, of class AIClient.
     */
    @Test
    public void testGetCards() {
        System.out.println("getCards");
        AIClient instance = new AIClient(0);
        Card[] expResult = new Card[8];
        expResult[0] = new Card("Eight", new Suit("Hearts"));
        instance.setCards(expResult);
        
        Card[] result = instance.getCards();
        assertArrayEquals(expResult, result);
    } // testGetCards()


    /**
     * Test of play method, of class AIClient.
     */
    @Test
    public void testPlay() {
        System.out.println("play");
        ArrayList<String> playedCards = new ArrayList<String>();
        String kozi = "Diamonds";
        String teamOfRound = "Team B";
        AIClient instance = new AIClient(0);
        Card[] c = new Card[8];
        c[0] = new Card("Eight", new Suit("Hearts"));
        instance.setCards(c);
        AIClient instance2 = new AIClient(2);
        AITeam team = new AITeam("Team A", instance, instance2);
        instance.setTeam(team);
        String result = instance.play(playedCards, kozi, teamOfRound);
        assertNotNull(result);

        
    } // testPlay()


    /**
     * Test of setTeam method, of class AIClient.
     */
    @Test
    public void testSetTeam_AITeam() {
        System.out.println("setTeam");
        AIClient ai1 = new AIClient(0);
        AIClient ai2 = new AIClient(1);
        AITeam aiTeam = new AITeam("Team A", ai1, ai2);
        ai1.setTeam(aiTeam);
        assertEquals("Team A", aiTeam.getName());
    } // testSetTeam_AITeam
    
}
