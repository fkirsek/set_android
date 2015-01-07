package hr.math.set;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SettingsActivity extends Activity {

	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;

	private EditText etPlayerName;
	private ToggleButton toggleButton;
	private RadioGroup radioGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		prefs = getSharedPreferences("SET", MODE_PRIVATE);
		editor = prefs.edit();

		/*
		 * Player name.
		 */
		etPlayerName = (EditText) findViewById(R.id.etPlayerName);
		etPlayerName.setText(prefs.getString("playerName", ""));

		etPlayerName.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				String tmpPlayerName = etPlayerName.getText().toString();
				if (tmpPlayerName.length() != 0) {
					editor.putString("playerName", tmpPlayerName);
					editor.commit();
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});

		/*
		 * Reshuffle card deck settings.
		 */
		toggleButton = (ToggleButton) findViewById(R.id.tbReshuffling);
		toggleButton.setChecked(prefs.getBoolean("cardDeckReshuffle", false));

		toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				editor.putBoolean("cardDeckReshuffle", isChecked);
				editor.commit();
			}
		});

		/*
		 * Number of players settings.
		 */
		radioGroup = (RadioGroup) findViewById(R.id.rgNumPlayers);
		int tmpNumPlayers = prefs.getInt("numPlayers", 2);
		radioGroup.check(getResources().getIdentifier("radioPlayers" + tmpNumPlayers, "id",
				"hr.math.set"));

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int newNumPlayers = Integer.parseInt(((RadioButton) findViewById(checkedId))
						.getText().toString());
				editor.putInt("numPlayers", newNumPlayers);
				editor.commit();
				Toast.makeText(getBaseContext(), String.valueOf(prefs.getInt("numPlayers", -100)),
						Toast.LENGTH_SHORT).show();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.settings, menu);
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
