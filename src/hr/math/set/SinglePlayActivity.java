package hr.math.set;

import hr.math.set.logic.Card;
import hr.math.set.logic.Table;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SinglePlayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LinearLayout tableLayout = (LinearLayout) View.inflate(this, R.layout.activity_single_play,
				null);

		Table table = Table.getInstance();
		List<Button> btns = new ArrayList<Button>();

		// Test
		for (int i = 0; i < 3; i++) {
			LinearLayout tableRow = new LinearLayout(this);
			tableRow.setOrientation(LinearLayout.HORIZONTAL);
			for (int j = 0; j < table.size()/3; j++) {
				Card card = table.get(i * 3 + j);
				Button tmpButton = (Button) View.inflate(this, R.layout.simple_btn, null);
				tmpButton.setText(card.toString());
				tableRow.addView(tmpButton);
			}
			tableLayout.addView(tableRow);
		}

		setContentView(tableLayout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_play, menu);
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
