// Priyank Chaudhary, Kevin Lee
package battleship;

public class Cruiser extends Ship{

	public Cruiser(){
		this.length = 3;
		this.hit = new boolean[3];
		for (int i = 0; i < hit.length; i++){
			this.hit[i] = false;
		}
	}
	
	@Override
	public String getShipType(){
		return "Cruiser";
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
