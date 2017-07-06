package com.mindbees.expenditure.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.mindbees.expenditure.ActivityCredit_Debit;
import com.mindbees.expenditure.ActivityAddExpense_Income;
import com.mindbees.expenditure.R;
import com.mindbees.expenditure.model.CalendarE;
import com.mindbees.expenditure.ui.FloatingActionButton;
import com.mindbees.expenditure.ui.FloatingActionsMenu;
import com.mindbees.expenditure.util.Tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentAddAccountCal extends Fragment implements OnClickListener{
	
//	private Button selectedDayMonthYearButton;
    private TextView currentMonth;
    private ImageButton prevMonth;
    private ImageButton nextMonth;
    private GridView calendarView;
    private GridCellAdapter adapter;
    private Calendar _calendar;
    private int month, year;
    private static final String dateTemplate = "MMMM yyyy";
    String flag ="abc";
    String date_month_year;
    
    GridView calendarWeek;
    private final String[] week = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    
    Tools tools;
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
		View v = inflater.inflate(R.layout.fragment_cat_cal, null);
		
		
		_calendar = Calendar.getInstance(Locale.getDefault());
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);
        
        tools = new Tools(getActivity());
      
      
        calendarWeek = (GridView) v.findViewById(R.id.calendar_week);
        prevMonth = (ImageButton) v.findViewById(R.id.prevMonth);
        prevMonth.setOnClickListener(this);
        
        currentMonth = (TextView) v.findViewById(R.id.curntMonth);
        currentMonth.setText(DateFormat.format(dateTemplate, _calendar.getTime()));
        
        nextMonth = (ImageButton) v.findViewById(R.id.nextMonth);
        nextMonth.setOnClickListener(this);
        
        calendarView = (GridView) v.findViewById(R.id.calendar);
        
        calendarWeek.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.inflate_week, week));
        
     // Initialised
        adapter = new GridCellAdapter(getActivity().getApplicationContext(), R.id.calendar_day_gridcell, month, year, false);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
		
		
		return v;
	}
	
	
	
	private void setGridCellAdapterToDate(int month, int year){
        adapter = new GridCellAdapter(getActivity().getApplicationContext(), R.id.calendar_day_gridcell, month, year, true);
        _calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
        currentMonth.setText(DateFormat.format(dateTemplate, _calendar.getTime()));
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if (v == prevMonth){
            if (month <= 1){
                month = 12;
                year--;
            }
            else
                month--;
            setGridCellAdapterToDate(month, year);
        }
        if (v == nextMonth){
            if (month > 11){
                month = 1;
                year++;
            }
            else
                month++;
            setGridCellAdapterToDate(month, year);
        }
		
	}
	
	
	
	public class GridCellAdapter extends BaseAdapter 
    {
        private final Context _context;

//        private final List<CalendarE> list;
        private static final int DAY_OFFSET = 1;
        private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        private int daysInMonth;
        private int currentDayOfMonth;
        private int currentWeekDay;
        private TextView gridcell;
        private TextView num_events_per_day;
        RelativeLayout bg;
        private final HashMap<String, Integer> eventsPerMonthMap;
        Calendar calendar;

        // Days in Current Month
        public GridCellAdapter(Context context, int textViewResourceId, int month, int year, boolean wantToRefresh){
                super();
                this._context = context;
//                this.list = new ArrayList<CalendarE>();
                calendar = Calendar.getInstance();
                setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
                setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));

                
                // Print Month
                if (activity.gridList.size() == 0 || wantToRefresh) {
                	printMonth(month, year);
				}
                

                // Find Number of Events
                eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
            }
        private String getMonthAsString(int i){
                return months[i];
            }

        private int getNumberOfDaysOfMonth(int i){
                return daysOfMonth[i];
            }

        public CalendarE getItem(int position){
                return activity.gridList.get(position);
            }

        @Override
        public int getCount(){
                return activity.gridList.size();
            }

        private void printMonth(int mm, int yy){
                int trailingSpaces = 0;
                int daysInPrevMonth = 0;
                int prevMonth = 0;
                int prevYear = 0;
                int nextMonth = 0;
                int nextYear = 0;

                int currentMonth = mm - 1;
                daysInMonth = getNumberOfDaysOfMonth(currentMonth);


                activity.gridList.clear();
                // Gregorian Calendar : MINUS 1, set to FIRST OF MONTH
                GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);

                if (currentMonth == 11){
                        prevMonth = currentMonth - 1;
                        daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                        nextMonth = 0;
                        prevYear = yy;
                        nextYear = yy + 1;
                    }
                else if (currentMonth == 0){
                        prevMonth = 11;
                        prevYear = yy - 1;
                        nextYear = yy;
                        daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                        nextMonth = 1;
                    }
                else{
                        prevMonth = currentMonth - 1;
                        nextMonth = currentMonth + 1;
                        nextYear = yy;
                        prevYear = yy;
                        daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                    }

                int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
                trailingSpaces = currentWeekDay;

                if (cal.isLeapYear(cal.get(Calendar.YEAR)) && mm == 2){
                        ++daysInMonth;
                    }

                // Trailing Month days
                for (int i = 0; i < trailingSpaces; i++){
//                        list.add(String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i) + "-GREY" + "-" + getMonthAsString(prevMonth) + "-" + prevYear);
                        
                	activity.gridList.add(new CalendarE(((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i), 0, prevMonth, prevYear, false));
                    }

                // Current Month Days
                for (int i = 1; i <= daysInMonth; i++){
                        if (i == getCurrentDayOfMonth()){
                        	activity.gridList.add(new CalendarE(i, 1, currentMonth, yy, false));

                        }else {
                        	activity.gridList.add(new CalendarE(i, 2, currentMonth, yy, false));
						}
                               /* list.add(String.valueOf(i) + "-BLUE" + "-" + getMonthAsString(currentMonth) + "-" + yy);
                        else
                                list.add(String.valueOf(i) + "-WHITE" + "-" + getMonthAsString(currentMonth) + "-" + yy);*/
                    }

                // Leading Month days
                for (int i = 0; i < activity.gridList.size() % 7; i++){
                	
                	activity.gridList.add(new CalendarE(i + 1, 0, nextMonth, nextYear, false));
//                        list.add(String.valueOf(i + 1) + "-GREY" + "-" + getMonthAsString(nextMonth) + "-" + nextYear);
                    }
            }

        private HashMap<String, Integer> findNumberOfEventsPerMonth(int year, int month){
                HashMap<String, Integer> map = new HashMap<String, Integer>();
                return map;
            }

        @Override
        public long getItemId(int position){
                return position;
            }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
                View row = convertView;
                if (row == null){
                        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        row = inflater.inflate(R.layout.calander_each_cell, parent, false);
                    }

                // Get a reference to the Day gridcell
                gridcell = (TextView) row.findViewById(R.id.calendar_day_gridcell);
                bg = (RelativeLayout) row.findViewById(R.id.bg);
                gridcell.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						if (activity.gridList.get(position).getWhichDay() != 0) {
							if (activity.gridPositionnn != -1) {
								activity.gridList.get(activity.gridPositionnn).setSelected(false);
							}
							activity.gridPositionnn = position;
							CalendarE calObj = (CalendarE) v.getTag();
							
//							v.setBackgroundResource(R.drawable.calander_bg_user_select);
							activity.gridList.get(position).setSelected(true);
							notifyDataSetChanged();
//			                flag ="Date selected ...";
//							bg.setBackgroundResource(R.drawable.calander_bg_user_select);
							
							activity.dateAdding = calObj.getPrevYear()+"-"+(calObj.getMonth()+1)+"-"+calObj.getDay();
							
//			                tools.showToastMessage(calObj.getDay()+":"+calObj.getMonth()+":"+calObj.getPrevYear());
						}
						
					}
				});

                // ACCOUNT FOR SPACING

