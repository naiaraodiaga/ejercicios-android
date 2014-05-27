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

		// earthquakeList.addAll(db.query(Double.parseDouble(mag)));

		// quakesList = earthquakeDB.selectByMag(Double.parseDouble(mag));

		// adapter = new QuakeLazyAdapter(getActivity(), quakesList);

		// setListAdapter(adapter); // Si no ponemos el setListAdapter, pondr�
		// loading... Lo ideal es poner el setListAdapter cuando se cargue la
		// lista
		adapter.setViewBinder(new EarthQuakeViewBinder());

		setListAdapter(adapter);

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	// @Override
	// public void refreshQuakesList(ArrayList<Earthquake> earthquake) {
	// // quakesList.addAll(earthquake);
	// // adapter.notifyDataSetChanged();
	// Log.d("NAIARA", "refreshQuakesList");
	//
	// }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// adapter.notifyDataSetChanged();

		new DownloadQuakesTask(getActivity()).execute(getString(R.string.Ruta));

		getLoaderManager().initLoader(QUAKES_LIST, null, this);
	}

	@Override
	public void onResume() { // Justo antes de obtener la vista, mostramos los
								// datos
		super.onResume();

		// String mag = PreferenceManager.getDefaultSharedPreferences(
		// getActivity()).getString(
		// getActivity().getString(R.string.MAG_KEY), "0");
		// Log.d("NAIARA", "mag: " + mag);
		//
		// ContentResolver cr = getActivity().getContentResolver();
		//
		// String where = MyContentProvider.MAGNITUDE + " >= ?";
		// String whereArgs[] = { mag };
		// String order = null;
		//
		// Cursor resultCursor = cr.query(MyContentProvider.CONTENT_URI, from,
		// where, whereArgs, order);
		//
		// while (resultCursor.moveToNext()) {
		// Log.d("NAIARA", resultCursor.getString(resultCursor
		// .getColumnIndex(MyContentProvider.PLACE)));
		// }
		//
		// adapter.swapCursor(resultCursor); // Esto es como el notifyChanges,
		// le
		// // decimos al adaptador el cursor
		// // que tiene que pintar
		// setListAdapter(adapter); // Ponemos el setListAdapter porque en
		// cuanto
		// // se cargue la lista, hay que notificar el
		// // cambio
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

		String mag = PreferenceManager.getDefaultSharedPreferences(
				getActivity()).getString(
				getActivity().getString(R.string.MAG_KEY), "0");
		Log.d("NAIARA", "mag: " + mag);

		ContentResolver cr = getActivity().getContentResolver();

		String where = MyContentProvider.MAGNITUDE + " >= ?";
		String whereArgs[] = { mag };
		String order = null;

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
