package skunk;

import java.util.ArrayList;

public class SkunkBadEvents {
	public SkunkUI skunkUI;
	public UI userInterface;
	
	public SkunkBadEvents(SkunkUI ui)
	{
		this.skunkUI = ui;
		this.userInterface = ui; // hide behind the interface UI
	}
	
	private void skunkEventUniversal(String skunkMessageInput, int penaltyInput, Player currentPlayer) {
		userInterface.println(skunkMessageInput);
		SkunkDomain.kitty += penaltyInput;
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
	
	public boolean skunkEventCheck(Player activePlayer, Dice skunkDiceInput) {
		// check if skunkDice has been rolled? and there's an activePlayer.
		// Here, it works since skunkEventCheck is called after they are initialized,
		// but may not be when called elsewhere.
		if (skunkDiceInput.getLastRoll() == 2)
		{
			doubleSkunk(activePlayer);
			activePlayer.setRoundScore(0);
			return true;
		}
		else if (skunkDiceInput.getLastRoll() == 3)
		{
			singleSkunkDeuce(activePlayer);
			return true;
		}
		else if (getDie1Roll(skunkDiceInput) == 1 || getDie2Roll(skunkDiceInput) == 1)
		{
			singleSkunk(activePlayer);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private int getDie2Roll(Dice skunkDiceInput) {
		return skunkDiceInput.getDie2().getLastRoll();
	}

	private int getDie1Roll (Dice skunkDiceInput) {
		return skunkDiceInput.getDie1().getLastRoll();
	}
	
}
