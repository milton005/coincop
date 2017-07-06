package com.mindbees.expenditure.model.Yearlyreport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 19-10-2016.
 */

public class ModelYearlyReport  implements Serializable{
    @SerializedName("info")
    @Expose
    private List<Info> info = new ArrayList<Info>();

    /**
     *
     * @return
     * The info
     */
    public List<Info> getInfo() {
        return info;
    }

    /**
     *
     * @param info
     * The info
     */
    public void setInfo(List<Info> info) {
        this.info = info;
    }
}
