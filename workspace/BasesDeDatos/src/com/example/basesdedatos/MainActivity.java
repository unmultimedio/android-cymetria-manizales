package com.example.basesdedatos;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Ayudante miAyudante;
	
	public void guardarUsuario(View v){
		EditText nombre = (EditText) findViewById(R.id.editTextName);
		EditText bebida = (EditText) findViewById(R.id.editTextDrink);
		EditText deporte = (EditText) findViewById(R.id.editTextSport);
		
		try {
			
			Usuario nuevo = new Usuario(
					nombre.getText().toString(),
					bebida.getText().toString(),
					deporte.getText().toString());
			boolean resultado = miAyudante.insertarUsuario(nuevo);
			
			String mensaje;
			if(resultado) mensaje = "Ingresado exitosamente";
			else mensaje = "No pudo ser ingresado";
			
			Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
			
		} catch (Exception e) {
			
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
			
		}
	}
	
	public void consultarUsuario(View v){
		List<Usuario> usuariosEnDB = miAyudante.consultarUsuarios();
		String toast = "";
		if(usuariosEnDB != null){
			for (Usuario usuario : usuariosEnDB) {
				toast += String.format("%d|%s|%s|%s\n", usuario.id, usuario.name, usuario.drink, usuario.sport);
			}
		}else{
			toast += "DB Vacía";
		}
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
		
		ListView laListaDeUsuarios = (ListView)findViewById(R.id.listViewUsers);
		
		laListaDeUsuarios.setAdapter(new MyCustomAdapter(this, R.layout.row_usuario, usuariosEnDB));
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		miAyudante = new Ayudante(getApplicationContext());
		miAyudante.prepareDataBases();
	}
	
	public void limpiarTablaUsuario(View v){
		miAyudante.limpiarTablaUsuarios();
		Toast.makeText(this, "Tabla Usuario: LIMPIA!", Toast.LENGTH_SHORT).show();
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
