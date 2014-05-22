package com.naiaraodiaga.earthquake;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class CustomizedListView extends Activity {
	// public static final String PLACE = "place";
	// public static final String TIME = "time";
	// public static final String DETAIL = "detail";
	// public static final String MAGNITUDE = "magnitude";
	// public static final String LAT = "lat";
	// public static final String LONG = "long";
	// public static final String URL = "url";

	private ListView list;
	private LazyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		list = (ListView) findViewById(R.id.list);
	}
}
