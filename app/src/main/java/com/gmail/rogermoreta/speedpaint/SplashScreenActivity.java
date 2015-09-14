package com.gmail.rogermoreta.speedpaint;

import java.util.Timer;
import java.util.TimerTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
import android.content.Intent;

public class SplashScreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// want fullscreen, we hide Activity's title and notification bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				Intent mainIntent = new Intent().setClass(
						SplashScreenActivity.this, Menu.class);
				startActivity(mainIntent);
				finish();// Destruimos esta activity para prevenit que el
							// usuario retorne aqui presionando el boton Atras.
			}
		};

		Timer timer = new Timer();
		long splashDelay = 3000;
		timer.schedule(task, splashDelay);// Pasado los 5 segundos dispara la
											// tarea
	}

}
