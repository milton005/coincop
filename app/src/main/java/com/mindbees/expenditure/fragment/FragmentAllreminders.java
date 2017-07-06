package com.mindbees.expenditure.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mindbees.expenditure.ActivityAddAccount;
import com.mindbees.expenditure.MainActivity;
import com.mindbees.expenditure.R;
import com.mindbees.expenditure.Interfaces.ImageVisibility;
import com.mindbees.expenditure.adapter.AdapterAllReminder;
import com.mindbees.expenditure.model.AllReminders;
import com.mindbees.expenditure.model.Homefragment.Modelallreminder;
import com.mindbees.expenditure.model.Homefragment.Reminder;
import com.mindbees.expenditure.model.StatusAccount;
import com.mindbees.expenditure.reminder.ActivitySetRemainder;
import com.mindbees.expenditure.retrofit.APIService;
import com.mindbees.expenditure.retrofit.ServiceGenerator;
import com.mindbees.expenditure.ui.FloatingActionButton;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;
import com.mindbees.expenditure.util.WebService;
import com.mindbees.expenditure.util.XMLParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAllreminders extends Fragment {

	public static final String TAG = "fragment_all_remainders";
	ImageVisibility mCallback;
	MainActivity activity;

	ListView listAllReminders;

	ArrayList<Reminder> allReminderList;
	AdapterAllReminder adapter;

	SwipeRefreshLayout mSwipeRefreshLayout;
	Tools tools;

	TextView noviewLabel;
	FloatingActionButton fab;
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_allreminders, container, false);

		anim_fab = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_btn_zoom);
		tools = new Tools(activity);
		mCallback.isVisible(Const.TAG_REMINDER);

		listAllReminders = (ListView) v.findViewById(R.id.list_allReminders);
		mSwipeRefreshLayout = (SwipeRefreshLayout) v
				.findViewById(R.id.activity_main_swipe_refresh_layout);

		noviewLabel = (TextView) v.findViewById(R.id.noViewLabel);

		allReminderList = new ArrayList<Reminder>();
		fab = (FloatingActionButton) v.findViewById(R.id.fab);

		adapter = new AdapterAllReminder(activity, allReminderList);
		listAllReminders.setAdapter(adapter);


		mSwipeRefreshLayout
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						loadAllReminders(BaseActivity.getpreference(Const.TAG_USERID, activity));
					}
				});

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		if (tools.isConnectingToInternet()) {
			loadAllReminders(BaseActivity.getpreference(Const.TAG_USERID, activity));
		} else {
			tools.showToastMessage(activity.getResources().getString(R.string.connectivity));
		}

		fab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fab.setVisibility(View.GONE);
				Intent i = new Intent(getActivity(), ActivitySetRemainder.class);
				startActivityForResult(i, 3);

			}
		});

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 3) {
			if (resultCode == Activity.RESULT_OK) {
				if (tools.isConnectingToInternet()) {
					loadAllReminders(BaseActivity.getpreference(Const.TAG_USERID, activity));
				} else {
					tools.showToastMessage(activity.getResources().getString(R.string.connectivity));
				}
			}
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub


		initializeTimerTask();
		super.onResume();
	}

	public void initializeTimerTask() {


		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// Do something after 5s = 5000ms
				fab.setVisibility(View.VISIBLE);
				fab.startAnimation(anim_fab);
			}
		}, Const.TAG_TIMER_DELAY);
	}


	private void loadAllReminders(String userid) {

		// final ProgressDialog pd = new ProgressDialog(getActivity());
		// pd.setMessage("Loading");
		// pd.setCancelable(false);


//		final ProgressDialog pd = new ProgressDialog(getActivity());
//		pd.setMessage("Loading");
//		pd.setCancelable(false);
		mSwipeRefreshLayout.setRefreshing(true);

		HashMap<String, String> params = new HashMap<>();
//		params.put("user_action", "1024");
		params.put("user_id", BaseActivity.getpreference(Const.TAG_USERID, activity));
		params.put("request","reminders");
//		params.put("search_date", date);
		APIService service = ServiceGenerator.createService(APIService.class, getContext());
		Call<Modelallreminder> call = service.all_reminder(params);
		call.enqueue(new Callback<Modelallreminder>() {
			@Override
			public void onResponse(Call<Modelallreminder> call, Response<Modelallreminder> response) {
//				if (pd.isShowing()) {
//					pd.dismiss();
//				}
				mSwipeRefreshLayout.setRefreshing(false);
				if (response.isSuccessful()) {
					allReminderList.clear();

					if (response.body() != null) {
						if (response.body().getReminder().size() > 0) {
							List<Reminder> reminders = response.body().getReminder();
							allReminderList.addAll(reminders);
							adapter.notifyDataSetChanged();
							if (reminders.size() != 0) {
								noviewLabel.setVisibility(View.GONE);
							} else {
								noviewLabel.setVisibility(View.VISIBLE);

							}
						}
					}
				}

			}

			@Override
			public void onFailure(Call<Modelallreminder> call, Throwable t) {
				mSwipeRefreshLayout.setRefreshing(false);
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
}
