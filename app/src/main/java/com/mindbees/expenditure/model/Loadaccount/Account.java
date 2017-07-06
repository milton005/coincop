package com.mindbees.expenditure.model.Loadaccount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by User on 12-10-2016.
 */

public class Account implements Serializable{
    @SerializedName("account_id")
    @Expose
    private String accountId;

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("account_title")
    @Expose
    private String accountTitle;
    @SerializedName("added_date")
    @Expose
    private String addedDate;
    @SerializedName("initial_amount")
    @Expose
    private String initialAmount;
    @SerializedName("final_balance")
    @Expose
    private String finalBalance;

    /**
     *
     * @return
     * The accountId
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     *
     * @param accountId
     * The account_id
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     *
     * @return
     * The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The accountTitle
     */
    public String getAccountTitle() {
        return accountTitle;
    }

    /**
     *
     * @param accountTitle
     * The account_title
     */
    public void setAccountTitle(String accountTitle) {
        this.accountTitle = accountTitle;
    }

    /**
     *
     * @return
     * The addedDate
     */
    public String getAddedDate() {
        return addedDate;
    }

    /**
     *
     * @param addedDate
     * The added_date
     */
    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    /**
     *
     * @return
     * The initialAmount
     */
    public String getInitialAmount() {
        return initialAmount;
    }

    /**
     *
     * @param initialAmount
     * The initial_amount
     */
    public void setInitialAmount(String initialAmount) {
        this.initialAmount = initialAmount;
    }

    /**
     *
     * @return
     * The finalBalance
     */
    public String getFinalBalance() {
        return finalBalance;
    }

    /**
     *
     * @param finalBalance
     * The final_balance
     */
    public void setFinalBalance(String finalBalance) {
        this.finalBalance = finalBalance;
    }

}
