package com.naiaraodiaga.preferencias;


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
		
		textAutorefresh = (TextView)findViewById(R.id.textViewAutorefresh);
		textInterval = (TextView)findViewById(R.id.textViewInterval);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			// Aqu’ abrimos un intent
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mySharedPreferences = getSharedPreferences(SettingsActivity.MY_PREFS, Activity.MODE_PRIVATE);
		isAutorefreshed = mySharedPreferences.getBoolean(SettingsActivity.AUTOREFRESH, false);
		intervalIndex = mySharedPreferences.getInt(SettingsActivity.INTERVAL, 0);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.intervalArray, android.R.layout.simple_spinner_item);
		String intervalValue = (String) adapter.getItem(intervalIndex);
		textAutorefresh.setText(String.valueOf(isAutorefreshed));
		textInterval.setText(intervalValue);
	}

}
