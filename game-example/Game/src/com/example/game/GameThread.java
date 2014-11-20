package com.example.game;

import java.util.Random;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

	private SurfaceHolder sh;
	private GameSurfaceView view;
	private Ball ball;
	private Canvas canvas;
	private int dx;
	private int dy;
	private int vx;
	private int vy;
	private int a;

	private boolean run = false;
	Paint backgroundPaint;
	Paint ballPaint;

	public GameThread(SurfaceHolder _holder, GameSurfaceView _view) {

		sh = _holder;
		view = _view;
		backgroundPaint = new Paint();
		backgroundPaint.setARGB(255, 255, 255, 255);
		ballPaint = new Paint();
		ballPaint.setARGB(255, 255, 0, 0);
		ball = new Ball();
		ball.x = 300;
		ball.y = 300;
		
		Random r = new Random();
		dx = 0;//r.nextInt(21) - 10;
		dy = 10;//r.nextInt(21) - 10;
		vx = 0;
		vy = 0;
		a = 5;
	}
	
	public void draw(Canvas canvas) {
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);
		canvas.drawCircle(ball.x, ball.y, 50, ballPaint);
	}

	public void setRunnable(boolean _run) {

		run = _run;
	}
	
	public void updateState() {
		ball.x += vx;
		ball.y += vy;
		vy += a;
		if (ball.y + 50 >= canvas.getHeight()) {
			vy = -vy + a;
			vy += 2;
		}
		if (ball.y < 50) {
			vy = -vy;
		}
	}

	@SuppressLint("WrongCall") public void run() {

		while (run) {

			canvas = null;
			try {

				canvas = sh.lockCanvas(null);
				updateState();

				synchronized (sh) {

					draw(canvas);
				}

			} finally {

				if (canvas != null) {

					sh.unlockCanvasAndPost(canvas);
				}

			}
			
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public Canvas getCanvas() {

		if (canvas != null) {

			return canvas;

		} else {

			return null;
		}
	}
}