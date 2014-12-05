package bouncer.logic;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
//import bouncer.common.Timer;

public class BouncerGame extends ArcadeGame {
	

	
    Random randomGenerator = new Random();
    Random randomGenerator2 = new Random();
	public static String TAG = BouncerGame.class.getCanonicalName();
	private Context mContext;
	private boolean platformHit=false;
	private int indexPlatformHit[]; 
	Date base;
	
	
	boolean drawBonus = false;
	int[] bonusDrawFromPoints;
	private static int MODE_WORLD_READABLE=1;
	private static String PREFS_NAME="bouncer_game";
	private static String SPEED_NAME="speedBall";

	
	boolean large=true;
	
	//Timer timer;
	// For text
	private Paint mTextPaint = new Paint();

	// For Bitmaps
	private Paint mBitmapPaint = new Paint();
	
	//for result text
	private Paint resultPaint = new Paint();
	
	// Game name
	public static final String NAME = "SpaceBlaster";
	
	// Refresh rate (ms)
	private static final long UPDATE_DELAY = 20; 

	Bitmap[] destroyPlatfromImages;
	
	int playerX;
	int playerY;
	
	int ballX;
	int ballY;
	
	Date timeHit;
	
	private int result;
	
	
	private Map<Integer,Integer> punctation = new HashMap<Integer, Integer>();
	
	
	private int points;
	/**
	 * Constructor
	 * 
	 * @param context
	 */
	public BouncerGame(Context context) {
		super(context);
		mContext = context;
		super.setUpdatePeriod(UPDATE_DELAY);
	}

	public BouncerGame(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		super.setUpdatePeriod(UPDATE_DELAY);
	}
	
	
	private int playerSpeed = 38;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int tx = (int)event.getX();
		int ty = (int)event.getY();
		if ( tx > playerX + player.getWidth())
			playerX+=playerSpeed;
		else if( tx < playerX)
			playerX-=playerSpeed;
			
			
		
