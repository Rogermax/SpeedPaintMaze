package com.gmail.rogermoreta.speedpaintmaze.model;

public class Turret {

    private int x;
    private int y;
    private long rechargeState;
    private long timeToRecharge;
    private boolean readyToFire;
    private float radius;
    private boolean objectiveSetted;
    private Enemy enemyToAttack;
    public static final float maxDistanceAttack = 200;
    private int attackState;
    public static final int maxAttackState = 200;
    public int tipo = 0;
    //private ArrayList<PisoTurret> pisos;

    public Turret(int x, int y, float radius, int tipo) {
        this.x = x;
        this.y = y;
        timeToRecharge = 500l;
        rechargeState = 0l;
        readyToFire = false;
        this.radius = radius;
        attackState = 0;
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }

    public void logic(long milisegundos) {
        if (attackState > 0 || (!readyToFire && rechargeState == 0)) {
            attackState += milisegundos;
            if (attackState > maxAttackState) {
                attackState = 0;
            }
        }
        if (!readyToFire) {
            rechargeState += milisegundos;
            if (rechargeState > timeToRecharge) {
                readyToFire = true;
            }
        }
    }

    public Enemy dispara() {
        objectiveSetted = false;
        readyToFire = false;
        rechargeState = 0;
        return enemyToAttack;
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

    @SuppressWarnings("unused")
    public float getRadius() {
        return radius;
    }

    public boolean estaProvocada() {
        return objectiveSetted;
    }

    public void provocar(Enemy enemy) {
        objectiveSetted = true;
        enemyToAttack = enemy;
    }

    public float getAttackPercentage() {
        return attackState /(float) maxAttackState;
    }

}
