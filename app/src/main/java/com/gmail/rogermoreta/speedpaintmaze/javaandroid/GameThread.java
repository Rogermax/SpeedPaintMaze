package com.gmail.rogermoreta.speedpaintmaze.javaandroid;

import android.util.Log;

import com.gmail.rogermoreta.speedpaintmaze.controller.Controller;

public class GameThread extends Thread {
    private Controller controller;
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

    /*@Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        while (running) {
            startTime = System.currentTimeMillis();
            synchronized (controller) {
                controller.timeToDoThings();
            }
            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0) {
                    sleep(sleepTime);
                } else {
                    sleep(10);
                }
            } catch (Exception e) {
            }
        }
    }*/

    // desired fps
    private final static int 	MAX_FPS = 50;
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
        long lastTimeFPSUpdated = System.currentTimeMillis();
        sleepTime = 0;

        while (running) {
            //canvas = null;
            // try locking the canvas for exclusive pixel editing
            // in the surface
                //canvas = this.surfaceHolder.lockCanvas();
            synchronized (controller) {
                beginTime = System.currentTimeMillis();
                framesSkipped = 0;	// resetting the frames skipped
                // update game state
                controller.update();
                controller.render();
                //this.gamePanel.update();
                // render state to the screen
                // draws the canvas on the panel
                //this.gamePanel.render(canvas);
                // calculate how long did the cycle take
                timeDiff = System.currentTimeMillis() - beginTime;
                /*if (System.currentTimeMillis() - lastTimeFPSUpdated > 500) {
                    Log.d("FPS:", (1000d / timeDiff)+"");
                    lastTimeFPSUpdated = System.currentTimeMillis();
                }*/
                // calculate sleep time
                sleepTime = (int)(FRAME_PERIOD - timeDiff);

                if (sleepTime > 0) {
                    // if sleepTime > 0 we're OK
                    try {
                        // send the thread to sleep for a short period
                        // very useful for battery saving
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {}
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