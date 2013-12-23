package battleship;

public class Submarine extends Ship {
	
	public Submarine(){
		this.length = 1;
		this.hit = new boolean[1];
		for (int i = 0; i < hit.length; i++){
			this.hit[i] = false;
		}
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
