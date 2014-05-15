package com.naiaraodiaga.internetconnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Build;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		OnClickListener onClick = new OnClickListener() {

			@Override
			public void onClick(View v) {
				initThread();
			}
		};
		Button downloadButton = (Button) findViewById(R.id.downloadButton);
		downloadButton.setOnClickListener(onClick);
		
	}

	public void initThread(){
		Thread t = new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					downloadPhotos();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
	}
	
	public void downloadPhotos() throws JSONException{
		try{
			String ruta = getString(R.string.ruta);
			URL url = new URL (ruta);
			
			HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
			
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
			  InputStream in = httpConnection.getInputStream();
			  processStream(in);
			}
		}
		catch(MalformedURLException e1){
			Log.d("NAIARA", "Malformed URL Exception: "+ e1);
		}
		catch(IOException e2){
			Log.d("NAIARA", "IO Exception: " + e2);
		}
	}
	
	public void processStream(InputStream in) throws IOException, JSONException{
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		
		BufferedReader bf = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;
		
        while ((line = bf.readLine()) != null) {
//            sb.append(line + "\n");
            sb.append(line);
        }
        json = new JSONObject(sb.toString());
        
        array = json.getJSONArray("photos");
        for (int i = 0; i < array.length(); i++) {
			JSONObject photo = array.getJSONObject(i);
			Log.d("NAIARA", photo.getString("image_url"));
		}
        
		Log.d("NAIARA", "processStream: "+in.toString()+" \n\n obj: "+json.toString()+ "\n\n\n array: "+array);
		
	}

}
