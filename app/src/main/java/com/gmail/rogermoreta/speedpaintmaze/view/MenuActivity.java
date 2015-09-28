package com.gmail.rogermoreta.speedpaintmaze.view;

import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;

import com.gmail.rogermoreta.speedpaintmaze.R;
import com.gmail.rogermoreta.speedpaintmaze.controller.MainManager;
import com.gmail.rogermoreta.speedpaintmaze.controller.ManagedActivity;

public class MenuActivity extends ManagedActivity implements OnClickListener {

    private static MediaPlayer mp;
    private static MainManager MM = MainManager.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

        getGameHelper().setMaxAutoSignInAttempts(0);
		super.onCreate(savedInstanceState);

	    setContentView(R.layout.menu_view);
        mp = MediaPlayer.create(this, R.raw.bip);
	    findViewById(R.id.sign_in_button).setOnClickListener(this);

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		
		((MenuView) findViewById(R.id.menu_view)).Init(this, getApiClient(), size.x, size.y);
        MM.setInstanceMenuActivity(this);
	}

    @Override
	public void onSignInFailed() {
	    findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
	}

	@Override
	public void onSignInSucceeded() {
	    findViewById(R.id.sign_in_button).setVisibility(View.GONE);
	}

	@Override
	public void onClick(View view) {
	    if (view.getId() == R.id.sign_in_button) {
	        beginUserInitiatedSignIn();
	    }
	}

    public boolean isSignedIn() {
        return super.isSignedIn();
    }

    public static void playSound() {
        if(mp.isPlaying())
        {
            mp.stop();
        }
        try {
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

}