package com.naiaraodiaga.earthquakecontentprovider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class EarthQuakeDetails extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		TextView text = (TextView) findViewById(R.id.textView1);
		Intent intent = getIntent();
		String texto = null;
		if (intent != null){
			texto = intent.getStringExtra("idStr");
		}
		text.setText(texto);
	}
}
