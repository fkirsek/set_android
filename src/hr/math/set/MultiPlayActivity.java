package hr.math.set;

import hr.math.set.logic.SetStatus;
import hr.math.set.logic.Table;
import hr.math.set.util.ImageAdapter;

import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class MultiPlayActivity extends Activity {

	private int numPlayers;
	private GridView gridview;
	private ImageAdapter adapter;
	private Table table;
	private int playerOnMove;
	private int[] scores;
	private int timeout;

	private int[] playerId = new int[] { R.id.button0, R.id.button1, R.id.button2, R.id.button3 };
	private int[] lightColorId = new int[] { R.color.lightBlue, R.color.lightRed,
			R.color.lightGreen, R.color.lightYellow };
	private int[] darkColorId = new int[] { R.color.darkBlue, R.color.darkRed, R.color.darkGreen,
			R.color.darkYellow };

	private CountDownTimer countDownTimer = null;
	TextView countDownTimerField = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multi_play);

		// a variable that memorizes the location of the countdown timer
		countDownTimerField = (TextView) findViewById(R.id.countdown);

		// reading variables from MultiPlayerObjects
		table = MultiPlayerObjects.table;
		numPlayers = MultiPlayerObjects.numPlayers;
		scores = MultiPlayerObjects.scores;
		timeout = MultiPlayerObjects.timeout;

		// update the player scores
		for (int i = 0; i < 4; i++) {
			playerOnMove = i;
			updateScoreDisplay();
		}

		if (numPlayers < 4) {
			findViewById(playerId[3]).setVisibility(View.GONE);
			findViewById(R.id.setsPlayer3).setVisibility(View.GONE);
		}
		if (numPlayers < 3) {
			findViewById(playerId[2]).setVisibility(View.GONE);
			findViewById(R.id.setsPlayer2).setVisibility(View.GONE);
		}

		// autofit width
		int width = getBaseContext().getResources().getDisplayMetrics().widthPixels - (int) convertDpToPixel(110, this);
		// 50dpi sa svake strane (za javljanje igraca), 5 spacing
		width = (int)(width/ 4);
		//gridview.setColumnWidth((int)(width - convertDpToPixel(100, this) / 4 - 5));
		
		gridview = (GridView) findViewById(R.id.gridview);
		adapter = new ImageAdapter(this, table, width);
		gridview.setAdapter(adapter);
		


		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				SetStatus status = table.selectCardAndCheck(position);

				adapter.notifyDataSetChanged();
				if (status == SetStatus.GAME_DONE) {
					endGame();
				} else if (status == SetStatus.SET_OK || status == SetStatus.SET_FAIL) {
					countDownTimerField.setVisibility(View.INVISIBLE);
					countDownTimer.cancel(); // stop the countdowntimer and make
												// the timer invisible
					processPlayerSelection(status);
				}
			}
		});

		gridview.setEnabled(false);
	}
	
	public static float convertDpToPixel(float dp, Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float px = dp * (metrics.densityDpi / 160f);
	    return px;
	}

	// updates the score of the player that was last playing
	private void updateScoreDisplay() {
		int textViewId = getResources().getIdentifier("setsPlayer" + playerOnMove, "id",
				"hr.math.set");
		TextView setsPlayer = (TextView) findViewById(textViewId);
		setsPlayer.setText(String.valueOf(scores[playerOnMove]));
	}

	// a new function that processes the result of the selection the player made
	// also used to automatically fail a player when the time runs outs
	private void processPlayerSelection(SetStatus status) {
		if (status == SetStatus.SET_OK) {
			scores[playerOnMove]++;
		} else {
			scores[playerOnMove]--;
		}
		
		updateScoreDisplay();

		((Button) findViewById(playerId[playerOnMove])).setBackgroundColor(getResources().getColor(
				darkColorId[playerOnMove]));
		table.clearSelection();
		gridview.setEnabled(false);
		setClickableMultiplayerButtons(true);
		
		adapter.notifyDataSetChanged();
	}

	public void playerMove(View v) {
		playerOnMove = Integer.parseInt(v.getTag().toString());

		gridview.setEnabled(true);

		setClickableMultiplayerButtons(false);

		((Button) findViewById(playerId[playerOnMove])).setBackgroundColor(getResources().getColor(
				lightColorId[playerOnMove]));

		MediaPlayer mp = MediaPlayer.create(MultiPlayActivity.this, R.raw.set);
		mp.start();

		countDownTimerField.setVisibility(View.VISIBLE);
		countDownTimerField.setText("10");
		// countdown timer
		countDownTimer = new CountDownTimer(timeout, 1000) {
			public void onTick(long millisUntilFinished) {
				countDownTimerField.setText("" + millisUntilFinished / 1000);
			}

			public void onFinish() {
				countDownTimerField.setVisibility(View.INVISIBLE); // TODO mozda
																	// ubaciti u
																	// processPlayerSelection
				processPlayerSelection(SetStatus.SET_FAIL); // fail the player
															// for running out
															// of time
			}
		}.start();
	}

	private void setClickableMultiplayerButtons(boolean clickable) {
		for (int i = 0; i < numPlayers; i++) {
			((Button) findViewById(playerId[i])).setClickable(clickable);
		}
	}

	public void endGame() {
		Intent resultAct = new Intent(this, MultiResultsActivity.class);
		resultAct
				.putExtra(
						"results",
						(String[]) Arrays.asList(
								((TextView) findViewById(R.id.setsPlayer0)).getText().toString(),
								((TextView) findViewById(R.id.setsPlayer1)).getText().toString(),
								((TextView) findViewById(R.id.setsPlayer2)).getText().toString(),
								((TextView) findViewById(R.id.setsPlayer3)).getText().toString())
								.toArray());
		resultAct.putExtra("numPlayers", numPlayers);
		startActivity(resultAct);

		MultiPlayerObjects.clear();
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.multi_play, menu);
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

	public void hint(View v) {
		table.hint();
		adapter.notifyDataSetChanged();
	}

	public void set(View v) {
		table.clearSelection();
		table.set();
		adapter.notifyDataSetChanged();
	}

	public void clickTest(View v) {
		Toast.makeText(this, "a", Toast.LENGTH_SHORT).show();
	}

}
