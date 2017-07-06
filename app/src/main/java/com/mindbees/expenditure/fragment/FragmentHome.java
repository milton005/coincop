package com.mindbees.expenditure.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


import com.mindbees.expenditure.MainActivity;
import com.mindbees.expenditure.R;
import com.mindbees.expenditure.fragment.HomeFragment.GridCellAdapter;
import com.mindbees.expenditure.model.CalendarE;
import com.special.ResideMenu.ResideMenu;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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

public class FragmentHome extends Fragment implements OnClickListener{
	private ViewPager viewPager;
	
	ResideMenu resideMenu;
	
	
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
    
    AdapterCustomPager adapterr;
    
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_home_, null);
		
		viewPager = (ViewPager) v.findViewById(R.id.view_pager);
		MainActivity parentActivity = (MainActivity) getActivity();
		resideMenu = parentActivity.getResideMenu();
		
		resideMenu.addIgnoredView(resideMenu);
		
		
		_calendar = Calendar.getInstance(Locale.getDefault());
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);
        
        calendarWeek = (GridView) v.findViewById(R.id.calendar_week);
        prevMonth = (ImageButton) v.findViewById(R.id.prevMonth);
        prevMonth.setOnClickListener(this);
        
        currentMonth = (TextView) v.findViewById(R.id.curntMonth);
        currentMonth.setText(DateFormat.format(dateTemplate, _calendar.getTime()));
        
        nextMonth = (ImageButton) v.findViewById(R.id.nextMonth);
        nextMonth.setOnClickListener(this);
        
        calendarView = (GridView) v.findViewById(R.id.calendar);
        
        calendarWeek.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.inflate_week, week));
		
		
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		adapterr = new AdapterCustomPager();
        viewPager.setAdapter(adapterr);
		
		
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
	}
	
	
	public class AdapterCustomPager extends PagerAdapter {

		
		
		public AdapterCustomPager() {
			super();
			// TODO Auto-generated constructor stub
		}


		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Integer.MAX_VALUE;
		}
		

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == ((RelativeLayout)arg1);
		}
		@Override
	    public void destroyItem(ViewGroup container, int position, Object object) {
	        container.removeView((RelativeLayout)object);
	    }
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			
			LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.calander_each_cell, container, false);
            
            adapter = new GridCellAdapter(getActivity().getApplicationContext(), R.id.calendar_day_gridcell, month, year);
            adapter.notifyDataSetChanged();
            calendarView.setAdapter(adapter);
	
			return row;
		}
		
	}
	
	
	
	public class GridCellAdapter extends BaseAdapter
    {
        private final Context _context;

        private final List<CalendarE> list;
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
        public GridCellAdapter(Context context, int textViewResourceId, int month, int year){
                super();
                this._context = context;
                this.list = new ArrayList<CalendarE>();
                calendar = Calendar.getInstance();
                setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
                setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));

                // Print Month
                printMonth(month, year);

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
                return list.get(position);
            }

        @Override
        public int getCount(){
                return list.size();
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

                if (cal.isLeapYear(cal.get(Calendar.YEAR)) && mm == 1){
                        ++daysInMonth;
                    }

                // Trailing Month days
                for (int i = 0; i < trailingSpaces; i++){
//                        list.add(String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i) + "-GREY" + "-" + getMonthAsString(prevMonth) + "-" + prevYear);
                        
                        list.add(new CalendarE(((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i), 0, prevMonth, prevYear, false));
                    }

                // Current Month Days
                for (int i = 1; i <= daysInMonth; i++){
                        if (i == getCurrentDayOfMonth()){
                            list.add(new CalendarE(i, 1, currentMonth, yy, false));

                        }else {
                        	list.add(new CalendarE(i, 2, currentMonth, yy, false));
						}
                               /* list.add(String.valueOf(i) + "-BLUE" + "-" + getMonthAsString(currentMonth) + "-" + yy);
                        else
                                list.add(String.valueOf(i) + "-WHITE" + "-" + getMonthAsString(currentMonth) + "-" + yy);*/
                    }

                // Leading Month days
                for (int i = 0; i < list.size() % 7; i++){
                	
                	list.add(new CalendarE(i + 1, 0, nextMonth, nextYear, false));
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
        public View getView(int position, View convertView, ViewGroup parent){
                View row = convertView;
                if (row == null){
                        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        row = inflater.inflate(R.layout.calander_each_cell, parent, false);
                    }

                // Get a reference to the Day gridcell
                gridcell = (TextView) row.findViewById(R.id.calendar_day_gridcell);
                bg = (RelativeLayout) row.findViewById(R.id.bg);
//                gridcell.setOnClickListener(this);

                // ACCOUNT FOR SPACING

//                String[] day_color = list.get(position).split("-");
                String theday = String.valueOf(list.get(position).getDay());
                String themonth = getMonthAsString(list.get(position).getMonth());
                String theyear = String.valueOf(list.get(position).getPrevYear());
                if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)){
                        if (eventsPerMonthMap.containsKey(theday)){
                                num_events_per_day = (TextView) row.findViewById(R.id.num_events_per_day);
                                Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
                                num_events_per_day.setText(numEvents.toString());
                            }
                    }

                // Set the Day GridCell
                gridcell.setText(theday);
                gridcell.setTag(theday + "-" + themonth + "-" + theyear);

                if (list.get(position).getWhichDay() == 0){
                	bg.setBackgroundResource(android.R.color.transparent);
                    gridcell.setTextColor(Color.LTGRAY);
                }
                
                if (list.get(position).getWhichDay() == 2){
                	bg.setBackgroundResource(android.R.color.transparent);
                    gridcell.setTextColor(Color.WHITE);
                }
                
                if (list.get(position).getWhichDay() == 1){
                	
                	if ((list.get(position).getMonth() == calendar.get(Calendar.MONTH)) && (list.get(position).getPrevYear() == calendar.get(Calendar.YEAR))) {
                		bg.setBackgroundResource(R.drawable.calander_bg);
					}else {
						bg.setBackgroundResource(android.R.color.transparent);
					}
                	
                    gridcell.setTextColor(Color.WHITE);
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



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		
		
	}
	
	

}
