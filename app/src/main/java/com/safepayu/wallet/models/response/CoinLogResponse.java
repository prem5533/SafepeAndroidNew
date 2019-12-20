package com.safepayu.wallet.models.response;

import java.util.List;

public class CoinLogResponse {

    /**
     * status : true
     * message : Coin log retrieved successfully
     * data : {"coinWallet":"10.00","log":[{"id":1,"userid":"u8375890846","transaction_no":"156688702052","description":"Coin ","operation":"debit","amount":5,"wallet_no":"sp_8376097766","mode_payment":"","easy_pay_id":"","type":"","Bank_ref_no":"","ip":null,"status":0,"created_at":"2019-08-27 06:23:40","updated_at":"2019-12-18 09:40:17"},{"id":2,"userid":"u8375890846","transaction_no":"156688702052","description":"Coin ","operation":"debit","amount":4,"wallet_no":"sp_8376097766","mode_payment":"","easy_pay_id":"","type":"","Bank_ref_no":"","ip":null,"status":0,"created_at":"2019-08-27 06:23:40","updated_at":"2019-12-18 09:40:20"},{"id":3,"userid":"u8375890846","transaction_no":"156688702052","description":"Coin ","operation":"debit","amount":1,"wallet_no":"sp_8376097766","mode_payment":"","easy_pay_id":"","type":"","Bank_ref_no":"","ip":null,"status":1,"created_at":"2019-08-27 06:23:40","updated_at":"2019-12-18 09:40:22"}]}
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
         * coinWallet : 10.00
         * log : [{"id":1,"userid":"u8375890846","transaction_no":"156688702052","description":"Coin ","operation":"debit","amount":5,"wallet_no":"sp_8376097766","mode_payment":"","easy_pay_id":"","type":"","Bank_ref_no":"","ip":null,"status":0,"created_at":"2019-08-27 06:23:40","updated_at":"2019-12-18 09:40:17"},{"id":2,"userid":"u8375890846","transaction_no":"156688702052","description":"Coin ","operation":"debit","amount":4,"wallet_no":"sp_8376097766","mode_payment":"","easy_pay_id":"","type":"","Bank_ref_no":"","ip":null,"status":0,"created_at":"2019-08-27 06:23:40","updated_at":"2019-12-18 09:40:20"},{"id":3,"userid":"u8375890846","transaction_no":"156688702052","description":"Coin ","operation":"debit","amount":1,"wallet_no":"sp_8376097766","mode_payment":"","easy_pay_id":"","type":"","Bank_ref_no":"","ip":null,"status":1,"created_at":"2019-08-27 06:23:40","updated_at":"2019-12-18 09:40:22"}]
         */

        private String coinWallet;
        private List<LogBean> log;

        public String getCoinWallet() {
            return coinWallet;
        }

        public void setCoinWallet(String coinWallet) {
            this.coinWallet = coinWallet;
        }

        public List<LogBean> getLog() {
            return log;
        }

        public void setLog(List<LogBean> log) {
            this.log = log;
        }

        public static class LogBean {
            /**
             * id : 1
             * userid : u8375890846
             * transaction_no : 156688702052
             * description : Coin
             * operation : debit
             * amount : 5
             * wallet_no : sp_8376097766
             * mode_payment :
             * easy_pay_id :
             * type :
             * Bank_ref_no :
             * ip : null
             * status : 0
             * created_at : 2019-08-27 06:23:40
             * updated_at : 2019-12-18 09:40:17
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
