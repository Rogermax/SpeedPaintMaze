package com.gmail.rogermoreta.speedpaintmaze.javaandroid;

import android.util.Log;

import com.gmail.rogermoreta.speedpaintmaze.controller.Controller;

public class GameThread extends Thread {
    private final Controller controller;
    private boolean running;

    public GameThread(Controller controller) {
        this.controller = controller;
        running = false;
    }

    public void encender() {
        running = true;
        start();
    }

    public void apagar() {
        running = false;
        interrupt();
    }

    // desired fps
    private final static int 	MAX_FPS = 30;
    // maximum number of frames to be skipped
    private final static int	MAX_FRAME_SKIPS = 5;
    // the frame period
    private final static int	FRAME_PERIOD = 1000 / MAX_FPS;

    @Override
    public void run() {
        //Canvas canvas;
        //Log.d(TAG, "Starting game loop");

        long beginTime;		// the time when the cycle begun
        long timeDiff;		// the time it took for the cycle to execute
        int sleepTime;		// ms to sleep (<0 if we're behind)
        int framesSkipped;	// number of frames being skipped

        long timeWastedInLogic = 0;
        long timeWastedinDrawing = 0;
        int i = 0;
        while (running) {
            //canvas = null;
            // try locking the canvas for exclusive pixel editing
            // in the surface
                //canvas = this.surfaceHolder.lockCanvas();
            synchronized (controller) {
                if (!controller.isPaused()) {
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;    // resetting the frames skipped
                    // update game state
                    i++;
                    controller.update();
                    /*if (i % 30 == 0) {
                        timeWastedInLogic = System.currentTimeMillis() - beginTime;
                        Log.d("GameThread", "LogicTime: " + timeWastedInLogic);
                    }*/
                    controller.render();
                    /*if (i % 30 == 0) {
                        timeWastedinDrawing = System.currentTimeMillis() - (beginTime + timeWastedInLogic);
                        Log.d("GameThread", "DrawTime: " + timeWastedinDrawing);
                    }*/
                    //this.gamePanel.update();
                    // render state to the screen
                    // draws the canvas on the panel
                    //this.gamePanel.render(canvas);
                    // calculate how long did the cycle take
                    timeDiff = System.currentTimeMillis() - beginTime;
                    // calculate sleep time
                    sleepTime = (int) (FRAME_PERIOD - timeDiff);

                    if (sleepTime > 0) {
                        if (i % 30 == 0) {
                            Log.d("GameThread", "sleepTime: " + sleepTime);
                        }
                        // if sleepTime > 0 we're OK
                        try {
                            // send the thread to sleep for a short period
                            // very useful for battery saving
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException ignored) {
                        }
                    }

                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                        // we need to catch up
                        // update without rendering
                        //this.gamePanel.update();
                        controller.update();
                        // add frame period to check if in next frame
                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                    }
                }
            }
        }
    }

}