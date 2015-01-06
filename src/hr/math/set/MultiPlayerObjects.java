package hr.math.set;

import hr.math.set.logic.Table;

//this class is used to store information used by the MultiPlayerActivity even when the activity itself finishes, ie. to allow the activity to resume

public class MultiPlayerObjects {
	static Table table = null;
	static int numPlayers = 2;

	// private constructor to deny initializing an object of this type
	private MultiPlayerObjects() {
	}

	// creates new instances of stopwatch and play, so that a new play session
	// may begin
	public static void init(boolean reshuf, int nPl) {
		numPlayers = nPl;
		table = new Table(reshuf);
	}

	// clears the initialized objects, happens when the single player game ends
	public static void clear() {
		table = null;
	}
}