package com.mindbees.expenditure.app;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.nostra13.universalimageloader.core.ImageLoader;

;
import android.app.Application;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

public class Expenditure extends Application {
	
	public static ImageLoader imageLoader = ImageLoader.getInstance();
	
	public static GoogleAnalytics analytics;
	public static Tracker tracker;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		MultiDex.install(this);
		 FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);
		 
		 if (!BaseActivity.getpreferenceBoolean(Const.TAG_RATE_NEVR, this)) {
			 int noOfTimes = BaseActivity.getpreferenceInt(Const.TAG_RATE, this);
			 noOfTimes++;
			 Log.e("coincop", noOfTimes+"");
			 BaseActivity.setpreferenceInt(Const.TAG_RATE, noOfTimes, this);
		}
		 
		 
		 
		 	analytics = GoogleAnalytics.getInstance(this);
		    analytics.setLocalDispatchPeriod(1800);

		    tracker = analytics.newTracker("UA-65691162-1"); // Replace with actual tracker/property Id
		    tracker.enableExceptionReporting(true);
		    tracker.enableAdvertisingIdCollection(true);
		    tracker.enableAutoActivityTracking(true);
	}

}
