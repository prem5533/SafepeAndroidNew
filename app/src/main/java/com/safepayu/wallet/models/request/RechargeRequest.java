package com.safepayu.wallet.models.request;

public class RechargeRequest {


    /**
     * transaction_id : 61125939
     * recharge_type : 1
     * number_type : 1
     * number : 8602278049
     * amount : 1
     * operator_code : 13
     * circle_code : 51
     * operator_id : 1
     * payment_mode : bank
     * description : recharge
     */

    private String transaction_id;
    private String recharge_type;
    private String number_type;
    private String number;
    private String amount;
    private String operator_code;
    private String circle_code;
    private String operator_id;
    private String payment_mode;
    private String description;

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getRecharge_type() {
        return recharge_type;
    }

    public void setRecharge_type(String recharge_type) {
        this.recharge_type = recharge_type;
    }

    public String getNumber_type() {
        return number_type;
    }

    public void setNumber_type(String number_type) {
        this.number_type = number_type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOperator_code() {
        return operator_code;
    }

    public void setOperator_code(String operator_code) {
        this.operator_code = operator_code;
    }

    public String getCircle_code() {
        return circle_code;
    }

    public void setCircle_code(String circle_code) {
        this.circle_code = circle_code;
    }

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
