package com.safepayu.wallet.models.request;

public class SendPaymentGatewayDetailsRequest {

    /**
     * user_id : u8750110867
     * bank_ref_no : 23512351
     * transaction_id : 34513451
     * description : csd;f asdfas
     * opration : credit
     * amount : 5900
     * mode_of_payment : Credit card
     * status : success
     * type : bank
     * easy_pay_id : 221311
     * package_amount : 5900
     * package_id : 5
     */

    private String user_id;
    private String bank_ref_no;
    private String transaction_id;
    private String description;
    private String opration;
    private String amount;
    private String mode_of_payment;
    private String status;
    private String type;
    private String easy_pay_id;
    private String package_amount;
    private String package_id;
    private String ecom;

    public String getEcom() {
        return ecom;
    }

    public void setEcom(String ecom) {
        this.ecom = ecom;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBank_ref_no() {
        return bank_ref_no;
    }

    public void setBank_ref_no(String bank_ref_no) {
        this.bank_ref_no = bank_ref_no;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOpration() {
        return opration;
    }

    public void setOpration(String opration) {
        this.opration = opration;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMode_of_payment() {
        return mode_of_payment;
    }

    public void setMode_of_payment(String mode_of_payment) {
        this.mode_of_payment = mode_of_payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEasy_pay_id() {
        return easy_pay_id;
    }

    public void setEasy_pay_id(String easy_pay_id) {
        this.easy_pay_id = easy_pay_id;
    }

    public String getPackage_amount() {
        return package_amount;
    }

    public void setPackage_amount(String package_amount) {
        this.package_amount = package_amount;
    }

    public String getPackage_id() {
        return package_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }
}
