package com.mindbees.expenditure.model.Getprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by User on 15-10-2016.
 */

public class User implements Serializable {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("fb_id")
    @Expose
    private String fbId;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("user_password")
    @Expose
    private String userPassword;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("currency_id")
    @Expose
    private String currencyId;
    @SerializedName("registered_date")
    @Expose
    private String registeredDate;
    @SerializedName("user_status")
    @Expose
    private String userStatus;
    @SerializedName("last_login_time")
    @Expose
    private String lastLoginTime;
    @SerializedName("user_photo")
    @Expose
    private String userPhoto;
    @SerializedName("country_name")
    @Expose
    private String countryName;

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
     * The fbId
     */
    public String getFbId() {
        return fbId;
    }

    /**
     *
     * @param fbId
     * The fb_id
     */
    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    /**
     *
     * @return
     * The fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     *
     * @param fullName
     * The full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     *
     * @return
     * The userPassword
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     *
     * @param userPassword
     * The user_password
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     *
     * @return
     * The userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     *
     * @param userEmail
     * The user_email
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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
     * The currencyId
     */
    public String getCurrencyId() {
        return currencyId;
    }

    /**
     *
     * @param currencyId
     * The currency_id
     */
    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    /**
     *
     * @return
     * The registeredDate
     */
    public String getRegisteredDate() {
        return registeredDate;
    }

    /**
     *
     * @param registeredDate
     * The registered_date
     */
    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    /**
     *
     * @return
     * The userStatus
     */
    public String getUserStatus() {
        return userStatus;
    }

    /**
     *
     * @param userStatus
     * The user_status
     */
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    /**
     *
     * @return
     * The lastLoginTime
     */
    public String getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     *
     * @param lastLoginTime
     * The last_login_time
     */
    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     *
     * @return
     * The userPhoto
     */
    public String getUserPhoto() {
        return userPhoto;
    }

    /**
     *
     * @param userPhoto
     * The user_photo
     */
    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    /**
     *
     * @return
     * The countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     *
     * @param countryName
     * The country_name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
