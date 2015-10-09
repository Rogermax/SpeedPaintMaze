package com.gmail.rogermoreta.speedpaintmaze.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
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
        MM.getBurbujitaController().onViewReady(this.getHolder());
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
                MM.getBurbujitaController().sendActionDown(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                MM.getBurbujitaController().sendActionMove(x, y);
                break;
            case MotionEvent.ACTION_UP:
                MM.getBurbujitaController().sendActionUp(x, y);
                break;
            default:
                return false;
        }
        return true;
    }
}
