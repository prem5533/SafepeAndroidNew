package com.safepayu.wallet.models.response;

public class SendPaymentGatewayDetailsResponse {


    /**
     * status : true
     * statusCode : 0
     * message : Money added to Successfully.
     */

    private boolean status;
    private int statusCode;
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
