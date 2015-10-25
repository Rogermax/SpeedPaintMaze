package com.gmail.rogermoreta.speedpaintmaze.view;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.gmail.rogermoreta.speedpaintmaze.controller.MainManager;
import com.gmail.rogermoreta.speedpaintmaze.controller.ManagedActivity;

public class BurbujitaOpenGLActivity  extends ManagedActivity {

    private GLSurfaceView mGLView;
    private static MainManager MM = MainManager.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getGameHelper().setMaxAutoSignInAttempts(0);
        super.onCreate(savedInstanceState);
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        mGLView = new BurbujitaOpenGLView(this);
        setContentView(mGLView);
    }



    @Override
    public void onResume() {
        super.onResume();
        MM.resumeBurbujita();
    }

    @Override
    public void onPause(){
        super.onPause();
        MM.pauseBurbujita();
    }


    @Override
    public void onSignInFailed() {

    }

    @Override
    public void onSignInSucceeded() {

    }
}
