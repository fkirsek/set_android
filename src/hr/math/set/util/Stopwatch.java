package hr.math.set.util;

import android.widget.Chronometer;
import android.os.SystemClock;

public class Stopwatch {
	static private int elapsedTime;
	static private long startTime;
	static public Boolean started;
	
	static{
		started = false;
	}
	
	//returns a "real" starting time, so that the penalty seconds, as well as time played before pausing, adds up
	public static long getWhenToStart(){
		return startTime - elapsedTime;
	}
	
	public static long getElapsedTime(){
		pause(); //this only updates the elapsed time
		return elapsedTime;
	}
	
	public static void init(){
		//if the game wasn't initialized, initialize it now
		if(started == false){
			started = true;
			elapsedTime = 0;	
			//test, add 100 seconds penalty for testing
			addPenaltyTime(100);
		}
		//either way, set the start timer to the current time;
		startTime = SystemClock.elapsedRealtime();

	}
	//adds a penalty time when the player requests more cards, even with an existing set, or uses a hint
	public static void addPenaltyTime(int penalty){
		elapsedTime += penalty*1000;
	}
	//reset the time, used when the activity is stopped
	public static void reset(){
		started = false;
		elapsedTime = 0;
	}
	
	public static void pause(){
		elapsedTime += SystemClock.elapsedRealtime() - startTime;
	}

}
