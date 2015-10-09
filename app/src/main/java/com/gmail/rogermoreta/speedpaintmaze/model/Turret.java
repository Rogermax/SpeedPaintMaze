package com.gmail.rogermoreta.speedpaintmaze.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class Turret {

    private int x;
    private int y;
    private long lastTimeShooted;
    private long timeToRecharge;
    private float radius;
    //private ArrayList<PisoTurret> pisos;

    public Turret(int x, int y, float radius) {
        this.x = x;
        this.y = y;
        timeToRecharge = 5000l;
        lastTimeShooted = System.currentTimeMillis()-4000l;
        this.radius = radius;
    }

    public Canvas drawBase(Canvas canvas) {
        Paint pincell = new Paint();
        pincell.setARGB(255, 255, 125, 0);
        canvas.drawBitmap(BurbujitaMap.turret0base, x-this.radius, y-this.radius, pincell);
        //canvas.drawRect(x - this.radius, y - this.radius, x + this.radius, y + this.radius, pincell);
        return canvas;
    }

    public Canvas drawCeil(Canvas canvas) {
        Paint pincell = new Paint();
        pincell.setARGB(255, 255, 125, 0);
        canvas.drawBitmap(BurbujitaMap.turret0ceil, x - this.radius, y - this.radius, pincell);
        //canvas.drawRect(x - this.radius, y - this.radius, x + this.radius, y + this.radius, pincell);
        return canvas;
    }


    public void logic() {

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean readyToFire() {
        if (System.currentTimeMillis() - lastTimeShooted > timeToRecharge) {
            lastTimeShooted = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }
}
