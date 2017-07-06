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
import com.mindbees.expenditure.MainActivity;
import com.mindbees.expenditure.R;
import com.mindbees.expenditure.Interfaces.DeleteItem;
import com.mindbees.expenditure.Interfaces.ImageVisibility;
import com.mindbees.expenditure.adapter.AdapterCategory;
import com.mindbees.expenditure.adapter.AdapterCategoryRemoveView;
import com.mindbees.expenditure.adapter.AdapterCategoryRemoveViewT;
import com.mindbees.expenditure.model.Category;
import com.mindbees.expenditure.model.creditdebit.loadcategory.Account;
import com.mindbees.expenditure.model.creditdebit.loadcategory.Modelloadcategory;
import com.mindbees.expenditure.model.delaccount.Modeldelaccount;
import com.mindbees.expenditure.retrofit.APIService;
import com.mindbees.expenditure.retrofit.ServiceGenerator;
import com.mindbees.expenditure.ui.FloatingActionButton;
import com.mindbees.expenditure.ui.HeaderFooterGridView;
import com.mindbees.expenditure.ui.swiperemove.Attributes;
import com.mindbees.expenditure.ui.swiperemove.SwipeLayout;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;
import com.mindbees.expenditure.util.WebService;
import com.mindbees.expenditure.util.XMLParser;
import com.special.ResideMenu.ResideMenu;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCategoryExpense extends Fragment implements DeleteItem{
	FragmentManager mFragmentManager;
	HeaderFooterGridView gridList;
	MainActivity activity;
	ImageVisibility mCallback;
	FragmentTransaction mFragmentTransaction;
	Tools tools;
	
	ArrayList<Category> allCategoryExpense;
	AdapterCategory adapterExpense;
//	AdapterCategoryRemoveView adapterExpense;
	
	TextView noviewLabel;
	BroadcastReceiver breceiver;
	
	RelativeLayout gestrBlock;
	
	 private ResideMenu resideMenu;
	
//	FloatingActionButton fab;
//	Animation anim_fab;

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
		View v = inflater.inflate(R.layout.fragment_category_expense, null);
		tools = new Tools(getActivity());
		
		gestrBlock = (RelativeLayout) v.findViewById(R.id.blockGesture);
		resideMenu = activity.getResideMenu();
		resideMenu.addIgnoredView(gestrBlock);
		
		mCallback.isVisible(Const.TAG_CATGS_EXP);
		
//		anim_fab = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_btn_zoom);
//		fab = (FloatingActionButton) v.findViewById(R.id.fab);
		
		gridList = (HeaderFooterGridView) v.findViewById(R.id.categoryList);
		noviewLabel = (TextView) v.findViewById(R.id.noViewLabel);
		
		View footerview = LayoutInflater.from(activity).inflate(R.layout.layout_footer, null, false);
		gridList.addFooterView(footerview,"", false);
		
		allCategoryExpense = new ArrayList<Category>();
//		adapterExpense = new AdapterCategoryRemoveView(getActivity(), allCategoryExpense);
		adapterExpense=new AdapterCategory(getActivity(),allCategoryExpense);
//		adapterExpense.setCallBack(this);
//		adapterExpense.setMode(Attributes.Mode.Single);
		gridList.setAdapter(adapterExpense);
		gridList.setSelected(false);
		
		return v;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (tools.isConnectingToInternet()) {
			loadCategorys(BaseActivity.getpreference(Const.TAG_USERID, getActivity()));
		}else {
			tools.showToastMessage(activity.getResources().getString(R.string.connectivity));
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		tools.showLog("destroy", 1);
//		getActivity().unregisterReceiver(breceiver);
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		
		/*IntentFilter intentFilter = new IntentFilter();
	    intentFilter.addAction(Const.KEY_FILTER+".ACTION_RQST_REFRESH_EXP");
		breceiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				
				tools.showLog("receiver", 1);
//				adapterExpense.allList.clear();
				
				loadCategorys(BaseActivity.getpreference(Const.TAG_USERID, getActivity()));
				
			}
		};
		
		
		getActivity().registerReceiver(breceiver, intentFilter);*/
		
		gridList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            	 ((SwipeLayout)(gridList.getChildAt(position - gridList.getFirstVisiblePosition()))).open(true);
				if (Integer.parseInt(allCategoryExpense.get(position).getCat_color()) == 0) {
				tools.showToastMessage("Cant edit default category");
				}
				else
				{
					String tid=allCategoryExpense.get(position).getCatId();
//					tools.showToastMessage(tid);
					Bundle args = new Bundle();
					args.putInt("Id",Integer.parseInt(tid));
					args.putInt("FRAG_TYPE",1);
					mFragmentManager = getActivity().getSupportFragmentManager();
					mFragmentTransaction = mFragmentManager.beginTransaction();
					Popupeditcategory p=Popupeditcategory.NewInstance();
					p.setArguments(args);
					p.show(mFragmentTransaction,"changeTitle");
				}


            }
        });
		gridList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				if (Integer.parseInt(allCategoryExpense.get(position).getCat_color()) == 0) {
					tools.showToastMessage("Cant delete default category");
				}
				else
				{
					wannaDelete(position);
				}

				return false;
			}
		});
	}
	
	/*@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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
	 
	 @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		fab.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
//				fab.setVisibility(View.GONE);
			}
		});
	}*/
	
	private void loadCategorys(String userid){

		final ProgressDialog pd = new ProgressDialog(getActivity());
		pd.setMessage("Loading");
		pd.setCancelable(false);
		pd.show();
		
		tools.showLog("loading", 1);

		HashMap<String, String> params = new HashMap<>();
//		params.put("user_action", "1011");
		params.put("user_id", userid);
		params.put("type_id", Const.TAG_EXPENSE_ID); // expense
		params.put("request","usercategory");

		APIService service = ServiceGenerator.createService(APIService.class,getContext());
		Call<Modelloadcategory> call = service.load_category(params);
		call.enqueue(new Callback<Modelloadcategory>() {
			@Override
			public void onResponse(Call<Modelloadcategory> call, Response<Modelloadcategory> response) {
				if (pd.isShowing()) {
					pd.dismiss();
				}
				if (response.isSuccessful())
				{
					if (response.body()!=null&&response.body().getAccount()!=null)
					{allCategoryExpense.clear();
						List<Account> accounts=response.body().getAccount();
						for(int i=0;i<accounts.size();i++) {
							String catId = accounts.get(i).getCategoryId();
							String catTitlle = accounts.get(i).getCategoryTitle();
							String type_id = accounts.get(i).getTypeId();
							String user_id = accounts.get(i).getUserId();
							String added_date = accounts.get(i).getAddedDate();
							String cat_image = accounts.get(i).getCatImage();
							String cat_color = accounts.get(i).getCatColor();
//							allCategory.addAll(accounts);
//							adapterViewPager.notifyDataSetChanged();
							Category cat = new Category();
							cat.setCatId(catId);
							cat.setCatTitlle(catTitlle);
							cat.setType_id(type_id);
							cat.setUser_id(user_id);
							cat.setAdded_date(added_date);
							cat.setCat_image(cat_image);
							cat.setCat_color(cat_color);
							cat.setSelected(false);
							allCategoryExpense.add(cat);
						}
						adapterExpense.notifyDataSetChanged();
//						adapterExpense = new AdapterCategory(getActivity(), allCategoryExpense);
//						adapterExpense.setCallBack(FragmentCategoryExpense.this);
//						adapterExpense.setMode(Attributes.Mode.Single);
//						gridList.setAdapter(adapterExpense);
						gridList.setSelected(false);
						if (allCategoryExpense.size() != 0) {
							noviewLabel.setVisibility(View.GONE);
						}else {
							noviewLabel.setVisibility(View.VISIBLE);

						}
					}
				}
			}

			@Override
			public void onFailure(Call<Modelloadcategory> call, Throwable t) {

			}
		});
//		WebService.post(Const.URL+"usercategory", params, new AsyncHttpResponseHandler() {
//
//
//			@Override
//			public void onStart() {
//				// TODO Auto-generated method stub
//				super.onStart();
//				pd.show();
//			}
//
//			@Override
//			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//				// TODO Auto-generated method stub
//
//				parse(new String(arg2));
//				tools.showLog(new String(arg2), 2);
//				 if (pd.isShowing()) {
//	                    pd.dismiss();
//	                }
//
//			}
//
//
//
//			@Override
//			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//				// TODO Auto-generated method stub
//
//				try {
//					Tools.debugResponse(new String(arg2));
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//                if (pd.isShowing()) {
//                    pd.dismiss();
//                }
//
//                Tools.debugThrowable(arg3);
//
//                if (arg2 != null) {
//                    Tools.debugResponse(new String(arg2));
//                }
//                tools.showToastMessage(activity.getResources().getString(R.string.connectivity));
//			}
//
//
//			private void parse(String string) {
//				// TODO Auto-generated method stub
////				allCategoryExpense.clear();
//				allCategoryExpense = new ArrayList<Category>();
//				String imageLink = Const.imageLink;
//				XMLParser parser = new XMLParser();
//		        try {
//		        	JSONObject jobject = new JSONObject(string);
//		            JSONArray jarray = jobject.getJSONArray("account");
//		            for (int i = 0; i < jarray.length(); i++) {
//
//		            	JSONObject jsonobject  = jarray.getJSONObject(i);
//
//		            	String catId = jsonobject.getString("category_id");
//		            	String catTitlle = jsonobject.getString("category_title");
//		            	String type_id = jsonobject.getString("type_id");
//		            	String user_id = jsonobject.getString("user_id");
//		            	String added_date = jsonobject.getString("added_date");
//		            	String cat_image = jsonobject.getString("cat_image");
//		            	String cat_color = jsonobject.getString("cat_color");
//
//		            	Category cat = new Category();
//		            	cat.setCatId(catId);
//		            	cat.setCatTitlle(catTitlle);
//		            	cat.setType_id(type_id);
//		            	cat.setUser_id(user_id);
//		            	cat.setAdded_date(added_date);
//		            	cat.setCat_image(imageLink+cat_image);
//		            	cat.setCat_color(cat_color);
//		            	cat.setSelected(false);
//
//		            	allCategoryExpense.add(cat);
//
//					}
//		        }catch(Exception e){
//		        	e.printStackTrace();
//		        }
////		        adapterExpense.allList.clear();
////		        adapterExpense.notifyDataSetChanged();
//
////		        adapterExpense = new AdapterCategoryRemoveView(getActivity(), allCategoryExpense);
////				adapterExpense.setCallBack(FragmentCategoryExpense.this);
////				adapterExpense.setMode(Attributes.Mode.Single);
////				gridList.setAdapter(adapterExpense);
////				gridList.setSelected(false);
//
//		        if (allCategoryExpense.size() != 0) {
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
		android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
			builder	.setTitle("Do you want to delete ?")
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								
								deleteItem(position);
							}

