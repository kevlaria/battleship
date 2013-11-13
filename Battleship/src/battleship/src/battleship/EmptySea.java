package battleship;

public class EmptySea extends Ship{

	public EmptySea(){
		this.length = 1;
	}
	
	@Override
	public String toString(){
			return ".";
	}
	
	@Override
	public boolean shootAt(int row, int column){
		return false;
	}
	
	@Override
	public boolean isSunk(){
		return false;
	}
	
}
