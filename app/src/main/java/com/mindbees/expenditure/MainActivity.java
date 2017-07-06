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
import com.mindbees.expenditure.Interfaces.ImageVisibility;
import com.mindbees.expenditure.Interfaces.UpdateUser;
import com.mindbees.expenditure.fragment.FragmentAccounts;
import com.mindbees.expenditure.fragment.FragmentAllreminders;
import com.mindbees.expenditure.fragment.FragmentCategory;
import com.mindbees.expenditure.fragment.FragmentSettingsTwo;
import com.mindbees.expenditure.fragment.HomeFragment;
import com.mindbees.expenditure.fragment.fragmentReportTwo;
import com.mindbees.expenditure.model.LOGIN_EMAIL.Modellogin;
import com.mindbees.expenditure.model.LOGIN_EMAIL.Result;
import com.mindbees.expenditure.retrofit.APIError;
import com.mindbees.expenditure.retrofit.APIService;
import com.mindbees.expenditure.retrofit.ErrorUtils;
import com.mindbees.expenditure.retrofit.ServiceGenerator;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;
import com.mindbees.expenditure.util.WebService;
import com.mindbees.expenditure.util.XMLParser;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements OnClickListener, ImageVisibility{
	Modellogin modellogin;
	private static long back_pressed;
		private ResideMenu resideMenu;
    private MainActivity mContext;
//    private ResideMenuItem item;
    private ResideMenuItem Home;
   private ResideMenuItem Accounts;
    private ResideMenuItem Category;
	private ResideMenuItem Reports;
	private ResideMenuItem Remainder;
	private ResideMenuItem Settings;
    ImageView titleBarRightMenu;
	FragmentManager mFragmentManager;
	FragmentTransaction mFragmentTransaction;


    Tools tools;
    
    public int listPosition = -1;
    
    int position=0;
	private int whichView = 0;
	TextView tvTitle;
	ImageView imageLogo;
	UpdateUser user;
	private DrawerLayout drawerLayout;
	FragmentManager fragmentmanager;
	private int fragment_reminder_type;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(null);
		setContentView(R.layout.activity_main);
		fragmentmanager = getSupportFragmentManager();
		mContext = this;
		tools = new Tools(this);
        setupMenu();
        
        titleBarRightMenu = (ImageView) findViewById(R.id.title_bar_right_menu);
        tvTitle = (TextView) findViewById(R.id.textMainTitle);
        imageLogo = (ImageView) findViewById(R.id.appicon);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

		gotToFragmentsWithoutBackStack(new HomeFragment());
		//Setting the actionbarToggle to drawer layout
		try
		{
			if(getIntent().hasExtra(Const.FRAGTYPE)){
				position=getIntent().getIntExtra(Const.FRAGTYPE,0);
				fragment_reminder_type=getIntent().getIntExtra(Const.FRAGMENT_REMINDER_TYPE,0);
				selectfragment();

			}
		}catch (Exception e)
		{

		}



//		if( savedInstanceState == null )
	if(position==0) {
			gotToFragmentsWithoutBackStack(new HomeFragment());
		}
        
        if (!getpreferenceBoolean(Const.TAG_DB, this)) {
			addCurrency();
			setpreferenceBoolean(Const.TAG_DB, true, this);
		}
        
        
        titleBarRightMenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (whichView == Const.TAG_SETTINGS) {
					
					FragmentSettingsTwo.validate();
				}
				
			}
		});
		
	}

	private void selectfragment() {
     FragmentCategory category=new FragmentCategory();
		gotToFragments2(category,FragmentCategory.TAG);
	}

	private void setFragmentView2(Fragment fragment) {
		resideMenu.clearIgnoredViewList();
		mFragmentTransaction=mFragmentManager.beginTransaction();
		Bundle data=new Bundle();

		data.putInt(Const.FRAGMENT_REMINDER_TYPE,fragment_reminder_type);
		fragment.setArguments(data);
		mFragmentTransaction.replace(R.id.frame,fragment);
		mFragmentTransaction.commit();

	}
	private void gotToFragments(Fragment fragment, String tag) {
		boolean fragmentPopped = fragmentmanager.popBackStackImmediate(tag, 0);
//		showLog(!fragmentPopped+"");
		if (fragment != null && !fragmentPopped) {
			fragmentmanager.beginTransaction().replace(R.id.frame, fragment)
					.addToBackStack(tag)
					.commit();
		}
	}
	private void gotToFragments2(Fragment fragment, String tag) {
		boolean fragmentPopped = fragmentmanager.popBackStackImmediate(tag, 0);
//		showLog(!fragmentPopped+"");
		if (fragment != null && !fragmentPopped) {
			Bundle data=new Bundle();

			data.putInt(Const.FRAGMENT_REMINDER_TYPE,fragment_reminder_type);
			fragment.setArguments(data);
			fragmentmanager.beginTransaction().replace(R.id.frame, fragment)
					.addToBackStack(tag)
					.commit();
		}
	}
	private void gotToFragmentsWithoutBackStack(Fragment fragment) {

		if (fragment != null) {
			fragmentmanager.beginTransaction().replace(R.id.frame, fragment)
					.commit();
		}
	}
	private void
	showRatePopup(){
		AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle).create();
		 
	    alertDialog.setTitle("Coin Cop"); 
	 
	    alertDialog.setMessage("Rate Coin Cop"); 
	 
	    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() { 
	 
	      public void onClick(DialogInterface dialog, int id) {
	 
	        setpreferenceBoolean(Const.TAG_RATE_NEVR, true, MainActivity.this);
	 
	    } });  
	 
	    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Later", new DialogInterface.OnClickListener() {
	 
	      public void onClick(DialogInterface dialog, int id) {
	 
	    	 setpreferenceInt(Const.TAG_RATE, 0, MainActivity.this);
	 
	    }});  
	 
	    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Never", new DialogInterface.OnClickListener() {
	 
	      public void onClick(DialogInterface dialog, int id) {
	 
	    	  setpreferenceBoolean(Const.TAG_RATE_NEVR, true, MainActivity.this);
	 
	    }}); 
	    
	    alertDialog.show();
	}
	
	void setupMenu(){
		resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.side_menu_bg);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
		resideMenu.setScaleValue(0.5f);

