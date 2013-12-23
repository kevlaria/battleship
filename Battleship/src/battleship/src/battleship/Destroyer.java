package battleship;

public class Destroyer extends Ship{
	
	public Destroyer(){
		this.length = 2;
		this.hit = new boolean[2];
		for (int i = 0; i < hit.length; i++){
			this.hit[i] = false;
		}
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
