package com.example.sis_mc_bissenbay;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyService extends Service {
	
	private MediaPlayer mediaPlayer;
	
	public void onCreate() {
		super.onCreate();
		mediaPlayer = MediaPlayer.create(this, R.raw.music);
		mediaPlayer.setLooping(true);
	}
	
	public void onDestroy() {
		super.onDestroy();
		mediaPlayer.stop();
	}
	
	public void onStart(Intent intent, int startId) {
		mediaPlayer.start();
	}

	public IBinder onBind(Intent intent) {
		return null;
	}
	
}
