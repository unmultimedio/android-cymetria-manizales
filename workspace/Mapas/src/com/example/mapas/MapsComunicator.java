package com.example.mapas;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public interface MapsComunicator {
	public void mapCameraMoved(CameraPosition position);
	public void clickInMap(LatLng position, int mapId);
}
