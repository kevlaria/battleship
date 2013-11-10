package battleship;

import static org.junit.Assert.*;

import java.util.ArrayList;

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
	
	@Test(expected=RuntimeException.class)
	public void testplaceShipAtOccupiedLocation(){
		cruiser.placeShipAt(5, 6, false, ocean); // should occupy (5,6), (5,7) and (5,8)
		battleship.placeShipAt(4, 7, true, ocean); // should occupy (4, 7), (5, 7), (6, 7) and (7,7)
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
		
		emptySea.placeShipAt(0, 0, true, ocean);
		assertFalse(emptySea.shootAt(0, 0));
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

		emptySea.placeShipAt(0, 0, true, ocean);
		emptySea.shootAt(0, 0);
		assertFalse(emptySea.isSunk());
	}
	
	@Test
	public void testtoString(){
		cruiser.placeShipAt(5, 6, false, ocean); // should occupy (5,6), (5,7) and (5,8)
		assertEquals("S", cruiser.toString());
		cruiser.shootAt(5, 6);
		assertEquals("S", cruiser.toString());
		cruiser.shootAt(5, 7);
		cruiser.shootAt(5, 8);
		assertEquals("x", cruiser.toString());
		assertEquals("-", ocean.ships[0][0].toString()); // should be empty sea
	}
	
	@Test
	public void testShipLocation(){
		cruiser.placeShipAt(5, 6, false, ocean); // should occupy (5,6), (5,7) and (5,8)
		ArrayList<int[]> shipLocation = cruiser.shipLocation();
		int[] location1 = new int[2];
		location1[0] = 5;
		location1[1] = 6;
		assertEquals(3, shipLocation.size());
		assertEquals(5, shipLocation.get(0)[0]);
		assertEquals(6, shipLocation.get(0)[1]);
		assertEquals(5, shipLocation.get(1)[0]);
		assertEquals(7, shipLocation.get(1)[1]);
		assertEquals(5, shipLocation.get(2)[0]);
		assertEquals(8, shipLocation.get(2)[1]);
	}

	
	
}
