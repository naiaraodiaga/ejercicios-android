package com.naiaraodiaga.intents;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Build;

public class SecondActivity extends Activity {
	EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

		Intent intent = getIntent();
		String texto = null;
		if (intent != null){
			texto = intent.getStringExtra("editTextPantalla1");
		}
		

		TextView textView = (TextView) findViewById(R.id.textViewPantalla2);
		if(texto != null){
			textView.setText(texto);
		}
		
		Button botonAceptar = (Button) findViewById(R.id.aceptar);

		OnClickListener onClickForm = new OnClickListener() {

			@Override
			public void onClick(View v) {
				aceptar();
			}
		};

		botonAceptar.setOnClickListener(onClickForm);

		Button botonAtras = (Button) findViewById(R.id.atras);
		botonAtras.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}

	public void aceptar() {
		editText = (EditText) findViewById(R.id.editTextPantalla2);
		Intent intent = new Intent();			// Creamos un intent vac’o como contenedor, bundle (porque no sabemos desde d—nde nos llaman, puede haber m‡s actividades)
		intent.putExtra("editTextPantalla2", editText.getText().toString());

		setResult(RESULT_OK, intent); // Si lo hacemos dentro de un escuchador, debemos poner Activity.RESULT_OK
		finish();
	}

}
