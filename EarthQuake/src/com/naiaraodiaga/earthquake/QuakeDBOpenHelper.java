package com.naiaraodiaga.earthquake;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuakeDBOpenHelper extends SQLiteOpenHelper{

	public static final String DATABASE_NAME = "Earthquake.db";
	private static final String DATABASE_TABLE = "Quakes";
	public static final int DATABASE_VERSION = 1;
	
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
	public static final String[] DATABASE_COLUMNS = new String[] {QUAKE_ID, ID_STR, PLACE, TIME, DETAIL, MAGNITUDE, LAT, LONG, URL, CREATED_AT, UPDATED_AT};
	
	private static final String DATABASE_CREATE = 
			"create table if not exists " + DATABASE_TABLE +" "+ "("
			+ QUAKE_ID + " integer primary key autoincrement, "
			+ ID_STR + " TEXT UNIQUE,"
			+ PLACE + " text,"
			+ TIME + " real, "
			+ DETAIL + " text, "
			+ MAGNITUDE + " real," 
			+ LAT+ " real, "
			+ LONG+ " real, "
			+ URL+ " text, "
			+ CREATED_AT+ " real, "
			+ UPDATED_AT+" real);";
	
	public QuakeDBOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}


	public static String getDatabaseTable() {
		return DATABASE_TABLE;
	}

	public static String[] getDatabaseColumns() {
		return DATABASE_COLUMNS;
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		try{
			Log.d("NAIARA", "oncreate: \n\n"+DATABASE_CREATE);
			db.execSQL(DATABASE_CREATE);
			
		}
		catch(Exception e){
			Log.d("NAIARA", "ERROR: "+e.getMessage());
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		// Create a new one.
		onCreate(db);
		
	}

}
