package com.safepayu.wallet.models.response;

public class CustOperatorResponse {

    /**
     * status : true
     * message : Operator details.
     * operator : {"number":"8602278049","operator_name":"Airtel","operator_code":"13"}
     */

    private boolean status;
    private String message;
    private OperatorBean operator;

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

    public OperatorBean getOperator() {
        return operator;
    }

    public void setOperator(OperatorBean operator) {
        this.operator = operator;
    }

    public static class OperatorBean {
        /**
         * number : 8602278049
         * operator_name : Airtel
         * operator_code : 13
         */

        private String number;
        private String operator_name;
        private String operator_code;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getOperator_name() {
            return operator_name;
        }

        public void setOperator_name(String operator_name) {
            this.operator_name = operator_name;
        }

        public String getOperator_code() {
            return operator_code;
        }

        public void setOperator_code(String operator_code) {
            this.operator_code = operator_code;
        }
    }
}
