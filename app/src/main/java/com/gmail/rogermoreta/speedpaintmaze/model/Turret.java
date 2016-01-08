package com.gmail.rogermoreta.speedpaintmaze.model;

import com.gmail.rogermoreta.speedpaintmaze.enums.TipoDisparo;

/**
 * Clase que representa una torreta con un m_type distintivo (m_type) y una entidad como objetivo.
 * Consta de una posicion (m_position).
 * Y esta basado en un sistema de 3 estados.
 * Atacando (m_attackState)
 * Recargando (después del ataque)
 * Esperando (Después de recargar si tiene objetivo a tiro)
 */
public class Turret {

    //Caracteristicas de la torreta
    public static final float maxDistanceAttack = 200f;
    private TipoDisparo m_type;
    private long m_price;
    private Entity m_entityTarget;

    //Estado fisico
    private Vector2D m_position;


    //Estado logico de la torreta
    public static final long m_maxAttackState = 200l;
    private long m_attackState;

    public static final long m_maxRechargeState = 500l;
    private long m_rechargeState;

    private boolean m_readyToFire;
    private boolean objectiveSetted;

    public Turret(Vector2D position, TipoDisparo tipo, long price) {
        m_type = tipo;
        m_price = price;
        m_entityTarget = null;


        m_position = position;

        m_attackState = 0l;
        m_rechargeState = 0l;

        m_readyToFire = true;
        objectiveSetted = false;
    }

    public TipoDisparo getTipo() {
        return m_type;
    }

    public void logic(long milisegundos) {
        if (m_attackState > 0) { //esta atacando
            m_attackState += milisegundos;
            if (m_attackState > m_maxAttackState) { //Si nos pasamos del tiempo ade ataque
                m_rechargeState = m_attackState-m_maxAttackState; //Si nos pasamos del tiempo de ataque, lo que sobra ya es tiempo de recarga.
                m_attackState = 0;
            }
        }
        else { //no esta atacando
            if (m_rechargeState > 0) { //no ataca, pero esta recargando
                m_rechargeState += milisegundos;
                if (m_rechargeState > m_maxRechargeState) {
                    m_rechargeState = 0;
                    m_readyToFire = true;
                }
            }
            else { //no ataca, ni recarga
                m_readyToFire = true;
            }
        }
    }

    public Entity dispara() {
        objectiveSetted = false;
        m_readyToFire = false;
        m_attackState = 1;
        return m_entityTarget;
    }

    public float getX() {
        return m_position.m_x;
    }

    public float getY() {
        return m_position.m_y;
    }

    public boolean readyToFire() {
        return m_readyToFire && objectiveSetted;
    }

    public boolean estaProvocada() {
        return objectiveSetted;
    }

    public void provocar(Entity entity) {
        objectiveSetted = true;
        m_entityTarget = entity;
    }

    public float getAttackPercentage() {
        return m_attackState /(float) m_maxAttackState;
    }

    public float getRechargePercentage() {
        return m_rechargeState /(float) m_maxRechargeState;
    }

    public long getPrice() {
        return m_price;
    }

    public Vector2D getPosition() {
        return m_position;
    }
}
