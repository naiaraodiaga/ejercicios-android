package com.naiaraodiaga.earthquakecontentprovider;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class EarthQuakeDetails extends Activity  implements 
LoaderCallbacks<Cursor> {
	
	private static int QUAKES_DETAIL = 2;
	private long quake_id = 0;
	TextView mag, place, time, detail, lat, lon, url;
	
	private String[] from = { MyContentProvider.MAGNITUDE,
			MyContentProvider.PLACE, MyContentProvider.TIME, MyContentProvider.DETAIL, 
			MyContentProvider.LAT, MyContentProvider.LONG, MyContentProvider.URL,
			MyContentProvider.QUAKE_ID }; // IMPORTANTE poner el id al final
	private int[] to = { R.id.detailMag, R.id.detailPlace, R.id.detailTime, R.id.detailDetail, R.id.detailLat, R.id.detailLong, R.id.detailUrl };
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		mag = (TextView)findViewById(R.id.detailMag);
		place = (TextView)findViewById(R.id.detailPlace);
		time = (TextView)findViewById(R.id.detailTime);
		detail = (TextView)findViewById(R.id.detailDetail);
		lat = (TextView)findViewById(R.id.detailLat);
		lon = (TextView)findViewById(R.id.detailLong);
		url = (TextView)findViewById(R.id.detailUrl);
		
		
		Intent intent = getIntent();
		
		
		if (intent != null){
			Log.d("NAIARA", "id: "+intent.getLongExtra("id", 0));
			quake_id = intent.getLongExtra("id", 0);
			
			getLoaderManager().initLoader(QUAKES_DETAIL, null, this);
		}
	}


	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {

		ContentResolver cr = getContentResolver();

//		String where = MyContentProvider.QUAKE_ID + " = ?";
//		String whereArgs[] = { String.valueOf(quake_id) };
//		String order = null;
//
//		CursorLoader cursorLoader = new CursorLoader(this,
//				MyContentProvider.CONTENT_URI, from, where, whereArgs, order);


		Uri rowAddress = ContentUris.withAppendedId(MyContentProvider.CONTENT_URI, quake_id);
		
		CursorLoader cursorLoader = new CursorLoader(this,
				rowAddress, MyContentProvider.DATABASE_COLUMNS, null, null, null);
		
		
		return cursorLoader;

	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (cursor.moveToFirst()){
			mag.setText(cursor.getString(cursor.getColumnIndex(MyContentProvider.MAGNITUDE)));
			place.setText(cursor.getString(cursor.getColumnIndex(MyContentProvider.PLACE)));
			SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.ENGLISH);
			String date = df.format(cursor.getLong(cursor.getColumnIndex(MyContentProvider.TIME)));
			time.setText(date);
			detail.setText(cursor.getString(cursor.getColumnIndex(MyContentProvider.DETAIL)));
			lat.setText(cursor.getString(cursor.getColumnIndex(MyContentProvider.LAT)));
			lon.setText(cursor.getString(cursor.getColumnIndex(MyContentProvider.LONG)));
			url.setText(cursor.getString(cursor.getColumnIndex(MyContentProvider.URL)));
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursor) {
		
		
	}
}
