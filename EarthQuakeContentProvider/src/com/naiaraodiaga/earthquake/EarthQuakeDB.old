package com.naiaraodiaga.earthquake;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;
import android.util.Log;

public class EarthQuakeDB {

	private QuakeDBOpenHelper quakeDBOpenHelper = null;
	private SQLiteDatabase db = null;
	private static EarthQuakeDB earthquakeDB = null;

	private EarthQuakeDB(Context context) {
		quakeDBOpenHelper = new QuakeDBOpenHelper(context,
				QuakeDBOpenHelper.DATABASE_NAME, null,
				QuakeDBOpenHelper.DATABASE_VERSION);
	}
	
	public static EarthQuakeDB getDB(Context context) {
		
		if(earthquakeDB == null){
			earthquakeDB = new EarthQuakeDB(context);
			earthquakeDB.openDB();	
		}

		return earthquakeDB;
	}

	public void openDB() {
		try {
			db = quakeDBOpenHelper.getWritableDatabase();

		} catch (Exception e) {
			Log.d("NAIARA", "ERROR EarthQuakeDB: " + e.getMessage());
		}
	}

	public void closeDB() {
		db.close();
	}

	
	public ArrayList<Earthquake> selectByMag(double mag) {
		
		String where = quakeDBOpenHelper.MAGNITUDE + "> ?";
		
		Log.d("NAIARA", "BD mag: "+ String.valueOf(mag));
		
		String whereArgs[] = { String.valueOf(mag)};
		Cursor cursor = db.query(quakeDBOpenHelper.getDatabaseTable(),
				quakeDBOpenHelper.getDatabaseColumns(), where, whereArgs, null, null,
				quakeDBOpenHelper.getDatabaseColumns()[0]);

		return parseToArrayList(cursor);
	}
	
	public ArrayList<Earthquake> selectAllBD() {
		Cursor cursor = db.query(quakeDBOpenHelper.getDatabaseTable(),
				quakeDBOpenHelper.getDatabaseColumns(), null, null, null, null,
				quakeDBOpenHelper.getDatabaseColumns()[0]);

		return parseToArrayList(cursor);
	}
	
	public ArrayList<Earthquake> parseToArrayList(Cursor cursor){
		ArrayList<Earthquake> arrayQuakes = new ArrayList<Earthquake>();
		
		int idIndex = cursor.getColumnIndexOrThrow(quakeDBOpenHelper.QUAKE_ID);
		int idStrIndex = cursor.getColumnIndexOrThrow(quakeDBOpenHelper.ID_STR);
		int placeIndex = cursor.getColumnIndexOrThrow(quakeDBOpenHelper.PLACE);
		int timeIndex = cursor.getColumnIndexOrThrow(quakeDBOpenHelper.TIME);
		int detailIndex = cursor.getColumnIndexOrThrow(quakeDBOpenHelper.DETAIL);
		int magnitudeIndex = cursor.getColumnIndexOrThrow(quakeDBOpenHelper.MAGNITUDE);
		int latIndex = cursor.getColumnIndexOrThrow(quakeDBOpenHelper.LAT);
		int longIndex = cursor.getColumnIndexOrThrow(quakeDBOpenHelper.LONG);
		int urlIndex = cursor.getColumnIndexOrThrow(quakeDBOpenHelper.URL);
		int createdAtIndex = cursor.getColumnIndexOrThrow(quakeDBOpenHelper.CREATED_AT);
		int updatedAtIndex = cursor.getColumnIndexOrThrow(quakeDBOpenHelper.UPDATED_AT);
		
		while (cursor.moveToNext()) {
			
			Earthquake quake = new Earthquake(
					cursor.getLong(idIndex), 
					cursor.getString(idStrIndex), 
					cursor.getString(placeIndex), 
					cursor.getLong(timeIndex), 
					cursor.getString(detailIndex),
					cursor.getDouble(magnitudeIndex),
					cursor.getDouble(latIndex),
					cursor.getDouble(longIndex),
					cursor.getString(urlIndex)
					);
			arrayQuakes.add(quake);
		}
		
		return arrayQuakes;
	}

