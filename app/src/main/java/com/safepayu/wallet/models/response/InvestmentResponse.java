package com.safepayu.wallet.models.response;

import java.util.List;

public class InvestmentResponse {

    /**
     * status : true
     * message : List of investments
     * data : {"investment":[{"id":1,"userid":"u9811871855","package_amount":1000,"bonus_amount":2000,"balance_amount":2000,"bonus_credited":0,"total_amount":1180,"tax":18,"buy_date":"2019-09-10 00:00:00","payment_mode":"Bank-Challan","refrence_no":"123456789012--amt-20","document_attached":null,"paid_to_account":"Hixson Technologies","paid_from_account":"sp_9811871855","status":0,"roi_status":0,"created_at":"2020-02-24 11:20:18","updated_at":"2020-02-24 11:20:18"},{"id":2,"userid":"u9811871855","package_amount":1000,"bonus_amount":2000,"balance_amount":2000,"bonus_credited":0,"total_amount":1180,"tax":18,"buy_date":"2019-09-10 00:00:00","payment_mode":"Bank-Challan","refrence_no":"123456789012--amt-20","document_attached":null,"paid_to_account":"Hixson Technologies","paid_from_account":"sp_9811871855","status":0,"roi_status":0,"created_at":"2020-02-24 11:20:21","updated_at":"2020-02-24 11:20:21"},{"id":3,"userid":"u9811871855","package_amount":1000,"bonus_amount":2000,"balance_amount":2000,"bonus_credited":0,"total_amount":1180,"tax":18,"buy_date":"2019-09-10 00:00:00","payment_mode":"Bank-Challan","refrence_no":"123456789012--amt-20","document_attached":null,"paid_to_account":"Hixson Technologies","paid_from_account":"sp_9811871855","status":0,"roi_status":0,"created_at":"2020-02-24 11:20:22","updated_at":"2020-02-24 11:20:22"},{"id":4,"userid":"u9811871855","package_amount":1000,"bonus_amount":2000,"balance_amount":2000,"bonus_credited":0,"total_amount":1180,"tax":18,"buy_date":"2019-09-10 00:00:00","payment_mode":"Bank-Challan","refrence_no":"123456789012--amt-20","document_attached":null,"paid_to_account":"Hixson Technologies","paid_from_account":"sp_9811871855","status":0,"roi_status":0,"created_at":"2020-02-24 11:20:23","updated_at":"2020-02-24 11:20:23"},{"id":5,"userid":"u9811871855","package_amount":1000,"bonus_amount":2000,"balance_amount":2000,"bonus_credited":0,"total_amount":1180,"tax":18,"buy_date":"2019-09-10 00:00:00","payment_mode":"Bank-Challan","refrence_no":"123456789012--amt-20","document_attached":null,"paid_to_account":"Hixson Technologies","paid_from_account":"sp_9811871855","status":0,"roi_status":0,"created_at":"2020-02-24 11:20:24","updated_at":"2020-02-24 11:20:24"},{"id":6,"userid":"u9811871855","package_amount":1000,"bonus_amount":2000,"balance_amount":2000,"bonus_credited":0,"total_amount":1180,"tax":18,"buy_date":"2019-09-10 00:00:00","payment_mode":"Bank-Challan","refrence_no":"123456789012--amt-20","document_attached":null,"paid_to_account":"Hixson Technologies","paid_from_account":"sp_9811871855","status":0,"roi_status":0,"created_at":"2020-02-24 11:20:25","updated_at":"2020-02-24 11:20:25"},{"id":7,"userid":"u9811871855","package_amount":1000,"bonus_amount":2000,"balance_amount":2000,"bonus_credited":0,"total_amount":1180,"tax":18,"buy_date":"2019-09-10 00:00:00","payment_mode":"Bank-Challan","refrence_no":"123456789012--amt-20","document_attached":null,"paid_to_account":"Hixson Technologies","paid_from_account":"sp_9811871855","status":0,"roi_status":0,"created_at":"2020-02-24 11:20:26","updated_at":"2020-02-24 11:20:26"},{"id":8,"userid":"u9811871855","package_amount":1000,"bonus_amount":2000,"balance_amount":2000,"bonus_credited":0,"total_amount":1180,"tax":18,"buy_date":"2019-09-10 00:00:00","payment_mode":"Bank-Challan","refrence_no":"123456789012--amt-20","document_attached":null,"paid_to_account":"Hixson Technologies","paid_from_account":"sp_9811871855","status":0,"roi_status":0,"created_at":"2020-02-24 11:20:27","updated_at":"2020-02-24 11:20:27"}],"instruction":["Earn free risk return of up to 7% p.a","No penalty on breaking Fixed deposit. Break the entire amount or just the amount you need!","Create Fixed Deposit anytime with a minimum of only Rs. 1,000","Check your interest earnings anytime"],"investment_total":9440}
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
         * investment : [{"id":1,"userid":"u9811871855","package_amount":1000,"bonus_amount":2000,"balance_amount":2000,"bonus_credited":0,"total_amount":1180,"tax":18,"buy_date":"2019-09-10 00:00:00","payment_mode":"Bank-Challan","refrence_no":"123456789012--amt-20","document_attached":null,"paid_to_account":"Hixson Technologies","paid_from_account":"sp_9811871855","status":0,"roi_status":0,"created_at":"2020-02-24 11:20:18","updated_at":"2020-02-24 11:20:18"},{"id":2,"userid":"u9811871855","package_amount":1000,"bonus_amount":2000,"balance_amount":2000,"bonus_credited":0,"total_amount":1180,"tax":18,"buy_date":"2019-09-10 00:00:00","payment_mode":"Bank-Challan","refrence_no":"123456789012--amt-20","document_attached":null,"paid_to_account":"Hixson Technologies","paid_from_account":"sp_9811871855","status":0,"roi_status":0,"created_at":"2020-02-24 11:20:21","updated_at":"2020-02-24 11:20:21"},{"id":3,"userid":"u9811871855","package_amount":1000,"bonus_amount":2000,"balance_amount":2000,"bonus_credited":0,"total_amount":1180,"tax":18,"buy_date":"2019-09-10 00:00:00","payment_mode":"Bank-Challan","refrence_no":"123456789012--amt-20","document_attached":null,"paid_to_account":"Hixson Technologies","paid_from_account":"sp_9811871855","status":0,"roi_status":0,"created_at":"2020-02-24 11:20:22","updated_at":"2020-02-24 11:20:22"},{"id":4,"userid":"u9811871855","package_amount":1000,"bonus_amount":2000,"balance_amount":2000,"bonus_credited":0,"total_amount":1180,"tax":18,"buy_date":"2019-09-10 00:00:00","payment_mode":"Bank-Challan","refrence_no":"123456789012--amt-20","document_attached":null,"paid_to_account":"Hixson Technologies","paid_from_account":"sp_9811871855","status":0,"roi_status":0,"created_at":"2020-02-24 11:20:23","updated_at":"2020-02-24 11:20:23"},{"id":5,"userid":"u9811871855","package_amount":1000,"bonus_amount":2000,"balance_amount":2000,"bonus_credited":0,"total_amount":1180,"tax":18,"buy_date":"2019-09-10 00:00:00","payment_mode":"Bank-Challan","refrence_no":"123456789012--amt-20","document_attached":null,"paid_to_account":"Hixson Technologies","paid_from_account":"sp_9811871855","status":0,"roi_status":0,"created_at":"2020-02-24 11:20:24","updated_at":"2020-02-24 11:20:24"},{"id":6,"userid":"u9811871855","package_amount":1000,"bonus_amount":2000,"balance_amount":2000,"bonus_credited":0,"total_amount":1180,"tax":18,"buy_date":"2019-09-10 00:00:00","payment_mode":"Bank-Challan","refrence_no":"123456789012--amt-20","document_attached":null,"paid_to_account":"Hixson Technologies","paid_from_account":"sp_9811871855","status":0,"roi_status":0,"created_at":"2020-02-24 11:20:25","updated_at":"2020-02-24 11:20:25"},{"id":7,"userid":"u9811871855","package_amount":1000,"bonus_amount":2000,"balance_amount":2000,"bonus_credited":0,"total_amount":1180,"tax":18,"buy_date":"2019-09-10 00:00:00","payment_mode":"Bank-Challan","refrence_no":"123456789012--amt-20","document_attached":null,"paid_to_account":"Hixson Technologies","paid_from_account":"sp_9811871855","status":0,"roi_status":0,"created_at":"2020-02-24 11:20:26","updated_at":"2020-02-24 11:20:26"},{"id":8,"userid":"u9811871855","package_amount":1000,"bonus_amount":2000,"balance_amount":2000,"bonus_credited":0,"total_amount":1180,"tax":18,"buy_date":"2019-09-10 00:00:00","payment_mode":"Bank-Challan","refrence_no":"123456789012--amt-20","document_attached":null,"paid_to_account":"Hixson Technologies","paid_from_account":"sp_9811871855","status":0,"roi_status":0,"created_at":"2020-02-24 11:20:27","updated_at":"2020-02-24 11:20:27"}]
         * instruction : ["Earn free risk return of up to 7% p.a","No penalty on breaking Fixed deposit. Break the entire amount or just the amount you need!","Create Fixed Deposit anytime with a minimum of only Rs. 1,000","Check your interest earnings anytime"]
         * investment_total : 9440
         */

