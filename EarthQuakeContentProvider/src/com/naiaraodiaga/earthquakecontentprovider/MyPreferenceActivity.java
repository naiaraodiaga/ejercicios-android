package com.naiaraodiaga.earthquakecontentprovider;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
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

//		boolean refresh = PreferenceManager.getDefaultSharedPreferences(
//				this).getBoolean(getString(R.string.AUTOREFRESH_KEY), false);
			
		
		if (key == getResources().getString(R.string.AUTOREFRESH_KEY)) {
			boolean autorefresh = prefs.getBoolean(key,  true);
			if(autorefresh){
				Log.d("NAIARA", "MyPreferenceActivity - Start service");
				setInexactRepeatingAlarm();
			}
			else{
				Log.d("NAIARA", "MyPreferenceActivity - Stop service");
			}
        }
		else{
			Log.d("NAIARA", "prefs: "+prefs.getString(key, "15"));
		}
	}
	
	
	
	private void setInexactRepeatingAlarm() {
	    //Get a reference to the Alarm Manager
	    AlarmManager alarmManager =
	    (AlarmManager)getSystemService(this.ALARM_SERVICE);
	    //Set the alarm to wake the device if sleeping.
	    int alarmType = AlarmManager.ELAPSED_REALTIME_WAKEUP;
	    //Schedule the alarm to repeat every half hour.
//	    long timeOrLengthofWait = AlarmManager.INTERVAL_HALF_HOUR;
	    long timeOrLengthofWait = 1000; 
	    //Create a Pending Intent that will broadcast and action
	    String ALARM_ACTION = "ALARM_ACTION";
	    Intent intentToFire = new Intent(ALARM_ACTION);
	    PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0,
	     intentToFire, 0);
	    //Wake up the device to fire an alarm in half an hour, and every
	    //half-hour after that.
//	    alarmManager.setInexactRepeating(alarmType,
//	                              timeOrLengthofWait,
//	                              timeOrLengthofWait,
//	                              alarmIntent);
	    alarmManager.setInexactRepeating(alarmType,
                0,
                timeOrLengthofWait,
                alarmIntent);
	}
}
