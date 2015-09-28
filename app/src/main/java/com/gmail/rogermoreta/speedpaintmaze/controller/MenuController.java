package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.app.Activity;

import com.gmail.rogermoreta.speedpaintmaze.view.MenuActivity;

public class MenuController extends Controller {

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private static MenuActivity menuActivity;

    public MenuController() {
        super();
    }

    @Override
    public void mostrarActividad(Activity activity, long miliseconds) {
        ManagedActivity.changeActivityToIn(activity, MenuActivity.class, miliseconds, true);
    }

    public void setActivity(MenuActivity activity) {
        menuActivity = activity;
    }
}
