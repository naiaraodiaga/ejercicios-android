package com.naiaraodiaga.earthquakecontentprovider;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.drm.DrmStore.Action;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

	private static Action accion; 
	
	@Override
	public void onReceive(Context ctx, Intent intent) {
		String action = intent.getAction().toString();
		Log.d("NAIARA", "AlarmReceiver - onReceive (action): "+action);
		
		
//		Intent intent = new Intent(this, QuakesDownloaderService.class);
//		getActivity().startService(intent);

	}

	
}
