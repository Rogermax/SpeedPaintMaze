package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.SurfaceHolder;

import com.gmail.rogermoreta.speedpaintmaze.R;
import com.gmail.rogermoreta.speedpaintmaze.javaandroid.GameThread;
import com.gmail.rogermoreta.speedpaintmaze.model.Bullet;
import com.gmail.rogermoreta.speedpaintmaze.model.BurbujitaMap;
import com.gmail.rogermoreta.speedpaintmaze.model.Turret;
import com.gmail.rogermoreta.speedpaintmaze.view.BurbujitaActivity;

import java.util.ArrayList;

public class BurbujitaController extends Controller {

    private GameThread gameThread;

    private BurbujitaActivity burbujitaActivity;
    private BurbujitaMap burbujitaMap;
    private boolean paused;

    public BurbujitaController() {
        paused = false;
        gameThread = new GameThread(this);
        gameThread.encender();
    }

    public void setActivity(BurbujitaActivity activity) {
        burbujitaActivity = activity;
    }

    @Override
    public void mostrarActividad(Activity activity, long miliseconds) {
        ManagedActivity.changeActivityToIn(activity, BurbujitaActivity.class, miliseconds, false);
    }

    public void onViewReady(SurfaceHolder surfaceHolder) {
        burbujitaMap = new BurbujitaMap(surfaceHolder, burbujitaActivity);
    }

    private void logic() {
        if (burbujitaMap != null && !paused) {
            burbujitaMap.logic();
        }
    }

    private void draw() {
        if (burbujitaMap != null) {
            burbujitaMap.draw();
        }
    }

    @Override
    public void update() {
        if (!paused) {
            logic();
        }
    }

    @Override
    public void render() {
        if (!paused) {
            draw();
        }
    }

    public void pause() {
        paused = true;
        draw();
    }

    public void sendActionDown(float x, float y) {
        burbujitaMap.createNewNextTurret((int) x, (int) y);
    }

    public void sendActionMove(float x, float y) {
        burbujitaMap.setNextTurret((int) x, (int) y);
    }

    public void sendActionUp(float x, float y) {
        burbujitaMap.buildTurret((int) x, (int) y);
        paused = false;
    }
}
