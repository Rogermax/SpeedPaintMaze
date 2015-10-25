package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.app.Activity;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import com.gmail.rogermoreta.speedpaintmaze.javaandroid.GameThread;
import com.gmail.rogermoreta.speedpaintmaze.javaandroid.Trace;
import com.gmail.rogermoreta.speedpaintmaze.model.BurbujitaMap;
import com.gmail.rogermoreta.speedpaintmaze.view.BurbujitaActivity;
import com.gmail.rogermoreta.speedpaintmaze.view.BurbujitaOpenGLActivity;

import java.util.ArrayList;

public class BurbujitaController extends Controller {

    @SuppressWarnings("FieldCanBeLocal")
    private GameThread gameThread;

    private BurbujitaMap burbujitaMap;
    private Long lastTimeUpdated;
    private Long lastTimeDrawed;
    private ArrayList<Long> historicTimeUpdates;
    private ArrayList<Long> historicTimeDraws;
    private int nextIndexUpdate;
    private int nextIndexDraw;
    private MainManager MM;
    private boolean isOpenGL;

    public BurbujitaController(MainManager mm) {
        isOpenGL = false;
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

    private void trace(String str) {
        Trace.write(" BurbujitaController::" + str);
    }

    @Override
    public void mostrarActividad(Activity activity, long miliseconds) {
        ManagedActivity.changeActivityToIn(activity, BurbujitaActivity.class, miliseconds, false);
    }

    public void mostrarActividadOpenGL(Activity activity, long miliseconds) {
        isOpenGL = true;
        ManagedActivity.changeActivityToIn(activity, BurbujitaOpenGLActivity.class, miliseconds, false);
    }

    public void onViewReady(SurfaceHolder surfaceHolder) {
        burbujitaMap = new BurbujitaMap(surfaceHolder, this);
        draw();
    }

    private void logic(long milisec) {
        if (burbujitaMap != null && !paused) {
            burbujitaMap.logic(milisec);
        }
    }

    public Canvas drawObjectIntoCanvas(Canvas canvas, Object object) {
        if (isOpenGL) {
            return canvas;
        }
        else {
            return MM.drawObjectIntoCanvas(canvas, object);
        }
    }

    private void draw() {
        if (burbujitaMap != null) {
            try {
                if (lastTimeDrawed != null) {
                    historicTimeDraws.set(nextIndexDraw, System.currentTimeMillis() - lastTimeDrawed);
                }
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
                if (lastTimeUpdated != null) {
                    historicTimeUpdates.set(nextIndexUpdate, System.currentTimeMillis() - lastTimeUpdated);
                }
                else {
                    lastTimeUpdated = System.currentTimeMillis();
                }
                nextIndexUpdate = (nextIndexUpdate + 1) % 5;
                logic(System.currentTimeMillis() - lastTimeUpdated);
                lastTimeUpdated = System.currentTimeMillis();
            }
            catch (Exception ignored){}
        }
    }

    @Override
    public void render() {
        draw();
    }

    public void pause() {
        Log.d("BurbujitaController", "PAUSA ACTIVADA");
        paused = true;
        draw();
    }


    public void resume() {
        //nothing to do, quitamos el pause cuando detectamos una pulsacion
    }

    public void sendActionDown(float x, float y) {
        if (paused) {
            lastTimeUpdated = System.currentTimeMillis();
            paused = false;
        }
        if (burbujitaMap != null) {
            if (burbujitaMap.insterfaceIsActive(x,y)) {
                burbujitaMap.highLightInterfaceButtons((int)x, (int) y);
            }
            else {
                burbujitaMap.highLight((int)x, (int) y);
            }
            //burbujitaMap.createNewNextTurret((int) x, (int) y);
        }
    }

    public void sendActionMove(float x, float y) {
        if (burbujitaMap != null) {
            if (burbujitaMap.insterfaceIsActive(x,y)) {
                burbujitaMap.highLightInterfaceButtons((int) x, (int) y);
            }
            else {
                burbujitaMap.highLight((int) x, (int) y);
            }
            //burbujitaMap.setNextTurret((int) x, (int) y);
        }
    }

    public void sendActionUp(float x, float y) {
        if (burbujitaMap != null) {
            if (burbujitaMap.insterfaceIsActive(x,y)) {
                burbujitaMap.buildTurret();
            }
            else {
                //muestra el interface si es un punto de construcicon
                burbujitaMap.showInterface((int) x, (int) y);
            }
        }
    }

    public void onSurfaceChange(SurfaceHolder holder) {
        if (burbujitaMap != null) {
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                burbujitaMap.reajustarTamaño(canvas);
                holder.unlockCanvasAndPost(canvas);
            }
            else {
                trace("canvas es null");
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

