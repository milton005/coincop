package com.mindbees.expenditure.util;

import java.util.HashMap;

import com.mindbees.expenditure.ActivityRegister;
import com.mindbees.expenditure.database.CurrencySymbol;
import com.mindbees.expenditure.database.MyDataBase;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by tony on 2/5/15.
 */
public class BaseActivity extends AppCompatActivity {

    public static void setpreferenceBoolean(String key, boolean value, Context activity) {
        SharedPreferences spns = activity.getSharedPreferences(Const.TAG_SET_PREF, MODE_PRIVATE);
        SharedPreferences.Editor e = spns.edit();
        e.putBoolean(key, value);
        e.commit();

    }

    public static boolean getpreferenceBoolean(String key, Context activity) {
        SharedPreferences spns = activity.getSharedPreferences(Const.TAG_SET_PREF, MODE_PRIVATE);
        return spns.getBoolean(key, false);
    }
    
    public static void setpreferenceInt(String key, int value, Context activity) {
        SharedPreferences spns = activity.getSharedPreferences(Const.TAG_SET_PREF, MODE_PRIVATE);
        SharedPreferences.Editor e = spns.edit();
        e.putInt(key, value);
        e.commit();

    }
    public void showSnackBar(String msg, boolean isSuccess){

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT);
        View view  = snackbar.getView();
        TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        if (isSuccess){
            view.setBackgroundColor(Color.GREEN);
        }else {
            view.setBackgroundColor(Color.RED);
        }

        snackbar.show();

//        Snackbar.make(context.findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show();
    }
    public static int getpreferenceInt(String key, Context activity) {
        SharedPreferences spns = activity.getSharedPreferences(Const.TAG_SET_PREF, MODE_PRIVATE);
        return spns.getInt(key, 0);
    }

    public static void setpreference(String key, String value, Context activity) {
        SharedPreferences spns = activity.getSharedPreferences(Const.TAG_SET_PREF, MODE_PRIVATE);
        SharedPreferences.Editor e = spns.edit();
        e.putString(key, value);
        e.commit();

    }


    public static String getpreference(String key, Context activity) {
        SharedPreferences spns = activity.getSharedPreferences(Const.TAG_SET_PREF, MODE_PRIVATE);
        return spns.getString(key, "no-data");
    }
    
    public static void setpreferenceAlarm(String key, int value, Context activity) {
        SharedPreferences spns = activity.getSharedPreferences(Const.TAG_SET_PREF, MODE_PRIVATE);
        SharedPreferences.Editor e = spns.edit();
        e.putInt(key, value);
        e.commit();

    }
    public static int getpreferenceAlarm(String key, Context activity) {
        SharedPreferences spns = activity.getSharedPreferences(Const.TAG_SET_PREF, MODE_PRIVATE);
        return spns.getInt(key, 0);
    }
    

	public void setupUI(View view) {
		 
	    //Set up touch listener for non-text box views to hide keyboard. 
		 if(!(view instanceof EditText)) {
			 
		        view.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						Tools.hideSoftKeyboard(BaseActivity.this); 
		                return false; 
					} 
		 
		        }); 
		    } 
	 
	    //If a layout container, iterate over children and seed recursion. 
		 if (view instanceof ViewGroup) {
			 
		        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
		 
		            View innerView = ((ViewGroup) view).getChildAt(i);
		 
		            setupUI(innerView);
		        } 
		    } 
	} 
	
	
	
    public void addCurrency(){
    	
    	MyDataBase db = new MyDataBase(this);
    	SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
    	db.openPermission(sqLiteDatabase);
        db.addCurrency(new CurrencySymbol("AUD", "Australia", "$"));
        db.addCurrency(new CurrencySymbol("CAD", "Canada", "$"));
        db.addCurrency(new CurrencySymbol("CNY", "China", "¥"));
        db.addCurrency(new CurrencySymbol("CUP", "Cuba", "₱"));
    	db.addCurrency(new CurrencySymbol("EUR", "Euro Member", "€"));
        db.addCurrency(new CurrencySymbol("HKD", "Hong Kong", "$"));
    	db.addCurrency(new CurrencySymbol("INR", "India", "₹"));
    	db.addCurrency(new CurrencySymbol("JPY", "Japan", "¥"));
        db.addCurrency(new CurrencySymbol("KPW", "Korea (North)", "₩"));
        db.addCurrency(new CurrencySymbol("KRW", "Korea (South)", "₩"));
        db.addCurrency(new CurrencySymbol("MXN", "Mexico", "$"));
        db.addCurrency(new CurrencySymbol("SGD", "Singapore", "$"));
    	db.addCurrency(new CurrencySymbol("GBP", "United Kingdom", "£"));
        db.addCurrency(new CurrencySymbol("USD", "United States", "$"));


    	
    	db.closePermission();
    	
		setpreference(Const.TAG_SYMBOL, db.getSymbol(BaseActivity.getpreference(Const.TAG_CURRENCY_ID, this)), this);

    	
    }

}
