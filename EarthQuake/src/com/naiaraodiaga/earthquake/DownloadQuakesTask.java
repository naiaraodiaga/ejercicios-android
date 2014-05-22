package com.naiaraodiaga.earthquake;

import java.net.URL;

import android.content.Context;
import android.os.AsyncTask;

public class DownloadQuakesTask extends AsyncTask<URL, Integer, Long>{

	private EarthQuakeDB earthquakeDB;
	private Context context;
	
	public DownloadQuakesTask(Context context) {
		this.context = context;
	}
	
	@Override
	protected Long doInBackground(URL... params) {
		
		earthquakeDB = new EarthQuakeDB(context);
		
		
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Long result) {
		
		
		
		super.onPostExecute(result);
	}

}
