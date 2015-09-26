package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.app.Activity;

import com.gmail.rogermoreta.speedpaintmaze.view.MenuActivity;

public class MenuController extends Controller {

    private static MenuActivity menuActivity;

    public MenuController() {
        super();
        menuActivity = new MenuActivity();
    }


    @Override
    public void mostrarActividad(Activity activity, long miliseconds) {
        menuActivity.changeActivityToIn(activity, MenuActivity.class, miliseconds);
    }
}
