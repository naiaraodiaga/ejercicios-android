package com.naiaraodiaga.earthquake;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.naiaraodiaga.earthquake.DownloadQuakesTask.IQuakesList;

public class QuakesListFragment extends ListFragment implements IQuakesList {

	private ArrayList<Earthquake> quakesList;
	private QuakeLazyAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		EarthQuakeDB earthquakeDB = EarthQuakeDB.getDB(inflater.getContext());

		String mag = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(getActivity().getString(R.string.MAG_KEY), "0");
		Log.d("NAIARA", "mag: "+mag);

		
//		earthquakeList.addAll(db.query(Double.parseDouble(mag)));
		
		quakesList = earthquakeDB.selectByMag(Double.parseDouble(mag));
		
		adapter = new QuakeLazyAdapter(getActivity(), quakesList);

		setListAdapter(adapter);

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void refreshQuakesList(ArrayList<Earthquake> earthquake) {
		quakesList.addAll(earthquake);
		adapter.notifyDataSetChanged();
		Log.d("NAIARA", "refreshQuakesList");

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		adapter.notifyDataSetChanged();

		new DownloadQuakesTask(getActivity(), this)
				.execute(getString(R.string.Ruta));

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Earthquake quake = this.adapter.getItem(position);
		String idStr = quake.getIdStr();
		
		Intent intent = new Intent(getActivity(), EarthQuakeDetails.class);
		intent.putExtra("idStr", idStr);
		startActivity(intent);
	}

	// @Override
	// public void onActivityCreated(Bundle savedInstanceState) {
	// super.onActivityCreated(savedInstanceState);
	// if (savedInstanceState != null) {
	// quakesList.addAll(savedInstanceState.getStringArrayList("quakesList"));
	// adapter.notifyDataSetChanged();
	// }
	// }

	// @Override
	// public void onSaveInstanceState(Bundle savedInstanceState) {
	// savedInstanceState.putStringArrayList("quakesList", quakesList);
	// super.onSaveInstanceState(savedInstanceState);
	// }
	//
	// public void addElement(String valor) {
	// quakesList.add(valor);
	// adapter.notifyDataSetChanged();
	// }

}
