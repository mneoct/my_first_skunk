package skunk;
import java.util.ArrayList;

import edu.princeton.cs.introcs.StdIn;

public class SkunkDomain
{
	public SkunkUI skunkUI;
	public UI userInterface;
	public static int kitty;
	
	public int numberOfPlayers;
	public String[] playerNames;
	public ArrayList<Player> players;

	public Player activePlayer;
	public int activePlayerIndex;

	public boolean wantsToQuit;
	public boolean oneMoreRoll;

	public Dice skunkDice;

	public SkunkDomain(SkunkUI ui)
	{
		this.skunkUI = ui;
		this.userInterface = ui; // hide behind the interface UI

		this.playerNames = new String[20];
		this.players = new ArrayList<Player>();
		this.skunkDice = new Dice();
		this.wantsToQuit = false;
		this.oneMoreRoll = false;
	}

	public void playerRegistration() {
		String numberPlayersString = skunkUI.promptReadAndReturn("How many players?");
		this.numberOfPlayers = Integer.parseInt(numberPlayersString);

		for (int playerNumber = 0; playerNumber < numberOfPlayers; playerNumber++)
		{
			userInterface.print("Enter name of player " + (playerNumber + 1) + ": ");
			playerNames[playerNumber] = StdIn.readLine();
			this.players.add(new Player(50));
		}
	}

	
	public void endTurnEvaluation(int newScore){
		userInterface.println("End of turn for " + playerNames[activePlayerIndex]);
		userInterface.println("Score for this turn is " + activePlayer.getTurnScore() + ", added to...");
		userInterface.println("Previous round score of " + activePlayer.getRoundScore());
		userInterface.println("Giving new round score of " + newScore);

		userInterface.println("");

		userInterface.println("Scoreboard: ");
		userInterface.println("Kitty has " + kitty);
		userInterface.println("player name -- turn score -- round score -- chips");
		userInterface.println("-----------------------");

		activePlayer.setRoundScore(newScore);
		
		for (int i = 0; i < numberOfPlayers; i++)
		{
			userInterface.println(playerNames[i] + " -- " + players.get(i).turnScore + " -- " + players.get(i).roundScore
					+ " -- " + players.get(i).getNumberChips());
		}
		userInterface.println("-----------------------");

		userInterface.println("Turn passes to right...");
	}
	
	public int finalComparisonGetWinner() {
		int winner = 0;
		int winnerScore = 0;
		
		for (int player = 0; player < numberOfPlayers; player++)
		{
			Player nextPlayer = players.get(player);
			userInterface.println("Final round score for " + playerNames[player] + " is " + nextPlayer.getRoundScore() + ".");
			if (nextPlayer.getRoundScore() > winnerScore)
			{
				winner = player;
				winnerScore = nextPlayer.getRoundScore();
			}
		}
		return winner;
	}
	
	public void finalReport(int winnerIndex) {
		userInterface.println("Round winner is " + playerNames[winnerIndex] + " with score of " + players.get(winnerIndex).getRoundScore());
		players.get(winnerIndex).setNumberChips(players.get(winnerIndex).getNumberChips() + kitty);
		userInterface.println("\nRound winner earns " + kitty + ", finishing with " + players.get(winnerIndex).getNumberChips());

		userInterface.println("\nFinal scoreboard for this round:");
		userInterface.println("player name -- round score -- total chips");
		userInterface.println("-----------------------");

		for (int pNumber = 0; pNumber < numberOfPlayers; pNumber++)
		{
			userInterface.println(playerNames[pNumber] + " -- " + players.get(pNumber).roundScore + " -- "
					+ players.get(pNumber).getNumberChips());
		}

		userInterface.println("-----------------------");
	}
	
	public void run()
	{
		userInterface.println("Welcome to Skunk 0.47\n");

		playerRegistration();
		
		// Setup and start game (keep).
		activePlayerIndex = 0;
		activePlayer = players.get(activePlayerIndex);

		// Game itself. 
		userInterface.println("Starting game...\n");
		
		boolean gameNotOver = true;
		
		while (gameNotOver)
		{
			userInterface.println("Next player is " + playerNames[activePlayerIndex] + ".");
			
			boolean wantsToRoll = SkunkRoll.askToRoll();
			SkunkRoll.playerDecidesToRoll(wantsToRoll, activePlayer, skunkDice);
			
			int activePlayerNewScore = activePlayer.getRoundScore() + activePlayer.getTurnScore();
			
			endTurnEvaluation(activePlayerNewScore);
			
			if (activePlayer.getRoundScore() >= 100) {
				gameNotOver = false;
			}

			activePlayerIndex = (activePlayerIndex + 1) % numberOfPlayers;
			activePlayer = players.get(activePlayerIndex);
			activePlayer.setTurnScore(0);
		}
		
		// Last round: everyone but last activePlayer gets another shot		
		userInterface.println("Last turn for all...");

		for (int i = activePlayerIndex, count = 0; count < numberOfPlayers - 1; i = (i++) % numberOfPlayers, count++)
		{
			userInterface.println("Last round for player " + playerNames[activePlayerIndex] + "...");

			boolean wantsToRoll = SkunkRoll.askToRoll();
			SkunkRoll.playerDecidesToRoll(wantsToRoll, activePlayer, skunkDice);
			
			int activePlayerNewScore = activePlayer.getRoundScore() + activePlayer.getTurnScore();
			
			activePlayer.setRoundScore(activePlayerNewScore);
			
			userInterface.println("Last roll of " + skunkDice.toString() 
			+ ", giving final round score of " + activePlayer.getRollScore());
			
			activePlayer.setTurnScore(0);
		}
		
		// final report.

		int winner = finalComparisonGetWinner();
		
		finalReport(winner); 
	}

	private int getDie2Roll() {
		return skunkDice.getDie2().getLastRoll();
	}

	private int getDie1Roll() {
		return skunkDice.getDie1().getLastRoll();
	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
