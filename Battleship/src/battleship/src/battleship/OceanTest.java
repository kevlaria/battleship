// Priyank Chaudhary, Kevin Lee

package battleship;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OceanTest {

	private Ship ship;
	private Ocean ocean;
	private EmptySea emptySea;
	private Battleship battleship;
	private Submarine submarine;
	private Destroyer destroyer;
	private Cruiser cruiser;
	
	@Before
	public void setUp() throws Exception {
		ship = new Ship();
		ocean = new Ocean();
		emptySea = new EmptySea();
		battleship = new Battleship();
		submarine = new Submarine();
		destroyer = new Destroyer();
		cruiser = new Cruiser();
	}

	
	/******
	 * Ocean method testers
	 * ******
	 */
	
	@Test
	public void testisOccupied() {
		assertFalse(ocean.isOccupied(2, 1));
		battleship.placeShipAt(1, 1, true, ocean); // should occupy (1,1), (2,1), (3,1) and (4,1)
		assertTrue(ocean.isOccupied(2,1));
	}

	
	@Test
	public void testupdateShotsFired(){
		assertEquals(0, ocean.getShotsFired());
		assertFalse(ocean.getShotsFiredAccumulated()[5][0]);
		ocean.updateShotsFired(5,0);
		assertEquals(1, ocean.getShotsFired());
		assertTrue(ocean.getShotsFiredAccumulated()[5][0]);
		ocean.updateShotsFired(5,0);
		assertEquals(2, ocean.getShotsFired());	
		assertTrue(ocean.getShotsFiredAccumulated()[5][0]);
	}
	
	
	@Test
	public void testshootAtOcean(){
		battleship.placeShipAt(1, 1, true, ocean); // should occupy (1,1), (2,1), (3,1) and (4,1)
		assertFalse(ocean.shootAt(0,0));
		assertTrue(ocean.shootAt(1, 1));
		assertTrue(ocean.shootAt(2, 1));
		assertTrue(battleship.hit[0]);
		assertTrue(battleship.hit[1]);
		assertFalse(battleship.hit[2]);
		assertEquals(3, ocean.getShotsFired());
		assertEquals(2, ocean.getHitCount());
		assertTrue(ocean.shootAt(2, 1));
		assertEquals(4, ocean.getShotsFired()); 
		assertEquals(3, ocean.getHitCount()); // ensure that hit count increases even if you fire at a previously hit location
		boolean[][] shotsFiredAccumulated = ocean.getShotsFiredAccumulated();
		assertTrue(shotsFiredAccumulated[0][0]);
		assertTrue(shotsFiredAccumulated[1][1]);
		assertFalse(shotsFiredAccumulated[3][1]);
		
		assertEquals(0, ocean.getShipsSunk());
		submarine.placeShipAt(9,9, true, ocean);
		assertTrue(ocean.shootAt(9,  9));
		assertEquals(4, ocean.getHitCount());
		assertEquals(1, ocean.getShipsSunk());
		ocean.shootAt(9, 9);
		assertEquals(4, ocean.getHitCount()); // ensure that hit count doesn't increase when you fire at a sunk ship

		assertTrue(ocean.shootAt(3,  1));
		assertTrue(ocean.shootAt(4,  1));
		assertEquals(2, ocean.getShipsSunk());
	}
	
	@Test
	public void testgetShipAtLocation(){
		battleship.placeShipAt(1, 1, true, ocean); // should occupy (1,1), (2,1), (3,1) and (4,1)
		assertEquals("Battleship", ocean.getShipAtLocation(2, 1));
		assertEquals("Battleship", ocean.getShipAtLocation(3, 1));
		assertEquals("Empty Sea", ocean.getShipAtLocation(5, 1));
	}
	
	
	@Test
	public void testisGameOver(){
		Submarine sub1 = new Submarine();
		Submarine sub2 = new Submarine();
		Submarine sub3 = new Submarine();
		Submarine sub4 = new Submarine();
		Submarine sub5 = new Submarine();
		Submarine sub6 = new Submarine();
		Submarine sub7 = new Submarine();
		Submarine sub8 = new Submarine();
		Submarine sub9 = new Submarine();
		Submarine sub10 = new Submarine();
		sub1.placeShipAt(9,9, true, ocean);
		sub2.placeShipAt(7,9, true, ocean);
		sub3.placeShipAt(5,9, true, ocean);
		sub4.placeShipAt(3,9, true, ocean);
		sub5.placeShipAt(1,9, true, ocean);
		sub6.placeShipAt(9,7, true, ocean);
		sub7.placeShipAt(9,5, true, ocean);
		sub8.placeShipAt(9,3, true, ocean);
		sub9.placeShipAt(9,1, true, ocean);
		sub10.placeShipAt(1,1, true, ocean);
		ocean.shootAt(9, 9);
		ocean.shootAt(7, 9);
		ocean.shootAt(5, 9);
		ocean.shootAt(3, 9);
		ocean.shootAt(1, 9);
		ocean.shootAt(9, 7);
		ocean.shootAt(9, 5);
		ocean.shootAt(9, 3);
		ocean.shootAt(9, 1);
		ocean.shootAt(1, 1);
		assertTrue(ocean.isGameOver());
		
	}
	
	@Test
	public void testsetupFleet(){
		Ship[] fleet = ocean.setupFleet();
		assertEquals("Battleship", fleet[0].getShipType());
		assertEquals("Submarine", fleet[9].getShipType());
		assertEquals("Destroyer", fleet[3].getShipType());
		assertEquals("Cruiser", fleet[2].getShipType());
	}
	
	@Test
	public void testplaceAllShipsRandomly2(){
		ocean.placeAllShipsRandomly();
		int numberOfEmptySea = 0;
		int numberOfSubmarines = 0;
		int numberOfDestroyers = 0;
		int numberOfCruisers = 0;
		int numberOfBattleships = 0;
		for (int i = 0; i < 10; i++){
			for (int j = 0; j < 10; j++){
				Ship[][] ships = ocean.getShipArray();
				String shipType = ships[i][j].getShipType();
				if (shipType.equals("Battleship")){
					numberOfBattleships ++;					
				} else if (shipType.equals("Cruiser")){
					numberOfCruisers ++;					
				} else if (shipType.equals("Destroyer")){
					numberOfDestroyers ++;
				} else if (shipType.equals("Submarine")){
					numberOfSubmarines ++;					
				} else {
					numberOfEmptySea ++;					
				}
			}
		}
		assertEquals(4, numberOfBattleships);
		assertEquals(6, numberOfCruisers);
		assertEquals(6, numberOfDestroyers);
		assertEquals(4, numberOfSubmarines);
		assertEquals(80, numberOfEmptySea);
		
		Ocean ocean2 = new Ocean();
		ocean2.placeAllShipsRandomly();
		assertNotSame(ocean, ocean2);
	}

	@Test
	
	public void testprint(){
		// Not tested as only prints out to screen - does not return anything
	}


}
