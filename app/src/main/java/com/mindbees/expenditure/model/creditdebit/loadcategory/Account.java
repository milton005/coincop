package com.mindbees.expenditure.model.creditdebit.loadcategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by User on 12-10-2016.
 */

public class Account implements Serializable{
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_title")
    @Expose
    private String categoryTitle;
    @SerializedName("type_id")
    @Expose
    private String typeId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("added_date")
    @Expose
    private String addedDate;
    @SerializedName("cat_image")
    @Expose
    private String catImage;
    @SerializedName("cat_color")
    @Expose
    private String catColor;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     *
     * @return
     * The categoryId
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     *
     * @param categoryId
     * The category_id
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     *
     * @return
     * The categoryTitle
     */
    public String getCategoryTitle() {
        return categoryTitle;
    }

    /**
     *
     * @param categoryTitle
     * The category_title
     */
    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    /**
     *
     * @return
     * The typeId
     */
    public String getTypeId() {
        return typeId;
    }

    /**
     *
     * @param typeId
     * The type_id
     */
    public void setTypeId(String typeId) {
        this.typeId = typeId;
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

    /**
     *
     * @return
     * The catImage
     */
    public String getCatImage() {
        return catImage;
    }

    /**
     *
     * @param catImage
     * The cat_image
     */
    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    /**
     *
     * @return
     * The catColor
     */
    public String getCatColor() {
        return catColor;
    }

    /**
     *
     * @param catColor
     * The cat_color
     */
    public void setCatColor(String catColor) {
        this.catColor = catColor;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;

    }
//    public boolean isSelected() {
//        return selected;
//    }
//
//    public void setSelected(boolean selected) {
//        this.selected = selected;
//    }
}
