/**
 * 
 */
package com.mindbees.expenditure.adapter;


import java.io.IOException;
import java.util.ArrayList;

import com.mindbees.expenditure.ActivityCredit_Debit;
import com.mindbees.expenditure.R;
import com.mindbees.expenditure.Interfaces.CategoryItem;
import com.mindbees.expenditure.app.Expenditure;
import com.mindbees.expenditure.model.Category;
import com.mindbees.expenditure.model.creditdebit.loadcategory.Account;
import com.mindbees.expenditure.ui.CircularContactView;
import com.mindbees.expenditure.util.Tools;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author tony
 * Jan 9, 2015
 * 4:16:45 PM
 */
public class AdapterViewPager extends PagerAdapter{
	
	ActivityCredit_Debit context;
	LayoutInflater inflater;
	public ArrayList<Category> list;
	DisplayImageOptions options;
	CategoryItem mCallback;
	private int[] PHOTO_TEXT_BACKGROUND_COLORS;
	int backgroundColorToUse;
//	int imagePosition = -1;
	
	Tools tools;

	public AdapterViewPager(ActivityCredit_Debit context, ArrayList<Category> list) {
		// TODO Auto-generated constructor stub
		
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.list = list;
		
		tools = new Tools(context);
		
		options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher).build();
        Expenditure.imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        PHOTO_TEXT_BACKGROUND_COLORS = context.getResources().getIntArray(R.array.contacts_text_background_colors);
		
	}
	
	public void setCallBack(Fragment context){
		mCallback = (CategoryItem) context;
	}
	public void setCallBack(Activity context){
		mCallback = (CategoryItem) context;
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
		
		View imageLayout = inflater.inflate(R.layout.inflate_category_report_two, container,
				false);

		final ViewHolder view_holdr = new ViewHolder();
		
			view_holdr.img_11 = (CircularContactView) imageLayout.findViewById(R.id.img_vp_1);

			view_holdr.img_22 = (CircularContactView) imageLayout.findViewById(R.id.img_vp_2);

			view_holdr.img_33 = (CircularContactView) imageLayout.findViewById(R.id.img_vp_3);

		
		view_holdr.img_1Shaded = (ImageView) imageLayout.findViewById(R.id.categryIconShaded);
		view_holdr.img_2Shaded = (ImageView) imageLayout.findViewById(R.id.categryIconShaded1);
		view_holdr.img_3Shaded = (ImageView) imageLayout.findViewById(R.id.categryIconShaded2);
		
		view_holdr.text1 = (TextView) imageLayout.findViewById(R.id.categoryName);
		view_holdr.text2 = (TextView) imageLayout.findViewById(R.id.categoryName1);
		view_holdr.text3 = (TextView) imageLayout.findViewById(R.id.categoryName2);

		final int p = 3 * position;
		
		

		try {
			
			
			if (p < list.size()) {
				
				
				view_holdr.text1.setText(list.get(p).getCatTitlle());
				if (Integer.parseInt(list.get(p).getCat_color()) == 0) {
					
					Expenditure.imageLoader.loadImage("http://coincop.mindmockups.com/uploads/categories/"+list.get(p).getCat_image(), options, new SimpleImageLoadingListener(){
						@Override
						public void onLoadingComplete(String imageUri, View view,
								Bitmap loadedImage) {
							// TODO Auto-generated method stub
							view_holdr.img_11.setImageBitmap(loadedImage);
						}
					});
					
				}else {
					backgroundColorToUse = PHOTO_TEXT_BACKGROUND_COLORS[p % PHOTO_TEXT_BACKGROUND_COLORS.length];
					view_holdr.img_11.setImageResource(R.drawable.category_new_icon, backgroundColorToUse);

				}
				
				if (list.get(p).isSelected()) {
					view_holdr.img_1Shaded.setVisibility(View.VISIBLE);
					view_holdr.img_2Shaded.setVisibility(View.GONE);
					view_holdr.img_3Shaded.setVisibility(View.GONE);
				}
			}
			
			
			if (p+1 < list.size()) {
				
				view_holdr.text2.setText(list.get(p+1).getCatTitlle());
				if (Integer.parseInt(list.get(p+1).getCat_color()) == 0) {
//					Picasso.with(context).load("http://coincop.mindmockups.com/uploads/categories/"+list.get(p+1).getCatImage()).into((Target) view_holdr.img_22);
					Expenditure.imageLoader.loadImage("http://coincop.mindmockups.com/uploads/categories/"+list.get(p+1).getCat_image(), options, new SimpleImageLoadingListener(){
						@Override
						public void onLoadingComplete(String imageUri, View view,
								Bitmap loadedImage) {
							// TODO Auto-generated method stub
							view_holdr.img_22.setImageBitmap(loadedImage);
						}
					});
					
				}else {
					backgroundColorToUse = PHOTO_TEXT_BACKGROUND_COLORS[(p+1) % PHOTO_TEXT_BACKGROUND_COLORS.length];
					view_holdr.img_22.setImageResource(R.drawable.category_new_icon, backgroundColorToUse);

				}
				
				if (list.get(p+1).isSelected()) {
					view_holdr.img_2Shaded.setVisibility(View.VISIBLE);
					view_holdr.img_1Shaded.setVisibility(View.GONE);
					view_holdr.img_3Shaded.setVisibility(View.GONE);
				}
			}
			if (p+2 < list.size()) {
				
				view_holdr.text3.setText(list.get(p+2).getCatTitlle());
				
				if (Integer.parseInt(list.get(p+2).getCat_color()) == 0) {
					
					Expenditure.imageLoader.loadImage("http://coincop.mindmockups.com/uploads/categories/"+list.get(p+2).getCat_image(), options, new SimpleImageLoadingListener(){
						@Override
						public void onLoadingComplete(String imageUri, View view,
								Bitmap loadedImage) {
							// TODO Auto-generated method stub
							view_holdr.img_33.setImageBitmap(loadedImage);
						}
					});
					
				}else {
					backgroundColorToUse = PHOTO_TEXT_BACKGROUND_COLORS[(p+2) % PHOTO_TEXT_BACKGROUND_COLORS.length];
					view_holdr.img_33.setImageResource(R.drawable.category_new_icon, backgroundColorToUse);

				}
				
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
		
		
		
		view_holdr.img_11.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (context.imagePosition != -1) {
					list.get(context.imagePosition).setSelected(false);
				}
				
				mCallback.onCategoryItemClicked(list.get(p).getCatId(), p);
				list.get(p).setSelected(true);
				notifyDataSetChanged();
				
				context.imagePosition = p;
				
				

			}
		});
		view_holdr.img_22.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (p+1 < list.size()) {
					
					if (context.imagePosition != -1) {
						list.get(context.imagePosition).setSelected(false);
					}
					
					mCallback.onCategoryItemClicked(list.get(p+1).getCatId(), p+1);
					list.get(p+1).setSelected(true);
					notifyDataSetChanged();
					
					context.imagePosition = p+1;
				}
				
				
				

			}
		});
		view_holdr.img_33.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (p+2 < list.size()) {
					if (context.imagePosition != -1) {
						list.get(context.imagePosition).setSelected(false);
					}
					
					
					mCallback.onCategoryItemClicked(list.get(p+2).getCatId(), p+2);
					list.get(p+2).setSelected(true);
					notifyDataSetChanged();

					context.imagePosition = p+2;
				}
				
				
				
			}
		});

		return imageLayout;

	}
	
	
	
	class ViewHolder {
//		ImageView img_1, img_2, img_3;
		CircularContactView img_11, img_22, img_33;
		ImageView img_1Shaded, img_2Shaded, img_3Shaded;
		TextView text1, text2, text3;
	}

	
}
