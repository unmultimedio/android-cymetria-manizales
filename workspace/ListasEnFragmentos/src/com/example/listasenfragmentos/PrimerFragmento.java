package com.example.listasenfragmentos;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

public class PrimerFragmento extends ListFragment {
	String[] countries;
	List<String> countriesList;
	InterfaceCom sender;
	MyCustomAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		countries = getResources().getStringArray(R.array.countries_array);
		countriesList = new ArrayList<String>();
		for(int i=0; i<countries.length; i++){
			countriesList.add(countries[i]);
		}
		
		adapter = new MyCustomAdapter(getActivity(), android.R.layout.simple_list_item_1, countriesList);
		setListAdapter(adapter);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View viewToReturn = inflater.inflate(R.layout.primer_fragmento, container, false);
		return viewToReturn;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		registerForContextMenu(getListView());
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		AdapterContextMenuInfo pais = (AdapterContextMenuInfo)item.getMenuInfo();
		
		int positionInArray = pais.position;
		
		//Con positionInArray imprimimos el diálogo para poder eliminar/editar el item de la lista
		

		return super.onContextItemSelected(item);
		
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		sender.huboUnClick(countriesList.get(position), position);
		
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try{
			sender = (InterfaceCom) activity;	
		}catch(Exception e){
			//Hubo un error....
		}
	}
	
}





















