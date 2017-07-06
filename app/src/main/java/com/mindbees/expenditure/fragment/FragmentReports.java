/*package com.mindbees.expenditure.fragment;

import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardUI;
import com.mindbees.expenditure.ActivityReport;
import com.mindbees.expenditure.ActivityStatus;
import com.mindbees.expenditure.R;
import com.mindbees.expenditure.Interfaces.ImageVisibility;
import com.mindbees.expenditure.cardview.MyCardView;
import com.mindbees.expenditure.util.Const;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class FragmentReports extends Fragment{
	
	private CardUI mCardView;
	
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
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_reports, null);
		
		mCallback.isVisible(Const.TAG_REPRT);
		
		mCardView = (CardUI) v.findViewById(R.id.cardsview);
		mCardView.setSwipeable(false);
		
		
		CardStack stackPlay = new CardStack();
//		stackPlay.setTitle("GOOGLE PLAY CARDS");
		mCardView.addStack(stackPlay);
		
		MyCardView cardIncomme = new MyCardView("Income",getActivity().getResources().getColor(R.color.yellow), R.drawable.report_img_income);
		
		mCardView.addCardToLastStack(cardIncomme);

		MyCardView cardExpenditure = new MyCardView("Expenditure",getActivity().getResources().getColor(R.color.orange), R.drawable.report_img_expenditure);

		mCardView.addCardToLastStack(cardExpenditure);

		MyCardView cardCategory = new MyCardView("Category",getActivity().getResources().getColor(R.color.green), R.drawable.report_img_category);

		
		mCardView.addCardToLastStack(cardCategory);
		
		MyCardView cardStatus = new MyCardView("Account Status",getActivity().getResources().getColor(R.color.light_green), R.drawable.report_img_income);


		mCardView
				.addCardToLastStack(cardStatus);

		// draw cards
		mCardView.refresh();
		
		
		cardIncomme.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				moveToReportTab(false, Const.TAG_INCOME_ID);
				
			}
		});
		cardExpenditure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				moveToReportTab(false, Const.TAG_EXPENSE_ID);

			}
		});
		cardCategory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				moveToReportTab(true, Const.TAG_CAT_ID);
			}
		});
		cardStatus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i = new Intent(getActivity(), ActivityStatus.class);
				startActivity(i);
				
			}
		});
		
		
		return v;
	}
	
	void moveToReportTab(boolean b, String id){
		Bundle bund = new Bundle();
		bund.putBoolean("isCategory", b);
		bund.putString("type", id);

		Intent i = new Intent(getActivity(), ActivityReport.class);
		i.putExtras(bund);
		startActivity(i);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

}
*/