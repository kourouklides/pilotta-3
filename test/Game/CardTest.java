/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import javax.swing.ImageIcon;
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
public class CardTest {
    /**
     * Test of getCardToString method, of class Card.
     */
    @Test
    public void testGetCardToString() {
        System.out.println("getCardToString");
        Card instance = new Card("Eight", new Suit("Hearts"));
        String expResult = "Eight";
        String result = instance.getCardToString();
        assertEquals(expResult, result);
    } // testGetCardToString()

    /**
     * Test of getCardRank method, of class Card.
     */
    @Test
    public void testGetCardRank() {
        System.out.println("getCardRank");
        Card instance = new Card("Eight", new Suit("Hearts"));
        int expResult = 2;
        int result = instance.getCardRank();
        assertEquals(expResult, result);
    } // testGetCardRank()

    /**
     * Test of getCardRankKozi method, of class Card.
     */
    @Test
    public void testGetCardRankKozi() {
        System.out.println("getCardRankKozi");
        Card instance = new Card("Jack", new Suit("Hearts"));
        int expResult = 8;
        int result = instance.getCardRankKozi();
        assertEquals(expResult, result);
    } // testGetCardRankKozi

    /**
     * Test of getCardPoints method, of class Card.
     */
    @Test
    public void testGetCardPoints() {
        System.out.println("getCardPoints");
        Card instance = new Card("Eight", new Suit("Hearts"));
        int expResult = 0;
        int result = instance.getCardPoints();
        assertEquals(expResult, result);
    } // testGetCardPoints()

    /**
     * Test of getCardPointsKozi method, of class Card.
     */
    @Test
    public void testGetCardPointsKozi() {
        System.out.println("getCardPointsKozi");
        Card instance = new Card("Jack", new Suit("Hearts"));
        int expResult = 20;
        int result = instance.getCardPointsKozi();
        assertEquals(expResult, result);
    } // testGetCardPointsKozi()

    /**
     * Test of setKozi method, of class Card.
     */
    @Test
    public void testSetKozi() {
        System.out.println("setKozi");
        boolean bool = true;
        Card instance = new Card("Eight", new Suit("Hearts"));
        instance.setKozi(bool);
        assertEquals(instance.isKozi(), bool);
    } // testSetKozi

    /**
     * Test of getCardNameGivenRank method, of class Card.
     */
    @Test
    public void testGetCardNameGivenRank() {
        System.out.println("getCardNameGivenRank");
        int rank = 1;
        String expResult = "Seven";
        String result = Card.getCardNameGivenRank(rank);
        assertEquals(expResult, result);
    } // testGetCardNameGivenRank()

    /**
     * Test of getFullCardToString method, of class Card.
     */
    @Test
    public void testGetFullCardToString() {
        System.out.println("getFullCardToString");
        Card instance = new Card("Eight", new Suit("Hearts"));
        String expResult = "Eight_Hearts";
        String result = instance.getFullCardToString();
        assertEquals(expResult, result);
    } // testGetFullCardToString()


    
}
