package skunk;
import java.util.ArrayList;
import edu.princeton.cs.introcs.StdIn;

public class SkunkDomain
{
	public SkunkUI skunkUI;
	public UI userInterface;
	public int numberOfPlayers;
	public String[] playerNames;
	public ArrayList<Player> players;
	public static int kitty;

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
	
	private void skunkEventUniversal(String skunkMessageInput, int penaltyInput, Player currentPlayer) {
		userInterface.println(skunkMessageInput);
		kitty += penaltyInput;
		currentPlayer.setNumberChips(currentPlayer.getNumberChips() - 1);
		currentPlayer.setTurnScore(0);
	}
	
	private void singleSkunk(Player currentPlayer) {
		String skunkMessage = "One Skunk! You lose the turn, the turn score, plus pay 1 chip to the kitty";
		int penalty = 1;
		skunkEventUniversal(skunkMessage, penalty, currentPlayer);
	}
	
	private void singleSkunkDeuce(Player currentPlayer) {
		String skunkMessage = "Skunks and Deuce! You lose the turn, the turn score, plus pay 2 chips to the kitty";
		int penalty = 2;
		skunkEventUniversal(skunkMessage, penalty, currentPlayer);
	}
	
	private void doubleSkunk(Player currentPlayer) {
		String skunkMessage = "Two Skunks! You lose the turn, the round score, plus pay 4 chips to the kitty";
		int penalty = 4;
		skunkEventUniversal(skunkMessage, penalty, currentPlayer);
	}
	
	public boolean skunkEventCheck() {
		// check if skunkDice has been rolled? and there's an activePlayer.
		// Here, it works since skunkEventCheck is called after they are initialized,
		// but may not be when called elsewhere.
		if (skunkDice.getLastRoll() == 2)
		{
			doubleSkunk(activePlayer);
			activePlayer.setRoundScore(0);
			return true;
		}
		else if (skunkDice.getLastRoll() == 3)
		{
			singleSkunkDeuce(activePlayer);
			return true;
		}
		else if (getDie1Roll() == 1 || getDie2Roll() == 1)
		{
			singleSkunk(activePlayer);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean askToRoll() {
		String wantsToRollStr = userInterface.promptReadAndReturn("Roll? y or n");
		return 'y' == wantsToRollStr.toLowerCase().charAt(0);
	}
	
	public void resultOfRollSuccess() {
		activePlayer.setRollScore(skunkDice.getLastRoll());
		activePlayer.setTurnScore(activePlayer.getTurnScore() + skunkDice.getLastRoll());
		userInterface.println(
				"Roll of " + skunkDice.toString() + ", gives new turn score of " + activePlayer.getTurnScore());
	}
	
	public void playerDecidesToRoll(boolean wantsToRollInput) {
		boolean internalRollDecision = wantsToRollInput;
		while (internalRollDecision)
		{
			activePlayer.setRollScore(0);
			skunkDice.roll();
			
			if (skunkEventCheck()) {
				internalRollDecision = false;
				break;
			}

			resultOfRollSuccess();
			internalRollDecision = askToRoll();
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
			
			boolean wantsToRoll = askToRoll();
			playerDecidesToRoll(wantsToRoll);
			
			int activePlayerNewScore = activePlayer.getRoundScore() + activePlayer.getTurnScore();
			
			endTurnEvaluation(activePlayerNewScore);
			
			if (activePlayer.getRoundScore() >= 100)
				gameNotOver = false;

			activePlayerIndex = (activePlayerIndex + 1) % numberOfPlayers;
			activePlayer = players.get(activePlayerIndex);
			activePlayer.setTurnScore(0);
		}
		
		// Last round: everyone but last activePlayer gets another shot		
		userInterface.println("Last turn for all...");

		for (int i = activePlayerIndex, count = 0; count < numberOfPlayers - 1; i = (i++) % numberOfPlayers, count++)
		{
			userInterface.println("Last round for player " + playerNames[activePlayerIndex] + "...");

			boolean wantsToRoll = askToRoll();			
			playerDecidesToRoll(wantsToRoll);
			
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
