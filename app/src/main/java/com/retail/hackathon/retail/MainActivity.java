package com.retail.hackathon.retail;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.service.BeaconManager;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;

import java.util.UUID;

public class MainActivity extends Activity {

	private BeaconManager beaconManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		RetailApplication application = (RetailApplication)getApplicationContext();

		beaconManager = application.getBeaconManager();
		beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
			@Override
			public void onServiceReady() {
				beaconManager.startMonitoring(new BeaconRegion(
					"monitored region", UUID.fromString("b9407f30-f5f8-466e-aff9-25556b57fe6d"), null, null));
			}
		});

		TextView textView = findViewById(R.id.textView);
		User user = SessionContext.getCurrentUser();
		textView.setText("Olá " + user.getFullName());

		UserPortraitScreenlet portrait = findViewById(R.id.portrait);
		portrait.setPortraitId(user.getPortraitId());
		portrait.load();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		beaconManager.disconnect();
	}
}
