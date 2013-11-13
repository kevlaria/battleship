package battleship;

public class Battleship extends Ship {

	public Battleship(){
		this.length = 4;
		this.hit = new boolean[4];
		for (int i = 0; i < hit.length; i++){
			this.hit[i] = false;
		}
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

