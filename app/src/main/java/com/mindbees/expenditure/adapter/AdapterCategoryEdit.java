package com.mindbees.expenditure.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mindbees.expenditure.Interfaces.DeleteItem;
import com.mindbees.expenditure.R;
import com.mindbees.expenditure.app.Expenditure;
import com.mindbees.expenditure.model.Category;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by User on 15-02-2017.
 */

public class AdapterCategoryEdit extends BaseAdapter {
    Context context;
    public ArrayList<Category> allList;
    DisplayImageOptions options;
    Bitmap image;
    DeleteItem mCallBack;
    private int[] PHOTO_TEXT_BACKGROUND_COLORS;
    public AdapterCategoryEdit (Context context,ArrayList<Category>allList)
    {
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
        return allList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final AdapterCategory.ViewHolder holder;
        return null;
    }
}
