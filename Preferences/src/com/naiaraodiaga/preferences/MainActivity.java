package com.naiaraodiaga.preferences;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity {

	TextView textAutorefresh;
	TextView textInterval;
	boolean isAutorefreshed;
	int intervalIndex;
	
	private SharedPreferences mySharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		textAutorefresh = (TextView)findViewById(R.id.textViewRefreshP1);
		textInterval = (TextView)findViewById(R.id.textViewIntervalP1);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			// Aqu’ abrimos un intent
			Intent intent = new Intent(this, MyPreferenceActivity.class);
			startActivity(intent);
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
