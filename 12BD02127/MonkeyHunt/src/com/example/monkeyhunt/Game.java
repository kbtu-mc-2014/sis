package com.example.monkeyhunt;

import gif.decoder.GifRun;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class Game extends Activity {

	// Screen

	public static int screenWidth;
	public static int screenHeight;
	public static float screenDensity;
	private boolean gameOver;

	static Rect dBackgroundImage;
	static Paint paintForImages;
	
	

	private Rect dGressImage;

	// Images

	private static Bitmap backgroundImage;
	private static Bitmap grassImage;
	public static Bitmap monkeyImage;
	public static Bitmap monkeyRightImage;

	// List of all ducks on a screen.

	private ArrayList<Monkey> aliveMonkeys;

	// How many ducks were killed?

	private int monkeyKilled;

	// Color and size for text.

	private Paint paintText;
	private int textSize;

	// Needed for new random coordinates.

	private Random random = new Random();

	// Position of text for restarting the game.

	private float textForRestart_x;

	private float textForRestart_y;
	
	
	
			
			
		//SurfaceView v = (SurfaceView)findViewById(R.id.surfaceView1);
	 //v.setZOrderOnTop(true);    // necessary
     //SurfaceHolder vHolder = v.getHolder();
     //vHolder.setFormat(PixelFormat.TRANSPARENT);

	public Game(int screenWidth, int screenHeight, Resources resources) {

		Game.screenWidth = screenWidth;
		Game.screenHeight = screenHeight;
		Game.screenDensity = resources.getDisplayMetrics().density;

		this.LoadContent(resources);

		dBackgroundImage = new Rect(0, 0, screenWidth, screenHeight);
		dGressImage = new Rect(0, screenHeight - Game.grassImage.getHeight(), screenWidth, screenHeight);
		paintForImages = new Paint();
		paintForImages.setFilterBitmap(true);
		aliveMonkeys = new ArrayList<Monkey>();

		textSize = 25;

		paintText = new Paint();

		paintText.setColor(Color.BLACK);
		paintText.setTextSize(textSize * Game.screenDensity);
		textForRestart_x = Game.screenWidth / 2 - 95 * Game.screenDensity;

		textForRestart_y = Game.screenHeight / 2;// - 20;

		this.ResetGame();

	}


	private void LoadContent(Resources resources) {
		//setContentView(R.layout.game);

		backgroundImage = BitmapFactory.decodeResource(resources, R.drawable.bg);

		grassImage = BitmapFactory.decodeResource(resources, R.drawable.grass);

		monkeyImage = BitmapFactory.decodeResource(resources, R.drawable.v);

		// Image for monkeys that come from left side of a screen.

		Matrix matrix = new Matrix();
			

		matrix.preScale(-1.0f, 1.0f);

		monkeyRightImage = Bitmap.createBitmap(monkeyImage, 0, 0,
				monkeyImage.getWidth(), monkeyImage.getHeight(), matrix, false);

	}


	private void ResetGame() {

		gameOver = false;

		aliveMonkeys.clear();

		Monkey.speed = Monkey.initSpeed;
		Monkey.timeBetweenMonkeys = Monkey.initTimeBetweenMonkeys;
		Monkey.timeOfLastMonkey = 0;
		Monkey.timeOfLastSpeedup = 0;
		monkeyKilled = 0;

		// We create some starting monkeys

		this.addNewMonkey();
		this.addNewMonkey();
		this.addNewMonkey();
	}


	public void Update(long gameTime) {

		if (gameOver) {
			return;
		}

		// Create new monkey if time

		if ((gameTime - Monkey.timeOfLastMonkey) > Monkey.timeBetweenMonkeys) {

			Monkey.timeOfLastMonkey = gameTime;

			this.addNewMonkey();

		}

		// Update monkeys

		for (int i = 0; i < aliveMonkeys.size(); i++) {

			Monkey m = aliveMonkeys.get(i);

			m.update();

			// Check if any monkey got away and if did end game.

			if (m.x > Game.screenWidth
					|| m.x < 0 - Game.monkeyImage.getWidth()) {

				gameOver = true;

				if (monkeyKilled > HighScore.highScore) {

					// New high score

					HighScore.highScore = monkeyKilled;

					// Save new high score to file.

					HighScore.saveHighScore();

				}

			}

		}

		// Speedup the game, if time

		if ((gameTime - Monkey.timeOfLastSpeedup) > Monkey.timeBetweenSpeedups) {

			Monkey.timeOfLastSpeedup = gameTime;

			Monkey.speed += 0.03;

			if (Monkey.timeBetweenMonkeys > (0.5 * 1000))

				Monkey.timeBetweenMonkeys -= 90;

		}

	}


	public void Draw(Canvas canvas) {

		// First we need to erase everything before drawing

		canvas.drawColor(Color.BLACK);

		// Draw background image.

		canvas.drawBitmap(Game.backgroundImage, null, this.dBackgroundImage,
				this.paintForImages);

		// Draw monkeys

		for (int i = 0; i < aliveMonkeys.size(); i++) {

			aliveMonkeys.get(i).draw(canvas);

		}

		// Draw grass

		canvas.drawBitmap(Game.grassImage, null, this.dGressImage,
				this.paintForImages);

		// Draw how many monkeys were killed.

		canvas.drawText("Score: " + Integer.toString(this.monkeyKilled), 8.0f,
				25.0f, paintText);

		canvas.drawText("High score: " + Integer.toString(HighScore.highScore),
				8.0f, textSize*2, paintText);

		if (gameOver) {

			canvas.drawText("Game over", Game.screenWidth / 2 - 95 * Game.screenDensity
					, Game.screenHeight / 3, paintText);

			canvas.drawText("Touch to restart", textForRestart_x,
					textForRestart_y, paintText);

		}

	}

	public void touchEvent_actionDown(MotionEvent event) {

		if (!gameOver) {

			this.checkIfAnyMonkeyShooted(event.getX(), event.getY());

		} else {

			if (event.getX() > textForRestart_x
					&& event.getX() < textForRestart_x + 280 &&

					event.getY() > textForRestart_y - 50
					&& event.getY() < textForRestart_y + 50) {

				this.ResetGame();

			}

		}

	}


	public void touchEvent_actionMove(MotionEvent event) {

	}

	public void touchEvent_actionUp(MotionEvent event) {

	}

//GamePanel g;
	private void checkIfAnyMonkeyShooted(float touchX, float touchY) {

		for (int i = 0; i < aliveMonkeys.size(); i++) {

			Monkey m = aliveMonkeys.get(i);

			if (m.wasItShoot((int) touchX, (int) touchY)) {
				//gifDraw(touchX, touchY);
				aliveMonkeys.remove(i);

				monkeyKilled++;

			}

		}

	}


	private int newYcoordinate() {


		int min = Game.monkeyImage.getHeight() / 2;

		int max = Game.screenHeight / 2;

		int height = max + min;

		int newYcoordiante = this.random.nextInt(height) + min;

		return newYcoordiante;

	}


	private void addNewMonkey() {

		this.aliveMonkeys.add(new Monkey(newYcoordinate()));

	}
	public void gifDraw(float touchX, float touchY){
		  GifRun w = new GifRun();
	     // w.LoadGif(v, this, R.drawable.puf, touchX, touchY);
	}

}