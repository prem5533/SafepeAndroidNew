package com.safepayu.wallet.models.response;

import java.util.List;

public class GetBeneficiaryResponse {

    /**
     * status : true
     * message : A list of Beneficiary Details
     * beneficiary : [{"id":1,"benId":"BEN20190911194319","name":"Rahul Kumar","userid":"u8602278049","paytm":null,"upi":null,"bank_account":"564885464846546","ifsc_code":"BOBR15465","created_at":"2019-09-11 19:43:19","updated_at":"2019-09-11 19:43:19"}]
     */

    private boolean status;
    private String message;
    private List<BeneficiaryBean> beneficiary;

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

    public List<BeneficiaryBean> getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(List<BeneficiaryBean> beneficiary) {
        this.beneficiary = beneficiary;
    }

    public static class BeneficiaryBean {
        /**
         * id : 1
         * benId : BEN20190911194319
         * name : Rahul Kumar
         * userid : u8602278049
         * paytm : null
         * upi : null
         * bank_account : 564885464846546
         * ifsc_code : BOBR15465
         * created_at : 2019-09-11 19:43:19
         * updated_at : 2019-09-11 19:43:19
         */

        private int id;
        private String benId;
        private String name;
        private String userid;
        private Object paytm;
        private Object upi;
        private String bank_account;
        private String ifsc_code;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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
