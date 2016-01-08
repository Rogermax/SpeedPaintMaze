package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.app.Activity;
import android.os.SystemClock;
import android.util.Log;

import com.gmail.rogermoreta.speedpaintmaze.enums.TipoDisparo;
import com.gmail.rogermoreta.speedpaintmaze.javaandroid.Trace;
import com.gmail.rogermoreta.speedpaintmaze.model.BurbujitaMap;
import com.gmail.rogermoreta.speedpaintmaze.model.Interface;
import com.gmail.rogermoreta.speedpaintmaze.view.BurbujitaOpenGLActivity;

public class BurbujitaControllerOpenGL extends Controller {

    private BurbujitaMap burbujitaMap;
    private Interface burbujitaInterface;
    private Long lastTimeUpdated;
    private int screenSizeHeight;
    private int screenSizeWidth;
    private float m_left;
    private float m_right;
    private float m_bottom;
    private float m_top;
    private float m_transformedX;
    private float m_transformedY;
    private int m_tipo;
    private int m_interfaceMostrada;


    public BurbujitaControllerOpenGL() {
        m_interfaceMostrada = 0;
        paused = true;
    }

    @SuppressWarnings("unused")
    private void trace(String str) {
        Trace.write(" BurbujitaControllerOpenGL::" + str);
    }

    @Override
    public void mostrarActividad(Activity activity, long miliseconds) {
        ManagedActivity.changeActivityToIn(activity, BurbujitaOpenGLActivity.class, miliseconds, false);
    }

    private void logic(long milisec) {
        if (burbujitaMap != null && !paused) {
            burbujitaMap.logic(milisec);
        }
        if (burbujitaInterface != null) {
            burbujitaInterface.logic(milisec);
        }
    }

