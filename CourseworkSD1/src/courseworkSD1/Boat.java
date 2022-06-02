package courseworkSD1;

import java.io.Serializable;
import java.util.ArrayList;

public class Boat implements Serializable {

	private static final long serialVersionUID = -1157552029820343778L;

	//name of the boat
	private String name;
	//size (number of cells) of the boat
	private int size;
	//points associated with the boat
	private int points;
	//value associated with the boat (cells that have the boat 'placed' on them will share this value)
	private int value;

	//an ArrayList of cells on a row that are free from ships
	private ArrayList <Cell> savedCells = new ArrayList <Cell> ();
	//an ArrayList of rows with the same x coordinate free from ships
	private ArrayList <Row> savedRows = new ArrayList <Row> ();

	public Boat(String name, int size, int points, int value) {
		setName(name);
		setSize(size);
		setPoints(points);
		setValue(value);
	}

	//this method builds the ship horizontally
	public boolean buildShipHorizontal(int cellNum, int rowNum, Grid theGrid, Row tempRow, Cell tempCell) {
		int finishedBuild = 0;

		//clears ArrayList from any previous build attempts
		savedCells.clear();

		/* this loop checks if there are enough available cells on a given row to build the ship,
		 * starting from the randomly generated coordinates
		*/
		do {
			if(tempRow.checkCellForShip(cellNum) == false) {

				//cell is added to the savedCells ArrayList if free
				savedCells.add(tempCell);

				//this moves the 'ship builder' on to the next cell
				cellNum++;
				tempCell = theGrid.getRow(rowNum).getCell(cellNum);

				//this checks whether the ship has reached its correct size yet
				//if it has, then it confirms the build using the saved cells ArrayList
				if(this.savedCells.size() == this.size) {

					for(Cell theCell : this.savedCells) {
						theCell.setShipHere(true);
						theCell.setValue(this.value);
						finishedBuild++;	
					}
				}
			} else {
				//this break is carried out if there is not enough cells free to build the ship
				break;
			}
			//continues the loop while the number of the saved cells in the ArrayList is smaller than the size of the ship
		} while (this.savedCells.size() < this.size);

		//returns true if the build was successful
		if(finishedBuild == this.size) {
			return true;
		} else {
			return false;
		}
	}

	//this method builds the ship vertically
	public boolean buildShipVertical(int cellNum, int rowNum, Grid theGrid, Row tempRow) {
		int finishedBuild = 0;
		savedRows.clear();

		//this loop checks whether there are enough cells (checking the same x coordinate on each row) across a number of rows to build the ship, starting with the given coordinates
		do {
			if(tempRow.checkCellForShip(cellNum) == false) {
				savedRows.add(tempRow);
				rowNum++;
				tempRow = theGrid.getRow(rowNum);

				if(this.savedRows.size() == this.size) {

					for(Row theRow: this.savedRows) {

						theRow.getCell(cellNum).setShipHere(true);
						theRow.getCell(cellNum).setValue(this.value);
						finishedBuild++;	
					}
				}
			} else {
				break;
			}
		} while (this.savedRows.size() < this.size);
		if(finishedBuild == this.size) {
			return true;
		} else {
			return false;
		}
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPoints() {
		return this.points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
