package com.safepayu.wallet.models.response;

import java.util.List;

public class RechargeHistoryResponse {


    /**
     * status : true
     * message : Recharge History Successfully
     * data : [{"id":1,"userid":"u9811871855","TransactionID":"86540199","UtransactionID":"Ymdhis","OperatorID":"GJR1910041729270068","transaction_id":null,"rech_type":1,"number_type":1,"number":"9811871855","amount":10,"operator_id":8,"status":1,"created_at":"2019-10-04 11:59:27","updated_at":"2019-10-04 11:59:27"}]
     */

    private boolean status;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * userid : u9811871855
         * TransactionID : 86540199
         * UtransactionID : Ymdhis
         * OperatorID : GJR1910041729270068
         * transaction_id : null
         * rech_type : 1
         * number_type : 1
         * number : 9811871855
         * amount : 10
         * operator_id : 8
         * status : 1
         * created_at : 2019-10-04 11:59:27
         * updated_at : 2019-10-04 11:59:27
         */

        private int id;
        private String userid;
        private String TransactionID;
        private String UtransactionID;
        private String OperatorID;
        private Object transaction_id;
        private int rech_type;
        private int number_type;
        private String number;
        private int amount;
        private int operator_id;
        private int status;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getTransactionID() {
            return TransactionID;
        }

        public void setTransactionID(String TransactionID) {
            this.TransactionID = TransactionID;
        }

        public String getUtransactionID() {
            return UtransactionID;
        }

        public void setUtransactionID(String UtransactionID) {
            this.UtransactionID = UtransactionID;
        }

        public String getOperatorID() {
            return OperatorID;
        }

        public void setOperatorID(String OperatorID) {
            this.OperatorID = OperatorID;
        }

        public Object getTransaction_id() {
            return transaction_id;
        }

        public void setTransaction_id(Object transaction_id) {
            this.transaction_id = transaction_id;
        }

        public int getRech_type() {
            return rech_type;
        }

        public void setRech_type(int rech_type) {
            this.rech_type = rech_type;
        }

        public int getNumber_type() {
            return number_type;
        }

        public void setNumber_type(int number_type) {
            this.number_type = number_type;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getOperator_id() {
            return operator_id;
        }

        public void setOperator_id(int operator_id) {
            this.operator_id = operator_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}
