package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.app.Activity;
import android.content.Intent;

import com.google.example.games.basegameutils.BaseGameActivity;

import java.util.Timer;
import java.util.TimerTask;

public abstract class ManagedActivity extends BaseGameActivity {

    public static void changeActivityToIn(final Activity activity, final Class activityClassToGo, long miliseconds, final boolean destructive) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(activity, activityClassToGo);
                activity.startActivity(mainIntent);
                if (destructive) activity.finish();// Destruimos esta activity para prevenir que el usuario vuelva aqui apretando atras.
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, miliseconds);// Pasado los 3 segundos dispara/fija/registra la tarea
    }

    @Override
    public abstract void onSignInFailed();

    @Override
    public abstract void onSignInSucceeded();
}
