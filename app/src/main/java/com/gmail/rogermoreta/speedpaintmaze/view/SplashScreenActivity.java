package com.gmail.rogermoreta.speedpaintmaze.view;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.gmail.rogermoreta.speedpaintmaze.controller.MainManager;
import com.gmail.rogermoreta.speedpaintmaze.controller.ManagedActivity;
import com.gmail.rogermoreta.speedpaintmaze.enums.Section;

public class SplashScreenActivity extends ManagedActivity {

    private static MainManager MM = MainManager.getInstance();

	@Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getGameHelper().setMaxAutoSignInAttempts(0);
		super.onCreate(savedInstanceState);

	}

    @Override
    public void onSignInFailed() {
        MM.goToSection(Section.MENU, this);
    }

    @Override
    public void onSignInSucceeded() {
        MM.goToSection(Section.MENU, this);
    }
}
