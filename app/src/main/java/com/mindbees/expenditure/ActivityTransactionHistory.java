package com.mindbees.expenditure;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mindbees.expenditure.adapter.AdapterMonth;
import com.mindbees.expenditure.adapter.AdapterTransactionHistory;
import com.mindbees.expenditure.adapter.Adapteryear;
import com.mindbees.expenditure.model.MonthlyAccountStat.Account;
import com.mindbees.expenditure.model.MonthlyAccountStat.ModelMonthlyAccountStat;
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
 * Created by User on 18-02-2017.
 */

public class ActivityTransactionHistory extends BaseActivity {
    Tools tools;
    ImageView bckBtn;
    TextView noviewLabel;
    Spinner year,month;
    String Accountid;
    Adapteryear adapterYear;
    ArrayList<Account>arraylist;
    AdapterMonth adapterMonth;
    AdapterTransactionHistory adapter;
    RecyclerView recyclerView;
    Calendar _calendar;
    Bundle bund;
    ArrayList<String> yearList;
    ArrayList<String>Monthlist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        tools=new Tools(this);
        initUi();
        bckBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });
        try{
          bund=getIntent().getExtras();
            Accountid=bund.getString("Account_id");
        }catch (Exception e){}
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
        arraylist=new ArrayList<Account>();
        adapter=new AdapterTransactionHistory(ActivityTransactionHistory.this,arraylist);
        recyclerView.setAdapter(adapter);
        setupUI();

    }

    private void setupUI() {
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
                if (tools.isConnectingToInternet())
                {
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
        params.put("account_id",Accountid);
        APIService service = ServiceGenerator.createService(APIService.class,ActivityTransactionHistory.this);
        Call<ModelMonthlyAccountStat>call=service.monthlyaccountstat(params);
        call.enqueue(new Callback<ModelMonthlyAccountStat>() {
            @Override
            public void onResponse(Call<ModelMonthlyAccountStat> call, Response<ModelMonthlyAccountStat> response) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.isSuccessful())
                {
                    if (response.body()!=null&&response.body().getAccount()!=null)
                    {
                        arraylist.clear();
                        ModelMonthlyAccountStat monthlyastat=response.body();
                        List<Account>accounts=monthlyastat.getAccount();
                        arraylist.addAll(accounts);
                        adapter.notifyDataSetChanged();
                        recyclerView.setVisibility(View.VISIBLE);
                        noviewLabel.setVisibility(View.GONE);
                    }
                    if (response.body().getAccount().isEmpty())
                    {
                        noviewLabel.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onFailure(Call<ModelMonthlyAccountStat> call, Throwable t) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
                tools.showToastMessage(getResources().getString(R.string.connectivity));
            }
        });

    }

    private void initUi() {
        bckBtn= (ImageView) findViewById(R.id.backBtn);
        noviewLabel= (TextView) findViewById(R.id.noViewLabel);
        year= (Spinner) findViewById(R.id.year);
        month= (Spinner) findViewById(R.id.month);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView_transaction);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(ActivityTransactionHistory.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
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
