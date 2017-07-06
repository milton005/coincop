package com.mindbees.expenditure.model.Getprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 15-10-2016.
 */

public class Modelgetprofile implements Serializable{
    @SerializedName("user")
    @Expose
    private List<User> user = new ArrayList<User>();

    /**
     *
     * @return
     * The user
     */
    public List<User> getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(List<User> user) {
        this.user = user;
    }

}
