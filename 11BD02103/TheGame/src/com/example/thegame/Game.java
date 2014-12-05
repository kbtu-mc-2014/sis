package com.example.thegame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class Game extends Activity {

	MediaPlayer mp1,jump,takecoin;
	 Gameloop gameLoopThread;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(new GameView(this));		
	}
	public class GameView extends SurfaceView{
	      Bitmap bmp;
	      Bitmap background;
	      Bitmap run1;
	      Bitmap coin;
	      
	      private SurfaceHolder holder;
	      private int x = 0,y=0,z=0,delay=0,getx,gety,sound=1;
	      int show=0,sx,sy;
	      int cspeed=0;
	      int score=0;
	      int volume;
	    @SuppressWarnings("deprecation")
		@SuppressLint("NewApi")
		public GameView(Context context) 
	      {
	    	  super(context);
	    	  
	    	  gameLoopThread = new Gameloop(this);
	    	  holder = getHolder();

	             holder.addCallback(new SurfaceHolder.Callback() {
				@SuppressWarnings("deprecation")
				@Override
               public void surfaceDestroyed(SurfaceHolder holder) 
               {
					gameLoopThread.setRunning(false);
					gameLoopThread.getThreadGroup().interrupt();
	             }
               
               @SuppressLint("WrongCall")
				@Override
               public void surfaceCreated(SurfaceHolder holder) 
               {
               	  gameLoopThread.setRunning(true);
               	  gameLoopThread.start();
               	  
	             }
               @Override
               public void surfaceChanged(SurfaceHolder holder, int format,int width, int height) 
	                    {
	                    }
	             });
	             
	             Display display = getWindowManager().getDefaultDisplay();
					
					sx = display.getWidth();
					sy = display.getHeight();;
					cspeed=sx/2;
	    	  background = BitmapFactory.decodeResource(getResources(), R.drawable.back);
	    	  run1=BitmapFactory.decodeResource(getResources(), R.drawable.run1);
	    	  coin=BitmapFactory.decodeResource(getResources(), R.drawable.coin);
	    	  run1=Bitmap.createScaledBitmap(run1, sx/9,sy/7, true);
	    	  coin=Bitmap.createScaledBitmap(coin, sx/16,sy/24, true);
	    	  background=Bitmap.createScaledBitmap(background, 2*sx,sy, true);
	    	  mp1=MediaPlayer.create(Game.this,R.raw.game);
	    	  jump=MediaPlayer.create(Game.this,R.raw.jump);
	    	  takecoin=MediaPlayer.create(Game.this,R.raw.cointake);
	      }
	      @Override
			public boolean onTouchEvent(MotionEvent event) {
				
	    	  	if(event.getAction()==MotionEvent.ACTION_DOWN)
	    	  	{
	    	  		show=1;
	    	  		
	    	  		getx=(int) event.getX();
	    	  		gety=(int) event.getY();
	    	  		//exit
	    	  		if(getx<25&&gety<25)
	    	  		{ 
	    	  			System.exit(0);
	    	  	
	    	  		}
	    	  	}
		  		return true;
	      }
	        @SuppressLint("WrongCall")
			@Override
		      protected void onDraw(Canvas canvas) 
		      {
		   
	        	SharedPreferences pref = getApplicationContext().getSharedPreferences("higher", MODE_PRIVATE);
	        	Editor editor = pref.edit();
	        	volume=pref.getInt("vloume", 0);
	        	if(volume==0)
	        	{
	        		sound=0;
	        	}
	        	canvas.drawColor(Color.BLACK);
	    	  	
		    	z=z-20;
		    	if(z==-sx)
		    	{
		    		z=0;
		    		canvas.drawBitmap(background, z, 0, null);
		    		
		    	}
		    	else
		    	{
		    		canvas.drawBitmap(background, z, 0, null);	
		    	}
		    	
		    		 x+=5;
		    		 if(x==20)
		    		 {
		    			 x=5;
		    		 }
		    		 
		    		  if(show==0)
		    				  canvas.drawBitmap(run1, sx/16, 15*sy/18, null);
		    
			    	 if(show==1)
			    	 {
			    		 if(sound==1)
			    		 {
			    		 jump.start(); 
			    		 }
			    		 
		    				  canvas.drawBitmap(run1, sx/16, 3*sy/4, null);
		    		
		    				  if(cspeed<=sx/8&&cspeed>=sx/16)
		    				  {
		    					  if(sound==1)
		 			    		 {
		    						  takecoin.start();
		    						  
		 			    		 }
		    					  cspeed=sx/2;
		    					  score+=10;
		    					
		    				  }
		    				
			    		 delay+=2;
			    		 if(delay==4)
			    		 {
			    		 show=0;
			    		 delay=0;
			    		 }
			    	 }
		    		  cspeed=cspeed-20;
				    	if(cspeed==-sx/2)
				    	{
				    		cspeed=sx/2;
				    		canvas.drawBitmap(coin, cspeed, 3*sy/4, null);
				   
				    	}
				    	else
				    	{
				    		canvas.drawBitmap(coin, cspeed, 3*sy/4, null);	
				    	}
			    		 
				    	 	Paint paint = new Paint();
				    	    paint.setColor(Color.BLUE);
				    	    paint.setAntiAlias(true);
				    	    paint.setFakeBoldText(true);
				    	    paint.setTextSize(25);
				    	    paint.setTextAlign(Align.LEFT);
				    	    canvas.drawText("Score: "+score, 3*sx/4, 20, paint);
		
					    	  if(sound==1)
				    		  {
				    		  mp1.start();
				    		  mp1.setLooping(true);
				    		  }
					    	  else
					    	  {
					    		 mp1.stop();
					    	  }
		    	  }
		    
		      }
	
		public class TeleListener extends PhoneStateListener 
		{
	      public void onCallStateChanged(int state,String incomingNumber)
	      {
	        if(state==TelephonyManager.CALL_STATE_RINGING)
              {
	        	mp1.stop();
	        	System.exit(0);  
              }
	       } 
	      
	    }
}
