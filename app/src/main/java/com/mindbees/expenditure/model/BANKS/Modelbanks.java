package com.mindbees.expenditure.model.BANKS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 12-10-2016.
 */

public class Modelbanks implements Serializable {
    @SerializedName("bank")
    @Expose
    private List<Bank> bank = new ArrayList<Bank>();

    /**
     *
     * @return
     * The bank
     */
    public List<Bank> getBank() {
        return bank;
    }

    /**
     *
     * @param bank
     * The bank
     */
    public void setBank(List<Bank> bank) {
        this.bank = bank;
    }
}
