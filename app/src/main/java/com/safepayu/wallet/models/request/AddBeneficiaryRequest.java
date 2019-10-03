package com.safepayu.wallet.models.request;

public class AddBeneficiaryRequest {


    /**
     * name : Rahul Kumar
     * paytm :
     * upi :
     * bank_account : 564885464846546
     * ifsc_code : BOBR15465
     */

    private String name;
    private String paytm;
    private String upi;
    private String bank_account;
    private String ifsc_code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaytm() {
        return paytm;
    }

    public void setPaytm(String paytm) {
        this.paytm = paytm;
    }

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
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
}
