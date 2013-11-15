// Priyank Chaudhary, Kevin Lee

package battleship;

import java.util.ArrayList;
import java.util.Arrays;

public class Ship {
	private int bowRow;
	private int bowColumn;
	protected int length;
	private boolean horizontal;
	protected boolean [] hit = new boolean[4];
	
	
	/**
	 * Returns true if it is okay to put a ship of this length with its bow in this location, 
	 * with the given orientation, and returns false otherwise. 
	 * - The ship must not overlap another ship, or touch another ship (vertically, horizontally, or diagonally), 
	 * and it must not "stick out" beyond the array.
	 * @param row
	 * @param column
	 * @param horizontal
	 * @param ocean
	 * @return true if you can place ship, false if not
	 */
	public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		if (this.stickOut(row, column, horizontal, ocean) || this.overlaps(row, column, horizontal, ocean) || this.touchAnotherShip(row, column, horizontal, ocean)){
					return false;
			}	
		return true;
	}	
	
	/**
	 * "Puts" the ship in the ocean. 
	 * This involves giving values to the bowRow, bowColumn, and horizontal instance variables in the ship, and 
	 * it also involves putting a reference to the ship in each of 1 or more locations (up to 4) 
	 * in the ships array in the Ocean object. 
	 * @param row
	 * @param column
	 * @param horizontal
	 * @param ocean
	 */
	public void placeShipAt(int row, int column, boolean horizontal, Ocean ocean){
		if(this.okToPlaceShipAt(row, column, horizontal, ocean)){	
			this.bowColumn = column;
			this.bowRow = row;
			this.horizontal = horizontal;
			Ship[][] ships = ocean.getShipArray();
			
			if (this.horizontal){
				for (int i = 0; i < this.length; i++){
					ships[row + i][column] = this;					
				} 
			} else {
				for (int i = 0; i < this.length; i++){
					ships[row][column + i] = this;
					}			
				}
			} else {
				throw new RuntimeException("Location (" + row + ", " + column + ") is occupied!");
			}
	}
	
	
	/**
	 * If a part of the ship occupies the given row and column, 
	 * and the ship hasn't been sunk, mark that part of the ship as "hit"
	 * @param row
	 * @param column
	 * @return true if the shot hit a ship that's not sunk, false otherwise
	 */
	public boolean shootAt(int row, int column){
		if (this.isSunk()){
			return false;
		} else {
			if (!this.shipOccupiesSpace(row, column)){
				return false;
			} else {
				int shipPart = this.shipPart(row, column);
				hit[shipPart] = true;
				return true;
			}
		}
	}
	
	
	/**
	 * Determines if the space around the potential location of a ship touches another ship or not
	 * @param row
	 * @param column
	 * @param horizontal
	 * @param ocean
	 * @return true if the spaces checked is occupied by another ship
	 */
	public boolean touchAnotherShip(int row, int column, boolean horizontal, Ocean ocean) {
		return (this.touchAnotherShipVertically(row, column, horizontal, ocean) ||
				this.touchAnotherShipHorizontally(row, column, horizontal, ocean) || 
				this.touchAnotherShipDiagonally(row, column, horizontal, ocean));
	}
	
	
	/**
	 * Determines if the corners of the ship touches another ship or not
	 * Returns 
	 * @param row
	 * @param column
	 * @param horizontal
	 * @param ocean
	 * @return true if the spaces checked is occupied by another ship
	 */
	public boolean touchAnotherShipDiagonally(int row, int column, boolean horizontal, Ocean ocean){
		ArrayList<int[]> location = this.shipPotentialLocation(row, column, horizontal);
		for (int[] coordinate : location){
			int xCoordinate = coordinate[0];			
			int yCoordinate = coordinate[1];
			if (xCoordinate == 0){
				if(touchAnotherShipDiagonallyX0Helper(0, yCoordinate, ocean)){
					return true;
				};
			} else if (xCoordinate == 9){
				if(touchAnotherShipDiagonallyX9Helper(9, yCoordinate, ocean)){
					return true;
				};		
			} else {
				if(touchAnotherShipDiagonallyXOtherHelper(xCoordinate, yCoordinate, ocean)){
					return true;
				};
			}
			
		}
		return false;
	}
	
	/**
	 * If the xCoordinate is not 0 or 9, tests if there are ships 1 diagonal from the relevant square
	 * @param xCoordinate
	 * @param yCoordinate
	 * @param ocean
	 * @return true if the spaces checked is occupied by another ship
	 */
	public boolean touchAnotherShipDiagonallyXOtherHelper(int xCoordinate, int yCoordinate, Ocean ocean){
		// x coordinate != 0 and != 9
		
		if (yCoordinate == 0){			// [x,0]
			if(ocean.isOccupied(xCoordinate - 1, yCoordinate + 1) || ocean.isOccupied(xCoordinate + 1, yCoordinate + 1)){
				return true;
			}
		} else if (yCoordinate == 9) {						// [x, 9]
			if(ocean.isOccupied(xCoordinate - 1, yCoordinate - 1) || ocean.isOccupied(xCoordinate + 1, yCoordinate - 1)){
				return true;
			}						
		} else {											// [x, y]
			if(ocean.isOccupied(xCoordinate - 1, yCoordinate - 1) || ocean.isOccupied(xCoordinate - 1, yCoordinate + 1) ||
					ocean.isOccupied(xCoordinate + 1, yCoordinate - 1) || ocean.isOccupied(xCoordinate + 1, yCoordinate + 1)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * If the xCoordinate is 0, tests if there are ships 1 diagonal from the relevant square
	 * @param xCoordinate
	 * @param yCoordinate
	 * @param ocean
	 * @return true if the spaces checked is occupied by another ship
	 */
	public boolean touchAnotherShipDiagonallyX0Helper(int xCoordinate, int yCoordinate, Ocean ocean){
		// x coordinate = 0
		
		if (yCoordinate == 0){			// [0,0]
			if(ocean.isOccupied(xCoordinate + 1, yCoordinate + 1)){
				return true;
			}
		} else if (yCoordinate == 9) {						// [0, 9]
			if(ocean.isOccupied(xCoordinate + 1, yCoordinate - 1)){
				return true;
			}						
		} else {											// [0, y]
			if(ocean.isOccupied(xCoordinate + 1, yCoordinate - 1) || ocean.isOccupied(xCoordinate + 1, yCoordinate + 1)){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * If the xCoordinate is 9, tests if there are ships 1 diagonal from the relevant square
	 * @param xCoordinate
	 * @param yCoordinate
	 * @param ocean
	 * @return true if the spaces checked is occupied by another ship
	 */
	public boolean touchAnotherShipDiagonallyX9Helper(int xCoordinate, int yCoordinate, Ocean ocean){
		// x coordinate = 9
		
		if (yCoordinate == 0){			// [9,0]
			if(ocean.isOccupied(xCoordinate - 1, yCoordinate + 1)){
				return true;
			}
		} else if (yCoordinate == 9) {						// [9, 9]
			if(ocean.isOccupied(xCoordinate - 1, yCoordinate - 1)){
				return true;
			}						
		} else {											// [9, y]
			if(ocean.isOccupied(xCoordinate - 1, yCoordinate - 1) || ocean.isOccupied(xCoordinate - 1, yCoordinate + 1)){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Determines whether the top and bottom flank of the potential ship location touches another ship.
	 * Returns true if flank touch.
	 * @param row
	 * @param column
	 * @param horizontal
	 * @param ocean
	 * @return true if the spaces checked is occupied by another ship
	 */
	public boolean touchAnotherShipHorizontally(int row, int column, boolean horizontal, Ocean ocean){
		ArrayList<int[]> location = this.shipPotentialLocation(row, column, horizontal);
		for (int[] coordinate : location){

			// We need to check if the space one above and one below of the current coordinate is occupied or not,
			// unless ship is on the top / bottom edge
			int xCoordinate = coordinate[0];			
			int yCoordinate = coordinate[1];		
			if ( yCoordinate > 0){
				if(ocean.isOccupied(xCoordinate, yCoordinate - 1)){
					return true;
				}
			}
			if (yCoordinate < 9){
				if(ocean.isOccupied(xCoordinate, yCoordinate + 1)){
					return true;
				}	
			}
		}
		return false;		
	}
	
	
	/**
	 * Determines whether the left and right flank of the potential ship location touches another ship. 
	 * Returns true if flanks touch.
	 * @param row
	 * @param column
	 * @param horizontal
	 * @param ocean
	 * @return true if the spaces checked is occupied by another ship
	 */
	public boolean touchAnotherShipVertically(int row, int column, boolean horizontal, Ocean ocean) {
		ArrayList<int[]> location = this.shipPotentialLocation(row, column, horizontal);
		for (int[] coordinate : location){

			// We need to check if the space one left and one right of the current coordinate is occupied or not,
			// unless ship is on the left / right edge
			int xCoordinate = coordinate[0];			
			int yCoordinate = coordinate[1];		
			if (xCoordinate > 0){
				if(ocean.isOccupied(xCoordinate - 1, yCoordinate)){
					return true;
				}
			}
			if (xCoordinate < 9){
				if(ocean.isOccupied(xCoordinate + 1, yCoordinate)){
					return true;
				}	
			}
		}
		return false;	
	}
	
	/**
	 * checks if the potential ship location is occupied by another ship or not. Returns true if the postiions are occupied.
	 * @param row
	 * @param column
	 * @param horizontal
	 * @param ocean
	 * @return true if the spaces checked is occupied by another ship
	 */
	public boolean overlaps(int row, int column, boolean horizontal, Ocean ocean) {
		ArrayList<int[]> potentialLocation = shipPotentialLocation(row, column, horizontal);
		for (int i=0;i<potentialLocation.size();i++) {
			
				int xCoordinate = potentialLocation.get(i)[0];
				int yCoordinate = potentialLocation.get(i)[1];
				if (ocean.isOccupied(xCoordinate, yCoordinate)){
					return true;
				}
		}
		return false;
	}
	
	public boolean stickOut(int row, int column, boolean horizontal, Ocean ocean){
		ArrayList<int[]> location = this.shipPotentialLocation(row, column, horizontal);
		for (int[] coordinate : location){
			if (coordinate[0] < 0 || coordinate[0] > 9){
				return true;
			} 		
			if (coordinate[1] < 0 || coordinate[1] > 9){
				return true;
			} 
		}	
		return false;	
	}
	
	/**
	 * Tests if a ship is sunk or not. 
	 * Returns true if all elements of the ship's 'hit' is true
	 * @return true if all elements of the ship's hit array is true
	 */
	public boolean isSunk(){
		for (int i = 0; i < this.hit.length; i++){
			if (hit[i] == false){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Given a row and column, determines if the ship occupies the particular space
	 * @param row
	 * @param column
	 * @return true if the spaces checked is occupied by another ship
	 */
	public boolean shipOccupiesSpace(int row, int column){
		// Determine the location of the ship based on instance variables, then compare parameters with the ship's location
		
		// If it's horizontal:
		if (this.horizontal){
			if (this.bowColumn != column){
				// since horizontal, ship will be in one column. First check if they're on the same column
				return false;
			} else {
				// ship will occupy between bowRow and bowRow + length - 1
				int stern = this.bowRow + this.length - 1;
				if (row >= this.bowRow && row <= stern){
					return true;
				} else {
					return false;
				}	
			}	
		} else {
			// if it's vertical:
			if (this.bowRow != row){
				// since vertical, ship will be in one row. First check if they're on the same row
				return false;
			} else {
				// ship will occupy between bowColumn and bowColumn + length - 1
				int stern = this.bowColumn + this.length - 1;
				if (column >= this.bowColumn && column <= stern){
					return true;
				} else {
					return false;
				}	
			}					
		}
	}
	
	/**
	 * Given a row and column, return an int which describes the corresponding part of the ship
	 * Assumes that the boat is on the correct row / column
	 * @param row
	 * @param column
	 * @return the part of the ship (0 = bow, (shipLength - 1) = stern)
	 */
	public int shipPart(int row, int column){
		int shipPart;
		if (this.horizontal){
			shipPart = row - this.bowRow; 
			return shipPart;
		} else {
			shipPart = column - this.bowColumn;
			return shipPart;
		}
	}
	
	/**
	 * Returns the potential coordinates of the ship (that has yet to be placed), 
	 * assuming that it will be placed in row,column. 
	 * Each coordinate is a 2-element array of integers 
	 * in the form array[0] = x and array[1] = y
	 * @return All the coordinates of a ship's potential location
	 */
	public ArrayList<int[]> shipPotentialLocation(int row, int column, boolean horizontal){
		
		ArrayList<int[]> shipLocation = new ArrayList<int[]>();
		if (horizontal){
			for (int i = 0; i < this.length; i++){
				int[] shipCoordinates = new int[2];
				shipCoordinates[0] = row + i;
				shipCoordinates[1] = column;
				shipLocation.add(shipCoordinates);
			}
		} else {
			for (int i = 0; i < this.length; i++){
				int[] shipCoordinates = new int[2];
				shipCoordinates[0] = row;
				shipCoordinates[1] = column + i;
				shipLocation.add(shipCoordinates);		
			}
		}
		return shipLocation;
	}
		
	
	
	/************
	 * GETTERS AND SETTERS
	 * *************
	 */
	
	public int getLength() {
		return length;
	}
	
	public int getBowRow() {
		return bowRow;
	}
	
	public int getBowColumn() {
		return bowColumn;
	}
	
	public void setBowRow(int row) {
		this.bowRow = row;
	}
	
	public void setBowColumn(int column) {
		this.bowColumn = column; 
	}
	
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}
	
	public String getShipType() {
		return "Empty Sea";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ship other = (Ship) obj;
		if (bowColumn != other.bowColumn)
			return false;
		if (bowRow != other.bowRow)
			return false;
		if (!Arrays.equals(hit, other.hit))
			return false;
		if (horizontal != other.horizontal)
			return false;
		if (length != other.length)
			return false;
		return true;
	}
	

	
	

}
