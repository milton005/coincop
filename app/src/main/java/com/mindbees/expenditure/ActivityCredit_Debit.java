package com.mindbees.expenditure;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.mindbees.expenditure.Interfaces.CategoryItem;
import com.mindbees.expenditure.Interfaces.RecyclerItemClicked;
import com.mindbees.expenditure.adapter.AdapterCategory;
import com.mindbees.expenditure.adapter.AdapterRE;
import com.mindbees.expenditure.adapter.AdapterViewPager;
import com.mindbees.expenditure.database.MyDataBase;
import com.mindbees.expenditure.fragment.FragmentAddAccountCal;
import com.mindbees.expenditure.fragment.FragmentAddAccountList;
import com.mindbees.expenditure.fragment.FragmentDescription;
import com.mindbees.expenditure.model.Accounts;
import com.mindbees.expenditure.model.Add_account.Modeladdaccount;
import com.mindbees.expenditure.model.CalendarE;
import com.mindbees.expenditure.model.Category;
import com.mindbees.expenditure.model.Homefragment.Modelallreminder;
import com.mindbees.expenditure.model.Homefragment.Reminder;
import com.mindbees.expenditure.model.addrecord.Modeladdrecord;
import com.mindbees.expenditure.model.creditdebit.loadcategory.Account;
import com.mindbees.expenditure.model.creditdebit.loadcategory.Modelloadcategory;
import com.mindbees.expenditure.retrofit.APIService;
import com.mindbees.expenditure.retrofit.ServiceGenerator;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;
import com.mindbees.expenditure.util.WebService;
import com.mindbees.expenditure.util.XMLParser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCredit_Debit extends BaseActivity implements OnClickListener, CategoryItem{
	
	Toolbar toolbar;
//	GridView mGridView;
	EditText eAddedValue;
	
	

	AdapterCategory adapter;
	Tools tools;
	
//	LinearLayout viewAcnt;
//	LinearLayout viewDate;
//	LinearLayout viewDesc;
	boolean isExpense;
	
	String categoryId = "";
	public int imagePosition = -1;
	ImageView imgClose;
	TextView currencySymbol;
	
	
	FragmentTabHost mtabHost;
	public ArrayList<Accounts> allAccounts = new ArrayList<Accounts>();
	public int listPosition = -1;
	public int gridPositionnn = -1;
	
	public String bankAccountId = "";
	public String dateAdding = "";
	public String desc = "";
	public int finalBalance = 0;
	public EditText edFromDescription;
	ImageView imageSubmit;
	public final List<CalendarE> gridList = new ArrayList();
	
	/*
	 * category section
	 */
	
	 ViewPager view_pager_avatar;
		ImageView imLft;
		ImageView imRght;
		RelativeLayout viewPagerView;
		public int pagerPosition = 0;
		ArrayList<Category> allCategory;
		AdapterViewPager adapterViewPager;
	TextView title;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categorys);
		
		setupUI(findViewById(R.id.parentView));
		
		if (getIntent().hasExtra("expense")) {
			isExpense = getIntent().getBooleanExtra("expense", true);
		}
		tools = new Tools(this);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		
//		mGridView = (GridView) findViewById(R.id.categoryList);
		
		view_pager_avatar = (ViewPager) findViewById(R.id.pager_avatar);
		imLft = (ImageView) findViewById(R.id.imgLeft);
		imRght = (ImageView) findViewById(R.id.imgRight);
		imLft.setOnClickListener(this);
		imRght.setOnClickListener(this);
		
		eAddedValue = (EditText) findViewById(R.id.addValue);
		currencySymbol = (TextView) findViewById(R.id.currency);
		imageSubmit = (ImageView) findViewById(R.id.imageSubmit);
		title= (TextView) findViewById(R.id.texttitle);
//		viewAcnt = (LinearLayout) findViewById(R.id.view1);
//		viewDate = (LinearLayout) findViewById(R.id.view2);
//		viewDesc = (LinearLayout) findViewById(R.id.view3);
		imgClose = (ImageView) findViewById(R.id.imgClose);
		
		
		mtabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mtabHost.setup(this, getSupportFragmentManager(),
				android.R.id.tabcontent);
		addViewTab(mtabHost);
		
