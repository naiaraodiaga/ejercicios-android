package com.naiaraodiaga.earthquake;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.naiaraodiaga.earthquake.DownloadQuakesTask.IQuakesList;

public class QuakesListFragment extends ListFragment implements IQuakesList {

	// private ArrayList<Earthquake> quakesList;
	// private QuakeLazyAdapter adapter;
	private SimpleCursorAdapter adapter;

	private String[] from = { MyContentProvider.MAGNITUDE,
			MyContentProvider.PLACE,
			MyContentProvider.TIME,
			MyContentProvider.QUAKE_ID };
	private int[] to = { R.id.magnitude, R.id.place, R.id.time };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// EarthQuakeDB earthquakeDB =
		// EarthQuakeDB.getDB(inflater.getContext());

		

		adapter = new SimpleCursorAdapter(getActivity(),
				R.layout.list_row_fragment, null, from, to, 0);

		// earthquakeList.addAll(db.query(Double.parseDouble(mag)));

		// quakesList = earthquakeDB.selectByMag(Double.parseDouble(mag));

		// adapter = new QuakeLazyAdapter(getActivity(), quakesList);

		setListAdapter(adapter);
		adapter.setViewBinder(new EarthQuakeViewBinder());

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void refreshQuakesList(ArrayList<Earthquake> earthquake) {
		// quakesList.addAll(earthquake);
//		adapter.notifyDataSetChanged();
		Log.d("NAIARA", "refreshQuakesList");

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// adapter.notifyDataSetChanged();

		new DownloadQuakesTask(getActivity(), this)
				.execute(getString(R.string.Ruta));

	}

	@Override
	public void onResume() {
		super.onResume();

		String mag = PreferenceManager.getDefaultSharedPreferences(
				getActivity()).getString(
				getActivity().getString(R.string.MAG_KEY), "0");
		Log.d("NAIARA", "mag: " + mag);

		ContentResolver cr = getActivity().getContentResolver();

		String where = MyContentProvider.MAGNITUDE + " >= ?";
		String whereArgs[] = { mag };
		String order = null;

		Cursor resultCursor = cr.query(MyContentProvider.CONTENT_URI, from,
				where, whereArgs, order);

		while (resultCursor.moveToNext()) {
			Log.d("NAIARA", resultCursor.getString(resultCursor
					.getColumnIndex(MyContentProvider.PLACE)));
		}

		adapter.swapCursor(resultCursor); // Esto es como el notifyChanges
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Earthquake quake = this.adapter.getItem(position);
		// String idStr = quake.getIdStr();
		//
		 Intent intent = new Intent(getActivity(), EarthQuakeDetails.class);
		 intent.putExtra("id", id);
		 startActivity(intent);
	}

}
