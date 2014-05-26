package com.naiaraodiaga.earthquakecontentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class MyContentProvider extends ContentProvider {

	private static final int ALLROWS = 1;
	private static final int SINGLE_ROW = 2;

	private QuakeDBOpenHelper quakeDBOpenHelper;
	
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
			.parse("content://com.naiaraodiaga.provider.earthquakecontentprovider/earthquakes");

	private static final UriMatcher uriMatcher;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("com.naiaraodiaga.provider.earthquakecontentprovider",
				"earthquakes", ALLROWS);
		uriMatcher.addURI("com.naiaraodiaga.provider.earthquakecontentprovider",
				"earthquakes/0", SINGLE_ROW);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = quakeDBOpenHelper.getWritableDatabase();
		
		switch (uriMatcher.match(uri)) {
	      case SINGLE_ROW :
	        String rowID = uri.getPathSegments().get(1);
	        selection = QUAKE_ID + "=" + rowID
	            + (!TextUtils.isEmpty(selection) ?
	              " AND (" + selection + ')' : "");
	      default: break;
	    }
		
		if (selection == null)
		      selection = "1";
		
		int deleteCount = db.delete(QuakeDBOpenHelper.DATABASE_TABLE,
				  selection, selectionArgs);
		
		getContext().getContentResolver().notifyChange(uri, null);
		
		return deleteCount;
	}

	@Override
	public String getType(Uri uri) {

		switch (uriMatcher.match(uri)) {
		case ALLROWS:
			return "vnd.android.cursor.dir/vnd.naiaraodiaga.provider.earthquakecontentprovider";
		case SINGLE_ROW:
			return "vnd.android.cursor.item/vnd.naiaraodiaga.provider.earthquakecontentprovider";
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}

	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = quakeDBOpenHelper.getWritableDatabase();

		String nullColumnHack = null;

		long id = db.insert(QuakeDBOpenHelper.DATABASE_TABLE, nullColumnHack,
				values);

		if (id > -1) {
			// Construct and return the URI of the newly inserted row.
			Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);
			// Notify any observers of the change in the data set.
			getContext().getContentResolver().notifyChange(insertedId, null);
			return insertedId;
		} else
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
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteDatabase db;
		String groupBy = null;
		String having = null;

		try {
			db = quakeDBOpenHelper.getWritableDatabase();

			SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

			switch (uriMatcher.match(uri)) {
			case SINGLE_ROW:
				String rowID = uri.getPathSegments().get(1);
				queryBuilder.appendWhere(QUAKE_ID + "="
						+ rowID);
			default:
				break;
			}

			queryBuilder.setTables(QuakeDBOpenHelper.DATABASE_TABLE);

			Cursor cursor = queryBuilder.query(db, projection, selection,
					selectionArgs, groupBy, having, sortOrder);

			return cursor;

		} catch (SQLiteException ex) {
			db = quakeDBOpenHelper.getReadableDatabase();
		}

		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = quakeDBOpenHelper.getWritableDatabase();

		switch (uriMatcher.match(uri)) {
		case SINGLE_ROW:
			String rowID = uri.getPathSegments().get(1);
			selection = QUAKE_ID
					+ "="
					+ rowID
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection
							+ ')' : "");
		default:
			break;
		}

		int updateCount = db.update(QuakeDBOpenHelper.DATABASE_TABLE, values,
				selection, selectionArgs);

		getContext().getContentResolver().notifyChange(uri, null);
		
	    return updateCount;
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
