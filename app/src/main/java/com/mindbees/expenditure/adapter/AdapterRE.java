package com.mindbees.expenditure.adapter;

import java.util.ArrayList;

import com.mindbees.expenditure.R;
import com.mindbees.expenditure.Interfaces.RecyclerItemClicked;
import com.mindbees.expenditure.app.Expenditure;
import com.mindbees.expenditure.model.Category;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterRE extends RecyclerView.Adapter<AdapterRE.CategoryViewHolder>{

	
	Context context;
	ArrayList<Category> allList;
	RecyclerItemClicked mCallback;
	DisplayImageOptions options;
	
	 public static class CategoryViewHolder extends RecyclerView.ViewHolder{

	        TextView tvtitle;
	        ImageView imgShop;
//	        View divider;

	        public CategoryViewHolder(View itemView) {
	            super(itemView);

	            tvtitle = (TextView) itemView.findViewById(R.id.categoryName);
	            imgShop = (ImageView) itemView.findViewById(R.id.categryIcon);
//	            divider = itemView.findViewById(R.id.divider);

	        }
	    }
	 
	 

	public AdapterRE(Context context, ArrayList<Category> allList) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.allList = allList;
		options = new DisplayImageOptions.Builder().cacheOnDisk(true).showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher).build();
        Expenditure.imageLoader.init(ImageLoaderConfiguration.createDefault(context));
	}
	
	public void setCallBack(RecyclerItemClicked mCallback){
		this.mCallback = mCallback;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return allList.size();
	}

	@Override
	public void onBindViewHolder(CategoryViewHolder arg0, final int arg1) {
		// TODO Auto-generated method stub
		Expenditure.imageLoader.displayImage(allList.get(arg1).getCat_image(), arg0.imgShop, options);
		arg0.tvtitle.setText(allList.get(arg1).getCatTitlle());
		
		arg0.itemView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mCallback.recyclerOnitemClicked(arg1);
				
			}
		});
	}

	@Override
	public CategoryViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		View itemView = LayoutInflater.from(arg0.getContext()).inflate(
				R.layout.inflate_category, arg0, false);

		return new CategoryViewHolder(itemView);
	}
}
