package com.gmail.rogermoreta.speedpaintmaze.view;

import android.os.Bundle;

import com.gmail.rogermoreta.speedpaintmaze.controller.MainManager;
import com.gmail.rogermoreta.speedpaintmaze.controller.ManagedActivity;

public class MazeActivity extends ManagedActivity {

    private static MainManager MM = MainManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getGameHelper().setMaxAutoSignInAttempts(0);
        super.onCreate(savedInstanceState); //Necesario siempre que haces override de onCreate
        setContentView(new MazeView(this));
    }

    @Override
    public void onPause(){
        super.onPause();
        MM.pauseMaze();
    }

    @Override
    public void onSignInFailed() {}

    @Override
    public void onSignInSucceeded() {}

}
