package com.mindbees.expenditure.adapter;

import java.util.ArrayList;

import com.mindbees.expenditure.R;
import com.mindbees.expenditure.app.Expenditure;
import com.mindbees.expenditure.database.MyDataBase;
import com.mindbees.expenditure.model.Category;
import com.mindbees.expenditure.model.Countrys;
import com.mindbees.expenditure.model.StatusAccount;
import com.mindbees.expenditure.ui.CircularContactView;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterAcntStats extends BaseAdapter{
	
	Context context;
	ArrayList<StatusAccount> allList;
	private int[] PHOTO_TEXT_BACKGROUND_COLORS;
	boolean fromAccount;
//	Tools tools;
	String symbol;
	

	public AdapterAcntStats(Context context, ArrayList<StatusAccount> allList, boolean fromAccount) {
		super();
		this.context = context;
		this.allList = allList;
		PHOTO_TEXT_BACKGROUND_COLORS = context.getResources().getIntArray(R.array.contacts_text_background_colors);
//		tools = new Tools(context);
//		symbol = tools.getCurrency();
		this.fromAccount = fromAccount;
		symbol = BaseActivity.getpreference(Const.TAG_SYMBOL, context);
		
		
		
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
			v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_acnt_status, null);
			holder = new ViewHolder();
			
			holder.tvAcntName = (TextView) v.findViewById(R.id.acnt_name);
			holder.tvamount = (TextView) v.findViewById(R.id.amount);
			holder.tvSymbol = (TextView) v.findViewById(R.id.symbol);
			holder.acntIcon = (CircularContactView) v.findViewById(R.id.statusIcon);
			
			v.setTag(holder);
		}else{
			holder = (ViewHolder) v.getTag();
		}
		
		 final int backgroundColorToUse=PHOTO_TEXT_BACKGROUND_COLORS[position % PHOTO_TEXT_BACKGROUND_COLORS.length];
		
		holder.tvAcntName.setText(allList.get(position).getAccount_tittle());
		if (fromAccount) {
			holder.tvamount.setText(allList.get(position).getInitial_amount());
		}else {
			holder.tvamount.setText(allList.get(position).getFinal_balance());
		}
		holder.acntIcon.getTextView().setTextColor(context.getResources().getColor(R.color.white));
		holder.tvSymbol.setText(symbol);
		holder.acntIcon.setTextAndBackgroundColor(allList.get(position).getNumber()+"", backgroundColorToUse, false);
		
		return v;
	}
	
	
	class ViewHolder {
		TextView tvAcntName, tvamount, tvSymbol;
		CircularContactView acntIcon;
	}

}
