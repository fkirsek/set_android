package hr.math.set;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class MultiResultsActivity extends Activity {
	
	private int numPlayers;
	private String[] results;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_multi_results);
		
		Bundle bundle = getIntent().getExtras();
		
		numPlayers = bundle.getInt("numPlayers");
		results = bundle.getStringArray("results");
		
		if (numPlayers < 4) {
			findViewById(R.id.results2).setVisibility(View.GONE);
		}
		if (numPlayers < 3) {
			findViewById(R.id.results3).setVisibility(View.GONE);
		}
		
		for (int i = 0; i < numPlayers; i++) {
			Log.d("TAG", "" + results[i]);
			int id = getResources().getIdentifier("tvSetsPlayer" + i, "id", "hr.math.set");
			if (id == 0) {
				Log.d("TAG", "nula = " + i);
			}
			((TextView) findViewById(id)).setText(results[i]);
		}
		
	}
	
	public void resultsOk(View view) {
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.multi_results, menu);
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
