package com.safepayu.wallet.models.response;

public class InvestmentReferResponse {

    /**
     * status : true
     * message : Referral found
     * data : Prem Singh
     */

    private boolean status;
    private String message;
    private String data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
