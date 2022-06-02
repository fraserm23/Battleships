package courseworkSD1;

import java.util.Comparator;

public class SortPlayers implements Comparator <Player> {

	@Override
	public int compare(Player player1, Player player2) {
		return player2.getPointsScored() - player1.getPointsScored();
	}
}


