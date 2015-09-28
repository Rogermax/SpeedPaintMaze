package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.util.Pair;
import android.view.SurfaceHolder;

import com.gmail.rogermoreta.speedpaintmaze.model.Maze;
import com.gmail.rogermoreta.speedpaintmaze.view.MazeActivity;


public class MazeController extends Controller {

    private MazeActivity mazeActivity;

    //attributes of view layer
    //private Layout layout;
    //private ArrayList<Bitmap> images;

    //attributes of model layer
    private Maze maze;
    private float step;
    private int width;
    private int height;
    private SurfaceHolder surfaceHolder;
    private int mazeSize;
    private short[][] mazeCellState;
    private int lastI;
    private int lastJ;
    private boolean isOneInAdvise;
    private boolean paused;

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
            lastI = 1;
            lastJ = 1;
            isOneInAdvise = true;
            paused = false;
            maze = new Maze(difficulty);
            maze.generateMazeStartingAt(0, 0);
            mazeSize = maze.getSize();
            mazeCellState = new short[mazeSize][mazeSize];
            final boolean[][] mazeWalls = maze.getMatrixWalls();
            for (int i = 0; i < mazeSize; i++) {
                for (int j = 0; j < mazeSize; j++) {
                    if (mazeWalls[i][j]) {
                        mazeCellState[i][j] = 1;
                    } else {
                        mazeCellState[i][j] = -1;
                    }
                }
            }
            for (Pair<Integer, Integer> p : Maze.getEndPoints()) {
                mazeCellState[p.first][p.second] = -3;
            }
            mazeCellState[1][1] = -2;
        } catch (Exception e) {
            Log.d("MazeController", "Exception en initModel:" + e);
        }
    }

    public void initView() {
        maze.printWalls();
    }

    @SuppressWarnings("unused")
    public boolean testRecursive() {
        return Maze.testRecursive(0);
    }

    @SuppressWarnings("unused")
    public void paint() {
        //View view = new View();
        //mazeActivity.setContentView(view);
    }

    @Override
    public void mostrarActividad(Activity activity, long miliseconds) {
        ManagedActivity.changeActivityToIn(activity, MazeActivity.class, miliseconds, false);
    }


    public void onViewReady(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        Canvas canvas = this.surfaceHolder.lockCanvas();

        stablishSize(canvas);
        drawMaze(canvas);

        this.surfaceHolder.unlockCanvasAndPost(canvas);
    }

    private void drawMaze(Canvas canvas) {
        canvas.drawARGB(255, 255, 255, 255);
        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; ++j) {
                switch (mazeCellState[i][j]) {
                    case -3:
                        canvas = dibujaFinSendero(canvas, i, j);
                        break;
                    case -2:
                        canvas = dibujaAdvertencia(canvas, i, j);
                        break;
                    case 0:
                        canvas = dibujaRastro(canvas, i, j);
                        break;
                    case 1:
                        canvas = dibujaPared(canvas, i, j);
                        break;
                }
            }
        }
        if (paused) {
            canvas.drawARGB(100, 255, 255, 255);
            Paint pincell = new Paint();
            pincell.setARGB(175, 0, 0, 0);
            pincell.setTextSize(Math.min(width,height)/7);
            pincell.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("PAUSED",width/2,height/2,pincell);
        }
    }


    private void stablishSize(Canvas canvas) {
        width = canvas.getWidth();
        height = canvas.getHeight();
        step = Math.min(width, height) / (float) maze.getSize();
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
        /*canvas.drawLine(i0, j0, i1, j0, paint);
        canvas.drawLine(i0, j0, i0, j1, paint);
        canvas.drawLine(i0, j0, i1, j1, paint);
        canvas.drawLine(i1, j0, i0, j1, paint);
        canvas.drawLine(i1, j0, i1, j1, paint);
        canvas.drawLine(i0, j1, i1, j1, paint);*/
        return canvas;
    }

    private Canvas dibujaRastro(Canvas canvas, int i, int j) {
        Paint pincel = new Paint();
        pincel.setARGB(255, 0, 0, 255);
        pincel.setStyle(Paint.Style.FILL);
        float i0 = i * step;
        float i1 = i0 + step;
        float j0 = j * step;
        float j1 = j0 + step;
        canvas.drawCircle((i0 + i1) / 2f, (j0 + j1) / 2, step / 2f, pincel);
        /*canvas.drawLine(i0, j0, i1, j0, paint);
        canvas.drawLine(i0, j0, i0, j1, paint);
        canvas.drawLine(i0, j0, i1, j1, paint);
        canvas.drawLine(i1, j0, i0, j1, paint);
        canvas.drawLine(i1, j0, i1, j1, paint);
        canvas.drawLine(i0, j1, i1, j1, paint);*/
        return canvas;
    }

    private Canvas dibujaAdvertencia(Canvas canvas, int i, int j) {
        Paint pincel = new Paint();
        pincel.setARGB(255, 255, 255, 0);
        pincel.setStyle(Paint.Style.FILL);
        float i0 = i * step;
        float i1 = i0 + step;
        float j0 = j * step;
        float j1 = j0 + step;
        canvas.drawCircle((i0 + i1) / 2f, (j0 + j1) / 2, step / 2f, pincel);
        return canvas;
    }

    private Canvas dibujaFinSendero(Canvas canvas, int i, int j) {
        Paint pincel = new Paint();
        pincel.setARGB(255, 255, 0, 0);
        pincel.setStyle(Paint.Style.FILL);
        float i0 = i * step;
        float i1 = i0 + step;
        float j0 = j * step;
        float j1 = j0 + step;
        canvas.drawCircle((i0 + i1) / 2f, (j0 + j1) / 2, step / 2f, pincel);
        return canvas;
    }

    public void sendActionDown(@SuppressWarnings("UnusedParameters") float x,@SuppressWarnings("UnusedParameters") float y) {

    }

    public void sendActionUp(@SuppressWarnings("UnusedParameters") float x,@SuppressWarnings("UnusedParameters") float y) {
        if (paused) {
            resume();
            Canvas canvas = this.surfaceHolder.lockCanvas();
            drawMaze(canvas);
            this.surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void sendActionMove(float x, float y) {
        if (!paused) {
            final int i;
            final int j;
            if (width < height) {
                i = (int) (x / (float) width * mazeSize);
                j = (int) (y / (float) width * mazeSize);
            } else {
                i = (int) (x / (float) height * mazeSize);
                j = (int) (y / (float) height * mazeSize);
            }
            if (0 <= i && i < mazeSize && 0 <= j && j < mazeSize && (mazeCellState[i][j] == -1 || mazeCellState[i][j] == -2)) {
                if (adjacent(i, j)) {
                    if (!isOneInAdvise) {
                        mazeCellState[i][j] = 0;
                        lastI = i;
                        lastJ = j;
                        mazeActivity.playSound();
                    }
                } else {
                    if (!isOneInAdvise) {
                        isOneInAdvise = true;
                        mazeCellState[lastI][lastJ] = -2;
                    } else {
                        if (lastI == i && lastJ == j) {
                            isOneInAdvise = false;
                            mazeCellState[lastI][lastJ] = 0;
                        }
                    }
                }
                Canvas canvas = this.surfaceHolder.lockCanvas();
                drawMaze(canvas);
                this.surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }


    private boolean adjacent(int i, int j) {
        return Math.abs(lastI - i) + Math.abs(lastJ - j) == 1;
    }


    public void pause() {
        paused = true;
        if (this.surfaceHolder != null) {
            Canvas canvas = this.surfaceHolder.lockCanvas();
            drawMaze(canvas);
            this.surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void resume() {
        paused = false;
    }

    public void setActivity(MazeActivity activity) {
        this.mazeActivity = activity;
    }
}