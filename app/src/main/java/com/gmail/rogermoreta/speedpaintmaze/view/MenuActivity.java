package com.gmail.rogermoreta.speedpaintmaze.view;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;

import com.gmail.rogermoreta.speedpaintmaze.R;
import com.gmail.rogermoreta.speedpaintmaze.controller.ManagedActivity;

public class MenuActivity extends ManagedActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

        getGameHelper().setMaxAutoSignInAttempts(0);
		super.onCreate(savedInstanceState);

	    setContentView(R.layout.menu_view);
	    findViewById(R.id.sign_in_button).setOnClickListener(this);

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		
		((MenuView) findViewById(R.id.menu_view)).Init(this, getApiClient(), size.x, size.y);
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

}