package com.mindbees.expenditure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.apache.http.Header;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mindbees.expenditure.adapter.AdapterCategory;
import com.mindbees.expenditure.adapter.AdapterCountry;
import com.mindbees.expenditure.model.Category;
import com.mindbees.expenditure.model.Countrys;
import com.mindbees.expenditure.model.register.Modelregister;
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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRegister extends BaseActivity{
	
	ImageView btnBack;
	
	RelativeLayout registerNow;
	EditText eEmail;
	EditText ePassword;
	EditText fullName;
//	Spinner eCountry;

	ArrayList<Countrys> countryList;

	Tools tools;
	AdapterCountry adaptercountry;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

//		setupUI(findViewById(R.id.parentView));
		tools = new Tools(this);
		
		
		overridePendingTransition(R.anim.activity_open_translate_btm,R.anim.activity_close_scale);
		
//		btnBack = (ImageView) findViewById(R.id.imgBack);
		registerNow = (RelativeLayout) findViewById(R.id.registerNow);
		eEmail = (EditText) findViewById(R.id.editEmail);
		eEmail.setFocusable(false);
		eEmail.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				eEmail.setFocusableInTouchMode(true);
				return false;

			}
		});
		ePassword = (EditText) findViewById(R.id.editPassword);
		ePassword.setFocusable(false);
		ePassword.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ePassword.setFocusableInTouchMode(true);
				return false;
			}
		});
		fullName = (EditText) findViewById(R.id.editFullName);
		fullName.setFocusable(false);
		fullName.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				fullName.setFocusableInTouchMode(true);
				return false;
			}
		});
//		eCountry = (Spinner) findViewById(R.id.spinCountry);

		
//		btnBack.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//				onBackPressed();
//
//			}
//		});
		
		registerNow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (tools.isConnectingToInternet()) {
					validateData();
				}else {
					tools.showToastMessage(getResources().getString(R.string.connectivity));
				}
				
			}
		});
		
	/*	if (tools.isConnectingToInternet()) {
			loadCountry();
		}*/
		
		
