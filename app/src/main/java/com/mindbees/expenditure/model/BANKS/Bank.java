package com.mindbees.expenditure.model.BANKS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by User on 12-10-2016.
 */

public class Bank implements Serializable{
    @SerializedName("bank_id")
    @Expose
    private String bankId;
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("bank_name")
    @Expose
    private String bankName;

    /**
     *
     * @return
     * The bankId
     */
    public String getBankId() {
        return bankId;
    }

    /**
     *
     * @param bankId
     * The bank_id
     */
    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    /**
     *
     * @return
     * The countryId
     */
    public String getCountryId() {
        return countryId;
    }

    /**
     *
     * @param countryId
     * The country_id
     */
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    /**
     *
     * @return
     * The bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     *
     * @param bankName
     * The bank_name
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

}
