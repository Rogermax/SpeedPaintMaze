package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.gmail.rogermoreta.speedpaintmaze.enums.Section;
import com.gmail.rogermoreta.speedpaintmaze.view.MazeActivity;
import com.gmail.rogermoreta.speedpaintmaze.view.MenuActivity;
import com.gmail.rogermoreta.speedpaintmaze.view.SplashScreenActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainManager {

    private static MainManager instance;
    private static Section sectionActual;
    private static MenuController menuController;
    private static MazeController mazeController;

    private MainManager() {
        sectionActual = Section.NONE;
    }

    public static MainManager getInstance() {
        if (instance == null) {
            instance = new MainManager();
        }
        return instance;
    }

    public static void goToSection(Section nextSection, Activity activity) {
        sectionActual = nextSection;
        switch (sectionActual) {
            case MENU:
                menuController = new MenuController();
                menuController.mostrarActividad(activity,3000l);
                break;
            case MAZE:
                mazeController = new MazeController();
                mazeController.initModel(22);
                mazeController.initView();
                mazeController.mostrarActividad(activity,500l);
                break;
            default:
                Log.d("MainManager", "goToSection value not processed:" + sectionActual);
        }
    }

    public static MazeController getMazeController() {
        return mazeController;
    }
}
