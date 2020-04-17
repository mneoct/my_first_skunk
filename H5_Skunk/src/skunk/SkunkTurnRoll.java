package skunk;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

public class SkunkTurnRoll {
	public static boolean askToRoll() {
		StdOut.print("Roll? y or n => ");
		String wantsToRollStr = StdIn.readLine();
		return 'y' == wantsToRollStr.toLowerCase().charAt(0);
	}
	
	public static void resultOfRollSuccess(SkunkPlayer activePlayer, Dice skunkDice) {
		activePlayer.setRollScore(skunkDice.getLastRoll());
		activePlayer.setTurnScore(activePlayer.getTurnScore() + skunkDice.getLastRoll());
		StdOut.println(
				"Roll of " + skunkDice.toString() + ", gives new turn score of " + activePlayer.getTurnScore());
	}
	
	public static void playerDecidesToRoll(boolean wantsToRollInput, SkunkPlayer activePlayer, Dice skunkDice) {
		boolean internalRollDecision = wantsToRollInput;
		while (internalRollDecision)
		{
			activePlayer.setRollScore(0);
			skunkDice.roll();
			
			if (SkunkTurnBad.skunkEventCheck(activePlayer, skunkDice)) {
				internalRollDecision = false;
				break;
			}

			resultOfRollSuccess(activePlayer, skunkDice);
			internalRollDecision = askToRoll();
		}
	}
}
