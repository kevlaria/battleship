package battleship;

public class Battleship extends Ship {

	public Battleship(){
		this.length = 4;
		this.hit = new boolean[4];
	}
	
	@Override
	public String getShipType(){
		return "Battleship";
	}
	
	@Override
	public String toString(){
		if (this.isSunk()){
			return "x";
		} else {
						
			return "S";			
		}
	}
	
}

