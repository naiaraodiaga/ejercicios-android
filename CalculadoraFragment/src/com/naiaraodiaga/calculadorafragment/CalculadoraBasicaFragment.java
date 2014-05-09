package com.naiaraodiaga.calculadorafragment;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

@SuppressLint("Recycle")
public class CalculadoraBasicaFragment extends Fragment {

	private ICalculadora iCalculadora;

	public interface ICalculadora {
		public void pressDigito(String num);

		public void pressOperador(String num);

		public void pressIgual();

		public void pressBorrar();
	}

	public CalculadoraBasicaFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);

		TypedArray digitos = getResources().obtainTypedArray(
				R.array.arrayDigitos);

		OnClickListener onClickDigito = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Button b = (Button) v;
				iCalculadora.pressDigito(b.getText().toString());

			}
		};

		Button botones;
		for (int i = 0; i < digitos.length(); i++) {
			botones = (Button) rootView.findViewById(digitos
					.getResourceId(i, 0));
			botones.setOnClickListener(onClickDigito);
		}

		
		
		TypedArray operadores = getResources().obtainTypedArray(
				R.array.arrayOperadores);

		OnClickListener onClickOperador = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Button b = (Button) v;
				iCalculadora.pressOperador(b.getText().toString());
			}
		};

		for (int i = 0; i < operadores.length(); i++) {
			botones = (Button) rootView.findViewById(operadores.getResourceId(
					i, 0));
			botones.setOnClickListener(onClickOperador);
		}

		
		
		OnClickListener onClickIgual = new OnClickListener() {

			@Override
			public void onClick(View v) {
				iCalculadora.pressIgual();
			}
		};
		

		rootView.findViewById(R.id.buttonIgual).setOnClickListener(
				onClickIgual);

		
		
		OnClickListener onClickBorrar = new OnClickListener() {

			@Override
			public void onClick(View v) {
				iCalculadora.pressBorrar();
			}
		};
		

		rootView.findViewById(R.id.buttonDel).setOnClickListener(
				onClickBorrar);
		
		
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		iCalculadora = (ICalculadora) activity;
	}

}
