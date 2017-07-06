package com.mindbees.expenditure;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.mindbees.expenditure.model.EditAccount.ModelEditAccount;
import com.mindbees.expenditure.retrofit.APIService;
import com.mindbees.expenditure.retrofit.ServiceGenerator;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 16-02-2017.
 */

public class ActivityEditAccount extends BaseActivity {
    EditText AccountName;
    ImageView closeBtn, submitBtn;
    Tools tools;
    String AccountId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        setupUI(findViewById(R.id.parentView));
        tools = new Tools(this);
        AccountName= (EditText) findViewById(R.id.description);
        closeBtn = (ImageView) findViewById(R.id.closeBtn);
//		ok = (FloatingActionButton) findViewById(R.id.imgOK);
        try
        {
            if (getIntent().hasExtra("Account_Id"))
            {
                AccountId=getIntent().getStringExtra("Account_Id");
            }

        }catch (Exception e) {

        }
        submitBtn = (ImageView) findViewById(R.id.imgOK);
        closeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (tools.isConnectingToInternet()) {
                    validateAccount();
                }else {
                    tools.showToastMessage(getResources().getString(R.string.connectivity));
                }

            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onBackPressed();
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
    private void validateAccount() {
        String acntTitle = AccountName.getText().toString().trim();
        AccountName.setError(null);
        if (acntTitle.isEmpty()) {
            AccountName.setError("Enter Account Name");

//        }else if (acntAmnt.isEmpty()) {
//            editAccountAmount.setError("Enter Amount");
        }else {
            addAccount(BaseActivity.getpreference(Const.TAG_USERID, this), acntTitle);
        }
    }

    private void addAccount(String userid, String acntTitle) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userid);
        params.put("account_title", acntTitle);
        params.put("account_id",AccountId);
        APIService service = ServiceGenerator.createService(APIService.class,ActivityEditAccount.this);
        Call<ModelEditAccount>call=service.edit_account(params);
        call.enqueue(new Callback<ModelEditAccount>() {
            @Override
            public void onResponse(Call<ModelEditAccount> call, Response<ModelEditAccount> response) {
                if (pd.isShowing()) { pd.dismiss(); }
                if (response.isSuccessful())
                {
                    if (response.body()!=null&&response.body().getResult()!=null){
                        ModelEditAccount modelEditAccount=response.body();
                        int value=modelEditAccount.getResult().getValue();
                        if (value==1)
                        {
                            String msg=modelEditAccount.getResult().getMessage();
                            tools.showToastMessage(msg);
                            Intent returnIntent = new Intent();
                            setResult(Activity.RESULT_OK, returnIntent);
//						}

                            finish();
                        }
                        else
                        {
                            tools.showToastMessage("Update Failed");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelEditAccount> call, Throwable t) {
                if (pd.isShowing()) { pd.dismiss(); }
                tools.showToastMessage(getResources().getString(R.string.connectivity));
            }
        });
    }
}
