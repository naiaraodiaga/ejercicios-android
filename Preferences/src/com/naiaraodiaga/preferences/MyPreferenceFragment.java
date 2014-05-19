package com.naiaraodiaga.preferences;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.widget.TextView;

public class MyPreferenceFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.userpreferences);
	}
}
