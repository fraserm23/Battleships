package courseworkSD1;

import java.io.Serializable;
import java.util.ArrayList;


public class Row implements Serializable {

	private static final long serialVersionUID = 6050743150145552870L;

	//the Row (y coordinate) number
	private int number;
	private int rowSize = 10;

	//stores an array of Cells, making a Row
	private ArrayList <Cell> theRow = new ArrayList <Cell> ();

	//creates the Row of Cells
	public Row (int number) {
		Cell tempCell;
		setNumber(number);
		for(int loop = 0; loop < rowSize; loop++) {
			tempCell = new Cell(loop);
			this.theRow.add(tempCell);	
		}
	}

	//checks the row for ships
	public boolean checkCellForShip(int cellNum) {
		return this.theRow.get(cellNum).isShipHere();
	}

	public Cell getCell(int cellNum) {
		return this.theRow.get(cellNum);
	}

	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getRowSize() {
		return this.rowSize;
	}

	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}

	public ArrayList<Cell> getTheRow() {
		return this.theRow;
	}

	public void setTheRow(ArrayList<Cell> theRow) {
		this.theRow = theRow;
	}
}
