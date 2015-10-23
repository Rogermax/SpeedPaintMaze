package com.gmail.rogermoreta.speedpaintmaze.model;

import java.util.ArrayList;

public class Interface {

    private float prop;
    private int interfaceStepShown;
    public static final int maxTimeInterfaceShown = 200;
    private ArrayList<InterfaceButton> buttons;
    private InterfaceButton moreInfoButton;
    private boolean isStarting;
    private boolean isRetracting;
    private boolean isActive;
    private InterfaceButton lastSelectedButton;
    private int selectedButton;

    public Interface(int width, int height, int numButtons) {
        interfaceStepShown = 0;
        isStarting = false;
        isRetracting = false;
        isActive = false;
        buttons = new ArrayList<>(numButtons);
        float radioButton = width/(float)(2*(numButtons+2));
        float offset = width/(float)(numButtons+2);
        moreInfoButton = new InterfaceButton(0,height+offset,0,height-offset, radioButton, false);
        for (int i = 2; i < numButtons+2; i++) {
            //(float initialX, float initialY, float endX, float endY, float radix, boolean isCircular)
            buttons.add(new InterfaceButton(offset * i+radioButton, height + radioButton, offset * i+radioButton, height - radioButton, radioButton, true));
        }
        prop = 0f;
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
                prop = 1f;
                isActive = true;
                isStarting = false;
            }
            else { //No esta en el tope
                prop = interfaceStepShown/(float)maxTimeInterfaceShown;
                isActive = false;
            }
        }
        else if(isRetracting) {
            isStarting = false;
            interfaceStepShown -= miliseconds;
            if (interfaceStepShown <= 0) { //Esta escondido por completo
                interfaceStepShown = 0;
                prop = 0f;
                isActive = false;
                isRetracting = false;
            }
            else { //No esta en el tope
                prop = interfaceStepShown/(float)maxTimeInterfaceShown;
                isActive = false;
            }

        }
        //En otro caso no esta en transicion, no hace nada.
    }

    public int isSomethingClicked(float x, float y) {
        if (moreInfoButton.isInside(x,y)) return 0;
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).isInside(x,y)) return i+2;
        }
        return -1;
    }

    public void startShowing() {
        moreInfoButton.startShowing();
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).startShowing();
        }
        if(!isActive && !isStarting) { //caso natural de empezar a iniciar el boton es mientras no este activo y no esta ya iniciandose
            isActive = false;
            isStarting = true;
            isRetracting = false;
            interfaceStepShown = 1;
            prop = 0f;
        }
        else if (isRetracting || isStarting) { //En caso de que se llame mientras esta haciendo starting o esta haciendo retracting solo actualiza estado
            isActive = false;
            isStarting = true;
            isRetracting = false;
        }
        //En otro caso (esta activo y no esta en transicion) no ha de hacer nada
    }

    public void startRetracting() {
        moreInfoButton.startRetracting();
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).startRetracting();
        }
        if (isActive) { //caso natural de empezar a retraer es cuando esta activo el boton.
            isActive = false;
            isStarting = false;
            isRetracting = true;
            interfaceStepShown = maxTimeInterfaceShown - 1;
            prop = 1f;
        }
        else if (isRetracting || isStarting) { //En caso de que se llame a retraer y ya lo esta haciendo o esta starting, solo cambiamos el estado.
            isActive = false;
            isStarting = false;
            isRetracting = true;
        }
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
                selectedButton = i+2;
                return;
            }
        }
    }

    public boolean isClickInside(float x, float y) {
        return isSomethingClicked(x,y) != -1;
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
}
