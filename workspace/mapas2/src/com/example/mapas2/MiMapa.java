package com.example.mapas2;

import com.google.android.gms.maps.MapFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class MiMapa extends Fragment {

	public int mapaId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View elFragmento = inflater.inflate(R.layout.mimapa_fragment,
				container, false);

		FrameLayout elContenedor = (FrameLayout) elFragmento
				.findViewById(R.id.contenedor_mapa);
		elContenedor.setId(R.id.contenedor_mapa + mapaId);

		MapFragment elMapaQueVamosAInyectar = new MapFragment();
		FragmentManager fm = getActivity().getFragmentManager();
		fm.beginTransaction()
				.add(R.id.contenedor_mapa + mapaId, elMapaQueVamosAInyectar, "mapa")
				.commit();

		return elFragmento;

	}
}
