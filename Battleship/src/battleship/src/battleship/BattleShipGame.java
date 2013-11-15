// Priyank Chaudhary, Kevin Lee

package battleship;

import java.util.Random;
import java.util.Scanner;

public class BattleShipGame {

	private Ocean ocean;
	
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
			int row = this.inputCoordinate("row");
			int column = this.inputCoordinate("column");
			this.fireShot(row, column, ocean);
			finished = ocean.isGameOver();			
		}
		this.printFinalStatus(ocean);
		System.exit(0);
	}
	
	
	/**
	 * Fires a shot. Prints out a message depending on a) whether a ship was hit or not, 
	 * and b) whether a ship was sunk or not.
	 * @param xCoordinate
	 * @param yCoordinate
	 * @param ocean
	 */
	public void fireShot(int row, int column, Ocean ocean){
		System.out.println("You've just fired a shot at (" + row + ", " + column + ").");
		int shipsSunkBefore = ocean.getShipsSunk();
		boolean isHitSuccessful = ocean.shootAt(row, column);
		
		int shipsSunkAfter = ocean.getShipsSunk();
		boolean newShipSunk = (shipsSunkBefore < shipsSunkAfter);
		
		if (isHitSuccessful && newShipSunk){
			System.out.println("\nYou sank a " + ocean.getShipAtLocation(row, column).toLowerCase() + "!");
		} else if (isHitSuccessful){
			System.out.println("\nYou hit a ship!");
		} else {
			Random random = new Random();
			int randomNumber = random.nextInt(10);
			this.printFailureMessage(randomNumber);
		}
	}
	

	public void printFailureMessage(int randomNumber){
		switch (randomNumber){
		case 0:
			System.out.println("\nYou hit a stray pigeon, but no ships.");
			break;
		case 1:
			System.out.println("\nYou scared off a mermaid, but didn't hit any ships.");
			break;
		case 2:
			System.out.println("\n'SPLASH'. You didn't hit anything.");
			break;
		case 3:
			System.out.println("\nThat nearly hit the moon. Try again.");
			break;
		case 4:
			System.out.println("\nThe cannonball sailed through the air gracefully, \nfailing to hit anything.");
			break;
		case 5:
			System.out.println("\nIf this were a diving competition, \nthe cannonball would have won first prize. \n\nDidn't hit anything though.");
			break;
		case 6:
			System.out.println("\n...Nope...");
			break;
		case 7:
			System.out.println("\nYour aim is terrible! Try again!");
			break;
		case 8:
			System.out.println("\nMissed. This could be a looooooong day...");
			break;			
		case 9:
			System.out.println("\nMaybe you should try aiming first before firing?");
			break;			
			
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
			System.out.print("\nWhich " + axis + " do you want to aim at? ");
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
	
	/**
	 * Print status of the ocean at each round
	 * @param ocean
	 */
	public void printStatus(Ocean ocean){
		int shotsFired = ocean.getShotsFired();
		int shipsSunk = ocean.getShipsSunk();
		int hitCount = ocean.getHitCount();
		System.out.println("\n************\n");
		ocean.print();		
		System.out.println("\n\n************");
		System.out.println("Your status:");
		System.out.println("\tYou fired " + shotsFired + " shot(s).");
		System.out.println("\tYou made " + hitCount + " hit(s).");
		System.out.println("\tYou sunk " + shipsSunk + " ship(s).");
	}

	public Ocean getOcean(){
		return this.ocean;
	}
	
	/**
	 * Print final status of the ocean
	 * @param ocean
	 */
	public void printFinalStatus(Ocean ocean){
		this.printStatus(ocean);
		int shotsFired = ocean.getShotsFired();
		if (shotsFired == 20){
			System.out.println("\nCongratulations! You destroyed the zombie fleet, AND you got the perfect score. \nAre you sure you weren't cheating?");			
		} else if (shotsFired < 40){
			System.out.println("\nCongratulations! You destroyed the zombie fleet, \nand saved enough ammo for all future zombie invasions!");						
		} else if (shotsFired < 55){
			System.out.println("\nCongratulations! You destroyed the zombie fleet, \nbut you've exhausted your ammo store. What are you going \nto do about the next zombie invasion?");						
		} else if (shotsFired < 70){
			System.out.println("\nYou took how many shots? \nA blind owl would have done better than you!");
		} else {
			System.out.println("\nWhile you were figuring out how a cannon works, \nthe zombie pirates had enough time to annihilate your artillery, \ndestroy your territory, and spawn a new civilisation. \nDon't quit your daytime job anytime too soon...");			
		}
	}
}