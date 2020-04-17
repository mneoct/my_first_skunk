package skunk;
import java.util.ArrayList;

public class SkunkDomain
{
	public SkunkUI skunkUI;
	public UI userInterface;
	public static int kitty;
	
	public int numberOfPlayers;
	public String[] playerNames;
	public ArrayList<SkunkPlayer> players;

	public SkunkPlayer activePlayer;
	public int activePlayerIndex;

	public boolean wantsToQuit;
	public boolean oneMoreRoll;

	public Dice skunkDice;

	public SkunkDomain(SkunkUI ui)
	{
		this.skunkUI = ui;
		this.userInterface = ui; // hide behind the interface UI

		this.playerNames = new String[20];
		this.players = new ArrayList<SkunkPlayer>();
		this.skunkDice = new Dice();
		this.wantsToQuit = false;
		this.oneMoreRoll = false;
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
		
	public void gameplay(boolean isFinalRound) {
		activePlayer.setTurnScore(0);
		if (isFinalRound == false) {
			userInterface.println("Next player is " + playerNames[activePlayerIndex] + ".");
		}
		else {
			userInterface.println("Last round for player " + playerNames[activePlayerIndex] + "...");
		}
				
		boolean wantsToRoll = SkunkTurnRoll.askToRoll();
		SkunkTurnRoll.playerDecidesToRoll(wantsToRoll, activePlayer, skunkDice);
		
		int activePlayerNewScore = activePlayer.getRoundScore() + activePlayer.getTurnScore();
		
		endTurnEvaluation(activePlayerNewScore);
	}
	
	public void gameRegular() {
		boolean isFinalTurn = false;
		
		while (!isFinalTurn)
		{
			gameplay(false);
			
			if (activePlayer.getRoundScore() >= 100) {
				isFinalTurn = true;
			}

			activePlayerIndex = (activePlayerIndex + 1) % numberOfPlayers;
			activePlayer = players.get(activePlayerIndex);
		}
	}
	
	public void gameFinalRound() {
		boolean isFinalRound = true;
		for (int i = activePlayerIndex, count = 0; count < numberOfPlayers - 1; i = (i++) % numberOfPlayers, count++)
		{
			gameplay(isFinalRound);	
		}
	}
	
	public void run()
	{
		userInterface.println("Welcome to Skunk 0.47\n");

		SkunkPlayerManager.playerRegistration();
		players = SkunkPlayerManager.players;
		playerNames = SkunkPlayerManager.playerNames;
		numberOfPlayers = SkunkPlayerManager.numberOfPlayers;
		
		activePlayerIndex = 0;
		activePlayer = players.get(activePlayerIndex);

		// Game itself. 
		userInterface.println("Starting game...\n");
		gameRegular();
		
		// Last round: everyone but last activePlayer gets another shot
		
		userInterface.println("Last turn for all...");
		gameFinalRound();
		
		// final report.

		int winner = SkunkEndGame.finalComparisonGetWinner(numberOfPlayers, playerNames, players);
		
		SkunkEndGame.finalReport(winner, playerNames, players, numberOfPlayers); 
	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
