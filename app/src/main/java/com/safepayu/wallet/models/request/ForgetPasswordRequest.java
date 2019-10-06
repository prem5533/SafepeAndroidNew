package com.safepayu.wallet.models.request;

public class ForgetPasswordRequest {

    /**
     * mobile : 9811871855
     * otp : 4607
     * password : 123456789
     * password_confirmation : 123456789
     */

    private String mobile;
    private int otp;
    private String password;
    private String password_confirmation;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
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
