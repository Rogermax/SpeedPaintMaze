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
    private Long lastTimeUpdated;
    private Long lastTimeDrawed;
    private ArrayList<Long> historicTimeUpdates;
    private ArrayList<Long> historicTimeDraws;
    private int nextIndexUpdate;
    private int nextIndexDraw;

    public BurbujitaController() {
        paused = true;
        gameThread = new GameThread(this);
        gameThread.encender();
        historicTimeUpdates = new ArrayList<>(5);
        historicTimeDraws = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            historicTimeUpdates.add(i, (long) 20);
            historicTimeDraws.add(i,(long) 20);
        }
        lastTimeUpdated = System.currentTimeMillis();
        lastTimeDrawed = System.currentTimeMillis();
        nextIndexUpdate = 0;
        nextIndexDraw = 0;
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
            historicTimeDraws.set(nextIndexDraw, System.currentTimeMillis()-lastTimeDrawed);
            lastTimeDrawed = System.currentTimeMillis();
            nextIndexDraw = (nextIndexDraw+1)%5;
            Long aux = 0l;
            Long aux2 = 0l;
            for (int i = 0; i < 5; i++) {
                aux += historicTimeDraws.get(i);
                aux2 += historicTimeUpdates.get(i);
            }
            burbujitaMap.draw(5000/aux,5000/aux2);
        }
    }

    @Override
    public void update() {
        if (!paused) {
            historicTimeUpdates.set(nextIndexUpdate, System.currentTimeMillis()-lastTimeUpdated);
            lastTimeUpdated = System.currentTimeMillis();
            nextIndexUpdate = (nextIndexUpdate+1)%5;
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