//		mtabHost.setCurrentTab(whichTab);
		setPadding();
		
		imLft.setAlpha(0.5f);
		imLft.setEnabled(false);
		
		allCategory = new ArrayList<Category>();
		adapterViewPager = new AdapterViewPager(this, allCategory);
    	adapterViewPager.setCallBack(this);
    	view_pager_avatar.setAdapter(adapterViewPager);
    	
    	
//		adapter = new AdapterCategory(ActivityCredit_Debit.this, allCategory);
//        mGridView.setAdapter(adapter);
		
        currencySymbol.setText(getpreference(Const.TAG_SYMBOL, this));
		if(isExpense)
		{
			eAddedValue.setHint("expense");
			title.setText("Set Expenditure");
		}
		else {
			eAddedValue.setHint("income");
			title.setText("Set Income");
		}
		
		if (tools.isConnectingToInternet()) {
			loadCategorys(BaseActivity.getpreference(Const.TAG_USERID, this));
		}
		
		imgClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
		
		mtabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				setPadding();
			}
			
			
		});
		
		imageSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (isExpense) {
					validateAccount(Const.TAG_EXPENSE_ID);
					
				}else {
					validateAccount(Const.TAG_INCOME_ID);
				}
				
			}
		});
		
		
		view_pager_avatar.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub

				try {
					pagerPosition = arg0;
					
					if (pagerPosition > 0) {
						imLft.setAlpha(1f);
						imLft.setEnabled(true);
					} else {
						imLft.setAlpha(0.5f);
						imLft.setEnabled(false);
					}

					if (pagerPosition < (((allCategory.size()%3)==0)?(allCategory.size()/3):(allCategory.size()/3)+1)-1) {
						imRght.setAlpha(1f);
						imRght.setEnabled(true);
					} else {
						imRght.setAlpha(0.5f);
						imRght.setEnabled(false);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		/*viewAcnt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
					moveTotabcategory(0);
			}
		});
		viewDate.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
							moveTotabcategory(1);
						
					}
				});
		viewDesc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					moveTotabcategory(2);
			}
		});*/
		
		/*mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				categoryId = adapter.allList.get(position).getCatId();
				
				if (gridPosition != -1) {
					adapter.allList.get(gridPosition).setSelected(false);
				}
				gridPosition = position;
				adapter.allList.get(position).setSelected(true);
				adapter.notifyDataSetChanged();
			}
		});*/
		
		
	}
	
	private void setPadding() {

		for (int i = 0; i < mtabHost.getTabWidget().getChildCount(); i++) {

			mtabHost.getTabWidget().getChildAt(i).setPadding(4, 0, 4, 0);

		}
	}
	
	
	private void addViewTab(FragmentTabHost mtabHost2) {
        // TODO Auto-generated method stub

        mtabHost2.addTab(
                mtabHost2.newTabSpec("tab1").setIndicator(getTabIndicator("ACCOUNT", R.drawable.add_acnt_icon)),
                FragmentAddAccountList.class, null);
        mtabHost2.addTab(
                mtabHost2.newTabSpec("tab2").setIndicator(getTabIndicator("DATE", R.drawable.add_date_icon)),
                FragmentAddAccountCal.class, null);

        mtabHost2.addTab(
                mtabHost2.newTabSpec("tab3").setIndicator(getTabIndicator("DESCRIPTION", R.drawable.add_description_icn)),
                FragmentDescription.class, null);


    }

	
	private View getTabIndicator(String title, int resId) {
        View tab = LayoutInflater.from(this).inflate(R.layout.inflate_tab, null);
        TextView tv = (TextView) tab.findViewById(R.id.textTab);
        tv.setText(title);
        ImageView im = (ImageView) tab.findViewById(R.id.imgeTab);
        im.setBackgroundResource(resId);
        return tab;

    }
	
	
	private void loadCategorys(String userid){
		
		final ProgressDialog pd = new ProgressDialog(this);
		pd.setMessage("Loading");
		pd.setCancelable(false);
		pd.show();

		HashMap<String, String> params = new HashMap<>();
//		params.put("user_action", "1011");
		params.put("user_id", userid);
		if (isExpense) {
			params.put("type_id", Const.TAG_EXPENSE_ID); // expense
		}else {
			params.put("type_id", Const.TAG_INCOME_ID); // income	
		}
		params.put("request","usercategory");

		APIService service = ServiceGenerator.createService(APIService.class,ActivityCredit_Debit.this);
		Call<Modelloadcategory> call = service.load_category(params);
		call.enqueue(new Callback<Modelloadcategory>() {
			@Override
			public void onResponse(Call<Modelloadcategory> call, Response<Modelloadcategory> response) {
				if (pd.isShowing()) {
					pd.dismiss();
				}
				if (response.isSuccessful()) {
					if(response.body()!=null){
						if(response.body().getAccount()!=null){
							List<Account>accounts=response.body().getAccount();
							for(int i=0;i<accounts.size();i++) {
								String catId = accounts.get(i).getCategoryId();
								String catTitlle = accounts.get(i).getCategoryTitle();
								String type_id = accounts.get(i).getTypeId();
								String user_id = accounts.get(i).getUserId();
								String added_date = accounts.get(i).getAddedDate();
								String cat_image = accounts.get(i).getCatImage();
								String cat_color = accounts.get(i).getCatColor();
//							allCategory.addAll(accounts);
//							adapterViewPager.notifyDataSetChanged();
								Category cat = new Category();
								cat.setCatId(catId);
								cat.setCatTitlle(catTitlle);
								cat.setType_id(type_id);
								cat.setUser_id(user_id);
								cat.setAdded_date(added_date);
								cat.setCat_image(cat_image);
								cat.setCat_color(cat_color);
								cat.setSelected(false);
								allCategory.add(cat);
							}
							adapterViewPager.notifyDataSetChanged();
						}
					}
				}

			}

			@Override
			public void onFailure(Call<Modelloadcategory> call, Throwable t) {
				if (pd.isShowing()) {
					pd.dismiss();
				}
//				tools.showToastMessage(getResources().getString(R.string.connectivity));
			}
		});
//		WebService.post(Const.URL+"usercategory", params, new AsyncHttpResponseHandler() {
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
//				String imageLink = Const.imageLink;
//		        try {
//
//		            JSONObject jobject = new JSONObject(string);
//		            JSONArray jarray = jobject.getJSONArray("account");
//		            for (int i = 0; i < jarray.length(); i++) {
//
//		            	JSONObject jsonobject  = jarray.getJSONObject(i);
////
//		            	String catId = jsonobject.getString("category_id");
//		            	String catTitlle = jsonobject.getString("category_title");
//		            	String type_id = jsonobject.getString("type_id");
//	            	String user_id = jsonobject.getString("user_id");
//	            	String added_date = jsonobject.getString("added_date");
//		            	String cat_image = jsonobject.getString("cat_image");
//		            	String cat_color = jsonobject.getString("cat_color");
//
//		            	Category cat = new Category();
//		            	cat.setCatId(catId);
//	            	cat.setCatTitlle(catTitlle);
//		            	cat.setType_id(type_id);
//	            	cat.setUser_id(user_id);
//		            	cat.setAdded_date(added_date);
//	            	cat.setCat_image(imageLink+cat_image);
//		            	cat.setCat_color(cat_color);
//	            	cat.setSelected(false);
//				allCategory.add(cat);
//
//					}
//
//		        }catch(Exception e){
//		        	e.printStackTrace();
//		        }
//
////		        adapter = new AdapterCategory(ActivityCategory.this, allCategory);
//		        adapterViewPager.notifyDataSetChanged();
////		        mGridView.setAdapter(adapter);
//
//			}
//		});
	}
	
	

	/*private void moveTotabcategory(int whichtab){
		
		String amount = eAddedValue.getText().toString().trim();
		
		if (gridPosition == -1) {
			tools.showToastMessage("Please select category");
			
		}else if (amount.isEmpty()) {
			tools.showToastMessage("Add Amount");
		}else {
			Bundle b = new Bundle();
			b.putInt("tab", whichtab);
			b.putString("amnt", amount);
			b.putString("cat_id", categoryId);
			if (isExpense) {
				b.putString("type_id", Const.TAG_EXPENSE_ID);
				
			}else {
				b.putString("type_id", Const.TAG_INCOME_ID);
			}
			
			Intent i = new Intent(getApplicationContext(), ActivityAddExpense_Income.class);
			i.putExtras(b);
			startActivity(i);
		}
		
		
		
	}*/
	
	

	protected void validateAccount(String typeid) {
		// TODO Auto-generated method stub
		String amount = eAddedValue.getText().toString().trim();
		try {
			desc = edFromDescription.getText().toString().trim();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (amount.isEmpty()) {
			tools.showToastMessage("Add Amount");
			
		}else if (categoryId.isEmpty()) {
			tools.showToastMessage("Select Category");
			
		}else if (bankAccountId.isEmpty()) {
			tools.showToastMessage("Select Account");
			mtabHost.setCurrentTab(0);
			
		}else if (typeid.contentEquals(Const.TAG_EXPENSE_ID)) {
			if (finalBalance < Integer.parseInt(amount)) {
//				tools.showToastMessage("You don't have enough balance in the selected account !");
				noBalance();
				mtabHost.setCurrentTab(0);
			}else if (dateAdding.isEmpty()) {
				mtabHost.setCurrentTab(1);
				tools.showToastMessage("Select Date");
			}else if (desc.isEmpty()) {
				mtabHost.setCurrentTab(2);
				tools.showToastMessage("Create Description");
				
			}else {
				submitAccount(bankAccountId, typeid, categoryId, amount, desc, dateAdding);
			}
			
		}else if (dateAdding.isEmpty()) {
			mtabHost.setCurrentTab(1);
			tools.showToastMessage("Select Date");
		}else if (desc.isEmpty()) {
			mtabHost.setCurrentTab(2);
			tools.showToastMessage("Create Description");
			
		}else {
			submitAccount(bankAccountId, typeid, categoryId, amount, desc, dateAdding);
		}
		
	}
	
	 private void noBalance() {

	        String msg = "You don't have enough balance in the selected account !";

	        new AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
	                .setMessage(msg)
	                .setNegativeButton("OK", null).show();
	    }
	
	private void submitAccount(String acnt_id, String type_id, String category_id, String amount, String description, String action_date) {
		// TODO Auto-generated method stub
		
		final ProgressDialog pd = new ProgressDialog(this);
		pd.setMessage("Loading");
		pd.setCancelable(false);
		pd.show();

		HashMap<String, String> params = new HashMap<>();
//		params.put("user_action", "1012");
		params.put("user_id", BaseActivity.getpreference(Const.TAG_USERID, this));
		params.put("account_id", acnt_id);
		params.put("type_id", type_id);
		params.put("category_id", category_id);
		params.put("amount", amount);
		params.put("description", description);
		params.put("action_date", action_date);
		params.put("request","addrecord");
		
		tools.showLog(acnt_id, 1);
		tools.showLog(type_id, 1);
		tools.showLog(category_id, 1);
		tools.showLog(amount, 1);
		tools.showLog(description, 1);
		tools.showLog(action_date, 1);
		APIService service = ServiceGenerator.createService(APIService.class,ActivityCredit_Debit.this);
		Call<Modeladdrecord> call = service.add_record(params);
		call.enqueue(new Callback<Modeladdrecord>() {
			@Override
			public void onResponse(Call<Modeladdrecord> call, Response<Modeladdrecord> response) {
				if (pd.isShowing()) {
					pd.dismiss();

				}
				if (response.isSuccessful())
				{
					if (response.body()!=null&&response.body().getResult()!=null){
						Modeladdrecord modeladdrecord=response.body();
						String message =modeladdrecord.getResult().getMessage();
						int value = modeladdrecord.getResult().getValue();
						tools.showToastMessage(message);
						try {
							if (value == 1) {

								resetValues();

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
			public void onFailure(Call<Modeladdrecord> call, Throwable t) {
				if (pd.isShowing()) {
					pd.dismiss();
				}
				tools.showToastMessage(getResources().getString(R.string.connectivity));
			}
		});
//		WebService.post(Const.URL+"addrecord", params, new AsyncHttpResponseHandler() {
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
////				tools.showLog(new String(arg2), 1);
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
//				tools.showLog(new String(arg2), 1);
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
		
		
	}
	
	
	private void parse(String string) throws NumberFormatException {
		// TODO Auto-generated method stub
		
    	String message = "";
    	int value = 0;
		
        try {
            JSONObject jobject = new JSONObject(string);
            JSONObject jsonobject = jobject.getJSONObject("result");
            
            value = jsonobject.getInt("value");
        	message = jsonobject.getString("message");

        }catch(Exception e){
        	e.printStackTrace();
        }
		tools.showToastMessage(message);
		
		try {
			if (value == 1) {
				
				resetValues();
				
				finish();
				
				
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void resetValues() {
		// TODO Auto-generated method stub
		
		eAddedValue.setText("");
		categoryId = "";
		bankAccountId = "";
		dateAdding = "";
		desc = "";
		adapterViewPager.list.get(imagePosition).setSelected(false);
		adapterViewPager.notifyDataSetChanged();
		imagePosition = -1;
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
	}
	
	
	
/*	private void loadAccounts(String userid) {

		final ProgressDialog pd = new ProgressDialog(this);
		pd.setMessage("Loading");
		pd.setCancelable(false);

		RequestParams params = new RequestParams();
		params.put("user_action", "1008");
		params.put("user_id", userid);

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

				parse(new String(arg2));
				tools.showLog(new String(arg2), 2);
				if (pd.isShowing()) {
					pd.dismiss();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub

				try {
					Tools.debugResponse(new String(arg2));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (pd.isShowing()) {
					pd.dismiss();
				}

				Tools.debugThrowable(arg3);

				if (arg2 != null) {
					Tools.debugResponse(new String(arg2));
				}
				tools.showToastMessage(getResources().getString(R.string.connectivity));
			}

			private void parse(String string) {
				// TODO Auto-generated method stub

				allAccounts = new ArrayList<Accounts>();
				XMLParser parser = new XMLParser();
				try {
					String xml = string;

					Document doc = parser.getDomElement(xml);
					NodeList nl = doc.getElementsByTagName("account");
					for (int i = 0; i < nl.getLength(); i++) {
						Element e = (Element) nl.item(i);

						String account_id = parser.getValue(e, "account_id");
						String user_id = parser.getValue(e, "user_id");
						String account_title = parser.getValue(e,
								"account_title");
						String added_date = parser.getValue(e, "added_date");
						String initial_amount = parser.getValue(e,
								"initial_amount");
						String final_balance = parser.getValue(e, "final_balance");

						Accounts acc = new Accounts();
						acc.setAccount_id(account_id);
						acc.setAccount_title(account_title);
						acc.setUser_id(user_id);
						acc.setInitial_amount(initial_amount);
						acc.setAdded_date(added_date);
						acc.setFinal_balance(final_balance);
						acc.setSelected(false);

						allAccounts.add(acc);

					}
				} catch (Exception e) {
					e.printStackTrace();
				}


//				listOfAccounts.setAdapter(adapter);

			}
		});
	}*/
	
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		 if (v == imLft){
	        	if (pagerPosition >= 0) {
	        		view_pager_avatar.setCurrentItem(pagerPosition - 1);
	        	}
	        	
	        }
	        if (v == imRght){
	        	if (pagerPosition < (((allCategory.size()%3)==0)?(allCategory.size()/3):(allCategory.size()/3)+1)-1) {
	        		view_pager_avatar.setCurrentItem(pagerPosition + 1);
	        	}
	        	
	        }
		
	}

	@Override
	public void onCategoryItemClicked(String categoryId, int position) {
		// TODO Auto-generated method stub
		this.categoryId = categoryId;
		
		/*if (bankAccountId.isEmpty()) {
			tools.showToastMessage("Select Account");
			mtabHost.setCurrentTab(0);
			
		}*/
	}


}
