package com.me.sprmn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;


public class WelcomeScreen implements Screen{
	
	
	 Game game;
	 OrthographicCamera camera;
	 SpriteBatch batch;
	 int playX;
	 Vector3 touch;
	 Boolean nextScreen;
	 
	public WelcomeScreen (Game game){
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 480, 854);
		batch = new SpriteBatch();
		playX = GameConstants.PLAY_X;
		touch = new Vector3();
		nextScreen = false;
	}

	@Override
	public void render(float delta) {
		update(delta);
		draw(delta);
		
	}

	

	private void update(float delta) {
		if((playX > 0 || Gdx.input.getAccelerometerX() < 0) && ((playX + GameConstants.BUTTON_WIDTH) < GameConstants.PLAY_Y +GameConstants.BUTTON_HEIGHT || Gdx.input.getAccelerometerX() > 0)){
			playX -= Gdx.input.getAccelerometerX();
		}
		
		if(Gdx.input.isTouched()){
			touch.set(Gdx.input.getX(), Gdx.input.getY(),0);
			camera.unproject(touch);
			
			if(touch.x > playX && touch.x < playX + GameConstants.BUTTON_WIDTH && touch.y > GameConstants.PLAY_Y && touch.y < GameConstants.PLAY_Y + GameConstants.BUTTON_HEIGHT){
				Gdx.input.vibrate(100);
				nextScreen = true;
				
			}
		}
		if(nextScreen){
			
			if(playX > (0 - GameConstants.BUTTON_WIDTH + 10)){
				playX -= GameConstants.BUTTON_ANIMATION_SPEED;				
			}
			if(!(playX > (0 - GameConstants.BUTTON_WIDTH + 10))){
				nextScreen = false;
//				game.setScreen(new GameScreen(game));
//				game.getScreen().dispose();
			}
		}
		
	}
	private void draw(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		batch.draw(Assets.sprite_play,playX, GameConstants.PLAY_Y);
		//Assets.font.draw(batch, ""+Assets.settings.getInteger("highscore",0), 225, 700);
		batch.end();
	}
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	

}
