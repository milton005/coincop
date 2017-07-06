package com.mindbees.expenditure.model.Reportyearlycategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by User on 19-10-2016.
 */

public class Info implements Serializable {
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("category_title")
    @Expose
    private String categoryTitle;
    @SerializedName("type_id")
    @Expose
    private String typeId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;

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

}
