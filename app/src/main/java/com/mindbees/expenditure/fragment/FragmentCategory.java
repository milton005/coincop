package com.mindbees.expenditure.fragment;

import java.util.ArrayList;

import com.mindbees.expenditure.ActivityAddAccount;
import com.mindbees.expenditure.ActivityCreateCategory;
import com.mindbees.expenditure.ActivityCreateCategoryTwo;
import com.mindbees.expenditure.ActivitySelectType;
import com.mindbees.expenditure.MainActivity;
import com.mindbees.expenditure.R;
import com.mindbees.expenditure.Interfaces.ImageVisibility;
import com.mindbees.expenditure.model.Category;
import com.mindbees.expenditure.ui.FloatingActionButton;
import com.mindbees.expenditure.ui.circular.SupportAnimator;
import com.mindbees.expenditure.ui.circular.ViewAnimationUtils;
import com.mindbees.expenditure.util.Const;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

public class FragmentCategory extends Fragment {
	public static final String TAG = "fragment_category";

//	ImageVisibility mCallback;

	FragmentTabHost mtabHost;
	MainActivity activity;
	
	FloatingActionButton fab;
	Animation anim_fab;
	
	/*
	 * main animation
	 */
//	FrameLayout parentview;
//	private SupportAnimator mAnimator;
	
//	public ArrayList<Category> allCategoryIncome = new ArrayList<Category>();
//	public ArrayList<Category> allCategoryExpense = new ArrayList<Category>();

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity = (MainActivity) activity;
		/*try {
			mCallback = (ImageVisibility) activity;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ClassCastException(activity.toString()
					+ " must implement Listener");
		}*/
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_category,container, false);

//		mCallback.isVisible(Const.TAG_CATGS);

		
		anim_fab = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_btn_zoom);
		fab = (FloatingActionButton) v.findViewById(R.id.fab);
		mtabHost = (FragmentTabHost) v.findViewById(android.R.id.tabhost);

		mtabHost.setup(getActivity(), getChildFragmentManager(),
				android.R.id.tabcontent);

		addViewTab(mtabHost);
		try
		{ Bundle extras=getArguments();
			final int fragment_Reminder_type=extras.getInt(Const.FRAGMENT_REMINDER_TYPE,0);
			mtabHost.setCurrentTab(fragment_Reminder_type);
		}catch (Exception e){}

		return v;
	}
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		fab.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fab.setVisibility(View.GONE);
				startActivity(new Intent(getActivity(), ActivityCreateCategoryTwo.class));
			}
		});
		
		
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initializeTimerTask();
	}
	
	 public void initializeTimerTask() { 
	
	
	final Handler handler = new Handler(); 
	handler.postDelayed(new Runnable() { 
	    @Override 
	    public void run() { 
	        // Do something after 5s = 5000ms 
	    	fab.setVisibility(View.VISIBLE);
	    	fab.startAnimation(anim_fab);
	    } 
	}, Const.TAG_TIMER_DELAY); 
}
	
	 static float hypo(int a, int b){
	        return (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
	    }

	private void addViewTab(FragmentTabHost mtabHost2) {
		// TODO Auto-generated method stub

		mtabHost2.addTab(

				mtabHost2.newTabSpec("tab1").setIndicator(
						getTabIndicator("INCOME")),
				FragmentCategoryIncome.class, null);

		mtabHost2.addTab(
				mtabHost2.newTabSpec("tab2").setIndicator(
						getTabIndicator("EXPENSE")),
				FragmentCategoryExpense.class, null);
	}

	private View getTabIndicator(String text) {
		View tab = LayoutInflater.from(activity).inflate(
				R.layout.inflate_tab_cat, null);
		TextView textt = (TextView) tab.findViewById(R.id.text_tab1);
		textt.setText(text);
		return tab;

	}

}
