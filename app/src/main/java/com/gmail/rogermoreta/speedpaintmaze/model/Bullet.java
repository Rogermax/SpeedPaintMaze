package com.gmail.rogermoreta.speedpaintmaze.model;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Bullet {

    private float posX;
    private float posY;
    private float velX;
    private float velY;
    private long lifeTime = 2000l;
    private long startTime;
    private float radius;

    public Bullet(float posX, float posY, float velX, float velY, float radius) {
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        this.velY = velY;
        this.radius = radius;
        startTime = System.currentTimeMillis();
    }

    public void logic() {
        posX += velX;
        posY += velY;
        this.radius -= this.radius*0.1;
    }

    public Canvas draw(Canvas canvas) {
        Paint pincell = new Paint();
        pincell.setARGB(255, 255, (int) ((System.currentTimeMillis() - startTime)*255/lifeTime), 0);
        canvas.drawCircle(posX, posY, this.radius, pincell);
        return canvas;
    }

    public boolean mustDie() {
        return (System.currentTimeMillis() - startTime > lifeTime);
    }

    public void logic(int lastX, int lastY) {
        double vel = Math.sqrt(velX*velX+velY*velY);
        double mod = Math.sqrt((lastX-posX)*(lastX-posX)+(lastY-posY)*(lastY-posY));
        velX = (float) ((lastX-posX)*vel/mod);
        velY = (float) ((lastY-posY)*vel/mod);
        logic();
    }
}
