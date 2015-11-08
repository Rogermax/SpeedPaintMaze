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

    public static final int cycleTimeMovement = 750;
    protected long timeRechargeAttack;
    protected long timeDying;
    protected long timeHitted;
    protected float totalLife;

    //statics
    protected int timesShootted = 0;

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

    public void logic(long milisenconds) {
        if (dyingState == 0) {
            if (hittedState > 0) {
                logicHitted(milisenconds);
            }
            logicMove(milisenconds);
            if (wannaAttack) {
                logicAttack(milisenconds);
            }
        }
        else {
            logicDie(milisenconds);
        }

    }

    private void logicHitted(long milisenconds) {
        hittedState+=milisenconds;
        if (hittedState > timeHitted) hittedState = 0;
    }

    private void logicDie(long milisenconds) {
        dyingState+=milisenconds;
        if (dyingState > timeDying) {
            isAlive = false;
        }
    }

    private void logicMove(long milisenconds) {
        if (posX != targetMoveX || posY != targetMoveY) {
            double distanceToTarget = Math.sqrt((targetMoveX - posX) * (targetMoveX - posX) + (targetMoveY - posY) * (targetMoveY - posY));
            recalculateVelocityVector(distanceToTarget);
            if (distanceToTarget < vel * milisenconds) {
                posX = targetMoveX;
                posY = targetMoveY;
            } else {
                posX += velX * milisenconds;
                posY += velY * milisenconds;
                movementState = (movementState+milisenconds)%cycleTimeMovement;
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private void logicAttack(long miliseconds) {
        if (attackState > 0) {
            attackState+=miliseconds;
            if(attackState > timeRechargeAttack) {
                attackState = 0;
                if (miObjetivoEstaAtiro()) {
                    timesShootted++;
                    attackState = 1;
                }
                else {
                    //nothingtodo
                }
            }
            else {
                //nothingtodo
            }
        }
        else {
            if (miObjetivoEstaAtiro()) {
                timesShootted++;
                attackState+=miliseconds;
            }
            else {
                //nothingtodo
            }
        }
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

    private void recalculateVelocityVector(double mod) {
        velX = (float) ((targetMoveX-posX)*vel/mod);
        velY = (float) ((targetMoveY-posY)*vel/mod);
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
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
    @SuppressWarnings("unused")
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

    public float getModuloVelocidadCuadrado() {return velX*velX+velY*velY;}

    public boolean isAlive() {
        return isAlive;
    }

    public float getVelX() {
        return velX;
    }
}
