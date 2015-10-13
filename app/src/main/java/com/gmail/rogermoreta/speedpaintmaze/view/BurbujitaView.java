package com.gmail.rogermoreta.speedpaintmaze.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gmail.rogermoreta.speedpaintmaze.controller.MainManager;

public class BurbujitaView extends SurfaceView implements SurfaceHolder.Callback {

    private MainManager MM = MainManager.getInstance();

    public BurbujitaView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        MM.burbujitaViewReady(this.getHolder());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        MM.burbujitaViewChanged(this.getHolder());
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public boolean onTouchEvent(@NonNull MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                MM.sendActionDownToBurbujita(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                MM.sendActionMoveToBurbujita(x, y);
                break;
            case MotionEvent.ACTION_UP:
                MM.sendActionUpToBurbujita(x, y);
                break;
            default:
                return false;
        }
        return true;
    }
}
