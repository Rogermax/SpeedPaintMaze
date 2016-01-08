package com.gmail.rogermoreta.speedpaintmaze.model;

public class Enemy extends Entity {

    //Resistencias
    float m_fireResistance;
    float m_plantResistance;
    float m_coldResistance;

    //Duracion Estado Actual
    protected long m_onFire;   //milisegundos desde que empezó a ser quemado
    protected long m_onPlant;  //milisegundos desde que empezó a ser plantado
    protected long m_onCold;   //milisegundos desde que empezó a ser helado

    //Max tiempo duracion estados
    protected long m_maxTimeFiring;         //maximo tiempo (ms) para estar siendo quemado
    protected long m_maxTimePlanting;       //maximo tiempo (ms) para estar siendo plantado
    protected long m_maxTimeColding;        //maximo tiempo (ms) para estar siendo helado

    //Guardar estados restaurados
    private float m_origianlVel;

    //fire damage per tick
    private float m_damagePerTick;
    private long m_tickTime;

    public Enemy() {
        super();
        m_damagable = true;
        m_fireResistance = 0.0f;
        m_plantResistance = 0.0f;
        m_coldResistance = 0.0f;
        m_onFire = 0l;
        m_onPlant = 0l;
        m_onCold = 0l;
        m_maxTimeFiring = 2000l;
        m_maxTimePlanting = 500l;
        m_maxTimeColding = 1000l;
        m_damagePerTick = 1f;
        m_tickTime = 100l;
        m_origianlVel = m_actualVel;
    }

    public void logic(long milisenconds) {
        quemar(milisenconds);
        plantar(milisenconds);
        relantizar(milisenconds);
        super.logic(milisenconds);
    }

    private void quemar(long milisenconds) {
        if (m_onFire > 0l) {
            m_onFire += milisenconds;
            float numeroDeTicks = m_onFire/100 - (m_onFire-milisenconds)/100; //Ticks Actuales - Ticks ya pegados antes.
            receiveDamage(m_damagePerTick*numeroDeTicks);
            if (m_onFire > m_maxTimeFiring) {
                dejarDeQuemar();
            }
        }
    }

    private void plantar(long milisenconds) {
        if (m_onPlant > 0l) {
            m_onPlant += milisenconds;
            if (m_onPlant > m_maxTimePlanting) {
                dejarDePlantar();
            }
        }
    }

    private void relantizar(long milisenconds) {
        if (m_onCold > 0l) {
            m_onCold += milisenconds;
            if (m_onCold > m_maxTimeColding) {
                dejarDeRelantizar();
            }
        }
    }

    public void dejarDeQuemar() {
        m_onFire = 0l;
    }

    public void dejarDePlantar() {
        m_onPlant = 0l;
        m_actualVel = m_origianlVel;
    }

    public void dejarDeRelantizar() {
        m_onCold = 0l;
        m_actualVel = m_origianlVel;
    }

    public void empezarAQuemar(long tiempoQuemando, float damagePerTick) {
        m_onFire = 1l;
        m_maxTimeFiring = tiempoQuemando;
        m_damagePerTick = damagePerTick;
    }

    public void empezarAPlantar(long tiempoPlantado) {
        if (m_onPlant == 0l && m_onCold == 0l) {
            m_origianlVel = m_actualVel;
            m_actualVel = 0f;
        }
        m_onPlant = 1l;
        m_maxTimePlanting = tiempoPlantado;
    }

    public void empezarARelantizar(float factorReductor) {
        if (m_onCold == 0l && m_onPlant == 0l) {
            m_origianlVel = m_actualVel;
            m_actualVel = m_actualVel*factorReductor;
        }
        m_onCold = 1l;
    }

}
