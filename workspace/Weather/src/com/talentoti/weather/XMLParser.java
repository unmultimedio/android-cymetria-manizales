package com.talentoti.weather;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.os.AsyncTask;

public class XMLParser {

	private String query;
	private Jibaro mejicano;

	public XMLParser(String query, Activity mejicano) {
		this.query = query;
		try {
			this.mejicano = (Jibaro) mejicano;
		} catch (ClassCastException e) {
			// Error
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
			// Error
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
		String url = "http://api.openweathermap.org/data/2.5/weather?q="
				+ query + "&mode=xml";

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
					HttpURLConnection conn = (HttpURLConnection) theUrl
							.openConnection();

					conn.setConnectTimeout(10 * 1000);
					conn.setRequestMethod("GET");
					conn.setReadTimeout(5 * 1000);
					conn.setDoInput(true);

					conn.connect();

					// Avisarle a la actividad que hay conexion
					publishProgress("Conexión activa");

					// Esperar la respuesta
					InputStream streamIn = conn.getInputStream();

					XmlPullParserFactory factory = XmlPullParserFactory
							.newInstance();

					XmlPullParser parser = factory.newPullParser();

					parser.setInput(streamIn, "UTF-8");

					publishProgress("Arrancando el parseo");

					String[] losDatos = hacerElParseo(parser);
					
					if(losDatos == null){

						publishProgress("Error parseando");
						return null;
					}
					

					publishProgress("Parseado completo :)");
					return losDatos;

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// Avisarle a la actividad del error
					publishProgress("Occurió un error conectando.");
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					publishProgress("Occurió un error instanciando el factory.");
				}

				return null;
			}

			private String[] hacerElParseo(XmlPullParser parser) {
				int event = -1;
				try {
					event = parser.getEventType();
					String name, temperature, pressure, humidity;
					name = temperature = pressure = humidity = "";
					while (event != XmlPullParser.END_DOCUMENT) {
						// lo recorro, verifico, etc
						if (event == XmlPullParser.START_TAG) {
							switch (parser.getName()) {
							case "temperature":
								temperature = parser.getAttributeValue(null,
										"value");
								break;
							case "pressure":
								pressure = parser.getAttributeValue(null,
										"value");
								break;
							case "humidity":
								humidity = parser.getAttributeValue(null,
										"value");
								break;
							case "country":
								event = parser.next();
								if(event == XmlPullParser.TEXT){
									name = parser.getText();
								}
								break;
							}
						}
						publishProgress("leyendo:"+parser.getName());
						event = parser.next();
					}
					return new String[]{name, temperature, pressure, humidity};
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					publishProgress("Ocurrió un error obteniendo el evento");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					publishProgress("Ocurrió un error avanzando en el xml");
				}
				return null;
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