//				}).setNegativeButton("No", new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						// TODO Auto-generated method stub
//						return ;
////						 ((SwipeLayout)(gridList.getChildAt(position - gridList.getFirstVisiblePosition()))).open(false);
//					}
				}).setNegativeButton("No",null);

			builder.show();
	}

	private void deleteItem(final int positon) {
		// TODO Auto-generated method stub

		final ProgressDialog pd = new ProgressDialog(getActivity());
		pd.setMessage("Loading");
		pd.setCancelable(false);
		pd.show();
		HashMap<String, String> params = new HashMap<>();
//		params.put("user_action", "1027");
		params.put("user_id", BaseActivity.getpreference(Const.TAG_USERID, activity));
		params.put("category_id", adapterExpense.allList.get(positon).getCatId());
		params.put("request","delcategory");
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
//							((SwipeLayout)(gridList.getChildAt(positon - gridList.getFirstVisiblePosition()))).close();

							adapterExpense.allList.remove(positon);
							adapterExpense.notifyDataSetChanged();

						}
					}
				}
			}

			@Override
			public void onFailure(Call<Modeldelaccount> call, Throwable t) {
				if (pd.isShowing()) {
					pd.dismiss();
				}
				tools.showToastMessage(activity.getResources().getString(R.string.connectivity));
			}
		});
//		WebService.post(Const.URL+"delcategory", params, new AsyncHttpResponseHandler() {
//
//			@Override
//			public void onStart() {
//				// TODO Auto-generated method stub
//				super.onStart();
//				// pd.show();
//			}
//
//			@Override
//			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//				// TODO Auto-generated method stub
//
//				parse(new String(arg2));
//				tools.showLog(new String(arg2), 2);
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
//
//	                ((SwipeLayout)(gridList.getChildAt(positon - gridList.getFirstVisiblePosition()))).close();
//
//	                adapterExpense.allList.remove(positon);
//	                adapterExpense.notifyDataSetChanged();
//
//				}
//
//			}
//		});
		
	}
	

}
