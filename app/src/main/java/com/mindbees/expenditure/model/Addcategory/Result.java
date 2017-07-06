package com.mindbees.expenditure.model.Addcategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by User on 14-10-2016.
 */

public class Result implements Serializable{
    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("game_id")
    @Expose
    private String gameId;

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
     * The gameId
     */
    public String getGameId() {
        return gameId;
    }

    /**
     *
     * @param gameId
     * The game_id
     */
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
