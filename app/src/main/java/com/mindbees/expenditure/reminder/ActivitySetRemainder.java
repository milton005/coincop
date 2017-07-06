package com.mindbees.expenditure.reminder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.http.Header;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mindbees.expenditure.ActivityLogin;
import com.mindbees.expenditure.R;
import com.mindbees.expenditure.Interfaces.ImageVisibility;
import com.mindbees.expenditure.model.CalendarE;
import com.mindbees.expenditure.model.Homefragment.Reminder;
import com.mindbees.expenditure.model.LOGIN_EMAIL.Modellogin;
import com.mindbees.expenditure.model.Reminder.Modelsetreminder;
import com.mindbees.expenditure.retrofit.APIService;
import com.mindbees.expenditure.retrofit.ServiceGenerator;
import com.mindbees.expenditure.ui.ExpandableHeightGridView;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;
import com.mindbees.expenditure.util.WebService;
import com.mindbees.expenditure.util.XMLParser;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Reminders;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class
ActivitySetRemainder extends BaseActivity implements OnClickListener {

	private TextView currentMonth;
	private ImageButton prevMonth;
	private ImageButton nextMonth;
	private ExpandableHeightGridView calendarView;
	private GridCellAdapter adapter;
	private Calendar _calendar;
	private int month, year;
	private static final String dateTemplate = "MMMM yyyy";
	String flag = "abc";
	String date_month_year;

	GridView calendarWeek;
	private final String[] week = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

	Tools tools;

	CalendarE calObj;


	int currentHoursin24Hours;
	boolean isAm;
	boolean isPmToAm = false;
	boolean isAmToPm = false;
	EditText desciption;
	int pref;


	ImageVisibility mCallback;

	public final List<CalendarE> gridList = new ArrayList();
	public int gridPositionReminder = -1;

	ImageView backBtn;
	Uri eventUrl;
	LinearLayout timeView, alarmType;
//    boolean timeViewEnabled = false;

	/*************************************************************/
//    Button btnSetAlarm;
	LinearLayout setAlarm;
	TextView tvTime;
	boolean isAlarmViewShows = false;
	ToggleButton daily, weekly, monthly, yearly;
	int typeOfReminder = 0;
	RadioGroup toggleGroup;
	//    ToggleButton imgSetAlarm;
	ImageView imgOk;

	public int hourOFDay = 0;
	public int minOFHour = 0;
	DialogFragment newFragment;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_remainder);
//		setupUI(findViewById(R.id.mnbv));


		_calendar = Calendar.getInstance(Locale.getDefault());
		month = _calendar.get(Calendar.MONTH) + 1;
		year = _calendar.get(Calendar.YEAR);


		hourOFDay = _calendar.get(Calendar.HOUR_OF_DAY);
		minOFHour = _calendar.get(Calendar.MINUTE);

		tools = new Tools(this);

		initiateUI();
//        newFragment = new TimePickerFragment();
		calendarWeek.setAdapter(new ArrayAdapter<String>(this, R.layout.inflate_week, week));

		adapter = new GridCellAdapter(this, R.id.calendar_day_gridcell, month, year, false);
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);
		imgOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (tools.isConnectingToInternet()) {
					if (gridPositionReminder != -1) {
						if (validateData()) {
//							setAlarmTime(calObj, hourOFDay, minOFHour);
							dataUploading();

						}

					} else {
						tools.showToastMessage("Please Select Day");
					}
				} else {
					tools.showToastMessage(getResources().getString(
							R.string.connectivity));
				}

			}
		});


		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