		return true;
	}
	
	
	
	
	/**
	 * Main Draw Sub
	 */
	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.dispatchDraw(canvas);
		paint(canvas);
	}
	
	boolean ingame = true;
	public void paint(Canvas g) {
		if (ingame)
			playGame(g);
	//	else
		//	showIntroScreen(g);
	}
	
	Bitmap[] platforms;
	int[] nrOfHits;
	//x,y,direction
	int[] initPoint;
	int[] direction;
	
	public void playGame(Canvas c) {
		
		
		
		drawPlayerPlatform(c);
		c.drawBitmap(platforms[0], initPoint[0], initPoint[1], mBitmapPaint);
		drawPlatforms(c);
	    movePlatforms();
	    drawBall(c);
	    moveBall();
	    if(platformHit){
	    	
	    	drawPlatformDestroy(c);
	    }
	    getScore();
	    drawResult(c);
	
	   
	    
	}
	
	
	
	public static int nrOfPlatforms = 8; 
	public void initPlatforms(){
		int width = getWidth();
		
		platforms=new Bitmap[nrOfPlatforms];
		initPoint=new int[nrOfPlatforms*2];
		direction=new int[nrOfPlatforms];
		nrOfHits=new int[nrOfPlatforms];
		//5 platforms
		for(int i = 0; i < nrOfPlatforms ; i++){
		platforms[i] = getImage(R.drawable.platformbluelarger);
		}
		
		for(int i = 0; i < nrOfPlatforms ; i++){
			nrOfHits[i]=0;
		}
		
		for(int i = 0; i < nrOfPlatforms*2 ; i+=2){
		
		//initPoint[i]=0;
		initPoint[i] = width/2 + i*platforms[0].getWidth()/4;
		//initPoint[i+1]=0;
		initPoint[i+1] = 10 + i * platforms[0].getHeight();
	
		
		}
		for(int i = 0; i < nrOfPlatforms ; i++){
			direction[i]=i+randomGenerator.nextInt(7)+1;
		}
		
		
		
		
	}
	
	public void drawBall(Canvas c){
		c.drawBitmap(ball, ballX, ballY, mBitmapPaint);
	}
	

	
	int ballSpeed = 7;
	int ballDirection = -1;
	
	int ballSpeedX = 5;
	int ballDirectionX = 0;
	
	boolean first = true;
	boolean second = false;

	public void moveBall(){
		
		Date afterHit=new Date();
		long fAfterHit=afterHit.getTime();
		long fTimeHit=timeHit.getTime();
		long timeSinceHit=fAfterHit-fTimeHit;
		int width = getWidth();
		
		
	/*	for(int i = 0; i < nrOfPlatforms*2; i+=2){
			
			
			if( (ballX > initPoint[i]) && (ballX < initPoint[i] + platforms[0].getWidth())
				&& (ballY <= initPoint[i+1] + platforms[0].getHeight() ? 
					initPoint[i+1] + platforms[0].getHeight() - ballY < platforms[0].getHeight() : false)){
				if(ballDirection==-1){
					
					platformHit=true;
					indexPlatformHit[i/2]=i;
					nrOfHits[i/2]+=1;
					if(nrOfHits[i/2]<6 && nrOfHits[i/2]>0){
						points+=punctation.get(nrOfHits[i/2]);
					//	System.out.println("points incremented to : " + points);
						}

				}
				//platformDestroy(i);
				
			  	ballDirection=1; 
			}
			
					
		}*/
		
		for(int i = 0; i < nrOfPlatforms*2; i+=2){
			
			
			//if( (ballX > initPoint[i]) && (ballX < initPoint[i] + platforms[0].getWidth())
			//	&& (ballY < initPoint[i+1] )){
				
			if( (ballX > initPoint[i]) && (ballX < initPoint[i] + platforms[0].getWidth())
					&& ( initPoint[i+1] >= ballY  ? initPoint[i+1] - ballY <= platforms[0].getHeight() : false )){
				Log.d(TAG, ballY + " <= " + initPoint[i+1]);
				//if(initPoint[i]-ballY == 1){ 
			  	   // Log.d(TAG, "in == 1"); 
				if(ballDirection==1){
					platformHit=true;
					indexPlatformHit[i/2]=i;
					nrOfHits[i/2]+=1;
					if(nrOfHits[i/2]<6 && nrOfHits[i/2]>0){
						points+=punctation.get(nrOfHits[i/2]);
						Log.d("punkt",""+punctation.get(nrOfHits[i/2]));
					//	System.out.println("points incremented to : " + points);
						}

				}
					ballDirection=-1;
					ballCollisionWithSpecyficPlatform();
				//}
			}
			
					
		}
		
		
		
		//2.with player
		  if( (ballX > playerX) && (ballX < playerX + player.getWidth())
				  && (ballY >= playerY - player.getHeight())){
			  ballCollisionWithPlayer();
			  timeHit=new Date();
			  
			  ballDirection=-1;
		  }
		//3.with up-wall
		  if(ballY <= 0 + ball.getHeight())
			  ballDirection=1;
		  
		  //4.with left and right wall
		  if(ballX <= 0)
			  ballDirectionX=1;
		  if(ballX >= width-ball.getWidth() )
			  ballDirectionX=-1;
		  
		  int height=getHeight();
		  //4.with down-wall
			  if(ballY > height)
				  gameOver();
		
		
			ballY+=ballSpeed*ballDirection;
			ballX+=ballSpeedX*ballDirectionX;
		
	}
	
	
	boolean firstBonusEnter=true;
	Bitmap platformsSprite;
	private void drawPlatformDestroy( Canvas c) {
		
		for(int j = 0; j < nrOfPlatforms; j++){
			
			for(int i = 0; i < nrOfPlatforms; i++){
				if(nrOfHits[i]>=1){
		/*if(nrOfHits[i]==1){
		if(drawBonus==false && firstBonusEnter){
			Log.d("drawBonus","weszlem do ifa y =  " + (initPoint[i]+1)+"hits= "+nrOfHits[i]);
			
		  	if(!(initPoint[i]==-100 || initPoint[i]+1==-100)){
		  		Log.d("drawBonus","inside ");
		  		drawBonus=true;
		  		bonusDrawFromPoints[0]=initPoint[indexPlatformHit[i]];
		  		bonusDrawFromPoints[1]=initPoint[indexPlatformHit[i]+1];
		  	}
		  		//}
		  	//}
		  	firstBonusEnter=false;
			}
		}*/
		  	
			initPoint[indexPlatformHit[i]] = -100;
			initPoint[indexPlatformHit[i]+1] = -100;
				}
			
			
			}
			
			
		
		}
	}
	

	int divideFragments = 5;
	int[] xydivFr;

	public void ballCollisionWithPlayer(){
	

		
		//int platformWidth = playerX + player.getWidth();
		int interval = player.getWidth()/divideFragments;
		
		int tempPlayerX=playerX;
		for(int i = 0; i < divideFragments*2; i+=2){
			xydivFr[i] = tempPlayerX;
			xydivFr[i+1] = tempPlayerX+interval;
			tempPlayerX = tempPlayerX+interval;
		}
		Log.d(TAG,""+ xydivFr);
		
		//conditions depends on where ball touch player platform
		if( (ballX > xydivFr[0]) && (ballX < xydivFr[1]) )
			ballDirectionX=-2;
		else if( (ballX > xydivFr[2]) && (ballX < xydivFr[3]) )
			ballDirectionX=-1;
		else if( (ballX > xydivFr[4]) && (ballX < xydivFr[5]) )
			ballDirectionX=0;
		else if( (ballX > xydivFr[6]) && (ballX < xydivFr[7]) )
			ballDirectionX=1;
		else if( (ballX > xydivFr[8]) && (ballX < xydivFr[9]) )
			ballDirectionX=2;
				
		
		
	}
	
	
	int divideFragmentsS = 3;
	int[] xydivFrS;
	public void ballCollisionWithSpecyficPlatform(){
		
		
		int interval = player.getWidth()/divideFragments;
		
		int tempPlayerX=playerX;
		for(int i = 0; i < divideFragments*2; i+=2){
			xydivFr[i] = tempPlayerX;
			xydivFr[i+1] = tempPlayerX+interval;
			tempPlayerX = tempPlayerX+interval;
		}
		Log.d(TAG,""+ xydivFr);
		
		//conditions depends on where ball touch player platform
		if( (ballX > xydivFr[0]) && (ballX < xydivFr[1]) )
			ballDirectionX=-1;
		else if( (ballX > xydivFr[2]) && (ballX < xydivFr[3]) )
			ballDirectionX=0;
		else if( (ballX > xydivFr[4]) && (ballX < xydivFr[5]) )
			ballDirectionX=1;
		
	}
	
	public void drawPlatforms(Canvas c){
		for(int i = 0; i < nrOfPlatforms ; i++){
	    int index=i*2;
		c.drawBitmap(platforms[i], initPoint[index], initPoint[index+1], mBitmapPaint);
		}
	}

	//int direction = 1;
	public void movePlatforms(){
		
		int width = getWidth();
		
		for(int index = 0; index < nrOfPlatforms ; index++){
			int iMul= index * 2;
			int i = initPoint[iMul];
			initPoint[iMul] = i + direction[index];
			
			if(initPoint[iMul] >= width - platforms[index].getWidth())
				direction[index] = -1*direction[index];
			if(initPoint[iMul] <= 0)
				direction[index] = 1*-(direction[index]);
		
		}
		
	}
	
	@Override
	protected void updatePhysics() {
		// TODO Auto-generated method stub
		
	}


	Bitmap player;
	Bitmap ball;
	boolean firstTime=true;
	@Override
	protected void initialize() {
		if(firstTime){
		
		//if(timer!=null)
			//timer.interrupt();
		
		Log.d(TAG,"initalize");
		int n;
		result=0;
		points=0;
			
		
		// Screen size
		int width = getWidth();
		int height = getHeight();
		
		initPlatforms();
		
		mTextPaint.setARGB(255, 255, 255, 255);
		
		player = getImage(R.drawable.player_larger);
		playerX = width/2;
		playerY = height - player.getHeight();
		
		ball = getImage(R.drawable.ball);
		ballX = playerX + player.getWidth()/2;
		ballY = playerY - ball.getHeight();
		xydivFr=new int[divideFragments*2];
		
		 //platformsSprite=getImage(R.drawable.sprite_platforms);
		
		destroyPlatfromImages=new Bitmap[5];
		destroyPlatfromImages[0]=getImage(R.drawable.platform_01);
		destroyPlatfromImages[1]=getImage(R.drawable.platform_02);
		destroyPlatfromImages[2]=getImage(R.drawable.platform_03);
		destroyPlatfromImages[3]=getImage(R.drawable.platform_04);
		destroyPlatfromImages[4]=getImage(R.drawable.platform_05);
		
		
		indexPlatformHit=new int[nrOfPlatforms];

		
		int base = 10;
		int step = 10;
		for(Integer i = 0; i < divideFragments; i++) {
	       punctation.put(i+1,base+step*i );
		}
		
		Log.d("punkt",punctation.toString());
		//System.out.println(punctation.toString());
		
		points=0;
		result=0;
		
		//timer = new Timer();
		//timer.start();
		
		timeHit = new Date();
		
		large=true;
		
		//result paint
		resultPaint = new Paint();
		resultPaint.setColor(Color.BLACK);
		resultPaint.setStyle(Style.FILL);
		resultPaint.setTextSize(50);
		
		String ballS=LoadPreferences(SPEED_NAME);
		ballSpeed=Integer.parseInt(ballS);
		if(ballSpeed==0)
			ballSpeed=1;
		
		bonusDrawFromPoints=new int[2];
		
		
		firstTime=false;
		
		}
	}
	

	
	
	public void drawPlayerPlatform(Canvas c){
	//	int height = getHeight();
		c.drawBitmap(player, playerX, playerY, mBitmapPaint );
	}

	@Override
	protected boolean gameOver() {
		//here should be something logic, now just restart
		ballDirection=-1;
		
		//checkResult();
		getScore();
		 
		
		
		Intent intent = new Intent(this.getContex(), GameOver.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("result", result+"");
		//System.out.println(timer.getValue());
		this.getContex().startActivity(intent);
		return false;
	}

	//returns long because super method, but return result is never used
	@Override
	protected long getScore() {
		result=points;
		return result;
		//return 0;
	}
	
	private void drawResult(Canvas c){
		
		int width = getWidth();
		int height = getHeight();
		
	    c.drawText(""+result, width/14, height,resultPaint );
		
	}
	
	
	/**
	 * load shared preferences
	 */
	
	 @SuppressLint("WorldReadableFiles")
	private String LoadPreferences(String key){
	   	   
	        String defaultString = "empty";  
	        String location ="";
	        SharedPreferences sharedPreferences = this.getContex().getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
	        location =  sharedPreferences.getString( key, defaultString );
	        System.out.println("loadRestore key = " + location);
	        	
	        return location;
	   
	       }
	

}
