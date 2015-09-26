package com.gmail.rogermoreta.speedpaintmaze.view;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gmail.rogermoreta.speedpaintmaze.controller.MainManager;

public class MazeView extends SurfaceView implements SurfaceHolder.Callback {

    public MazeView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        MainManager.getMazeController().onViewReady(this.getHolder());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                MainManager.getMazeController().sendActionDown(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                MainManager.getMazeController().sendActionMove(x, y);
                break;
            case MotionEvent.ACTION_UP:
                MainManager.getMazeController().sendActionUp(x, y);
                break;
            default:
                return false;
        }
        return true;
    }
}
