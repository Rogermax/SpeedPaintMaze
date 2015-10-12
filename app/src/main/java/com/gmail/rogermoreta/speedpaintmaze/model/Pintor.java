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
    public static Bitmap finalmonster0;
    public static Bitmap finalmonster1;
    public static Bitmap finalmonster2;
    public static Bitmap finalmonster3;
    public static Bitmap finalmonster4;
    public static Bitmap finalmonsterdie0;
    public static Bitmap finalmonsterdie1;
    public static Bitmap finalmonsterdie2;
    public static Bitmap finalmonsterdie3;
    public static Bitmap finalmonsterdie4;
    public static Bitmap finalmonsterdie5;
    public static Bitmap platano0;
    public static Bitmap platano1;
    public static Bitmap platano2;
    public static Bitmap platano3;
    public static Bitmap platano4;
    public static Bitmap platanoHurt0;
    public static Bitmap platanodie0;
    public static Bitmap platanodie1;
    public static Bitmap platanodie2;
    public static Bitmap platanodie3;
    public static Bitmap platanodie4;
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
        finalmonster0 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.monstruofinal1)), 100, 100, true);
        finalmonster1 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.monstruofinal2)), 100, 100, true);
        finalmonster2 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.monstruofinal3)), 100, 100, true);
        finalmonster3 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.monstruofinal4)), 100, 100, true);
        finalmonster4 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.monstruofinal5)), 100, 100, true);
        finalmonsterdie0 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.monstruofinal6)), 100, 100, true);
        finalmonsterdie1 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.monstruofinal7)), 100, 100, true);
        finalmonsterdie2 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.monstruofinal8)), 100, 100, true);
        finalmonsterdie3 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.monstruofinal9)), 100, 100, true);
        finalmonsterdie4 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.monstruofinal10)), 100, 100, true);
        finalmonsterdie5 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.monstruofinal11)), 100, 100, true);
        platano0 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.platano)), 100, 100, true);
        platano1 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.platano2)), 100, 100, true);
        platano2 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.platano3)), 100, 100, true);
        platano3 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.platano4)), 100, 100, true);
        platano4 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.platano5)), 100, 100, true);
        platanoHurt0 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.platano9)), 100, 100, true);
        platanodie0 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.platano10)), 100, 100, true);
        platanodie1 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.platano11)), 100, 100, true);
        platanodie2 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.platano12)), 100, 100, true);
        platanodie3 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.platano13)), 100, 100, true);
        platanodie4 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(activity, R.drawable.platano14)), 100, 100, true);
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
            switch ((int) (enemy.getMovementCycleTime() * 5 / Enemy.cycleTimeMovement) % 5) {
                case 0:
                    canvas.drawBitmap(finalmonster0, enemy.getX(), enemy.getY(), pincell);
                    break;
                case 1:
                    canvas.drawBitmap(finalmonster1, enemy.getX(), enemy.getY(), pincell);
                    break;
                case 2:
                    canvas.drawBitmap(finalmonster2, enemy.getX(), enemy.getY(), pincell);
                    break;
                case 3:
                    canvas.drawBitmap(finalmonster3, enemy.getX(), enemy.getY(), pincell);
                    break;
                case 4:
                    canvas.drawBitmap(finalmonster4, enemy.getX(), enemy.getY(), pincell);
                    break;
            }
        } else {
            if (enemy.isAlive) {
                switch ((int) (enemy.getDyingState() * 5 / enemy.getTimeDying()) % 5) {
                    case 0:
                        canvas.drawBitmap(finalmonsterdie0, enemy.getX(), enemy.getY(), pincell);
                        break;
                    case 1:
                        canvas.drawBitmap(finalmonsterdie1, enemy.getX(), enemy.getY(), pincell);
                        break;
                    case 2:
                        canvas.drawBitmap(finalmonsterdie2, enemy.getX(), enemy.getY(), pincell);
                        break;
                    case 3:
                        canvas.drawBitmap(finalmonsterdie3, enemy.getX(), enemy.getY(), pincell);
                        break;
                    case 4:
                        canvas.drawBitmap(finalmonsterdie4, enemy.getX(), enemy.getY(), pincell);
                        break;
                }
            }
            else {
                //No pinto nada
            }
        }
        canvas = drawLife(canvas, enemy.getX(), enemy.getY(), enemy.getLife(), enemy.getTotalLife());
        return canvas;
    }

    public Canvas drawBaseMonster(Canvas canvas, BaseMonster enemy) {
        Paint pincell = new Paint();
        pincell.setARGB(255, 0, 255, 125);
        if (enemy.getDyingState() == 0) {
            if (enemy.getHittedState() > 0) {
                canvas.drawBitmap(platanoHurt0, enemy.getX(), enemy.getY(), pincell);
            }
            else {
                switch ((int) (enemy.getMovementCycleTime() * 9 / Enemy.cycleTimeMovement) % 9) {
                    case 0:
                    case 8:
                        canvas.drawBitmap(platano0, enemy.getX(), enemy.getY(), pincell);
                        break;
                    case 1:
                    case 7:
                        canvas.drawBitmap(platano1, enemy.getX(), enemy.getY(), pincell);
                        break;
                    case 2:
                    case 6:
                        canvas.drawBitmap(platano2, enemy.getX(), enemy.getY(), pincell);
                        break;
                    case 3:
                    case 5:
                        canvas.drawBitmap(platano3, enemy.getX(), enemy.getY(), pincell);
                        break;
                    case 4:
                        canvas.drawBitmap(platano4, enemy.getX(), enemy.getY(), pincell);
                        break;
                }
            }
        } else {
            if (enemy.isAlive) {
                switch ((int) (enemy.getDyingState() * 5 / enemy.getTimeDying()) % 5) {
                    case 0:
                        canvas.drawBitmap(platanodie0, enemy.getX(), enemy.getY(), pincell);
                        break;
                    case 1:
                        canvas.drawBitmap(platanodie1, enemy.getX(), enemy.getY(), pincell);
                        break;
                    case 2:
                        canvas.drawBitmap(platanodie2, enemy.getX(), enemy.getY(), pincell);
                        break;
                    case 3:
                        canvas.drawBitmap(platanodie3, enemy.getX(), enemy.getY(), pincell);
                        break;
                    case 4:
                        canvas.drawBitmap(platanodie4, enemy.getX(), enemy.getY(), pincell);
                        break;
                }
            }
            else {
                canvas.drawBitmap(platanodie4, enemy.getX(), enemy.getY(), pincell);
            }
        }
        canvas = drawLife(canvas, enemy.getX(), enemy.getY(), enemy.getLife(), enemy.getTotalLife());
        return canvas;
    }


    private Canvas drawLife(Canvas canvas, float x, float y, float life, float totalLife) {
        Paint pincell = new Paint();
        pincell.setARGB(255, 0, 0, 0);
        canvas.drawRect(x - 2, y - 6, x + 102, y, pincell);
        pincell.setARGB(255, 0, 255, 0);
        canvas.drawRect(x, y - 4, x + 100 * life / totalLife, y - 2, pincell);
        pincell.setARGB(255, 255, 0, 0);
        canvas.drawRect(x + 100 * life / totalLife, y - 4, x + 100,y-2,pincell);
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
