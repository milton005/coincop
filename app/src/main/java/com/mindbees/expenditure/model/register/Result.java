package com.mindbees.expenditure.model.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 06-10-2016.
 */

public class Result {
    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("userId")
    @Expose
    private String userId;

    /**
     *
     * @return
     * The value
     */
    public Integer getValue() {
        return value;
    }

    /**
     *
     * @param value
     * The value
     */
    public void setValue(Integer value) {
        this.value = value;}
    public void setMessage(String message)
    {
        this.message=message;
    }
    public String getMessage(){return  message;}
    public void setUserId(String userId)
    {
        this.userId=userId;
    }
    public String getUserId(){
        return userId;
    }
}
