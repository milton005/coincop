package com.mindbees.expenditure.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.mindbees.expenditure.Interfaces.DeleteItem;
import com.mindbees.expenditure.Interfaces.itemclick;
import com.mindbees.expenditure.R;


/**
 * Created by User on 06-02-2017.
 */

public class AdapterTutorial extends PagerAdapter {
    private Context context;
    private LayoutInflater mLayoutInflater;
    private TypedArray typedArray;
    itemclick mCallback;
    public AdapterTutorial(Context context, TypedArray typedArray) {
        this.context = context;
        this.typedArray = typedArray;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return typedArray.length();
    }
    public void setCallback(Activity mCallback){
        this.mCallback = (itemclick) mCallback;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container,  int position) {
        View itemView = mLayoutInflater.inflate(R.layout.inflate_tutorial, container, false);


        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        ImageView im_close1= (ImageView) itemView.findViewById(R.id.image_close_tut1);
        ImageView im_close2= (ImageView) itemView.findViewById(R.id.image_close_tut2);
        if (position==1||position==2)
        {
            im_close2.setVisibility(View.VISIBLE);
        }
        else {
            im_close2.setVisibility(View.GONE);
        }
        if (position==0)
        {
            im_close1.setVisibility(View.VISIBLE);

        }
        else {
            im_close1.setVisibility(View.GONE);
        }
        im_close1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.OnItemClick(0);
            }
        });

        im_close2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.OnItemClick(0);
            }
        });
//        imageView.setImageDrawable(typedArray.getDrawable(position));
        Glide.with(context)
                .load(typedArray.getResourceId(position, 0))
                .into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}