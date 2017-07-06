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
import com.mindbees.expenditure.ActivityEditAccount;
import com.mindbees.expenditure.MainActivity;

import com.mindbees.expenditure.Interfaces.DeleteItem;
import com.mindbees.expenditure.Interfaces.ImageVisibility;
import com.mindbees.expenditure.R;
import com.mindbees.expenditure.adapter.AdapterAcntStats;
import com.mindbees.expenditure.adapter.AdapterAccountRemoveView;
import com.mindbees.expenditure.model.Accounts;
import com.mindbees.expenditure.model.Loadaccount.Account;
import com.mindbees.expenditure.model.Loadaccount.Modeloadaccount;
import com.mindbees.expenditure.model.StatusAccount;
import com.mindbees.expenditure.model.delaccount.Modeldelaccount;
import com.mindbees.expenditure.retrofit.APIService;
import com.mindbees.expenditure.retrofit.ServiceGenerator;
import com.mindbees.expenditure.ui.FloatingActionButton;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;
import com.mindbees.expenditure.util.WebService;
import com.mindbees.expenditure.util.XMLParser;
import com.mindbees.expenditure.ui.swiperemove.Attributes;
import com.mindbees.expenditure.ui.swiperemove.SwipeLayout;
import com.special.ResideMenu.ResideMenu;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAccounts extends Fragment implements DeleteItem{

	public static final String TAG ="fragment_account" ;
	ImageVisibility mCallback;

	ListView listaccount_Status;
	ArrayList<StatusAccount> accountStatus;
//	AdapterAcntStats adapter;
	AdapterAccountRemoveView adapter;
	Tools tools;

	SwipeRefreshLayout mSwipeRefreshLayout;
	FloatingActionButton fab;
	
	Animation anim_fab;
	
	TextView noviewLabel;
	
	RelativeLayout gestrBlock;
	MainActivity activity;
	
	 private ResideMenu resideMenu;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			mCallback = (ImageVisibility) activity;
			this.activity = (MainActivity) activity;
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
		View v = inflater.inflate(R.layout.fragment_accounts,container, false);
		mCallback.isVisible(Const.TAG_ACNTS);

		anim_fab = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_btn_zoom);
		
		gestrBlock = (RelativeLayout) v.findViewById(R.id.blockGesture);
		resideMenu = activity.getResideMenu();
		resideMenu.addIgnoredView(gestrBlock);
		
		
		listaccount_Status = (ListView) v.findViewById(R.id.account_status);
		
		View footerview = LayoutInflater.from(activity).inflate(R.layout.layout_footer, null, false);
		listaccount_Status.addFooterView(footerview);
		
		mSwipeRefreshLayout = (SwipeRefreshLayout) v
				.findViewById(R.id.activity_main_swipe_refresh_layout);
		fab = (FloatingActionButton) v.findViewById(R.id.fab);
		tools = new Tools(getActivity());
		
		noviewLabel = (TextView) v.findViewById(R.id.noViewLabel);

		accountStatus = new ArrayList<StatusAccount>();
		adapter = new AdapterAccountRemoveView(getActivity(), accountStatus, true);
		adapter.setCallBack(this);
		listaccount_Status.setAdapter(adapter);
		
		adapter.setMode(Attributes.Mode.Single);
		listaccount_Status.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String Acntid=accountStatus.get(position).getAccount_id();
//				tools.showToastMessage(Acntid);
				Intent i=new Intent(getActivity(), ActivityEditAccount.class);
				i.putExtra("Account_Id",Acntid);
				startActivityForResult(i,3);

//                ((SwipeLayout)(listaccount_Status.getChildAt(position - listaccount_Status.getFirstVisiblePosition()))).open();
            }
        });
		/*listaccount_Status.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("ListView", "OnTouch");
                return false;
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "OnItemLongClickListener", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.e("ListView", "onScrollStateChanged");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ListView", "onItemSelected:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("ListView", "onNothingSelected:");
            }
        });*/
		
		
		
		
		
		

		mSwipeRefreshLayout
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						loadAccounts(BaseActivity.getpreference(
								Const.TAG_USERID, getActivity()));
					}
				});

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		if (tools.isConnectingToInternet()) {
			
			tools.showLog("onactivity", 1);
			loadAccounts(BaseActivity.getpreference(Const.TAG_USERID,
					getActivity()));
		} else {
			tools.showToastMessage(activity.getResources().getString(R.string.connectivity));
		}
		
		fab.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fab.setVisibility(View.GONE);
				Intent i = new Intent(getActivity(), ActivityAddAccount.class);
