package battleship;

public class Destroyer extends Ship{
	
	public Destroyer(){
		this.length = 2;
		this.hit = new boolean[2];
	}
	
	@Override
	public String getShipType(){
		return "Destroyer";
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
