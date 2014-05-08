package com.naiaraodiaga.calculadora;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity {

	private Calculadora calculadora;
	private TextView resultView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		calculadora = new Calculadora();
		resultView = (TextView)findViewById(R.id.textView1);
	}
	

	// Called after onCreate has finished, use to restore UI state @Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	     super.onRestoreInstanceState(savedInstanceState);
	     // Restore UI state from the savedInstanceState.
	     // This bundle has also been passed to onCreate.
	     calculadora.setDisplay((String)savedInstanceState.get("display"));
	     resultView.setText(calculadora.displayValor());
	     calculadora.setOperador((String)savedInstanceState.get("operador"));
	     calculadora.setOperando1((String)savedInstanceState.get("operando1"));
	     calculadora.setUltimaEntradaEsOperador(Boolean.parseBoolean((String)savedInstanceState.get("ultimaEntradaEsOperador")));
	     
	}
	
	// Called to save UI state changes at the end of the active lifecycle.
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	     // Save UI state changes to the savedInstanceState.
	     // This bundle will be passed to onCreate and onRestoreInstanceState if the process is killed and restarted by the run time.
		savedInstanceState.putString("display", calculadora.displayValor());
		savedInstanceState.putString("operador", calculadora.getOperador());
		savedInstanceState.putString("operando1", calculadora.getOperando1());
		savedInstanceState.putString("ultimaEntradaEsOperador", String.valueOf(calculadora.isUltimaEntradaEsOperador()));
	    super.onSaveInstanceState(savedInstanceState);
	}
	
	
	public void pressDigito(View v) {
		Button boton = (Button) v;
		String num = boton.getText().toString();
		
		calculadora.inputDigito(num);
		resultView.setText(calculadora.displayValor());
	}
	
	public void pressOperador(View v) {
		Button boton = (Button) v;
		String op = boton.getText().toString();
		
		calculadora.inputOperador(op);
		resultView.setText(calculadora.displayValor());
	}
	
	
	public void pressIgual (View v){
		calculadora.inputIgual();
		resultView.setText(calculadora.displayValor());
	}
	

	public void pressBorrar (View v){
		calculadora.inputBorrar();
		resultView.setText(calculadora.displayValor());
	}
	
}
