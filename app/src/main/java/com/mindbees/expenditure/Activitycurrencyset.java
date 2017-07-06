package com.mindbees.expenditure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.mindbees.expenditure.Interfaces.DeleteItem;
import com.mindbees.expenditure.adapter.AdapterCurrency;
import com.mindbees.expenditure.database.CurrencySymbol;
import com.mindbees.expenditure.database.MyDataBase;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;

import java.util.ArrayList;

/**
 * Created by User on 20-02-2017.
 */

public class Activitycurrencyset extends BaseActivity implements DeleteItem{
    Tools tools;
    ImageView closeBtn, submitBtn;
    ListView listView;
    MyDataBase db ;
    int defaultPosition = -1;
    String symbol = "";
    String curencyId = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_currency);
        initUI();
        db = new MyDataBase(Activitycurrencyset.this);
        ArrayList<CurrencySymbol> list = new ArrayList<CurrencySymbol>();
        curencyId = BaseActivity.getpreference(Const.TAG_CURRENCY_ID, Activitycurrencyset.this);
        list = db.getAllCurrency(curencyId);
        final AdapterCurrency curncy = new AdapterCurrency(this, list);
        curncy.setCallback(this);
        listView.setAdapter(curncy);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (defaultPosition != -1) {
                    curncy.allList.get(defaultPosition).setSelected(false);
                }

                defaultPosition = position;
                symbol = curncy.allList.get(position).getCrncySymbol();
                curencyId = curncy.allList.get(position).getCrncyName();

                curncy.allList.get(position).setSelected(true);
                curncy.notifyDataSetChanged();
            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.setpreference(Const.TAG_SYMBOL, symbol, Activitycurrencyset.this);
                BaseActivity.setpreference(Const.TAG_CURRENCY_ID, curencyId,Activitycurrencyset.this);
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        super.onBackPressed();
    }
    private void initUI() {
        closeBtn= (ImageView) findViewById(R.id.popup_currency_close);
        listView= (ListView) findViewById(R.id.gridView_currency);
        submitBtn= (ImageView) findViewById(R.id.imageselect);
    }

    @Override
    public void onDeleteItem(int position) {
        defaultPosition = position;
    }
}
