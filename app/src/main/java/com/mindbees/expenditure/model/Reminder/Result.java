package com.mindbees.expenditure.model.Reminder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by User on 12-10-2016.
 */

public class Result implements Serializable{
    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("reminder_id")
    @Expose
    private String reminderId;

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
        this.value = value;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The reminderId
     */
    public String getReminderId() {
        return reminderId;
    }

    /**
     *
     * @param reminderId
     * The reminder_id
     */
    public void setReminderId(String reminderId) {
        this.reminderId = reminderId;
    }
}
