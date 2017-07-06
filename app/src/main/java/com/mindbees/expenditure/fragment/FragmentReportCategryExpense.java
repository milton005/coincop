package com.mindbees.expenditure.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mindbees.expenditure.ActivityReportCategoryView;
import com.mindbees.expenditure.R;
import com.mindbees.expenditure.adapter.AdapterChartview;
import com.mindbees.expenditure.model.ChartView;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;
import com.mindbees.expenditure.util.WebService;
import com.mindbees.expenditure.util.XMLParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentReportCategryExpense extends Fragment{
	ListView list;
	
	ArrayList<ChartView> allList;
	Tools tools;
	
	Bundle bund;
	String selectedYear;
	List<String> tempList;
	int duration;
	
	AdapterChartview adapter;
	
	private int[] PHOTO_TEXT_BACKGROUND_COLORS;
	
	ActivityReportCategoryView activity;
	String categoryId;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity = (ActivityReportCategoryView) activity; 
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		bund = activity.getIntent().getExtras();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_report_cat_expense, null);
		
		list = (ListView) v.findViewById(R.id.list);
		
//		categoryId = bund.getString("catID");
//		selectedYear = bund.getString("year");
//		duration = bund.getInt("duration");
//
		tools = new Tools(getActivity());
		
		
		PHOTO_TEXT_BACKGROUND_COLORS = getResources().getIntArray(R.array.contacts_text_background_colors);
		
	 	allList = new ArrayList<ChartView>();
        adapter = new AdapterChartview(getActivity(), allList);
        list.setAdapter(adapter);
        
        tempList = seperateDate("2016");
        
        
        if (tools.isConnectingToInternet()) {
        	loadDat(duration);
		}else {
			tools.showToastMessage("No Internet Connection !");
		}
		
		return v;
	}
	
	private void loadDat(int duration2) {
		// TODO Auto-generated method stub
		switch (duration) {
		case 101:
			loadDetils(selectedYear, "", Const.TAG_EXPENSE_ID, categoryId);
			break;

		case 102:
			loadDetils(tempList.get(1), tempList.get(0), Const.TAG_EXPENSE_ID, categoryId);
			break;
		case 103:
			String endYear = tempList.get(0)+"-"+"12"+"-"+"31";
			loadDetils(selectedYear, endYear, Const.TAG_EXPENSE_ID, categoryId);
			break;

		default:
			break;
		}
	}
	
	private List<String> seperateDate(String date){
		List<String> elephantList = Arrays.asList(date.split("-"));
		return elephantList;
	}
	
	
	
private void loadDetils(String startYear, String endYear, String type, String categoryId){
		
		final ProgressDialog pd = new ProgressDialog(getActivity());
		pd.setMessage("Loading");
		pd.setCancelable(false);
		
		RequestParams params = new RequestParams();
		params.put("user_id", BaseActivity.getpreference(Const.TAG_USERID, getActivity()));
		params.put("type_id", type);
		params.put("category_id", categoryId);
		
		if (duration == 103) { // yearly
			params.put("user_action", "1014");
			params.put("start_date", startYear);
			params.put("end_date", endYear);
		}else if (duration == 102) {
			params.put("user_action", "1016");
			params.put("month", startYear);
			params.put("year", endYear);
		}else if (duration == 101) {
			params.put("user_action", "1013");
			params.put("action_date", startYear);
		}
			
			
			
		
		WebService.post(Const.URL, params,
				new AsyncHttpResponseHandler() {
			
			
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
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
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
                tools.showToastMessage(activity.getResources().getString(R.string.connectivity));
			}
			
			
			private void parse(String string) {
				// TODO Auto-generated method stub
				
//				allCategory = new ArrayList<Category>();
				XMLParser parser = new XMLParser();
		        try {
		            String xml = string;

		            Document doc = parser.getDomElement(xml);
		            NodeList nl = doc.getElementsByTagName("account");
		            for (int i = 0; i < nl.getLength(); i++) {
		            	Element e = (Element) nl.item(i);
		            	
		            	String record_id = parser.getValue(e, "record_id");
		            	String user_id = parser.getValue(e, "user_id");
		            	String account_id = parser.getValue(e, "account_id");
		            	String type_id = parser.getValue(e, "type_id");
		            	String category_id = parser.getValue(e, "category_id");
		            	String amount = parser.getValue(e, "amount");
		            	String description = parser.getValue(e, "description");
		            	String action_date = parser.getValue(e, "action_date");
		            	String added_date = parser.getValue(e, "added_date");
		            	String category_title = parser.getValue(e, "category_title");
		            	String account_title = parser.getValue(e, "account_title");
		            	
		            	
		            	final int backgroundColorToUse = PHOTO_TEXT_BACKGROUND_COLORS[i % PHOTO_TEXT_BACKGROUND_COLORS.length];
		            	
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
		            	chrt.setColor(backgroundColorToUse);
		            	
		            	allList.add(chrt);
		            	
						
					}
		        }catch(Exception e){
		        	e.printStackTrace();
		        }
		        
		        
		        
		        adapter.notifyDataSetChanged();
		        
		        
		       
				
			}
		});
	}

}
