package com.naiaraodiaga.earthquakecontentprovider;

import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class QuakesListFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	// private ArrayList<Earthquake> quakesList;
	// private QuakeLazyAdapter adapter;
	private SimpleCursorAdapter adapter;
	private static int QUAKES_LIST = 1;
	
	String mag = "0";

	private String[] from = { MyContentProvider.MAGNITUDE,
			MyContentProvider.PLACE, MyContentProvider.TIME,
			MyContentProvider.QUAKE_ID }; // IMPORTANTE poner el id al final
	private int[] to = { R.id.magnitude, R.id.place, R.id.time };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// EarthQuakeDB earthquakeDB =
		// EarthQuakeDB.getDB(inflater.getContext());

		adapter = new SimpleCursorAdapter(getActivity(),
				R.layout.list_row_fragment, null, from, to, 0); // Pasamos el
																// cursor vac�o
																// porque a�n no
																// queremos
																// mostrar datos


		// setListAdapter(adapter); // Si no ponemos el setListAdapter, pondr�
		// loading... Lo ideal es poner el setListAdapter cuando se cargue la
		// lista
		adapter.setViewBinder(new EarthQuakeViewBinder());

		setListAdapter(adapter);

		return super.onCreateView(inflater, container, savedInstanceState);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
                           
		// adapter.notifyDataSetChanged();

		this.refreshEarthQuakes();

		getLoaderManager().initLoader(QUAKES_LIST, null, this);
	}
	
	public void refreshEarthQuakes() {
//		new DownloadQuakesTask(getActivity()).execute(getString(R.string.Ruta));
		
		Intent intent = new Intent(getActivity(), QuakesDownloaderService.class);
		getActivity().startService(intent);
	}

	@Override
	public void onResume() { // Justo antes de obtener la vista, mostramos los
								// datos
		super.onResume();

		
		getLoaderManager().restartLoader(QUAKES_LIST, null, this); // Reiniciamos el loader para que coja todos los cambios
		 
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Earthquake quake = this.adapter.getItem(position);
		// String idStr = quake.getIdStr();
		Log.d("NAIARA", "LIST - id: " + id);
		Log.d("NAIARA", "LIST - position: " + position);
		Log.d("NAIARA", "LIST - view: " + v);
		Log.d("NAIARA", "LIST - listview: " + l);
		Intent intent = new Intent(getActivity(), EarthQuakeDetails.class);
		intent.putExtra("id", id);
		startActivity(intent);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {

		mag = PreferenceManager.getDefaultSharedPreferences(
				getActivity()).getString(
				getActivity().getString(R.string.MAG_KEY), "0");
		Log.d("NAIARA", "mag: " + mag);

		ContentResolver cr = getActivity().getContentResolver();

		String where = MyContentProvider.MAGNITUDE + " >= ?";
		String whereArgs[] = { mag };
		String order = MyContentProvider.TIME + " DESC";

		CursorLoader cursorLoader = new CursorLoader(getActivity(),
				MyContentProvider.CONTENT_URI, from, where, whereArgs, order);

		setListAdapter(adapter);

		return cursorLoader;

	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		adapter.swapCursor(cursor);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);

	}

}
