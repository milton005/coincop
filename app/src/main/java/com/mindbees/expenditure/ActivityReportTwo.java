package com.mindbees.expenditure;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mindbees.expenditure.adapter.AdapterExapndList;
import com.mindbees.expenditure.adapter.Adapteryear;
import com.mindbees.expenditure.model.ChartView;
import com.mindbees.expenditure.model.Reportyearlycategory.Info;
import com.mindbees.expenditure.model.Reportyearlycategory.ModelYearlyCategoryReport;
import com.mindbees.expenditure.model.Total;
import com.mindbees.expenditure.model.Update_profile.Modelupdate_profile;
import com.mindbees.expenditure.model.Yearlyreport.Account;
import com.mindbees.expenditure.model.Yearlyreport.ModelYearlyReport;
import com.mindbees.expenditure.retrofit.APIService;
import com.mindbees.expenditure.retrofit.ServiceGenerator;
import com.mindbees.expenditure.ui.swiperemove.Attributes;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;
import com.mindbees.expenditure.util.WebService;
import com.mindbees.expenditure.util.XMLParser;
import com.nostra13.universalimageloader.utils.L;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityReportTwo extends AppCompatActivity{
	
	ExpandableListView expnadbleList;
	Tools tools;
	
	List<String> listDataHeader;
    HashMap<String, List<ChartView>> listDataChild;
    List<String> temList;
    AdapterExapndList adapter;
    
    Bundle bund;
    String type;
    ImageView bckBtn;
    TextView noviewLabel;
    boolean isCategory;
    
    ArrayList<String> yearList;
    Adapteryear adapterYear;
    Spinner spinner;
    Calendar _calendar;
    
    public int totalExpense = 0;
    public int totalIncome = 0;
    
    TextView tvTotalIncome;
    TextView tvTotalExpense;
    ArrayList<Total> total = new ArrayList<Total>();
    LinearLayout totalLayout;
//    int year ;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_two);
		
		bund = getIntent().getExtras();
		type = bund.getString("type");
		isCategory = bund.getBoolean("isCategory");
		
		tools = new Tools(this);
		expnadbleList = (ExpandableListView) findViewById(R.id.listExpand);
		bckBtn = (ImageView) findViewById(R.id.backBtn);
		noviewLabel = (TextView) findViewById(R.id.noViewLabel);
		spinner = (Spinner) findViewById(R.id.year);
		tvTotalIncome = (TextView) findViewById(R.id.totalIncome);
		tvTotalExpense = (TextView) findViewById(R.id.totalExpense);
		totalLayout = (LinearLayout) findViewById(R.id.total);
		
		DisplayMetrics metrics = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(metrics); 
	    int width = metrics.widthPixels;  
	    
	    if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
	    	expnadbleList.setIndicatorBounds(width-GetPixelFromDips(35), width-GetPixelFromDips(5));
	    } else {  
	    	expnadbleList.setIndicatorBoundsRelative(width-GetPixelFromDips(35), width-GetPixelFromDips(5));
	    } 
//	    expnadbleList.setIndicatorBounds(width - GetPixelFromDips(50), width - GetPixelFromDips(10));   
	 
	    
	    _calendar = Calendar.getInstance(Locale.getDefault());

		int yearCrnt = _calendar.get(Calendar.YEAR);
		int u=_calendar.get(Calendar.MONTH);

	    
	    yearList = new ArrayList<String>();

		for (int i = 2013; i <= 2026 ; i++) {
			
			yearList.add(i+"");
		}
		
		adapterYear = new Adapteryear(this, yearList);
		spinner.setAdapter(adapterYear);
		spinner.setSelection(getIndex(adapterYear, String.valueOf(yearCrnt)));
	    
		
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<ChartView>>();
		
		adapter = new AdapterExapndList(listDataHeader, listDataChild, this, isCategory);
		expnadbleList.setAdapter(adapter);
		
		
		
		expnadbleList.setOnGroupExpandListener(new OnGroupExpandListener() {
			
			@Override
			public void onGroupExpand(int groupPosition) {
				// TODO Auto-generated method stub
//				tools.showLog(groupPosition+"", 1);
				
				
				setTotal(groupPosition, true);
			}
		});
		expnadbleList.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			
			@Override
			public void onGroupCollapse(int groupPosition) {
				// TODO Auto-generated method stub
				setTotal(groupPosition, false);
			}
		});
		
		
