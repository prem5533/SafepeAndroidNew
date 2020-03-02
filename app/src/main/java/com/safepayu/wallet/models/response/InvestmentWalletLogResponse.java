package com.safepayu.wallet.models.response;

import java.util.List;

public class InvestmentWalletLogResponse {

    /**
     * status : true
     * message : Investment Logs
     * data : {"logList":[{"id":1,"userid":"u9811871855","transaction_no":"20200224180929","description":"Investment wallet to main wallet","operation":"debit","amount":9,"wallet_no":"sp_9811871855","mode_payment":"NULL","easy_pay_id":"NULL","type":"NULL","Bank_ref_no":"NULL","ip":null,"status":1,"pending_status":1,"created_at":"2020-02-24 18:09:29","updated_at":"2020-02-24 18:09:29"},{"id":2,"userid":"u9811871855","transaction_no":"20200224181657","description":"Investment wallet to main wallet","operation":"debit","amount":9,"wallet_no":"sp_9811871855","mode_payment":"NULL","easy_pay_id":"NULL","type":"NULL","Bank_ref_no":"NULL","ip":null,"status":1,"pending_status":1,"created_at":"2020-02-24 18:16:57","updated_at":"2020-02-24 18:16:57"},{"id":3,"userid":"u9811871855","transaction_no":"20200224204119","description":"Investment wallet to main wallet","operation":"debit","amount":10,"wallet_no":"sp_9811871855","mode_payment":null,"easy_pay_id":null,"type":null,"Bank_ref_no":null,"ip":null,"status":1,"pending_status":1,"created_at":"2020-02-24 20:41:19","updated_at":"2020-02-24 20:41:19"}],"amount":"9900"}
     */

    private boolean status;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * logList : [{"id":1,"userid":"u9811871855","transaction_no":"20200224180929","description":"Investment wallet to main wallet","operation":"debit","amount":9,"wallet_no":"sp_9811871855","mode_payment":"NULL","easy_pay_id":"NULL","type":"NULL","Bank_ref_no":"NULL","ip":null,"status":1,"pending_status":1,"created_at":"2020-02-24 18:09:29","updated_at":"2020-02-24 18:09:29"},{"id":2,"userid":"u9811871855","transaction_no":"20200224181657","description":"Investment wallet to main wallet","operation":"debit","amount":9,"wallet_no":"sp_9811871855","mode_payment":"NULL","easy_pay_id":"NULL","type":"NULL","Bank_ref_no":"NULL","ip":null,"status":1,"pending_status":1,"created_at":"2020-02-24 18:16:57","updated_at":"2020-02-24 18:16:57"},{"id":3,"userid":"u9811871855","transaction_no":"20200224204119","description":"Investment wallet to main wallet","operation":"debit","amount":10,"wallet_no":"sp_9811871855","mode_payment":null,"easy_pay_id":null,"type":null,"Bank_ref_no":null,"ip":null,"status":1,"pending_status":1,"created_at":"2020-02-24 20:41:19","updated_at":"2020-02-24 20:41:19"}]
         * amount : 9900
         */

        private String amount;
        private List<LogListBean> logList;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public List<LogListBean> getLogList() {
            return logList;
        }

        public void setLogList(List<LogListBean> logList) {
            this.logList = logList;
        }

        public static class LogListBean {
            /**
             * id : 1
             * userid : u9811871855
             * transaction_no : 20200224180929
             * description : Investment wallet to main wallet
             * operation : debit
             * amount : 9
             * wallet_no : sp_9811871855
             * mode_payment : NULL
             * easy_pay_id : NULL
             * type : NULL
             * Bank_ref_no : NULL
             * ip : null
             * status : 1
             * pending_status : 1
             * created_at : 2020-02-24 18:09:29
             * updated_at : 2020-02-24 18:09:29
             */

            private int id;
            private String userid;
            private String transaction_no;
            private String description;
            private String operation;
            private double amount;
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

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
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
}