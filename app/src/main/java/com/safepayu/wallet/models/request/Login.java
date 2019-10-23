package com.safepayu.wallet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("mobile")
    @Expose
    String mobile;
    @SerializedName("password")
    @Expose
    String password;
    @SerializedName("otp")
    @Expose
    String otp;
    @SerializedName("passcode")
    @Expose
    String passCode;
    @SerializedName("userid")
    @Expose
    String userId;
    /**
     * deviceid : CS123
     * rememberme : true
     */

    private String deviceid;
    private boolean rememberme;

    public Login() {
    }

    public Login(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    public Login(String mobile, String password,String deviceid, boolean rememberme) {
        this.mobile = mobile;
        this.password = password;
        this.deviceid = deviceid;
        this.rememberme = rememberme;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public boolean isRememberme() {
        return rememberme;
    }

    public void setRememberme(boolean rememberme) {
        this.rememberme = rememberme;
    }
}
