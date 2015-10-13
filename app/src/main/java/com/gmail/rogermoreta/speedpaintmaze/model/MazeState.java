package com.gmail.rogermoreta.speedpaintmaze.model;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class MazeState implements Serializable {

    public static final int defaultColor = Color.GRAY;

    private transient MazeState instance;

    private int[][] colorMap;
    private int[][] colorRestrictiveMap;
    private int[][] colorChangeMap;
    private boolean[][] wallMap;
    private boolean[][] visitedMap;
    private int currentColor;
    private int currentX;
    private int currentY;
    private int mazeSize;
    private ArrayList<Pair> endPoints;
    private ArrayList<Pair> posibleNextPositions;

    public MazeState(MazeConstructor mazeConstructor) {
        try {
            mazeConstructor.generateMazeStartingAt(0, 0);
            mazeSize = mazeConstructor.getSize();
            colorMap = new int[mazeSize][mazeSize];
            colorRestrictiveMap = new int[mazeSize][mazeSize];
            colorChangeMap = new int[mazeSize][mazeSize];
            wallMap = new boolean[mazeSize][mazeSize];
            visitedMap = new boolean[mazeSize][mazeSize];
            endPoints = new ArrayList<>();
            posibleNextPositions = new ArrayList<>();
            currentX = 1;
            currentY = 0;
            currentColor = defaultColor;
            final boolean[][] mazeWalls = mazeConstructor.getMatrixWalls();
            for (int i = 0; i < mazeSize; i++) {
                for (int j = 0; j < mazeSize; j++) {
                    wallMap[i][j] = mazeWalls[i][j];
                    visitedMap[i][j] = mazeWalls[i][j];
                    colorChangeMap[i][j] = defaultColor;
                    if (mazeWalls[i][j]) {
                        colorMap[i][j] = Color.BLACK;
                        colorRestrictiveMap[i][j] = Color.BLACK;
                    } else {
                        colorMap[i][j] = Color.WHITE;
                        colorRestrictiveMap[i][j] = Color.WHITE;
                    }
                }
            }
            int i = 0;
            for (Pair p : MazeConstructor.getEndPoints()) {
                endPoints.add(new Pair(p.first, p.second));
                switch (i) {
                    case 0:
                        colorChangeMap[p.first][p.second] = Color.GREEN;
                        break;
                    case 1:
                        colorChangeMap[p.first][p.second] = Color.YELLOW;
                        break;
                    case 2:
                        colorChangeMap[p.first][p.second] = Color.BLUE;
                        break;
                    default:
                        colorChangeMap[p.first][p.second] = Color.RED;
                }
                i++;
            }
            posibleNextPositions.add(new Pair(1, 1));
        } catch (Exception e) {
            Log.d("MazeState", "Exception en creadora:" + e);
        }
    }

    @SuppressWarnings("unused")
    public int getColorPosition(int i, int j) {
        return colorChangeMap[i][j];
    }

    public boolean isAnEndPoint(int x, int y) {
        for (Pair p : endPoints) {
            if (p.first == x && p.second == y) return true;
        }
        return false;
    }

    private void setState(MazeState mazeState) {
        try {
            mazeSize = mazeState.mazeSize;
            colorMap = new int[mazeSize][mazeSize];
            colorRestrictiveMap = new int[mazeSize][mazeSize];
            colorChangeMap = new int[mazeSize][mazeSize];
            wallMap = new boolean[mazeSize][mazeSize];
            visitedMap = new boolean[mazeSize][mazeSize];
            endPoints = new ArrayList<>();
            posibleNextPositions = new ArrayList<>();
            currentX = mazeState.currentX;
            currentY = mazeState.currentY;
            currentColor = mazeState.currentColor;
            for (int i = 0; i < mazeSize; i++) {
                for (int j = 0; j < mazeSize; j++) {
                    wallMap[i][j] = mazeState.wallMap[i][j];
                    visitedMap[i][j] = mazeState.visitedMap[i][j];
                    colorMap[i][j] = mazeState.colorMap[i][j];
                    colorRestrictiveMap[i][j] = mazeState.colorRestrictiveMap[i][j];
                    colorChangeMap[i][j] = mazeState.colorChangeMap[i][j];
                }
            }
            for (Pair p : mazeState.endPoints) {
                endPoints.add(new Pair(p.first, p.second));
            }
            for (Pair p : mazeState.posibleNextPositions) {
                posibleNextPositions.add(new Pair(p.first, p.second));
            }
        } catch (Exception e) {
            Log.d("MazeState", "Exception en creadora:" + e);
        }
    }

    public ArrayList<Pair> getPosibleNextPositions() {
        return posibleNextPositions;
    }

    public int[][] getColorMap() {
        return colorMap;
    }

    public void moveToPosible(int index) {
        currentX = posibleNextPositions.get(index).first;
        currentY = posibleNextPositions.get(index).second;
        if (colorChangeMap[currentX][currentY] != defaultColor) {
            currentColor = colorChangeMap[currentX][currentY];
        }
        colorMap[currentX][currentY] = currentColor;
        int endPointIndex = locationOfTheEndPoint();
        if (endPointIndex > -1) {
            endPoints.remove(endPointIndex);
            updatePosibleNextPositions(true);
        } else {
            updatePosibleNextPositions(false);
        }
        visitedMap[currentX][currentY] = true;
    }

    private void updatePosibleNextPositions(boolean overAnEndPoint) {
        posibleNextPositions.clear();
        if (overAnEndPoint) {
            for (Pair p : endPoints) {
                posibleNextPositions.add(new Pair(p.first, p.second));
            }
        }
        if (0 <= currentX - 1 && !wallMap[currentX - 1][currentY] && !visitedMap[currentX - 1][currentY]) {
            posibleNextPositions.add(new Pair(currentX - 1, currentY));
        }
        if (0 <= currentY - 1 && !wallMap[currentX][currentY - 1] && !visitedMap[currentX][currentY - 1]) {
            posibleNextPositions.add(new Pair(currentX, currentY - 1));
        }
        if (currentX + 1 < mazeSize && !wallMap[currentX + 1][currentY] && !visitedMap[currentX + 1][currentY]) {
            posibleNextPositions.add(new Pair(currentX + 1, currentY));
        }
        if (currentY + 1 < mazeSize && !wallMap[currentX][currentY + 1] && !visitedMap[currentX][currentY + 1]) {
            posibleNextPositions.add(new Pair(currentX, currentY + 1));
        }
    }

    public int locationOfTheEndPoint() {
        int i = 0;
        for (Pair p : endPoints) {
            if (p.first == currentX && p.second == currentY) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @SuppressWarnings("unused")
    public void saveState(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput("mazeState.dat", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(this);
            os.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public MazeState loadState(Context context) {
        try {
            FileInputStream fis = context.openFileInput("mazeState.dat");
            ObjectInputStream is = new ObjectInputStream(fis);
            instance = (MazeState) is.readObject();
            is.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        setState(instance);
        return instance;
    }

    public int getSize() {
        return mazeSize;
    }

    public boolean[][] getWalls() {
        return wallMap;
    }

    public int getCurrentColor() {
        return currentColor;
    }
}
