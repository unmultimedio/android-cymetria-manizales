package com.cymetria.notificaciones2;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void makeNotification(View v){
		// Generar la notificación
		
		EditText contentTitle, contentText, contentInfo, ticker;
		int amountOfVibrations;
		long vibrationLength;
		boolean isAutocancel;
		
		// Obtenemos la información de la vista
		contentTitle = (EditText) findViewById(R.id.editText1);
		contentText = (EditText) findViewById(R.id.editText2);
		contentInfo = (EditText) findViewById(R.id.editText3);
		ticker = (EditText) findViewById(R.id.editText4);
		
		// Nos aseguramos que no vengan vacíos
		String title, text, info, tickerString;
		title = contentTitle.getText().toString().isEmpty() ? getResources().getString(R.string.empty) : contentTitle.getText().toString();
		text = contentText.getText().toString().isEmpty() ? getResources().getString(R.string.empty) : contentText.getText().toString();
		info = contentInfo.getText().toString().isEmpty() ? getResources().getString(R.string.empty) : contentInfo.getText().toString();
		tickerString = ticker.getText().toString().isEmpty() ? getResources().getString(R.string.empty) : ticker.getText().toString();
		
		Spinner aov = (Spinner) findViewById(R.id.spinner1);
		amountOfVibrations = aov.getSelectedItemPosition();
		
		SeekBar duracion = (SeekBar) findViewById(R.id.seekBar1);
		vibrationLength = (long) duracion.getProgress();
		
		CheckBox iac = (CheckBox) findViewById(R.id.checkBox1);
		isAutocancel = iac.isChecked();
		
		NotificationCompat.Builder constructor = new NotificationCompat.Builder(this);
		
		Intent aLaSegunda = new Intent(this, SecondActivity.class);
		aLaSegunda.putExtra("title", title);
		
		PendingIntent pIntent = PendingIntent.getActivity(
				this, 
				0, 
				aLaSegunda, 
				PendingIntent.FLAG_UPDATE_CURRENT);
		
		// Requeridas
		constructor.setContentTitle(title);
		constructor.setContentText(text);
		constructor.setSmallIcon(R.drawable.ic_launcher);
		constructor.setContentIntent(pIntent); // API <= 10
		
		// Opcionales
		constructor.setTicker(tickerString);
		constructor.setContentInfo(info);
		constructor.setAutoCancel(isAutocancel);
		
		//Vibracion del equipo
		//Generar el patrón dinámicamente
		long[] patron = new long[amountOfVibrations*2];
		for(int i=0; i<amountOfVibrations*2; i++){
			patron[i] = vibrationLength;
		}
		constructor.setVibrate(patron);
		
		// Acciones
		
		PendingIntent pIntent1, pIntent2, pIntent3;
		Intent intent1 = new Intent(this, SecondActivity.class);
		intent1.putExtra("title", "Botón 1");
		Intent intent2 = new Intent(this, SecondActivity.class);
		intent2.putExtra("title", "Botón 2");
		Intent intent3 = new Intent(this, SecondActivity.class);
		intent3.putExtra("title", "Botón 3");
		pIntent1 = PendingIntent.getActivity(this, 1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
		pIntent2 = PendingIntent.getActivity(this, 2, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
		pIntent3 = PendingIntent.getActivity(this, 3, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
		constructor.addAction(R.drawable.ic_stat_boton1, getResources().getString(R.string.button1), pIntent1);
		constructor.addAction(R.drawable.ic_stat_boton2, getResources().getString(R.string.button2), pIntent2);
		constructor.addAction(R.drawable.ic_stat_boton3, getResources().getString(R.string.button3), pIntent3);
		
		
		// NOTIFICAMOS!
		Notification n = constructor.build();
		
		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		manager.notify(1, n);
		
		
	}

}
