package com.gmail.rogermoreta.speedpaintmaze.model;

import android.util.Log;

import com.gmail.rogermoreta.speedpaintmaze.enums.TipoCasilla;
import com.gmail.rogermoreta.speedpaintmaze.enums.TipoDisparo;

import java.util.ArrayList;

public class BurbujitaMap {

    //private float offsetY;
    //private float offsetX;
    //private float factorDeEscalado;
    //private SurfaceHolder surfaceHolder;
    private ArrayList<Turret> turrets;
    private ArrayList<Bullet> bullets;
    private ArrayList<Enemy> enemies;
    private ArrayList<Coin> coins;
    public Casilla[][] mapaPosicionesCasillas;
    //private Paint pincell;
    private int mapHeight;
    private int mapWidth;
    private int numeroCasillasX;
    private int numeroCasillasY;
    private Turret nextTurret;
    private int lastX;
    private int lastY;
    //private boolean nextTurretBuilded;
    private ArrayList<Casilla> mapaEnCasillas;
    //private Interface interfaceInstance;
    private int lastSelectedCasilla;
    public static final char[] codifiedMap;
    private long money;
    private int lifes;
    private int indexInici;


    static {
        codifiedMap = ("8x13" +
                "Ir7******db2.F..." +
                ".......*.*..." +
                "db3******dl7.*..." +
                "*........*..." +
                "*........*..." +
                "dr9********dt5..." +
                "............." +
                ".............").toCharArray();
    }

    /*static {
        codifiedMap = ("3x5" +
                "Ir4***db2" +
                "F...*" +
                "dt1***dl4").toCharArray();
    }*/


    public BurbujitaMap() throws Exception {
        //this.surfaceHolder = surfaceHolder;
        money = 300;
        lifes = 3;
        lastX = -1;
        lastY = -1;
        lastSelectedCasilla = -1;
        turrets = new ArrayList<>();
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        coins = new ArrayList<>();
        //Canvas canvas = this.surfaceHolder.lockCanvas();
        //reajustarTamaño(canvas);
        indexInici = leerMapa();
        Casilla inici = mapaEnCasillas.get(indexInici);
        Enemy enemy = new BaseMonster();
        enemy.init(inici.getPosX()*100f, inici.getPosY()*100f, 20f, 5f);
        enemies.add(enemy);
        enemy = new BaseMonster();
        enemy.init(inici.getPosX()*100f, inici.getPosY()*100f, 40f, 4f);
        enemies.add(enemy);
        enemy = new BaseMonster();
        enemy.init(inici.getPosX()*100f, inici.getPosY()*100f, 60f, 3f);
        enemies.add(enemy);
        enemy = new BaseMonster();
        enemy.init(inici.getPosX()*100f, inici.getPosY()*100f, 80f, 2f);
        enemies.add(enemy);
        enemy = new BaseMonster();
        enemy.init(inici.getPosX()*100f, inici.getPosY()*100f, 100f, 1f);
        enemies.add(enemy);
        //pincell = new Paint();
        //pincell.setARGB(175, 0, 0, 0);
        //this.surfaceHolder.unlockCanvasAndPost(canvas);
    }

    private int leerMapa() throws Exception {
        int filas = 0;
        int longFilas = 0; //columnas
        int i = 0;
        boolean trobat = false;
        for (; i < codifiedMap.length && !trobat; i++) {
            if (codifiedMap[i] < 48 || codifiedMap[i] > 57) {
                trobat = true;
            } else {
                filas = filas * 10 + (codifiedMap[i] - 48);
            }
        }
        trobat = false;
        for (; i < codifiedMap.length && !trobat; i++) {
            if (codifiedMap[i] < 48 || codifiedMap[i] > 57) {
                trobat = true;
            } else {
                longFilas = longFilas * 10 + (codifiedMap[i] - 48);
            }
        }
        mapaEnCasillas = new ArrayList<>(longFilas * filas);
        if (filas > longFilas) {
            throw new Exception("Mapa invalido, mas columnas que filas.");
        }
        else {
            mapWidth = longFilas * 100;
            mapHeight = filas * 100;
            numeroCasillasX = longFilas;
            numeroCasillasY = filas;
            mapaPosicionesCasillas = new Casilla[filas][longFilas];
            return leerMapaCodificado(i);
        }


        //pincell.setTextAlign(Paint.Align.CENTER);
        //pincell.setTextSize(Math.min(mapWidth, mapHeight) / 15);

    }

