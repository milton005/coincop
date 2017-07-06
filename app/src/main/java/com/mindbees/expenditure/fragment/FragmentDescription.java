package com.mindbees.expenditure.fragment;

import com.mindbees.expenditure.ActivityAddExpense_Income;
import com.mindbees.expenditure.ActivityCredit_Debit;
import com.mindbees.expenditure.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class FragmentDescription extends Fragment{
	
	EditText description;
	ActivityCredit_Debit activity;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity = (ActivityCredit_Debit) activity;
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
		View v = inflater.inflate(R.layout.fragment_cat_desc, null);
		
		description = (EditText) v.findViewById(R.id.description);
		activity.edFromDescription = description;
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		activity.desc = description.getText().toString().trim();
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (activity.desc != "") {
			description.setText(activity.desc);
		}
	}

}
