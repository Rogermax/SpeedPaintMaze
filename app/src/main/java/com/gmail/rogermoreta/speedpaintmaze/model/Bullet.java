package com.gmail.rogermoreta.speedpaintmaze.model;

public class Bullet {

    private Enemy enemy;
    private float posX;
    private float posY;
    private float velX;
    private float velY;
    private long tiempoDeVida;
    private long tiempoDeExplosion;
    private boolean existe;
    private float radius;
    private int faseExplosion;
    private int faseDeDisparo;
    private float vel;

    public Bullet(float posX, float posY, float vel, Enemy enemy, float radius) {
        this.posX = posX;
        this.posY = posY;
        this.vel = vel;
        this.enemy = enemy;
        double distanciaAobjetivo = Math.sqrt((enemy.getX()-posX)*(enemy.getX()-posX)+(enemy.getY()-posY)*(enemy.getY()-posY));
        velX = (float) ((enemy.getX()-posX)*vel/distanciaAobjetivo);
        velY = (float) ((enemy.getY()-posY)*vel/distanciaAobjetivo);
        this.radius = radius;
        faseExplosion = 0;
        faseDeDisparo = 0;
        tiempoDeVida = 3000l;
        existe = true;
        tiempoDeExplosion = 200l;

    }

    public void logic(long milisegundos) {
        if (existe) {
            if (faseExplosion > 0) {
                faseExplosion += milisegundos;
                if (faseExplosion > tiempoDeExplosion) existe = false;
            } else {
                faseDeDisparo += milisegundos;
                if (faseDeDisparo > tiempoDeVida) explota();
                else {
                    double distanciaAobjetivo = Math.sqrt((enemy.getX()-posX)*(enemy.getX()-posX)+(enemy.getY()-posY)*(enemy.getY()-posY));
                    velX = (float) ((enemy.getX()-posX)*vel/distanciaAobjetivo);
                    velY = (float) ((enemy.getY()-posY)*vel/distanciaAobjetivo);
                    posX += (velX * milisegundos);
                    posY += (velY * milisegundos);
                }
            }
        }
    }

    public void explota() {
        faseExplosion = 1;
    }

    public boolean estaExplotando() {
        return faseExplosion > 0;
    }

    public boolean existe() {
        return existe;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public float getRadius() {
        return radius;
    }

    public long getLifeTime() {
        return faseDeDisparo;
    }
    public long getMaxLifeTime() {
        return tiempoDeVida;
    }
}
