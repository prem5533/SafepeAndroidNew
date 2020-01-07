package com.safepayu.wallet.models.response;

import java.util.List;

public class OperatorResponse {

    /**
     * status : true
     * message : Operators List.
     * operators : [{"id":1,"operator_name":"Airtel","operator_code":"12"},{"id":2,"operator_name":"Vodafone","operator_code":"13"},{"id":3,"operator_name":"Aircel","operator_code":"14"},{"id":4,"operator_name":"Bsnl topup","operator_code":"16"},{"id":5,"operator_name":"Virgin gsm","operator_code":"17"},{"id":6,"operator_name":"Docomo","operator_code":"18"},{"id":7,"operator_name":"Videocon","operator_code":"19"},{"id":8,"operator_name":"Idea","operator_code":"23"},{"id":9,"operator_name":"Jio","operator_code":"147"}]
     */

    private boolean status;
    private String message;
    private List<OperatorsBean> operators;

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

    public List<OperatorsBean> getOperators() {
        return operators;
    }

    public void setOperators(List<OperatorsBean> operators) {
        this.operators = operators;
    }

    public static class OperatorsBean {
        /**
         * id : 1
         * operator_name : Airtel
         * operator_code : 12
         */


        private int id;
        private String operator_name;
        private String operator_code;
        private String image;
        /**
         * govcharge : 22
         */

        private String govcharge;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getGovcharge() {
            return govcharge;
        }

        public void setGovcharge(String govcharge) {
            this.govcharge = govcharge;
        }
    }
}
