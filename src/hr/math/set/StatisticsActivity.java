package hr.math.set;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

public class StatisticsActivity extends Activity {

	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_statistics);

		getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);

		/*
		 * Font
		 */
		Typeface custom_font = Typeface.createFromAsset(getAssets(),
				"fonts/KGSecondChancesSketch.ttf");

		((TextView) findViewById(R.id.tvHighScore)).setTypeface(custom_font);
		((TextView) findViewById(R.id.tvLastScore)).setTypeface(custom_font);

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
		getMenuInflater().inflate(R.menu.statistics, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
