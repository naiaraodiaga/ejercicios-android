package com.naiaraodiaga.earthquake;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.os.Build;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// EarthQuakeDB earthquakeDB = new EarthQuakeDB(this);

		// earthquakeDB.selectAllBD();
		// Log.d("NAIARA", "select: "+ earthquakeDB.selectAllBD());

		// Earthquake quake = new Earthquake("t1", "Fukushima",
		// Long.valueOf(1006200700), "Mega Earthquake", 7.0, 31.06, 45.72,
		// "http://www.fukushima.org");
		// Earthquake quake2 = new Earthquake("t2", "Tokyo",
		// Long.valueOf(1006200700), "Tokyo Earthquake", 5.0, 31.06, 42.72,
		// "http://www.tokyo.org");
		// earthquakeDB.insertQuake(quake);
		// earthquakeDB.insertQuake(quake2);
		//
		// quake.setPlace("Osaka");
		//
		// earthquakeDB.updateQuake(1, quake);

		// earthquakeDB.deleteQuake(2);

		// earthquakeDB.deleteAllQuakes();

		// QuakesDownloader qd = new QuakesDownloader(EarthQuakeDB.getDB(this),
		// this);

		// ArrayList<Earthquake> arrayQuake = earthquakeDB.selectAllBD();
		// for (Earthquake earthquake : arrayQuake) {
		// Log.d("NAIARA", "Id: " + earthquake.getIdStr());
		// Log.d("NAIARA", "Place: " + earthquake.getPlace());
		// Log.d("NAIARA", "Time: " + earthquake.getTime());
		// Log.d("NAIARA", "Detail: " + earthquake.getDetail());
		// Log.d("NAIARA", "Magnitude: " + earthquake.getMagnitude());
		// Log.d("NAIARA", "Lat: " + earthquake.getLat());
		// Log.d("NAIARA", "Long: " + earthquake.getLon());
		// Log.d("NAIARA", "URL: " + earthquake.getUrl());
		// }
		//
		// Log.d("NAIARA", "selectByMag");
		// ArrayList<Earthquake> arrayQuake2 = earthquakeDB.selectByMag(2);
		// for (Earthquake earthquake : arrayQuake2) {
		// Log.d("NAIARA", "Id: " + earthquake.getIdStr());
		// Log.d("NAIARA", "Place: " + earthquake.getPlace());
		// Log.d("NAIARA", "Time: " + earthquake.getTime());
		// Log.d("NAIARA", "Detail: " + earthquake.getDetail());
		// Log.d("NAIARA", "Magnitude: " + earthquake.getMagnitude());
		// Log.d("NAIARA", "Lat: " + earthquake.getLat());
		// Log.d("NAIARA", "Long: " + earthquake.getLon());
		// Log.d("NAIARA", "URL: " + earthquake.getUrl());
		// }

		// addQuakesToList(arrayQuake2);

		if (savedInstanceState == null) {
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			fragmentTransaction.add(R.id.container, new QuakesListFragment());
			fragmentTransaction.commit();
		}
//		
		
		
//		public static final String QUAKE_ID = "_id";
//		public static final String ID_STR = "id_str";
//		public static final String PLACE = "place";
//		public static final String TIME = "time";
//		public static final String DETAIL = "detail";
//		public static final String MAGNITUDE = "magnitude";
//		public static final String LAT = "lat";
//		public static final String LONG = "long";
//		public static final String URL = "url";
//		public static final String CREATED_AT = "created_at";
//		public static final String UPDATED_AT = "updated_at";
		
		ContentResolver cr = getContentResolver();
		String[] result_columns = new String[] {
			    MyContentProvider.QUAKE_ID,
			    MyContentProvider.ID_STR,
			    MyContentProvider.PLACE,
			    MyContentProvider.TIME,
			    MyContentProvider.DETAIL,
			    MyContentProvider.MAGNITUDE,
			    MyContentProvider.LAT,
			    MyContentProvider.LONG,
			    MyContentProvider.URL,
			    MyContentProvider.CREATED_AT,
			    MyContentProvider.UPDATED_AT
			    };
		
		
		String where = null;
		String whereArgs[] = null;
		String order = null;
		
		Cursor resultCursor = cr.query(MyContentProvider.CONTENT_URI, result_columns,
                where, whereArgs, order);
		
		while (resultCursor.moveToNext()) {
			Log.d("NAIARA", resultCursor.getString(resultCursor.getColumnIndex(MyContentProvider.PLACE)));
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
		if (id == R.id.ListLayout) {
			// Aqu’ abrimos un intent
			Intent intent = new Intent(this, MyPreferenceActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// public void addQuakesToList(ArrayList<Earthquake> arrayQuake) {
	//
	// for (Earthquake earthquake : arrayQuake) {
	//
	// ((QuakesListFragment)
	// getFragmentManager().findFragmentByTag("ListLayout")).addElement(earthquake.getMagnitude()
	// + ": " + earthquake.getPlace() + ": " + earthquake.getTime());
	// }
	//
	//
	// }

}
