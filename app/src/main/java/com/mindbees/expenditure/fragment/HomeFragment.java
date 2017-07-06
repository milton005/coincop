package com.mindbees.expenditure.fragment;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mindbees.expenditure.ActivityCredit_Debit;
import com.mindbees.expenditure.ActivityLogin;
import com.mindbees.expenditure.ActivitySelectType;
import com.mindbees.expenditure.MainActivity;
import com.mindbees.expenditure.R;
import com.mindbees.expenditure.Interfaces.ImageVisibility;
import com.mindbees.expenditure.adapter.AdapterAllAccounts;
import com.mindbees.expenditure.adapter.AdapterAllReminder;
import com.mindbees.expenditure.model.AllAccounts.ModelAllAccount;
import com.mindbees.expenditure.model.AllReminders;
import com.mindbees.expenditure.model.CalendarE;
import com.mindbees.expenditure.model.Homefragment.Modelallreminder;
import com.mindbees.expenditure.model.Homefragment.Reminder;
import com.mindbees.expenditure.model.LOGIN_EMAIL.Modellogin;
import com.mindbees.expenditure.model.AllAccounts.Account;
import com.mindbees.expenditure.retrofit.APIService;
import com.mindbees.expenditure.retrofit.ServiceGenerator;
import com.mindbees.expenditure.ui.FloatingActionButton;
import com.mindbees.expenditure.ui.FloatingActionsMenu;
import com.mindbees.expenditure.ui.circular.SupportAnimator;
import com.mindbees.expenditure.ui.circular.ViewAnimationUtils;
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
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements OnClickListener{

	public static final String TAG ="Home_FRAGMENT" ;
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
    
    TextView textHeading;
    
    Tools tools;
//    private FloatingActionsMenu fam;
    private FloatingActionButton fabExpense, fabIncome;
    
    MainActivity activity;
    ImageVisibility mCallback;
    
    
    FrameLayout parentview;
	private SupportAnimator mAnimator;
	
	ListView reminderSelectedDate;
    
	
	ArrayList<Reminder> selectedReminders;
	ArrayList<Account>selectedAccounts;
	AdapterAllReminder adapterReminder;
	AdapterAllAccounts adapterAccounts;
	CalendarE calObj;
//	ArrayList<Integer> reminderDay;
	
	TextView noviewLabel;
	Animation anim_fab;
	
    
    @Override
    public void onAttach(Activity activity) {
    	// TODO Auto-generated method stub
    	super.onAttach(activity);
    	
    	this.activity = (MainActivity) activity;
    	
    	try {
			mCallback = (ImageVisibility) activity;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ClassCastException(activity.toString()
                    + " must implement Listener");
		}
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
		View v = inflater.inflate(R.layout.fragment_home,container, false);
		
		anim_fab = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_btn_zoom);
		parentview = (FrameLayout) v.findViewById(R.id.parentView);
		
		mCallback.isVisible(Const.TAG_HOME);
		_calendar = Calendar.getInstance(Locale.getDefault());
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);
        
        tools = new Tools(getActivity());
//        fam = (FloatingActionsMenu) v.findViewById(R.id.multiple_actions);
        fabExpense = (FloatingActionButton) v.findViewById(R.id.expense);
//        fabIncome = (FloatingActionButton) v.findViewById(R.id.income);
      
      
        reminderSelectedDate = (ListView) v.findViewById(R.id.reminderSelectedDate);
        calendarWeek = (GridView) v.findViewById(R.id.calendar_week);
        prevMonth = (ImageButton) v.findViewById(R.id.prevMonth);
        prevMonth.setOnClickListener(this);
        
        currentMonth = (TextView) v.findViewById(R.id.curntMonth);
        currentMonth.setText(DateFormat.format(dateTemplate, _calendar.getTime()));
        
        textHeading = (TextView) v.findViewById(R.id.textListHHeading);
        
        nextMonth = (ImageButton) v.findViewById(R.id.nextMonth);
        nextMonth.setOnClickListener(this);
        
        calendarView = (GridView) v.findViewById(R.id.calendar);
        
        noviewLabel = (TextView) v.findViewById(R.id.noViewLabel);
        
        calendarWeek.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.inflate_week, week));
        
     // Initialised
        adapter = new GridCellAdapter(getActivity().getApplicationContext(), R.id.calendar_day_gridcell, month, year);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
        
		
		return v;
	}
	
	
	
	private void setGridCellAdapterToDate(int month, int year){
        adapter = new GridCellAdapter(getActivity().getApplicationContext(), R.id.calendar_day_gridcell, month, year);
        _calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
        currentMonth.setText(DateFormat.format(dateTemplate, _calendar.getTime()));
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
    }
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		fabExpense.setVisibility(View.VISIBLE);
		initializeTimerTask();
		parentview.setBackgroundColor(getActivity().getResources().getColor(android.R.color.transparent));
	}
	
	 public void initializeTimerTask() { 
	    	
	    	
	    	final Handler handler = new Handler(); 
	    	handler.postDelayed(new Runnable() { 
	    	    @Override 
	    	    public void run() { 
	    	        // Do something after 5s = 5000ms 
	    	    	fabExpense.setVisibility(View.VISIBLE);
	    	    	fabExpense.startAnimation(anim_fab);
	    	    } 
	    	}, Const.TAG_TIMER_DELAY); 
	 }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		
