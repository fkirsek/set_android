package hr.math.set.util;

import android.os.SystemClock;

public class Stopwatch {
	private int elapsedTime;
	private long startTime;

	// constructor
	public Stopwatch() {
		init();
	}

	// initializes the counter
	public void init() {
		elapsedTime = 0;
		startTime = SystemClock.elapsedRealtime();
	}
	
	//initializes just the startTime variable, used when the game is resumed
	public void resume(){
		startTime = SystemClock.elapsedRealtime();
	}

	// returns a "real" starting time, so that the penalty seconds, as well as
	// time played before pausing, adds up
	public long getWhenToStart() {
		return startTime - elapsedTime;
	}

	public long getElapsedTime() {
		pause(); // this only updates the elapsed time
		return elapsedTime;
	}

	// adds a penalty time when the player requests more cards, even with an
	// existing set, or uses a hint
	public void addPenaltyTime(int penalty) {
		elapsedTime += penalty * 1000;
	}

	// reset the time, used when the activity is stopped
	public void reset() {
		elapsedTime = 0;
	}

	public void pause() {
		elapsedTime += SystemClock.elapsedRealtime() - startTime;
	}

}
