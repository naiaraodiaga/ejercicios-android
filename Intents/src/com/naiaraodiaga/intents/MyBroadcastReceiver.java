package com.naiaraodiaga.intents;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
	private String incomingNumber;

	@Override
	public void onReceive(Context context, Intent intent) {

		try {
			String action = intent.getAction().toString();
			String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
			Bundle bundle = intent.getExtras();

			Log.d("NAIARA", "Action: " + action);

			if (action.equalsIgnoreCase("android.intent.action.PHONE_STATE")) {
				if (state.equalsIgnoreCase("RINGING")) {
					incomingNumber = intent
							.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
					Log.d("NAIARA", "Incoming number: " + incomingNumber);
					
					Toast t = Toast.makeText(context, "Incoming number: "+incomingNumber, Toast.LENGTH_LONG);
				    t.show();
				}
			} else if (action
					.equalsIgnoreCase("android.provider.Telephony.SMS_RECEIVED")) {
				

				if (bundle != null) {
					final Object[] pdusObj = (Object[]) bundle.get("pdus");

					for (int i = 0; i < pdusObj.length; i++) {


						SmsMessage currentMessage = SmsMessage
								.createFromPdu((byte[]) pdusObj[i]);
						String phoneNumber = currentMessage
								.getDisplayOriginatingAddress();

						String senderNum = phoneNumber;
						String message = currentMessage.getDisplayMessageBody();

						
						String notificacion = "Sender num: " + senderNum + "; Message: " + message;
						Log.d("NAIARA", notificacion);
						
						Toast t = Toast.makeText(context, "SMS Received: "+notificacion, Toast.LENGTH_LONG);
					    t.show();
					}
				}
			}
			else if (action.equalsIgnoreCase("android.intent.action.AIRPLANE_MODE")){
				Log.d("NAIARA", "Airplane mode");
				Toast t = Toast.makeText(context, "Airplane mode", Toast.LENGTH_LONG);
			    t.show();
			}
			else if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
				Log.d("NAIARA", "There's not conectivity");
				Toast t = Toast.makeText(context, "There's not conectivity", Toast.LENGTH_LONG);
			    t.show();
			}

		} catch (Exception e) {
			Log.d("NAIARA", "Exception: " + e.getMessage());
		}

	}

}
