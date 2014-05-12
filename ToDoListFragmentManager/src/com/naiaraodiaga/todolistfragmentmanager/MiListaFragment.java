package com.naiaraodiaga.todolistfragmentmanager;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MiListaFragment extends ListFragment{

	private ArrayList<String> listaTareas;
	private ArrayAdapter<String> adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		listaTareas = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, listaTareas);

		setListAdapter(adapter);

		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null) {
			listaTareas.addAll(savedInstanceState.getStringArrayList("listaTareas"));
			adapter.notifyDataSetChanged();
		}
	}


	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putStringArrayList("listaTareas", listaTareas);
		super.onSaveInstanceState(savedInstanceState);
	}
	
	

	public void addElement(String valor) {
		listaTareas.add(valor);
		adapter.notifyDataSetChanged();
	}
	
}
