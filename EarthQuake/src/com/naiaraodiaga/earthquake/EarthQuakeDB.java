package com.naiaraodiaga.earthquake;

import java.sql.Timestamp;
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

	public EarthQuakeDB(Context context) {
		quakeDBOpenHelper = new QuakeDBOpenHelper(context,
				QuakeDBOpenHelper.DATABASE_NAME, null,
				QuakeDBOpenHelper.DATABASE_VERSION);
		db = this.openDB();
	}

	public SQLiteDatabase openDB() {
		try {
			db = quakeDBOpenHelper.getWritableDatabase();

		} catch (Exception e) {
			Log.d("NAIARA", "ERROR EarthQuakeDB: " + e.getMessage());
		}
		return db;
	}

	public void closeDB() {
		db.close();
	}

	public Cursor selectAllBD() {
		Cursor cursor = db.query(quakeDBOpenHelper.getDatabaseTable(),
				quakeDBOpenHelper.getDatabaseColumns(), null, null, null, null,
				quakeDBOpenHelper.getDatabaseColumns()[0]);

		return cursor;
	}

	public void addNewQuake(Earthquake quake) {
		// Create a new row of values to insert.
		ContentValues newValues = new ContentValues();

		Date currentDate = new Date();
		// Assign values for each row.
		newValues.put(quakeDBOpenHelper.PLACE, quake.getPlace());
		newValues.put(quakeDBOpenHelper.TIME, quake.getTime().toString());
		newValues.put(quakeDBOpenHelper.DETAIL, quake.getDetail());
		newValues.put(quakeDBOpenHelper.MAGNITUDE, quake.getMagnitude());
		newValues.put(quakeDBOpenHelper.LAT, quake.getLat());
		newValues.put(quakeDBOpenHelper.LONG, quake.getLon());
		newValues.put(quakeDBOpenHelper.URL, quake.getUrl());
		newValues.put(quakeDBOpenHelper.CREATED_AT, currentDate.getTime());
		newValues.put(quakeDBOpenHelper.UPDATED_AT, currentDate.getTime());

		// Insert the row into your table
		db.insert(quakeDBOpenHelper.getDatabaseTable(), null, newValues);
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

		db.update(quakeDBOpenHelper.getDatabaseTable(), updatedValues, where,
				whereArgs);
	}

	public void deleteAllQuakes() {
		// Delete the rows that match the where clause.
		db.delete(quakeDBOpenHelper.getDatabaseTable(), null, null);
	}

	public void deleteQuake(int quakeId) {
		// Specify a where clause that determines which row(s) to delete.
		// Specify where arguments as necessary.
		String where = quakeDBOpenHelper.QUAKE_ID + "= ?";
		String whereArgs[] = { String.valueOf(quakeId) };
		// Delete the rows that match the where clause.
		db.delete(quakeDBOpenHelper.getDatabaseTable(), where, whereArgs);
	}
}
