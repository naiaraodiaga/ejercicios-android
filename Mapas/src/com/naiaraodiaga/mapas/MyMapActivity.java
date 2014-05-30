package com.naiaraodiaga.mapas;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyMapActivity extends Activity {

	private double lat, lon;
	private String ciudad; 
	private HashMap<Marker, String> quakemarkers = new HashMap<Marker, String>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layout);
		
//		lat = 40.417325;
//		lon = -3.683081;
		lat = 43.3662954;
		lon = -2.5046162;
		
//		ciudad = "Madrid";
		ciudad = "Lekeitio";
		
		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lon)).title(ciudad);
		marker.snippet("El centro del mundo");
		map.addMarker(marker);
		
		MarkerOptions marker2 = new MarkerOptions().position(new LatLng(40.417325, -3.683081)).title(ciudad);
		map.addMarker(marker2);
		
		map.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				Log.d("NAIARA", "Yujuuuu: "+marker.getId());
				return false;
			}
		});
		
//		ArrayList<String> array = new ArrayList<String>();
//		array.con
		
		
		LatLng lugar = new LatLng(lat, lon);
		CameraPosition camPos = new CameraPosition.Builder().target(lugar).zoom(18).bearing(45).tilt(70).build();
		
		CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(camPos);
		map.animateCamera(camUpd);
		
	}
	
}
