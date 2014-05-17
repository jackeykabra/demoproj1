package com.android.workingday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends Activity  {
	
	Thread splashTread = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		
		splashTread = new Thread() {
			@Override
			public void run() {
				try {
					
					sleep(3000);
					
					
				} catch (Exception e) {
					// do nothing
					e.printStackTrace();
					
				} finally {
					
					startActivity(new Intent(SplashScreen.this,
							MainActivity.class));
					finish();
					overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
				}
			}
		};
		splashTread.start();
		
	}	
}
