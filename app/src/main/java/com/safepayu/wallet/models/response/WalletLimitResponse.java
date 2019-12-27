package com.safepayu.wallet.models.response;

public class WalletLimitResponse {
    /**
     * status : true
     * message : Maximum wallet withdrawal limit left
     * data : {"limit":7969.5,"wallet_balance":"2488.00"}
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
         * limit : 7969.5
         * wallet_balance : 2488.00
         */

        private double limit;
        private String wallet_balance;

        public double getLimit() {
            return limit;
        }

        public void setLimit(double limit) {
            this.limit = limit;
        }

        public String getWallet_balance() {
            return wallet_balance;
        }

        public void setWallet_balance(String wallet_balance) {
            this.wallet_balance = wallet_balance;
        }
    }

    /**
     * status : true
     * message : Maximum wallet withdrawal limit left
     * data : {"limit":8000,"wallet_balance":"0.00"}
     */
/*
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
        *//**
         * limit : 8000
         * wallet_balance : 0.00
         *//*

        private int limit;
        private String wallet_balance;

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public String getWallet_balance() {
            return wallet_balance;
        }

        public void setWallet_balance(String wallet_balance) {
            this.wallet_balance = wallet_balance;
        }
    }*/


}
