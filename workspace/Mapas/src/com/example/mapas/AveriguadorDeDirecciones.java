package com.example.mapas;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class AveriguadorDeDirecciones extends
		AsyncTask<LatLng, Integer, List<Address>> {

	private Context elContexto;
	private Marker elMarcador;

	public Marker getElMarcador() {
		return elMarcador;
	}

	public void setElMarcador(Marker elMarcador) {
		this.elMarcador = elMarcador;
	}

	public Context getElContexto() {
		return elContexto;
	}

	public void setElContexto(Context elContexto) {
		this.elContexto = elContexto;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected List<Address> doInBackground(LatLng... params) {
		// TODO Auto-generated method stub

		LatLng primeraCoordenada = params[0];

		Geocoder coder = new Geocoder(elContexto, Locale.getDefault());

		List<Address> addresses = null;
		try {
			addresses = coder.getFromLocation(primeraCoordenada.latitude,
					primeraCoordenada.longitude, 2);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return addresses;

	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(List<Address> addreses) {
		// TODO Auto-generated method stub
		super.onPostExecute(addreses);

		if (!addreses.isEmpty()) {
			// SI
			Address laPrimeraDireccion = addreses.get(0);
			String elSnippet = "";
			elSnippet += laPrimeraDireccion.getAddressLine(0)+"-";
			elSnippet += laPrimeraDireccion.getCountryName();
			elSnippet += "("+laPrimeraDireccion.getCountryCode()+")";
			elMarcador.setSnippet(elSnippet);
		} else {
			elMarcador.setSnippet("No se recibieron direcciones");
		}

	}

}








