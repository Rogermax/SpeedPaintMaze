package com.gmail.rogermoreta.speedpaintmaze.model;

public class Turret {

    private int x;
    private int y;
    private long shootingState;
    private long timeToRecharge;
    private boolean readyToFire;
    private float radius;
    private boolean objectiveSetted;
    private float objX;
    private float objY;
    private Enemy enemyToAttack;
    public static final float maxDistanceAttack = 200;
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

    public Enemy dispara() {
        objectiveSetted = false;
        readyToFire = false;
        shootingState = 0;
        return enemyToAttack;
    }

    public void provocar(int x, int y) {
        objectiveSetted = true;
        objX = x;
        objY = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean readyToFire() {
        return readyToFire && objectiveSetted;
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

    public boolean estaProvocada() {
        return objectiveSetted;
    }

    public float getObjX() {
        return objX;
    }

    public float getObjY() {
        return objY;
    }

    public void provocar(Enemy enemy) {
        objectiveSetted = true;
        objX = x;
        objY = y;
        enemyToAttack = enemy;
    }
}
