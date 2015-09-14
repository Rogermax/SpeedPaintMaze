package com.gmail.rogermoreta.speedpaint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.example.games.basegameutils.BaseGameActivity;

public class MainActivity extends BaseGameActivity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.sign_in_points);
	    findViewById(R.id.sign_in_button).setOnClickListener(this);       
	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		Log.i("MainActivity", "SigInFailed");

	    findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		Log.i("MainActivity", "SigInSucced");
	    findViewById(R.id.sign_in_button).setVisibility(View.GONE);
		Intent mainIntent = new Intent().setClass(
				MainActivity.this, Menu.class);
		startActivity(mainIntent);
		finish();// Destruimos esta activity para prevenit que el
					// usuario retorne aqui presionando el boton Atras.
	}

	@Override
	public void onClick(View view) {
	    if (view.getId() == R.id.sign_in_button) {
	        // start the asynchronous sign in flow
	        beginUserInitiatedSignIn();
	    }
	}
}