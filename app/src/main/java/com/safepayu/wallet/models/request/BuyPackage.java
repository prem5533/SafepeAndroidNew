package com.safepayu.wallet.models.request;

public class BuyPackage {


    /**
     * transaction_type : 1
     * package_id : 4
     * buy_date : 2019-09-10 10:25:40
     * payment_mode : Bank-Challan
     * refrence_no : 123456789012--amt-20
     * document_attached :
     * paid_to_account : Hixson Technologies
     * paid_from_account : 9807785349
     */

    private String transaction_type;
    private String package_id;
    private String buy_date;
    private String payment_mode;
    private String refrence_no;
    private String document_attached;
    private String paid_to_account;
    private String paid_from_account;

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getPackage_id() {
        return package_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }

    public String getBuy_date() {
        return buy_date;
    }

    public void setBuy_date(String buy_date) {
        this.buy_date = buy_date;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getRefrence_no() {
        return refrence_no;
    }

    public void setRefrence_no(String refrence_no) {
        this.refrence_no = refrence_no;
    }

    public String getDocument_attached() {
        return document_attached;
    }

    public void setDocument_attached(String document_attached) {
        this.document_attached = document_attached;
    }

    public String getPaid_to_account() {
        return paid_to_account;
    }

    public void setPaid_to_account(String paid_to_account) {
        this.paid_to_account = paid_to_account;
    }

    public String getPaid_from_account() {
        return paid_from_account;
    }

    public void setPaid_from_account(String paid_from_account) {
        this.paid_from_account = paid_from_account;
    }
}
