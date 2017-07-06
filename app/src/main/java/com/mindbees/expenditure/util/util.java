package com.mindbees.expenditure.util;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by User on 06-10-2016.
 */

public class util {
    private static util instance;

    public void showSnackBar(String msg, boolean isSuccess, View contentView){

        try {
            Snackbar snackbar = Snackbar.make(contentView, msg, Snackbar.LENGTH_SHORT);
            View view  = snackbar.getView();
            TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            if (isSuccess){
                view.setBackgroundColor(Color.GRAY);
            }else {
                view.setBackgroundColor(Color.RED);
            }

            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
