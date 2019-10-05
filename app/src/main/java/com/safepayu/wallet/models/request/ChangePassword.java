package com.safepayu.wallet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePassword {

    @SerializedName("user_id")
    @Expose
    String user_id;
    @SerializedName("current")
    @Expose
    String current;
    @SerializedName("password")
    @Expose
    String password;
    @SerializedName("password_confirmation")
    @Expose
    String password_confirmation;

    public ChangePassword(String user_id, String current, String password, String password_confirmation) {
        this.user_id = user_id;
        this.current = current;
        this.password = password;
        this.password_confirmation = password_confirmation;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }
}
