/*package com.mindbees.expenditure.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.http.Header;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mindbees.expenditure.ActivityAddExpense_Income;
import com.mindbees.expenditure.ActivityChartView;
import com.mindbees.expenditure.ActivityReport;
import com.mindbees.expenditure.ActivityReportCategoryView;
import com.mindbees.expenditure.R;
import com.mindbees.expenditure.Interfaces.CategoryItem;
import com.mindbees.expenditure.adapter.AdapterViewPager;
import com.mindbees.expenditure.fragment.FragmentAddAccountCal.GridCellAdapter;
import com.mindbees.expenditure.model.CalendarE;
import com.mindbees.expenditure.model.Category;
import com.mindbees.expenditure.ui.ExpandableHeightGridView;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;
import com.mindbees.expenditure.util.WebService;
import com.mindbees.expenditure.util.XMLParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentReportDaily extends Fragment implements OnClickListener, CategoryItem{
	
	private TextView currentMonth;
    private ImageButton prevMonth;
    private ImageButton nextMonth;
    private ExpandableHeightGridView calendarView;
    private GridCellAdapter adapter;
    private Calendar _calendar;
    private int month, year;
    private static final String dateTemplate = "MMMM yyyy";
    String flag ="abc";
    String date_month_year;
    
    GridView calendarWeek;
    private final String[] week = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    
    Tools tools;
    ActivityReport activity;
    
    public List<CalendarE> gridList;
    
    ImageView imgSubmit;
    Bundle bund;
    boolean isCategory;
    
    String selectedYear;
    CalendarE calObj;
    ImageView imgOK;
    
    
    
     *  category section
     
    
    ViewPager view_pager_avatar;
	ImageView imLft;
	ImageView imRght;
	RelativeLayout viewPagerView;
	public int pagerPosition = 0;
	public ArrayList<Category> categoryImgList;
	AdapterViewPager adapterViewPager;
	
	String categoryID = "";
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity = (ActivityReport) activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		bund = activity.getIntent().getExtras();
		isCategory = bund.getBoolean("isCategory");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v = inflater.inflate(R.layout.fragment_report_daily, null);
		
		
		_calendar = Calendar.getInstance(Locale.getDefault());
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);
        
        tools = new Tools(getActivity());
      
        view_pager_avatar = (ViewPager) v.findViewById(R.id.pager_avatar);
		imLft = (ImageView) v.findViewById(R.id.imgLeft);
		imRght = (ImageView) v.findViewById(R.id.imgRight);
		viewPagerView = (RelativeLayout) v.findViewById(R.id.viewPagerview);
		imgOK = (ImageView) v.findViewById(R.id.imgOK);
		imgOK.setOnClickListener(this);
		imLft.setOnClickListener(this);
		imRght.setOnClickListener(this);
      
        calendarWeek = (GridView) v.findViewById(R.id.calendar_week);
        prevMonth = (ImageButton) v.findViewById(R.id.prevMonth);
        prevMonth.setOnClickListener(this);
        
        currentMonth = (TextView) v.findViewById(R.id.curntMonth);
        currentMonth.setText(DateFormat.format(dateTemplate, _calendar.getTime()));
        
        nextMonth = (ImageButton) v.findViewById(R.id.nextMonth);
        nextMonth.setOnClickListener(this);
        
        calendarView = (ExpandableHeightGridView) v.findViewById(R.id.calendar);
        calendarView.setExpanded(true);
        
        imgSubmit = (ImageView) v.findViewById(R.id.imgOK);
        
        calendarWeek.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.inflate_week, week));
        
        imLft.setAlpha(0.5f);
		imLft.setEnabled(false);
        
     // Initialised
        adapter = new GridCellAdapter(getActivity().getApplicationContext(), R.id.calendar_day_gridcell, month, year, false);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
        
        
        if (isCategory) {
        	
        	categoryImgList = new ArrayList<Category>();
        	
        	adapterViewPager = new AdapterViewPager(getActivity(), categoryImgList);
        	adapterViewPager.setCallBack(this);
        	view_pager_avatar.setAdapter(adapterViewPager);
        	
        	if (activity.categoryImgList.size() == 0) {
        		if (tools.isConnectingToInternet()) {
            		loadCategorys(BaseActivity.getpreference(Const.TAG_USERID, activity));
    			}else {
    				tools.showToastMessage("Network Error");
    			}      
			}else {
				viewPagerView.setVisibility(View.VISIBLE);
				adapterViewPager = new AdapterViewPager(getActivity(),  activity.categoryImgList);
	        	adapterViewPager.setCallBack(this);
	        	view_pager_avatar.setAdapter(adapterViewPager);
			}
        	  	
		}
		
		
		return v;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
		if (activity.gridPositionMonths != -1) {
			activity.categoryImgList.get(activity.gridPositionMonths).setSelected(false);
			activity.gridPositionMonths = -1;
			adapterViewPager.notifyDataSetChanged();
			
		}
		
		
		super.onResume();
	}
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		
		view_pager_avatar.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub

				pagerPosition = arg0;
				if (pagerPosition > 0) {
					imLft.setAlpha(1f);
					imLft.setEnabled(true);
				} else {
					imLft.setAlpha(0.5f);
					imLft.setEnabled(false);
				}

				if (pagerPosition < 2) {
					imRght.setAlpha(1f);
					imRght.setEnabled(true);
				} else {
					imRght.setAlpha(0.5f);
					imRght.setEnabled(false);
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		
	}
	
	private void setGridCellAdapterToDate(int month, int year){
        adapter = new GridCellAdapter(getActivity().getApplicationContext(), R.id.calendar_day_gridcell, month, year, true);
        _calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
        currentMonth.setText(DateFormat.format(dateTemplate, _calendar.getTime()));
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
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
        
        if (v == imLft){
        	if (pagerPosition >= 0) {
        		view_pager_avatar.setCurrentItem(pagerPosition - 1);
        	}
        	
        }
        if (v == imRght){
        	if (pagerPosition <= 2) {
        		view_pager_avatar.setCurrentItem(pagerPosition + 1);
        	}
        	
        }
        
        if (v == imgOK) {
        	
        	if (isCategory) {
        		if (categoryID != "") {
        			Intent i = new Intent(getActivity(), ActivityReportCategoryView.class);
            		bund.putString("catID", categoryID);
    	        	bund.putInt("duration", 101);
    	        	bund.putString("year", selectedYear);
    	        	i.putExtras(bund);
    				startActivity(i);
				}else {
					tools.showToastMessage("Select Category");
				}
        		
				
			}else {
	        	Intent i = new Intent(getActivity(), ActivityChartView.class);
	        	bund.putInt("duration", 101);
	        	bund.putString("year", selectedYear);
	        	i.putExtras(bund);
				startActivity(i);
			}
        	
        	
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

                gridList = new ArrayList();
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
                return gridList.get(position);
            }

        @Override
        public int getCount(){
                return gridList.size();
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


                gridList.clear();
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
                        
                	gridList.add(new CalendarE(((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i), 0, prevMonth, prevYear, false));
                    }

                // Current Month Days
                for (int i = 1; i <= daysInMonth; i++){
                        if (i == getCurrentDayOfMonth()){
    	                	if ((currentMonth == calendar.get(Calendar.MONTH)) && (prevYear == calendar.get(Calendar.YEAR))) {
    	                		gridList.add(new CalendarE(i, 1, currentMonth, yy, true));

    	                	}else {
    	                		gridList.add(new CalendarE(i, 1, currentMonth, yy, false));

							}
                        	
                        }else {
                        	gridList.add(new CalendarE(i, 2, currentMonth, yy, false));
						}
                                list.add(String.valueOf(i) + "-BLUE" + "-" + getMonthAsString(currentMonth) + "-" + yy);
                        else
                                list.add(String.valueOf(i) + "-WHITE" + "-" + getMonthAsString(currentMonth) + "-" + yy);
                    }

                // Leading Month days
                for (int i = 0; i < gridList.size() % 7; i++){
                	
                	gridList.add(new CalendarE(i + 1, 0, nextMonth, nextYear, false));
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
						
						if (gridList.get(position).getWhichDay() != 0) {
							if (activity.gridPosition != -1) {
								gridList.get(activity.gridPosition).setSelected(false);
							}
							activity.gridPosition = position;
							calObj = (CalendarE) v.getTag();
							
//							int mnth = calObj.getMonth()+1;
//	                		selectedYear = calObj.getPrevYear()+"-"+mnth+"-"+calObj.getDay();
							
							gridList.get(position).setSelected(true);
							notifyDataSetChanged();
							
//							activity.dateAdding = calObj.getPrevYear()+"-"+(calObj.getMonth()+1)+"-"+calObj.getDay();
							
						}
						
					}
				});

                // ACCOUNT FOR SPACING

//                String[] day_color = list.get(position).split("-");
                String theday = String.valueOf(gridList.get(position).getDay());
                String themonth = getMonthAsString(gridList.get(position).getMonth());
                String theyear = String.valueOf(gridList.get(position).getPrevYear());
                if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)){
                        if (eventsPerMonthMap.containsKey(theday)){
                                num_events_per_day = (TextView) row.findViewById(R.id.num_events_per_day);
                                Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
                                num_events_per_day.setText(numEvents.toString());
                            }
                    }

                // Set the Day GridCell
                gridcell.setText(theday);
                gridcell.setTag(new CalendarE().setData(gridList.get(position)));

               
                
                
                
                if (gridList.get(position).isSelected()) {
                	int mnth = gridList.get(position).getMonth()+1;
                	selectedYear = gridList.get(position).getPrevYear()+"-"+mnth+"-"+gridList.get(position).getDay();
                	tools.showLog(selectedYear, 2);
                	activity.gridPosition = position;
                	bg.setBackgroundResource(R.drawable.calander_bg_user_select_report);
				}else {
					
					if (gridList.get(position).getWhichDay() == 0){
	                	bg.setBackgroundResource(android.R.color.transparent);
	                    gridcell.setTextColor(Color.LTGRAY);
	                }
	                
	                if (gridList.get(position).getWhichDay() == 2){
	                	bg.setBackgroundResource(android.R.color.transparent);
	                    gridcell.setTextColor(Color.WHITE);
	                }
	                
	                if (gridList.get(position).getWhichDay() == 1){
	                	
	                	bg.setBackgroundResource(android.R.color.transparent);
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


	@Override
	public void onCategoryItemClicked(String categoryId, int position) {
		// TODO Auto-generated method stub
		categoryID = categoryId;
		activity.gridPositionMonths = position;
		
	}
	
	
	private void loadCategorys(String userid){
		
		final ProgressDialog pd = new ProgressDialog(getActivity());
		pd.setMessage("Loading");
		pd.setCancelable(false);
		
		tools.showLog(userid, 1);
		
		RequestParams params = new RequestParams();
			params.put("user_action", "1010");
			params.put("user_id", userid);
			
		
		
		WebService.post(Const.URL, params, new AsyncHttpResponseHandler() {
			
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				pd.show();
			}
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				
				viewPagerView.setVisibility(View.VISIBLE);
				
				parse(new String(arg2));
				tools.showLog(new String(arg2), 2);
				 if (pd.isShowing()) {
	                    pd.dismiss();
	                }
				
			}
			
			

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// TODO Auto-generated method stub
				
				try {
					Tools.debugResponse(new String(arg2));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Tools.debugThrowable(arg3);

                if (arg2 != null) {
                    Tools.debugResponse(new String(arg2));
                }
                tools.showToastMessage(activity.getResources().getString(R.string.connectivity));
			}
			
			
			private void parse(String string) {
				// TODO Auto-generated method stub
				
//				allCategory = new ArrayList<Category>();
				XMLParser parser = new XMLParser();
		        try {
		            String xml = string;

		            Document doc = parser.getDomElement(xml);
		            NodeList nl = doc.getElementsByTagName("account");
		            for (int i = 0; i < nl.getLength(); i++) {
		            	Element e = (Element) nl.item(i);
		            	
		            	String catId = parser.getValue(e, "category_id");
		            	String catTitlle = parser.getValue(e, "category_title");
		            	String type_id = parser.getValue(e, "type_id");
		            	String user_id = parser.getValue(e, "user_id");
		            	String added_date = parser.getValue(e, "added_date");
		            	String cat_image = parser.getValue(e, "cat_image");
		            	String cat_color = parser.getValue(e, "cat_color");
		            	
		            	Category cat = new Category();
		            	cat.setCatId(catId);
		            	cat.setCatTitlle(catTitlle);
		            	cat.setType_id(type_id);
		            	cat.setUser_id(user_id);
		            	cat.setAdded_date(added_date);
		            	cat.setCat_image(cat_image);
		            	cat.setSelected(false);
		            	cat.setCat_color(cat_color);
		            	
		            	categoryImgList.add(cat);
		            	
						
					}
		        }catch(Exception e){
		        	e.printStackTrace();
		        }
		        activity.categoryImgList.clear();
		        activity.categoryImgList = categoryImgList;
//		        adapter = new AdapterCategory(ActivityCategory.this, allCategory);
		        adapterViewPager.notifyDataSetChanged();
//		        mGridView.setAdapter(adapter);
				
			}
		});
	}
	
	
	

}
*/