package com.gmail.rogermoreta.speedpaintmaze.view;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;

import com.gmail.rogermoreta.speedpaintmaze.R;
import com.gmail.rogermoreta.speedpaintmaze.controller.MainManager;
import com.gmail.rogermoreta.speedpaintmaze.controller.ManagedActivity;

public class BurbujitaActivity extends ManagedActivity {

    SoundPool sp;
    public static boolean prepared = false;
    private int soundId;
    private static MainManager MM = MainManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getGameHelper().setMaxAutoSignInAttempts(0);
        super.onCreate(savedInstanceState); //Necesario siempre que haces override de onCreate
        setContentView(new BurbujitaView(this));
        //noinspection deprecation
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundId = sp.load(this, R.raw.bip, 1);
        sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                prepared = true;
            }
        });
        MM.setInstanceBurbujitaActivity(this);
    }

    @Override
    public void onPause(){
        super.onPause();
        MM.pauseBurbujita();
    }

    @Override
    public void onSignInFailed() {}

    @Override
    public void onSignInSucceeded() {}

    public void playSound() {
        if (prepared) {
            sp.play(soundId,1f,1f,1,0,1f);
        }
    }
}

