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
     * wallet_amount :
     * bank_amount :
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
    private String wallet_amount;
    private String bank_amount;
    /**
     * stdCode : 22
     * opvalue2 :
     * opvalue3 :
     * validityHours : 2
     */
    private String stdCode;
    private String opvalue2;
    private String opvalue3;
    private String validityHours;


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

    public String getStdCode() {
        return stdCode;
    }

    public void setStdCode(String stdCode) {
        this.stdCode = stdCode;
    }

    public String getOpvalue2() {
        return opvalue2;
    }

    public void setOpvalue2(String opvalue2) {
        this.opvalue2 = opvalue2;
    }

    public String getOpvalue3() {
        return opvalue3;
    }

    public void setOpvalue3(String opvalue3) {
        this.opvalue3 = opvalue3;
    }

    public String getWallet_amount() {
        return wallet_amount;
    }

    public void setWallet_amount(String wallet_amount) {
        this.wallet_amount = wallet_amount;
    }

    public String getBank_amount() {
        return bank_amount;
    }

    public void setBank_amount(String bank_amount) {
        this.bank_amount = bank_amount;
    }

    public String getValidityHours() {
        return validityHours;
    }

    public void setValidityHours(String validityHours) {
        this.validityHours = validityHours;
    }
}
