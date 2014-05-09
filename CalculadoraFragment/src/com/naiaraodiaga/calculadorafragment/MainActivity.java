package com.naiaraodiaga.calculadorafragment;


import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity implements CalculadoraBasicaFragment.ICalculadora{

	private Calculadora calculadora;
	private TextView resultView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		calculadora = new Calculadora();
		resultView = (TextView)findViewById(R.id.textView1);
		
		
		/*
		// array int
		 * botones = {R.id.button1, R.id.button2...}
		 * for(botones){
		 * 	(Button) v.setOnClick
		 * 		onClick(View v){
		 * 			activity.digito(n);
		 * 	}
		 * }
		*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

		@Override
		public void pressDigito(String num) {
			calculadora.inputDigito(num);
			resultView.setText(calculadora.displayValor());
			
		}

		@Override
		public void pressOperador(String op) {
			calculadora.inputOperador(op);
			resultView.setText(calculadora.displayValor());
			
		}

		@Override
		public void pressIgual() {
			calculadora.inputIgual();
			resultView.setText(calculadora.displayValor());
			
		}

		@Override
		public void pressBorrar() {
			calculadora.inputBorrar();
			resultView.setText(calculadora.displayValor());
		}
		

}
