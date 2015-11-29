package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.SurfaceHolder;

import com.gmail.rogermoreta.speedpaintmaze.enums.Section;
import com.gmail.rogermoreta.speedpaintmaze.enums.Sound;
import com.gmail.rogermoreta.speedpaintmaze.javaandroid.Trace;

public class MainManager {

    private static MainManager instance;
    private static MenuController menuController;
    private static MazeController mazeController;
    private static BurbujitaControllerOpenGL burbujitaControllerOpenGL;
    private Section sectionActual;
    private Musico musico;
    private Context context;

    private MainManager() {
        sectionActual = Section.NONE;
        menuController = new MenuController();
        mazeController = new MazeController(this);
        trace("================ I P L ================");
    }

    public BurbujitaControllerOpenGL getBurbujitaControllerOpenGL() {
        return burbujitaControllerOpenGL;
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
            case BURBUOPENGL:
                burbujitaControllerOpenGL = new BurbujitaControllerOpenGL();
                trace("Mostramos actividad burbujitaControllerOpenGL en 0.5 segundos.");
                burbujitaControllerOpenGL.mostrarActividad(activity, 500l);
            default:
                Log.d("MainManager", "goToSection value not processed:" + sectionActual);
        }
    }

    public void pauseMaze() {
        if (mazeController != null) {
            mazeController.pause();
        }
    }

    public void pauseBurbujita() {
        if (burbujitaControllerOpenGL != null) {
            trace("Enviamos pause a burbujita");
            burbujitaControllerOpenGL.pause();
        }
    }


    public void resumeBurbujita() {
        if (burbujitaControllerOpenGL != null) {
            trace("Enviamos resume a burbujitaOpenGL");
            burbujitaControllerOpenGL.resume();
        }
    }

    public void setContext(@NonNull Context context) {
        trace("Creamos pintor y musico");
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
        if (burbujitaControllerOpenGL != null) {
            burbujitaControllerOpenGL.sendActionDown(x, y);
        }
    }

    public void sendActionMoveToBurbujita(float x, float y) {
        if (burbujitaControllerOpenGL != null) {
            burbujitaControllerOpenGL.sendActionMove(x, y);
        }
    }

    public void sendActionUpToBurbujita(float x, float y) {
        if (burbujitaControllerOpenGL != null) {
            burbujitaControllerOpenGL.sendActionUp(x, y);
        }
    }
    public Context getContext() {
        return context;
    }

    public void burbujitaOpenGLViewChanged(int width, int height) {
        try {
            if (burbujitaControllerOpenGL != null) {
                burbujitaControllerOpenGL.onViewChanged(width, height);
            }
            else {
                Log.d("MainManager", "burbujitaViewChanged::fail->burbujitaControllerOpenGL es null");
            }
        }
        catch(Exception e) {
            trace("Error en onViewChange:"+e.toString());
        }
    }
}
