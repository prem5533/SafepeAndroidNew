package com.safepayu.wallet.models.response;

import java.util.List;

public class OperatorResponse {

    /**
     * status : true
     * message : Operators List.
     * operators : [{"id":"0","image":"","operator_code":"0","name":"Select Operator","govcharge":""},{"id":1,"operator_name":"Airtel","operator_code":"1","image":"uploaded/operator/airtel.jpg","govcharge":0},{"id":2,"operator_name":"Bsnl Topup","operator_code":"4","image":"uploaded/operator/bsnl.jpg","govcharge":0},{"id":3,"operator_name":"Tata Docomo Topup","operator_code":"7","image":"uploaded/operator/docomo.jpg","govcharge":0},{"id":4,"operator_name":"Idea","operator_code":"3","image":"uploaded/operator/idea.jpg","govcharge":0},{"id":5,"operator_name":"MTNL DL Topup","operator_code":"17","image":null,"govcharge":0},{"id":6,"operator_name":"TATA INDICOM","operator_code":"9","image":null,"govcharge":0},{"id":7,"operator_name":"UNINOR","operator_code":"12","image":null,"govcharge":0},{"id":8,"operator_name":"Vodafone","operator_code":"10","image":"uploaded/operator/vodafone.png","govcharge":0},{"id":10,"operator_name":"JIO","operator_code":"93","image":"uploaded/operator/jio.jpg","govcharge":0},{"id":83,"operator_name":"Bsnl Special","operator_code":"5","image":"uploaded/operator/bsnl.jpg","govcharge":0},{"id":84,"operator_name":"Tata Docomo Special","operator_code":"8","image":"uploaded/operator/docomo.jpg","govcharge":0},{"id":85,"operator_name":"Tata Indicom","operator_code":"9","image":"uploaded/operator/docomo.jpg","govcharge":0},{"id":86,"operator_name":"MTNL DL Special","operator_code":"18","image":null,"govcharge":0},{"id":87,"operator_name":"MTNL Mumbai","operator_code":"19","image":null,"govcharge":0},{"id":88,"operator_name":"MTNL Mumbai Special","operator_code":"20","image":null,"govcharge":0}]
     * history : [{"number":"9454756926","operator_id":"1","amount":10,"image":"uploaded/operator/airtel.jpg","operator_name":"Airtel","date":"15-12-2019 12:37:43"},{"number":"8375968883","operator_id":"1","amount":10,"image":"uploaded/operator/airtel.jpg","operator_name":"Airtel","date":"12-01-2020 12:31:59"}]
     */

    private boolean status;
    private String message;
    private List<OperatorsBean> operators;
    private List<HistoryBean> history;

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

    public List<HistoryBean> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryBean> history) {
        this.history = history;
    }

    public static class OperatorsBean {
        /**
         * id : 0
         * image :
         * operator_code : 0
         * name : Select Operator
         * govcharge :
         * operator_name : Airtel
         */

        private String id;
        private String image;
        private String operator_code;
        private String name;
        private String govcharge;
        private String operator_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getOperator_code() {
            return operator_code;
        }

        public void setOperator_code(String operator_code) {
            this.operator_code = operator_code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGovcharge() {
            return govcharge;
        }

        public void setGovcharge(String govcharge) {
            this.govcharge = govcharge;
        }

        public String getOperator_name() {
            return operator_name;
        }

        public void setOperator_name(String operator_name) {
            this.operator_name = operator_name;
        }
    }

    public static class HistoryBean {
        /**
         * number : 9454756926
         * operator_id : 1
         * amount : 10
         * image : uploaded/operator/airtel.jpg
         * operator_name : Airtel
         * date : 15-12-2019 12:37:43
         */

        private String number;
        private String operator_id;
        private int amount;
        private String image;
        private String operator_name;
        private String date;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getOperator_id() {
            return operator_id;
        }

        public void setOperator_id(String operator_id) {
            this.operator_id = operator_id;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getOperator_name() {
            return operator_name;
        }

        public void setOperator_name(String operator_name) {
            this.operator_name = operator_name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
