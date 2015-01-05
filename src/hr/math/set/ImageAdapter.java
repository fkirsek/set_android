package hr.math.set;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
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

        imageView.setImageResource(mThumbIds[position]);
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