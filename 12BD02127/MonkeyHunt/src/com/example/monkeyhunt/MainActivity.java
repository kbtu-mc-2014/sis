package com.example.monkeyhunt;


import android.os.Bundle;
import android.app.Activity;
import android.view.View;


public class MainActivity extends Activity {
	
	private boolean showingMainMenu;
	private GamePanel gamePanel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        
        showingMainMenu = true;
    }

    @Override
    public void onBackPressed(){
    	if(!showingMainMenu){
    		showingMainMenu = true;
    		// Stop game loop
    		gamePanel.surfaceDestroyed(null);
    		setContentView(R.layout.activity_main);
    	}else{
    		// Quit
    		super.onBackPressed();
    	}
    }
    
    // Start game on click
    public void onClickStartGame(View v){
    	showingMainMenu = false;
    	
    	// High score
        HighScore.ctx = this.getBaseContext();
        HighScore.loadHighScore();
        
        // Start and show game.
        gamePanel = new GamePanel(this);
    	setContentView(gamePanel);
    }

}