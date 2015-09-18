package com.gmail.rogermoreta.speedpaintmaze.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gmail.rogermoreta.speedpaintmaze.R;

public class LogrosView extends SurfaceView implements SurfaceHolder.Callback {

	SharedPreferences sharedPref;

	public LogrosView(Context context) {
		super(context);
	}

	public LogrosView(Context context, AttributeSet attributeSet) {
	    super(context, attributeSet);
	    getHolder().addCallback(this);
		setFocusable(true);
		sharedPref = getContext()
				.getSharedPreferences(
						getContext().getString(R.string.sharedPoints),
						Context.MODE_PRIVATE);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// nothing here
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {

	}
	
	@Override
	public boolean onTouchEvent(@NonNull MotionEvent event) {
		// int x = (int) event.getX();
		// int y = (int) event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:

			break;
		}

		return true;
	}

}
