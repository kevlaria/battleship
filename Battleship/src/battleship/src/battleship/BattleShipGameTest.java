package battleship;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BattleShipGameTest {
	BattleShipGame game;
	Ocean ocean;
	
	@Before
	public void setUp() throws Exception {
		game = new BattleShipGame();
		ocean = new Ocean();
	}

	@Test
	public void testIsValid() {
		 assertTrue(game.isValid("3"));
		 assertTrue(game.isValid("9"));
		 assertTrue(game.isValid("0"));
		 assertFalse(game.isValid("-1"));
		 assertFalse(game.isValid("10"));
		 assertFalse(game.isValid("a"));
		 assertFalse(game.isValid("3.1"));
		 assertFalse(game.isValid("3.0"));
	}

	@Test
	public void testIsInt() {
		 assertTrue(game.isInt("3"));
		 assertFalse(game.isInt("a"));
		 assertFalse(game.isInt("3.0"));
	}
	
	@Test
	public void testinputCoordinate(){
		// Not implemented as requires user input
	}
	
	@Test
	public void testfireShot(){
		boolean[][] shotsFired = ocean.getShotsFiredAccumulated();
		assertFalse(shotsFired[5][4]);
		assertFalse(shotsFired[3][2]);
		game.fireShot(5, 4, ocean);
		boolean[][] shotsFired2 = ocean.getShotsFiredAccumulated();
		assertTrue(shotsFired2[5][4]);
		assertFalse(shotsFired2[3][2]);
		game.fireShot(3, 2, ocean);
		boolean[][] shotsFired3 = ocean.getShotsFiredAccumulated();
		assertTrue(shotsFired3[5][4]);
		assertTrue(shotsFired2[3][2]);
	}

}