        private double investment_total;
        private List<InvestmentBean> investment;
        private List<String> instruction;

        public double getInvestment_total() {
            return investment_total;
        }

        public void setInvestment_total(double investment_total) {
            this.investment_total = investment_total;
        }

        public List<InvestmentBean> getInvestment() {
            return investment;
        }

        public void setInvestment(List<InvestmentBean> investment) {
            this.investment = investment;
        }

        public List<String> getInstruction() {
            return instruction;
        }

        public void setInstruction(List<String> instruction) {
            this.instruction = instruction;
        }

        public static class InvestmentBean {
            /**
             * id : 1
             * userid : u9811871855
             * package_amount : 1000
             * bonus_amount : 2000
             * balance_amount : 2000
             * bonus_credited : 0
             * total_amount : 1180
             * tax : 18
             * buy_date : 2019-09-10 00:00:00
             * payment_mode : Bank-Challan
             * refrence_no : 123456789012--amt-20
             * document_attached : null
             * paid_to_account : Hixson Technologies
             * paid_from_account : sp_9811871855
             * status : 0
             * roi_status : 0
             * created_at : 2020-02-24 11:20:18
             * updated_at : 2020-02-24 11:20:18
             */
            private int id;
            private String userid;
            private double package_amount;
            private double bonus_amount;
            private double balance_amount;
            private double bonus_credited;
            private double total_amount;
            private double tax;
            private String buy_date;
            private String payment_mode;
            private String refrence_no;
            private Object document_attached;
            private String paid_to_account;
            private String paid_from_account;
            private int status;
            private int roi_status;
            private String finalAmount;
            private String days;
            private String rateOfInterest;
            private String roiApplied;
            private String totalDays;
            private String currentWithdrawalAmount;
            private String penalty;
            private String created_at;
            private String updated_at;
            /**
             * safepetransactionId : 20200224070056
             */

