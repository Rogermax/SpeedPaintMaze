package com.gmail.rogermoreta.speedpaintmaze.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.gmail.rogermoreta.speedpaintmaze.controller.BurbujitaGLRenderer;
import com.gmail.rogermoreta.speedpaintmaze.controller.MainManager;

public class BurbujitaOpenGLView extends GLSurfaceView{

    private MainManager MM = MainManager.getInstance();

    public BurbujitaOpenGLView(Context context)
    {
        super(context);
        getHolder().addCallback(this);

        // Create an OpenGL ES 3.0 context
        setEGLContextClientVersion(3);
    }

    public BurbujitaOpenGLView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    // Hides superclass method.
    public void setRenderer(BurbujitaGLRenderer renderer)
    {
        super.setRenderer(renderer);
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