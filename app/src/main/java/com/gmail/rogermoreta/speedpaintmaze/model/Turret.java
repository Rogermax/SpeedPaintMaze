package com.gmail.rogermoreta.speedpaintmaze.model;

public class Turret {

    private int x;
    private int y;
    private long shootingState;
    private long timeToRecharge;
    private boolean readyToFire;
    private float radius;
    //private ArrayList<PisoTurret> pisos;

    public Turret(int x, int y, float radius) {
        this.x = x;
        this.y = y;
        timeToRecharge = 3000l;
        shootingState = 0l;
        readyToFire = false;
        this.radius = radius;
    }

    public void logic(long milisegundos) {
        shootingState += milisegundos;
        if (shootingState > timeToRecharge) {
            readyToFire = true;
        }
    }

    public void dispara() {
        readyToFire = false;
        shootingState = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean readyToFire() {
        return readyToFire;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getRadius() {
        return radius;
    }
}
