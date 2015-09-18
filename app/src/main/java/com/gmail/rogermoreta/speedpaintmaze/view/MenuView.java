package com.gmail.rogermoreta.speedpaintmaze.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gmail.rogermoreta.speedpaintmaze.thread.ClickThread;
import com.gmail.rogermoreta.speedpaintmaze.R;
import com.gmail.rogermoreta.speedpaintmaze.model.Figura;
import com.gmail.rogermoreta.speedpaintmaze.model.Rectangulo;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class MenuView extends SurfaceView implements SurfaceHolder.Callback {

    private ClickThread thread;
    private ArrayList<Figura> figuras;
    private int figuraActiva;
    //Paint p; //The Paint class holds the style and color information about how to draw geometries, text and bitmaps.
    int width;
    int height;
    private Bitmap bg;
    private Bitmap jugar;
    private Bitmap jugara;
    private Bitmap resistencia;
    private Bitmap resistenciaa;
    private Bitmap puntos;
    private Bitmap puntosa;
    private Bitmap logros;
    private Bitmap logrosa;
    private int offset;

    //Para google Api y saber si la actividad menu ha recibido bien el login
    private GoogleApiClient GAP;
    private MenuActivity BGA;


    public MenuView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public MenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // nothing here
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {

        figuras = new ArrayList<>(); //Añadimos 4 rectangulos lógicos, para saber si clickan dentro.

        figuras.add(new Rectangulo(1, width / 4, 3 * height / 7 + offset, width / 2, height / 7));
        figuras.add(new Rectangulo(2, width / 4, 4 * height / 7 + offset, width / 2, height / 7));
        figuras.add(new Rectangulo(3, width / 4, 5 * height / 7 + offset, width / 2, height / 7));
        figuras.add(new Rectangulo(4, width / 4, 6 * height / 7 + offset, width / 2, height / 7));

        figuraActiva = -1;

        //arrancamos el thread.
        thread = new ClickThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override //paramos el thread.
    public void surfaceDestroyed(SurfaceHolder arg0) {
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void onDrawC(Canvas canvas) {
        //p.setAntiAlias(true); //Pa que desenfoque las aristas y se vea mas suave.

        //Pintamos el background
        canvas.drawBitmap(bg, 0, 0, null);

        //Pintamos botones, segun si estan apretados o no y si tenemos conexion con Google pay service
        for (int i = 0; i < figuras.size(); ++i) {
            Figura f = figuras.get(i);
            int figura_id = f.getId();
            switch (figura_id) {
                case 1:
                    if (figura_id == figuraActiva) {
                        canvas.drawBitmap(jugara, width / 4, 3 * height / 7 + offset, null);
                    } else {
                        canvas.drawBitmap(jugar, width / 4, 3 * height / 7 + offset, null);
                    }
                    break;
                case 2:
                    if (figura_id == figuraActiva) {
                        canvas.drawBitmap(resistenciaa, width / 4, 4 * height / 7 + offset, null);
                    } else {
                        canvas.drawBitmap(resistencia, width / 4, 4 * height / 7 + offset, null);
                    }
                    break;
                case 3:
                    if (figura_id == figuraActiva) {
                        canvas.drawBitmap(puntosa, width / 4, 5 * height / 7 + offset, null);
                    } else {
                        canvas.drawBitmap(puntos, width / 4, 5 * height / 7 + offset, null);
                    }
                    break;
                case 4:
                    if (BGA.signIn) {
                        if (figura_id == figuraActiva) {
                            canvas.drawBitmap(logrosa, width / 4, 6 * height / 7 + offset, null);
                        } else {
                            canvas.drawBitmap(logros, width / 4, 6 * height / 7 + offset, null);
                        }
                    }
                    break;
                default:
                    System.out.println("No deberia entrar en el switch de onDraw con la opcion: " + figura_id);
            }
        }
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (Figura f : figuras) {
                    if (f.cointainsPoint(x, y)) {
                        figuraActiva = f.getId();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                figuraActiva = -1;
                for (Figura f : figuras) {
                    if (f.cointainsPoint(x, y)) {
                        figuraActiva = f.getId();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                for (Figura f : figuras) {
                    if (f.getId() == figuraActiva) {
                        Intent mainIntent;
                        int figura_id = f.getId();
                        switch (figura_id) {
                            case 1:
                                mainIntent = new Intent().setClass(getContext(), GameActivity.class);
                                mainIntent.putExtra("time", 500l); //Partida con inc. de 0.5 sec.
                                getContext().startActivity(mainIntent);
                                break;
                            case 2:
                                mainIntent = new Intent().setClass(getContext(), GameActivity.class);
                                mainIntent.putExtra("time", 30000l); //Partida con inc. de 3.0 sec.
                                getContext().startActivity(mainIntent);
                                break;
                            case 3:
                                getContext().startActivity(new Intent().setClass(getContext(), PointsActivity.class));
                                break;
                            case 4:
                                //new Logros_Manager(BGA,GAP);
                                //BGA.startActivityForResult(Games.Achievements.getAchievementsIntent(GAP), 2);
                                break;
                            default:
                                System.out.println("No deberia entrar en el switch de onTouch up con la opcion: " + figura_id);
                        }
                    }
                }
                break;
        }

        return true;
    }

    public void Init(MenuActivity base, GoogleApiClient googleApiClient, int width, int height) {

        this.width = width;
        this.height = height;

        GAP = googleApiClient;
        BGA = base;

        //margen superior
        offset = -height / 14;

        //Cargamos todas las imagenes.
        bg = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(getContext(), R.drawable.menut)), width, height, true);
        jugar = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(getContext(), R.drawable.botonjugar)), width / 2, height / 7, true);
        jugara = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(getContext(), R.drawable.botonjugara)), width / 2, height / 7, true);
        resistencia = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(getContext(), R.drawable.botonresistencia)), width / 2, height / 7, true);
        resistenciaa = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(getContext(), R.drawable.botonresistenciaa)), width / 2, height / 7, true);
        puntos = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(getContext(), R.drawable.botonpuntos)), width / 2, height / 7, true);
        puntosa = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(getContext(), R.drawable.botonpuntosa)), width / 2, height / 7, true);
        logros = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(getContext(), R.drawable.botonlogros)), width / 2, height / 7, true);
        logrosa = Bitmap.createScaledBitmap(drawableToBitmap(ContextCompat.getDrawable(getContext(), R.drawable.botonlogrosa)), width / 2, height / 7, true);
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
