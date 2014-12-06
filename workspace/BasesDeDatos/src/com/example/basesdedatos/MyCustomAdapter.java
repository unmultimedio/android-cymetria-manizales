package com.example.basesdedatos;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyCustomAdapter extends ArrayAdapter<Usuario> {
	
	
	Context theContext;
	List<Usuario> elements;
	
	public MyCustomAdapter(Context context, int resource, List<Usuario> objects) {
		super(context, resource, objects);
		this.theContext = context;
		this.elements = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) theContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(R.layout.row_usuario, parent, false);
		TextView name, id, drink, sport;
		
		name = (TextView) rowView.findViewById(R.id.textViewName);
		id = (TextView) rowView.findViewById(R.id.textViewId);
		drink = (TextView) rowView.findViewById(R.id.textViewDrink);
		sport = (TextView) rowView.findViewById(R.id.textViewSport);
		
		name.setText(elements.get(position).name);
		id.setText(String.valueOf(elements.get(position).id));
		drink.setText(elements.get(position).drink);
		sport.setText(elements.get(position).sport);
		
		return rowView;
	}
}















