package com.safepayu.wallet.models.response;

public class BuyPackageResponse {

    /**
     * status : true
     * data : {"package_id":"4","userid":"u8375968388","package_amount":25000,"bonus_amount":50000,"balance_amount":50000,"total_amount":29500,"tax":"18.00","buy_date":"2019-09-10","payment_mode":"Bank-Challan","refrence_no":"123456789012--amt-20","document_attached":null,"paid_to_account":"Hixson Technologies","paid_from_account":"9807785349","updated_at":"2020-02-05 18:16:33","created_at":"2020-02-05 18:16:33","id":1105,"footnote":"Your package will be approved within 3 hrs.","safepetransactionId":"20200205181633"}
     * message : Package purched Successfully.
     */

    private boolean status;
    private DataBean data;
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * package_id : 4
         * userid : u8375968388
         * package_amount : 25000
         * bonus_amount : 50000
         * balance_amount : 50000
         * total_amount : 29500
         * tax : 18.00
         * buy_date : 2019-09-10
         * payment_mode : Bank-Challan
         * refrence_no : 123456789012--amt-20
         * document_attached : null
         * paid_to_account : Hixson Technologies
         * paid_from_account : 9807785349
         * updated_at : 2020-02-05 18:16:33
         * created_at : 2020-02-05 18:16:33
         * id : 1105
         * footnote : Your package will be approved within 3 hrs.
         * safepetransactionId : 20200205181633
         */

        private String package_id;
        private String userid;
        private int package_amount;
        private int bonus_amount;
        private int balance_amount;
        private int total_amount;
        private String tax;
        private String buy_date;
        private String payment_mode;
        private String refrence_no;
        private Object document_attached;
        private String paid_to_account;
        private String paid_from_account;
        private String updated_at;
        private String created_at;
        private int id;
        private String footnote;
        private String safepetransactionId;

        public String getPackage_id() {
            return package_id;
        }

        public void setPackage_id(String package_id) {
            this.package_id = package_id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public int getPackage_amount() {
            return package_amount;
        }

        public void setPackage_amount(int package_amount) {
            this.package_amount = package_amount;
        }

        public int getBonus_amount() {
            return bonus_amount;
        }

        public void setBonus_amount(int bonus_amount) {
            this.bonus_amount = bonus_amount;
        }

        public int getBalance_amount() {
            return balance_amount;
        }

        public void setBalance_amount(int balance_amount) {
            this.balance_amount = balance_amount;
        }

        public int getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(int total_amount) {
            this.total_amount = total_amount;
        }

        public String getTax() {
            return tax;
        }

        public void setTax(String tax) {
            this.tax = tax;
        }

        public String getBuy_date() {
            return buy_date;
        }

        public void setBuy_date(String buy_date) {
            this.buy_date = buy_date;
        }

        public String getPayment_mode() {
            return payment_mode;
        }

        public void setPayment_mode(String payment_mode) {
            this.payment_mode = payment_mode;
        }

        public String getRefrence_no() {
            return refrence_no;
        }

        public void setRefrence_no(String refrence_no) {
            this.refrence_no = refrence_no;
        }

        public Object getDocument_attached() {
            return document_attached;
        }

        public void setDocument_attached(Object document_attached) {
            this.document_attached = document_attached;
        }

        public String getPaid_to_account() {
            return paid_to_account;
        }

        public void setPaid_to_account(String paid_to_account) {
            this.paid_to_account = paid_to_account;
        }

        public String getPaid_from_account() {
            return paid_from_account;
        }

        public void setPaid_from_account(String paid_from_account) {
            this.paid_from_account = paid_from_account;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFootnote() {
            return footnote;
        }

        public void setFootnote(String footnote) {
            this.footnote = footnote;
        }

        public String getSafepetransactionId() {
            return safepetransactionId;
        }

        public void setSafepetransactionId(String safepetransactionId) {
            this.safepetransactionId = safepetransactionId;
        }
    }
}