    @Override
    public void update() {
        if (!paused) {
            try {
                logic(SystemClock.uptimeMillis() - lastTimeUpdated);
                lastTimeUpdated = SystemClock.uptimeMillis();
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public void render() {
        //Nothing to do, lo trata openGL en su propio Thread.
    }

    public void pause() {
        Log.d("BurbujitaControllOpenGL", "PAUSA ACTIVADA");
        trace("------------- PAUSA ACTIVADA -------------");
        paused = true;
    }


    public void resume() {
        trace("------------- RESUME ACTIVADO -------------");
        //nothing to do, quitamos el pause cuando detectamos una pulsacion
    }

    public void sendActionDown(float x, float y) {
        transform(x, y);
        if (paused) {
            lastTimeUpdated = SystemClock.uptimeMillis();
            paused = false;
        }
        if (burbujitaMap != null && burbujitaInterface != null) {
            switch (m_interfaceMostrada) {
                case 0: //juego normal, sin interfaces
                    //nothing to do (se hace al levantar el dedo)
                    break;
                case 1: //Interface de escoger torreta
                    pressOnInterface(m_transformedX, m_transformedY);
                    break;
                case 2: //Detalles torreta
                    //nothing to do (se hace al levantar el dedo)
                    break;
                case 3: //Detalle bicho
                    //nothing to do (se hace al levantar el dedo)
                    break;
                default:
            }
        }
    }

    public void sendActionMove(float x, float y) {
        transform(x, y);
        if (burbujitaMap != null && burbujitaInterface != null) {
            switch (m_interfaceMostrada) {
                case 0: //juego normal, sin interfaces
                    //nothing to do (se hace al levantar el dedo)
                    break;
                case 1: //Interface de escoger torreta
                    moveOnInterface(m_transformedX, m_transformedY);
                    break;
                case 2: //Detalles torreta
                    //nothing to do (se hace al levantar el dedo)
                    break;
                case 3: //Detalle bicho
                    //nothing to do (se hace al levantar el dedo)
                    break;
                default:
            }
        }
    }

    public void sendActionUp(float x, float y) {
        transform(x, y);
        if (burbujitaMap != null && burbujitaInterface != null) {
            switch (m_interfaceMostrada) {
                case 0: //juego normal, sin interfaces
                    clickEnMapa(m_transformedX,m_transformedY);
                    break;
                case 1: //Interface de escoger torreta
                    soltarEnInterface(m_transformedX, m_transformedY);
                    break;
                case 2: //Detalles torreta
                    //TODO, no esta hecha la interface 2
                    break;
                case 3: //Detalle bicho
                    //TODO, no esta hecha la interface 2 prima
                    break;
            }
        }
    }

    private void pressOnInterface(float x, float y) {
        burbujitaMap.highLight((int) x,(int) y);
        if (burbujitaInterface.highLight(x, y)) {
            //Si estoy presionando un boton de torreta, la selecciono.
            m_tipo = burbujitaInterface.getSelectedButton();
            switch (m_tipo) {
                case 0:
                    burbujitaMap.buildPreTurret(TipoDisparo.FUEGO, 100);
                    break;
                case 1:
                    burbujitaMap.buildPreTurret(TipoDisparo.VENENO, 100);;
                    break;
                case 2:
                    burbujitaMap.buildPreTurret(TipoDisparo.HIELO, 100);
                    break;
            }
            burbujitaInterface.startRetracting();
        }
        else {
            //Si no estoy presionando, le dejo ese cometido al move
            burbujitaMap.unHighLight();
            burbujitaInterface.desSeleccionar();
            m_tipo = -1;
        }
    }

    private void moveOnInterface(float x, float y) {
        burbujitaMap.highLight((int) x,(int) y);
        if (burbujitaMap.canBuildTurretOn((int)x,(int)y)) {
            switch (m_tipo) {
                case 0:
                    burbujitaMap.buildPreTurret(TipoDisparo.FUEGO, 100);
                    break;
                case 1:
                    burbujitaMap.buildPreTurret(TipoDisparo.VENENO, 100);
                    break;
                case 2:
                    burbujitaMap.buildPreTurret(TipoDisparo.HIELO, 100);
                    break;
                default:
                    burbujitaMap.unHighLight();
                    burbujitaInterface.desSeleccionar();
                    m_tipo = -1;
            }
        }
        else {
            //burbujitaMap.unHighLight();
            //burbujitaInterface.desSeleccionar();
            //m_tipo = -1;
        }
        //burbujitaMap.setNextTurret((int) x, (int) y);
    }

    private void soltarEnInterface(float x, float y) {
        switch (m_tipo) {
            case 0:
                burbujitaMap.buildTurret(TipoDisparo.FUEGO, 100);
                break;
            case 1:
                burbujitaMap.buildTurret(TipoDisparo.VENENO, 100);
                break;
            case 2:
                burbujitaMap.buildTurret(TipoDisparo.HIELO, 100);
                break;
        }
        m_interfaceMostrada = 0;
        burbujitaMap.destroyPreBuildTurret();
        burbujitaMap.unHighLight();
        burbujitaInterface.desSeleccionar();
    }

    private void clickEnMapa(float x, float y) {
        m_interfaceMostrada = 1;
        burbujitaInterface.desSeleccionar();
        burbujitaInterface.startShowing();
    }

    public boolean isPaused() {
        return paused;
    }

    public BurbujitaMap getBurbujitaMap() {
        return burbujitaMap;
    }

    public void onViewChanged(int width, int height) throws Exception {
        trace("Creamos Interface de 8");
        burbujitaMap = new BurbujitaMap();
        screenSizeWidth = width;
        screenSizeHeight = height;
        lastTimeUpdated = SystemClock.uptimeMillis();
    }

    public Interface getInterface() {
        return burbujitaInterface;
    }

    public void createInterface(float left, float right, float bottom, float top) {
        m_left = left;
        m_right = right;
        m_bottom = bottom;
        m_top = top;
        burbujitaInterface = new Interface(3, m_left, m_bottom, m_right, m_top, true);
    }

    private void transform(float x, float y) {
        m_transformedX = x * (m_right - m_left) / screenSizeWidth + m_left;
        m_transformedY = (screenSizeHeight - y) * (m_top - m_bottom) / screenSizeHeight + m_bottom;
    }
}

