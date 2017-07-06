package com.mindbees.expenditure.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindbees.expenditure.R;
import com.mindbees.expenditure.Interfaces.DeleteItem;
import com.mindbees.expenditure.adapter.AdapterAcntStats.ViewHolder;
import com.mindbees.expenditure.app.Expenditure;
import com.mindbees.expenditure.database.MyDataBase;
import com.mindbees.expenditure.fragment.FragmentAccounts;
import com.mindbees.expenditure.model.Category;
import com.mindbees.expenditure.model.StatusAccount;
import com.mindbees.expenditure.ui.CircularContactView;
import com.mindbees.expenditure.ui.swiperemove.BaseSwipeAdapter;
import com.mindbees.expenditure.ui.swiperemove.SimpleSwipeListener;
import com.mindbees.expenditure.ui.swiperemove.SwipeLayout;
import com.mindbees.expenditure.ui.swiperemove.SwipeLayout.SwipeListener;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class AdapterCategoryRemoveView extends BaseSwipeAdapter{
	
	Context context;
	public ArrayList<Category> allList;
	DisplayImageOptions options;
	Bitmap image;
	DeleteItem mCallBack;
	private int[] PHOTO_TEXT_BACKGROUND_COLORS;
	

	public AdapterCategoryRemoveView(Context context, ArrayList<Category> allList) {
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
	
	public void setCallBack(Fragment context){
		mCallBack = (DeleteItem) context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return allList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getSwipeLayoutResourceId(int position) {
		// TODO Auto-generated method stub
		return R.id.swipe;
	}

	@Override
	public View generateView(final int position, ViewGroup parent) {
		// TODO Auto-generated method stub
		;
		View v  = LayoutInflater.from(context).inflate(R.layout.inflate_remove_category, null);
	        SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
	        
	       /* swipeLayout.addSwipeListener(new SimpleSwipeListener() {
	            @Override
	            public void onOpen(SwipeLayout layout) {
	                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
	            }
	        });*/
	        /*swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
	            @Override
	            public void onDoubleClick(SwipeLayout layout, boolean surface) {
	                Toast.makeText(context, "DoubleClick", Toast.LENGTH_SHORT).show();
	            }
	        });*/
	        
//
	        
//	        swipeLayout.addSwipeListener(new SwipeListener() {
//
//				@Override
//				public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
//					// TODO Auto-generated method stub
//
//				}
//
//				@Override
//				public void onStartOpen(SwipeLayout layout) {
//					// TODO Auto-generated method stub
//					if (Integer.parseInt(allList.get(position).getCat_color()) == 0) {
//						layout.close();
//						Toast.makeText(context, "Can't delete default Categorys", Toast.LENGTH_SHORT).show();
//					}
//
//
//				}
//
//				@Override
//				public void onStartClose(SwipeLayout layout) {
//					// TODO Auto-generated method stub
//
//				}
//
//				@Override
//				public void onOpen(SwipeLayout layout) {
//					// TODO Auto-generated method stub
//					/*if (Integer.parseInt(allList.get(position).getCat_color()) == 0) {
//						layout.close();
//						Toast.makeText(context, "Can't delete default Categorys", Toast.LENGTH_SHORT).show();
//					}*/
//				}
//
//				@Override
//				public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
//					// TODO Auto-generated method stub
//					if (Integer.parseInt(allList.get(position).getCat_color()) == 0) {
//						layout.close();
//					}
//				}
//
//				@Override
//				public void onClose(SwipeLayout layout) {
//					// TODO Auto-generated method stub
//
//				}
//			});
	        
	        
	        v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
//	                Toast.makeText(context, "click delete", Toast.LENGTH_SHORT).show();
	            	
	                mCallBack.onDeleteItem(position);
	                
	            }
	        });
		
        return v;
	}

	@Override
	public void fillValues(int position, View v) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
			holder = new ViewHolder();
			
			
			holder.tvtitle = (TextView) v.findViewById(R.id.categoryName);
			
			holder.imgShaded = (ImageView) v.findViewById(R.id.categryIconShaded);
//			if (Integer.parseInt(allList.get(position).getCat_color()) == 0) {
//				holder.imgShop = (ImageView) v.findViewById(R.id.categryIcon);
			holder.imgShopp = (CircularContactView) v.findViewById(R.id.categryIcon);
			/*}else {
				
			}*/
			
			 Log.e("coincop", allList.get(position).getCat_color());
			
			v.setTag(holder);
			
			
			holder.tvtitle.setText(allList.get(position).getCatTitlle());
			
			if (Integer.parseInt(allList.get(position).getCat_color()) == 0) {
//
					Expenditure.imageLoader.loadImage("http://coincop.mindmockups.com/uploads/categories/"+allList.get(position).getCat_image(), options, new SimpleImageLoadingListener(){
						@Override
						public void onLoadingComplete(String imageUri, View view,
													  Bitmap loadedImage) {
							// TODO Auto-generated method stub
							holder.imgShopp.setImageBitmap(loadedImage);
						}
					});

//				Expenditure.imageLoader.loadImage(allList.get(position).getCat_image(), options, new SimpleImageLoadingListener(){
//					@Override
//					public void onLoadingComplete(String imageUri, View view,
//							Bitmap loadedImage) {
//						// TODO Auto-generated method stub
//						holder.imgShopp.setImageBitmap(loadedImage);
//					}
//				});
				

			}else {
				 final int backgroundColorToUse = PHOTO_TEXT_BACKGROUND_COLORS[position % PHOTO_TEXT_BACKGROUND_COLORS.length];

				holder.imgShopp.setImageResource(R.drawable.category_new_icon, backgroundColorToUse);
				
			}	
			
			
//			if (Integer.parseInt(allList.get(position).getCat_color()) == 0) {
//				Expenditure.imageLoader.displayImage(allList.get(position).getCat_image(), holder.imgShop, options);
		/*	}else {
				holder.imgShopp.setImageResource(R.drawable.category_new_icon, Integer.parseInt(allList.get(position).getCat_color()));
				
			}*/
			
			
			if (allList.get(position).isSelected()) {
				
				holder.imgShaded.setVisibility(View.VISIBLE);
				
			}else {
				
				holder.imgShaded.setVisibility(View.GONE);
				
			}
		
		
	}
	
	class ViewHolder {
		TextView tvtitle;
//		ImageView imgShop;
		ImageView imgShaded;
		CircularContactView imgShopp;
		
	}

}