/*		btnSetAlarm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (tools.isConnectingToInternet()) {
					if (gridPositionReminder != -1) {
						if (validateData()) {
							DialogFragment newFragment = new TimePickerFragment();
							newFragment.show(getSupportFragmentManager(), "timePicker");
						}

					} else {
						tools.showToastMessage("Please Select Day");
					}
				}else {
					tools.showToastMessage(getResources().getString(R.string.connectivity));
				}
				
				if (isAlarmViewShows) {
					tvTime.setText("Time");
					setAlarm.setVisibility(View.VISIBLE);
					isAlarmViewShows = false;
				}else {
					tvTime.setText("");
					setAlarm.setVisibility(View.GONE);
					isAlarmViewShows = true;
				}

			}

		});*/
		
		/*imgSetAlarm.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				
				hourOFDay = 0;
				minOFHour = 0;
				
				if (isChecked) {
					tvTime.setText("Time");
					setAlarm.setVisibility(View.VISIBLE);
					isAlarmViewShows = true;
				}else {
//					tvTime.setText("");
					setAlarm.setVisibility(View.GONE);
					isAlarmViewShows = false;
				}
				
			}
		});*/


		tvTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isAlarmViewShows = false;
				Calendar mcurrentTime = Calendar.getInstance();
				int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
				int minut = mcurrentTime.get(Calendar.MINUTE);
				TimePickerDialog mTimePicker;
				mTimePicker = new TimePickerDialog(ActivitySetRemainder.this, new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
						isAlarmViewShows = true;
			hourOFDay = selectedHour;
			minOFHour = selectedMinute;
//						setAlarmTime(calObj, selectedHour, selectedMinute);
						tvTime.setText(updateTime(selectedHour,selectedMinute));
					}
				}, hour, minut, true);//Yes 24 hour time
				mTimePicker.setTitle("Select Time");
				mTimePicker.show();
//				newFragment = new TimePickerFragment();
//				newFragment.show(getSupportFragmentManager(), "timePicker");
			}
		});


		daily.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
										 boolean isChecked) {
				// TODO Auto-generated method stub

				validateCheck(daily, 1, isChecked);


			}
		});
		weekly.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
										 boolean isChecked) {
				// TODO Auto-generated method stub
				validateCheck(weekly, 2, isChecked);


			}
		});
		monthly.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
										 boolean isChecked) {
				// TODO Auto-generated method stub

				validateCheck(monthly, 3, isChecked);


			}
		});
		yearly.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
										 boolean isChecked) {
				// TODO Auto-generated method stub

				validateCheck(yearly, 4, isChecked);


			}
		});


	}


	private void initiateUI() {
		// TODO Auto-generated method stub
		backBtn = (ImageView) findViewById(R.id.backBtn);

		imgOk = (ImageView) findViewById(R.id.imgOK);

		desciption = (EditText) findViewById(R.id.description);


		calendarWeek = (GridView) findViewById(R.id.calendar_week);
		prevMonth = (ImageButton) findViewById(R.id.prevMonth);
		prevMonth.setOnClickListener(this);

		currentMonth = (TextView) findViewById(R.id.curntMonth);
		currentMonth.setText(DateFormat.format(dateTemplate, _calendar.getTime()));

		nextMonth = (ImageButton) findViewById(R.id.nextMonth);
		nextMonth.setOnClickListener(this);

		calendarView = (ExpandableHeightGridView) findViewById(R.id.calendar);
		calendarView.setExpanded(true);

       // btnSetAlarm = (Button) findViewById(R.id.btnSetAlarm);
		setAlarm = (LinearLayout) findViewById(R.id.bbb);
		tvTime = (TextView) findViewById(R.id.timeset);
		daily = (ToggleButton) findViewById(R.id.toggleButtonDaily);
		weekly = (ToggleButton) findViewById(R.id.toggleButtonweekly);
		monthly = (ToggleButton) findViewById(R.id.toggleButtonMonthly);
		yearly = (ToggleButton) findViewById(R.id.toggleButtonYearly);
		toggleGroup = (RadioGroup) findViewById(R.id.toggleGroup);
//        imgSetAlarm = (ToggleButton)  findViewById(R.id.imgSetAlarm);
		tvTime.setText(getCrntTime());
		View.OnFocusChangeListener ofcListener = new MyFocusChangeListener();
		desciption.setOnFocusChangeListener(ofcListener);

	}

	private String getCrntTime() {
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		return updateTime(hour, minute);
	}

	protected void validateCheck(ToggleButton selectedView, int alarmType, boolean isChecked) {
		// TODO Auto-generated method stub


		if (isChecked) {
			typeOfReminder = alarmType;
			for (int j = 0; j < toggleGroup.getChildCount(); j++) {

				final LinearLayout viewLinaer = (LinearLayout)
						toggleGroup.getChildAt(j);
				final ToggleButton view = (ToggleButton) viewLinaer.getChildAt(0);
				if (view == selectedView) {
//						view.setChecked(true);
				} else {
					view.setChecked(false);
				}

			}
		}
	}