	public void insertQuake(Earthquake quake) {
		// Create a new row of values to insert.
		ContentValues newValues = new ContentValues();

		Date currentDate = new Date();
		// Assign values for each row.
		newValues.put(quakeDBOpenHelper.ID_STR, quake.getIdStr());
		newValues.put(quakeDBOpenHelper.PLACE, quake.getPlace());
		newValues.put(quakeDBOpenHelper.TIME, quake.getTime().getTime());
		newValues.put(quakeDBOpenHelper.DETAIL, quake.getDetail());
		newValues.put(quakeDBOpenHelper.MAGNITUDE, quake.getMagnitude());
		newValues.put(quakeDBOpenHelper.LAT, quake.getLat());
		newValues.put(quakeDBOpenHelper.LONG, quake.getLon());
		newValues.put(quakeDBOpenHelper.URL, quake.getUrl());
		newValues.put(quakeDBOpenHelper.CREATED_AT, currentDate.getTime());
		newValues.put(quakeDBOpenHelper.UPDATED_AT, currentDate.getTime());

		// Insert the row into your table
		try{
			db.insert(quakeDBOpenHelper.getDatabaseTable(), null, newValues);
			Log.d("NAIARA", "Quake inserted");
		}
		catch(Exception e){
			Log.d("NAIARA", "ERROR - EartchQuakeDB (insertQuake): "+e.getMessage());
		}
	}

	public void updateQuake(int quakeId, Earthquake quake) {
		// Create the updated row Content Values.
		ContentValues updatedValues = new ContentValues();
		// Assign values for each row.
		Date currentDate = new Date();
		// Assign values for each row.
		updatedValues.put(quakeDBOpenHelper.PLACE, quake.getPlace());
		updatedValues.put(quakeDBOpenHelper.TIME, quake.getTime().toString());
		updatedValues.put(quakeDBOpenHelper.DETAIL, quake.getDetail());
		updatedValues.put(quakeDBOpenHelper.MAGNITUDE, quake.getMagnitude());
		updatedValues.put(quakeDBOpenHelper.LAT, quake.getLat());
		updatedValues.put(quakeDBOpenHelper.LONG, quake.getLon());
		updatedValues.put(quakeDBOpenHelper.URL, quake.getUrl());
		updatedValues.put(quakeDBOpenHelper.UPDATED_AT, currentDate.getTime());
		// Specify a where clause the defines which rows should be // updated.
		// Specify where arguments as necessary.
		String where = quakeDBOpenHelper.QUAKE_ID + "= ?";
		String whereArgs[] = { String.valueOf(quakeId) };
		// Update the row with the specified index with the new values.

		try{
			db.update(quakeDBOpenHelper.getDatabaseTable(), updatedValues, where, whereArgs);
			Log.d("NAIARA", "Quake updated");
		}
		catch(Exception e){
			Log.d("NAIARA", "ERROR - EartchQuakeDB (updateQuake): "+e.getMessage());
		}
		
	}

	public void deleteAllQuakes() {
		// Delete all rows 
		try{
			db.delete(quakeDBOpenHelper.getDatabaseTable(), null, null);
			Log.d("NAIARA", "All quakes deleted");
		}
		catch(Exception e){
			Log.d("NAIARA", "ERROR - EarthQuakeDB (deleteAllQuakes): "+e.getMessage());
		}
	}

	public void deleteQuake(int quakeId) {
		// Specify a where clause that determines which row(s) to delete.
		// Specify where arguments as necessary.
		String where = quakeDBOpenHelper.QUAKE_ID + "= ?";
		String whereArgs[] = { String.valueOf(quakeId) };
		// Delete the rows that match the where clause.
		try{
			db.delete(quakeDBOpenHelper.getDatabaseTable(), where, whereArgs);
			Log.d("NAIARA", "Quake deleted");
		}
		catch(Exception e){
			Log.d("NAIARA", "ERROR - EarthQuakeDB (deleteQuake): "+e.getMessage());
		}
	}
}
