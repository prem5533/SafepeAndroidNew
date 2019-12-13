package com.safepayu.wallet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequest {

    @SerializedName("current")
    @Expose
    String current;
    @SerializedName("password")
    @Expose
    String password;
    @SerializedName("password_confirmation")
    @Expose
    String password_confirmation;

    public ChangePasswordRequest(String current, String password, String password_confirmation) {
        this.current = current;
        this.password = password;
        this.password_confirmation = password_confirmation;
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
