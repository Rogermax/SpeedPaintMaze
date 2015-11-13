package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.opengl.GLES30;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.SurfaceHolder;

import com.gmail.rogermoreta.speedpaintmaze.R;
import com.gmail.rogermoreta.speedpaintmaze.enums.TipoCasilla;
import com.gmail.rogermoreta.speedpaintmaze.javaandroid.Trace;
import com.gmail.rogermoreta.speedpaintmaze.model.Bullet;
import com.gmail.rogermoreta.speedpaintmaze.model.BurbujitaMap;
import com.gmail.rogermoreta.speedpaintmaze.model.Casilla;
import com.gmail.rogermoreta.speedpaintmaze.model.Enemy;
import com.gmail.rogermoreta.speedpaintmaze.model.Interface;
import com.gmail.rogermoreta.speedpaintmaze.model.InterfaceButton;
import com.gmail.rogermoreta.speedpaintmaze.model.Turret;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

public class Pintor {

    private static final int COORDS_PER_VERTEX = 3;
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
    private SurfaceHolder burbujitaHolder;

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

    public Canvas drawPreTurret(Canvas canvas, Turret t) {
        if (torretaseta != null) {
            Paint pincell = new Paint();
            pincell.setARGB(100, 255, 125, 0);
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
                switch ((int) (enemy.getMovementCycleTime() * 5 / enemy.getCycleTimeMovement()) % 5) {
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

    /*public Canvas drawBaseMonster(Canvas canvas, BaseMonster enemy) {
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
                    switch ((int) (enemy.getMovementCycleTime() * 9 / enemy.getCycleTimeMovement()) % 9) {
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
                if (enemy.m_isAlive()) {
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
    }*/


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

    public void drawBurbujitaMapAndInterface(BurbujitaMap burbujitaMap, Interface burbujitaInterface) {
        if (burbujitaHolder != null) {
            Paint pincell = new Paint();
            pincell.setARGB(255, 181, 230, 29);
            Canvas canvas = burbujitaHolder.lockCanvas();
            canvas.drawARGB(255, 0, 0, 0);
            int screenWidth = canvas.getWidth();
            int screenHeight = canvas.getHeight();
            try {
                int mapWidth = burbujitaMap.getMapWidth() * 100;
                int mapHeight = burbujitaMap.getMapHeight() * 100;
                float offsetX;
                float offsetY;
                float factorDeEscalado;
                float escaldoX = screenWidth / (float) mapWidth;
                float escaldoY = screenHeight / (float) mapHeight;
                if (escaldoX < escaldoY) {
                    factorDeEscalado = escaldoX;
                    offsetX = 0f;
                    offsetY = screenHeight / 2f - factorDeEscalado * mapHeight / 2f;
                } else {
                    factorDeEscalado = escaldoY;
                    offsetX = screenWidth / 2f - factorDeEscalado * mapWidth / 2f;
                    offsetY = 0f;
                }
                canvas.translate(offsetX, offsetY);
                canvas.scale(factorDeEscalado, factorDeEscalado);
                canvas.drawARGB(255, 0, 0, 0);
                //canvas.translate(offsetX, offsetY);
                //canvas.scale(factorDeEscalado, factorDeEscalado);
                canvas = drawCasillas(burbujitaMap, canvas);
                canvas = drawTurrets(burbujitaMap, canvas);
                canvas = drawEnemies(burbujitaMap, canvas);
                canvas = drawBullets(burbujitaMap, canvas);
                //canvas = drawTurretsCeils(canvas);
        /*if (!burbujitaMap.getNextTurretBuilded() && burbujitaMap.getNextTurret() != null) {
            try {
                canvas = burbujitaController.drawObjectIntoCanvas(canvas, burbujitaMap.getNextTurret());
            } catch (Exception ignored) {

            }
        }*/
                //canvas.drawText("FPS: " + Math.round(fps), mapWidth / 2, 6 * mapHeight / 8, pincell);
                //canvas.drawText("UPS: " + Math.round(ups), mapWidth / 2, 7 * mapHeight / 8, pincell);


                //canvas.scale(1 / factorDeEscalado, 1 / factorDeEscalado);
                //canvas.translate(-offsetX, -offsetY);
                //canvas = drawInterface(canvas);
                canvas.scale(1 / factorDeEscalado, 1 / factorDeEscalado);
                canvas.translate(-offsetX, -offsetY);
            }
            catch (Exception e) {
                trace("Ha petado en Mapa por: " + e);
            }
            try {
                if (burbujitaInterface.stepShowing() > 0) {
                    ArrayList<InterfaceButton> aux = burbujitaInterface.getButtons();
                    int size = aux.size();
                    for (int i = 0; i < size; i++) {
                        pincell.setARGB(255, 0, 0, 0);
                        InterfaceButton aux2 = aux.get(i);
                        float radix = aux2.getRadix();
                        if (iconoseta1 == null || iconoseta2 == null || iconoseta3 == null) {
                            iconoseta1 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.iconoseta1)), (int) (2 * radix), (int) (2 * radix), true);
                            iconoseta2 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.iconoseta2)), (int) (2 * radix), (int) (2 * radix), true);
                            iconoseta3 = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.iconoseta3)), (int) (2 * radix), (int) (2 * radix), true);
                        }
                        float iconoX = aux2.getActualX();
                        float iconoY = aux2.getActualY();
                        if (i == 0) {
                            if (aux2.isSelected()) {
                                canvas.drawBitmap(iconoseta1, iconoX-radix, iconoY-radix, pincell);
                            } else {
                                canvas.drawBitmap(iconoseta1, iconoX-radix, iconoY-radix, pincell);
                                pincell.setARGB(100, 100, 100, 100);
                                canvas.drawCircle(iconoX, iconoY, radix, pincell);
                            }
                        } else if (i == 1) {
                            if (aux2.isSelected()) {
                                canvas.drawBitmap(iconoseta2, iconoX-radix, iconoY-radix, pincell);
                            } else {
                                canvas.drawBitmap(iconoseta2, iconoX-radix, iconoY-radix, pincell);
                                pincell.setARGB(100, 100, 100, 100);
                                canvas.drawCircle(iconoX, iconoY, radix, pincell);
                            }
                        } else if (i == 2) {
                            if (aux2.isSelected()) {
                                canvas.drawBitmap(iconoseta3, iconoX-radix, iconoY-radix, pincell);
                            } else {
                                canvas.drawBitmap(iconoseta3, iconoX-radix, iconoY-radix, pincell);
                                pincell.setARGB(100, 100, 100, 100);
                                canvas.drawCircle(iconoX, iconoY, radix, pincell);
                            }
                        } else {
                            if (aux2.isCircular()) {
                                canvas.drawCircle(iconoX, iconoY, radix, pincell);
                            } else {
                                canvas.drawRect(iconoX-radix, iconoY-radix, iconoX + radix, iconoY + radix, pincell);
                            }
                        }
                    }
                    InterfaceButton moreInfo = burbujitaInterface.getMoreInfoButton();
                    float radix = moreInfo.getRadix();
                    float iconoX = moreInfo.getActualX();
                    float iconoY = moreInfo.getActualY();
                    if (moreInfo.isSelected()) {
                        pincell.setARGB(255, 0, 255, 0);
                    } else {
                        pincell.setARGB(255, 255, 0, 0);
                    }
                    if (moreInfo.isCircular()) {
                        canvas.drawCircle(iconoX, iconoY, radix, pincell);
                    } else {
                        canvas.drawRect(iconoX-radix, iconoY-radix, iconoX + radix, iconoY+radix, pincell);
                    }
                }
            }
            catch (Exception e) {
                trace("Ha petado en Interface por: " + e);
            }
            burbujitaHolder.unlockCanvasAndPost(canvas);
        }
    }


    private void trace(String str) {
        Trace.write(" Pintor::" + str);
    }

    private Canvas drawCasillas(BurbujitaMap burbujitaMap, Canvas canvas) {
        ArrayList<Casilla> casillas = burbujitaMap.getCasillaMap();
        int size = casillas.size();
        for (int i = 0; i < size; i++) {
            canvas = drawCasilla(canvas, casillas.get(i));
        }
        return canvas;
    }

    private Canvas drawTurrets(BurbujitaMap burbujitaMap, Canvas canvas) {
        ArrayList<Turret> turrets = burbujitaMap.getTurrets();
        int size = turrets.size();
        for (int i = 0; i < size; i++) {
            canvas = drawTurret(canvas, turrets.get(i));
        }
        Turret nextTurret = burbujitaMap.getNextTurret();
        if (nextTurret != null) {
            canvas = drawPreTurret(canvas, nextTurret);
        }
        return canvas;
    }

    private Canvas drawEnemies(BurbujitaMap burbujitaMap, Canvas canvas) {
        ArrayList<Enemy> enemies = burbujitaMap.getEnemies();
        int size = enemies.size();
        for (int i = 0; i < size; i++) {
            canvas = drawEnemy(canvas, enemies.get(i));
        }
        return canvas;
    }

    private Canvas drawBullets(BurbujitaMap burbujitaMap, Canvas canvas) {
        ArrayList<Bullet> bullets = burbujitaMap.getBullets();
        int size = bullets.size();
        for (int i = 0; i < size; i++) {
            canvas = drawBullet(canvas, bullets.get(i));
        }
        return canvas;
    }

    public void setBurbujitaHolder(SurfaceHolder burbujitaHolder) {
        iconoseta1 = null;
        iconoseta2 = null;
        iconoseta3 = null;
        this.burbujitaHolder = burbujitaHolder;
    }
}
