package com.me.sprmn;

import java.util.Random;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class GameScreen implements Screen {
	Game game;
	OrthographicCamera camera;
	SpriteBatch batch;
	Animation temp;
	float showTime;
	int gameState;
	int manY;
	float rotation;
	int gravityEffect;
	int firstWallX, secondWallX;
	Random rand;
	int tempInt;
	Boolean firstOnStage, secondOnStage;
	int upperBound, lowerBound;
	int skyX;
//	int score;
	
	
	public GameScreen(Game game){
		this.game= game;
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 480, 854);
		batch = new SpriteBatch();
		showTime = 0F;
		gameState = 0;
		manY = 397;
		rotation = 0F;
		gravityEffect = 0;
		firstWallX = GameConstants.WALL_INITIAL_X;
		secondWallX = GameConstants.WALL_INITIAL_X;
		rand = new Random();
		tempInt = 0;
		firstOnStage = true;
		secondOnStage = false;
		upperBound = 0;
		lowerBound = 0;
		skyX = 0;
		//score = 0;
		
		
	} 

	@Override
	public void render(float delta) {
		update(delta);
		draw(delta);

	}

	private void draw(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		
		batch.draw(Assets.sprite_sky, skyX, 0);
		batch.draw(Assets.current_frame, 200, manY, 0, 0, 60, 60, 1, 1, rotation);
		batch.draw(Assets.current_wall, firstWallX, 0);
		batch.draw(Assets.next_wall, secondWallX, 0);
		//Assets.font.draw(batch, ""+score, 225, 100);
		batch.end();
	}
		
	
	private void update(float delta) {
		switch(gameState){
		case 0:
			loadAnimation();
			break;
		
		case 1:
			updateAnimation();
			break;
			
		}
		generalUpdate();
		
		if(Gdx.input.isTouched()){
			manY -= 15 + (manY/100);
		}
		
		if(manY > 427){
			gravityEffect = 1;
		}
		if(manY < 427){
			gravityEffect = -1;
		}
		
		if((manY + 60) > 854){
			Gdx.input.vibrate(100);
			game.setScreen(new WelcomeScreen(game));
			game.getScreen().dispose();
		}
	}

//	private void nextScreen() {
//		Gdx.input.vibrate(100);
//		//if(score > Assets.settings.getInteger("highscore", 0)){
//			//Assets.settings.putInteger("highscore", score);
//			//Assets.settings.flush();
//		}
		
		
		
	
	private void generalUpdate() {
		manY += 5 + gravityEffect;
		rotation = (float)((manY - 427)/10);
		skyX -=1;
		if(skyX <-479){
			skyX = 0;
		}
		
		if((firstWallX < 200 && firstWallX > 198) || (secondWallX < 200 && secondWallX > 198)){
			Gdx.input.vibrate(100);
			
		}
		if(firstOnStage || secondWallX < 150){
			firstWallX -= GameConstants.WALL_SPEED;
					}
		if(secondOnStage || firstWallX < 150){
			secondWallX -= GameConstants.WALL_SPEED;
		}
		if(firstWallX < GameConstants.WALL_END){
			firstOnStage = false;
			secondOnStage = true;
		}
		if(secondWallX < GameConstants.WALL_END){
			firstOnStage = true;
			secondOnStage = false;;
		}
		
		if((firstWallX > 200 && firstWallX < 260) || (secondWallX > 200 || secondWallX < 260)){
			if((manY < upperBound) || ((manY + rotation) < upperBound) || ((manY + 60) > lowerBound)
					|| ((manY + 60 + rotation) > lowerBound)){
				Gdx.input.vibrate(100);
				game.setScreen(new WelcomeScreen(game));
				game.getScreen().dispose();
				
			}
		}
	}

	private void updateAnimation() {
		showTime = Gdx.graphics.getDeltaTime();
		Assets.current_frame = temp.getKeyFrame(showTime,true);
		if(secondOnStage && firstWallX < GameConstants.WALL_END){
			tempInt = rand.nextInt(7);
			Assets.current_wall = Assets.frames_wall[0][tempInt];
			firstWallX = GameConstants.WALL_INITIAL_X;
			upperBound = 150 +(50*tempInt);
			lowerBound = 350 +(50*tempInt);
				}
		if(firstOnStage && secondWallX < GameConstants.WALL_END){
			tempInt = rand.nextInt(7);
			Assets.next_wall = Assets.frames_wall[0][tempInt];
			secondWallX = GameConstants.WALL_INITIAL_X;
			upperBound = 150 +(50*tempInt);
			lowerBound = 350 +(50*tempInt);
				}
	}
 
	private void loadAnimation() {
		temp = Assets.animation_man;
		showTime = Gdx.graphics.getDeltaTime();
		Assets.current_frame = temp.getKeyFrame(showTime,true);
		gameState = 1;
		tempInt = rand.nextInt(7);
		Assets.current_wall = Assets.frames_wall[0][tempInt];
		Assets.next_wall = Assets.frames_wall[0][tempInt];
		upperBound = 150 +(50*tempInt);
		lowerBound = 350 +(50*tempInt);
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
