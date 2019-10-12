package com.safepayu.wallet.models.response;

public class SendToWalletResponse {

    /**
     * status : true
     * message : transaction Successfully.
     * UtrId : 20191012165743
     * data : 2019-10-12T11:27:43.000000Z
     */

    private boolean status;
    private String message;
    private String UtrId;
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

    public String getUtrId() {
        return UtrId;
    }

    public void setUtrId(String UtrId) {
        this.UtrId = UtrId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
