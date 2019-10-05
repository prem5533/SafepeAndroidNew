package com.safepayu.wallet.models.response;

public class HashKeyResponse {


    /**
     * status : true
     * message : success
     * hash : qtf4gk39ke20191004101032100hellsandeepsandeep@gmail.comj2RR7XSYNRQTF4GK39KE
     * merchant_key : QTF4GK39KE
     * merchant_salt : J2RR7XSYNR
     * transactionId : 20191004101032
     */

    private boolean status;
    private String message;
    private String hash;
    private String merchant_key;
    private String merchant_salt;
    private String transactionId;

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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getMerchant_key() {
        return merchant_key;
    }

    public void setMerchant_key(String merchant_key) {
        this.merchant_key = merchant_key;
    }

    public String getMerchant_salt() {
        return merchant_salt;
    }

    public void setMerchant_salt(String merchant_salt) {
        this.merchant_salt = merchant_salt;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
