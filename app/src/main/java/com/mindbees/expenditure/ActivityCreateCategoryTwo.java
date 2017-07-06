package com.mindbees.expenditure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.Header;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mindbees.expenditure.adapter.AdapterCurrency;
import com.mindbees.expenditure.database.CurrencySymbol;
import com.mindbees.expenditure.database.MyDataBase;
import com.mindbees.expenditure.fragment.Popup_add_category;
import com.mindbees.expenditure.fragment.Popupeditcategory;
import com.mindbees.expenditure.model.Addcategory.Modeladdcategory;
import com.mindbees.expenditure.model.Homefragment.Modelallreminder;
import com.mindbees.expenditure.retrofit.APIService;
import com.mindbees.expenditure.retrofit.ServiceGenerator;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;
import com.mindbees.expenditure.util.WebService;
import com.mindbees.expenditure.util.XMLParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCreateCategoryTwo extends BaseActivity{
	FragmentManager mFragmentManager;
	FragmentTransaction mFragmentTransaction;
	ImageView imageIncome, imageExpense, closeBtn;
	TextView textIncome, textExpense;
	
	
	Animation anim_iconIncome;
	Animation anim_iconExpense;
	
	Timer timer;
    TimerTask timerTask;
  //we are going to use a handler to be able to run in our TimerTask 
    final Handler handler = new Handler();
    
    AlertDialog categoryDialog;
    boolean isIncome = false;
    Tools tools;
    boolean wantToCloseDialog = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_type_cat);
//		setupUI(findViewById(R.id.parentView));
		
		tools = new Tools(this);
		imageIncome = (ImageView) findViewById(R.id.imageIncome);
		imageExpense = (ImageView) findViewById(R.id.imageExpense);
		textIncome = (TextView) findViewById(R.id.textIncome);
		textExpense = (TextView) findViewById(R.id.textexpense);
		
		closeBtn = (ImageView) findViewById(R.id.closeBtn);
		
		anim_iconIncome = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.type_icon_zoom);
		anim_iconExpense = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.type_icon_zoom);
		
		
		initializeTimerTask(); 		
		
		imageIncome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*Intent i = new Intent(ActivityCreateCategoryNew.this, ActivityCredit_Debit.class);
				i.putExtra("expense", false);
				startActivity(i);*/
				isIncome = true;
//				categoryDialog.show();
				mFragmentManager = getSupportFragmentManager();
				mFragmentTransaction = mFragmentManager.beginTransaction();
				Bundle args = new Bundle();
				args.putInt("FRAG_TYPE",1);
				Popup_add_category p= Popup_add_category.NewInstance();
				p.setArguments(args);
				p.show(mFragmentTransaction,"addtitle");
//				categoryName();
				
			}
		});
		
		imageExpense.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*Intent i = new Intent(ActivityCreateCategoryNew.this, ActivityCredit_Debit.class);
				i.putExtra("expense", true);
				startActivity(i);*/
				isIncome = false;
//				categoryDialog.show();
				mFragmentManager = getSupportFragmentManager();
				mFragmentTransaction = mFragmentManager.beginTransaction();
				Bundle args = new Bundle();
				args.putInt("FRAG_TYPE",2);
				Popup_add_category p= Popup_add_category.NewInstance();
				p.setArguments(args);
				p.show(mFragmentTransaction,"addtitle");
			}
		});
		
		
		closeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
		
		anim_iconIncome.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				imageExpense.setVisibility(View.VISIBLE);
				textIncome.setTextColor(getResources().getColor(R.color.white));
				imageExpense.startAnimation(anim_iconExpense);
				
			}
		});
		anim_iconExpense.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				textExpense.setTextColor(getResources().getColor(R.color.white));
				
			}
		});
		
