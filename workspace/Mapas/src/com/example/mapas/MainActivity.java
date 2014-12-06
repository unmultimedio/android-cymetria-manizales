package com.example.mapas;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity implements MapsComunicator {

	MyMapFragment fragmentMap1, fragmentMap2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		fragmentMap1 = new MyMapFragment();
		fragmentMap1.mapId = 1;
		fragmentMap2 = new MyMapFragment();
		fragmentMap2.mapId = 2;
		
		FragmentManager fm = getFragmentManager();
		
		fm.beginTransaction().replace(R.id.fragmentMap1, fragmentMap1, "fragmento-mapa-1").commit();
		fm.beginTransaction().replace(R.id.fragmentMap2, fragmentMap2, "fragmento-mapa-2").commit();
	}
	
	public void loadMapFragments(View v) {
		fragmentMap1.loadMap("map-1");
		fragmentMap2.loadMap("map-2");
	}
	
	public void styleMaps(View v){	
		fragmentMap1.setMapFeatures(GoogleMap.MAP_TYPE_NORMAL,true);
		fragmentMap2.setMapFeatures(GoogleMap.MAP_TYPE_HYBRID,false);
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

	@Override
	public void mapCameraMoved(CameraPosition position) {
		fragmentMap1.moveMapTo(position);
		fragmentMap2.moveMapTo(position);
	}

	@Override
	public void clickInMap(LatLng position, int mapId) {
		fragmentMap1.addMarker(position, mapId);
		fragmentMap2.addMarker(position, mapId);
	}
	
}
