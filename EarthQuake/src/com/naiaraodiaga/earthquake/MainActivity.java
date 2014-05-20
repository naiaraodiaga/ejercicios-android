package com.naiaraodiaga.earthquake;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		EarthQuakeDB earthquakeDB = new EarthQuakeDB(this);
	
		
//		earthquakeDB.selectAllBD();
//		Log.d("NAIARA", "select: "+ earthquakeDB.selectAllBD());
		
		Earthquake quake = new Earthquake("Fukushima", Long.valueOf(1006200700), "Mega Earthquake", 7.0, 31.06, 45.72, "http://www.fukushima.org");
		Earthquake quake2 = new Earthquake("Tokyo", Long.valueOf(1006200700), "Tokyo Earthquake", 5.0, 31.06, 42.72, "http://www.tokyo.org");
		earthquakeDB.addNewQuake(quake);
		earthquakeDB.addNewQuake(quake2);
		
		quake.setPlace("Osaka");
		
		earthquakeDB.updateQuake(1, quake);
		
		
		earthquakeDB.closeDB();
		

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
