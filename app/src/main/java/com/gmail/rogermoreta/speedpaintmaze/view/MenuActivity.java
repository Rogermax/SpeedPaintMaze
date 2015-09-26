package com.gmail.rogermoreta.speedpaintmaze.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;

import com.gmail.rogermoreta.speedpaintmaze.R;
import com.gmail.rogermoreta.speedpaintmaze.controller.ManagedActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MenuActivity extends ManagedActivity implements OnClickListener {


	boolean signIn;
	@Override
	public void onCreate(Bundle savedInstanceState) {

        getGameHelper().setMaxAutoSignInAttempts(0);
		super.onCreate(savedInstanceState);

		// want fullscreen, we hide Activity's title and notification bar
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);

		signIn = false;
	    setContentView(R.layout.menu_view);
	    findViewById(R.id.sign_in_button).setOnClickListener(this);

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		
		((MenuView) findViewById(R.id.menu_view)).Init(this, getApiClient(), size.x, size.y);
	}

    @Override
    public void changeActivityToIn(final Activity from, final Class activityClassToGo, long miliseconds) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(from, activityClassToGo);
                from.startActivity(mainIntent);
                from.finish();// Destruimos esta activity para prevenir que el usuario vuelva aqui apretando atras.
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, miliseconds);// Pasado los 3 segundos dispara/fija/registra la tarea
    }

    @Override
	public void onSignInFailed() {
	    findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
		signIn = false;
	}

	@Override
	public void onSignInSucceeded() {
	    findViewById(R.id.sign_in_button).setVisibility(View.GONE);
		signIn = true;
	}

	@Override
	public void onClick(View view) {
	    if (view.getId() == R.id.sign_in_button) {
	        beginUserInitiatedSignIn();
	    }
	    /*else if (view.getId() == R.id.button1) {
			SharedPreferences sharedPref = getSharedPreferences(
					getString(R.string.sharedPoints),
					Context.MODE_PRIVATE);
			int temp = sharedPref.getInt("puntos_normal_aux", 0);
			int best = sharedPref.getInt("puntos_normal_best", 0);
			SharedPreferences.Editor editor = sharedPref.edit();
			if (!sharedPref.getBoolean("puntos_normal_bool", true))
			{
				editor.putBoolean("puntos_normal_bool", 
						true);
				if (temp > best) {
					Log.i("PointsActivity", "Lo va a meter en google");
					editor.putInt("puntos_normal_best", 
							temp);
					Games.Leaderboards.submitScore(getApiClient(), getString(R.string.leaderboard_10_seconds_ranking), temp);
				}
			
				editor.commit();
			}
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), getString(R.string.leaderboard_10_seconds_ranking)), 1);
	        // show sign-in button, hide the sign-out button
	        ((TextView) findViewById(R.id.textView1)).setText("le has dado a ranking: "+Math.max(best, temp));
	    }*/
	}


}