            private String safepetransactionId;

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

            public double getPackage_amount() {
                return package_amount;
            }

            public void setPackage_amount(double package_amount) {
                this.package_amount = package_amount;
            }

            public double getBonus_amount() {
                return bonus_amount;
            }

            public void setBonus_amount(double bonus_amount) {
                this.bonus_amount = bonus_amount;
            }

            public double getBalance_amount() {
                return balance_amount;
            }

            public void setBalance_amount(double balance_amount) {
                this.balance_amount = balance_amount;
            }

            public double getBonus_credited() {
                return bonus_credited;
            }

            public void setBonus_credited(double bonus_credited) {
                this.bonus_credited = bonus_credited;
            }

            public double getTotal_amount() {
                return total_amount;
            }

            public void setTotal_amount(double total_amount) {
                this.total_amount = total_amount;
            }

            public double getTax() {
                return tax;
            }

            public void setTax(double tax) {
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getRoi_status() {
                return roi_status;
            }

            public void setRoi_status(int roi_status) {
                this.roi_status = roi_status;
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

            public String getSafepetransactionId() {
                return safepetransactionId;
            }

            public void setSafepetransactionId(String safepetransactionId) {
                this.safepetransactionId = safepetransactionId;
            }

            public String getFinalAmount() {
                return finalAmount;
            }

            public String getDays() {
                return days;
            }

            public String getRateOfInterest() {
                return rateOfInterest;
            }

            public String getRoiApplied() {
                return roiApplied;
            }

            public String getTotalDays() {
                return totalDays;
            }

            public String getCurrentWithdrawalAmount() {
                return currentWithdrawalAmount;
            }

            public String getPenalty() {
                return penalty;
            }
        }
    }
}
