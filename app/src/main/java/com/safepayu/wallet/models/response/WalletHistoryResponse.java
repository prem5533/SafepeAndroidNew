package com.safepayu.wallet.models.response;

import java.util.List;

public class WalletHistoryResponse {

    /**
     * status : true
     * message : Wallet History Successfully
     * data : [{"id":1,"userid":"u9811871855","transaction_no":"20191002093402","description":"Withdrawl from safepe Wallet","operation":"debit","amount":59000,"wallet_no":"sp_9811871855","mode_payment":"''","easy_pay_id":"''","type":"''","Bank_ref_no":"''","ip":null,"status":1,"pending_status":0,"created_at":"2019-10-02 09:34:02","updated_at":"2019-10-02 09:34:02"},{"id":2,"userid":"u9811871855","transaction_no":"20191002093519","description":"Withdrawl from safepe Wallet","operation":"debit","amount":5900,"wallet_no":"sp_9811871855","mode_payment":"''","easy_pay_id":"''","type":"''","Bank_ref_no":"''","ip":null,"status":1,"pending_status":0,"created_at":"2019-10-02 09:35:19","updated_at":"2019-10-02 09:35:19"},{"id":3,"userid":"u9811871855","transaction_no":"20191002093801","description":"Withdrawl from safepe Wallet","operation":"debit","amount":5900,"wallet_no":"sp_9811871855","mode_payment":"''","easy_pay_id":"''","type":"''","Bank_ref_no":"''","ip":null,"status":1,"pending_status":0,"created_at":"2019-10-02 09:38:01","updated_at":"2019-10-02 09:38:01"},{"id":4,"userid":"u9811871855","transaction_no":"20191002093845","description":"Withdrawl from safepe Wallet","operation":"debit","amount":5900,"wallet_no":"sp_9811871855","mode_payment":"''","easy_pay_id":"''","type":"''","Bank_ref_no":"''","ip":null,"status":1,"pending_status":0,"created_at":"2019-10-02 09:38:45","updated_at":"2019-10-02 09:38:45"},{"id":5,"userid":"u9811871855","transaction_no":"20191003103008","description":"wallet to wallet transaction","operation":"debite","amount":300,"wallet_no":"sp_9811871855","mode_payment":"''","easy_pay_id":"''","type":"''","Bank_ref_no":"''","ip":null,"status":1,"pending_status":0,"created_at":"2019-10-03 10:30:08","updated_at":"2019-10-03 10:30:08"},{"id":6,"userid":"u9811871855","transaction_no":"20191003103108","description":"wallet to wallet transaction","operation":"debite","amount":300,"wallet_no":"sp_9811871855","mode_payment":"''","easy_pay_id":"''","type":"''","Bank_ref_no":"''","ip":null,"status":1,"pending_status":0,"created_at":"2019-10-03 10:31:08","updated_at":"2019-10-03 10:31:08"},{"id":8,"userid":"u9811871855","transaction_no":"20191003111618","description":"wallet to wallet transaction","operation":"debite","amount":300,"wallet_no":"sp_9811871855","mode_payment":"''","easy_pay_id":"''","type":"''","Bank_ref_no":"''","ip":null,"status":1,"pending_status":0,"created_at":"2019-10-03 11:16:18","updated_at":"2019-10-03 11:16:18"},{"id":10,"userid":"u9811871855","transaction_no":"20191003111841","description":"wallet to wallet transaction","operation":"debite","amount":300,"wallet_no":"sp_9811871855","mode_payment":"''","easy_pay_id":"''","type":"''","Bank_ref_no":"''","ip":null,"status":1,"pending_status":0,"created_at":"2019-10-03 11:18:41","updated_at":"2019-10-03 11:18:41"},{"id":12,"userid":"u9811871855","transaction_no":"20191003112025","description":"wallet to wallet transaction","operation":"debite","amount":300,"wallet_no":"sp_9811871855","mode_payment":"''","easy_pay_id":"''","type":"''","Bank_ref_no":"''","ip":null,"status":1,"pending_status":0,"created_at":"2019-10-03 11:20:25","updated_at":"2019-10-03 11:20:25"},{"id":14,"userid":"u9811871855","transaction_no":"20191003112242","description":"wallet to wallet transaction","operation":"debite","amount":300,"wallet_no":"sp_9811871855","mode_payment":"''","easy_pay_id":"''","type":"''","Bank_ref_no":"''","ip":null,"status":1,"pending_status":0,"created_at":"2019-10-03 11:22:42","updated_at":"2019-10-03 11:22:42"},{"id":23,"userid":"u9811871855","transaction_no":"20191005143049","description":"commitionWallate to main wallet ","operation":"credit","amount":12900,"wallet_no":"sp_9811871855","mode_payment":"''","easy_pay_id":"''","type":"''","Bank_ref_no":"''","ip":null,"status":1,"pending_status":0,"created_at":"2019-10-05 14:30:49","updated_at":"2019-10-05 14:30:49"},{"id":24,"userid":"u9811871855","transaction_no":"20191005143251","description":"commitionWallate to main wallet ","operation":"credit","amount":100,"wallet_no":"sp_9811871855","mode_payment":"''","easy_pay_id":"''","type":"''","Bank_ref_no":"''","ip":null,"status":1,"pending_status":0,"created_at":"2019-10-05 14:32:51","updated_at":"2019-10-05 14:32:51"},{"id":29,"userid":"u9811871855","transaction_no":"20191008094026","description":"wallet to wallet transaction","operation":"debite","amount":100,"wallet_no":"sp_9811871855","mode_payment":"''","easy_pay_id":"''","type":"''","Bank_ref_no":"''","ip":null,"status":1,"pending_status":0,"created_at":"2019-10-08 09:40:26","updated_at":"2019-10-08 09:40:26"},{"id":31,"userid":"u9811871855","transaction_no":"20191008094106","description":"wallet to wallet transaction","operation":"debite","amount":100,"wallet_no":"sp_9811871855","mode_payment":"''","easy_pay_id":"''","type":"''","Bank_ref_no":"''","ip":null,"status":1,"pending_status":0,"created_at":"2019-10-08 09:41:06","updated_at":"2019-10-08 09:41:06"},{"id":33,"userid":"u9811871855","transaction_no":"20191008095408","description":"wallet to wallet transaction","operation":"debite","amount":100,"wallet_no":"sp_9811871855","mode_payment":"''","easy_pay_id":"''","type":"''","Bank_ref_no":"''","ip":null,"status":1,"pending_status":0,"created_at":"2019-10-08 09:54:08","updated_at":"2019-10-08 09:54:08"},{"id":35,"userid":"u9811871855","transaction_no":"20191008111924","description":"wallet to wallet transaction","operation":"debite","amount":100,"wallet_no":"sp_9811871855","mode_payment":"''","easy_pay_id":"''","type":"''","Bank_ref_no":"''","ip":null,"status":1,"pending_status":0,"created_at":"2019-10-08 11:19:24","updated_at":"2019-10-08 11:19:24"}]
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
         * mode_payment : ''
         * easy_pay_id : ''
         * type : ''
         * Bank_ref_no : ''
         * ip : null
         * status : 1
         * pending_status : 0
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
        private String mode_payment;
        private String easy_pay_id;
        private String type;
        private String Bank_ref_no;
        private Object ip;
        private int status;
        private int pending_status;
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

        public String getMode_payment() {
            return mode_payment;
        }

        public void setMode_payment(String mode_payment) {
            this.mode_payment = mode_payment;
        }

        public String getEasy_pay_id() {
            return easy_pay_id;
        }

        public void setEasy_pay_id(String easy_pay_id) {
            this.easy_pay_id = easy_pay_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBank_ref_no() {
            return Bank_ref_no;
        }

        public void setBank_ref_no(String Bank_ref_no) {
            this.Bank_ref_no = Bank_ref_no;
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

        public int getPending_status() {
            return pending_status;
        }

        public void setPending_status(int pending_status) {
            this.pending_status = pending_status;
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
