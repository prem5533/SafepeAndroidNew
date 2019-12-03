package com.safepayu.wallet.models.request;

public class HashKeyRequest {


    /**
     * merchant_payment_amount : 100
     * merchant_productInfo : hell
     * customer_firstName : sandeep
     * customer_email_id : sandeep@gmail.com
     */

    private String merchant_payment_amount;
    private String merchant_productInfo;
    private String customer_firstName;
    private String customer_email_id;

    private String type;

    public String getMerchant_payment_amount() {
        return merchant_payment_amount;
    }

    public void setMerchant_payment_amount(String merchant_payment_amount) {
        this.merchant_payment_amount = merchant_payment_amount;
    }

    public String getMerchant_productInfo() {
        return merchant_productInfo;
    }

    public void setMerchant_productInfo(String merchant_productInfo) {
        this.merchant_productInfo = merchant_productInfo;
    }

    public String getCustomer_firstName() {
        return customer_firstName;
    }

    public void setCustomer_firstName(String customer_firstName) {
        this.customer_firstName = customer_firstName;
    }

    public String getCustomer_email_id() {
        return customer_email_id;
    }

    public void setCustomer_email_id(String customer_email_id) {
        this.customer_email_id = customer_email_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
