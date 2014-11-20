package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	GameThread gameThread;
	
	public GameSurfaceView(Context context) {
		super(context);
		getHolder().addCallback(this);
		gameThread = new GameThread(getHolder(), this);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.e("ARTI", "created");
		gameThread.setRunnable(true);
		gameThread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		gameThread.setRunnable(false);

		while (retry) {

			try {

				gameThread.join();
				retry = false;

			} catch (InterruptedException ie) {

				// Try again and again and again
			}

			break;
		}

		gameThread = null;
	}

}
