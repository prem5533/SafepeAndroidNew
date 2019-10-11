package com.safepayu.wallet.models.response;

public class CommissionDetailsResponse {

    /**
     * status : true
     * message : Commission Business Details
     * totleftbusiness : 0
     * totrightbusiness : 0
     * totmatchtbusiness : 0
     * totdirectbusiness : 0
     * totmatchincome : 0
     * totdirectincome : 0
     * totincome : 0
     * totwithdrawwallet : 100
     * totcurrentcommwallet : 0
     */

    private boolean status;
    private String message;
    private int totleftbusiness;
    private int totrightbusiness;
    private int totmatchtbusiness;
    private int totdirectbusiness;
    private int totmatchincome;
    private int totdirectincome;
    private int totincome;
    private int totwithdrawwallet;
    private int totcurrentcommwallet;

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

    public int getTotleftbusiness() {
        return totleftbusiness;
    }

    public void setTotleftbusiness(int totleftbusiness) {
        this.totleftbusiness = totleftbusiness;
    }

    public int getTotrightbusiness() {
        return totrightbusiness;
    }

    public void setTotrightbusiness(int totrightbusiness) {
        this.totrightbusiness = totrightbusiness;
    }

    public int getTotmatchtbusiness() {
        return totmatchtbusiness;
    }

    public void setTotmatchtbusiness(int totmatchtbusiness) {
        this.totmatchtbusiness = totmatchtbusiness;
    }

    public int getTotdirectbusiness() {
        return totdirectbusiness;
    }

    public void setTotdirectbusiness(int totdirectbusiness) {
        this.totdirectbusiness = totdirectbusiness;
    }

    public int getTotmatchincome() {
        return totmatchincome;
    }

    public void setTotmatchincome(int totmatchincome) {
        this.totmatchincome = totmatchincome;
    }

    public int getTotdirectincome() {
        return totdirectincome;
    }

    public void setTotdirectincome(int totdirectincome) {
        this.totdirectincome = totdirectincome;
    }

    public int getTotincome() {
        return totincome;
    }

    public void setTotincome(int totincome) {
        this.totincome = totincome;
    }

    public int getTotwithdrawwallet() {
        return totwithdrawwallet;
    }

    public void setTotwithdrawwallet(int totwithdrawwallet) {
        this.totwithdrawwallet = totwithdrawwallet;
    }

    public int getTotcurrentcommwallet() {
        return totcurrentcommwallet;
    }

    public void setTotcurrentcommwallet(int totcurrentcommwallet) {
        this.totcurrentcommwallet = totcurrentcommwallet;
    }
}
