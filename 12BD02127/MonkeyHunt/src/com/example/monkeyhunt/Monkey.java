package com.example.monkeyhunt;

import gif.decoder.GifRun;

//import com.example.giftest.R;

import android.graphics.Canvas;

import android.graphics.Rect;

public class Monkey {

	// This are starting data

	public static final float initSpeed = 5;
	public static final long initTimeBetweenMonkeys = 1500; // in milliseconds

	public static float speed;
	public static long timeBetweenMonkeys; // in milliseconds
	public static long timeOfLastMonkey;

	public static boolean direction = true;

	// Needed for speeding up the game

	public static long timeBetweenSpeedups = 250; // in milliseconds
	public static long timeOfLastSpeedup;

	// position on the screen.

	public float x;
	public float y;


	private float velocity;

	public Monkey(int y) {

		this.y = y;

		if (Monkey.direction) {
			this.x = Game.screenWidth;
			velocity = speed * -1;
		} else {
			this.x = 0 - Game.monkeyImage.getWidth();
			velocity = speed;
		}

		// change direction for a next monkey.

		Monkey.direction = !Monkey.direction;

	}

	public void update() {

		this.x += velocity;

	}

	public void draw(Canvas canvas) {
			

		if (velocity < 0)
			canvas.drawBitmap(Game.monkeyImage, x, y, null);
		else
			canvas.drawBitmap(Game.monkeyRightImage, x, y, null);
	}
	

	public boolean wasItShoot(int touchX, int touchY) {

		Rect mRect = new Rect((int) this.x, (int) this.y, (int) this.x
				+ Game.monkeyImage.getWidth(), (int) this.y
				+ Game.monkeyImage.getHeight());

		return mRect.contains(touchX, touchY);

	}

}