//	public  static class TimePickerFragment extends DialogFragment implements
//			TimePickerDialog.OnTimeSetListener {
//
//
//
//		@Override
//		public Dialog onCreateDialog(Bundle savedInstanceState) {
//			// Use the current time as the default values for the picker
//
////			isAlarmViewShows = false;
//			final Calendar c = Calendar.getInstance();
//			int hour = c.get(Calendar.HOUR_OF_DAY);
//			int minute = c.get(Calendar.MINUTE);
//
//			// Create a new instance of TimePickerDialog and return it
//			return new TimePickerDialog(getActivity(), this, hour, minute,
//					false);
//		}
//
//		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//			// Do something with the time chosen by the user
////			isAlarmViewShows = true;
////			hourOFDay = hourOfDay;
////			minOFHour = minute;
//
//			Log.e("expenditure", hourOfDay + "");
//			Log.e("expenditure", minute + "");
//
////			setAlarmTime(calObj, hourOfDay, minute);
//
////			tvTime.setText(updateTime(hourOfDay, minute));
//
//		}
//	}


	private String updateTime(int hours, int mins) {

		String timeSet = "";
		if (hours > 12) {
			hours -= 12;
			timeSet = "PM";
		} else if (hours == 0) {
			hours += 12;
			timeSet = "AM";
		} else if (hours == 12)
			timeSet = "PM";
		else
			timeSet = "AM";

		String minutes = "";
		if (mins < 10)
			minutes = "0" + mins;
		else
			minutes = String.valueOf(mins);

		// Append in a StringBuilder
		String HrsTime_12 = new StringBuilder().append(hours).append(':')
				.append(minutes).append(timeSet).toString();

		return HrsTime_12;

	}


	protected boolean validateData() {
		// TODO Auto-generated method stub


		String description = desciption.getText().toString().trim();
		desciption.setError(null);

		if (description.isEmpty()) {
			desciption.setError("Description");
			return false;
		} else {
			return true;
		}

	}
	
	/*protected boolean validateDataWithoutTime() {
		// TODO Auto-generated method stub
		
    	String description = desciption.getText().toString().trim();
		desciption.setError(null);
		
		if (description.isEmpty()) {
			desciption.setError("Description");
			return false;
		}else {
			return true;
		}
			
	}*/

	private class MyFocusChangeListener implements View.OnFocusChangeListener {

		public void onFocusChange(View v, boolean hasFocus){

			if(v.getId() == R.id.description&& !hasFocus) {

				InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

			}
		}
	}
	private void dataUploading() {

		int mnth = calObj.getMonth() + 1;
		String dateAdding = calObj.getPrevYear() + "-" + mnth + "-" + calObj.getDay();
		String description = desciption.getText().toString().trim();

		submitReminder(description, dateAdding);

	}

	private void setGridCellAdapterToDate(int month, int year) {
		adapter = new GridCellAdapter(this.getApplicationContext(), R.id.calendar_day_gridcell, month, year, true);
		_calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
		currentMonth.setText(DateFormat.format(dateTemplate, _calendar.getTime()));
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v == prevMonth) {
//			setCurrenttime();
			if (month <= 1) {
				month = 12;
				year--;
			} else
				month--;
			setGridCellAdapterToDate(month, year);
		}
		if (v == nextMonth) {
//        	setCurrenttime();
			if (month > 11) {
				month = 1;
				year++;
			} else
				month++;
			setGridCellAdapterToDate(month, year);
		}


	}


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent returnIntent = new Intent();
		setResult(Activity.RESULT_CANCELED, returnIntent);
		super.onBackPressed();
	}


	public class GridCellAdapter extends BaseAdapter {
		private final Context _context;

		//        private final List<CalendarE> list;
		private static final int DAY_OFFSET = 1;
		private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

		private  final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		private int daysInMonth;
		private int currentDayOfMonth;
		private int currentWeekDay;
		private TextView gridcell;
		private TextView num_events_per_day;
		RelativeLayout bg;
		ImageView selectedCalenderimg;
		private final HashMap<String, Integer> eventsPerMonthMap;
		Calendar calendar;

		// Days in Current Month
		public GridCellAdapter(Context context, int textViewResourceId, int month, int year, boolean wantToRefresh) {
			super();
			this._context = context;
//                this.list = new ArrayList<CalendarE>();
			calendar = Calendar.getInstance();
			setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
			setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));


			// Print Month
			if (gridList.size() == 0 || wantToRefresh) {
				printMonth(month, year);
			}


			// Find Number of Events
			eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
		}

		private String getMonthAsString(int i) {
			return months[i];
		}

		private int getNumberOfDaysOfMonth(int i) {
			return daysOfMonth[i];
		}

		public CalendarE getItem(int position) {
			return gridList.get(position);
		}

		@Override
		public int getCount() {
			return gridList.size();
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


			gridList.clear();
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
//                        list.add(String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i) + "-GREY" + "-" + getMonthAsString(prevMonth) + "-" + prevYear);

				gridList.add(new CalendarE(((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i), 0, prevMonth, prevYear, false));
			}

			// Current Month Days
			for (int i = 1; i <= daysInMonth; i++) {
				if (i == getCurrentDayOfMonth()) {
					gridList.add(new CalendarE(i, 1, currentMonth, yy, false));

				} else {
					gridList.add(new CalendarE(i, 2, currentMonth, yy, false));
				}
                               /* list.add(String.valueOf(i) + "-BLUE" + "-" + getMonthAsString(currentMonth) + "-" + yy);
                        else
                                list.add(String.valueOf(i) + "-WHITE" + "-" + getMonthAsString(currentMonth) + "-" + yy);*/
			}

			// Leading Month days
			for (int i = 0; i < gridList.size() % 7; i++) {

				gridList.add(new CalendarE(i + 1, 0, nextMonth, nextYear, false));
//                        list.add(String.valueOf(i + 1) + "-GREY" + "-" + getMonthAsString(nextMonth) + "-" + nextYear);
			}
		}

		private HashMap<String, Integer> findNumberOfEventsPerMonth(int year, int month) {
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			return map;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View row = convertView;
			if (row == null) {
				LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.calander_each_cell, parent, false);
			}

			// Get a reference to the Day gridcell
			gridcell = (TextView) row.findViewById(R.id.calendar_day_gridcell);
			selectedCalenderimg = (ImageView) row.findViewById(R.id.selectedCalenderimg);
			bg = (RelativeLayout) row.findViewById(R.id.bg);
			gridcell.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (gridList.get(position).getWhichDay() != 0) { // before and after days
						if (gridPositionReminder != -1) {
							gridList.get(gridPositionReminder).setSelected(false);
						}
						gridPositionReminder = position;


//							 setBackgroundResource(R.drawable.calander_bg_user_select);
						gridList.get(position).setSelected(true);
						if (gridList.get(position).isSelected()) {
							bg.setBackgroundResource(R.drawable.calander_bg_user_select);
							selectedCalenderimg.setVisibility(View.VISIBLE);
						}

							notifyDataSetChanged();

//							dateAdding = calObj.getPrevYear()+"-"+(calObj.getMonth()+1)+"-"+calObj.getDay();

						calObj = (CalendarE) v.getTag();

						int mnth = calObj.getMonth() + 1;

//							dateSelected.setText(calObj.getPrevYear()+"-"+mnth+"-"+calObj.getDay());


					}

				}
			});

			// ACCOUNT FOR SPACING

