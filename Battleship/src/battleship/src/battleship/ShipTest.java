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
	private Submarine submarine;
	private Destroyer destroyer;
	
	@Before
	public void setUp() throws Exception {
		ship = new Ship();
		ocean = new Ocean();
		emptySea = new EmptySea();
		battleship = new Battleship();
		cruiser = new Cruiser();
		submarine = new Submarine();
		destroyer = new Destroyer();
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
		
		submarine.placeShipAt(9, 9, true, ocean);
		assertFalse(submarine.isSunk());
		
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
		Ship[][] ships = ocean.getShipArray();
		assertEquals(".", ships[0][0].toString()); // should be empty sea
	}
	
	@Test
	public void testShipLocation(){
		ArrayList<int[]> shipLocation = cruiser.shipPotentialLocation(5, 6, false); // should occupy (5,6), (5,7) and (5,8)
		assertEquals(3, shipLocation.size());
		assertEquals(5, shipLocation.get(0)[0]);
		assertEquals(6, shipLocation.get(0)[1]);
		assertEquals(5, shipLocation.get(1)[0]);
		assertEquals(7, shipLocation.get(1)[1]);
		assertEquals(5, shipLocation.get(2)[0]);
		assertEquals(8, shipLocation.get(2)[1]);
		
		ArrayList<int[]> shipLocation2 = battleship.shipPotentialLocation(0, 0, true); // should occupy (0,0), (1,0), (2,0) and (3,0)
		assertEquals(4, shipLocation2.size());
		assertEquals(0, shipLocation2.get(0)[0]);
		assertEquals(0, shipLocation2.get(0)[1]);
		assertEquals(1, shipLocation2.get(1)[0]);
		assertEquals(0, shipLocation2.get(1)[1]);
		assertEquals(2, shipLocation2.get(2)[0]);
		assertEquals(0, shipLocation2.get(2)[1]);
		assertEquals(3, shipLocation2.get(3)[0]);
		assertEquals(0, shipLocation2.get(3)[1]);	
	}

	@Test
	public void testtouchAnotherShipCornerOfTheSea(){
		battleship.placeShipAt(1, 1, true, ocean); // should occupy (1,1), (2,1), (3,1) and (4,1)
		battleship.placeShipAt(8, 5, false, ocean); // should occupy (8,5), (8,6), (8,7) and (8,8)
		assertTrue(submarine.touchAnotherShip(0, 0, true, ocean));
		assertTrue(submarine.touchAnotherShip(0, 1, false, ocean));
		assertTrue(submarine.touchAnotherShip(9, 9, false, ocean));
		assertTrue(submarine.touchAnotherShip(9, 8, false, ocean));
	}
	
	@Test
	public void testtouchAnotherShipMiddleOfTheSea(){
		battleship.placeShipAt(5, 5, true, ocean); // should occupy (5,5), (6,5), (7,5) and (8,5)
		assertTrue(submarine.touchAnotherShip(5, 6, true, ocean));
		assertTrue(submarine.touchAnotherShip(6, 6, true, ocean));
		assertTrue(submarine.touchAnotherShip(8, 6, true, ocean));
		assertTrue(submarine.touchAnotherShip(9, 5, true, ocean));
		assertTrue(submarine.touchAnotherShip(4, 5, true, ocean));
		assertTrue(submarine.touchAnotherShip(4, 4, true, ocean));
		assertTrue(submarine.touchAnotherShip(4, 6, true, ocean));
		assertTrue(submarine.touchAnotherShip(9, 4, true, ocean));
		assertTrue(submarine.touchAnotherShip(9, 6, true, ocean));
	}
	
	@Test
	public void testoverlaps(){
		battleship.placeShipAt(5, 5, true, ocean); // should occupy (5,5), (6,5), (7,5) and (8,5)
		assertTrue(cruiser.overlaps(5, 5, true, ocean));
		assertTrue(cruiser.overlaps(8, 5, true, ocean));
		assertTrue(cruiser.overlaps(6, 4, false, ocean));
		assertFalse(cruiser.overlaps(6, 4, true, ocean));
		assertFalse(cruiser.overlaps(6, 6, false, ocean));
		assertTrue(submarine.overlaps(6, 5, false, ocean));
	}
	
	@Test
	public void testtouchAnotherShipVertically(){
		battleship.placeShipAt(5, 5, true, ocean); // should occupy (5,5), (6,5), (7,5) and (8,5)		
		assertTrue(cruiser.touchAnotherShipVertically(4, 5, false, ocean));
		assertFalse(cruiser.touchAnotherShipVertically(4, 6, false, ocean));
		assertTrue(cruiser.touchAnotherShipVertically(9, 3, false, ocean));
		assertFalse(cruiser.touchAnotherShipVertically(9, 2, false, ocean));
		assertTrue(cruiser.touchAnotherShipVertically(2, 5, true, ocean));
		assertFalse(cruiser.touchAnotherShipVertically(1, 5, true, ocean));
		assertTrue(submarine.touchAnotherShipVertically(9, 5, true, ocean));
		assertFalse(submarine.touchAnotherShipVertically(8, 6, true, ocean));
		
		battleship.placeShipAt(1, 1, true, ocean); // should occupy (1,1), (2,1), (3,1) and (4,1)		
		assertTrue(cruiser.touchAnotherShipVertically(0, 0, false, ocean));
		assertFalse(submarine.touchAnotherShipVertically(0, 0, false, ocean));

		battleship.placeShipAt(5, 8, true, ocean); // should occupy (5,8), (6,8), (7,8) and (8,8)			
		assertTrue(submarine.touchAnotherShipVertically(9, 8, true, ocean));
		assertFalse(submarine.touchAnotherShipVertically(9, 9, true, ocean));

	}
	
	@Test
	public void testtouchAnotherShipHorizontally(){
		battleship.placeShipAt(5, 5, false, ocean); // should occupy (5,5), (5,6), (5,7) and (5,8)		
		assertTrue(cruiser.touchAnotherShipHorizontally(5, 4, true, ocean));
		assertTrue(cruiser.touchAnotherShipHorizontally(3, 4, true, ocean));
		assertFalse(cruiser.touchAnotherShipHorizontally(2, 4, true, ocean));
		assertTrue(cruiser.touchAnotherShipHorizontally(4, 9, true, ocean));
		assertTrue(submarine.touchAnotherShipHorizontally(5,9, true, ocean));

		battleship.placeShipAt(1, 1, true, ocean); // should occupy (1,1), (2,1), (3,1) and (4,1)			
		assertTrue(submarine.touchAnotherShipHorizontally(1, 0, true, ocean));
		assertFalse(submarine.touchAnotherShipHorizontally(0, 0, true, ocean));

		destroyer.placeShipAt(7, 8, true, ocean); // should occupy (7,8) and (8,8)			
		assertTrue(submarine.touchAnotherShipHorizontally(8, 9, true, ocean));
		assertFalse(submarine.touchAnotherShipHorizontally(9, 9, true, ocean));

		
	}
	
	@Test
	public void testtouchAnotherShipDiagonally(){
		battleship.placeShipAt(5, 5, false, ocean);   // should occupy (5,5), (5,6), (5,7) and (5,8)
		assertTrue(cruiser.touchAnotherShipDiagonally(4, 6, true, ocean));
		
		submarine.placeShipAt(1, 1, true, ocean);
		assertTrue(submarine.touchAnotherShipDiagonally(0, 0, true, ocean));
		assertTrue(submarine.touchAnotherShipDiagonally(2, 0, true, ocean));
		assertTrue(submarine.touchAnotherShipDiagonally(0, 2, true, ocean));
		assertTrue(submarine.touchAnotherShipDiagonally(2, 2, true, ocean));
		assertFalse(submarine.touchAnotherShipDiagonally(1, 1, true, ocean));
		assertFalse(submarine.touchAnotherShipDiagonally(1, 0, true, ocean));
		assertFalse(submarine.touchAnotherShipDiagonally(0, 1, true, ocean));
		
		submarine.placeShipAt(8, 0, true, ocean);
		assertFalse(submarine.touchAnotherShipDiagonally(9, 0, true, ocean));
		assertFalse(submarine.touchAnotherShipDiagonally(8, 1, true, ocean));
		assertTrue(submarine.touchAnotherShipDiagonally(9, 1, true, ocean));
		assertTrue(submarine.touchAnotherShipDiagonally(7, 1, true, ocean));
		
		submarine.placeShipAt(8, 8, true, ocean);
		assertTrue(submarine.touchAnotherShipDiagonally(9, 9, true, ocean));
		assertTrue(submarine.touchAnotherShipDiagonally(7, 9, true, ocean));
		assertTrue(submarine.touchAnotherShipDiagonally(7, 7, true, ocean));
		assertTrue(submarine.touchAnotherShipDiagonally(9, 7, true, ocean));
		
		
	}
	
	@Test
	public void testtouchAnotherShipDiagonallyX0Helper(){
		cruiser.placeShipAt(1, 1, true, ocean);	// should occupy (1,1), (2, 1) and (3,1)
		destroyer.placeShipAt(1, 8, true, ocean);  // should occupy (1,8) and (2,8)
		assertTrue(submarine.touchAnotherShipDiagonallyX0Helper(0, 9, ocean));
		assertTrue(submarine.touchAnotherShipDiagonallyX0Helper(0, 7, ocean));
		assertFalse(submarine.touchAnotherShipDiagonallyX0Helper(0, 8, ocean));
		assertTrue(submarine.touchAnotherShipDiagonallyX0Helper(0, 0, ocean));
		assertTrue(submarine.touchAnotherShipDiagonallyX0Helper(0, 2, ocean));
		assertFalse(submarine.touchAnotherShipDiagonallyX0Helper(0, 4, ocean));		
		assertFalse(submarine.touchAnotherShipDiagonallyX0Helper(1, 1, ocean));		
	}
	
	@Test
	public void testtouchAnotherShipDiagonallyX9Helper(){
		cruiser.placeShipAt(6, 1, true, ocean);	// should occupy (6,1), (7, 1) and (8,1)
		destroyer.placeShipAt(7, 8, true, ocean);  // should occupy (7,8) and (8,8)
		assertTrue(submarine.touchAnotherShipDiagonallyX9Helper(9, 9, ocean));
		assertTrue(submarine.touchAnotherShipDiagonallyX9Helper(9, 7, ocean));
		assertFalse(submarine.touchAnotherShipDiagonallyX9Helper(9, 8, ocean));
		assertTrue(submarine.touchAnotherShipDiagonallyX9Helper(9, 0, ocean));
		assertTrue(submarine.touchAnotherShipDiagonallyX9Helper(9, 2, ocean));
		assertFalse(submarine.touchAnotherShipDiagonallyX9Helper(9, 4, ocean));		
		assertFalse(submarine.touchAnotherShipDiagonallyX9Helper(7, 8, ocean));		
	}
	
	@Test
	public void testtouchAnotherShipDiagonallyXOtherHelpter(){
	cruiser.placeShipAt(5, 2, true, ocean);	// should occupy (5,2), (6, 2) and (7,2)
	assertTrue(submarine.touchAnotherShipDiagonallyXOtherHelper(5, 1, ocean));
	assertTrue(submarine.touchAnotherShipDiagonallyXOtherHelper(5, 3, ocean));
	assertTrue(submarine.touchAnotherShipDiagonallyXOtherHelper(8, 1, ocean));
	assertTrue(submarine.touchAnotherShipDiagonallyXOtherHelper(8, 3, ocean));
	assertFalse(submarine.touchAnotherShipDiagonallyXOtherHelper(4, 2, ocean));
	assertFalse(submarine.touchAnotherShipDiagonallyXOtherHelper(6, 2, ocean));
	}
	
	@Test
	public void teststickOut(){
		assertFalse(battleship.stickOut(0, 0, true, ocean));
		assertFalse(battleship.stickOut(0, 0, false, ocean));
		assertTrue(battleship.stickOut(7, 0, true, ocean));
		assertTrue(battleship.stickOut(0, 7, false, ocean));
		
		assertFalse(cruiser.stickOut(0, 7, false, ocean));
		assertTrue(cruiser.stickOut(0, 8, false, ocean));
	
		assertFalse(cruiser.stickOut(7, 0, true, ocean));
		assertTrue(cruiser.stickOut(8, 0, true, ocean));
		
	}
	

	
	
}
