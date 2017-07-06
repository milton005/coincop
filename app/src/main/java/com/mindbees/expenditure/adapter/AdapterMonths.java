package com.mindbees.expenditure.adapter;

import java.util.ArrayList;
import java.util.List;

import com.mindbees.expenditure.R;
import com.mindbees.expenditure.app.Expenditure;
import com.mindbees.expenditure.model.Category;
import com.mindbees.expenditure.model.Months;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

//public class AdapterMonths extends BaseAdapter{
//
//	Context context;
//	public List<Months> allList;
//
//	public AdapterMonths(Context context, List<Months> gridList) {
//		super();
//		this.context = context;
//		this.allList = gridList;
//
//	}
//
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return allList.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		View v = convertView;
//		ViewHolder holder;
//		if (v == null) {
//			v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_months, null);
//			holder = new ViewHolder();
//
//			holder.months = (TextView) v.findViewById(R.id.calendar_month);
//			holder.bg = (RelativeLayout) v.findViewById(R.id.bg);
//
//			v.setTag(holder);
//		}else{
//			holder = (ViewHolder) v.getTag();
//		}
//
//		holder.months.setText(allList.get(position).getMonthName());
//
//		if (allList.get(position).isChecked()) {
//			holder.bg.setBackgroundResource(R.drawable.month_bg_user_select);
//		}else {
//			holder.bg.setBackgroundResource(android.R.color.transparent);
//		}
//
//
//		return v;
//	}
//
//	class ViewHolder {
//		TextView months;
//		RelativeLayout bg;
//	}
//
//}
