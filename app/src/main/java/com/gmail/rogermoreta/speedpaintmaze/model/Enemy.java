package com.gmail.rogermoreta.speedpaintmaze.model;

public class Enemy extends Entity {

    int timesShootted = 0;
    public static final int cycleTimeMovement = 750;

    public Enemy() {
        super();
        wannaAttack = true;
    }

    @Override
    public void logic(long milisenconds) {
        if (dyingState == 0) {
            if (hittedState > 0) {
                logicHitted(milisenconds);
            }
            else {
                logicMove(milisenconds);
                if (wannaAttack) {
                    logicAttack(milisenconds);
                }
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

    private void recalculateVelocityVector(double mod) {
        velX = (float) ((targetMoveX-posX)*vel/mod);
        velY = (float) ((targetMoveY-posY)*vel/mod);
    }

}
