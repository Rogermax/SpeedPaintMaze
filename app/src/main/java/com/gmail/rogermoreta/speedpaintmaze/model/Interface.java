package com.gmail.rogermoreta.speedpaintmaze.model;

import com.gmail.rogermoreta.speedpaintmaze.javaandroid.Trace;

import java.util.ArrayList;

/**
 * Clase Interface, para un juego.
 * Dado:
 *      x_min, y_min, x_max, y_max de las coordenadas de vision de pantalla
 *      numero de botones para la parte inferior (max 8)
 *      boolean para intercanviar coordenadas y (opengl tiene arriba la coordenada inferior)
 *
 * entonces la interfaz crea los botones retractiles en la parte inferior, (8 max)
 * un icono de herrmanientas arriba a la derecha, y un icono de detalles abajo a la izq.
 */
public class Interface {

    private final float radioButton;
    private int interfaceStepShown;
    public static final int maxTimeInterfaceShown = 250;
    private ArrayList<InterfaceButton> buttons;
    private InterfaceButton moreInfoButton;
    private boolean isStarting;
    private boolean isRetracting;
    private boolean isActive;
    private InterfaceButton lastSelectedButton;
    private int selectedButton;

    public Interface(int numButtons, float x_min, float y_min, float x_max, float y_max, boolean yInvertida) {
        interfaceStepShown = 0;
        isStarting = false;
        isRetracting = false;
        isActive = false;
        buttons = new ArrayList<>(numButtons);
        radioButton = (x_max-x_min) / (float) (2 * (8 + 2));
        float offset = (x_max-x_min) /(float)(8+2);
        if (yInvertida) {
            moreInfoButton = new InterfaceButton(radioButton, y_min - radioButton, radioButton, y_min + radioButton, radioButton, false);
            for (int i = 2; i < numButtons + 2; i++) {
                //(float initialX, float initialY, float endX, float endY, float radix, boolean isCircular)
                buttons.add(new InterfaceButton(offset * i + radioButton, y_min - radioButton, offset * i + radioButton, y_min + radioButton, radioButton, true));
            }
        }
        else {
            moreInfoButton = new InterfaceButton(radioButton, y_max + radioButton, radioButton, y_max - radioButton, radioButton, false);
            for (int i = 2; i < numButtons + 2; i++) {
                //(float initialX, float initialY, float endX, float endY, float radix, boolean isCircular)
                buttons.add(new InterfaceButton(offset * i + radioButton, y_max + radioButton, offset * i + radioButton, y_max - radioButton, radioButton, true));
            }
        }

    }


    private void trace(String str) {
        Trace.write(" Interface::" + str);
    }

    public void logic(long miliseconds) {
        moreInfoButton.logic(miliseconds);
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).logic(miliseconds);
        }
        if (isStarting) {
            isRetracting = false;
            interfaceStepShown += miliseconds;
            if (interfaceStepShown >= maxTimeInterfaceShown) { //ha llegado al tope
                interfaceStepShown = maxTimeInterfaceShown;
                isActive = true;
                isStarting = false;
            }
            else { //No esta en el tope
                isActive = false;
            }
        }
        else if(isRetracting) {
            isStarting = false;
            interfaceStepShown -= miliseconds;
            if (interfaceStepShown <= 0) { //Esta escondido por completo
                interfaceStepShown = 0;
                isActive = false;
                isRetracting = false;
            }
            else { //No esta en el tope
                isActive = false;
            }

        }
        //En otro caso no esta en transicion, no hace nada.
    }

    public void startShowing() {
        trace("**Se llama a startShowing estado: "+estado());
        moreInfoButton.startShowing();
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).startShowing();
        }
        if(!isActive && !isStarting && !isRetracting) { //caso natural de empezar a iniciar el boton es mientras no este activo y no esta ya iniciandose
            isActive = false;
            isStarting = true;
            isRetracting = false;
            interfaceStepShown = 1;
        }
        else if (isRetracting || isStarting) { //En caso de que se llame mientras esta haciendo starting o esta haciendo retracting solo actualiza estado
            isActive = false;
            isStarting = true;
            isRetracting = false;
        }
        trace("**Se acaba la llamada a startShowing estado: "+estado());
        //En otro caso (esta activo y no esta en transicion) no ha de hacer nada
    }

    private String estado() {
        return "isActive: "+isActive+"-isStarting: "+isStarting+"-isRetracting: "+isRetracting+"-interfaceStepShown: "+interfaceStepShown;
    }

    public void startRetracting() {
        trace("##Se llama a startRetracting estado: "+estado());
        moreInfoButton.startRetracting();
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).startRetracting();
        }
        if (isActive) { //caso natural de empezar a retraer es cuando esta activo el boton.
            isActive = false;
            isStarting = false;
            isRetracting = true;
            interfaceStepShown = maxTimeInterfaceShown - 1;
        }
        else if (isRetracting || isStarting) { //En caso de que se llame a retraer y ya lo esta haciendo o esta starting, solo cambiamos el estado.
            isActive = false;
            isStarting = false;
            isRetracting = true;
        }
        trace("##Se acaba la llamada a startRetracting estado: "+estado());
        //En otro caso (no esta activo y no esta en transicion) no debe hacer nada porque ya esta retraido por completo.
    }

    public ArrayList<InterfaceButton> getButtons() {
        return buttons;
    }

    public InterfaceButton getMoreInfoButton() {
        return moreInfoButton;
    }

    public int stepShowing() {
        return interfaceStepShown;
    }

    public boolean isActive() {
        return isActive;
    }

    public void highLight(float x, float y) {
        if (lastSelectedButton != null) {
            lastSelectedButton.unSelect();
            selectedButton = -1;
        }
        if (moreInfoButton.isInside(x,y)) {
            lastSelectedButton = moreInfoButton;
            moreInfoButton.select();
            selectedButton = 0;
            return;
        }
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).isInside(x,y)) {
                lastSelectedButton = buttons.get(i);
                buttons.get(i).select();
                selectedButton = i;
                return;
            }
        }
    }

    public int getSelectedButton() {
        return selectedButton;
    }

    public void desSeleccionar() {
        if (lastSelectedButton != null) {
            lastSelectedButton.unSelect();
            lastSelectedButton = null;
        }
        selectedButton = -1;
    }

    public float getRadioButton() {
        return radioButton;
    }
}
