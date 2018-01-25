package com.voicesyncelectronicear;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.Window;

public class SplashActivity extends Activity {
	private final int SplashDELAY=2000; // in ms
	private static final Class<?>mainClass=EEActivity.class;	// the main activity to call
	private Thread mSplashThread; 
	
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 				// remove title bar
        setContentView(R.layout.activity_splash);
        
        final SplashActivity sPlashScreen = this;   

		mSplashThread=new Thread(){									// The thread to wait for splash screen events
			@Override public void run(){
				try { synchronized(this){ wait(SplashDELAY); }} catch(InterruptedException ex) {}// Wait given period of time or exit on touch
				finish();
				Intent intent = new Intent(SplashActivity.this, mainClass); // call the mainClass activity
				SplashActivity.this.startActivity(intent);
			}
		};
		mSplashThread.start();   
    }
    @Override public boolean onTouchEvent(MotionEvent evt) { 	// Processes splash screen touch events
        if(evt.getAction() == MotionEvent.ACTION_DOWN)  {
            synchronized(mSplashThread){  mSplashThread.notifyAll();   }
        }
        return true;
    }    
}
