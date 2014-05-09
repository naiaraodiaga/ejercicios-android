package com.naiaraodiaga.calculadorafragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CalculadoraCientificaFragment extends Fragment{
	public CalculadoraCientificaFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_scientific, container,
				false);
		return rootView;
	}
}
