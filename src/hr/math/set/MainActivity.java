package hr.math.set;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
	Typeface custom_font;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		prefs = getSharedPreferences("SET", MODE_PRIVATE);

		custom_font = Typeface.createFromAsset(getAssets(), "fonts/Drawing Guides.ttf");

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
		getMenuInflater().inflate(R.menu.main, menu);
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

	public void newSinglePlay(View view) {
		if (((Button) findViewById(R.id.btnResumeGame)).getVisibility() == View.VISIBLE) {
			// Resume button is visible -> ask user if he really wants to start
			// new game
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setTitle("Are you sure?");
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					startNewSinglePlay();
				}
			});
			builder.setNegativeButton("No", null);
			builder.create().show();
		} else {
			// Resume button is not visible -> start new game immediately
			startNewSinglePlay();
		}
	}

	private void startNewSinglePlay() {
		Intent intent = new Intent(MainActivity.this, SinglePlayActivity.class);
		SinglePlayerObjects.init(prefs.getBoolean("cardDeckReshuffle", false));
		MainActivity.this.startActivity(intent);
	}

	public void rsmSinglePlay(View view) {
		Intent intent = new Intent(MainActivity.this, SinglePlayActivity.class);
		this.startActivity(intent);
	}

	public void newMultiPlay(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Pick number of players").setItems(R.array.num_players,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// The 'which' argument contains the index position
						// of the selected item
						Intent intent = new Intent(MainActivity.this, MultiPlayActivity.class);
						MultiPlayerObjects.init(prefs.getBoolean("cardDeckReshuffle", false),
								which + 2, prefs.getInt("timeoutSeconds", 10));
						MainActivity.this.startActivity(intent);
					}
				});

		builder.create().show();
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

}
