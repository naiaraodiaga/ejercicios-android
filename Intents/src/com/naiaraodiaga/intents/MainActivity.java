package com.naiaraodiaga.intents;

import java.io.File;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.MediaStore;

public class MainActivity extends Activity {

	protected static final int REQUEST_FORM = 0;
	protected static final int REQUEST_IMAGE_CAPTURE = 1;
	private static final int REQUEST_CODE_PICK_CONTACTS = 2;
	ImageView imagen;
	EditText editText;
	TextView textView;
	File ruta;
	Bundle extras;
	Uri uriContact;
	String contactID; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		imagen = (ImageView) findViewById(R.id.imagen);
		editText = (EditText) findViewById(R.id.editTextPantalla1);
		textView = (TextView) findViewById(R.id.textViewPantalla1);
		
		Button botonForm = (Button) findViewById(R.id.form);
		
		OnClickListener onClickForm = new OnClickListener() {

			@Override
			public void onClick(View v) {
				requestForm();
			}
		};

		botonForm.setOnClickListener(onClickForm);

		Button botonCamara = (Button) findViewById(R.id.camara);
		OnClickListener onClickCamara = new OnClickListener() {

			@Override
			public void onClick(View v) {
				camara();
			}
		};
		botonCamara.setOnClickListener(onClickCamara);
		
		
		Button botonContactos = (Button) findViewById(R.id.contactos);
		OnClickListener onClickContactos = new OnClickListener() {

			@Override
			public void onClick(View v) {
				contactos();
			}
		};
		botonContactos.setOnClickListener(onClickContactos);
	}

	public void requestForm() {
		
		if(!editText.getText().toString().equalsIgnoreCase("")){
			Intent intent = new Intent(MainActivity.this, SecondActivity.class);
			intent.putExtra("editTextPantalla1", editText.getText().toString());
			startActivityForResult(intent, REQUEST_FORM);
		}
	}

	public void camara() {
		
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	    }
	    
//		if (Environment.getExternalStorageState().equals(
//				Environment.MEDIA_MOUNTED)) { 				// Si existe c‡mara
//
//			File directorio = new File(
//					Environment.getExternalStorageDirectory() + "/MisImagenes"); // Definimos el directorio
//
//			if (directorio.exists() == false) { 			// Si el directorio no existe, lo creamos
//				directorio.mkdirs();
//			}
//			ruta = new File(directorio, "imagen1.jpg");
//			Uri uriImagen = Uri.fromFile(ruta); 			// Obtenemos la uri
//			Intent takePictureIntent = new Intent(
//					MediaStore.ACTION_IMAGE_CAPTURE);
//			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriImagen);
//
//			startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//		}
	}
	
	public void contactos(){
		Intent contactsIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
		startActivityForResult(contactsIntent, REQUEST_CODE_PICK_CONTACTS);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case (REQUEST_FORM):
			
			if (resultCode == Activity.RESULT_OK) {
				String texto = data.getStringExtra("editTextPantalla2");
				textView.setText(texto);
			} else {
				textView.setText("");
			}
			break;
		case (REQUEST_IMAGE_CAPTURE):
			if (resultCode == RESULT_OK) {
				extras = data.getExtras();
				cargarImagen(extras);
			}
			break;
		case (REQUEST_CODE_PICK_CONTACTS): 
			if(resultCode == RESULT_OK){
				uriContact = data.getData();
				 
	            retrieveContactName();
	            retrieveContactNumber();
//	            retrieveContactPhoto();
			}
		}

	}
	
	public void cargarImagen(Bundle extras){
		if(extras != null){
			Bitmap imageBitmap = (Bitmap) extras.get("data");		    
//	        Bitmap image = BitmapFactory.decodeFile(ruta.getAbsolutePath());
	        imagen.setImageBitmap(imageBitmap);
		}
	}
	
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
		editText.setText(savedInstanceState.getString("editText"));
		textView.setText(savedInstanceState.getString("textView"));
		
		extras = savedInstanceState.getBundle("extras");
		cargarImagen(extras);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putBundle("extras", extras);
		
		savedInstanceState.putString("editText", editText.getText().toString());
		savedInstanceState.putString("textView", textView.getText().toString());
		
		super.onSaveInstanceState(savedInstanceState);
	}
	
	
	private void retrieveContactName() {
		 
        String contactName = null;
 
        // querying contact data store
        Cursor cursor = getContentResolver().query(uriContact, null, null, null, null);
 
        if (cursor.moveToFirst()) {
 
            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.
 
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }
 
        cursor.close();
 
        Log.d("NAIARA", "Contact Name: " + contactName);
        textView.setText(contactName);
 
    }
	
	private void retrieveContactNumber() {
		 
        String contactNumber = null;
 
        // getting contacts ID
        Cursor cursorID = getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);
 
        if (cursorID.moveToFirst()) {
 
            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }
 
        cursorID.close();
 
        Log.d("NAIARA", "Contact ID: " + contactID);
 
        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
 
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
 
                new String[]{contactID},
                null);
 
        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }
 
        cursorPhone.close();
 
        Log.d("NAIARA", "Contact Phone Number: " + contactNumber);
        
        String nombre = textView.getText().toString();
        Log.d("NAIARA", "Contact Name: " + nombre);
        textView.setText(nombre + " " + contactNumber);
    }
}
