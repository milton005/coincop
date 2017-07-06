package com.mindbees.expenditure.model.Homefragment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 11-10-2016.
 */

public class Reminder {
    @SerializedName("reminder_id")
    @Expose
    private String reminderId;
    @SerializedName("reminder_text")
    @Expose
    private String reminderText;
    @SerializedName("reminder_date")
    @Expose
    private String reminderDate;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("added_date")
    @Expose
    private String addedDate;

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

    /**
     *
     * @return
     * The reminderText
     */
    public String getReminderText() {
        return reminderText;
    }

    /**
     *
     * @param reminderText
     * The reminder_text
     */
    public void setReminderText(String reminderText) {
        this.reminderText = reminderText;
    }

    /**
     *
     * @return
     * The reminderDate
     */
    public String getReminderDate() {
        return reminderDate;
    }

    /**
     *
     * @param reminderDate
     * The reminder_date
     */
    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
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
}
