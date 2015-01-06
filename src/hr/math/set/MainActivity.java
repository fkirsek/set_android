package hr.math.set;

import hr.math.set.logic.CardDeck;
import hr.math.set.logic.Table;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

		button = (ImageButton) findViewById(R.id.imgButton);
		button.setOnClickListener(imgButtonHandler);
		
		prefs = getSharedPreferences("SET", MODE_PRIVATE);
	}

	View.OnClickListener imgButtonHandler = new View.OnClickListener() {
		public void onClick(View v) {
			button.setImageResource(R.drawable.ic_launcher);
		}
	};
	
	@Override
    public void onResume() {
            super.onResume();
            if (Table.getInstance().size() == 0) {
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
		Table.getInstance().initializeTable();
		CardDeck.getInstance().setReshuffle(prefs.getBoolean("cardDeckReshuffle", false));
		this.startActivity(intent);
	}

	public void rsmSinglePlay(View view) {
		Intent intent = new Intent(MainActivity.this, SinglePlayActivity.class);
		CardDeck.getInstance().setReshuffle(prefs.getBoolean("cardDeckReshuffle", false));
		this.startActivity(intent);
	}
	
	public void newMultiPlay(View view) {
		Intent intent = new Intent(MainActivity.this, MultiPlayActivity.class);
		Table.getInstance().initializeTable();
		CardDeck.getInstance().setReshuffle(prefs.getBoolean("cardDeckReshuffle", false));
		this.startActivity(intent);		
	}

	public void settings(View view) {
		Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
		this.startActivity(intent);
	}

}