    private int leerMapaCodificado(int i) {
        int longFilas = numeroCasillasX;
        int filas = numeroCasillasY;
        boolean trobat = false;
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
                            trobat2 = false;
                            for (; i < codifiedMap.length && !trobat2; i++) {
                                if (codifiedMap[i] < 48 || codifiedMap[i] > 57) {
                                    trobat2 = true;
                                } else {
                                    numSaltos = numSaltos * 10 + (codifiedMap[i] - 48);
                                }
                            }
                            i--;
                            casilla.setNextPositions(casillaX, casillaY - numSaltos);
                            break;
                        case 'r':
                            i++;
                            trobat2 = false;
                            for (; i < codifiedMap.length && !trobat2; i++) {
                                if (codifiedMap[i] < 48 || codifiedMap[i] > 57) {
                                    trobat2 = true;
                                } else {
                                    numSaltos = numSaltos * 10 + (codifiedMap[i] - 48);
                                }
                            }
                            i--;
                            casilla.setNextPositions(casillaX + numSaltos, casillaY);
                            break;
                        case 'l':
                            i++;
                            trobat2 = false;
                            for (; i < codifiedMap.length && !trobat2; i++) {
                                if (codifiedMap[i] < 48 || codifiedMap[i] > 57) {
                                    trobat2 = true;
                                } else {
                                    numSaltos = numSaltos * 10 + (codifiedMap[i] - 48);
                                }
                            }
                            i--;
                            casilla.setNextPositions(casillaX - numSaltos, casillaY);
                            break;
                        case 'b':
                            i++;
                            trobat2 = false;
                            for (; i < codifiedMap.length && !trobat2; i++) {
                                if (codifiedMap[i] < 48 || codifiedMap[i] > 57) {
                                    trobat2 = true;
                                } else {
                                    numSaltos = numSaltos * 10 + (codifiedMap[i] - 48);
                                }
                            }
                            i--;
                            casilla.setNextPositions(casillaX, casillaY + numSaltos);
                    }
                    i--;
                    mapaEnCasillas.add(casilla);
                    indexInici = casillaX+casillaY*longFilas;
                    mapaPosicionesCasillas[casillaY][casillaX] = casilla;
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
                    casilla = new Casilla(TipoCasilla.CAMINO, casillaX, casillaY);
                    mapaEnCasillas.add(casilla);
                    mapaPosicionesCasillas[casillaY][casillaX] = casilla;
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
                            trobat2 = false;
                            for (; i < codifiedMap.length && !trobat2; i++) {
                                if (codifiedMap[i] < 48 || codifiedMap[i] > 57) {
                                    trobat2 = true;
                                } else {
                                    numSaltos = numSaltos * 10 + (codifiedMap[i] - 48);
                                }
                            }
                            i--;
                            if (i+1 < codifiedMap.length && i/longFilas == (i+1)/longFilas && codifiedMap[i+1]!='.') {
                                casilla.setGiro(3);
                            }
                            else {
                                casilla.setGiro(4);
                            }
                            casilla.setNextPositions(casillaX, casillaY - numSaltos);
                            break;
                        case 'r':
                            i++;
                            trobat2 = false;
                            for (; i < codifiedMap.length && !trobat2; i++) {
                                if (codifiedMap[i] < 48 || codifiedMap[i] > 57) {
                                    trobat2 = true;
                                } else {
                                    numSaltos = numSaltos * 10 + (codifiedMap[i] - 48);
                                }
                            }
                            i--;
                            if (i+longFilas < codifiedMap.length && codifiedMap[i+longFilas]!='.') {
                                casilla.setGiro(2);
                            }
                            else {
                                casilla.setGiro(3);
                            }
                            casilla.setNextPositions(casillaX + numSaltos, casillaY);
                            break;
                        case 'l':
                            i++;
                            trobat2 = false;
                            for (; i < codifiedMap.length && !trobat2; i++) {
                                if (codifiedMap[i] < 48 || codifiedMap[i] > 57) {
                                    trobat2 = true;
                                } else {
                                    numSaltos = numSaltos * 10 + (codifiedMap[i] - 48);
                                }
                            }
                            i--;
                            if (i+longFilas < codifiedMap.length && codifiedMap[i+longFilas]!='.') {
                                casilla.setGiro(1);
                            }
                            else {
                                casilla.setGiro(4);
                            }
                            casilla.setNextPositions(casillaX - numSaltos, casillaY);
                            break;
                        case 'b':
                            i++;
                            trobat2 = false;
                            for (; i < codifiedMap.length && !trobat2; i++) {
                                if (codifiedMap[i] < 48 || codifiedMap[i] > 57) {
                                    trobat2 = true;
                                } else {
                                    numSaltos = numSaltos * 10 + (codifiedMap[i] - 48);
                                }
                            }
                            i--;
                            if (i+1 < codifiedMap.length && i/longFilas == (i+1)/longFilas && codifiedMap[i+1]!='.') {
                                casilla.setGiro(1);
                            }
                            else {
                                if (i-1 < codifiedMap.length && i/longFilas == (i-1)/longFilas && codifiedMap[i-1]!='.') {
                                    casilla.setGiro(1);
                                }
                                else {
                                    casilla.setGiro(2);
                                }
                            }
                            casilla.setNextPositions(casillaX, casillaY + numSaltos);
                    }
                    i--;
                    mapaEnCasillas.add(casilla);
                    mapaPosicionesCasillas[casillaY][casillaX] = casilla;
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
                    casilla = new Casilla(TipoCasilla.CESPED, casillaX, casillaY);
                    mapaEnCasillas.add(casilla);
                    mapaPosicionesCasillas[casillaY][casillaX] = casilla;
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
                    mapaPosicionesCasillas[casillaY][casillaX] = casilla;
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
        return indexInici;
    }

    /*public void reajustarTamaño(Canvas canvas) {
        int screenWidth = canvas.getWidth();
        int screenHeight = canvas.getHeight();
        interfaceInstance = new Interface(screenWidth, screenHeight, 8);
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
    }*/

    /*public void draw(float fps, float ups) {
        if (this.surfaceHolder != null) {
            Canvas canvas = this.surfaceHolder.lockCanvas();
            canvas.drawARGB(255, 0, 0, 0);
            canvas.translate(offsetX, offsetY);
            canvas.scale(factorDeEscalado, factorDeEscalado);
            pincell.setARGB(255, 181, 230, 29);
            canvas.drawRect(0, 0, canvasWidth, canvasHeight, pincell);
            canvas = drawMap(canvas);
            canvas = drawTurrets(canvas);
            canvas = drawEnemies(canvas);
            canvas = drawBullets(canvas);
            //canvas = drawTurretsCeils(canvas);
            if (!nextTurretBuilded && nextTurret != null) {
                try {
                    canvas = burbujitaController.drawObjectIntoCanvas(canvas, nextTurret);
                } catch (Exception ignored) {

                }
            }
            pincell.setARGB(175, 0, 0, 0);
            canvas.drawText("FPS: " + Math.round(fps), canvasWidth / 2, 6 * canvasHeight / 8, pincell);
            canvas.drawText("UPS: " + Math.round(ups), canvasWidth / 2, 7 * canvasHeight / 8, pincell);


            canvas.scale(1 / factorDeEscalado, 1 / factorDeEscalado);
            canvas.translate(-offsetX, -offsetY);
            canvas = drawInterface(canvas);
            this.surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private Canvas drawInterface(Canvas canvas) {
        canvas = burbujitaController.drawObjectIntoCanvas(canvas, interfaceInstance);
        return canvas;
    }

    */

    /*private Canvas drawTurretsCeils(Canvas canvas) {
        for (int i = 0; i < turrets.size(); i++) {
            canvas = burbujitaController.drawObjectIntoCanvas(canvas, turrets.get(i));
        }
        return canvas;
    }*/

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
        actualizarEstadoMonedas(milisegundos);
        //actualizarEstadoInterface(milisegundos);
    }

    private void actualizarEstadoMonedas(long milisegundos) {
        for (int i =  0; i < coins.size(); i++) {
            coins.get(i).logic(milisegundos);
            if (coins.get(i).mustDisappear()) {
                coins.remove(i);
                i--;
            }
        }
    }

    /*private void actualizarEstadoInterface(long milisegundos) {
        interfaceInstance.logic(milisegundos);
    }*/

    private void calcularColisiones() {
        for (int i = 0; i < bullets.size(); ++i) {
            Bullet disparo = bullets.get(i);
            if (!disparo.existe()) {
                bullets.remove(i);
                i--;
            } else {
                if (!disparo.estaExplotando()) {
                    boolean trobat = false;
                    for (int j = 0; j < enemies.size() && !trobat; ++j) {
                        Enemy enemyActual = enemies.get(j);
                        if (disparo.getObjectiveEntity().equals(enemyActual)) {
                            trobat = true;
                            if (colisionaConEnemigo(disparo, enemies.get(j))) {
                                disparo.explota();
                                Enemy enemigoTocado = enemies.get(j);
                                switch (disparo.getType()) {
                                    case FUEGO:
                                        enemigoTocado.empezarAQuemar(2000l,3*1f/2000f*100f); //Simula durante 2 segundos 3 ostias de 1 de daño
                                        break;
                                    case VENENO:
                                        enemigoTocado.empezarAPlantar(200l);
                                        break;
                                    case HIELO:
                                        enemigoTocado.empezarARelantizar(0.33f);
                                        break;
                                    default:
                                }
                                enemigoTocado.receiveDamage(1);
                            }
                        }
                    }
                    if (!trobat) disparo.explota();
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

    private boolean colisionaConEnemigo(Bullet disparo, Enemy enemy) {
        return (disparo.getPosX() - (enemy.getX())) * (disparo.getPosX() - enemy.getX()) + (disparo.getPosY() - enemy.getY()) * (disparo.getPosY() - enemy.getY()) < disparo.getRadius() * disparo.getRadius();
    }

    private void actualizarEstadoBichos(long milisegundos) {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemigoTratado = enemies.get(i);
            lastX = (int) enemigoTratado.getX();
            lastY = (int) enemigoTratado.getY();
            int casillaX = (int) (enemigoTratado.getX() / 100);
            int casillaY = (int) (enemigoTratado.getY() / 100);
            int indexOfCasilla = casillaX + casillaY * numeroCasillasX;
            Casilla casillaDelBicho = mapaEnCasillas.get(indexOfCasilla);
            if (casillaDelBicho.esDeFin()) {
                lifes--;
                if (lifes == 0) lifes = 3;
                enemigoTratado.setPosX(mapaEnCasillas.get(indexInici).getPosX());
                enemigoTratado.setPosY(mapaEnCasillas.get(indexInici).getPosX());
            }
            if (casillaDelBicho.esDeDireccionamiento() && (casillaDelBicho.getPosX() * 100 - enemigoTratado.getX()) * (casillaDelBicho.getPosX() * 100 - enemigoTratado.getX()) < 26) {
                enemigoTratado.asignarMoveTarget(casillaDelBicho.getRandomNextX(), casillaDelBicho.getRandomNextY());
            }
            enemigoTratado.logic(milisegundos);
            for (int j = 0; j < turrets.size(); ++j) {
                Turret torretaTratada = turrets.get(j);
                if (!torretaTratada.estaProvocada()) {
                    if ((torretaTratada.getX() - lastX) * (torretaTratada.getX() - lastX) + (torretaTratada.getY() - lastY) * (torretaTratada.getY() - lastY) < Turret.maxDistanceAttack * Turret.maxDistanceAttack) {
                        torretaTratada.provocar(enemigoTratado);
                    }
                }
            }
            if (enemigoTratado.getDyingState() > enemigoTratado.m_timeDying) {
                money += 50;
                coins.add(new Coin(50,enemigoTratado.getX(),enemigoTratado.getY()));
                enemies.remove(i);
                i--;
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
                Entity enemy = torretaTratada.dispara();
                if (enemy.isAlive()) {
                    bullets.add(new Bullet(torretaTratada.getPosition(), 0.40f, enemy, 0.015f * Math.max(mapWidth, mapHeight), torretaTratada.getTipo()));
                }
            }
        }
    }

    private void actualizarEstadoDisparos(long milisegundos) {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).logic(milisegundos);
        }
    }

    /*public void setNextTurret(int x, int y) {
        if (nextTurretBuilded || nextTurret == null) {
            createNewNextTurret(x,y);
        }
        else {
            x = (int) ((x - offsetX) / factorDeEscalado);
            y = (int) ((y - offsetY) / factorDeEscalado);
            if (x > 0 && x < canvasWidth && y > 0 && y < canvasHeight) {
                int casillaX = x / 100;
                int casillaY = y / 100;
                int indexOfCasilla = casillaX + casillaY * numeroCasillasX;
                Casilla casilla = mapaEnCasillas.get(indexOfCasilla);
                if (casilla.getTipoCasilla() == TipoCasilla.CESPED && !casilla.tieneTorreta()) {
                    nextTurret.setX(casillaX * 100);
                    nextTurret.setY(casillaY * 100);
                } else {
                    nextTurretBuilded = true;
                }
            }
        }
    }*/

    public void buildTurret(TipoDisparo tipo, int price) {
        if (lastSelectedCasilla > -1 && !mapaEnCasillas.get(lastSelectedCasilla).tieneTorreta() && money >= price) {
            Casilla cas = mapaEnCasillas.get(lastSelectedCasilla);
            //int selectedOption = burbujitaController.getSelectedButton();
            if (tipo != TipoDisparo.NULL) {
               nextTurret = new Turret(Vector2D.multipicaPorEscalar(cas.getPosition(),100), tipo, price);
            }
            if (nextTurret != null) {
                mapaEnCasillas.get(lastSelectedCasilla).ponTorreta();
                turrets.add(nextTurret);
                money -= nextTurret.getPrice();
            }
            //interfaceInstance.desSeleccionar();
            //hideConstructorInterface();
            cas.deseleccionar();
            lastSelectedCasilla = -1;
        }
    }

    /*public void showInterface(int x, int y) {
        if (canBuildTurretOn(x,y)) {
            showConstructionInterface();
        }
    }*/

    /*
    private void showConstructionInterface() {
        interfaceInstance.startShowing();
    }

    private void hideConstructorInterface() {
        interfaceInstance.startRetracting();
    }

    public boolean insterfaceIsActive(float x, float y) {
        return interfaceInstance.isActive() && interfaceInstance.isClickInside(x, y);
    }*/

    public void highLight(int x, int y) {
        if (canBuildTurretOn(x, y)) {
            //x = (int) ((x - offsetX) / factorDeEscalado);
            //y = (int) ((y - offsetY) / factorDeEscalado);
            int casillaX = x / 100;
            int casillaY = y / 100;
            int indexOfCasilla = casillaX + casillaY * numeroCasillasX;
            Casilla casilla = mapaEnCasillas.get(indexOfCasilla);
            if (lastSelectedCasilla > -1) {
                mapaEnCasillas.get(lastSelectedCasilla).deseleccionar();
            }
            lastSelectedCasilla = indexOfCasilla;
            casilla.seleccionar();
        }
    }

    public void unHighLight() {
        if (lastSelectedCasilla > -1) {
            mapaEnCasillas.get(lastSelectedCasilla).deseleccionar();
        }
    }

    /*
    public void highLightInterfaceButtons(float x, float y) {
        interfaceInstance.highLight(x, y);
    }*/

    public boolean canBuildTurretOn(int x, int y) {
        //x = (int) ((x - offsetX) / factorDeEscalado);
        //y = (int) ((y - offsetY) / factorDeEscalado);
        if (x > 0 && x < mapWidth && y > 0 && y < mapHeight) {
            int casillaX = x / 100;
            int casillaY = y / 100;
            int indexOfCasilla = casillaX + casillaY * numeroCasillasX;
            Casilla casilla = mapaEnCasillas.get(indexOfCasilla);
            if (casilla.getTipoCasilla() == TipoCasilla.CESPED && !casilla.tieneTorreta()) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Casilla> getCasillaMap() {
        return mapaEnCasillas;
    }

    public int getMapWidth() {
        return numeroCasillasX;
    }

    public int getMapHeight() {
        return numeroCasillasY;
    }

    public ArrayList<Turret> getTurrets() {
        return turrets;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void buildPreTurret(TipoDisparo tipo, long price) {
        if (lastSelectedCasilla > -1 && !mapaEnCasillas.get(lastSelectedCasilla).tieneTorreta()) {
            Casilla cas = mapaEnCasillas.get(lastSelectedCasilla);
            //int selectedOption = burbujitaController.getSelectedButton();
            if (tipo != TipoDisparo.NULL) {
                nextTurret = new Turret(Vector2D.multipicaPorEscalar(cas.getPosition(),100), tipo, price);
            }
        }
    }

    public Turret getNextTurret() {
        return nextTurret;
    }

    public void destroyPreBuildTurret() {
        nextTurret = null;
    }

    public ArrayList<Coin> getCoins() {
        return coins;
    }

    public long getMoney() {
        return money;
    }

    public int getLifes() {
        return lifes;
    }
}
