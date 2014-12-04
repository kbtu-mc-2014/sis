package com.example.arslan.askhatsismc;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;


public class AirGame extends Activity {


    private AirGameView airGameView;

    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_game);
        airGameView = (AirGameView)findViewById(R.id.airGameView);


        gestureDetector = new GestureDetector(this, gestureListener);
    }


    public boolean onTouchEvent(MotionEvent event){

        int action = event.getAction();


        if(action == MotionEvent.ACTION_DOWN||action == MotionEvent.ACTION_MOVE){

            int y = (int)event.getY() - (airGameView.man.getBitMap().getHeight()/2);
            airGameView.man.goTo(airGameView.man.getX(),y);
        }
        return gestureDetector.onTouchEvent(event);
    }
    GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {

    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.air_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
