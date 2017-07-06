package com.mindbees.expenditure.retrofit;



import com.mindbees.expenditure.model.Add_account.Modeladdaccount;
import com.mindbees.expenditure.model.Addcategory.Modeladdcategory;
import com.mindbees.expenditure.model.AllAccounts.ModelAllAccount;
import com.mindbees.expenditure.model.BANKS.Modelbanks;
import com.mindbees.expenditure.model.Contact.Modelcontact;
import com.mindbees.expenditure.model.EditAccount.ModelEditAccount;
import com.mindbees.expenditure.model.Editcategory.ModelEditCategory;
import com.mindbees.expenditure.model.Getprofile.Modelgetprofile;
import com.mindbees.expenditure.model.Homefragment.Modelallreminder;
import com.mindbees.expenditure.model.LOGIN_EMAIL.Modellogin;
import com.mindbees.expenditure.model.LoadIncome.ModelLoadIncome;
import com.mindbees.expenditure.model.Loadaccount.Modeloadaccount;
import com.mindbees.expenditure.model.Fblogin.ModelFblogin;
import com.mindbees.expenditure.model.MonthlyAccountStat.ModelMonthlyAccountStat;
import com.mindbees.expenditure.model.Reminder.Modelsetreminder;
import com.mindbees.expenditure.model.ReportCategory.ModelReportCategory;
import com.mindbees.expenditure.model.Reportyearlycategory.ModelYearlyCategoryReport;
import com.mindbees.expenditure.model.Update_profile.Modelupdate_profile;
import com.mindbees.expenditure.model.Yearlyreport.ModelYearlyReport;
import com.mindbees.expenditure.model.addrecord.Modeladdrecord;
import com.mindbees.expenditure.model.creditdebit.loadcategory.Modelloadcategory;
import com.mindbees.expenditure.model.delaccount.Modeldelaccount;
import com.mindbees.expenditure.model.register.Modelregister;
import com.mindbees.expenditure.util.Urls;

import java.io.UTFDataFormatException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;


/**
 * Created by tony on 14/06/2016.
 */
public interface APIService {

