package com.example.sis_mc_bissenbay;

import android.annotation.SuppressLint;
import android.graphics.Canvas;

@SuppressLint("WrongCall")
public class GameManager extends Thread {

	static final long FPS = 10;
	private GameView view;
	private boolean running = false;

	public GameManager(GameView view) {
		this.view = view;
	}
	
	public void setRunning(boolean run) {
		running = run;
	}
	
	public void run() {
		long ticksPS = 1000/FPS;
		long startTime, sleepTime;
		while(running) {
			Canvas c = null;
			startTime = System.currentTimeMillis();
			try {
				c = view.getHolder().lockCanvas();
				synchronized (view.getHolder()) {
					view.onDraw(c);
				}
			} finally {
				if(c != null) {
					view.getHolder().unlockCanvasAndPost(c);
				}
			}
			sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
			try {
				if(sleepTime > 0) sleep(sleepTime);
				else sleep(10);
			} catch (Exception e) {
				
			}
		}
	}
		
}
