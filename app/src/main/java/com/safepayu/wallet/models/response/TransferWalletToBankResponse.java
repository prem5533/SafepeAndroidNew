package com.safepayu.wallet.models.response;

public class TransferWalletToBankResponse {


    /**
     * status : true
     * transaction : pending
     * message : Transfer request pending at the bank
     * referenceId : 58559570
     * transactionId : 20191012184606
     * date : 2019-10-12 18:46:08
     * statusCode : 2
     */

    private boolean status;
    private String transaction;
    private String message;
    private String referenceId;
    private String transactionId;
    private String date;
    private int statusCode;

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

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
