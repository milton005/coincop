package com.mindbees.expenditure.fragment;

import com.mindbees.expenditure.ActivityReportCategory;
import com.mindbees.expenditure.ActivityReportCategoryView;
import com.mindbees.expenditure.ActivityStatus;
import com.mindbees.expenditure.ActivityReportTwo;
import com.mindbees.expenditure.R;
import com.mindbees.expenditure.Interfaces.ImageVisibility;
import com.mindbees.expenditure.util.Const;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class fragmentReportTwo extends Fragment implements OnClickListener{

	public static final String TAG ="fragment_report_two" ;
	LinearLayout btnIncome, btnexpense, btnStatus, btnCategory;
	ImageView topview;
	
	ImageVisibility mCallback;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			mCallback = (ImageVisibility) activity;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ClassCastException(activity.toString()
                    + " must implement Listener");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v = inflater.inflate(R.layout.fragment_report_two,container, false);
		
		mCallback.isVisible(Const.TAG_REPRT);
		
		btnIncome = (LinearLayout) v.findViewById(R.id.layoutIncome);
		btnexpense = (LinearLayout) v.findViewById(R.id.layoutExpense);
		btnStatus = (LinearLayout) v.findViewById(R.id.layoutStatus);
		btnCategory = (LinearLayout) v.findViewById(R.id.layoutCategory);
		topview= (ImageView) v.findViewById(R.id.topimage);
		topview.setOnClickListener(this);
		btnIncome.setOnClickListener(this);
		btnexpense.setOnClickListener(this);
		btnStatus.setOnClickListener(this);
		btnCategory.setOnClickListener(this);
		
		return v;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btnIncome) {
			moveToReportTab(false, Const.TAG_INCOME_ID);
//			Intent i = new Intent(getActivity(), ActivityReportCategory.class);
//			startActivity(i);
		}else if (v == btnexpense) {
			moveToReportTab(false, Const.TAG_EXPENSE_ID);
		}else if (v == btnStatus) {
			Intent i = new Intent(getActivity(), ActivityStatus.class);
			startActivity(i);
		}else if (v == btnCategory) {
			moveToReportTab(true, Const.TAG_CAT_ID);
		}
		else if(v==topview)
		{
//			Intent i=new Intent(getActivity(), ActivityReportCategoryView.class);
//			startActivity(i);
		}
		
	}
	void moveToReportTab(boolean b, String id){
		Bundle bund = new Bundle();
		bund.putBoolean("isCategory", b);
		bund.putString("type", id);

		Intent i = new Intent(getActivity(), ActivityReportCategory.class);
		i.putExtras(bund);
		startActivity(i);
	}

}
