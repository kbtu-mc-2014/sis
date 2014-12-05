package com.example.sis_mc_bissenbay;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("WrongCall")
public class GameView extends SurfaceView {
	
	private GameManager gameLoopThread;
	private Sprite sprite;
	private Bitmap bitmap;
	private List<Sprite> sprites = new ArrayList<Sprite>();
	private long lastClick;
	private SoundPool sounds;
	private int sExplosion;
	
	public GameView(Context context) {
		super(context);
		gameLoopThread = new GameManager(this);
		sounds = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		sExplosion = sounds.load(context, R.raw.sound, 1);
		getHolder().addCallback(new SurfaceHolder.Callback() {
			public void surfaceDestroyed(SurfaceHolder holder) {
				boolean retry = true;
				gameLoopThread.setRunning(false);
				while(retry) {
					try {
						gameLoopThread.join();
						retry = false;
					} catch (InterruptedException e) {
						
					}
				}
			}
			public void surfaceCreated(SurfaceHolder holder) {
				createSprites();
				gameLoopThread.setRunning(true);
				gameLoopThread.start();
			}
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				
			}
		});
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		if(System.currentTimeMillis() - lastClick > 2000) {
			lastClick = System.currentTimeMillis();
			float x = event.getX();
			float y = event.getY();
			synchronized (getHolder()) {
				for(int i=sprites.size()-1;i>=0;i--) {
					Sprite sprite = sprites.get(i);
					if(sprite.isCollition(x, y)) {
						sounds.play(sExplosion, 1.0f, 1.0f, 0, 0, 1.5f);
						sprites.remove(sprite);
						break;
					}
				}
			}
		}
		return true;
	}
	
	private void createSprites() {
		sprites.add(createSprite(R.drawable.image_1));
		sprites.add(createSprite(R.drawable.image_2));
		sprites.add(createSprite(R.drawable.image_3));
		sprites.add(createSprite(R.drawable.image_4));
		sprites.add(createSprite(R.drawable.image_5));
		sprites.add(createSprite(R.drawable.image_6));
		sprites.add(createSprite(R.drawable.image_7));
		sprites.add(createSprite(R.drawable.image_8));
		sprites.add(createSprite(R.drawable.image_9));
		sprites.add(createSprite(R.drawable.image_10));
	}
	
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		for(Sprite sprite: sprites) {
			sprite.onDraw(canvas);
		}
	}
	
	private Sprite createSprite(int resource) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
		return new Sprite(this, bmp);
	}
	
}
