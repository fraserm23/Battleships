package courseworkSD1;

import java.io.Serializable;

public class Cell implements Serializable {

	private static final long serialVersionUID = 8440172788246630512L;

	//the Cell (x coordinate) number
	private int number;
	private boolean shipHere = false;
	//a value associated with the cell's state (either type of ship on it(1-5), no ship on it(0), already fired at(-1))
	private int value;

	public Cell(int number) {
		setNumber(number);
	}

	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isShipHere() {
		return this.shipHere;
	}

	public void setShipHere(boolean shipHere) {
		this.shipHere = shipHere;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
