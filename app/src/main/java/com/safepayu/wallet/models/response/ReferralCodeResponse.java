package com.safepayu.wallet.models.response;

public class ReferralCodeResponse {


    /**
     * status : true
     * message : Referral Name
     * packages : sandeep kumar
     */

    private boolean status;
    private String message;
    private String packages;

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

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }
}
