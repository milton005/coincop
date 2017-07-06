package com.mindbees.expenditure.adapter;

import java.util.List;

import com.mindbees.expenditure.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class Adapteryear extends BaseAdapter{
	
	public List<String> list ;
	Context _activity;
	LayoutInflater inflator;
	
	

	public Adapteryear(Context activity,
			List<String> region_list) {
		// TODO Auto-generated constructor stub
		this.list = region_list;
		this._activity = activity;
		inflator = LayoutInflater.from(_activity);
		
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub

		View view_1 = arg1;
		ViewHolder view_holdr;
		if (arg1 == null) {
			view_holdr = new ViewHolder();
			view_1 = inflator.inflate(R.layout.inflate_year, null);

			view_holdr.tv_option = (TextView) view_1
					.findViewById(R.id.textDistrict);
			view_1.setTag(view_holdr);
		} else {

			view_holdr = (ViewHolder) view_1.getTag();

		}

		view_holdr.tv_option.setText(list.get(arg0));
		view_1.setTag(R.id.textDistrict, list.get(arg0));

		return view_1;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view_1 = convertView;
		ViewHolder view_holdr;
		if (view_1 == null) {
			view_holdr = new ViewHolder();
			view_1 = inflator.inflate(R.layout.inflate_view_spinner, null);

			view_holdr.tv_option = (TextView) view_1
					.findViewById(R.id.textDistrict);
			view_1.setTag(view_holdr);
		} else {

			view_holdr = (ViewHolder) view_1.getTag();

		}

		view_holdr.tv_option.setText(list.get(position));
		view_1.setTag(R.id.textDistrict, list.get(position));

		return view_1;
	}

	class ViewHolder{
		TextView tv_option;
	}

}
