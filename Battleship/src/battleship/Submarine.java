package battleship;

public class Submarine extends Ship {
	
	public Submarine(){
		this.length = 1;
		this.hit = new boolean[1];
	}
	
	@Override
	public String getShipType(){
		return "Submarine";
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
