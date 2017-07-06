package com.mindbees.expenditure.model.Contact;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by User on 19-10-2016.
 */

public class Modelcontact implements Serializable{
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("success_msg")
    @Expose
    private String successMsg;

    /**
     *
     * @return
     * The success
     */
    public String getSuccess() {
        return success;
    }

    /**
     *
     * @param success
     * The success
     */
    public void setSuccess(String success) {
        this.success = success;
    }

    /**
     *
     * @return
     * The successMsg
     */
    public String getSuccessMsg() {
        return successMsg;
    }

    /**
     *
     * @param successMsg
     * The success_msg
     */
    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }
}