//        String titles[] = { "Home", "Accounts", "Category", "Reports", "Reminder", "Settings" };
//        int icon[] = { R.drawable.side_menu_home, R.drawable.side_menu_acounts, R.drawable.side_menu_category, R.drawable.side_menu_report, R.drawable.side_menu_reminder, R.drawable.side_menu_my_profile };
		Home = new ResideMenuItem(this,R.drawable.side_menu_home,"Home");
		Accounts=new ResideMenuItem(this,R.drawable.side_menu_acounts,"Accounts");
		Category=new ResideMenuItem(this,R.drawable.side_menu_category,"Category");
		Reports=new ResideMenuItem(this,R.drawable.side_menu_report,"Reports");
		Remainder=new ResideMenuItem(this,R.drawable.side_menu_help,"Help");
		Settings=new ResideMenuItem(this,R.drawable.side_menu_my_profile,"Settings");

		Home.setOnClickListener(this);
		Accounts.setOnClickListener(this);
		Category.setOnClickListener(this);
		Reports.setOnClickListener(this);
		Remainder.setOnClickListener(this);
		Settings.setOnClickListener(this);

		resideMenu.addMenuItem(Home, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(Accounts, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(Category, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(Reports, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(Remainder,ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(Settings,ResideMenu.DIRECTION_LEFT);
//
//        for (int i = 0; i < titles.length; i++){
//        	ResideMenuItem item;
////        	if (i == (titles.length-1)) {
////        		 item = new ResideMenuItem(this,icon[i], titles[i], false);
////			}else {
////				 item = new ResideMenuItem(this, icon[i], titles[i], true);
////			}
//			item = new ResideMenuItem(this, icon[i], titles[i]);
//            item.setTag(titles[i]);
//            item.setOnClickListener(this);
//            resideMenu.addMenuItem(item,  ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
//        }

        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
//				setFragmentView(new HomeFragment());
            }
        });
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		if (!BaseActivity.getpreferenceBoolean(Const.TAG_LOGIN_FB, this)) {
			submitLogin(BaseActivity.getpreference(Const.TAG_USEREMAIL, this), BaseActivity.getpreference(Const.TAG_USERPASS, this));

		}
	}
	
	
	 private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
	        @Override
	        public void openMenu() {
//	           / Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
	        }

	        @Override
	        public void closeMenu() {
//	            Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
	        }
	    };
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return resideMenu.dispatchTouchEvent(ev);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==Home)
		{  gotToFragments(new HomeFragment(), HomeFragment.TAG);
//			getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}
		if(v==Accounts)
		{
			gotToFragments(new FragmentAccounts(), FragmentAccounts.TAG);
		}
		if (v==Category)
		{
			gotToFragments(new FragmentCategory(), FragmentCategory.TAG);
		}
		if (v==Reports)
		{gotToFragments(new fragmentReportTwo(), fragmentReportTwo
				.TAG);
		}
		if (v==Remainder)
		{
			Intent intent=new Intent(MainActivity.this,TutorialActivity.class);
			startActivity(intent);
//			gotToFragments(new FragmentAllreminders(), FragmentAllreminders.TAG);
		}
		if (v==Settings)
		{gotToFragments(new FragmentSettingsTwo(), FragmentSettingsTwo.TAG);
		}
//		String value = (String) v.getTag();
//
//		if (value.contentEquals("H"+"ome")) {
//			changeFragment(new HomeFragment());
//		}else if (value.contentEquals("A"+"ccounts")) {
//			changeFragment(new FragmentAccounts());
//		}else if (value.contentEquals("C"+"ategory")) {
//			changeFragment(new FragmentCategory());
//		}else if (value.contentEquals("R"+"eports")) {
//			changeFragment(new fragmentReportTwo());
//		}else if (value.contentEquals("R" +
//				"eminder")) {
//			changeFragment(new FragmentAllreminders());
//		}else if (value.contentEquals("S"+"ettings")) {
//			changeFragment(new FragmentSettingsTwo());
//		}
//
		resideMenu.closeMenu();
	
		
	}
	public void setFragmentView(Fragment fragment) {
		resideMenu.clearIgnoredViewList();
		FragmentManager fm=this.getSupportFragmentManager();
		fm.beginTransaction()
				.add(R.id.frame, fragment)
				.commit();
	}
	private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, targetFragment, "fragment")
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

	private void submitLogin(final String email, final String password) {
		// TODO Auto-generated method stub
		
/*		final ProgressDialog pd = new ProgressDialog(this);
		pd.setMessage("Loading");
		pd.setCancelable(false);
*/
		HashMap<String, String> params = new HashMap<>();
//		params.put("user_action", "1001");
		params.put("user_email", email);
		params.put("password", password);
		params.put("request", "login");
		APIService service = ServiceGenerator.createService(APIService.class, MainActivity.this);
		Call<Modellogin> call = service.login_email(params);
		call.enqueue(new Callback<Modellogin>() {
			@Override
			public void onResponse(Call<Modellogin> call, Response<Modellogin> response) {
				if (response.isSuccessful()) {
					modellogin = response.body();
					if (modellogin != null && modellogin.getResult() != null) {
						parse(modellogin);

					}
				} else {
					APIError error = ErrorUtils.parseError(response);
					showSnackBar(error.getResult().get(0).getMessage(), false);

				}

			}

			@Override
			public void onFailure(Call<Modellogin> call, Throwable t) {

			}
		});
	}
