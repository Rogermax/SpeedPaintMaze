package com.gmail.rogermoreta.speedpaintmaze.model;

import com.gmail.rogermoreta.speedpaintmaze.enums.TipoDisparo;

/**
 * Clase que representa una bala con un m_type distintivo (m_type), un radio (m_radius) y una entidad como objetivo (m_entityTarget).
 * Consta de una posicion y velocidad.
 * Y esta basado en un sistema de 3 estados.
 * Existe (se debe de pintar algo)
 * Explotando (esta detonando)
 * Estandard (avanzando hacia el objetivo)
 */
public class Bullet {
    //Caracteristicas de la bala
    private TipoDisparo m_type;
    private float m_radius;
    private Entity m_entityTarget;

    //Estado fisico
    private Vector2D m_position;
    private Vector2D m_velocity;
    private float m_vel; //calculable, pero mejor tenenrlo a mano

    //Estado logico de la bala
    private boolean m_exists;

    public static final long m_maxTimeLivig = 3000l;
    private long m_attackState;

    public static final long m_maxTimeExploting = 200l;
    private long m_explosionState;

    public Bullet(Vector2D posicion, float vel, Entity entity, float radius, TipoDisparo type) {
        //Caracteristicas de la bala
        m_type = type;
        m_radius = radius;
        m_entityTarget = entity;

        //Estado fisico
        m_position = new Vector2D(posicion);
        m_velocity = Vector2D.resta(entity.getPosition(), posicion);
        m_velocity.normaliza();
        m_velocity.multipicaPorEscalar(vel);
        m_vel = vel;

        //Estado logico de la bala
        m_exists = true;
        m_explosionState = 0l;
        m_attackState = 0l;

    }

    public TipoDisparo getType() {
        return m_type;
    }

    public void logic(long milisegundos) {
        if (m_exists) {
            if (m_explosionState > 0) {
                m_explosionState += milisegundos;
                if (m_explosionState > m_maxTimeExploting) m_exists = false;
            } else {
                m_attackState += milisegundos;
                if (m_attackState > m_maxTimeLivig || m_entityTarget == null) explota();
                else {
                    m_velocity = Vector2D.resta(new Vector2D(m_entityTarget.getX(), m_entityTarget.getY()),m_position);
                    m_velocity.normaliza();
                    m_velocity.multipicaPorEscalar(m_vel);
                    m_position.suma(Vector2D.multipicaPorEscalar(m_velocity,milisegundos));
                }
            }
        }
    }

    public void explota() {
        m_explosionState = 1;
    }

    public boolean estaExplotando() {
        return m_explosionState > 0;
    }

    public boolean existe() {
        return m_exists;
    }

    public float getPosX() {
        return m_position.m_x;
    }

    public float getPosY() {
        return m_position.m_y;
    }

    public float getRadius() {
        return m_radius;
    }

    public long getLifeTime() {
        return m_attackState;
    }

    public Entity getObjectiveEntity() {
        return m_entityTarget;
    }
}
