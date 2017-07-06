package com.mindbees.expenditure.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.mindbees.expenditure.R;
import com.mindbees.expenditure.R.id;
import com.mindbees.expenditure.model.ChartView;
import com.mindbees.expenditure.model.ModelReport;
import com.mindbees.expenditure.ui.CircularContactView;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AdapterExapndList extends BaseExpandableListAdapter {
	
	List<String> headerTitle;
	HashMap<String, List<ChartView>> childDatas;
	
	private Context context;
	private int[] PHOTO_TEXT_BACKGROUND_COLORS;
//	Tools tools;
	boolean isCategory;
	String symbol;
	

	public AdapterExapndList(List<String> headerTitle,
			HashMap<String, List<ChartView>> childDatas, Context context, boolean isCategory) {
		super();
		this.headerTitle = headerTitle;
		this.childDatas = childDatas;
		this.context = context;
		this.isCategory = isCategory;
		
		PHOTO_TEXT_BACKGROUND_COLORS = context.getResources().getIntArray(R.array.contacts_text_background_colors);
//		tools = new Tools(context);
		symbol = BaseActivity.getpreference(Const.TAG_SYMBOL, context);
	}
	
	public List<ChartView> getEachList(String month){
		
		return childDatas.get(month);
		
	}
	public String getHeader(int groupPosition){
		
		return this.headerTitle.get(groupPosition);
		
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return this.headerTitle.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		 return this.childDatas.get(this.headerTitle.get(groupPosition))
	                .size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		  return this.headerTitle.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return this.childDatas.get(this.headerTitle.get(groupPosition))
                .get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 String headerTitle = (String) getGroup(groupPosition);
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) this.context
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.inflate_list_header, null);
	        }
	 
	        TextView lblListHeader = (TextView) convertView
	                .findViewById(R.id.testHeader);
//	        lblListHeader.setTypeface(null, Typeface.BOLD);
	        lblListHeader.setText(headerTitle);
	 
	        return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ChartView report = (ChartView) getChild(groupPosition, childPosition);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.inflate_expnd_item, null);
		}
	
		CircularContactView view = (CircularContactView) convertView.findViewById(R.id.dayLetter);
		TextView tvDay = (TextView) convertView.findViewById(R.id.textday);
		TextView tvCat = (TextView) convertView.findViewById(R.id.textCategory);
		TextView tvBnk = (TextView) convertView.findViewById(R.id.textbank);
		TextView amount2 = (TextView) convertView.findViewById(R.id.amount2);
		TextView symbol2 = (TextView) convertView.findViewById(R.id.symbol2);
		
		/*RelativeLayout lay1 = (RelativeLayout) convertView.findViewById(id.lay1);
		RelativeLayout lay2 = (RelativeLayout) convertView.findViewById(id.lay2);
		RelativeLayout lay3 = (RelativeLayout) convertView.findViewById(id.lay3);*/
		
		
		/*TextView amount1 = (TextView) convertView.findViewById(R.id.amount1);
		TextView symbol1 = (TextView) convertView.findViewById(R.id.symbol1);
		TextView amount3 = (TextView) convertView.findViewById(R.id.amount3);
		TextView symbol3 = (TextView) convertView.findViewById(R.id.symbol3);*/
		
		final int backgroundColorToUse=PHOTO_TEXT_BACKGROUND_COLORS[childPosition % PHOTO_TEXT_BACKGROUND_COLORS.length];
		
		
//		tvCat.setText(report.getCategory_title());
		if (isCategory) {
			
			tvBnk.setVisibility(View.GONE);
			tvDay.setText(report.getCategory_title());
			view.setTextAndBackgroundColor(getFirstLetter(report.getCategory_title()), backgroundColorToUse, false);
			
			if (report.getType_id().contentEquals(Const.TAG_EXPENSE_ID)) {
				tvCat.setText("Expense");
			}else {
				tvCat.setText("Income");
			}
//			amount2.setText(report.getAmount());
//			symbol2.setText(symbol);
			
		}else {
			tvBnk.setVisibility(View.VISIBLE);
			List<String> temList = seperateDate(report.getAction_date());
//			String day = getDay(temList);
			String day = getDayy(report.getAction_date());
			tvDay.setText(report.getCategory_title());
			view.setTextAndBackgroundColor(getFirstLetter(report.getCategory_title()), backgroundColorToUse, false);
			tvCat.setText(temList.get(2)+" "+day);
			tvBnk.setText(report.getAccount_title());
//			amount2.setText(report.getAmount());
//			symbol2.setText(symbol);
		}
		
		amount2.setText(report.getAmount());
		symbol2.setText(symbol);
		
		
		
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
	private List<String> seperateDate(String date){
		List<String> elephantList = Arrays.asList(date.split("-"));
		return elephantList;
	}
	private String getDay(List<String> date){
		
		Calendar cal = Calendar.getInstance();
		String dayOfTheWeek = "";
		try {
			cal.set(Integer.parseInt(date.get(0)), Integer.parseInt(date.get(1)), Integer.parseInt(date.get(2)), 00, 00, 00);
			dayOfTheWeek = (String) android.text.format.DateFormat.format("EEEE", cal.getTimeInMillis());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return dayOfTheWeek;
		
	}
	
	private String getDayy(String date){
		
		String goal = "";
		SimpleDateFormat inFormat;
		SimpleDateFormat outFormat;
		Date a;
		try {
			inFormat = new SimpleDateFormat("yyyy-MM-dd");
			a = inFormat.parse(date);
			outFormat = new SimpleDateFormat("EEEE");
			goal = outFormat.format(a);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return goal;
	}
	private String getFirstLetter(String day){
		String firstLetter = day.substring(0,1);
		firstLetter = firstLetter.toUpperCase();
		return firstLetter;
		
	}

}
