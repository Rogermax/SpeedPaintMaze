package com.gmail.rogermoreta.speedpaintmaze.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.SurfaceHolder;

import com.gmail.rogermoreta.speedpaintmaze.R;
import com.gmail.rogermoreta.speedpaintmaze.view.BurbujitaActivity;

import java.util.ArrayList;
import java.util.Random;

public class BurbujitaMap {

    private SurfaceHolder surfaceHolder;
    private ArrayList<Turret> turrets;
    private ArrayList<Bullet> bullets;
    private Paint pincell;
    private int height;
    private int width;
    private Turret nextTurret;
    private int lastX;
    private int lastY;
    public static Bitmap turret0base;
    public static Bitmap turret0ceil;

    public BurbujitaMap(SurfaceHolder surfaceHolder, BurbujitaActivity burbujitaActivity) {
        lastX = -1;
        lastY = -1;
        turrets = new ArrayList<>();
        bullets = new ArrayList<>();
        this.surfaceHolder = surfaceHolder;
        Canvas canvas = this.surfaceHolder.lockCanvas();
        width = canvas.getWidth();
        height = canvas.getHeight();
        pincell = new Paint();
        pincell.setARGB(175, 0, 0, 0);
        pincell.setTextAlign(Paint.Align.CENTER);
        pincell.setTextSize(Math.min(width, height) / 15);
        turret0base = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(burbujitaActivity, R.drawable.turret0base)), (int) (0.05*Math.max(width, height)), (int) (0.05*Math.max(width, height)), true);
        turret0ceil = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(burbujitaActivity, R.drawable.turret0ceil)), (int) (Math.max(width, height)*0.05), (int) (Math.max(width, height)*0.05), true);
        this.surfaceHolder.unlockCanvasAndPost(canvas);
    }

    public void draw(float fps, float ups) {
        if (this.surfaceHolder != null) {
            Canvas canvas = this.surfaceHolder.lockCanvas();
            canvas.drawARGB(255, 181, 230, 29);
            canvas = drawTurretsBases(canvas);
            canvas = drawBullets(canvas);
            canvas = drawTurretsCeils(canvas);
            if (nextTurret != null) {
                try {
                    nextTurret.drawBase(canvas);
                    nextTurret.drawCeil(canvas);
                }
                catch (Exception e) {

                }
            }
            canvas.drawText("FPS: "+fps, width / 2, 6 * height / 8, pincell);
            canvas.drawText("UPS: "+ups, width / 2, 7 * height / 8, pincell);
            this.surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private Canvas drawTurretsBases(Canvas canvas) {
        for (int i = 0; i < turrets.size(); i++) {
            canvas = turrets.get(i).drawBase(canvas);
        }
        return canvas;
    }

    private Canvas drawBullets(Canvas canvas) {
        for (int i = 0; i < bullets.size(); i++) {
            canvas = bullets.get(i).draw(canvas);
        }
        return canvas;
    }

    private Canvas drawTurretsCeils(Canvas canvas) {
        for (int i = 0; i < turrets.size(); i++) {
            canvas = turrets.get(i).drawCeil(canvas);
        }
        return canvas;
    }

    public void logic() {
        for (int i = 0; i < turrets.size(); i++) {
            Turret aux = turrets.get(i);
            aux.logic();
            int objX;
            int objY;
            if (lastX == -1 || lastY == -1) {
                objX = new Random().nextInt(width);
                objY = new Random().nextInt(height);
            }
            else {
                objX = lastX;
                objY = lastY;
            }
            float distX = aux.getX()-objX;
            float distY = aux.getY()-objY;
            double dist = Math.sqrt(distX*distX+distY*distY);
            if (aux.readyToFire()) {
                bullets.add(new Bullet(aux.getX(), aux.getY(), distX*40/(float)dist, distY*40/(float)dist, 0.015f*Math.max(width, height) ));
            }
        }
        for (int i = 0; i < bullets.size(); i++) {
            if (lastX == -1 || lastY == -1) {
                bullets.get(i).logic();
            }
            else {
                bullets.get(i).logic(lastX, lastY);
            }
            if (bullets.get(i).mustDie()) bullets.remove(i);
        }
    }

    public void createNewNextTurret(int x, int y) {
        nextTurret = new Turret(x,y,0.02f*Math.max(width, height));
        lastX = x;
        lastY = y;
    }

    public void setNextTurret(int x, int y) {
        nextTurret.setX(x);
        nextTurret.setY(y);
        lastX = x;
        lastY = y;
    }

    public void buildTurret(int x, int y) {
        turrets.add(new Turret(x, y, nextTurret.getRadius()));
        lastX = x;
        lastY = y;
    }

    private static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
