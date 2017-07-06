package com.mindbees.expenditure;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mindbees.expenditure.adapter.AdapterMonth;
import com.mindbees.expenditure.adapter.AdapterReportCategory;
import com.mindbees.expenditure.adapter.Adapteryear;
import com.mindbees.expenditure.model.ReportCategory.Account;
import com.mindbees.expenditure.model.ReportCategory.ModelReportCategory;
import com.mindbees.expenditure.model.Total;
import com.mindbees.expenditure.retrofit.APIService;
import com.mindbees.expenditure.retrofit.ServiceGenerator;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 16-02-2017.
 */

public class ActivityReportCategory extends BaseActivity {
    Tools tools;
    ImageView bckBtn;
    TextView  noviewLabel;
    Spinner year,month;
    ListView list;
    ArrayList<Account> allList;
    ArrayList<String> yearList;
    ArrayList<String>Monthlist;
    Adapteryear adapterYear;
    AdapterMonth adapterMonth;
    Bundle bund;
    AdapterReportCategory adapter;
    Calendar _calendar;
    boolean isCategory;
    String type;
    TextView tvTotalIncome;
    TextView tvTotalExpense;
    ArrayList<Total> total = new ArrayList<Total>();
    LinearLayout totalLayout;
    private int totalExpense;
    private int totalIncome;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_category);
        initUi();
        try {
            bund = getIntent().getExtras();
            type = bund.getString("type");
            isCategory = bund.getBoolean("isCategory");
        }
        catch (Exception e)
        {}
        bckBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });

        tools = new Tools(this);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        _calendar = Calendar.getInstance(Locale.getDefault());

        int yearCrnt = _calendar.get(Calendar.YEAR);
        int u=_calendar.get(Calendar.MONTH);
        yearList = new ArrayList<String>();
        Monthlist=new ArrayList<String>();

        for (int i = 2013; i <= 2018 ; i++) {

            yearList.add(i+"");
        }
        adapterYear = new Adapteryear(this, yearList);
        year.setAdapter(adapterYear);
        year.setSelection(getIndex(adapterYear,String.valueOf(yearCrnt)));
        Monthlist.add("January");
        Monthlist.add("February");
        Monthlist.add("March");
        Monthlist.add("May");
        Monthlist.add("June");
        Monthlist.add("July");
        Monthlist.add("August");
        Monthlist.add("September");
        Monthlist.add("October");
        Monthlist.add("November");
        Monthlist.add("December");
        adapterMonth=new AdapterMonth(this,Monthlist);
        month.setAdapter(adapterMonth);
        month.setSelection(u);
        allList=new ArrayList<Account>();
       adapter=new AdapterReportCategory(this,allList,isCategory);
        list.setAdapter(adapter);
        SetUi();
    }

    private void SetUi() {
       year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(tools.isConnectingToInternet()) {
                   loadDetails();
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(tools.isConnectingToInternet()) {
                loadDetails();
            }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadDetails() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();
        int monthselected=month.getSelectedItemPosition()+1;

        int yearselect=year.getSelectedItemPosition();
        String yearselected=adapterYear.list.get(yearselect);
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", BaseActivity.getpreference(Const.TAG_USERID, this));
        params.put("year", yearselected);
        params.put("month",String.valueOf(monthselected));
        if (isCategory)
        {params.put("request","monthlyreport");
            APIService service = ServiceGenerator.createService(APIService.class,ActivityReportCategory.this);
            Call<ModelReportCategory>call=service.categoryreport(params);
            call.enqueue(new Callback<ModelReportCategory>() {
                @Override
                public void onResponse(Call<ModelReportCategory> call, Response<ModelReportCategory> response) {
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response.isSuccessful())
                    {
                        if (response.body()!=null&&response.body().getAccount()!=null)
                        {
                            totalLayout.setVisibility(View.VISIBLE);
                            list.setVisibility(View.VISIBLE);
                            allList.clear();
                            ModelReportCategory reportCategory=response.body();
                            List<Account>accounts=reportCategory.getAccount();
                            allList.addAll(accounts);
                            SetTotal(accounts);
                            adapter.notifyDataSetChanged();
                            if (!accounts.isEmpty())
                            {
                                noviewLabel.setVisibility(View.GONE);
                            }
                            else {
                                noviewLabel.setVisibility(View.VISIBLE);

                            }
                        }
                        if (response.body().getAccount().isEmpty())
                        {
                           list.setVisibility(View.GONE);
                            noviewLabel.setVisibility(View.VISIBLE);
                            totalLayout.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelReportCategory> call, Throwable t) {
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                    tools.showToastMessage(getResources().getString(R.string.connectivity));
                }
            });

        }
        else {
            params.put("type_id", type);
            params.put("request","monthlyreport");
            APIService service = ServiceGenerator.createService(APIService.class,ActivityReportCategory.this);
            Call<ModelReportCategory>call=service.categoryreport(params);
            call.enqueue(new Callback<ModelReportCategory>() {
                @Override
                public void onResponse(Call<ModelReportCategory> call, Response<ModelReportCategory> response) {
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response.isSuccessful())
                    {
                        if (response.body()!=null&&response.body().getAccount()!=null)
                        {
                            totalLayout.setVisibility(View.GONE);
                            list.setVisibility(View.VISIBLE);
                            allList.clear();
                            ModelReportCategory reportCategory=response.body();
                            List<Account>accounts=reportCategory.getAccount();
                            allList.addAll(accounts);
                            adapter.notifyDataSetChanged();
                            if (!accounts.isEmpty())
                            {
                                noviewLabel.setVisibility(View.GONE);
                            }
                            else {
                                noviewLabel.setVisibility(View.VISIBLE);

                            }
                        }
                        if (response.body().getAccount().isEmpty())
                        {
                            list.setVisibility(View.GONE);
                            noviewLabel.setVisibility(View.VISIBLE);
                            totalLayout.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelReportCategory> call, Throwable t) {
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                    tools.showToastMessage(getResources().getString(R.string.connectivity));
                }
            });
        }
    }

    private void SetTotal(List<Account> accounts) {
        totalExpense = 0;
        totalIncome = 0;
        for (int i=0;i<accounts.size();i++)
        {
            if(accounts.get(i).getTypeId().contentEquals(Const.TAG_INCOME_ID))
            {
                totalIncome = totalIncome
                        + Integer.parseInt(accounts.get(i).getAmount());
            }
            else if (accounts.get(i).getTypeId().contentEquals(Const.TAG_EXPENSE_ID))
            {totalExpense = totalExpense
                    + Integer.parseInt(accounts.get(i).getAmount());

            }
        }
        if (totalExpense != 0 && totalIncome != 0) {
            tvTotalIncome.setVisibility(View.VISIBLE);
            tvTotalExpense.setVisibility(View.VISIBLE);
            tvTotalIncome.setText(String.valueOf("Income " + totalIncome));
            tvTotalExpense.setText(String
                    .valueOf("Expense " + totalExpense));
        } else if (totalIncome != 0 && totalExpense == 0) {
            tvTotalIncome.setVisibility(View.VISIBLE);
            tvTotalExpense.setVisibility(View.GONE);
            tvTotalIncome.setText(String.valueOf("Income " + totalIncome));
        } else if (totalExpense != 0 && totalIncome == 0) {
            tvTotalExpense.setVisibility(View.VISIBLE);
            tvTotalIncome.setVisibility(View.GONE);
            tvTotalExpense.setText(String
                    .valueOf("Expense " + totalExpense));
        }
    }


    private void initUi() {
        noviewLabel= (TextView) findViewById(R.id.noViewLabel);
        year= (Spinner) findViewById(R.id.year);
        month= (Spinner) findViewById(R.id.month);
        list= (ListView) findViewById(R.id.listExpand);
        bckBtn = (ImageView) findViewById(R.id.backBtn);
        tvTotalIncome = (TextView) findViewById(R.id.totalIncome);
        tvTotalExpense = (TextView) findViewById(R.id.totalExpense);
        totalLayout = (LinearLayout) findViewById(R.id.total);
    }
    private int getIndex(Adapteryear adapter2, String myString) {

        int index = 0;

        for (int i = 0; i < adapter2.list.size(); i++) {
//	            Log.e(LOG_TAG, "option id-----------"+adapter2.list.get(i).get_optionId());

            if (adapter2.list.get(i).contentEquals(myString)) {

                index = i;

            }
        }
        return index;
    }
}
