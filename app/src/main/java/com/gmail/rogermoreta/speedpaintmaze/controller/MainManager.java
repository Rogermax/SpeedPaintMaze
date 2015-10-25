package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.SurfaceHolder;

import com.gmail.rogermoreta.speedpaintmaze.enums.Section;
import com.gmail.rogermoreta.speedpaintmaze.enums.Sound;
import com.gmail.rogermoreta.speedpaintmaze.javaandroid.Trace;
import com.gmail.rogermoreta.speedpaintmaze.model.BaseMonster;
import com.gmail.rogermoreta.speedpaintmaze.model.Bullet;
import com.gmail.rogermoreta.speedpaintmaze.model.BurbujitaMap;
import com.gmail.rogermoreta.speedpaintmaze.model.Casilla;
import com.gmail.rogermoreta.speedpaintmaze.model.Enemy;
import com.gmail.rogermoreta.speedpaintmaze.model.Interface;
import com.gmail.rogermoreta.speedpaintmaze.model.Turret;

import javax.microedition.khronos.opengles.GL10;

public class MainManager {

    private static MainManager instance;
    private static MenuController menuController;
    private static MazeController mazeController;
    private static BurbujitaController burbujitaController;
    private Section sectionActual;
    private Pintor pintor;
    private Musico musico;
    private BurbujitaGLRenderer burbujitaGLRenderer;
    private Context context;

    private MainManager() {
        sectionActual = Section.NONE;
        menuController = new MenuController();
        mazeController = new MazeController(this);
        burbujitaController = new BurbujitaController(this);
        trace("Creados controllers del mainManager");
    }

    private void trace(String str) {
        Trace.write(" MainManager::"+str);
    }

    public static MainManager getInstance() {
        if (instance == null) {
            instance = new MainManager();
        }
        return instance;
    }


    public void goToSection(Section nextSection, Activity activity) {
        sectionActual = nextSection;
        switch (sectionActual) {
            case MENU:
                menuController.mostrarActividad(activity, 3000l);
                break;
            case MAZE:
                mazeController.initModel(5);
                mazeController.mostrarActividad(activity, 500l);
                break;
            case BURBU:
                trace("Mostramos actividad burbujitaController en 0.5 segundos.");
                burbujitaController.mostrarActividad(activity, 500l);
                break;
            case BURBUOPENGL:
                trace("Mostramos actividad burbujitaController en 0.5 segundos.");
                burbujitaController.mostrarActividadOpenGL(activity, 500l);
            default:
                Log.d("MainManager", "goToSection value not processed:" + sectionActual);
        }
    }

    /*public MazeController getMazeController() {
        return mazeController;
    }

    public BurbujitaController getBurbujitaController() {
        return burbujitaController;
    }*/

    public void pauseMaze() {
        if (mazeController != null) {
            mazeController.pause();
        }
    }

    public void pauseBurbujita() {
        if (burbujitaController != null) {
            trace("Enviamos pause a burbujita");
            burbujitaController.pause();
        }
    }


    public void resumeBurbujita() {
        if (burbujitaController != null) {
            trace("Enviamos resume a burbujita");
            burbujitaController.resume();
        }
    }

    public void setContext(@NonNull Context context) {
        trace("Creamos pintor y musico");
        pintor = new Pintor(context);
        musico = new Musico(context);
        this.context = context;
    }

    public void playSound(Sound sound) {
        if (musico != null) {
            musico.playSound(sound);
        }
        else {
            Log.d("MainManager", "playSound::fail->musico==null");
        }
    }

    public Canvas drawObjectIntoCanvas(Canvas canvas, Object object) {
        //Cuidado con las subclases, BaseMonster extiende Enemigo, por tanto BaseMonster es instancia de Enemigo (pero no al reves)
        //Por tanto si hay subclases, se han de mirar primero las mas particulares y al final la versión  más extensa.
        Canvas canvasRet = canvas;
        if (object instanceof Bullet) {
            canvasRet = Pintor.drawBullet(canvas, (Bullet) object);
        }
        else if (object instanceof Casilla) {
            canvasRet = Pintor.drawCasilla(canvas, (Casilla) object);
        }
        else if (object instanceof BaseMonster) {
            canvasRet = pintor.drawBaseMonster(canvas, (BaseMonster) object);
        }
        else if (object instanceof Enemy) {
            canvasRet = pintor.drawEnemy(canvas, (Enemy) object);
        }
        else if (object instanceof Turret) {
            canvasRet = pintor.drawTurret(canvas, (Turret) object);
        }
        else if (object instanceof Interface) {
            canvasRet = pintor.drawInterface(canvas, (Interface) object);
        }
        else {
            Log.d("MainManager","drawObjectIntoCanvas::fail->do not know how to draw"+object);
        }
        return canvasRet;
    }

    public void sendActionDownToMaze(float x, float y) {
        if (mazeController != null) {
            mazeController.sendActionDown(x, y);
        }
        else {
            Log.d("MainManager","sendActionDownToMaze::fail->mazeController es null");
        }
    }

    public void sendActionMoveToMaze(float x, float y) {
        if (mazeController != null) {
            mazeController.sendActionMove(x, y);
        }
        else {
            Log.d("MainManager","sendActionMoveToMaze::fail->mazeController es null");
        }
    }

    public void sendActionUpToMaze(float x, float y) {
        if (mazeController != null) {
            mazeController.sendActionUp(x, y);
        }
        else {
            Log.d("MainManager","sendActionUpToMaze::fail->mazeController es null");
        }
    }

    public void mazeViewReady(SurfaceHolder holder) {
        if (mazeController != null) {
            mazeController.onViewReady(holder);
        }
        else {
            Log.d("MainManager", "mazeViewReady::fail->mazeController es null");
        }
    }

    public void sendActionDownToBurbujita(float x, float y) {
        if (burbujitaController != null) {
            burbujitaController.sendActionDown(x, y);
        }
        else {
            Log.d("MainManager","sendActionDownToBurbujita::fail->burbujitaController es null");
        }
    }

    public void sendActionMoveToBurbujita(float x, float y) {
        if (burbujitaController != null) {
            burbujitaController.sendActionMove(x, y);
        }
        else {
            Log.d("MainManager","sendActionMoveToBurbujita::fail->burbujitaController es null");
        }
    }

    public void sendActionUpToBurbujita(float x, float y) {
        if (burbujitaController != null) {
            burbujitaController.sendActionUp(x, y);
        }
        else {
            Log.d("MainManager","sendActionUpToBurbujita::fail->burbujitaController es null");
        }
    }

    public void burbujitaViewReady(SurfaceHolder holder) {
        if (burbujitaController != null) {
            burbujitaController.onViewReady(holder);
        }
        else {
            Log.d("MainManager", "burbujitaViewReady::fail->burbujitaController es null");
        }
    }

    public void burbujitaViewChanged(SurfaceHolder holder) {
        if (burbujitaController != null) {
            burbujitaController.onSurfaceChange(holder);
        }
        else {
            Log.d("MainManager", "burbujitaViewChanged::fail->burbujitaController es null");
        }
    }

    public void setRendererOpenGL(BurbujitaGLRenderer burbujitaGLRenderer) {
        this.burbujitaGLRenderer = burbujitaGLRenderer;
    }

    public BurbujitaMap getBurbujitaMap() {
        return burbujitaController.getBurbujitaMap();
    }

    public Context getContext() {
        return context;
    }
}
