package com.mindbees.expenditure;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mindbees.expenditure.adapter.AdapterCategory;
import com.mindbees.expenditure.model.Category;
import com.mindbees.expenditure.model.LOGIN_EMAIL.Modellogin;
import com.mindbees.expenditure.model.LOGIN_EMAIL.Result;
import com.mindbees.expenditure.model.register.Modelregister;
import com.mindbees.expenditure.retrofit.APIError;
import com.mindbees.expenditure.retrofit.APIService;
import com.mindbees.expenditure.retrofit.ErrorUtils;
import com.mindbees.expenditure.retrofit.ServiceGenerator;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;
import com.mindbees.expenditure.util.WebService;
import com.mindbees.expenditure.util.XMLParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLogin extends BaseActivity {
	Modellogin modellogin;
	ImageView imageBack;
	RelativeLayout loginNow;
	EditText eEmail;
	EditText ePassword;

	Tools tools;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setupUI(findViewById(R.id.parentView));
		tools = new Tools(this);

		overridePendingTransition(R.anim.activity_open_translate_btm, R.anim.activity_close_scale);

//		imageBack = (ImageView) findViewById(R.id.imgBack);
		loginNow = (RelativeLayout) findViewById(R.id.loginNow);
		eEmail = (EditText) findViewById(R.id.editEmail);
		ePassword = (EditText) findViewById(R.id.editPassword);


//		imageBack.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//				finish();
//
//			}
//		});

		loginNow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (tools.isConnectingToInternet()) {
					validateData();
				} else {
					tools.showToastMessage(getResources().getString(R.string.connectivity));
				}

			}
		});

	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate_btm);
	}

	protected void validateData() {
		// TODO Auto-generated method stub

		String email = eEmail.getText().toString().trim();
		String password = ePassword.getText().toString().trim();

		eEmail.setError(null);
		ePassword.setError(null);

		if (email.isEmpty()) {
			eEmail.setError("Required email");
		} else if (!tools.isValidEmail(email)) {
			eEmail.setError("Required valid email");
		} else if (password.isEmpty()) {
			ePassword.setError("Required password");
		} else {
			submitLogin(email, password);
		}

	}

	private void submitLogin(String email, String password) {
		// TODO Auto-generated method stub

		final ProgressDialog pd = new ProgressDialog(this);
		pd.setMessage("Loading");
		pd.setCancelable(false);
		pd.show();

		HashMap<String, String> params = new HashMap<>();
//		params.put("user_action", "1001");
		params.put("user_email", email);
		params.put("password", password);
		params.put("request","login");
		APIService service = ServiceGenerator.createService(APIService.class, ActivityLogin.this);
		Call<Modellogin> call = service.login_email(params);
		call.enqueue(new Callback<Modellogin>() {
			@Override
			public void onResponse(Call<Modellogin> call, Response<Modellogin> response) {
				if (pd.isShowing()) {
					pd.dismiss();
				}

				if (response.isSuccessful()) {
					modellogin = response.body();
					if (modellogin != null && modellogin.getResult() != null) {
						showSnackBar("Success",true);
						parse(modellogin);

					}
				} else {
					APIError error = ErrorUtils.parseError(response);
					showSnackBar(error.getResult().get(0).getMessage(), false);

				}
			}

			@Override
			public void onFailure(Call<Modellogin> call, Throwable t) {
				if (pd.isShowing()) {
					pd.dismiss();
				}

			}
		});
//		WebService.post(Const.URL+"login", params, new AsyncHttpResponseHandler() {
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
//				tools.showLog(new String(arg2), 1);
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
//
//
//                if (pd.isShowing()) {
//                    pd.dismiss();
//                }
//
//
//
//                if (arg2 != null) {
//                    Tools.debugResponse(new String(arg2));
//                }
//                Tools.debugThrowable(arg3);
//                tools.showToastMessage(getResources().getString(R.string.connectivity));
//			}
//
//
//		});


	}


	private void parse(Modellogin modellogin) throws NumberFormatException {
		// TODO Auto-generated method stub


		if (modellogin == null) {
			return;
		}
		if (modellogin.getResult() != null) {
			if (modellogin.getResult().get(0) != null) {
				savePreference(modellogin.getResult().get(0));

			}
		}}
