package com.example.dialogos;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity implements Comunicador {

	public void abrirDialogo(View v){
		MiDialogo elDialogo = new MiDialogo();
		//elDialogo.laActividad = (Comunicador)this;
		elDialogo.setLaActividad(this);
		elDialogo.show(getFragmentManager(), "dialog");
		
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

	@Override
	public void aQueBotonLeDiClick(int boton) {
		TextView elTexto = (TextView) findViewById(R.id.textViewRegultado);
		
		switch(boton){
		case 0://Se hizo click en boton negativo
			elTexto.setText("Hiciste click en negativo");
			elTexto.setTextColor(getResources().getColor(R.color.red_negativo));
			
			break;
		case 1://Se hizo click en boton positivo
			elTexto.setText("Hiciste click en positivo");
			elTexto.setTextColor(getResources().getColor(R.color.green_positivo));
			break;
			default://Ocurrió alguna cosa extraña
				Toast.makeText(getApplicationContext(), "Oops", Toast.LENGTH_SHORT).show();
		}
	}

}
























