package com.mindbees.expenditure.adapter;

import java.util.ArrayList;

import com.mindbees.expenditure.R;
import com.mindbees.expenditure.app.Expenditure;
import com.mindbees.expenditure.model.Category;
import com.mindbees.expenditure.model.Countrys;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterCountry extends BaseAdapter{
	
	Context context;
	ArrayList<Countrys> allList;
	
	

	public AdapterCountry(Context context, ArrayList<Countrys> allList) {
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
			v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, null);
			holder = new ViewHolder();
			
			holder.tvtitle = (TextView) v.findViewById(R.id.txt_item);
//			holder.imgShop = (ImageView) v.findViewById(R.id.categryIcon);
			
			v.setTag(holder);
		}else{
			holder = (ViewHolder) v.getTag();
		}
		
		holder.tvtitle.setText(allList.get(position).getCountry_name());
		v.setTag(R.id.txt_item,String.valueOf(allList.get(position).getId()));

		
		
		return v;
	}
	
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		ViewHolder holder;
		if (v == null) {
			holder = new ViewHolder();
			v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, null);

			holder.tvtitle = (TextView) v
					.findViewById(R.id.txt_item);
			v.setTag(holder);
		} else {

			holder = (ViewHolder) v.getTag();

		}

		holder.tvtitle.setText(allList.get(position).getCountry_name());
		v.setTag(R.id.txt_item,String.valueOf(allList.get(position).getId()));

		return v;
	}

	
	class ViewHolder {
		TextView tvtitle;
	}

}
