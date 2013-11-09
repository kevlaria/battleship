package battleship;

import java.util.Arrays;

public class Ship {
	private int bowRow;
	private int bowColumn;
	protected int length;
	private boolean horizontal;
	private boolean [] hit = new boolean[4];
	

	
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
		return null;
	}


//	public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
//		boolean okToPlace = false;
//	
//		return okToPlace;
//	}	

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
