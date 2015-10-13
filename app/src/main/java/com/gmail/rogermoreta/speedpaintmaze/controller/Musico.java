package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.gmail.rogermoreta.speedpaintmaze.R;
import com.gmail.rogermoreta.speedpaintmaze.enums.Sound;

public class Musico {

    private final SoundPool sp;
    private final int soundId;
    private boolean prepared;

    public Musico(Context context) {
        //noinspection deprecation
        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundId = sp.load(context, R.raw.beep, 1);
        sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                prepared = true;
            }
        });
    }

    public void playSound(Sound sound) {
        if (prepared) {
            switch (sound) {
                case BEEP:
                    sp.play(soundId, 1f, 1f, 1, 0, 1f);
                    break;
                default:
                    Log.d("Musico", "playSound::fail->Sound not found: " + sound);
            }
        }
    }

}
