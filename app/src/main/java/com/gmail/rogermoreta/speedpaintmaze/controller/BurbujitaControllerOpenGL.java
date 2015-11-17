package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.app.Activity;
import android.os.SystemClock;
import android.util.Log;

import com.gmail.rogermoreta.speedpaintmaze.javaandroid.Trace;
import com.gmail.rogermoreta.speedpaintmaze.model.BurbujitaMap;
import com.gmail.rogermoreta.speedpaintmaze.model.Interface;
import com.gmail.rogermoreta.speedpaintmaze.view.BurbujitaOpenGLActivity;

import java.util.ArrayList;

public class BurbujitaControllerOpenGL extends Controller {

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
    private int screenSizeHeight;
    private int screenSizeWidth;
    private float m_left;
    private float m_right;
    private float m_bottom;
    private float m_top;
    private float m_transformedX;
    private float m_transformedY;
    private long m_money;

    public BurbujitaControllerOpenGL(MainManager mm) {
        paused = true;
        m_money = 0l;
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
        ManagedActivity.changeActivityToIn(activity, BurbujitaOpenGLActivity.class, miliseconds, false);
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
        transform(x, y);
        x = m_transformedX;
        y = m_transformedY;
        if (paused) {
            lastTimeUpdated = SystemClock.uptimeMillis();
            paused = false;
        }
        if (burbujitaMap != null) {
            if (burbujitaInterface.isActive()) {
                burbujitaInterface.highLight(x, y);
                int tipo = burbujitaInterface.getSelectedButton();
                if (-1 < tipo && tipo < 9) {
                    burbujitaMap.buildPreTurret(tipo);
                } else {
                    burbujitaMap.destroyPreBuildTurret();
                }
                burbujitaMap.highLight((int) x, (int) y);
            } else {
                burbujitaMap.highLight((int) x, (int) y);
            }
            //burbujitaMap.createNewNextTurret((int) x, (int) y);
        }
    }

    public void sendActionMove(float x, float y) {
        transform(x, y);
        x = m_transformedX;
        y = m_transformedY;
        if (burbujitaMap != null) {
            if (burbujitaInterface.isActive()) {
                burbujitaInterface.highLight(x, y);
                int tipo = burbujitaInterface.getSelectedButton();
                if (-1 < tipo && tipo < 9) {
                    burbujitaMap.buildPreTurret(tipo);
                } else {
                    burbujitaMap.destroyPreBuildTurret();
                }
                burbujitaMap.highLight((int) x, (int) y);
            } else {
                burbujitaMap.highLight((int) x, (int) y);
            }
            //burbujitaMap.setNextTurret((int) x, (int) y);
        }
    }

    public void sendActionUp(float x, float y) {
        transform(x, y);
        x = m_transformedX;
        y = m_transformedY;
        if (burbujitaMap != null) {
            burbujitaMap.destroyPreBuildTurret();
            if (burbujitaInterface.isActive()) {
                int tipo = burbujitaInterface.getSelectedButton();
                if (-1 < tipo && tipo < 9) {
                    burbujitaMap.buildTurret(tipo);
                    burbujitaMap.destroyPreBuildTurret();
                    burbujitaInterface.desSeleccionar();
                    burbujitaInterface.startRetracting();
                } else {
                    burbujitaInterface.highLight(x, y);
                    burbujitaMap.highLight((int) x, (int) y);
                }
            } else {
                burbujitaMap.highLight((int) x, (int) y);
                if (burbujitaMap.canBuildTurretOn((int) x, (int) y)) {
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

    /*public void onViewReady(int width, int height) {
        trace("Creamos Interface de 8");
        burbujitaMap = new BurbujitaMap();
        screenSizeWidth = width;
        screenSizeHeight = height;
        burbujitaInterface = new Interface(8, burbujitaMap.getMapWidth() * 100, burbujitaMap.getMapHeight() * 100);
        render();
    }*/

    public void onViewChanged(int width, int height) throws Exception {
        trace("Creamos Interface de 8");
            burbujitaMap = new BurbujitaMap();
            screenSizeWidth = width;
            screenSizeHeight = height;
            render();
    }

    public Interface getInterface() {
        return burbujitaInterface;
    }

    public void createInterface(float left, float right, float bottom, float top) {
        m_left = left;
        m_right = right;
        m_bottom = bottom;
        m_top = top;
        burbujitaInterface = new Interface(3, m_left, m_bottom, m_right, m_top, m_money, true);
    }

    private void transform(float x, float y) {
        m_transformedX = x * (m_right - m_left) / screenSizeWidth + m_left;
        m_transformedY = (screenSizeHeight - y) * (m_top - m_bottom) / screenSizeHeight + m_bottom;
    }
}

