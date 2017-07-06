package com.mindbees.expenditure.model.AllAccounts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 14-02-2017.
 */

public class ModelAllAccount implements Serializable {
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
