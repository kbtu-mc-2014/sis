package com.sis_aliba;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

public class PlayerGame extends Activity {
	SampleView tView;
	private Handler handler;
	Snake snake1 = null;
	int h = 0, w = 0;
	ArrayList<Food> foodList = new ArrayList<Food>();
	int widthPlaces = 20;
	int higthPlaces = 20;
	Rect[][] places = new Rect[widthPlaces][higthPlaces];
	Rect bottemRect = null;
	Rect[] playButtons = null;;
	Rect restartRect = null;
	String[] pButtonString;
	Paint[] p = new Paint[8];
	int rectAreaResize = 0;
	String gOver = "Game Over";
	int speed = 200;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(tView = new SampleView(this));
		
		handler = new Handler();
		handler.postDelayed(runnable, 400);
		
		
	}
	
	
	
	private Runnable runnable = new Runnable() {
		   @Override
		   public void run() {
			  snake1.moveSnake1();
			  tView.invalidate();
			  handler.postDelayed(this, speed);
		   }
		};

 
	public class SampleView extends View {
		int x, y;

		public SampleView(Context context) {
			super(context);
			setFocusable(true);

			snake1 = new Snake(widthPlaces, higthPlaces);
			snake1.myColor.setColor(Color.BLACK);
			
			
			if(p[0] == null){
				//Buttons rectangles on the bottom
				p[0] = new Paint();
				p[0].setAntiAlias(true);
				p[0].setColor(Color.BLACK);
				p[0].setStyle(Paint.Style.STROKE); 
				p[0].setStrokeWidth(4.5f);
				
				//Food
				p[1] = new Paint();
				p[1].setColor(Color.GREEN);
				p[1].setStyle(Paint.Style.FILL);
				p[1].setStrokeWidth(4.5f);
				
				//Text
				p[2] = new Paint();
				p[2].setColor(Color.BLACK);
				p[2].setStyle(Paint.Style.FILL);
				p[2].setStrokeWidth(4f);
				p[2].setTextAlign(Align.CENTER);
				p[2].setTextSize(40f);
			
				
			}
		}
		
		@Override
		public boolean onTouchEvent(MotionEvent ev){
		    x = (int) ev.getX();
		    y = (int) ev.getY();

		    
			if(playButtons != null && snake1 != null){
				// 0 = down 1 = up 10 = left 11 = right
				if(playButtons[0].contains(x, y) && snake1.direction != 11){
					snake1.direction = Direction.LEFT.direct;
					
				}
				if(playButtons[1].contains(x, y) && snake1.direction != 10){
					snake1.direction = Direction.RIGHT.direct;
				}
				if(playButtons[2].contains(x, y) && snake1.direction != 1){
					snake1.direction = Direction.DOWN.direct;
				}
				if(playButtons[3].contains(x, y) && snake1.direction != 0){
					snake1.direction = Direction.UP.direct;
				}
				if(playButtons[4].contains(x, y)){
					speedUp();
				}
				if(playButtons[5].contains(x, y)){
					speedDown();
				}
			}
			
			if(snake1 != null){
				if(snake1.killedMyself || snake1.collision){
					if(restartRect.contains(x, y)){
						handler.postDelayed(runnable, 400);
						snake1 = new Snake(widthPlaces, higthPlaces);
						foodList.clear();
						speed = 400;
					}
				}
			}
			
			x = 0;
			y = 0;
			
		    return true;
		       
		}
		@Override
		protected void onDraw(Canvas canvas) {
			if(w == 0 || h == 0){
				h = this.getHeight();
				w = this.getWidth();
			}

			if(playButtons == null ){
				pButtonString = new String[6];
				playButtons = new Rect[6];
				playButtons[0] = new Rect(20,w+((h-w)/4),(w/4),h-20);
				pButtonString[0] = "<--";
				playButtons[1] = new Rect((w/4*3),w+((h-w)/4),(w-20),h-20);
				pButtonString[1] = "-->";
				playButtons[2] = new Rect((w/4*1)+20,(h-((h-w)/2))+((h-w)/8) ,(w/4*3)-20,h-20);
				pButtonString[2] = "Down";
				playButtons[3] = new Rect((w/4*1)+20,w+((h-w)/4),(w/4*3)-20,(w+(h-w)/2)+((h-w)/8)-20);
				pButtonString[3] = "Up";
				playButtons[4] = new Rect((w/8*7),w+20,(w-20),w+((h-w)/5));
				pButtonString[4] = "+";
				playButtons[5] = new Rect((w/8*6),w+20,(w/8*7)-20,w+((h-w)/5));
				pButtonString[5] = "-";
				bottemRect = new Rect(0,w+4,w,h);
				restartRect = new Rect(w/2-100,h/2,w/2+100,h/2+100);
								
			}

			if(places[0][0] == null){
				rectAreaResize = w/widthPlaces;
				for(int i = 0; i < places.length; i++){
					for(int j = 0; j < places[0].length; j++){
						places[j][i] = new Rect(i*rectAreaResize, j*rectAreaResize, i*rectAreaResize + rectAreaResize , j*rectAreaResize + rectAreaResize);	
					}
				}
			}

			if(foodList.size() < snake1.posArray.size()/6){
				foodList.add(new Food(widthPlaces, higthPlaces));
			}
			
		
					
			canvas.drawColor(Color.YELLOW);
			canvas.drawRect(bottemRect,p[0]);
			
			for(int i = 0; i < playButtons.length; i++){
				canvas.drawRect(playButtons[i], p[0]);
				canvas.drawText(pButtonString[i], playButtons[i].exactCenterX(), playButtons[i].exactCenterY() + 10, p[2]);
			}
			
			
			if(places != null && foodList != null && !foodList.isEmpty()){
				for(int i = 0; i < foodList.size() && foodList.get(i) != null; i++){
					canvas.drawRect(places[foodList.get(i).myXPos][foodList.get(i).myYPos], p[1]);
				}
			}

			if(snake1 != null){
				if(snake1.killedMyself ){
					canvas.drawText(gOver, w/2, h/2-40, p[2]);
					canvas.drawRect(restartRect, p[0]);
					canvas.drawText("Restart", w/2, h/2+50, p[2]);
					handler.removeCallbacksAndMessages(null);
				}
				
				
				
					
					
					
					
				for(int i = 0; i < snake1.posArray.size(); i++){			
					if(i == 0){
						snake1.myColor.setStyle(Paint.Style.FILL_AND_STROKE);
						canvas.drawRect(places[snake1.posArray.get(i).x][snake1.posArray.get(i).y], snake1.myColor);
						snake1.myColor.setStyle(Paint.Style.STROKE);
					}else{
						canvas.drawRect(places[snake1.posArray.get(i).x][snake1.posArray.get(i).y], snake1.myColor);
					}
	
					if(!foodList.isEmpty()){	
						for(int j = 0; j < foodList.size(); j++){
								if(snake1.posArray.get(i).x == foodList.get(j).myXPos && snake1.posArray.get(i).y == foodList.get(j).myYPos){
									if(i == 0 && j == 0){
										foodList.add(new Food(widthPlaces, higthPlaces));
										speedUp();
									}
									if(i == snake1.myLength - 1){
										snake1.addFood(foodList.get(j).myXPos, foodList.get(j).myYPos);
										if(foodList.size() >= snake1.posArray.size()/6){
											foodList.remove(j);
										}else{
											foodList.get(j).replaceMe();
										}
									}		
								}
							
						}
						
					}
				}
				canvas.drawText("Length: " + Integer.toString(snake1.myLength) + " Speed: " + 30000/speed, w/3,w+((h-w)/6), p[2]);
				
			}
		}
		public void speedUp(){
			if(speed > 120){
				speed -= 8;
			}else if(speed > 100){
				speed -= 2;
			}
		}
		public void speedDown(){
			if(speed < 400){
				speed += 2;
			}
		}
	}
}