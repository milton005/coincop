package com.mindbees.expenditure;

import com.mindbees.expenditure.fragment.FragmentReportCategryExpense;
import com.mindbees.expenditure.fragment.FragmentReportCategryIncome;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityReportCategoryView extends AppCompatActivity{
	
	FragmentTabHost mtabHost;
	
	Bundle bund;
	ImageView imgClose;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_categoryview);
		
		bund = getIntent().getExtras();
		
		mtabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mtabHost.setup(this, getSupportFragmentManager(),
				android.R.id.tabcontent);

		addViewTab(mtabHost);
		
		findViewById(R.id.backBtn).setOnClickListener(new OnClickListener() {
			
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
				mtabHost2.newTabSpec("tab1").setIndicator(
						getTabIndicator("INCOME")),
				FragmentReportCategryIncome.class, bund);

		mtabHost2.addTab(
				mtabHost2.newTabSpec("tab2").setIndicator(
						getTabIndicator("EXPENSE")),
				FragmentReportCategryExpense.class, bund);
	}

	private View getTabIndicator(String text) {
		View tab = LayoutInflater.from(this).inflate(
				R.layout.inflate_tab_cat, null);
		TextView textt = (TextView) tab.findViewById(R.id.text_tab1);
		textt.setText(text);
		return tab;

	}

}

