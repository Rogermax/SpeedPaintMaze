package com.gmail.rogermoreta.speedpaintmaze.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

import com.gmail.rogermoreta.speedpaintmaze.controller.BurbujitaController;
import com.gmail.rogermoreta.speedpaintmaze.enums.TipoCasilla;

import java.util.ArrayList;

public class BurbujitaMap {

    private float offsetY;
    private float offsetX;
    private float factorDeEscalado;
    private SurfaceHolder surfaceHolder;
    private ArrayList<Turret> turrets;
    private ArrayList<Bullet> bullets;
    private ArrayList<Enemy> enemies;
    private Paint pincell;
    private int canvasHeight;
    private int canvasWidth;
    private int numeroCasillasX;
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private int numeroCasillasY;
    private Turret nextTurret;
    private int lastX;
    private int lastY;
    private boolean nextTurretBuilded;
    private ArrayList<Casilla> mapaEnCasillas;
    private BurbujitaController burbujitaController;
    public static final char[] codifiedMap;

    static {
        codifiedMap = ("5x4" +
                "Ir3**db2" +
                "...*" +
                "db2**dl3" +
                "*..." +
                "dr3**F").toCharArray();
    }


    public BurbujitaMap(SurfaceHolder surfaceHolder, BurbujitaController burbujitaController) {
        this.surfaceHolder = surfaceHolder;
        this.burbujitaController = burbujitaController;
        lastX = -1;
        lastY = -1;
        turrets = new ArrayList<>();
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        Canvas canvas = this.surfaceHolder.lockCanvas();
        reajustarTamaño(canvas);
        BaseMonster enemy = new BaseMonster();
        enemy.setVelocity(4f);
        enemies.add(enemy);
        enemy = new BaseMonster();
        enemy.setVelocity(3f);
        enemies.add(enemy);
        pincell = new Paint();
        pincell.setARGB(175, 0, 0, 0);
        this.surfaceHolder.unlockCanvasAndPost(canvas);
        leerMapa();
    }

