package courseworkSD1;

import java.util.Random;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Game implements Serializable {

	private static final long serialVersionUID = -1669452376047412245L;

	private Grid theGrid;
	
	//max points the player can score
	private int maxPoints = 30;
	//max shots the player can take
	private int maxShots = 10;
	//Used to set cells values to 'already shot'.
	private int alreadyShot = -1;
	//creates a new player object associated with the game object
	Player player1 = new Player();
	//creates a HighScoreTable that can have data loaded on to it from a saved file
	HighScoreTable theHighScoreTable = new HighScoreTable();
	//an ArrayList of the created boats to be put on the grid
	private ArrayList <Boat> theBoats = new ArrayList <Boat> ();

	public Game() {}

	public void startNewGame(Game theGame) {
		createGrid();
		createBoats();
		populateGrid(theGame);
		//saves game after grid has been created and ships have been successfully built
		RunGame.saveGame(theGame);
		welcomeNewPlayer();
		playGame(theGame);
	}

	public void createGrid() {
		theGrid = new Grid();
	}

	public void createBoats() {
		//creating new boat objects with name, size, points and value data (value is what associates a boat to a cell/cells)
		Boat aircraftCarrier = new Boat("Aircraft Carrier", 5, 2, 1);
		Boat battleship = new Boat("Battleship", 4, 4, 2);
		Boat submarine = new Boat("Submarine", 3, 6, 3);
		Boat destroyer = new Boat("Destroyer", 2, 8, 4);
		Boat patrolBoat = new Boat("Patrol Boat", 1, 10, 5);

		//adds the boat objects to the ArrayList
		this.theBoats.add(aircraftCarrier);
		this.theBoats.add(battleship);
		this.theBoats.add(submarine);
		this.theBoats.add(destroyer);
		this.theBoats.add(patrolBoat);
	}

	//this method places the boats on the grid randomly
	public void populateGrid(Game theGame) {
		int rowNum, cellNum, rangeOne, plusOne;
		boolean isHorizontal;
		boolean successfulBuild = false;
		Cell tempCell;
		Row tempRow;

		//sets the range of the random number generator
		rangeOne = theGrid.getHighAxisNum();

		//used to try and take the integer out of the code
		plusOne = 1;

		//for creating random numbers for coordinates
		Random numGenerator = new Random();

		//for deciding whether to build horizontally or vertically
		Random trueOrFalseGenerator = new Random();

		//loop for 'shipbuilding', places each one of the ship objects in the ArrayList on to the grid
		for(Boat tempBoat : this.theBoats) {
			do{
				isHorizontal = trueOrFalseGenerator.nextBoolean();

				/* rangeOne - tempBoat.getSize()+plusOne makes sure that the boat does not start trying to build too 
				 * close to the edge of the grid, but also makes sure that every part of grid is available 
				 * for building on.
				 */
				rowNum = numGenerator.nextInt(rangeOne - tempBoat.getSize()+plusOne);
				cellNum = numGenerator.nextInt(rangeOne - tempBoat.getSize()+plusOne);
				successfulBuild = false;

				//sets a temporary cell to the randomly generated coordinates
				tempCell = theGrid.getRow(rowNum).getCell(cellNum);
				//sets a temporary row to the randomly generated row (y) coordinate
				tempRow = theGrid.getRow(rowNum);

				//checks if the temporary cell has a ship on it
				if(tempCell.isShipHere() == false) {
					//if it doesn't, then the system checks whether to build horizontally based on the random boolean
					if(isHorizontal == true) {
						//if so, then it builds horizontally
						successfulBuild = tempBoat.buildShipHorizontal(cellNum, rowNum, theGrid, tempRow, tempCell);
						if(successfulBuild == true) {
							//if the ship was successfully built and placed, breaks out of the loop to move on to the next ship
							break;
						}
					} else {
						//builds ship vertically
						successfulBuild = tempBoat.buildShipVertical(cellNum, rowNum, theGrid, tempRow);
						if(successfulBuild == true) {
							break;
						}
					}
				} 
				//repeats loop while the current ship in the ArrayList has not been built and placed on the grid successfully
			}while(successfulBuild == false);
		}
	}

	//used for testing throughout the design
	public void printGrid() {
		String output = "";
		for(Row tempRow : theGrid.getTheGrid()) {
			output = output +"\n";
			for(Cell tempCell : tempRow.getTheRow()) {
				output = output +tempCell.getNumber() +", " +tempRow.getNumber() +", " +tempCell.getValue() +"\n";
			}
		}
		System.out.println(output);
	}

	//creates a String output to print the ship coordinates for the Debug mode
	public String printGridDebugMode() {
		String acOutput, batOutput, subOutput, desOutput, pbOutput;
		acOutput = "Aircraft Carrier: \t";
		batOutput = "Battleship: \t";
		subOutput = "Submarine: \t";
		desOutput = "Destroyer: \t";
		pbOutput = "Patrol Boat: \t";

		//make simpler with for each loop
		for(Row tempRow : theGrid.getTheGrid()) {
			for(Cell tempCell : tempRow.getTheRow()) {
				if(tempCell.getValue() == theBoats.get(0).getValue()) {
					acOutput = acOutput +"(" +tempCell.getNumber() +"," +tempRow.getNumber() +") ";
				}
				if(tempCell.getValue() == theBoats.get(1).getValue()) {
					batOutput = batOutput +"(" +tempCell.getNumber() +"," +tempRow.getNumber() +") ";
				}
				if(tempCell.getValue() == theBoats.get(2).getValue()) {
					subOutput = subOutput +"(" +tempCell.getNumber() +"," +tempRow.getNumber() +") ";
				}
				if(tempCell.getValue() == theBoats.get(3).getValue()) {
					desOutput = desOutput +"(" +tempCell.getNumber() +"," +tempRow.getNumber() +") ";
				}
				if(tempCell.getValue() == theBoats.get(4).getValue()) {
					pbOutput = pbOutput +"(" +tempCell.getNumber() +"," +tempRow.getNumber() +") ";
				}
			}
		}
		return acOutput +"\n" +batOutput +"\n" +subOutput +"\n" + desOutput +"\n" +pbOutput;
	}

	/* Welcomes new player, sets their name and gives instructions. Only used for new games.
	 * separated out from playGame() method when the save game functionality was implemented
	 */
	public void welcomeNewPlayer() {
		String welcomeMessage;

		//sets player name
		player1.setName(JOptionPane.showInputDialog("Welcome to Battleship! Please enter your name."));

		//sets the players number of shots left
		player1.setShotsLeft(maxShots);

		welcomeMessage = "Welcome " +player1.getName()+".\n";
		welcomeMessage = welcomeMessage +"This game is played on a " +theGrid.getGridSize() +"x" +theGrid.getGridSize() +" grid.\n";
		welcomeMessage = welcomeMessage +"Each axis is numbered from " +theGrid.getLowAxisNum() +" to " +theGrid.getHighAxisNum() +".\n";
		welcomeMessage = welcomeMessage +"You will have " +maxShots +" shots to try and sink all the ships.\n";
		welcomeMessage = welcomeMessage +"The current game has saved. It will save after every turn taken.\n";
		welcomeMessage = welcomeMessage +"The high score table will be displayed at the end of the game with the top 5 player scores.";

		JOptionPane.showMessageDialog(null, welcomeMessage, "Welcome", JOptionPane.INFORMATION_MESSAGE);
	}

	//method for player input and feedback on game progress
	public void playGame (Game theGame) {
		String inputAsString;
		String output = "";
		int gameChoice, debugMode, normalMode, xInput, yInput;

		int [] validatedInput;

		//for removing integers from code
		debugMode = 0;
		normalMode = 1;

		//loads the saved High Score Table in to the game 
		loadHighScoreTable();

		gameChoice = JOptionPane.showConfirmDialog(null, "Do you wish to play in Debug mode?", "Debug mode?", JOptionPane.YES_NO_OPTION);

		//plays game in debug mode
		if(gameChoice == debugMode) {
			do{
				//saves the game after each turn
				RunGame.saveGame(theGame);
				output = "Please enter the coordinates you'd like to target.\n\n";
				output = output +"Each coordinate must be between " +theGrid.getLowAxisNum() +" and " +theGrid.getHighAxisNum()	+"\n\n";	
				output = output +"Please use the following format: 1,1\n\n";
				output = output +printGridDebugMode();

				//takes in player input
				inputAsString = JOptionPane.showInputDialog(output);

				//checks the format is correct (e.g. 1,1)
				inputAsString = validateFormat(inputAsString, theGrid.getLowAxisNum(), theGrid.getHighAxisNum());

				//validates the coordinates and returns them as an array
				validatedInput = validateInput(inputAsString, theGrid.getLowAxisNum(), theGrid.getHighAxisNum());

				//sets the coordinates to individual variables
				xInput = validatedInput[0];
				yInput = validatedInput[1];

				if(theGrid.getCellFromRow(yInput, xInput).isShipHere() == true) {
					hitShip(xInput, yInput, player1);
				} else {
					if(theGrid.getCellFromRow(yInput, xInput).isShipHere() == false) {
						if(theGrid.getCellFromRow(yInput, xInput).getValue() == alreadyShot) {
							goAgain(player1);
						} else {
							missShip(xInput, yInput, player1);
						}
					}
				}
			} while (player1.getPointsScored() < maxPoints && player1.getShotsTaken() < maxShots);
		}

		//plays game in normal mode
		if(gameChoice == normalMode) {
			do{
				//saves the game after each turn
				RunGame.saveGame(theGame);
				output = "Please enter the coordinates you'd like to target.\n\n";
				output = output +"Each coordinate must be between " +theGrid.getLowAxisNum() +" and " +theGrid.getHighAxisNum()	+"\n\n";	
				output = output +"Please use the following format: 1,1\n\n";

				inputAsString = JOptionPane.showInputDialog(output);

				inputAsString = validateFormat(inputAsString, theGrid.getLowAxisNum(), theGrid.getHighAxisNum());

				validatedInput = validateInput(inputAsString, theGrid.getLowAxisNum(), theGrid.getHighAxisNum());

				xInput = validatedInput[0];
				yInput = validatedInput[1];

				//checks whether there is a ship on the coordinates given by the player
				if(theGrid.getCellFromRow(yInput, xInput).isShipHere() == true) {
					//if so, hitShip() method is used
					hitShip(xInput, yInput, player1);
				} else {
					/*if not, then a couple of different methods can be called depending on whether the player
					 *has fired at the cell or not before
					 */
					if(theGrid.getCellFromRow(yInput, xInput).isShipHere() == false) {
						if(theGrid.getCellFromRow(yInput, xInput).getValue() == alreadyShot) {
							goAgain(player1);
						} else {
							missShip(xInput, yInput, player1);
						}
					}
				}
				/*loop runs while the players points have not reached the maximum (meaning
				 * they can't have shot all the ships) or if they have not taken all of
				 * their shots
				 */
			} while (player1.getPointsScored() < maxPoints && player1.getShotsTaken() < maxShots);
		}
		//adds player to the high score table
		theHighScoreTable.thePlayers.add(player1);
		//results are displayed at the end of the game
		displayResults(player1.getName(), player1.getPointsScored(), player1.getShotsLeft(), player1.getBoatsSunk());
		//displays and saves the high score table as it currently is
		displayAndSaveHighScoreTable();
	}

	//method to validate input format
	public String validateFormat(String inputAsString, int minRange, int maxRange) {
		String output = "";
		String newInputAsString = "";
		//checks whether the comma is at the right place
		if(inputAsString.charAt(1) != ',') {
			do {
				output = "Error - please make sure the coordinate numbers are in the correct format \n(e.g. 1,1) and are between " +minRange +" and " +maxRange +".";
				newInputAsString = JOptionPane.showInputDialog(null, output, "Error", JOptionPane.ERROR_MESSAGE);
			} while (newInputAsString.charAt(1) != ',');
			return newInputAsString;
		} 
		return inputAsString;
	}

	//method to validate coordinate numbers are within the range of the grid's x and y axes
	private int[] validateInput(String inputAsString, int minRange, int maxRange) {
		String output = "";
		String newInputAsString = "";
		String xInputAsString, yInputAsString;
		int xInput, yInput, ARRAY_SIZE;

		String [] inputSplit;

		ARRAY_SIZE = 2;
		int [] validatedInput = new int[ARRAY_SIZE];

		//splits the string input at the comma and places the integers in a string array
		inputSplit = inputAsString.split(",");

		xInputAsString = inputSplit[0];
		yInputAsString = inputSplit[1];

		//turns the string elements taken from the array into integers
		xInput = Integer.parseInt(xInputAsString);
		yInput = Integer.parseInt(yInputAsString);

		//checks integers are within range
		if(xInput < minRange || xInput > maxRange || yInput < minRange || yInput > maxRange) {
			do {
				output = "Error - please make sure the coordinate numbers are in the correct format \n(e.g. 1,1) and are between " +minRange +" and " +maxRange +".";

				newInputAsString = JOptionPane.showInputDialog(null, output, "Error", JOptionPane.ERROR_MESSAGE);
				newInputAsString = validateFormat(newInputAsString, minRange, maxRange);
				//use stringAt for validation here
				inputSplit = newInputAsString.split(",");

				xInputAsString = inputSplit[0];
				yInputAsString = inputSplit[1];

				xInput = Integer.parseInt(xInputAsString);
				yInput = Integer.parseInt(yInputAsString);
			} while (xInput < minRange || xInput > maxRange || yInput < minRange || yInput > maxRange);
		}

		validatedInput[0] = xInput;
		validatedInput[1] = yInput;

		//returns validated integers as an array
		return validatedInput;
	}

	//method for when the player hits a ship
	public void hitShip(int xInput, int yInput, Player player1) {
		int shipHitValue;
		String output;

		//finds the value of the ship hit from the value of the cell
		shipHitValue = theGrid.getCellFromRow(yInput, xInput).getValue();

		//loop to find the type of ship hit and get data associated with it
		for(Boat tempBoat : this.theBoats){
			if(tempBoat.getValue() == shipHitValue){
				player1.addPoints(tempBoat.getPoints());
				player1.removeShot();
				player1.getBoatsSunk().add(tempBoat);

				output = "You hit a ship!\n";
				output = output +"Ship type: " +tempBoat.getName() +"\n";
				output = output +"Points: " +tempBoat.getPoints() +"\n";
				output = output +"Total points: " +player1.getPointsScored() +"\n";
				output = output +"Shots left: " +player1.getShotsLeft();

				JOptionPane.showMessageDialog(null, output);

				//updates the grid's data
				theGrid.getCellFromRow(yInput, xInput).setShipHere(false);
				theGrid.getCellFromRow(yInput, xInput).setValue(alreadyShot);

				//calls method to sink the whole ship now it has been shot
				sinkWholeShip(shipHitValue);

				//adds a shot to the players shotsTaken data
				player1.addShotTaken();
			}
		}
	}

	//method for when a player misses a ship
	public void missShip(int xInput, int yInput, Player player1) {
		String output;

		//updates player's data
		player1.removeShot();
		player1.addShotTaken();

		//updates grid's data
		theGrid.getCellFromRow(yInput, xInput).setValue(alreadyShot);

		output = "You missed!\n";
		output = output +"Total points: " +player1.getPointsScored() +"\n";
		output = output +"Shots left: " +player1.getShotsLeft();

		JOptionPane.showMessageDialog(null, output);
	}

	//method to tell player they have already shot a cell
	public void goAgain(Player player1) {
		String output;

		output = "You have already shot this coordinate! Please go again.\nYou have " +player1.getShotsLeft() +" shots left.";

		JOptionPane.showMessageDialog(null, output, "Already shot!", JOptionPane.INFORMATION_MESSAGE);	
	}

	//method for sinking the whole ship when it has been shot once
	public void sinkWholeShip(int shipHitValue) {
		int sunkenShip = 0;

		//checks entire grid for cells with the value of the ship that has been hit
		for(Row tempRow : theGrid.getTheGrid()) {
			for(Cell tempCell : tempRow.getTheRow()) {
				if(tempCell.getValue() == shipHitValue) {

					//updates those cells with new data
					tempCell.setValue(sunkenShip);
					tempCell.setShipHere(false);
				}
			}
		}
	}

	//method to display the results of the game to the player
	public void displayResults(String name, int pointsScored, int shotsLeft, ArrayList<Boat> boatsSunk) {
		String output;
		String boatNames = "";
		for(Boat tempBoat : boatsSunk) {
			boatNames = boatNames +tempBoat.getName() +"\n";
		}

		output = "Well done " +name +".\nYou scored: " +pointsScored +" points.\n"; 
		output = output +"You had " +shotsLeft +" shots left.\n";
		output = output +"You sank these boats: \n\n" +boatNames;

		JOptionPane.showMessageDialog(null, output);
	}

	private void displayAndSaveHighScoreTable() {
		theHighScoreTable.displayTable();
		try {
			FileOutputStream fileOut = new FileOutputStream("/tmp/table.ser");
			ObjectOutputStream tableOut = new ObjectOutputStream(fileOut);

			//writes the current HighScoreTable object state to the file
			tableOut.writeObject(theHighScoreTable);
			tableOut.close();
			fileOut.close();
		} catch (IOException i) {}
	}

	private void loadHighScoreTable() {
		try {
			FileInputStream fileIn = new FileInputStream("/tmp/table.ser");
			ObjectInputStream tableIn = new ObjectInputStream(fileIn);

			//sets the new HighScoreTable object to the saved HighScoreTable object
			theHighScoreTable = (HighScoreTable) tableIn.readObject();

			tableIn.close();
			fileIn.close();
		} catch (IOException i) {} 
		catch (ClassNotFoundException c) {}
	}

	public int getMaxPoints() {
		return this.maxPoints;
	}

	public void setMaxPoints(int maxPoints) {
		this.maxPoints = maxPoints;
	}

	public int getMaxShots() {
		return this.maxShots;
	}

	public void setMaxShots(int maxShots) {
		this.maxShots = maxShots;
	}
}
