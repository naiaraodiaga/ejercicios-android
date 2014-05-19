package com.naiaraodiaga.preferences;

import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyPreferenceActivity extends PreferenceActivity {

	private static final int SHOW_PREFERENCES = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void onBuildHeaders(List<Header> target) {
		loadHeadersFromResource(R.xml.userpreferenceheaders, target);
	}


}