package com.gmail.rogermoreta.speedpaintmaze.model;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    protected float life;
    protected float damage;
    protected double vel;
    protected float velX;
    protected float velY;
    protected float posX;
    protected float posY;
    protected float targetAttackX;
    protected float targetAttackY;
    protected float targetMoveX;
    protected float targetMoveY;
    protected float attackDistance;
    protected boolean wannaAttack;
    protected boolean damagable;
    protected boolean isAlive;
    protected long hittedState;
    protected long dyingState;
    protected long attackState;
    protected long movementState;

    protected long timeRechargeAttack;
    protected long timeDying;
    protected long timeHitted;
    protected float totalLife;

    public Entity() {
        posX = 0f;
        posY = 0f;
        velX = 0f;
        velY = 0f;
        vel = 1d;
        life = 100f;
        totalLife = 100f;
        damage = 1f;
        damagable = true;
        isAlive = true;
        dyingState = 0;
        attackState = 0;
        movementState = 0;
        targetAttackX = 0f;
        targetAttackY = 0f;
        targetMoveX = 0f;
        targetMoveY = 0f;
        timeRechargeAttack = 1000l;
        timeDying = 1000l;
        timeHitted = 100l;
        attackDistance = 25f;
        wannaAttack = false;
    }

    public void asignarMoveTarget(float x, float y) {
        targetMoveX = x;
        targetMoveY = y;
    }

    public void asignarAttackTarget(float x, float y) {
        targetAttackX = x;
        targetAttackY = y;
    }

    public void quitarMoveTarget() {
        targetMoveX = posX;
        targetMoveY = posY;
    }

    public abstract void logic(long milisenconds);


    protected boolean miObjetivoEstaAtiro() {
        return (attackDistance * attackDistance > (targetAttackX - posX) * (targetAttackX - posX) + (targetAttackY - posY) * (targetAttackY - posY));
    }

    public void setVelocity(float velocity) {
        vel = velocity / 20d;
    }

    public void receiveDamage(float damage) {
        life -= damage;
        hittedState = 1;
        if (dyingState == 0 && life <= 0) {
            dyingState = 1;
        }
    }

    public float getX() {
        return posX;
    }

    public float getY() {
        return posY;
    }

    public long getDyingState() {
        return dyingState;
    }
    public long getHittedState() {
        return hittedState;
    }

    public long getMovementCycleTime(){
        return movementState;
    }

    public long getTimeDying() {
        return timeDying;
    }

    public float getLife() {
        return life;
    }

    public float getTotalLife() {
        return totalLife;
    }
}
