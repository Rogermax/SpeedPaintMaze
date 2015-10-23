package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.gmail.rogermoreta.speedpaintmaze.R;
import com.gmail.rogermoreta.speedpaintmaze.enums.TipoCasilla;
import com.gmail.rogermoreta.speedpaintmaze.model.BaseMonster;
import com.gmail.rogermoreta.speedpaintmaze.model.Bullet;
import com.gmail.rogermoreta.speedpaintmaze.model.Casilla;
import com.gmail.rogermoreta.speedpaintmaze.model.Enemy;
import com.gmail.rogermoreta.speedpaintmaze.model.Interface;
import com.gmail.rogermoreta.speedpaintmaze.model.InterfaceButton;
import com.gmail.rogermoreta.speedpaintmaze.model.Turret;

import java.util.ArrayList;

public class Pintor {


    public static Bitmap turret0base;
    public static Bitmap turret0ceil;
    public static Bitmap torretaseta;
    public static Bitmap torretasetaattack;
    public static Bitmap torretaseta2;
    public static Bitmap torretasetaattack2;
    public static Bitmap torretaseta3;
    public static Bitmap torretasetaattack3;
    public static Bitmap iconoseta1;
    public static Bitmap iconoseta2;
    public static Bitmap iconoseta3;
    public static Bitmap hierba;
    public static Bitmap tierra;
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
    private Context context;

