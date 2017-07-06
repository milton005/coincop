package com.mindbees.expenditure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mindbees.expenditure.R;

import java.util.List;

/**
 * Created by User on 16-02-2017.
 */

public class AdapterMonth extends BaseAdapter {
    public List<String> list ;
    Context _activity;
    LayoutInflater inflator;
    public AdapterMonth(Context activity,
                       List<String> region_list) {
        // TODO Auto-generated constructor stub
        this.list = region_list;
        this._activity = activity;
        inflator = LayoutInflater.from(_activity);


    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view_1 = convertView;
        AdapterMonth.ViewHolder view_holdr;
        if (convertView == null) {
            view_holdr = new AdapterMonth.ViewHolder();
            view_1 = inflator.inflate(R.layout.inflate_months, null);

            view_holdr.tv_option = (TextView) view_1
                    .findViewById(R.id.textDistrict);
            view_1.setTag(view_holdr);
        } else {

            view_holdr = (AdapterMonth.ViewHolder) view_1.getTag();

        }

        view_holdr.tv_option.setText(list.get(position));
        view_1.setTag(R.id.textDistrict, list.get(position));

        return view_1;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view_1 = convertView;
        AdapterMonth.ViewHolder view_holdr;
        if (view_1 == null) {
            view_holdr = new AdapterMonth.ViewHolder();
            view_1 = inflator.inflate(R.layout.inflate_view_spinner, null);

            view_holdr.tv_option = (TextView) view_1
                    .findViewById(R.id.textDistrict);
            view_1.setTag(view_holdr);
        } else {

            view_holdr = (AdapterMonth.ViewHolder) view_1.getTag();

        }

        view_holdr.tv_option.setText(list.get(position));
        view_1.setTag(R.id.textDistrict, list.get(position));

        return view_1;
    }
    class ViewHolder{
        TextView tv_option;
    }
}
