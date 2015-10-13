package com.gmail.rogermoreta.speedpaintmaze.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gmail.rogermoreta.speedpaintmaze.controller.MainManager;

public class MazeView extends SurfaceView implements SurfaceHolder.Callback {

    private MainManager MM = MainManager.getInstance();

    public MazeView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        MM.mazeViewReady(this.getHolder());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public boolean onTouchEvent(@NonNull MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                MM.sendActionDownToMaze(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                MM.sendActionMoveToMaze(x, y);
                break;
            case MotionEvent.ACTION_UP:
                MM.sendActionUpToMaze(x, y);
                break;
            default:
                return false;
        }
        return true;
    }
}
