/*package com.mindbees.expenditure;

import java.util.ArrayList;

import com.mindbees.expenditure.fragment.FragmentAddAccountCal;
import com.mindbees.expenditure.fragment.FragmentAddAccountList;
import com.mindbees.expenditure.fragment.FragmentDescription;
import com.mindbees.expenditure.fragment.FragmentReportDaily;
import com.mindbees.expenditure.fragment.FragmentReportMonthly;
import com.mindbees.expenditure.fragment.FragmentReportYearly;
import com.mindbees.expenditure.model.Category;
import com.mindbees.expenditure.util.Tools;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityReport extends AppCompatActivity{
	
	FragmentTabHost mtabHost;
	int whichTab;
	Tools tools;
	
	ImageView imgClose, imgSubmit;
	
	public int gridPosition = -1;
	public int gridPositionMonths = -1;
//	public int gridPositionYear = -1;
	Bundle bund;
	
	public ArrayList<Category> categoryImgList = new ArrayList<Category>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		
		
		bund = getIntent().getExtras();
		
		imgClose= (ImageView) findViewById(R.id.imgClose);
		
		mtabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mtabHost.setup(this, getSupportFragmentManager(),
				android.R.id.tabcontent);
		
		addViewTab(mtabHost);
		
//		mtabHost.setCurrentTab(whichTab);
		
		imgClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
	}
	
	private void addViewTab(FragmentTabHost mtabHost2) {
        // TODO Auto-generated method stub

        mtabHost2.addTab(
                mtabHost2.newTabSpec("tab1").setIndicator(getTabIndicatorDaily(R.drawable.daily)),
                FragmentReportDaily.class, bund);
        mtabHost2.addTab(
                mtabHost2.newTabSpec("tab2").setIndicator(getTabIndicatorMonthly( R.drawable.monthly)),
                FragmentReportMonthly.class, bund);

        mtabHost2.addTab(
                mtabHost2.newTabSpec("tab3").setIndicator(getTabIndicatorYearly(R.drawable.yearly)),
                FragmentReportYearly.class, bund);


    }
	
	private View getTabIndicatorDaily(int resId) {
        View tab = LayoutInflater.from(this).inflate(R.layout.inflate_tab_report_daily, null);
        LinearLayout im = (LinearLayout) tab.findViewById(R.id.imgTabReport);
        im.setBackgroundResource(resId);
        return tab;

    }
	private View getTabIndicatorMonthly(int resId) {
        View tab = LayoutInflater.from(this).inflate(R.layout.inflate_tab_report_monthly, null);
        LinearLayout im = (LinearLayout) tab.findViewById(R.id.imgTabReport);
        im.setBackgroundResource(resId);
        return tab;

    }
	private View getTabIndicatorYearly(int resId) {
        View tab = LayoutInflater.from(this).inflate(R.layout.inflate_tab_report_yearly, null);
        LinearLayout im = (LinearLayout) tab.findViewById(R.id.imgTabReport);
        im.setBackgroundResource(resId);
        return tab;

    }

}
*/