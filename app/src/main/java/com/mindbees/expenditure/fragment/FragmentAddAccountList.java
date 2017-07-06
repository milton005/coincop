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
import com.mindbees.expenditure.ActivityCredit_Debit;
import com.mindbees.expenditure.ActivityAddExpense_Income;
import com.mindbees.expenditure.R;
import com.mindbees.expenditure.adapter.AdapterAccounts;
import com.mindbees.expenditure.adapter.AdapterCategory;
import com.mindbees.expenditure.model.Accounts;
import com.mindbees.expenditure.model.Category;
import com.mindbees.expenditure.model.Loadaccount.Account;
import com.mindbees.expenditure.model.Loadaccount.Modeloadaccount;
import com.mindbees.expenditure.model.StatusAccount;
import com.mindbees.expenditure.model.creditdebit.loadcategory.Modelloadcategory;
import com.mindbees.expenditure.retrofit.APIService;
import com.mindbees.expenditure.retrofit.ServiceGenerator;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;
import com.mindbees.expenditure.util.WebService;
import com.mindbees.expenditure.util.XMLParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAddAccountList extends Fragment {

	Tools tools;
	ListView listOfAccounts;
	ArrayList<Accounts> allAccounts;
	AdapterAccounts adapter;
	ActivityCredit_Debit activity;
	TextView noLabel;

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

		View v = inflater.inflate(R.layout.fragment_account_cat, null);
		
//		setRetainInstance(true);

		tools = new Tools(getActivity());
		listOfAccounts = (ListView) v.findViewById(R.id.listBanks);
		noLabel = (TextView) v.findViewById(R.id.addAccount);
//		listOfAccounts.setSelector(R.drawable.list_selector);
		
		noLabel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), ActivityAddAccount.class);
//				i.putExtra("fromTab", true);
				startActivityForResult(i, 1);
			}
		});
		
		adapter = new AdapterAccounts(getActivity(), activity.allAccounts);
		listOfAccounts.setAdapter(adapter);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		
		
		if (tools.isConnectingToInternet()) {
			if (activity.allAccounts.size() == 0) {
				loadAccounts(BaseActivity.getpreference(Const.TAG_USERID, getActivity()));
			}else {
//				adapter = new AdapterAccounts(getActivity(), activity.allAccounts);
//				listOfAccounts.setAdapter(adapter);
				tools.showLog(activity.listPosition+"", 2);
				noLabel.setVisibility(View.GONE);
				 adapter.notifyDataSetChanged();
				 
			}
			
		}else {
			tools.showToastMessage(getActivity().getResources().getString(R.string.connectivity));
		}
		
		listOfAccounts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
//				tools.showLog(position+"", 2);
				if (activity.listPosition != -1) {
					adapter.allList.get(activity.listPosition).setSelected(false);
				}
				
				activity.listPosition = position;
				try {
					activity.finalBalance = Integer.parseInt(adapter.allList.get(activity.listPosition).getFinal_balance());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (Exception e) {
					// TODO: handle exception
				}
				
//				listOfAccounts.getChildAt(position).setBackgroundColor(color);
				adapter.allList.get(position).setSelected(true);
				adapter.notifyDataSetChanged();
				activity.bankAccountId = adapter.allList.get(position).getAccount_id();
			}
		});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 1) {
	        if (resultCode == Activity.RESULT_OK) {
	           loadAccounts(BaseActivity.getpreference(Const.TAG_USERID, getActivity()));
	        } 
	    } 
	}

	public void loadAccounts(String userid) {

		final ProgressDialog pd = new ProgressDialog(getActivity());
		pd.setMessage("Loading");
		pd.setCancelable(false);
		pd.show();

		HashMap<String, String> params = new HashMap<>();
//		params.put("user_action", "1008");
		params.put("user_id", userid);
		params.put("request","useraccounts");
		APIService service = ServiceGenerator.createService(APIService.class,getContext());
		Call<Modeloadaccount> call = service.load_accounts(params);
		call.enqueue(new Callback<Modeloadaccount>() {
			@Override
			public void onResponse(Call<Modeloadaccount> call, Response<Modeloadaccount> response) {
				if (pd.isShowing()) {
					pd.dismiss();
				}
					if(response.isSuccessful())
					{
						if(response.body()!=null&&response.body().getAccount()!=null) {
							List<Account> accounts = response.body().getAccount();
							for (int i = 0; i < accounts.size(); i++){
								String account_id = accounts.get(i).getAccountId();
								String user_id = accounts.get(i).getUserId();
								String account_title = accounts.get(i).getAccountTitle();
								String added_date = accounts.get(i).getAddedDate();
								String initial_amount = accounts.get(i).getInitialAmount();
								String final_balance = accounts.get(i).getFinalBalance();

								Accounts acc = new Accounts();
								acc.setAccount_id(account_id);
								acc.setAccount_title(account_title);
								acc.setUser_id(user_id);
								acc.setInitial_amount(initial_amount);
								acc.setAdded_date(added_date);
								acc.setFinal_balance(final_balance);
								acc.setSelected(false);
								activity.allAccounts.add(acc);
							}
							adapter.notifyDataSetChanged();
							if (activity.allAccounts.size() > 0) {
								noLabel.setVisibility(View.GONE);
							}
							listOfAccounts.setAdapter(adapter);

						}
					}

			}

			@Override
			public void onFailure(Call<Modeloadaccount> call, Throwable t) {
				if (pd.isShowing()) {
					pd.dismiss();
				}
				tools.showToastMessage(activity.getResources().getString(R.string.connectivity));
			}
		});
//		WebService.post(Const.URL+"useraccounts", params, new AsyncHttpResponseHandler() {
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
//				if (pd.isShowing()) {
//					pd.dismiss();
//				}
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
//				if (pd.isShowing()) {
//					pd.dismiss();
//				}
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
//				allAccounts = new ArrayList<Accounts>();
//				try {
//					JSONObject jobject = new JSONObject(string);
//					JSONArray jarray = jobject.getJSONArray("account");
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
//						Accounts acc = new Accounts();
//						acc.setAccount_id(account_id);
//						acc.setAccount_title(account_title);
//						acc.setUser_id(user_id);
//						acc.setInitial_amount(initial_amount);
//						acc.setAdded_date(added_date);
//						acc.setFinal_balance(final_balance);
//						acc.setSelected(false);
//
//
//						activity.allAccounts.add(acc);
//
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
////				activity.allAccounts = allAccounts;
////				adapter = new AdapterAccounts(getActivity(), allAccounts);
//				 adapter.notifyDataSetChanged();
//
//				 if (activity.allAccounts.size() > 0) {
//					noLabel.setVisibility(View.GONE);
//				}
////				listOfAccounts.setAdapter(adapter);
//
//			}
//		});
	}

}
