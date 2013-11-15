// Priyank Chaudhary, Kevin Lee

package battleship;

import java.util.Random;

public class Ocean {

	private Ship[][] ships = new Ship[10][10];
	private int shotsFired;    //-- The total number of shots fired by the user.
	private int hitCount;    // -- The number of times a shot hit a ship. If the user shoots the same part of a ship more than once, every hit is counted, even though the additional "hits" don't do the user any good.
	private int shipsSunk;
	private boolean[][] shotsFiredAccumulated = new boolean[10][10];
	
	public Ocean() {
		for (int i = 0; i < 10; i++){
			for (int j = 0; j < 10; j++){		
				ships[i][j] = new EmptySea();
				shotsFiredAccumulated[i][j] = false;
			}
		}
	}
	
	/**
	 * Places all 10 ships randomly in the ocean
	 */
    public void placeAllShipsRandomly(){
    	Ship[] fleet = setupFleet();
    	for (Ship ship : fleet){
    		boolean successful = false;
    		while(!successful){
                Random random = new Random();
                int xCoordinate = random.nextInt(10);
                int yCoordinate = random.nextInt(10);
                boolean horizontal = random.nextBoolean();
                if(ship.okToPlaceShipAt(xCoordinate, yCoordinate, horizontal, this)){
                	ship.placeShipAt(xCoordinate, yCoordinate, horizontal, this);
                	successful = true;
                }    			
    		}
    	}  	
    }
	
    
    /**
     * Creates a fleet of 4 Subs, 3 Destroyers, 2 Cruisers and 1 Battleship
     * @return An array of Ships with the above ships
     */
    public Ship[] setupFleet(){
    	Ship[] fleet = new Ship[10];
    	
    	Battleship battleship = new Battleship();
    	fleet[0] = battleship;
    	Cruiser cruiser1 = new Cruiser();
    	fleet[1] = cruiser1;
    	Cruiser cruiser2 = new Cruiser();
    	fleet[2] = cruiser2;
    	Destroyer destroyer1 = new Destroyer();
    	fleet[3] = destroyer1;
    	Destroyer destroyer2 = new Destroyer();
    	fleet[4] = destroyer2;
    	Destroyer destroyer3 = new Destroyer();
    	fleet[5] = destroyer3;
    	Submarine submarine1 = new Submarine();
    	fleet[6] = submarine1;
    	Submarine submarine2 = new Submarine();
    	fleet[7] = submarine2;
    	Submarine submarine3 = new Submarine();
    	fleet[8] = submarine3;
    	Submarine submarine4 = new Submarine();
    	fleet[9] = submarine4;	
    	return fleet;   	
    }
    
    
	/**
	 * Returns true if the given location contains a ship, false if it doesn't.
	 * @param row
	 * @param column
	 * @return true if the govern location contains a ship
	 */
	public boolean isOccupied(int row, int column){
		Ship location = this.ships[row][column];
		String shipType = location.getShipType();
		if (shipType.equals("Empty Sea")){
			return false;
		}
		return true;	
	}
	
	/**
	 * Returns ship array
	 * @return an array of Ships from the ocean
	 */
	public Ship[][] getShipArray(){
		return this.ships;
	}
	
	
	
	
	/**
	 * Returns true if the given location contains 
	 * a "real" ship, still afloat, (not an EmptySea), false if it does not.
	 * - updates the number of shots that have been fired
	 * - Updates shotsFiredAccumulated (array of all shots that have been fired in the game)
	 * - updates the number of hits (regardless of whether the same square has been hit > 1 times)
	 * - Updates the ship's hit boolean array if the hit is successful
	 * - updates number of ships sunk if ship is sunk
	 * @param row
	 * @param column
	 * @return true if ship has been hit
	 */
	public boolean shootAt(int row, int column){
		this.updateShotsFired(row, column);
		if(isOccupied(row, column)){
			Ship ship = this.getShipArray()[row][column];
			if (ship.shootAt(row, column)){
				this.hitCount ++;
				if(ship.isSunk()){
					this.shipsSunk ++;
				}
				return true;
			}
			else {
				return false; // Not occupied therefore EmptySea
			}
		}
		return false; // Not occupied therefore EmptySea
	}
	
	
	/**
	 * - updates the number of shots that have been fired
	 * - Updates shotsFiredAccumulated (array of all shots that have been fired in the game)
	 * @param row
	 * @param column
	 */
	public void updateShotsFired(int row, int column){
		this.shotsFired ++;
		shotsFiredAccumulated[row][column] = true;			

	}
	
	
	/**
	 * Given an row and column coordinate, returns the ship type of that location in the ocean (as a string)
	 * @param row
	 * @param column
	 * @param ocean
	 * @return the ship type at xCoordinate, yCoordinate
	 */
	public String getShipAtLocation(int row, int column){
		Ship[][] ships = this.getShipArray();
		String shipType = ships[row][column].getShipType();
		return shipType;
	}
	
	
	/**
	 * Prints the ocean out. 
	 * '.' marks an unshot-location
	 * '-' marks shot location that's empty sea
	 * 'S' marks a shot location where a ship is located
	 * 'x' marks a sunk ship
	 */
	public void print(){
		System.out.print("   ");
		for (int i = 0; i < 10; i++){ 			// printing header
			System.out.print("  " + i + "  ");
		}
		
		for (int i = 0; i < 10; i ++){ // each row rows
			System.out.print("\n");
			System.out.print("\n" + i + "  ");
			
			for (int j = 0; j < 10; j ++){
				if (this.shotsFiredAccumulated[i][j]){ // shot has been fired at this location
					System.out.print("  " + this.ships[i][j] + "  ");							
				} else {
					System.out.print("  .  ");
				}
			}		
		}
	}
	
	
	/**
	 * Determines if game is over or not. 
	 * @return True if game is over (10 ships are sunk)
	 */
	public boolean isGameOver(){
		if (this.shipsSunk >= 10){
			return true;
		}
		return false;
	}
	
	/************
	 * GETTERS AND SETTERS
	 * *************
	 */
	
	public int getShotsFired(){
		return this.shotsFired;
	}
	
	public int getHitCount(){
		return this.hitCount;
	}
	
	public int getShipsSunk(){
		return this.shipsSunk;
	}

	public boolean[][] getShotsFiredAccumulated(){
		return this.shotsFiredAccumulated;
	}
	
}
