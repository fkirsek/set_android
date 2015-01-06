package hr.math.set;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SettingsActivity extends Activity {

	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		prefs = getSharedPreferences("SET", MODE_PRIVATE);
		editor = prefs.edit();

		/*
		 * Reshuffle card deck settings.
		 */
		CheckBox shuffleCheck = (CheckBox) findViewById(R.id.shuffleCheck);
		shuffleCheck.setChecked(prefs.getBoolean("cardDeckReshuffle", false));

		shuffleCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				editor.putBoolean("cardDeckReshuffle", isChecked);
				editor.commit();
			}
		});

		/*
		 * Number of players settings.
		 */
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgNumPlayers);
		int tmpNumPlayers = prefs.getInt("numPlayers", 2);
		radioGroup.check(getResources().getIdentifier("radioPlayers" + tmpNumPlayers, "id",
				"hr.math.set"));

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int newNumPlayers = Integer.parseInt(((RadioButton) findViewById(checkedId)).getText().toString());
				editor.putInt("numPlayers", newNumPlayers);
				editor.commit();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
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
