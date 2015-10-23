package com.gmail.rogermoreta.speedpaintmaze.model;

public class InterfaceButton {
    private int interfaceStepShown;
    private int maxTimeInterfaceShown;
    private boolean isActive;
    private float initialX;
    private float endX;
    private float initialY;
    private float endY;
    private float prop;
    private float actualX;
    private float actualY;
    private boolean isCircular;
    private boolean isStarting;
    private boolean isRetracting;
    private boolean isSelected;
    private float radix;

    public InterfaceButton(float initialX, float initialY, float endX, float endY, float radix, boolean isCircular) {
        maxTimeInterfaceShown = Interface.maxTimeInterfaceShown;
        isActive = false;
        isStarting = false;
        isRetracting = false;
        isSelected = false;
        interfaceStepShown = 0;
        prop = 0f;
        this.initialX = initialX;
        this.initialY = initialY;
        this.endX = endX;
        this.endY = endY;
        this.radix = radix;
        this.isCircular = isCircular;
        this.actualX = initialX;
        this.actualY = initialY;
    }

    public void logic(long miliseconds) {
        if (isStarting) {
            isRetracting = false;
            interfaceStepShown += miliseconds;
            if (interfaceStepShown >= maxTimeInterfaceShown) { //ha llegado al tope
                interfaceStepShown = maxTimeInterfaceShown;
                prop = 1f;
                isActive = true;
                isStarting = false;
                actualX = endX;
                actualY = endY;
            }
            else { //No esta en el tope
                prop = interfaceStepShown/(float)maxTimeInterfaceShown;
                isActive = false;
                actualX = (endX-initialX)*prop+initialX;
                actualY = (endY-initialY)*prop+initialY;
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
                actualX = initialX;
                actualY = initialY;
            }
            else { //No esta en el tope
                prop = interfaceStepShown/(float)maxTimeInterfaceShown;
                isActive = false;
                actualX = (endX-initialX)*prop+initialX;
                actualY = (endY-initialY)*prop+initialY;
            }

        }
        //En otro caso no esta en transicion, no hace nada.
    }

    public void startShowing() {
        if(!isActive && !isStarting) { //caso natural de empezar a iniciar el boton es mientras no este activo y no esta ya iniciandose
            isActive = false;
            isStarting = true;
            isRetracting = false;
            interfaceStepShown = 1;
            prop = 0f;
            this.actualX = this.endX;
            this.actualY = this.endY;
        }
        else if (isRetracting || isStarting) { //En caso de que se llame mientras esta haciendo starting o esta haciendo retracting solo actualiza estado
            isActive = false;
            isStarting = true;
            isRetracting = false;
        }
        //En otro caso (esta activo y no esta en transicion) no ha de hacer nada
    }

    public void startRetracting() {
        if (isActive) { //caso natural de empezar a retraer es cuando esta activo el boton.
            isActive = false;
            isStarting = false;
            isRetracting = true;
            interfaceStepShown = maxTimeInterfaceShown - 1;
            prop = 1f;
            this.actualX = this.initialX;
            this.actualY = this.initialY;
        }
        else if (isRetracting || isStarting) { //En caso de que se llame a retraer y ya lo esta haciendo o esta starting, solo cambiamos el estado.
            isActive = false;
            isStarting = false;
            isRetracting = true;
        }
        //En otro caso (no esta activo y no esta en transicion) no debe hacer nada porque ya esta retraido por completo.
    }

    public boolean isInside(float x, float y) {
        if (isCircular) {
            return (actualX-x)*(actualX-x)+(actualY-y)*(actualY-y) <= radix*radix;
        }
        else {
            return (actualX)<x&&x<(actualX+2*radix)&&(actualY)<y&&y<(actualY+2*radix);
        }
    }

    public float getActualX() {
        return actualX;
    }

    public float getActualY() {
        return actualY;
    }

    public float getRadix() {
        return radix;
    }

    public void select() {
        isSelected = true;
    }

    public void unSelect() {
        isSelected = false;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isCircular() {
        return isCircular;
    }
}