//		WebService.post(Const.URL+"login", params, new AsyncHttpResponseHandler() {
//
//
//			@Override
//			public void onStart() {
//				// TODO Auto-generated method stub
//				super.onStart();
////				pd.show();
//			}
//
//			@Override
//			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//				// TODO Auto-generated method stub
//
//				parse(new String(arg2));
////				tools.showLog(new String(arg2), 3);
//				 /*if (pd.isShowing()) {
//	                    pd.dismiss();
//	                }*/
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
//                /*if (pd.isShowing()) {
//                    pd.dismiss();
//                }*/
//
//                Tools.debugThrowable(arg3);
//
//                if (arg2 != null) {
//                    Tools.debugResponse(new String(arg2));
//                }
//			}
//
			
			
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

	private void savePreference(Result result) {
		String value = "";
		String user_id = "";
		String message = "";
		value=result.getUserStatus();


//
//	String value = "";
//		    	String user_id = "";
//		    	String message = "";
//
//		        try {
//		        	JSONObject jobject = new JSONObject(string);
//		        	JSONArray jArray = jobject.getJSONArray("result");
//		        	for (int i = 0; i < jArray.length(); i++) {
//
//		        		JSONObject jsonObj = jArray.getJSONObject(i);
//
//		        		value = jsonObj.getString("user_status");
//
//		        	}
//		        }catch(Exception e){
//		        	e.printStackTrace();
//		        }
		        try{
		        	
		        	if (Integer.parseInt(value) == 0) {
		            	
		            	SharedPreferences spns = getSharedPreferences(Const.TAG_SET_PREF, MODE_PRIVATE);
		                SharedPreferences.Editor e = spns.edit();
		                e.putBoolean(Const.TAG_LOGIN, false);
		                e.putString(Const.TAG_USERID, "");
		                e.putString(Const.TAG_USERPASS, "");
		                e.putString(Const.TAG_USEREMAIL, "");
		                e.putString(Const.TAG_CURRENCY_ID, "");
		                e.commit();
		                
		                startActivity(new Intent(MainActivity.this, ActivityLogin.class));
	                    finish();
		                
		    		}
					else
					{
						user_id=result.getUserId();
						SharedPreferences spns = getSharedPreferences(Const.TAG_SET_PREF, MODE_PRIVATE);
						SharedPreferences.Editor e = spns.edit();
						e.putString(Const.TAG_USERID,user_id);
						e.commit();
					}
		        	
		        }catch(NumberFormatException e){
		        	
		        }
		        
				
			}
			
			
