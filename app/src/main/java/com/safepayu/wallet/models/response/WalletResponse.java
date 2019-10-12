package com.safepayu.wallet.models.response;

public class WalletResponse {

    /**
     * status : true
     * message : User Wallet Details.
     * wallet : {"id":1115,"userid":"u8376097766","number":"sp_8376097766","amount":626007.99,"status":1,"created_at":"2019-10-12 21:17:41","updated_at":"2019-10-12 21:17:41"}
     */

    private boolean status;
    private String message;
    private WalletBean wallet;

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

    public WalletBean getWallet() {
        return wallet;
    }

    public void setWallet(WalletBean wallet) {
        this.wallet = wallet;
    }

    public static class WalletBean {
        /**
         * id : 1115
         * userid : u8376097766
         * number : sp_8376097766
         * amount : 626007.99
         * status : 1
         * created_at : 2019-10-12 21:17:41
         * updated_at : 2019-10-12 21:17:41
         */

        private int id;
        private String userid;
        private String number;
        private double amount;
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

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
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