//
////		XMLParser parser = new XMLParser();
//        try {
//        	JSONObject jobject = new JSONObject(string);
//        	JSONArray jArray = jobject.getJSONArray("result");
//        	for (int i = 0; i < jArray.length(); i++) {
//
//        		JSONObject jsonObj = jArray.getJSONObject(i);
//
//        		user_status = jsonObj.getString("user_status");
//        		try {
//					if (Integer.parseInt(user_status) == 1) {
//						user_id = jsonObj.getString("user_id");
//						fb_id = jsonObj.getString("fb_id");
//						user_password = jsonObj.getString("user_password");
//						user_email = jsonObj.getString("user_email");
//						country_id = jsonObj.getString("country_id");
//						registered_date = jsonObj.getString("registered_date");
//						full_name = jsonObj.getString("full_name");
//						currency_id = jsonObj.getString("currency_id");
//					}else {
//						msg =jsonObj.getString("message");
//					}
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//
//        	}
//        }catch(Exception e){
//        	e.printStackTrace();
//        }
//
//        if (Integer.parseInt(user_status) == 1) {
//
//        	SharedPreferences spns = getSharedPreferences(Const.TAG_SET_PREF, MODE_PRIVATE);
//            SharedPreferences.Editor e = spns.edit();
//            e.putBoolean(Const.TAG_LOGIN, true);
//            e.putBoolean(Const.TAG_LOGIN_FB, false);
//            e.putString(Const.TAG_USERID, user_id);
//            e.putString(Const.TAG_USERPASS, user_password);
//            e.putString(Const.TAG_USEREMAIL, user_email);
////            e.putString(Const.TAG_CURRENCY_ID, currency_id);
//            e.putString(Const.TAG_CURRENCY_ID, tools.getCurrencyCode());
//            e.putString(Const.TAG_FULNAME, full_name);
//            e.commit();
//
//
//            Intent bIntent = new Intent();
//			bIntent.setAction(Const.KEY_FILTER+".ACTION_RQST_FNSH");
//			sendBroadcast(bIntent);
//
//            Intent i = new Intent(getApplicationContext(), MainActivity.class);
//    		startActivity(i);
//    		finish();
//
//		}else {
//			tools.showToastMessage(msg);
//		}
//
//
//	}

	private void savePreference(Result modellogin) {
		String user_id = "";
		String fb_id = "";
		String user_password = "";
		String user_email = "";
		String country_id = "";
		String registered_date = "";
		String last_login_time = "";
		String photo = "";
		String user_status = "";
		String full_name = "";
		String currency_id = "";
		user_status = modellogin.getUserStatus();
		fb_id = modellogin.getFbId();
		user_password = modellogin.getUserPassword();
		user_email = modellogin.getUserEmail();
		country_id = modellogin.getCountryId();
		registered_date = modellogin.getRegisteredDate();
		user_id = modellogin.getUserId();
		full_name = modellogin.getFullName();
		currency_id = modellogin.getCurrencyId();
		last_login_time = modellogin.getLastLoginTime();
		photo = modellogin.getUserPhoto();
		SharedPreferences spns = getSharedPreferences(Const.TAG_SET_PREF, MODE_PRIVATE);
		SharedPreferences.Editor e = spns.edit();
		e.putBoolean(Const.TAG_LOGIN, true);
		e.putBoolean(Const.TAG_LOGIN_FB, false);
		e.putString(Const.TAG_USERID, user_id);
		e.putString(Const.TAG_USERPASS, user_password);
		e.putString(Const.TAG_USEREMAIL, user_email);
//            e.putString(Const.TAG_CURRENCY_ID, currency_id);
		e.putString(Const.TAG_CURRENCY_ID, tools.getCurrencyCode());
		e.putString(Const.TAG_FULNAME, full_name);
		e.commit();
		Intent bIntent = new Intent();
		bIntent.setAction(Const.KEY_FILTER + ".ACTION_RQST_FNSH");
		sendBroadcast(bIntent);

		Intent i = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(i);
		finish();


	}

}

