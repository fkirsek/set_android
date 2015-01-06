package hr.math.set;

import hr.math.set.logic.SetStatus;
import hr.math.set.logic.Table;
import hr.math.set.util.ImageAdapter;
import hr.math.set.util.Stopwatch;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.Toast;

public class SinglePlayActivity extends Activity {

	GridView gridview;
	ImageAdapter adapter;
	Table table;
	Chronometer chronometer;

	protected void onPause() {
		super.onPause();
		Stopwatch.pause();
		chronometer.stop();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_play);

		table = Table.getInstance();

		// for testing
		Toast.makeText(SinglePlayActivity.this, Stopwatch.started.toString(), Toast.LENGTH_SHORT)
				.show();

		// setting up the clock
		Stopwatch.init();
		chronometer = (Chronometer) findViewById(R.id.chronometer);
		chronometer.setBase(Stopwatch.getWhenToStart());
		chronometer.start();

		// setting up the grid view
		gridview = (GridView) findViewById(R.id.gridview);
		adapter = new ImageAdapter(this);
		gridview.setAdapter(adapter);

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

				SetStatus status = table.selectCardAndCheck(position);

				adapter.notifyDataSetChanged();
				if (status == SetStatus.GAME_DONE) {
					endGame();
				} else
					((Button) findViewById(R.id.btnHint)).setEnabled(true);
			}
		});
	}

	public void endGame() {
		Toast.makeText(SinglePlayActivity.this, "Kraj partije", Toast.LENGTH_SHORT).show(); 
		Stopwatch.reset();// reset the stopwatch
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
		Toast.makeText(SinglePlayActivity.this, "Exited the game", Toast.LENGTH_SHORT).show(); // probably
																								// superfluous
		finish();
	}

	public void hint(View v) {
		table.hint();
		// TODO uncomment in production ((Button)
		// findViewById(R.id.btnHint)).setEnabled(false);
		adapter.notifyDataSetChanged();
	}

	public void set(View v) {
		table.clearSelection();
		table.set();
		adapter.notifyDataSetChanged();
	}

}
