package com.mindbees.expenditure.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by tony on 24/4/15.
 */
public class Tools {
    Context context;
    private String TAG_LOG = "expenditure";


    public Tools(Context context) {

        this.context = context;
    }


    public  boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean isConnectingToInternet(){

        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }
    
    public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list =
			packageManager.queryIntentActivities(intent,
					PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

    public final static  String throwableToString(Throwable t) {
        if (t == null)
            return null;

        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
    public final static  void debugThrowable( Throwable t) {
        if (t != null) {
            Log.e("response", "AsyncHttpClient returned error", t);
            throwableToString(t);
        }
    }
    public final static  void debugResponse( String response) {
        if (response != null) {
            //Log.d(TAG, "Response data:");
            Log.e("response", response);
        }
    }





    public void showToastMessage(String paramString)
    {
        Toast localToast = Toast.makeText(context, paramString, Toast.LENGTH_SHORT);
        localToast.setGravity(16, 0, 0);
        localToast.show();
    }

    public void showLog(String msg, int color){
        if (color == 0){
            Log.v(TAG_LOG, msg);
        }else if(color == 1){
            Log.e(TAG_LOG, msg);
        }else if(color == 2){
            Log.i(TAG_LOG, msg);
        }else if(color == 3){
            Log.d(TAG_LOG, msg);
        }
    }


    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public void composeEmail(String[] addresses) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle
        // this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        // intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
    /*public void composeEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle
        // this
//        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        // intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }*/

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
    
    public String getCurrency(){
    	Currency currency = Currency.getInstance(Locale.getDefault());
    	String symbol = currency.getSymbol(Locale.getDefault());
    	
    	return symbol;
    }
    public String getCurrencyCode(){
    	Currency currency = Currency.getInstance(Locale.getDefault());
    	String symbol = currency.getCurrencyCode();
    	
    	return symbol;
    }

    
    public static  void hideSoftKeyboard(Activity activity) {
        try {
			InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } 
    
    public static File createFile(Context context){
    	File mFileTemp;
    	String state = Environment.getExternalStorageState();
    	if (Environment.MEDIA_MOUNTED.equals(state)) {
    		mFileTemp = new File(Environment.getExternalStorageDirectory(), Const.TEMP_PHOTO_FILE);
    	}
    	else {
    		mFileTemp = new File(context.getFilesDir(), Const.TEMP_PHOTO_FILE);
    	}
    	return mFileTemp;
    }


}