    /**
     *
     * @param context para saber de donde coger los drawable
     */
    public Pintor(Context context) {
        this.context = context;
        turret0base = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.turret0base)), 100, 100, true);
        turret0ceil = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.turret0ceil)), 100, 100, true);
        torretaseta = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.torretaseta)), 100, 100, true);
        torretasetaattack = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.torretasetaattack)), 100, 100, true);
        torretaseta2 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.torretaseta2)), 100, 100, true);
        torretasetaattack2 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.torretasetaattack2)), 100, 100, true);
        torretaseta3 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.torretaseta3)), 100, 100, true);
        torretasetaattack3 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.torretasetaattack3)), 100, 100, true);
        hierba = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.hierba1)), 100, 100, true);
        tierra = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.tierra)), 100, 100, true);
        monsterHead0 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.monsterhead0)), 32, 32, true);
        monsterHead1 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.monsterhead1)), 32, 32, true);
        monsterBody0 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.monsterbody0)), 32, 32, true);
        monsterBody1 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.monsterbody1)), 32, 32, true);
        finalmonster0 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.monstruofinal1)), 100, 100, true);
        finalmonster1 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.monstruofinal2)), 100, 100, true);
        finalmonster2 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.monstruofinal3)), 100, 100, true);
        finalmonster3 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.monstruofinal4)), 100, 100, true);
        finalmonster4 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.monstruofinal5)), 100, 100, true);
        finalmonsterdie0 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.monstruofinal6)), 100, 100, true);
        finalmonsterdie1 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.monstruofinal7)), 100, 100, true);
        finalmonsterdie2 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.monstruofinal8)), 100, 100, true);
        finalmonsterdie3 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.monstruofinal9)), 100, 100, true);
        finalmonsterdie4 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.monstruofinal10)), 100, 100, true);
        finalmonsterdie5 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.monstruofinal11)), 100, 100, true);
        platano0 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.platano)), 100, 100, true);
        platano1 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.platano2)), 100, 100, true);
        platano2 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.platano3)), 100, 100, true);
        platano3 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.platano4)), 100, 100, true);
        platano4 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.platano5)), 100, 100, true);
        platanoHurt0 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.platano9)), 100, 100, true);
        platanodie0 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.platano10)), 100, 100, true);
        platanodie1 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.platano11)), 100, 100, true);
        platanodie2 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.platano12)), 100, 100, true);
        platanodie3 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.platano13)), 100, 100, true);
        platanodie4 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.platano14)), 100, 100, true);
    }

    public static Canvas drawCasilla(Canvas canvas, Casilla casilla) {
        int left = casilla.getPosX()*100;
        int top = casilla.getPosY()*100;
        Paint pincell = new Paint();
        if (casilla.getTipoCasilla() == TipoCasilla.CAMINO) {
            canvas.drawBitmap(tierra, left, top, pincell);
            //pincell.setARGB(255, 204, 119, 34);
        }
        else {
            canvas.drawBitmap(hierba, left, top, pincell);
            pincell.setARGB(100,100,100,100);
            if (casilla.isSelected()) {
                canvas.drawRect(left,top,left+100, top+100, pincell);
            }
            //pincell.setARGB(255, 68, 148, 74);
        }
        //canvas.drawRect(left, top, left + Casilla.size, top + Casilla.size, pincell);
        return canvas;
    }

    public Canvas drawTurret(Canvas canvas, Turret t) {
        if (torretaseta != null) {
            Paint pincell = new Paint();
            pincell.setARGB(255, 255, 125, 0);
            if (t.getAttackPercentage() > 0.5f) {
                switch (t.tipo) {
                    case 3:
                        canvas.drawBitmap(torretasetaattack2, t.getX(), t.getY(), pincell);
                        break;
                    case 4:
                        canvas.drawBitmap(torretasetaattack3, t.getX(), t.getY(), pincell);
                        break;
                    default:
                        canvas.drawBitmap(torretasetaattack, t.getX(), t.getY(), pincell);
                }
            }
            else {
                switch (t.tipo) {
                    case 3:
                        canvas.drawBitmap(torretaseta2, t.getX(), t.getY(), pincell);
                        break;
                    case 4:
                        canvas.drawBitmap(torretaseta3, t.getX(), t.getY(), pincell);
                        break;
                    default:
                        canvas.drawBitmap(torretaseta, t.getX(), t.getY(), pincell);
                }
            }
        }
        else {
            Log.d("Pintor", "torretaseta es null, no pinto");
        }
        return canvas;
    }

    /*public Canvas drawCeilTurret(Canvas canvas, Turret t) {
        if (turret0ceil != null) {
            Paint pincell = new Paint();
            pincell.setARGB(255, 255, 125, 0);
            canvas.drawBitmap(turret0ceil, t.getX(), t.getY(), pincell);
        }
        else {
            Log.d("Pintor", "turret0ceil es null, no pinto");
        }
        return canvas;
    }*/


    public Canvas drawEnemy(Canvas canvas, Enemy enemy) {
        if (finalmonster0 != null && finalmonster1 != null && finalmonster2 != null && finalmonster3 != null && finalmonster4 != null) {
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
                if (enemy.isAlive()) {
                    if (finalmonsterdie0 != null && finalmonsterdie1 != null && finalmonsterdie2 != null && finalmonsterdie3 != null && finalmonsterdie4 != null) {
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
                        Log.d("Pintor", "falta algun monsterfinaldie, no pinto su muerte");
                    }
                }
            }
        }
        else {
            Log.d("Pintor", "algun frame de finalmonster es null, pinto solo la vida");
        }
        canvas = drawLife(canvas, enemy.getX(), enemy.getY(), enemy.getLife(), enemy.getTotalLife());
        return canvas;
    }

    public Canvas drawBaseMonster(Canvas canvas, BaseMonster enemy) {
        if (platano0 != null && platano1 != null && platano2 != null && platano3 != null && platano4 != null) {
            Paint pincell = new Paint();
            pincell.setARGB(255, 0, 255, 125);
            if (enemy.getDyingState() == 0) {
                if (enemy.getHittedState() > 0) {
                    if (platanoHurt0 != null) {
                        canvas.drawBitmap(platanoHurt0, enemy.getX(), enemy.getY(), pincell);
                    } else {
                        Log.d("Pintor", "platanoHurt0 es null, no pinto cuando le pegan");
                    }
                } else {
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
                if (enemy.isAlive()) {
                    if (platanodie0 != null && platanodie1 != null && platanodie2 != null && platanodie3 != null && platanodie4 != null) {
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
                    } else {
                        Log.d("Pintor", "falta algun platanodie, no pinto su muerte");
                    }
                } else {
                    if (platanodie4 != null) {
                        canvas.drawBitmap(platanodie4, enemy.getX(), enemy.getY(), pincell);
                    } else {
                        Log.d("Pintor", "platanodie4 es null, no pinto cuando queda muerto en el suelo");
                    }
                }
            }
        }
        else {
            Log.d("Pintor", "algun platano es null, solo pinto la vida");
        }
        canvas = drawLife(canvas, enemy.getX(), enemy.getY(), enemy.getLife(), enemy.getTotalLife());
        return canvas;
    }


    private static Canvas drawLife(Canvas canvas, float x, float y, float life, float totalLife) {
        Paint pincell = new Paint();
        pincell.setARGB(255, 0, 0, 0);
        canvas.drawRect(x - 2, y - 6, x + 102, y, pincell);
        pincell.setARGB(255, 0, 255, 0);
        canvas.drawRect(x, y - 4, x + 100 * life / totalLife, y - 2, pincell);
        pincell.setARGB(255, 255, 0, 0);
        canvas.drawRect(x + 100 * life / totalLife, y - 4, x + 100, y - 2, pincell);
        return canvas;
    }

    public static Canvas drawBullet(Canvas canvas, Bullet bullet) {
        Paint pincell = new Paint();
        pincell.setARGB(255, 255, (int) (bullet.getLifeTime() * 255 / bullet.getMaxLifeTime()), 0);
        canvas.drawCircle(bullet.getPosX(), bullet.getPosY(), bullet.getRadius(), pincell);
        return canvas;
    }

    public Canvas drawInterface(Canvas canvas, Interface interfaceInstance) {
        Paint pincell = new Paint();
        pincell.setARGB(255, 255, 0, 0);
        if (interfaceInstance.stepShowing() > 0) {
            ArrayList<InterfaceButton> aux = interfaceInstance.getButtons();
            int size = aux.size();
            for (int i = 0; i < size; i++) {
                pincell.setARGB(255, 0, 0, 0);
                InterfaceButton aux2 = aux.get(i);
                if (iconoseta1 == null || iconoseta2 == null || iconoseta3 == null) {
                    iconoseta1 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.iconoseta1)), (int)(2*aux2.getRadix()), (int)(2*aux2.getRadix()), true);
                    iconoseta2 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.iconoseta2)), (int)(2*aux2.getRadix()), (int)(2*aux2.getRadix()), true);
                    iconoseta3 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.iconoseta3)), (int)(2*aux2.getRadix()), (int)(2*aux2.getRadix()), true);
                }
                if (i == 0) {
                    if (aux2.isSelected()) {
                        canvas.drawBitmap(iconoseta1, aux2.getActualX()-aux2.getRadix(),aux2.getActualY()-aux2.getRadix(), pincell);
                    }
                    else {
                        canvas.drawBitmap(iconoseta1, aux2.getActualX()-aux2.getRadix(),aux2.getActualY()-aux2.getRadix(), pincell);
                        pincell.setARGB(100, 100, 100, 100);
                        canvas.drawCircle(aux2.getActualX(), aux2.getActualY(), aux2.getRadix(), pincell);
                    }
                }
                else if (i == 1) {
                    if (aux2.isSelected()) {
                        canvas.drawBitmap(iconoseta2, aux2.getActualX()-aux2.getRadix(),aux2.getActualY()-aux2.getRadix(), pincell);
                    }
                    else {
                        canvas.drawBitmap(iconoseta2, aux2.getActualX()-aux2.getRadix(),aux2.getActualY()-aux2.getRadix(), pincell);
                        pincell.setARGB(100, 100, 100, 100);
                        canvas.drawCircle(aux2.getActualX(), aux2.getActualY(), aux2.getRadix(), pincell);
                    }
                }
                else if (i == 2) {
                    if (aux2.isSelected()) {
                        canvas.drawBitmap(iconoseta3, aux2.getActualX()-aux2.getRadix(),aux2.getActualY()-aux2.getRadix(), pincell);
                    }
                    else {
                        canvas.drawBitmap(iconoseta3, aux2.getActualX()-aux2.getRadix(),aux2.getActualY()-aux2.getRadix(), pincell);
                        pincell.setARGB(100, 100, 100, 100);
                        canvas.drawCircle(aux2.getActualX(), aux2.getActualY(), aux2.getRadix(), pincell);
                    }
                }
                else {
                    if (aux2.isCircular()) {
                        canvas.drawCircle(aux2.getActualX(), aux2.getActualY(), aux2.getRadix(), pincell);
                    }
                    else {
                        canvas.drawRect(aux2.getActualX(), aux2.getActualY(), aux2.getActualX()+2*aux2.getRadix(), aux2.getActualY()+2*aux2.getRadix(), pincell);
                    }
                }
            }
            InterfaceButton moreInfo = interfaceInstance.getMoreInfoButton();
            if (moreInfo.isSelected()) {
                pincell.setARGB(255, 0, 255, 0);
            }
            else {
                pincell.setARGB(255, 255, 0, 0);
            }
            if (moreInfo.isCircular()) {
                canvas.drawCircle(moreInfo.getActualX(), moreInfo.getActualY(), moreInfo.getRadix(), pincell);
            }
            else {
                canvas.drawRect(moreInfo.getActualX(), moreInfo.getActualY(), moreInfo.getActualX() + 2 * moreInfo.getRadix(), moreInfo.getActualY() + 2 * moreInfo.getRadix(), pincell);
            }
        }
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
