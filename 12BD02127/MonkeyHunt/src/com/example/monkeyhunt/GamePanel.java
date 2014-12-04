package com.example.monkeyhunt;

import gif.decoder.GifRun;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

	private Game game;
	private GameLoopThread gameLoopThread;
	//SurfaceView v = (SurfaceView)findViewById(R.id.surfaceView1);
	
	// X coordinate of the touched position
    //private float mX;
 
    // Y Coordinate of the touched position
    //private float mY;
 
	
	public GamePanel(Context context) {
		

		super(context);

		// Focus must be on GamePanel so that events can be handled.

		this.setFocusable(true);

		// For intercepting events on the surface.

		this.getHolder().addCallback(this);

	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {

		// We can now safely start the game loop.

		startGame();

	}

	private void startGame() {

		game = new Game(getWidth(), getHeight(), getResources());
		gameLoopThread = new GameLoopThread(this.getHolder(), game);
		gameLoopThread.running = true;
		gameLoopThread.start();

	}

	public void surfaceDestroyed(SurfaceHolder holder) {

		gameLoopThread.running = false;

		// Shut down the game loop thread cleanly.

		boolean retry = true;
		while (retry) {

			try {
				gameLoopThread.join();
				retry = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		// This is for single-touch. For multi-touch use
		// MotionEventCompat.getActionMasked(event);

		int action = event.getAction();

		if (action == MotionEvent.ACTION_DOWN) {

			game.touchEvent_actionDown(event);

		}

		if (action == MotionEvent.ACTION_MOVE) {

			game.touchEvent_actionMove(event);

		}

		if (action == MotionEvent.ACTION_UP) {

			game.touchEvent_actionUp(event);

		}

		return true;

	}

	

}