package com.example.arslan.arslansis;

/**
 * Created by Arslan on 04.12.2014.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public class Items{
    private Context mContext;

    private int x;
    private int y;

    private int skin;
    private int currentSkin;

    private BitmapDrawable[] graphic = new BitmapDrawable[10];

    public Items (Context context, int xSet, int ySet, int outfit) {
        x = xSet;
        y = ySet;
        skin = outfit;
        currentSkin = 0;
        mContext = context;
        graphic[0] = (BitmapDrawable) mContext.getResources().getDrawable(skin);
    }
    public void goTo(int xPos, int yPos) {
        x = xPos;
        y = yPos;
    }
    public void setCostume(int c, int i) {
        graphic[i] = (BitmapDrawable) mContext.getResources().getDrawable(c);
    }
    public void setCurrentCostume(int i){
        currentSkin = i;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public Bitmap getBitMap(){
        return graphic[currentSkin].getBitmap();
    }

}