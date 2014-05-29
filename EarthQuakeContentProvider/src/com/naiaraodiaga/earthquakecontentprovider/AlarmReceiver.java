package com.naiaraodiaga.earthquakecontentprovider;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.drm.DrmStore.Action;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
	
	public static String ALARM_ACTION = "com.naiaraodiaga.earthquake.alarmService";
	
	@Override
	public void onReceive(Context ctx, Intent intent) {

		Intent serviceIntent = new Intent(ctx, QuakesDownloaderService.class);
	    ctx.startService(serviceIntent);
	}

	
}
