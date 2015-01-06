package hr.math.set;

import hr.math.set.logic.Table;
import hr.math.set.util.Stopwatch;

//this class is used to store information used by the SinglePlayerActivity even when the activity itself finishes, ie. to allow the activity to resume

public class SinglePlayerObjects {
	static Stopwatch stopwatch = null;
	static Table table = null;

	// private constructor to deny initializing an object of this type
	private SinglePlayerObjects() {
	}

	// creates new instances of stopwatch and play, so that a new play session
	// may begin
	// the argument is the setting whether the game will reshuffle the deck once
	// all sets have been found, or not
	public static void init(boolean reshuf) {
		stopwatch = new Stopwatch();
		table = new Table(reshuf);
	}

	// clears the initialized objects, happens when the single player game ends
	public static void clear() {
		stopwatch = null;
		table = null;
	}
}
