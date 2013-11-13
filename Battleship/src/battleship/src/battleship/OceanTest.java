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

	
//	@Test
//	public void testshootAtOcean(){
//		battleship.placeShipAt(1, 1, true, ocean); // should occupy (1,1), (2,1), (3,1) and (4,1)			
//		assertFalse(ocean.shootAt(0,0));	
//	}
	
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
				switch (shipType){
					case "Battleship":
						numberOfBattleships ++;
					case "Cruiser":
						numberOfCruisers ++;
					case "Destroyer":
						numberOfDestroyers ++;
					case "Submarine":
						numberOfSubmarines ++;
					case "EmptySea":
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



}
