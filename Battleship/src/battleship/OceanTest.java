package battleship;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OceanTest {

	private Ship ship;
	private Ocean ocean;
	private EmptySea emptySea;
	private Battleship battleship;
	
	@Before
	public void setUp() throws Exception {
		ship = new Ship();
		ocean = new Ocean();
		emptySea = new EmptySea();
		battleship = new Battleship();
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



}