/*		selectedReminders = new ArrayList<Reminder>();
//		reminderDay = new ArrayList<Integer>();
		adapterReminder = new AdapterAllReminder(getActivity(),selectedReminders );
		reminderSelectedDate.setAdapter(adapterReminder);*/
        selectedAccounts=new ArrayList<Account>();
		adapterAccounts=new AdapterAllAccounts(getActivity(),selectedAccounts);
		reminderSelectedDate.setAdapter(adapterAccounts);



		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int mnth = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		String dayy = year+"-"+(mnth+1)+"-"+day;
//		tools.showLog(dayy, 1);
		
//		setTextWRTDate(dayy);
		if (tools.isConnectingToInternet()) {
		loadTransactions(dayy);
//		loadAllReminders(dayy);
		}else {
			tools.showToastMessage(activity.getResources().getString(R.string.connectivity));

		}
		
		
		
		fabExpense.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				 parentview.setBackgroundColor(getActivity().getResources().getColor(R.color.primary_clr_transperent));
                	
                	
                	
                	int cx = parentview.getRight();
    		        int cy = parentview.getBottom();
    		        
    		        float finalRadius = hypo(parentview.getWidth(), parentview.getHeight());
    		        
    		        mAnimator = ViewAnimationUtils.createCircularReveal(parentview, cx, cy, 0, finalRadius);
    		        
    		        mAnimator.addListener(new SupportAnimator.AnimatorListener() {
    		            @Override
    		            public void onAnimationStart() {
    		            	
    		            }

    		            @Override
    		            public void onAnimationEnd() {
    		            	
    		            	Intent i = new Intent(getActivity(), ActivitySelectType.class);
    						startActivity(i);
    		            }

    		            @Override
    		            public void onAnimationCancel() {

    		            }

    		            @Override
    		            public void onAnimationRepeat() {

    		            }
    		        });
    		        
    		        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
    		        mAnimator.setDuration(400);
    		        mAnimator.start();
                	
    		        fabExpense.setVisibility(View.GONE);
					
