package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.app.Activity;
import android.os.SystemClock;
import android.util.Log;

import com.gmail.rogermoreta.speedpaintmaze.javaandroid.GameThread;
import com.gmail.rogermoreta.speedpaintmaze.javaandroid.Trace;
import com.gmail.rogermoreta.speedpaintmaze.model.BurbujitaMap;
import com.gmail.rogermoreta.speedpaintmaze.model.Interface;
import com.gmail.rogermoreta.speedpaintmaze.view.BurbujitaActivity;

import java.util.ArrayList;

public class BurbujitaController extends Controller {

    @SuppressWarnings("FieldCanBeLocal")
    private GameThread gameThread;

    private BurbujitaMap burbujitaMap;
    private Interface burbujitaInterface;
    private Long lastTimeUpdated;
    private Long lastTimeDrawed;
    @SuppressWarnings({"unused", "MismatchedQueryAndUpdateOfCollection"})
    private ArrayList<Long> historicTimeUpdates;
    @SuppressWarnings({"unused", "MismatchedQueryAndUpdateOfCollection"})
    private ArrayList<Long> historicTimeDraws;
    private int nextIndexUpdate;
    private int nextIndexDraw;
    private MainManager MM;

    public BurbujitaController(MainManager mm) {
        paused = true;
        MM = mm;
        historicTimeUpdates = new ArrayList<>(5);
        historicTimeDraws = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            historicTimeUpdates.add(i, (long) 20);
            historicTimeDraws.add(i,(long) 20);
        }
        nextIndexUpdate = 0;
        nextIndexDraw = 0;
    }

    @SuppressWarnings("unused")
    private void trace(String str) {
        Trace.write(" BurbujitaController::" + str);
    }

    @Override
    public void mostrarActividad(Activity activity, long miliseconds) {
        ManagedActivity.changeActivityToIn(activity, BurbujitaActivity.class, miliseconds, false);
    }

    public void onViewReady() {
        burbujitaInterface = new Interface(8);
        gameThread = new GameThread(this);
        gameThread.encender();
        burbujitaMap = new BurbujitaMap(this);
        render();
    }

    private void logic(long milisec) {
        if (burbujitaMap != null && !paused) {
            burbujitaMap.logic(milisec);
        }
        if (burbujitaInterface != null) {
            burbujitaInterface.logic(milisec);
        }
    }

    @Override
    public void update() {
        if (!paused) {
            try {
                if (lastTimeUpdated != null) {
                    historicTimeUpdates.set(nextIndexUpdate, SystemClock.uptimeMillis() - lastTimeUpdated);
                }
                else {
                    lastTimeUpdated = SystemClock.uptimeMillis();
                }
                nextIndexUpdate = (nextIndexUpdate + 1) % 5;
                logic(SystemClock.uptimeMillis() - lastTimeUpdated);
                lastTimeUpdated = SystemClock.uptimeMillis();
            }
            catch (Exception ignored){}
        }
    }

    @Override
    public void render() {
        if (burbujitaMap != null) {
            try {
                if (lastTimeDrawed != null) {
                    historicTimeDraws.set(nextIndexDraw, SystemClock.uptimeMillis() - lastTimeDrawed);
                }
                lastTimeDrawed = SystemClock.uptimeMillis();
                nextIndexDraw = (nextIndexDraw+1)%5;
                MM.drawBurbujitaMap(burbujitaMap);
                MM.drawInterface(burbujitaInterface);
            }
            catch (Exception ignored){}
        }
    }

    public void pause() {
        Log.d("BurbujitaController", "PAUSA ACTIVADA");
        paused = true;
        render();
    }


    public void resume() {
        //nothing to do, quitamos el pause cuando detectamos una pulsacion
    }

    public void sendActionDown(float x, float y) {
        if (paused) {
            lastTimeUpdated = SystemClock.uptimeMillis();
            paused = false;
        }
        if (burbujitaMap != null) {
            if (burbujitaInterface.isActive()) {
                burbujitaInterface.highLight(x, y);
            }
            else {
                burbujitaMap.highLight((int)x, (int) y);
            }
            //burbujitaMap.createNewNextTurret((int) x, (int) y);
        }
    }

    public void sendActionMove(float x, float y) {
        if (burbujitaMap != null) {
            if (burbujitaInterface.isActive()) {
                burbujitaInterface.highLight(x, y);
            }
            else {
                burbujitaMap.highLight((int) x, (int) y);
            }
            //burbujitaMap.setNextTurret((int) x, (int) y);
        }
    }

    public void sendActionUp(float x, float y) {
        if (burbujitaMap != null) {
            if (burbujitaInterface.isActive()) {
                burbujitaInterface.highLight(x, y);
                burbujitaMap.buildTurret();
                burbujitaInterface.startRetracting();
            }
            else {
                burbujitaMap.highLight((int) x, (int) y);
                //muestra el interface si es un punto de construcicon
                burbujitaInterface.startShowing();
            }
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public BurbujitaMap getBurbujitaMap() {
        return burbujitaMap;
    }
}