//                String[] day_color = list.get(position).split("-");
			String theday = String.valueOf(gridList.get(position).getDay());
			String themonth = getMonthAsString(gridList.get(position).getMonth());
			String theyear = String.valueOf(gridList.get(position).getPrevYear());
			if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)) {
				if (eventsPerMonthMap.containsKey(theday)) {
					num_events_per_day = (TextView) row.findViewById(R.id.num_events_per_day);
					Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
					num_events_per_day.setText(numEvents.toString());
				}
			}

			// Set the Day GridCell
			gridcell.setText(theday);
			gridcell.setTag(new CalendarE().setData(gridList.get(position)));


			if (gridList.get(position).isSelected()) {
                	bg.setBackgroundResource(R.drawable.calander_bg_user_select);
				selectedCalenderimg.setVisibility(View.VISIBLE);
			} else {

				if (gridList.get(position).getWhichDay() == 0) {
					selectedCalenderimg.setVisibility(View.GONE);
					bg.setBackgroundResource(android.R.color.transparent);
					gridcell.setTextColor(Color.LTGRAY);
				}

				if (gridList.get(position).getWhichDay() == 2) {
					selectedCalenderimg.setVisibility(View.GONE);
					bg.setBackgroundResource(android.R.color.transparent);
					gridcell.setTextColor(Color.WHITE);
				}

				if (gridList.get(position).getWhichDay() == 1) {


					if ((gridList.get(position).getMonth() == calendar.get(Calendar.MONTH)) && (gridList.get(position).getPrevYear() == calendar.get(Calendar.YEAR))) {
						bg.setBackgroundResource(R.drawable.calander_bg);
						selectedCalenderimg.setVisibility(View.GONE);
					} else {
						selectedCalenderimg.setVisibility(View.GONE);
						bg.setBackgroundResource(android.R.color.transparent);
					}

					gridcell.setTextColor(Color.WHITE);
				}


			}

			return row;
		}


		public int getCurrentDayOfMonth() {
			return currentDayOfMonth;
		}

		private void setCurrentDayOfMonth(int currentDayOfMonth) {
			this.currentDayOfMonth = currentDayOfMonth;
		}

		public void setCurrentWeekDay(int currentWeekDay) {
			this.currentWeekDay = currentWeekDay;
		}

		public int getCurrentWeekDay() {
			return currentWeekDay;
		}
	}


	protected void setAlarmTime(CalendarE calObj, int hr, int min) {
		// TODO Auto-generated method

		long alrmTime = 0;
		boolean value = checkEachType();

		if (!value) {
			typeOfReminder = 0;
		}

		Calendar cal = Calendar.getInstance();

		int mnth = calObj.getMonth();


//			if (timeViewEnabled) {
		cal.set(calObj.getPrevYear(), mnth, calObj.getDay(), hr, min, 00);
		alrmTime = cal.getTimeInMillis();
			/*}else {
				
//				alrmTime = cal.getTimeInMillis()+60*10*1000;
				cal.set(calObj.getPrevYear(), mnth, calObj.getDay(), 00, 00, 00);
				alrmTime = cal.getTimeInMillis();
			}*/


		ContentValues eventValues = new ContentValues();

		eventValues.put("calendar_id", 1); // id, We need to choose from our mobile for primary its 1
		eventValues.put("title", "Coin Cop");
		eventValues.put("description", desciption.getText().toString().trim());
		eventValues.put("dtstart", alrmTime);
		eventValues.put("dtend", alrmTime + 60 * 1000);
		eventValues.put("eventTimezone", TimeZone.getDefault().getID());
		eventValues.put("eventStatus", 1); // This information is sufficient for     most entries tentative (0), confirmed (1) or canceled (2):
//	         eventValues.put("visibility", 0);
//	         eventValues.put("transparency", 0);
		if (isAlarmViewShows) {
			eventValues.put("hasAlarm", 1); // 0 for false, 1 for true
		} else {
			eventValues.put("hasAlarm", 0); // 0 for false, 1 for true
		}

		eventValues.put("allDay", 0);

			/*Intent intent = new Intent(Intent.ACTION_EDIT);
			intent.setType("vnd.android.cursor.item/event");
			intent.putExtra("beginTime", alrmTime);
			intent.putExtra("allDay", false);
//			intent.putExtra("rrule", "FREQ=DAILY");
			intent.putExtra("endTime", alrmTime+60*1000);
			intent.putExtra("title", "Coin Cop");
			intent.putExtra("description", desciption.getText().toString().trim());
			startActivityForResult(intent, 101);*/


		switch (typeOfReminder) {
			case 0:
				break;
			case 1:
				/*intent.putExtra(Events.RRULE,
						"FREQ=DAILY");*/

				eventValues.put(Events.RRULE, "FREQ=DAILY");
				break;
			case 2:
				
				/*intent.putExtra(Events.RRULE,
						"FREQ=WEEKLY");*/

				eventValues.put(Events.RRULE, "FREQ=WEEKLY");
				break;
			case 3:
				
				/*intent.putExtra(Events.RRULE,
						"FREQ=MONTHLY");*/

				eventValues.put(Events.RRULE, "FREQ=MONTHLY");

				break;
			case 4:
				
				/*intent.putExtra(Events.RRULE,
						"FREQ=YEARLY");*/

				eventValues.put(Events.RRULE, "FREQ=YEARLY");

				break;

			default:
				break;
		}

		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR},1);
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		Uri eventUriString = Uri.parse("content://com.android.calendar/events");
		eventUrl = getContentResolver().insert(eventUriString, eventValues);


		long eventID = Long.parseLong(eventUrl.getLastPathSegment());


		if (isAlarmViewShows) {
			ContentValues reminders = new ContentValues();
			reminders.put(CalendarContract.Reminders.EVENT_ID, eventID);
			reminders.put(CalendarContract.Reminders.METHOD, Reminders.METHOD_ALERT);
			reminders.put(CalendarContract.Reminders.MINUTES,"5");



//				  Uri uri2 = Uri.parse("content://com.android.calendar/reminders");
//			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
//				// TODO: Consider calling
//				//    ActivityCompat#requestPermissions
//				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR},1);
//				// here to request the missing permissions, and then overriding
//				//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//				//                                          int[] grantResults)
//				// to handle the case where the user grants the permission. See the documentation
//				// for ActivityCompat#requestPermissions for more details.
//				return;
//			}

			Uri a = getContentResolver().insert(CalendarContract.Reminders.CONTENT_URI, reminders);
			tools.showToastMessage("Reminder Activated");
			}
		     
	          
			  
	          resetValues();
	          