//				i.putExtra("fromTab", false);
				startActivityForResult(i, 2);
				
			}
		});
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
//		fab.setVisibility(View.VISIBLE);
		initializeTimerTask();
		
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

	private void loadAccounts(String userid) {
		mSwipeRefreshLayout.setRefreshing(true);
		// final ProgressDialog pd = new ProgressDialog(getActivity());
		// pd.setMessage("Loading");
		// pd.setCancelable(false);
		HashMap<String, String> params = new HashMap<>();
//		params.put("user_action", "1008");
		params.put("user_id", userid);
		params.put("request","useraccounts");
		APIService service = ServiceGenerator.createService(APIService.class,getContext());
		Call<Modeloadaccount> call = service.load_accounts(params);
		call.enqueue(new Callback<Modeloadaccount>() {
			@Override
			public void onResponse(Call<Modeloadaccount> call, Response<Modeloadaccount> response) {
				mSwipeRefreshLayout.setRefreshing(false);
				if(response.isSuccessful())
				{accountStatus.clear();
					if(response.body()!=null&&response.body().getAccount()!=null) {
						List<Account> accounts = response.body().getAccount();
						for (int i = 0; i < accounts.size(); i++){
							String account_id = accounts.get(i).getAccountId();
							String user_id = accounts.get(i).getUserId();
							String account_title = accounts.get(i).getAccountTitle();
							String added_date = accounts.get(i).getAddedDate();
							String initial_amount = accounts.get(i).getInitialAmount();
							String final_balance = accounts.get(i).getFinalBalance();

							StatusAccount scnt = new StatusAccount();
							scnt.setAccount_id(account_id);
							scnt.setUserid(user_id);
							scnt.setAccount_tittle(account_title);
							scnt.setAddedDate(added_date);
							scnt.setInitial_amount(initial_amount);
							scnt.setFinal_balance(final_balance);
							scnt.setNumber(i + 1);
							accountStatus.add(scnt);
						}
						adapter.notifyDataSetChanged();
						if (accountStatus.size() != 0) {
							noviewLabel.setVisibility(View.GONE);
						}else {
							noviewLabel.setVisibility(View.VISIBLE);

						}

					}
				}
			}

			@Override
			public void onFailure(Call<Modeloadaccount> call, Throwable t) {
				mSwipeRefreshLayout.setRefreshing(false);
				tools.showToastMessage(activity.getResources().getString(R.string.connectivity));
			}
		});
//		WebService.post(Const.URL+"useraccounts", params, new AsyncHttpResponseHandler() {
//
//			@Override
//			public void onStart() {
//				// TODO Auto-generated method stub
//				super.onStart();
//				// pd.show();
//				mSwipeRefreshLayout.setRefreshing(true);
//			}
//
//			@Override
//			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//				// TODO Auto-generated method stub
//
//				tools.showLog(new String(arg2), 2);
//				parse(new String(arg2));
//
//				mSwipeRefreshLayout.setRefreshing(false);
//				/*
//				 * if (pd.isShowing()) { pd.dismiss(); }
//				 */
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
//				mSwipeRefreshLayout.setRefreshing(false);
//
//				/*
//				 * if (pd.isShowing()) { pd.dismiss(); }
//				 */
//
//				Tools.debugThrowable(arg3);
//
//				if (arg2 != null) {
//					Tools.debugResponse(new String(arg2));
//				}
//				tools.showToastMessage(activity.getResources().getString(R.string.connectivity));
//			}
//
//			private void parse(String string) {
//				// TODO Auto-generated method stub
//
//				// allCategory = new ArrayList<Category>();
//
//				accountStatus.clear();
//				try {
//
//					JSONObject jobject = new JSONObject(string);
//					JSONArray jarray = jobject.getJSONArray("account");
//
//					for (int i = 0; i < jarray.length(); i++) {
//						JSONObject jsonobject = jarray.getJSONObject(i);
//
//						String account_id = jsonobject.getString("account_id");
//						String user_id = jsonobject.getString("user_id");
//						String account_title = jsonobject.getString("account_title");
//						String added_date = jsonobject.getString("added_date");
//						String initial_amount = jsonobject.getString("initial_amount");
//						String final_balance = jsonobject.getString("final_balance");
//
//						StatusAccount scnt = new StatusAccount();
//						scnt.setAccount_id(account_id);
//						scnt.setUserid(user_id);
//						scnt.setAccount_tittle(account_title);
//						scnt.setAddedDate(added_date);
//						scnt.setInitial_amount(initial_amount);
//						scnt.setFinal_balance(final_balance);
//						scnt.setNumber(i + 1);
//
//						accountStatus.add(scnt);
//
//					}
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				adapter.notifyDataSetChanged();
//
//				if (accountStatus.size() != 0) {
//					noviewLabel.setVisibility(View.GONE);
//				}else {
//					noviewLabel.setVisibility(View.VISIBLE);
//
//				}
//
//			}
//		});
	}

	@Override
	public void onDeleteItem(int position) {
		// TODO Auto-generated method stub
		wannaDelete(position);
	}
	
	
	public void wannaDelete(final int position) {
		new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle)
				.setMessage("Do you want to delete ?")
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								
								deleteItem(position);
							}

				}).setNegativeButton("No", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						 ((SwipeLayout)(listaccount_Status.getChildAt(position - listaccount_Status.getFirstVisiblePosition()))).close(true);
					}
				}).show();
	}

	private void deleteItem(final int positon) {
		// TODO Auto-generated method stub

		final ProgressDialog pd = new ProgressDialog(getActivity());
		pd.setMessage("Loading");
		pd.setCancelable(false);
		pd.show();

		HashMap<String, String> params = new HashMap<>();
//		params.put("user_action", "1008");
		params.put("user_id", BaseActivity.getpreference(Const.TAG_USERID, activity));
		params.put("account_id", adapter.allList.get(positon).getAccount_id());
		params.put("request","delaccount");
		APIService service = ServiceGenerator.createService(APIService.class,getContext());
		Call<Modeldelaccount> call = service.del_account(params);
		call.enqueue(new Callback<Modeldelaccount>() {
			@Override
			public void onResponse(Call<Modeldelaccount> call, Response<Modeldelaccount> response) {
				if (pd.isShowing()) {
					pd.dismiss();
				}
				if (response.isSuccessful())
				{
					if (response.body()!=null&&response.body().getResult()!=null)
					{
						Modeldelaccount modeldelaccount=response.body();
						String message=modeldelaccount.getResult().getMessage();
						int value=modeldelaccount.getResult().getValue();
						tools.showToastMessage(message);
						if (value == 1) {
							((SwipeLayout)(listaccount_Status.getChildAt(positon - listaccount_Status.getFirstVisiblePosition()))).close();
							adapter.allList.remove(positon);
							adapter.notifyDataSetChanged();
							if (adapter.allList.size() == 0) {
								noviewLabel.setVisibility(View.VISIBLE);
							}

						}
					}
				}
			}

			@Override
			public void onFailure(Call<Modeldelaccount> call, Throwable t) {
				if (pd.isShowing()) {
					pd.dismiss();
				}
			}
		});
//		WebService.post(Const.URL+"delaccount", params, new AsyncHttpResponseHandler() {
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
////				tools.showLog(new String(arg2), 2);
//
//				 if (pd.isShowing()) {
//					 pd.dismiss(); }
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
//			private void parse(String string) {
//				// TODO Auto-generated method stub
//
//				// allCategory = new ArrayList<Category>();
//				int value = 0;
//				String message = "";
//
//				try {
//
//					JSONObject jobject = new JSONObject(string);
//					JSONObject jsonobject = jobject.getJSONObject("result");
//
//					value = jsonobject.getInt("value");
//					message = jsonobject.getString("message");
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				tools.showToastMessage(message);
//
//				if (value == 1) {
//	                ((SwipeLayout)(listaccount_Status.getChildAt(positon - listaccount_Status.getFirstVisiblePosition()))).close();;
//					adapter.allList.remove(positon);
//					adapter.notifyDataSetChanged();
//					if (adapter.allList.size() == 0) {
//						noviewLabel.setVisibility(View.VISIBLE);
//					}
//
//				}
//
//			}
//		});
		
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 2) {
	        if (resultCode == Activity.RESULT_OK) {
	        	tools.showLog("onactivityResult", 1);
	           loadAccounts(BaseActivity.getpreference(Const.TAG_USERID, getActivity()));
	        } 
	    }
		if (requestCode==3)
		{
			if (resultCode == Activity.RESULT_OK) {
				tools.showLog("onactivityResult", 1);
				loadAccounts(BaseActivity.getpreference(Const.TAG_USERID, getActivity()));
			}
		}
		
	}


}
