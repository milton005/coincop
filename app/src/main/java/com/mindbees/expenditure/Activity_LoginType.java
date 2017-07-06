package com.mindbees.expenditure;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.http.Header;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mindbees.expenditure.model.Fblogin.ModelFblogin;
import com.mindbees.expenditure.model.Fblogin.Result;
import com.mindbees.expenditure.retrofit.APIService;
import com.mindbees.expenditure.retrofit.ServiceGenerator;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;
import com.mindbees.expenditure.util.WebService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_LoginType extends Activity{
	
	RelativeLayout btnLogin;
	RelativeLayout tutorial;
	RelativeLayout btnFb;
	RelativeLayout btnRegister;
	public static Context context;
	BroadcastReceiver breceiver;
	Tools tools;
	// fb
	
	CallbackManager callbackManager;

//    public boolean isFbLogin;
	private static final String NAME = "name";
	private static final String ID = "id";
	private static final String EMAIL = "email";
	private static final String GENDER = "gender";
	private static final String FIELDS = "fields";

//    private static final String PICTURE = "picture.type(large)";


    private static final String REQUEST_FIELDS =
            TextUtils.join(",", new String[]{ID, NAME, EMAIL, GENDER});
    
    
    
    Bitmap mIcon1 = null;
     String imgUrl = "";
     String id = "";
     String name = "";
     String email = "";
     ProgressDialog pd;
    
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_type);
		facebookSDKInitialize();
		context = this;
		tools = new Tools(this);
		
//		 callbackManager = CallbackManager.Factory.create();
		
		btnLogin = (RelativeLayout) findViewById(R.id.loginNow);
		btnFb = (RelativeLayout) findViewById(R.id.connectFacebook);
		btnRegister = (RelativeLayout) findViewById(R.id.registerNow);
		tutorial= (RelativeLayout) findViewById(R.id.tutorial);
		tutorial.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i=new Intent(Activity_LoginType.this,TutorialActivity.class);
				startActivity(i);
			}
		});

		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(Activity_LoginType.this, ActivityLogin.class);
				startActivity(i);
				
			}
		});
		
		btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Activity_LoginType.this, ActivityRegister.class);
				startActivity(i);
			}
		});
		
		btnFb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LoginManager.getInstance().logInWithReadPermissions(Activity_LoginType.this, Arrays.asList("public_profile", "user_friends","email"));

			}
		});
		
		
		IntentFilter intentFilter = new IntentFilter();
	    intentFilter.addAction(Const.KEY_FILTER+".ACTION_RQST_FNSH");
		breceiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				
				finish();
				
			}
		};
		
		LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                tools.showToastMessage("Login success");

                tools.showLog(loginResult.getAccessToken() + "", 1);

//                isFbLogin = true;
                fetchUserInfo();

            }

            @Override
            public void onCancel() {
//                tools.showToastMessage("Login cancel");

            }

            @Override
            public void onError(FacebookException e) {

                tools.showToastMessage("Login error");

            }
        });
		
		
		
		
		
		registerReceiver(breceiver, intentFilter);
		
		
		
	}
	private void facebookSDKInitialize() {
		FacebookSdk.sdkInitialize(getApplicationContext());
		callbackManager = CallbackManager.Factory.create();

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// Logs 'install' and 'app activate' App Events.
		  AppEventsLogger.activateApp(this);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// Logs 'app deactivate' App Event.
		  AppEventsLogger.deactivateApp(this);
	}
	
	
	private void fetchUserInfo() {
//        final String PERMISSION = "publish_actions";
        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken, new GraphRequest.GraphJSONObjectCallback() {

						@Override
						public void onCompleted(JSONObject object,
								GraphResponse response) {
							// TODO Auto-generated method stub
							
							 tools.showLog(object+"", 2);
	                            tools.showLog(response+"",3);

	                            parseUserInfo(object);


//	                            user = me;
//	                            updateUI();
							
						}
                    });
            Bundle parameters = new Bundle();
            parameters.putString(FIELDS, REQUEST_FIELDS);
            request.setParameters(parameters);
            GraphRequest.executeBatchAsync(request);

