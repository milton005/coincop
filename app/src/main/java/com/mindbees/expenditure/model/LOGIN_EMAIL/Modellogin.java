package com.mindbees.expenditure.model.LOGIN_EMAIL;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 07-10-2016.
 */

public class Modellogin implements Serializable{
    @SerializedName("result")
    @Expose
    private List<Result> result = new ArrayList<Result>();

    /**
     *
     * @return
     * The result
     */
    public List<Result> getResult() {
        return result;
    }

    /**
     *
     * @param result
     * The result
     */
    public void setResult(List<Result> result) {
        this.result = result;
    }
}
