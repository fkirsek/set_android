package hr.math.set;

import hr.math.set.logic.SetStatus;
import hr.math.set.logic.Table;
import hr.math.set.util.ImageAdapter;

import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class MultiPlayActivity extends Activity {

	private int numPlayers;
	private GridView gridview;
	private ImageAdapter adapter;
	private Table table;
	private int playerOnMove;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multi_play);

		table = MultiPlayerObjects.table;
		numPlayers = MultiPlayerObjects.numPlayers;

		if (numPlayers < 4) {
			findViewById(R.id.imageView3).setVisibility(View.GONE);
			findViewById(R.id.setsPlayer3).setVisibility(View.GONE);
		}
		if (numPlayers < 3) {
			findViewById(R.id.imageView2).setVisibility(View.GONE);
			findViewById(R.id.setsPlayer2).setVisibility(View.GONE);
		}

		gridview = (GridView) findViewById(R.id.gridview);
		adapter = new ImageAdapter(this, table);
		gridview.setAdapter(adapter);

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				SetStatus status = table.selectCardAndCheck(position);

				adapter.notifyDataSetChanged();
				if (status == SetStatus.GAME_DONE) {
					endGame();
				} else if (status == SetStatus.SET_OK || status == SetStatus.SET_FAIL) {
					int textViewId = getResources().getIdentifier("setsPlayer" + playerOnMove,
							"id", "hr.math.set");
					TextView setsPlayer = (TextView) findViewById(textViewId);

					int tmpNumSets = Integer.parseInt(setsPlayer.getText().toString());

					if (status == SetStatus.SET_OK) {
						setsPlayer.setText(String.valueOf(tmpNumSets + 1));
					} else {
						setsPlayer.setText(String.valueOf(tmpNumSets - 1));
					}

					gridview.setEnabled(false);
				}
			}
		});

		gridview.setEnabled(false);

	}

	public void playerMove(View v) {
		playerOnMove = Integer.parseInt(v.getTag().toString());
		Toast.makeText(this, v.getTag().toString(), Toast.LENGTH_SHORT).show();
		gridview.setEnabled(true);
	}

	public void endGame() {

		Intent resultAct = new Intent(this, MultiResultsActivity.class);
		resultAct.putExtra("results", (String[]) Arrays.asList(
				((TextView)findViewById(R.id.setsPlayer0)).getText().toString(),
				((TextView)findViewById(R.id.setsPlayer1)).getText().toString(),
				((TextView)findViewById(R.id.setsPlayer2)).getText().toString(),
				((TextView)findViewById(R.id.setsPlayer3)).getText().toString()).toArray());
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
