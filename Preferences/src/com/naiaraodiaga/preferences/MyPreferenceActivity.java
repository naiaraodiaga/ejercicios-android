package com.naiaraodiaga.preferences;

import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Switch;

public class MyPreferenceActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new MyPreferenceFragment())
				.commit();

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
	}

	// public void onBuildHeaders(List<Header> target) {
	// loadHeadersFromResource(R.xml.userpreferenceheaders, target);
	// }

	@Override
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		Log.d("NAIARA", "prefs: "+prefs.toString());
		Log.d("NAIARA", "key: "+key);
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