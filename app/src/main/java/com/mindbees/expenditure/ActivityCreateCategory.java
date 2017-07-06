package com.mindbees.expenditure;

import java.util.ArrayList;

import org.apache.http.Header;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mindbees.expenditure.Interfaces.CategoryColorSelect;
import com.mindbees.expenditure.adapter.AdapterViewPagerCreateNew;
import com.mindbees.expenditure.model.CategoryColor;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;
import com.mindbees.expenditure.util.WebService;
import com.mindbees.expenditure.util.XMLParser;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

public class ActivityCreateCategory extends AppCompatActivity implements OnClickListener, CategoryColorSelect{
	
	ViewPager view_pager_avatar;
	ImageView imLft;
	ImageView imRght;
	ImageView imgOK;
	EditText textCategoryName;
	
	public int pagerPosition = 0;
	
	AdapterViewPagerCreateNew adapterViewPager;
	private int[] PHOTO_TEXT_BACKGROUND_COLORS;
	ArrayList<CategoryColor> colorList;
	
//	public int gridPositionMonths = -1;
	
	private int colorCode = 0;
	
	ImageView closeBtn, submitBtn;
	boolean isIncome;
	Tools tools;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_category);
		
		if (getIntent().hasExtra("income")) {
			isIncome = getIntent().getBooleanExtra("income", false);
		}
		
		tools = new Tools(this);
		
		view_pager_avatar = (ViewPager) findViewById(R.id.pager_avatar);
		imLft = (ImageView) findViewById(R.id.imgLeft);
		imRght = (ImageView) findViewById(R.id.imgRight);
		
		closeBtn = (ImageView) findViewById(R.id.closeBtn);
		submitBtn = (ImageView) findViewById(R.id.imgOK);
		
		textCategoryName = (EditText) findViewById(R.id.textCategoryName);
		
		imLft.setOnClickListener(this);
		imRght.setOnClickListener(this);
		closeBtn.setOnClickListener(this);
		submitBtn.setOnClickListener(this);
		imLft.setAlpha(0.5f);
		imLft.setEnabled(false);
		
		insertColor();
		
		
		
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

	private void insertColor() {
		// TODO Auto-generated method stub
		
		PHOTO_TEXT_BACKGROUND_COLORS = getResources().getIntArray(R.array.category_bg_color);
		colorList = new ArrayList<CategoryColor>();
		
		for (int i = 0; i < PHOTO_TEXT_BACKGROUND_COLORS.length; i++) {
			
			CategoryColor catColor = new CategoryColor();
			catColor.setColor(PHOTO_TEXT_BACKGROUND_COLORS[i]);
			catColor.setSelected(false);
			colorList.add(catColor);
		}
		
		adapterViewPager = new AdapterViewPagerCreateNew(this, colorList);
    	adapterViewPager.setCallBack(this);
    	view_pager_avatar.setAdapter(adapterViewPager);
		
		
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
	        
	        if (v == closeBtn){
	        	onBackPressed();
	        }
	        if (v == submitBtn){
	        	if (tools.isConnectingToInternet()) {
	        		validateCheck();
				}else {
					tools.showToastMessage(getResources().getString(R.string.connectivity));
				}
	        	
	        	
	        }
		
	}

	private void validateCheck() {
		// TODO Auto-generated method stub
		
		String categoryTitle = textCategoryName.getText().toString();
		
		textCategoryName.setError(null);

		if (categoryTitle.isEmpty()) {
			textCategoryName.setError("Enter Category Name");
		}else if (colorCode == 0) {
			tools.showToastMessage("Please Select Category Color");
		}else {
			insertCategoryColor(categoryTitle, String.valueOf(colorCode));
		}
		
		
	}

	private void insertCategoryColor(String catTitle, String catColor) {
		// TODO Auto-generated method stub
		
		
		final ProgressDialog pd = new ProgressDialog(this);
		pd.setMessage("Loading");
		pd.setCancelable(false);

		RequestParams params = new RequestParams();
		params.put("user_action", "1009");
		params.put("user_id", BaseActivity.getpreference(Const.TAG_USERID, this));
		params.put("category_title", catTitle);
		params.put("cat_image", "category_new_icon.png");
		params.put("cat_color", catColor);
		
		if (isIncome) {
			params.put("type_id", Const.TAG_INCOME_ID);
		}else {
			params.put("type_id", Const.TAG_EXPENSE_ID);
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

				String value = "";
				String message = "";
				
				XMLParser parser = new XMLParser();
				try {
					String xml = string;

					Document doc = parser.getDomElement(xml);
					NodeList nl = doc.getElementsByTagName("result");
					for (int i = 0; i < nl.getLength(); i++) {
						Element e = (Element) nl.item(i);

						value = parser.getValue(e, "value");
						message = parser.getValue(e, "message");
						String game_id = parser.getValue(e,
								"game_id");

					}
				} catch (Exception e) {
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
		});
		
		
		
	}

	@Override
	public void onColorSelected(int color, int position) {
		// TODO Auto-generated method stub
		
		colorCode = color;
		
	}

}
