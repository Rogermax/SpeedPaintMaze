package com.gmail.rogermoreta.speedpaintmaze.model;

import android.os.SystemClock;

import java.util.ArrayList;
import java.util.Random;

public class MazeConstructor {

    private static ArrayList<Pair> endPoints;
    private static boolean[][] mazeRecursive;
    private static boolean[][] mazeWallsStructure;
    private static int mazeSizeEdge;
    private static Random r;
    private static int startI;
    private static int startJ;


    /**
     * @param difficulty: must be 1 or greater.
     */
    public MazeConstructor(int difficulty) throws Exception {
        if (0 < difficulty) {

            mazeSizeEdge = difficulty + 1;
            mazeRecursive = new boolean[mazeSizeEdge][mazeSizeEdge];
            endPoints = new ArrayList<>();
            for (int i = 0; i < mazeSizeEdge; i++) {
                for (int j = 0; j < mazeSizeEdge; j++) {
                    mazeRecursive[i][j] = false;
                }
            }
        } else {
            throw new Exception("Error en los parametros de entrada de la creadora MazeConstructor(int)");
        }
    }

    public static ArrayList<Pair> getEndPoints() {
        return endPoints;
    }

    /**
     * Genera un maze con entrada en i0, j0.
     *
     * @param i0 casilla i de comienzo
     * @param j0 casilla j de comienzo
     */
    public void generateMazeStartingAt(int i0, int j0) throws Exception {
        if (0 <= i0 && i0 < mazeSizeEdge && 0 <= j0 && j0 < mazeSizeEdge) {
            final int mazeSizeLogic = mazeSizeEdge * 2 + 1;
            r = new Random(SystemClock.uptimeMillis());
            mazeWallsStructure = new boolean[mazeSizeLogic][mazeSizeLogic];
            for (int i = 0; i < mazeSizeLogic; i++) {
                for (int j = 0; j < mazeSizeLogic; j++) {
                    mazeWallsStructure[i][j] = true;
                }
            }
            startI = i0;
            startJ = j0;
            generateMazeRecursive(i0, j0);
        } else {
            throw new Exception("Error en los parametros de entrada de generateMazeStartingAt(int,int)");
        }

    }

    @SuppressWarnings("unused")
    public static int getStartI() {
        return startI;
    }

    @SuppressWarnings("unused")
    public static int getStartJ() {
        return startJ;
    }

    /**
     * Cuando entra la posicion i0, j0 no esta marcada.Se marca y se va a la siguiente.
     *
     * @param i0 posicion i del analisis
     * @param j0 posicio j del anailisis
     */
    private void generateMazeRecursive(int i0, int j0) {
        final int random_choice = r.nextInt(24);
        mazeRecursive[i0][j0] = true;
        mazeWallsStructure[2 * i0 + 1][2 * j0 + 1] = false;
        boolean oneVisited = false;
        switch (random_choice) {
            case 0://
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }
                break;
            case 1://
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4                
                }
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }
                break;
            case 2://
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }
                break;
            case 3://
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4
                }
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }
                break;
            case 4://
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4
                }
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }
                break;
            case 5://
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4
                }
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }
                break;
            case 6://
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }
                break;
            case 7://
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4
                }
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }
                break;
            case 8://
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }
                break;
            case 9://
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4
                }
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }
                break;
            case 10://
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4
                }
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }
                break;
            case 11://
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4
                }
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }
                break;
            case 12:
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }
                break;
            case 13:
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4
                }
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }
                break;
            case 14:
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }
                break;
            case 15:
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4
                }
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }
                break;
            case 16:
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4
                }
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }
                break;
            case 17:
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4
                }
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }
                break;
            case 18:
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4
                }
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }
                break;
            case 19:
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4
                }
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }
                break;
            case 20:
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4
                }
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }
                break;
            case 21:
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4
                }
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }
                break;
            case 22:
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4
                }
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }
                break;
            case 23:
                if (j0 + 1 < mazeSizeEdge && !mazeRecursive[i0][j0 + 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 2] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 + 3] = false;
                    generateMazeRecursive(i0, j0 + 1); //4
                }
                if (i0 + 1 < mazeSizeEdge && !mazeRecursive[i0 + 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 2][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 + 3][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 + 1, j0); //3
                }
                if (0 <= j0 - 1 && !mazeRecursive[i0][j0 - 1]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0 + 1][2 * j0] = false;
                    mazeWallsStructure[2 * i0 + 1][2 * j0 - 1] = false;
                    generateMazeRecursive(i0, j0 - 1); //2
                }
                if (0 <= i0 - 1 && !mazeRecursive[i0 - 1][j0]) {
                    oneVisited = true;
                    mazeWallsStructure[2 * i0][2 * j0 + 1] = false;
                    mazeWallsStructure[2 * i0 - 1][2 * j0 + 1] = false;
                    generateMazeRecursive(i0 - 1, j0); //1
                }
                if (!oneVisited) {
                    endPoints.add(new Pair(2 * i0 + 1, 2 * j0 + 1));
                }

        }
    }

    public static boolean testRecursive(int fin) {
        return fin > 752 || testRecursive(fin + 1);
    }

    @SuppressWarnings("unused")
    public void printWalls() {
        final int mazeSizeLogic = mazeSizeEdge * 2 + 1;
        System.out.println("Laberinto:");
        for (int i = mazeSizeLogic - 1; i >= 0; i--) {
            for (int j = 0; j < mazeSizeLogic; j++) {
                if (mazeWallsStructure[i][j]) {
                    System.out.print("||");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.print("\n");
        }
        System.out.println(":FIN");
    }

    public boolean[][] getMatrixWalls() {
        return mazeWallsStructure;
    }

    public int getSize() {
        return mazeSizeEdge * 2 + 1;
    }
}
