package com.mindbees.expenditure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mindbees.expenditure.R;
import com.mindbees.expenditure.model.AllAccounts.Account;
import com.mindbees.expenditure.model.Homefragment.Reminder;
import com.mindbees.expenditure.ui.CircularContactView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by User on 14-02-2017.
 */

public class AdapterAllAccounts extends BaseAdapter {
    Context context;
    ArrayList<Account> allList;

    private int[] PHOTO_TEXT_BACKGROUND_COLORS;

    private final String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    Calendar cal;
    int crnt_day;
    int crntmonth;
    int year;

    public AdapterAllAccounts(Context context, ArrayList<Account> allList) {
        super();
        this.context = context;
        this.allList = allList;
        PHOTO_TEXT_BACKGROUND_COLORS = context.getResources().getIntArray(R.array.contacts_text_background_colors);

        cal = 	Calendar.getInstance(Locale.getDefault());
        crntmonth=cal.get(Calendar.MONTH)+1;

        crnt_day = cal.get(Calendar.DAY_OF_MONTH);
        year=cal.get(Calendar.YEAR);

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = convertView;
        AdapterAllAccounts.ViewHolder holder;
        if (v == null) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_allreminders, null);
            holder = new AdapterAllAccounts.ViewHolder();

            holder.tvReminderTitle = (TextView) v.findViewById(R.id.reminderTitle);
            holder.tvDueDate = (TextView) v.findViewById(R.id.reminderDue);
            holder.imageDate = (CircularContactView) v.findViewById(R.id.imgLeftDueDate);

            v.setTag(holder);
        }else{
            holder = (AdapterAllAccounts.ViewHolder) v.getTag();
        }

        final int backgroundColorToUse = PHOTO_TEXT_BACKGROUND_COLORS[position % PHOTO_TEXT_BACKGROUND_COLORS.length];
        List<String> tempList = seperateDate(allList.get(position).getActionTime().trim());

        holder.tvReminderTitle.setText(allList.get(position).getCategoryTitle());
        holder.tvDueDate.setText(allList.get(position).getAmount());

//        int dueDayCount = findDueDay(tempList.get(2));
//
//        int duemonth=findDuemonth(tempList.get(1));
//        int dueyear=findDueyear(tempList.get(0));
//        if(dueyear>0)
//        {
//            if(dueyear ==1)
//            {
//                holder.tvDueDate.setText("Expired Previous year");
//            }
//            else
//            {
//                holder.tvDueDate.setText("Expired more than "+""+(dueyear-1)+""+" years ago");
//            }
//
//        }
//        if(dueyear<0)
//        {
//            if(dueyear==-1)
//            {
//                holder.tvDueDate.setText("Due Next year");
//            }
//            else
//            {
//                holder.tvDueDate.setText("Due in "+Math.abs(dueyear)+""+" years");
//            }
//        }
//        if(dueyear==0) {
//            if (duemonth > 0) {
////			holder.tvDueDate.setText("Expired ");
//                if (duemonth == 1) {
//                    holder.tvDueDate.setText("Expired Previous Month");
//                } else {
//                    holder.tvDueDate.setText("Expired more than" +
//                            "" + (duemonth-1) + "" + " months ago");
//                }
//            }
//            if (duemonth < 0) {
////			holder.tvDueDate.setText("Due");
//                if (duemonth == -1) {
//                    holder.tvDueDate.setText("Due Next month");
//
//                } else {
//                    holder.tvDueDate.setText("Due in " + Math.abs(duemonth) + "" + " months");
//                }
//            }
//            if (duemonth == 0) {
//                if (dueDayCount < 0) {
//                    if (dueDayCount == -1) {
//                        holder.tvDueDate.setText("Due Tomorrow");
//                    } else {
//                        holder.tvDueDate.setText("Due in " + Math.abs(dueDayCount) + "" + " days");
//                    }
//
//                } else if (dueDayCount == 0) {
//                    holder.tvDueDate.setText("Due Today");
//                } else {
//                    if (dueDayCount == 1) {
//                        holder.tvDueDate.setText("Expired Yesterday");
//                    } else {
//                        holder.tvDueDate.setText("Expired " + dueDayCount + "" + " days ago");
//                    }
//
//                }
//            }
//        }



        holder.imageDate.getTextView().setTextSize(11);
        holder.imageDate.getTextView().setTextColor(context.getResources().getColor(R.color.white));
        holder.imageDate.setTextAndBackgroundColor(tempList.get(2)+"\n"+getMonthAsString(Integer.parseInt(tempList.get(1))), backgroundColorToUse, true);

        return v;
    }

    private String getMonthAsString(int i){
        return months[i-1];
    }

    private List<String> seperateDate(String date){
        List<String> elephantList = Arrays.asList(date.split("-"));
        return elephantList;
    }

    private int findDueDay(String date){


	/*	Log.d("coincop", "alarm date ::::::::::"+ date+"");
		Log.d("coincop", "Crnt day ::::::::::"+ crnt_day+"");*/

        int dueDay = crnt_day - Integer.parseInt(date);

//		Log.d("coincop", "due day ::::::::::"+ dueDay+"");

        return dueDay;
    }
    private int findDuemonth(String date){


	/*	Log.d("coincop", "alarm date ::::::::::"+ date+"");
		Log.d("coincop", "Crnt day ::::::::::"+ crnt_day+"");*/

        int dueDay = crntmonth - Integer.parseInt(date);

//		Log.d("coincop", "due day ::::::::::"+ dueDay+"");

        return dueDay;
    }

    private int findDueyear(String date){


	/*	Log.d("coincop", "alarm date ::::::::::"+ date+"");
		Log.d("coincop", "Crnt day ::::::::::"+ crnt_day+"");*/

        int dueDay = year- Integer.parseInt(date);

//		Log.d("coincop", "due day ::::::::::"+ dueDay+"");

        return dueDay;
    }
    class ViewHolder {
        TextView tvReminderTitle, tvDueDate;
        CircularContactView imageDate;
    }

}