//		tempList = seperateDate("");
//		Calendar cal = Calendar.getInstance();
//		year = cal.get(Calendar.YEAR);
//		String startyear = String.valueOf(year)+"-"+"01"+"-"+"01";
//		String endyaer = String.valueOf(year)+"-"+"12"+"-"+"31";
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String yearSelected = adapterYear.list.get(position);
				if (tools.isConnectingToInternet()) {
					loadDetils(type, yearSelected);
				}else {
					tools.showToastMessage(getResources().getString(R.string.connectivity));
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
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
	
	 private int getIndex(Adapteryear adapter2, String myString) {

	        int index = 0;

	        for (int i = 0; i < adapter2.list.size(); i++) {
//	            Log.e(LOG_TAG, "option id-----------"+adapter2.list.get(i).get_optionId());

	            if (adapter2.list.get(i).contentEquals(myString)) {

	                index = i;

	            }
	        }
	        return index;
	    }
	
	
	public int GetPixelFromDips(float pixels) {
	    // Get the screen's density scale  
	    final float scale = getResources().getDisplayMetrics().density;
	    // Convert the dps to pixels, based on density scale 
	    return (int) (pixels * scale + 0.5f);
	}
	
	
	
	
private void loadDetils(String type, String yearSelected){
	
		String url = "";
		
		final ProgressDialog pd = new ProgressDialog(this);
		pd.setMessage("Loading");
		pd.setCancelable(false);
	pd.show();

	HashMap<String, String> params = new HashMap<>();
		params.put("user_id", BaseActivity.getpreference(Const.TAG_USERID, this));
		params.put("year", yearSelected);
		if (isCategory) {
//			params.put("user_action", "1028");
			params.put("request","yearlycategoryreport");
			APIService service = ServiceGenerator.createService(APIService.class,ActivityReportTwo.this);
			Call<ModelYearlyCategoryReport> call = service.yearlycategoryreport(params);
			call.enqueue(new Callback<ModelYearlyCategoryReport>() {
				@Override
				public void onResponse(Call<ModelYearlyCategoryReport> call, Response<ModelYearlyCategoryReport> response) {
					if (pd.isShowing()) {
						pd.dismiss();
					}
					if (response.isSuccessful()) {
						if (response.body() != null && response.body().getInfo() != null) {
							List<Info> infos = response.body().getInfo();
							listDataHeader.clear();
							listDataChild.clear();
							total.clear();
							totalExpense = 0;
							totalIncome = 0;
							String month = "";
							//	String monthTotal = "";
							String prevMonth = "";
							ArrayList<ChartView> acntDetail = null;
							for (int i = 0; i < infos.size(); i++) {
								month = infos.get(i).getMonth();

								String type_id = infos.get(i).getTypeId();
								String category_id = infos.get(i).getCategoryId();
								String amount = infos.get(i).getAmount();
								String category_title = infos.get(i).getCategoryTitle();
//       				 	monthTotal = month;


								ChartView chrt = new ChartView();
								chrt.setType_id(type_id);
								chrt.setCategory_id(category_id);
								chrt.setAmount(amount);
								chrt.setCategory_title(category_title);
								chrt.setMonth(month);


								if (!(month.contentEquals(prevMonth))) {
									acntDetail = new ArrayList<ChartView>();

								}

								acntDetail.add(chrt);


								if (!(month.contentEquals(prevMonth))) {

									listDataHeader.add(month);
									listDataChild.put(month, acntDetail);
									prevMonth = month;

        		/*for (int j = 0; j < acntDetail.size(); j++) {
        			if (acntDetail.get(j).getType_id().contentEquals(Const.TAG_EXPENSE_ID)) {
        				totalExpense = totalExpense + Integer.parseInt(acntDetail.get(j).getAmount());
        			}else if (acntDetail.get(j).getType_id().contentEquals(Const.TAG_INCOME_ID)) {
        				totalIncome = totalIncome + Integer.parseInt(acntDetail.get(j).getAmount());
        			}
				}

        		Total tot = new Total();
				tot.setTotalExpense(totalExpense);
				tot.setTotalIncome(totalIncome);
				total.add(tot);
				totalExpense = 0;
				totalIncome = 0;*/

								}
							}
							adapter.notifyDataSetChanged();
							if (listDataHeader.size() > 0) {

								noviewLabel.setVisibility(View.GONE);
								expnadbleList.expandGroup(listDataHeader.size() - 1, true);
								setTotal(listDataHeader.size() - 1, true);
							}

						}
						if(response.body().getInfo().isEmpty())
						{
							expnadbleList.setVisibility(View.GONE);
							noviewLabel.setVisibility(View.VISIBLE);
							totalLayout.setVisibility(View.GONE);

						}
					}
				}

				@Override
				public void onFailure(Call<ModelYearlyCategoryReport> call, Throwable t) {
					if (pd.isShowing()) {
						pd.dismiss();
					}
					tools.showToastMessage(getResources().getString(R.string.connectivity));
				}
			});
			
		}else {	
//			params.put("user_action", "1025");
		params.put("request","yearlyreport");
			params.put("type_id", type);
			APIService service = ServiceGenerator.createService(APIService.class,ActivityReportTwo.this);
			Call<ModelYearlyReport> call = service.yearlyreport(params);
			call.enqueue(new Callback<ModelYearlyReport>() {
				@Override
				public void onResponse(Call<ModelYearlyReport> call, Response<ModelYearlyReport> response) {
					if (pd.isShowing()) {
						pd.dismiss();
					}
					if (response.isSuccessful())
					{

						if (response.body()!=null&&response.body().getInfo()!=null)
						{
							ModelYearlyReport modelYearlyReport=response.body();
							List<com.mindbees.expenditure.model.Yearlyreport.Info> infos=modelYearlyReport.getInfo();
						listDataHeader.clear();
							listDataChild.clear();
							total.clear();
							totalExpense = 0;
							totalIncome = 0;

							String month = "";
							String prevMonth="";

							ArrayList<ChartView> acntDetails = new ArrayList<ChartView>();
							for(int i=0;i<infos.size();i++) {
								List<Account> accounts = infos.get(i).getAccount();
								for (int j = 0; j < accounts.size(); j++) {

									String record_id = accounts.get(j).getRecordId();
									String user_id = accounts.get(j).getUserId();
									String account_id = accounts.get(j).getAccountId();
									String type_id = accounts.get(j).getTypeId();
									String category_id = accounts.get(j).getCategoryId();
									String amount = accounts.get(j).getAmount();
									String description = accounts.get(j).getDescription();
									String action_date = accounts.get(j).getActionDate();
									String added_date = accounts.get(j).getAddedDate();
									String category_title = accounts.get(j).getCategoryTitle();
									String account_title = accounts.get(j).getAccountTitle();
									String day = accounts.get(j).getDay();
									month = accounts.get(j).getMonth();

			            	/*if (type_id.contentEquals(Const.TAG_EXPENSE_ID)) {
			    				totalExpense = totalExpense + Integer.parseInt(amount);
			    			}else if (type_id.contentEquals(Const.TAG_INCOME_ID)) {
			    				totalIncome = totalIncome + Integer.parseInt(amount);
			    			}
			            	*/


									ChartView chrt = new ChartView();
									chrt.setRecord_id(record_id);
									chrt.setUser_id(user_id);
									chrt.setAccount_id(account_id);
									chrt.setType_id(type_id);
									chrt.setCategory_id(category_id);
									chrt.setAmount(amount);
									chrt.setDescription(description);
									chrt.setAction_date(action_date);
									chrt.setAdded_date(added_date);
									chrt.setCategory_title(category_title);
									chrt.setAccount_title(account_title);
									chrt.setMonth(month);
									chrt.setDay(day);
									if (!(month.contentEquals(prevMonth))) {
										acntDetails = new ArrayList<ChartView>();

									}

									acntDetails.add(chrt);
									if (!(month.contentEquals(prevMonth))) {

										listDataHeader.add(month);
										listDataChild.put(month, acntDetails);
										prevMonth = month;
									}

								}
//								listDataHeader.add(month);
//								listDataChild.put(month, acntDetails);
								adapter.notifyDataSetChanged();

								}
							if (listDataHeader.size() > 0) {

								noviewLabel.setVisibility(View.GONE);

								expnadbleList.expandGroup(listDataHeader.size() - 1, true);
								setTotal(listDataHeader.size() - 1, true);
							}
						}
						if(response.body().getInfo().isEmpty())
						{
							expnadbleList.setVisibility(View.GONE);
							noviewLabel.setVisibility(View.VISIBLE);
							totalLayout.setVisibility(View.GONE);

						}
					}
				}

				@Override
				public void onFailure(Call<ModelYearlyReport> call, Throwable t) {
					if (pd.isShowing()) {
						pd.dismiss();
					}
					tools.showToastMessage(getResources().getString(R.string.connectivity));
				}
			});
		}
		
		
//		WebService.post(url, params, new AsyncHttpResponseHandler() {
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
//				if (isCategory) {
//					parse_category(new String(arg2));
//				}else {
//					parse(new String(arg2));
//				}
//
//				tools.showLog(new String(arg2), 2);
//
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
//
//				listDataHeader.clear();
//				listDataChild.clear();
//				total.clear();
//				totalExpense = 0;
//				totalIncome = 0;
//
//				String month = "";
//				ArrayList<ChartView> acntDetails;
//		        try {
//
//
//
//		            JSONObject jobject = new JSONObject(string);
//		            JSONArray jarray = jobject.getJSONArray("info");
//		            for (int i = 0; i < jarray.length(); i++) {
//		            	acntDetails = new ArrayList<ChartView>();
//
//						JSONObject jsonobject = jarray.getJSONObject(i);
//						JSONArray jsonarray = jsonobject.getJSONArray("account");
//
//						for (int j = 0; j < jsonarray.length(); j++) {
//							JSONObject jsoninner = jsonarray.getJSONObject(j);
//
//							String record_id = jsoninner.getString("record_id");
//			            	String user_id = jsoninner.getString("user_id");
//			            	String account_id = jsoninner.getString("account_id");
//			            	String type_id = jsoninner.getString("type_id");
//			            	String category_id = jsoninner.getString("category_id");
//			            	String amount = jsoninner.getString("amount");
//			            	String description = jsoninner.getString("description");
//			            	String action_date = jsoninner.getString("action_date");
//			            	String added_date = jsoninner.getString("added_date");
//			            	String category_title = jsoninner.getString("category_title");
//			            	String account_title = jsoninner.getString("account_title");
//			            	String day = jsoninner.getString("day");
//			            	month = jsoninner.getString("month");
//
//			            	/*if (type_id.contentEquals(Const.TAG_EXPENSE_ID)) {
//			    				totalExpense = totalExpense + Integer.parseInt(amount);
//			    			}else if (type_id.contentEquals(Const.TAG_INCOME_ID)) {
//			    				totalIncome = totalIncome + Integer.parseInt(amount);
//			    			}
//			            	*/
//
//
//			            	ChartView chrt = new ChartView();
//			            	chrt.setRecord_id(record_id);
//			            	chrt.setUser_id(user_id);
//			            	chrt.setAccount_id(account_id);
//			            	chrt.setType_id(type_id);
//			            	chrt.setCategory_id(category_id);
//			            	chrt.setAmount(amount);
//			            	chrt.setDescription(description);
//			            	chrt.setAction_date(action_date);
//			            	chrt.setAdded_date(added_date);
//			            	chrt.setCategory_title(category_title);
//			            	chrt.setAccount_title(account_title);
//			            	chrt.setMonth(month);
//			            	chrt.setDay(day);
//			            	acntDetails.add(chrt);
//
//
//						}
//						/*Total tot = new Total();
//						tot.setTotalExpense(totalExpense);
//						tot.setTotalIncome(totalIncome);
//						total.add(tot);*/
//
//						listDataHeader.add(month);
//		            	listDataChild.put(month, acntDetails);
////		            	totalExpense = 0;
////						totalIncome = 0;
//
//					}
//
//		        }catch(Exception e){
//		        	e.printStackTrace();
//		        }
//
//
//		        adapter.notifyDataSetChanged();
//		        if (listDataHeader.size() > 0) {
//
//		        	noviewLabel.setVisibility(View.GONE);
//		        	expnadbleList.expandGroup(listDataHeader.size()-1, true);
//		        	setTotal(listDataHeader.size()-1, true);				}
//
//
//
//			}
//		});
	}

protected void parse_category(String string) {
	// TODO Auto-generated method stub
	
	listDataHeader.clear();
	listDataChild.clear();
	total.clear();
	totalExpense = 0;
	totalIncome = 0;
	
	String month = "";
//	String monthTotal = "";
	String prevMonth = "";
	ArrayList<ChartView> acntDetail = null;
    try {
        
        JSONObject jobject = new JSONObject(string);
        JSONArray jarray = jobject.getJSONArray("info");
        
        for (int i = 0; i < jarray.length(); i++) {
        	JSONObject jsonobject = jarray.getJSONObject(i);
        	
        	month = jsonobject.getString("month");
        	
        	String type_id = jsonobject.getString("type_id");
        	String category_id = jsonobject.getString("category_id");
        	String amount = jsonobject.getString("amount");
        	String category_title = jsonobject.getString("category_title");
//        	monthTotal = month;

    		
        
        	ChartView chrt = new ChartView();
        	chrt.setType_id(type_id);
        	chrt.setCategory_id(category_id);
        	chrt.setAmount(amount);
        	chrt.setCategory_title(category_title);
        	chrt.setMonth(month);
        	

        	if (!(month.contentEquals(prevMonth))) {
        		acntDetail = new ArrayList<ChartView>();
        		
			}
        	
        	acntDetail.add(chrt);
        	
        	
        	if (!(month.contentEquals(prevMonth))) {
        		
        		listDataHeader.add(month);
        		listDataChild.put(month, acntDetail);
        		prevMonth = month;
        		
        		/*for (int j = 0; j < acntDetail.size(); j++) {
        			if (acntDetail.get(j).getType_id().contentEquals(Const.TAG_EXPENSE_ID)) {
        				totalExpense = totalExpense + Integer.parseInt(acntDetail.get(j).getAmount());
        			}else if (acntDetail.get(j).getType_id().contentEquals(Const.TAG_INCOME_ID)) {
        				totalIncome = totalIncome + Integer.parseInt(acntDetail.get(j).getAmount());
        			}
				}
        		
        		Total tot = new Total();
				tot.setTotalExpense(totalExpense);
				tot.setTotalIncome(totalIncome);
				total.add(tot);
				totalExpense = 0;
				totalIncome = 0;*/
        		
        	}
        	
        	
        	
        	
        }
    }catch(Exception e){
    	e.printStackTrace();
    }
    
    
    adapter.notifyDataSetChanged();
    if (listDataHeader.size() > 0) {
    	
    	noviewLabel.setVisibility(View.GONE);
    	expnadbleList.expandGroup(listDataHeader.size()-1, true);
    	setTotal(listDataHeader.size()-1, true);
	}
    
	
}

	private void setTotal(int groupPosition, boolean isVisible) {
		// TODO Auto-generated method stub
		totalExpense = 0;
		totalIncome = 0;

		if (isVisible) {
//			totalLayout.setVisibility(View.VISIBLE);
			List<ChartView> list = adapter.getEachList(adapter
					.getHeader(groupPosition));

			for (int j = 0; j < list.size(); j++) {
				if (list.get(j).getType_id()
						.contentEquals(Const.TAG_EXPENSE_ID)) {
					totalExpense = totalExpense
							+ Integer.parseInt(list.get(j).getAmount());
				} else if (list.get(j).getType_id()
						.contentEquals(Const.TAG_INCOME_ID)) {
					totalIncome = totalIncome
							+ Integer.parseInt(list.get(j).getAmount());
				}
			}

			if (totalExpense != 0 && totalIncome != 0) {
				tvTotalIncome.setVisibility(View.VISIBLE);
				tvTotalExpense.setVisibility(View.VISIBLE);
				tvTotalIncome.setText(String.valueOf("Income " + totalIncome));
				tvTotalExpense.setText(String
						.valueOf("Expense " + totalExpense));
			} else if (totalIncome != 0 && totalExpense == 0) {
				tvTotalIncome.setVisibility(View.VISIBLE);
				tvTotalExpense.setVisibility(View.GONE);
				tvTotalIncome.setText(String.valueOf("Income " + totalIncome));
			} else if (totalExpense != 0 && totalIncome == 0) {
				tvTotalExpense.setVisibility(View.VISIBLE);
				tvTotalIncome.setVisibility(View.GONE);
				tvTotalExpense.setText(String
						.valueOf("Expense " + totalExpense));
			}
		}else {
//			totalLayout.setVisibility(View.GONE);
		}

	}

}
