package com.naiaraodiaga.todolistfragmentmanager;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class InputFragment extends Fragment{
	
	private IInput iInput;

	public interface IInput {
		public void addToDo(EditText text);
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.input_fragment, container,
				false);

		final EditText editText = (EditText) rootView.findViewById(R.id.editText1);
		
		editText.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_DOWN){
					if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER){
						iInput.addToDo(editText);
						return true;
					}
				}
				return false;
				
			}
		});	
		
		
		OnClickListener onClick = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Button b = (Button) v;
				
				iInput.addToDo(editText);

			}
		};
		
		Button botones = (Button) rootView.findViewById(R.id.addButton);
		botones.setOnClickListener(onClick);
		
		
		return rootView;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		iInput = (IInput) activity;
	}
	
	
}
