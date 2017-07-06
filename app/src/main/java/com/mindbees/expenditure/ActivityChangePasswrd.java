package com.mindbees.expenditure;

import org.apache.http.Header;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mindbees.expenditure.R.id;
import com.mindbees.expenditure.model.Update_profile.Modelupdate_profile;
import com.mindbees.expenditure.retrofit.APIService;
import com.mindbees.expenditure.retrofit.ServiceGenerator;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;
import com.mindbees.expenditure.util.WebService;
import com.mindbees.expenditure.util.XMLParser;

import android.app.ProgressDialog;
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

public class ActivityChangePasswrd extends BaseActivity implements OnClickListener{
	
	EditText oldPass, newPass, confirmPass;
	RelativeLayout submit;
	Tools tools;
	
	ImageView bckBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_passwrd);
		
		setupUI(findViewById(R.id.parentView));
		overridePendingTransition(R.anim.activity_open_translate_btm,R.anim.activity_close_scale);
		
		tools = new Tools(this);
		
		oldPass = (EditText) findViewById(R.id.editOldPass);
		newPass = (EditText) findViewById(R.id.editNewPass);
		confirmPass = (EditText) findViewById(R.id.editConfirmPassword);
		submit = (RelativeLayout) findViewById(R.id.submit);
		bckBtn = (ImageView) findViewById(R.id.backBtn);
		submit.setOnClickListener(this);
		bckBtn.setOnClickListener(this);
		
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate_btm);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == submit) {
			validateField();
		}else if (v == bckBtn) {
			onBackPressed();
		}
		
	}

	private void validateField() {
		// TODO Auto-generated method stub
		
		String oldPassword = oldPass.getText().toString();
		String newPassword = newPass.getText().toString();
		String confirmPassword = confirmPass.getText().toString();
		
		
		if (oldPassword.isEmpty()) {
			oldPass.setError("Required Old Password");
		}else if (newPassword.isEmpty()) {
			newPass.setError("Required New Password");
		}else if (confirmPassword.isEmpty()) {
			confirmPass.setError("Confirm Password");
		}else if (!(oldPassword.contentEquals(BaseActivity.getpreference(Const.TAG_USERPASS, this)))) {
			oldPass.setError("Try Correct Password");
		}else if (!(newPassword.contentEquals(confirmPassword))) {
			confirmPass.setError("Password Mismatch !");
		}else {
			submitUpdate(newPassword);
		}
		
		
	}

	private void submitUpdate(final String newPassword) {
		// TODO Auto-generated method stub

		final ProgressDialog pd = new ProgressDialog(ActivityChangePasswrd.this);
		pd.setMessage("Updating");
		pd.setCancelable(false);
		pd.show();

		HashMap<String, String> params = new HashMap<>();
//		params.put("user_action", "1005");
		params.put("user_id", BaseActivity.getpreference(Const.TAG_USERID, this));
		params.put("request","updateprofile");
		params.put("currency_id", 1+"");
		params.put("new_password", newPassword);

		APIService service = ServiceGenerator.createService(APIService.class,ActivityChangePasswrd.this);
		Call<Modelupdate_profile> call = service.update_password(params);
		call.enqueue(new Callback<Modelupdate_profile>() {
			@Override
			public void onResponse(Call<Modelupdate_profile> call, Response<Modelupdate_profile> response) {
				if (pd.isShowing()) {
					pd.dismiss();
			}
				if(response.isSuccessful())
				{
					if (response.body()!=null&&response.body().getResult()!=null)
					{
						Modelupdate_profile modelupdate_profile = response.body();
						String msg = modelupdate_profile.getResult().getMessage();
						int value = modelupdate_profile.getResult().getValue();
						if (value == 1) {
							BaseActivity.setpreference(Const.TAG_USERPASS, newPassword, ActivityChangePasswrd.this);

							tools.showToastMessage("Updated");
						}
						else
						{
							tools.showToastMessage("Error in updating");
						}
						tools.showToastMessage(msg);
					}
				}
			}

			@Override
			public void onFailure(Call<Modelupdate_profile> call, Throwable t) {
				if (pd.isShowing()) {
					pd.dismiss();
				}
				tools.showToastMessage(getResources().getString(R.string.connectivity));
			}
		});
//		call.enqueue(new Callback<Modelupdate_profile>() {
//			@Override
//			public void onResponse(Call<Modelupdate_profile> call, Response<Modelupdate_profile> response) {
//				if (pd.isShowing()) {
//					pd.dismiss();
//				}
//				if (response.isSuccessful()) {
//					if (response.body() != null && response.body().getResult() != null) {
//						Modelupdate_profile modelupdate_profile = response.body();
//						String msg = modelupdate_profile.getResult().getMessage();
//						int value = modelupdate_profile.getResult().getValue();
//						if (value == 1) {
//							BaseActivity.setpreference(Const.TAG_USERPASS, newPassword, ActivityChangePasswrd.this);
//
//							tools.showToastMessage("Updated");
//						}
//						else
//						{
//							tools.showToastMessage("Error in updating");
//						}
//						tools.showToastMessage(msg);
//					}
//				}
//			}
//
//			@Override
//			public void onFailure(Call<Modelupdate_profile> call, Throwable t) {
//				if (pd.isShowing()) {
//					pd.dismiss();
//				}
//				tools.showToastMessage(getResources().getString(R.string.connectivity));
//			}
//		});
		
//		WebService.post(Const.URL+"updateprofile", params, new AsyncHttpResponseHandler() {
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
//				int value = 0;
//
//		        try {
//		        	 JSONObject jobject = new JSONObject(string);
//			            JSONObject jsonobject = jobject.getJSONObject("result");
//
//			            msg = jsonobject.getString("message");
//		            	value = jsonobject.getInt("value");
//
//		            if (value == 1) {
//		            	BaseActivity.setpreference(Const.TAG_USERPASS, newPassword, ActivityChangePasswrd.this);
//		            	tools.showToastMessage("Updated");
//					}else {
//						tools.showToastMessage("Error in updating");
//					}
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
