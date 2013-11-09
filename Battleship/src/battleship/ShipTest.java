package battleship;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ShipTest {

	private Ship ship;
	private Ocean ocean;
	private EmptySea emptySea;
	private Battleship battleship;
	private Cruiser cruiser;
	
	@Before
	public void setUp() throws Exception {
		ship = new Ship();
		ocean = new Ocean();
		emptySea = new EmptySea();
		battleship = new Battleship();
		cruiser = new Cruiser();
	}

	/******
	 * Ship method testers
	 * ******
	 */
	
	@Test
	public void testgetShipType(){
		assertEquals("Empty Sea", emptySea.getShipType());
		assertEquals("Battleship", battleship.getShipType());
		assertEquals("Cruiser", cruiser.getShipType());
		// Need test cases for other types of ships
	}
	
	@Test
	public void testplaceShipAt(){
		battleship.placeShipAt(0, 0, true, ocean); // should occupy (0,0), (1,0), (2,0) and (3,0)
		assertTrue(ocean.isOccupied(0, 0));
		assertTrue(ocean.isOccupied(1, 0));
		assertTrue(ocean.isOccupied(2, 0));
		assertTrue(ocean.isOccupied(3, 0));
		assertFalse(ocean.isOccupied(4, 0));
		assertFalse(ocean.isOccupied(0, 1));
		cruiser.placeShipAt(5, 6, false, ocean); // should occupy (5,6), (5,7) and (5,8)
		assertTrue(ocean.isOccupied(5, 6));
		assertTrue(ocean.isOccupied(5, 8));
		assertFalse(ocean.isOccupied(5, 5));
		assertFalse(ocean.isOccupied(4, 6));
		assertFalse(ocean.isOccupied(6, 8));
	}
	
	@Test
	public void testshipOccupiesSpace(){
		battleship.placeShipAt(0, 0, true, ocean); // should occupy (0,0), (1,0), (2,0) and (3,0)
		assertTrue(battleship.shipOccupiesSpace(0, 0));
		assertTrue(battleship.shipOccupiesSpace(2, 0));
		assertFalse(battleship.shipOccupiesSpace(0, 1));
		cruiser.placeShipAt(5, 6, false, ocean); // should occupy (5,6), (5,7) and (5,8)
		assertTrue(cruiser.shipOccupiesSpace(5, 7));
		assertFalse(cruiser.shipOccupiesSpace(5, 9));
	}
	
	@Test
	public void testshipPart(){
		cruiser.placeShipAt(5, 6, false, ocean); // should occupy (5,6), (5,7) and (5,8)
		assertEquals(0, cruiser.shipPart(5, 6));
		assertEquals(1, cruiser.shipPart(5, 7));
		assertEquals(2, cruiser.shipPart(5, 8));
		battleship.placeShipAt(0, 0, true, ocean); // should occupy (0,0), (1,0), (2,0) and (3,0)
		assertEquals(0, battleship.shipPart(0, 0));
		assertEquals(1, battleship.shipPart(1, 0));
		assertEquals(2, battleship.shipPart(2, 0));
		assertEquals(3, battleship.shipPart(3, 0));
	}
	
	@Test
	public void testshootAt(){
		cruiser.placeShipAt(5, 6, false, ocean); // should occupy (5,6), (5,7) and (5,8)
		assertFalse(cruiser.shootAt(4, 6));
		assertTrue(cruiser.shootAt(5, 8));
		assertTrue(cruiser.hit[2]);
		assertFalse(cruiser.hit[1]);
	}
	
	@Test
	public void testisSunk(){
		cruiser.placeShipAt(5, 6, false, ocean); // should occupy (5,6), (5,7) and (5,8)
		assertFalse(cruiser.isSunk());
		cruiser.shootAt(5, 6);
		assertFalse(cruiser.isSunk());
		cruiser.shootAt(5, 7);
		cruiser.shootAt(5, 8);
		assertTrue(cruiser.isSunk());	
	}
}
