package com.mindbees.expenditure.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.DialogFragment ;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mindbees.expenditure.ActivityCreateCategoryTwo;
import com.mindbees.expenditure.MainActivity;
import com.mindbees.expenditure.R;
import com.mindbees.expenditure.model.Addcategory.Modeladdcategory;
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

public class Popup_add_category extends DialogFragment{
    View view;
    Tools tools;
    ImageView close;
    EditText Title;
    int id;
    String name;
    int Fragtype;
    LinearLayout submit;
 public     static Popup_add_category NewInstance()
    {
        Popup_add_category p=new Popup_add_category();
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
        view = inflater.inflate(R.layout.popup_add_category, container, false);
        getDialog().getWindow().requestFeature(1);
        tools = new Tools(getActivity());
        try {
            Bundle Extras=getArguments();
//            id=Extras.getInt("Id");
            Fragtype=Extras.getInt("FRAG_TYPE");
//            reminder_fragment_type=Extras.getInt(Constants.FRAGMENT_REMINDER_TYPE);

        }catch (Exception e)
        {

        }
        initUI(view);
        return view;
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
    private void initUI(View view) {
        close= (ImageView) view.findViewById(R.id.popup_add_category_close);
        Title= (EditText) view.findViewById(R.id.popup_add_category_edittext);
        submit= (LinearLayout) view.findViewById(R.id.popup_add_category_submit);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
//                getActivity().finish();
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

    private void submitdata() {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", BaseActivity.getpreference(Const.TAG_USERID,getActivity()));
        params.put("category_title", name);
        params.put("cat_image", "category_new_icon.png");
        params.put("cat_color", "1");
        params.put("type_id", String.valueOf(Fragtype));
        params.put("request","addcategory");

        APIService service = ServiceGenerator.createService(APIService.class,getContext());
        Call<Modeladdcategory> call = service.add_category(params);
        call.enqueue(new Callback<Modeladdcategory>() {
            @Override
            public void onResponse(Call<Modeladdcategory> call, Response<Modeladdcategory> response) {
                if (pd.isShowing()) {
                    pd.dismiss();
                    if(response.isSuccessful())
                    {
                        if (response.body()!=null&&response.body().getResult()!=null)
                        {
                            Modeladdcategory modeladdcategory=response.body();
                            int value = 0;
                            String message = "";
                            value=modeladdcategory.getResult().getValue();
                            message=modeladdcategory.getResult().getMessage();
                            tools.showToastMessage(message);
                            try {
                                if (value == 1) {
                                    Intent bIntent = new Intent();
//                                    if (isIncome) {
//                                        bIntent.setAction(Const.KEY_FILTER+".ACTION_RQST_REFRESH_INC");
//                                    }else {
//                                        bIntent.setAction(Const.KEY_FILTER+".ACTION_RQST_REFRESH_EXP");
//                                    }
//
//                                    sendBroadcast(bIntent);
                                    Intent intent=new Intent(getActivity(),MainActivity.class);
                                    intent.putExtra(Const.FRAGTYPE,1);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                                            | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    getActivity().overridePendingTransition(0, 0);
                                    getActivity().finish();

                                    getActivity().overridePendingTransition(0, 0);

                                    intent.putExtra(Const.FRAGMENT_REMINDER_TYPE,Fragtype-1);
                                    startActivity(intent);
//										finish();
                                }
                            } catch (NumberFormatException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Modeladdcategory> call, Throwable t) {

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
