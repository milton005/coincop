package com.mindbees.expenditure;

import java.io.IOException;
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
import com.mindbees.expenditure.Interfaces.DeleteItem;
import com.mindbees.expenditure.fragment.FragmentAccounts;
import com.mindbees.expenditure.model.Add_account.Modeladdaccount;
import com.mindbees.expenditure.model.BANKS.Bank;
import com.mindbees.expenditure.model.BANKS.Modelbanks;
import com.mindbees.expenditure.model.Banks;
import com.mindbees.expenditure.model.StatusAccount;
import com.mindbees.expenditure.model.creditdebit.loadcategory.Account;
import com.mindbees.expenditure.model.creditdebit.loadcategory.Modelloadcategory;
import com.mindbees.expenditure.retrofit.APIService;
import com.mindbees.expenditure.retrofit.ServiceGenerator;
import com.mindbees.expenditure.ui.FloatingActionButton;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.ObjectSerializer;
import com.mindbees.expenditure.util.Tools;
import com.mindbees.expenditure.util.WebService;
import com.mindbees.expenditure.util.XMLParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAddAccount extends BaseActivity{
	
	EditText editAccountName;
	EditText editAccountAmount;
	
	ImageView closeBtn, submitBtn;
//	FloatingActionButton ok;
	Tools tools;
	TextView currency;
	DeleteItem listner;
	
	ArrayList<String> allBanks;
	ArrayAdapter<String> adapter;
//	boolean fromTab;
	SharedPreferences spns;
//	Animation anim_rotate;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_account);
		setupUI(findViewById(R.id.parentView));
		tools = new Tools(this);
		
		/*if (getIntent().hasExtra("fromTab")) {
			fromTab = getIntent().getBooleanExtra("fromTab", false);
		}*/
		
//		anim_rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
		
//		listner = (RefreshListner) FragmentAccounts();
		
		editAccountName = (EditText) findViewById(R.id.description);
		editAccountAmount = (EditText) findViewById(R.id.amount);
		currency = (TextView) findViewById(R.id.currency);
		
		closeBtn = (ImageView) findViewById(R.id.closeBtn);
//		ok = (FloatingActionButton) findViewById(R.id.imgOK);
		submitBtn = (ImageView) findViewById(R.id.imgOK);
		
		currency.setText(tools.getCurrency());
		
		
//		initializeTimerTask();
		
//		spns = getSharedPreferences(Const.TAG_SET_PREF, MODE_PRIVATE);
//		try {
//			allBanks = (ArrayList<String>) ObjectSerializer.deserialize(spns.getString("hai", ObjectSerializer.serialize(new ArrayList<String>())));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		tools.showLog(allBanks.size()+"", 1);
//
//		if (allBanks.size() > 0) {
//			adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, allBanks);
//			editAccountName.setThreshold(1);
//			editAccountName.setAdapter(adapter);
////			adapter.notifyDataSetChanged();
//
//		}else {
//
//			allBanks = new ArrayList<String>();
//			adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, allBanks);
//			editAccountName.setThreshold(1);
//			editAccountName.setAdapter(adapter);
//
//			if (tools.isConnectingToInternet()) {
//				loadBanks();
//			}else {
//				tools.showToastMessage(getResources().getString(R.string.connectivity));
//			}
//		}

		/*if (tools.isConnectingToInternet()) {
			loadBanks();
		}else {
			tools.showToastMessage(getResources().getString(R.string.connectivity));
		}*/
		
		
		
		closeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (tools.isConnectingToInternet()) {
					validateAccount();
				}else {
					tools.showToastMessage(getResources().getString(R.string.connectivity));
				}
				
			}
		});
		
		submitBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
	}
	
	/*public void initializeTimerTask() { 
    	
    	
    	final Handler handler = new Handler(); 
    	handler.postDelayed(new Runnable() { 
    	    @Override 
    	    public void run() { 
    	        // Do something after 5s = 5000ms 
    	    	submitBtn.setVisibility(View.VISIBLE);
    	    	submitBtn.startAnimation(anim_rotate);
    	    } 
    	}, 200); 
 }*/
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent returnIntent = new Intent();
		setResult(Activity.RESULT_CANCELED, returnIntent);
		super.onBackPressed();
	}
	
	protected void validateAccount() {
		// TODO Auto-generated method stub
		
		String acntTitle = editAccountName.getText().toString().trim();
		String acntAmnt = editAccountAmount.getText().toString().trim();
		
		editAccountName.setError(null);
		editAccountAmount.setError(null);
		
		if (acntTitle.isEmpty()) {
			editAccountName.setError("Enter Account Name");
		}else if (acntAmnt.isEmpty()) {
			editAccountAmount.setError("Enter Amount");
		}else {
			addAccount(BaseActivity.getpreference(Const.TAG_USERID, this), acntTitle, acntAmnt);
		}
		
	}



	private void addAccount(String userid, String acntTitle, String amount) {

		final ProgressDialog pd = new ProgressDialog(this);
		pd.setMessage("Loading");
		pd.setCancelable(false);
		pd.show();

		HashMap<String, String> params = new HashMap<>();
//		params.put("user_action", "1007");
		params.put("user_id", userid);
		params.put("account_title", acntTitle);
		params.put("initial_amount", amount);
		params.put("request","addaccount");
		APIService service = ServiceGenerator.createService(APIService.class,ActivityAddAccount.this);
		Call<Modeladdaccount> call = service.add_account(params);
		call.enqueue(new Callback<Modeladdaccount>() {
			@Override
			public void onResponse(Call<Modeladdaccount> call, Response<Modeladdaccount> response) {
				if (pd.isShowing()) { pd.dismiss(); }
				if (response.body() != null) {
					if (response.body().getResult() != null) {
						Modeladdaccount modeladdaccount=response.body();
						int value=modeladdaccount.getResult().getValue();
						String message=modeladdaccount.getResult().getMessage();
						if(message!=null)
						{
							tools.showToastMessage(message);
						}
						try {
							if (value == 1) {
//						listner.onRefreshListner();

//						if (fromTab) {
								Intent returnIntent = new Intent();
								setResult(Activity.RESULT_OK, returnIntent);
//						}

								finish();
							}
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			}

			@Override
			public void onFailure(Call<Modeladdaccount> call, Throwable t) {
				if (pd.isShowing()) { pd.dismiss(); }
				tools.showToastMessage(getResources().getString(R.string.connectivity));
			}
		});
//		WebService.post(Const.URL+"addaccount", params, new AsyncHttpResponseHandler() {
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
//				tools.showToastMessage(getResources().getString(R.string.connectivity));
//			}
//
//			private void parse(String string) {
//				// TODO Auto-generated method stub
//
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
//
//				tools.showToastMessage(message);
//				try {
//					if (value == 1) {
////						listner.onRefreshListner();
//
////						if (fromTab) {
//							Intent returnIntent = new Intent();
//							setResult(Activity.RESULT_OK, returnIntent);
////						}
//
//						finish();
//					}
//				} catch (NumberFormatException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			}
//		});
	}
	
	
	
	private void loadBanks() {

		 final ProgressDialog pd = new ProgressDialog(this);
		 pd.setMessage("Loading");
		 pd.setCancelable(false);
		pd.show();

		HashMap<String, String> params = new HashMap<>();
//		RequestParams params = new RequestParams();
//		params.put("user_action", "1023");
		params.put("country_id", "99");
		params.put("keyword", "");
		params.put("request","getbank");
		APIService service = ServiceGenerator.createService(APIService.class,ActivityAddAccount.this);
		Call<Modelbanks> call = service.load_banks(params);
		call.enqueue(new Callback<Modelbanks>() {
			@Override
			public void onResponse(Call<Modelbanks> call, Response<Modelbanks> response) {
				if (pd.isShowing()) { pd.dismiss(); }
				if (response.isSuccessful()) {
					if (response.body() != null) {
						if(response.body().getBank()!=null)
						{
							List<Bank> banks=response.body().getBank();
							for(int i=0;i<banks.size();i++){
								String bank_name=banks.get(i).getBankName();
								allBanks.add(bank_name);
							}
							adapter.notifyDataSetChanged();
							spns = getSharedPreferences(Const.TAG_SET_PREF, MODE_PRIVATE);
							SharedPreferences.Editor e = spns.edit();
							try {
								e.putString("hai", ObjectSerializer.serialize(allBanks));
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							e.commit();

						}

					}
				}
			}

			@Override
			public void onFailure(Call<Modelbanks> call, Throwable t) {
				if (pd.isShowing()) { pd.dismiss(); }
				tools.showToastMessage(getResources().getString(R.string.connectivity));
			}
		});
//		WebService.post(Const.URL+"getbank", params, new AsyncHttpResponseHandler() {
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
//				tools.showLog(new String(arg2), 2);
//
//				if (pd.isShowing()) { pd.dismiss(); }
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
//				// allCategory = new ArrayList<Category>();
//
//				try {
//
//					JSONObject jobject = new JSONObject(string);
//					JSONArray jarray = jobject.getJSONArray("bank");
//					for (int i = 0; i < jarray.length(); i++) {
//						JSONObject jsonobject = jarray.getJSONObject(i);
//
//						String bank_name = jsonobject.getString("bank_name");
//
//						allBanks.add(bank_name);
//					}
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//				adapter.notifyDataSetChanged();
//
//				spns = getSharedPreferences(Const.TAG_SET_PREF, MODE_PRIVATE);
//	            SharedPreferences.Editor e = spns.edit();
//	            try {
//					e.putString("hai", ObjectSerializer.serialize(allBanks));
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//	            e.commit();
//
//
//			}
//		});
	}

}
