package com.talentoti.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService {

	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		
		if(!extras.isEmpty()){
			//Leer todo lo que traen los extras
			// Averiguar de qué tipo es el mensaje
			GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
			String messageType = gcm.getMessageType(intent);
			switch (messageType) {
			case GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE:
				String elMensaje = extras.getString("message");
				notificarAlUsuario(elMensaje);
				break;
			default:
				// Llegó un mensaje de otro tipo, error, borrado, servicio no disponible, etc...
				notificarAlUsuario("Llegó una alerta de otro tipo.");
				break;
			}
		}else{
			// llegó un intent vacío
			notificarAlUsuario("Llegó un intent sin extras.");
		}
		
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private void notificarAlUsuario(String elMensaje) {
		// Notificamos al usuario.
		
		NotificationManager nm = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle("GCM Notification")
        .setStyle(new NotificationCompat.BigTextStyle()
        .bigText(elMensaje))
        .setContentText(elMensaje);

        mBuilder.setContentIntent(contentIntent);
        nm.notify(1, mBuilder.build());
	}

}
