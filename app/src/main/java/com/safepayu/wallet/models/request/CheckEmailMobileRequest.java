package com.safepayu.wallet.models.request;

public class CheckEmailMobileRequest {

    /**
     * mobile : 9454756926
     * email : kietsandeepkumar@gmail.com
     * type : 2
     */

    private String mobile;
    private String email;
    private String type;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
