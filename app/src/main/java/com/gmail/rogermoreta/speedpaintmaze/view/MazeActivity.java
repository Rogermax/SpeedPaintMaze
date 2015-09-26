package com.gmail.rogermoreta.speedpaintmaze.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.gmail.rogermoreta.speedpaintmaze.controller.ManagedActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MazeActivity extends ManagedActivity {

    @Override
    public void onCreate(Bundle b) {
        getGameHelper().setMaxAutoSignInAttempts(0);
        super.onCreate(b); //Necesario siempre que haces override de onCreate
        setContentView(new MazeView(this));
    }

    @Override
    /**
     * Esta función se ejecuta cuando el usuario deja de interactuar con esta actividad.
     * Por tanto, es el momento para guardar el estado de la partida.
     */
    public void onPause() {
        super.onPause(); //Necesario siempre que haces override de onPause

    }


    @Override
    public void changeActivityToIn(final Activity from, final Class activityClassToGo, long miliseconds) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(from, activityClassToGo);
                from.startActivity(mainIntent);
                //finish();// Destruimos esta activity para prevenir que el usuario vuelva aqui apretando atras.
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, miliseconds);// Pasado los 3 segundos dispara/fija/registra la tarea
    }

    @Override
    public void onSignInFailed() {}

    @Override
    public void onSignInSucceeded() {}
}
