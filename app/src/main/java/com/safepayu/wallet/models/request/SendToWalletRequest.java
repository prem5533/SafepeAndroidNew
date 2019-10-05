package com.safepayu.wallet.models.request;

public class SendToWalletRequest {

    /**
     * user_id : u9811871855
     * mobile : 8602278049
     * amount : 300
     */

    private String user_id;
    private String mobile;
    private String amount;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
