package com.naiaraodiaga.earthquake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class QuakesDownloader {
	
//	private EarthQuakeDB earthquakeDB;
	private Context context;
	
//	public QuakesDownloader(EarthQuakeDB earthquakeDB, Context context) {
//		this.earthquakeDB = earthquakeDB;
//		this.context = context;
//
//		initThread();
//	}
	
	public QuakesDownloader(Context context) {
		this.context = context;

		initThread();
	}
	
	
	public void downloadQuakes() throws JSONException {
		try {
			String ruta = context.getString(R.string.Ruta);
			URL url = new URL(ruta);

			HttpURLConnection httpConnection = (HttpURLConnection) url
					.openConnection();

			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream in = httpConnection.getInputStream();
				processStream(in);
			}
			Log.d("NAIARA", "*** JSON downloaded ***");
		} catch (MalformedURLException e1) {
			Log.d("NAIARA", "Malformed URL Exception: " + e1);
		} catch (IOException e2) {
			Log.d("NAIARA", "IO Exception: " + e2);
		}
	}
	
	public void processStream(InputStream in) throws IOException, JSONException {
		JSONObject json = new JSONObject();
		JSONArray quakesArray = new JSONArray();

		BufferedReader bf = new BufferedReader(new InputStreamReader(in,
				"UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;

		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		json = new JSONObject(sb.toString());

		quakesArray = json.getJSONArray("features");
		for (int i = 0; i < quakesArray.length(); i++) {
			Earthquake quake = new Earthquake();
			
			JSONObject jsonQuake = quakesArray.getJSONObject(i);
			quake.setIdStr(jsonQuake.getString("id"));
			quake.setPlace(jsonQuake.getJSONObject("properties").getString("place"));
			quake.setTime(jsonQuake.getJSONObject("properties").getLong("time"));
			quake.setDetail(jsonQuake.getJSONObject("properties").getString("detail"));
			quake.setMagnitude(jsonQuake.getJSONObject("properties").getDouble("mag"));
			quake.setLat(jsonQuake.getJSONObject("geometry").getJSONArray("coordinates").getDouble(1));
			quake.setLon(jsonQuake.getJSONObject("geometry").getJSONArray("coordinates").getDouble(0));
			quake.setUrl(jsonQuake.getJSONObject("properties").getString("url"));
			
//			earthquakeDB.insertQuake(quake);
			insertQuake(quake);
			
		}
		
		Log.d("NAIARA", " *** JSON processed ***");
	}
	
	public void initThread() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					downloadQuakes();
//					earthquakeDB.closeDB();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
	}
	
	public void insertQuake(Earthquake quake) {
		ContentValues newValues = new ContentValues();
		
		Date currentDate = new Date();
		
		newValues.put(MyContentProvider.QUAKE_ID, quake.get_id());
		newValues.put(MyContentProvider.ID_STR, quake.getIdStr());
		newValues.put(MyContentProvider.PLACE, quake.getPlace());
		newValues.put(MyContentProvider.TIME, quake.getTime().getTime());
		newValues.put(MyContentProvider.DETAIL, quake.getDetail());
		newValues.put(MyContentProvider.MAGNITUDE, quake.getMagnitude());
		newValues.put(MyContentProvider.LAT, quake.getLat());
		newValues.put(MyContentProvider.LONG, quake.getLon());
		newValues.put(MyContentProvider.URL, quake.getUrl());
		newValues.put(MyContentProvider.CREATED_AT, currentDate.getTime());
		newValues.put(MyContentProvider.UPDATED_AT, currentDate.getTime());

	}
}
