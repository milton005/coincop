package com.mindbees.expenditure.fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.facebook.login.LoginManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mindbees.expenditure.ActivityChangePasswrd;
import com.mindbees.expenditure.ActivityContact;
import com.mindbees.expenditure.ActivityLogin;
import com.mindbees.expenditure.Activity_LoginType;
import com.mindbees.expenditure.Activitycurrencyset;
import com.mindbees.expenditure.MainActivity;
import com.mindbees.expenditure.R;
import com.mindbees.expenditure.Interfaces.DeleteItem;
import com.mindbees.expenditure.Interfaces.ImageVisibility;
import com.mindbees.expenditure.Interfaces.UpdateUser;
import com.mindbees.expenditure.adapter.AdapterCurrency;
import com.mindbees.expenditure.app.Expenditure;
import com.mindbees.expenditure.database.CurrencySymbol;
import com.mindbees.expenditure.database.MyDataBase;
import com.mindbees.expenditure.model.Getprofile.Modelgetprofile;
import com.mindbees.expenditure.model.Getprofile.User;
import com.mindbees.expenditure.model.Update_profile.Modelupdate_profile;
import com.mindbees.expenditure.model.creditdebit.loadcategory.Modelloadcategory;
import com.mindbees.expenditure.retrofit.APIService;
import com.mindbees.expenditure.retrofit.ServiceGenerator;
import com.mindbees.expenditure.ui.CircularContactView;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.InternalStorageContentProvider;
import com.mindbees.expenditure.util.Tools;
import com.mindbees.expenditure.util.WebService;
import com.mindbees.expenditure.util.XMLParser;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import eu.janmuller.android.simplecropimage.CropImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class FragmentSettingsTwo extends Fragment implements DeleteItem{
	
//	private final int SELECT_GALLERY_PHOTO = 1;
//	private final int ACTION_TAKE_PHOTO = 2;
//	private final int DO_CROP = 3;
	
	
	public static final int REQUEST_CODE_GALLERY      = 0x1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CODE_CROP_IMAGE   = 0x3;
	public static final String TAG ="fragment_settings_two" ;
	private File mFileTemp;
	
	static EditText tv_user_name;
	static ImageView img_userpic;
	static ImageView img_cover_pic;
	
	ImageVisibility mCallback;
	CircularContactView changePass;
	CircularContactView changeEmail;
	CircularContactView Logout;
//	CircularContactView imgLeftIconSignout;
	CircularContactView imgLeftIconContact;
	
	AlertDialog currencyDialog;
	
	RelativeLayout cPass, cEmail, cCurrency,cContact;
	RelativeLayout loginOut,view;
	
	static Tools tools;
	ImageView imageEdit;
	String user_photo = "";
	RelativeLayout parent;
	EditText userName;
	static EditText userEmail;
	
	static DisplayImageOptions options;
	static MainActivity activity;
//	static String USER_EMAIL;
	TextView textSymbol;
	
	int defaultPosition = -1;
	String symbol = "";
	String curencyId = "";
	 MyDataBase db ;
	
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		try {
			mCallback = (ImageVisibility) activity;
			FragmentSettingsTwo.activity = (MainActivity) activity;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ClassCastException(activity.toString()
                    + " must implement Listener");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v = inflater.inflate(R.layout.fragment_settings_two,container,false);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int screenHeight = displaymetrics.heightPixels;

		int actionBarHeight = 0;
		TypedValue tv = new TypedValue();
		if (getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
			actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());

		}
		int h=screenHeight/4;
		view= (RelativeLayout) v.findViewById(R.id.view);
		view.setMinimumHeight(screenHeight - (actionBarHeight+h));
		parent = (RelativeLayout) v.findViewById(R.id.parentView);
		setupUI(parent);
		mCallback.isVisible(Const.TAG_SETTINGS);
		tools = new Tools(getActivity());
		db = new MyDataBase(getActivity());
		
//		USER_EMAIL = BaseActivity.getpreference(Const.TAG_USEREMAIL, getActivity());
		textSymbol = (TextView) v.findViewById(R.id.textSymbol);
		userEmail = (EditText) v.findViewById(R.id.userEmail);
		tv_user_name = (EditText) v.findViewById(R.id.tv_user_name);
		img_userpic = (ImageView) v.findViewById(R.id.im_user_profile_pic);
		img_cover_pic = (ImageView) v.findViewById(R.id.im_cover_photo);
		changePass = (CircularContactView) v.findViewById(R.id.imgLeftIconPassword);
		changeEmail = (CircularContactView) v.findViewById(R.id.imgLeftIconEmail);
		Logout = (CircularContactView) v.findViewById(R.id.imgLeftIconLogout);
		cPass = (RelativeLayout) v.findViewById(R.id.lauoutPAss);
		cEmail = (RelativeLayout) v.findViewById(R.id.lauoutEmail);
		cCurrency = (RelativeLayout) v.findViewById(R.id.lauoutCurrency);
		cContact = (RelativeLayout) v.findViewById(R.id.lauoutContact);
		loginOut = (RelativeLayout) v.findViewById(R.id.logOut);
		imageEdit = (ImageView) v.findViewById(R.id.imageEdit);
//		imgLeftIconSignout = (CircularContactView) v.findViewById(R.id.imgLeftIconSignout);
		imgLeftIconContact = (CircularContactView) v.findViewById(R.id.imgLeftIconContact);
		
		changePass.setImageResource(R.drawable.change_password, getResources().getColor(R.color.green));
		changeEmail.setImageResource(R.drawable.email, getResources().getColor(R.color.green));
		Logout.setImageResource(R.drawable.image_currency, getResources().getColor(R.color.green));
//		imgLeftIconSignout.setImageResource(R.drawable.ic_action_logout, getResources().getColor(R.color.green));
		imgLeftIconContact.setImageResource(R.drawable.ic_action_new_email, getResources().getColor(R.color.green));
		
		if (!BaseActivity.getpreferenceBoolean(Const.TAG_LOGIN_FB, getActivity())) {
			cPass.setVisibility(View.VISIBLE);
			cEmail.setVisibility(View.VISIBLE);
		}
		curencyId = BaseActivity.getpreference(Const.TAG_CURRENCY_ID, activity);
		symbol = db.getSymbol(curencyId);
		BaseActivity.setpreference(Const.TAG_SYMBOL, symbol, activity);
		
		tools.showLog(symbol, 1);
		tools.showLog(curencyId, 1);
		
		
		setCurrency();
		
		if (tools.isConnectingToInternet()) {
			getProfileDetails();
		}else {
			tools.showToastMessage(activity.getResources().getString(R.string.connectivity));
		}
		
		
		options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher).build();
        Expenditure.imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        
