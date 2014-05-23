package com.naiaraodiaga.earthquake;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.net.Uri;
import android.util.Log;

public class MyContentProvider extends ContentProvider {

	private static final int ALLROWS = 1;
	private static final int SINGLE_ROW = 2;

	public static final String QUAKE_ID = "_id";
	public static final String ID_STR = "id_str";
	public static final String PLACE = "place";
	public static final String TIME = "time";
	public static final String DETAIL = "detail";
	public static final String MAGNITUDE = "magnitude";
	public static final String LAT = "lat";
	public static final String LONG = "long";
	public static final String URL = "url";
	public static final String CREATED_AT = "created_at";
	public static final String UPDATED_AT = "updated_at";

	public static final Uri CONTENT_URI = Uri
			.parse("content://com.naiaraodiaga.provider.earthquakeprovider/earthquakes");

	private static final UriMatcher uriMatcher;

	private SQLiteOpenHelper quakeDBOpenHelper;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("com.naiaraodiaga.provider.earthquakeprovider",
				"earthquake", ALLROWS);
		uriMatcher.addURI("com.naiaraodiaga.provider.earthquakeprovider",
				"earthquake/0", SINGLE_ROW);
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		quakeDBOpenHelper = new QuakeDBOpenHelper(getContext(),
				QuakeDBOpenHelper.DATABASE_NAME, null,
				QuakeDBOpenHelper.DATABASE_VERSION);

		return true;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

	private class QuakeDBOpenHelper extends SQLiteOpenHelper {

		public static final String DATABASE_NAME = "Earthquake.db";
		private static final String DATABASE_TABLE = "Quakes";
		public static final int DATABASE_VERSION = 1;

		public final String[] DATABASE_COLUMNS = new String[] { QUAKE_ID,
				ID_STR, PLACE, TIME, DETAIL, MAGNITUDE, LAT, LONG, URL,
				CREATED_AT, UPDATED_AT };

		private static final String DATABASE_CREATE = "create table if not exists "
				+ DATABASE_TABLE
				+ " "
				+ "("
				+ QUAKE_ID
				+ " integer primary key autoincrement, "
				+ ID_STR
				+ " TEXT UNIQUE,"
				+ PLACE
				+ " text,"
				+ TIME
				+ " datetime, "
				+ DETAIL
				+ " text, "
				+ MAGNITUDE
				+ " real,"
				+ LAT
				+ " real, "
				+ LONG
				+ " real, "
				+ URL
				+ " text, "
				+ CREATED_AT
				+ " datetime, " + UPDATED_AT + " datetime);";

		public QuakeDBOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		public String getDatabaseTable() {
			return DATABASE_TABLE;
		}

		public String[] getDatabaseColumns() {
			return DATABASE_COLUMNS;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				Log.d("NAIARA", "oncreate: \n\n" + DATABASE_CREATE);
				db.execSQL(DATABASE_CREATE);

			} catch (Exception e) {
				Log.d("NAIARA", "ERROR: " + e.getMessage());
			}

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			// Create a new one.
			onCreate(db);

		}

	}

}
