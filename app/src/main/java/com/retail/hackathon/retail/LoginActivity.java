package com.retail.hackathon.retail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;

/**
 * @author Silvio Santos
 */
public class LoginActivity extends AppCompatActivity implements LoginListener{

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		if (SessionContext.isLoggedIn()) {
			startMainActivity();

			return;
		}

		LoginScreenlet loginScreenlet = (LoginScreenlet)findViewById(
			R.id.login_screenlet);

		loginScreenlet.setListener(this);

		EditText login = loginScreenlet.findViewById(R.id.liferay_login);
		EditText password = loginScreenlet.findViewById(R.id.liferay_password);

		login.setText("test@liferay.com", TextView.BufferType.EDITABLE);
		password.setText("test", TextView.BufferType.EDITABLE);
	}

	@Override
	protected void onResume() {
		super.onResume();

		SystemRequirementsChecker.checkWithDefaultDialogs(this);
	}

	@Override
	public void onLoginSuccess(User user) {
		startMainActivity();
	}

	private void startMainActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void onLoginFailure(Exception e) {
		Toast.makeText(this, "LoginFailure", Toast.LENGTH_LONG).show();
	}
}