//		categoryName();
		
	}

	 public void initializeTimerTask() { 
	    	
	    	
	    	final Handler handler = new Handler(); 
	    	handler.postDelayed(new Runnable() { 
	    	    @Override 
	    	    public void run() { 
	    	        // Do something after 5s = 5000ms 
	    	    	imageIncome.setVisibility(View.VISIBLE);
	    	    	imageIncome.startAnimation(anim_iconIncome);
	    	    } 
	    	}, 400); 
	 }
	 
	 
	 private void categoryName() {
		 
			
	        View dialoglayout = LayoutInflater.from(this).inflate(R.layout.inflate_create_cat_two, null);
	        
	        final EditText titleText = (EditText) dialoglayout.findViewById(R.id.editText_category_title);
	        
	        

	        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
	        builder.setView(dialoglayout)
	        .setTitle("Add Category Title")
	        .setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
//					String title = titleText.getText().toString();
					
					if (tools.isConnectingToInternet()) {
						validateCheck(titleText);
					}else {
						tools.showToastMessage(getResources().getString(R.string.connectivity));
					}
					
					
				}

							
			}).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					hideSoftKeyboard(titleText);
				}
			});
	        
	        categoryDialog = builder.create();
	        categoryDialog.show();
	        categoryDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					
					if (tools.isConnectingToInternet()) {
						validateCheck(titleText);
					}else {
						tools.showToastMessage(getResources().getString(R.string.connectivity));
					}
					
					if(wantToCloseDialog){ 
						wantToCloseDialog = false;
						 categoryDialog.dismiss();
					 }
				}
				 
	                 
				
			});
		
	   
	    }
	 
	 
	 private void validateCheck(EditText titleText) {
			// TODO Auto-generated method stub
		 String title = titleText.getText().toString();
			if (title.isEmpty()) {
				titleText.setError("Enter CateoryTitle");
			} else {
				wantToCloseDialog = true;
				if (isIncome) {
					insertCategory(title,
							Const.TAG_INCOME_ID);
				} else {
					insertCategory(title,
							Const.TAG_EXPENSE_ID);
				}
				hideSoftKeyboard(titleText);
				categoryDialog.dismiss();
			}

		}
	 
	 public void hideSoftKeyboard(EditText edit) {
	        try {
				InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(edit.getWindowToken(), 0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    } 
	 
	 
	 private void insertCategory(String catTitle, final String typeid) {
			// TODO Auto-generated method stub
			
			
			final ProgressDialog pd = new ProgressDialog(this);
			pd.setMessage("Loading");
			pd.setCancelable(false);
		 	pd.show();
		    HashMap<String, String> params = new HashMap<>();
//			params.put("user_action", "1009");
			params.put("user_id", BaseActivity.getpreference(Const.TAG_USERID, this));
			params.put("category_title", catTitle);
			params.put("cat_image", "category_new_icon.png");
			params.put("cat_color", "1");
			params.put("type_id", typeid);
		 	params.put("request","addcategory");
		    APIService service = ServiceGenerator.createService(APIService.class, ActivityCreateCategoryTwo.this);
		    Call<Modeladdcategory> call = service.add_category(params);
		 	call.enqueue(new Callback<Modeladdcategory>() {
				@Override
				public void onResponse(Call<Modeladdcategory> call, Response<Modeladdcategory> response) {
					if (pd.isShowing()) {
						pd.dismiss();
						if(response.isSuccessful())
						{
							if (response.body()!=null&&response.body().getResult()!=null)
							{
								Modeladdcategory modeladdcategory=response.body();
								int value = 0;
								String message = "";
								value=modeladdcategory.getResult().getValue();
								message=modeladdcategory.getResult().getMessage();
								tools.showToastMessage(message);
								try {
									if (value == 1) {
										Intent bIntent = new Intent();
										if (isIncome) {
											bIntent.setAction(Const.KEY_FILTER+".ACTION_RQST_REFRESH_INC");
										}else {
											bIntent.setAction(Const.KEY_FILTER+".ACTION_RQST_REFRESH_EXP");
										}

										sendBroadcast(bIntent);
										Intent intent=new Intent(ActivityCreateCategoryTwo.this,MainActivity.class);
										intent.putExtra(Const.FRAGTYPE,1);
										intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
												| Intent.FLAG_ACTIVITY_NO_ANIMATION);
										overridePendingTransition(0, 0);
										finish();

										overridePendingTransition(0, 0);
										int fragtype=Integer.parseInt(typeid);
										intent.putExtra(Const.FRAGMENT_REMINDER_TYPE,fragtype-1);
										startActivity(intent);
//										finish();
									}
								} catch (NumberFormatException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						}
					}
				}

				@Override
				public void onFailure(Call<Modeladdcategory> call, Throwable t) {
					if (pd.isShowing()) {
						pd.dismiss();
						Tools.debugResponse(new String(String.valueOf(t)));
						tools.showToastMessage(getResources().getString(R.string.connectivity));
					}
				}
			});

//			WebService.post(Const.URL+"addcategory", params, new AsyncHttpResponseHandler() {
//
//				@Override
//				public void onStart() {
//					// TODO Auto-generated method stub
//					super.onStart();
//					pd.show();
//				}
//
//				@Override
//				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//					// TODO Auto-generated method stub
//
//					parse(new String(arg2));
//					tools.showLog(new String(arg2), 2);
//					if (pd.isShowing()) {
//						pd.dismiss();
//					}
//
//				}
//
//				@Override
//				public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//						Throwable arg3) {
//					// TODO Auto-generated method stub
//
//					try {
//						Tools.debugResponse(new String(arg2));
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//					if (pd.isShowing()) {
//						pd.dismiss();
//					}
//
//					Tools.debugThrowable(arg3);
//
//					if (arg2 != null) {
//						Tools.debugResponse(new String(arg2));
//					}
//					tools.showToastMessage(getResources().getString(R.string.connectivity));
//				}
//
//				private void parse(String string) {
//					// TODO Auto-generated method stub
//
//					int value = 0;
//					String message = "";
//
//					try {
//						JSONObject jobject = new JSONObject(string);
//						JSONObject jsonobject = jobject.getJSONObject("result");
//
//
//						value = jsonobject.getInt("value");
//						message = jsonobject.getString("message");
//
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//
//					tools.showToastMessage(message);
//					try {
//						if (value == 1) {
//							Intent bIntent = new Intent();
//							if (isIncome) {
//								bIntent.setAction(Const.KEY_FILTER+".ACTION_RQST_REFRESH_INC");
//							}else {
//								bIntent.setAction(Const.KEY_FILTER+".ACTION_RQST_REFRESH_EXP");
//							}
//
//			    			sendBroadcast(bIntent);
//							finish();
//						}
//					} catch (NumberFormatException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//				}
//			});
			
			
			
		}
	 
	
}
