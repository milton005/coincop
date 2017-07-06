package com.mindbees.expenditure.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment ;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mindbees.expenditure.MainActivity;
import com.mindbees.expenditure.R;
import com.mindbees.expenditure.model.AllAccounts.ModelAllAccount;
import com.mindbees.expenditure.model.Editcategory.ModelEditCategory;
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
 * Created by User on 15-02-2017.
 */

public class Popupeditcategory extends DialogFragment {
    View view;
    Tools tools;
    ImageView close;
    EditText Title;
    int id;
    String name;
    int Fragtype;
    LinearLayout submit;
    static Popupeditcategory NewInstance()
    {
        Popupeditcategory p=new Popupeditcategory();
        return p;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL,  R.style.Theme_FullScrenn);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.popupeditcategoryexpens,container,false);
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().requestFeature(1);
        tools = new Tools(getActivity());
        try {
            Bundle Extras=getArguments();
            id=Extras.getInt("Id");
            Fragtype=Extras.getInt("FRAG_TYPE");
//            reminder_fragment_type=Extras.getInt(Constants.FRAGMENT_REMINDER_TYPE);

        }catch (Exception e)
        {

        }
//        view.findViewById(R.id.button_close_tips).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent i=new Intent(getActivity(),Detalle.class);
////                startActivity(i);
//                dismiss();
//            }
//        });

        initUI(view);
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                validateform();
//            }
//        });

        return view;
    }

    private void initUI(View view) {
        close= (ImageView) view.findViewById(R.id.popup_edit_category_close);
        Title= (EditText) view.findViewById(R.id.popup_edit_category_edittext);
        submit= (LinearLayout) view.findViewById(R.id.popup_edit_category_submit);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tools.isConnectingToInternet())
                {
                    if(checkdata()){
                        hideSoftKeyboard(Title);
                        submitdata();
                    }
                }
            }
        });


    }
    public void hideSoftKeyboard(EditText edit) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(edit.getWindowToken(), 0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void submitdata() {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id",BaseActivity.getpreference(Const.TAG_USERID,getActivity()));
        params.put("category_title",name);
        params.put("category_id",String.valueOf(id));
        APIService service = ServiceGenerator.createService(APIService.class,getContext());
        Call<ModelEditCategory>call=service.edit_category(params);
        call.enqueue(new Callback<ModelEditCategory>() {
            @Override
            public void onResponse(Call<ModelEditCategory> call, Response<ModelEditCategory> response) {
                if (pd.isShowing()) {
                    pd.dismiss();

                }
                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        if (response.body().getResult()!=null)
                        {
                            ModelEditCategory editcategory=response.body();
                            int value=editcategory.getResult().getValue();
                            if(value==1)
                            {
                                String message=editcategory.getResult().getMessage();
                                tools.showToastMessage(message);
                                Intent intent=new Intent(getActivity(), MainActivity.class);
                                intent.putExtra(Const.FRAGTYPE,1);
                                intent.putExtra(Const.FRAGMENT_REMINDER_TYPE,Fragtype);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                                        | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                getActivity().overridePendingTransition(0, 0);
                                getActivity().finish();

                                getActivity().overridePendingTransition(0, 0);
                                startActivity(intent);
                            }
                            else {
                                tools.showToastMessage("UpdateFailed");
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelEditCategory> call, Throwable t) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
                tools.showToastMessage("UpdateFailed");
            }
        });
    }

    private boolean checkdata() {
        name=Title.getText().toString().trim();
        if (name.isEmpty())
        {
            Title.setError("Enter Category Title");
            return false;
        }
        else {
           return true;
        }

    }


}
