package com.safepayu.wallet.models.response;

public class CommissionWalletTransferResponse {

    /**
     * status : true
     * message : Send Successfully.
     * UtrId : 20191018185844
     * date : 2019-10-18 18:58:44
     */

    private boolean status;
    private String message;
    private String UtrId;
    private String date;

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

    public String getUtrId() {
        return UtrId;
    }

    public void setUtrId(String UtrId) {
        this.UtrId = UtrId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
