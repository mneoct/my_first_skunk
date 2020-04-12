package skunk;

import java.util.ArrayList;
import java.util.Scanner;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

public class SkunkPlayer {
	
	public static int numberOfPlayers;
	public static String[] playerNames;
	public static ArrayList<Player> players;
	public static Scanner playerNameInput = new Scanner(System.in);
	
	public SkunkPlayer()
	{
		SkunkPlayer.playerNames = new String[20];
		SkunkPlayer.players = new ArrayList<Player>();
	}
	
	public Player activePlayer;
	public int activePlayerIndex;
	
	public String promptReadAndReturn(String question)
	{
		StdOut.print(question + " => ");
		String result = StdIn.readLine();
		return result;
	}
	
	public static void playerRegistration() {
		StdOut.print("How many players? => ");
		String result = StdIn.readLine();
		numberOfPlayers = Integer.parseInt(result);
		for (int playerNumber = 0; playerNumber < numberOfPlayers; playerNumber++)
		{
			StdOut.print("Enter name of player " + (playerNumber + 1) + ": ");
			playerNames[playerNumber] = playerNameInput.nextLine();
			players.add(new Player(50));
		}
	}
}
