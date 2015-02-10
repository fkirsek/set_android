package hr.math.set;

import hr.math.set.logic.SetStatus;
import hr.math.set.logic.Table;
import hr.math.set.util.ImageAdapter;
import hr.math.set.util.Stopwatch;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.TextView;

public class SinglePlayActivity extends Activity {

	// buttons, views, adapters
	private GridView gridview;
	private ImageAdapter adapter;
	private Chronometer chronometer;
	private TextView scoreBox;
	private Button draw3;

	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;

	// these three objects are pulled from the SinglePlayerObjects class
	Table table;
	Stopwatch stopwatch;
	// a field so I can work with references, ie. avoid packing/unpacking
	// objects etc.
	private int[] score;

	protected void onPause() {
		super.onPause();
		stopwatch.pause();
		chronometer.stop();
	}

	protected void onResume() {
		super.onResume();
		// setting up the clock
		stopwatch.resume();
		chronometer = (Chronometer) findViewById(R.id.chronometer);
		chronometer.setBase(stopwatch.getWhenToStart());
		chronometer.start();
	}
	
	public static float convertDpToPixel(float dp, Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float px = dp * (metrics.densityDpi / 160f);
	    return px;
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_play);

		Typeface custom_font = Typeface.createFromAsset(getAssets(),
				"fonts/KGSecondChancesSketch.ttf");

		((Button) findViewById(R.id.btnHint)).setTypeface(custom_font);
		((Button) findViewById(R.id.btnNext3)).setTypeface(custom_font);

		prefs = getSharedPreferences("SET", MODE_PRIVATE);
		editor = prefs.edit();

		table = SinglePlayerObjects.table;
		stopwatch = SinglePlayerObjects.stopwatch;
		score = SinglePlayerObjects.score;

		draw3 = (Button) findViewById(R.id.btnNext3);

		// if the reshuffle is set, don't show the chronometer
		if (SinglePlayerObjects.reshuf) {
			chronometer = (Chronometer) findViewById(R.id.chronometer);
			chronometer.setVisibility(View.GONE);
		}

		// set up scorebox
		scoreBox = (TextView) findViewById(R.id.scoreBox);
		scoreBox.setText(Integer.toString(score[0]));

		
		// autofit width
		
		int width = getBaseContext().getResources().getDisplayMetrics().widthPixels - (int) convertDpToPixel(40, this);
		width = (int)(width/4);
		//gridview.setColumnWidth(width/4 - 10);
		
		// setting up the grid view
		gridview = (GridView) findViewById(R.id.gridview);
		adapter = new ImageAdapter(this, table, width);
		gridview.setAdapter(adapter);
		
		

		
		
		if (table.size() == 12) {
			draw3.setEnabled(true);
		} else {
			draw3.setEnabled(false);
		}

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

				SetStatus status = table.selectCardAndCheck(position);

				adapter.notifyDataSetChanged();
				if (status == SetStatus.GAME_DONE) {
					endGame();
				} else if (status == SetStatus.SET_OK || status == SetStatus.SET_FAIL) {
					((Button) findViewById(R.id.btnHint)).setEnabled(true);
				}

				if (table.size() == 12 && table.canDrawNext3()) {
					draw3.setEnabled(true);
				} else {
					draw3.setEnabled(false);
				}

				if (status == SetStatus.SET_OK) {
					// if a set was found...
					score[0]++;
					scoreBox.setText(Integer.toString(score[0]));
				}
			}
		});
	}

	public void endGame() {
		// Toast.makeText(SinglePlayActivity.this, "Kraj partije",
		// Toast.LENGTH_SHORT).show();
		SinglePlayerObjects.clear(); // clear the objects from the
										// SinglePlayerObjects class

		long milisec = stopwatch.getElapsedTime();
		String lastScore = String.format(Locale.getDefault(), "%02d:%02d.%03d",
				TimeUnit.MILLISECONDS.toMinutes(milisec),
				TimeUnit.MILLISECONDS.toSeconds(milisec) % 60,
				TimeUnit.MILLISECONDS.toMillis(milisec) % 1000);

		editor.putString("lastScoreDeck", lastScore);
		editor.commit();

		Intent resultAct = new Intent(this, SingleResultsActivity.class);
		startActivity(resultAct);

		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.single_play, menu);
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

	public void exit(View v) {
		finish();
	}

	public void hint(View v) {
		table.hint();
		((Button) findViewById(R.id.btnHint)).setEnabled(false);

		// add the penalty time, update the total time spent, and set the
		// chronometer to that time
		addPenaltyTime();

		adapter.notifyDataSetChanged();
	}

	public void drawNext3(View v) {
		table.drawNext3();
		adapter.notifyDataSetChanged();
		draw3.setEnabled(false);

		// add penalty time for hitting next only if there was a set beforehand
		if (table.existsSet())
			addPenaltyTime();
	}

	private void addPenaltyTime() {
		stopwatch.addPenaltyTime(10);
		stopwatch.pause();
		stopwatch.resume();
		chronometer = (Chronometer) findViewById(R.id.chronometer);
		chronometer.setBase(stopwatch.getWhenToStart());
		chronometer.start();
	}

	public void set(View v) {
		table.clearSelection();
		table.set();
		adapter.notifyDataSetChanged();
	}

}
