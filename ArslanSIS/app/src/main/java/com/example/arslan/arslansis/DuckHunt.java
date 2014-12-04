package com.example.arslan.arslansis;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;


public class DuckHunt extends Activity {

    private DuckHuntView duckHuntView;
    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duck_hunt);
        duckHuntView = (DuckHuntView)findViewById(R.id.DuckHuntView);
        gestureDetector = new GestureDetector(this, gestureListener);

    }

    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN||action == MotionEvent.ACTION_MOVE){
            int y = (int)event.getY() - (duckHuntView.hunter.getBitMap().getHeight()/2);
            duckHuntView.hunter.goTo(duckHuntView.hunter.getX(),y);
        }
        return gestureDetector.onTouchEvent(event);
    }
    GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {

        public boolean onDoubleTap(MotionEvent e) {
            if(duckHuntView.bullet.getX()==250 && duckHuntView.bullet.getY()==20) {
                duckHuntView.cl = true;
                duckHuntView.bullet.goTo(duckHuntView.hunter.getX()+120, duckHuntView.hunter.getY()+90);
                return true;
            }
            if(duckHuntView.bullet1.getX()==350 && duckHuntView.bullet1.getY()==20) {
                duckHuntView.cl1 = true;
                duckHuntView.bullet1.goTo(duckHuntView.hunter.getX()+120, duckHuntView.hunter.getY()+90);
                return true;
            }
            if(duckHuntView.bullet2.getX()==450 && duckHuntView.bullet2.getY()==20) {
                duckHuntView.cl2 = true;
                duckHuntView.bullet2.goTo(duckHuntView.hunter.getX()+120, duckHuntView.hunter.getY()+90);
                return true;
            }
            if(duckHuntView.bullet3.getX()==550 && duckHuntView.bullet3.getY()==20) {
                duckHuntView.cl3 = true;
                duckHuntView.bullet3.goTo(duckHuntView.hunter.getX()+120, duckHuntView.hunter.getY()+90);
                return true;
            }
            if(duckHuntView.bullet4.getX()==650 && duckHuntView.bullet4.getY()==20) {
                duckHuntView.cl4 = true;
                duckHuntView.bullet4.goTo(duckHuntView.hunter.getX()+120, duckHuntView.hunter.getY()+90);
                return true;
            }
            return true;
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.duck_hunt, menu);
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
