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
	ListFragment lista;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		if(savedInstanceState == null) {
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			fragmentTransaction.add(R.id.inputLayout, new InputFragment());
			fragmentTransaction.add(R.id.listLayout, new MiListaFragment(), "listLayout");
			fragmentTransaction.commit();
		}
	}

	@Override
	public void addToDo(EditText editText) {
		if(!editText.getText().toString().equalsIgnoreCase("")){
			((MiListaFragment)getFragmentManager().findFragmentByTag("listLayout")).addElement(editText.getText().toString());
		}
		
	}
	
	

//	public void onRestoreInstanceState(Bundle savedInstanceState) {
//		super.onRestoreInstanceState(savedInstanceState);
//		
//		listaTareas.addAll(savedInstanceState.getStringArrayList("listaTareas"));
//		adapter.notifyDataSetChanged();
//	}
//
//	@Override
//	public void onSaveInstanceState(Bundle savedInstanceState) {
//		savedInstanceState.putStringArrayList("listaTareas", listaTareas);
//
//		super.onSaveInstanceState(savedInstanceState);
//	}
	
	
}
