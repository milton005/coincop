package com.mindbees.expenditure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieSlice;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mindbees.expenditure.adapter.AdapterChartview;
import com.mindbees.expenditure.model.Category;
import com.mindbees.expenditure.model.ChartView;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;
import com.mindbees.expenditure.util.WebService;
import com.mindbees.expenditure.util.XMLParser;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityChartView extends AppCompatActivity{
	
	PieGraph pg;
	ListView listCategorys;
	TextView currentDate;
	TextView money;
	TextView pichartType;
	TextView symbol;
	
	Tools tools;
	ArrayList<ChartView> allList;
	
	private int[] PHOTO_TEXT_BACKGROUND_COLORS;
	AdapterChartview adapter;
	
	Bundle bund;
	String type;
	String selectedYear;
	List<String> tempList;
	
	LinearLayout centreCircle;
	
    private final String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

    int duration;
	
	//come data from each fragment class daily, monthly, yearly
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chartview);
		
		bund = getIntent().getExtras();
		type = bund.getString("type");
		selectedYear = bund.getString("year");
		duration = bund.getInt("duration");
		
		tools = new Tools(this);
		
		pg = (PieGraph) findViewById(R.id.piegraph);
		listCategorys = (ListView) findViewById(R.id.listReports);
		currentDate = (TextView) findViewById(R.id.reportDate);
		money = (TextView) findViewById(R.id.reportRupees);
		symbol = (TextView) findViewById(R.id.symbol);
		pichartType = (TextView) findViewById(R.id.type);
		centreCircle = (LinearLayout) findViewById(R.id.centreCircle);
		
		PHOTO_TEXT_BACKGROUND_COLORS = getResources().getIntArray(R.array.contacts_text_background_colors);
		
		 tempList = seperateDate(selectedYear);
		 
		 
		 
		 
		 
		 
		  	allList = new ArrayList<ChartView>();
	        adapter = new AdapterChartview(this, allList);
	        listCategorys.setAdapter(adapter);
		 
		 
	        if (tools.isConnectingToInternet()) {
	        	loadDat(duration);
			}else {
				tools.showToastMessage("No Internet Connection !");
			}
	        
		
		 

		
			/*Resources resources = getResources();
		
			PieSlice slice = new PieSlice();
	        
	        slice = new PieSlice();
	        slice.setColor(resources.getColor(R.color.orange));
	        slice.setValue(2000);
	        pg.addSlice(slice);
	        slice = new PieSlice();
	        slice.setColor(resources.getColor(R.color.yellow));
	        slice.setValue(5000);
	        pg.addSlice(slice);*/
	        
	        
	      
		
	}
	
	private void loadDat(int duration2) {
		// TODO Auto-generated method stub
		switch (duration) {
		case 101:
			loadDetils(selectedYear, "", type);
			break;

		case 102:
			loadDetils(tempList.get(1), tempList.get(0), type);
			break;
		case 103:
			String endYear = tempList.get(0)+"-"+"12"+"-"+"31";
			loadDetils(selectedYear, endYear, type);
			break;

		default:
			break;
		}
	}

	private List<String> seperateDate(String date){
		List<String> elephantList = Arrays.asList(date.split("-"));
		return elephantList;
	}
	
	
	private String getMonthAsString(int i){
        return months[i-1];
    }


	
	
	private void loadDetils(String startYear, String endYear, String type){
		
		final ProgressDialog pd = new ProgressDialog(this);
		pd.setMessage("Loading");
		pd.setCancelable(false);
		
		RequestParams params = new RequestParams();
		params.put("user_id", BaseActivity.getpreference(Const.TAG_USERID, this));
		params.put("type_id", type);
		
		if (duration == 103) { // yearly
			params.put("user_action", "1014");
			params.put("start_date", startYear);
			params.put("end_date", endYear);
		}else if (duration == 102) { // monthly
			params.put("user_action", "1016");
			params.put("month", startYear);
			params.put("year", endYear);
		}else if (duration == 101) {
			params.put("user_action", "1013");
			params.put("action_date", startYear);
		}
			
			
			
		
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
                tools.showToastMessage(getResources().getString(R.string.connectivity));
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
		        
		        
		        setDatas();
		        
		        adapter.notifyDataSetChanged();
		        
		        setChartView();
		        
		       
				
			}
		});
	}
	
	
	private void setDatas(){
		
		int totalMoney = 0;
		if (allList.size() > 0) {
			centreCircle.setVisibility(View.VISIBLE);
			
			if (duration == 101) { // daily
				
				String text = "<font color=#CC000000>"+tempList.get(2)+" "+"</font> <font color=#727272>"+getMonthAsString(Integer.parseInt(tempList.get(1)))+" "+"</font> <font color=#CC000000>"+tempList.get(0)+"</font>";
				currentDate.setText(Html.fromHtml(text));

				
				
//				currentDate.setText(tempList.get(2)+" "+getMonthAsString(Integer.parseInt(tempList.get(1)))+" "+tempList.get(0));
				
			}else if (duration == 102) { // monthly
				String text = "</font> <font color=#727272>"+getMonthAsString(Integer.parseInt(tempList.get(1)))+" "+"</font> <font color=#CC000000>"+tempList.get(0)+"</font>";

				currentDate.setText(Html.fromHtml(text));
				
//				currentDate.setText(getMonthAsString(Integer.parseInt(tempList.get(1)))+" "+tempList.get(0));
				
			}else if(duration == 103){ // yearly
				
				String text = "</font> <font color=#CC000000>"+tempList.get(0)+"</font>";

				
				currentDate.setText(Html.fromHtml(text));
			}
			
			
			symbol.setText(tools.getCurrency());
			
			for (int i = 0; i < allList.size(); i++) {
				totalMoney = totalMoney + Integer.parseInt(allList.get(i).getAmount());
			}
			
			money.setText(totalMoney+"");
			
			if (type.contentEquals( Const.TAG_INCOME_ID)) {
				pichartType.setText("INCOME");
			}else if (type.contentEquals( Const.TAG_EXPENSE_ID)) {
				pichartType.setText("EXPENSE");
			}
			
		}
		
		
		
		
		
	}

	private void setChartView(){
		PieSlice slice;
		for (int i = 0; i < allList.size(); i++) {
			
			slice = new PieSlice();
			slice.setColor(allList.get(i).getColor());
//	        slice.setSelectedColor(getResources().getColor(R.color.red));
	        slice.setValue(Integer.parseInt(allList.get(i).getAmount()));
//	        slice.setTitle("first");
	        pg.addSlice(slice);
		}
	
	}
	

}
