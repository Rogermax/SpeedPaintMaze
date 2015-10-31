package com.gmail.rogermoreta.speedpaintmaze.view;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.gmail.rogermoreta.speedpaintmaze.controller.BurbujitaGLRenderer;
import com.gmail.rogermoreta.speedpaintmaze.controller.MainManager;
import com.gmail.rogermoreta.speedpaintmaze.controller.ManagedActivity;

public class BurbujitaOpenGLActivity extends ManagedActivity {

    private static MainManager MM = MainManager.getInstance();

    private BurbujitaOpenGLView mGLSurfaceView;
    private BurbujitaGLRenderer mRenderer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getGameHelper().setMaxAutoSignInAttempts(0);
        super.onCreate(savedInstanceState);

        mGLSurfaceView = new BurbujitaOpenGLView(this);

        mRenderer = new BurbujitaGLRenderer(this);

        // Set the renderer to our demo renderer, defined below.
        mGLSurfaceView.setRenderer(mRenderer);

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