//     currencyChange();
		
		return v;
	}

	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		setupUI(parent);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		loginOut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				logOut();
			}
		});
		cPass.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(), ActivityChangePasswrd.class));
			}
		});
		
		
		imageEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				mFileTemp = Tools.createFile(activity);
				pickPhotoDialog();
			}
		});
		
		cCurrency.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//			currencyDialog.show();
				Intent intent=new Intent(getActivity(), Activitycurrencyset.class);
				startActivityForResult(intent,4);
			}
		});
		
		cContact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(), ActivityContact.class));
			}
		});
		
	}
	
	private void setCurrency(){
		curencyId = BaseActivity.getpreference(Const.TAG_CURRENCY_ID, activity);
		symbol = db.getSymbol(curencyId);
		textSymbol.setText(symbol);
	}
	
	
	
//	private void currencyChange() {
//
//
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View dialoglayout = inflater.inflate(R.layout.currency_change, null);
//
//        final ListView curncyList = (ListView) dialoglayout.findViewById(R.id.gridView_currency);
//
//
//        ArrayList<CurrencySymbol> list = new ArrayList<CurrencySymbol>();
//        list = db.getAllCurrency(curencyId);
//        final AdapterCurrency curncy = new AdapterCurrency(getActivity(), list);
////        curncy.setCallback(this);
//        curncyList.setAdapter(curncy);
//
//        curncyList.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// TODO Auto-generated method stub
//
//				if (defaultPosition != -1) {
//					curncy.allList.get(defaultPosition).setSelected(false);
//				}
//
//				defaultPosition = position;
//				symbol = curncy.allList.get(position).getCrncySymbol();
//				curencyId = curncy.allList.get(position).getCrncyName();
//
//				curncy.allList.get(position).setSelected(true);
//				curncy.notifyDataSetChanged();
//
//			}
//		});
//
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
//        builder.setView(dialoglayout)
//        .setTitle("Currency")
//        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				BaseActivity.setpreference(Const.TAG_SYMBOL, symbol, activity);
//				BaseActivity.setpreference(Const.TAG_CURRENCY_ID, curencyId, activity);
//				setCurrency();
//				currencyDialog.dismiss();
//			}
//		}).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//
//			}
//		});
//        currencyDialog = builder.create();
//    }
//
//
	
	
	
	public void logOut() {
		new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle)
				.setTitle("Coin Cop")
				.setMessage("Do you want to log out ?")
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								
								clearCachedValues();
								
							}

				}).setNegativeButton("No", null).show();
	}

	protected void clearCachedValues() {
		// TODO Auto-generated method stub
         
		if (!BaseActivity.getpreferenceBoolean(Const.TAG_LOGIN_FB, getActivity())) {
			LoginManager.getInstance().logOut();
		}
		
         BaseActivity.setpreferenceBoolean(Const.TAG_LOGIN, false, getActivity());
         BaseActivity.setpreferenceBoolean(Const.TAG_LOGIN_FB, false, getActivity());
		BaseActivity.setpreference(Const.TAg_FB_ID,"",getActivity());
         BaseActivity.setpreference(Const.TAG_USERID, "", getActivity());
         BaseActivity.setpreference(Const.TAG_USERPASS, "", getActivity());
         BaseActivity.setpreference(Const.TAG_USEREMAIL, "", getActivity());
         BaseActivity.setpreference(Const.TAG_CURRENCY_ID, "", getActivity());
         BaseActivity.setpreference(Const.TAG_FULNAME, "", getActivity());
//         BaseActivity.setpreference(Const.TAG_USERFB, "", getActivity());
         BaseActivity.setpreference(Const.TAG_CURRENCY_ID, "", getActivity());
         BaseActivity.setpreference(Const.TAG_SYMBOL, "", getActivity());
         
         Intent intent = new Intent(getActivity(), Activity_LoginType.class);
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);  
         startActivity(intent);
         getActivity().finish();
		
	}
	
	
	
	
	
	
	/*public class LoadBitmapTask extends AsyncTask<String, Integer, Bitmap>{
		@Override
		protected Bitmap doInBackground(String... params) {
			return getBitmapFromURL("");
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			img_cover_pic.setImageBitmap(result);
			AlphaAnimation alpha = new AlphaAnimation(0.3F, 0.3F);
			alpha.setDuration(0); // Make animation instant
			alpha.setFillAfter(true); // Tell it to persist after the animation ends
			img_cover_pic.startAnimation(alpha);
		}
    	
    }
	public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }*/
	
	
	public void setupUI(View view) {
		 
	    //Set up touch listener for non-text box views to hide keyboard. 
		 if(!(view instanceof EditText)) {
			 
		        view.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						Tools.hideSoftKeyboard(getActivity()); 
						
		                return false; 
					} 
		 
		        }); 
		    } 
	 
	    //If a layout container, iterate over children and seed recursion. 
		 if (view instanceof ViewGroup) {
			 
		        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
		 
		            View innerView = ((ViewGroup) view).getChildAt(i);
		 
		            setupUI(innerView);
		        } 
		    } 
	}
	
	private void getProfileDetails() {
		// TODO Auto-generated method stub


		final ProgressDialog pd = new ProgressDialog(getActivity());
		pd.setMessage("Loading");
		pd.setCancelable(false);
		pd.show();


		HashMap<String, String> params = new HashMap<>();
//		params.put("user_action", "1004");
		params.put("user_id", BaseActivity.getpreference(Const.TAG_USERID, getActivity()));
		params.put("request","profile");
		APIService service = ServiceGenerator.createService(APIService.class,getContext());
		Call<Modelgetprofile> call = service.get_profile(params);
		call.enqueue(new Callback<Modelgetprofile>() {
			@Override
			public void onResponse(Call<Modelgetprofile> call, Response<Modelgetprofile> response) {
				if (pd.isShowing()) {
					pd.dismiss();
				}
				if (response.isSuccessful())
				{
					if (response.body()!=null&&response.body().getUser()!=null)
					{
						List<User>users=response.body().getUser();

						String currency_id = "";
						String country_name = "";
						String country_id = "";
						String full_name = "";
						String email = "";
						for(int i=0;i<users.size();i++)
						{
							 user_photo = users.get(i).getUserPhoto();
							 currency_id = users.get(i).getCurrencyId();
							 country_name = users.get(i).getCountryName();
							 country_id = users.get(i).getCountryId();
							 full_name = users.get(i).getFullName();
							 email = users.get(i).getUserEmail();
						}
						try {
							if (BaseActivity.getpreferenceBoolean(Const.TAG_LOGIN_FB, getActivity())) {
								if(user_photo.isEmpty()){
									String fbid=BaseActivity.getpreference(Const.TAg_FB_ID,getActivity());
									user_photo="https://graph.facebook.com/" + fbid + "/picture?type=large";
								}

							}
							setDetails(user_photo, currency_id, email);
						}

						catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}

			@Override
			public void onFailure(Call<Modelgetprofile> call, Throwable t) {
				if (pd.isShowing()) {
					pd.dismiss();
				}
				tools.showToastMessage(getActivity().getResources().getString(R.string.connectivity));
			}
		});
//
//		WebService.post(Const.URL+"profile", params, new AsyncHttpResponseHandler() {
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
//				parseGet(new String(arg2));
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
//                tools.showToastMessage(getActivity().getResources().getString(R.string.connectivity));
//			}
//
//
//
//			private void parseGet(String string) throws NumberFormatException {
//				// TODO Auto-generated method stub
//
//
//		    	String user_photo = "";
//				String currency_id = "";
//				String country_name = "";
//				String country_id = "";
//				String full_name = "";
//				String email = "";
//
//		        try {
//		            JSONObject jobject = new JSONObject(string);
//		            JSONArray jarray = jobject.getJSONArray("user");
//		            for (int i = 0; i < jarray.length(); i++) {
//
//		            	JSONObject jsonobject = jarray.getJSONObject(i);
//
//		            	user_photo = jsonobject.getString("user_photo");
//		            	currency_id = jsonobject.getString("currency_id");
//		            	country_name = jsonobject.getString("country_name");
//		            	country_id = jsonobject.getString("country_id");
//		            	full_name = jsonobject.getString("full_name");
//		            	email = jsonobject.getString("user_email");
//
//					}
//
//		        }catch(Exception e){
//		        	e.printStackTrace();
//		        }
//				try {
//
//					setDetails(user_photo, currency_id, email);
//				}
//
//				catch (Exception e) {
//					e.printStackTrace();
//				}
//
//
//			}
//
//
//		});
//
		
	}
	
	private static void setDetails(String photo, String id, String email2) {

		tv_user_name.setText(BaseActivity.getpreference(Const.TAG_FULNAME,
				activity));

		userEmail.setText(email2);
		loadPhoto(photo);

	}
	
	public static void validate(){
		
		String userName= tv_user_name.getText().toString();
		String email = userEmail.getText().toString();
		
		
		if (userName.isEmpty()) {
			tv_user_name.setError("User Name Required!");
		}else if (email.isEmpty()) {
			userEmail.setError("Email Required !");
		}else if (!tools.isValidEmail(email)) {
			userEmail.setError("Enter Valid Email!");
		}else {
			
			updateDetails(userName, email);
		}
		
	}
	
	
	
	
	public static void updateDetails(final String userName, final String email){

		final ProgressDialog pdd = new ProgressDialog(activity);
		pdd.setMessage("Loading");
		pdd.setCancelable(false);
		pdd.show();

		HashMap<String, String> params = new HashMap<>();
//		params.put("user_action", "1005");
		params.put("user_id", BaseActivity.getpreference(Const.TAG_USERID, activity));
		params.put("user_email", email);
		params.put("full_name", userName);
		params.put("currency_id", 1+"");
		params.put("request","updateprofile");
		APIService service = ServiceGenerator.createService(APIService.class,activity);
		Call<Modelupdate_profile> call = service.update_profile(params);
		call.enqueue(new Callback<Modelupdate_profile>() {
			@Override
			public void onResponse(Call<Modelupdate_profile> call, Response<Modelupdate_profile> response) {
				if (pdd.isShowing()) {
					pdd.dismiss();
				}
				if (response.isSuccessful()) {
					if (response.body() != null && response.body().getResult() != null) {
						Modelupdate_profile modelupdate_profile = response.body();
						String msg = modelupdate_profile.getResult().getMessage();
						int value = modelupdate_profile.getResult().getValue();
						if (value == 1) {
							BaseActivity.setpreference(Const.TAG_FULNAME, userName, activity);
							BaseActivity.setpreference(Const.TAG_USEREMAIL, email, activity);

							setDetails(userName, "", email);
						}
						tools.showToastMessage(msg);
					}
				}
			}

			@Override
			public void onFailure(Call<Modelupdate_profile> call, Throwable t) {
				if (pdd.isShowing()) {
					pdd.dismiss();
				}
				tools.showToastMessage(activity.getResources().getString(R.string.connectivity));
			}
		});
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
//				)
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
//                tools.showToastMessage(activity.getResources().getString(R.string.connectivity));
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
//		            JSONObject jobject = new JSONObject(string);
//		            JSONObject jsonobject = jobject.getJSONObject("result");
//
//		            msg = jsonobject.getString("message");
//	            	value = jsonobject.getInt("value");
//
//
//		            if (value == 1) {
//		            	BaseActivity.setpreference(Const.TAG_FULNAME, userName, activity);
//		            	BaseActivity.setpreference(Const.TAG_USEREMAIL, email, activity);
//
//		            	setDetails(userName, "", email);
//					}
//		            tools.showToastMessage(msg);
//
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
	
	
	/****************upload image section ******************************/

	static void loadPhoto(String uri) {

		Expenditure.imageLoader.loadImage(uri, options,
				new SimpleImageLoadingListener() {

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						// TODO Auto-generated method stub
						super.onLoadingComplete(imageUri, view, loadedImage);

						if (loadedImage != null) {

							img_userpic.setImageBitmap(loadedImage);

							img_cover_pic.setImageBitmap(loadedImage);
							AlphaAnimation alpha = new AlphaAnimation(0.3F,
									0.3F);
							alpha.setDuration(0); // Make animation instant
							alpha.setFillAfter(true); // Tell it to persist
														// after the animation
														// ends
							img_cover_pic.startAnimation(alpha);

						}
					}

				});
	}
	
	void pickPhotoDialog() {
		String[] item = { "Take Picture", "Upload From Library" };
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);

		builder.setTitle("UPLOAD IMAGE").setItems(item,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						switch (which) {
						case 0:
							takePicture();
							break;
						case 1:

							openGallery();
							

							break;
						default:
							break;
						}
					}


				}).setNegativeButton("CANCEL",null);
		builder.show();
	}
	
	
	private void takePicture() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
        	Uri mImageCaptureUri = null;
        	String state = Environment.getExternalStorageState();
        	if (Environment.MEDIA_MOUNTED.equals(state)) {
        		mImageCaptureUri = Uri.fromFile(mFileTemp);
        	}
        	else {
	        	mImageCaptureUri = InternalStorageContentProvider.CONTENT_URI;
        	}	
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        } catch (ActivityNotFoundException e) {

//            Log.d(TAG, "cannot take picture", e);
        }
    }
	
	private void openGallery() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY);
    }
	
	
	private void startCropImage() {

        Intent intent = new Intent(getActivity(), CropImage.class);
        intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
        intent.putExtra(CropImage.SCALE, true);

        intent.putExtra(CropImage.ASPECT_X, 3);
        intent.putExtra(CropImage.ASPECT_Y, 3);

        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {

		case REQUEST_CODE_GALLERY:
			if (resultCode == Activity.RESULT_OK) {
				try {

                    InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                    FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
                    copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();

                    startCropImage();

                } catch (Exception e) {

                }
			}
			break;
		case REQUEST_CODE_TAKE_PICTURE:

			if (resultCode == Activity.RESULT_OK) {
				startCropImage();
			}
			break;
			// ACTION_TAKE_PHOTO_S

		case REQUEST_CODE_CROP_IMAGE :

			if (resultCode == Activity.RESULT_OK) {

				String path = data.getStringExtra(CropImage.IMAGE_PATH);
                if (path == null) {

                    return;
                }

               final Bitmap photo = BitmapFactory.decodeFile(mFileTemp.getPath());
				try {
					bimapTofile(photo);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				img_userpic.post(new Runnable() {

					@Override
					public void run() {
							try {

								img_userpic.setImageBitmap(photo);
								
								img_cover_pic.setImageBitmap(photo);
								AlphaAnimation alpha = new AlphaAnimation(0.3F, 0.3F);
								alpha.setDuration(0); // Make animation instant
								alpha.setFillAfter(true); // Tell it to persist after the animation ends
								img_cover_pic.startAnimation(alpha);

							} catch (Exception e) {
								e.printStackTrace();
							}
					}
				});
			}
			case 4:if(resultCode==Activity.RESULT_OK)
			{
			setCurrency();
			}
		}
	}
	
	
	public static void copyStream(InputStream input, OutputStream output)
            throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
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
	
	
	final ProgressDialog pd = new ProgressDialog(getActivity());
	pd.setCancelable(false);
	pd.setIndeterminate(false);
	pd.setMax(100);
	pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	
	RequestParams params = new RequestParams();
	params.put("user_id", BaseActivity.getpreference(Const.TAG_USERID, getActivity()));
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
				int progressPercentage = (int)100*bytesWritten/totalSize;
				pd.setProgress(progressPercentage);
			}

			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				pd.show();
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
				 loadPhoto(new String(arg2));
			}
		});
		
	
	
	
}

@Override
public void onDeleteItem(int position) {
	// TODO Auto-generated method stub
	defaultPosition = position;
}

}