//                String[] day_color = list.get(position).split("-");
                String theday = String.valueOf(activity.gridList.get(position).getDay());
                String themonth = getMonthAsString(activity.gridList.get(position).getMonth());
                String theyear = String.valueOf(activity.gridList.get(position).getPrevYear());
                if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)){
                        if (eventsPerMonthMap.containsKey(theday)){
                                num_events_per_day = (TextView) row.findViewById(R.id.num_events_per_day);
                                Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
                                num_events_per_day.setText(numEvents.toString());
                            }
                    }

                // Set the Day GridCell
                gridcell.setText(theday);
                gridcell.setTag(new CalendarE().setData(activity.gridList.get(position)));

               
                
                
                
                if (activity.gridList.get(position).isSelected()) {
                	bg.setBackgroundResource(R.drawable.calander_bg_user_select);
				}else {
					
					if (activity.gridList.get(position).getWhichDay() == 0){
	                	bg.setBackgroundResource(android.R.color.transparent);
	                    gridcell.setTextColor(Color.LTGRAY);
	                }
	                
	                if (activity.gridList.get(position).getWhichDay() == 2){
	                	bg.setBackgroundResource(android.R.color.transparent);
	                    gridcell.setTextColor(Color.WHITE);
	                }
	                
	                if (activity.gridList.get(position).getWhichDay() == 1){
	                	
	                	
	                	if ((activity.gridList.get(position).getMonth() == calendar.get(Calendar.MONTH)) && (activity.gridList.get(position).getPrevYear() == calendar.get(Calendar.YEAR))) {
	                		bg.setBackgroundResource(R.drawable.calander_bg);
						}else {
							bg.setBackgroundResource(android.R.color.transparent);
						}
	                	
	                    gridcell.setTextColor(Color.WHITE);
	                }
					
					
				}
                	
//                    gridcell.setTextColor(getResources().getColor(R.color.primary_clr));
                
                return row;
            }

        public int getCurrentDayOfMonth(){
                return currentDayOfMonth;
            }

        private void setCurrentDayOfMonth(int currentDayOfMonth){
                this.currentDayOfMonth = currentDayOfMonth;
            }
        public void setCurrentWeekDay(int currentWeekDay){
                this.currentWeekDay = currentWeekDay;
            }
        public int getCurrentWeekDay(){
                return currentWeekDay;
            }
    }

}
