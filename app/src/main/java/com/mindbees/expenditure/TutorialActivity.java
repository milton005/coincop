package com.mindbees.expenditure;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.ViewConfiguration;

import com.mindbees.expenditure.Interfaces.DeleteItem;
import com.mindbees.expenditure.Interfaces.itemclick;
import com.mindbees.expenditure.R;
import com.mindbees.expenditure.adapter.AdapterTutorial;
import com.mindbees.expenditure.util.BaseActivity;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by User on 21-02-2017.
 */

public class TutorialActivity extends BaseActivity implements itemclick {
    private AdapterTutorial adapterTutorial;
    private ViewPager viewPager;
    private CirclePageIndicator circlePageIndicator;
    private ViewPager mViewPager;
    private int mCurrentState = -1;
    private static int INTERVAL = ViewConfiguration.getLongPressTimeout();
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        initUi();
    }

    private void initUi() {
        viewPager = (ViewPager) findViewById(R.id.pager);
        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.circlePageIndicator);
        adapterTutorial = new AdapterTutorial(this, getDrawerImages());
        adapterTutorial.setCallback(this);
        viewPager.setAdapter(adapterTutorial);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mCurrentState = state;
                if (viewPager.getCurrentItem() == 4) {
                    if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                        mHandler.postDelayed(mRun, INTERVAL);
                    }
                }
            }
        });

//       circlePageIndicator.setViewPager(viewPager);

    }
    private Runnable mRun = new Runnable() {

        @Override
        public void run() {
            // we got the Runnable to be executed. If we are on the second page
            // and the user let go of the ViewPager in our time frame then start
            // the Activity(also cancel the dozen Runnables that were posted)
            if (mCurrentState == ViewPager.SCROLL_STATE_IDLE
                    && viewPager.getCurrentItem() == 4) {
                mHandler.removeCallbacks(mRun);// or always set it to run
                finish();

            }
        }
    };
    private TypedArray getDrawerImages() {
        return getResources().obtainTypedArray(R.array.tutorial_screens);
    }



    @Override
    public void OnItemClick(int psition) {
        finish();
    }
}