//				}
				
			}
		});
	}


	
	/*private void setTextWRTDate(String day){

		String text = "<font color=#2196F3> REMINDER </font> <font color=#000000>"+day+"</font>";
		
		textHeading.setText(Html.fromHtml(text));
	}*/
	
	 static float hypo(int a, int b){
	        return (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
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

        private final List<CalendarE> list;
        private static final int DAY_OFFSET = 1;
        private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        private int daysInMonth;
        private int currentDayOfMonth;
        private int currentWeekDay;
        private TextView gridcell;
        private ImageView num_events_per_day;
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

		private void printMonth(int mm, int yy) {
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

			if (currentMonth == 11) {
				prevMonth = currentMonth - 1;
				daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
				nextMonth = 0;
				prevYear = yy;
				nextYear = yy + 1;
			} else if (currentMonth == 0) {
				prevMonth = 11;
				prevYear = yy - 1;
				nextYear = yy;
				daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
				nextMonth = 1;
			} else {
				prevMonth = currentMonth - 1;
				nextMonth = currentMonth + 1;
				nextYear = yy;
				prevYear = yy;
				daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
			}

			int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
			trailingSpaces = currentWeekDay;

			if (cal.isLeapYear(cal.get(Calendar.YEAR)) && mm == 2) {
				++daysInMonth;
			}

			// Trailing Month days
			for (int i = 0; i < trailingSpaces; i++) {

				list.add(new CalendarE(
						((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i),
						0, prevMonth, prevYear, false));
			}

			// Current Month Days
			for (int i = 1; i <= daysInMonth; i++) {
				if (i == getCurrentDayOfMonth()) {
					list.add(new CalendarE(i, 1, currentMonth, yy, false));

				} else {
					list.add(new CalendarE(i, 2, currentMonth, yy, false));
				}
			}

			// Leading Month days
			for (int i = 0; i < list.size() % 7; i++) {

				list.add(new CalendarE(i + 1, 0, nextMonth, nextYear, false));
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
                        row = inflater.inflate(R.layout.calender_layout_eachcell_2, parent, false);
                    }

                // Get a reference to the Day gridcell
                gridcell = (TextView) row.findViewById(R.id.calendar_day_gridcell);
                num_events_per_day = (ImageView) row.findViewById(R.id.num_events_per_day);
                bg = (RelativeLayout) row.findViewById(R.id.bg);
                gridcell.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						if (list.get(position).getWhichDay() != 0) {
							
							if (activity.listPosition != -1) {
								list.get(activity.listPosition).setSelected(false);
							}
							
							activity.listPosition = position;
							list.get(position).setSelected(true);
							if (list.get(position).isSelected()) {

								bg.setBackgroundResource(R.drawable.calander_bg_user_select);
							}
							notifyDataSetChanged();

							calObj = (CalendarE) v.getTag();
//			                flag ="Date selected ...";
							
							int mnth = calObj.getMonth()+1;
							String dateAdding = calObj.getPrevYear()+"-"+mnth+"-"+calObj.getDay();
							
//							setTextWRTDate(dateAdding);
							
							if (tools.isConnectingToInternet()) {
//								loadAllReminders(dateAdding);
								loadTransactions(dateAdding);
							}
							
			                
//			                tools.showToastMessage(calObj.getDay()+":"+calObj.getMonth()+":"+calObj.getPrevYear());
			                
			                
						}
						
					}
				});

                // ACCOUNT FOR SPACING

//                String[] day_color = list.get(position).split("-");
                String theday = String.valueOf(list.get(position).getDay());
                String themonth = getMonthAsString(list.get(position).getMonth());
                String theyear = String.valueOf(list.get(position).getPrevYear());
                /*if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)){
                        if (eventsPerMonthMap.containsKey(theday)){
                                num_events_per_day = (TextView) row.findViewById(R.id.num_events_per_day);
                                Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
                                num_events_per_day.setText(numEvents.toString());
                            }
                    }*/

                // Set the Day GridCell
                gridcell.setText(theday);
                gridcell.setTag(new CalendarE().setData(list.get(position)));
                
                
                /*if (checkReminders(list.get(position).getDay())) {
                	num_events_per_day.setVisibility(View.VISIBLE);
				}else {
					num_events_per_day.setVisibility(View.GONE);
				}*/
                
                
                if (list.get(position).isSelected()) {

               	bg.setBackgroundResource(R.drawable.calander_bg_user_select);
				}else {
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
				}
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
	
	/*private boolean checkReminders(int day){
		 
		Set<Integer> set=  new TreeSet<Integer>();
		set.addAll(reminderDay);
		
		if (set.contains(day)) {
			return true;
		}else {
			return false;
		}
	}*/

	private void loadTransactions(String date) {
		final ProgressDialog pd = new ProgressDialog(getActivity());
		pd.setMessage("Loading..");
		pd.setCancelable(false);
		pd.show();
		HashMap<String, String> params = new HashMap<>();
		params.put("user_id", BaseActivity.getpreference(Const.TAG_USERID, activity));
		params.put("searchdate", date);

		APIService service = ServiceGenerator.createService(APIService.class, getContext());
		Call<ModelAllAccount>call=service.all_accounts(params);
		call.enqueue(new Callback<ModelAllAccount>() {
			@Override
			public void onResponse(Call<ModelAllAccount> call, Response<ModelAllAccount> response) {
				if (pd.isShowing()) {
					pd.dismiss();

				}
				if (response.isSuccessful())
				{
					if(response.body()!=null)
					{
						if(response.body().getAccount().size()>0)
						{
							selectedAccounts.clear();
							List<Account>accounts=response.body().getAccount();
							selectedAccounts.addAll(accounts);
							adapterAccounts.notifyDataSetChanged();
							if (!accounts.isEmpty())
							{
								noviewLabel.setVisibility(View.GONE);
							}
							else {
								noviewLabel.setVisibility(View.VISIBLE);

							}

						}
						if (response.body().getAccount().isEmpty())
						{
							selectedAccounts.clear();
							List<Account>accounts=response.body().getAccount();
							selectedAccounts.addAll(accounts);
							adapterAccounts.notifyDataSetChanged();
							noviewLabel.setVisibility(View.VISIBLE);
						}
					}
				}
			}

			@Override
			public void onFailure(Call<ModelAllAccount> call, Throwable t) {
				if (pd.isShowing()) {
					pd.dismiss();
				}
			}
		});
	}
	
	
	private void loadAllReminders(String date) {

		 final ProgressDialog pd = new ProgressDialog(getActivity());
		 pd.setMessage("Loading");
		 pd.setCancelable(false);



		HashMap<String, String> params = new HashMap<>();
//		params.put("user_action", "1024");
		params.put("user_id", BaseActivity.getpreference(Const.TAG_USERID, activity));
		params.put("search_date", date);
		params.put("request","reminders");
		APIService service = ServiceGenerator.createService(APIService.class, getContext());
		Call<Modelallreminder> call = service.all_reminder(params);
		call.enqueue(new Callback<Modelallreminder>() {
			@Override
			public void onResponse(Call<Modelallreminder> call, Response<Modelallreminder> response) {
				if (pd.isShowing()) {
					pd.dismiss();
				}
				if (response.isSuccessful()) {

					if(response.body()!=null)
					{
						if(response.body().getReminder().size()>0)
						{
							selectedReminders.clear();
							List<Reminder>reminders=response.body().getReminder();
							selectedReminders.addAll(reminders);
							adapterReminder.notifyDataSetChanged();
							if (!reminders.isEmpty()) {
								noviewLabel.setVisibility(View.GONE);
							}else {
								noviewLabel.setVisibility(View.VISIBLE);

							}
						}
						if(response.body().getReminder().isEmpty())
						{
							selectedReminders.clear();
							List<Reminder>reminders=response.body().getReminder();
							selectedReminders.addAll(reminders);
							adapterReminder.notifyDataSetChanged();
							noviewLabel.setVisibility(View.VISIBLE);
						}

					}
				}

			}

			@Override
			public void onFailure(Call<Modelallreminder> call, Throwable t) {
				if (pd.isShowing()) {
					pd.dismiss();
				}
			}
		});
//		WebService.post(Const.URL+"reminders", params, new AsyncHttpResponseHandler() {
//
//			@Override
//			public void onStart() {
//				// TODO Auto-generated method stub
//				super.onStart();
//				 pd.show();
//			}
//
//			@Override
//			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//				// TODO Auto-generated method stub
//
//				parse(new String(arg2));
//				tools.showLog(new String(arg2), 1);
//
//				  if (pd.isShowing()) { pd.dismiss(); }
//
//
//			}
//
//			@Override
//			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//					Throwable arg3) {
//				// TODO Auto-generated method stub
//
//				try {
//					Tools.debugResponse(new String(arg2));
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//
//				 if (pd.isShowing()) { pd.dismiss(); }
//
//
//				Tools.debugThrowable(arg3);
//
//				if (arg2 != null) {
//					Tools.debugResponse(new String(arg2));
//				}
//				tools.showToastMessage(activity.getResources().getString(R.string.connectivity));
//			}
//
//			private void parse(Reminder reminder) {
//				// TODO Auto-generated method stub
//				selectedReminders.clear();
//				try {
//
//					JSONObject jobject = new JSONObject(string);
//					JSONArray jArray = jobject.getJSONArray("reminder");
//					for (int i = 0; i < jArray.length(); i++) {
//						JSONObject jsonobject = jArray.getJSONObject(i);
//
//						String reminder_id = jsonobject.getString("reminder_id");
//						String reminder_text = jsonobject.getString("reminder_text");
//						String reminder_date = jsonobject.getString("reminder_date");
//						String user_id = jsonobject.getString("user_id");
//						String added_date = jsonobject.getString("added_date");
//
//						AllReminders scnt = new AllReminders();
//						scnt.setReminder_id(reminder_id);
//						scnt.setReminder_text(reminder_text);
//						scnt.setReminder_date(reminder_date);
//						scnt.setUser_id(user_id);
//						scnt.setAdded_date(added_date);
//
//						selectedReminders.add(scnt);
//
//					}
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//
//				adapterReminder.notifyDataSetChanged();
////				adapter.notifyDataSetChanged();
//
//				if (selectedReminders.size() != 0) {
//					noviewLabel.setVisibility(View.GONE);
//				}else {
//					noviewLabel.setVisibility(View.VISIBLE);
//
//				}
//
//			}
//		});
	}
	
	
	private int seperateDate(String date){
		List<String> elephantList = Arrays.asList(date.split("-"));
		int aa = 0;
		try {
			aa = Integer.parseInt(elephantList.get(2));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aa;
	}






}
