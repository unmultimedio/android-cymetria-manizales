package com.example.mapas;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyMapFragment extends Fragment {
	
	private GoogleMap map;
	List<Marker> markers;
	public int mapId;
	private MapsComunicator interfaceMaps;
	

	OnMarkerClickListener markerClickListener = new OnMarkerClickListener() {
		
		@Override
		public boolean onMarkerClick(Marker marker) {
			// TODO Auto-generated method stub
			AveriguadorDeDirecciones ad = new AveriguadorDeDirecciones();
			ad.setElContexto(getActivity());
			ad.setElMarcador(marker);
			ad.execute(marker.getPosition());
			return false;
		}
	};

	OnCameraChangeListener cameraListener = new OnCameraChangeListener() {
		
		@Override
		public void onCameraChange(CameraPosition position) {
			//Avisar a la actividad
			interfaceMaps.mapCameraMoved(position);
		}
	};
	

	OnMapLongClickListener mapClickListener = new OnMapLongClickListener() {
		
		@Override
		public void onMapLongClick(LatLng coord) {
			interfaceMaps.clickInMap(coord, mapId);
		}
	};
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		try{
			interfaceMaps = (MapsComunicator)activity;
		}catch(Exception e){
			//Error
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.map_fragment, container, false);
		FrameLayout frame = (FrameLayout) fragmentView.findViewById(R.id.map);
		// revisar esto: http://stackoverflow.com/questions/1714297/android-view-setidint-id-programmatically-how-to-avoid-id-conflicts
		frame.setId(frame.getId()+mapId);
		return fragmentView;
	}
	
	public void loadMap(String tag){
		MapFragment myMap = new MapFragment();
		FragmentManager fm = getActivity().getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.map+mapId, myMap, tag);
		ft.commit();
		//map = myMap.getMap();
	}
	
	public void setMapFeatures(int type, boolean location){
		map = ((MapFragment)getActivity().getFragmentManager().findFragmentById(R.id.map+mapId)).getMap();
		switch (type) {
		case GoogleMap.MAP_TYPE_HYBRID:
		case GoogleMap.MAP_TYPE_NONE:
		case GoogleMap.MAP_TYPE_NORMAL:
		case GoogleMap.MAP_TYPE_SATELLITE:
		case GoogleMap.MAP_TYPE_TERRAIN:
			map.setMapType(type);
			break;
		default:
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			Log.d("mzl", "Estilo de mapa desconocido, aplicado normal.");
			break;
		}
		markers = new ArrayList<Marker>();
		map.setMyLocationEnabled(location);
		map.setOnCameraChangeListener(cameraListener);
		map.setOnMapLongClickListener(mapClickListener);
		map.setOnMarkerClickListener(markerClickListener);
		
	}

	public void moveMapTo(CameraPosition position) {
		map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
	}

	public void addMarker(LatLng position, int mapId){
		markers.add(
				map.addMarker(
						new MarkerOptions()
						.position(position)
						.title( String.valueOf(mapId) )
							)
						);
	}
}
