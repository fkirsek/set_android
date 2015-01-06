package hr.math.set;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class StatisticsActivity extends Activity {

	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);
		
		prefs = getSharedPreferences("SET", MODE_PRIVATE);
		
		String highScoreDeck = prefs.getString("highScoreDeck", "Nan");
		((TextView) findViewById(R.id.tvHighScoreDeck)).setText(highScoreDeck);
		

		String highScoreDeckName = prefs.getString("highScoreDeckName", "NN");
		((TextView) findViewById(R.id.tvHighScoreDeckName)).setText(highScoreDeckName);
		

		String lastScoreDeck = prefs.getString("lastScoreDeck", "Nan");
		((TextView) findViewById(R.id.tvLastScoreDeck)).setText(lastScoreDeck);
		
		String lastScoreDeckName = prefs.getString("lastScoreDeckName", "NN");
		((TextView) findViewById(R.id.tvLastScoreDeckName)).setText(lastScoreDeckName);	

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.statistics, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
