package hr.math.set;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {
	
	ImageButton button;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);

	    button= (ImageButton)findViewById(R.id.imgButton);
	    button.setOnClickListener(imgButtonHandler);
	}


	View.OnClickListener imgButtonHandler = new View.OnClickListener() {

	    public void onClick(View v) {
	        button.setImageResource(R.drawable.ic_launcher);

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
	
	public void singlePlay(View view) {		
		Intent intent = new Intent(MainActivity.this, SinglePlayActivity.class);
		this.startActivity(intent);
		
	}
}
