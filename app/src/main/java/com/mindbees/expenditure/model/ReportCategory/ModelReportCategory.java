package com.mindbees.expenditure.model.ReportCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 16-02-2017.
 */

public class ModelReportCategory implements Serializable {
    @SerializedName("account")
    @Expose
    private List<Account> account = null;

    public List<Account> getAccount() {
        return account;
    }

    public void setAccount(List<Account> account) {
        this.account = account;
    }
}
