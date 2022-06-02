package courseworkSD1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

public class HighScoreTable implements Serializable {

	private static final long serialVersionUID = -3293141792578790703L;

	//stores the players that are featured on the high score table
	ArrayList <Player> thePlayers = new ArrayList <Player> ();

	public HighScoreTable() {}


	public void displayTable() {
		//sorts the players in to the correct order
		Collections.sort(thePlayers, new SortPlayers());

		String output = "";
		int positionNum = 1;

		//prints players names and points
		for(Player tempPlayer : this.thePlayers) {
			output = output +positionNum +". Name: " +tempPlayer.getName() +"  Score: " +tempPlayer.getPointsScored() +"\n";
			positionNum++;
			if(positionNum > 5) {
				break;
			}
		}
		JOptionPane.showMessageDialog(null, output, "High Score Table", JOptionPane.INFORMATION_MESSAGE);
	}


	public ArrayList<Player> getThePlayers() {
		return this.thePlayers;
	}

	public void setThePlayers(ArrayList<Player> thePlayers) {
		this.thePlayers = thePlayers;
	}

}

	
