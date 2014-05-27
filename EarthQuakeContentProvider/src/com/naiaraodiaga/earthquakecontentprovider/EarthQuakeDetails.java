package com.naiaraodiaga.earthquakecontentprovider;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class EarthQuakeDetails extends Activity  implements 
LoaderCallbacks<Cursor> {
	
	private static int QUAKES_DETAIL = 2;
	private long quake_id = 0;
	TextView mag;
	
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

		String where = MyContentProvider.QUAKE_ID + " = ?";
		String whereArgs[] = { String.valueOf(quake_id) };
		String order = null;

		CursorLoader cursorLoader = new CursorLoader(this,
				MyContentProvider.CONTENT_URI, from, where, whereArgs, order);


		return cursorLoader;

	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (cursor.moveToFirst()){
		mag.setText(cursor.getString(cursor.getColumnIndex(MyContentProvider.MAGNITUDE)));
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursor) {
		
		
	}
}
