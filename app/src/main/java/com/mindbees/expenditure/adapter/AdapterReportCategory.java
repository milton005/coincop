package com.mindbees.expenditure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mindbees.expenditure.R;
import com.mindbees.expenditure.model.ReportCategory.Account;
import com.mindbees.expenditure.ui.CircularContactView;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 16-02-2017.
 */

public class AdapterReportCategory extends BaseAdapter {
    boolean isCategory;
    private Context context;
    String symbol;
    private int[] PHOTO_TEXT_BACKGROUND_COLORS;

    ArrayList<Account> allList;
    public AdapterReportCategory(Context context, ArrayList<Account> allList,boolean isCategory)
    {
        super();
        this.context = context;
        this.allList = allList;
        this.isCategory=isCategory;
        PHOTO_TEXT_BACKGROUND_COLORS = context.getResources().getIntArray(R.array.contacts_text_background_colors);
        symbol = BaseActivity.getpreference(Const.TAG_SYMBOL, context);
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
        AdapterReportCategory.ViewHolder holder;
        if (v==null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.inflate_expnd_item, null);

            holder = new AdapterReportCategory.ViewHolder();
            holder.view = (CircularContactView) convertView.findViewById(R.id.dayLetter);
            holder.tvDay = (TextView) convertView.findViewById(R.id.textday);
            holder.tvCat = (TextView) convertView.findViewById(R.id.textCategory);
            holder.tvBnk = (TextView) convertView.findViewById(R.id.textbank);
            holder.amount2 = (TextView) convertView.findViewById(R.id.amount2);
            holder.symbol2 = (TextView) convertView.findViewById(R.id.symbol2);
            convertView.setTag(holder);
        }
        else{
            holder = (AdapterReportCategory.ViewHolder) convertView.getTag();
        }

        final int backgroundColorToUse=PHOTO_TEXT_BACKGROUND_COLORS[position % PHOTO_TEXT_BACKGROUND_COLORS.length];
            if (isCategory) {

               holder.tvBnk.setVisibility(View.GONE);
                holder.tvDay.setText(allList.get(position).getCategoryTitle());
               holder. view.setTextAndBackgroundColor(getFirstLetter(allList.get(position).getCategoryTitle()), backgroundColorToUse, false);

                if (allList.get(position).getTypeId().contentEquals(Const.TAG_EXPENSE_ID)) {
                    holder.tvCat.setText("Expense");
                }else {
                    holder.tvCat.setText("Income");
                }
//			amount2.setText(report.getAmount());
//			symbol2.setText(symbol);

            }
            else {

                holder.tvBnk.setVisibility(View.VISIBLE);
                List<String> temList = seperateDate(allList.get(position).getAddedDate());
                String day = getDayy(allList.get(position).getAddedDate());
               holder.tvDay.setText(allList.get(position).getCategoryTitle());
                holder.view.setTextAndBackgroundColor(getFirstLetter(allList.get(position).getCategoryTitle()), backgroundColorToUse, false);
               holder.tvCat.setText(temList.get(2)+" "+day);
                holder.tvBnk.setText(allList.get(position).getAccountTitle());
            }
       holder. amount2.setText(allList.get(position).getAmount());
        holder.symbol2.setText(symbol);

        return convertView;
    }

    private String getFirstLetter(String day){
        String firstLetter = day.substring(0,1);
        firstLetter = firstLetter.toUpperCase();
        return firstLetter;

    }
    private List<String> seperateDate(String date){
        List<String> elephantList = Arrays.asList(date.split("-"));
        return elephantList;
    }

    private String getDayy(String date){

        String goal = "";
        SimpleDateFormat inFormat;
        SimpleDateFormat outFormat;
        Date a;
        try {
            inFormat = new SimpleDateFormat("yyyy-MM-dd");
            a = inFormat.parse(date);
            outFormat = new SimpleDateFormat("EEEE");
            goal = outFormat.format(a);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return goal;
    }
    class ViewHolder {
        TextView tvDay, tvCat,tvBnk,amount2,symbol2;
        CircularContactView view;
    }
}
