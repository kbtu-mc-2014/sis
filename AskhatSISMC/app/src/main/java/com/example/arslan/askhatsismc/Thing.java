package com.example.arslan.askhatsismc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public class Thing{
    private Context mContext;


    private int x;
    private int y;


    private String name;


    private int costume;
    private int currentCostume;

    private BitmapDrawable[] graphic = new BitmapDrawable[10];

    public Thing (Context context, int xSet, int ySet, String n, int outfit) {
        x = xSet;
        y = ySet;
        name = n;
        costume = outfit;
        currentCostume = 0;
        mContext = context;
        graphic[0] = (BitmapDrawable) mContext.getResources().getDrawable(costume);
    }

    public void goTo(int xPos, int yPos) {
        x = xPos;
        y = yPos;
    }

    public void setCostume(int c, int i) {
        graphic[i] = (BitmapDrawable) mContext.getResources().getDrawable(c);
    }

    public void setCurrentCostume(int i){
        currentCostume = i;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public Bitmap getBitMap(){
        return graphic[currentCostume].getBitmap();
    }

}
