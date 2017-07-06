package com.mindbees.expenditure.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.mindbees.expenditure.R;
import com.mindbees.expenditure.app.Expenditure;
import com.mindbees.expenditure.model.AllReminders;
import com.mindbees.expenditure.model.Category;
import com.mindbees.expenditure.model.ChartView;
import com.mindbees.expenditure.model.Countrys;
import com.mindbees.expenditure.model.ReminderSet;
import com.mindbees.expenditure.ui.CircularContactView;
import com.mindbees.expenditure.util.Tools;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterChartview extends BaseAdapter{
	
	Context context;
	ArrayList<ChartView> allList;
	
	
	Tools tools;
	String symbol;
	
    
	public AdapterChartview(Context context, ArrayList<ChartView> allList) {
		super();
		this.context = context;
		this.allList = allList;
		
		
		tools = new Tools(context);
		symbol = tools.getCurrency();
		
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
			v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_chartlist, null);
			holder = new ViewHolder();
			
			holder.tvReminderTitle = (TextView) v.findViewById(R.id.title);
			holder.tvDueDate = (TextView) v.findViewById(R.id.date);
			holder.tvBank = (TextView) v.findViewById(R.id.bank);
			holder.tvSymbol = (TextView) v.findViewById(R.id.symbol);
			holder.tvAmount = (TextView) v.findViewById(R.id.amount);
			holder.imageDate = (CircularContactView) v.findViewById(R.id.imgLeftDueDate);
			
			v.setTag(holder);
		}else{
			holder = (ViewHolder) v.getTag();
		}
		
		tools.showLog(allList.get(position).getCategory_title(), 1);
		
			holder.tvReminderTitle.setText(allList.get(position).getCategory_title());
				
			 holder.tvDueDate.setText(allList.get(position).getAction_date());
				
			 holder.tvBank.setText(allList.get(position).getAccount_title());
				
			 holder.tvSymbol.setText(symbol);
			 holder.tvAmount.setText(allList.get(position).getAmount());
				
			
			holder.imageDate.setTextAndBackgroundColor("", allList.get(position).getColor(), true);
		
		return v;
	}
	
	
	class ViewHolder {
		TextView tvReminderTitle, tvDueDate, tvBank, tvSymbol, tvAmount;
		CircularContactView imageDate;
	}

}
