package com.gmail.rogermoreta.speedpaintmaze.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gmail.rogermoreta.speedpaintmaze.R;
import com.google.android.gms.common.api.GoogleApiClient;

public class PointsView extends SurfaceView implements SurfaceHolder.Callback {

    int width;
    int height;
    int RR, G, B;
    private PointsActivity BGA;
    int puntos_maximos_normal, puntos_maximos_resistencia;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPref;
    private Bitmap background;
    private Bitmap ranking;
    private Bitmap rankinga;
    private Bitmap cero;
    private Bitmap uno;
    private Bitmap dos;
    private Bitmap tres;
    private Bitmap cuatro;
    private Bitmap cinco;
    private Bitmap seis;
    private Bitmap siete;
    private Bitmap ocho;
    private Bitmap nueve;

    public PointsView(Context context) {
        super(context);
    }

    public PointsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getHolder().addCallback(this);
        setFocusable(true);
        sharedPref = getContext()
                .getSharedPreferences(
                        getContext().getString(R.string.sharedPoints),
                        Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.apply();
        RR = G = B = 255;
    }

    public void Init(PointsActivity base, @SuppressWarnings("UnusedParameters") GoogleApiClient googleApiClient, int width, int height) {
        this.width = width;
        this.height = height;
        puntos_maximos_normal = Math.max(sharedPref.getInt("puntos_normal_aux", 0), sharedPref.getInt("puntos_normal_best", 0));
        puntos_maximos_resistencia = Math.max(sharedPref.getInt("puntos_resistencia_aux", 0), sharedPref.getInt("puntos_resistencia_best", 0));
        //GAP = googleApiClient;
        BGA = base;
        int ancho_digito = width / 12;
        int alto_digito = height / 9;
        background = drawableToBitmap(ContextCompat.getDrawable(getContext(), R.drawable.cero));
        cero = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
        background = drawableToBitmap(ContextCompat.getDrawable(getContext(), R.drawable.uno));
        uno = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
        background = drawableToBitmap(ContextCompat.getDrawable(getContext(), R.drawable.dos));
        dos = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
        background = drawableToBitmap(ContextCompat.getDrawable(getContext(), R.drawable.tres));
        tres = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
        background = drawableToBitmap(ContextCompat.getDrawable(getContext(), R.drawable.cuatro));
        cuatro = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
        background = drawableToBitmap(ContextCompat.getDrawable(getContext(), R.drawable.cinco));
        cinco = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
        background = drawableToBitmap(ContextCompat.getDrawable(getContext(), R.drawable.seis));
        seis = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
        background = drawableToBitmap(ContextCompat.getDrawable(getContext(), R.drawable.siete));
        siete = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
        background = drawableToBitmap(ContextCompat.getDrawable(getContext(), R.drawable.ocho));
        ocho = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
        background = drawableToBitmap(ContextCompat.getDrawable(getContext(), R.drawable.nueve));
        nueve = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
        background = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(getContext(), R.drawable.menuranking)), width, height, true);
        ranking = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(getContext(), R.drawable.botonranking)), width / 2, height / 7, true);
        rankinga = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(getContext(), R.drawable.botonrankinga)), width / 2, height / 7, true);
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

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        doDrawBackground00();
        //pinta fondo
        //pinta puntuaciones
        //pinta botones
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
    }

    private void pinta_puntos_normal(Canvas canvas) {
        int puntos = puntos_maximos_normal;
        int dig1 = puntos / 10000;
        int dig2 = (puntos - dig1 * 10000) / 1000;
        int dig3 = (puntos - dig1 * 10000 - dig2 * 1000) / 100;
        int dig4 = (puntos - dig1 * 10000 - dig2 * 1000 - dig3 * 100) / 10;
        int dig5 = puntos - dig1 * 10000 - dig2 * 1000 - dig3 * 100 - dig4 * 10;
        int posx = width / 8 - width / 256;
        int posy = height / 7;
        if (dig1 != 0) {
            dibuja_digito(canvas, dig1, posx, posy);
        }
        posx += width / 15;
        if (dig1 != 0 || dig2 != 0) {
            dibuja_digito(canvas, dig2, posx, posy);
        }
        posx += width / 15;
        if (dig1 != 0 || dig2 != 0 || dig3 != 0) {
            dibuja_digito(canvas, dig3, posx, posy);
        }
        posx += width / 15;
        if (dig1 != 0 || dig2 != 0 || dig3 != 0 || dig4 != 0) {
            dibuja_digito(canvas, dig4, posx, posy);
        }
        posx += width / 15;
        dibuja_digito(canvas, dig5, posx, posy);
    }

    private void pinta_puntos_resistencia(Canvas canvas) {
        int puntos = puntos_maximos_resistencia;
        int dig1 = puntos / 10000;
        int dig2 = (puntos - dig1 * 10000) / 1000;
        int dig3 = (puntos - dig1 * 10000 - dig2 * 1000) / 100;
        int dig4 = (puntos - dig1 * 10000 - dig2 * 1000 - dig3 * 100) / 10;
        int dig5 = puntos - dig1 * 10000 - dig2 * 1000 - dig3 * 100 - dig4 * 10;
        int posx = 6 * width / 12 + width / 64;
        int posy = height / 7;
        if (dig1 != 0) {
            dibuja_digito(canvas, dig1, posx, posy);
        }
        posx += width / 15;
        if (dig1 != 0 || dig2 != 0) {
            dibuja_digito(canvas, dig2, posx, posy);
        }
        posx += width / 15;
        if (dig1 != 0 || dig2 != 0 || dig3 != 0) {
            dibuja_digito(canvas, dig3, posx, posy);
        }
        posx += width / 15;
        if (dig1 != 0 || dig2 != 0 || dig3 != 0 || dig4 != 0) {
            dibuja_digito(canvas, dig4, posx, posy);
        }
        posx += width / 15;
        dibuja_digito(canvas, dig5, posx, posy);
    }

    public void doDrawBackground00() {
        Canvas c = getHolder().lockCanvas();
        c.drawBitmap(background, 0, 0, null);
        pinta_puntos_normal(c);
        pinta_puntos_resistencia(c);
        if (BGA.signIn) {
            c.drawBitmap(ranking, width / 4, height / 2 - height / 16, null);
            c.drawBitmap(ranking, width / 4, 3 * height / 4 - height / 32, null);
        }
        getHolder().unlockCanvasAndPost(c);
    }

    public void doDrawBackground10() {
        Canvas c = getHolder().lockCanvas();
        c.drawBitmap(background, 0, 0, null);
        pinta_puntos_normal(c);
        pinta_puntos_resistencia(c);
        if (BGA.signIn) {
            c.drawBitmap(rankinga, width / 4, height / 2 - height / 16, null);
            c.drawBitmap(ranking, width / 4, 3 * height / 4 - height / 32, null);
        }
        getHolder().unlockCanvasAndPost(c);
    }

    public void doDrawBackground01() {
        Canvas c = getHolder().lockCanvas();
        c.drawBitmap(background, 0, 0, null);
        pinta_puntos_normal(c);
        pinta_puntos_resistencia(c);
        if (BGA.signIn) {
            c.drawBitmap(ranking, width / 4, height / 2 - height / 16, null);
            c.drawBitmap(rankinga, width / 4, 3 * height / 4 - height / 32, null);
        }
        getHolder().unlockCanvasAndPost(c);
    }


    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        //Si apreta por la zona de un boton, cambiar la vista del boton apagado.
        //si suelta encima un boton, pues StartActivity LeaderBoard.
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (x > width / 4 && x < 3 * width / 4) {
                    if (y > (height / 2 - height / 16) && y < (height / 2 - height / 16 + height / 7)) {
                        doDrawBackground10();
                    } else if (y > (3 * height / 4 - height / 32) && y < (3 * height / 4 - height / 32 + height / 7)) {
                        doDrawBackground01();
                    } else {
                        doDrawBackground00();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (x > width / 4 && x < 3 * width / 4) {
                    if (y > (height / 2 - height / 16) && y < (height / 2 - height / 16 + height / 7)) {
                        doDrawBackground10();
                    } else if (y > (3 * height / 4 - height / 32) && y < (3 * height / 4 - height / 32 + height / 7)) {
                        doDrawBackground01();
                    } else {
                        doDrawBackground00();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                doDrawBackground00();
                if (x > width / 4 && x < 3 * width / 4) {
                    if (y > (height / 2 - height / 16) && y < (height / 2 - height / 16 + height / 7) && BGA.signIn) {
                        muestra_ranking_normal();
                    } else if (y > (3 * height / 4 - height / 32) && y < (3 * height / 4 - height / 32 + height / 7) && BGA.signIn) {
                        muestra_ranking_resistencia();
                    }
                }
                break;
        }

        return true;
    }

    private void muestra_ranking_normal() {
        int best = sharedPref.getInt("puntos_normal_best", 0);
        int actual = sharedPref.getInt("puntos_normal_aux", 0);
        if (best < actual) {
            //Games.Leaderboards.submitScore(GAP,  getContext().getString(R.string.leaderboard_10_seconds_ranking), actual);
            editor.putInt("puntos_normal_best", actual);
            editor.commit();
            Log.i("PointsView", "metera en normal actual:" + actual);
        }
        //BGA.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(GAP, getContext().getString(R.string.leaderboard_10_seconds_ranking)), 1);
    }

    private void muestra_ranking_resistencia() {
        int best = sharedPref.getInt("puntos_resistencia_best", 0);
        int actual = sharedPref.getInt("puntos_resistencia_aux", 0);
        if (best < actual) {
            //Games.Leaderboards.submitScore(GAP,  getContext().getString(R.string.leaderboard_resistance_ranking), actual);
            editor.putInt("puntos_resistencia_best", actual);
            editor.commit();
            Log.i("PointsView", "metera en resistance actual:" + actual);
        }
        //BGA.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(GAP, getContext().getString(R.string.leaderboard_resistance_ranking)), 3);
    }

    public void desconecta() {
        doDrawBackground00();
    }

    public void conecta() {
        doDrawBackground00();
    }

    private void dibuja_digito(Canvas canvas, int dig1, int posx, int posy) {
        if (canvas != null) {
            switch (dig1) {
                case 0:
                    canvas.drawBitmap(cero, posx, posy, null);
                    break;
                case 1:
                    canvas.drawBitmap(uno, posx, posy, null);
                    break;
                case 2:
                    canvas.drawBitmap(dos, posx, posy, null);
                    break;
                case 3:
                    canvas.drawBitmap(tres, posx, posy, null);
                    break;
                case 4:
                    canvas.drawBitmap(cuatro, posx, posy, null);
                    break;
                case 5:
                    canvas.drawBitmap(cinco, posx, posy, null);
                    break;
                case 6:
                    canvas.drawBitmap(seis, posx, posy, null);
                    break;
                case 7:
                    canvas.drawBitmap(siete, posx, posy, null);
                    break;
                case 8:
                    canvas.drawBitmap(ocho, posx, posy, null);
                    break;
                case 9:
                    canvas.drawBitmap(nueve, posx, posy, null);
                    break;
                default:
                    //nunca deberia entrar
            }
        }
    }
}
