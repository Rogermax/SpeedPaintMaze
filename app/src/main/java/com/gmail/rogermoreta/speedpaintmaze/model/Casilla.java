package com.gmail.rogermoreta.speedpaintmaze.model;

import com.gmail.rogermoreta.speedpaintmaze.enums.TipoCasilla;

public class Casilla {
    public static final int size = 100;
    private Vector2D m_position;
    private Vector2D m_nextPosition;
    private boolean esDeDireccionamiento;
    private boolean esDeInicio;
    private boolean esDeFin;
    private boolean tieneTorreta;
    private boolean estaSeleccionada;
    private TipoCasilla tipoCasilla;
    private int m_giro;
    //private Random r = new Random(SystemClock.uptimeMillis());

    public Casilla(TipoCasilla tipoCasilla, int posX, int posY) {
        m_position = new Vector2D(posX, posY);
        this.tipoCasilla = tipoCasilla;
        esDeDireccionamiento = false;
        esDeInicio = false;
        esDeFin = false;
        tieneTorreta = false;
        estaSeleccionada = false;
        m_giro = 0;
    }

    public Casilla(TipoCasilla tipoCasilla, int posX, int posY, boolean esDeInicio, boolean esDeFin) {
        m_position = new Vector2D(posX, posY);
        this.tipoCasilla = tipoCasilla;
        this.esDeInicio = esDeInicio;
        this.esDeFin = esDeFin;
        esDeDireccionamiento = false;
        tieneTorreta = false;
        estaSeleccionada = false;
        m_giro = 0;
    }

    public boolean esDeDireccionamiento() {
        return esDeDireccionamiento;
    }

    public boolean esDeInicio() {
        return esDeInicio;
    }

    public boolean tieneTorreta() {
        return tieneTorreta;
    }

    public void seleccionar() {
        estaSeleccionada = true;
    }

    public void deseleccionar() {
        estaSeleccionada = false;
    }

    /*public void setTurret(Turret turret) {
        tieneTorreta = true;
        this.turret = turret;
    }*/

    /*public void removeTurret() {
        turret = null;
        tieneTorreta = false;
    }*/

    public boolean esDeFin() {
        return esDeFin;
    }

    public int getPosX() {
        return Math.round(m_position.m_x);
    }

    public int getPosY() {
        return Math.round(m_position.m_y);
    }

    public void setNextPositions(int x, int y) {
        esDeDireccionamiento = true;
        m_nextPosition = new Vector2D(x,y);
    }

    public TipoCasilla getTipoCasilla() {
        return tipoCasilla;
    }

    /**
     * No es random, se ha cambiado por que creaba solapaciones
     * @return real position
     */
    public float getRandomNextX() {
        return m_nextPosition.m_x*100f/*+r.nextInt(100)*/;
    }

    /**
     * No es random, se ha cambiado por que creaba solapaciones
     * @return real position
     */
    public float getRandomNextY() {
        return m_nextPosition.m_y*100f/*+r.nextInt(100)*/;
    }

    public void ponTorreta() {
        tieneTorreta = true;
    }

    public boolean isSelected() {
        return estaSeleccionada;
    }

    public Vector2D getPosition() {
        return m_position;
    }

    public void setGiro(int giro) {
        m_giro = giro;
    }

    public int getGiro() {
        return m_giro;
    }
}
