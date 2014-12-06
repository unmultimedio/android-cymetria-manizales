package com.example.listasenfragmentos;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SegundoFragmento extends ListFragment {
	List<String> selectedCountriesList;
	MyCustomAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.segundo_fragmento, container, false);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		selectedCountriesList = new ArrayList<String>();
		
		adapter = new MyCustomAdapter(
				getActivity(),
				android.R.layout.simple_list_item_1, 
				selectedCountriesList);
		setListAdapter(adapter);
		
		
	}
	
	
	
}
















