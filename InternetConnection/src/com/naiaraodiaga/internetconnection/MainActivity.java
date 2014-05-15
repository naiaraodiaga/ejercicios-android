package com.naiaraodiaga.internetconnection;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private long reference;
	private BroadcastReceiver receiver;
	private DownloadManager downloadManager;

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

		IntentFilter filter = new IntentFilter(
				DownloadManager.ACTION_DOWNLOAD_COMPLETE);

		receiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {

				Log.d("NAIARA", "Downloading");
				
				long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
				if(id == reference) {
					Query q = new Query();
					q.setFilterById(id);
					
					Cursor downloads = downloadManager.query(q);
					
					int nameIdx = downloads.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
					int sizeIdx = downloads.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
					while(downloads.moveToNext()) {
						// Obtener los datos
						String name = downloads.getString(nameIdx);
						int size = downloads.getInt(sizeIdx);
						
						Log.d("NAIARA", "Nombre : " + name);
						Log.d("NAIARA", "Tama–o : " + String.valueOf(size));
						
						Intent testIntent = new Intent();
			            testIntent.setAction(Intent.ACTION_VIEW);
			            File f = new File(name);
			            Uri uri = Uri.fromFile(f);

			            startActivity(testIntent);
						
					}
				}
			}
		};

		registerReceiver(receiver, filter);

		OnClickListener onClickZip = new OnClickListener() {

			@Override
			public void onClick(View v) {

				download();
			}
		};
		Button downloadZipButton = (Button) findViewById(R.id.downloadZipButton);
		downloadZipButton.setOnClickListener(onClickZip);

		

	}

	public void initThread() {
		Thread t = new Thread(new Runnable() {
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

	public void downloadPhotos() throws JSONException {
		try {
			String ruta = getString(R.string.ruta);
			URL url = new URL(ruta);

			HttpURLConnection httpConnection = (HttpURLConnection) url
					.openConnection();

			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream in = httpConnection.getInputStream();
				processStream(in);
			}
		} catch (MalformedURLException e1) {
			Log.d("NAIARA", "Malformed URL Exception: " + e1);
		} catch (IOException e2) {
			Log.d("NAIARA", "IO Exception: " + e2);
		}
	}

	public void processStream(InputStream in) throws IOException, JSONException {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();

		BufferedReader bf = new BufferedReader(new InputStreamReader(in,
				"UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;

		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		json = new JSONObject(sb.toString());

		array = json.getJSONArray("photos");
		for (int i = 0; i < array.length(); i++) {
			JSONObject photo = array.getJSONObject(i);
			Log.d("NAIARA", "Name: " + photo.getString("name") + "\t URL: "
					+ photo.getString("image_url"));
		}
	}

	public void download() {
		try {

			String serviceString = Context.DOWNLOAD_SERVICE;
			downloadManager = (DownloadManager) getSystemService(serviceString);

			Uri uri = Uri
					.parse("http://developer.android.com/shareables/icon_templates-v4.0.zip");
			DownloadManager.Request request = new Request(uri);
			// request.setAllowedNetworkTypes(Request.NETWORK_WIFI);
			request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
			
			// Por defecto las descargas se guardan aqu’
			// /data/data/com.android.providers.downloads/cache "id" "name"
			// Es mejor hacerlo aqu’
			// /mnt/sdcard/Android/data/com.___.downloads/files/Download
			
			request.setDestinationInExternalFilesDir(this,
					Environment.DIRECTORY_DOWNLOADS, uri.getLastPathSegment());

			reference = downloadManager.enqueue(request);
		} catch (Exception e) {
			Log.d("NAIARA", "Exception: " + e.getMessage());
		}
	}

}
