package com.talentoti.weather;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements Jibaro {
	
	EditText query, city, temperature, pressure, humidity;
	TextView log;
	JSONParser jsonParser;
	XMLParser xmlParser;
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		query = (EditText) findViewById(R.id.query);
		city = (EditText) findViewById(R.id.city);
		temperature = (EditText) findViewById(R.id.temperature);
		pressure = (EditText) findViewById(R.id.pressure);
		humidity = (EditText) findViewById(R.id.humidity);
		log = (TextView) findViewById(R.id.log);
		
		jsonParser = new JSONParser(null, this);
		xmlParser = new XMLParser(null, this);
	}

	public void searchQuery(View v){
		String theQuery = query.getText().toString();
		if(!theQuery.isEmpty()){
			switch (v.getId()) {
			case R.id.searchBtnJson:
				jsonParser.setQuery(theQuery);
				jsonParser.doMyJob();	
				break;
			case R.id.searchBtnXml:
				xmlParser.setQuery(theQuery);
				xmlParser.doMyJob();
				break;

			default:
				
				break;
			}
			
		}else{
			Toast.makeText(this, "El query está vacío", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void clearFields(View v){
		// Limpio los textos
		query.setText("");
		city.setText("");
		temperature.setText("");
		pressure.setText("");
		humidity.setText("");
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
	public void enviarDatos(String[] datos) {
		if(datos != null && datos.length == 4){
			city.setText(datos[0]);
			temperature.setText(datos[1]);
			pressure.setText(datos[2]);
			humidity.setText(datos[3]);	
		}else{
			clearFields(null);
		}
	}

	@Override
	public void actualizarProgreso(String etapa) {
		log.append("\n"+etapa);
	}

}