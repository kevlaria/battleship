package battleship;

public class Cruiser extends Ship{

	public Cruiser(){
		this.length = 3;
		this.hit = new boolean[3];
	}
	
	@Override
	public String getShipType(){
		return "Cruiser";
	}
	
	@Override
	public String toString(){
		return "S";
	}
}
