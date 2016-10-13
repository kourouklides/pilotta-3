/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdvancedSinglePlayerMC;

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
public class MCClientTest {
    

    /**
     * Test of play method, of class MCClient.
     */
    @Test
    public void testPlay() {
        System.out.println("play");
        ArrayList<String> cardsOnTable = new ArrayList<String>();
        String kozi = "Hearts";
        String team = "Team A";
        MCClient instance = new MCClient(0);
        Card[] c = new Card[8];
        c[0] = new Card("Eight", new Suit("Hearts"));
        instance.setCards(c);
        String result = instance.play(cardsOnTable, kozi, team);
        assertNotNull(result);
    } // testPlay()


    /**
     * Test of getID method, of class MCClient.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        MCClient instance = new MCClient(0);
        int expResult = 0;
        int result = instance.getID();
        assertEquals(expResult, result);
    } // testGetID()

    /**
     * Test of getName method, of class MCClient.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        MCClient instance = new MCClient(0);
        String expResult = "Bot 0";
        String result = instance.getName();
        assertEquals(expResult, result);
    } // testGetName()

    /**
     * Test of getCards method, of class MCClient.
     */
    @Test
    public void testGetCards() {
        System.out.println("getCards");
        MCClient instance = new MCClient(0);
        Card[] c = new Card[8];
        c[0] = new Card("Eight", new Suit("Hearts"));
        instance.setCards(c);
        Card[] result = instance.getCards();
        assertArrayEquals(c, result);

    } // testGetCards()

    /**
     * Test of setTeam method, of class MCClient.
     */
    @Test
    public void testSetTeam_MCAITeam() {
        System.out.println("setTeam");
        MCClient instance = new MCClient(0);
        MCClient instance2 = new MCClient(1);
        MCAITeam team = new MCAITeam("Team A", instance, instance2);
        instance.setTeam(team);
        assertEquals("Team A", team.getName());
    }
    
}