    @Headers("Oakey:try")
   @FormUrlEncoded
   @POST(Urls.register)
   Call<Modelregister> register(@FieldMap HashMap<String, String> params);
    @Headers("Oakey:try")
    @FormUrlEncoded
    @POST(Urls.register)
    Call<Modellogin> login_email(@FieldMap HashMap<String, String> params);
    @Headers("Oakey:try")
    @FormUrlEncoded
    @POST(Urls.register)
    Call<Modelallreminder> all_reminder(@FieldMap HashMap<String, String> params);
    @Headers("Oakey:try")
    @FormUrlEncoded
    @POST(Urls.register)
    Call<Modelsetreminder> add_reminder(@FieldMap HashMap<String, String> params);
    @Headers("Oakey:try")
    @FormUrlEncoded
    @POST(Urls.register)
    Call<Modelloadcategory> load_category(@FieldMap HashMap<String, String> params);
 @Headers("Oakey:try")
 @FormUrlEncoded
 @POST(Urls.register)
 Call<ModelLoadIncome> load_income(@FieldMap HashMap<String, String> params);
    @Headers("Oakey:try")
    @FormUrlEncoded
    @POST(Urls.register)
    Call<Modelbanks> load_banks(@FieldMap HashMap<String, String> params);
 @Headers("Oakey:try")
 @FormUrlEncoded
 @POST(Urls.register)
 Call<Modeladdaccount> add_account(@FieldMap HashMap<String, String> params);
 @Headers("Oakey:try")
 @FormUrlEncoded
 @POST(Urls.register)
 Call<Modeloadaccount> load_accounts(@FieldMap HashMap<String, String> params);
 @Headers("Oakey:try")
 @FormUrlEncoded
 @POST(Urls.register)
 Call<Modeladdrecord> add_record(@FieldMap HashMap<String, String> params);
 @Headers("Oakey:try")
 @FormUrlEncoded
 @POST(Urls.register)
 Call<Modeldelaccount> del_account(@FieldMap HashMap<String, String> params);
 @Headers("Oakey:try")
 @FormUrlEncoded
 @POST(Urls.register)
 Call<Modeladdcategory> add_category(@FieldMap HashMap<String, String> params);
 @Headers("Oakey:try")
 @FormUrlEncoded
 @POST(Urls.register)
 Call<Modelgetprofile> get_profile(@FieldMap HashMap<String, String> params);
 @Headers("Oakey:try")
 @FormUrlEncoded
 @POST(Urls.register)
 Call<Modelupdate_profile> update_profile(@FieldMap HashMap<String, String> params);
 @Headers("Oakey:try")
 @FormUrlEncoded
 @POST(Urls.register)
 Call<Modelupdate_profile> update_password(@FieldMap HashMap<String, String> params);
 @Headers("Oakey:try")
 @FormUrlEncoded
 @POST(Urls.register)
 Call<Modelcontact> contactus(@FieldMap HashMap<String, String> params);
 @Headers("Oakey:try")
 @FormUrlEncoded
 @POST(Urls.register)
 Call<ModelYearlyCategoryReport> yearlycategoryreport(@FieldMap HashMap<String, String> params);
 @Headers("Oakey:try")
 @FormUrlEncoded
 @POST(Urls.register)
 Call<ModelYearlyReport> yearlyreport(@FieldMap HashMap<String, String> params);
 @Headers("Oakey:try")
 @FormUrlEncoded
 @POST(Urls.register)
 Call<Modeloadaccount> load_status(@FieldMap HashMap<String, String> params);
 @Headers("Oakey:try")
 @FormUrlEncoded
 @POST(Urls.register)
 Call<ModelFblogin> fb_login(@FieldMap HashMap<String, String> params);
 @FormUrlEncoded
 @POST(Urls.AllAccounts)
 Call<ModelAllAccount>all_accounts(@FieldMap HashMap<String,String>params);
 @FormUrlEncoded
 @POST(Urls.EditCategory)
 Call<ModelEditCategory>edit_category(@FieldMap HashMap<String,String>params);
 @FormUrlEncoded
 @POST(Urls.EditAccount)
 Call<ModelEditAccount>edit_account(@FieldMap HashMap<String,String>params);
 @Headers("Oakey:try")
 @FormUrlEncoded
 @POST(Urls.register)
 Call<ModelReportCategory> categoryreport(@FieldMap HashMap<String, String> params);
 @FormUrlEncoded
 @POST(Urls.TRANSACTION_HISTORY)
 Call<ModelMonthlyAccountStat>monthlyaccountstat(@FieldMap HashMap<String,String>params);
//
//
//
//    @FormUrlEncoded
//    @POST(Urls.register)
//    Call<ModelRegister> register(@FieldMap HashMap<String, String> params);
//
//    @FormUrlEncoded
//    @POST(Urls.facebookRegister)
//    Call<ModelFbRegister> facebookRegister(@FieldMap Map<String, String> map);
//
//    @FormUrlEncoded
//    @POST(Urls.facebookLogin)
//    Call<ModelLogin> facebookLogin(@FieldMap Map<String, String> map);
//
//    @POST(Urls.getJobList)
//    Call<ModelJobs> getJobs(@QueryMap Map<String, String> map);
//
//    @FormUrlEncoded
//    @POST(Urls.getnotification)
//    Call<ModelJobs>getnotification(@FieldMap Map<String, String> map);
//    @FormUrlEncoded
//    @POST(Urls.getaboutus)
//    Call<ModelAboutUs>getaboutus(@FieldMap Map<String, String> map);
//    @FormUrlEncoded
//    @POST(Urls.getnotificationcount)
//    Call<ModelNotificationCount>getnotificationcount(@FieldMap Map<String, String> map);
//    @FormUrlEncoded
//    @POST(Urls.getlogout)
//    Call<Modellogout>getlogout(@FieldMap Map<String, String> map);
//    @FormUrlEncoded
//    @POST(Urls.getPutYourSkillsToWork)
//    Call<ModelPutYourSkillsToWork> getJobseekers(@FieldMap Map<String, String> map);
//    @FormUrlEncoded
//    @POST(Urls.getPlants)
//    Call<ModelPlants> getPlants(@FieldMap Map<String, String> map);
//    @POST(Urls.getplant1)
//    Call<ModelPlants> getPlants1(@QueryMap Map<String, String> map);
//
//    @POST(Urls.getRequirements)
//    Call<ModelRequirements> getRequirements(@QueryMap Map<String, String> map);
//
//    @FormUrlEncoded
//    @POST(Urls.getJobDetail)
//    Call<ModelJobDetail> getJobDetail(@FieldMap Map<String, String> map);
//
//
//    @FormUrlEncoded
//    @POST(Urls.getPutPlantDetail)
//    Call<ModelPutPlantDetail> getPutPlantDetail(@FieldMap Map<String, String> map);
//
//    @FormUrlEncoded
//    @POST(Urls.updateProfile)
//    Call<ModelUpadateJobprofile> getUpdateProfile(@FieldMap Map<String, String> map);
//
//    @FormUrlEncoded
//    @POST(Urls.jobSeekerDetail)
//    Call<ModelJobSeekerDetail> getJobSeekerDetail(@FieldMap Map<String, String> map);
//
//    @FormUrlEncoded
//    @POST(Urls.applyNow)
//    Call<ModelApplyNow> getApplyNow(@FieldMap Map<String, String> map);

}
