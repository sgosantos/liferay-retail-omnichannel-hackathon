package com.retail.hackathon.retail;

import android.support.v7.app.AppCompatActivity;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;

/**
 * @author Silvio Santos
 */
public class MainActivity extends AppCompatActivity {

	@Override
	protected void onResume() {
		super.onResume();

		SystemRequirementsChecker.checkWithDefaultDialogs(this);

	}
}
