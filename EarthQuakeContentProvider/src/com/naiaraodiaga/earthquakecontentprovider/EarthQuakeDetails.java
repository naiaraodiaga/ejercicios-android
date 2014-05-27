package com.naiaraodiaga.earthquakecontentprovider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class EarthQuakeDetails extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		TextView text = (TextView) findViewById(R.id.textView1);
		Intent intent = getIntent();
		Log.d("NAIARA", "Intent: "+intent);
		String texto = null;
		if (intent != null){
			Log.d("NAIARA", "id: "+intent.getStringExtra("id"));
			texto = intent.getStringExtra("id");
		}
		text.setText(texto);
	}
}
