package com.mindbees.expenditure.model.Yearlyreport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 19-10-2016.
 */

public class Info implements Serializable{
    @SerializedName("account")
    @Expose
    private List<Account> account = new ArrayList<Account>();

    /**
     *
     * @return
     * The account
     */
    public List<Account> getAccount() {
        return account;
    }

    /**
     *
     * @param account
     * The account
     */
    public void setAccount(List<Account> account) {
        this.account = account;
    }

}