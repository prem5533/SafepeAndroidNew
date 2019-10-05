package com.safepayu.wallet.models.response;

import com.google.gson.annotations.SerializedName;

public class PackageDetailsResponse {


    /**
     * status : true
     * message : Latest User purchase package Details.
     * package : {"id":2,"package_name":"GOLD","package_id":4,"userid":"u8605278049","package_amount":50000,"bonus_amount":100000,"balance_amount":100000,"bonus_credited":0,"total_amount":59000,"tax":18,"buy_date":"2019-09-10 00:00:00","payment_mode":"Bank-Challan","document_attached":null}
     */

    private boolean status;
    private String message;
    @SerializedName("package")
    private PackageBean packageX;

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

    public PackageBean getPackageX() {
        return packageX;
    }

    public void setPackageX(PackageBean packageX) {
        this.packageX = packageX;
    }

    public static class PackageBean {
        /**
         * id : 2
         * package_name : GOLD
         * package_id : 4
         * userid : u8605278049
         * package_amount : 50000
         * bonus_amount : 100000
         * balance_amount : 100000
         * bonus_credited : 0
         * total_amount : 59000
         * tax : 18
         * buy_date : 2019-09-10 00:00:00
         * payment_mode : Bank-Challan
         * document_attached : null
         */

        private int id;
        private String package_name;
        private int package_id;
        private String userid;
        private int package_amount;
        private int bonus_amount;
        private int balance_amount;
        private int bonus_credited;
        private int total_amount;
        private int tax;
        private String buy_date;
        private String payment_mode;
        private Object document_attached;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPackage_name() {
            return package_name;
        }

        public void setPackage_name(String package_name) {
            this.package_name = package_name;
        }

        public int getPackage_id() {
            return package_id;
        }

        public void setPackage_id(int package_id) {
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

        public int getBonus_credited() {
            return bonus_credited;
        }

        public void setBonus_credited(int bonus_credited) {
            this.bonus_credited = bonus_credited;
        }

        public int getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(int total_amount) {
            this.total_amount = total_amount;
        }

        public int getTax() {
            return tax;
        }

        public void setTax(int tax) {
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

        public Object getDocument_attached() {
            return document_attached;
        }

        public void setDocument_attached(Object document_attached) {
            this.document_attached = document_attached;
        }
    }
}
