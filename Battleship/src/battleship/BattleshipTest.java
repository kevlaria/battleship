package battleship;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BattleshipTest {

	@Before
	public void setUp() throws Exception {
		Ship ship = new Ship();
		Ocean ocean = new Ocean();
	}

	
	/******
	 * Ocean method testers
	 * ******
	 */
	
	@Test
	public void testisOccupied() {
		assertFalse(ocean.isOccupied(3, 5));
		// Need test cases when battleship is added
	}

	
	/******
	 * Ship method testers
	 * ******
	 */
	
	@Test
	public void testgetShipType(){
		assertEquals(null, ship.getShipType());
		// Need test cases for 
	}

}
