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
//	private String numberDisplayed = "";
//	private double resultNumber = 0.0;
	private TextView textView, resultView;
	private boolean ultimaEntradaEsOperador;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		calculadora = new Calculadora();
		resultView = (TextView)findViewById(R.id.textView1);
	}
	
	
	
	public void pressDigito(View v) {
		Button boton = (Button) v;
		String num = boton.getText().toString();
		
		Log.d("NAIARA", "num: "+num);
	
		calculadora.inputDigito(num);
		resultView.setText(calculadora.displayValor());
	}
	
	public void pressOperador(View v) {
		Button boton = (Button) v;
		String op = boton.getText().toString();
		
		Log.d("NAIARA", "op: "+op);
	
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
