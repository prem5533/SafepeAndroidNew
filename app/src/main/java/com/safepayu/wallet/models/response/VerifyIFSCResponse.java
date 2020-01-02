package com.safepayu.wallet.models.response;

public class VerifyIFSCResponse {

    /**
     * status : true
     * message : Data found successfully
     * data : {"BRANCH":"OKHLA PHASE 3","CENTRE":"DELHI","DISTRICT":"DELHI","STATE":"DELHI","ADDRESS":"ICICI BANK LTD., 254, OKHLA PHASE - 3, NEW DELHI - 110020, DELHI","CONTACT":"","MICR":"110229213","RTGS":true,"CITY":"DELHI","NEFT":true,"IMPS":true,"BANK":"ICICI Bank","BANKCODE":"ICIC","IFSC":"ICIC0001820"}
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
         * BRANCH : OKHLA PHASE 3
         * CENTRE : DELHI
         * DISTRICT : DELHI
         * STATE : DELHI
         * ADDRESS : ICICI BANK LTD., 254, OKHLA PHASE - 3, NEW DELHI - 110020, DELHI
         * CONTACT :
         * MICR : 110229213
         * RTGS : true
         * CITY : DELHI
         * NEFT : true
         * IMPS : true
         * BANK : ICICI Bank
         * BANKCODE : ICIC
         * IFSC : ICIC0001820
         */

        private String BRANCH;
        private String CENTRE;
        private String DISTRICT;
        private String STATE;
        private String ADDRESS;
        private String CONTACT;
        private String MICR;
        private boolean RTGS;
        private String CITY;
        private boolean NEFT;
        private boolean IMPS;
        private String BANK;
        private String BANKCODE;
        private String IFSC;

        public String getBRANCH() {
            return BRANCH;
        }

        public void setBRANCH(String BRANCH) {
            this.BRANCH = BRANCH;
        }

        public String getCENTRE() {
            return CENTRE;
        }

        public void setCENTRE(String CENTRE) {
            this.CENTRE = CENTRE;
        }

        public String getDISTRICT() {
            return DISTRICT;
        }

        public void setDISTRICT(String DISTRICT) {
            this.DISTRICT = DISTRICT;
        }

        public String getSTATE() {
            return STATE;
        }

        public void setSTATE(String STATE) {
            this.STATE = STATE;
        }

        public String getADDRESS() {
            return ADDRESS;
        }

        public void setADDRESS(String ADDRESS) {
            this.ADDRESS = ADDRESS;
        }

        public String getCONTACT() {
            return CONTACT;
        }

        public void setCONTACT(String CONTACT) {
            this.CONTACT = CONTACT;
        }

        public String getMICR() {
            return MICR;
        }

        public void setMICR(String MICR) {
            this.MICR = MICR;
        }

        public boolean isRTGS() {
            return RTGS;
        }

        public void setRTGS(boolean RTGS) {
            this.RTGS = RTGS;
        }

        public String getCITY() {
            return CITY;
        }

        public void setCITY(String CITY) {
            this.CITY = CITY;
        }

        public boolean isNEFT() {
            return NEFT;
        }

        public void setNEFT(boolean NEFT) {
            this.NEFT = NEFT;
        }

        public boolean isIMPS() {
            return IMPS;
        }

        public void setIMPS(boolean IMPS) {
            this.IMPS = IMPS;
        }

        public String getBANK() {
            return BANK;
        }

        public void setBANK(String BANK) {
            this.BANK = BANK;
        }

        public String getBANKCODE() {
            return BANKCODE;
        }

        public void setBANKCODE(String BANKCODE) {
            this.BANKCODE = BANKCODE;
        }

        public String getIFSC() {
            return IFSC;
        }

        public void setIFSC(String IFSC) {
            this.IFSC = IFSC;
        }
    }
}
