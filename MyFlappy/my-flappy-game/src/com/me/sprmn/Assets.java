
package com.me.sprmn;




import com.badlogic.gdx.Gdx;





import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.graphics.g2d.Sprite;


public class Assets {
	
	public static Texture texture_play;
	public static Sprite sprite_play;
	
	public static Texture texture_man;
	public static TextureRegion[][] frames_man;
	public static Animation animation_man;
	public static TextureRegion current_frame;
	
	public static Texture texture_wall;
	public static TextureRegion[][] frames_wall;
	public static TextureRegion current_wall;
	public static TextureRegion next_wall;
	
	public static Texture texture_sky;
	public static Sprite sprite_sky;
	
	//public static BitmapFont font;
	
	//public static Preferences settings;
	

	
	public static  void load(){
		Texture.setEnforcePotImages(false);
		texture_play = new Texture(Gdx.files.internal("WelcomeScreen/play-btn.png"));
		
		texture_play.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		sprite_play = new Sprite(texture_play);
		sprite_play.flip(false, true);
		
		texture_man = new Texture(Gdx.files.internal("WelcomeScreen/flappy_man_animation.png"));
		
		frames_man = TextureRegion.split(texture_man, 150, 60);
		frames_man[0][0].flip(false, true);
		frames_man[0][1].flip(false, true);
		animation_man = new Animation(0.3F, frames_man[0]);
		
		
		texture_wall = new Texture(Gdx.files.internal("WelcomeScreen/wall_sprite.png"));
	
		frames_wall = TextureRegion.split(texture_wall, 40, 794);		
		
		for(int i = 0; i < 7; i++){
			frames_wall[0][i].flip(false, true);
		}
		
		texture_sky = new Texture(Gdx.files.internal("WelcomeScreen/sky1.png"));
		sprite_sky = new Sprite(texture_sky);
		
		sprite_sky.flip(false, true);
		
		
		
		
		
//		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("my_font.ttf"));
//		font = gen.generateFont(50);
//		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		font.setColor(Color.valueOf("ff06fa"));	
//		font.setScale(1, -1);
//		
//		settings = Gdx.app.getPreferences("settings");
	}

}
