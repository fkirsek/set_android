package hr.math.set;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	ImageButton button;
	SharedPreferences prefs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		prefs = getSharedPreferences("SET", MODE_PRIVATE);

		Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Drawing Guides.ttf");

		((Button) findViewById(R.id.btnResumeGame)).setTypeface(custom_font);
		((Button) findViewById(R.id.btnNewGame)).setTypeface(custom_font);
		((Button) findViewById(R.id.btnMultiplayer)).setTypeface(custom_font);
		((Button) findViewById(R.id.btnSettings)).setTypeface(custom_font);
		((Button) findViewById(R.id.btnStats)).setTypeface(custom_font);
		((Button) findViewById(R.id.btnExit)).setTypeface(custom_font);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (SinglePlayerObjects.table == null) {
			((Button) findViewById(R.id.btnResumeGame)).setVisibility(View.GONE);
		} else {
			((Button) findViewById(R.id.btnResumeGame)).setVisibility(View.VISIBLE);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	public void newSinglePlay(View view) {
		Intent intent = new Intent(MainActivity.this, SinglePlayActivity.class);

		// initialize a new game with the boolean value of set reshuffle
		SinglePlayerObjects.init(prefs.getBoolean("cardDeckReshuffle", false));
		this.startActivity(intent);
	}

	public void rsmSinglePlay(View view) {
		Intent intent = new Intent(MainActivity.this, SinglePlayActivity.class);
		this.startActivity(intent);
	}

	public void newMultiPlay(View view) {
		Intent numPlayersIntent = new Intent(MainActivity.this, NumPlayersActivity.class);
		this.startActivityForResult(numPlayersIntent, 1);
	}

	public void settings(View view) {
		Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
		this.startActivity(intent);
	}

	public void statistics(View view) {
		Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
		this.startActivity(intent);
	}

	public void exit(View view) {
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode == RESULT_OK) {
			if (resultCode == RESULT_OK) {
				Intent intent = new Intent(MainActivity.this, MultiPlayActivity.class);
				MultiPlayerObjects.init(prefs.getBoolean("cardDeckReshuffle", false),
						prefs.getInt("numPlayers", 2));
				this.startActivity(intent);
			}
		}
	}

}
