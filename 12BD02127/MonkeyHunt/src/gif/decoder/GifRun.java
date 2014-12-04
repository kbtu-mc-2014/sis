// Created by JeffMeJones@gmail.com
package gif.decoder;



import com.example.monkeyhunt.GamePanel;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;


public class GifRun implements Runnable, Callback {

	
	public Bitmap bmb;
	public GIFDecode decode;
	public int ind;
	public int gifCount;
	public SurfaceHolder mSurfaceHolder ;
	boolean surfaceExists;
	// X coordinate of the touched position
    private float mX;
 
    // Y Coordinate of the touched position
    private float mY;
	
	public void LoadGif(SurfaceView v, android.content.Context theTHIS, int R_drawable, float mX, float mY)
	{		
		//InputStream Raw= context.getResources().openRawResource(R.drawable.image001);
	       mSurfaceHolder = v.getHolder();
	       mSurfaceHolder.addCallback(this);
	       decode = new GIFDecode();
	       decode.read(theTHIS.getResources().openRawResource(R_drawable));
	       ind = 0;
	       mX = this.mX;
	       mY = this.mY;
			// decode.
			gifCount = decode.getFrameCount();
			bmb = decode.getFrame(0);
			surfaceExists=true;
			Thread t = new Thread(this);
			t.start();
	}

	public void run() 
	{
		while (surfaceExists) {
			try {
				
					Canvas rCanvas = mSurfaceHolder.lockCanvas();
					rCanvas.drawBitmap(bmb, mX, mY, new Paint());
					//ImageView im = (ImageView) findViewById(R.id.imageView1);
					//im.setImageBitmap(bmb);
					
					mSurfaceHolder.unlockCanvasAndPost(rCanvas);
					bmb = decode.next();
					
				Thread.sleep(100);
			} catch (Exception ex) {

			}
		}
		
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) 
	{
		
		
		
	}

	public void surfaceCreated(SurfaceHolder holder) 
	{
		
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		
		surfaceExists=false;
	}
	
}
