package com.talentoti.weather;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;

public class JSONParser {

	private String query;
	private Jibaro mejicano;

	public JSONParser(String query, Activity mejicano) {
		this.query = query;
		try {
			this.mejicano = (Jibaro) mejicano;
		} catch (ClassCastException e) {
			//Error
			this.mejicano = null;
		}
	}

	public Jibaro getMejicano() {
		return mejicano;
	}

	public void setMejicano(Activity mejicano) {
		try {
			this.mejicano = (Jibaro) mejicano;
		} catch (ClassCastException e) {
			//Error
		}
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void doMyJob() {
		// Formar la URL
		String url = "http://api.openweathermap.org/data/2.5/weather?q=" + query + "&mode=json";

		AsyncTask<String, String, String[]> worker = new AsyncTask<String, String, String[]>() {

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				// Avisarle a la actividad que vamos a arrancar
				publishProgress("Arrancando la tarea...");
			}

			@Override
			protected String[] doInBackground(String... params) {
				// TODO Auto-generated method stub
				String url = params[0];

				// Hacer la conexion
				try {
					URL theUrl = new URL(url);

					publishProgress("URL creada");
					HttpURLConnection conn = (HttpURLConnection) theUrl.openConnection();

					conn.setConnectTimeout(10 * 1000);
					conn.setRequestMethod("GET");
					conn.setReadTimeout(5 * 1000);
					conn.setDoInput(true);

					conn.connect();

					// Avisarle a la actividad que hay conexion
					publishProgress("Conexión activa");

					// Esperar la respuesta
					InputStream streamIn = conn.getInputStream();

					// Leerla y convertirla a String
					String jsonString = convertirAString(streamIn);

					publishProgress("Tenemos el string");
					
					if (jsonString.isEmpty()) {
						publishProgress("No hay nada en el string");
						return null;
					}

					// Convertir a JSON
					String[] losDatos = hacerElParseo(jsonString);

					publishProgress("Tenemos los datos");
					
					return losDatos;

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// Avisarle a la actividad del error
					publishProgress("Occurió un error conectando.");
				}

				return null;
			}

			private String[] hacerElParseo(String jsonString) {
				try {
					// Extraer datos
					JSONObject json = new JSONObject(jsonString);
					String name = json.get("name").toString();

					JSONObject main = json.getJSONObject("main");

					String temperature = main.get("temp").toString();
					String pressure = main.get("pressure").toString();
					String humidity = main.get("humidity").toString();

					return new String[] { name, temperature, pressure, humidity };

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return null;
			}

			private String convertirAString(InputStream streamIn) {
				Scanner scanner = new Scanner(streamIn);
				scanner.useDelimiter("\\A");
				String toReturn = scanner.hasNext() ? scanner.next() : "";
				return toReturn;
			}
			
			@Override
			protected void onProgressUpdate(String... values) {
				super.onProgressUpdate(values);
				mejicano.actualizarProgreso(values[0]);
			}
			
			@Override
			protected void onPostExecute(String[] result) {
				super.onPostExecute(result);
				// Devolver
				mejicano.enviarDatos(result);
			}

		};
		
		worker.execute(url);
		
	}
}

















