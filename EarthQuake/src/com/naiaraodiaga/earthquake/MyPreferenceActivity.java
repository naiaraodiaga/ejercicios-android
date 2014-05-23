package com.naiaraodiaga.earthquake;


import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View.OnCreateContextMenuListener;

public class MyPreferenceActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new MyPreferenceFragment())
				.commit();

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

		if (key == getResources().getString(R.string.AUTOREFRESH_KEY)) {
			boolean autorefresh = prefs.getBoolean(key,  true);
			if(autorefresh){
				Log.d("NAIARA", "Start service");
			}
			else{
				Log.d("NAIARA", "Stop service");
			}
        }
		else{
			Log.d("NAIARA", "prefs: "+prefs.getString(key, "15"));
		}
	}

}
