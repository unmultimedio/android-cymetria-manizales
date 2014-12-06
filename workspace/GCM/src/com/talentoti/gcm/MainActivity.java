package com.talentoti.gcm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class MainActivity extends Activity {

	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private final static String KEY_REG_ID = "com.talentoti.gcm.regId";
	// ID del proyecto
	private final static String SENDER_ID = "852925513117";
	private final static String SERVER_URL = "http://labs.kaputlab.co/gcm_server_php/register.php";

	Context context;

	EditText name, email;
	String nameStr, emailStr;
	TextView log;

	public void tryRegister(View v) {
		// Intentar el registro
		
		nameStr = name.getText().toString();
		emailStr = email.getText().toString();
		
		if(nameStr.isEmpty() || emailStr.isEmpty()){
			Toast.makeText(this, "Algún campo está vacío", Toast.LENGTH_SHORT).show();
		}else{
			if (existeRegIdEnPreferencias()) {
				// Saltar el paso de registrar, esperando notificaciones
				log.append("\nYa estás registrado en GCM, esperando notificaciones...");
			} else {
				// Pedir un regId, y enviar a back
				askToGCM();
			}
		}
	}

	private void askToGCM() {
		AsyncTask<Void, String, Void> gcmWorker = new AsyncTask<Void, String, Void>() {

			@Override
			protected void onProgressUpdate(String... values) {
				super.onProgressUpdate(values);
				log.append("\n" + values[0]);
			}

			@Override
			protected Void doInBackground(Void... params) {

				GoogleCloudMessaging gcm = GoogleCloudMessaging
						.getInstance(context);

				try {
					String regId = gcm.register(SENDER_ID);
					publishProgress("Reg Id recibido: \"" + regId + "\"");

					// Guardar el regId en preferencias
					if(guardarEnPreferencias(regId)){
						publishProgress("Preferencias guardadas exitosamente en local.");
					}else{
						publishProgress("Preferencias no pudieron ser guardadas.");
					}
					
					registrarEnMiPropioServer(regId);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return null;
			}

			private boolean registrarEnMiPropioServer(String regId) {
				HttpClient cliente = new DefaultHttpClient();
				HttpPost postObj = new HttpPost(SERVER_URL);
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("name", nameStr));
				params.add(new BasicNameValuePair("email", emailStr));
				params.add(new BasicNameValuePair("regId", regId));
				
				try {
					postObj.setEntity(new UrlEncodedFormEntity(params));
					
					HttpResponse respuesta =  cliente.execute(postObj);
					publishProgress("Conexión exitosa con el backend, cod("+respuesta.getStatusLine().getStatusCode()+")");					
					
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					publishProgress("Error con los parámetros post");
				} catch (IOException e) {
					e.printStackTrace();
					publishProgress("Error con la conexión");
				}
				
				return false;
			}

			private boolean guardarEnPreferencias(String regId) {
				SharedPreferences prefs = getPreferences(MODE_PRIVATE);
				SharedPreferences.Editor editor = prefs.edit();
				editor.putString(KEY_REG_ID, regId);
				return editor.commit();
			}

		};

		gcmWorker.execute(null, null, null);
	}

	private boolean existeRegIdEnPreferencias() {

		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		if (prefs.contains(KEY_REG_ID)) {
			return true;
		}
		return false;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		context = getApplicationContext();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		log = (TextView) findViewById(R.id.textViewLog);
		name = (EditText) findViewById(R.id.editTextName);
		email = (EditText) findViewById(R.id.editTextEmail);

		checkPlayServices();
	}

	private void checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				log.append("\nSeñor@ Usuari@, esta aplicación no es para ti.");
				finish();
			}
		}
		log.append("\nGoogle Play Services Disponible!");
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
