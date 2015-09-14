package com.gmail.rogermoreta.speedpaint;

import java.util.ArrayList;

import com.purplebrain.adbuddiz.sdk.AdBuddiz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = GameView.class.getSimpleName();

	private GameThread thread;
	private long beginTime;
	private long last_time;
	private long tiempo_parpadeo;
	private long tiempo_finpartida;
	private final static Paint paint = new Paint();

	int width;
	int height;
	int level = 0;
	long time_milis = 0;
	private boolean partida_ON = false;
	// private int xPos,yPos;
	private Pair<Integer, Integer> lastPair = null;
	private static Bitmap lienzoBg;
	private static Bitmap lienzo;
	private static Bitmap porcentaje;
	private static Bitmap fintiempo;
	private static Bitmap instruc;
	private static Bitmap instruc2;
	private Bitmap resultado;
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
	private Bitmap ncero;
	private Bitmap nuno;
	private Bitmap ndos;
	private Bitmap ntres;
	private Bitmap ncuatro;
	private Bitmap ncinco;
	private Bitmap nseis;
	private Bitmap nsiete;
	private Bitmap nocho;
	private Bitmap nnueve;
	private Bitmap boton;
	private Bitmap botona;
	ArrayList<Pair<Integer, Integer>> cola_click;
	private boolean[][] mask_paint;

	private boolean fin_partida = false;
	private boolean cambio = false;
	private int pixels;
	private int total_pixels;
	private Rectangulo r;
	private int offset;
	private boolean apretado = false;
	private boolean resistencia = true;
	private boolean fin_finpartida = false;
	private SharedPreferences.Editor editor;
	private SharedPreferences sharedPref;
	private long tiempo_regeneracion;

	public GameView(Context context) {
		super(context);
	}

	public GameView(Context context, int width, int height, long tiempo) {
		super(context);
		getHolder().addCallback(this);
		thread = new GameThread(getHolder(), this);
		setFocusable(true);

		if (tiempo < 1500) resistencia = false;

		AdBuddiz.setPublisherKey("3bbbf437-a99b-40bb-a835-e025d2f92dc3");
	    AdBuddiz.cacheAds((Activity) context); // this = current Activity
		
	    tiempo_parpadeo = SystemClock.uptimeMillis();
	    // Begin loading your interstitial.
	    offset = -height /48;
		r = new Rectangulo(1, width / 3, 6 * height / 7+offset, width / 3+width/45,
				height / 8);
		// CACA
		paint.setARGB(255, 255, 255, 255);
		sharedPref = getContext()
				.getSharedPreferences(
						getContext().getString(R.string.sharedPoints),
						Context.MODE_PRIVATE);
		editor = sharedPref.edit();
		tiempo_regeneracion = tiempo;
		total_pixels = 10 * (width /11) * 213 * (height / 280);
		pixels = 0;
		cola_click = new ArrayList<Pair<Integer, Integer>>();
		this.width = width;
		this.height = height;
		this.mask_paint = new boolean[width][height];
		Paint circlePaint = new Paint();
		circlePaint.setAntiAlias(true);
		circlePaint.setColor(Color.RED);
		lienzo = Bitmap.createBitmap(width, 6 * height / 7,	Bitmap.Config.ARGB_8888);
		Bitmap background = BitmapFactory.decodeResource(getResources(),
				R.drawable.boton);
		boton = Bitmap.createScaledBitmap(background, width / 3, height / 8, true);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.botona);
		botona = Bitmap.createScaledBitmap(background, width / 3, height / 8,
				true);
		lienzoBg = Bitmap.createScaledBitmap(((BitmapDrawable) getResources().getDrawable(R.drawable.fondo_lienzo)).getBitmap(), width, height, true);
		if (resistencia) {
			instruc = Bitmap.createScaledBitmap(((BitmapDrawable) getResources().getDrawable(R.drawable.instrucr)).getBitmap(), width, height, true);
			instruc2 = Bitmap.createScaledBitmap(((BitmapDrawable) getResources().getDrawable(R.drawable.instrucr2)).getBitmap(), width, height, true);
		}
		else
		{
			instruc = Bitmap.createScaledBitmap(((BitmapDrawable) getResources().getDrawable(R.drawable.instrucn)).getBitmap(), width, height, true);
			instruc2 = Bitmap.createScaledBitmap(((BitmapDrawable) getResources().getDrawable(R.drawable.instrucn2)).getBitmap(), width, height, true);
		}
		resultado = Bitmap.createScaledBitmap(((BitmapDrawable) getResources().getDrawable(R.drawable.puntos)).getBitmap(), width, height, true);
		int ancho_digito = width/12;
		int alto_digito = height/10;
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.cero);
		cero = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.uno);
		uno = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.dos);
		dos = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.tres);
		tres = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.cuatro);
		cuatro = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.cinco);
		cinco = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.seis);
		seis = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.siete);
		siete = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.ocho);
		ocho = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.nueve);
		nueve = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.porcentaje);
		porcentaje = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.fintiempo);
		fintiempo = Bitmap.createScaledBitmap(((BitmapDrawable) getResources().getDrawable(R.drawable.fintiempo)).getBitmap(), width, height, true);
		
		ancho_digito = width/16;
		alto_digito = height/14;
		
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.nivel_0);
		ncero = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.nivel_1);
		nuno = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.nivel_2);
		ndos = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.nivel_3);
		ntres = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.nivel_4);
		ncuatro = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.nivel_5);
		ncinco = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.nivel_6);
		nseis = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.nivel_7);
		nsiete = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.nivel_8);
		nocho = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.nivel_9);
		nnueve = Bitmap.createScaledBitmap(background, ancho_digito, alto_digito, true);
		
		int margenx = width / 22;
		int margeny = height / 40;
		for (int i = margeny; i < 11 * height / 14; ++i) {
			for (int j = margenx; j < width - margenx; ++j) {
				lienzo.setPixel(j, i, 0x00000000);
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// nothing here
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

		// if it is the first time the thread starts

		if (thread.getState() == Thread.State.NEW) {
			GameThread.setRunning(true);
			thread.start();
		} else if (thread.getState() == Thread.State.TERMINATED) {
			thread = new GameThread(getHolder(), this);
			GameThread.setRunning(true);
			thread.start(); // Start a new thread
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		Log.d(TAG, "Surface is being destroyed");
		boolean retry = true;
		GameThread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {

			}
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Pair<Integer, Integer> aux = new Pair<Integer, Integer>(
				(int) event.getX(), (int) event.getY());

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (partida_ON)
				lastPair = aux;
			if (fin_finpartida) {
				int x = (int) event.getX();
				int y = (int) event.getY();
				if (x > r.getX() && x < r.getX() + r.getAncho()
						&& y > r.getY() && y < r.getY() + r.getAlto()) {
					apretado = true;
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			apretado = false;
			if (fin_finpartida) {
				int x = (int) event.getX();
				int y = (int) event.getY();
				if (x > r.getX() && x < r.getX() + r.getAncho()
						&& y > r.getY() && y < r.getY() + r.getAlto()) {
					apretado = true;
				}
			}			
			if (partida_ON)
				cola_click.add(aux);
			break;
		case MotionEvent.ACTION_UP:
			if (!partida_ON) {
				beginTime = SystemClock.uptimeMillis();
				last_time = SystemClock.uptimeMillis();
				partida_ON = true;
			}
			if (fin_finpartida && apretado) {
				fin();
			}
			apretado = false;
			break;
		default:
			return false;
		}
		return true;
	}

	public void render(Canvas canvas) {

		 //canvas.drawColor(Color.BLACK);
		/*
		 * if (clickan) { for (int ii = 0; ii < width; ++ii) { for (int jj = 0;
		 * jj < height; ++jj) { if (mask_click[ii][jj]) { for (int i =
		 * xPos-ancho_pinzel/2; i < xPos+ancho_pinzel/2; ++i) { for (int j =
		 * yPos-alto_pinzel/2; j < yPos+alto_pinzel/2; ++j) { Log.i("GameView",
		 * "i: "+i+"\nj: "+j); if (i > width/22 && i < 21*width/22 && j >
		 * height/40 && j < 11*height/14 && !mask_paint[i][j]) {
		 * mask_paint[i][j] = true; lienzo.setPixel(i, j, 0xffff0000); } } }
		 * mask_click[ii][jj] = false; } } } clickan = false; }
		 */

		canvas.drawBitmap(lienzoBg, 0, 0, null);
		if (partida_ON && !fin_partida) 
		{
			canvas.drawBitmap(lienzo, 0, 0, null);
		}
		if (!partida_ON) {
			if (cambio) canvas.drawBitmap(instruc2, 0, 0, null);
			else canvas.drawBitmap(instruc, 0, 0, null);
		}
			

		if (partida_ON) {
			displayTime(canvas,(10 * 1000 - time_milis)/ 1000,((10 * 1000 - time_milis) - ((10 * 1000 - time_milis) / 1000) * 1000)/ 10);
			//displayPercentage(canvas, "" + pixels * 100 / total_pixels + "%");
			displayLevel(canvas, level);
		}
		if (fin_partida) {
			if (!fin_finpartida) canvas.drawBitmap(fintiempo, 0, 0, null);
			else {
				canvas.drawBitmap(resultado, 0, 0, null);
				if (apretado) canvas.drawBitmap(botona, width / 3 + width/45, 6 * height / 7 + offset, null);
				else canvas.drawBitmap(boton, width / 3 + width/45, 6 * height / 7 + offset, null);
				pinta_level(canvas);
				pinta_porcentaje(canvas);
				pinta_puntos(canvas);
			}
		}
		//displayFps(canvas, avgFps);
	}

	private void pinta_level(Canvas canvas) {
		int dig1 = (int) (level/100);
		int dig2 = (int) ((level-(dig1)*100)/10);
		int dig3 = (int) (level-dig1*100-dig2*10);
		int posx = 7*width/20;
		int posy = 4*height/32;
		if (dig1 != 0) dibuja_digito(canvas, dig1,posx,posy);
		posx += width/10;
		if (dig1 != 0 || dig2 != 0) dibuja_digito(canvas, dig2,posx,posy);
		posx += width/10;
		dibuja_digito(canvas, dig3,posx,posy);
	}

	private void pinta_porcentaje(Canvas canvas) {
		int px = pixels*100/total_pixels;
		int dig1 = (int) (px/100);
		int dig2 = (int) ((px-(dig1)*100)/10);
		int dig3 = (int) (px-dig1*100-dig2*10);
		int posx = 16*width/40;
		int posy = 51*height/128;
		if (dig2 != 0) dibuja_digito(canvas, dig2,posx,posy);
		posx += width/10;
		dibuja_digito(canvas, dig3,posx,posy);
		posx += width/10;
		dibuja_digito(canvas, 10,posx,posy);
	}
	
	private void pinta_puntos(Canvas canvas) {
		int puntos = (int) (level * 100 + (pixels * 100)	/ (0.9 * total_pixels));
		int dig1 = (int) (puntos/10000);
		int dig2 = (int) ((puntos-dig1*10000)/1000);
		int dig3 = (int) ((puntos-dig1*10000-dig2*1000)/100);
		int dig4 = (int) ((puntos-dig1*10000-dig2*1000-dig3*100)/10);
		int dig5 = (int) (puntos-dig1*10000-dig2*1000-dig3*100-dig4*10);
		int posx = 6*width/20;
		int posy = 21*height/32;
		if (dig1 != 0) dibuja_digito(canvas, dig1,posx,posy);
		posx += width/10;
		if (dig1 != 0 || dig2 != 0) dibuja_digito(canvas, dig2,posx,posy);
		posx += width/10;
		if (dig1 != 0 || dig2 != 0 || dig3 != 0) dibuja_digito(canvas, dig3,posx,posy);
		posx += width/10;
		if (dig1 != 0 || dig2 != 0 || dig3 != 0 || dig4 != 0) dibuja_digito(canvas, dig4,posx,posy);
		posx += width/10;
		dibuja_digito(canvas, dig5,posx,posy);
	}
	

	/**
	 * This is the game update method. It iterates through all the objects and
	 * calls their update method if they have one or calls specific engine's
	 * update method.
	 */
	public void update() {
		if (!fin_partida) {
			int ancho_pinzel = 10 * width / 110;
			int alto_pinzel = 213 * width / 2800;
	
			if (partida_ON) {
	
				for (int ii = 0; ii < cola_click.size(); ++ii) {
	
					// pintar cuadrado
					// Log.i(TAG,"level<<24: "+Integer.toHexString(level<<24));
					// int pintura = (103409*level+36469)%0x00ffffff;
					int color = 0x88000000 | (0x000000ff << level);
					int x = lastPair.first;
					int y = lastPair.second;
					for (int i = x - ancho_pinzel / 2; i < x + ancho_pinzel / 2; ++i) {
						for (int j = y - alto_pinzel / 2; j < y + alto_pinzel / 2; ++j) {
							// Log.i("GameView", "i: "+i+"\nj: "+j);
							if (i > width / 22 && i < 21 * width / 22
									&& j >= height / 40 && j < 11 * height / 14
									&& !mask_paint[i][j]) {
								mask_paint[i][j] = true;
								pixels++;
								lienzo.setPixel(i, j, color);
							}
						}
					}
					// escoger pasos y esquina
					int pasos = Math.max(
							Math.abs(lastPair.first - cola_click.get(ii).first),
							Math.abs(lastPair.second - cola_click.get(ii).second));
					if (lastPair.first > cola_click.get(ii).first) {
						if (lastPair.second > cola_click.get(ii).second) {// esquina
																			// buena
																			// es la
																			// inversa
																			// (negativa,
																			// negativa)
																			// Log.i(TAG,
																			// "Ha elegido ir para arriba, izquierda");
							for (int i2 = 0; i2 < pasos; ++i2) {
								int i = x
										- ancho_pinzel
										/ 2
										- Math.round(Math.abs(lastPair.first
												- cola_click.get(ii).first)
												* (i2 + 1) / pasos);
								int j = y
										- alto_pinzel
										/ 2
										- Math.round(Math.abs(lastPair.second
												- cola_click.get(ii).second)
												* (i2 + 1) / pasos);
								for (int j2 = 0; j2 < ancho_pinzel; ++j2) {
									if (i + j2 > width / 22
											&& i + j2 < 21 * width / 22
											&& j >= height / 40
											&& j < 11 * height / 14
											&& !mask_paint[i + j2][j]) {
										mask_paint[i + j2][j] = true;
										pixels++;
										lienzo.setPixel(i + j2, j, color);
									}
								}
								for (int j2 = 0; j2 < alto_pinzel; ++j2) {
									if (i > width / 22 && i < 21 * width / 22
											&& j + j2 >= height / 40
											&& j + j2 < 11 * height / 14
											&& !mask_paint[i][j + j2]) {
										mask_paint[i][j + j2] = true;
										pixels++;
										lienzo.setPixel(i, j + j2, color);
									}
								}
							}
						} else if (lastPair.second < cola_click.get(ii).second) {// esquina
																					// buena
																					// es
																					// la
																					// inversa
																					// (negativa,
																					// positiva)
							for (int i2 = 0; i2 < pasos; ++i2) {
								// Log.i(TAG,
								// "Ha elegido ir para abajo, izquierda");
								int i = x
										- ancho_pinzel
										/ 2
										- Math.round(Math.abs(lastPair.first
												- cola_click.get(ii).first)
												* (i2 + 1) / pasos);
								int j = y
										+ alto_pinzel
										/ 2
										+ Math.round(Math.abs(lastPair.second
												- cola_click.get(ii).second)
												* (i2 + 1) / pasos);
								for (int j2 = 0; j2 < ancho_pinzel; ++j2) {
									if (i + j2 > width / 22
											&& i + j2 < 21 * width / 22
											&& j >= height / 40
											&& j < 11 * height / 14
											&& !mask_paint[i + j2][j]) {
										mask_paint[i + j2][j] = true;
										pixels++;
										lienzo.setPixel(i + j2, j, color);
									}
								}
								for (int j2 = 0; j2 < alto_pinzel; ++j2) {
									if (i > width / 22 && i < 21 * width / 22
											&& j - j2 >= height / 40
											&& j - j2 < 11 * height / 14
											&& !mask_paint[i][j - j2]) {
										mask_paint[i][j - j2] = true;
										pixels++;
										lienzo.setPixel(i, j - j2, color);
									}
								}
							}
						}
					} else if (lastPair.first < cola_click.get(ii).first) {
						if (lastPair.second > cola_click.get(ii).second) {// esquina
																			// buena
																			// es la
																			// inversa
																			// (positiva,
																			// negativa)
							for (int i2 = 0; i2 < pasos; ++i2) {
								// Log.i(TAG, "Ha elegido ir para arriba, derecha");
								int i = x
										+ ancho_pinzel
										/ 2
										+ Math.round(Math.abs(lastPair.first
												- cola_click.get(ii).first)
												* (i2 + 1) / pasos);
								int j = y
										- alto_pinzel
										/ 2
										- Math.round(Math.abs(lastPair.second
												- cola_click.get(ii).second)
												* (i2 + 1) / pasos);
								for (int j2 = 0; j2 < ancho_pinzel; ++j2) {
									if (i - j2 > width / 22
											&& i - j2 < 21 * width / 22
											&& j >= height / 40
											&& j < 11 * height / 14
											&& !mask_paint[i - j2][j]) {
										mask_paint[i - j2][j] = true;
										pixels++;
										lienzo.setPixel(i - j2, j, color);
									}
								}
								for (int j2 = 0; j2 < alto_pinzel; ++j2) {
									if (i > width / 22 && i < 21 * width / 22
											&& j + j2 >= height / 40
											&& j + j2 < 11 * height / 14
											&& !mask_paint[i][j + j2]) {
										mask_paint[i][j + j2] = true;
										pixels++;
										lienzo.setPixel(i, j + j2, color);
									}
								}
							}
	
						} else if (lastPair.second < cola_click.get(ii).second) {// esquina
																					// buena
																					// es
																					// la
																					// inversa
																					// (positiva,
																					// positiva)
							for (int i2 = 0; i2 < pasos; ++i2) {
								// Log.i(TAG, "Ha elegido ir para abajo, derecha");
								int i = x
										+ ancho_pinzel
										/ 2
										+ Math.round(Math.abs(lastPair.first
												- cola_click.get(ii).first)
												* (i2 + 1) / pasos);
								int j = y
										+ alto_pinzel
										/ 2
										+ Math.round(Math.abs(lastPair.second
												- cola_click.get(ii).second)
												* (i2 + 1) / pasos);
								for (int j2 = 0; j2 < ancho_pinzel; ++j2) {
									if (i - j2 > width / 22
											&& i - j2 < 21 * width / 22
											&& j >= height / 40
											&& j < 11 * height / 14
											&& !mask_paint[i - j2][j]) {
										mask_paint[i - j2][j] = true;
										pixels++;
										lienzo.setPixel(i - j2, j, color);
									}
								}
								for (int j2 = 0; j2 < alto_pinzel; ++j2) {
									if (i > width / 22 && i < 21 * width / 22
											&& j - j2 >= height / 40
											&& j - j2 < 11 * height / 14
											&& !mask_paint[i][j - j2]) {
										mask_paint[i][j - j2] = true;
										pixels++;
										lienzo.setPixel(i, j - j2, color);
									}
								}
							}
						}
					}
					lastPair = new Pair<Integer, Integer>(cola_click.get(ii).first,
							cola_click.get(ii).second);
	
				}
				cola_click = new ArrayList<Pair<Integer, Integer>>();
				if (pixels >= 0.9 * total_pixels) {
					pixels = 0;
					lienzo = Bitmap.createBitmap(width, 6 * height / 7,
							Bitmap.Config.ARGB_8888);
					mask_paint = new boolean[width][height];

					editor.putInt(getContext().getString(R.string.lrt),
							Math.min(sharedPref.getInt(getContext().getString(R.string.lrt),Integer.MAX_VALUE),
							(int) (SystemClock.uptimeMillis()-(last_time))));
					last_time = SystemClock.uptimeMillis();
					beginTime = Math.min(SystemClock.uptimeMillis(), beginTime
							+ tiempo_regeneracion);
					level++;
				}
				time_milis = SystemClock.uptimeMillis() - beginTime;
				if (time_milis > 10 * 1000 || level > 999) {
					time_milis = 10000;
					tiempo_finpartida = SystemClock.uptimeMillis();
					fin_partida = true;
					guardar_puntos();
					
					
					// Log.i("GameView", "Partida al: "+pixels*100/total_pixels);
				}
			}
			else {
				if (SystemClock.uptimeMillis() - tiempo_parpadeo > 500)
				{
					tiempo_parpadeo = SystemClock.uptimeMillis();
					cambio = !cambio;
				}
			}
		}
		else {
			if (!fin_finpartida && SystemClock.uptimeMillis() - tiempo_finpartida > 2000)
			{
				fin_finpartida = true;
			}
		}

	}

	private void guardar_puntos() {
		Long ultimo = sharedPref.getLong("ultimo_tiempo", 0);
		if (tiempo_finpartida-ultimo > 1440*60*1000) 
		{
			editor.putInt(getContext().getString(R.string.dsjt), 1);
			editor.putLong("dia_referencia", tiempo_finpartida + 1440*60000);
		}
		else
		{
			Long dia_ref = sharedPref.getLong("dia_referencia", 0);
			if(ultimo < dia_ref && dia_ref < tiempo_finpartida) {
				editor.putLong("dia_referencia", dia_ref+1440*60000);
				editor.putInt(getContext().getString(R.string.dsjt), sharedPref.getInt(getContext().getString(R.string.dsjt),0)+1);
			}
		}
		editor.putLong("ultimo_tiempo", tiempo_finpartida);
		if (resistencia) 
		{
			editor.putInt("puntos_resistencia_aux", 
					Math.max(sharedPref.getInt("puntos_resistencia_aux", 0), (int) (level * 100 + (pixels * 100)	/ (0.9 * total_pixels)))
					);
			editor.putInt(getContext().getString(R.string.prt), sharedPref.getInt(getContext().getString(R.string.prt),0)+1);
			editor.putInt(getContext().getString(R.string.lmrt), Math.max(sharedPref.getInt(getContext().getString(R.string.lmrt),0),level));
		}
		else 
		{
			editor.putInt("puntos_normal_aux", 
					Math.max(sharedPref.getInt("puntos_normal_aux", 0), (int) (level * 100 + (pixels * 100)	/ (0.9 * total_pixels)))
					);
			editor.putInt(getContext().getString(R.string.pnt), sharedPref.getInt(getContext().getString(R.string.pnt),0)+1);
			editor.putInt(getContext().getString(R.string.lmnt), Math.max(sharedPref.getInt(getContext().getString(R.string.lmnt),0),level));
		}
		editor.commit();
	}

	@SuppressWarnings("unused")
	private void displayFps(Canvas canvas, String fps) {
		if (canvas != null && fps != null) {
			canvas.drawText(fps, this.getWidth() - 100, 50, paint);
		}
	}

	@SuppressWarnings("unused")
	private void displayPercentage(Canvas canvas, String perc) {
		if (canvas != null && perc != null) {
			canvas.drawText(perc, this.getWidth() / 18, 50, paint);
		}
	}

	private void displayTime(Canvas canvas, long sec, long cent) {
		int dig1 = (int) (sec/10);
		int dig2 = (int) (sec-(sec/10)*10);
		int dig3 = (int) (cent/10);
		int dig4 = (int) (cent-(cent/10)*10);
		int posx = 6*width/44;
		int posy = 41*height/48;
		dibuja_digito(canvas, dig1,posx,posy);
		posx += width/12;
		dibuja_digito(canvas, dig2,posx,posy);
		posx += width/8;
		dibuja_digito(canvas, dig3,posx,posy);
		posx += width/12;
		dibuja_digito(canvas, dig4,posx,posy);
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
				case 10:
					canvas.drawBitmap(porcentaje, posx, posy, null);
				break;
				default:
					//nunca deberia entrar
			}
		}
	}

	private void dibuja_ndigito(Canvas canvas, int dig1, int posx, int posy) {
		if (canvas != null) {
			switch (dig1) {
				case 0:
					canvas.drawBitmap(ncero, posx, posy, null);
				break;
				case 1:
					canvas.drawBitmap(nuno, posx, posy, null);
				break;
				case 2:
					canvas.drawBitmap(ndos, posx, posy, null);
				break;
				case 3:
					canvas.drawBitmap(ntres, posx, posy, null);
				break;
				case 4:
					canvas.drawBitmap(ncuatro, posx, posy, null);
				break;
				case 5:
					canvas.drawBitmap(ncinco, posx, posy, null);
				break;
				case 6:
					canvas.drawBitmap(nseis, posx, posy, null);
				break;
				case 7:
					canvas.drawBitmap(nsiete, posx, posy, null);
				break;
				case 8:
					canvas.drawBitmap(nocho, posx, posy, null);
				break;
				case 9:
					canvas.drawBitmap(nnueve, posx, posy, null);
				break;
				default:
					//nunca deberia entrar
			}
		}
	}
	
	private void displayLevel(Canvas canvas, int level) {
		//if (canvas != null && level != null) {
		//	canvas.drawText(level, this.getWidth() / 18, 6 * height / 7 - 100,
		//			paint);
		//}
		int dig1 = (int) (level/100);
		int dig2 = (int) ((level-(dig1)*100)/10);
		int dig3 = (int) (level-dig1*100-dig2*10);
		int posx = 13*width/16-width/128;
		int posy = 13*height/14+height/128;
		if (dig1 != 0) 
		{
			dibuja_ndigito(canvas, dig1,posx,posy);
			posx += width/16-width/96;
			posy -= width/32;
		}
		if (dig1 != 0 || dig2 != 0) dibuja_ndigito(canvas, dig2,posx,posy);
		posx += width/16-width/96;
		posy -= width/32;
		dibuja_ndigito(canvas, dig3,posx,posy);
	}
	
	private void fin() {
		displayInterstitial();
		((Activity) getContext()).finish();	
	}

	// Invoke displayInterstitial() when you are ready to display an interstitial.
	  public void displayInterstitial() {
		  AdBuddiz.showAd((Activity) getContext());
	  }
	  
	 /* public static Bitmap ResizeTransparentBitmap(Bitmap bmpSrc, int nWidth, int nHeight, int nFilterType, int nAspectRatio)
	    {
	        if(bmpSrc== null)
	            return null;

	        //Get the original dimensions of the bitmap
	        int nOriginWidth = bmpSrc.getWidth();
	        int nOriginHeight = bmpSrc.getHeight();
	        if(nWidth == nOriginWidth && nHeight == nOriginHeight)
	            return bmpSrc;

	        //Prepare a drawing bitmap and graphic object
	        Bitmap bmpOrigin = new Bitmap(nOriginWidth, nOriginHeight);
	        Graphics graph = Graphics.create(bmpOrigin);

	        //Create a line of transparent pixels for later use
	        int[] aEmptyLine = new int[nWidth];
	        for(int x = 0; x < nWidth; x++)
	            aEmptyLine[x] = 0x00000000;
	        //Create two scaled bitmaps
	        Bitmap[] bmpScaled = new Bitmap[2];
	        for(int i = 0; i < 2; i++)
	        {
	            //Draw the bitmap on a white background first, then on a black background
	            graph.setColor((i == 0) ? Color.WHITE : Color.BLACK);
	            graph.fillRect(0, 0, nOriginWidth, nOriginHeight);
	            graph.drawBitmap(0, 0, nOriginWidth, nOriginHeight, bmpSrc, 0, 0);

	            //Create a new bitmap with the desired size
	            bmpScaled[i] = new Bitmap(nWidth, nHeight);
	            if(nAspectRatio == Bitmap.SCALE_TO_FIT)
	            {
	                //Set the alpha channel of all pixels to 0 to ensure transparency is
	                //applied around the picture, if needed by the transformation
	                for(int y = 0; y < nHeight; y++)
	                    bmpScaled[i].setARGB(aEmptyLine, 0, nWidth, 0, y, nWidth, 1);
	            }

	            //Scale the bitmap
	            bmpOrigin.scaleInto(bmpScaled[i], nFilterType, nAspectRatio);
	        }

	        //Prepare objects for final iteration
	        Bitmap bmpFinal = bmpScaled[0];
	        int[][] aPixelLine = new int[2][nWidth];

	        //Iterate every line of the two scaled bitmaps
	        for(int y = 0; y < nHeight; y++)
	        {
	            bmpScaled[0].getARGB(aPixelLine[0], 0, nWidth, 0, y, nWidth, 1);
	            bmpScaled[1].getARGB(aPixelLine[1], 0, nWidth, 0, y, nWidth, 1);

	            //Check every pixel one by one
	            for(int x = 0; x < nWidth; x++)
	            {
	                //If the pixel was untouched (alpha channel still at 0), keep it transparent
	                if(((aPixelLine[0][x] >> 24) & 0xff) == 0)
	                    aPixelLine[0][x] = 0x00000000;
	                else
	                {
	                    //Compute the alpha value based on the difference of intensity
	                    //in the red channel
	                    int nAlpha = ((aPixelLine[1][x] >> 16) & 0xff) -
	                                    ((aPixelLine[0][x] >> 16) & 0xff) + 255;
	                    if(nAlpha == 0)
	                        aPixelLine[0][x] = 0x00000000; //Completely transparent
	                    else if(nAlpha >= 255)
	                        aPixelLine[0][x] |= 0xff000000; //Completely opaque
	                    else
	                    {
	                        //Compute the value of the each channel one by one
	                        int nRed = ((aPixelLine[0][x] >> 16 ) & 0xff);
	                        int nGreen = ((aPixelLine[0][x] >> 8 ) & 0xff);
	                        int nBlue = (aPixelLine[0][x] & 0xff);

	                        nRed = (int)(255 + (255.0 * ((double)(nRed-255)/(double)nAlpha)));
	                        nGreen = (int)(255 + (255.0 * ((double)(nGreen-255)/(double)nAlpha)));
	                        nBlue = (int)(255 + (255.0 * ((double)(nBlue-255)/(double)nAlpha)));

	                        if(nRed < 0) nRed = 0;
	                        if(nGreen < 0) nGreen = 0;
	                        if(nBlue < 0) nBlue = 0;
	                        aPixelLine[0][x] = nBlue | (nGreen<<8) | (nRed<<16) | (nAlpha<<24);
	                    }
	                }
	            }

	            //Change the pixels of this line to their final value
	            bmpFinal.setARGB(aPixelLine[0], 0, nWidth, 0, y, nWidth, 1);
	        }
	        return bmpFinal;
	    }*/
}
