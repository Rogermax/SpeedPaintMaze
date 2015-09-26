package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.app.Activity;

import java.io.Serializable;

public abstract class Controller implements Serializable{

    protected Controller(){}
    public abstract void mostrarActividad(Activity activity, long milisecondss);

}
