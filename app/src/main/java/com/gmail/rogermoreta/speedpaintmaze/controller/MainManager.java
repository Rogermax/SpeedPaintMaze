package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.SurfaceHolder;

import com.gmail.rogermoreta.speedpaintmaze.enums.Section;
import com.gmail.rogermoreta.speedpaintmaze.enums.Sound;
import com.gmail.rogermoreta.speedpaintmaze.javaandroid.Trace;
import com.gmail.rogermoreta.speedpaintmaze.model.BurbujitaMap;
import com.gmail.rogermoreta.speedpaintmaze.model.Interface;

public class MainManager {

    private static MainManager instance;
    private static MenuController menuController;
    private static MazeController mazeController;
    private static BurbujitaController burbujitaController;
    private static BurbujitaControllerOpenGL burbujitaControllerOpenGL;
    private Section sectionActual;
    private Pintor pintor;
    private Musico musico;
    private Context context;
    private BurbujitaGLRenderer rendererOpenGL;

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
            case BURBU:
                burbujitaController = new BurbujitaController(this);
                trace("Mostramos actividad burbujitaController en 0.5 segundos.");
                burbujitaController.mostrarActividad(activity, 500l);
                break;
            case BURBUOPENGL:
                burbujitaControllerOpenGL = new BurbujitaControllerOpenGL(this);
                trace("Mostramos actividad burbujitaControllerOpenGL en 0.5 segundos.");
                burbujitaControllerOpenGL.mostrarActividad(activity, 500l);
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
        if (burbujitaControllerOpenGL != null) {
            trace("Enviamos pause a burbujitaOpenGL");
            burbujitaControllerOpenGL.pause();
        }
    }


    public void resumeBurbujita() {
        if (burbujitaController != null) {
            trace("Enviamos resume a burbujita");
            burbujitaController.resume();
        }
        if (burbujitaControllerOpenGL != null) {
            trace("Enviamos resume a burbujitaOpenGL");
            burbujitaControllerOpenGL.resume();
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

    /*public Canvas drawObjectIntoCanvas(Canvas canvas, Object object) {
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
    }*/

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
        if (burbujitaControllerOpenGL != null) {
            burbujitaControllerOpenGL.sendActionDown(x, y);
        }
    }

    public void sendActionMoveToBurbujita(float x, float y) {
        if (burbujitaController != null) {
            burbujitaController.sendActionMove(x, y);
        }
        if (burbujitaControllerOpenGL != null) {
            burbujitaControllerOpenGL.sendActionMove(x, y);
        }
    }

    public void sendActionUpToBurbujita(float x, float y) {
        if (burbujitaController != null) {
            burbujitaController.sendActionUp(x, y);
        }
        if (burbujitaControllerOpenGL != null) {
            burbujitaControllerOpenGL.sendActionUp(x, y);
        }
    }

    public void burbujitaViewReady(SurfaceHolder holder) {
        trace("Burbujita view READY!");
        if (burbujitaController != null) {
            burbujitaController.onViewReady(holder);
            pintor.setBurbujitaHolder(holder);
        }
        else {
            Log.d("MainManager", "burbujitaViewReady::fail->burbujitaController es null");
        }
    }

    public void burbujitaViewChanged(SurfaceHolder holder) {
        trace("Burbujita view CHANGE!");
        if (burbujitaController != null) {
            burbujitaController.onViewChanged(holder);
            pintor.setBurbujitaHolder(holder);
        }
        else {
            Log.d("MainManager", "burbujitaViewChanged::fail->burbujitaController es null");
        }
    }

    public BurbujitaMap getBurbujitaMap() {
        if (burbujitaController != null) {
            return burbujitaController.getBurbujitaMap();
        }
        if (burbujitaControllerOpenGL != null) {
            return burbujitaControllerOpenGL.getBurbujitaMap();
        }
        else return null;
    }

    public Context getContext() {
        return context;
    }

    public void drawBurbujitaMapAndInterface(BurbujitaMap burbujitaMap, Interface burbujitaInterface) {
        pintor.drawBurbujitaMapAndInterface(burbujitaMap, burbujitaInterface);
    }

    public void setRendererOpenGL(BurbujitaGLRenderer rendererOpenGL) {
        this.rendererOpenGL = rendererOpenGL;
    }

    public void burbujitaOpenGLViewChanged(int width, int height) {
        if (burbujitaControllerOpenGL != null) {
            burbujitaControllerOpenGL.onViewChanged(width,height);
        }
        else {
            Log.d("MainManager", "burbujitaViewChanged::fail->burbujitaControllerOpenGL es null");
        }
    }
}
