package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

import com.gmail.rogermoreta.speedpaintmaze.enums.Sound;
import com.gmail.rogermoreta.speedpaintmaze.javaandroid.GameThread;
import com.gmail.rogermoreta.speedpaintmaze.model.MazeConstructor;
import com.gmail.rogermoreta.speedpaintmaze.model.MazeState;
import com.gmail.rogermoreta.speedpaintmaze.model.Pair;
import com.gmail.rogermoreta.speedpaintmaze.view.MazeActivity;

import java.util.ArrayList;


public class MazeController extends Controller {

    //attributes of view layer
    //private Layout layout;
    //private ArrayList<Bitmap> images;

    //attributes of model layer
    private MazeState mazeState;
    private float step;
    private int width;
    private int height;
    private SurfaceHolder surfaceHolder;
    private int[][] mazeColor;
    private boolean[][] mazeWalls;
    private int mazeSize;
    private int lastI;
    private int lastJ;
    private boolean paused;
    private boolean pintaPosibles;
    private ArrayList<Pair> posibleNextPositions;
    private boolean finished;
    private GameThread gameThread;
    private MainManager mm;

    public MazeController(MainManager mm) {
        super();
        this.mm = mm;
    }

    /**
     * inicializa el modelo lógico del laberinto (mazeConstructor)
     *
     * @param difficulty: complejidad con la que se va a crear el mapa.
     */
    public void initModel(int difficulty) {
        try {
            paused = false;
            finished = false;
            pintaPosibles = true;
            mazeState = new MazeState(new MazeConstructor(difficulty));
            mazeColor = mazeState.getColorMap();
            mazeSize = mazeState.getSize();
            mazeWalls = mazeState.getWalls();
            posibleNextPositions = mazeState.getPosibleNextPositions();
            lastI = 0;
            lastJ = 1;
            surfaceHolder = null;
            gameThread = new GameThread(this);
            gameThread.encender();
        } catch (Exception e) {
            Log.d("MazeController", "Exception en initModel:" + e);
        }
    }

    @SuppressWarnings("unused")
    public boolean testRecursive() {
        return MazeConstructor.testRecursive(0);
    }

    @Override
    public void mostrarActividad(Activity activity, long miliseconds) {
        ManagedActivity.changeActivityToIn(activity, MazeActivity.class, miliseconds, false);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        if (!paused) {
            drawMaze();
        }
    }

    @Override
    public boolean isPaused() {
        return false;
    }


    @SuppressWarnings("unused")
    public void onViewReady(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        stablishSize();
        drawMaze();
    }

