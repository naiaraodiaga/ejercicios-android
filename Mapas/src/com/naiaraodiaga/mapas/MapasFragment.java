package com.naiaraodiaga.mapas;

import java.util.Date;

import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MapasFragment extends Fragment {

	private String serviceString = Context.LOCATION_SERVICE;
	private String bestProvider;
	private LocationManager locationManager;
	private Button refresh;
	private Button mapa;
	private TextView latitud, longitud, altitud;
	private int t = 5000; // milliseconds
	private int distance = 5; // meters
	private final int locationUpdateRC = 0;
	private int flags = PendingIntent.FLAG_UPDATE_CURRENT;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		latitud = (TextView) rootView.findViewById(R.id.latitud);
		longitud = (TextView) rootView.findViewById(R.id.longitud);
		altitud = (TextView) rootView.findViewById(R.id.altitud);
		refresh = (Button) rootView.findViewById(R.id.botonActualizar);
		mapa = (Button) rootView.findViewById(R.id.button1);

	
		bestProvider = getBestProvider();
		Log.d("NAIARA", "bestProvider: " + bestProvider);
		

		refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.d("NAIARA", "Actualizar");
				obtenerUltimaLocalizacion();
			}
		});
		
		
		mapa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getActivity(), MyMapActivity.class);
				startActivity(i);
				
			}
		});
		

		
		
		String serviceString = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) getActivity().getSystemService(serviceString);
		
		obtenerUltimaLocalizacion();
		obtenerLocalizacion();
		
		return rootView;
	}

	public void obtenerUltimaLocalizacion() {
		bestProvider = this.getBestProvider();
		Location lastLocation = locationManager.getLastKnownLocation(bestProvider);
		
		if (lastLocation != null) {
			actualizarPantalla(lastLocation);
			Log.d("NAIARA", String.valueOf(lastLocation.getLatitude()));
		}
		else {
			Log.d("NAIARA", "No hay œltima localizaci—n");
		}

	}
	
	private String getBestProvider() {
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		// criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setAltitudeRequired(true);
		criteria.setSpeedRequired(false);

		// return locationManager.getBestProvider(criteria, true);
		return LocationManager.GPS_PROVIDER;
	}

	public void obtenerLocalizacion() {
		bestProvider = getBestProvider();

		LocationListener locListener = new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				Intent intent = new Intent(getActivity(),
						MyLocationUpdateReceiver.class);
				PendingIntent pendingIntent = PendingIntent
						.getBroadcast(getActivity(), locationUpdateRC,
								intent, flags);
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location location) {
				actualizarPantalla(location);
				
			}
		}; 
		locationManager.requestLocationUpdates(bestProvider, t, distance,locListener);

	}
	
	public void actualizarPantalla(Location location){
		latitud.setText(String.valueOf(location.getLatitude()));
		longitud.setText(String.valueOf(location.getLongitude()));
		altitud.setText(String.valueOf(location.getAltitude()));
	}
}
