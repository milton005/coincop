package com.mindbees.expenditure.model.Homefragment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11-10-2016.
 */

public class Modelallreminder implements Serializable {
    @SerializedName("reminder")
    @Expose
    private List<Reminder> reminder = new ArrayList<Reminder>();

    /**
     *
     * @return
     * The reminder
     */
    public List<Reminder> getReminder() {
        return reminder;
    }

    /**
     *
     * @param reminder
     * The reminder
     */
    public void setReminder(List<Reminder> reminder) {
        this.reminder = reminder;
    }
}