//			startActivity(intent);
			
			
	}

	private boolean deleteReminder() {
		long eventID = Long.parseLong(eventUrl.getLastPathSegment());
		/*Uri eventUri = Uri
				.parse("content://com.android.calendar/events");*/
		Uri deleteUri = null;
		deleteUri = ContentUris.withAppendedId(Events.CONTENT_URI, eventID);
		int val = getContentResolver().delete(deleteUri, null, null);

		
		
		Uri uri = Uri.parse("content://com.android.calendar/reminders");
		
		Uri deleteUriReminder = null;
		deleteUriReminder = ContentUris.withAppendedId(uri, eventID);
		
		int rows = getContentResolver().delete(deleteUriReminder, null, null);
		
		tools.showLog("delete :: "+rows+"", 1);
		
		
		if (val == 1) {
			return true;
		}else {
			return false;
		}
		
		
	}
	
	private void submitReminder(String description, String date) {

		 final ProgressDialog pd = new ProgressDialog(this);
		 pd.setMessage("Loading");
		 pd.setCancelable(false);
		 pd.show();

		HashMap<String, String> params = new HashMap<>();
//		params.put("user_action", "1018");
		params.put("user_id", BaseActivity.getpreference(Const.TAG_USERID, this));
		params.put("reminder_text", description);
		params.put("reminder_date", date);
		params.put("request","addreminder");
		APIService service = ServiceGenerator.createService(APIService.class, ActivitySetRemainder.this);
		Call<Modelsetreminder> call = service.add_reminder(params);
		call.enqueue(new Callback<Modelsetreminder>() {
			@Override
			public void onResponse(Call<Modelsetreminder> call, Response<Modelsetreminder> response) {
				if (pd.isShowing()) {
					pd.dismiss();
				}
				if (response.isSuccessful()) {
					Modelsetreminder modelsetreminder=response.body();
					if(modelsetreminder!=null&&modelsetreminder.getResult()!=null)
					{
						int value=modelsetreminder.getResult().getValue();
						String message=modelsetreminder.getResult().getMessage();
						tools.showToastMessage(message);
						if (value == 1) {
							setAlarmTime(calObj, hourOFDay, minOFHour);

							Intent returnIntent = new Intent();
							setResult(Activity.RESULT_OK, returnIntent);


							finish();
						}
					}

				}

			}

			@Override
			public void onFailure(Call<Modelsetreminder> call, Throwable t) {
				if (pd.isShowing()) {
					pd.dismiss();
				}
				tools.showToastMessage(getResources().getString(R.string.connectivity));
			}
		});
//		WebService.post(Const.URL+"addreminder", params, new AsyncHttpResponseHandler() {
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
//
//				parse(new String(arg2));
//				tools.showLog(new String(arg2), 2);
//
//				if (pd.isShowing()) {
//					pd.dismiss();
//				}
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
//				tools.showToastMessage(getResources().getString(R.string.connectivity));
//			}
//
//			private void parse(String string) {
//				// TODO Auto-generated method stub
//
//				String message = "";
//				int value = 0;
//
//				try {
//
//					JSONObject jobject = new JSONObject(string);
//					JSONObject jsonobject = jobject.getJSONObject("result");
//
//					value = jsonobject.getInt("value");
//					message = jsonobject.getString("message");
//					String game_id = jsonobject.getString("game_id");
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//				tools.showToastMessage(message);
//
//				if (value == 1) {
//					setAlarmTime(calObj, hourOFDay, minOFHour);
//
//					Intent returnIntent = new Intent();
//					setResult(Activity.RESULT_OK, returnIntent);
//
//
//					finish();
//				}
//			}
//		});
	}
	
	boolean checkEachType() {
		// TODO Auto-generated method stub
		boolean isbtnActivated = false;

		for (int j = 0; j < toggleGroup.getChildCount(); j++) {

			final LinearLayout viewLinaer = (LinearLayout) toggleGroup
					.getChildAt(j);
			final ToggleButton view = (ToggleButton) viewLinaer.getChildAt(0);
			if (view.isChecked()) {
				isbtnActivated = true;
			}
		}
		return isbtnActivated;

	}


	protected void resetValues() {
		// TODO Auto-generated method stub
		desciption.setText("");
		gridList.get(gridPositionReminder).setSelected(false);
		adapter.notifyDataSetChanged();
		hourOFDay = 0;
		minOFHour = 0;
		isAlarmViewShows = false;
		
		for (int j = 0; j < toggleGroup.getChildCount(); j++) {

			final LinearLayout viewLinaer = (LinearLayout) toggleGroup
					.getChildAt(j);
			final ToggleButton view = (ToggleButton) viewLinaer.getChildAt(0);
				view.setChecked(false);
		}
		
	}

}
