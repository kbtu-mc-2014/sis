package com.example.sis_mc_bissenbay;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {

	private GameView gameView;
	private Bitmap bitmap;
	private int x, y, xSpeed = 7, ySpeed = 7, currentFrame = 0, width, height;
	private static final int BMP_ROWS = 4, BMP_COLUMNS = 3, MAX_SPEED = 5;
	private int[] DIRECTION_TO_ANIMATION_MAP = {3, 1, 0, 2};
	private Rect src, dst;
	
	public Sprite(GameView gameView, Bitmap bitmap) {
		this.gameView = gameView;
		this.bitmap = bitmap;
		Random random = new Random();
		xSpeed = random.nextInt(10)-5;
		ySpeed = random.nextInt(10)-5;
		width = bitmap.getWidth() / BMP_COLUMNS;
		height = bitmap.getHeight() / BMP_ROWS;
		x = random.nextInt(gameView.getWidth() - width);
		y = random.nextInt(gameView.getWidth() - height);
	}
	
	private void update() {
		if(x >= gameView.getWidth() - width - xSpeed || x + xSpeed <= 0) {
			xSpeed = -xSpeed;
		}
		if(x + xSpeed < 0) {
			xSpeed = 5;
		}
		x += xSpeed;
		if(y >= gameView.getHeight() - height - ySpeed || y + ySpeed <= 0) {
			ySpeed = -ySpeed;
		}
		y += ySpeed;
		currentFrame = ++currentFrame%BMP_COLUMNS;
	}
	
	public boolean isCollition(float x2, float y2) {
		return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
	}
	
	public void onDraw(Canvas canvas) {
		update();
		int srcX = currentFrame*width;
		int srcY = getAnimationRow() * height;
		src = new Rect(srcX, srcY, srcX + width, srcY + height);
		dst = new Rect(x, y, x + width, y + height);
		canvas.drawBitmap(bitmap, src, dst, null);
	}
	
	private int getAnimationRow() {
		double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
		int direction = (int) Math.round(dirDouble)%BMP_ROWS;
		return DIRECTION_TO_ANIMATION_MAP[direction];
	}
	
}
