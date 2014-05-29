package com.naiaraodiaga.earthquakecontentprovider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class QuakesDownloaderService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		startBackgroundTask(intent, startId);

		Log.d("NAIARA", "QuakesDownloaderService - onStartCommand");

		return Service.START_STICKY;

//		return super.onStartCommand(intent, flags, startId);
	}

	private void startBackgroundTask(Intent intent, int startId) {

		if(this.deviceIsOnline()){
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {

					Log.d("NAIARA", "QuakesDownloaderService - startBackgroundTask");
					try {
						downloadQuakes(getString(R.string.Ruta));
						stopSelf();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
			t.start();
		}
		else{
			Toast toast = Toast.makeText(this, "No connectivity", Toast.LENGTH_LONG);
			toast.show();
		}
		

	}

	private ArrayList<Earthquake> downloadQuakes(String ruta)
			throws JSONException {
		ArrayList<Earthquake> earthquakeList = new ArrayList<Earthquake>();
		try {
			URL url = new URL(ruta);

			HttpURLConnection httpConnection = (HttpURLConnection) url
					.openConnection();

			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream in = httpConnection.getInputStream();
				earthquakeList = processStream(in);
			}
			Log.d("NAIARA", "*** JSON downloaded ***");
		} catch (MalformedURLException e1) {
			Log.d("NAIARA", "Malformed URL Exception: " + e1);
		} catch (IOException e2) {
			Log.d("NAIARA", "IO Exception: " + e2);
		}
		return earthquakeList;
	}

	private ArrayList<Earthquake> processStream(InputStream in)
			throws IOException, JSONException {
		ArrayList<Earthquake> earthquakeList = new ArrayList<Earthquake>();

		JSONObject json = new JSONObject();
		JSONArray quakesArray = new JSONArray();

		BufferedReader bf = new BufferedReader(new InputStreamReader(in,
				"UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;

		try {
			while ((line = bf.readLine()) != null) {
				sb.append(line);
			}
			json = new JSONObject(sb.toString());

			quakesArray = json.getJSONArray("features");
			for (int i = 0; i < quakesArray.length(); i++) {
				Earthquake quake = new Earthquake();

				JSONObject jsonQuake = quakesArray.getJSONObject(i);
				quake.setIdStr(jsonQuake.getString("id"));
				quake.setPlace(jsonQuake.getJSONObject("properties").getString(
						"place"));
				quake.setTime(jsonQuake.getJSONObject("properties").getLong(
						"time"));
				quake.setDetail(jsonQuake.getJSONObject("properties")
						.getString("detail"));
				quake.setMagnitude(jsonQuake.getJSONObject("properties")
						.getDouble("mag"));
				quake.setLat(jsonQuake.getJSONObject("geometry")
						.getJSONArray("coordinates").getDouble(1));
				quake.setLon(jsonQuake.getJSONObject("geometry")
						.getJSONArray("coordinates").getDouble(0));
				quake.setUrl(jsonQuake.getJSONObject("properties").getString(
						"url"));

				insertQuake(quake);

				earthquakeList.add(quake);
			}

			Log.d("NAIARA", " *** JSON processed ***");
		} catch (Exception e) {
			Log.d("NAIARA", "ERROR - QuakesDownloaderService (processStream)");
		}
		return earthquakeList;
	}

	private void insertQuake(Earthquake quake) {
		ContentValues newValues = new ContentValues();

		Date currentDate = new Date();

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

		ContentResolver cr = this.getContentResolver();

		Uri myRowUri = cr.insert(MyContentProvider.CONTENT_URI, newValues);

	}
	
	
	public boolean deviceIsOnline() {
		ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo netInfo = cm.getActiveNetworkInfo();

		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		else{
			return false;
		}
	}
}
