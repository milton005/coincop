package com.mindbees.expenditure.adapter;

import java.util.ArrayList;

import com.mindbees.expenditure.R;
import com.mindbees.expenditure.app.Expenditure;
import com.mindbees.expenditure.model.Category;
import com.mindbees.expenditure.model.Countrys;
import com.mindbees.expenditure.model.ReminderSet;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterReminderList extends BaseAdapter{
	
	Context context;
	ArrayList<ReminderSet> allList;
	
	

	public AdapterReminderList(Context context, ArrayList<ReminderSet> allList) {
		super();
		this.context = context;
		this.allList = allList;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return allList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		ViewHolder holder;
		if (v == null) {
			v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_reminder_list, null);
			holder = new ViewHolder();
			
			holder.tvtitle = (TextView) v.findViewById(R.id.txt_item);
			holder.tvDate = (TextView) v.findViewById(R.id.txt_date);
			
			v.setTag(holder);
		}else{
			holder = (ViewHolder) v.getTag();
		}
		
		holder.tvtitle.setText(allList.get(position).getDesc());
		holder.tvDate.setText(allList.get(position).getDate()+","+allList.get(position).getTime());
		
		return v;
	}
	
	
	class ViewHolder {
		TextView tvtitle, tvDate;
	}

}