//		});
//
//
//	}
//
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		if (resideMenu.isOpened()) {
			resideMenu.closeMenu();
		}else {
			/*if (back_pressed + 2000 > System.currentTimeMillis()) {
				super.onBackPressed();
			}else {
				tools.showToastMessage("Press once again to exit!");
			}
			back_pressed = System.currentTimeMillis();*/
			
			
			 if (!BaseActivity.getpreferenceBoolean(Const.TAG_RATE_NEVR, this)) {
		        	if (getpreferenceInt(Const.TAG_RATE, this) == 2) {
		            	/*tools.showLog("yeah", 1); // show dialog with later never and yes
		    			tools.showToastMessage("Yeah !");*/
		        		showRatePopup();
		    		}else {
		    			exitApp();
					}
		        }else {
		        	exitApp();
				}
			
			
			
			
		}
		
		
	}
	
	
	public void exitApp() {
		new AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
				.setTitle("Coin Cop")
				.setMessage("Do you want to exit ?")
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								moveTaskToBack(true);
								MainActivity.this.finish();


							}

				}).setNegativeButton("No", null).show();
	}
	

	@Override
	public void isVisible(int whichView) {
		// TODO Auto-generated method stub
		this.whichView = whichView;
		switch (whichView) {
		case 0: // home page
			tvTitle.setText("");
			imageLogo.setVisibility(View.VISIBLE);
			titleBarRightMenu.setVisibility(View.GONE);
			break;
		case 1: // accounts
			imageLogo.setVisibility(View.GONE);
			tvTitle.setText("Accounts");
			titleBarRightMenu.setVisibility(View.GONE);
			break;
		case 2: // category income
			imageLogo.setVisibility(View.GONE);
			tvTitle.setText("Category");
			titleBarRightMenu.setVisibility(View.GONE);
			break;
		case 3: // Reports
			imageLogo.setVisibility(View.GONE);
			tvTitle.setText("");
			titleBarRightMenu.setVisibility(View.GONE);
			break;

		case 4: // reminder
			imageLogo.setVisibility(View.GONE);
			tvTitle.setText("All Reminders");
			titleBarRightMenu.setVisibility(View.GONE);
			break;
		case 5: // Settings
			imageLogo.setVisibility(View.GONE);
			tvTitle.setText("Settings");
			titleBarRightMenu.setVisibility(View.VISIBLE);
			break;
		case 6: // category expense
			imageLogo.setVisibility(View.GONE);
			tvTitle.setText("Category");
//			titleBarRightMenu.setVisibility(View.VISIBLE);
			break;

		default:
			titleBarRightMenu.setVisibility(View.GONE);
			break;
		}
		
	}
	
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
	}
	
	public ResideMenu getResideMenu(){
        return resideMenu;
    }
}
