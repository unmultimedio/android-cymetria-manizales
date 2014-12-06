package com.example.p11_content_provider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileAdapter extends ArrayAdapter {
	
	private Context myContext;
	private MyProfile[] myProfiles;
	
	public ProfileAdapter(Context context, MyProfile[] profiles) {
		super(context, R.layout.word_row, profiles);

		this.myContext = context;
		this.myProfiles = profiles;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater myInflater = (LayoutInflater)myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = myInflater.inflate(R.layout.profile_row, parent, false);
		TextView id = (TextView)rowView.findViewById(R.id.textViewIDProfile);
		TextView name = (TextView)rowView.findViewById(R.id.textViewNameProfile);
		ImageView thumb = (ImageView) rowView.findViewById(R.id.imageViewProfile);
		
		if(myProfiles[position] != null){
			id.setText(String.valueOf(myProfiles[position].get_ID()));
			name.setText(myProfiles[position].getNAME());
			thumb.setImageURI(myProfiles[position].getImage());
		}
		
		return rowView;
		
	}
}
