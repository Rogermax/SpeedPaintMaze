package com.gmail.rogermoreta.speedpaintmaze.model;

import com.gmail.rogermoreta.speedpaintmaze.enums.TipoCasilla;

public class Casilla {
    public static final int size = 100;
    private int posX;
    private int posY;
    private int posNextX;
    private int posNextY;
    private boolean esDeDireccionamiento;
    private boolean esDeInicio;
    private boolean esDeFin;
    private boolean tieneTorreta;
    private Turret turret;
    private TipoCasilla tipoCasilla;
    private Casilla nextCasilla;

    public Casilla(TipoCasilla tipoCasilla, int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.tipoCasilla = tipoCasilla;
        esDeDireccionamiento = false;
        esDeInicio = false;
        esDeFin = false;
        tieneTorreta = false;
    }

    public Casilla(TipoCasilla tipoCasilla, int posX, int posY, boolean esDeInicio, boolean esDeFin) {
        this.posX = posX;
        this.posY = posY;
        this.tipoCasilla = tipoCasilla;
        this.esDeInicio = esDeInicio;
        this.esDeFin = esDeFin;
        esDeDireccionamiento = false;
        tieneTorreta = false;
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

    public void setTurret(Turret turret) {
        tieneTorreta = true;
        this.turret = turret;
    }

    public void removeTurret() {
        turret = null;
        tieneTorreta = false;
    }

    public boolean esDeFin() {
        return esDeFin;
    }

    public void setNextCasilla(Casilla casilla) {
        esDeDireccionamiento = true;
        nextCasilla = casilla;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setNextPositions(int x, int y) {
        posNextX = x;
        posNextY = y;
    }
}
