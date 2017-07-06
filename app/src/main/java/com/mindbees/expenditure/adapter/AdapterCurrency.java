package com.mindbees.expenditure.adapter;

import java.util.ArrayList;

import com.mindbees.expenditure.R;
import com.mindbees.expenditure.Interfaces.DeleteItem;
import com.mindbees.expenditure.app.Expenditure;
import com.mindbees.expenditure.database.CurrencySymbol;
import com.mindbees.expenditure.model.Accounts;
import com.mindbees.expenditure.model.Category;
import com.mindbees.expenditure.model.Countrys;
import com.mindbees.expenditure.ui.CircularContactView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterCurrency extends BaseAdapter{
	
	Context context;
	public ArrayList<CurrencySymbol> allList;
	DeleteItem mCallback;
	

	public AdapterCurrency(Context context, ArrayList<CurrencySymbol> allList) {
		super();
		this.context = context;
		this.allList = allList;
		
	}
	
	public void setCallback(Activity mCallback){
		this.mCallback = (DeleteItem) mCallback;
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
			v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_currency_layout, null);
			holder = new ViewHolder();
			
			holder.view = (CircularContactView) v.findViewById(R.id.dayLetter);
			holder.tvtitleCountry = (TextView) v.findViewById(R.id.textday);
			
			v.setTag(holder);
		}else{
			holder = (ViewHolder) v.getTag();
		}
		holder.view.setTextAndBackgroundColor(allList.get(position).getCrncySymbol(),context.getResources().getColor(R.color.cat_tab_color),false);
//		holder.tvtitle.setText(allList.get(position).getCrncySymbol());
		holder.tvtitleCountry.setText(allList.get(position).getCrncyCountry());
		
		if (allList.get(position).isSelected()) {
			mCallback.onDeleteItem(position);

			v.setBackgroundColor(context.getResources().getColor(R.color.divider));
		}else {
			v.setBackgroundColor(context.getResources().getColor(android.R.color.white));
		}
		
		return v;
	}
	
	class ViewHolder {
		CircularContactView view;
		TextView tvtitleCountry;
	}

}
