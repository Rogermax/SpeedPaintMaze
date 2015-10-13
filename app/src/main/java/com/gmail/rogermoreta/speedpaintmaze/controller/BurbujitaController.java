package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.app.Activity;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.gmail.rogermoreta.speedpaintmaze.javaandroid.GameThread;
import com.gmail.rogermoreta.speedpaintmaze.model.BurbujitaMap;
import com.gmail.rogermoreta.speedpaintmaze.view.BurbujitaActivity;

import java.util.ArrayList;

public class BurbujitaController extends Controller {

    @SuppressWarnings("FieldCanBeLocal")
    private GameThread gameThread;

    private BurbujitaMap burbujitaMap;
    private boolean paused;
    private Long lastTimeUpdated;
    private Long lastTimeDrawed;
    private ArrayList<Long> historicTimeUpdates;
    private ArrayList<Long> historicTimeDraws;
    private int nextIndexUpdate;
    private int nextIndexDraw;
    private MainManager MM;

    public BurbujitaController(MainManager mm) {
        paused = true;
        MM = mm;
        gameThread = new GameThread(this);
        gameThread.encender();
        historicTimeUpdates = new ArrayList<>(5);
        historicTimeDraws = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            historicTimeUpdates.add(i, (long) 20);
            historicTimeDraws.add(i,(long) 20);
        }
        nextIndexUpdate = 0;
        nextIndexDraw = 0;
    }

    @Override
    public void mostrarActividad(Activity activity, long miliseconds) {
        ManagedActivity.changeActivityToIn(activity, BurbujitaActivity.class, miliseconds, false);
    }

    public void onViewReady(SurfaceHolder surfaceHolder) {
        burbujitaMap = new BurbujitaMap(surfaceHolder, this);
        burbujitaMap.draw(50f, 50f);
    }

    private void logic(long milisec) {
        if (burbujitaMap != null && !paused) {
            burbujitaMap.logic(milisec);
        }
    }

    public Canvas drawObjectIntoCanvas(Canvas canvas, Object object, int capa) {
        return MM.drawObjectIntoCanvas(canvas, object, capa);
    }

    private void draw() {
        if (burbujitaMap != null) {
            try {
                historicTimeDraws.set(nextIndexDraw, System.currentTimeMillis()-lastTimeDrawed);
                lastTimeDrawed = System.currentTimeMillis();
                nextIndexDraw = (nextIndexDraw+1)%5;
                Long aux = 1l;
                Long aux2 = 1l;
                for (int i = 0; i < 5; i++) {
                    aux += historicTimeDraws.get(i);
                    aux2 += historicTimeUpdates.get(i);
                }
                burbujitaMap.draw(5000f / aux, 5000f / aux2);
            }
            catch (Exception ignored){}
        }
    }

    @Override
    public void update() {
        if (!paused) {
            try {
                historicTimeUpdates.set(nextIndexUpdate, System.currentTimeMillis() - lastTimeUpdated);
                logic(System.currentTimeMillis() - lastTimeUpdated);
                lastTimeUpdated = System.currentTimeMillis();
                nextIndexUpdate = (nextIndexUpdate + 1) % 5;
            }
            catch (Exception ignored){}
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
        paused = false;
        burbujitaMap.createNewNextTurret((int) x, (int) y);
    }

    public void sendActionMove(float x, float y) {
        burbujitaMap.setNextTurret((int) x, (int) y);
    }

    public void sendActionUp(float x, float y) {
        burbujitaMap.buildTurret((int) x, (int) y);
        lastTimeUpdated = System.currentTimeMillis();
        lastTimeDrawed = System.currentTimeMillis();
    }

    public void onSurfaceChange(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        burbujitaMap.reajustarTamaño(canvas);
        holder.unlockCanvasAndPost(canvas);
    }
}
