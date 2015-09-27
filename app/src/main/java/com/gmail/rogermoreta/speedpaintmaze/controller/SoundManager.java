package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;

public class SoundManager {

    // originally from http://marblemice.blogspot.com/2010/04/generate-and-play-tone-in-android.html
    // and modified by Steve Pomeroy <steve@staticfree.info>
    private static final int duration = 1; // seconds
    private static final int sampleRate = 8000;
    private static final int numSamples = duration * sampleRate;
    private static final double sample[] = new double[numSamples];
    private static final double freqOfTone = 440; // hz
    private static final byte generatedSnd[] = new byte[2 * numSamples];
    private static SoundManager instance;
    private static Handler handler = new Handler();
    private static Thread thread;

    private SoundManager() {
        genTone();
    }

    public static SoundManager getInstance() {
        if (instance == null) instance = new SoundManager();
        return instance;
    }


    public void playSound() {
        // Use a new tread as this can take a while
        thread = new Thread(new Runnable() {
            public void run() {
                genTone();
                handler.post(new Runnable() {

                    public void run() {
                        playSoundPrivate();
                    }
                });
            }
        });
        thread.start();
    }

    private static void playSoundPrivate() {
        final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, generatedSnd.length, AudioTrack.MODE_STATIC);
        audioTrack.write(generatedSnd, 0, generatedSnd.length);
        audioTrack.play();
    }

    private static void genTone() {
        // fill out the array
        for (int i = 0; i < numSamples; ++i) {
            // sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone));
            sample[i] = Math.sin((2 * Math.PI - .001) * i / (sampleRate / freqOfTone));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        int ramp = numSamples / 20;

        for (int i = 0; i < ramp; i++) {
            // scale to maximum amplitude
            final short val = (short) ((sample[i] * 32767) * i / ramp);
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }

        for (int i = ramp; i < numSamples - ramp; i++) {
            // scale to maximum amplitude
            final short val = (short) ((sample[i] * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }

        for (int i = numSamples - ramp; i < numSamples; i++) {
            // scale to maximum amplitude
            final short val = (short) ((sample[i] * 32767) * (numSamples - i) / ramp);
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }
    }
}
