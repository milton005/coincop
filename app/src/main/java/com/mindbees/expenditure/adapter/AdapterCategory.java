package com.mindbees.expenditure.adapter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import com.mindbees.expenditure.Interfaces.DeleteItem;
import com.mindbees.expenditure.R;
import com.mindbees.expenditure.app.Expenditure;
import com.mindbees.expenditure.model.Category;
import com.mindbees.expenditure.ui.CircularContactView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterCategory extends BaseAdapter{
	
	Context context;
	public ArrayList<Category> allList;
	DisplayImageOptions options;
	Bitmap image;
	private int[] PHOTO_TEXT_BACKGROUND_COLORS;

	public AdapterCategory(Context context, ArrayList<Category> allList) {
		super();
		this.context = context;
		this.allList = allList;
		options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher).build();
		if (Expenditure.imageLoader.isInited()) {
			Expenditure.imageLoader.destroy();
		}
		Expenditure.imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		PHOTO_TEXT_BACKGROUND_COLORS = context.getResources().getIntArray(R.array.contacts_text_background_colors);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return allList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
//	public void setCallBack(Fragment context){
//		mCallBack = (DeleteItem) context;
//	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		final ViewHolder holder;
		if (v == null) {
			
//			if (Integer.parseInt(allList.get(position).getCat_color()) == 0) {
//				v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_category, null);
				v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_category_two, null);
				
			/*}else {
				v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_category_two, null);
			}*/
			
			holder = new ViewHolder();
			holder.round= (ImageView) v.findViewById(R.id.Round_red);

			holder.tvtitle = (TextView) v.findViewById(R.id.categoryName);
			
			holder.imgShaded = (ImageView) v.findViewById(R.id.categryIconShaded);
//			if (Integer.parseInt(allList.get(position).getCat_color()) == 0) {
//				holder.imgShop = (ImageView) v.findViewById(R.id.categryIcon);
			holder.imgShopp = (CircularContactView) v.findViewById(R.id.categryIcon);
			/*}else {
				
			}*/
			
			v.setTag(holder);
		}else{
			holder = (ViewHolder) v.getTag();
		}
		
		holder.tvtitle.setText(allList.get(position).getCatTitlle());
		
		if (Integer.parseInt(allList.get(position).getCat_color()) == 0) {


			Expenditure.imageLoader.loadImage("http://coincop.mindmockups.com/uploads/categories/"+allList.get(position).getCat_image(), options, new SimpleImageLoadingListener(){
				@Override
				public void onLoadingComplete(String imageUri, View view,
											  Bitmap loadedImage) {
					// TODO Auto-generated method stub
					holder.imgShopp.setImageBitmap(loadedImage);
					holder.round.setVisibility(View.VISIBLE);
				}
			});
			

		}else {
			final int backgroundColorToUse = PHOTO_TEXT_BACKGROUND_COLORS[position % PHOTO_TEXT_BACKGROUND_COLORS.length];

			holder.imgShopp.setImageResource(R.drawable.category_new_icon, backgroundColorToUse);
//			holder.imgShopp.setImageResource(R.drawable.category_new_icon, Integer.parseInt(allList.get(position).getCat_color()));
			
		}	
		
		
//		if (Integer.parseInt(allList.get(position).getCat_color()) == 0) {
//			Expenditure.imageLoader.displayImage(allList.get(position).getCat_image(), holder.imgShop, options);
	/*	}else {
			holder.imgShopp.setImageResource(R.drawable.category_new_icon, Integer.parseInt(allList.get(position).getCat_color()));
			
		}*/
		
		
		if (allList.get(position).isSelected()) {
			holder.imgShaded.setVisibility(View.VISIBLE);
		}else {
			holder.imgShaded.setVisibility(View.GONE);
		}
		
		
		return v;
	}
	
	class ViewHolder {
		ImageView round;
		TextView tvtitle;
//		ImageView imgShop;
		ImageView imgShaded;
		CircularContactView imgShopp;
		
	}
	

}
