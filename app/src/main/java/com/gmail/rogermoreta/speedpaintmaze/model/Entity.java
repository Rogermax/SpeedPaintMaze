package com.gmail.rogermoreta.speedpaintmaze.model;

import java.io.Serializable;

/**
 * Clase Entity
 * Representa un ente móvil y capaz de atacar a un objetivo mientras se mueve hacia otro
 * Este ente tiene X puntos de vida, hace X puntos de daño, tiene una posicion y una velocidad
 * To do elemento del juego con estas características debería heredar de esta.
 */
public abstract class Entity implements Serializable {
    //Vida
    protected float m_actualLife;
    protected float m_totalLife;
    protected boolean m_damagable;
    protected boolean m_isBeeingHitted;
    protected boolean m_isAlive;

    //ataque
    protected boolean m_isCapableForAttacking;
    protected float m_damage;
    protected float m_attackDistance;
    protected Vector2D m_objetivoDeAtaque;
    protected boolean m_isRecharging;

    //posicion y velocidad
    protected Vector2D m_posicion;
    protected Vector2D m_velocidad;
    protected Vector2D m_objetivoDeMovimiento;
    protected float m_actualVel;


    //estados y caracteristicas de la entidad
    protected long m_hittedState;   //milisegundos desde que empezó a ser pegado
    protected long m_attackState;   //milisegundos desde que empezó el ataque
    protected long m_dyingState;    //milisegundos desde que empezó a morir
    protected long m_movementState; //milisegundos desde el ultimo fin de ciclo de mov.

    protected long m_timeHitted;            //maximo tiempo (ms) para estar siendo pegado
    protected long m_timeRechargingAttack;  //maximo tiempo (ms) para estar recargando (tiempo de recarga)
    protected long m_timeDying;             //maximo tiempo (ms) para estar muriendo (tiempo de muerte)
    protected long m_cycleTimeMovement;     //maximo tiempo (ms) que dura el ciclo de movimiento (deberia depender de la velocidad y de los frames del bicho)

    //Para estadisticas, no funcional.
    protected int timesItHasShootted = 0;

    public Entity() {
        //Vida
        m_actualLife = 100f;
        m_totalLife = 100f;
        m_damagable = true;
        m_isBeeingHitted = false;
        m_isAlive = true;

        //ataque
        m_isCapableForAttacking = false;
        m_damage = 0f;
        m_attackDistance = 25f;
        m_objetivoDeAtaque = new Vector2D();
        m_isRecharging = false;

        //posicion y velocidad
        m_posicion = new Vector2D();
        m_velocidad = new Vector2D();
        m_objetivoDeMovimiento = new Vector2D();
        m_actualVel = 0f;

        //estados y caracteristicas de la entidad
        m_hittedState = 0l;
        m_attackState = 0l;
        m_dyingState = 0l;
        m_movementState = 0l;


        m_timeHitted = 100l;
        m_timeRechargingAttack = 1000l;
        m_timeDying = 1000l;
        m_cycleTimeMovement = 1000l; //por defecto completara el ciclo de sprites en 1 segundo
    }

    public void init(float posX, float posY, float life, float vel) {
        m_posicion.m_x = posX;
        m_posicion.m_y = posY;
        m_actualVel = vel/20f;
        m_actualLife = life;
    }

    public void logic(long milisenconds) {
        if (m_dyingState == 0) {
            if (m_hittedState > 0) {
                logicHitted(milisenconds);
            }
            logicMove(milisenconds);
            if (m_isCapableForAttacking) {
                logicAttack(milisenconds);
            }
        }
        else {
            logicDie(milisenconds);
        }

    }

    private void logicHitted(long milisenconds) {
        m_hittedState +=milisenconds;
        if (m_hittedState > m_timeHitted) m_hittedState = 0;
    }

    private void logicDie(long milisenconds) {
        m_dyingState +=milisenconds;
        if (m_dyingState > m_timeDying) {
            m_isAlive = false;
        }
    }

    private void logicMove(long milisenconds) {
        if (!m_posicion.igualA(m_objetivoDeMovimiento)) {
            float distanceToTarget = Vector2D.resta(m_posicion,m_objetivoDeMovimiento).modulo();
            recalculateVelocityVector();
            if (distanceToTarget < m_actualVel * milisenconds) {
                m_posicion.igualarA(m_objetivoDeMovimiento);
            } else {
                m_posicion.suma(Vector2D.multipicaPorEscalar(m_velocidad,(float) milisenconds));
                m_movementState = (m_movementState +milisenconds)% m_cycleTimeMovement;
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private void logicAttack(long miliseconds) {
        if (m_attackState > 0) {
            m_attackState +=miliseconds;
            if(m_attackState > m_timeRechargingAttack) {
                m_attackState = 0;
                if (miObjetivoEstaAtiro()) {
                    timesItHasShootted++;
                    m_attackState = 1;
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
                timesItHasShootted++;
                m_attackState +=miliseconds;
            }
            else {
                //nothingtodo
            }
        }
    }

    public void asignarMoveTarget(float x, float y) {
        m_objetivoDeMovimiento = new Vector2D(x,y);
        startMoving();
    }

    public void asignarAttackTarget(float x, float y) {
        m_objetivoDeAtaque = new Vector2D(x,y);
    }

    public void quitarMoveTarget() {
        m_objetivoDeMovimiento.igualarA(m_posicion);
    }

    protected boolean miObjetivoEstaAtiro() {
        return m_attackDistance > Vector2D.resta(m_posicion, m_objetivoDeAtaque).modulo();
    }

    public void receiveDamage(float damage) {
        if (m_damagable) {
            m_actualLife -= damage;
            m_hittedState = 1;
            if (m_dyingState == 0 && m_actualLife <= 0) {
                m_dyingState = 1;
            }
        }
    }

    private void recalculateVelocityVector() {
        Vector2D vectorDirector = Vector2D.resta(m_objetivoDeMovimiento,m_posicion);
        m_velocidad = Vector2D.multipicaPorEscalar(Vector2D.normalizado(vectorDirector), m_actualVel);
    }

    private void startMoving() {
        Vector2D vectorDirector = Vector2D.resta(m_objetivoDeMovimiento,m_posicion);
        m_velocidad = Vector2D.multipicaPorEscalar(Vector2D.normalizado(vectorDirector), m_actualVel);
    }

    public void setPosX(int posX) {
        m_posicion.m_x = posX;
    }

    public void setPosY(int posY) {
        m_posicion.m_y = posY;
    }

    public float getX() {
        return m_posicion.m_x;
    }

    public float getY() {
        return m_posicion.m_y;
    }

    public long getDyingState() {
        return m_dyingState;
    }

    public long getHittedState() {
        return m_hittedState;
    }

    public long getMovementCycleTime(){
        return m_movementState;
    }

    public long getTimeDying() {
        return m_timeDying;
    }

    public float getLife() {
        return m_actualLife;
    }

    public float getTotalLife() {
        return m_totalLife;
    }

    public float getModuloVelocidad() {
        return m_actualVel;
    }

    public boolean isAlive() {
        return m_isAlive;
    }

    public float getVelX() {
        return m_velocidad.m_x;
    }

    public long getCycleTimeMovement() {
        return m_cycleTimeMovement;
    }

    public Vector2D getPosition() {
        return m_posicion;
    }

}
