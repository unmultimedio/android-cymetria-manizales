package com.example.p11_content_provider;

import android.net.Uri;

public class MyProfile {

	private int _ID;
	private String NAME;
	private Uri Image;
	
	public MyProfile(int _ID, String nAME, Uri image) {
		super();
		this._ID = _ID;
		NAME = nAME;
		Image = image;
	}
	
	public int get_ID() {
		return _ID;
	}
	public void set_ID(int _ID) {
		this._ID = _ID;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public Uri getImage() {
		return Image;
	}
	public void setImage(Uri image) {
		Image = image;
	}
}
