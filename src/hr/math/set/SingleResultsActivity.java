package hr.math.set;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SingleResultsActivity extends Activity {

	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;

	private EditText playerName;
	private TextView playerScore;
	private TextView newHighScore;
	
	private String lastScoreDeck;
	private boolean isHighScore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_results);

		prefs = getSharedPreferences("SET", MODE_PRIVATE);

		playerName = (EditText) findViewById(R.id.etPlayerName);
		playerScore = (TextView) findViewById(R.id.tvPlayerScore);
		newHighScore = (TextView) findViewById(R.id.tvNewHighScore);
		
		lastScoreDeck = prefs.getString("lastScoreDeck", "99:99.999");  // it should never be 99:99.999
		isHighScore = isNewHighScore(lastScoreDeck);

		playerName.setText(prefs.getString("lastScoreDeckName", ""));
		playerScore.setText(lastScoreDeck);

		if (isHighScore) {
			newHighScore.setVisibility(View.VISIBLE);
			newHighScore.setText("NEW HIGH SCORE"); // TODO i18n
		} else {
			newHighScore.setVisibility(View.GONE);
		}

	}

	private boolean isNewHighScore(String lastScoreDeck) {
		String highScoreDeck = prefs.getString("highScoreDeck", "");

		if (lastScoreDeck.isEmpty() || lastScoreDeck.compareTo(highScoreDeck) < 0) {
			return true;
		} else {
			return false;
		}
	}

	public void saveResult(View view) {
		String enteredPlayerName = playerName.getText().toString();
		if (enteredPlayerName.isEmpty()) {
			playerName.requestFocus();
			playerName.setHint("Enter your name"); // TODO i18n
			return;
		}
		
		editor = prefs.edit();
		editor.putString("lastScoreDeckName", enteredPlayerName);
		editor.putString("lastScoreDeck", lastScoreDeck);
		
		if (isHighScore) {
			editor.putString("highScoreDeckName", enteredPlayerName);
			editor.putString("highScoreDeck", lastScoreDeck);
		}
		editor.commit();
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.single_results, menu);
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
