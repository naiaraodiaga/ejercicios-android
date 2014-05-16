package com.naiaraodiaga.preferencias;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Switch;

public class SettingsActivity extends Activity {

	public static final String MY_PREFS = "My preferences";
	public static final String AUTOREFRESH = "autorefresh";
	public static final String INTERVAL = "Interval";	
	
	private SharedPreferences mySharedPreferences;
	
	Switch autorefreshSwitch;
	Spinner intervalSpinner;
	boolean isAutorefreshed;
	int intervalIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		autorefreshSwitch = (Switch) findViewById(R.id.autorefreshP2);
		intervalSpinner = (Spinner) findViewById(R.id.intervalSpinner);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.intervalArray,
				android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		intervalSpinner.setAdapter(adapter);
		
		mySharedPreferences = getSharedPreferences(MY_PREFS, Activity.MODE_PRIVATE);
		
		isAutorefreshed = mySharedPreferences.getBoolean(AUTOREFRESH, false);
		intervalIndex = mySharedPreferences.getInt(INTERVAL, 0);
		autorefreshSwitch.setChecked(isAutorefreshed);
		intervalSpinner.setSelection(intervalIndex);
		 
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putBoolean(AUTOREFRESH,autorefreshSwitch.isChecked());
		editor.putInt(INTERVAL, intervalSpinner.getSelectedItemPosition());
		editor.apply();
	}
}
