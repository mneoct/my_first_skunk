package skunk;

import java.util.ArrayList;
import java.util.Scanner;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

public class SkunkPlayerManager {
	
	public static int numberOfPlayers;
	public static String[] playerNames = new String[20];
	public static ArrayList<Player> players = new ArrayList<Player>();;
	public static Scanner playerNameInput = new Scanner(System.in);
	
	// no idea how UI works so its copied here.
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
			playerNames[playerNumber] = StdIn.readLine();
			players.add(new Player(50));
		}
	}
}
