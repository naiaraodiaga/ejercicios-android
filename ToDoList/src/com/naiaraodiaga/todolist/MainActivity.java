package com.naiaraodiaga.todolist;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity {

	private ArrayList<String> listaTareas;
	private ArrayAdapter<String> adapter;
	private EditText elements;
	private ListView tareas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listaTareas = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listaTareas);
		elements = (EditText) findViewById(R.id.elements);
		tareas = (ListView) findViewById(R.id.listaTareas);
		tareas.setAdapter(adapter);
		
		elements.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_DOWN){
					if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER){
						addElementoLista();
						return true;
					}
				}
				return false;
				
			}
		});
	}

	public void addTarea(View v) {
		addElementoLista();
	}
	
	public void addElementoLista(){
		if(!elements.getText().toString().equalsIgnoreCase("")){
			listaTareas.add(0, elements.getText().toString());
			adapter.notifyDataSetChanged();
			elements.setText("");
		}
		
	}

	// Called after onCreate has finished, use to restore UI state @Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		// Restore UI state from the savedInstanceState.
		// This bundle has also been passed to onCreate.
		listaTareas
				.addAll(savedInstanceState.getStringArrayList("listaTareas"));
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