//            LoginManager.getInstance().logInWithPublishPermissions(
//                    this,
//                    Arrays.asList(PERMISSION));
        } else {

			tools.showLog("access token is null", 2);
//            user = null;
        }
    }

    private void parseUserInfo(JSONObject me) {

        

        try {
            id = me.getString("id");

            name = me.getString("name");
            email = me.getString("email");
            imgUrl = me.getJSONObject("picture").getJSONObject("data").getString("url");
            

        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        tools.showLog(id, 1);
        tools.showLog(name, 1);
        tools.showLog(email, 1);
        tools.showLog(imgUrl, 1);
        
        new Thread(new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {

                    }
                });
                
                URL url = null;
                
        		try {
        			url = new URL(imgUrl);
        			mIcon1 = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        		} catch (MalformedURLException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}catch (IOException e) {
        			// TODO: handle exception
        		}
                
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                    	
                    	submitLogin(email, id, name);
                    	
                    	

                    }
                });


            }
        }).start();
        
        

    }
    
public void bimapTofile(Bitmap bitmap) throws IOException{
		
		//create a file to write bitmap data
		File f =new  File(Environment.getExternalStorageDirectory()
		+ File.separator + Const.TEMP_PHOTO_FILE);
		f.createNewFile();

		//Convert bitmap to byte array
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
		byte[] bitmapdata = bos.toByteArray();

		
//		String encodedimage = encodeImage(bitmapdata);
		//write the bytes in file
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(bitmapdata);
		
		fos.flush();
		fos.close();
		
		UploadImageTask(f);
	}
	

