package com.example.arslan.askhatsismc;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class AirGameView extends View {
    private Handler h;
    Random rand = new Random();
    Random ran = new Random();
    Random ran1 = new Random();
    Random ran2 = new Random();
    Random ran3 = new Random();
    Random ran4 = new Random();
    public Thing man;
    public Thing enemy;
    public Thing enemy1;
    public Thing enemy2;
    public Thing enemy3;
    public Thing enemy4;


    private final int FRAME_RATE = 50;
    public AirGameView(Context context,AttributeSet attrs){
        super(context,attrs);
        h = new Handler();
        int i1 = (ran1.nextInt(50)+550);
        int i2 = (ran2.nextInt(50)+550);
        int i3 = (ran3.nextInt(50)+550);
        int i4 = (ran4.nextInt(50)+550);
        int i = (ran.nextInt(50)+550);

        man = new Thing(context,1500,150,"man", R.drawable.timon);

        enemy = new Thing(context,0,i,"enemy", R.drawable.bug);
        enemy1 = new Thing(context,0,i1,"enemy", R.drawable.bug);
        enemy2 = new Thing(context,0,i2,"enemy", R.drawable.bug);
        enemy3 = new Thing(context,0,i3,"enemy", R.drawable.bug);
        enemy4 = new Thing(context,0,i4,"enemy", R.drawable.bug);
        man.setCostume(R.drawable.pumba, 1);



    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };
    protected void onDraw(Canvas c){

        int i = (rand.nextInt(5)+10);
        int i1 = (ran1.nextInt(10)+20);
        int i2 = (ran2.nextInt(10)+20);
        int i3 = (ran3.nextInt(5)+20);
        int i4 = (ran4.nextInt(20)+30);

       if((Math.abs(enemy.getY() - man.getY())<60)&&(Math.abs(enemy.getX() - man.getX())<60)){
           man.setCurrentCostume(1);
           enemy.goTo(0,(getHeight()/5)*5);
        }
        if((Math.abs(enemy1.getY() - man.getY())<60)&&(Math.abs(enemy1.getX() - man.getX())<60)){
            man.setCurrentCostume(1);
            enemy1.goTo(0,(getHeight()/5)*4);
        }
        if((Math.abs(enemy2.getY() - man.getY())<60)&&(Math.abs(enemy2.getX() - man.getX())<60)){
            man.setCurrentCostume(1);
            enemy2.goTo(0,(getHeight()/5)*3);
        }
        if((Math.abs(enemy3.getY() - man.getY())<60)&&(Math.abs(enemy3.getX() - man.getX())<60)){
            man.setCurrentCostume(1);
            enemy3.goTo(0,(getHeight()/5)*2);
        }
        if((Math.abs(enemy4.getY() - man.getY())<60)&&(Math.abs(enemy4.getX() - man.getX())<60)){
            man.setCurrentCostume(1);
            enemy4.goTo(0,(getHeight()/5)*1);
        }
        enemy.goTo(enemy.getX()+i, enemy.getY());
        if(enemy.getX()>c.getWidth()){
            enemy.setCurrentCostume(0);
            enemy.goTo(0,(getHeight()/5)*5);

        }
        enemy1.goTo(enemy1.getX()+i1, enemy1.getY());
        if(enemy1.getX()>c.getWidth()){
            enemy1.setCurrentCostume(0);
            enemy1.goTo(0,(getHeight()/5)*4);

        }
        enemy2.goTo(enemy2.getX()+i2, enemy2.getY());
        if(enemy2.getX()>c.getWidth()){
            enemy2.setCurrentCostume(0);
            enemy2.goTo(0,(getHeight()/5)*3);

        }
        enemy3.goTo(enemy3.getX()+i3, enemy3.getY());
        if(enemy3.getX()>c.getWidth()){
            enemy3.setCurrentCostume(0);
            enemy3.goTo(0,(getHeight()/5)*2);

        }
        enemy4.goTo(enemy4.getX()+i4, enemy4.getY());
        if(enemy4.getX()>c.getWidth()){
            enemy4.setCurrentCostume(0);
            enemy4.goTo(0,getHeight()/5);

        }
        c.drawBitmap(man.getBitMap(), man.getX(), man.getY(), null);
        c.drawBitmap(enemy.getBitMap(), enemy.getX(), enemy.getY(),null);
        c.drawBitmap(enemy1.getBitMap(), enemy1.getX(), enemy1.getY(),null);
        c.drawBitmap(enemy2.getBitMap(), enemy2.getX(), enemy2.getY(),null);
        c.drawBitmap(enemy3.getBitMap(), enemy3.getX(), enemy3.getY(),null);
        c.drawBitmap(enemy4.getBitMap(), enemy4.getX(), enemy4.getY(),null);
        h.postDelayed(r,FRAME_RATE);
    }
}