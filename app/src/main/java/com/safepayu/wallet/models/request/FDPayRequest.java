package com.safepayu.wallet.models.request;

public class FDPayRequest {

    /**
     * transaction_type : 1
     * buy_date : 2019-09-10 10:25:40
     * payment_mode : Bank-Challan
     * refrence_no : 123456789012--amt-20
     * document_attached :
     * paid_to_account : Hixson Technologies
     * paid_from_account : 9807785349
     * package_amount : 1000
     * refer : fd8376097766
     * amount : 2000
     */

    private String transaction_type;
    private String buy_date;
    private String payment_mode;
    private String refrence_no;
    private String document_attached;
    private String paid_to_account;
    private String paid_from_account;
    private String package_amount;
    private String refer;
    private String amount;
    private String interestRateId;


    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
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

    public String getPackage_amount() {
        return package_amount;
    }

    public void setPackage_amount(String package_amount) {
        this.package_amount = package_amount;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setInterestRateId(String interestRateId) {
        this.interestRateId = interestRateId;
    }
}
