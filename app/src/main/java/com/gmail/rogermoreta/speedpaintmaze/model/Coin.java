package com.gmail.rogermoreta.speedpaintmaze.model;

public class Coin {
    public static final long m_maxTimeShowing = 1000l;
    private long m_value;
    private long m_timeShowing;
    private float m_posX;
    private float m_posY;

    public void logic(long ms) {
        m_timeShowing += ms;
    }

    public float getX() {
        return m_posX;
    }

    public float getY() {
        return m_posY;
    }

    public long getValue() {
        return m_value;
    }

    public boolean mustDisappear() {
        return m_timeShowing >= m_maxTimeShowing;
    }

    public Coin(long value, float posx, float posy) {
        m_timeShowing = 0l;
        m_value = value;
        m_posX = posx;
        m_posY = posy;
    }

    public long getTime() {
        return m_timeShowing;
    }
}
