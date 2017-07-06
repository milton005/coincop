package com.mindbees.expenditure.model.LoadIncome;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by User on 02-11-2016.
 */

public class Account implements Serializable {
    @SerializedName("record_id")
    @Expose
    private String recordId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("account_id")
    @Expose
    private String accountId;
    @SerializedName("type_id")
    @Expose
    private String typeId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("account_date")
    @Expose
    private String actionDate;
    @SerializedName("added_date")
    @Expose
    private String addedDate;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("category_title")
    @Expose
    private String categoryTitle;
    @SerializedName("account_title")
    @Expose
    private String accountTitle;
    @SerializedName("month")
    @Expose
    private String month;

    /**
     *
     * @return
     * The recordId
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     *
     * @param recordId
     * The record_id
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId;
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
     * The typeId
     */
    public String getTypeId() {
        return typeId;
    }

    /**
     *
     * @param typeId
     * The type_id
     */
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    /**
     *
     * @return
     * The categoryId
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     *
     * @param categoryId
     * The category_id
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     *
     * @return
     * The amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     * The amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The actionDate
     */
    public String getActionDate() {
        return actionDate;
    }

    /**
     *
     * @param actionDate
     * The action_date
     */
    public void setActionDate(String actionDate) {
        this.actionDate = actionDate;
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
     * The day
     */
    public String getDay() {
        return day;
    }

    /**
     *
     * @param day
     * The day
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     *
     * @return
     * The categoryTitle
     */
    public String getCategoryTitle() {
        return categoryTitle;
    }

    /**
     *
     * @param categoryTitle
     * The category_title
     */
    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
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
     * The month
     */
    public String getMonth() {
        return month;
    }

    /**
     *
     * @param month
     * The month
     */
    public void setMonth(String month) {
        this.month = month;
    }
}
