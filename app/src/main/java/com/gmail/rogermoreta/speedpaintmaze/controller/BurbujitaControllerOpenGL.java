package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.app.Activity;
import android.os.SystemClock;
import android.util.Log;

import com.gmail.rogermoreta.speedpaintmaze.javaandroid.Trace;
import com.gmail.rogermoreta.speedpaintmaze.model.BurbujitaMap;
import com.gmail.rogermoreta.speedpaintmaze.model.Interface;
import com.gmail.rogermoreta.speedpaintmaze.view.BurbujitaOpenGLActivity;

public class BurbujitaControllerOpenGL extends Controller {

    private BurbujitaMap burbujitaMap;
    private Interface burbujitaInterface;
    private Long lastTimeUpdated;
    private int screenSizeHeight;
    private int screenSizeWidth;
    private float m_left;
    private float m_right;
    private float m_bottom;
    private float m_top;
    private float m_transformedX;
    private float m_transformedY;

    public BurbujitaControllerOpenGL() {
        paused = true;
    }

    @SuppressWarnings("unused")
    private void trace(String str) {
        Trace.write(" BurbujitaControllerOpenGL::" + str);
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
                logic(SystemClock.uptimeMillis() - lastTimeUpdated);
                lastTimeUpdated = SystemClock.uptimeMillis();
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public void render() {
        //Nothing to do, lo trata openGL en su propio Thread.
    }

    public void pause() {
        Log.d("BurbujitaControllOpenGL", "PAUSA ACTIVADA");
        trace("------------- PAUSA ACTIVADA -------------");
        paused = true;
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
                    burbujitaMap.buildPreTurret(tipo, 100);
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
                    burbujitaMap.buildPreTurret(tipo,100);
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
                    burbujitaMap.buildTurret(tipo,100);
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

    public void onViewChanged(int width, int height) throws Exception {
        trace("Creamos Interface de 8");
        burbujitaMap = new BurbujitaMap();
        screenSizeWidth = width;
        screenSizeHeight = height;
        lastTimeUpdated = SystemClock.uptimeMillis();
    }

    public Interface getInterface() {
        return burbujitaInterface;
    }

    public void createInterface(float left, float right, float bottom, float top) {
        m_left = left;
        m_right = right;
        m_bottom = bottom;
        m_top = top;
        burbujitaInterface = new Interface(3, m_left, m_bottom, m_right, m_top, true);
    }

    private void transform(float x, float y) {
        m_transformedX = x * (m_right - m_left) / screenSizeWidth + m_left;
        m_transformedY = (screenSizeHeight - y) * (m_top - m_bottom) / screenSizeHeight + m_bottom;
    }
}