    private void drawMaze() {
        if (this.surfaceHolder != null) {
            Canvas canvas = this.surfaceHolder.lockCanvas();
            canvas.drawARGB(255, 255, 255, 255);
            for (int i = 0; i < mazeSize; i++) {
                for (int j = 0; j < mazeSize; ++j) {
                    if (mazeWalls[i][j]) {
                        dibujaPared(canvas, i, j);
                    } else {
                        dibujaColor(canvas, i, j);
                    }
                }
            }
            if (pintaPosibles) {
                for (Pair p : posibleNextPositions) {
                    float[] hsv = new float[3];
                    int color = (mazeState.isAnEndPoint(p.first, p.second)) ? mazeState.getColorPosition(p.first, p.second) : mazeState.getCurrentColor();
                    Color.colorToHSV(color, hsv);
                    hsv[2] *= 0.6f; // value component
                    color = Color.HSVToColor(hsv);
                    dibujaAdvise(canvas, p.first, p.second, color);
                }
            }
            Paint pincell = new Paint();
            pincell.setARGB(175, 0, 0, 0);
            pincell.setTextAlign(Paint.Align.CENTER);
            if (paused) {
                pincell.setTextSize(Math.min(width, height) / 7);
                canvas.drawARGB(100, 255, 255, 255);
                canvas.drawText("PAUSED", width / 2, height / 2, pincell);
            }
            pincell.setTextSize(Math.min(width, height) / 15);
            canvas.drawText("Time: "+ SystemClock.uptimeMillis(), width / 2, 3 * height / 4, pincell);
            this.surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }


    private void stablishSize() {
        Canvas canvas = this.surfaceHolder.lockCanvas();
        width = canvas.getWidth();
        height = canvas.getHeight();
        step = Math.min(width, height) / (float) mazeState.getSize();
        this.surfaceHolder.unlockCanvasAndPost(canvas);
    }

    private Canvas dibujaPared(Canvas canvas, int i, int j) {
        Paint pincel = new Paint();
        pincel.setARGB(255, 0, 0, 0);
        pincel.setStyle(Paint.Style.FILL);
        float i0 = i * step;
        float i1 = i0 + step;
        float j0 = j * step;
        float j1 = j0 + step;
        canvas.drawRect(i0, j1, i1, j0, pincel);
        return canvas;
    }

    private Canvas dibujaColor(Canvas canvas, int i, int j) {
        Paint pincel = new Paint();
        pincel.setColor(mazeColor[i][j]);
        pincel.setStyle(Paint.Style.FILL);
        float i0 = i * step;
        float i1 = i0 + step;
        float j0 = j * step;
        float j1 = j0 + step;
        canvas.drawCircle((i0 + i1) / 2f, (j0 + j1) / 2, step / 2f, pincel);
        return canvas;
    }

    private Canvas dibujaAdvise(Canvas canvas, int i, int j, int color) {
        Paint pincel = new Paint();
        pincel.setColor(color);
        pincel.setStyle(Paint.Style.FILL);
        float i0 = i * step;
        float i1 = i0 + step;
        float j0 = j * step;
        float j1 = j0 + step;
        canvas.drawCircle((i0 + i1) / 2f, (j0 + j1) / 2, step / 2f, pincel);
        return canvas;
    }

    @SuppressWarnings("unused")
    public void sendActionDown(@SuppressWarnings("UnusedParameters") float x, @SuppressWarnings("UnusedParameters") float y) {

    }

    @SuppressWarnings("unused")
    public void sendActionUp(@SuppressWarnings("UnusedParameters") float x, @SuppressWarnings("UnusedParameters") float y) {
        if (!finished && paused) {
            resume();
            drawMaze();
        }
    }

    @SuppressWarnings("unused")
    public void sendActionMove(float x, float y) {
        if (!finished && !paused) {
            final int i;
            final int j;
            if (width < height) {
                i = (int) (x / (float) width * mazeSize);
                j = (int) (y / (float) width * mazeSize);
            } else {
                i = (int) (x / (float) height * mazeSize);
                j = (int) (y / (float) height * mazeSize);
            }
            if ((lastI != i || lastJ != j) && 0 <= i && i < mazeSize && 0 <= j && j < mazeSize) {
                int positionInsidePosibles = getPositionInsidePosibles(i, j);
                if (positionInsidePosibles > -1) {
                    mazeState.moveToPosible(positionInsidePosibles);
                    pintaPosibles = mazeState.locationOfTheEndPoint() >= 0;
                    drawMaze();
                    mm.playSound(Sound.BEEP);
                } else {
                    if (posibleNextPositions.isEmpty()) {
                        finish();
                    }
                    else {
                        pintaPosibles = true;
                        drawMaze();
                    }
                }
                lastI = i;
                lastJ = j;
            }
        }
    }

    private int getPositionInsidePosibles(int i, int j) {
        int cont = 0;
        for (Pair p : posibleNextPositions) {
            if (p.first == i && p.second == j) {
                return cont;
            }
            cont++;
        }
        return -1;
    }

    public void finish() {
        gameThread.apagar();
        finished = true;
        paused = true;
        drawMaze();
    }

    public void pause() {
        paused = true;
        drawMaze();
        //mazeState.saveState(mazeActivity);
    }

    public void resume() {
        paused = false;
        //mazeState.loadState(mazeActivity);
    }

}