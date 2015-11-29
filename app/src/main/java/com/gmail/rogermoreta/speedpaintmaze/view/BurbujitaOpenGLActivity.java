package com.gmail.rogermoreta.speedpaintmaze.view;

import android.os.Bundle;

import com.gmail.rogermoreta.speedpaintmaze.controller.BurbujitaGLRenderer;
import com.gmail.rogermoreta.speedpaintmaze.controller.MainManager;
import com.gmail.rogermoreta.speedpaintmaze.controller.ManagedActivity;

public class BurbujitaOpenGLActivity extends ManagedActivity {

    private static MainManager MM = MainManager.getInstance();

    private BurbujitaOpenGLView mGLSurfaceView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getGameHelper().setMaxAutoSignInAttempts(0);
        super.onCreate(savedInstanceState);

        mGLSurfaceView = new BurbujitaOpenGLView(this);

        // Set the renderer to our demo renderer, defined below.
        mGLSurfaceView.setRenderer(new BurbujitaGLRenderer(this));

        setContentView(mGLSurfaceView);
    }

    @Override
    protected void onResume() {
        // The activity must call the GL surface view's onResume() on activity
        // onResume().
        super.onResume();
        MM.resumeBurbujita();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        // The activity must call the GL surface view's onPause() on activity
        // onPause().
        super.onPause();
        MM.pauseBurbujita();
        mGLSurfaceView.onPause();
    }


    @Override
    public void onSignInFailed() {

    }

    @Override
    public void onSignInSucceeded() {

    }
}
