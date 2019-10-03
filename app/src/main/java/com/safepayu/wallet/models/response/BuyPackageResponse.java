package com.safepayu.wallet.models.response;

public class BuyPackageResponse {

    /**
     * status : true
     * message : Package purched Successfully.
     */

    private boolean status;
    private String message;

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
}
