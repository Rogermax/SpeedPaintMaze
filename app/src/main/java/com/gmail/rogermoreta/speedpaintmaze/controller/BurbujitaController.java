package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

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
    private int screenSizeWidth = 1;
    private int screenSizeHeight = 1;
    private float offsetX;
    private float offsetY;
    private float factorDeEscalado;

    public BurbujitaController(MainManager mm) {
        paused = true;
        MM = mm;
        historicTimeUpdates = new ArrayList<>(5);
        historicTimeDraws = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            historicTimeUpdates.add(i, (long) 20);
            historicTimeDraws.add(i, (long) 20);
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
                } else {
                    lastTimeUpdated = SystemClock.uptimeMillis();
                }
                nextIndexUpdate = (nextIndexUpdate + 1) % 5;
                logic(SystemClock.uptimeMillis() - lastTimeUpdated);
                lastTimeUpdated = SystemClock.uptimeMillis();
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public synchronized void render() {
        if (burbujitaMap != null) {
            try {
                if (lastTimeDrawed != null) {
                    historicTimeDraws.set(nextIndexDraw, SystemClock.uptimeMillis() - lastTimeDrawed);
                }
                lastTimeDrawed = SystemClock.uptimeMillis();
                nextIndexDraw = (nextIndexDraw + 1) % 5;
                MM.drawBurbujitaMapAndInterface(burbujitaMap, burbujitaInterface);
            } catch (Exception ignored) {
            }
        }
    }

    public void pause() {
        Log.d("BurbujitaController", "PAUSA ACTIVADA");
        trace("------------- PAUSA ACTIVADA -------------");
        paused = true;
        render();
    }


    public void resume() {
        trace("------------- RESUME ACTIVADO -------------");
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
                int tipo = burbujitaInterface.getSelectedButton();
                if (tipo > -1) {
                    burbujitaMap.buildPreTurret(tipo, 100);
                } else {
                    burbujitaMap.destroyPreBuildTurret();
                }
                burbujitaMap.highLight((int) ((x - offsetX) / factorDeEscalado), (int) ((y - offsetY) / factorDeEscalado));
            } else {
                burbujitaMap.highLight((int) ((x - offsetX) / factorDeEscalado), (int) ((y - offsetY) / factorDeEscalado));
            }
            //burbujitaMap.createNewNextTurret((int) x, (int) y);
        }
    }

    public void sendActionMove(float x, float y) {
        if (burbujitaMap != null) {
            if (burbujitaInterface.isActive()) {
                burbujitaInterface.highLight(x, y);
                int tipo = burbujitaInterface.getSelectedButton();
                if (tipo > -1) {
                    burbujitaMap.buildPreTurret(tipo, 100);
                } else {
                    burbujitaMap.destroyPreBuildTurret();
                }
                burbujitaMap.highLight((int) ((x - offsetX) / factorDeEscalado), (int) ((y - offsetY) / factorDeEscalado));
            } else {
                burbujitaMap.highLight((int) ((x - offsetX) / factorDeEscalado), (int) ((y - offsetY) / factorDeEscalado));
            }
            //burbujitaMap.setNextTurret((int) x, (int) y);
        }
    }

    public void sendActionUp(float x, float y) {
        if (burbujitaMap != null) {
            if (burbujitaInterface.isActive()) {
                int tipo = burbujitaInterface.getSelectedButton();
                if (tipo > -1) {
                    burbujitaMap.buildTurret(tipo,100);
                    burbujitaInterface.desSeleccionar();
                    burbujitaInterface.startRetracting();
                } else {
                    burbujitaMap.destroyPreBuildTurret();
                    burbujitaInterface.highLight(x, y);
                    burbujitaMap.highLight((int) ((x - offsetX) / factorDeEscalado), (int) ((y - offsetY) / factorDeEscalado));
                }
            } else {
                burbujitaMap.highLight((int) ((x - offsetX) / factorDeEscalado), (int) ((y - offsetY) / factorDeEscalado));
                burbujitaMap.destroyPreBuildTurret();
                if (burbujitaMap.canBuildTurretOn((int) ((x - offsetX) / factorDeEscalado), (int) ((y - offsetY) / factorDeEscalado))) {
                    burbujitaInterface.desSeleccionar();
                    burbujitaInterface.startShowing();
                }
                //muestra el interface si es un punto de construcicon
            }
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public BurbujitaMap getBurbujitaMap() {
        return burbujitaMap;
    }

    public void onViewReady(SurfaceHolder holder) throws Exception {
        trace("Creamos GameThread e Interface de 8");
        burbujitaMap = new BurbujitaMap();
        gameThread = new GameThread(this);
        gameThread.encender();
        setSizes(holder);
        //burbujitaInterface = new Interface(8, screenSizeWidth, screenSizeHeight);
        render();
    }

    public void onViewChanged(SurfaceHolder holder) {
        setSizes(holder);
        //burbujitaInterface = new Interface(8, screenSizeWidth, screenSizeHeight);
        render();
    }

    private void setSizes(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        screenSizeWidth = canvas.getWidth();
        screenSizeHeight = canvas.getHeight();
        int mapWidth = burbujitaMap.getMapWidth() * 100;
        int mapHeight = burbujitaMap.getMapHeight() * 100;
        float escaladoX = screenSizeWidth / (float) mapWidth;
        float escaladoY = screenSizeHeight / (float) mapHeight;
        if (escaladoX < escaladoY) {
            factorDeEscalado = escaladoX;
            offsetX = 0f;
            offsetY = screenSizeHeight / 2f - factorDeEscalado * mapHeight / 2f;
        } else {
            factorDeEscalado = escaladoY;
            offsetX = screenSizeWidth / 2f - factorDeEscalado * mapWidth / 2f;
            offsetY = 0f;
        }
        holder.unlockCanvasAndPost(canvas);
    }
}

