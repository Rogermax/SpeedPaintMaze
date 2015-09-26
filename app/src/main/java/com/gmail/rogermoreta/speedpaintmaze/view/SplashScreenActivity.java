package com.gmail.rogermoreta.speedpaintmaze.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.gmail.rogermoreta.speedpaintmaze.controller.MainManager;
import com.gmail.rogermoreta.speedpaintmaze.controller.ManagedActivity;
import com.gmail.rogermoreta.speedpaintmaze.enums.Section;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends ManagedActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // want fullscreen, we hide Activity's title and notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getGameHelper().setMaxAutoSignInAttempts(0);
		super.onCreate(savedInstanceState);

	}

    @Override
    public void changeActivityToIn(final Activity from, final Class activityClassToGo, long miliseconds) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(from, activityClassToGo);
                from.startActivity(mainIntent);
                from.finish();// Destruimos esta activity para prevenir que el usuario vuelva aqui apretando atras.
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, miliseconds);// Pasado los 3 segundos dispara/fija/registra la tarea
    }

    @Override
    public void onSignInFailed() {
        MainManager.goToSection(Section.MENU, this);
    }

    @Override
    public void onSignInSucceeded() {
        MainManager.goToSection(Section.MENU, this);
    }
}