    private void leerMapa() {
        int filas = 0;
        int longFilas = 0; //columnas
        int i = 0;
        int decimalPosition = 1;
        mapaEnCasillas = new ArrayList<>();
        boolean trobat = false;
        for (; i < codifiedMap.length && !trobat; i++) {
            if (codifiedMap[i] < 48 || codifiedMap[i] > 57) {
                trobat = true;
            } else {
                filas += decimalPosition * (codifiedMap[i] - 48);
                decimalPosition *= 10;
            }
        }
        decimalPosition = 1;
        trobat = false;
        for (; i < codifiedMap.length && !trobat; i++) {
            if (codifiedMap[i] < 48 || codifiedMap[i] > 57) {
                trobat = true;
            } else {
                longFilas += decimalPosition * (codifiedMap[i] - 48);
                decimalPosition *= 10;
            }
        }
        canvasWidth = longFilas * 100;
        canvasHeight = filas * 100;
        numeroCasillasX = longFilas;
        numeroCasillasY = filas;
        pincell.setTextAlign(Paint.Align.CENTER);
        pincell.setTextSize(Math.min(canvasWidth, canvasHeight) / 15);
        trobat = false;
        i--;
        int casillaX = 0;
        int casillaY = 0;
        Casilla casilla;
        for (; i < codifiedMap.length && !trobat; i++) {
            boolean trobat2;
            int numSaltos;
            switch (codifiedMap[i]) {
                case 'I':
                    casilla = new Casilla(TipoCasilla.CAMINO, casillaX, casillaY, true, false);
                    i++;
                    numSaltos = 0;
                    switch (codifiedMap[i]) {
                        case 't':
                            i++;
                            decimalPosition = 1;
                            trobat2 = false;
                            for (; i < codifiedMap.length && !trobat2; i++) {
                                if (codifiedMap[i] < 48 || codifiedMap[i] > 57) {
                                    trobat2 = true;
                                } else {
                                    numSaltos += decimalPosition * (codifiedMap[i] - 48);
                                    decimalPosition *= 10;
                                }
                            }
                            i--;
                            casilla.setNextPositions(casillaX, casillaY - numSaltos);
                            break;
                        case 'r':
                            i++;
                            decimalPosition = 1;
                            trobat2 = false;
                            for (; i < codifiedMap.length && !trobat2; i++) {
                                if (codifiedMap[i] < 48 || codifiedMap[i] > 57) {
                                    trobat2 = true;
                                } else {
                                    numSaltos += decimalPosition * (codifiedMap[i] - 48);
                                    decimalPosition *= 10;
                                }
                            }
                            i--;
                            casilla.setNextPositions(casillaX + numSaltos, casillaY);
                            break;
                        case 'l':
                            i++;
                            decimalPosition = 1;
                            trobat2 = false;
                            for (; i < codifiedMap.length && !trobat2; i++) {
                                if (codifiedMap[i] < 48 || codifiedMap[i] > 57) {
                                    trobat2 = true;
                                } else {
                                    numSaltos += decimalPosition * (codifiedMap[i] - 48);
                                    decimalPosition *= 10;
                                }
                            }
                            i--;
                            casilla.setNextPositions(casillaX - numSaltos, casillaY);
                            break;
                        case 'b':
                            i++;
                            decimalPosition = 1;
                            trobat2 = false;
                            for (; i < codifiedMap.length && !trobat2; i++) {
                                if (codifiedMap[i] < 48 || codifiedMap[i] > 57) {
                                    trobat2 = true;
                                } else {
                                    numSaltos += decimalPosition * (codifiedMap[i] - 48);
                                    decimalPosition *= 10;
                                }
                            }
                            i--;
                            casilla.setNextPositions(casillaX, casillaY + numSaltos);
                    }
                    i--;
                    mapaEnCasillas.add(casilla);
                    casillaX++;
                    if (casillaX >= longFilas) {
                        casillaX = 0;
                        casillaY++;
                        if (casillaY >= filas) {
                            trobat = true;
                        }
                    }
                    break;
                case '*':
                    mapaEnCasillas.add(new Casilla(TipoCasilla.CAMINO, casillaX, casillaY));
                    casillaX++;
                    if (casillaX >= longFilas) {
                        casillaX = 0;
                        casillaY++;
                        if (casillaY >= filas) {
                            trobat = true;
                        }
                    }
                    break;
                case 'd':
                    casilla = new Casilla(TipoCasilla.CAMINO, casillaX, casillaY, false, false);
                    i++;
                    numSaltos = 0;
                    switch (codifiedMap[i]) {
                        case 't':
                            i++;
                            decimalPosition = 1;
                            trobat2 = false;
                            for (; i < codifiedMap.length && !trobat2; i++) {
                                if (codifiedMap[i] < 48 || codifiedMap[i] > 57) {
                                    trobat2 = true;
                                } else {
                                    numSaltos += decimalPosition * (codifiedMap[i] - 48);
                                    decimalPosition *= 10;
                                }
                            }
                            i--;
                            casilla.setNextPositions(casillaX, casillaY - numSaltos);
                            break;
                        case 'r':
                            i++;
                            decimalPosition = 1;
                            trobat2 = false;
                            for (; i < codifiedMap.length && !trobat2; i++) {
                                if (codifiedMap[i] < 48 || codifiedMap[i] > 57) {
                                    trobat2 = true;
                                } else {
                                    numSaltos += decimalPosition * (codifiedMap[i] - 48);
                                    decimalPosition *= 10;
                                }
                            }
                            i--;
                            casilla.setNextPositions(casillaX + numSaltos, casillaY);
                            break;
                        case 'l':
                            i++;
                            decimalPosition = 1;
                            trobat2 = false;
                            for (; i < codifiedMap.length && !trobat2; i++) {
                                if (codifiedMap[i] < 48 || codifiedMap[i] > 57) {
                                    trobat2 = true;
                                } else {
                                    numSaltos += decimalPosition * (codifiedMap[i] - 48);
                                    decimalPosition *= 10;
                                }
                            }
                            i--;
                            casilla.setNextPositions(casillaX - numSaltos, casillaY);
                            break;
                        case 'b':
                            i++;
                            decimalPosition = 1;
                            trobat2 = false;
                            for (; i < codifiedMap.length && !trobat2; i++) {
                                if (codifiedMap[i] < 48 || codifiedMap[i] > 57) {
                                    trobat2 = true;
                                } else {
                                    numSaltos += decimalPosition * (codifiedMap[i] - 48);
                                    decimalPosition *= 10;
                                }
                            }
                            i--;
                            casilla.setNextPositions(casillaX, casillaY + numSaltos);
                    }
                    i--;
                    mapaEnCasillas.add(casilla);
                    casillaX++;
                    if (casillaX >= longFilas) {
                        casillaX = 0;
                        casillaY++;
                        if (casillaY >= filas) {
                            trobat = true;
                        }
                    }
                    break;
                case '.':
                    mapaEnCasillas.add(new Casilla(TipoCasilla.CESPED, casillaX, casillaY));
                    casillaX++;
                    if (casillaX >= longFilas) {
                        casillaX = 0;
                        casillaY++;
                        if (casillaY >= filas) {
                            trobat = true;
                        }
                    }
                    break;
                case 'F':
                    casilla = new Casilla(TipoCasilla.CAMINO, casillaX, casillaY, false, true);
                    mapaEnCasillas.add(casilla);
                    casillaX++;
                    if (casillaX >= longFilas) {
                        casillaX = 0;
                        casillaY++;
                        if (casillaY >= filas) {
                            trobat = true;
                        }
                    }
                    break;
                default:
                    Log.d("BurbujitaMapa", "Mapa erroneo!");
            }
        }
    }

