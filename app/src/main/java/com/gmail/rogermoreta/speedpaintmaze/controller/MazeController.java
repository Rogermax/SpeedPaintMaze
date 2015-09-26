package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.util.Log;
import android.view.SurfaceHolder;

import com.gmail.rogermoreta.speedpaintmaze.model.Maze;
import com.gmail.rogermoreta.speedpaintmaze.view.MazeActivity;

import java.util.ArrayList;

public class MazeController extends Controller {

    private MazeActivity mazeActivity;

    //attributes of view layer
    private Layout layout;
    private ArrayList<Bitmap> images;

    //attributes of model layer
    private Maze maze;


    public MazeController() {
        super();
        mazeActivity = new MazeActivity();
    }

    /**
     * inicializa el modelo lógico del laberinto (maze)
     *
     * @param difficulty: complejidad con la que se va a crear el mapa.
     */
    public void initModel(int difficulty) {
        try {
            maze = new Maze(difficulty);
            maze.generateMazeStartingAt(0, 0);
        } catch (Exception e) {
            Log.d("MazeController", "Exception en initModel:" + e);
        }
    }

    public void initView() {
        maze.printWalls();
    }

    public boolean testRecursive() {
        return Maze.testRecursive(0);
    }

    public void paint() {
        //View view = new View();
        //mazeActivity.setContentView(view);
    }

    @Override
    public void mostrarActividad(Activity activity, long miliseconds) {
        mazeActivity.changeActivityToIn(activity, MazeActivity.class, miliseconds);
    }


    public void onViewReady(SurfaceHolder surfaceHolder) {
        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.drawARGB(255, 255, 255, 255);

        final int mazeSize = maze.getSize();
        final boolean[][] mazeWalls = maze.getMatrixWalls();


        int w = canvas.getWidth();
        int h = canvas.getHeight();

        final float stepX = w / (float)mazeSize;
        final float stepY = h / (float)mazeSize;


        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; ++j) {
                if (mazeWalls[i][j]) {
                    canvas = dibujaPared(canvas, i, j, stepX, stepY);
                }
            }
        }
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    private Canvas dibujaPared(Canvas canvas, int i, int j, float stepX, float stepY) {
        Paint paint = new Paint();
        paint.setARGB(255, 0, 0, 0);
        float i0 = i*stepX;
        float i1 = i0+stepX;
        float j0 = j*stepX;
        float j1 = j0+stepX;
        canvas.drawLine(i0, j0, i1, j0, paint);
        canvas.drawLine(i0, j0, i0, j1, paint);
        canvas.drawLine(i0, j0, i1, j1, paint);
        canvas.drawLine(i1, j0, i0, j1, paint);
        canvas.drawLine(i1, j0, i1, j1, paint);
        canvas.drawLine(i0, j1, i1, j1, paint);
        return canvas;
    }
}