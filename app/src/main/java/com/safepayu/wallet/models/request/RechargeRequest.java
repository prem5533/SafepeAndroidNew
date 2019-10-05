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
     */

    private String recharge_type;
    private String number;
    private String amount;
    private String operator_code;
    private String circle_code;
    private String operator_id;

    public String getRecharge_type() {
        return recharge_type;
    }

    public void setRecharge_type(String recharge_type) {
        this.recharge_type = recharge_type;
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
}
