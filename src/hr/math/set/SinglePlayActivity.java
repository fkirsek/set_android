package hr.math.set;

import hr.math.set.logic.Card;
import hr.math.set.logic.SetStatus;
import hr.math.set.logic.Table;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
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
	            
	            SetStatus status = Table.getInstance().selectCard(position);
	            
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
	
	
	public class ImageAdapter extends BaseAdapter {
	    private Context mContext;

	    public ImageAdapter(Context c) {
	        mContext = c;
	    }

	    public int getCount() {	    	
	        return Table.getInstance().size();
	    }

	    public Object getItem(int position) {
	        return Table.getInstance().get(position);
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    // create a new ImageView for each item referenced by the Adapter
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView;
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            imageView = new ImageView(mContext);
	            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            imageView.setPadding(8, 8, 8, 8);
	        } else {
	            imageView = (ImageView) convertView;
	        }
	        
	        Card tempCard = Table.getInstance().get(position);
	        String cardName = tempCard.toString();
	        String packageName = getPackageName();
	        
	        int resId = getResources().getIdentifier(cardName, "drawable", packageName);
	        imageView.setImageResource(resId);
	        
	        if( Table.getInstance().getSelection().contains(tempCard) ){
	        	imageView.setBackgroundColor( Color.rgb(200, 0, 0) );
	        }
	        
	        return imageView;
	        
	        
	    }

	    // references to our images
	    private Integer[] mThumbIds = {
	            R.drawable.c0000, R.drawable.c0001,
	            R.drawable.c0002, R.drawable.c0010,
	            R.drawable.c0011, R.drawable.c0012,
	            R.drawable.c0020, R.drawable.c0021,
	            R.drawable.c0022,
	            R.drawable.c1000, R.drawable.c1001,
	            R.drawable.c1002, R.drawable.c1010,
	            R.drawable.c1011, R.drawable.c1012,
	            R.drawable.c1020, R.drawable.c1021,
	            R.drawable.c1022
	    };
	}
	
}


