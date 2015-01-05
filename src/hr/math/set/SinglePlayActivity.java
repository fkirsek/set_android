package hr.math.set;

import hr.math.set.logic.Card;
import hr.math.set.logic.SetStatus;
import hr.math.set.logic.Table;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class SinglePlayActivity extends Activity {

	GridView gridview;
	ImageAdapter adapter;
	Table table;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// LinearLayout tableLayout = (LinearLayout) View.inflate(this,
		// R.layout.activity_single_play,
		// null);

		table = Table.getInstance();

		setContentView(R.layout.activity_single_play);

		gridview = (GridView) findViewById(R.id.gridview);
		adapter = new ImageAdapter(this);
		gridview.setAdapter(adapter);

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Toast.makeText(SinglePlayActivity.this, table.get(position).toString(),
						Toast.LENGTH_SHORT).show();

				SetStatus status = table.selectCard(position);
				if (status == SetStatus.SET_OK) {
					table.removeSelected();
					if (table.size() < 12) {
						table.drawNext3();
					}
					if (!table.ensureSet()) {
						Toast.makeText(SinglePlayActivity.this, "Kraj partije", Toast.LENGTH_SHORT)
								.show();
					}
					table.clearSelection();
					((Button) findViewById(R.id.btnHint)).setEnabled(true);
				} else if (status == SetStatus.SET_FAIL) {
					table.clearSelection();
				}

				adapter.notifyDataSetChanged();

			}
		});
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

	public void finishGame(View v) {
		Toast.makeText(SinglePlayActivity.this, "finished the game", Toast.LENGTH_SHORT).show();
		finish();
	}

	public void hint(View v) {
		Table.getInstance().hint();
		// TODO uncomment in production ((Button)
		// findViewById(R.id.btnHint)).setEnabled(false);
		adapter.notifyDataSetChanged();
	}

	public void set(View v) {
		Table.getInstance().set();
		adapter.notifyDataSetChanged();
	}

	public class ImageAdapter extends BaseAdapter {
		private Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return table.size();
		}

		public Object getItem(int position) {
			return table.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		// create a new ImageView for each item referenced by the Adapter
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) { // if it's not recycled, initialize some
										// attributes
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(8, 8, 8, 8);
			} else {
				imageView = (ImageView) convertView;
			}

			Card tempCard = table.get(position);
			String cardName = tempCard.toString();
			String packageName = getPackageName();

			int resId = getResources().getIdentifier(cardName, "drawable", packageName);
			imageView.setImageResource(resId);

			if (table.getSelection().contains(tempCard)) {
				imageView.setBackgroundColor(Color.rgb(200, 0, 0));
			} else {
				imageView.setBackgroundColor(Color.alpha(0));
			}

			return imageView;

		}

		// references to our images
		/*
		 * private Integer[] mThumbIds = { R.drawable.c0000, R.drawable.c0001,
		 * R.drawable.c0002, R.drawable.c0010, R.drawable.c0011,
		 * R.drawable.c0012, R.drawable.c0020, R.drawable.c0021,
		 * R.drawable.c0022, R.drawable.c1000, R.drawable.c1001,
		 * R.drawable.c1002, R.drawable.c1010, R.drawable.c1011,
		 * R.drawable.c1012, R.drawable.c1020, R.drawable.c1021,
		 * R.drawable.c1022 };
		 */
	}

}
