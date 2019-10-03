package com.safepayu.wallet.models.response;

public  class AddBeneficiaryResponse {


    /**
     * status : true
     * message : Beneficiary Added Successfully.
     * $beneficiary : {"benId":"BEN20190911194319","name":"Rahul Kumar","userid":"u8602278049","paytm":null,"upi":null,"bank_account":"564885464846546","ifsc_code":"BOBR15465","updated_at":"2019-09-11 19:43:19","created_at":"2019-09-11 19:43:19","id":1}
     */

    private boolean status;
    private String message;
    private $beneficiaryBean $beneficiary;
    /**
     * reason : Please provide a valid Bank IFSC code.
     */

    private String reason;

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

    public $beneficiaryBean get$beneficiary() {
        return $beneficiary;
    }

    public void set$beneficiary($beneficiaryBean $beneficiary) {
        this.$beneficiary = $beneficiary;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public static class $beneficiaryBean {
        /**
         * benId : BEN20190911194319
         * name : Rahul Kumar
         * userid : u8602278049
         * paytm : null
         * upi : null
         * bank_account : 564885464846546
         * ifsc_code : BOBR15465
         * updated_at : 2019-09-11 19:43:19
         * created_at : 2019-09-11 19:43:19
         * id : 1
         */

        private String benId;
        private String name;
        private String userid;
        private Object paytm;
        private Object upi;
        private String bank_account;
        private String ifsc_code;
        private String updated_at;
        private String created_at;
        private int id;

        public String getBenId() {
            return benId;
        }

        public void setBenId(String benId) {
            this.benId = benId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public Object getPaytm() {
            return paytm;
        }

        public void setPaytm(Object paytm) {
            this.paytm = paytm;
        }

        public Object getUpi() {
            return upi;
        }

        public void setUpi(Object upi) {
            this.upi = upi;
        }

        public String getBank_account() {
            return bank_account;
        }

        public void setBank_account(String bank_account) {
            this.bank_account = bank_account;
        }

        public String getIfsc_code() {
            return ifsc_code;
        }

        public void setIfsc_code(String ifsc_code) {
            this.ifsc_code = ifsc_code;
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
    }
}
