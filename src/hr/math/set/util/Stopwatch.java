package hr.math.set.util;

import android.widget.Chronometer;
import android.os.SystemClock;

public class Stopwatch {
	static private int penaltyTime;
	static private Chronometer chronometer;
	static private long startTime;
	public void init(){
		//if the game wasn't initialized, initialize it now
		if(chronometer == null){
			
			startTime = SystemClock.elapsedRealtime();
			penaltyTime = 0;
			chronometer = new Chronometer(null);
			
			//test
			addPenaltyTime(100);
		}
	}
	//adds a penalty time when the player requests more cards, even with an existing set, or uses a hint
	public void addPenaltyTime(int penalty){
		penaltyTime += penalty;
		chronometer.setBase(SystemClock.elapsedRealtime() + penaltyTime*1000);
	}
	//reset the chronometer
	public void reset(){
		penaltyTime = 0;
		chronometer = null;
	}

}
