package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.app.Activity;

import com.google.example.games.basegameutils.BaseGameActivity;

public abstract class ManagedActivity extends BaseGameActivity {

    public abstract void changeActivityToIn(final Activity activity, final Class activityClassToGo, long miliseconds);

    @Override
    public abstract void onSignInFailed();

    @Override
    public abstract void onSignInSucceeded();
}
