package com.naiaraodiaga.todolistfragmentmanager;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.naiaraodiaga.todolistfragmentmanager.InputFragment.IInput;

public class MainActivity extends Activity implements IInput {
	private ArrayList<String> listaTareas;
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ListFragment lista = new ListFragment();

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(R.id.inputLayout, new InputFragment());
		fragmentTransaction.replace(R.id.listLayout, lista);
		fragmentTransaction.commit();

		listaTareas = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listaTareas);

		lista.setListAdapter(adapter);
	}

	@Override
	public void addToDo(EditText editText) {
		if (!editText.getText().toString().equalsIgnoreCase("")) {
			listaTareas.add(0, editText.getText().toString());
			adapter.notifyDataSetChanged();
			editText.setText("");
		}
	}

	// Called after onCreate has finished, use to restore UI state @Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		// Restore UI state from the savedInstanceState.
		// This bundle has also been passed to onCreate.
		listaTareas.addAll(savedInstanceState.getStringArrayList("listaTareas"));
		adapter.notifyDataSetChanged();
	}

	// Called to save UI state changes at the end of the active lifecycle.
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Save UI state changes to the savedInstanceState.
		// This bundle will be passed to onCreate and onRestoreInstanceState if
		// the process is killed and restarted by the run time.
		savedInstanceState.putStringArrayList("listaTareas", listaTareas);

		super.onSaveInstanceState(savedInstanceState);
	}
}
