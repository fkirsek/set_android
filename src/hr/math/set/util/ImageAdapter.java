package hr.math.set.util;

import hr.math.set.logic.Card;
import hr.math.set.logic.Table;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	private Context mContext;
	private Table table;

	public ImageAdapter(Context c, Table t) {
		table = t;
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
			imageView.setLayoutParams(new GridView.LayoutParams(85,135)); //-2 is wrap content
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(1, 1, 1, 1);
		} else {
			imageView = (ImageView) convertView;
		}

		Card tempCard = table.get(position);
		String cardName = tempCard.toString();
		String packageName = mContext.getPackageName();

		int resId = mContext.getResources().getIdentifier(cardName, "drawable", packageName);
		imageView.setImageResource(resId);

		if (table.getSelection().contains(tempCard)) {
			imageView.setBackgroundColor(Color.rgb(200, 0, 0));
		} else {
			imageView.setBackgroundColor(Color.alpha(0));
		}

		return imageView;

	}

}
