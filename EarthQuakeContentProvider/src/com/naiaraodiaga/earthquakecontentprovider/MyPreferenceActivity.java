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
				this.cancelAlarm();
			}
        }
		else if(key == getResources().getString(R.string.INTERVAL_KEY)){
			setInexactRepeatingAlarm();
		}
		else{
			Log.d("NAIARA", "prefs: "+prefs.getString(key, "15"));
		}
	}
	
	
	
	private void setInexactRepeatingAlarm() {
		Log.d("NAIARA", "setInexactRepeatingAlarm");
	    //Get a reference to the Alarm Manager
		try{
			
		
	    AlarmManager alarmManager =
	    (AlarmManager)getSystemService(this.ALARM_SERVICE);
	    //Set the alarm to wake the device if sleeping.
	    int alarmType = AlarmManager.ELAPSED_REALTIME_WAKEUP;
	    //Schedule the alarm to repeat every half hour.
	    
	    int interval = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.INTERVAL_KEY), "5"));
	    
	    Log.d("NAIARA", "Intervalo: "+interval);
	    
	    long timeOrLengthofWait = 1000 * 60 * interval; // 1000 = Un segundo
	    
	    
	    //Create a Pending Intent that will broadcast and action
	    
	    Intent intentToFire = new Intent(AlarmReceiver.ALARM_ACTION);
	    PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0,
	     intentToFire, 0);

	    alarmManager.setInexactRepeating(alarmType,
                0,
                timeOrLengthofWait,
                alarmIntent); // El 0 indica que la primera alarma se lanzar‡ ya mismo
		}catch(Exception e1){
			Log.d("NAIARA", "ERROR - MyPreferenceActivity (setInexactRepeatingAlarm): "+e1.getMessage());
			cancelAlarm();
			Log.d("NAIARA", "Alarm canceled");
		}
		
	}
	
	
	private void cancelAlarm() {
		try{
			Log.d("NAIARA", "cancelAlarm");
			String ALARM_ACTION = "com.naiaraodiaga.earthquake.alarmService";
			Intent ringintent = new Intent(ALARM_ACTION);
		    PendingIntent pi = PendingIntent.getBroadcast(this, 0, ringintent, 0);
		    AlarmManager am = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
		    am.cancel(pi);
		}catch(Exception e2){
			Log.d("NAIARA", "ERROR - MyPreferenceActivity (cancelAlarm): "+e2.getMessage());
		}
		
	}
	
	
}
