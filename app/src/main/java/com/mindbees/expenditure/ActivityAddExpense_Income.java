package com.mindbees.expenditure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.http.Header;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mindbees.expenditure.fragment.FragmentAddAccountList;
import com.mindbees.expenditure.fragment.FragmentAddAccountCal;
import com.mindbees.expenditure.fragment.FragmentDate;
import com.mindbees.expenditure.fragment.FragmentDescription;
import com.mindbees.expenditure.model.Accounts;
import com.mindbees.expenditure.model.CalendarE;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;
import com.mindbees.expenditure.util.WebService;
import com.mindbees.expenditure.util.XMLParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class ActivityAddExpense_Income extends AppCompatActivity{
	FragmentTabHost mtabHost;
	int whichTab;
	Tools tools;
	public ArrayList<Accounts> allAccounts = new ArrayList<Accounts>();
	public final List<CalendarE> gridList = new ArrayList();
	
	public int listPosition = -1;
	public int gridPosition = -1;
	
	public String bankAccountId = "";
	public String dateAdding = "";
	public String desc = "";
	ImageView imgClose, imgSubmit;
	Bundle bund;
	String typeId, catId, amount;
	public EditText edFromDescription;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categorytab);

		overridePendingTransition(R.anim.activity_open_translate_top_btm,R.anim.activity_close_scale);
		tools = new Tools(this);
		
		
		bund = getIntent().getExtras();
		
		whichTab = bund.getInt("tab");
		typeId = bund.getString("type_id");
		catId = bund.getString("cat_id");
		amount = bund.getString("amnt");

//		tools.showLog(typeId, 0);
//		tools.showLog(catId, 0);
//		tools.showLog(amount, 0);
		
		
		imgClose= (ImageView) findViewById(R.id.imgClose);
		imgSubmit= (ImageView) findViewById(R.id.imgSubmit);
		mtabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mtabHost.setup(this, getSupportFragmentManager(),
				android.R.id.tabcontent);
		

		addViewTab(mtabHost);
		
		mtabHost.setCurrentTab(whichTab);
		setPadding();
		
		
		mtabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				setPadding();
			}
			
			
		});
		
		imgClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
		imgSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				validateAccount();
				
			}
		});
		
	}
	
	
	protected void validateAccount() {
		// TODO Auto-generated method stub
		
		try {
			desc = edFromDescription.getText().toString().trim();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (bankAccountId.isEmpty()) {
			tools.showToastMessage("Select Account");
			
		}else if (catId.isEmpty()) {
			tools.showToastMessage("Select Category");
			
		}else if (amount.isEmpty()) {
			tools.showToastMessage("Select Amount");
			
		}else if (desc.isEmpty()) {
			tools.showToastMessage("Create Description");
			
		}else if (dateAdding.isEmpty()) {
			tools.showToastMessage("Select Date");
		}else {
			submitAccount(bankAccountId, typeId, catId, amount, desc, dateAdding);
		}
		
	}


	private void setPadding() {

		for (int i = 0; i < mtabHost.getTabWidget().getChildCount(); i++) {

			mtabHost.getTabWidget().getChildAt(i).setPadding(4, 0, 4, 0);

		}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate_top_btm);
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
	
	
	
	private void submitAccount(String acnt_id, String type_id, String category_id, String amount, String description, String action_date) {
		// TODO Auto-generated method stub
		
		final ProgressDialog pd = new ProgressDialog(this);
		pd.setMessage("Loading");
		pd.setCancelable(false);
				
		RequestParams params = new RequestParams();
		params.put("user_action", "1012");
		params.put("user_id", BaseActivity.getpreference(Const.TAG_USERID, this));
		params.put("account_id", acnt_id);
		params.put("type_id", type_id);
		params.put("category_id", category_id);
		params.put("amount", amount);
		params.put("description", description);
		params.put("action_date", action_date);
		
		tools.showLog(acnt_id, 1);
		tools.showLog(type_id, 1);
		tools.showLog(category_id, 1);
		tools.showLog(amount, 1);
		tools.showLog(description, 1);
		tools.showLog(action_date, 1);
		
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
				tools.showLog(new String(arg2), 1);
				 if (pd.isShowing()) {
	                    pd.dismiss();
	                }
				
			}
			
			

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// TODO Auto-generated method stub
				
				Tools.debugResponse(new String(arg2));
				tools.showLog(new String(arg2), 1);

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Tools.debugThrowable(arg3);

                if (arg2 != null) {
                    Tools.debugResponse(new String(arg2));
                }
                tools.showToastMessage(getResources().getString(R.string.connectivity));
			}
			
			
		});
		
		
	}
	
	
	private void parse(String string) throws NumberFormatException {
		// TODO Auto-generated method stub
		
    	String message = "";
    	String value = "";
		
		XMLParser parser = new XMLParser();
        try {
            String xml = string;

            Document doc = parser.getDomElement(xml);
            NodeList nl = doc.getElementsByTagName("result");
            for (int i = 0; i < nl.getLength(); i++) {
            	Element e = (Element) nl.item(i);
            	
            	value = parser.getValue(e, "value");
            	message = parser.getValue(e, "message");
            	
            	
				
			}
        }catch(Exception e){
        	e.printStackTrace();
        }
		tools.showToastMessage(message);
		
		try {
			if (Integer.parseInt(value) == 1) {
				finish();
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
