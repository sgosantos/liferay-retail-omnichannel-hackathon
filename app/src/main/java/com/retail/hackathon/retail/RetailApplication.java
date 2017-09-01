package com.retail.hackathon.retail;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.util.List;

public class RetailApplication extends Application {

	private BeaconManager beaconManager;

	@Override
	public void onCreate() {
		super.onCreate();

		beaconManager = new BeaconManager(getApplicationContext());

		beaconManager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
			@Override
			public void onEnteredRegion(BeaconRegion region, List<Beacon> beacons) {
				System.out.println("RetailApplication.onEnteredRegion " + beacons.toString());
				showNotification(
					"Bem-vindo de volta!",
					"Sua compra já está pronta esperando por você!");
			}
			@Override
			public void onExitedRegion(BeaconRegion region) {
				// could add an "exit" notification too if you want (-:
			}
		});

		beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
			@Override
			public void onServiceReady() {
				beaconManager.startMonitoring(new BeaconRegion(
					"monitored region", null, null, null));
			}
		});
	}

	public void showNotification(String title, String message) {
		Intent notifyIntent = new Intent(this, MainActivity.class);
		notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
			new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
		Notification notification = new Notification.Builder(this)
			.setSmallIcon(android.R.drawable.ic_dialog_info)
			.setContentTitle(title)
			.setContentText(message)
			.setAutoCancel(true)
			.setContentIntent(pendingIntent)
			.build();
		notification.defaults |= Notification.DEFAULT_SOUND;
		NotificationManager notificationManager =
			(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(1, notification);
	}
}