package battleship;

public class Ocean {

	private Ship[][] ships = new Ship[10][10];
	private int shotsFired;    //-- The total number of shots fired by the user.
	private int hitCount;    // -- The number of times a shot hit a ship. If the user shoots the same part of a ship more than once, every hit is counted, even though the additional "hits" don't do the user any good.
	private int shipsSunk;
	
	public Ocean() {
		for (int i = 0; i < 10; i++){
			for (int j = 0; j < 10; j++){
				
				ships[i][j] = new EmptySea();
				
			}
		}
	}
	
	/**
	 * Returns true if the given location contains a ship, false if it doesn't.
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean isOccupied(int row, int column){
		Ship location = this.ships[row][column];
		if (location.getShipType().equals(null)){
			return false;
		}
		return true;
		
	}
	
}
