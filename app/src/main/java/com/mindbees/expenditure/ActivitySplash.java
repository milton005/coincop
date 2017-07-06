package com.mindbees.expenditure;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by tony on 16/5/15.
 */
public class ActivitySplash extends AppCompatActivity{

    Animation anim_bottom_to_top;
    Animation anim_icon;
    Animation zoomin;
    ImageView imgBluebg;
    ImageView imgIcon;
    ImageView imgShimmerOne;
    ImageView imgShimmerTwo;
    ImageView imgShimmerThree;
    
    AlphaAnimation  blinkanimation;
    int i = 0;
    
    
    Timer timer;
    TimerTask timerTask;
  //we are going to use a handler to be able to run in our TimerTask 
    final Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imgBluebg = (ImageView) findViewById(R.id.blue_bg);
        imgIcon = (ImageView) findViewById(R.id.icon);
        imgShimmerOne = (ImageView) findViewById(R.id.icon_shimerOne);
        imgShimmerTwo = (ImageView) findViewById(R.id.icon_shimerTwo);
        imgShimmerThree = (ImageView) findViewById(R.id.icon_shimerThree);
        
        anim_bottom_to_top = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splah_bg_top_btm);
        anim_icon = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_icon_zoom);
        zoomin=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomin);
//        zoomin=new ScaleAnimation((float)1.0,(float)1.05,(float)1.0,(float)1.05,(float).5,(float).5);
//        zoomin.setDuration(300);
//        zoomin.setInterpolator(new DecelerateInterpolator());
//        zoomin.setRepeatCount(Animation.INFINITE);
//        zoomin.setRepeatMode(Animation.REVERSE);
        blinkanimation= new AlphaAnimation(1, (float) .8); // Change alpha from fully visible to invisible
        blinkanimation.setDuration(300); // duration - half a second
        blinkanimation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        blinkanimation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        blinkanimation.setRepeatMode(Animation.REVERSE);

        imgBluebg.startAnimation(anim_bottom_to_top);
        
        
        
        imgIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = null;
				stoptimertask();
				if (BaseActivity.getpreferenceBoolean(Const.TAG_LOGIN, ActivitySplash.this)) {
					i = new Intent(ActivitySplash.this, MainActivity.class);
				}else {
					i = new Intent(ActivitySplash.this, Activity_LoginType.class);
				}
				startActivity(i);
				finish();
				
			}
		});


        anim_bottom_to_top.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                imgIcon.setVisibility(View.VISIBLE);
                imgIcon.startAnimation(anim_icon);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        
        anim_icon.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				/*imgShimmer.setVisibility(View.VISIBLE);
				imgShimmer.startAnimation(blinkanimation);*/
                imgIcon.startAnimation(zoomin);
				 startTimer(); 
				
			}
		});
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	
    }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	stoptimertask();
    }
    
    
    public void startTimer() { 
        //set a new Timer 
        timer = new Timer();
          
        //initialize the TimerTask's job 
        initializeTimerTask(); 
          
        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms 
        timer.schedule(timerTask, 0, 500); //
    } 
    
    
    public void stoptimertask() {
        //stop the timer, if it's not already null 
        if (timer != null) {
            timer.cancel();
            timer = null;
        } 
    } 
    
    public void initializeTimerTask() { 
    	
    	
        
        timerTask = new TimerTask() {
            public void run() { 
                  
                //use a handler to run a toast that shows the current timestamp 
                handler.post(new Runnable() {
                    public void run() { 
                    	
                    	if (i == 4) {
							i = 0;
//							imgShimmerThree.setVisibility(View.GONE);
//							imgShimmerTwo.setVisibility(View.GONE);
//							imgShimmerOne.setVisibility(View.GONE);
						}
                    	
                    	if (i== 1) {
//                    		imgShimmerThree.setVisibility(View.VISIBLE);
                    		
//							i++;
						}else if (i== 2) {
//							i++;
//							imgShimmerOne.setVisibility(View.VISIBLE);
//							imgShimmerTwo.setVisibility(View.VISIBLE);
						}else if (i== 3) {
//							i++;
//							imgShimmerOne.setVisibility(View.VISIBLE);
//							imgShimmerTwo.setVisibility(View.VISIBLE);
//							imgShimmerOne.setVisibility(View.VISIBLE);
						}
                    	
                    	i++;
                    	
                    } 
                }); 
            } 
        }; 
    }
}
