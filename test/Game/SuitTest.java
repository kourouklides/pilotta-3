/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

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
public class SuitTest {
    
    /**
     * Test of getSuitToString method, of class Suit.
     */
    @Test
    public void testGetSuitToString_0args() {
        System.out.println("getSuitToString");
        Suit instance = new Suit("Hearts");
        String expResult = "Hearts";
        String result = instance.getSuitToString();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSuitToInt method, of class Suit.
     */
    @Test
    public void testGetSuitToInt() {
        System.out.println("getSuitToInt");
        String suitStr = "Hearts";
        Suit instance = new Suit("Hearts");
        int expResult = 1;
        int result = instance.getSuitToInt(suitStr);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSuitToString method, of class Suit.
     */
    @Test
    public void testGetSuitToString_int() {
        System.out.println("getSuitToString");
        int suitInt = 1;
        String expResult = "Hearts";
        String result = Suit.getSuitToString(suitInt);
        assertEquals(expResult, result);
    }
    
}
