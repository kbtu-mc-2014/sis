package com.example.sis_mc_bissenbay;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

public class StartActivity extends ActionBarActivity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_start);
		startService(new Intent(this, MyService.class));
	}
	
	public void btnClick(View v) {
		switch (v.getId()) {
		case R.id.btnStart:
			Intent i = new Intent();
			i.setClass(this, MainActivity.class);
			startActivity(i);
			break;
		case R.id.btnExit:
			finish();
			stopService(new Intent(this, MyService.class));
			break;
		}
	}
	
	public void onBackPressed() {
		stopService(new Intent(this, MyService.class));
	}

}