    public void reajustarTamaño(Canvas canvas) {
        int screenWidth = canvas.getWidth();
        int screenHeight = canvas.getHeight();
        nextTurretBuilded = true;
        //YO kiero un canvas lógico de 1080*1920
        //Si mi pantalla es de 320*400
        //Recibiré un canvas con width y height de 320,400
        //Entonces dibujaré to do fuera de la pantalla, por tanto, cuando acabe de pintar
        //se ha de reescalar a 320/1080 y 400/1920
        //canvas.scale((float) width / 1080f, (float) height / 1920f);
        float escaldoX = screenWidth / (float) canvasWidth;
        float escaldoY = screenHeight / (float) canvasHeight;
        if (escaldoX < escaldoY) {
            factorDeEscalado = escaldoX;
            offsetX = 0;
            offsetY = screenHeight / 2f - factorDeEscalado * canvasHeight / 2f;
        } else {
            factorDeEscalado = escaldoY;
            offsetX = screenWidth / 2f - factorDeEscalado * canvasWidth / 2f;
            offsetY = 0;
        }
    }

    public void draw(float fps, float ups) {
        if (this.surfaceHolder != null) {
            Canvas canvas = this.surfaceHolder.lockCanvas();
            canvas.drawARGB(255, 0, 0, 0);
            canvas.translate(offsetX, offsetY);
            canvas.scale(factorDeEscalado, factorDeEscalado);
            pincell.setARGB(255, 181, 230, 29);
            canvas.drawRect(0, 0, canvasWidth, canvasHeight, pincell);
            canvas = drawMap(canvas);
            canvas = drawTurretsBases(canvas);
            canvas = drawEnemies(canvas);
            canvas = drawBullets(canvas);
            canvas = drawTurretsCeils(canvas);
            if (!nextTurretBuilded && nextTurret != null) {
                try {
                    canvas = burbujitaController.drawObjectIntoCanvas(canvas, nextTurret, 0);
                    canvas = burbujitaController.drawObjectIntoCanvas(canvas, nextTurret, 1);
                } catch (Exception ignored) {

                }
            }
            pincell.setARGB(175, 0, 0, 0);
            canvas.drawText("FPS: " + Math.round(fps), canvasWidth / 2, 6 * canvasHeight / 8, pincell);
            canvas.drawText("UPS: " + Math.round(ups), canvasWidth / 2, 7 * canvasHeight / 8, pincell);
            this.surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private Canvas drawMap(Canvas canvas) {
        for (int i = 0; i < mapaEnCasillas.size(); ++i) {
            canvas = burbujitaController.drawObjectIntoCanvas(canvas, mapaEnCasillas.get(i), 0);
        }
        return canvas;
    }

    private Canvas drawTurretsBases(Canvas canvas) {
        for (int i = 0; i < turrets.size(); i++) {
            canvas = burbujitaController.drawObjectIntoCanvas(canvas, turrets.get(i), 0);
        }
        return canvas;
    }

    private Canvas drawEnemies(Canvas canvas) {
        for (int i = 0; i < enemies.size(); i++) {
            if (i == 0) {
                canvas = burbujitaController.drawObjectIntoCanvas(canvas, enemies.get(i), 0);
            }
            if (i == 1) {
                canvas = burbujitaController.drawObjectIntoCanvas(canvas, enemies.get(i), 0);
            }
        }
        return canvas;
    }

    private Canvas drawBullets(Canvas canvas) {
        for (int i = 0; i < bullets.size(); i++) {
            canvas = burbujitaController.drawObjectIntoCanvas(canvas, bullets.get(i),0);
        }
        return canvas;
    }

    private Canvas drawTurretsCeils(Canvas canvas) {
        for (int i = 0; i < turrets.size(); i++) {
            canvas = burbujitaController.drawObjectIntoCanvas(canvas, turrets.get(i),1);
        }
        return canvas;
    }

    /**
     * Se actualizan los estados de todos los elementos que dependen del tiempo
     * Es importante actualizar el estado de las torretas antes que la de los disparos
     * porque las torretas crean más disparos.
     *
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
            } else {
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

    /*private boolean colisionaConTurret(Bullet disparo, Turret turret) {
        return (disparo.getPosX() - turret.getX()) * (disparo.getPosX() - turret.getX()) + (disparo.getPosY() - turret.getY()) * (disparo.getPosY() - turret.getY()) < disparo.getRadius() * disparo.getRadius();
    }*/

    private boolean colisionaConEnemigo(Bullet disparo, BaseMonster enemy) {
        return (disparo.getPosX() - (enemy.getX())) * (disparo.getPosX() - enemy.getX()) + (disparo.getPosY() - enemy.getY()) * (disparo.getPosY() - enemy.getY()) < disparo.getRadius() * disparo.getRadius();
    }

    private void actualizarEstadoBichos(long milisegundos) {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemigoTratado = enemies.get(i);
            enemigoTratado.logic(milisegundos);
            lastX = (int) enemies.get(i).getX();
            lastY = (int) enemies.get(i).getY();
            int casillaX = (int) (enemies.get(i).getX()/100);
            int casillaY = (int) (enemies.get(i).getY()/100);
            int indexOfCasilla = casillaX+casillaY*numeroCasillasX;
            Casilla casillaDelBicho = mapaEnCasillas.get(indexOfCasilla);
            if (casillaDelBicho.esDeFin()) {
                enemigoTratado.setPosX(0);
                enemigoTratado.setPosY(0);
            }
            if (casillaDelBicho.esDeInicio()) {
                enemigoTratado.asignarMoveTarget(casillaDelBicho.getRandomNextX(), casillaDelBicho.getRandomNextY());
            }
            if (casillaDelBicho.esDeDireccionamiento() && (casillaDelBicho.getPosX() * 100 - enemigoTratado.getX()) * (casillaDelBicho.getPosX() * 100 -enemigoTratado.getX()) < 5) {
                enemigoTratado.asignarMoveTarget(casillaDelBicho.getRandomNextX(),casillaDelBicho.getRandomNextY());
            }
            for (int j = 0; j < turrets.size(); ++j) {
                Turret torretaTratada = turrets.get(j);
                if (!torretaTratada.estaProvocada()) {
                    if ((torretaTratada.getX()-lastX)*(torretaTratada.getX()-lastX)+(torretaTratada.getY()-lastY)*(torretaTratada.getY()-lastY) < Turret.maxDistanceAttack*Turret.maxDistanceAttack) {
                        torretaTratada.provocar(enemigoTratado);
                    }
                }
            }
            /*
            if (lastX >= 0.9 * canvasWidth && lastY >= 0.9 * canvasHeight) {
                enemigoTratado.asignarMoveTarget(0, 0);
            } else if (lastX <= 0.1 * canvasWidth && lastY <= 0.1 * canvasHeight) {
                enemigoTratado.asignarMoveTarget(canvasWidth, canvasHeight);
            }
            */
        }
    }

    private void actualizarEstadoTorretas(long milisegundos) {
        for (int i = 0; i < turrets.size(); i++) {
            Turret torretaTratada = turrets.get(i);
            torretaTratada.logic(milisegundos);
            if (torretaTratada.readyToFire()) {
                Enemy enemy = torretaTratada.dispara();
                bullets.add(new Bullet(torretaTratada.getX(), torretaTratada.getY(), 0.40f, enemy, 0.015f * Math.max(canvasWidth, canvasHeight)));
            }
        }
    }

    private void actualizarEstadoDisparos(long milisegundos) {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).logic(milisegundos);
        }
    }

    public void createNewNextTurret(int x, int y) {
        x = (int) ((x - offsetX) / factorDeEscalado);
        y = (int) ((y - offsetY) / factorDeEscalado);
        if (x > 0 && x < canvasWidth && y > 0 && y < canvasHeight) {
            nextTurretBuilded = false;
            nextTurret = new Turret(x, y, 0.02f * Math.max(canvasWidth, canvasHeight));
        }
    }

    public void setNextTurret(int x, int y) {
        x = (int) ((x - offsetX) / factorDeEscalado);
        y = (int) ((y - offsetY) / factorDeEscalado);
        if (x > 0 && x < canvasWidth && y > 0 && y < canvasHeight) {
            if (nextTurretBuilded) {
                nextTurretBuilded = false;
                nextTurret = new Turret(x, y, 0.02f * Math.max(canvasWidth, canvasHeight));
            } else {
                nextTurret.setX(x);
                nextTurret.setY(y);
            }
        }
    }

    public void buildTurret(int x, int y) {
        x = (int) ((x - offsetX) / factorDeEscalado);
        y = (int) ((y - offsetY) / factorDeEscalado);
        if (x > 0 && x < canvasWidth && y > 0 && y < canvasHeight) {
            nextTurretBuilded = true;
            turrets.add(new Turret(x, y, nextTurret.getRadius()));
        } else {
            nextTurretBuilded = true;
            turrets.add(nextTurret);
        }
    }

}
