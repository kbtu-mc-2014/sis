package com.me.sprmn;

import com.badlogic.gdx.Game;



public class SuperMF extends Game{

	@Override
	
	public void create() {
		Assets.load();
		setScreen(new WelcomeScreen(this));
		
	}

}
