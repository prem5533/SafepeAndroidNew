package com.safepayu.wallet.models.response;

import java.util.List;

public class MyOrderResponse {

    /**
     * status : true
     * message : Data get Successfully!
     * BankToWallet : [{"id":1,"userid":"u9811871855","bank_ref_no":"192970976348","transaction_id":"20191024164037","amount":"1.00","opration":"Debit","wallet_no":"sp_9811871855","description":"Buy Package PROMOTIONAL","mode_of_payment":"DC","type":"bank","easy_pay_id":"TC4D5E8RR9","package_id":"2","package_amount":"1.00","status":1,"created_at":"2019-10-24 16:41:49","updated_at":"2019-10-24 16:41:49"},{"id":2,"userid":"u9811871855","bank_ref_no":"192970976348","transaction_id":"20191024164037","amount":"1.00","opration":"Debit","wallet_no":"sp_9811871855","description":"Buy Package PROMOTIONAL","mode_of_payment":"DC","type":"bank","easy_pay_id":"TC4D5E8RR9","package_id":"2","package_amount":"1.00","status":1,"created_at":"2019-10-24 16:41:49","updated_at":"2019-10-24 16:41:49"}]
     */

    private boolean status;
    private String message;
    private List<BankToWalletBean> BankToWallet;

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

    public List<BankToWalletBean> getBankToWallet() {
        return BankToWallet;
    }

    public void setBankToWallet(List<BankToWalletBean> BankToWallet) {
        this.BankToWallet = BankToWallet;
    }

    public static class BankToWalletBean {
        /**
         * id : 1
         * userid : u9811871855
         * bank_ref_no : 192970976348
         * transaction_id : 20191024164037
         * amount : 1.00
         * opration : Debit
         * wallet_no : sp_9811871855
         * description : Buy Package PROMOTIONAL
         * mode_of_payment : DC
         * type : bank
         * easy_pay_id : TC4D5E8RR9
         * package_id : 2
         * package_amount : 1.00
         * status : 1
         * created_at : 2019-10-24 16:41:49
         * updated_at : 2019-10-24 16:41:49
         */

        private int id;
        private String userid;
        private String bank_ref_no;
        private String transaction_id;
        private String amount;
        private String opration;
        private String wallet_no;
        private String description;
        private String mode_of_payment;
        private String type;
        private String easy_pay_id;
        private String package_id;
        private String package_amount;
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

        public String getBank_ref_no() {
            return bank_ref_no;
        }

        public void setBank_ref_no(String bank_ref_no) {
            this.bank_ref_no = bank_ref_no;
        }

        public String getTransaction_id() {
            return transaction_id;
        }

        public void setTransaction_id(String transaction_id) {
            this.transaction_id = transaction_id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getOpration() {
            return opration;
        }

        public void setOpration(String opration) {
            this.opration = opration;
        }

        public String getWallet_no() {
            return wallet_no;
        }

        public void setWallet_no(String wallet_no) {
            this.wallet_no = wallet_no;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMode_of_payment() {
            return mode_of_payment;
        }

        public void setMode_of_payment(String mode_of_payment) {
            this.mode_of_payment = mode_of_payment;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getEasy_pay_id() {
            return easy_pay_id;
        }

        public void setEasy_pay_id(String easy_pay_id) {
            this.easy_pay_id = easy_pay_id;
        }

        public String getPackage_id() {
            return package_id;
        }

        public void setPackage_id(String package_id) {
            this.package_id = package_id;
        }

        public String getPackage_amount() {
            return package_amount;
        }

        public void setPackage_amount(String package_amount) {
            this.package_amount = package_amount;
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
