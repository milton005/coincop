package com.mindbees.expenditure;


import java.util.Timer;
import java.util.TimerTask;

import com.mindbees.expenditure.fragment.FragmentAllreminders;
import com.mindbees.expenditure.fragment.HomeFragment;
import com.mindbees.expenditure.reminder.ActivitySetRemainder;
import com.mindbees.expenditure.ui.circular.SupportAnimator;
import com.mindbees.expenditure.ui.circular.ViewAnimationUtils;
import com.mindbees.expenditure.ui.circular.widget.RevealFrameLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivitySelectType extends AppCompatActivity{
	
	ImageView imageIncome, imageExpense, closeBtn, imageReminder;
	TextView textIncome, textExpense, textReminder;


	Animation anim_iconIncome;
	Animation anim_iconExpense;
	Animation anim_iconReminder;

	Timer timer;
    TimerTask timerTask;
  //we are going to use a handler to be able to run in our TimerTask 
    final Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_type_two);
		
		imageIncome = (ImageView) findViewById(R.id.imageIncome);
		imageExpense = (ImageView) findViewById(R.id.imageExpense);
//		imageReminder = (ImageView) findViewById(R.id.imageReminder);
//		textReminder = (TextView) findViewById(R.id.textReminder);
		textIncome = (TextView) findViewById(R.id.textIncome);
		textExpense = (TextView) findViewById(R.id.textexpense);
		
		closeBtn = (ImageView) findViewById(R.id.closeBtn);
		
		anim_iconIncome = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.type_icon_zoom);
		anim_iconExpense = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.type_icon_zoom);
//		anim_iconReminder = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.type_icon_zoom);
		
//		startTimer();
		initializeTimerTask(); 		
		
		imageIncome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ActivitySelectType.this, ActivityCredit_Debit.class);
				i.putExtra("expense", false);
				startActivity(i);
				
			}
		});
		
		imageExpense.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ActivitySelectType.this, ActivityCredit_Debit.class);
				i.putExtra("expense", true);
				startActivity(i);
				
			}
		});
		
//		imageReminder.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent i = new Intent(ActivitySelectType.this, ActivitySetRemainder.class);
//				startActivity(i);
//
//			}
//		});
		
		closeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				onBackPressed();
				Intent i= new Intent(ActivitySelectType.this,MainActivity.class);
				startActivity(i);
			}
		});

		
//		anim_iconReminder.setAnimationListener(new AnimationListener() {
//
//			@Override
//			public void onAnimationStart(Animation animation) {
//				// TODO Auto-generated method stub
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				// TODO Auto-generated method stub
//				textReminder.setTextColor(getResources().getColor(R.color.white));
//				imageIncome.startAnimation(anim_iconIncome);
//				imageIncome.setVisibility(View.VISIBLE);
//			}
//		});
		anim_iconIncome.setAnimationListener(new AnimationListener() {
			
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
				imageExpense.setVisibility(View.VISIBLE);
				textIncome.setTextColor(getResources().getColor(R.color.white));
				imageExpense.startAnimation(anim_iconExpense);
				
			}
		});
		anim_iconExpense.setAnimationListener(new AnimationListener() {
			
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
				textExpense.setTextColor(getResources().getColor(R.color.white));
				
			}
		});
		
        
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		stoptimertask();
	}
	
	 @Override
	    protected void onDestroy() {
	    	// TODO Auto-generated method stub
	    	super.onDestroy();
//	    	stoptimertask();
	    }
	
	/*public void startTimer() { 
        //set a new Timer 
        timer = new Timer();
          
        //initialize the TimerTask's job 
        initializeTimerTask(); 
          
        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms 
        timer.schedule(timerTask, 600, 500); //
    } */
    
    
    /*public void stoptimertask() {
        //stop the timer, if it's not already null 
        if (timer != null) {
            timer.cancel();
            timer = null;
        } 
    } */
    
    public void initializeTimerTask() { 
    	
    	
    	final Handler handler = new Handler(); 
    	handler.postDelayed(new Runnable() { 
    	    @Override 
    	    public void run() { 
    	        // Do something after 5s = 5000ms
				imageIncome.setVisibility(View.VISIBLE);
				imageIncome.startAnimation(anim_iconIncome);
//    	    	imageReminder.setVisibility(View.VISIBLE);
//    	    	imageReminder.startAnimation(anim_iconReminder);
    	    } 
    	}, 400); 
    	
        
        /*timerTask = new TimerTask() {
            public void run() { 
                  
                //use a handler to run a toast that shows the current timestamp 
                handler.post(new Runnable() {
                    public void run() { 
                    	
                    	stoptimertask();
                    } 
                }); 
            } 
        }; */
    }

}
