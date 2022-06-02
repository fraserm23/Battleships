package courseworkSD1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JOptionPane;

public class RunGame implements Serializable {

	private static final long serialVersionUID = -6448655293855429439L;

	public static void main(String[] args) {

		//creates new game object
		Game theGame = new Game();

		//for taking integers out of the code
		int yes = 0;
		int no = 1;

		int option = JOptionPane.showConfirmDialog(null, "Would you like to load from the last saved game?", "Load Game", JOptionPane.YES_NO_OPTION);

		if(option == no) {
			theGame.startNewGame(theGame);
		} else {
			if(option == yes) {
				loadGame(theGame);
			}
		}
	}

	//method for loading game from saved file
	public static void loadGame(Game theGame) {
		try {
			FileInputStream fileIn = new FileInputStream("/tmp/game.ser");
			ObjectInputStream gameIn = new ObjectInputStream(fileIn);

			//sets the new game object to the saved game object
			theGame = (Game) gameIn.readObject();

			//starts game from where it left off
			theGame.playGame(theGame);
			gameIn.close();
			fileIn.close();
		} catch (IOException i) {} 
		catch (ClassNotFoundException c) {}
	}

	//method for saving game to a file
	public static void saveGame(Game theGame) {
		try {
			FileOutputStream fileOut = new FileOutputStream("/tmp/game.ser");
			ObjectOutputStream gameOut = new ObjectOutputStream(fileOut);

			//writes the current game object state to the file
			gameOut.writeObject(theGame);
			gameOut.close();
			fileOut.close();
		} catch (IOException i) {}
	}
}
