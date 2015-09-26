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
                Log.d("MazeView", "ACTION DOWN: (" + x + "," + y + ")");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("MazeView", "ACTION ACTION_MOVE: (" + x + "," + y + ")");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("MazeView", "ACTION_UP DOWN: (" + x + "," + y + ")");
                break;
            default:
                return false;
        }
        return true;
    }
}
