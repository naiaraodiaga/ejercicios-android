package com.naiaraodiaga.earthquake;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class QuakesListFragment extends ListFragment {

	private ArrayList<Earthquake> quakesList;
	private ArrayAdapter<Earthquake> adapter;
	
	public QuakesListFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		EarthQuakeDB earthquakeDB = new EarthQuakeDB(inflater.getContext());
		
		quakesList = earthquakeDB.selectByMag(0);
		adapter = new ArrayAdapter<Earthquake>(inflater.getContext(), android.R.layout.simple_list_item_1, quakesList);

		setListAdapter(adapter);

		return super.onCreateView(inflater, container, savedInstanceState);
	}
	

//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//		if (savedInstanceState != null) {
//			quakesList.addAll(savedInstanceState.getStringArrayList("quakesList"));
//			adapter.notifyDataSetChanged();
//		}
//	}


//	@Override
//	public void onSaveInstanceState(Bundle savedInstanceState) {
//		savedInstanceState.putStringArrayList("quakesList", quakesList);
//		super.onSaveInstanceState(savedInstanceState);
//	}
//	
//	public void addElement(String valor) {
//		quakesList.add(valor);
//		adapter.notifyDataSetChanged();
//	}
	
}
