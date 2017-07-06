package com.mindbees.expenditure;

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
import com.mindbees.expenditure.adapter.AdapterAcntStats;
import com.mindbees.expenditure.model.Category;
import com.mindbees.expenditure.model.Loadaccount.Account;
import com.mindbees.expenditure.model.Loadaccount.Modeloadaccount;
import com.mindbees.expenditure.model.StatusAccount;
import com.mindbees.expenditure.retrofit.APIService;
import com.mindbees.expenditure.retrofit.ServiceGenerator;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;
import com.mindbees.expenditure.util.WebService;
import com.mindbees.expenditure.util.XMLParser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityStatus extends AppCompatActivity{
	
	ListView listaccount_Status;
	ArrayList<StatusAccount> accountStatus;
	ImageView backBtn;
	AdapterAcntStats adapter;
	Tools tools;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);
		
		listaccount_Status = (ListView) findViewById(R.id.account_status);
		backBtn = (ImageView) findViewById(R.id.backBtn);
		
		tools = new Tools(this);
		
		accountStatus = new ArrayList<StatusAccount>();
		adapter = new AdapterAcntStats(this, accountStatus, false);
		listaccount_Status.setAdapter(adapter);
		
		
		if (tools.isConnectingToInternet()) {
			
			loadAccounts(BaseActivity.getpreference(Const.TAG_USERID, this));
			
		}else {
			tools.showToastMessage(getResources().getString(R.string.connectivity));
		}
		
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		listaccount_Status.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String accountid=accountStatus.get(position).getAccount_id();
				Intent intent=new Intent(ActivityStatus.this,ActivityTransactionHistory.class);
				intent.putExtra("Account_id",accountid);
				startActivity(intent);
			}
		});
	}
	
	
private void loadAccounts(String userid){
		
		final ProgressDialog pd = new ProgressDialog(this);
		pd.setMessage("Loading");
		pd.setCancelable(false);
		pd.show();

	HashMap<String, String> params = new HashMap<>();
//		params.put("user_action", "1008");
	params.put("user_id", userid);
	params.put("request","useraccounts");
	APIService service = ServiceGenerator.createService(APIService.class,ActivityStatus.this);
	Call<Modeloadaccount> call = service.load_status(params);
	call.enqueue(new Callback<Modeloadaccount>() {
		@Override
		public void onResponse(Call<Modeloadaccount> call, Response<Modeloadaccount> response) {
			if (pd.isShowing()) {
				pd.dismiss();
			}

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


				}
			}
		}

		@Override
		public void onFailure(Call<Modeloadaccount> call, Throwable t) {
			if (pd.isShowing()) {
				pd.dismiss();
			}

			tools.showToastMessage(getResources().getString(R.string.connectivity));
		}
	});
		
		
//		WebService.post(Const.URL+"useraccounts", params, new AsyncHttpResponseHandler() {
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
//                tools.showToastMessage(getResources().getString(R.string.connectivity));
//			}
//
//
//			private void parse(String string) {
//				// TODO Auto-generated method stub
//
////				allCategory = new ArrayList<Category>();
//				accountStatus.clear();
//		        try {
//		        	JSONObject jobject = new JSONObject(string);
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
//		        }catch(Exception e){
//		        	e.printStackTrace();
//		        }
//		        adapter.notifyDataSetChanged();
//
//			}
//		});
	}

}
