package skunk;

import java.util.ArrayList;

import edu.princeton.cs.introcs.StdOut;

public class SkunkEndGame {
	public static int finalComparisonGetWinner(int numberOfPlayers, String[] playerNames, ArrayList<Player> players) {
		int winner = 0;
		int winnerScore = 0;
		
		for (int player = 0; player < numberOfPlayers; player++)
		{
			Player nextPlayer = players.get(player);
			StdOut.println("Final round score for " + playerNames[player] + " is " + nextPlayer.getRoundScore() + ".");
			if (nextPlayer.getRoundScore() > winnerScore)
			{
				winner = player;
				winnerScore = nextPlayer.getRoundScore();
			}
		}
		return winner;
	}
	
	public static void finalReport(int winnerIndex, String[] playerNames, ArrayList<Player> players, int numberOfPlayers) {
		StdOut.println("Round winner is " + playerNames[winnerIndex] + " with score of " + players.get(winnerIndex).getRoundScore());
		players.get(winnerIndex).setNumberChips(players.get(winnerIndex).getNumberChips() + SkunkDomain.kitty);
		StdOut.println("\nRound winner earns " + SkunkDomain.kitty + ", finishing with " + players.get(winnerIndex).getNumberChips());

		StdOut.println("\nFinal scoreboard for this round:");
		StdOut.println("player name -- round score -- total chips");
		StdOut.println("-----------------------");

		for (int pNumber = 0; pNumber < numberOfPlayers; pNumber++)
		{
			StdOut.println(playerNames[pNumber] + " -- " + players.get(pNumber).roundScore + " -- "
					+ players.get(pNumber).getNumberChips());
		}

		StdOut.println("-----------------------");
	}
}
