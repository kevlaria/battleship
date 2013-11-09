package battleship;

/**
 * @author priyank
 *
 */
public class BattleShipGame {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EmptySea emptySea = new EmptySea();
		System.out.println(emptySea.getLength());
		Ocean ocean = new Ocean();
		System.out.println(ocean.isOccupied(9, 9));
		Battleship battleship = new Battleship();
		battleship.placeShipAt(0, 0, true, ocean);
		

	}

}