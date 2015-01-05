package hr.math.set;

import hr.math.set.logic.Table;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SinglePlayActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// LinearLayout tableLayout = (LinearLayout) View.inflate(this, R.layout.activity_single_play,
		//		null);

		Table table = Table.getInstance();
		
		setContentView( R.layout.activity_single_play);

	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter(this));

	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            Toast.makeText(SinglePlayActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	        }
	    });

		/*
		// Test
		for (int i = 0; i < table.size() / 3; i++) {
			LinearLayout tableRow = (LinearLayout) View.inflate(this, R.layout.table_row, null);//findViewById(rows[i]);
			if (i % 2 == 1) {
				tableRow.setBackgroundColor(getResources().getColor(android.R.color.black));
			}
			Log.d("TAG", "row" + i);
			for (int j = 0; j < 3; j++) {
				Log.d("TAG", "col" + j);
				
				if (tableRow == null) {
					Log.d("TAG", "null");
				}
				
				ImageButton imgBtn = (ImageButton) View
						.inflate(this, R.layout.simple_img_btn, null);
				imgBtn.setImageResource(R.drawable.c0000);
				if (j % 2 == 0) {
					imgBtn.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));
				}
				tableRow.addView(imgBtn);
			}
			tableLayout.addView(tableRow);
		}
		*/
		
		
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
