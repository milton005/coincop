package com.mindbees.expenditure;

import org.apache.http.Header;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

//import com.google.android.gms.internal.el;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mindbees.expenditure.model.Contact.Modelcontact;
import com.mindbees.expenditure.model.Update_profile.Modelupdate_profile;
import com.mindbees.expenditure.retrofit.APIService;
import com.mindbees.expenditure.retrofit.ServiceGenerator;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;
import com.mindbees.expenditure.util.WebService;
import com.mindbees.expenditure.util.XMLParser;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityContact extends BaseActivity{
	EditText email;
	EditText name;
//	EditText phone;
	EditText subject;
	EditText message;
	
	RelativeLayout submit;
	Tools tools;
	
	ImageView bckBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		
		setupUI(findViewById(R.id.parentView));
		overridePendingTransition(R.anim.activity_open_translate_btm,R.anim.activity_close_scale);
		
		
		tools = new Tools(this);
		email = (EditText) findViewById(R.id.email);
		name = (EditText) findViewById(R.id.name);
//		phone = (EditText) findViewById(R.id.phoneNumber);
		subject = (EditText) findViewById(R.id.subject);
		message = (EditText) findViewById(R.id.msg);
		bckBtn = (ImageView) findViewById(R.id.backBtn);
		submit = (RelativeLayout) findViewById(R.id.submit);
		
		
		name.setText(getpreference(Const.TAG_FULNAME, this));
		email.setText(getpreference(Const.TAG_USEREMAIL, this));
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (tools.isConnectingToInternet()) {
					validateCheck();
				}else{
					 tools.showToastMessage(getResources().getString(R.string.connectivity));
				}
				
			}
		});
		
		bckBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate_btm);
	}

	protected void validateCheck() {
		// TODO Auto-generated method stub
		
		String mEmail = email.getText().toString();
		String mName = name.getText().toString();
//		String mPhone = phone.getText().toString();
		String mSubject = subject.getText().toString();
		String mMsg = message.getText().toString();
		
		
		/*if (mEmail.isEmpty()) {
			email.setError("Email");
		}else if (!tools.isValidEmail(mEmail)) {
			email.setError("Valid Email");
		}else if (mPhone.isEmpty()) {
			phone.setError("Phone Number");
		}else */
		if (mEmail.isEmpty()) {
			email.setError("Email");
		}else if (!tools.isValidEmail(mEmail)) {
			email.setError("Valid Email");
		}else if (mName.isEmpty()) {
			name.setError("Name");
		}else if (mSubject.isEmpty()) {
			subject.setError("Subject");
		}else if (mMsg.isEmpty()) {
			message.setError("Message");
		}else {
			submitConntact(mEmail, mName, mSubject, mMsg);
		}
		
	}
	
	
	
	private void submitConntact(String email, String name, String subject, String msg) {
		// TODO Auto-generated method stub
		
		final ProgressDialog pdd = new ProgressDialog(this);
		pdd.setMessage("Updating");
		pdd.setCancelable(false);
		pdd.show();

		HashMap<String, String> params = new HashMap<>();
//		params.put("user_action", "1030");
		params.put("name", name);
		params.put("email", email);
		params.put("subject", subject);
		params.put("message", msg);
		params.put("request","contact");
		
		tools.showLog(name, 1);
		tools.showLog(email, 1);
		tools.showLog(subject, 1);
		tools.showLog(msg, 1);

		APIService service = ServiceGenerator.createService(APIService.class,ActivityContact.this);
		Call<Modelcontact> call = service.contactus(params);
		call.enqueue(new Callback<Modelcontact>() {
			@Override
			public void onResponse(Call<Modelcontact> call, Response<Modelcontact> response) {
				if (pdd.isShowing()) {
					pdd.dismiss();
				}
				if (response.isSuccessful())
				{
					if (response.body()!=null);
					Modelcontact modelcontact=response.body();
					String msg=modelcontact.getSuccessMsg();
					tools.showToastMessage(msg);
				}
			}

			@Override
			public void onFailure(Call<Modelcontact> call, Throwable t) {
				if (pdd.isShowing()) {
					pdd.dismiss();
				}
				tools.showToastMessage(getResources().getString(R.string.connectivity));
			}
		});
//		WebService.post(Const.URL+"contact", params, new AsyncHttpResponseHandler() {
//
//
//			@Override
//			public void onStart() {
//				// TODO Auto-generated method stub
//				super.onStart();
//				pdd.show();
//			}
//
//			@Override
//			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//				// TODO Auto-generated method stub
//
//				parse(new String(arg2));
//				tools.showLog(new String(arg2), 3);
//				 if (pdd.isShowing()) {
//	                    pdd.dismiss();
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
//                if (pdd.isShowing()) {
//                    pdd.dismiss();
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
//		    	String msg = "";
//				String value = "";
//
//		        try {
//
//		            JSONObject jobject = new JSONObject(string);
//
//		            msg = jobject.getString("success_msg");
//	            	value = jobject.getString("success");
//
//		            tools.showToastMessage(msg);
//		            /*if (Integer.parseInt(value) == 1) {
//		            	tools.showToastMessage("Updated");
//					}else {
//						tools.showToastMessage("Error in updating");
//					}*/
//
//		        }catch(Exception e){
//		        	e.printStackTrace();
//		        }
//
//			}
//
//
//		});
	}

}
