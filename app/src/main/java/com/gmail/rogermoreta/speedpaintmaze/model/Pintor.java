package com.gmail.rogermoreta.speedpaintmaze.model;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.gmail.rogermoreta.speedpaintmaze.R;

public class Pintor {


    public static Bitmap turret0base;
    public static Bitmap turret0ceil;
    public static Bitmap monsterHead0;
    public static Bitmap monsterHead1;
    public static Bitmap monsterBody0;
    public static Bitmap monsterBody1;
    private int width;
    private int height;
    private int sizeOfTurret;
    private int sizeOfMonster;

    public Pintor(int width, int height, Activity activity/*, float porcentajeTurret, float porcentajeMonster*/) {
        this.width = width;
        this.height = height;
        int maxLongitud = Math.max(width,height);
        //sizeOfTurret = (int) (maxLongitud*porcentajeTurret/100);
        //sizeOfMonster = (int) (maxLongitud*porcentajeMonster/100);
        turret0base = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.turret0base)), 100, 100, true);
        turret0ceil = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.turret0ceil)), 100, 100, true);
        monsterHead0 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.monsterhead0)), 32, 32, true);
        monsterHead1 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.monsterhead1)), 32, 32, true);
        monsterBody0 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.monsterbody0)), 32, 32, true);
        monsterBody1 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.monsterbody1)), 32, 32, true);
    }

    public Canvas drawBaseTurret(Canvas canvas, Turret t) {
        Paint pincell = new Paint();
        pincell.setARGB(255, 255, 125, 0);
        canvas.drawBitmap(turret0base, t.getX()-t.getRadius(), t.getY()-t.getRadius()/2, pincell);
        return canvas;
    }

    public Canvas drawCeilTurret(Canvas canvas, Turret t) {
        Paint pincell = new Paint();
        pincell.setARGB(255, 255, 125, 0);
        canvas.drawBitmap(turret0ceil, t.getX()-t.getRadius(), t.getY()-t.getRadius()/2, pincell);
        return canvas;
    }


    public Canvas drawEnemy(Canvas canvas, Enemy enemy) {
        Paint pincell = new Paint();
        pincell.setARGB(255, 0, 255, 125);
        if (enemy.getDyingState() == 0) {
            switch ((int) (enemy.getMovementCycleTime() * 2 / Enemy.cycleTimeMovement) % 2) {
                case 0:
                    canvas.drawBitmap(monsterBody0, enemy.getX(), enemy.getY(), pincell);
                    canvas.drawBitmap(monsterHead0, enemy.getX(), enemy.getY(), pincell);
                    break;
                case 1:
                    canvas.drawBitmap(monsterBody1, enemy.getX(), enemy.getY(), pincell);
                    canvas.drawBitmap(monsterHead1, enemy.getX(), enemy.getY(), pincell);
                    break;
            }
        } else {
            if (enemy.isAlive) {
                if (enemy.getDyingState()%2 == 0) {
                    canvas.drawBitmap(monsterBody0, enemy.getX(), enemy.getY(), pincell);
                    canvas.drawBitmap(monsterHead0, enemy.getX(), enemy.getY(), pincell);
                }
                else {
                    canvas.drawBitmap(monsterBody1, enemy.getX(), enemy.getY(), pincell);
                    canvas.drawBitmap(monsterHead1, enemy.getX(), enemy.getY(), pincell);
                }
            }
            else {
                //No pinto nada
            }
        }
        return canvas;
    }

    public Canvas drawBullet(Canvas canvas, Bullet bullet) {
        Paint pincell = new Paint();
        pincell.setARGB(255, 255, (int) (bullet.getLifeTime()*255/bullet.getMaxLifeTime()), 0);
        canvas.drawCircle(bullet.getPosX(), bullet.getPosY(), bullet.getRadius(), pincell);
        return canvas;
    }


    public static Bitmap drawableToBitmap (Drawable drawable) {
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
