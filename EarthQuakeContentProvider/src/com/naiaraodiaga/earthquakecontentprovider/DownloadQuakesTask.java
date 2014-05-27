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

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadQuakesTask extends AsyncTask<String, Integer, ArrayList<Earthquake>>{

//	public interface IQuakesList {
//		public void refreshQuakesList(ArrayList<Earthquake> earthquake);
//	}
	
//	private EarthQuakeDB earthquakeDB;
	private Context context;
//	private IQuakesList iQuakes;
	
//	public DownloadQuakesTask(Context context, IQuakesList iQuakes) {
//		this.context = context;
////		this.iQuakes = iQuakes;
////		earthquakeDB = EarthQuakeDB.getDB(context);
//	}
	
	public DownloadQuakesTask(Context context) {
		this.context = context;
//		this.iQuakes = iQuakes;
//		earthquakeDB = EarthQuakeDB.getDB(context);
	}

	@Override
	protected ArrayList<Earthquake> doInBackground(String... params) {
		
		ArrayList<Earthquake> earthquakeList = new ArrayList<Earthquake>();
		int count = params.length;
		for (int i = 0; i < count; i++) {
			try {
				earthquakeList = downloadQuakes(params[i]);
			} catch (JSONException e) {
				Log.d("NAIARA", "ERROR - DownloadQuakesTask: "+e.getMessage());
			}
		}

		return earthquakeList;
	}
	

	
	@Override
	protected void onPostExecute(ArrayList<Earthquake> result) {
		super.onPostExecute(result);
//		iQuakes.refreshQuakesList(result);
	}

	
	public ArrayList<Earthquake> downloadQuakes(String ruta) throws JSONException {
		ArrayList<Earthquake> earthquakeList = new ArrayList<Earthquake>();
		try {
//			String ruta = context.getString(R.string.Ruta);
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
	
	public ArrayList<Earthquake> processStream(InputStream in) throws IOException, JSONException {
		ArrayList<Earthquake> earthquakeList = new ArrayList<Earthquake>();
		
		JSONObject json = new JSONObject();
		JSONArray quakesArray = new JSONArray();

		BufferedReader bf = new BufferedReader(new InputStreamReader(in,
				"UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;

		try{
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
				
//				earthquakeDB.insertQuake(quake);
				insertQuake(quake);
				
				earthquakeList.add(quake);
			}
			
			Log.d("NAIARA", " *** JSON processed ***");
		}
		catch(Exception e){
			Log.d("NAIARA", "ERROR - DownloadQuakesTask (processStream)");
		}
		return earthquakeList;
	}

	public void insertQuake(Earthquake quake) {
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
		
		ContentResolver cr = context.getContentResolver();
		
		Uri myRowUri = cr.insert(MyContentProvider.CONTENT_URI, newValues);

	}
}
