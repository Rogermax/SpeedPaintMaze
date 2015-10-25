package com.gmail.rogermoreta.speedpaintmaze.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.gmail.rogermoreta.speedpaintmaze.controller.BurbujitaGLRenderer;
import com.gmail.rogermoreta.speedpaintmaze.controller.MainManager;

public class BurbujitaOpenGLView  extends GLSurfaceView implements SurfaceHolder.Callback{

    private final BurbujitaGLRenderer mRenderer;
    private MainManager MM = MainManager.getInstance();

    public BurbujitaOpenGLView(Context context){
        super(context);
        getHolder().addCallback(this);

        // Create an OpenGL ES 3.0 context
        setEGLContextClientVersion(3);

        mRenderer = new BurbujitaGLRenderer();
        MM.setRendererOpenGL(mRenderer);

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
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