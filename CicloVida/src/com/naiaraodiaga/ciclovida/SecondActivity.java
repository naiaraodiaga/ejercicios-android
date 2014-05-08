package com.naiaraodiaga.ciclovida;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class SecondActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

		Log.d("TAG", "onCreate SecondActivity");
		
//		if (savedInstanceState == null) {
//			getFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
	}
	
	@Override
	protected void onStart() {
		Log.d("TAG", "onStart SecondActivity");
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		Log.d("TAG", "onResume SecondActivity");
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		Log.d("TAG", "onPause SecondActivity");
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		Log.d("TAG", "onStop SecondActivity");
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		Log.d("TAG", "onDestroy SecondActivity");
		super.onDestroy();
	}
	
	public void startMain(View v){
		Log.d("TAG", "startMain SecondActivity");
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	public void startThird(View v){
		Log.d("TAG", "startThird SecondActivity");
		Intent intent = new Intent(this, ThirdActivity.class);
		startActivity(intent);
	}
	
	public void close(View v){
		Log.d("TAG", "Close SecondActivity");
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_second,
					container, false);
			return rootView;
		}
	}

}
