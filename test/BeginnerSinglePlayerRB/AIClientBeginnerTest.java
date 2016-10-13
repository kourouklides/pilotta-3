/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeginnerSinglePlayerRB;

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
public class AIClientBeginnerTest {
    
    public AIClientBeginnerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getName method, of class AIClientBeginner.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        AIClientBeginner instance = new AIClientBeginner(0);
        String expResult = "Bot 0";
        String result = instance.getName();
        assertEquals(expResult, result);
    } // testGetName()

    /**
     * Test of getID method, of class AIClientBeginner.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        AIClientBeginner instance = new AIClientBeginner(0);
        int expResult = 0;
        int result = instance.getID();
        assertEquals(expResult, result);
    } // testGetID()

    /**
     * Test of setCards method, of class AIClientBeginner.
     */
    @Test
    public void testSetCards() {
        System.out.println("setCards");
        Card[] c = new Card[8];
        c[0] = new Card("Eight", new Suit("Diamonds"));
        c[1] = new Card("Seven", new Suit("Diamonds"));
        c[2] = new Card("Nine", new Suit("Diamonds"));
        c[3] = new Card("Ten", new Suit("Diamonds"));
        c[4] = new Card("Jack", new Suit("Diamonds"));
        c[5] = new Card("Queen", new Suit("Diamonds"));
        c[6] = new Card("King", new Suit("Diamonds"));
        c[7] = new Card("Ace", new Suit("Diamonds"));
        AIClientBeginner instance = new AIClientBeginner(0);
        instance.setCards(c);
        int expCardSize = 8;
        assertEquals(c.length, expCardSize);
    } // testSetCards()

    /**
     * Test of getCards method, of class AIClientBeginner.
     */
    @Test
    public void testGetCards() {
        System.out.println("getCards");
        AIClientBeginner instance = new AIClientBeginner(0);

        Card[] c = new Card[8];
        c[0] = new Card("Eight", new Suit("Diamonds"));
        c[1] = new Card("Seven", new Suit("Diamonds"));
        c[2] = new Card("Nine", new Suit("Diamonds"));
        c[3] = new Card("Ten", new Suit("Diamonds"));
        c[4] = new Card("Jack", new Suit("Diamonds"));
        c[5] = new Card("Queen", new Suit("Diamonds"));
        c[6] = new Card("King", new Suit("Diamonds"));
        c[7] = new Card("Ace", new Suit("Diamonds"));
        instance.setCards(c);
        Card[] expResult = c;
        Card[] result = instance.getCards();
        assertArrayEquals(expResult, result);
    } // testGetCards()

    /**
     * Test of sortCards method, of class AIClientBeginner.
     */
    @Test
    public void testSortCards() {
        System.out.println("sortCards");
        AIClientBeginner instance = new AIClientBeginner(0);
        instance.sortCards();
    } // testSortCards()


    /**
     * Test of play method, of class AIClientBeginner.
     */
    @Test
    public void testPlay() {
        System.out.println("play");
        ArrayList<String> playedCards = new ArrayList<String>();
        String kozi = "Hearts";
        String teamOfRound = "Team A";
        AIClientBeginner instance = new AIClientBeginner(0);
        String result = instance.play(playedCards, kozi, teamOfRound);
        assertNotNull(result);
    } // testPlay()

    /**
     * Test of bid method, of class AIClientBeginner.
     */
    @Test
    public void testBid() {
        System.out.println("bid");
        int biddingRoundID = 1;
        int currentBid = 8;
        String currentSuit = "Hearts";
        ArrayList<String> biddingHistory = new ArrayList<String>();
        biddingHistory.add("0_8_Hearts");
        AIClientBeginner instance = new AIClientBeginner(0);
        String result = instance.bid(biddingRoundID, currentBid, currentSuit, biddingHistory);
        assertNotNull(result);
    } // testBid()



    /**
     * Test of setTeam method, of class AIClientBeginner.
     */
    @Test
    public void testSetTeam_AITeamBeginner2() {
        System.out.println("setTeam");
        AIClientBeginner instance = new AIClientBeginner(0);
        AIClientBeginner instance2 = new AIClientBeginner(1);
        AITeamBeginner2 aiTeam = new AITeamBeginner2("Team A", instance, instance2);

        instance.setTeam(aiTeam);
        instance2.setTeam(aiTeam);

        assertEquals("Team A", aiTeam.getName());
        assertEquals(instance, aiTeam.getFirstPlayer());
        assertEquals(instance2, aiTeam.getSecondPlayer());
    }
    
}
