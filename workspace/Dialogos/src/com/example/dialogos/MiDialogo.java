package com.example.dialogos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class MiDialogo extends DialogFragment {

	private Comunicador laActividad;

	public void setLaActividad(Activity laActividad) {
		try{
			this.laActividad = (Comunicador)laActividad;
		}catch(Exception e){
			//Error porque la actividad no implementa esa interfaz
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateDialog(savedInstanceState);
		
		//AbrirDialogo
				AlertDialog.Builder constructor = new AlertDialog.Builder(getActivity());
				
				constructor.setTitle("Este es el título");
				constructor.setMessage("Este es el mensaje");
				constructor.setNegativeButton("Negativo", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(getActivity(), "Click en negativo", Toast.LENGTH_SHORT).show();
						laActividad.aQueBotonLeDiClick(0);
					}
				});
				constructor.setPositiveButton("Positive",new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Toast.makeText(getActivity(), "Click en positivo", Toast.LENGTH_SHORT).show();
						laActividad.aQueBotonLeDiClick(1);
					}
				});
				// Acá construyo todo lo demás...
				LayoutInflater inflador = getActivity().getLayoutInflater();
				View laVista = inflador.inflate(R.layout.layout_alert_1, null);
				
				constructor.setView(laVista);
				
				//Mostrar el diálogo de alerta
				return constructor.create();
	}

	
}














