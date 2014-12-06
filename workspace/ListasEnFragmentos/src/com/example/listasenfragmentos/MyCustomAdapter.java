package com.example.listasenfragmentos;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

public class MyCustomAdapter extends ArrayAdapter<String>{

	public MyCustomAdapter(Context context, int resource, List<String> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}

}
