package com.gmail.rogermoreta.speedpaintmaze.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.gmail.rogermoreta.speedpaintmaze.R;
import com.google.example.games.basegameutils.BaseGameActivity;

public class LogrosActivity extends BaseGameActivity implements OnClickListener{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// want fullscreen, we hide Activity's title and notification bar
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
	    setContentView(R.layout.sign_in_logros);
	    findViewById(R.id.sign_in_button).setOnClickListener(this);
	    //findViewById(R.id.button1).setOnClickListener(this); 
		//findViewById(R.id.button1).setEnabled(false);
	}
	
	@Override
	public void onSignInFailed() {
		Log.i("MainActivity", "SigInFailed");

		//findViewById(R.id.button1).setEnabled(false);
		
	    findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
	    
	}

	@Override
	public void onSignInSucceeded() {
		Log.i("MainActivity", "SigInSucced");

		//findViewById(R.id.button1).setEnabled(true);
		
	    findViewById(R.id.sign_in_button).setVisibility(View.GONE);


	}

	@Override
	public void onClick(View view) {
	    if (view.getId() == R.id.sign_in_button) {
	        // start the asynchronous sign in flow
	        beginUserInitiatedSignIn();
	    }
	}

}