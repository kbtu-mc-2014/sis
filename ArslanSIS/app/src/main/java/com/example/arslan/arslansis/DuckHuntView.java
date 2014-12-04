package com.example.arslan.arslansis;

/**
 * Created by Arslan on 04.12.2014.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class DuckHuntView extends View {
    private Handler h;
    public Items hunter;
    public Items bullet;
    public Items bullet1;
    public Items bullet2;
    public Items bullet3;
    public Items bullet4;
    public Items duck;
    boolean cl = false;
    boolean cl1 = false;
    boolean cl2 = false;
    boolean cl3 = false;
    boolean cl4 = false;
    public Random r0 = new Random();
    public int counter = 0;
    private final int FRAME_RATE = 120;

    public DuckHuntView(Context context,AttributeSet attrs){
        super(context,attrs);
        h = new Handler();

        hunter = new Items(context,50,150, R.drawable.hunter);
        bullet = new Items(context,250,20, R.drawable.bullet);
        bullet1 = new Items(context,350,20, R.drawable.bullet);
        bullet2 = new Items(context,450,20, R.drawable.bullet);
        bullet3 = new Items(context,550,20, R.drawable.bullet);
        bullet4 = new Items(context,650,20, R.drawable.bullet);
        duck = new Items(context,1200,540, R.drawable.duck_l1);
        duck.setCostume(R.drawable.duck_s, 1);
        duck.setCostume(R.drawable.duck_f, 2);
        duck.setCostume(R.drawable.duck_l1, 3);
        duck.setCostume(R.drawable.duck_l2, 4);
        duck.setCostume(R.drawable.duck_l3, 5);
        duck.setCostume(R.drawable.duck_r1, 6);
        duck.setCostume(R.drawable.duck_r2, 7);
        duck.setCostume(R.drawable.duck_r3, 8);

    }
    private Runnable r = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };
    protected void onDraw(Canvas c){
        if(cl) {
            bullet.goTo(bullet.getX() + 30, bullet.getY());
        }
        if(cl1) {
            bullet1.goTo(bullet1.getX() + 30, bullet1.getY());
        }
        if(cl2) {
            bullet2.goTo(bullet2.getX() + 30, bullet2.getY());
        }
        if(cl3) {
            bullet3.goTo(bullet3.getX() + 30, bullet3.getY());
        }
        if(cl4) {
            bullet4.goTo(bullet4.getX() + 30, bullet4.getY());
        }
        if(bullet.getX() > c.getWidth()){
            cl = false;
            bullet.goTo(250,20);
        }
        if(bullet1.getX() > c.getWidth()){
            cl1 = false;
            bullet1.goTo(350,20);
        }
        if(bullet2.getX() > c.getWidth()){
            cl2 = false;
            bullet2.goTo(450,20);
        }
        if(bullet3.getX() > c.getWidth()){
            cl3 = false;
            bullet3.goTo(550,20);
        }
        if(bullet4.getX() > c.getWidth()){
            cl4 = false;
            bullet4.goTo(650,20);
        }
        if((Math.abs(duck.getY() - bullet.getY())<60)&&(Math.abs(duck.getX() - bullet.getX())<60)){
            duck.setCurrentCostume(1);
            duck.goTo(duck.getX(), duck.getY() + 20);
            duck.goTo(1200,540);
            counter++;
        }
        if((Math.abs(duck.getY() - bullet1.getY())<60)&&(Math.abs(duck.getX() - bullet1.getX())<60)){
            duck.setCurrentCostume(1);
            duck.goTo(duck.getX(),duck.getY()+20);
            duck.goTo(1200,540);
            counter++;
        }
        if((Math.abs(duck.getY() - bullet2.getY())<60)&&(Math.abs(duck.getX() - bullet2.getX())<60)){
            duck.setCurrentCostume(1);
            duck.goTo(duck.getX(),duck.getY()+20);
            duck.goTo(1200,540);
            counter++;
        }
        if((Math.abs(duck.getY() - bullet3.getY())<60)&&(Math.abs(duck.getX() - bullet3.getX())<60)){
            duck.setCurrentCostume(1);
            duck.goTo(duck.getX(),duck.getY()+20);
            duck.goTo(1200,540);
            counter++;
        }
        if((Math.abs(duck.getY() - bullet4.getY())<60)&&(Math.abs(duck.getX() - bullet4.getX())<60)){
            duck.setCurrentCostume(1);
            duck.goTo(duck.getX(),duck.getY()+20);
            duck.goTo(1200,540);
            counter++;
        }

        int i = (r0.nextInt(4-1+1)+1);

        if(i == 4){
            duck.goTo(duck.getX()+50, duck.getY()+50);
            duck.setCurrentCostume(3);
            duck.setCurrentCostume(4);
            duck.setCurrentCostume(5);
        }

        else if(i == 3){
            duck.goTo(duck.getX()+50, duck.getY()-50);
            duck.setCurrentCostume(3);
            duck.setCurrentCostume(4);
            duck.setCurrentCostume(5);
        }

        else if(i == 2){
            duck.goTo(duck.getX()-50, duck.getY()+50);
            duck.setCurrentCostume(6);
            duck.setCurrentCostume(7);
            duck.setCurrentCostume(8);
        }
        else{
            duck.goTo(duck.getX()-50, duck.getY()-50);
            duck.setCurrentCostume(6);
            duck.setCurrentCostume(7);
            duck.setCurrentCostume(8);
        }

        if(duck.getY()>c.getHeight()){
            duck.goTo(duck.getX()-10, duck.getY()-10);
            duck.goTo(duck.getX()+10, duck.getY()-10);
            duck.goTo(duck.getX()-10, duck.getY()-10);
            duck.goTo(duck.getX()+10, duck.getY()-10);
        }
        if(duck.getY()>c.getWidth()){
            duck.goTo(duck.getX()-10, duck.getY()-10);
            duck.goTo(duck.getX()-10, duck.getY()+10);
            duck.goTo(duck.getX()-10, duck.getY()-10);
            duck.goTo(duck.getX()-10, duck.getY()+10);
        }
        c.drawBitmap(hunter.getBitMap(),hunter.getX(),hunter.getY(),null);
        c.drawBitmap(bullet.getBitMap(),bullet.getX(),bullet.getY(),null);
        c.drawBitmap(bullet1.getBitMap(),bullet1.getX(),bullet1.getY(),null);
        c.drawBitmap(bullet2.getBitMap(),bullet2.getX(),bullet2.getY(),null);
        c.drawBitmap(bullet3.getBitMap(),bullet3.getX(),bullet3.getY(),null);
        c.drawBitmap(bullet4.getBitMap(),bullet4.getX(),bullet4.getY(),null);
        c.drawBitmap(duck.getBitMap(),duck.getX(),duck.getY(),null);
        h.postDelayed(r,FRAME_RATE);
    }
}