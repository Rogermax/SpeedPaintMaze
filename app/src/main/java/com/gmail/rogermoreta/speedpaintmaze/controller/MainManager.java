package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.app.Activity;
import android.util.Log;

import com.gmail.rogermoreta.speedpaintmaze.enums.Section;
import com.gmail.rogermoreta.speedpaintmaze.view.MazeActivity;
import com.gmail.rogermoreta.speedpaintmaze.view.MenuActivity;

public class MainManager {

    private static MainManager instance;
    private static Section sectionActual;
    private static MenuController menuController;
    private static MazeController mazeController;

    private MainManager() {
        sectionActual = Section.NONE;
        menuController = new MenuController();
        mazeController = new MazeController();
    }

    public static MainManager getInstance() {
        if (instance == null) {
            instance = new MainManager();
        }
        return instance;
    }



    public void goToSection(Section nextSection, Activity activity) {
        sectionActual = nextSection;
        switch (sectionActual) {
            case MENU:
                menuController.mostrarActividad(activity, 3000l);
                break;
            case MAZE:
                mazeController.initModel(5);
                mazeController.initView();
                mazeController.mostrarActividad(activity,500l);
                break;
            default:
                Log.d("MainManager", "goToSection value not processed:" + sectionActual);
        }
    }

    public MazeController getMazeController() {
        return mazeController;
    }

    public void setInstanceMenuActivity(MenuActivity instanceMenuActivity) {
        menuController.setActivity(instanceMenuActivity);
    }
    public void setInstanceMazeActivity(MazeActivity instanceMazeActivity) {
        mazeController.setActivity(instanceMazeActivity);
    }

    public void pauseMaze() {
        mazeController.pause();
    }
}
