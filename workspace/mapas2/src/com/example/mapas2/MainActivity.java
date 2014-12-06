package com.example.mapas2;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity {

	// Llave: AIzaSyDNrfCGMFBxDOmfzgkSam4yvfmUPWWTp-g
	
	GoogleMap elMapaObj;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//FrameLayout contenedor = (FrameLayout) findViewById(R.id.contenedor1);
		
		FragmentManager fm = getFragmentManager();
		
		MiMapa elMapa = new MiMapa();
		elMapa.mapaId = 1;
		MiMapa elMapa2 = new MiMapa();
		elMapa.mapaId = 2;
		
		fm.beginTransaction().add(R.id.contenedor1, elMapa, "mapa-1").commit(); 
		fm.beginTransaction().add(R.id.contenedor1, elMapa2, "mapa-2").commit(); 
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		elMapaObj = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		//		
//elMapaObj.setOnMapLongClickListener(new OnMapLongClickListener() {
//			
//			@Override
//			public void onMapLongClick(LatLng arg0) {
//				// Cada vez que haga click largo en el mapa
//				Marker mimarker = elMapaObj.addMarker(new MarkerOptions()
//								.position(arg0)
//								.title("Este es mi primer marcador")
//								.snippet("Y este es el subtítulo")
//								.draggable(true));
//			}
//		});
//		
//		elMapaObj.setOnMarkerDragListener(new OnMarkerDragListener() {
//			
//			@Override
//			public void onMarkerDragStart(Marker marker) {
//				marker.setSnippet("Empezó a arrastrarse");
//				marker.showInfoWindow();
//			}
//			
//			@Override
//			public void onMarkerDragEnd(Marker marker) {
//				marker.setSnippet("Terminó de arrastrarse");
//				marker.showInfoWindow();
//			}
//			
//			@Override
//			public void onMarkerDrag(Marker marker) {
		
//		Geocoder coder = new Geocoder(getApplicationContext(), Locale.getDefault());
//		
//		List<Address> addresses = null;
//		String title = "";
//		try {
//			addresses = coder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 2);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			title = "No hay dirección";
//		}
//		if(addresses != null && addresses.size()>0){
//			Address first = addresses.get(0);
//			title = String.format("%s, %s, %s",
//					first.getMaxAddressLineIndex() > 0 ? first.getAddressLine(0) : "-",
//					first.getLocality(),
//					first.getCountryName());
//		}else{
//			title = "No resultados";
//		}
//		
//		marker.setTitle(title);
		
//				LatLng laPosicion = marker.getPosition();
//				marker.setSnippet(String.format("(%d,%d)",laPosicion.latitude, laPosicion.longitude));
//				marker.showInfoWindow();
//			}
//		});
//		
//		elMapaObj.setMyLocationEnabled(true);
//		
//		elMapaObj.setOnCameraChangeListener(new OnCameraChangeListener() {
//			
//			@Override
//			public void onCameraChange(CameraPosition laNuevaPosicion) {
//				Log.d("mzl-mapas", laNuevaPosicion.target.toString()+" "+laNuevaPosicion.zoom+ " " + laNuevaPosicion.tilt);
//			}
//		});
		
	}
	
	public void cargarEstilos(View v){
//		elMapaObj = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
//		
//		elMapaObj.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		
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

}
