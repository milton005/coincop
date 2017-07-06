/*package com.mindbees.expenditure.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.Header;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mindbees.expenditure.ActivityChartView;
import com.mindbees.expenditure.ActivityReport;
import com.mindbees.expenditure.ActivityReportCategoryView;
import com.mindbees.expenditure.R;
import com.mindbees.expenditure.Interfaces.CategoryItem;
import com.mindbees.expenditure.adapter.AdapterMonths;
import com.mindbees.expenditure.adapter.AdapterViewPager;
import com.mindbees.expenditure.model.Category;
import com.mindbees.expenditure.model.Months;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;
import com.mindbees.expenditure.util.WebService;
import com.mindbees.expenditure.util.XMLParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentReportYearly extends Fragment implements OnClickListener, CategoryItem{
	
	private TextView currentYear;

	Tools tools;
	ActivityReport activity;

	public List<Months> gridList;
	GridView calendarWeek;

	private Calendar _calendar;
	private int year;
	AdapterMonths adapter;
	
	Bundle bund;
    boolean isCategory;
    ImageView imgOK;
    String selectedYear;
	
    
    
     *  category section
     
    
    ViewPager view_pager_avatar;
	ImageView imLft;
	ImageView imRght;
	RelativeLayout viewPagerView;
	public int pagerPosition = 0;
	public ArrayList<Category> categoryImgList;
	AdapterViewPager adapterViewPager;
	
	String categoryID = "";
	
    
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity = (ActivityReport) activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		bund = activity.getIntent().getExtras();
		isCategory = bund.getBoolean("isCategory");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_report_yearly, null);

		tools = new Tools(getActivity());
		calendarWeek = (GridView) v.findViewById(R.id.calendar_week);
		currentYear = (TextView) v.findViewById(R.id.curntMonth);
		
		
		view_pager_avatar = (ViewPager) v.findViewById(R.id.pager_avatar);
		imLft = (ImageView) v.findViewById(R.id.imgLeft);
		imRght = (ImageView) v.findViewById(R.id.imgRight);
		viewPagerView = (RelativeLayout) v.findViewById(R.id.viewPagerview);
		imgOK = (ImageView) v.findViewById(R.id.imgOK);
		imLft.setOnClickListener(this);
		imRght.setOnClickListener(this);
		imgOK.setOnClickListener(this);
		
		imLft.setAlpha(0.5f);
		imLft.setEnabled(false);

		_calendar = Calendar.getInstance(Locale.getDefault());

		year = _calendar.get(Calendar.YEAR);

		gridList = new ArrayList<Months>();

		for (int i = 2015; i <= 2026 ; i++) {

			Months mon = new Months();
			mon.setMonthName(i+"");
			if (i == year) {
				selectedYear = String.valueOf(i)+"-"+"01"+"-"+"01";
				activity.gridPosition = i - 2015;
				mon.setChecked(true);
			} else {
				mon.setChecked(false);
			}

			gridList.add(mon);
		}
		
//		currentYear.setText(year+"");
		
		adapter = new AdapterMonths(getActivity(), gridList);
		calendarWeek.setAdapter(adapter);
		
		
		if (isCategory) {
        	
        	categoryImgList = new ArrayList<Category>();
        	
        	viewPagerView.setVisibility(View.VISIBLE);
        	adapterViewPager = new AdapterViewPager(getActivity(),  activity.categoryImgList);
        	adapterViewPager.setCallBack(this);
        	view_pager_avatar.setAdapter(adapterViewPager);
        	
        	if (activity.categoryImgList.size() == 0) {
        		if (tools.isConnectingToInternet()) {
	        		loadCategorys(BaseActivity.getpreference(Const.TAG_USERID, activity));
				}else {
					tools.showToastMessage("Network Error");
				}
			}
        	
		}

		return v;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
		if (activity.gridPositionMonths != -1) {
			activity.categoryImgList.get(activity.gridPositionMonths).setSelected(false);
			activity.gridPositionMonths = -1;
			adapterViewPager.notifyDataSetChanged();
		}
		super.onResume();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		
		calendarWeek.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				if (activity.gridPosition != -1) {
					adapter.allList.get(activity.gridPosition).setChecked(false);
				}
				selectedYear = adapter.allList.get(position).getMonthName()+"-"+"01"+"-"+"01";
				
				activity.gridPosition = position;
				adapter.allList.get(position).setChecked(true);
				adapter.notifyDataSetChanged();
				
			}
		});
		
		
		view_pager_avatar.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub

				pagerPosition = arg0;
				if (pagerPosition > 0) {
					imLft.setAlpha(1f);
					imLft.setEnabled(true);
				} else {
					imLft.setAlpha(0.5f);
					imLft.setEnabled(false);
				}

				if (pagerPosition < 2) {
					imRght.setAlpha(1f);
					imRght.setEnabled(true);
				} else {
					imRght.setAlpha(0.5f);
					imRght.setEnabled(false);
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
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if (v == imLft){
        	if (pagerPosition >= 0) {
        		view_pager_avatar.setCurrentItem(pagerPosition - 1);
        	}
        	
        }
        if (v == imRght){
        	if (pagerPosition <= 2) {
        		view_pager_avatar.setCurrentItem(pagerPosition + 1);
        	}
        	
        }
        if (v == imgOK) {
        	if (isCategory) {
        		if (categoryID != "") {
        			Intent i = new Intent(getActivity(), ActivityReportCategoryView.class);
            		bund.putString("catID", categoryID);
    	        	bund.putInt("duration", 103);
    	        	bund.putString("year", selectedYear);
    	        	i.putExtras(bund);
    				startActivity(i);
				}else {
					tools.showToastMessage("Select Category");
				}
        		
				
			}else {
				Intent i = new Intent(getActivity(), ActivityChartView.class);
	        	bund.putInt("duration", 103);
	        	bund.putString("year", selectedYear);
	        	i.putExtras(bund);
				startActivity(i);
			}
        	
		}
		
	}
	
	
	
private void loadCategorys(String userid){
		
		final ProgressDialog pd = new ProgressDialog(getActivity());
		pd.setMessage("Loading");
		pd.setCancelable(false);
		
		RequestParams params = new RequestParams();
			params.put("user_action", "1010");
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
				
				viewPagerView.setVisibility(View.VISIBLE);
				
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
		            	
		            	String catId = parser.getValue(e, "category_id");
		            	String catTitlle = parser.getValue(e, "category_title");
		            	String type_id = parser.getValue(e, "type_id");
		            	String user_id = parser.getValue(e, "user_id");
		            	String added_date = parser.getValue(e, "added_date");
		            	String cat_image = parser.getValue(e, "cat_image");
		            	String cat_color = parser.getValue(e, "cat_color");
		            	
		            	Category cat = new Category();
		            	cat.setCatId(catId);
		            	cat.setCatTitlle(catTitlle);
		            	cat.setType_id(type_id);
		            	cat.setUser_id(user_id);
		            	cat.setAdded_date(added_date);
		            	cat.setCat_image(cat_image);
		            	cat.setSelected(false);
		            	cat.setCat_color(cat_color);
		            	
		            	categoryImgList.add(cat);
		            	
						
					}
		        }catch(Exception e){
		        	e.printStackTrace();
		        }
		        
//		        adapter = new AdapterCategory(ActivityCategory.this, allCategory);
		        adapterViewPager.notifyDataSetChanged();
//		        mGridView.setAdapter(adapter);
				
			}
		});
	}

	
	

	@Override
	public void onCategoryItemClicked(String categoryId, int position) {
		// TODO Auto-generated method stub
		categoryID = categoryId;
		activity.gridPositionMonths = position;
		
	}

}
*/