private void UploadImageTask(File f) {
	// TODO Auto-generated method stub
	
	
	/*final ProgressDialog pd = new ProgressDialog(this);
	pd.setCancelable(false);
	pd.setIndeterminate(false);
	pd.setMax(100);
	pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);*/
	
	RequestParams params = new RequestParams();
	params.put("user_id", BaseActivity.getpreference(Const.TAG_USERID, this));
	try {
		params.put("userfile", f);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	AsyncHttpClient client = new AsyncHttpClient();
		client.post(Const.URL_UPLOAD_PHOTO, params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onProgress(int bytesWritten, int totalSize) {
				// TODO Auto-generated method stub
				super.onProgress(bytesWritten, totalSize);
//				int progressPercentage = (int)100*bytesWritten/totalSize;
//				pd.setProgress(progressPercentage);
			}

			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
//				pd.show();
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				Tools.debugThrowable(arg3);

				if (arg2 != null) {
					Tools.debugResponse(new String(arg2));
				}
				if (pd.isShowing()) {
					pd.dismiss();
				}
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				Tools.debugResponse(new String(arg2));

				if (pd.isShowing()) {
					pd.dismiss();
				}
//				submitLogin(email, id, name);
				
				Intent bIntent = new Intent();
				bIntent.setAction(Const.KEY_FILTER+".ACTION_RQST_FNSH");
				sendBroadcast(bIntent);
	            
	            Intent i = new Intent(getApplicationContext(), MainActivity.class);
	    		startActivity(i);
	    		finish();
			}
		});
	
}
    
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	unregisterReceiver(breceiver);
    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
			callbackManager.onActivityResult(requestCode, resultCode, data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    
    
    private void submitLogin(final String email, final String fbid, final String name) {
		// TODO Auto-generated method stub
		
    	pd = new ProgressDialog(this);
		pd.setMessage("Loading");
		pd.setCancelable(false);
		pd.show();

		HashMap<String, String> params = new HashMap<>();
//		params.put("user_action", "1015");
		params.put("user_email", email);
		params.put("fb_id", fbid);
		params.put("request","facebooklogin");
		APIService service = ServiceGenerator.createService(APIService.class,Activity_LoginType.this);
		Call<ModelFblogin> call = service.fb_login(params);
		call.enqueue(new Callback<ModelFblogin>() {
			@Override
			public void onResponse(Call<ModelFblogin> call, Response<ModelFblogin> response) {
				if (pd.isShowing()) {
					pd.dismiss();
				}
				if(response.isSuccessful())
				{
					if(response.body()!=null)
					{String user_id = "";
						String user_password = "";
						String msg = "";
						int value = 0;
						ModelFblogin modelFblogin=response.body();
						value = modelFblogin.getResult().getValue();
						if (value == 1) {
							user_id = modelFblogin.getResult().getUserId();
						}else {
							msg = modelFblogin.getResult().getMessage();
						}
						if (value == 1) {

							SharedPreferences spns = getSharedPreferences(Const.TAG_SET_PREF, MODE_PRIVATE);
							SharedPreferences.Editor e = spns.edit();
							e.putString(Const.TAg_FB_ID,fbid);
							e.putBoolean(Const.TAG_LOGIN, true);
							e.putBoolean(Const.TAG_LOGIN_FB, true);
							e.putString(Const.TAG_USERID, user_id);
							e.putString(Const.TAG_FULNAME, name);
							e.putString(Const.TAG_CURRENCY_ID, tools.getCurrencyCode());
//		            e.putString(Const.TAG_USERPASS, user_password);
							e.putString(Const.TAG_USEREMAIL, email);
//		            e.putString(Const.TAG_COUNTRYID, country_id);
							e.commit();
							Intent bIntent = new Intent();
							bIntent.setAction(Const.KEY_FILTER+".ACTION_RQST_FNSH");
							sendBroadcast(bIntent);

							Intent i = new Intent(getApplicationContext(), MainActivity.class);
							startActivity(i);
							finish();

//							try {
//								bimapTofile(mIcon1);
//							} catch (IOException ea) {
//								// TODO Auto-generated catch block
//								ea.printStackTrace();
//							}



						}else {
							tools.showToastMessage(msg);
						}


					}
				}
			}

			@Override
			public void onFailure(Call<ModelFblogin> call, Throwable t) {
				if (pd.isShowing()) {
					pd.dismiss();
				}
				tools.showToastMessage(getResources().getString(R.string.connectivity));
			}
		});
//		WebService.post(Const.URL+"facebooklogin", params, new AsyncHttpResponseHandler() {
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
////				tools.showLog(new String(arg2), 1);
//				parse(new String(arg2));
//				tools.showLog(new String(arg2), 1);
//				/* if (pd.isShowing()) {
//	                    pd.dismiss();
//	                }*/
//
//			}
//
//
//
//			private void parse(String string) {
//				// TODO Auto-generated method stub
//
//				String user_id = "";
//		    	String user_password = "";
//		    	String msg = "";
//		    	int value = 0;
//
//		        try {
//		            JSONObject jobject = new JSONObject(string);
//		            JSONObject jsonobject = jobject.getJSONObject("result");
//
//		            value = jsonobject.getInt("value");
//		            if (value == 1) {
//	            		user_id = jsonobject.getString("user_id");
//					}else {
//						msg = jsonobject.getString("message");
//					}
//
//		        }catch(Exception e){
//		        	e.printStackTrace();
//		        }
//
//		        if (value == 1) {
//
//		        	SharedPreferences spns = getSharedPreferences(Const.TAG_SET_PREF, MODE_PRIVATE);
//		            SharedPreferences.Editor e = spns.edit();
//		            e.putBoolean(Const.TAG_LOGIN, true);
//		            e.putBoolean(Const.TAG_LOGIN_FB, true);
//		            e.putString(Const.TAG_USERID, user_id);
//		            e.putString(Const.TAG_FULNAME, name);
//		            e.putString(Const.TAG_CURRENCY_ID, tools.getCurrencyCode());
////		            e.putString(Const.TAG_USERPASS, user_password);
//		            e.putString(Const.TAG_USEREMAIL, email);
////		            e.putString(Const.TAG_COUNTRYID, country_id);
//		            e.commit();
//
//
//		            try {
//						bimapTofile(mIcon1);
//					} catch (IOException ea) {
//						// TODO Auto-generated catch block
//						ea.printStackTrace();
//					}
//
//
//
//				}else {
//					tools.showToastMessage(msg);
//				}
//
//			}
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
//		});
//
		
		
		
		
		
	}
	
	
	

}
