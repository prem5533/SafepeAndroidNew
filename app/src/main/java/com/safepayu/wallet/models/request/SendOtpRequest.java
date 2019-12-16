package com.safepayu.wallet.models.request;

public class SendOtpRequest {


    /**
     * mobile : 9999988888
     * type : 1
     */

    private String mobile;
    private String type;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
