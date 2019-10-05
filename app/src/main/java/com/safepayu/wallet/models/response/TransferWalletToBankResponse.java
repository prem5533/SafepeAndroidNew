package com.safepayu.wallet.models.response;

public class TransferWalletToBankResponse {


    /**
     * status : false
     * transaction : failed
     * message : Beneficiary does not exist
     */

    private boolean status;
    private String transaction;
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