//		adaptercountry = new AdapterCountry(ActivityRegister.this, countryList);
//		eCountry.setAdapter(adaptercountry);
		
	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate_btm);
	}

	protected void validateData() {
		// TODO Auto-generated method stub
		
		/*View v = eCountry.getSelectedView();
		TextView tv_option = (TextView) v.findViewById(R.id.txt_item);
		String mOptionId = String.valueOf(v.getTag(R.id.txt_item));*/
		
		String email = eEmail.getText().toString().trim();
		String password = ePassword.getText().toString().trim();
		String name = fullName.getText().toString();
//		String country = ePassword.getText().toString().trim();
		
		eEmail.setError(null);
		ePassword.setError(null);
//		tv_option.setError(null);
		if (name.isEmpty()) {
			fullName.setError("Required FullName");
			fullName.requestFocus();
		}else if (email.isEmpty()) {
			eEmail.setError("Required email");
			eEmail.requestFocus();
		}else if (!tools.isValidEmail(email)) {
			eEmail.setError("Required valid email");
		}else if (password.isEmpty()) {
			ePassword.setError("Required password");
			ePassword.requestFocus();
		}else {
			submitLogin(email, password, name);
		}
		
	}

	private void submitLogin(final String email, final String password, final String name) {
		// TODO Auto-generated method stub

		final ProgressDialog pd = new ProgressDialog(this);
		pd.setMessage("Loading");
		pd.setCancelable(false);
		pd.show();

		HashMap<String, String> params = new HashMap<>();
//		params.put("f", "1003");
		params.put("user_email", email);
		params.put("user_password", password);
		params.put("full_name", name);
		params.put("request","register");
		APIService service = ServiceGenerator.createService(APIService.class, ActivityRegister.this);
		Call<Modelregister> call = service.register(params);
		call.enqueue(new Callback<Modelregister>() {
			@Override
			public void onResponse(Call<Modelregister> call, Response<Modelregister> response) {
				if (response.isSuccessful()) {
					if (pd.isShowing()) {
						pd.dismiss();
					}
					Modelregister modelregister = response.body();
					if(modelregister!=null){
						if(modelregister.getResult()!=null)
						{
							String msg=modelregister.getResult().getMessage();
						final String user_id=modelregister.getResult().getUserId();
							showSnackBar(user_id,true);
							int i=modelregister.getResult().getValue();
							if(i==0)
							{
								showSnackBar(msg,false);
							}
							else {
								showSnackBar(msg,true);
								SharedPreferences spns = getSharedPreferences(Const.TAG_SET_PREF, MODE_PRIVATE);
								SharedPreferences.Editor e = spns.edit();
								e.putBoolean(Const.TAG_LOGIN, true);

								e.putString(Const.TAG_USERPASS, password);
								e.putString(Const.TAG_USEREMAIL, email);
								e.putString(Const.TAG_FULNAME, name);
								e.putString(Const.TAG_CURRENCY_ID, tools.getCurrencyCode());
								e.commit();
								Intent bIntent = new Intent();
								bIntent.setAction(Const.KEY_FILTER+".ACTION_RQST_FNSH");
								sendBroadcast(bIntent);
								Intent k = new Intent(getApplicationContext(), MainActivity.class);
								startActivity(k);
								finish();

							}

						}

					}
				}
			}

			@Override
			public void onFailure(Call<Modelregister> call, Throwable t) {
				if (pd.isShowing()) {
					pd.dismiss();
				}
			}
		});
//		WebService.post(Const.URL+"register", params, new AsyncHttpResponseHandler() {
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
//				Tools.debugResponse(new String(arg2));
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
//
//			private void parse(String string) throws NumberFormatException {
//				// TODO Auto-generated method stub
//
//
//				int value = 0;
//		    	String user_id = "";
//		    	String message = "";
//
//		        try {
//
//		        	JSONObject jObject = new JSONObject(string);
//		        	JSONObject jsonobject = jObject.getJSONObject("result");
//
//		        	value = jsonobject.getInt("value");
//		        	user_id = jsonobject.getString("user_id");
//	            	message = jsonobject.getString("message");
//
//		        }catch(Exception e){
//		        	e.printStackTrace();
//		        }
//		        try{
//
//		        	if (value == 1) {
//
//		            	SharedPreferences spns = getSharedPreferences(Const.TAG_SET_PREF, MODE_PRIVATE);
//		                SharedPreferences.Editor e = spns.edit();
//		                e.putBoolean(Const.TAG_LOGIN, true);
//		                e.putString(Const.TAG_USERID, user_id);
//		                e.putString(Const.TAG_USERPASS, password);
//		                e.putString(Const.TAG_USEREMAIL, email);
//		                e.putString(Const.TAG_FULNAME, name);
//		                e.putString(Const.TAG_CURRENCY_ID, tools.getCurrencyCode());
//		                e.commit();
//
//		                tools.showToastMessage(message);
//
//		                Intent bIntent = new Intent();
//		    			bIntent.setAction(Const.KEY_FILTER+".ACTION_RQST_FNSH");
//		    			sendBroadcast(bIntent);
//
//		                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//		        		startActivity(i);
//		        		finish();
//
//		    		}else {
//		    			tools.showToastMessage(message);
//		    		}
//
//
//		        }catch(NumberFormatException e){
//
//		        }
//
//
//			}
//
//
//		});
		
		
	}
	
	
private void loadCountry(){
		
		final ProgressDialog pd = new ProgressDialog(this);
		pd.setMessage("Loading");
		pd.setCancelable(false);
		
		RequestParams params = new RequestParams();
		params.put("user_action", "1022");
		
		
		
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
				
				parsecountry(new String(arg2));
				 if (pd.isShowing()) {
	                    pd.dismiss();
	                }
				
			}
			
			

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// TODO Auto-generated method stub
				
				Tools.debugResponse(new String(arg2));

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Tools.debugThrowable(arg3);

                if (arg2 != null) {
                    Tools.debugResponse(new String(arg2));
                }
                tools.showToastMessage(getResources().getString(R.string.connectivity));
			}
			
			
			private void parsecountry(String string) {
				// TODO Auto-generated method stub
				countryList = new ArrayList<Countrys>();
				
				Countrys coun = new Countrys();
				coun.setId(-1);
				coun.setCountry_name("--select country--");
				countryList.add(coun);
				
				XMLParser parser = new XMLParser();
		        try {
		            String xml = string;

		            Document doc = parser.getDomElement(xml);
		            NodeList nl = doc.getElementsByTagName("country");
		            for (int i = 0; i < nl.getLength(); i++) {
		            	Element e = (Element) nl.item(i);
		            	
		            	String id = parser.getValue(e, "id");
		            	String country_code = parser.getValue(e, "country_code");
		            	String country_name = parser.getValue(e, "country_name");
		            	
		            	Countrys country = new Countrys();
		            	country.setId(Integer.parseInt(id));
		            	country.setCountry_name(country_name);
		            	country.setCountry_code(country_code);
		            	
		            	
		            	countryList.add(country);
		            	
						
					}
		        }catch(Exception e){
		        	e.printStackTrace();
		        }
		        
//		        adaptercountry = new AdapterCountry(ActivityRegister.this, countryList);
//		        eCountry.setAdapter(adaptercountry);
		       
				
			}
		});
	}

}
