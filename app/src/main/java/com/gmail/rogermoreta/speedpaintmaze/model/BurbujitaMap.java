package com.gmail.rogermoreta.speedpaintmaze.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import com.gmail.rogermoreta.speedpaintmaze.view.BurbujitaActivity;

import java.util.ArrayList;
import java.util.Random;

public class BurbujitaMap {

    private SurfaceHolder surfaceHolder;
    private ArrayList<Turret> turrets;
    private ArrayList<Bullet> bullets;
    private ArrayList<Enemy> enemies;
    private Paint pincell;
    private int height;
    private int width;
    private Turret nextTurret;
    private int lastX;
    private int lastY;
    private Pintor pintor;

    public BurbujitaMap(SurfaceHolder surfaceHolder, BurbujitaActivity burbujitaActivity) {
        lastX = -1;
        lastY = -1;
        turrets = new ArrayList<>();
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        this.surfaceHolder = surfaceHolder;
        Canvas canvas = this.surfaceHolder.lockCanvas();
        width = canvas.getWidth();
        height = canvas.getHeight();
        BaseMonster enemy = new BaseMonster();
        enemy.asignarMoveTarget(width,height);
        enemy.setVelocity(5f);
        enemies.add(enemy);
        pincell = new Paint();
        pincell.setARGB(175, 0, 0, 0);
        pincell.setTextAlign(Paint.Align.CENTER);
        pincell.setTextSize(Math.min(width, height) / 15);
        this.surfaceHolder.unlockCanvasAndPost(canvas);
        pintor = new Pintor(width,height,burbujitaActivity);
    }

    public void draw(float fps, float ups) {
        if (this.surfaceHolder != null) {
            Canvas canvas = this.surfaceHolder.lockCanvas();
            canvas.drawARGB(255, 181, 230, 29);
            canvas = drawTurretsBases(canvas);
            canvas = drawEnemies(canvas);
            canvas = drawBullets(canvas);
            canvas = drawTurretsCeils(canvas);
            if (nextTurret != null) {
                try {
                    pintor.drawBaseTurret(canvas, nextTurret);
                    pintor.drawCeilTurret(canvas, nextTurret);
                }
                catch (Exception ignored) {

                }
            }
            canvas.drawText("FPS: "+Math.round(fps), width / 2, 6 * height / 8, pincell);
            canvas.drawText("UPS: "+Math.round(ups), width / 2, 7 * height / 8, pincell);
            this.surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private Canvas drawTurretsBases(Canvas canvas) {
        for (int i = 0; i < turrets.size(); i++) {
            canvas = pintor.drawBaseTurret(canvas, turrets.get(i));
        }
        return canvas;
    }

    private Canvas drawEnemies(Canvas canvas) {
        for (int i = 0; i < enemies.size(); i++) {
            canvas = pintor.drawEnemy(canvas, enemies.get(i));
        }
        return canvas;
    }

    private Canvas drawBullets(Canvas canvas) {
        for (int i = 0; i < bullets.size(); i++) {
            canvas = pintor.drawBullet(canvas, bullets.get(i));
        }
        return canvas;
    }

    private Canvas drawTurretsCeils(Canvas canvas) {
        for (int i = 0; i < turrets.size(); i++) {
            canvas = pintor.drawCeilTurret(canvas, turrets.get(i));
        }
        return canvas;
    }

    /**
     * Se actualizan los estados de todos los elementos que dependen del tiempo
     * Es importante actualizar el estado de las torretas antes que la de los disparos
     * porque las torretas crean más disparos.
     * @param milisegundos tiempo desde la última actualización de estado, esta variable
     *                     deberia ser siempre de unos 20ms, pero puede variar.
     */
    public void logic(long milisegundos) {
        calcularColisiones();
        actualizarEstadoBichos(milisegundos);
        actualizarEstadoTorretas(milisegundos);
        actualizarEstadoDisparos(milisegundos);
    }

    private void calcularColisiones() {
        for (int i = 0; i < bullets.size(); ++i) {
            Bullet disparo = bullets.get(i);
            if (!disparo.existe()) {
                bullets.remove(i);
            }
            else {
                if (!disparo.estaExplotando()) {
                    for (int j = 0; j < enemies.size(); ++j) {
                        if (colisionaConEnemigo(disparo, (BaseMonster) enemies.get(j))) {
                            disparo.explota();
                            enemies.get(j).receiveDamage(1);
                        }
                    }
                    /*for (int j = 0; j < turrets.size(); ++j) {
                        if (colisionaConTurret(disparo, turrets.get(j))) {
                            disparo.explota();
                            //enemies.get(i).receiveDamage(1);
                        }
                    }*/
                }
            }
        }
    }

    private boolean colisionaConTurret(Bullet disparo, Turret turret) {
        return (disparo.getPosX()-turret.getX())*(disparo.getPosX()-turret.getX())+(disparo.getPosY()-turret.getY())*(disparo.getPosY()-turret.getY()) < disparo.getRadius()*disparo.getRadius();
    }

    private boolean colisionaConEnemigo(Bullet disparo, BaseMonster enemy) {
        return (disparo.getPosX()-(enemy.getX()))*(disparo.getPosX()-enemy.getX())+(disparo.getPosY()-enemy.getY())*(disparo.getPosY()-enemy.getY()) < disparo.getRadius()*disparo.getRadius();
    }

    private void actualizarEstadoBichos(long milisegundos) {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemigoTratado = enemies.get(i);
            enemigoTratado.logic(milisegundos);
            lastX = (int) enemies.get(i).getX();
            lastY = (int) enemies.get(i).getY();
            if (lastX >= 0.9*width && lastY >= 0.9*height) {
                enemigoTratado.asignarMoveTarget(0, 0);
            }
            else if (lastX <= 0.1*width && lastY <= 0.1*height) {
                enemigoTratado.asignarMoveTarget(width, height);
            }
        }
    }

    private void actualizarEstadoTorretas(long milisegundos) {
        for (int i = 0; i < turrets.size(); i++) {
            Turret torretaTratada = turrets.get(i);
            torretaTratada.logic(milisegundos);
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
            if (torretaTratada.readyToFire()) {
                torretaTratada.dispara();
                bullets.add(new Bullet(torretaTratada.getX(), torretaTratada.getY(), 0.40f, objX, objY, 0.015f*Math.max(width, height) ));
            }
        }
    }

    private void actualizarEstadoDisparos(long milisegundos) {
        for (int i = 0; i < bullets.size(); i++) {
            if (lastX == -1 || lastY == -1) {
                bullets.get(i).logic(milisegundos);
            }
            else {
                bullets.get(i).logic(milisegundos,lastX, lastY);
            }
        }
    }

    public void createNewNextTurret(int x, int y) {
        nextTurret = new Turret(x,y,0.02f*Math.max(width, height));
    }

    public void setNextTurret(int x, int y) {
        nextTurret.setX(x);
        nextTurret.setY(y);
    }

    public void buildTurret(int x, int y) {
        turrets.add(new Turret(x, y, nextTurret.getRadius()));
    }

}
