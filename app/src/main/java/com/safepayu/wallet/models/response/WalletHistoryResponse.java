package com.safepayu.wallet.models.response;

import java.util.List;

public class WalletHistoryResponse {

    /**
     * status : true
     * message : Wallet History Successfully
     * data : [{"id":1,"userid":"u9811871855","transaction_no":"20191002093402","description":"Withdrawl from safepe Wallet","operation":"debit","amount":59000,"wallet_no":"sp_9811871855","ip":null,"status":1,"created_at":"2019-10-02 09:34:02","updated_at":"2019-10-02 09:34:02"},{"id":2,"userid":"u9811871855","transaction_no":"20191002093519","description":"Withdrawl from safepe Wallet","operation":"debit","amount":5900,"wallet_no":"sp_9811871855","ip":null,"status":1,"created_at":"2019-10-02 09:35:19","updated_at":"2019-10-02 09:35:19"},{"id":3,"userid":"u9811871855","transaction_no":"20191002093801","description":"Withdrawl from safepe Wallet","operation":"debit","amount":5900,"wallet_no":"sp_9811871855","ip":null,"status":1,"created_at":"2019-10-02 09:38:01","updated_at":"2019-10-02 09:38:01"},{"id":4,"userid":"u9811871855","transaction_no":"20191002093845","description":"Withdrawl from safepe Wallet","operation":"debit","amount":5900,"wallet_no":"sp_9811871855","ip":null,"status":1,"created_at":"2019-10-02 09:38:45","updated_at":"2019-10-02 09:38:45"},{"id":8,"userid":"u9811871855","transaction_no":"20191006090109","description":"Beneficiary does not exist","operation":"debit","amount":2000,"wallet_no":"sp_9811871855","ip":null,"status":1,"created_at":"2019-10-06 09:01:09","updated_at":"2019-10-06 09:01:09"},{"id":9,"userid":"u9811871855","transaction_no":"20191006090125","description":"Beneficiary does not exist","operation":"debit","amount":2,"wallet_no":"sp_9811871855","ip":null,"status":1,"created_at":"2019-10-06 09:01:25","updated_at":"2019-10-06 09:01:25"}]
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
         * transaction_no : 20191002093402
         * description : Withdrawl from safepe Wallet
         * operation : debit
         * amount : 59000
         * wallet_no : sp_9811871855
         * ip : null
         * status : 1
         * created_at : 2019-10-02 09:34:02
         * updated_at : 2019-10-02 09:34:02
         */

        private int id;
        private String userid;
        private String transaction_no;
        private String description;
        private String operation;
        private int amount;
        private String wallet_no;
        private Object ip;
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

        public String getTransaction_no() {
            return transaction_no;
        }

        public void setTransaction_no(String transaction_no) {
            this.transaction_no = transaction_no;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getWallet_no() {
            return wallet_no;
        }

        public void setWallet_no(String wallet_no) {
            this.wallet_no = wallet_no;
        }

        public Object getIp() {
            return ip;
        }

        public void setIp(Object ip) {
            this.ip = ip;
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
