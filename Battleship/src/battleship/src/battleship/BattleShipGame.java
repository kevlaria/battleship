package battleship;

import java.util.Scanner;

public class BattleShipGame {

	Ocean ocean;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {	
		new BattleShipGame().run();
	}
	
	void run(){
		Ocean ocean = new Ocean();
		System.out.println("A fleet of zombie pirates are invading your territory! \nHelp your fellow countrymen and sink the pirates' fleet!");
		this.initiateGame(ocean);
	}
	
	
	/**
	 * Initiates the game, and loops until game is finished.
	 * @param ocean
	 */
	public void initiateGame(Ocean ocean){
		ocean.placeAllShipsRandomly();
		boolean finished = ocean.isGameOver();
		while (!finished){
			this.printStatus(ocean);
			int xCoordinate = this.inputCoordinate("x");
			int yCoordinate = this.inputCoordinate("y");
			this.fireShot(xCoordinate, yCoordinate, ocean);
			finished = ocean.isGameOver();			
		}
		System.out.println("Congratulations!");
		System.exit(0);
	}
	
	
	/**
	 * Fires a shot. Prints out a message depending on a) whether a ship was hit or not, 
	 * and b) whether a ship was sunk or not.
	 * @param xCoordinate
	 * @param yCoordinate
	 * @param ocean
	 */
	public void fireShot(int xCoordinate, int yCoordinate, Ocean ocean){
		System.out.println("You've just shot (" + xCoordinate + ", " + yCoordinate + ").");
		int shipsSunkBefore = ocean.getShipsSunk();
		boolean isHitSuccessful = ocean.shootAt(xCoordinate, xCoordinate);
		
		int shipsSunkAfter = ocean.getShipsSunk();
		boolean newShipSunk = (shipsSunkBefore < shipsSunkAfter);
		
		if (isHitSuccessful && newShipSunk){
			System.out.println("You sunk a " + ocean.getShipAtLocation(xCoordinate, yCoordinate) + "!");
		} else if (isHitSuccessful){
			System.out.println("You hit a ship!");
		} else {
			System.out.println("You hit a stray pigeon, but no ships.");
		}
	}
	
	

	
	/**
	 * Asks the user to provide the x or y coordinate of the shot
	 * @param axis - either x or y
	 * @return an integer representing the coordinate
	 */
	public int inputCoordinate(String axis){
		Scanner scanner = new Scanner(System.in);
		String input = "";
		boolean valid = false;
		while(!valid){
			System.out.print("\nPlease enter the " + axis + " coordinate of your shot: ");
			input = scanner.nextLine();
			if(isValid(input)){
				valid = true;
			} else {
				this.printErrorMessage(input);
			}
		}
		int inputInteger = Integer.parseInt(input);
		return inputInteger;
	}
	
	
	/**
	 * Takes an input and checks if it's an integer between 0 - 9. Returns true if it does
	 * @param input
	 * @return true if input is an integer between 0 - 9
	 */
	public boolean isValid(String input){
		if(!isInt(input)){
			return false;
		}
		int inputInt = Integer.parseInt(input);
		if (inputInt < 0 || inputInt > 9){
			return false;
		}
		return true;
	}
	
	
	/**
	 * Tests if an input is an integer or not
	 * @param input
	 * @return true if it's an integer, false otherwise
	 */
	public boolean isInt(String input){
		try{
			Integer.parseInt(input);
			return true;
		}
		catch (NumberFormatException exception) {
			return false;
		}
	}
	
	
	/**
	 * Print error message
	 * @param input - string to indicate n'th polynomial input (eg "first", "second")
	 * @result - a print statement
	 */
	public void printErrorMessage(String input) {
		System.out.print("Your input, '" + input + "', is invalid. Please re-enter.\n");
	}
	
	
	public void printStatus(Ocean ocean){
		int shotsFired = ocean.getShotsFired();
		int shipsSunk = ocean.getShipsSunk();
		int hitCount = ocean.getHitCount();
		System.out.println("\n\n\n************");
		ocean.print();		
		System.out.println("\n\n\n************");
		System.out.println("Your status:");
		System.out.println("\tYou have fired " + shotsFired + " shot(s).");
		System.out.println("\tYou have made " + hitCount + " hit(s).");
		System.out.println("\tYou have sunk " + shipsSunk + " ship(s).");
	}

}