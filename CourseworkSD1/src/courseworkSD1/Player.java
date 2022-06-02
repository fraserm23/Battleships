package courseworkSD1;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

	private static final long serialVersionUID = 6082172150978917184L;

	private String name;

	/* I decided to have both shotsTaken and shotsLeft for the purposes of
	 * displaying information to the player.
	 */
	private int pointsScored, shotsTaken, shotsLeft;

	//an ArrayList that stores all the boats the player has sunk
	ArrayList <Boat> boatsSunk = new ArrayList <Boat> ();

	public Player() {}

	//calculates the number of shots the player has taken
	public void addShotTaken() {
		setShotsTaken(getShotsTaken()+1);
	}

	//adds on points to the players total score when they hit a ship
	public void addPoints(int pointsScored) {
		setPointsScored(this.pointsScored + pointsScored);
	}

	//removes a shot from the number of shots the player has left
	public void removeShot() {
		setShotsLeft(getShotsLeft()-1);
	}

	public int getShotsTaken() {
		return this.shotsTaken;
	}

	public void setShotsTaken(int shotsTaken) {
		this.shotsTaken = shotsTaken;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPointsScored() {
		return this.pointsScored;
	}

	public void setPointsScored(int pointsScored) {
		this.pointsScored = pointsScored;
	}

	public int getShotsLeft() {
		return this.shotsLeft;
	}

	public void setShotsLeft(int shotsLeft) {
		this.shotsLeft = shotsLeft;
	}

	public ArrayList<Boat> getBoatsSunk() {
		return this.boatsSunk;
	}

	public void setBoatsSunk(ArrayList<Boat> boatsSunk) {
		this.boatsSunk = boatsSunk;
	}
}
