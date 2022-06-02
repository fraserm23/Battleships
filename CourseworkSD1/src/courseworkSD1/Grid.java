package courseworkSD1;

import java.io.Serializable;
import java.util.ArrayList;

public class Grid implements Serializable {

	private static final long serialVersionUID = 8170108858152323160L;

	private int gridSize = 10;
	private int lowAxisNum = 0;
	private int highAxisNum = this.gridSize - 1;

	//stores an array of rows, making a grid
	private ArrayList <Row> theGrid = new ArrayList <Row> ();

	//creates the Grid of Rows
	public Grid() {
		Row tempRow;
		for(int loop = 0; loop < gridSize; loop++) {
			tempRow = new Row(loop);
			this.theGrid.add(tempRow);
		}
	}

	//checks a given row for ships
	public boolean checkRow(int rowNum, int cellNum) {
		return this.theGrid.get(rowNum).checkCellForShip(cellNum);
	}

	//returns a specific cell from a specific row
	public Cell getCellFromRow(int rowNum, int cellNum) {
		return this.theGrid.get(rowNum).getCell(cellNum);	
	}

	public int getGridSize() {
		return this.gridSize;
	}

	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

	public Row getRow(int rowNum) {
		return this.theGrid.get(rowNum);
	}

	public int getLowAxisNum() {
		return this.lowAxisNum;
	}

	public void setLowAxisNum(int lowAxisNum) {
		this.lowAxisNum = lowAxisNum;
	}

	public int getHighAxisNum() {
		return this.highAxisNum;
	}

	public void setHighAxisNum(int highAxisNum) {
		this.highAxisNum = highAxisNum;
	}

	public ArrayList<Row> getTheGrid() {
		return this.theGrid;
	}

	public void setTheGrid(ArrayList<Row> theGrid) {
		this.theGrid = theGrid;
	}

}
