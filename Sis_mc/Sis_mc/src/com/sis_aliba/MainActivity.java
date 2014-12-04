package com.sis_aliba;


import java.util.ArrayList;
import com.sis_aliba.*;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	Button btnExit;
	Button btnStartGame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getWindow().getDecorView().setBackgroundColor(Color.YELLOW);	
		
		btnExit = (Button) findViewById(R.id.btnExit);
		btnStartGame = (Button) findViewById(R.id.bttnStartGame);
		
		btnExit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
     			finish();
			}
		});
		
		btnStartGame.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                Intent myIntent = new Intent(arg0.getContext(),PlayerGame.class);
				startActivity(myIntent);
			}
		});
	}


}
