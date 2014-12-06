package com.example.preferenciascompartidas;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	public void getSavedPrefences(View v){
		//Quiero leer las preferencias guardadas
		SharedPreferences prefs = this.getPreferences(MODE_PRIVATE);
		
		String pref1 = prefs.getString("pref1", getResources().getString(R.string.no_saved_pref));
		String pref2 = String.valueOf(prefs.getInt("pref2", 0));
		String pref3 = String.valueOf(prefs.getLong("pref3", (long)'0'));
		
		//Mostrar los valores en pantalla
		EditText caja1, caja2, caja3;
		caja1 = (EditText)findViewById(R.id.editText1);
		caja2 = (EditText)findViewById(R.id.editText2);
		caja3 = (EditText)findViewById(R.id.editText3);
		
		caja1.setText(pref1);
		caja2.setText(pref2);
		caja3.setText(pref3);
		
	}
	
	public void setPreferencia(View v){
		
		SharedPreferences prefs = this.getPreferences(MODE_PRIVATE);
		
		SharedPreferences.Editor editor = prefs.edit();
		
		switch (v.getId()) {
		case R.id.setPref1:
			//Se hizo click en el botón 1
			
			EditText edit1 = (EditText) findViewById(R.id.editText1);
			String elTexto = edit1.getText().toString();
			if(!elTexto.isEmpty()){
				editor.putString("pref1", elTexto);
				editor.commit();
				Toast.makeText(this, "Preferencia 1 guardada exitosamente", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, "El texto está vacío", Toast.LENGTH_SHORT).show();
			}
			
			break;
			
		case R.id.setPref2:
			EditText edit2 = (EditText) findViewById(R.id.editText2);
			try{
				int elNumero = Integer.parseInt(edit2.getText().toString());
				editor.putInt("pref2", elNumero);
				editor.commit();
				Toast.makeText(this, "Preferencia 2 guardada exitosamente", Toast.LENGTH_SHORT).show();
			}catch(Exception e){
				//Lo ingresado no es un entero
				Toast.makeText(this, "No has ingresado un número", Toast.LENGTH_SHORT).show();
			}
			break;
			
		case R.id.setPref3:
			EditText edit3 = (EditText) findViewById(R.id.editText3);
			String elTexto3 = edit3.getText().toString();
			if(!elTexto3.isEmpty()){
				char char3 = elTexto3.charAt(0);
				editor.putLong("pref1", (long)char3);
				editor.commit();
				Toast.makeText(this, "Preferencia 3 guardada exitosamente", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, "El texto está vacío", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			Toast.makeText(this, "Oops", Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
