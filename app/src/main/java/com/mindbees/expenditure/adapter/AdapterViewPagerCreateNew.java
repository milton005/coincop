/**
 * 
 */
package com.mindbees.expenditure.adapter;


import java.util.ArrayList;

import com.mindbees.expenditure.R;
import com.mindbees.expenditure.Interfaces.CategoryColorSelect;
import com.mindbees.expenditure.model.CategoryColor;
import com.mindbees.expenditure.ui.CircularContactView;
import com.mindbees.expenditure.util.Tools;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * @author tony
 * Jan 9, 2015
 * 4:16:45 PM
 */
public class AdapterViewPagerCreateNew extends PagerAdapter{
	
	Context context;
	LayoutInflater inflater;
	ArrayList<CategoryColor> list;
//	DisplayImageOptions options;
	CategoryColorSelect mCallback;
	int imagePosition = -1;
	
	Tools tools;

	public AdapterViewPagerCreateNew(Context context, ArrayList<CategoryColor> list) {
		// TODO Auto-generated constructor stub
		
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.list = list;
		
		tools = new Tools(context);
		
//		options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).showImageOnLoading(R.drawable.ic_launcher)
//                .showImageForEmptyUri(R.drawable.ic_launcher).build();
//        Expenditure.imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		
	}
	
	public void setCallBack(Activity context){
		mCallback = (CategoryColorSelect) context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return (((list.size()%3)==0)?(list.size()/3):(list.size()/3)+1);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0.equals(arg1);
	}
	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return POSITION_NONE;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		View imageLayout = inflater.inflate(R.layout.inflate_category_create_new, container,
				false);

		ViewHolder view_holdr = new ViewHolder();

		view_holdr.img_1 = (CircularContactView) imageLayout.findViewById(R.id.img_vp_1);

		view_holdr.img_2 = (CircularContactView) imageLayout.findViewById(R.id.img_vp_2);

		view_holdr.img_3 = (CircularContactView) imageLayout.findViewById(R.id.img_vp_3);
		
		view_holdr.img_1Shaded = (ImageView) imageLayout.findViewById(R.id.categryIconShaded);
		view_holdr.img_2Shaded = (ImageView) imageLayout.findViewById(R.id.categryIconShaded1);
		view_holdr.img_3Shaded = (ImageView) imageLayout.findViewById(R.id.categryIconShaded2);
		
//		view_holdr.text1 = (TextView) imageLayout.findViewById(R.id.categoryName);
//		view_holdr.text2 = (TextView) imageLayout.findViewById(R.id.categoryName1);
//		view_holdr.text3 = (TextView) imageLayout.findViewById(R.id.categoryName2);

		final int p = 3 * position;

		try {

//			view_holdr.text1.setText(list.get(p).getCatTitlle());
//			view_holdr.text2.setText(list.get(p+1).getCatTitlle());
//			view_holdr.text3.setText(list.get(p+2).getCatTitlle());
			
			if (p < list.size()) {
				view_holdr.img_1.setTextAndBackgroundColor("", list.get(p).getColor(), true);
				if (list.get(p).isSelected()) {
					view_holdr.img_1Shaded.setVisibility(View.VISIBLE);
					view_holdr.img_2Shaded.setVisibility(View.GONE);
					view_holdr.img_3Shaded.setVisibility(View.GONE);
				}
			}
			
			
			if (p+1 < list.size()) {
				view_holdr.img_2.setTextAndBackgroundColor("", list.get(p+1).getColor(), true);
				
				if (list.get(p+1).isSelected()) {
					view_holdr.img_2Shaded.setVisibility(View.VISIBLE);
					view_holdr.img_1Shaded.setVisibility(View.GONE);
					view_holdr.img_3Shaded.setVisibility(View.GONE);
				}
			}
			if (p+2 < list.size()) {
				view_holdr.img_3.setTextAndBackgroundColor("", list.get(p+2).getColor(), true);
				
				if (list.get(p+2).isSelected()) {
					view_holdr.img_3Shaded.setVisibility(View.VISIBLE);
					view_holdr.img_1Shaded.setVisibility(View.GONE);
					view_holdr.img_2Shaded.setVisibility(View.GONE);
				}
			}
			
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		container.addView(imageLayout, 0);
		
		
		
		view_holdr.img_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (imagePosition != -1) {
					list.get(imagePosition).setSelected(false);
				}
				
				mCallback.onColorSelected(list.get(p).getColor(), p);
				list.get(p).setSelected(true);
				notifyDataSetChanged();
				
				imagePosition = p;
				
				tools.showLog(imagePosition+"", 2);
				

			}
		});
		view_holdr.img_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (p+1 < list.size()) {
					if (imagePosition != -1) {
						list.get(imagePosition).setSelected(false);
					}
					
					mCallback.onColorSelected(list.get(p+1).getColor(), p+1);
					list.get(p+1).setSelected(true);
					notifyDataSetChanged();
					
					imagePosition = p+1;
					
					tools.showLog(list.get(p+1).getColor()+"", 2);
					tools.showLog(imagePosition+"", 2);
				}
				
				
				

			}
		});
		view_holdr.img_3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (p+2 < list.size()) {
					if (imagePosition != -1) {
						list.get(imagePosition).setSelected(false);
					}
					
					
					
					mCallback.onColorSelected(list.get(p+2).getColor(), p+2);
					list.get(p+2).setSelected(true);
					notifyDataSetChanged();

					imagePosition = p+2;
					tools.showLog(imagePosition+"", 2);
				}
				
				
				
			}
		});

		return imageLayout;

	}
	
	
	
	
	class ViewHolder {
		CircularContactView img_1, img_2, img_3;
		ImageView img_1Shaded, img_2Shaded, img_3Shaded;
//		TextView text1, text2, text3;
	}

	
}
